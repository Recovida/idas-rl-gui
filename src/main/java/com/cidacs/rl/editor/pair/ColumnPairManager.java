package com.cidacs.rl.editor.pair;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ListSelectionModel;

import com.cidacs.rl.editor.gui.ColumnPairTableModel;
import com.cidacs.rl.editor.gui.LinkageColumnButtonPanel;
import com.cidacs.rl.editor.gui.LinkageColumnPanel;
import com.cidacs.rl.editor.undo.UndoHistory;

public class ColumnPairManager {

    protected UndoHistory history;
    protected LinkageColumnButtonPanel buttonPanel;
    protected LinkageColumnPanel editingPanel;
    protected ColumnPairTableModel model;
    protected ListSelectionModel selectionModel;

    protected List<ColumnPair> columnPairs;

    public ColumnPairManager(UndoHistory history,
            LinkageColumnButtonPanel buttonPanel,
            LinkageColumnPanel editingPanel, ColumnPairTableModel model,
            ListSelectionModel selectionModel) {
        this.history = history;
        this.buttonPanel = buttonPanel;
        this.editingPanel = editingPanel;
        this.model = model;
        this.selectionModel = selectionModel;
        this.columnPairs = new LinkedList<>();

        buttonPanel.getAddPairBtn().addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                model.addRow(new Object[0]);
            }
        });
    }

    public UndoHistory getHistory() {
        return history;
    }

    public LinkageColumnButtonPanel getButtonPanel() {
        return buttonPanel;
    }

    public LinkageColumnPanel getEditingPanel() {
        return editingPanel;
    }

    public ColumnPairTableModel getTableModel() {
        return model;
    }

    public ListSelectionModel getSelectionModel() {
        return selectionModel;
    }

    public List<ColumnPair> getColumnPairs() {
        return Collections.unmodifiableList(columnPairs);
    }

}
