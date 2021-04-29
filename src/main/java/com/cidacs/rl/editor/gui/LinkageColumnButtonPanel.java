package com.cidacs.rl.editor.gui;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class LinkageColumnButtonPanel extends JPanel {

    private static final long serialVersionUID = 3447357352169636148L;
    private JButton addPairBtn;

    public JButton getAddPairBtn() {
        return addPairBtn;
    }

    public JButton getDeletePairBtn() {
        return deletePairBtn;
    }

    private JButton deletePairBtn;

    public LinkageColumnButtonPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        Component horizontalGlue = Box.createHorizontalGlue();
        add(horizontalGlue);

        addPairBtn = new JButton("Add pair");
        add(addPairBtn);

        Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
        rigidArea_1.setPreferredSize(new Dimension(8, 20));
        add(rigidArea_1);

        Component horizontalGlue_1 = Box.createHorizontalGlue();
        horizontalGlue_1.setMaximumSize(new Dimension(50, 0));
        add(horizontalGlue_1);

        deletePairBtn = new JButton("Delete selected pair");
        deletePairBtn.setEnabled(false);
        add(deletePairBtn);

        Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));
        rigidArea_2.setPreferredSize(new Dimension(8, 20));
        add(rigidArea_2);

        setBorder(BorderFactory.createRaisedSoftBevelBorder());

    }

}
