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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.NumberEditor;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import recovida.idas.rl.core.io.Separator;
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

    private final JButton linkageDirBtn;

    private final JButton indexDirBtn;

    // labels

    private final JLabel indexDirLbl;

    private final JLabel minScoreLbl;

    private final JLabel maxRowsLbl;

    private final JLabel linkageDirLbl;

    private final JLabel threadsLbl;

    private final JLabel coresLabel;

    private JLabel cleaningRegexLbl;

    private JLabel colSepLbl;

    private JLabel decSepLbl;

    private final ErrorIconLabel linkageDirWarningLbl;

    private final ErrorIconLabel indexDirWarningLbl;

    private ErrorIconLabel cleaningRegexWarningLbl;

    private ErrorIconLabel decSepWarningLbl;

    // fields

    private final JSpinnerWithBlankValue threadsField;

    private final JTextFieldWithPlaceholder linkageDirField;

    private final JTextFieldWithPlaceholder indexDirField;

    private final JSpinnerWithBlankValue minScoreField;

    private final JSpinnerWithBlankValue maxRowsField;

    private JTextField cleaningRegexField;

    private JComboBox<Separator> colSepField;

    private JComboBox<Separator> decSepField;

    /**
     * Creates an instance of the panel.
     */
    public OptionsTabPanel() {

        // layout

        GridBagLayout gbl = new GridBagLayout();
        gbl.columnWidths = new int[] { 300, 100 };
        gbl.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        gbl.columnWeights = new double[] { 0.0, 1.0 };
        gbl.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 1.0 };
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
        linkageDirLblGBC.fill = GridBagConstraints.HORIZONTAL;
        linkageDirLblGBC.insets = new Insets(0, 0, 5, 5);
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
        indexDirLblGBC.fill = GridBagConstraints.HORIZONTAL;
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
        maxRowsLblGBC.insets = new Insets(0, 0, 5, 5);
        maxRowsLblGBC.gridx = 0;
        maxRowsLblGBC.gridy = 5;
        add(maxRowsLbl, maxRowsLblGBC);

        JPanel maxRowsPanel = new JPanel();
        GridBagConstraints maxRowsPanelGBC = new GridBagConstraints();
        maxRowsPanelGBC.fill = GridBagConstraints.BOTH;
        maxRowsPanelGBC.insets = new Insets(0, 0, 5, 0);
        maxRowsPanelGBC.gridx = 1;
        maxRowsPanelGBC.gridy = 5;
        add(maxRowsPanel, maxRowsPanelGBC);
        maxRowsPanel.setLayout(new BoxLayout(maxRowsPanel, BoxLayout.X_AXIS));
        maxRowsField = new JSpinnerWithBlankValue(maxRowsModel, "0");
        maxRowsPanel.add(maxRowsField);
        maxRowsField.setBlankValue(Integer.MAX_VALUE);

        Component maxRowsHorGlue = Box.createHorizontalGlue();
        maxRowsHorGlue.setPreferredSize(new Dimension(950, 0));
        maxRowsPanel.add(maxRowsHorGlue);

        // num threads

        SpinnerNumberModel threadsModel = new SpinnerNumberModel(0, 0, 256, 1);

        threadsLbl = new JLabel("_Number of threads");
        threadsLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints threadsLblGBC = new GridBagConstraints();
        threadsLblGBC.fill = GridBagConstraints.HORIZONTAL;
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

        // cleaning regex

        cleaningRegexLbl = new JLabel("_Cleaning regex");
        cleaningRegexLbl.setPreferredSize(new Dimension(150, 17));
        cleaningRegexLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints cleaningRegexLblGBC = new GridBagConstraints();
        cleaningRegexLblGBC.fill = GridBagConstraints.HORIZONTAL;
        cleaningRegexLblGBC.insets = new Insets(0, 0, 5, 5);
        cleaningRegexLblGBC.gridx = 0;
        cleaningRegexLblGBC.gridy = 6;
        add(cleaningRegexLbl, cleaningRegexLblGBC);

        JPanel cleaningRegexContainer;
        cleaningRegexContainer = new JPanel();
        GridBagConstraints cleaningRegexContainerGBC = new GridBagConstraints();
        cleaningRegexContainerGBC.insets = new Insets(0, 0, 5, 0);
        cleaningRegexContainerGBC.fill = GridBagConstraints.BOTH;
        cleaningRegexContainerGBC.gridx = 1;
        cleaningRegexContainerGBC.gridy = 6;
        add(cleaningRegexContainer, cleaningRegexContainerGBC);
        cleaningRegexContainer.setLayout(
                new BoxLayout(cleaningRegexContainer, BoxLayout.X_AXIS));

        cleaningRegexWarningLbl = new ErrorIconLabel();
        getCleaningRegexWarningLbl().setVisible(false);
        cleaningRegexContainer.add(getCleaningRegexWarningLbl());

        cleaningRegexField = new JTextField();
        cleaningRegexContainer.add(cleaningRegexField);
        cleaningRegexField.setColumns(10);

        // column separator

        colSepLbl = new JLabel("_Column separator (output)");
        colSepLbl.setPreferredSize(new Dimension(150, 17));
        colSepLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints colSepLblGBC = new GridBagConstraints();
        colSepLblGBC.fill = GridBagConstraints.HORIZONTAL;
        colSepLblGBC.insets = new Insets(0, 0, 5, 5);
        colSepLblGBC.gridx = 0;
        colSepLblGBC.gridy = 7;
        add(colSepLbl, colSepLblGBC);

        JPanel colSepPanel = new JPanel();
        GridBagConstraints colSepPanelGBC = new GridBagConstraints();
        colSepPanelGBC.insets = new Insets(0, 0, 5, 0);
        colSepPanelGBC.fill = GridBagConstraints.BOTH;
        colSepPanelGBC.gridx = 1;
        colSepPanelGBC.gridy = 7;
        add(colSepPanel, colSepPanelGBC);
        colSepPanel.setLayout(new BoxLayout(colSepPanel, BoxLayout.X_AXIS));

        colSepField = new JComboBox<>();
        colSepField.setMinimumSize(new Dimension(1000, 27));
        colSepField.setMaximumSize(new Dimension(20000, 32767));
        colSepPanel.add(colSepField);

        Component colSepHorGlue = Box.createHorizontalGlue();
        colSepPanel.add(colSepHorGlue);

        // decimal separator

        decSepLbl = new JLabel("_Decimal separator (output)");
        decSepLbl.setPreferredSize(new Dimension(150, 17));
        decSepLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints decSepLblGBC = new GridBagConstraints();
        decSepLblGBC.fill = GridBagConstraints.HORIZONTAL;
        decSepLblGBC.insets = new Insets(0, 0, 5, 5);
        decSepLblGBC.gridx = 0;
        decSepLblGBC.gridy = 8;
        add(decSepLbl, decSepLblGBC);

        JPanel decSepPanel = new JPanel();
        GridBagConstraints decSepPanelGBC = new GridBagConstraints();
        decSepPanelGBC.insets = new Insets(0, 0, 5, 0);
        decSepPanelGBC.fill = GridBagConstraints.BOTH;
        decSepPanelGBC.gridx = 1;
        decSepPanelGBC.gridy = 8;
        add(decSepPanel, decSepPanelGBC);
        decSepPanel.setLayout(new BoxLayout(decSepPanel, BoxLayout.X_AXIS));

        decSepWarningLbl = new ErrorIconLabel();
        decSepPanel.add(getDecSepWarningLbl());

        decSepField = new JComboBox<>();
        decSepField.setMinimumSize(new Dimension(700, 27));
        decSepField.setMaximumSize(new Dimension(23000, 32767));
        decSepPanel.add(decSepField);

        Component decSepHorGlue = Box.createHorizontalGlue();
        decSepPanel.add(decSepHorGlue);

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
        cleaningRegexLbl
                .setText(MessageProvider.getMessage("options.cleaningregex"));
        decSepLbl.setText(MessageProvider.getMessage("options.decsep"));
        colSepLbl.setText(MessageProvider.getMessage("options.colsep"));
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

    public JTextField getCleaningRegexField() {
        return cleaningRegexField;
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

    public ErrorIconLabel getCleaningRegexWarningLbl() {
        return cleaningRegexWarningLbl;
    }

    public JComboBox<Separator> getColSepField() {
        return colSepField;
    }

    public JComboBox<Separator> getDecSepField() {
        return decSepField;
    }

    public ErrorIconLabel getDecSepWarningLbl() {
        return decSepWarningLbl;
    }

}
