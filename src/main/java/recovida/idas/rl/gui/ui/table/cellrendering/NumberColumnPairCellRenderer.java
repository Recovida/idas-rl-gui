package recovida.idas.rl.gui.ui.table.cellrendering;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import recovida.idas.rl.gui.lang.MessageProvider;
import recovida.idas.rl.gui.ui.table.ColumnPairTable;

/**
 * Renders the "number" field in a {@link ColumnPairTable}. The text is bold and
 * right-aligned.
 */
public class NumberColumnPairCellRenderer extends AbstractColumnPairCellRenderer
        implements TableCellRenderer {

    private static final long serialVersionUID = 1791050245687596559L;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        boolean invalid = !isValid(table, row, column);
        boolean italic = false;
        if (value == null || (int) value < 0) {
            italic = true;
            value = "  " + MessageProvider.getMessage("columns.table.blank");
        }
        Component c = getDefaultRenderer(table, value, isSelected, hasFocus,
                row, column);
        if (invalid) {
            showErrorIcon(table, c);
            c.setForeground(getErrorColour(isSelected));
            if (italic)
                c.setFont(c.getFont().deriveFont(Font.ITALIC));
        }

        if (!italic && c instanceof JLabel) {
            ((JLabel) c).setHorizontalAlignment(JLabel.RIGHT);
            c.setFont(c.getFont().deriveFont(Font.BOLD));
        }
        return c;
    }

}
