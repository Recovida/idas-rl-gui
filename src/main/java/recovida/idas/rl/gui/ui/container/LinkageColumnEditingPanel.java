package recovida.idas.rl.gui.ui.container;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import recovida.idas.rl.core.config.ConfigReader;
import recovida.idas.rl.gui.lang.MessageProvider;
import recovida.idas.rl.gui.ui.ErrorIconLabel;
import recovida.idas.rl.gui.ui.JComboBoxSuggestionProvider;
import recovida.idas.rl.gui.ui.Translatable;
import recovida.idas.rl.gui.ui.field.JSpinnerWithBlankValue;
import recovida.idas.rl.gui.ui.field.JTextFieldWithPlaceholder;
import recovida.idas.rl.gui.ui.table.ColumnPairTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import java.awt.Color;

/**
 * A panel to let the user edit the column pair fields in a
 * {@link ColumnPairTable} (outside the table).
 */
public class LinkageColumnEditingPanel extends JPanel implements Translatable {

    private static final long serialVersionUID = 1L;

    private static final String[] TYPES = { "copy", "name", "date", "ibge",
            "gender", "numerical_id", "categorical" };

    private final JTextFieldWithPlaceholder firstRenameField;

    private final JTextFieldWithPlaceholder secondRenameField;

    private final JSpinnerWithBlankValue weightField;

    private final JSpinnerWithBlankValue phonWeightField;

    private final JComboBox<String> typeField;

    private final JComboBox<String> firstNameField;

    private final JSpinner numberField;

    private final JComboBox<String> secondNameField;

    private final ErrorIconLabel weightWarningLbl;

    private final ErrorIconLabel typeWarningLbl;

    private final ErrorIconLabel secondNameWarningLbl;

    private final ErrorIconLabel numberWarningLbl;

    private final ErrorIconLabel firstNameWarningLbl;

    private final JLabel phonWeightLbl;

    private final JLabel weightLbl;

    private final ErrorIconLabel phonWeightWarningLbl;

    private final JLabel typeLbl;

    private final JLabel renameSecondLbl;

    private final JLabel nameSecondLbl;

    private final JLabel renameFirstLbl;

    private final JLabel nameFirstLbl;

    private final JLabel numberLbl;
    private JTextField similarityColField;
    private JPanel similarityPanel;
    private JSpinnerWithBlankValue similarityMinField;
    private JLabel similarityColLbl;
    private JLabel similarityMinLbl;
    private Component verticalGlue;
    private Component verticalGlue_1;

    public JTextFieldWithPlaceholder getFirstRenameField() {
        return firstRenameField;
    }

    public JTextFieldWithPlaceholder getSecondRenameField() {
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
     * Creates an instance of the panel.
     */
    public LinkageColumnEditingPanel() {

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        JPanel panel = new JPanel();
        add(panel);
        GridBagLayout panelGBL = new GridBagLayout();
        panelGBL.columnWidths = new int[] { 0, 50, 35, 171, 10, 35, 107, 169,
                0 };
        panelGBL.rowHeights = new int[] { 13, 35, 35, 35, 35, 35 };
        panelGBL.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 1.0, 0.0,
                0.0, 0.0, 0.0 };
        panelGBL.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0 };
        panel.setLayout(panelGBL);

        Component vg = Box.createVerticalGlue();
        GridBagConstraints vgGBC = new GridBagConstraints();
        vgGBC.insets = new Insets(0, 0, 5, 5);
        vgGBC.gridx = 0;
        vgGBC.gridy = 0;
        panel.add(vg, vgGBC);

        numberLbl = new JLabel("_Number");
        numberLbl.setPreferredSize(new Dimension(25, 17));
        numberLbl.setHorizontalAlignment(SwingConstants.CENTER);
        numberLbl.setHorizontalTextPosition(SwingConstants.CENTER);
        numberLbl.setVerticalAlignment(SwingConstants.BOTTOM);
        numberLbl.setVerticalTextPosition(SwingConstants.BOTTOM);
        GridBagConstraints numberLblGBC = new GridBagConstraints();
        numberLblGBC.fill = GridBagConstraints.BOTH;
        numberLblGBC.insets = new Insets(0, 0, 5, 5);
        numberLblGBC.gridx = 1;
        numberLblGBC.gridy = 1;
        panel.add(numberLbl, numberLblGBC);

