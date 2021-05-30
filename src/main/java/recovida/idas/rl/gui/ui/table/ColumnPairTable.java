package recovida.idas.rl.gui.ui.table;

import java.util.Collections;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import recovida.idas.rl.gui.ui.table.cellrendering.NameColumnPairCellRenderer;
import recovida.idas.rl.gui.ui.table.cellrendering.NumberColumnPairCellRenderer;
import recovida.idas.rl.gui.ui.table.cellrendering.PhonWeightColumnPairCellRenderer;
import recovida.idas.rl.gui.ui.table.cellrendering.RenameColumnPairCellRenderer;
import recovida.idas.rl.gui.ui.table.cellrendering.TypeColumnPairCellRenderer;
import recovida.idas.rl.gui.ui.table.cellrendering.WeightColumnPairCellRenderer;

public class ColumnPairTable extends JTable {

    private static final long serialVersionUID = -7568545759297132632L;
    
    
    protected ColumnPairTableModel model;
    
    public ColumnPairTable(ColumnPairTableModel model) {
        super(model);
        this.model = model;
        getTableHeader().setReorderingAllowed(false);
        setShowVerticalLines(true);
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        TableRowSorter<ColumnPairTableModel> sorter = new TableRowSorter<>(model);
        sorter.setSortKeys(Collections.singletonList(new RowSorter.SortKey(0, SortOrder.ASCENDING)));
        setRowSorter(sorter);
        setUpdateSelectionOnSort(true);
        setRowHeight(getRowHeight() * 15 / 10);
        sorter.setSortsOnUpdates(true);
        SwingUtilities.invokeLater(() -> {
            getColumnModel().getColumn(model.getColumnIndex("weight"))
                    .setCellRenderer(new WeightColumnPairCellRenderer());
            getColumnModel().getColumn(model.getColumnIndex("phon_weight"))
                    .setCellRenderer(new PhonWeightColumnPairCellRenderer());
            getColumnModel().getColumn(model.getColumnIndex("rename_a"))
                    .setCellRenderer(new RenameColumnPairCellRenderer("index_a"));
            getColumnModel().getColumn(model.getColumnIndex("rename_b"))
                    .setCellRenderer(new RenameColumnPairCellRenderer("index_b"));
            getColumnModel().getColumn(model.getColumnIndex("index_a"))
                    .setCellRenderer(new NameColumnPairCellRenderer());
            getColumnModel().getColumn(model.getColumnIndex("index_b"))
                    .setCellRenderer(new NameColumnPairCellRenderer());
            getColumnModel().getColumn(model.getColumnIndex("type")).setCellRenderer(new TypeColumnPairCellRenderer());
            getColumnModel().getColumn(model.getColumnIndex("number"))
                    .setCellRenderer(new NumberColumnPairCellRenderer());
        });

    }
    
    @Override
    public ColumnPairTableModel getModel() {
        return model;
    }
    
    @Override
    public void setModel(TableModel model) {
        if (!(model instanceof ColumnPairTableModel))
            throw new IllegalArgumentException();
        this.model = (ColumnPairTableModel) model;
        super.setModel(model);
    }


}
