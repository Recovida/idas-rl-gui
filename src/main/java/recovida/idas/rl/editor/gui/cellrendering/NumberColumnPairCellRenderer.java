package recovida.idas.rl.editor.gui.cellrendering;

import java.awt.Component;
import java.awt.Font;
import java.util.Collection;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import recovida.idas.rl.editor.lang.MessageProvider;

public class NumberColumnPairCellRenderer extends ColumnPairCellRenderer
        implements TableCellRenderer {

    private static final long serialVersionUID = 1791050245687596559L;

    public interface ColIdxProvider {
        Collection<Integer> getColIdxWithNumber(int number);
    }

    protected ColIdxProvider colIdxProvider = null;

    public void setColIdxProvider(ColIdxProvider colIdxProvider) {
        this.colIdxProvider = colIdxProvider;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        boolean invalid = false;
        boolean italic = true;
        if (value == null || (int) value < 0) {
            invalid = true;
            italic = true;
            value = "  " + MessageProvider.getMessage("columns.table.blank");
        } else {
            if (colIdxProvider != null) {
                if (colIdxProvider.getColIdxWithNumber((int) value)
                        .size() > 1) {
                    invalid = true;
                }
            }
        }
        Component c = getDefaultRenderer(table, value, isSelected, hasFocus,
                row, column);
        if (invalid) {
            showErrorIcon(table, c);
            c.setForeground(getErrorColour(isSelected));
            if (italic)
                c.setFont(c.getFont().deriveFont(Font.ITALIC));
        }
        return c;
    }

}