        nameFirstLbl = new JLabel("_Column name (A)");
        nameFirstLbl.setPreferredSize(new Dimension(123, 17));
        nameFirstLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints nameFirstLblGBC = new GridBagConstraints();
        nameFirstLblGBC.fill = GridBagConstraints.HORIZONTAL;
        nameFirstLblGBC.insets = new Insets(0, 0, 5, 5);
        nameFirstLblGBC.gridx = 3;
        nameFirstLblGBC.gridy = 1;
        panel.add(nameFirstLbl, nameFirstLblGBC);

        JPanel firstNameContainer = new JPanel();
        GridBagConstraints firstNameContainerGBC = new GridBagConstraints();
        firstNameContainerGBC.fill = GridBagConstraints.BOTH;
        firstNameContainerGBC.insets = new Insets(0, 0, 5, 5);
        firstNameContainerGBC.gridx = 4;
        firstNameContainerGBC.gridy = 1;
        panel.add(firstNameContainer, firstNameContainerGBC);
        firstNameContainer
                .setLayout(new BoxLayout(firstNameContainer, BoxLayout.X_AXIS));

        firstNameField = new JComboBox<>();
        firstNameContainer.add(firstNameField);
        firstNameField.setEditable(true);
        new JComboBoxSuggestionProvider(firstNameField);

        firstNameWarningLbl = new ErrorIconLabel();
        GridBagConstraints firstNameWarningLblGBC = new GridBagConstraints();
        firstNameWarningLblGBC.anchor = GridBagConstraints.WEST;
        firstNameWarningLblGBC.insets = new Insets(0, 0, 5, 5);
        firstNameWarningLblGBC.gridx = 5;
        firstNameWarningLblGBC.gridy = 1;
        panel.add(firstNameWarningLbl, firstNameWarningLblGBC);

        renameFirstLbl = new JLabel("_rename to");
        renameFirstLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints renameFirstLblGBC = new GridBagConstraints();
        renameFirstLblGBC.fill = GridBagConstraints.HORIZONTAL;
        renameFirstLblGBC.insets = new Insets(0, 0, 5, 5);
        renameFirstLblGBC.gridx = 6;
        renameFirstLblGBC.gridy = 1;
        panel.add(renameFirstLbl, renameFirstLblGBC);

        firstRenameField = new JTextFieldWithPlaceholder();
        GridBagConstraints firstRenameFieldGBC = new GridBagConstraints();
        firstRenameFieldGBC.insets = new Insets(0, 0, 5, 5);
        firstRenameFieldGBC.fill = GridBagConstraints.BOTH;
        firstRenameFieldGBC.gridx = 7;
        firstRenameFieldGBC.gridy = 1;
        panel.add(firstRenameField, firstRenameFieldGBC);
        firstRenameField.setColumns(10);

        JPanel numberContainer = new JPanel();
        GridBagConstraints numberContainerGBC = new GridBagConstraints();
        numberContainerGBC.fill = GridBagConstraints.BOTH;
        numberContainerGBC.insets = new Insets(0, 0, 5, 5);
        numberContainerGBC.gridx = 1;
        numberContainerGBC.gridy = 2;
        panel.add(numberContainer, numberContainerGBC);
        numberContainer
                .setLayout(new BoxLayout(numberContainer, BoxLayout.X_AXIS));

        numberField = new JSpinnerWithBlankValue(
                new SpinnerNumberModel(0, -1, ConfigReader.MAX_NUMBER, 1), "0");
        numberContainer.add(numberField);
        ((JSpinnerWithBlankValue) numberField)
                .setBlankValue(Integer.valueOf(-1));
        numberField.setPreferredSize(new Dimension(40, 30));

