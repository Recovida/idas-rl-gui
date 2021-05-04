package com.cidacs.rl.editor.pair;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import com.cidacs.rl.editor.gui.ColumnPairTableModel;
import com.cidacs.rl.editor.gui.LinkageColumnButtonPanel;
import com.cidacs.rl.editor.gui.LinkageColumnEditingPanel;
import com.cidacs.rl.editor.undo.AddColumnPairCommand;
import com.cidacs.rl.editor.undo.DeleteColumnPairCommand;
import com.cidacs.rl.editor.undo.EditColumnPairFieldCommand;
import com.cidacs.rl.editor.undo.UndoHistory;

public class ColumnPairManager {

    protected UndoHistory history;
    protected LinkageColumnButtonPanel buttonPanel;
    protected LinkageColumnEditingPanel editingPanel;
    protected JTable table;
    protected ColumnPairTableModel model;
    protected ListSelectionModel selectionModel;

    public AtomicInteger nextNumber = new AtomicInteger(1);

    private boolean ignoreSelectionListener = false;
    private boolean ignoreListener = false;
    private boolean completelyIgnoreListener = false; // don't even call
                                                      // onChange()
    protected Map<String, JComponent> fieldFromKey = new HashMap<>();

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
        buttonPanel.getDeletePairBtn().addActionListener(e -> history.push(
                new DeleteColumnPairCommand(this, table.getSelectedRow())));

        selectionModel.addListSelectionListener(new ListSelectionListener() {

            private double zeroIfNull(Double x) {
                return x == null ? 0.0 : x;
            }

            private int zeroIfNull(Integer x) {
                return x == null ? 0 : x;
            }

            @Override
            public synchronized void valueChanged(ListSelectionEvent e) {
                if (ignoreSelectionListener)
                    return;
                completelyIgnoreListener = true;
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
                    editingPanel.setEnabled(true);
                    completelyIgnoreListener = false;
                }
                buttonPanel.getDeletePairBtn().setEnabled(index != -1);
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

    public int addColumnPair(int index, Object[] contents) {
        model.insertRow(index, contents);
        table.setRowSelectionInterval(index, index);
        return index;
    }

    public int addColumnPair() {
        Set<Integer> used = new HashSet<>();
        for (int i = 0; i < model.getRowCount(); i++)
            used.add(model.getIntegerValue(i, "number"));
        int n;
        do {
            n = nextNumber.getAndIncrement();
        } while (used.contains(n));
        return addColumnPair(model.getRowCount(), new Integer[] { n });
    }

    public Object[] deleteColumnPair(int index) {
        Object[] r = model.getRowAsArray(index);
        model.removeRow(index);
        int n = model.getRowCount();
        if (index < n)
            table.setRowSelectionInterval(index, index);
        else if (n > 0)
            table.setRowSelectionInterval(n - 1, n - 1);
        return r;
    }

    public void onChange(int rowIndex, String key, Object newValue) {
        System.out.format("%d_%s = “%s”%n", rowIndex, key, newValue);
        ignoreSelectionListener = true;
        model.setValue(rowIndex, key, newValue);
//        if (key.equals("number"))
//            ((DefaultRowSorter<?, ?>) table.getRowSorter()).sort();
        ignoreSelectionListener = false;
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
                if (!completelyIgnoreListener) {
                    if (newValue != null && newValue.equals(oldValue))
                        return;

                    int rowIndex = table.getSelectedRow();
                    if (rowIndex >= 0)
                        rowIndex = table.convertRowIndexToModel(rowIndex);

                    if (!ignoreListener && rowIndex >= 0)
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
                    if (!completelyIgnoreListener) {
                        if (!ignoreListener && rowIndex >= 0) {
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
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                if (!skipField) {
                    ignoreListener = true;
                    if (table.getSelectedRow() != rowIndex)
                        table.setRowSelectionInterval(rowIndex, rowIndex);
                    JComponent field = fieldFromKey.get(key);
                    if (field instanceof JTextField) {
                        ((JTextField) field).setText((String) newValue);
                    } else if (field instanceof JComboBox) {
                        ((JComboBox<?>) field).setSelectedItem(newValue);
                    } else if (field instanceof JSpinner) {
                        try {
                            ((JSpinner) field).commitEdit();
                        } catch (ParseException e) {
                        }
                        ((JSpinner) field).setValue(newValue);
                    }
                    field.requestFocus();
                    ignoreListener = false;
                }
            }
        });
    }

    public void setValue(int rowIndex, String key, Object newValue) {
        setValue(rowIndex, key, newValue, false);
    }

}
