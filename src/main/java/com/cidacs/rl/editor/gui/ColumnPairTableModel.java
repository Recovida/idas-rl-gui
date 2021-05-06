package com.cidacs.rl.editor.gui;

import java.util.HashMap;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

public class ColumnPairTableModel extends DefaultTableModel {

    private static final long serialVersionUID = 1L;

    private final String[] keys = { "number", "type", "weight", "phon_weight",
            "index_a", "rename_a", "index_b", "rename_b" };
    private final Class<?>[] types = { Integer.class, String.class,
            Double.class, Double.class, String.class, String.class,
            String.class, String.class };
    private Map<String, Integer> indexFromKey = new HashMap<>();

    // TODO: i18n
    private final String[] displayNames = { "Number", "Type", "Weight",
            "Phon. weight", "Column (A)", "Renamed (A)", "Column (B)",
            "Renamed (B)" };

    public ColumnPairTableModel() {
        for (int i = 0; i < keys.length; i++)
            indexFromKey.put(keys[i], i);
        setColumnIdentifiers(displayNames);
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

}
