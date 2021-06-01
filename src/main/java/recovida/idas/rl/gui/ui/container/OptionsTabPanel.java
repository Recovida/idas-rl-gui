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
import recovida.idas.rl.gui.ui.ErrorIconLabel;
import recovida.idas.rl.gui.ui.Translatable;
import recovida.idas.rl.gui.ui.field.JSpinnerWithBlankValue;
import recovida.idas.rl.gui.ui.field.JTextFieldWithPlaceholder;

/**
 * A panel to let the user edit some linkage options.
 */
public class OptionsTabPanel extends JPanel implements Translatable {

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

    private ErrorIconLabel linkageDirWarningLbl;

    private ErrorIconLabel indexDirWarningLbl;

    // fields

    private JSpinnerWithBlankValue threadsField;

    private JTextFieldWithPlaceholder linkageDirField;

    private JTextFieldWithPlaceholder indexDirField;

    private JSpinnerWithBlankValue minScoreField;

    private JSpinnerWithBlankValue maxRowsField;

    /**
     * Creates an instance of the panel.
     */
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
        GridBagConstraints optionsTabTopMarginGBC = new GridBagConstraints();
        optionsTabTopMarginGBC.insets = new Insets(0, 0, 5, 5);
        optionsTabTopMarginGBC.gridx = 0;
        optionsTabTopMarginGBC.gridy = 0;
        add(optionsTabTopMargin, optionsTabTopMarginGBC);

        // linkage directory

        linkageDirLbl = new JLabel("_Linkage location");
        linkageDirLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints linkageDirLblGBC = new GridBagConstraints();
        linkageDirLblGBC.insets = new Insets(0, 0, 5, 5);
        linkageDirLblGBC.anchor = GridBagConstraints.EAST;
        linkageDirLblGBC.gridx = 0;
        linkageDirLblGBC.gridy = 1;
        add(linkageDirLbl, linkageDirLblGBC);

        JPanel linkageDirContainer = new JPanel();
        GridBagConstraints linkageDirContainerGBC = new GridBagConstraints();
        linkageDirContainerGBC.fill = GridBagConstraints.BOTH;
        linkageDirContainerGBC.insets = new Insets(0, 0, 5, 0);
        linkageDirContainerGBC.gridx = 1;
        linkageDirContainerGBC.gridy = 1;
        add(linkageDirContainer, linkageDirContainerGBC);
        linkageDirContainer.setLayout(
                new BoxLayout(linkageDirContainer, BoxLayout.X_AXIS));

        linkageDirWarningLbl = new ErrorIconLabel();
        getLinkageDirWarningLbl().setVisible(false);
        linkageDirContainer.add(getLinkageDirWarningLbl());

        linkageDirField = new JTextFieldWithPlaceholder();
        linkageDirContainer.add(linkageDirField);
        linkageDirField.setColumns(10);

        linkageDirBtn = new JButton("_Select...");
        linkageDirContainer.add(linkageDirBtn);

        // index dir

        indexDirLbl = new JLabel("_Index location");
        indexDirLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints indexDirLblGBC = new GridBagConstraints();
        indexDirLblGBC.anchor = GridBagConstraints.EAST;
        indexDirLblGBC.insets = new Insets(0, 0, 5, 5);
        indexDirLblGBC.gridx = 0;
        indexDirLblGBC.gridy = 2;
        add(indexDirLbl, indexDirLblGBC);

        JPanel indexDirContainer = new JPanel();
        GridBagConstraints indexDirContainerGBC = new GridBagConstraints();
        indexDirContainerGBC.fill = GridBagConstraints.BOTH;
        indexDirContainerGBC.insets = new Insets(0, 0, 5, 0);
        indexDirContainerGBC.gridx = 1;
        indexDirContainerGBC.gridy = 2;
        add(indexDirContainer, indexDirContainerGBC);
        indexDirContainer
                .setLayout(new BoxLayout(indexDirContainer, BoxLayout.X_AXIS));

        indexDirWarningLbl = new ErrorIconLabel();
        indexDirContainer.add(getIndexDirWarningLbl());
        getIndexDirWarningLbl().setVisible(false);

        indexDirField = new JTextFieldWithPlaceholder();
        indexDirContainer.add(indexDirField);
        indexDirField.setColumns(10);

        indexDirBtn = new JButton("_Select...");
        indexDirContainer.add(indexDirBtn);

        // minimum score

