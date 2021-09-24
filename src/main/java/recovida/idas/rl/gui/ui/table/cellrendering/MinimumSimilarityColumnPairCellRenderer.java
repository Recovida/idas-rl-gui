package recovida.idas.rl.gui.ui.table.cellrendering;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import recovida.idas.rl.gui.lang.MessageProvider;
import recovida.idas.rl.gui.ui.table.ColumnPairTable;

/**
 * Renderer for minimum similarity field in a {@link ColumnPairTable}. The
 * decimal separator is chosen according to the current language.
 */
public class MinimumSimilarityColumnPairCellRenderer
        extends AbstractColumnPairCellRenderer implements TableCellRenderer {

    private static final long serialVersionUID = 4089960339928197149L;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (shouldBeBlank(table, row) || !(value instanceof Double))
            value = "";
        else
            value = String.format(MessageProvider.getLocale(), "%.4f", value);
        Component c = getDefaultRenderer(table, value, isSelected, hasFocus,
                row, column);
        if (!isValid(table, row, column)) {
            c.setForeground(getErrorColour(isSelected));
            showErrorIcon(table, c);
        } else {
            if (c instanceof JLabel)
                ((JLabel) c).setHorizontalAlignment(JLabel.RIGHT);
        }
        return c;
    }

    protected boolean shouldBeBlank(JTable table, int row) {
        return !"name".equals(getValue(table, row, "type")) || Double
                .valueOf(0.0).equals(getValue(table, row, "min_similarity"));
    }

}
