package recovida.idas.rl.gui.ui.container;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import recovida.idas.rl.gui.lang.MessageProvider;
import recovida.idas.rl.gui.ui.JComboBoxSuggestionProvider;
import recovida.idas.rl.gui.ui.Translatable;
import recovida.idas.rl.gui.ui.WarningIcon;
import recovida.idas.rl.gui.ui.field.EncodingComboBox;
import recovida.idas.rl.gui.ui.field.JComboBoxWithPlaceholder;
import recovida.idas.rl.gui.ui.field.JTextFieldWithPlaceholder;

public class DatasetsTabPanel extends JPanel implements Translatable {

    private static final long serialVersionUID = -2753754491877596410L;

    // fields
    private JTextField firstDatasetField;

    private JTextField secondDatasetField;

    private JComboBoxWithPlaceholder firstEncodingField;

    private JComboBoxWithPlaceholder secondEncodingField;

    private JTextFieldWithPlaceholder firstDatasetSuffixField;

    private JTextFieldWithPlaceholder secondDatasetSuffixField;

    private JTextFieldWithPlaceholder firstDatasetRowNumColField;

    private JTextFieldWithPlaceholder secondDatasetRowNumColField;

    // error icons
    private WarningIcon firstDatasetWarningLbl;

    private WarningIcon secondDatasetWarningLbl;

    private WarningIcon firstEncodingWarningLbl;

    private WarningIcon secondEncodingWarningLbl;

    private WarningIcon firstDatasetSuffixWarningLbl;

    private WarningIcon secondDatasetSuffixWarningLbl;

    private WarningIcon secondDatasetRowNumColWarningLbl;

    private WarningIcon firstDatasetRowNumColWarningLbl;

    // buttons
    private JButton firstDatasetBtn;

    private JButton secondDatasetBtn;

    // labels
    private JLabel firstDatasetLabel;

    private JLabel secondDatasetLabel;

    private JLabel fileNameLbl;

    private JLabel encodingLbl;

    private JLabel suffixLbl;

    private JLabel rowNumColLbl;

