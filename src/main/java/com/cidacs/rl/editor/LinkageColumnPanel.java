package com.cidacs.rl.editor;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 */

/**
 *
 */
public class LinkageColumnPanel extends JPanel {
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;

    /**
     * Create the panel.
     */
    public LinkageColumnPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JPanel panel = new JPanel();
        add(panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[] { 40, 40, 40, 40, 40, 70 };
        gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
        gbl_panel.columnWeights = new double[] { 1.0, 1.0, 1.0, 1.0, 0.0, 1.0,
                0.0 };
        gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
                Double.MIN_VALUE };
        panel.setLayout(gbl_panel);

        JLabel lblNewLabel_5 = new JLabel("Number");
        lblNewLabel_5.setPreferredSize(new Dimension(25, 17));
        lblNewLabel_5.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_5.setHorizontalTextPosition(SwingConstants.CENTER);
        lblNewLabel_5.setVerticalAlignment(SwingConstants.BOTTOM);
        lblNewLabel_5.setVerticalTextPosition(SwingConstants.BOTTOM);
        GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
        gbc_lblNewLabel_5.fill = GridBagConstraints.BOTH;
        gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_5.gridx = 0;
        gbc_lblNewLabel_5.gridy = 0;
        panel.add(lblNewLabel_5, gbc_lblNewLabel_5);

        JLabel lblNewLabel = new JLabel("Column name (A)");
        lblNewLabel.setPreferredSize(new Dimension(123, 17));
        lblNewLabel.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel.gridx = 1;
        gbc_lblNewLabel.gridy = 0;
        panel.add(lblNewLabel, gbc_lblNewLabel);

        JComboBox comboBox = new JComboBox();
        comboBox.setEditable(true);
        GridBagConstraints gbc_comboBox = new GridBagConstraints();
        gbc_comboBox.gridwidth = 2;
        gbc_comboBox.insets = new Insets(0, 0, 5, 5);
        gbc_comboBox.fill = GridBagConstraints.BOTH;
        gbc_comboBox.gridx = 2;
        gbc_comboBox.gridy = 0;
        panel.add(comboBox, gbc_comboBox);

        JLabel lblNewLabel_2 = new JLabel("rename to");
        lblNewLabel_2.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
        gbc_lblNewLabel_2.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_2.gridx = 4;
        gbc_lblNewLabel_2.gridy = 0;
        panel.add(lblNewLabel_2, gbc_lblNewLabel_2);

        textField = new JTextField();
        GridBagConstraints gbc_textField = new GridBagConstraints();
        gbc_textField.gridwidth = 2;
        gbc_textField.insets = new Insets(0, 0, 5, 0);
        gbc_textField.fill = GridBagConstraints.BOTH;
        gbc_textField.gridx = 5;
        gbc_textField.gridy = 0;
        panel.add(textField, gbc_textField);
        textField.setColumns(10);

        JSpinner spinner_1 = new JSpinner();
        spinner_1.setPreferredSize(new Dimension(40, 30));
        GridBagConstraints gbc_spinner_1 = new GridBagConstraints();
        gbc_spinner_1.fill = GridBagConstraints.BOTH;
        gbc_spinner_1.insets = new Insets(0, 0, 5, 5);
        gbc_spinner_1.gridx = 0;
        gbc_spinner_1.gridy = 1;
        panel.add(spinner_1, gbc_spinner_1);

