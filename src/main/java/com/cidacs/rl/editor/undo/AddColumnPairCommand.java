package com.cidacs.rl.editor.undo;

import com.cidacs.rl.editor.pair.ColumnPairManager;

public class AddColumnPairCommand extends Command {

    private ColumnPairManager manager;
    int index = 0;
    private String type;
    private String leftColumn;
    private String rightColumn;

    public AddColumnPairCommand(ColumnPairManager manager) {
        this(manager, null, null, null);
    }

    public AddColumnPairCommand(ColumnPairManager manager, String type,
            String leftColumn, String rightColumn) {
        this.manager = manager;
        this.type = type;
        this.leftColumn = leftColumn;
        this.rightColumn = rightColumn;
    }

    @Override
    public void undo() {
        manager.deleteColumnPair(index);
    }

    @Override
    public void redo() {
        index = manager.addColumnPair(type, leftColumn, rightColumn);
    }

    @Override
    public String getSummary() {
        return "add column";
    }

}
