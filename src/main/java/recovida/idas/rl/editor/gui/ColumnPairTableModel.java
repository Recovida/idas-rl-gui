package recovida.idas.rl.editor.gui;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import recovida.idas.rl.editor.lang.MessageProvider;

public class ColumnPairTableModel extends DefaultTableModel {

    private static final long serialVersionUID = 1L;

    private final String[] keys = { "number", "type", "weight", "phon_weight",
            "index_a", "rename_a", "index_b", "rename_b" };
    private final Class<?>[] types = { Integer.class, String.class,
            Double.class, Double.class, String.class, String.class,
            String.class, String.class };
    private Map<String, Integer> indexFromKey = new HashMap<>();

    public ColumnPairTableModel() {
        for (int i = 0; i < keys.length; i++)
            indexFromKey.put(keys[i], i);
        setColumnIdentifiers(keys); // temporary
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }

    public Object getValue(int rowIndex, String key) {
        return getValueAt(rowIndex, indexFromKey.get(key));
    }

    public String getStringValue(int rowIndex, String key) {
        return (String) getValue(rowIndex, key);
    }

    public Integer getIntegerValue(int rowIndex, String key) {
        return (Integer) getValue(rowIndex, key);
    }

    public Double getDoubleValue(int rowIndex, String key) {
        return (Double) getValue(rowIndex, key);
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int colIndex) {
        super.setValueAt(value, rowIndex, colIndex);
        if (colIndex == getColumnIndex("type"))
            fireTableRowsUpdated(rowIndex, rowIndex);
        else if (colIndex == getColumnIndex("index_a"))
            fireTableCellUpdated(rowIndex, getColumnIndex("rename_a"));
        else if (colIndex == getColumnIndex("index_b"))
            fireTableCellUpdated(rowIndex, getColumnIndex("rename_b"));
    }

    public void setValue(int rowIndex, String key, Object value) {
        setValueAt(value, rowIndex, indexFromKey.get(key));
    }

    public Object[] getRowAsArray(int rowIndex) {
        Object[] r = new Object[getColumnCount()];
        for (int i = 0; i < getColumnCount(); i++)
            r[i] = getValueAt(rowIndex, i);
        return r;
    }

    public Map<String, Object> getRowAsMap(int rowIndex) {
        Map<String, Object> r = new LinkedHashMap<>();
        for (int i = 0; i < getColumnCount(); i++) {
            r.put(keys[i], getValueAt(rowIndex, i));
        }
        return r;
    }

    @Override
    public Class<?> getColumnClass(int column) {
        return types[column];
    }

    public String getColumnKey(int columnIndex) {
        return keys[columnIndex];
    }

    public int getColumnIndex(String key) {
        return indexFromKey.get(key);
    }

    public void updateLocalisedStrings(TableColumnModel cm) {
        String[] displayNames = new String[keys.length];
        for (int i = 0; i < displayNames.length; i++)
            cm.getColumn(i).setHeaderValue(MessageProvider.getMessage(
                    "columns.table." + keys[i].replaceAll("_", "")));
        if (getRowCount() > 0)
            fireTableRowsUpdated(0, getRowCount() - 1);
    }

    public boolean validate() {
        for (int i = 0; i < getRowCount(); i++)
            if (!validateRow(i))
                return false;
        return true;
    }

    public boolean validateRow(int rowIndex) {
        for (String key : keys)
            if (!validateCell(rowIndex, key))
                return false;
        return true;
    }

    protected Collection<String> firstDatasetColumnNames = null;
    protected Collection<String> secondDatasetColumnNames = null;

    public synchronized boolean validateCell(int rowIndex, String key) {
        Object value = getValue(rowIndex, key);
        switch (key) {
        case "type":
            return LinkageColumnEditingPanel.getTypes().contains(value);
        case "weight":
            return "copy".equals(getValue(rowIndex, "type"))
                    || value instanceof Double && (Double) value >= 0;
        case "phon_weight":
            return !"name".equals(getValue(rowIndex, "type"))
                    || value instanceof Double && (Double) value >= 0;
        case "index_a":
            return firstDatasetColumnNames == null
                    || ("copy".equals(getValue(rowIndex, "type"))
                            && "".equals(value))
                    || firstDatasetColumnNames.contains(value);
        case "index_b":
            return secondDatasetColumnNames == null
                    || ("copy".equals(getValue(rowIndex, "type"))
                            && "".equals(value))
                    || secondDatasetColumnNames.contains(value);
        case "rename_a":
        case "rename_b":
            return true;
        case "number":
            return false;
        default:
            return false;
        }
    }

}
