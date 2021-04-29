package com.cidacs.rl.editor.undo;

import com.cidacs.rl.editor.pair.ColumnPairManager;

public class DeleteColumnPairCommand extends Command {

    private ColumnPairManager manager;
    int index = 0;
    private Object[] contents = new Object[0];

    public DeleteColumnPairCommand(ColumnPairManager manager, int index) {
        this.manager = manager;
        this.index = index;
        System.out.println(index);
    }

    @Override
    public void undo() {
        manager.addColumnPair(index, contents);
    }

    @Override
    public void redo() {
        contents = manager.deleteColumnPair(index);
    }

}
