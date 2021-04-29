package com.cidacs.rl.editor.gui;

import java.util.HashMap;
import java.util.Map;

import javax.swing.table.DefaultTableModel;

public class ColumnPairTableModel extends DefaultTableModel {

    private static final long serialVersionUID = 1L;

    private final String[] keys = { "number", "type", "weight", "phon_weight",
            "index_a", "rename_a", "index_b", "rename_b" };
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

    public void setValue(int rowIndex, String key, Object value) {
        setValueAt(value, rowIndex, indexFromKey.get(key));
    }

    public Object[] getRowAsArray(int rowIndex) {
        Object[] r = new Object[getColumnCount()];
        for (int i = 0; i < getColumnCount(); i++)
            r[i] = getValueAt(rowIndex, i);
        return r;
    }

}
