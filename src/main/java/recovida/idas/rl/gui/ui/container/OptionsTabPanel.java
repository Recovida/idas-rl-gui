package recovida.idas.rl.gui.ui.container;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.MessageFormat;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.NumberEditor;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import recovida.idas.rl.gui.lang.MessageProvider;
import recovida.idas.rl.gui.ui.WarningIcon;
import recovida.idas.rl.gui.ui.field.JSpinnerWithBlankValue;
import recovida.idas.rl.gui.ui.field.JTextFieldWithPlaceholder;

public class OptionsTabPanel extends JPanel {

    private static final long serialVersionUID = -4785642571939247630L;

    // buttons
    private JButton linkageDirBtn;
    private JButton indexDirBtn;

    // labels
    private JLabel indexDirLbl;
    private JLabel minScoreLbl;
    private JLabel maxRowsLbl;
    private JLabel linkageDirLbl;
    private JLabel threadsLbl;
    private JLabel coresLabel;
    private JSpinnerWithBlankValue threadsField;
    private JTextFieldWithPlaceholder linkageDirField;
    private JTextFieldWithPlaceholder indexDirField;
    private JSpinner minScoreField;
    private JSpinner maxRowsField;

    public OptionsTabPanel() {

        // layout

        GridBagLayout gbl = new GridBagLayout();
        gbl.columnWidths = new int[] { 250, 500 };
        gbl.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
        gbl.columnWeights = new double[] { 0.0, 1.0 };
        gbl.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                Double.MIN_VALUE };
        setLayout(gbl);

        // margin

        Component optionsTabTopMargin = Box
                .createRigidArea(new Dimension(20, 20));
        GridBagConstraints gbc_optionsTabTopMargin = new GridBagConstraints();
        gbc_optionsTabTopMargin.insets = new Insets(0, 0, 5, 5);
        gbc_optionsTabTopMargin.gridx = 0;
        gbc_optionsTabTopMargin.gridy = 0;
        add(optionsTabTopMargin, gbc_optionsTabTopMargin);

        // linkage directory

        linkageDirLbl = new JLabel("_Linkage location");
        linkageDirLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_linkageDirLbl = new GridBagConstraints();
        gbc_linkageDirLbl.insets = new Insets(0, 0, 5, 5);
        gbc_linkageDirLbl.anchor = GridBagConstraints.EAST;
        gbc_linkageDirLbl.gridx = 0;
        gbc_linkageDirLbl.gridy = 1;
        add(linkageDirLbl, gbc_linkageDirLbl);

        JPanel linkageDirContainer = new JPanel();
        GridBagConstraints gbc_linkageDirContainer = new GridBagConstraints();
        gbc_linkageDirContainer.fill = GridBagConstraints.BOTH;
        gbc_linkageDirContainer.insets = new Insets(0, 0, 5, 0);
        gbc_linkageDirContainer.gridx = 1;
        gbc_linkageDirContainer.gridy = 1;
        add(linkageDirContainer, gbc_linkageDirContainer);
        linkageDirContainer.setLayout(
                new BoxLayout(linkageDirContainer, BoxLayout.X_AXIS));

        JLabel linkageDirWarningLbl = new WarningIcon();
        linkageDirContainer.add(linkageDirWarningLbl);

        linkageDirField = new JTextFieldWithPlaceholder();
        linkageDirField.setHorizontalAlignment(SwingConstants.TRAILING);
        linkageDirContainer.add(linkageDirField);
        linkageDirField.setColumns(10);

        linkageDirBtn = new JButton("_Select...");
        linkageDirContainer.add(linkageDirBtn);

        // index dir

        indexDirLbl = new JLabel("_Index location");
        indexDirLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_indexDirLbl = new GridBagConstraints();
        gbc_indexDirLbl.anchor = GridBagConstraints.EAST;
        gbc_indexDirLbl.insets = new Insets(0, 0, 5, 5);
        gbc_indexDirLbl.gridx = 0;
        gbc_indexDirLbl.gridy = 2;
        add(indexDirLbl, gbc_indexDirLbl);

        JPanel indexDirContainer = new JPanel();
        GridBagConstraints gbc_indexDirContainer = new GridBagConstraints();
        gbc_indexDirContainer.fill = GridBagConstraints.BOTH;
        gbc_indexDirContainer.insets = new Insets(0, 0, 5, 0);
        gbc_indexDirContainer.gridx = 1;
        gbc_indexDirContainer.gridy = 2;
        add(indexDirContainer, gbc_indexDirContainer);
        indexDirContainer
                .setLayout(new BoxLayout(indexDirContainer, BoxLayout.X_AXIS));

        JLabel indexDirWarningLbl = new WarningIcon();
        indexDirContainer.add(indexDirWarningLbl);

        indexDirField = new JTextFieldWithPlaceholder();
        indexDirField.setHorizontalAlignment(SwingConstants.TRAILING);
        indexDirContainer.add(indexDirField);
        indexDirField.setColumns(10);

        indexDirBtn = new JButton("_Select...");
        indexDirContainer.add(indexDirBtn);

        // minimum score

        SpinnerNumberModel minScoreModel = new SpinnerNumberModel(0.0, 0.0, 100,
                0.001);
        minScoreLbl = new JLabel("_Minimum score");
        minScoreLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_minScoreLbl = new GridBagConstraints();
        gbc_minScoreLbl.fill = GridBagConstraints.HORIZONTAL;
        gbc_minScoreLbl.insets = new Insets(0, 0, 5, 5);
        gbc_minScoreLbl.gridx = 0;
        gbc_minScoreLbl.gridy = 3;
        add(minScoreLbl, gbc_minScoreLbl);
        minScoreField = new JSpinnerWithBlankValue(minScoreModel, "0.000");

