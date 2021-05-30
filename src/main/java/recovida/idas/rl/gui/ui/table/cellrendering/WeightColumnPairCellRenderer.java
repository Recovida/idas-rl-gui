package recovida.idas.rl.gui.ui.table.cellrendering;

import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class WeightColumnPairCellRenderer extends ColumnPairCellRenderer
        implements TableCellRenderer {

    private static final long serialVersionUID = 4089960339928197149L;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (shouldBeBlank(table, row))
            value = "";
        Component c = getDefaultRenderer(table, value, isSelected, hasFocus,
                row, column);
        if (!isValid(table, row, column)) {
            c.setForeground(getErrorColour(isSelected));
            showErrorIcon(table, c);
        }
        return c;
    }

    public boolean shouldBeBlank(JTable table, int row) {
        return "copy".equals(getValue(table, row, "type"));
    }

}
