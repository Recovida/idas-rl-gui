package recovida.idas.rl.gui.undo;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

/**
 * This class is a command manager for command design pattern.
 */
public class UndoHistory {

    /**
     * Exception that is thrown after an attempt to {@link UndoHistory#undo()}
     * or {@link UndoHistory#redo()} when such action is not available.
     */
    public static class UndoException extends RuntimeException {

        private static final long serialVersionUID = -5362766944453158884L;

    }

    /**
     * Represents a state {@link UndoHistory}.
     */
    protected static class HistoryPropertyState {
        boolean canRedo = false;

        boolean canUndo = false;

        boolean isClean = true;

        String undoSummary = null;

        String redoSummary = null;

        /**
         * Builds a snapshot of the current state.
         *
         * @param history the history
         * @return a snapshot of the history
         */
        public static HistoryPropertyState get(UndoHistory history) {
            HistoryPropertyState s = new HistoryPropertyState();
            s.canRedo = history.canRedo();
            s.canUndo = history.canUndo();
            s.isClean = history.isClean();
            s.undoSummary = history.getUndoSummary();
            s.redoSummary = history.getRedoSummary();
            return s;
        }

        /**
         * Sends notification to listeners if the state given in the parameter
         * differs from this state.
         *
         * @param that      the other state
         * @param listeners a collection of listeners to be notified if the
         *                  states are different
         */
        public void notifyIfChanged(HistoryPropertyState that,
                Collection<HistoryPropertyChangeEventListener> listeners) {
            for (HistoryPropertyChangeEventListener listener : listeners) {
                if (canRedo != that.canRedo)
                    listener.canRedoChanged(canRedo);
                if (canUndo != that.canUndo)
                    listener.canUndoChanged(canUndo);
                if (isClean != that.isClean)
                    listener.cleanChanged(isClean);
                if (!Objects.equals(undoSummary, that.undoSummary))
                    listener.undoSummaryChanged(undoSummary);
                if (!Objects.equals(redoSummary, that.redoSummary))
                    listener.redoSummaryChanged(redoSummary);
            }
        }
    }

    protected List<AbstractCommand> commandList;

    protected ListIterator<AbstractCommand> commandListIterator;

    protected int cleanIndex;

    protected List<HistoryPropertyChangeEventListener> listeners;

    /**
     * Creates an empty undo history.
     */
    public UndoHistory() {
        commandList = new LinkedList<>();
        commandListIterator = commandList.listIterator();
        cleanIndex = 0;
        listeners = new LinkedList<>();
    }

    /**
     * Checks whether an undo action is possible.
     *
     * @return <code>true</code> if and only if {@link #undo()} can be called
     */
    public boolean canUndo() {
        return commandListIterator.hasPrevious();
    }

    /**
     * Checks whether a redo action is possible.
     *
     * @return <code>true</code> if and only if {@link #redo()} can be called
     */
    public boolean canRedo() {
        return commandListIterator.hasNext();
    }

    /**
     * Does or redoes an action. Listeners are notified.
     *
     * @throws UndoException if there is no action to redo
     */
    public void redo() {
        if (!canRedo())
            throw new UndoException();
        HistoryPropertyState oldState = HistoryPropertyState.get(this);
        commandListIterator.next().redo();
        HistoryPropertyState.get(this).notifyIfChanged(oldState, listeners);
    }

    /**
     * Undoes an action. Listeners are notified.
     *
     * @throws UndoException if there is no action to undo.
     */
    public void undo() {
        if (!canUndo())
            throw new UndoException();
        HistoryPropertyState oldState = HistoryPropertyState.get(this);
        commandListIterator.previous().undo();
        HistoryPropertyState.get(this).notifyIfChanged(oldState, listeners);
    }

    /**
     * Pushes a command to the list and executes its action. Listeners are
     * notified.
     *
     * @param command the command to be added
     */
    public void push(AbstractCommand command) {
        HistoryPropertyState oldState = HistoryPropertyState.get(this);
        if (cleanIndex > commandListIterator.nextIndex())
            cleanIndex = -1;
        else if (!isClean() && commandListIterator.hasPrevious()
                && !commandListIterator.hasNext()) {
            AbstractCommand previousCommand = commandListIterator.previous();
            commandListIterator.next();
            if (previousCommand.merge(command))
                command.markAsMerged();
        } else
            while (commandListIterator.hasNext()) {
                commandListIterator.next();
                commandListIterator.remove();
            }
        command.redo();
        if (!command.wasMerged())
            commandListIterator.add(command);
        HistoryPropertyState.get(this).notifyIfChanged(oldState, listeners);
    }

    public boolean isClean() {
        return cleanIndex == commandListIterator.nextIndex();
    }

    public void setClean() {
        HistoryPropertyState oldState = HistoryPropertyState.get(this);
        cleanIndex = commandListIterator.nextIndex();
        HistoryPropertyState.get(this).notifyIfChanged(oldState, listeners);
    }

    public int getCleanIndex() {
        return cleanIndex;
    }

    /**
     * Adds a listener that will be notified when state changes.
     *
     * @param listener the listener to add
     */
    public void addPropertyChangeListener(
            HistoryPropertyChangeEventListener listener) {
        listeners.add(listener);
    }

    /**
     * Gets a short description of the action that can be undone (to be
     * displayed on a menu or button tooltip). Uses the current language.
     *
     * @return <code>null</code> if it is impossible to undo, or a short summary
     *         of the action that can be undone
     */
    public String getUndoSummary() {
        if (!canUndo())
            return null;
        String summary = commandListIterator.previous().getSummary();
        commandListIterator.next();
        return summary;
    }

    /**
     * Gets a short description of the action that can be redone (to be
     * displayed on a menu or button tooltip). Uses the current language.
     *
     * @return <code>null</code> if it is impossible to redo, or a short summary
     *         of the action that can be undone
     */
    public String getRedoSummary() {
        if (!canRedo())
            return null;
        String summary = commandListIterator.next().getSummary();
        commandListIterator.previous();
        return summary;
    }

    /**
     * Clears the history, making it equivalent to a fresh instance. If the
     * history was not previously empty, listeners are notified.
     */
    public void clearAll() {
        HistoryPropertyState oldState = HistoryPropertyState.get(this);
        commandList = new LinkedList<>();
        commandListIterator = commandList.listIterator();
        cleanIndex = 0;
        HistoryPropertyState.get(this).notifyIfChanged(oldState, listeners);
    }

}