        ((JSpinnerWithBlankValue) minScoreField)
                .setBlankValue(Double.valueOf(0.0));
        GridBagConstraints gbc_minScoreField = new GridBagConstraints();
        gbc_minScoreField.anchor = GridBagConstraints.WEST;
        gbc_minScoreField.insets = new Insets(0, 0, 5, 0);
        gbc_minScoreField.gridx = 1;
        gbc_minScoreField.gridy = 3;
        add(minScoreField, gbc_minScoreField);

        JSpinner.NumberEditor ne_minScoreField = (NumberEditor) minScoreField
                .getEditor();
        ne_minScoreField.setPreferredSize(new Dimension(122, 29));
        ne_minScoreField.setRequestFocusEnabled(false);

        // max rows

        SpinnerNumberModel maxRowsModel = new SpinnerNumberModel(
                Integer.MAX_VALUE, 0, Integer.MAX_VALUE, 1);

        maxRowsLbl = new JLabel("_Only read first rows (A)");
        maxRowsLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        maxRowsLbl.setPreferredSize(new Dimension(150, 17));
        GridBagConstraints gbc_maxRowsLbl = new GridBagConstraints();
        gbc_maxRowsLbl.fill = GridBagConstraints.HORIZONTAL;
        gbc_maxRowsLbl.insets = new Insets(0, 0, 0, 5);
        gbc_maxRowsLbl.gridx = 0;
        gbc_maxRowsLbl.gridy = 5;
        add(maxRowsLbl, gbc_maxRowsLbl);
        maxRowsField = new JSpinnerWithBlankValue(maxRowsModel, "0");
        ((JSpinnerWithBlankValue) maxRowsField)
                .setBlankValue(Integer.MAX_VALUE);
        GridBagConstraints gbc_maxRowsField = new GridBagConstraints();
        gbc_maxRowsField.anchor = GridBagConstraints.WEST;
        gbc_maxRowsField.gridx = 1;
        gbc_maxRowsField.gridy = 5;
        add(maxRowsField, gbc_maxRowsField);

        // num threads

        SpinnerNumberModel threadsModel = new SpinnerNumberModel(0, 0, 256, 1);

        threadsLbl = new JLabel("_Number of threads");
        threadsLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_threadsLbl = new GridBagConstraints();
        gbc_threadsLbl.anchor = GridBagConstraints.EAST;
        gbc_threadsLbl.insets = new Insets(0, 0, 5, 5);
        gbc_threadsLbl.gridx = 0;
        gbc_threadsLbl.gridy = 4;
        add(threadsLbl, gbc_threadsLbl);

        JPanel threadsPanel = new JPanel();
        threadsPanel.setBorder(null);
        GridBagConstraints gbc_threadsPanel = new GridBagConstraints();
        gbc_threadsPanel.anchor = GridBagConstraints.WEST;
        gbc_threadsPanel.fill = GridBagConstraints.VERTICAL;
        gbc_threadsPanel.insets = new Insets(0, 0, 5, 0);
        gbc_threadsPanel.gridx = 1;
        gbc_threadsPanel.gridy = 4;
        add(threadsPanel, gbc_threadsPanel);
        threadsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));

        threadsField = new JSpinnerWithBlankValue(threadsModel, "0");
        threadsPanel.add(threadsField);
        threadsField.setBlankValue(Integer.valueOf(0));

        JSpinner.NumberEditor ne_threadsField = (NumberEditor) threadsField
                .getEditor();
        ne_threadsField.setPreferredSize(new Dimension(122, 29));
        ne_threadsField.setRequestFocusEnabled(false);

        Component horizontalStrut = Box.createHorizontalStrut(20);
        threadsPanel.add(horizontalStrut);

        coresLabel = new JLabel(
                "_On this computer, this number should ideally be at most n.");
        threadsPanel.add(coresLabel);

    }

    public void updateLocalisedStrings() {
        indexDirLbl
                .setText(MessageProvider.getMessage("options.indexlocation"));
        indexDirBtn.setText(
                MessageProvider.getMessage("options.indexlocation.select"));
        linkageDirLbl
                .setText(MessageProvider.getMessage("options.linkagelocation"));
        linkageDirBtn.setText(
                MessageProvider.getMessage("options.linkagelocation.select"));
        maxRowsLbl.setText(MessageProvider.getMessage("options.maxrows"));
        minScoreLbl.setText(MessageProvider.getMessage("options.minscore"));
        threadsLbl.setText(MessageProvider.getMessage("options.threads"));
        coresLabel.setText(MessageFormat.format(
                MessageProvider.getMessage("options.threads.cores"),
                Runtime.getRuntime().availableProcessors()));
    }

    public JButton getLinkageDirBtn() {
        return linkageDirBtn;
    }

    public JButton getIndexDirBtn() {
        return indexDirBtn;
    }

    public JLabel getIndexDirLbl() {
        return indexDirLbl;
    }

    public JLabel getMinScoreLbl() {
        return minScoreLbl;
    }

    public JLabel getMaxRowsLbl() {
        return maxRowsLbl;
    }

    public JLabel getLinkageDirLbl() {
        return linkageDirLbl;
    }

    public JLabel getThreadsLbl() {
        return threadsLbl;
    }

    public JLabel getCoresLabel() {
        return coresLabel;
    }

    public JSpinnerWithBlankValue getThreadsField() {
        return threadsField;
    }

    public JTextFieldWithPlaceholder getLinkageDirField() {
        return linkageDirField;
    }

    public JTextFieldWithPlaceholder getIndexDirField() {
        return indexDirField;
    }

    public JSpinner getMinScoreField() {
        return minScoreField;
    }

    public JSpinner getMaxRowsField() {
        return maxRowsField;
    }

}
