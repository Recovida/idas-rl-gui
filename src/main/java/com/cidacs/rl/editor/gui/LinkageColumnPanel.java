package com.cidacs.rl.editor.gui;

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
    private JTextField firstRenameField;
    private JTextField secondRenameField;
    private JTextField idField;

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

        JLabel numberLbl = new JLabel("Number");
        numberLbl.setPreferredSize(new Dimension(25, 17));
        numberLbl.setHorizontalAlignment(SwingConstants.CENTER);
        numberLbl.setHorizontalTextPosition(SwingConstants.CENTER);
        numberLbl.setVerticalAlignment(SwingConstants.BOTTOM);
        numberLbl.setVerticalTextPosition(SwingConstants.BOTTOM);
        GridBagConstraints gbc_numberLbl = new GridBagConstraints();
        gbc_numberLbl.fill = GridBagConstraints.BOTH;
        gbc_numberLbl.insets = new Insets(0, 0, 5, 5);
        gbc_numberLbl.gridx = 0;
        gbc_numberLbl.gridy = 0;
        panel.add(numberLbl, gbc_numberLbl);

        JLabel nameFirstLbl = new JLabel("Column name (A)");
        nameFirstLbl.setPreferredSize(new Dimension(123, 17));
        nameFirstLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_nameFirstLbl = new GridBagConstraints();
        gbc_nameFirstLbl.fill = GridBagConstraints.HORIZONTAL;
        gbc_nameFirstLbl.insets = new Insets(0, 0, 5, 5);
        gbc_nameFirstLbl.gridx = 1;
        gbc_nameFirstLbl.gridy = 0;
        panel.add(nameFirstLbl, gbc_nameFirstLbl);

        JComboBox firstNameField = new JComboBox();
        firstNameField.setEditable(true);
        GridBagConstraints gbc_firstNameField = new GridBagConstraints();
        gbc_firstNameField.gridwidth = 2;
        gbc_firstNameField.insets = new Insets(0, 0, 5, 5);
        gbc_firstNameField.fill = GridBagConstraints.BOTH;
        gbc_firstNameField.gridx = 2;
        gbc_firstNameField.gridy = 0;
        panel.add(firstNameField, gbc_firstNameField);

        JLabel renameFirstLbl = new JLabel("rename to");
        renameFirstLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_renameFirstLbl = new GridBagConstraints();
        gbc_renameFirstLbl.fill = GridBagConstraints.HORIZONTAL;
        gbc_renameFirstLbl.insets = new Insets(0, 0, 5, 5);
        gbc_renameFirstLbl.gridx = 4;
        gbc_renameFirstLbl.gridy = 0;
        panel.add(renameFirstLbl, gbc_renameFirstLbl);

        firstRenameField = new JTextField();
        GridBagConstraints gbc_firstRenameField = new GridBagConstraints();
        gbc_firstRenameField.gridwidth = 2;
        gbc_firstRenameField.insets = new Insets(0, 0, 5, 0);
        gbc_firstRenameField.fill = GridBagConstraints.BOTH;
        gbc_firstRenameField.gridx = 5;
        gbc_firstRenameField.gridy = 0;
        panel.add(firstRenameField, gbc_firstRenameField);
        firstRenameField.setColumns(10);

        JSpinner numberField = new JSpinner();
        numberField.setPreferredSize(new Dimension(40, 30));
        GridBagConstraints gbc_numberField = new GridBagConstraints();
        gbc_numberField.fill = GridBagConstraints.BOTH;
        gbc_numberField.insets = new Insets(0, 0, 5, 5);
        gbc_numberField.gridx = 0;
        gbc_numberField.gridy = 1;
        panel.add(numberField, gbc_numberField);

        JLabel nameSecondLbl = new JLabel("Column name (B)");
        nameSecondLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        nameSecondLbl.setPreferredSize(new Dimension(123, 17));
        GridBagConstraints gbc_nameSecondLbl = new GridBagConstraints();
        gbc_nameSecondLbl.fill = GridBagConstraints.HORIZONTAL;
        gbc_nameSecondLbl.insets = new Insets(0, 0, 5, 5);
        gbc_nameSecondLbl.gridx = 1;
        gbc_nameSecondLbl.gridy = 1;
        panel.add(nameSecondLbl, gbc_nameSecondLbl);

        JComboBox secondNameField = new JComboBox();
        secondNameField.setEditable(true);
        GridBagConstraints gbc_secondNameField = new GridBagConstraints();
        gbc_secondNameField.gridwidth = 2;
        gbc_secondNameField.insets = new Insets(0, 0, 5, 5);
        gbc_secondNameField.fill = GridBagConstraints.BOTH;
        gbc_secondNameField.gridx = 2;
        gbc_secondNameField.gridy = 1;
        panel.add(secondNameField, gbc_secondNameField);

        JLabel renameSecondLbl = new JLabel("rename to");
        renameSecondLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        renameSecondLbl.setPreferredSize(new Dimension(80, 17));
        GridBagConstraints gbc_renameSecondLbl = new GridBagConstraints();
        gbc_renameSecondLbl.anchor = GridBagConstraints.EAST;
        gbc_renameSecondLbl.insets = new Insets(0, 0, 5, 5);
        gbc_renameSecondLbl.gridx = 4;
        gbc_renameSecondLbl.gridy = 1;
        panel.add(renameSecondLbl, gbc_renameSecondLbl);

        secondRenameField = new JTextField();
        GridBagConstraints gbc_secondRenameField = new GridBagConstraints();
        gbc_secondRenameField.gridwidth = 2;
        gbc_secondRenameField.insets = new Insets(0, 0, 5, 0);
        gbc_secondRenameField.fill = GridBagConstraints.BOTH;
        gbc_secondRenameField.gridx = 5;
        gbc_secondRenameField.gridy = 1;
        panel.add(secondRenameField, gbc_secondRenameField);
        secondRenameField.setColumns(10);

        JLabel idLbl = new JLabel("ID");
        idLbl.setVerticalAlignment(SwingConstants.BOTTOM);
        idLbl.setVerticalTextPosition(SwingConstants.BOTTOM);
        GridBagConstraints gbc_idLbl = new GridBagConstraints();
        gbc_idLbl.fill = GridBagConstraints.VERTICAL;
        gbc_idLbl.insets = new Insets(0, 0, 5, 5);
        gbc_idLbl.gridx = 0;
        gbc_idLbl.gridy = 2;
        panel.add(idLbl, gbc_idLbl);

        JLabel typeLbl = new JLabel("Type");
        typeLbl.setVerticalAlignment(SwingConstants.BOTTOM);
        typeLbl.setPreferredSize(new Dimension(29, 25));
        typeLbl.setVerticalTextPosition(SwingConstants.BOTTOM);
        typeLbl.setHorizontalTextPosition(SwingConstants.CENTER);
        typeLbl.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_typeLbl = new GridBagConstraints();
        gbc_typeLbl.fill = GridBagConstraints.VERTICAL;
        gbc_typeLbl.insets = new Insets(0, 0, 5, 5);
        gbc_typeLbl.gridx = 1;
        gbc_typeLbl.gridy = 2;
        panel.add(typeLbl, gbc_typeLbl);

        JLabel weightLbl = new JLabel("Weight");
        weightLbl.setVerticalAlignment(SwingConstants.BOTTOM);
        weightLbl.setVerticalTextPosition(SwingConstants.BOTTOM);
        weightLbl.setHorizontalTextPosition(SwingConstants.CENTER);
        weightLbl.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_weightLbl = new GridBagConstraints();
        gbc_weightLbl.fill = GridBagConstraints.BOTH;
        gbc_weightLbl.insets = new Insets(0, 0, 5, 5);
        gbc_weightLbl.gridx = 2;
        gbc_weightLbl.gridy = 2;
        panel.add(weightLbl, gbc_weightLbl);

        JLabel phonWeightLbl = new JLabel("Phon. weight");
        phonWeightLbl.setVerticalAlignment(SwingConstants.BOTTOM);
        phonWeightLbl.setVerticalTextPosition(SwingConstants.BOTTOM);
        phonWeightLbl.setHorizontalTextPosition(SwingConstants.CENTER);
        phonWeightLbl.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_phonWeightLbl = new GridBagConstraints();
        gbc_phonWeightLbl.fill = GridBagConstraints.BOTH;
        gbc_phonWeightLbl.insets = new Insets(0, 0, 5, 5);
        gbc_phonWeightLbl.gridx = 3;
        gbc_phonWeightLbl.gridy = 2;
        panel.add(phonWeightLbl, gbc_phonWeightLbl);

        idField = new JTextField();
        idField.setPreferredSize(new Dimension(10, 29));
        GridBagConstraints gbc_idField = new GridBagConstraints();
        gbc_idField.fill = GridBagConstraints.BOTH;
        gbc_idField.insets = new Insets(0, 0, 0, 5);
        gbc_idField.gridx = 0;
        gbc_idField.gridy = 3;
        panel.add(idField, gbc_idField);
        idField.setColumns(10);

        JComboBox typeField = new JComboBox();
        typeField.setEditable(true);
        GridBagConstraints gbc_typeField = new GridBagConstraints();
        gbc_typeField.insets = new Insets(0, 0, 0, 5);
        gbc_typeField.fill = GridBagConstraints.BOTH;
        gbc_typeField.gridx = 1;
        gbc_typeField.gridy = 3;
        panel.add(typeField, gbc_typeField);

        JSpinner weightField = new JSpinner();
        weightField.setPreferredSize(new Dimension(65, 30));
        weightField.setMinimumSize(new Dimension(50, 30));
        GridBagConstraints gbc_weightField = new GridBagConstraints();
        gbc_weightField.fill = GridBagConstraints.HORIZONTAL;
        gbc_weightField.insets = new Insets(0, 0, 0, 5);
        gbc_weightField.gridx = 2;
        gbc_weightField.gridy = 3;
        panel.add(weightField, gbc_weightField);

        JSpinner phonWeightField = new JSpinner();
        GridBagConstraints gbc_phonWeightField = new GridBagConstraints();
        gbc_phonWeightField.fill = GridBagConstraints.BOTH;
        gbc_phonWeightField.insets = new Insets(0, 0, 0, 5);
        gbc_phonWeightField.gridx = 3;
        gbc_phonWeightField.gridy = 3;
        panel.add(phonWeightField, gbc_phonWeightField);

    }

}
