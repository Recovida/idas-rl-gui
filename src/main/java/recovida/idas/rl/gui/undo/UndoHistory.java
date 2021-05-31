package recovida.idas.rl.gui.undo;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

public class UndoHistory {

    @SuppressWarnings("serial")
    public static class UndoException extends RuntimeException {

    }

    protected static class HistoryPropertyState {
        boolean canRedo = false;
        boolean canUndo = false;
        boolean isClean = true;
        String undoSummary = null;
        String redoSummary = null;

        public static HistoryPropertyState get(UndoHistory h) {
            HistoryPropertyState s = new HistoryPropertyState();
            s.canRedo = h.canRedo();
            s.canUndo = h.canUndo();
            s.isClean = h.isClean();
            s.undoSummary = h.getUndoSummary();
            s.redoSummary = h.getRedoSummary();
            return s;
        }

        public void notifyIfChanged(HistoryPropertyState that,
                Collection<HistoryPropertyChangeEventListener> listeners) {
            for (HistoryPropertyChangeEventListener listener : listeners) {
                if (this.canRedo != that.canRedo)
                    listener.canRedoChanged(this.canRedo);
                if (this.canUndo != that.canUndo)
                    listener.canUndoChanged(this.canUndo);
                if (this.isClean != that.isClean)
                    listener.cleanChanged(this.isClean);
                if (!Objects.equals(this.undoSummary, that.undoSummary))
                    listener.undoSummaryChanged(this.undoSummary);
                if (!Objects.equals(this.redoSummary, that.redoSummary))
                    listener.redoSummaryChanged(this.redoSummary);
            }
        }
    }

    protected List<AbstractCommand> commandList;
    protected ListIterator<AbstractCommand> commandListIterator;
    protected int cleanIndex;
    protected List<HistoryPropertyChangeEventListener> listeners;

    public UndoHistory() {
        commandList = new LinkedList<>();
        commandListIterator = commandList.listIterator();
        cleanIndex = 0;
        listeners = new LinkedList<>();
    }

    public boolean canUndo() {
        return commandListIterator.hasPrevious();
    }

    public boolean canRedo() {
        return commandListIterator.hasNext();
    }

    public void redo() {
        if (!canRedo())
            throw new UndoException();
        HistoryPropertyState oldState = HistoryPropertyState.get(this);
        commandListIterator.next().redo();
        HistoryPropertyState.get(this).notifyIfChanged(oldState, listeners);
    }

    public void undo() {
        if (!canUndo())
            throw new UndoException();
        HistoryPropertyState oldState = HistoryPropertyState.get(this);
        commandListIterator.previous().undo();
        HistoryPropertyState.get(this).notifyIfChanged(oldState, listeners);
    }

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

    public void addPropertyChangeListener(
            HistoryPropertyChangeEventListener listener) {
        listeners.add(listener);
    }

    public String getUndoSummary() {
        if (!canUndo())
            return null;
        String summary = commandListIterator.previous().getSummary();
        commandListIterator.next();
        return summary;
    }

    public String getRedoSummary() {
        if (!canRedo())
            return null;
        String summary = commandListIterator.next().getSummary();
        commandListIterator.previous();
        return summary;
    }

    public void clearAll() {
        HistoryPropertyState oldState = HistoryPropertyState.get(this);
        commandList = new LinkedList<>();
        commandListIterator = commandList.listIterator();
        cleanIndex = 0;
        HistoryPropertyState.get(this).notifyIfChanged(oldState, listeners);
    }

}
