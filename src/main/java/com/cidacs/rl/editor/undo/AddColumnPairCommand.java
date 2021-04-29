package com.cidacs.rl.editor.undo;

import com.cidacs.rl.editor.pair.ColumnPairManager;

public class AddColumnPairCommand extends Command {

    private ColumnPairManager manager;
    int index = 0;

    public AddColumnPairCommand(ColumnPairManager manager) {
        this.manager = manager;
    }

    @Override
    public void undo() {
        manager.deleteColumnPair(index);
    }

    @Override
    public void redo() {
        index = manager.addColumnPair();
    }

}
