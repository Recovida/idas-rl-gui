package recovida.idas.rl.gui.ui.table.cellrendering;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import recovida.idas.rl.gui.ui.table.ColumnPairTable;

/**
 * Renderer for "phon_weight" in a {@link ColumnPairTable}. Except for the fact
 * that it does not display a value when type is not "name", it is identical to
 * {@link WeightColumnPairCellRenderer}.
 */
public class PhonWeightColumnPairCellRenderer
        extends WeightColumnPairCellRenderer implements TableCellRenderer {

    private static final long serialVersionUID = 231461201036374229L;

    @Override
    public boolean shouldBeBlank(JTable table, int row) {
        return !"name".equals(getValue(table, row, "type"));
    }

}