        SpinnerNumberModel minScoreModel = new SpinnerNumberModel(0.0, 0.0, 100,
                0.001);
        minScoreLbl = new JLabel("_Minimum score");
        minScoreLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints minScoreLblGBC = new GridBagConstraints();
        minScoreLblGBC.fill = GridBagConstraints.HORIZONTAL;
        minScoreLblGBC.insets = new Insets(0, 0, 5, 5);
        minScoreLblGBC.gridx = 0;
        minScoreLblGBC.gridy = 3;
        add(minScoreLbl, minScoreLblGBC);
        minScoreField = new JSpinnerWithBlankValue(minScoreModel, "0.000");

        minScoreField.setBlankValue(Double.valueOf(0.0));
        GridBagConstraints minScoreFieldGBC = new GridBagConstraints();
        minScoreFieldGBC.anchor = GridBagConstraints.WEST;
        minScoreFieldGBC.insets = new Insets(0, 0, 5, 0);
        minScoreFieldGBC.gridx = 1;
        minScoreFieldGBC.gridy = 3;
        add(minScoreField, minScoreFieldGBC);

        JSpinner.NumberEditor minScoreFieldNE = (NumberEditor) minScoreField
                .getEditor();
        minScoreFieldNE.setPreferredSize(new Dimension(122, 29));
        minScoreFieldNE.setRequestFocusEnabled(false);

        // max rows

        SpinnerNumberModel maxRowsModel = new SpinnerNumberModel(
                Integer.MAX_VALUE, 0, Integer.MAX_VALUE, 1);

        maxRowsLbl = new JLabel("_Only read first rows (A)");
        maxRowsLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        maxRowsLbl.setPreferredSize(new Dimension(150, 17));
        GridBagConstraints maxRowsLblGBC = new GridBagConstraints();
        maxRowsLblGBC.fill = GridBagConstraints.HORIZONTAL;
        maxRowsLblGBC.insets = new Insets(0, 0, 0, 5);
        maxRowsLblGBC.gridx = 0;
        maxRowsLblGBC.gridy = 5;
        add(maxRowsLbl, maxRowsLblGBC);
        maxRowsField = new JSpinnerWithBlankValue(maxRowsModel, "0");
        maxRowsField.setBlankValue(Integer.MAX_VALUE);
        GridBagConstraints maxRowsFieldGBC = new GridBagConstraints();
        maxRowsFieldGBC.anchor = GridBagConstraints.WEST;
        maxRowsFieldGBC.gridx = 1;
        maxRowsFieldGBC.gridy = 5;
        add(maxRowsField, maxRowsFieldGBC);

        // num threads

        SpinnerNumberModel threadsModel = new SpinnerNumberModel(0, 0, 256, 1);

        threadsLbl = new JLabel("_Number of threads");
        threadsLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints threadsLblGBC = new GridBagConstraints();
        threadsLblGBC.anchor = GridBagConstraints.EAST;
        threadsLblGBC.insets = new Insets(0, 0, 5, 5);
        threadsLblGBC.gridx = 0;
        threadsLblGBC.gridy = 4;
        add(threadsLbl, threadsLblGBC);

        JPanel threadsPanel = new JPanel();
        threadsPanel.setBorder(null);
        GridBagConstraints threadsPanelGBC = new GridBagConstraints();
        threadsPanelGBC.anchor = GridBagConstraints.WEST;
        threadsPanelGBC.fill = GridBagConstraints.VERTICAL;
        threadsPanelGBC.insets = new Insets(0, 0, 5, 0);
        threadsPanelGBC.gridx = 1;
        threadsPanelGBC.gridy = 4;
        add(threadsPanel, threadsPanelGBC);
        threadsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));

        threadsField = new JSpinnerWithBlankValue(threadsModel, "0");
        threadsField.setBlankValue(Integer.valueOf(0));
        threadsPanel.add(threadsField);

        JSpinner.NumberEditor threadsFieldEditor = (NumberEditor) threadsField
                .getEditor();
        threadsFieldEditor.setPreferredSize(new Dimension(122, 29));
        threadsFieldEditor.setRequestFocusEnabled(false);

        Component horizontalStrut = Box.createHorizontalStrut(20);
        threadsPanel.add(horizontalStrut);

        coresLabel = new JLabel(
                "_On this computer, this number should ideally be at most n.");
        threadsPanel.add(coresLabel);

    }

    @Override
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
        minScoreField.updateLocalisedStrings();
        maxRowsField.updateLocalisedStrings();
        threadsField.updateLocalisedStrings();
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

    public ErrorIconLabel getLinkageDirWarningLbl() {
        return linkageDirWarningLbl;
    }

    public ErrorIconLabel getIndexDirWarningLbl() {
        return indexDirWarningLbl;
    }

}
