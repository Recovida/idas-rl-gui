package recovida.idas.rl.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.filechooser.FileNameExtensionFilter;

import recovida.idas.rl.gui.DatasetPeek.DatasetPeekResult;
import recovida.idas.rl.gui.lang.MessageProvider;
import recovida.idas.rl.gui.listener.ColumnPairInclusionExclusionListener;
import recovida.idas.rl.gui.listener.ColumnPairSelectionListener;
import recovida.idas.rl.gui.listener.ColumnPairValueChangeListener;
import recovida.idas.rl.gui.listener.SettingItemChangeListener;
import recovida.idas.rl.gui.pair.ColumnPairManager;
import recovida.idas.rl.gui.settingitem.AbstractSettingItem;
import recovida.idas.rl.gui.settingitem.NumberSettingItem;
import recovida.idas.rl.gui.settingitem.StringSettingItem;
import recovida.idas.rl.gui.settingitem.StringSettingItemWithList;
import recovida.idas.rl.gui.ui.ErrorIconLabel;
import recovida.idas.rl.gui.ui.Translatable;
import recovida.idas.rl.gui.ui.container.DatasetsTabPanel;
import recovida.idas.rl.gui.ui.container.ExecutionPanel;
import recovida.idas.rl.gui.ui.container.LinkageColumnButtonPanel;
import recovida.idas.rl.gui.ui.container.LinkageColumnEditingPanel;
import recovida.idas.rl.gui.ui.container.MainMenuBar;
import recovida.idas.rl.gui.ui.container.MainToolBar;
import recovida.idas.rl.gui.ui.container.OptionsTabPanel;
import recovida.idas.rl.gui.ui.field.JSpinnerWithBlankValue;
import recovida.idas.rl.gui.ui.table.ColumnPairTable;
import recovida.idas.rl.gui.ui.table.ColumnPairTableModel;
import recovida.idas.rl.gui.ui.window.AboutWindow;
import recovida.idas.rl.gui.undo.HistoryPropertyChangeEventListener;
import recovida.idas.rl.gui.undo.UndoHistory;

/**
 * The main class of the GUI.
 */
public class MainWindow implements Translatable {

    /* Non-GUI attributes */

    /**
     * The name of the program as shown on the title bar.
     */
    public static final String PROGRAM_NAME = "IDaS-RL";

    UndoHistory history = new UndoHistory();

    ConfigurationFile cf = new ConfigurationFile();

    String currentFileName = null;

    boolean dirty = false;

    boolean skipValidation = false; // while filling in values read from file

    FileChangeWatcher currentFileChangeWatcher = null;

    FileChangeWatcher datasetAFileChangeWatcher = null;

    FileChangeWatcher datasetBFileChangeWatcher = null;

    private final ColumnPairManager manager;

    ConcurrentMap<String, ConcurrentMap<String, DatasetPeek>> peekFromFileNameAndEncoding = new ConcurrentHashMap<>();

    Execution execution = null;

    /* GUI components */

    private JFrame frame;

    private JTable linkageColsTable;

    private JTabbedPane tabbedPane;

    private DatasetsTabPanel datasetsTabPanel;

    private OptionsTabPanel optionsTabPanel;

    private LinkageColumnEditingPanel linkageColsEditingPanel;

    private LinkageColumnButtonPanel linkageColsButtonPanel;

    private ColumnPairTableModel columnPairTableModel;

    private JPanel linkageColsTabPanel;

    private final ExecutionPanel executionTabPanel;

    private MainToolBar mainToolBar;

    private MainMenuBar menuBar;

    /**
     * Launches the application. If exactly one argument is provided and it is
     * the name of an existing configuration file, then it is automatically
     * opened.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            String fn = null;
            if (args.length == 1) {
                File f = new File(args[0]);
                if (f.isFile())
                    fn = f.getAbsoluteFile().toString();
            }
            new MainWindow(fn);
        });

    }

    /**
     * Creates and shows the main window, without opening any configuration
     * files.
     *
     * @wbp.parser.entryPoint
     */
    public MainWindow() {
        this(null);
    }

    /**
     * Creates and shows the main window, and also loads a configuration file.
     *
     * @param fileToOpen the name of the file to be loaded
     */
    public MainWindow(String fileToOpen) {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
        }
        init();

        // Tab: DATASETS
        SettingItemChangeListener datasetsTabEventListenerTop = o -> {
            if (!skipValidation)
                tabbedPane.setSelectedComponent(datasetsTabPanel);
            validateDatasetsTabTopPart();
        };
        SettingItemChangeListener datasetsTabEventListenerBottom = o -> {
            if (!skipValidation)
                tabbedPane.setSelectedComponent(datasetsTabPanel);
            validateDatasetsTabBottomPart();
        };

