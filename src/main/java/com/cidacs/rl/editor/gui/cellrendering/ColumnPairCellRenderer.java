package com.cidacs.rl.editor.gui.cellrendering;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import com.cidacs.rl.editor.gui.ColumnPairTableModel;
import com.cidacs.rl.editor.gui.WarningIcon;

public abstract class ColumnPairCellRenderer extends JLabel
        implements TableCellRenderer {

    private static final long serialVersionUID = 4429833685885120604L;

    protected ColumnPairTableModel getModel(JTable table) {
        return (ColumnPairTableModel) table.getModel();
    }

    protected Component getDefaultRenderer(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        return renderer.getTableCellRendererComponent(table, value, isSelected,
                hasFocus, row, column);
    }

    protected Object getValue(JTable table, int viewRowIndex, String key) {
        return getModel(table)
                .getValue(table.convertRowIndexToModel(viewRowIndex), key);
    }

    protected Color getErrorColour(boolean isSelected) {
        return !isSelected ? new Color(255, 0, 0) : new Color(255, 150, 150);
    }

    protected Color getPlaceholderColour(boolean isSelected) {
        return !isSelected ? Color.GRAY : Color.LIGHT_GRAY;
    }

    protected void showErrorIcon(JTable table, JLabel c) {
        WarningIcon.applyIconToLabel(c, table.getRowHeight() * 4 / 5);
    }

    protected void showErrorIcon(JTable table, Component c) {
        if (c instanceof JLabel)
            showErrorIcon(table, (JLabel) c);
    }

}
