package com.cidacs.rl.editor.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

public class LinkageColumnEditingPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField firstRenameField;
    private JTextField secondRenameField;
    private final Component horizontalGlue_1 = Box.createHorizontalGlue();
    private JSpinnerWithBlankValue weightField;
    private JSpinnerWithBlankValue phonWeightField;
    private JComboBox<String> typeField;
    private JComboBox<String> firstNameField;
    private JSpinner numberField;
    private JComboBox<String> secondNameField;

    public JTextField getFirstRenameField() {
        return firstRenameField;
    }

    public JTextField getSecondRenameField() {
        return secondRenameField;
    }

    public JSpinner getPhonWeightField() {
        return phonWeightField;
    }

    public JSpinner getWeightField() {
        return weightField;
    }

    public JComboBox<String> getTypeField() {
        return typeField;
    }

    public JComboBox<String> getFirstNameField() {
        return firstNameField;
    }

    public JSpinner getNumberField() {
        return numberField;
    }

    public JComboBox<String> getSecondNameField() {
        return secondNameField;
    }

    /**
     * Create the panel.
     */
    public LinkageColumnEditingPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JPanel panel = new JPanel();
        add(panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[] { 0, 40, 40, 40, 40, 40, 70, 0, 0 };
        gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
        gbl_panel.columnWeights = new double[] { 0.0, 1.0, 1.0, 1.0, 1.0, 0.0,
                1.0, 0.0, 0.0 };
        gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                Double.MIN_VALUE };
        panel.setLayout(gbl_panel);

        Component verticalGlue_1 = Box.createVerticalGlue();
        GridBagConstraints gbc_verticalGlue_1 = new GridBagConstraints();
        gbc_verticalGlue_1.insets = new Insets(0, 0, 5, 5);
        gbc_verticalGlue_1.gridx = 3;
        gbc_verticalGlue_1.gridy = 0;
        panel.add(verticalGlue_1, gbc_verticalGlue_1);

        JLabel numberLbl = new JLabel("Number");
        numberLbl.setPreferredSize(new Dimension(25, 17));
        numberLbl.setHorizontalAlignment(SwingConstants.CENTER);
        numberLbl.setHorizontalTextPosition(SwingConstants.CENTER);
        numberLbl.setVerticalAlignment(SwingConstants.BOTTOM);
        numberLbl.setVerticalTextPosition(SwingConstants.BOTTOM);
        GridBagConstraints gbc_numberLbl = new GridBagConstraints();
        gbc_numberLbl.fill = GridBagConstraints.BOTH;
        gbc_numberLbl.insets = new Insets(0, 0, 5, 5);
        gbc_numberLbl.gridx = 1;
        gbc_numberLbl.gridy = 1;
        panel.add(numberLbl, gbc_numberLbl);

        JLabel nameFirstLbl = new JLabel("Column name (A)");
        nameFirstLbl.setPreferredSize(new Dimension(123, 17));
        nameFirstLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_nameFirstLbl = new GridBagConstraints();
        gbc_nameFirstLbl.fill = GridBagConstraints.HORIZONTAL;
        gbc_nameFirstLbl.insets = new Insets(0, 0, 5, 5);
        gbc_nameFirstLbl.gridx = 2;
        gbc_nameFirstLbl.gridy = 1;
        panel.add(nameFirstLbl, gbc_nameFirstLbl);

        firstNameField = new JComboBox<>();
        firstNameField.setEditable(true);
        GridBagConstraints gbc_firstNameField = new GridBagConstraints();
        gbc_firstNameField.gridwidth = 2;
        gbc_firstNameField.insets = new Insets(0, 0, 5, 5);
        gbc_firstNameField.fill = GridBagConstraints.BOTH;
        gbc_firstNameField.gridx = 3;
        gbc_firstNameField.gridy = 1;
        panel.add(firstNameField, gbc_firstNameField);

        JLabel renameFirstLbl = new JLabel("rename to");
        renameFirstLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_renameFirstLbl = new GridBagConstraints();
        gbc_renameFirstLbl.fill = GridBagConstraints.HORIZONTAL;
        gbc_renameFirstLbl.insets = new Insets(0, 0, 5, 5);
        gbc_renameFirstLbl.gridx = 5;
        gbc_renameFirstLbl.gridy = 1;
        panel.add(renameFirstLbl, gbc_renameFirstLbl);

        firstRenameField = new JTextField();
        GridBagConstraints gbc_firstRenameField = new GridBagConstraints();
        gbc_firstRenameField.gridwidth = 2;
        gbc_firstRenameField.insets = new Insets(0, 0, 5, 5);
        gbc_firstRenameField.fill = GridBagConstraints.BOTH;
        gbc_firstRenameField.gridx = 6;
        gbc_firstRenameField.gridy = 1;
        panel.add(firstRenameField, gbc_firstRenameField);
        firstRenameField.setColumns(10);

        numberField = new JSpinner();
        numberField.setPreferredSize(new Dimension(40, 30));
        GridBagConstraints gbc_numberField = new GridBagConstraints();
        gbc_numberField.fill = GridBagConstraints.BOTH;
        gbc_numberField.insets = new Insets(0, 0, 5, 5);
        gbc_numberField.gridx = 1;
        gbc_numberField.gridy = 2;
        panel.add(numberField, gbc_numberField);

        JLabel nameSecondLbl = new JLabel("Column name (B)");
        nameSecondLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        nameSecondLbl.setPreferredSize(new Dimension(123, 17));
        GridBagConstraints gbc_nameSecondLbl = new GridBagConstraints();
        gbc_nameSecondLbl.fill = GridBagConstraints.HORIZONTAL;
        gbc_nameSecondLbl.insets = new Insets(0, 0, 5, 5);
        gbc_nameSecondLbl.gridx = 2;
        gbc_nameSecondLbl.gridy = 2;
        panel.add(nameSecondLbl, gbc_nameSecondLbl);

        secondNameField = new JComboBox<>();
        secondNameField.setEditable(true);
        GridBagConstraints gbc_secondNameField = new GridBagConstraints();
        gbc_secondNameField.gridwidth = 2;
        gbc_secondNameField.insets = new Insets(0, 0, 5, 5);
        gbc_secondNameField.fill = GridBagConstraints.BOTH;
        gbc_secondNameField.gridx = 3;
        gbc_secondNameField.gridy = 2;
        panel.add(secondNameField, gbc_secondNameField);

        JLabel renameSecondLbl = new JLabel("rename to");
        renameSecondLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        renameSecondLbl.setPreferredSize(new Dimension(80, 17));
        GridBagConstraints gbc_renameSecondLbl = new GridBagConstraints();
        gbc_renameSecondLbl.anchor = GridBagConstraints.EAST;
        gbc_renameSecondLbl.insets = new Insets(0, 0, 5, 5);
        gbc_renameSecondLbl.gridx = 5;
        gbc_renameSecondLbl.gridy = 2;
        panel.add(renameSecondLbl, gbc_renameSecondLbl);

        secondRenameField = new JTextField();
        GridBagConstraints gbc_secondRenameField = new GridBagConstraints();
        gbc_secondRenameField.gridwidth = 2;
        gbc_secondRenameField.insets = new Insets(0, 0, 5, 5);
        gbc_secondRenameField.fill = GridBagConstraints.BOTH;
        gbc_secondRenameField.gridx = 6;
        gbc_secondRenameField.gridy = 2;
        panel.add(secondRenameField, gbc_secondRenameField);
        secondRenameField.setColumns(10);

        JLabel typeLbl = new JLabel("Type");
        typeLbl.setVerticalAlignment(SwingConstants.BOTTOM);
        typeLbl.setPreferredSize(new Dimension(29, 25));
        typeLbl.setVerticalTextPosition(SwingConstants.BOTTOM);
        typeLbl.setHorizontalTextPosition(SwingConstants.CENTER);
        typeLbl.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_typeLbl = new GridBagConstraints();
        gbc_typeLbl.fill = GridBagConstraints.VERTICAL;
        gbc_typeLbl.insets = new Insets(0, 0, 5, 5);
        gbc_typeLbl.gridx = 2;
        gbc_typeLbl.gridy = 3;
        panel.add(typeLbl, gbc_typeLbl);

        JLabel weightLbl = new JLabel("Weight");
        weightLbl.setVerticalAlignment(SwingConstants.BOTTOM);
        weightLbl.setVerticalTextPosition(SwingConstants.BOTTOM);
        weightLbl.setHorizontalTextPosition(SwingConstants.CENTER);
        weightLbl.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_weightLbl = new GridBagConstraints();
        gbc_weightLbl.fill = GridBagConstraints.BOTH;
        gbc_weightLbl.insets = new Insets(0, 0, 5, 5);
        gbc_weightLbl.gridx = 3;
        gbc_weightLbl.gridy = 3;
        panel.add(weightLbl, gbc_weightLbl);

        JLabel phonWeightLbl = new JLabel("Phon. weight");
        phonWeightLbl.setVerticalAlignment(SwingConstants.BOTTOM);
        phonWeightLbl.setVerticalTextPosition(SwingConstants.BOTTOM);
        phonWeightLbl.setHorizontalTextPosition(SwingConstants.CENTER);
        phonWeightLbl.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_phonWeightLbl = new GridBagConstraints();
        gbc_phonWeightLbl.fill = GridBagConstraints.BOTH;
        gbc_phonWeightLbl.insets = new Insets(0, 0, 5, 5);
        gbc_phonWeightLbl.gridx = 4;
        gbc_phonWeightLbl.gridy = 3;
        panel.add(phonWeightLbl, gbc_phonWeightLbl);

        Component horizontalGlue = Box.createHorizontalGlue();
        GridBagConstraints gbc_horizontalGlue = new GridBagConstraints();
        gbc_horizontalGlue.insets = new Insets(0, 0, 5, 5);
        gbc_horizontalGlue.gridx = 0;
        gbc_horizontalGlue.gridy = 4;
        panel.add(horizontalGlue, gbc_horizontalGlue);

        typeField = new JComboBox<>();
        typeField.setEditable(true);
        GridBagConstraints gbc_typeField = new GridBagConstraints();
        gbc_typeField.insets = new Insets(0, 0, 5, 5);
        gbc_typeField.fill = GridBagConstraints.BOTH;
        gbc_typeField.gridx = 2;
        gbc_typeField.gridy = 4;
        panel.add(typeField, gbc_typeField);

        Double zero = 0.0;
        weightField = new JSpinnerWithBlankValue(
                new SpinnerNumberModel(0.0, 0.0, Double.MAX_VALUE, 0.1));
        weightField.setBlankValue(zero);
        weightField.setEditor(new JSpinner.NumberEditor(weightField, "0.0000"));
        weightField.setPreferredSize(new Dimension(65, 30));
        weightField.setMinimumSize(new Dimension(50, 30));
        GridBagConstraints gbc_weightField = new GridBagConstraints();
        gbc_weightField.fill = GridBagConstraints.HORIZONTAL;
        gbc_weightField.insets = new Insets(0, 0, 5, 5);
        gbc_weightField.gridx = 3;
        gbc_weightField.gridy = 4;
        panel.add(weightField, gbc_weightField);

        phonWeightField = new JSpinnerWithBlankValue(
                new SpinnerNumberModel(0.0, 0.0, Double.MAX_VALUE, 0.1));
        phonWeightField.setBlankValue(zero);
        phonWeightField.setEditor(
                new JSpinner.NumberEditor(phonWeightField, "0.0000"));
        phonWeightField.setPreferredSize(new Dimension(65, 30));
        phonWeightField.setMinimumSize(new Dimension(50, 30));
        GridBagConstraints gbc_phonWeightField = new GridBagConstraints();
        gbc_phonWeightField.fill = GridBagConstraints.BOTH;
        gbc_phonWeightField.insets = new Insets(0, 0, 5, 5);
        gbc_phonWeightField.gridx = 4;
        gbc_phonWeightField.gridy = 4;
        panel.add(phonWeightField, gbc_phonWeightField);
        GridBagConstraints gbc_horizontalGlue_1 = new GridBagConstraints();
        gbc_horizontalGlue_1.insets = new Insets(0, 0, 5, 0);
        gbc_horizontalGlue_1.gridx = 8;
        gbc_horizontalGlue_1.gridy = 4;
        panel.add(horizontalGlue_1, gbc_horizontalGlue_1);

        Component verticalGlue = Box.createVerticalGlue();
        GridBagConstraints gbc_verticalGlue = new GridBagConstraints();
        gbc_verticalGlue.insets = new Insets(0, 0, 0, 5);
        gbc_verticalGlue.gridx = 3;
        gbc_verticalGlue.gridy = 5;
        panel.add(verticalGlue, gbc_verticalGlue);

    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        Queue<Component> q = new LinkedList<>(Arrays.asList(getComponents()));
        while (!q.isEmpty()) {
            JComponent c = (JComponent) q.remove();
            c.setEnabled(enabled);
            if (c instanceof JPanel)
                q.addAll(Arrays.asList(c.getComponents()));
        }
    }

}
