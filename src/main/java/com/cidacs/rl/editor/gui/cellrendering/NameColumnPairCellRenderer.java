package com.cidacs.rl.editor.gui.cellrendering;

import java.awt.Component;
import java.awt.Font;
import java.util.Collection;
import java.util.Collections;

import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import com.cidacs.rl.editor.lang.MessageProvider;

public class NameColumnPairCellRenderer extends ColumnPairCellRenderer
        implements TableCellRenderer {

    private static final long serialVersionUID = 4089960339928197149L;

    protected Collection<String> validNames = null;

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (value == null)
            value = "";
        boolean invalid = !isValid(table, row, value);
        boolean italic = false;
        if (invalid && "".equals(value)) {
            value = getBlankText(table, row);
            italic = true;
        }
        Component c = getDefaultRenderer(table, value, isSelected, hasFocus,
                row, column);
        if (invalid) {
            c.setForeground(getErrorColour(isSelected));
            if (italic)
                c.setFont(c.getFont().deriveFont(Font.ITALIC));
            showErrorIcon(table, c);
        }
        return c;
    }

    public boolean isValid(JTable table, int viewRowIndex, Object value) {
        return validNames == null
                || ("copy".equals(getValue(table, viewRowIndex, "type"))
                        && "".equals(value))
                || validNames.contains(value);
    }

    public String getBlankText(JTable table, int viewRowIndex) {
        if ("copy".equals(getValue(table, viewRowIndex, "type")))
            return "";
        return "  " + MessageProvider.getMessage("columns.table.blank");
    }

    public Collection<String> getValidNames() {
        return Collections.unmodifiableCollection(validNames);
    }

    public void setValidNames(Collection<String> validNames) {
        this.validNames = validNames;
    }

}
