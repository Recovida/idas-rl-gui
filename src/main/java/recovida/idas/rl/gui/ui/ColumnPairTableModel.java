package recovida.idas.rl.gui.ui;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import recovida.idas.rl.gui.lang.MessageProvider;
import recovida.idas.rl.gui.ui.container.LinkageColumnEditingPanel;

public class ColumnPairTableModel extends DefaultTableModel {

    private static final long serialVersionUID = 1L;

    private final String[] keys = { "number", "type", "weight", "phon_weight",
            "index_a", "rename_a", "index_b", "rename_b" };
    private final Class<?>[] types = { Integer.class, String.class,
            Double.class, Double.class, String.class, String.class,
            String.class, String.class };
    private Map<String, Integer> indexFromKey = new HashMap<>();
    protected Map<Integer, Collection<Integer>> numberToColIdx = new HashMap<>();

    protected Collection<String> firstDatasetColumnNames = null;
    protected Collection<String> secondDatasetColumnNames = null;

    protected Vector<boolean[]> valid = new Vector<>();

    public ColumnPairTableModel() {
        for (int i = 0; i < keys.length; i++)
            indexFromKey.put(keys[i], i);
        setColumnIdentifiers(keys); // temporary
    }

    public void setFirstDatasetColumnNames(
            Collection<String> firstDatasetColumnNames) {
        this.firstDatasetColumnNames = firstDatasetColumnNames;
        if (getRowCount() > 0)
            fireTableRowsUpdated(0, getRowCount() - 1);
    }

    public Collection<String> getFirstDatasetColumnNames() {
        return firstDatasetColumnNames == null ? null
                : Collections.unmodifiableCollection(firstDatasetColumnNames);
    }

    public void setSecondDatasetColumnNames(
            Collection<String> secondDatasetColumnNames) {
        this.secondDatasetColumnNames = secondDatasetColumnNames;
        if (getRowCount() > 0)
            fireTableRowsUpdated(0, getRowCount() - 1);
    }

    public Collection<String> getSecondDatasetColumnNames() {
        return secondDatasetColumnNames == null ? null
                : Collections.unmodifiableCollection(secondDatasetColumnNames);
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

    public void updateRowValidation(int rowIndex) {
        if (valid.get(rowIndex) == null)
            valid.set(rowIndex, new boolean[keys.length]);
        boolean[] v = valid.get(rowIndex);
        Object value;
        Object type = getValue(rowIndex, "type");
        // validate type
        value = type;
        v[indexFromKey.get("type")] = LinkageColumnEditingPanel.getTypes()
                .contains(value);
        // validate weight
        value = getValue(rowIndex, "weight");
        v[indexFromKey.get("weight")] = "copy".equals(type) || value != null
                && value instanceof Double && (Double) value >= 0;
        // validate phon weight
        value = getValue(rowIndex, "phon_weight");
        v[indexFromKey.get("phon_weight")] = value == null
                || !"name".equals(type)
                || value instanceof Double && (Double) value >= 0;
        // validate index_a
        value = getValue(rowIndex, "index_a");
        v[indexFromKey.get("index_a")] = firstDatasetColumnNames == null
                || ("copy".equals(getValue(rowIndex, "type"))
                        && "".equals(value))
                || firstDatasetColumnNames.contains(value);
        // validate index_b
        value = getValue(rowIndex, "index_b");
        v[indexFromKey.get("index_b")] = secondDatasetColumnNames == null
                || ("copy".equals(getValue(rowIndex, "type"))
                        && "".equals(value))
                || secondDatasetColumnNames.contains(value);
        // validate rename_a and rename_b
        v[indexFromKey.get("rename_a")] = true;
        v[indexFromKey.get("rename_b")] = true;
        // validate number
        value = getValue(rowIndex, "number");
        v[indexFromKey.get("number")] = numberToColIdx
                .getOrDefault(value, Collections.emptySet()).size() == 1;
    }

    public boolean isValid() {
        return valid.stream().allMatch(v -> v != null
                && IntStream.range(0, v.length).allMatch(i -> v[i]));
    }

    public boolean isRowValid(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= getRowCount())
            return false;
        boolean[] v = valid.get(rowIndex);
        return v != null && IntStream.range(0, v.length).allMatch(i -> v[i]);
    }

