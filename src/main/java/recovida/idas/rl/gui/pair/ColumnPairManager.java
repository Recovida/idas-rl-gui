package recovida.idas.rl.gui.pair;

import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import recovida.idas.rl.gui.lang.MessageProvider;
import recovida.idas.rl.gui.listener.ColumnPairInclusionExclusionListener;
import recovida.idas.rl.gui.listener.ColumnPairSelectionListener;
import recovida.idas.rl.gui.listener.ColumnPairValueChangeListener;
import recovida.idas.rl.gui.ui.ColumnPairTableModel;
import recovida.idas.rl.gui.ui.cellrendering.RenameColumnPairCellRenderer;
import recovida.idas.rl.gui.ui.container.LinkageColumnButtonPanel;
import recovida.idas.rl.gui.ui.container.LinkageColumnEditingPanel;
import recovida.idas.rl.gui.ui.window.BulkCopyColumnInclusionDialogue;
import recovida.idas.rl.gui.undo.AddColumnPairCommand;
import recovida.idas.rl.gui.undo.Command;
import recovida.idas.rl.gui.undo.CompositeCommand;
import recovida.idas.rl.gui.undo.DeleteColumnPairCommand;
import recovida.idas.rl.gui.undo.EditColumnPairFieldCommand;
import recovida.idas.rl.gui.undo.UndoHistory;

public class ColumnPairManager {

    protected UndoHistory history;
    protected LinkageColumnButtonPanel buttonPanel;
    protected LinkageColumnEditingPanel editingPanel;
    protected JTable table;
    protected ColumnPairTableModel model;
    protected ListSelectionModel selectionModel;

    protected String firstRenameSuffix = "";
    protected String secondRenameSuffix = "";

    public final int FIRST_NUMBER = 1;
    public final int FIRST_COPY_NUMBER = 201;

    protected final AtomicInteger nextNumber = new AtomicInteger(FIRST_NUMBER);
    protected final AtomicInteger nextCopyNumber = new AtomicInteger(
            FIRST_COPY_NUMBER);

    protected boolean ignoreSelectionEvent = false;
    protected boolean ignoreChangeEvent = false;
    protected boolean completelyIgnoreChangeEvent = false; // don't even call
                                                           // onChange()
    protected Map<String, JComponent> fieldFromKey = new HashMap<>();

    protected Collection<String> firstDatasetColumnNames = null;
    protected Collection<String> secondDatasetColumnNames = null;

    protected List<ColumnPairSelectionListener> selectionListeners = new LinkedList<>();
    protected List<ColumnPairInclusionExclusionListener> rowInclusionExclusionListeners = new LinkedList<>();
    protected List<ColumnPairValueChangeListener> valueChangeListeners = new LinkedList<>();

