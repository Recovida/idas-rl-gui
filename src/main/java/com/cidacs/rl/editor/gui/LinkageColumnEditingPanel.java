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
    private final Component rightHorizontalGlue = Box.createHorizontalGlue();
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

    public LinkageColumnEditingPanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JPanel panel = new JPanel();
        add(panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[] { 0, 40, 40, 40, 40, 40, 70, 0 };
        gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
        gbl_panel.columnWeights = new double[] { 0.0, 1.0, 1.0, 1.0, 1.0, 0.0,
                1.0, 0.0 };
        gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                Double.MIN_VALUE };
        panel.setLayout(gbl_panel);

        Component topVerticalGlue = Box.createVerticalGlue();
        GridBagConstraints gbc_topVerticalGlue = new GridBagConstraints();
        gbc_topVerticalGlue.insets = new Insets(0, 0, 5, 5);
        gbc_topVerticalGlue.gridx = 3;
        gbc_topVerticalGlue.gridy = 0;
        panel.add(topVerticalGlue, gbc_topVerticalGlue);

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

        JPanel firstNameContainer = new JPanel();
        GridBagConstraints gbc_firstNameContainer = new GridBagConstraints();
        gbc_firstNameContainer.fill = GridBagConstraints.BOTH;
        gbc_firstNameContainer.gridwidth = 2;
        gbc_firstNameContainer.insets = new Insets(0, 0, 5, 5);
        gbc_firstNameContainer.gridx = 3;
        gbc_firstNameContainer.gridy = 1;
        panel.add(firstNameContainer, gbc_firstNameContainer);
        firstNameContainer
                .setLayout(new BoxLayout(firstNameContainer, BoxLayout.X_AXIS));

        firstNameField = new JComboBox<>();
        firstNameContainer.add(firstNameField);
        firstNameField.setEditable(true);
        new JComboBoxSuggestionProvider(firstNameField);

        JLabel firstNameWarningLbl = new WarningIcon();
        firstNameContainer.add(firstNameWarningLbl);

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
        gbc_firstRenameField.insets = new Insets(0, 0, 5, 5);
        gbc_firstRenameField.fill = GridBagConstraints.BOTH;
        gbc_firstRenameField.gridx = 6;
        gbc_firstRenameField.gridy = 1;
        panel.add(firstRenameField, gbc_firstRenameField);
        firstRenameField.setColumns(10);

        JPanel numberContainer = new JPanel();
        GridBagConstraints gbc_numberContainer = new GridBagConstraints();
        gbc_numberContainer.fill = GridBagConstraints.BOTH;
        gbc_numberContainer.insets = new Insets(0, 0, 5, 5);
        gbc_numberContainer.gridx = 1;
        gbc_numberContainer.gridy = 2;
        panel.add(numberContainer, gbc_numberContainer);
        numberContainer
                .setLayout(new BoxLayout(numberContainer, BoxLayout.X_AXIS));

        numberField = new JSpinnerWithBlankValue(
                new SpinnerNumberModel(0, -1, Integer.MAX_VALUE, 1));
        numberContainer.add(numberField);
        ((JSpinnerWithBlankValue) numberField)
                .setBlankValue(Integer.valueOf(-1));
        numberField.setPreferredSize(new Dimension(40, 30));

        JLabel numberWarningLbl = new WarningIcon();
        numberContainer.add(numberWarningLbl);

        JLabel nameSecondLbl = new JLabel("Column name (B)");
        nameSecondLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        nameSecondLbl.setPreferredSize(new Dimension(123, 17));
        GridBagConstraints gbc_nameSecondLbl = new GridBagConstraints();
        gbc_nameSecondLbl.fill = GridBagConstraints.HORIZONTAL;
        gbc_nameSecondLbl.insets = new Insets(0, 0, 5, 5);
        gbc_nameSecondLbl.gridx = 2;
        gbc_nameSecondLbl.gridy = 2;
        panel.add(nameSecondLbl, gbc_nameSecondLbl);

        JPanel secondNameContainer = new JPanel();
        GridBagConstraints gbc_secondNameContainer = new GridBagConstraints();
        gbc_secondNameContainer.fill = GridBagConstraints.BOTH;
        gbc_secondNameContainer.gridwidth = 2;
        gbc_secondNameContainer.insets = new Insets(0, 0, 5, 5);
        gbc_secondNameContainer.gridx = 3;
        gbc_secondNameContainer.gridy = 2;
        panel.add(secondNameContainer, gbc_secondNameContainer);
        secondNameContainer.setLayout(
                new BoxLayout(secondNameContainer, BoxLayout.X_AXIS));

        secondNameField = new JComboBox<>();
        secondNameContainer.add(secondNameField);
        secondNameField.setEditable(true);
        new JComboBoxSuggestionProvider(secondNameField);

        JLabel secondNameWarningLbl = new WarningIcon();
        secondNameContainer.add(secondNameWarningLbl);

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

        Component leftHorizontalGlue = Box.createHorizontalGlue();
        GridBagConstraints gbc_leftHorizontalGlue = new GridBagConstraints();
        gbc_leftHorizontalGlue.insets = new Insets(0, 0, 5, 5);
        gbc_leftHorizontalGlue.gridx = 0;
        gbc_leftHorizontalGlue.gridy = 4;
        panel.add(leftHorizontalGlue, gbc_leftHorizontalGlue);

        JPanel typeContainer = new JPanel();
        GridBagConstraints gbc_typeContainer = new GridBagConstraints();
        gbc_typeContainer.fill = GridBagConstraints.BOTH;
        gbc_typeContainer.insets = new Insets(0, 0, 5, 5);
        gbc_typeContainer.gridx = 2;
        gbc_typeContainer.gridy = 4;
        panel.add(typeContainer, gbc_typeContainer);
        typeContainer.setLayout(new BoxLayout(typeContainer, BoxLayout.X_AXIS));

        typeField = new JComboBox<>();
        typeContainer.add(typeField);
        typeField.setEditable(true);
        new JComboBoxSuggestionProvider(typeField);

        JLabel typeWarningLbl = new WarningIcon();
        typeContainer.add(typeWarningLbl);

        Double zero = 0.0;

        JPanel weightContainer = new JPanel();
        GridBagConstraints gbc_weightContainer = new GridBagConstraints();
        gbc_weightContainer.fill = GridBagConstraints.BOTH;
        gbc_weightContainer.insets = new Insets(0, 0, 5, 5);
        gbc_weightContainer.gridx = 3;
        gbc_weightContainer.gridy = 4;
        panel.add(weightContainer, gbc_weightContainer);
        weightContainer
                .setLayout(new BoxLayout(weightContainer, BoxLayout.X_AXIS));
        weightField = new JSpinnerWithBlankValue(
                new SpinnerNumberModel(0.0, 0.0, Double.MAX_VALUE, 0.1));
        weightContainer.add(weightField);
        weightField.setBlankValue(zero);
        weightField.setEditor(new JSpinner.NumberEditor(weightField, "0.0000"));
        weightField.setPreferredSize(new Dimension(65, 30));
        weightField.setMinimumSize(new Dimension(50, 30));

        JLabel weightWarningLbl = new WarningIcon();
        weightContainer.add(weightWarningLbl);

        JPanel phonWeightContainer = new JPanel();
        GridBagConstraints gbc_phonWeightContainer = new GridBagConstraints();
        gbc_phonWeightContainer.fill = GridBagConstraints.BOTH;
        gbc_phonWeightContainer.insets = new Insets(0, 0, 5, 5);
        gbc_phonWeightContainer.gridx = 4;
        gbc_phonWeightContainer.gridy = 4;
        panel.add(phonWeightContainer, gbc_phonWeightContainer);
        phonWeightContainer.setLayout(
                new BoxLayout(phonWeightContainer, BoxLayout.X_AXIS));

        phonWeightField = new JSpinnerWithBlankValue(
                new SpinnerNumberModel(0.0, 0.0, Double.MAX_VALUE, 0.1));
        phonWeightContainer.add(phonWeightField);
        phonWeightField.setBlankValue(zero);
        phonWeightField.setEditor(
                new JSpinner.NumberEditor(phonWeightField, "0.0000"));
        phonWeightField.setPreferredSize(new Dimension(65, 30));
        phonWeightField.setMinimumSize(new Dimension(50, 30));

        JLabel phonWeightWarningLbl = new WarningIcon();
        phonWeightContainer.add(phonWeightWarningLbl);
        GridBagConstraints gbc_rightHorizontalGlue = new GridBagConstraints();
        gbc_rightHorizontalGlue.insets = new Insets(0, 0, 5, 0);
        gbc_rightHorizontalGlue.gridx = 7;
        gbc_rightHorizontalGlue.gridy = 4;
        panel.add(rightHorizontalGlue, gbc_rightHorizontalGlue);

        Component bottomVerticalGlue = Box.createVerticalGlue();
        GridBagConstraints gbc_bottomVerticalGlue = new GridBagConstraints();
        gbc_bottomVerticalGlue.insets = new Insets(0, 0, 0, 5);
        gbc_bottomVerticalGlue.gridx = 3;
        gbc_bottomVerticalGlue.gridy = 5;
        panel.add(bottomVerticalGlue, gbc_bottomVerticalGlue);

        init();

    }

    protected void init() {
        String[] types = { "copy", "name", "date", "ibge", "gender",
                "numerical_id", "categorical" };
        for (String type : types)
            typeField.addItem(type);
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
