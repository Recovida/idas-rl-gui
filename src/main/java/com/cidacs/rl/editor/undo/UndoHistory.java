package com.cidacs.rl.editor.undo;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class UndoHistory {

    @SuppressWarnings("serial")
    public static class UndoException extends RuntimeException {

    }

    protected static class HistoryPropertyState {
        boolean canRedo = false;
        boolean canUndo = false;
        boolean isClean = true;

        public static HistoryPropertyState get(UndoHistory h) {
            HistoryPropertyState s = new HistoryPropertyState();
            s.canRedo = h.canRedo();
            s.canUndo = h.canUndo();
            s.isClean = h.isClean();
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
            }
        }
    }

    protected List<Command> commandList;
    protected ListIterator<Command> commandListIterator;
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

    public void push(Command command) {
        HistoryPropertyState oldState = HistoryPropertyState.get(this);
        if (cleanIndex > commandListIterator.nextIndex())
            cleanIndex = -1;
        else if (!isClean() && commandListIterator.hasPrevious()
                && !commandListIterator.hasNext()) {
            Command previousCommand = commandListIterator.previous();
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

}
