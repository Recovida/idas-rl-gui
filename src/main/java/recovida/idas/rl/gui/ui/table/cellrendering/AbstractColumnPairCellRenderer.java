package recovida.idas.rl.gui.ui.table.cellrendering;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import recovida.idas.rl.gui.ui.WarningIcon;
import recovida.idas.rl.gui.ui.table.ColumnPairTableModel;

public abstract class AbstractColumnPairCellRenderer extends JLabel
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

    protected boolean isValid(JTable table, int viewRowIndex,
            int viewColIndex) {
        return getModel(table).isCellValid(
                table.convertRowIndexToModel(viewRowIndex),
                table.convertColumnIndexToModel(viewColIndex));
    }

}