        cf.addSettingItem("db_a",
                new StringSettingItem(history, "", "",
                        datasetsTabPanel.getFirstDatasetField(),
                        datasetsTabEventListenerTop));
        cf.addSettingItem("encoding_a",
                new StringSettingItemWithList(history, "", "UTF-8",
                        datasetsTabPanel.getFirstEncodingField(),
                        datasetsTabEventListenerTop));
        cf.addSettingItem("suffix_a",
                new StringSettingItem(history, "", "_dsa",
                        datasetsTabPanel.getFirstDatasetSuffixField(),
                        datasetsTabEventListenerBottom));
        cf.addSettingItem("row_num_col_a",
                new StringSettingItem(history, "", "#A",
                        datasetsTabPanel.getFirstDatasetRowNumColField(),
                        datasetsTabEventListenerBottom));

        cf.addSettingItem("db_b",
                new StringSettingItem(history, "", "",
                        datasetsTabPanel.getSecondDatasetField(),
                        datasetsTabEventListenerTop));
        cf.addSettingItem("encoding_b",
                new StringSettingItemWithList(history, "", "UTF-8",
                        datasetsTabPanel.getSecondEncodingField(),
                        datasetsTabEventListenerTop));
        cf.addSettingItem("suffix_b",
                new StringSettingItem(history, "", "_dsb",
                        datasetsTabPanel.getSecondDatasetSuffixField(),
                        datasetsTabEventListenerBottom));
        cf.addSettingItem("row_num_col_b",
                new StringSettingItem(history, "", "#B",
                        datasetsTabPanel.getSecondDatasetRowNumColField(),
                        datasetsTabEventListenerBottom));

        // Tab: OPTIONS
        SettingItemChangeListener optionsTabEventListener = o -> {
            if (!skipValidation)
                tabbedPane.setSelectedComponent(optionsTabPanel);
            validateOptionsTab();
        };
        cf.addSettingItem("db_index", new StringSettingItem(history, "", "",
                optionsTabPanel.getIndexDirField(), optionsTabEventListener));
        cf.addSettingItem("linkage_folder",
                new StringSettingItem(history, "", "",
                        optionsTabPanel.getLinkageDirField(),
                        optionsTabEventListener));
        cf.addSettingItem("min_score", new NumberSettingItem(history, 0.0, 0.0,
                optionsTabPanel.getMinScoreField(), optionsTabEventListener));
        cf.addSettingItem("num_threads", new NumberSettingItem(history, 0, 0,
                optionsTabPanel.getThreadsField()));
        cf.addSettingItem("max_rows",
                new NumberSettingItem(history, Integer.MAX_VALUE,
                        Integer.MAX_VALUE, optionsTabPanel.getMaxRowsField(),
                        optionsTabEventListener));

        // Tab: COLUMNS
        ColumnPairValueChangeListener linkageColsTabAddDelEventListener = (
                int index, String key, Object newValue) -> {
            if (!skipValidation)
                tabbedPane.setSelectedComponent(linkageColsTabPanel);
            validateLinkageColsTab();
        };
        ColumnPairInclusionExclusionListener linkageColsTabValueChangeEventListener = new ColumnPairInclusionExclusionListener() {

            @Override
            public void insertedColumnPair(int index, Object[] columnPairData) {
                validateLinkageColsTab();
            }

            @Override
            public void deletedColumnPair(int index, Object[] columnPairData) {
                validateLinkageColsTab();
            }
        };
        ColumnPairSelectionListener linkageColsTabSelChangeEventListener = (
                int index) -> {
            validateLinkageColsTabSelectedRow(index);
        };
        manager = new ColumnPairManager(history, linkageColsButtonPanel,
                linkageColsEditingPanel, linkageColsTable);

        // Tab: EXECUTION
        executionTabPanel = new ExecutionPanel();
        tabbedPane.addTab("_Execution", null, executionTabPanel, null);
        manager.addInclusionExclusionListener(
                linkageColsTabValueChangeEventListener);
        manager.addSelectionListener(linkageColsTabSelChangeEventListener);
        manager.addValueChangeSelectionListener(
                linkageColsTabAddDelEventListener);
        cf.setPairPanager(manager);

        // Menus

        menuBar.getNewFileMenuItem().addActionListener(e -> doNew());
        menuBar.getExitMenuItem().addActionListener(e -> doExit());
        menuBar.getOpenFileMenuItem().addActionListener(e -> doOpen());
        menuBar.getSaveFileMenuItem().addActionListener(e -> doSave());
        menuBar.getSaveAsFileMenuItem().addActionListener(e -> doSaveAs());
        menuBar.getRedoMenuItem().addActionListener(e -> doRedo());
        menuBar.getUndoMenuItem().addActionListener(e -> doUndo());
        menuBar.getAboutMenuItem().addActionListener(e -> doAbout());
        menuBar.getRunMenuItem().addActionListener(e -> doRun());
        menuBar.getCancelMenuItem().addActionListener(e -> doCancel());

        // Toolbar buttons

        mainToolBar.getNewFileBtn().addActionListener(e -> doNew());
        mainToolBar.getOpenFileBtn().addActionListener(e -> doOpen());
        mainToolBar.getSaveFileBtn().addActionListener(e -> doSave());
        mainToolBar.getRedoBtn().addActionListener(e -> doRedo());
        mainToolBar.getUndoBtn().addActionListener(e -> doUndo());
        mainToolBar.getRunBtn().addActionListener(e -> doRun());
        mainToolBar.getCancelBtn().addActionListener(e -> doCancel());