        numberWarningLbl = new ErrorIconLabel();
        numberWarningLbl.setVisible(false);
        GridBagConstraints numberWarningLblGBC = new GridBagConstraints();
        numberWarningLblGBC.anchor = GridBagConstraints.WEST;
        numberWarningLblGBC.insets = new Insets(0, 0, 5, 5);
        numberWarningLblGBC.gridx = 2;
        numberWarningLblGBC.gridy = 2;
        panel.add(numberWarningLbl, numberWarningLblGBC);

        nameSecondLbl = new JLabel("_Column name (B)");
        nameSecondLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        nameSecondLbl.setPreferredSize(new Dimension(123, 17));
        GridBagConstraints nameSecondLblGBC = new GridBagConstraints();
        nameSecondLblGBC.fill = GridBagConstraints.HORIZONTAL;
        nameSecondLblGBC.insets = new Insets(0, 0, 5, 5);
        nameSecondLblGBC.gridx = 3;
        nameSecondLblGBC.gridy = 2;
        panel.add(nameSecondLbl, nameSecondLblGBC);

        JPanel secondNameContainer = new JPanel();
        GridBagConstraints secondNameContainerGBC = new GridBagConstraints();
        secondNameContainerGBC.fill = GridBagConstraints.BOTH;
        secondNameContainerGBC.insets = new Insets(0, 0, 5, 5);
        secondNameContainerGBC.gridx = 4;
        secondNameContainerGBC.gridy = 2;
        panel.add(secondNameContainer, secondNameContainerGBC);
        secondNameContainer.setLayout(
                new BoxLayout(secondNameContainer, BoxLayout.X_AXIS));

        secondNameField = new JComboBox<>();
        secondNameContainer.add(secondNameField);
        secondNameField.setEditable(true);
        new JComboBoxSuggestionProvider(secondNameField);

        secondNameWarningLbl = new ErrorIconLabel();
        GridBagConstraints secondNameWarningLblGBC = new GridBagConstraints();
        secondNameWarningLblGBC.anchor = GridBagConstraints.WEST;
        secondNameWarningLblGBC.insets = new Insets(0, 0, 5, 5);
        secondNameWarningLblGBC.gridx = 5;
        secondNameWarningLblGBC.gridy = 2;
        panel.add(secondNameWarningLbl, secondNameWarningLblGBC);

        renameSecondLbl = new JLabel("_rename to");
        renameSecondLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        renameSecondLbl.setPreferredSize(new Dimension(80, 17));
        GridBagConstraints renameSecondLblGBC = new GridBagConstraints();
        renameSecondLblGBC.fill = GridBagConstraints.HORIZONTAL;
        renameSecondLblGBC.insets = new Insets(0, 0, 5, 5);
        renameSecondLblGBC.gridx = 6;
        renameSecondLblGBC.gridy = 2;
        panel.add(renameSecondLbl, renameSecondLblGBC);

        secondRenameField = new JTextFieldWithPlaceholder();
        GridBagConstraints secondRenameFieldGBC = new GridBagConstraints();
        secondRenameFieldGBC.insets = new Insets(0, 0, 5, 5);
        secondRenameFieldGBC.fill = GridBagConstraints.BOTH;
        secondRenameFieldGBC.gridx = 7;
        secondRenameFieldGBC.gridy = 2;
        panel.add(secondRenameField, secondRenameFieldGBC);
        secondRenameField.setColumns(10);

        typeLbl = new JLabel("_Type");
        typeLbl.setVerticalAlignment(SwingConstants.BOTTOM);
        typeLbl.setPreferredSize(new Dimension(29, 25));
        typeLbl.setVerticalTextPosition(SwingConstants.BOTTOM);
        typeLbl.setHorizontalTextPosition(SwingConstants.CENTER);
        typeLbl.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints typeLblGBC = new GridBagConstraints();
        typeLblGBC.fill = GridBagConstraints.BOTH;
        typeLblGBC.insets = new Insets(0, 0, 5, 5);
        typeLblGBC.gridx = 1;
        typeLblGBC.gridy = 4;
        panel.add(typeLbl, typeLblGBC);

