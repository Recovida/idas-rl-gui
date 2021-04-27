package com.cidacs.rl.editor.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

public class LinkageColumnButtonPanel extends JPanel {

    private static final long serialVersionUID = 3447357352169636148L;

    public LinkageColumnButtonPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        Component horizontalGlue = Box.createHorizontalGlue();
        add(horizontalGlue);

        JButton btnNewButton_1 = new JButton("Add pair");
        add(btnNewButton_1);

        Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
        rigidArea_1.setPreferredSize(new Dimension(8, 20));
        add(rigidArea_1);

        Component horizontalGlue_1 = Box.createHorizontalGlue();
        horizontalGlue_1.setMaximumSize(new Dimension(50, 0));
        add(horizontalGlue_1);

        JButton btnNewButton = new JButton("Delete selected pair");
        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        add(btnNewButton);

        Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));
        rigidArea_2.setPreferredSize(new Dimension(8, 20));
        add(rigidArea_2);

        setBorder(BorderFactory.createRaisedSoftBevelBorder());

    }

}
