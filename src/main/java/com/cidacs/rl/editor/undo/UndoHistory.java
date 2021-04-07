package com.cidacs.rl.editor.undo;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

public class UndoHistory {

    @SuppressWarnings("serial")
    public static class UndoException extends RuntimeException {

    }

    protected List<Command> commandList;
    protected ListIterator<Command> commandListIterator;

    public UndoHistory() {
        commandList = new LinkedList<Command>();
        commandListIterator = commandList.listIterator();
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
        commandListIterator.next().redo();
    }

    public void undo() {
        if (!canUndo())
            throw new UndoException();
        commandListIterator.previous().undo();
    }

    public void push(Command command) {
        while (commandListIterator.hasNext()) {
            commandListIterator.next();
            commandListIterator.remove();
        }
        command.redo();
        commandListIterator.add(command);
    }

}