        // Undo - changed state

        history.addPropertyChangeListener(
                new HistoryPropertyChangeEventListener() {

                    @Override
                    public void cleanChanged(boolean isClean) {
                        dirty = !isClean;
                        updateConfigFileLabel();
                    }

                    @Override
                    public void canUndoChanged(boolean canUndo) {
                        menuBar.getUndoMenuItem().setEnabled(canUndo);
                        mainToolBar.getUndoBtn().setEnabled(canUndo);
                    }

                    @Override
                    public void canRedoChanged(boolean canRedo) {
                        menuBar.getRedoMenuItem().setEnabled(canRedo);
                        mainToolBar.getRedoBtn().setEnabled(canRedo);
                    }

                    @Override
                    public void undoSummaryChanged(String summary) {
                        updateUndoMenuText(summary);
                    }

                    @Override
                    public void redoSummaryChanged(String summary) {
                        updateRedoMenuText(summary);
                    }
                });

        // LANGUAGE

        mainToolBar.getLanguageCBox().addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                MessageProvider.setLocale((Locale) e.getItem());
                recovida.idas.rl.core.lang.MessageProvider
                        .setLocale((Locale) e.getItem());
                updateLocalisedStrings();
            }
        });

        updateConfigFileLabel();
        updateLocalisedStrings();

        if (fileToOpen != null)
            doOpen(fileToOpen);
        else
            doNew();

        // Initial validation
        SwingUtilities.invokeLater(this::validateAllTabs);

        // show frame
        frame.setVisible(true);

    }

    protected Icon getTabErrorIcon() {
        return new ImageIcon(ErrorIconLabel.BUFFERED_IMAGE.getScaledInstance(15,
                15, Image.SCALE_SMOOTH));
    }

    protected synchronized int validateDatasetsTabTopPart() {
        if (skipValidation)
            return -1;

        int errorCount = 0;

        @SuppressWarnings("rawtypes")
        Map<String, AbstractSettingItem> items = cf.getSettingItems();

        // Encodings
        String enc1 = (String) items.get("encoding_a").getCurrentValue();
        String enc2 = (String) items.get("encoding_b").getCurrentValue();
        if (enc1 == null || enc1.isEmpty())
            enc1 = (String) items.get("encoding_a").getDefaultValue();
        if (enc2 == null || enc2.isEmpty())
            enc2 = (String) items.get("encoding_b").getDefaultValue();
        if (!isValidEncoding(enc1)) {
            datasetsTabPanel.getFirstEncodingWarningLbl().setToolTipText(
                    MessageProvider.getMessage("datasets.badencoding"));
            datasetsTabPanel.getFirstEncodingWarningLbl().setVisible(true);
            errorCount++;
        } else {
            datasetsTabPanel.getFirstEncodingWarningLbl().setVisible(false);
        }
        if (!isValidEncoding(enc2)) {
            datasetsTabPanel.getSecondEncodingWarningLbl().setToolTipText(
                    MessageProvider.getMessage("datasets.badencoding"));
            datasetsTabPanel.getSecondEncodingWarningLbl().setVisible(true);
            errorCount++;
        } else {
            datasetsTabPanel.getSecondEncodingWarningLbl().setVisible(false);
        }

        // File names
        Path dir = currentFileName == null ? null
                : Paths.get(currentFileName).getParent();
        String f1 = (String) items.get("db_a").getCurrentValue();
        String f2 = (String) items.get("db_b").getCurrentValue();
        String fn1, fn2;
        if (dir != null) {
            fn1 = dir.resolve(f1).toAbsolutePath().toString();
            fn2 = dir.resolve(f2).toAbsolutePath().toString();
        } else {
            fn1 = f1;
            fn2 = f2;
        }
        DatasetPeek p;
        if (!peekFromFileNameAndEncoding.containsKey(fn1))
            peekFromFileNameAndEncoding.put(fn1,
                    new ConcurrentHashMap<String, DatasetPeek>());
        ConcurrentMap<String, DatasetPeek> m = peekFromFileNameAndEncoding
                .get(fn1);
        if (m.containsKey(enc1)) {
            p = m.get(enc1);
        } else {
            p = new DatasetPeek(dir, fn1, enc1);
            p.peek();
            if (fn1 != null && !fn1.isEmpty()) {
                m.put(enc1, p);
                if (datasetAFileChangeWatcher != null)
                    datasetAFileChangeWatcher.disable();
                datasetAFileChangeWatcher = new FileChangeWatcher(
                        Paths.get(fn1), () -> {
                            peekFromFileNameAndEncoding.remove(fn1);
                            validateDatasetsTabTopPart();
                        }, true);
                datasetAFileChangeWatcher.enable();
            }
        }
        DatasetPeekResult result = p.getResult();
        if (result != DatasetPeekResult.SUCCESS) {
            errorCount++;
            datasetsTabPanel.getFirstDatasetWarningLbl()
                    .setToolTipText(getDatasetHeaderErrorMessage(result, fn1));
            datasetsTabPanel.getFirstDatasetWarningLbl().setVisible(true);
        } else
            datasetsTabPanel.getFirstDatasetWarningLbl().setVisible(false);
        manager.setFirstDatasetColumnNames(p.getColumnNames());
        if (!peekFromFileNameAndEncoding.containsKey(fn2))
            peekFromFileNameAndEncoding.put(fn2,
                    new ConcurrentHashMap<String, DatasetPeek>());
        m = peekFromFileNameAndEncoding.get(fn2);
        if (m.containsKey(enc2)) {
            p = m.get(enc2);
        } else {
            p = new DatasetPeek(dir, fn2, enc2);
            p.peek();
            if (fn2 != null && !fn2.isEmpty()) {
                m.put(enc2, p);
                if (datasetBFileChangeWatcher != null)
                    datasetBFileChangeWatcher.disable();
                datasetBFileChangeWatcher = new FileChangeWatcher(
                        Paths.get(fn2), () -> {
                            peekFromFileNameAndEncoding.remove(fn2);
                            validateDatasetsTabTopPart();
                        }, true);
                datasetBFileChangeWatcher.enable();
            }
        }
        result = p.getResult();
        if (result != DatasetPeekResult.SUCCESS) {
            errorCount++;
            datasetsTabPanel.getSecondDatasetWarningLbl()
                    .setToolTipText(getDatasetHeaderErrorMessage(result, fn2));
            datasetsTabPanel.getSecondDatasetWarningLbl().setVisible(true);
        } else
            datasetsTabPanel.getSecondDatasetWarningLbl().setVisible(false);
        manager.setSecondDatasetColumnNames(p.getColumnNames());
        if (manager.getColumnPairCount() > 0)
            manager.getTableModel().fireTableRowsUpdated(0,
                    manager.getColumnPairCount() - 1);
        validateLinkageColsTab();
        tabbedPane.setIconAt(tabbedPane.indexOfComponent(datasetsTabPanel),
                errorCount == 0 ? null : getTabErrorIcon());
        return errorCount;
    }

    protected synchronized int validateDatasetsTabBottomPart() {
        if (skipValidation)
            return -1;

        int errorCount = 0;
        @SuppressWarnings("rawtypes")
        Map<String, AbstractSettingItem> items = cf.getSettingItems();
        String s1, s2;

        // Suffixes
        s1 = (String) items.get("suffix_a").getCurrentValue();
        s2 = (String) items.get("suffix_b").getCurrentValue();
        if (s1 == null || s1.isEmpty())
            s1 = (String) items.get("suffix_a").getDefaultValue();
        if (s2 == null || s2.isEmpty())
            s2 = (String) items.get("suffix_b").getDefaultValue();
        if (s1.equals(s2)) {
            String msg = MessageProvider
                    .getMessage("datasets.identicalprefixes");
            datasetsTabPanel.getFirstDatasetSuffixWarningLbl()
                    .setToolTipText(msg);
            datasetsTabPanel.getSecondDatasetSuffixWarningLbl()
                    .setToolTipText(msg);
            datasetsTabPanel.getFirstDatasetSuffixWarningLbl().setVisible(true);
            datasetsTabPanel.getSecondDatasetSuffixWarningLbl()
                    .setVisible(true);
            errorCount++;
        } else {
            datasetsTabPanel.getFirstDatasetSuffixWarningLbl()
                    .setVisible(false);
            datasetsTabPanel.getSecondDatasetSuffixWarningLbl()
                    .setVisible(false);
        }
        manager.setFirstRenameSuffix(s1);
        manager.setSecondRenameSuffix(s2);

        // Row number column names
        s1 = (String) items.get("row_num_col_a").getCurrentValue();
        s2 = (String) items.get("row_num_col_b").getCurrentValue();
        if (s1 == null || s1.isEmpty())
            s1 = (String) items.get("row_num_col_a").getDefaultValue();
        if (s2 == null || s2.isEmpty())
            s2 = (String) items.get("row_num_col_b").getDefaultValue();
        if (s1.equals(s2)) {
            String msg = MessageProvider
                    .getMessage("datasets.identicalrownumcolnames");
            datasetsTabPanel.getFirstDatasetRowNumColWarningLbl()
                    .setToolTipText(msg);
            datasetsTabPanel.getSecondDatasetRowNumColWarningLbl()
                    .setToolTipText(msg);
            datasetsTabPanel.getFirstDatasetRowNumColWarningLbl()
                    .setVisible(true);
            datasetsTabPanel.getSecondDatasetRowNumColWarningLbl()
                    .setVisible(true);
            errorCount++;
        } else {
            datasetsTabPanel.getFirstDatasetRowNumColWarningLbl()
                    .setVisible(false);
            datasetsTabPanel.getSecondDatasetRowNumColWarningLbl()
                    .setVisible(false);
        }
        return errorCount;
    }

    protected synchronized int validateOptionsTab() {
        if (skipValidation)
            return -1;
        int errorCount = 0;
        String linkageDir = (String) cf.getSettingItems().get("linkage_folder")
                .getCurrentValue();
        if (linkageDir == null || linkageDir.isEmpty()) {
            optionsTabPanel.getLinkageDirWarningLbl().setVisible(true);
            errorCount++;
        } else
            optionsTabPanel.getLinkageDirWarningLbl().setVisible(false);
        String indexDir = (String) cf.getSettingItems().get("db_index")
                .getCurrentValue();
        if (indexDir == null || indexDir.isEmpty()) {
            optionsTabPanel.getIndexDirWarningLbl().setVisible(true);
            errorCount++;
        } else
            optionsTabPanel.getIndexDirWarningLbl().setVisible(false);
        tabbedPane.setIconAt(tabbedPane.indexOfComponent(optionsTabPanel),
                errorCount == 0 ? null : getTabErrorIcon());
        return errorCount;
    }

    protected synchronized int validateLinkageColsTabSelectedRow(int rowIndex,
            String key) {
        if (skipValidation)
            return -1;
        boolean error = rowIndex >= 0
                && !columnPairTableModel.isCellValid(rowIndex, key);
        JLabel lbl;
        switch (key) {
        case "index_a":
            lbl = linkageColsEditingPanel.getFirstNameWarningLbl();
            lbl.setVisible(error);
            lbl.setToolTipText(
                    error ? MessageProvider.getMessage("columns.badcolumn")
                            : "");
            break;
        case "index_b":
            lbl = linkageColsEditingPanel.getSecondNameWarningLbl();
            lbl.setVisible(error);
            lbl.setToolTipText(
                    error ? MessageProvider.getMessage("columns.badcolumn")
                            : "");
            break;
        case "type":
            lbl = linkageColsEditingPanel.getTypeWarningLbl();
            lbl.setVisible(error);
            lbl.setToolTipText(
                    error ? MessageProvider.getMessage("columns.badtype") : "");
            break;
        case "weight":
        case "phon_weight":
        case "number":
        case "rename_a":
        case "rename_b":
        default:
            break;
        }
        return error ? 1 : 0;
    }

    protected synchronized int validateLinkageColsTabSelectedRow(int index) {
        if (skipValidation)
            return -1;
        int errorCount = 0;
        String[] keys = { "number", "index_a", "index_b", "type", "weight",
                "phon_weight", "rename_a", "rename_b" };
        for (String key : keys)
            errorCount += validateLinkageColsTabSelectedRow(index, key);
        return errorCount;
    }

    protected synchronized int validateLinkageColsTab() {
        if (skipValidation)
            return -1;
        int index = linkageColsTable.getSelectedRow();
        if (index >= 0)
            index = linkageColsTable.convertRowIndexToModel(index);
        validateLinkageColsTabSelectedRow(index);
        int errorCount = columnPairTableModel.isValid() ? 0 : 1;
        tabbedPane.setIconAt(tabbedPane.indexOfComponent(linkageColsTabPanel),
                errorCount == 0 ? null : getTabErrorIcon());
        return errorCount;
    }

    protected int validateAllTabs() {
        return validateDatasetsTabTopPart() + validateDatasetsTabBottomPart()
                + validateOptionsTab() + validateLinkageColsTab();
    }

    protected static String getDatasetHeaderErrorMessage(
            DatasetPeekResult result, String fileName) {
        switch (result) {
        case BLANK_NAME:
            return MessageProvider.getMessage("datasets.blankfilename");
        case FILE_NOT_FOUND:
            return MessageProvider.getMessage("datasets.badfile");
        case IO_ERROR:
            return MessageProvider.getMessage("datasets.unreadablefile");
        case UNSUPPORTED_CONTENTS:
            return MessageProvider.getMessage("datasets.badcontents");
        case UNSUPPORTED_FORMAT:
            return MessageProvider.getMessage("datasets.badformat");
        default:
            return null;
        }
    }

    /**
     * Clears every field on each tab, including the linkage column table.
     */
    @SuppressWarnings("unchecked")
    public void clearAllFields() {
        for (AbstractSettingItem<?, ?> item : cf.getSettingItems().values()) {
            JComponent component = item.getGuiComponent();
            if (component instanceof JTextField)
                ((JTextField) component).setText("");
            else if (component instanceof JComboBox<?>
                    && ((JComboBox<String>) component).isEditable())
                ((JComboBox<String>) item.getGuiComponent())
                        .setSelectedItem("");
            else if (component instanceof JSpinnerWithBlankValue)
                ((JSpinnerWithBlankValue) component).setValue(
                        ((JSpinnerWithBlankValue) component).getBlankValue());
        }
        columnPairTableModel.setRowCount(0);
    }

    protected static boolean isValidEncoding(String enc) {
        if (enc == null)
            return false;
        try {
            return enc.toUpperCase().replaceAll("[^A-Z0-9]", "").equals("ANSI")
                    || Charset.isSupported(enc);
        } catch (IllegalCharsetNameException e) {
            return false;
        }
    }

    protected void doUndo() {
        if (history.canUndo())
            history.undo();
    }

    protected void doRedo() {
        if (history.canRedo())
            history.redo();
    }

    protected synchronized void doNew() {
        if (dirty) {
            int ans = promptToSaveChanges();
            if (ans == JOptionPane.YES_OPTION)
                doSave();
            else if (ans == JOptionPane.CANCEL_OPTION
                    || ans == JOptionPane.CLOSED_OPTION)
                return;
        }
        skipValidation = true;
        clearAllFields();
        executionTabPanel.clear();
        updateConfigFileName(null);
        // auto-fill index dir and linkage dir
        optionsTabPanel.getIndexDirField().setText("index_dir");
        optionsTabPanel.getLinkageDirField().setText("linkage_dir");
        SwingUtilities.invokeLater(() -> {
            skipValidation = false;
            validateAllTabs();
        });
        history.clearAll();
        manager.reset();
    }

    protected synchronized void doOpen() {
        if (dirty) {
            int ans = promptToSaveChanges();
            if (ans == JOptionPane.YES_OPTION)
                doSave();
            else if (ans == JOptionPane.CANCEL_OPTION
                    || ans == JOptionPane.CLOSED_OPTION)
                return;
        }
        String newConfigFileName = selectConfigFile(currentFileName == null
                ? null
                : Paths.get(currentFileName).toAbsolutePath().toString());
        if (newConfigFileName != null) {
            doOpen(newConfigFileName);
        }
    }

    protected void doOpen(String newConfigFileName) {
        skipValidation = true;
        clearAllFields();
        manager.reset();
        executionTabPanel.clear();
        if (cf.load(newConfigFileName)) {
            updateConfigFileName(newConfigFileName);
            history.clearAll();
            linkageColsTable.clearSelection();
        } else {
            JOptionPane.showMessageDialog(frame,
                    MessageProvider.getMessage("menu.file.open.cantopen"),
                    MessageProvider.getMessage("menu.file.open.error"),
                    JOptionPane.ERROR_MESSAGE);
        }
        SwingUtilities.invokeLater(() -> {
            skipValidation = false;
            validateAllTabs();
        });
    }

    protected synchronized void doSave() {
        if (columnPairTableModel.hasDuplicateNumbers()) {
            showDuplicateNumbersWarning();
            return;
        }
        if (currentFileName == null) {
            doSaveAs();
            return;
        }
        if (currentFileChangeWatcher != null)
            currentFileChangeWatcher.disable();
        if (cf.save(currentFileName)) {
            boolean wasDirty = dirty;
            updateConfigFileName(currentFileName);
            history.setClean();
            if (validateAllTabs() > 0 && wasDirty)
                JOptionPane.showMessageDialog(frame,
                        MessageProvider
                                .getMessage("menu.file.save.containserrors"),
                        MessageProvider.getMessage("menu.file.save.warning"),
                        JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame,
                    MessageProvider.getMessage("menu.file.save.cantsave"),
                    MessageProvider.getMessage("menu.file.save.error"),
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    protected synchronized void doSaveAs() {
        if (columnPairTableModel.hasDuplicateNumbers()) {
            showDuplicateNumbersWarning();
            return;
        }
        String fn = selectConfigFile(currentFileName, true);
        if (fn != null) {
            if (currentFileName != null)
                dirty = true;
            currentFileName = fn;
            doSave();
        }
    }

    protected void showDuplicateNumbersWarning() {
        JOptionPane.showMessageDialog(frame,
                MessageProvider.getMessage("menu.file.save.duplicatenumbers"),
                MessageProvider.getMessage("menu.file.save.error"),
                JOptionPane.ERROR_MESSAGE);
    }

    protected void doExit() {
        if (dirty) {
            int ans = promptToSaveChanges();
            if (ans == JOptionPane.YES_OPTION)
                doSave();
            else if (ans == JOptionPane.CANCEL_OPTION
                    || ans == JOptionPane.CLOSED_OPTION)
                return;
        }
        Arrays.stream(new FileChangeWatcher[] { datasetAFileChangeWatcher,
                datasetBFileChangeWatcher, currentFileChangeWatcher })
                .filter(w -> w != null).forEach(FileChangeWatcher::disable);
        frame.dispose();
    }

    protected void doRun() {
        mainToolBar.getRunBtn().setEnabled(false);
        menuBar.getRunMenuItem().setEnabled(false);
        mainToolBar.getCancelBtn().setEnabled(true);
        menuBar.getCancelMenuItem().setEnabled(true);
        if (dirty)
            doSave();
        execution = new Execution(executionTabPanel.addExecutionPanel(),
                currentFileName);
        tabbedPane.setSelectedComponent(executionTabPanel);
        CompletableFuture<Boolean> f = execution.start();
        f.thenAccept((Boolean success) -> {
            SwingUtilities.invokeLater(() -> {
                mainToolBar.getRunBtn().setEnabled(true);
                menuBar.getRunMenuItem().setEnabled(true);
                mainToolBar.getCancelBtn().setEnabled(false);
                menuBar.getCancelMenuItem().setEnabled(false);
                f.join();
            });
        });
    }

    protected void doCancel() {
        mainToolBar.getCancelBtn().setEnabled(false);
        menuBar.getCancelMenuItem().setEnabled(false);
        if (execution != null) {
            execution.cancel();
        }
        execution = null;
    }

    protected void doAbout() {
        new AboutWindow(frame).setVisible(true);
    }

    protected synchronized void updateConfigFileName(String fn) {
        if (currentFileChangeWatcher != null)
            currentFileChangeWatcher.disable();
        if (fn != null) {
            if (!fn.equals(currentFileName) || currentFileChangeWatcher == null)
                currentFileChangeWatcher = new FileChangeWatcher(Paths.get(fn),
                        () -> {
                            int result = promptToSaveChangesAfterChangeOnDisk();
                            if (result == JOptionPane.YES_OPTION)
                                doSave();
                            else if (result == JOptionPane.NO_OPTION)
                                doOpen(currentFileName);
                        });
            currentFileChangeWatcher.enable();
        } else
            currentFileChangeWatcher = null;
        currentFileName = fn;
        dirty = false;
        menuBar.getRunMenuItem().setEnabled(fn != null);
        mainToolBar.getRunBtn().setEnabled(fn != null);
        updateConfigFileLabel();
    }

    protected int promptToSaveChanges() {
        String msg = currentFileName == null
                ? MessageProvider.getMessage("menu.file.save.savechangesnofile")
                : MessageFormat.format(
                        MessageProvider
                                .getMessage("menu.file.save.savechangesfile"),
                        currentFileName);
        return JOptionPane.showOptionDialog(frame, msg,
                MessageProvider.getMessage("menu.file.save.savechanges"),
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, null, null);
    }

    protected int promptToSaveChangesAfterChangeOnDisk() {
        String[] opt = new String[] {
                MessageProvider.getMessage("menu.file.save."
                        + (dirty ? "saveandoverwrite" : "overwrite")),
                MessageProvider.getMessage("menu.file.save."
                        + (dirty ? "discardandreload" : "reload")) };
        String msg = MessageFormat.format(
                MessageProvider.getMessage("menu.file.save.changedondisk"),
                currentFileName == null ? ""
                        : Paths.get(currentFileName).getFileName());
        String title = MessageProvider
                .getMessage("menu.file.save.changedexternally");
        int result;
        do {
            result = JOptionPane.showOptionDialog(frame, msg, title,
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                    null, opt, null);
        } while (result == JOptionPane.CLOSED_OPTION);
        return result;
    }

    /**
     * Initialise the contents of the frame.
     */
    private void init() {
        frame = new JFrame();
        frame.setPreferredSize(new Dimension(900, 500));
        frame.setMinimumSize(new Dimension(900, 400));
        frame.setBounds(100, 100, 799, 481);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                doExit();
            }
        });
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        datasetsTabPanel = new DatasetsTabPanel();
        tabbedPane.addTab("_Datasets", null, datasetsTabPanel, null);

        datasetsTabPanel.getFirstDatasetBtn().addActionListener(e -> {
            String current = datasetsTabPanel.getFirstDatasetField().getText();
            String result = selectDatasetFile(
                    current.isEmpty() ? null : current);
            if (result != null)
                datasetsTabPanel.getFirstDatasetField().setText(result);
        });
        datasetsTabPanel.getSecondDatasetBtn().addActionListener(e -> {
            String current = datasetsTabPanel.getSecondDatasetField().getText();
            String result = selectDatasetFile(
                    current.isEmpty() ? null : current);
            if (result != null)
                datasetsTabPanel.getSecondDatasetField().setText(result);
        });

        optionsTabPanel = new OptionsTabPanel();
        tabbedPane.addTab("_Options", null, optionsTabPanel, null);

        optionsTabPanel.getLinkageDirBtn().addActionListener(e -> {
            String current = optionsTabPanel.getLinkageDirField().getText();
            String result = selectDir(current.isEmpty() ? null : current);
            if (result != null)
                optionsTabPanel.getLinkageDirField().setText(result);
        });
        optionsTabPanel.getIndexDirBtn().addActionListener(e -> {
            String current = optionsTabPanel.getIndexDirField().getText();
            String result = selectDir(current.isEmpty() ? null : current);
            if (result != null)
                optionsTabPanel.getIndexDirField().setText(result);
        });

        linkageColsTabPanel = new JPanel();
        tabbedPane.addTab("_Columns", null, linkageColsTabPanel, null);

        columnPairTableModel = new ColumnPairTableModel();
        linkageColsTable = new ColumnPairTable(columnPairTableModel);
        linkageColsTabPanel.setLayout(new BorderLayout(0, 0));

        mainToolBar = new MainToolBar();
        frame.getContentPane().add(mainToolBar, BorderLayout.NORTH);
        JScrollPane linkageColsScrollPane = new JScrollPane(linkageColsTable);
        linkageColsTabPanel.add(linkageColsScrollPane);

        linkageColsEditingPanel = new LinkageColumnEditingPanel();
        linkageColsTabPanel.add(linkageColsEditingPanel, BorderLayout.SOUTH);

        linkageColsButtonPanel = new LinkageColumnButtonPanel();
        linkageColsTabPanel.add(linkageColsButtonPanel, BorderLayout.NORTH);
        linkageColsEditingPanel.setEnabled(false);

        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

        menuBar = new MainMenuBar();
        frame.setJMenuBar(menuBar);

        ToolTipManager.sharedInstance().setInitialDelay(100);

        frame.pack();

        Font f = frame.getFont();
        frame.setFont(f.deriveFont(f.getSize2D() * 1.1f));

    }

    private String selectDatasetFile(String currentName) {
        JFileChooser chooser = new JFileChooser();
        Path dir = currentFileName == null ? Paths.get(".").toAbsolutePath()
                : Paths.get(currentFileName).toAbsolutePath().getParent();
        chooser.setCurrentDirectory(currentName != null
                ? dir.resolve(currentName).getParent().toFile()
                : dir.toFile());
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                MessageProvider.getMessage("datasets.supportedformats"), "csv",
                "dbf");
        chooser.setFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            Path here = currentFileName == null ? null
                    : Paths.get(currentFileName).toAbsolutePath().getParent();
            String f = chooser.getSelectedFile().getAbsolutePath();
            if (here != null && f.startsWith(here.toString() + File.separator))
                return here.relativize(Paths.get(f)).toString();
            return f;
        }
        return null;
    }

    private String selectConfigFile(String fileName, boolean save) {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(
                fileName != null ? new File(fileName).getParentFile()
                        : new File(".").getAbsoluteFile());
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                MessageProvider
                        .getMessage(save ? "menu.file.save.propertiesfile"
                                : "menu.file.open.propertiesfile"),
                "properties", "ini");
        chooser.setFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);
        int result = save ? chooser.showSaveDialog(frame)
                : chooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION)
            return chooser.getSelectedFile().getAbsolutePath();
        return null;
    }

    private String selectDir(String fileName) {
        JFileChooser chooser = new JFileChooser();
        Path dir = currentFileName == null ? Paths.get(".").toAbsolutePath()
                : Paths.get(currentFileName).toAbsolutePath().getParent();
        chooser.setCurrentDirectory(
                fileName != null ? dir.resolve(fileName).getParent().toFile()
                        : dir.toFile());
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(true);
        int result = chooser.showSaveDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION)
            return chooser.getSelectedFile().getAbsolutePath();
        return null;
    }

    private String selectConfigFile(String fileName) {
        return selectConfigFile(fileName, false);
    }

    private void updateConfigFileLabel() {
        String unsaved = MessageProvider.getMessage("menu.unsavedfile");
        menuBar.getCurrentFileLbl().setText((dirty ? "[*] " : "")
                + (currentFileName == null ? unsaved : currentFileName));
        frame.setTitle((dirty ? "[*] " : "")
                + (currentFileName == null ? unsaved
                        : new File(currentFileName).getName())
                + " - " + PROGRAM_NAME);
    }

    protected void updateUndoMenuText(String summary) {
        String txt = MessageProvider.getMessage("menu.edit.undo")
                + (summary != null ? String.format(" (%s)", summary) : "");
        menuBar.getUndoMenuItem().setText(txt);
        mainToolBar.getUndoBtn().setToolTipText(txt);
    }

    protected void updateRedoMenuText(String summary) {
        String txt = MessageProvider.getMessage("menu.edit.redo")
                + (summary != null ? String.format(" (%s)", summary) : "");
        menuBar.getRedoMenuItem().setText(txt);
        mainToolBar.getRedoBtn().setToolTipText(txt);
    }

    @Override
    public void updateLocalisedStrings() {
        // menu bar
        menuBar.updateLocalisedStrings();
        updateUndoMenuText(history.getUndoSummary());
        updateRedoMenuText(history.getRedoSummary());
        updateConfigFileLabel();

        // toolbar
        mainToolBar.updateLocalisedStrings();

        // tab - datasets
        tabbedPane.setTitleAt(tabbedPane.indexOfComponent(datasetsTabPanel),
                " " + MessageProvider.getMessage("datasets") + " ");
        datasetsTabPanel.updateLocalisedStrings();

        // tab - options
        tabbedPane.setTitleAt(tabbedPane.indexOfComponent(optionsTabPanel),
                " " + MessageProvider.getMessage("options") + " ");
        optionsTabPanel.updateLocalisedStrings();

        // tab - columns
        tabbedPane.setTitleAt(tabbedPane.indexOfComponent(linkageColsTabPanel),
                " " + MessageProvider.getMessage("columns") + " ");
        linkageColsEditingPanel.updateLocalisedStrings();
        linkageColsButtonPanel.updateLocalisedStrings();
        ColumnPairTableModel model = (ColumnPairTableModel) linkageColsTable
                .getModel();
        model.updateLocalisedStrings(linkageColsTable.getColumnModel());

        // tab - execution
        tabbedPane.setTitleAt(tabbedPane.indexOfComponent(executionTabPanel),
                " " + MessageProvider.getMessage("execution") + " ");
        executionTabPanel.updateLocalisedStrings();

        // Swing-provided buttons such as 'Ok', 'Open', 'Cancel'
        JComponent.setDefaultLocale(MessageProvider.getLocale());
        Locale.setDefault(MessageProvider.getLocale());

        // re-create validation messages
        validateAllTabs();
    }

}
