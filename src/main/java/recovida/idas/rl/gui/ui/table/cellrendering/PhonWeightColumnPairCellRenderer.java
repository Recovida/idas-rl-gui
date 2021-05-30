package recovida.idas.rl.gui.ui.table.cellrendering;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class PhonWeightColumnPairCellRenderer
        extends WeightColumnPairCellRenderer implements TableCellRenderer {

    private static final long serialVersionUID = 231461201036374229L;

    @Override
    public boolean shouldBeBlank(JTable table, int row) {
        return !"name".equals(getValue(table, row, "type"));
    }

}