    public DatasetsTabPanel() {

        // layout

        GridBagLayout datasetsTabPanelGBL = new GridBagLayout();
        datasetsTabPanelGBL.columnWidths = new int[] { 35, 150, 180, 150, 35 };
        datasetsTabPanelGBL.rowHeights = new int[] { 10, 20, 30, 0, 30, 30, 0,
                30 };
        datasetsTabPanelGBL.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
                0.0 };
        datasetsTabPanelGBL.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 1.0 };
        setLayout(datasetsTabPanelGBL);

        // top margin

        Component datasetsTabTopMargin = Box
                .createRigidArea(new Dimension(20, 20));
        GridBagConstraints datasetsTabTopMarginGBC = new GridBagConstraints();
        datasetsTabTopMarginGBC.insets = new Insets(0, 0, 5, 5);
        datasetsTabTopMarginGBC.gridx = 2;
        datasetsTabTopMarginGBC.gridy = 0;
        add(datasetsTabTopMargin, datasetsTabTopMarginGBC);

        // bottom margin
        Component datasetsTabBottomMargin = Box.createVerticalGlue();
        GridBagConstraints datasetsTabBottomMarginGBC = new GridBagConstraints();
        datasetsTabBottomMarginGBC.fill = GridBagConstraints.VERTICAL;
        datasetsTabBottomMarginGBC.insets = new Insets(0, 0, 0, 5);
        datasetsTabBottomMarginGBC.gridx = 2;
        datasetsTabBottomMarginGBC.gridy = 7;
        add(datasetsTabBottomMargin, datasetsTabBottomMarginGBC);

        // top label (A)

        firstDatasetLabel = new JLabel("_Dataset A");
        firstDatasetLabel
                .setFont(firstDatasetLabel.getFont().deriveFont(Font.BOLD));
        GridBagConstraints firstDatasetLabelGBC = new GridBagConstraints();
        firstDatasetLabelGBC.weightx = 1.0;
        firstDatasetLabelGBC.insets = new Insets(0, 0, 5, 5);
        firstDatasetLabelGBC.gridx = 1;
        firstDatasetLabelGBC.gridy = 1;
        add(firstDatasetLabel, firstDatasetLabelGBC);

        // top label (B)

        secondDatasetLabel = new JLabel("_Dataset B");
        secondDatasetLabel
                .setFont(secondDatasetLabel.getFont().deriveFont(Font.BOLD));
        GridBagConstraints secondDatasetLabelGBC = new GridBagConstraints();
        secondDatasetLabelGBC.weightx = 1.0;
        secondDatasetLabelGBC.insets = new Insets(0, 0, 5, 5);
        secondDatasetLabelGBC.gridx = 3;
        secondDatasetLabelGBC.gridy = 1;
        add(secondDatasetLabel, secondDatasetLabelGBC);

        // file name

        fileNameLbl = new JLabel("_File name");
        GridBagConstraints fileNameLblGBC = new GridBagConstraints();
        fileNameLblGBC.insets = new Insets(0, 0, 5, 5);
        fileNameLblGBC.gridx = 2;
        fileNameLblGBC.gridy = 2;
        add(fileNameLbl, fileNameLblGBC);

        // file name (A)

        firstDatasetWarningLbl = new WarningIcon();
        GridBagConstraints firstDatasetWarningLblGBC = new GridBagConstraints();
        firstDatasetWarningLblGBC.anchor = GridBagConstraints.EAST;
        firstDatasetWarningLblGBC.insets = new Insets(0, 0, 5, 5);
        firstDatasetWarningLblGBC.gridx = 0;
        firstDatasetWarningLblGBC.gridy = 2;
        add(firstDatasetWarningLbl, firstDatasetWarningLblGBC);

        JPanel firstDatasetContainer = new JPanel();
        GridBagConstraints firstDatasetContainerGBC = new GridBagConstraints();
        firstDatasetContainerGBC.insets = new Insets(0, 0, 5, 5);
        firstDatasetContainerGBC.fill = GridBagConstraints.HORIZONTAL;
        firstDatasetContainerGBC.gridx = 1;
        firstDatasetContainerGBC.gridy = 2;
        add(firstDatasetContainer, firstDatasetContainerGBC);
        firstDatasetContainer.setLayout(
                new BoxLayout(firstDatasetContainer, BoxLayout.X_AXIS));

        firstDatasetField = new JTextField();
        firstDatasetField.setHorizontalAlignment(SwingConstants.TRAILING);
        firstDatasetContainer.add(firstDatasetField);
        firstDatasetField.setColumns(10);

        firstDatasetBtn = new JButton("_Select...");
        firstDatasetContainer.add(firstDatasetBtn);

        // file name (B)

        JPanel secondDatasetContainer = new JPanel();
        GridBagConstraints secondDatasetContainerGBC = new GridBagConstraints();
        secondDatasetContainerGBC.insets = new Insets(0, 0, 5, 5);
        secondDatasetContainerGBC.fill = GridBagConstraints.HORIZONTAL;
        secondDatasetContainerGBC.gridx = 3;
        secondDatasetContainerGBC.gridy = 2;
        add(secondDatasetContainer, secondDatasetContainerGBC);
        secondDatasetContainer.setLayout(
                new BoxLayout(secondDatasetContainer, BoxLayout.X_AXIS));

        secondDatasetBtn = new JButton("_Select...");
        secondDatasetContainer.add(secondDatasetBtn);

        secondDatasetField = new JTextField();
        secondDatasetField.setHorizontalAlignment(SwingConstants.TRAILING);
        secondDatasetContainer.add(secondDatasetField);
        secondDatasetField.setColumns(10);

        secondDatasetWarningLbl = new WarningIcon();
        GridBagConstraints secondDatasetWarningLblGBC = new GridBagConstraints();
        secondDatasetWarningLblGBC.anchor = GridBagConstraints.WEST;
        secondDatasetWarningLblGBC.insets = new Insets(0, 0, 5, 0);
        secondDatasetWarningLblGBC.gridx = 4;
        secondDatasetWarningLblGBC.gridy = 2;
        add(secondDatasetWarningLbl, secondDatasetWarningLblGBC);

        // encoding

        encodingLbl = new JLabel("_Encoding");
        GridBagConstraints encodingLblGBC = new GridBagConstraints();
        encodingLblGBC.insets = new Insets(0, 0, 5, 5);
        encodingLblGBC.gridx = 2;
        encodingLblGBC.gridy = 3;
        add(encodingLbl, encodingLblGBC);

        // encoding (A)

        firstEncodingWarningLbl = new WarningIcon();
        GridBagConstraints firstEncodingWarningLblGBC = new GridBagConstraints();
        firstEncodingWarningLblGBC.anchor = GridBagConstraints.EAST;
        firstEncodingWarningLblGBC.insets = new Insets(0, 0, 5, 5);
        firstEncodingWarningLblGBC.gridx = 0;
        firstEncodingWarningLblGBC.gridy = 3;
        add(firstEncodingWarningLbl, firstEncodingWarningLblGBC);

        JPanel firstEncodingContainer = new JPanel();
        GridBagConstraints firstEncodingContainerGBC = new GridBagConstraints();
        firstEncodingContainerGBC.fill = GridBagConstraints.HORIZONTAL;
        firstEncodingContainerGBC.insets = new Insets(0, 0, 5, 5);
        firstEncodingContainerGBC.gridx = 1;
        firstEncodingContainerGBC.gridy = 3;
        add(firstEncodingContainer, firstEncodingContainerGBC);
        firstEncodingContainer.setLayout(
                new BoxLayout(firstEncodingContainer, BoxLayout.X_AXIS));

        firstEncodingField = new EncodingComboBox();
        firstEncodingField.setEditable(true);
        new JComboBoxSuggestionProvider(firstEncodingField);
        firstEncodingContainer.add(firstEncodingField);

        // encoding (B)

        JPanel secondEncodingContainer = new JPanel();
        GridBagConstraints secondEncodingContainerGBC = new GridBagConstraints();
        secondEncodingContainerGBC.insets = new Insets(0, 0, 5, 5);
        secondEncodingContainerGBC.fill = GridBagConstraints.HORIZONTAL;
        secondEncodingContainerGBC.gridx = 3;
        secondEncodingContainerGBC.gridy = 3;
        add(secondEncodingContainer, secondEncodingContainerGBC);
        secondEncodingContainer.setLayout(
                new BoxLayout(secondEncodingContainer, BoxLayout.X_AXIS));

        secondEncodingField = new EncodingComboBox();
        secondEncodingField.setEditable(true);
        new JComboBoxSuggestionProvider(secondEncodingField);
        secondEncodingContainer.add(secondEncodingField);

        secondEncodingWarningLbl = new WarningIcon();
        GridBagConstraints secondEncodingWarningLblGBC = new GridBagConstraints();
        secondEncodingWarningLblGBC.anchor = GridBagConstraints.WEST;
        secondEncodingWarningLblGBC.insets = new Insets(0, 0, 5, 0);
        secondEncodingWarningLblGBC.gridx = 4;
        secondEncodingWarningLblGBC.gridy = 3;
        add(secondEncodingWarningLbl, secondEncodingWarningLblGBC);

        // suffix

        suffixLbl = new JLabel("_Suffix");
        GridBagConstraints suffixLblGBC = new GridBagConstraints();
        suffixLblGBC.insets = new Insets(0, 0, 5, 5);
        suffixLblGBC.gridx = 2;
        suffixLblGBC.gridy = 4;
        add(suffixLbl, suffixLblGBC);

        // suffix (A)

        firstDatasetSuffixWarningLbl = new WarningIcon();
        GridBagConstraints firstDatasetSuffixWarningLblGBC = new GridBagConstraints();
        firstDatasetSuffixWarningLblGBC.anchor = GridBagConstraints.EAST;
        firstDatasetSuffixWarningLblGBC.insets = new Insets(0, 0, 5, 5);
        firstDatasetSuffixWarningLblGBC.gridx = 0;
        firstDatasetSuffixWarningLblGBC.gridy = 4;
        add(firstDatasetSuffixWarningLbl, firstDatasetSuffixWarningLblGBC);

        JPanel firstDatasetSuffixContainer = new JPanel();
        GridBagConstraints firstDatasetSuffixContainerGBC = new GridBagConstraints();
        firstDatasetSuffixContainerGBC.fill = GridBagConstraints.BOTH;
        firstDatasetSuffixContainerGBC.insets = new Insets(0, 0, 5, 5);
        firstDatasetSuffixContainerGBC.gridx = 1;
        firstDatasetSuffixContainerGBC.gridy = 4;
        add(firstDatasetSuffixContainer, firstDatasetSuffixContainerGBC);
        firstDatasetSuffixContainer.setLayout(
                new BoxLayout(firstDatasetSuffixContainer, BoxLayout.X_AXIS));

        firstDatasetSuffixField = new JTextFieldWithPlaceholder();
        firstDatasetSuffixContainer.add(firstDatasetSuffixField);
        firstDatasetSuffixField.setColumns(10);

        // suffix (B)

        JPanel secondDatasetSuffixContainer = new JPanel();
        GridBagConstraints secondDatasetSuffixContainerGBC = new GridBagConstraints();
        secondDatasetSuffixContainerGBC.fill = GridBagConstraints.BOTH;
        secondDatasetSuffixContainerGBC.insets = new Insets(0, 0, 5, 5);
        secondDatasetSuffixContainerGBC.gridx = 3;
        secondDatasetSuffixContainerGBC.gridy = 4;
        add(secondDatasetSuffixContainer, secondDatasetSuffixContainerGBC);
        secondDatasetSuffixContainer.setLayout(
                new BoxLayout(secondDatasetSuffixContainer, BoxLayout.X_AXIS));

        secondDatasetSuffixField = new JTextFieldWithPlaceholder();
        secondDatasetSuffixContainer.add(secondDatasetSuffixField);
        secondDatasetSuffixField.setColumns(10);

        secondDatasetSuffixWarningLbl = new WarningIcon();
        GridBagConstraints secondDatasetSuffixWarningLblGBC = new GridBagConstraints();
        secondDatasetSuffixWarningLblGBC.anchor = GridBagConstraints.WEST;
        secondDatasetSuffixWarningLblGBC.insets = new Insets(0, 0, 5, 0);
        secondDatasetSuffixWarningLblGBC.gridx = 4;
        secondDatasetSuffixWarningLblGBC.gridy = 4;
        add(secondDatasetSuffixWarningLbl, secondDatasetSuffixWarningLblGBC);

        // row num col

        rowNumColLbl = new JLabel("_Row number column name");
        GridBagConstraints rowNumColLblGBC = new GridBagConstraints();
        rowNumColLblGBC.insets = new Insets(0, 0, 5, 5);
        rowNumColLblGBC.gridx = 2;
        rowNumColLblGBC.gridy = 5;
        add(rowNumColLbl, rowNumColLblGBC);

        // row num col (A)

        JPanel firstDatasetRowNumColContainer = new JPanel();
        GridBagConstraints firstDatasetRowNumColContainerGBC = new GridBagConstraints();
        firstDatasetRowNumColContainerGBC.fill = GridBagConstraints.BOTH;
        firstDatasetRowNumColContainerGBC.insets = new Insets(0, 0, 5, 5);
        firstDatasetRowNumColContainerGBC.gridx = 1;
        firstDatasetRowNumColContainerGBC.gridy = 5;
        add(firstDatasetRowNumColContainer, firstDatasetRowNumColContainerGBC);
        firstDatasetRowNumColContainer.setLayout(new BoxLayout(
                firstDatasetRowNumColContainer, BoxLayout.X_AXIS));

        firstDatasetRowNumColField = new JTextFieldWithPlaceholder();
        firstDatasetRowNumColContainer.add(firstDatasetRowNumColField);
        firstDatasetRowNumColField.setColumns(10);

        firstDatasetRowNumColWarningLbl = new WarningIcon();
        GridBagConstraints firstDatasetRowNumColWarningLblGBC = new GridBagConstraints();
        firstDatasetRowNumColWarningLblGBC.anchor = GridBagConstraints.EAST;
        firstDatasetRowNumColWarningLblGBC.insets = new Insets(0, 0, 5, 5);
        firstDatasetRowNumColWarningLblGBC.gridx = 0;
        firstDatasetRowNumColWarningLblGBC.gridy = 5;
        add(firstDatasetRowNumColWarningLbl,
                firstDatasetRowNumColWarningLblGBC);

        // row num col (B)

        JPanel secondDatasetRowNumColContainer = new JPanel();
        GridBagConstraints secondDatasetRowNumColContainerGBC = new GridBagConstraints();
        secondDatasetRowNumColContainerGBC.fill = GridBagConstraints.BOTH;
        secondDatasetRowNumColContainerGBC.insets = new Insets(0, 0, 5, 5);
        secondDatasetRowNumColContainerGBC.gridx = 3;
        secondDatasetRowNumColContainerGBC.gridy = 5;
        add(secondDatasetRowNumColContainer,
                secondDatasetRowNumColContainerGBC);
        secondDatasetRowNumColContainer.setLayout(new BoxLayout(
                secondDatasetRowNumColContainer, BoxLayout.X_AXIS));

        secondDatasetRowNumColField = new JTextFieldWithPlaceholder();
        secondDatasetRowNumColContainer.add(secondDatasetRowNumColField);
        secondDatasetRowNumColField.setColumns(10);

        secondDatasetRowNumColWarningLbl = new WarningIcon();
        GridBagConstraints secondDatasetRowNumColWarningLblGBC = new GridBagConstraints();
        secondDatasetRowNumColWarningLblGBC.anchor = GridBagConstraints.WEST;
        secondDatasetRowNumColWarningLblGBC.insets = new Insets(0, 0, 5, 0);
        secondDatasetRowNumColWarningLblGBC.gridx = 4;
        secondDatasetRowNumColWarningLblGBC.gridy = 5;
        add(secondDatasetRowNumColWarningLbl,
                secondDatasetRowNumColWarningLblGBC);

    }

    @Override
    public void updateLocalisedStrings() {
        firstDatasetLabel
                .setText(MessageProvider.getMessage("datasets.dataseta"));
        secondDatasetLabel
                .setText(MessageProvider.getMessage("datasets.datasetb"));
        firstDatasetBtn.setText(MessageProvider.getMessage("datasets.select"));
        secondDatasetBtn.setText(MessageProvider.getMessage("datasets.select"));
        fileNameLbl.setText(MessageProvider.getMessage("datasets.filename"));
        encodingLbl.setText(MessageProvider.getMessage("datasets.encoding"));
        suffixLbl.setText(MessageProvider.getMessage("datasets.suffix"));
        rowNumColLbl
                .setText(MessageProvider.getMessage("datasets.rownumcolname"));

    }

    public JTextField getFirstDatasetField() {
        return firstDatasetField;
    }

    public JTextField getSecondDatasetField() {
        return secondDatasetField;
    }

    public JComboBoxWithPlaceholder getFirstEncodingField() {
        return firstEncodingField;
    }

    public JComboBoxWithPlaceholder getSecondEncodingField() {
        return secondEncodingField;
    }

    public JTextFieldWithPlaceholder getFirstDatasetSuffixField() {
        return firstDatasetSuffixField;
    }

    public JTextFieldWithPlaceholder getSecondDatasetSuffixField() {
        return secondDatasetSuffixField;
    }

    public JTextFieldWithPlaceholder getFirstDatasetRowNumColField() {
        return firstDatasetRowNumColField;
    }

    public JTextFieldWithPlaceholder getSecondDatasetRowNumColField() {
        return secondDatasetRowNumColField;
    }

    public WarningIcon getFirstDatasetWarningLbl() {
        return firstDatasetWarningLbl;
    }

    public WarningIcon getSecondDatasetWarningLbl() {
        return secondDatasetWarningLbl;
    }

    public WarningIcon getFirstEncodingWarningLbl() {
        return firstEncodingWarningLbl;
    }

    public WarningIcon getSecondEncodingWarningLbl() {
        return secondEncodingWarningLbl;
    }

    public WarningIcon getFirstDatasetSuffixWarningLbl() {
        return firstDatasetSuffixWarningLbl;
    }

    public WarningIcon getSecondDatasetSuffixWarningLbl() {
        return secondDatasetSuffixWarningLbl;
    }

    public WarningIcon getSecondDatasetRowNumColWarningLbl() {
        return secondDatasetRowNumColWarningLbl;
    }

    public WarningIcon getFirstDatasetRowNumColWarningLbl() {
        return firstDatasetRowNumColWarningLbl;
    }

    public JButton getFirstDatasetBtn() {
        return firstDatasetBtn;
    }

    public JButton getSecondDatasetBtn() {
        return secondDatasetBtn;
    }

    public JLabel getFirstDatasetLabel() {
        return firstDatasetLabel;
    }

    public JLabel getSecondDatasetLabel() {
        return secondDatasetLabel;
    }

    public JLabel getFileNameLbl() {
        return fileNameLbl;
    }

    public JLabel getEncodingLbl() {
        return encodingLbl;
    }

    public JLabel getSuffixLbl() {
        return suffixLbl;
    }

    public JLabel getRowNumColLbl() {
        return rowNumColLbl;
    }

}
