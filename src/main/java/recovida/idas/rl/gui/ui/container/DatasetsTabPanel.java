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
import recovida.idas.rl.gui.ui.WarningIcon;
import recovida.idas.rl.gui.ui.field.EncodingComboBox;
import recovida.idas.rl.gui.ui.field.JComboBoxWithPlaceholder;
import recovida.idas.rl.gui.ui.field.JTextFieldWithPlaceholder;

public class DatasetsTabPanel extends JPanel {

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

        GridBagLayout gbl_datasetsTabPanel = new GridBagLayout();
        gbl_datasetsTabPanel.columnWidths = new int[] { 35, 150, 180, 150, 35 };
        gbl_datasetsTabPanel.rowHeights = new int[] { 10, 20, 30, 0, 30, 30, 0,
                30 };
        gbl_datasetsTabPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
                0.0 };
        gbl_datasetsTabPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 1.0 };
        setLayout(gbl_datasetsTabPanel);

        // top margin

        Component datasetsTabTopMargin = Box
                .createRigidArea(new Dimension(20, 20));
        GridBagConstraints gbc_datasetsTabTopMargin = new GridBagConstraints();
        gbc_datasetsTabTopMargin.insets = new Insets(0, 0, 5, 5);
        gbc_datasetsTabTopMargin.gridx = 2;
        gbc_datasetsTabTopMargin.gridy = 0;
        add(datasetsTabTopMargin, gbc_datasetsTabTopMargin);

        // bottom margin
        Component datasetsTabBottomMargin = Box.createVerticalGlue();
        GridBagConstraints gbc_datasetsTabBottomMargin = new GridBagConstraints();
        gbc_datasetsTabBottomMargin.fill = GridBagConstraints.VERTICAL;
        gbc_datasetsTabBottomMargin.insets = new Insets(0, 0, 0, 5);
        gbc_datasetsTabBottomMargin.gridx = 2;
        gbc_datasetsTabBottomMargin.gridy = 7;
        add(datasetsTabBottomMargin, gbc_datasetsTabBottomMargin);

        // top label (A)

        firstDatasetLabel = new JLabel("_Dataset A");
        firstDatasetLabel
                .setFont(firstDatasetLabel.getFont().deriveFont(Font.BOLD));
        GridBagConstraints gbc_firstDatasetLabel = new GridBagConstraints();
        gbc_firstDatasetLabel.weightx = 1.0;
        gbc_firstDatasetLabel.insets = new Insets(0, 0, 5, 5);
        gbc_firstDatasetLabel.gridx = 1;
        gbc_firstDatasetLabel.gridy = 1;
        add(firstDatasetLabel, gbc_firstDatasetLabel);

        // top label (B)

        secondDatasetLabel = new JLabel("_Dataset B");
        secondDatasetLabel
                .setFont(secondDatasetLabel.getFont().deriveFont(Font.BOLD));
        GridBagConstraints gbc_secondDatasetLabel = new GridBagConstraints();
        gbc_secondDatasetLabel.weightx = 1.0;
        gbc_secondDatasetLabel.insets = new Insets(0, 0, 5, 5);
        gbc_secondDatasetLabel.gridx = 3;
        gbc_secondDatasetLabel.gridy = 1;
        add(secondDatasetLabel, gbc_secondDatasetLabel);

        // file name

        fileNameLbl = new JLabel("_File name");
        GridBagConstraints gbc_fileNameLbl = new GridBagConstraints();
        gbc_fileNameLbl.insets = new Insets(0, 0, 5, 5);
        gbc_fileNameLbl.gridx = 2;
        gbc_fileNameLbl.gridy = 2;
        add(fileNameLbl, gbc_fileNameLbl);

        // file name (A)

        firstDatasetWarningLbl = new WarningIcon();
        GridBagConstraints gbc_firstDatasetWarningLbl = new GridBagConstraints();
        gbc_firstDatasetWarningLbl.anchor = GridBagConstraints.EAST;
        gbc_firstDatasetWarningLbl.insets = new Insets(0, 0, 5, 5);
        gbc_firstDatasetWarningLbl.gridx = 0;
        gbc_firstDatasetWarningLbl.gridy = 2;
        add(firstDatasetWarningLbl, gbc_firstDatasetWarningLbl);

        JPanel firstDatasetContainer = new JPanel();
        GridBagConstraints gbc_firstDatasetContainer = new GridBagConstraints();
        gbc_firstDatasetContainer.insets = new Insets(0, 0, 5, 5);
        gbc_firstDatasetContainer.fill = GridBagConstraints.HORIZONTAL;
        gbc_firstDatasetContainer.gridx = 1;
        gbc_firstDatasetContainer.gridy = 2;
        add(firstDatasetContainer, gbc_firstDatasetContainer);
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
        GridBagConstraints gbc_secondDatasetContainer = new GridBagConstraints();
        gbc_secondDatasetContainer.insets = new Insets(0, 0, 5, 5);
        gbc_secondDatasetContainer.fill = GridBagConstraints.HORIZONTAL;
        gbc_secondDatasetContainer.gridx = 3;
        gbc_secondDatasetContainer.gridy = 2;
        add(secondDatasetContainer, gbc_secondDatasetContainer);
        secondDatasetContainer.setLayout(
                new BoxLayout(secondDatasetContainer, BoxLayout.X_AXIS));

        secondDatasetBtn = new JButton("_Select...");
        secondDatasetContainer.add(secondDatasetBtn);

        secondDatasetField = new JTextField();
        secondDatasetField.setHorizontalAlignment(SwingConstants.TRAILING);
        secondDatasetContainer.add(secondDatasetField);
        secondDatasetField.setColumns(10);

        secondDatasetWarningLbl = new WarningIcon();
        GridBagConstraints gbc_secondDatasetWarningLbl = new GridBagConstraints();
        gbc_secondDatasetWarningLbl.anchor = GridBagConstraints.WEST;
        gbc_secondDatasetWarningLbl.insets = new Insets(0, 0, 5, 0);
        gbc_secondDatasetWarningLbl.gridx = 4;
        gbc_secondDatasetWarningLbl.gridy = 2;
        add(secondDatasetWarningLbl, gbc_secondDatasetWarningLbl);

        // encoding

        encodingLbl = new JLabel("_Encoding");
        GridBagConstraints gbc_encodingLbl = new GridBagConstraints();
        gbc_encodingLbl.insets = new Insets(0, 0, 5, 5);
        gbc_encodingLbl.gridx = 2;
        gbc_encodingLbl.gridy = 3;
        add(encodingLbl, gbc_encodingLbl);

        // encoding (A)

        firstEncodingWarningLbl = new WarningIcon();
        GridBagConstraints gbc_firstEncodingWarningLbl = new GridBagConstraints();
        gbc_firstEncodingWarningLbl.anchor = GridBagConstraints.EAST;
        gbc_firstEncodingWarningLbl.insets = new Insets(0, 0, 5, 5);
        gbc_firstEncodingWarningLbl.gridx = 0;
        gbc_firstEncodingWarningLbl.gridy = 3;
        add(firstEncodingWarningLbl, gbc_firstEncodingWarningLbl);

        JPanel firstEncodingContainer = new JPanel();
        GridBagConstraints gbc_firstEncodingContainer = new GridBagConstraints();
        gbc_firstEncodingContainer.fill = GridBagConstraints.HORIZONTAL;
        gbc_firstEncodingContainer.insets = new Insets(0, 0, 5, 5);
        gbc_firstEncodingContainer.gridx = 1;
        gbc_firstEncodingContainer.gridy = 3;
        add(firstEncodingContainer, gbc_firstEncodingContainer);
        firstEncodingContainer.setLayout(
                new BoxLayout(firstEncodingContainer, BoxLayout.X_AXIS));

        firstEncodingField = new EncodingComboBox();
        firstEncodingField.setEditable(true);
        new JComboBoxSuggestionProvider(firstEncodingField);
        firstEncodingContainer.add(firstEncodingField);

        // encoding (B)

        JPanel secondEncodingContainer = new JPanel();
        GridBagConstraints gbc_secondEncodingContainer = new GridBagConstraints();
        gbc_secondEncodingContainer.insets = new Insets(0, 0, 5, 5);
        gbc_secondEncodingContainer.fill = GridBagConstraints.HORIZONTAL;
        gbc_secondEncodingContainer.gridx = 3;
        gbc_secondEncodingContainer.gridy = 3;
        add(secondEncodingContainer, gbc_secondEncodingContainer);
        secondEncodingContainer.setLayout(
                new BoxLayout(secondEncodingContainer, BoxLayout.X_AXIS));

        secondEncodingField = new EncodingComboBox();
        secondEncodingField.setEditable(true);
        new JComboBoxSuggestionProvider(secondEncodingField);
        secondEncodingContainer.add(secondEncodingField);

        secondEncodingWarningLbl = new WarningIcon();
        GridBagConstraints gbc_secondEncodingWarningLbl = new GridBagConstraints();
        gbc_secondEncodingWarningLbl.anchor = GridBagConstraints.WEST;
        gbc_secondEncodingWarningLbl.insets = new Insets(0, 0, 5, 0);
        gbc_secondEncodingWarningLbl.gridx = 4;
        gbc_secondEncodingWarningLbl.gridy = 3;
        add(secondEncodingWarningLbl, gbc_secondEncodingWarningLbl);

        // suffix

        suffixLbl = new JLabel("_Suffix");
        GridBagConstraints gbc_suffixLbl = new GridBagConstraints();
        gbc_suffixLbl.insets = new Insets(0, 0, 5, 5);
        gbc_suffixLbl.gridx = 2;
        gbc_suffixLbl.gridy = 4;
        add(suffixLbl, gbc_suffixLbl);

        // suffix (A)

        firstDatasetSuffixWarningLbl = new WarningIcon();
        GridBagConstraints gbc_firstDatasetSuffixWarningLbl = new GridBagConstraints();
        gbc_firstDatasetSuffixWarningLbl.anchor = GridBagConstraints.EAST;
        gbc_firstDatasetSuffixWarningLbl.insets = new Insets(0, 0, 5, 5);
        gbc_firstDatasetSuffixWarningLbl.gridx = 0;
        gbc_firstDatasetSuffixWarningLbl.gridy = 4;
        add(firstDatasetSuffixWarningLbl, gbc_firstDatasetSuffixWarningLbl);

        JPanel firstDatasetSuffixContainer = new JPanel();
        GridBagConstraints gbc_firstDatasetSuffixContainer = new GridBagConstraints();
        gbc_firstDatasetSuffixContainer.fill = GridBagConstraints.BOTH;
        gbc_firstDatasetSuffixContainer.insets = new Insets(0, 0, 5, 5);
        gbc_firstDatasetSuffixContainer.gridx = 1;
        gbc_firstDatasetSuffixContainer.gridy = 4;
        add(firstDatasetSuffixContainer, gbc_firstDatasetSuffixContainer);
        firstDatasetSuffixContainer.setLayout(
                new BoxLayout(firstDatasetSuffixContainer, BoxLayout.X_AXIS));

        firstDatasetSuffixField = new JTextFieldWithPlaceholder();
        firstDatasetSuffixContainer.add(firstDatasetSuffixField);
        firstDatasetSuffixField.setColumns(10);

        // suffix (B)

        JPanel secondDatasetSuffixContainer = new JPanel();
        GridBagConstraints gbc_secondDatasetSuffixContainer = new GridBagConstraints();
        gbc_secondDatasetSuffixContainer.fill = GridBagConstraints.BOTH;
        gbc_secondDatasetSuffixContainer.insets = new Insets(0, 0, 5, 5);
        gbc_secondDatasetSuffixContainer.gridx = 3;
        gbc_secondDatasetSuffixContainer.gridy = 4;
        add(secondDatasetSuffixContainer, gbc_secondDatasetSuffixContainer);
        secondDatasetSuffixContainer.setLayout(
                new BoxLayout(secondDatasetSuffixContainer, BoxLayout.X_AXIS));

        secondDatasetSuffixField = new JTextFieldWithPlaceholder();
        secondDatasetSuffixContainer.add(secondDatasetSuffixField);
        secondDatasetSuffixField.setColumns(10);

        secondDatasetSuffixWarningLbl = new WarningIcon();
        GridBagConstraints gbc_secondDatasetSuffixWarningLbl = new GridBagConstraints();
        gbc_secondDatasetSuffixWarningLbl.anchor = GridBagConstraints.WEST;
        gbc_secondDatasetSuffixWarningLbl.insets = new Insets(0, 0, 5, 0);
        gbc_secondDatasetSuffixWarningLbl.gridx = 4;
        gbc_secondDatasetSuffixWarningLbl.gridy = 4;
        add(secondDatasetSuffixWarningLbl, gbc_secondDatasetSuffixWarningLbl);

        // row num col

        rowNumColLbl = new JLabel("_Row number column name");
        GridBagConstraints gbc_rowNumColLbl = new GridBagConstraints();
        gbc_rowNumColLbl.insets = new Insets(0, 0, 5, 5);
        gbc_rowNumColLbl.gridx = 2;
        gbc_rowNumColLbl.gridy = 5;
        add(rowNumColLbl, gbc_rowNumColLbl);

        // row num col (A)

        JPanel firstDatasetRowNumColContainer = new JPanel();
        GridBagConstraints gbc_firstDatasetRowNumColContainer = new GridBagConstraints();
        gbc_firstDatasetRowNumColContainer.fill = GridBagConstraints.BOTH;
        gbc_firstDatasetRowNumColContainer.insets = new Insets(0, 0, 5, 5);
        gbc_firstDatasetRowNumColContainer.gridx = 1;
        gbc_firstDatasetRowNumColContainer.gridy = 5;
        add(firstDatasetRowNumColContainer, gbc_firstDatasetRowNumColContainer);
        firstDatasetRowNumColContainer.setLayout(new BoxLayout(
                firstDatasetRowNumColContainer, BoxLayout.X_AXIS));

        firstDatasetRowNumColField = new JTextFieldWithPlaceholder();
        firstDatasetRowNumColContainer.add(firstDatasetRowNumColField);
        firstDatasetRowNumColField.setColumns(10);

        firstDatasetRowNumColWarningLbl = new WarningIcon();
        GridBagConstraints gbc_firstDatasetRowNumColWarningLbl = new GridBagConstraints();
        gbc_firstDatasetRowNumColWarningLbl.anchor = GridBagConstraints.EAST;
        gbc_firstDatasetRowNumColWarningLbl.insets = new Insets(0, 0, 5, 5);
        gbc_firstDatasetRowNumColWarningLbl.gridx = 0;
        gbc_firstDatasetRowNumColWarningLbl.gridy = 5;
        add(firstDatasetRowNumColWarningLbl,
                gbc_firstDatasetRowNumColWarningLbl);

        // row num col (B)

        JPanel secondDatasetRowNumColContainer = new JPanel();
        GridBagConstraints gbc_secondDatasetRowNumColContainer = new GridBagConstraints();
        gbc_secondDatasetRowNumColContainer.fill = GridBagConstraints.BOTH;
        gbc_secondDatasetRowNumColContainer.insets = new Insets(0, 0, 5, 5);
        gbc_secondDatasetRowNumColContainer.gridx = 3;
        gbc_secondDatasetRowNumColContainer.gridy = 5;
        add(secondDatasetRowNumColContainer,
                gbc_secondDatasetRowNumColContainer);
        secondDatasetRowNumColContainer.setLayout(new BoxLayout(
                secondDatasetRowNumColContainer, BoxLayout.X_AXIS));

        secondDatasetRowNumColField = new JTextFieldWithPlaceholder();
        secondDatasetRowNumColContainer.add(secondDatasetRowNumColField);
        secondDatasetRowNumColField.setColumns(10);

        secondDatasetRowNumColWarningLbl = new WarningIcon();
        GridBagConstraints gbc_secondDatasetRowNumColWarningLbl = new GridBagConstraints();
        gbc_secondDatasetRowNumColWarningLbl.anchor = GridBagConstraints.WEST;
        gbc_secondDatasetRowNumColWarningLbl.insets = new Insets(0, 0, 5, 0);
        gbc_secondDatasetRowNumColWarningLbl.gridx = 4;
        gbc_secondDatasetRowNumColWarningLbl.gridy = 5;
        add(secondDatasetRowNumColWarningLbl,
                gbc_secondDatasetRowNumColWarningLbl);

    }

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