    public ColumnPairManager(UndoHistory history,
            LinkageColumnButtonPanel buttonPanel,
            LinkageColumnEditingPanel editingPanel, JTable table) {
        this.history = history;
        this.buttonPanel = buttonPanel;
        this.editingPanel = editingPanel;
        this.table = table;
        this.model = (ColumnPairTableModel) table.getModel();
        this.selectionModel = table.getSelectionModel();

        buttonPanel.getAddPairBtn().addActionListener(
                e -> history.push(new AddColumnPairCommand(this)));
        buttonPanel.getDeletePairBtn().addActionListener(
                e -> history.push(new DeleteColumnPairCommand(this,
                        table.convertRowIndexToModel(table.getSelectedRow()))));
        buttonPanel.getAddCopyColsBtn().addActionListener(e -> {
            if (firstDatasetColumnNames != null
                    || secondDatasetColumnNames != null) {
                Set<String> alreadyIncludedLeft = new HashSet<>();
                Set<String> alreadyIncludedRight = new HashSet<>();
                for (int i = 0; i < model.getRowCount(); i++) {
                    String left = model.getStringValue(i, "index_a");
                    String right = model.getStringValue(i, "index_b");
                    if (left != null && !left.isEmpty())
                        alreadyIncludedLeft.add(left);
                    if (right != null && !right.isEmpty())
                        alreadyIncludedRight.add(right);
                }
                BulkCopyColumnInclusionDialogue dialogue = new BulkCopyColumnInclusionDialogue(
                        (Frame) SwingUtilities.getRoot(table));
                if (firstDatasetColumnNames != null)
                    for (String col : firstDatasetColumnNames)
                        dialogue.addItemToLeftPanel(col,
                                alreadyIncludedLeft.contains(col));
                if (secondDatasetColumnNames != null)
                    for (String col : secondDatasetColumnNames)
                        dialogue.addItemToRightPanel(col,
                                alreadyIncludedRight.contains(col));
                List<String>[] result = dialogue.run();
                List<Command> cmd = new LinkedList<>();
                for (String c : result[0])
                    cmd.add(new AddColumnPairCommand(ColumnPairManager.this,
                            "copy", c, null));
                for (String c : result[1])
                    cmd.add(new AddColumnPairCommand(ColumnPairManager.this,
                            "copy", null, c));
                if (!cmd.isEmpty())
                    history.push(new CompositeCommand(cmd) {

                        @Override
                        public String getSummary() {
                            return MessageProvider
                                    .getMessage("columns.addcopy.summary");
                        }
                    });
            }
        });

        selectionModel.addListSelectionListener(new ListSelectionListener() {

            private double zeroIfNull(Double x) {
                return x == null ? 0.0 : x;
            }

            private int zeroIfNull(Integer x) {
                return x == null ? 0 : x;
            }

            @Override
            public synchronized void valueChanged(ListSelectionEvent e) {
                if (ignoreSelectionEvent)
                    return;
                completelyIgnoreChangeEvent = true;
                if (e.getValueIsAdjusting())
                    return;
                editingPanel.setEnabled(false);
                int index = table.getSelectedRow();
                if (index != -1) {
                    index = table.convertRowIndexToModel(index);
                    editingPanel.getNumberField().setValue(
                            zeroIfNull(model.getIntegerValue(index, "number")));
                    editingPanel.getTypeField().setSelectedItem(
                            model.getStringValue(index, "type"));
                    editingPanel.getWeightField().setValue(
                            zeroIfNull(model.getDoubleValue(index, "weight")));
                    editingPanel.getPhonWeightField().setValue(zeroIfNull(
                            model.getDoubleValue(index, "phon_weight")));
                    editingPanel.getFirstNameField().setSelectedItem(
                            model.getStringValue(index, "index_a"));
                    editingPanel.getSecondNameField().setSelectedItem(
                            model.getStringValue(index, "index_b"));
                    editingPanel.getFirstRenameField()
                            .setText(model.getStringValue(index, "rename_a"));
                    editingPanel.getSecondRenameField()
                            .setText(model.getStringValue(index, "rename_b"));
                    updateWeightFieldVisibility(
                            model.getStringValue(index, "type"));
                    editingPanel.setEnabled(true);
                    completelyIgnoreChangeEvent = false;
                }
                buttonPanel.getDeletePairBtn().setEnabled(index != -1);
                for (ColumnPairSelectionListener listener : selectionListeners)
                    if (listener != null)
                        listener.selected(index);
                updateRenameFieldPlaceholder();
            }
        });

        associateKeyWithField("rename_a", editingPanel.getFirstRenameField());
        associateKeyWithField("rename_b", editingPanel.getSecondRenameField());
        associateKeyWithField("index_a", editingPanel.getFirstNameField());
        associateKeyWithField("index_b", editingPanel.getSecondNameField());
        associateKeyWithField("type", editingPanel.getTypeField());
        associateKeyWithField("weight", editingPanel.getWeightField());
        associateKeyWithField("phon_weight", editingPanel.getPhonWeightField());
        associateKeyWithField("number", editingPanel.getNumberField());

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int colIndex = table.getSelectedColumn();
                if (colIndex >= 0) {
                    colIndex = table.convertColumnIndexToModel(colIndex);
                    JComponent field = fieldFromKey
                            .get(model.getColumnKey(colIndex));
                    if (field != null)
                        field.requestFocus();
                }
            }
        });

        editingPanel.getPhonWeightLbl().setVisible(false);
        editingPanel.getPhonWeightField().setVisible(false);

    }

    public UndoHistory getHistory() {
        return history;
    }

    public LinkageColumnButtonPanel getButtonPanel() {
        return buttonPanel;
    }

    public LinkageColumnEditingPanel getEditingPanel() {
        return editingPanel;
    }

    public ColumnPairTableModel getTableModel() {
        return model;
    }

    public ListSelectionModel getSelectionModel() {
        return selectionModel;
    }

    public synchronized int addColumnPair(int index, Object[] contents) {
        model.insertRow(index, contents);
        int viewIndex = table.convertRowIndexToView(index);
        table.setRowSelectionInterval(viewIndex, viewIndex);
        for (ColumnPairInclusionExclusionListener listener : rowInclusionExclusionListeners)
            if (listener != null)
                listener.insertedColumnPair(index, contents);
        return index;
    }

    public void updateCellsWithNumber(int number) {
        for (int i = 0; i < model.getRowCount(); i++)
            if (Objects.equals(Integer.valueOf(number),
                    model.getValue(i, "number")))
                model.fireTableRowsUpdated(i, i);
    }

    public int addColumnPair(String type, String firstDatasetColumn,
            String secondDatasetColumn) {
        Object[] contents = new Object[model.getColumnCount()];
        contents[model.getColumnIndex("number")] = getNextNumber(
                "copy".equals(type) ? nextCopyNumber : nextNumber);
        if (firstDatasetColumn != null)
            contents[model.getColumnIndex("index_a")] = firstDatasetColumn;
        if (secondDatasetColumn != null)
            contents[model.getColumnIndex("index_b")] = secondDatasetColumn;
        if (type != null)
            contents[model.getColumnIndex("type")] = type;
        contents[model.getColumnIndex("weight")] = 0.0;
        return addColumnPair(model.getRowCount(), contents);
    }

    public int addColumnPair() {
        return addColumnPair(null, null, null);
    }

    public Map<String, Object> getColumnPair(int index) {
        return model.getRowAsMap(index);
    }

    public int getColumnPairCount() {
        return model.getRowCount();
    }

    protected int getNextNumber(AtomicInteger counter) {
        Set<Integer> used = new HashSet<>();
        for (int i = 0; i < model.getRowCount(); i++)
            used.add(model.getIntegerValue(i, "number"));
        int n;
        do {
            n = counter.getAndIncrement();
        } while (used.contains(n));
        return n;
    }

    public synchronized Object[] deleteColumnPair(int index) {
        Object[] r = model.getRowAsArray(index);
        int viewIndex = table.convertRowIndexToView(index);
        model.removeRow(index);
        int n = model.getRowCount();
        if (viewIndex < n)
            table.setRowSelectionInterval(viewIndex, viewIndex);
        else if (n > 0)
            table.setRowSelectionInterval(n - 1, n - 1);
        for (ColumnPairInclusionExclusionListener listener : rowInclusionExclusionListeners)
            if (listener != null)
                listener.deletedColumnPair(viewIndex, r);
        return r;
    }

    public synchronized void onChange(int rowIndex, String key,
            Object newValue) {
        System.out.format("%d_%s = “%s”%n", rowIndex, key, newValue);
        ignoreSelectionEvent = true;
        model.setValue(rowIndex, key, newValue);
        ignoreSelectionEvent = false;
        for (ColumnPairValueChangeListener listener : valueChangeListeners)
            if (listener != null)
                listener.changed(rowIndex, key, newValue);
        if ("type".equals(key)) {
            updateWeightFieldVisibility((String) newValue);
        } else if ("index_a".equals(key) || "index_b".equals(key)) {
            updateRenameFieldPlaceholder();
        }
    }

    protected void updateWeightFieldVisibility(String type) {
        boolean hasPhonWeight = "name".equals(type);
        boolean hasWeight = !"copy".equals(type);
        editingPanel.getPhonWeightLbl().setVisible(hasPhonWeight);
        editingPanel.getPhonWeightField().setVisible(hasPhonWeight);
        editingPanel.getWeightField().setVisible(hasWeight);
        editingPanel.getWeightLbl().setVisible(hasWeight);
    }

    protected void updateRenameFieldPlaceholder() {
        int index = table.getSelectedRow();
        if (index == -1) {
            editingPanel.getFirstRenameField().setPlaceholder("");
            editingPanel.getSecondRenameField().setPlaceholder("");
            return;
        }
        String name = Optional
                .ofNullable(model.getStringValue(
                        table.convertRowIndexToModel(index), "index_a"))
                .orElse("");
        editingPanel.getFirstRenameField().setPlaceholder(
                name.isEmpty() ? "" : (name + "_" + firstRenameSuffix));
        name = Optional
                .ofNullable(model.getStringValue(
                        table.convertRowIndexToModel(index), "index_b"))
                .orElse("");
        editingPanel.getSecondRenameField().setPlaceholder(
                name.isEmpty() ? "" : (name + "_" + secondRenameSuffix));
    }

    protected void updateRenameCellsPlaceholder() {
        TableCellRenderer renderer;
        renderer = table.getColumnModel()
                .getColumn(model.getColumnIndex("rename_a")).getCellRenderer();
        if (renderer instanceof RenameColumnPairCellRenderer) {
            ((RenameColumnPairCellRenderer) renderer)
                    .setSuffix(firstRenameSuffix);
        }
        renderer = table.getColumnModel()
                .getColumn(model.getColumnIndex("rename_b")).getCellRenderer();
        if (renderer instanceof RenameColumnPairCellRenderer) {
            ((RenameColumnPairCellRenderer) renderer)
                    .setSuffix(secondRenameSuffix);
        }
        if (model.getRowCount() > 0)
            model.fireTableRowsUpdated(0, model.getRowCount() - 1);
    }

    protected void associateKeyWithField(String key, JSpinner field) {
        fieldFromKey.put(key, field);
        JFormattedTextField tf = ((JSpinner.DefaultEditor) field.getEditor())
                .getTextField();
        tf.getDocument().addDocumentListener(new DocumentListener() {

            Number oldValue = (Number) field.getValue();

            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                Number newValue;
                try {
                    String txt = e.getDocument().getText(0,
                            e.getDocument().getLength());
                    newValue = (Number) ((DefaultFormatter) tf.getFormatter())
                            .stringToValue(txt);
                } catch (ParseException | BadLocationException e1) {
                    return;
                }
                if (!completelyIgnoreChangeEvent) {
                    if (newValue != null && newValue.equals(oldValue))
                        return;

                    int rowIndex = table.getSelectedRow();
                    if (rowIndex >= 0)
                        rowIndex = table.convertRowIndexToModel(rowIndex);

                    if (!ignoreChangeEvent && rowIndex >= 0)
                        history.push(new EditColumnPairFieldCommand<>(
                                ColumnPairManager.this, rowIndex, key, oldValue,
                                newValue, true));
                    onChange(rowIndex, key, newValue);
                }
                oldValue = newValue;
            }
        });
    }

    protected void associateKeyWithField(String key, JTextField field) {
        fieldFromKey.put(key, field);
        addListenerToComponent(key, field.getDocument());
    }

    protected void associateKeyWithField(String key, JComboBox<?> field) {
        fieldFromKey.put(key, field);
        addListenerToComponent(key,
                ((JTextComponent) field.getEditor().getEditorComponent())
                        .getDocument());
    }

    protected void addListenerToComponent(String key, Document doc) {
        doc.addDocumentListener(new DocumentListener() {

            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    String newValue = e.getDocument().getText(0,
                            e.getDocument().getLength());
                    int rowIndex = table.getSelectedRow();
                    if (rowIndex >= 0)
                        rowIndex = table.convertRowIndexToModel(rowIndex);
                    if (!completelyIgnoreChangeEvent) {
                        if (!ignoreChangeEvent && rowIndex >= 0) {
                            String oldValue = (String) model.getValue(rowIndex,
                                    key);
                            history.push(new EditColumnPairFieldCommand<>(
                                    ColumnPairManager.this, rowIndex, key,
                                    oldValue, newValue, true));
                        }
                        onChange(rowIndex, key, newValue);
                    }
                } catch (BadLocationException e1) {
                }

            }
        });
    }

    public synchronized void setValue(int rowIndex, String key, Object newValue,
            boolean skipField) {
        SwingUtilities.invokeLater(() -> {
            if (!skipField) {
                ignoreChangeEvent = true;
                if (table.getSelectedRow() != rowIndex)
                    table.setRowSelectionInterval(rowIndex, rowIndex);
                JComponent field = fieldFromKey.get(key);
                if (field instanceof JTextField) {
                    ((JTextField) field).setText((String) newValue);
                } else if (field instanceof JComboBox) {
                    ((JComboBox<?>) field).setSelectedIndex(-1);
                    ((JComboBox<?>) field).setSelectedItem(newValue);
                } else if (field instanceof JSpinner) {
                    try {
                        ((JSpinner) field).commitEdit();
                    } catch (ParseException e) {
                    }
                    ((JSpinner) field).setValue(newValue);
                }
                field.requestFocus();
                ignoreChangeEvent = false;
            }
        });
    }

    public void setValue(int rowIndex, String key, Object newValue) {
        setValue(rowIndex, key, newValue, false);
    }

    public Collection<String> getFirstDatasetColumnNames() {
        return firstDatasetColumnNames == null ? null
                : Collections.unmodifiableCollection(firstDatasetColumnNames);
    }

    public void setFirstDatasetColumnNames(
            Collection<String> firstDatasetColumnNames) {
        this.firstDatasetColumnNames = firstDatasetColumnNames;
        completelyIgnoreChangeEvent = true;
        JComboBox<String> field = getEditingPanel().getFirstNameField();
        setComboBoxItems(field, firstDatasetColumnNames);
        int idx = table.getSelectedRow();
        field.setSelectedItem(idx == -1 ? ""
                : model.getValue(table.convertRowIndexToModel(idx), "index_a"));
        completelyIgnoreChangeEvent = false;
        buttonPanel.getAddCopyColsBtn()
                .setEnabled(firstDatasetColumnNames != null
                        || secondDatasetColumnNames != null);
        model.setFirstDatasetColumnNames(firstDatasetColumnNames);
    }

    public Collection<String> getSecondDatasetColumnNames() {
        return secondDatasetColumnNames == null ? null
                : Collections.unmodifiableCollection(secondDatasetColumnNames);
    }

    public void setSecondDatasetColumnNames(
            Collection<String> secondDatasetColumnNames) {
        this.secondDatasetColumnNames = secondDatasetColumnNames;
        completelyIgnoreChangeEvent = true;
        JComboBox<String> field = getEditingPanel().getSecondNameField();
        setComboBoxItems(field, secondDatasetColumnNames);
        int idx = table.getSelectedRow();
        field.setSelectedItem(idx == -1 ? ""
                : model.getValue(table.convertRowIndexToModel(idx), "index_b"));
        completelyIgnoreChangeEvent = false;
        buttonPanel.getAddCopyColsBtn()
                .setEnabled(firstDatasetColumnNames != null
                        || secondDatasetColumnNames != null);
        model.setSecondDatasetColumnNames(secondDatasetColumnNames);
        if (model.getRowCount() > 0)
            model.fireTableRowsUpdated(0, model.getRowCount() - 1);
    }

    public void setComboBoxItems(JComboBox<String> field,
            Collection<String> items) {
        field.removeAllItems();
        if (items != null)
            for (String item : items)
                field.addItem(item);
    }

    public void addSelectionListener(ColumnPairSelectionListener listener) {
        selectionListeners.add(listener);
    }

    public void addInclusionExclusionListener(
            ColumnPairInclusionExclusionListener listener) {
        rowInclusionExclusionListeners.add(listener);
    }

    public void addValueChangeSelectionListener(
            ColumnPairValueChangeListener listener) {
        valueChangeListeners.add(listener);
    }

    public String getFirstRenameSuffix() {
        return firstRenameSuffix;
    }

    public void setFirstRenameSuffix(String firstRenameSuffix) {
        this.firstRenameSuffix = Optional.of(firstRenameSuffix).orElse("");
        updateRenameFieldPlaceholder();
        updateRenameCellsPlaceholder();
    }

    public String getSecondRenameSuffix() {
        return secondRenameSuffix;
    }

    public void setSecondRenameSuffix(String secondRenameSuffix) {
        this.secondRenameSuffix = Optional.of(secondRenameSuffix).orElse("");
        updateRenameFieldPlaceholder();
        updateRenameCellsPlaceholder();
    }

    public void reset() {
        nextNumber.set(FIRST_NUMBER);
        nextCopyNumber.set(FIRST_COPY_NUMBER);
    }

    public Collection<Integer> getColIdxWithNumber(int number) {
        return model.getColIdxWithNumber(number);
    }

}
