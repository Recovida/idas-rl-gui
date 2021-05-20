package recovida.idas.rl.editor.gui.cellrendering;

import java.util.Collection;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import recovida.idas.rl.editor.gui.LinkageColumnEditingPanel;

public class TypeColumnPairCellRenderer extends NameColumnPairCellRenderer
        implements TableCellRenderer {

    private static final long serialVersionUID = 5803111621355561051L;

    public TypeColumnPairCellRenderer() {
        super();
        super.setValidNames(LinkageColumnEditingPanel.getTypes());
    }

    @Override
    public boolean isValid(JTable table, int viewRowIndex, Object value) {
        return validNames.contains(value);
    }

    @Override
    public void setValidNames(Collection<String> validNames) {
        // cannot change
    }

}
