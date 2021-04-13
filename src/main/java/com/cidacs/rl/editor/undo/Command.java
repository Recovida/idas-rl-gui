package com.cidacs.rl.editor.undo;

public abstract class Command {

    public abstract void undo();

    public abstract void redo();

    public boolean merge(Command that) {
        return false;
    }

}