    public boolean isCellValid(int rowIndex, int colIndex) {
        if (rowIndex < 0 || rowIndex >= getRowCount() || colIndex < 0
                || colIndex >= keys.length)
            return false;
        boolean[] v = valid.get(rowIndex);
        return v != null && v[colIndex];
    }

    public boolean isCellValid(int rowIndex, String key) {
        return isCellValid(rowIndex, indexFromKey.getOrDefault(key, -1));
    }

    public void updateCellsWithNumber(int number) {
        for (int i = 0; i < getRowCount(); i++)
            if (Objects.equals(Integer.valueOf(number),
                    getValue(i, "number"))) {
                updateRowValidation(i);
                fireTableRowsUpdated(i, i);
            }
    }

    @Override
    public void moveRow(int start, int end, int to) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addColumn(Object columnName,
            @SuppressWarnings("rawtypes") Vector columnData) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int colIndex) {
        int numberIndex = getColumnIndex("number");
        Integer oldNumber = null, newNumber = null;
        if (colIndex == numberIndex) {
            oldNumber = (Integer) getValue(rowIndex, "number");
            newNumber = (Integer) value;
            if (!Objects.equals(oldNumber, newNumber)) {
                if (oldNumber != null)
                    numberToColIdx.get(oldNumber).remove(rowIndex);
                if (!numberToColIdx.containsKey(newNumber))
                    numberToColIdx.put(newNumber, new HashSet<>());
                numberToColIdx.get(newNumber).add(rowIndex);
            }
        }
        super.setValueAt(value, rowIndex, colIndex);
        updateRowValidation(rowIndex);
        if (colIndex == getColumnIndex("type"))
            fireTableRowsUpdated(rowIndex, rowIndex);
        else if (colIndex == getColumnIndex("index_a"))
            fireTableCellUpdated(rowIndex, getColumnIndex("rename_a"));
        else if (colIndex == getColumnIndex("index_b"))
            fireTableCellUpdated(rowIndex, getColumnIndex("rename_b"));
        else if (colIndex == getColumnIndex("number")) {
            if (oldNumber != null)
                updateCellsWithNumber(oldNumber);
            if (newNumber != null)
                updateCellsWithNumber(newNumber);
        }
    }

    @Override
    public void removeRow(int row) {
        Integer number = (Integer) getValue(row, "number");
        if (number != null) {
            numberToColIdx.getOrDefault(number, Collections.emptySet())
                    .remove(row);
            numberToColIdx.replaceAll(
                    (num, set) -> set.stream().map(x -> x < row ? x : (x - 1))
                            .collect(Collectors.toSet()));
        }
        valid.remove(row);
        super.removeRow(row);
        if (number != null)
            updateCellsWithNumber(number);
    }

    @Override
    public void insertRow(int row,
            @SuppressWarnings("rawtypes") Vector rowData) {
        int numberIndex = getColumnIndex("number");
        int number = Integer.MAX_VALUE;
        if (rowData != null && numberIndex < rowData.size()) {
            number = (Integer) rowData.get(numberIndex);
            numberToColIdx.replaceAll(
                    (num, set) -> set.stream().map(x -> x < row ? x : (x + 1))
                            .collect(Collectors.toSet()));
            if (!numberToColIdx.containsKey(number))
                numberToColIdx.put(number, new HashSet<>());
            numberToColIdx.get(number).add(row);
        }
        valid.insertElementAt(null, row);
        super.insertRow(row, rowData);
        updateRowValidation(row);
        updateCellsWithNumber(number);
    }

    public boolean hasDuplicateNumbers() {
        return numberToColIdx.values().stream().anyMatch(x -> x.size() > 1);
    }

    @Override
    public void setRowCount(int rowCount) {
        if (rowCount < getRowCount())
            numberToColIdx.replaceAll((num, set) -> set.stream()
                    .filter(x -> x < rowCount).collect(Collectors.toSet()));
        valid.setSize(rowCount);
        super.setRowCount(rowCount);
    }

    public Collection<Integer> getColIdxWithNumber(int number) {
        return Collections.unmodifiableCollection(
                numberToColIdx.getOrDefault(number, Collections.emptySet()));
    }

}
