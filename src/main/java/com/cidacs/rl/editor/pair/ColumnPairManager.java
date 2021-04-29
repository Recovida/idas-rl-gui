package com.cidacs.rl.editor.pair;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.cidacs.rl.editor.gui.ColumnPairTableModel;
import com.cidacs.rl.editor.gui.LinkageColumnButtonPanel;
import com.cidacs.rl.editor.gui.LinkageColumnEditingPanel;
import com.cidacs.rl.editor.undo.AddColumnPairCommand;
import com.cidacs.rl.editor.undo.DeleteColumnPairCommand;
import com.cidacs.rl.editor.undo.UndoHistory;

public class ColumnPairManager {

    protected UndoHistory history;
    protected LinkageColumnButtonPanel buttonPanel;
    protected LinkageColumnEditingPanel editingPanel;
    protected JTable table;
    protected ColumnPairTableModel model;
    protected ListSelectionModel selectionModel;

    public ColumnPairManager(UndoHistory history,
            LinkageColumnButtonPanel buttonPanel,
            LinkageColumnEditingPanel editingPanel, JTable table) {
        this.history = history;
        this.buttonPanel = buttonPanel;
        this.editingPanel = editingPanel;
        this.table = table;
        this.model = (ColumnPairTableModel) table.getModel();
        this.selectionModel = table.getSelectionModel();

        ColumnPairManager manager = this;

        buttonPanel.getAddPairBtn().addActionListener(
                e -> history.push(new AddColumnPairCommand(manager)));
        buttonPanel.getDeletePairBtn().addActionListener(e -> history.push(
                new DeleteColumnPairCommand(manager, table.getSelectedRow())));

        selectionModel.addListSelectionListener(new ListSelectionListener() {

            private double zeroIfNull(Double x) {
                return x == null ? 0.0 : x;
            }

            private int zeroIfNull(Integer x) {
                return x == null ? 0 : x;
            }

            @Override
            public synchronized void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting())
                    return;
                editingPanel.setEnabled(false);
                int index = table.getSelectedRow();
                if (index != -1) {
                    editingPanel.getNumberField().setValue(
                            zeroIfNull(model.getIntegerValue(index, "number")));
                    editingPanel.getTypeField().setSelectedItem(
                            model.getIntegerValue(index, "type"));
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
                }
                buttonPanel.getDeletePairBtn().setEnabled(index != -1);
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
        return index;
    }

    public int addColumnPair() {
        return addColumnPair(model.getRowCount(), new Object[0]);
    }

    public Object[] deleteColumnPair(int index) {
        Object[] r = model.getRowAsArray(index);
        model.removeRow(index);
        return r;
    }

}