        JLabel lblNewLabel_1 = new JLabel("Column name (B)");
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.TRAILING);
        lblNewLabel_1.setPreferredSize(new Dimension(123, 17));
        GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
        gbc_lblNewLabel_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_1.gridx = 1;
        gbc_lblNewLabel_1.gridy = 1;
        panel.add(lblNewLabel_1, gbc_lblNewLabel_1);

        JComboBox comboBox_1 = new JComboBox();
        comboBox_1.setEditable(true);
        GridBagConstraints gbc_comboBox_1 = new GridBagConstraints();
        gbc_comboBox_1.gridwidth = 2;
        gbc_comboBox_1.insets = new Insets(0, 0, 5, 5);
        gbc_comboBox_1.fill = GridBagConstraints.BOTH;
        gbc_comboBox_1.gridx = 2;
        gbc_comboBox_1.gridy = 1;
        panel.add(comboBox_1, gbc_comboBox_1);

        JLabel lblNewLabel_3 = new JLabel("rename to");
        lblNewLabel_3.setHorizontalAlignment(SwingConstants.TRAILING);
        lblNewLabel_3.setPreferredSize(new Dimension(80, 17));
        GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
        gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_3.gridx = 4;
        gbc_lblNewLabel_3.gridy = 1;
        panel.add(lblNewLabel_3, gbc_lblNewLabel_3);

        textField_1 = new JTextField();
        GridBagConstraints gbc_textField_1 = new GridBagConstraints();
        gbc_textField_1.gridwidth = 2;
        gbc_textField_1.insets = new Insets(0, 0, 5, 0);
        gbc_textField_1.fill = GridBagConstraints.BOTH;
        gbc_textField_1.gridx = 5;
        gbc_textField_1.gridy = 1;
        panel.add(textField_1, gbc_textField_1);
        textField_1.setColumns(10);

        JLabel lblNewLabel_7 = new JLabel("ID");
        lblNewLabel_7.setVerticalAlignment(SwingConstants.BOTTOM);
        lblNewLabel_7.setVerticalTextPosition(SwingConstants.BOTTOM);
        GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
        gbc_lblNewLabel_7.fill = GridBagConstraints.VERTICAL;
        gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_7.gridx = 0;
        gbc_lblNewLabel_7.gridy = 2;
        panel.add(lblNewLabel_7, gbc_lblNewLabel_7);

        JLabel lblType = new JLabel("Type");
        lblType.setVerticalAlignment(SwingConstants.BOTTOM);
        lblType.setPreferredSize(new Dimension(29, 25));
        lblType.setVerticalTextPosition(SwingConstants.BOTTOM);
        lblType.setHorizontalTextPosition(SwingConstants.CENTER);
        lblType.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblType = new GridBagConstraints();
        gbc_lblType.fill = GridBagConstraints.VERTICAL;
        gbc_lblType.insets = new Insets(0, 0, 5, 5);
        gbc_lblType.gridx = 1;
        gbc_lblType.gridy = 2;
        panel.add(lblType, gbc_lblType);

        JLabel lblNewLabel_4 = new JLabel("Weight");
        lblNewLabel_4.setVerticalAlignment(SwingConstants.BOTTOM);
        lblNewLabel_4.setVerticalTextPosition(SwingConstants.BOTTOM);
        lblNewLabel_4.setHorizontalTextPosition(SwingConstants.CENTER);
        lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
        gbc_lblNewLabel_4.fill = GridBagConstraints.BOTH;
        gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_4.gridx = 2;
        gbc_lblNewLabel_4.gridy = 2;
        panel.add(lblNewLabel_4, gbc_lblNewLabel_4);

        JLabel lblNewLabel_6 = new JLabel("Phon. weight");
        lblNewLabel_6.setVerticalAlignment(SwingConstants.BOTTOM);
        lblNewLabel_6.setVerticalTextPosition(SwingConstants.BOTTOM);
        lblNewLabel_6.setHorizontalTextPosition(SwingConstants.CENTER);
        lblNewLabel_6.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
        gbc_lblNewLabel_6.fill = GridBagConstraints.BOTH;
        gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_6.gridx = 3;
        gbc_lblNewLabel_6.gridy = 2;
        panel.add(lblNewLabel_6, gbc_lblNewLabel_6);

        textField_2 = new JTextField();
        textField_2.setPreferredSize(new Dimension(10, 29));
        GridBagConstraints gbc_textField_2 = new GridBagConstraints();
        gbc_textField_2.fill = GridBagConstraints.BOTH;
        gbc_textField_2.insets = new Insets(0, 0, 0, 5);
        gbc_textField_2.gridx = 0;
        gbc_textField_2.gridy = 3;
        panel.add(textField_2, gbc_textField_2);
        textField_2.setColumns(10);

        JComboBox comboBox_1_1 = new JComboBox();
        comboBox_1_1.setEditable(true);
        GridBagConstraints gbc_comboBox_1_1 = new GridBagConstraints();
        gbc_comboBox_1_1.insets = new Insets(0, 0, 0, 5);
        gbc_comboBox_1_1.fill = GridBagConstraints.BOTH;
        gbc_comboBox_1_1.gridx = 1;
        gbc_comboBox_1_1.gridy = 3;
        panel.add(comboBox_1_1, gbc_comboBox_1_1);

        JSpinner spinner = new JSpinner();
        spinner.setPreferredSize(new Dimension(65, 30));
        spinner.setMinimumSize(new Dimension(50, 30));
        GridBagConstraints gbc_spinner = new GridBagConstraints();
        gbc_spinner.fill = GridBagConstraints.HORIZONTAL;
        gbc_spinner.insets = new Insets(0, 0, 0, 5);
        gbc_spinner.gridx = 2;
        gbc_spinner.gridy = 3;
        panel.add(spinner, gbc_spinner);

        JSpinner spinner_2 = new JSpinner();
        GridBagConstraints gbc_spinner_2 = new GridBagConstraints();
        gbc_spinner_2.fill = GridBagConstraints.BOTH;
        gbc_spinner_2.insets = new Insets(0, 0, 0, 5);
        gbc_spinner_2.gridx = 3;
        gbc_spinner_2.gridy = 3;
        panel.add(spinner_2, gbc_spinner_2);

    }

}
