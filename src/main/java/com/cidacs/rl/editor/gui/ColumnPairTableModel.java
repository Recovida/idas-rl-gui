package com.cidacs.rl.editor.gui;

import javax.swing.table.DefaultTableModel;

public class ColumnPairTableModel extends DefaultTableModel {

    private static final long serialVersionUID = 1L;

    public ColumnPairTableModel() {
        addColumn("Number");
        addColumn("ID");
        addColumn("Type");
        addColumn("Weight");
        addColumn("Phon. weight");
        addColumn("Column (A)");
        addColumn("Renamed (A)");
        addColumn("Column (B)");
        addColumn("Renamed (B)");
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

}