        weightLbl = new JLabel("_Weight");
        weightLbl.setVerticalAlignment(SwingConstants.BOTTOM);
        weightLbl.setVerticalTextPosition(SwingConstants.BOTTOM);
        weightLbl.setHorizontalTextPosition(SwingConstants.CENTER);
        weightLbl.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints weightLblGBC = new GridBagConstraints();
        weightLblGBC.anchor = GridBagConstraints.EAST;
        weightLblGBC.insets = new Insets(0, 0, 5, 5);
        weightLblGBC.gridx = 3;
        weightLblGBC.gridy = 4;
        panel.add(weightLbl, weightLblGBC);

        JPanel weightContainer = new JPanel();
        GridBagConstraints weightContainerGBC = new GridBagConstraints();
        weightContainerGBC.fill = GridBagConstraints.BOTH;
        weightContainerGBC.insets = new Insets(0, 0, 5, 5);
        weightContainerGBC.gridx = 4;
        weightContainerGBC.gridy = 4;
        panel.add(weightContainer, weightContainerGBC);
        weightContainer
                .setLayout(new BoxLayout(weightContainer, BoxLayout.X_AXIS));
        weightField = new JSpinnerWithBlankValue(
                new SpinnerNumberModel(0.0, 0.0, Double.MAX_VALUE, 0.1),
                "0.0000");
        weightContainer.add(weightField);
        weightField.setPreferredSize(new Dimension(65, 30));
        weightField.setMinimumSize(new Dimension(50, 30));

        weightWarningLbl = new ErrorIconLabel();
        weightWarningLbl.setVisible(false);
        GridBagConstraints weightWarningLblGBC = new GridBagConstraints();
        weightWarningLblGBC.anchor = GridBagConstraints.WEST;
        weightWarningLblGBC.insets = new Insets(0, 0, 5, 5);
        weightWarningLblGBC.gridx = 5;
        weightWarningLblGBC.gridy = 4;
        panel.add(weightWarningLbl, weightWarningLblGBC);

        similarityPanel = new JPanel();
        similarityPanel.setBorder(new TitledBorder(null, "_Similarity", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(59, 59, 59)));
        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.gridheight = 3;
        gbc_panel_1.gridwidth = 2;
        gbc_panel_1.insets = new Insets(0, 0, 5, 5);
        gbc_panel_1.fill = GridBagConstraints.VERTICAL;
        gbc_panel_1.gridx = 6;
        gbc_panel_1.gridy = 3;
        panel.add(similarityPanel, gbc_panel_1);
        GridBagLayout gbl_similarityPanel = new GridBagLayout();
        gbl_similarityPanel.columnWidths = new int[] { 95, 140 };
        gbl_similarityPanel.rowHeights = new int[] { 33, 33, 0, 0 };
        gbl_similarityPanel.columnWeights = new double[] { 0.0, 0.0 };
        gbl_similarityPanel.rowWeights = new double[] { 1.0, 1.0, 0.0,
                Double.MIN_VALUE };
        similarityPanel.setLayout(gbl_similarityPanel);

        similarityColLbl = new JLabel("_Column name");
        similarityColLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_similarityColLbl = new GridBagConstraints();
        gbc_similarityColLbl.fill = GridBagConstraints.BOTH;
        gbc_similarityColLbl.insets = new Insets(0, 0, 5, 5);
        gbc_similarityColLbl.gridx = 0;
        gbc_similarityColLbl.gridy = 0;
        similarityPanel.add(similarityColLbl, gbc_similarityColLbl);

        similarityColField = new JTextField();
        GridBagConstraints gbc_similarityColField = new GridBagConstraints();
        gbc_similarityColField.fill = GridBagConstraints.BOTH;
        gbc_similarityColField.insets = new Insets(0, 0, 5, 0);
        gbc_similarityColField.gridx = 1;
        gbc_similarityColField.gridy = 0;
        similarityPanel.add(similarityColField, gbc_similarityColField);
        similarityColField.setColumns(10);

        SpinnerNumberModel minSimilarityModel = new SpinnerNumberModel(0.0, 0.0,
                100, 0.001);

