package com.cidacs.rl.editor.undo;

public abstract class Command {

    public abstract void undo();

    public abstract void redo();

    public abstract boolean merge(Command that);

}
