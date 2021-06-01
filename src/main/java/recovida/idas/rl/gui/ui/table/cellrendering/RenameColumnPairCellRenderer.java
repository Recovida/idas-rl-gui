package recovida.idas.rl.gui.ui.table.cellrendering;

import java.awt.Component;
import java.util.Optional;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import recovida.idas.rl.gui.ui.table.ColumnPairTable;

/**
 * Renderer for "rename" fields in a {@link ColumnPairTable}, which shows the
 * default value when blank.
 */
public class RenameColumnPairCellRenderer extends AbstractColumnPairCellRenderer
        implements TableCellRenderer {

    private static final long serialVersionUID = 4089960339928197149L;

    private final String nameKey;

    private String suffix = "";

    /**
     * Creates the renderer given the key of the corresponding name field.
     *
     * @param nameKey the key of the corresponding field name (e.g. if this
     *                class renders <code>rename_a</code> field, then this
     *                parameter is <code>index_a</code>)
     */
    public RenameColumnPairCellRenderer(String nameKey) {
        this.nameKey = nameKey;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        boolean showPlaceholder = false;
        if (value == null)
            value = "";
        if (shouldBeBlank(table, row))
            value = "";
        else if (value instanceof String && ((String) value).isEmpty()) {
            value = getPlaceholder(table, row);
            showPlaceholder = true;
        }
        Component c = getDefaultRenderer(table, value, isSelected, hasFocus,
                row, column);
        if (showPlaceholder)
            c.setForeground(getPlaceholderColour(isSelected));
        return c;
    }

    protected boolean shouldBeBlank(JTable table, int viewRowIndex) {
        return Optional
                .ofNullable((String) getValue(table, viewRowIndex, nameKey))
                .orElse("").isEmpty();
    }

    public String getPlaceholder(JTable table, int viewRowIndex) {
        String name = Optional
                .ofNullable((String) getValue(table, viewRowIndex, nameKey))
                .orElse("");
        return name.isEmpty() ? "" : (name + '_' + suffix);
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = Optional.ofNullable(suffix).orElse("");
    }

}