        similarityMinLbl = new JLabel("_Minimum");
        similarityMinLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_similarityMinLbl = new GridBagConstraints();
        gbc_similarityMinLbl.fill = GridBagConstraints.BOTH;
        gbc_similarityMinLbl.insets = new Insets(0, 0, 5, 5);
        gbc_similarityMinLbl.gridx = 0;
        gbc_similarityMinLbl.gridy = 1;
        similarityPanel.add(similarityMinLbl, gbc_similarityMinLbl);
        similarityMinField = new JSpinnerWithBlankValue(minSimilarityModel,
                "0.000");
        similarityMinField.setBlankValue(Double.valueOf(0.0));
        GridBagConstraints gbc_similarityMinField = new GridBagConstraints();
        gbc_similarityMinField.insets = new Insets(0, 0, 5, 0);
        gbc_similarityMinField.fill = GridBagConstraints.BOTH;
        gbc_similarityMinField.gridx = 1;
        gbc_similarityMinField.gridy = 1;
        similarityPanel.add(similarityMinField, gbc_similarityMinField);

        verticalGlue_1 = Box.createVerticalGlue();
        GridBagConstraints gbc_verticalGlue_1 = new GridBagConstraints();
        gbc_verticalGlue_1.gridx = 1;
        gbc_verticalGlue_1.gridy = 2;
        similarityPanel.add(verticalGlue_1, gbc_verticalGlue_1);

        Double zero = 0.0;

        JPanel typeContainer = new JPanel();
        GridBagConstraints typeContainerGBC = new GridBagConstraints();
        typeContainerGBC.fill = GridBagConstraints.BOTH;
        typeContainerGBC.insets = new Insets(0, 0, 5, 5);
        typeContainerGBC.gridx = 1;
        typeContainerGBC.gridy = 5;
        panel.add(typeContainer, typeContainerGBC);
        typeContainer.setLayout(new BoxLayout(typeContainer, BoxLayout.X_AXIS));

        typeField = new JComboBox<>();
        typeContainer.add(typeField);
        typeField.setEditable(true);
        new JComboBoxSuggestionProvider(typeField);

        typeWarningLbl = new ErrorIconLabel();
        typeWarningLbl.setVisible(false);
        GridBagConstraints typeWarningLblGBC = new GridBagConstraints();
        typeWarningLblGBC.anchor = GridBagConstraints.WEST;
        typeWarningLblGBC.insets = new Insets(0, 0, 5, 5);
        typeWarningLblGBC.gridx = 2;
        typeWarningLblGBC.gridy = 5;
        panel.add(typeWarningLbl, typeWarningLblGBC);

        phonWeightLbl = new JLabel("_Phon. weight");
        phonWeightLbl.setVerticalAlignment(SwingConstants.BOTTOM);
        phonWeightLbl.setVerticalTextPosition(SwingConstants.BOTTOM);
        phonWeightLbl.setHorizontalTextPosition(SwingConstants.CENTER);
        phonWeightLbl.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints phonWeightLblGBC = new GridBagConstraints();
        phonWeightLblGBC.anchor = GridBagConstraints.EAST;
        phonWeightLblGBC.insets = new Insets(0, 0, 5, 5);
        phonWeightLblGBC.gridx = 3;
        phonWeightLblGBC.gridy = 5;
        panel.add(phonWeightLbl, phonWeightLblGBC);

        JPanel phonWeightContainer = new JPanel();
        GridBagConstraints phonWeightContainerGBC = new GridBagConstraints();
        phonWeightContainerGBC.fill = GridBagConstraints.BOTH;
        phonWeightContainerGBC.insets = new Insets(0, 0, 5, 5);
        phonWeightContainerGBC.gridx = 4;
        phonWeightContainerGBC.gridy = 5;
        panel.add(phonWeightContainer, phonWeightContainerGBC);
        phonWeightContainer.setLayout(
                new BoxLayout(phonWeightContainer, BoxLayout.X_AXIS));

        phonWeightField = new JSpinnerWithBlankValue(
                new SpinnerNumberModel(0.0, 0.0, Double.MAX_VALUE, 0.1),
                "0.0000");
        phonWeightContainer.add(phonWeightField);
        phonWeightField.setBlankValue(zero);
        phonWeightField.setPreferredSize(new Dimension(65, 30));
        phonWeightField.setMinimumSize(new Dimension(50, 30));

        phonWeightWarningLbl = new ErrorIconLabel();
        phonWeightWarningLbl.setVisible(false);
        GridBagConstraints phonWeightWarningLblGBC = new GridBagConstraints();
        phonWeightWarningLblGBC.anchor = GridBagConstraints.WEST;
        phonWeightWarningLblGBC.insets = new Insets(0, 0, 5, 5);
        phonWeightWarningLblGBC.gridx = 5;
        phonWeightWarningLblGBC.gridy = 5;
        panel.add(phonWeightWarningLbl, phonWeightWarningLblGBC);

        Component bottomVerticalGlue = Box.createVerticalGlue();
        GridBagConstraints bottomVerticalGlueGBC = new GridBagConstraints();
        bottomVerticalGlueGBC.insets = new Insets(0, 0, 5, 5);
        bottomVerticalGlueGBC.gridx = 4;
        bottomVerticalGlueGBC.gridy = 6;
        panel.add(bottomVerticalGlue, bottomVerticalGlueGBC);

        verticalGlue = Box.createVerticalGlue();
        GridBagConstraints gbc_verticalGlue = new GridBagConstraints();
        gbc_verticalGlue.insets = new Insets(0, 0, 0, 5);
        gbc_verticalGlue.gridx = 1;
        gbc_verticalGlue.gridy = 7;
        panel.add(verticalGlue, gbc_verticalGlue);

        init();

    }

    protected void init() {
        for (String type : TYPES)
            typeField.addItem(type);
        typeField.setSelectedIndex(-1);
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

    public ErrorIconLabel getPhonWeightWarningLbl() {
        return phonWeightWarningLbl;
    }

    public ErrorIconLabel getWeightWarningLbl() {
        return weightWarningLbl;
    }

    public ErrorIconLabel getTypeWarningLbl() {
        return typeWarningLbl;
    }

    public ErrorIconLabel getSecondNameWarningLbl() {
        return secondNameWarningLbl;
    }

    public ErrorIconLabel getNumberWarningLbl() {
        return numberWarningLbl;
    }

    public ErrorIconLabel getFirstNameWarningLbl() {
        return firstNameWarningLbl;
    }

    public static Collection<String> getTypes() {
        return Arrays.asList(TYPES);
    }

    public JLabel getPhonWeightLbl() {
        return phonWeightLbl;
    }

    public JLabel getWeightLbl() {
        return weightLbl;
    }

    @Override
    public void updateLocalisedStrings() {
        numberLbl.setText(
                "* " + MessageProvider.getMessage("columns.editing.number"));
        nameFirstLbl
                .setText(MessageProvider.getMessage("columns.editing.indexa"));
        renameFirstLbl
                .setText(MessageProvider.getMessage("columns.editing.renamea"));
        nameSecondLbl
                .setText(MessageProvider.getMessage("columns.editing.indexb"));
        renameSecondLbl
                .setText(MessageProvider.getMessage("columns.editing.renameb"));
        typeLbl.setText(
                "* " + MessageProvider.getMessage("columns.editing.type"));
        weightLbl.setText(
                "* " + MessageProvider.getMessage("columns.editing.weight"));
        phonWeightLbl.setText(
                MessageProvider.getMessage("columns.editing.phonweight"));
        similarityColLbl.setText(
                MessageProvider.getMessage("columns.editing.colsimilarity"));
        similarityMinLbl.setText(
                MessageProvider.getMessage("columns.editing.minsimilarity"));
        ((TitledBorder) similarityPanel.getBorder()).setTitle(
                MessageProvider.getMessage("columns.editing.similarity"));
        weightField.updateLocalisedStrings();
        phonWeightField.updateLocalisedStrings();
        similarityMinField.updateLocalisedStrings();
    }

    public JPanel getSimilarityPanel() {
        return similarityPanel;
    }

    public JTextField getSimilarityColField() {
        return similarityColField;
    }

    public JSpinnerWithBlankValue getSimilarityMinField() {
        return similarityMinField;
    }

}
