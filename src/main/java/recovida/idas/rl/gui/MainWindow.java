package recovida.idas.rl.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableRowSorter;

import recovida.idas.rl.gui.DatasetPeek.DatasetPeekResult;
import recovida.idas.rl.gui.lang.MessageProvider;
import recovida.idas.rl.gui.listener.ColumnPairInclusionExclusionListener;
import recovida.idas.rl.gui.listener.ColumnPairSelectionListener;
import recovida.idas.rl.gui.listener.ColumnPairValueChangeListener;
import recovida.idas.rl.gui.listener.SettingItemChangeListener;
import recovida.idas.rl.gui.pair.ColumnPairManager;
import recovida.idas.rl.gui.settingitem.NumberSettingItem;
import recovida.idas.rl.gui.settingitem.SettingItem;
import recovida.idas.rl.gui.settingitem.StringSettingItem;
import recovida.idas.rl.gui.settingitem.StringSettingItemWithList;
import recovida.idas.rl.gui.ui.ColumnPairTableModel;
import recovida.idas.rl.gui.ui.WarningIcon;
import recovida.idas.rl.gui.ui.cellrendering.NameColumnPairCellRenderer;
import recovida.idas.rl.gui.ui.cellrendering.NumberColumnPairCellRenderer;
import recovida.idas.rl.gui.ui.cellrendering.PhonWeightColumnPairCellRenderer;
import recovida.idas.rl.gui.ui.cellrendering.RenameColumnPairCellRenderer;
import recovida.idas.rl.gui.ui.cellrendering.TypeColumnPairCellRenderer;
import recovida.idas.rl.gui.ui.cellrendering.WeightColumnPairCellRenderer;
import recovida.idas.rl.gui.ui.container.DatasetsTabPanel;
import recovida.idas.rl.gui.ui.container.ExecutionPanel;
import recovida.idas.rl.gui.ui.container.LinkageColumnButtonPanel;
import recovida.idas.rl.gui.ui.container.LinkageColumnEditingPanel;
import recovida.idas.rl.gui.ui.container.MainMenuBar;
import recovida.idas.rl.gui.ui.field.JSpinnerWithBlankValue;
import recovida.idas.rl.gui.ui.field.JTextFieldWithPlaceholder;
import recovida.idas.rl.gui.ui.window.AboutWindow;
import recovida.idas.rl.gui.undo.HistoryPropertyChangeEventListener;
import recovida.idas.rl.gui.undo.UndoHistory;
import recovida.idas.rl.gui.ui.container.MainToolBar;

public class MainWindow {

    /* Non-GUI attributes */
    public final static String PROGRAM_NAME = "IDaS-RL";
    UndoHistory history = new UndoHistory();
    ConfigurationFile cf = new ConfigurationFile();
    String currentFileName = null;
    boolean dirty = false;
    boolean skipValidation = false; // while filling in values read from file
    FileChangeWatcher currentFileChangeWatcher = null;
    private ColumnPairManager manager;
    ConcurrentMap<String, ConcurrentMap<String, DatasetPeek>> peekFromFileNameAndEncoding = new ConcurrentHashMap<>();

    /* GUI components */
    private JFrame frame;
    private JTable linkageColsTable;
    private JTextFieldWithPlaceholder linkageDirField;
    private JTextFieldWithPlaceholder indexDirField;
    private JSpinner minScoreField;
    private JSpinner maxRowsField;
    private JTabbedPane tabbedPane;
    private DatasetsTabPanel datasetsTabPanel;
    private JPanel optionsTabPanel;
    private LinkageColumnEditingPanel linkageColsEditingPanel;
    private LinkageColumnButtonPanel linkageColsButtonPanel;
    private ColumnPairTableModel columnPairTableModel;
    private JPanel linkageColsTabPanel;

    private JButton linkageDirBtn;
    private JLabel indexDirLbl;
    private JButton indexDirBtn;
    private JLabel minScoreLbl;
    private JLabel maxRowsLbl;
    private JLabel linkageDirLbl;
    private JSpinnerWithBlankValue threadsField;
    private JLabel threadsLbl;
    private JPanel threadsPanel;
    private JLabel coresLabel;
    private Component horizontalStrut;
    private ExecutionPanel executionTabPanel;
    private MainToolBar mainToolBar;
    private MainMenuBar menuBar;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new MainWindow();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public MainWindow() {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
        }
        initialize();

        // Tab: DATASETS
        SettingItemChangeListener datasetsTabEventListenerTop = (o) -> {
            if (!skipValidation)
                tabbedPane.setSelectedComponent(datasetsTabPanel);
            validateDatasetsTabTopPart();
        };
        SettingItemChangeListener datasetsTabEventListenerBottom = (o) -> {
            if (!skipValidation)
                tabbedPane.setSelectedComponent(datasetsTabPanel);
            validateDatasetsTabBottomPart();
        };

        cf.addSettingItem("db_a", new StringSettingItem(history, "", "", datasetsTabPanel.getFirstDatasetField(),
                datasetsTabEventListenerTop));
        cf.addSettingItem("encoding_a", new StringSettingItemWithList(history, "", "UTF-8",
                datasetsTabPanel.getFirstEncodingField(), datasetsTabEventListenerTop));
        cf.addSettingItem("suffix_a", new StringSettingItem(history, "", "_dsa",
                datasetsTabPanel.getFirstDatasetSuffixField(), datasetsTabEventListenerBottom));
        cf.addSettingItem("row_num_col_a", new StringSettingItem(history, "", "#A",
                datasetsTabPanel.getFirstDatasetRowNumColField(), datasetsTabEventListenerBottom));

        cf.addSettingItem("db_b", new StringSettingItem(history, "", "", datasetsTabPanel.getSecondDatasetField(),
                datasetsTabEventListenerTop));
        cf.addSettingItem("encoding_b", new StringSettingItemWithList(history, "", "UTF-8",
                datasetsTabPanel.getSecondEncodingField(), datasetsTabEventListenerTop));
        cf.addSettingItem("suffix_b", new StringSettingItem(history, "", "_dsb",
                datasetsTabPanel.getSecondDatasetSuffixField(), datasetsTabEventListenerBottom));
        cf.addSettingItem("row_num_col_b", new StringSettingItem(history, "", "#B",
                datasetsTabPanel.getSecondDatasetRowNumColField(), datasetsTabEventListenerBottom));

        // Tab: OPTIONS
        SettingItemChangeListener optionsTabEventListener = (o) -> {
            if (!skipValidation)
                tabbedPane.setSelectedComponent(optionsTabPanel);
            validateOptionsTab();
        };
        cf.addSettingItem("db_index", new StringSettingItem(history, "", "", indexDirField, optionsTabEventListener));
        cf.addSettingItem("linkage_folder",
                new StringSettingItem(history, "", "", linkageDirField, optionsTabEventListener));
        cf.addSettingItem("min_score",
                new NumberSettingItem(history, 0.0, 0.0, minScoreField, optionsTabEventListener));
        cf.addSettingItem("num_threads", new NumberSettingItem(history, 0, 0, threadsField));

        horizontalStrut = Box.createHorizontalStrut(20);
        threadsPanel.add(horizontalStrut);

        coresLabel = new JLabel("_On this computer, this number should ideally be at most n.");
        threadsPanel.add(coresLabel);
        cf.addSettingItem("max_rows", new NumberSettingItem(history, Integer.MAX_VALUE, Integer.MAX_VALUE, maxRowsField,
                optionsTabEventListener));
        linkageDirBtn.addActionListener(e -> {
            String current = linkageDirField.getText();
            String result = selectDir(current.isEmpty() ? null : current);
            if (result != null)
                linkageDirField.setText(result);
        });
        indexDirBtn.addActionListener(e -> {
            String current = indexDirField.getText();
            String result = selectDir(current.isEmpty() ? null : current);
            if (result != null)
                indexDirField.setText(result);
        });

        // Tab: COLUMNS
        ColumnPairValueChangeListener linkageColsTabAddDelEventListener = (int index, String key, Object newValue) -> {
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
        ColumnPairSelectionListener linkageColsTabSelChangeEventListener = (int index) -> {
            validateLinkageColsTabSelectedRow(index);
        };
        manager = new ColumnPairManager(history, linkageColsButtonPanel, linkageColsEditingPanel, linkageColsTable);

        executionTabPanel = new ExecutionPanel();
        tabbedPane.addTab("_Execution", null, executionTabPanel, null);
        manager.addInclusionExclusionListener(linkageColsTabValueChangeEventListener);
        manager.addSelectionListener(linkageColsTabSelChangeEventListener);
        manager.addValueChangeSelectionListener(linkageColsTabAddDelEventListener);
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

        // Toolbar buttons

        mainToolBar.getNewFileBtn().addActionListener(e -> doNew());
        mainToolBar.getOpenFileBtn().addActionListener(e -> doOpen());
        mainToolBar.getSaveFileBtn().addActionListener(e -> doSave());
        mainToolBar.getRedoBtn().addActionListener(e -> doRedo());
        mainToolBar.getUndoBtn().addActionListener(e -> doUndo());
        mainToolBar.getRunBtn().addActionListener(e -> doRun());

        // Undo - changed state

        history.addPropertyChangeListener(new HistoryPropertyChangeEventListener() {

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
                recovida.idas.rl.core.lang.MessageProvider.setLocale((Locale) e.getItem());
                updateLocalisedStrings();
            }
        });

        updateConfigFileLabel();
        updateLocalisedStrings();

        // show frame
        frame.setVisible(true);

        // Initial validation
        SwingUtilities.invokeLater(() -> validateAllTabs());

    }

    protected Icon getTabErrorIcon() {
        return new ImageIcon(WarningIcon.BUFFERED_IMAGE.getScaledInstance(15, 15, Image.SCALE_SMOOTH));
    }

    public synchronized int validateDatasetsTabTopPart() {
        if (skipValidation)
            return -1;

        int errorCount = 0;

        @SuppressWarnings("rawtypes")
        Map<String, SettingItem> items = cf.getSettingItems();

        // Encodings
        String enc1 = (String) items.get("encoding_a").getCurrentValue();
        String enc2 = (String) items.get("encoding_b").getCurrentValue();
        if (enc1 == null || enc1.isEmpty())
            enc1 = (String) items.get("encoding_a").getDefaultValue();
        if (enc2 == null || enc2.isEmpty())
            enc2 = (String) items.get("encoding_b").getDefaultValue();
        if (!isValidEncoding(enc1)) {
            datasetsTabPanel.getFirstEncodingWarningLbl()
                    .setToolTipText(MessageProvider.getMessage("datasets.badencoding"));
            datasetsTabPanel.getFirstEncodingWarningLbl().setVisible(true);
            errorCount++;
        } else {
            datasetsTabPanel.getFirstEncodingWarningLbl().setVisible(false);
        }
        if (!isValidEncoding(enc2)) {
            datasetsTabPanel.getSecondEncodingWarningLbl()
                    .setToolTipText(MessageProvider.getMessage("datasets.badencoding"));
            datasetsTabPanel.getSecondEncodingWarningLbl().setVisible(true);
            errorCount++;
        } else {
            datasetsTabPanel.getSecondEncodingWarningLbl().setVisible(false);
        }

        // File names
        Path dir = currentFileName == null ? null : Paths.get(currentFileName).getParent();
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
            peekFromFileNameAndEncoding.put(fn1, new ConcurrentHashMap<String, DatasetPeek>());
        ConcurrentMap<String, DatasetPeek> m = peekFromFileNameAndEncoding.get(fn1);
        if (m.containsKey(enc1)) {
            p = m.get(enc1);
        } else {
            p = new DatasetPeek(dir, fn1, enc1);
            p.peek();
            if (fn1 != null && !fn1.isEmpty()) {
                m.put(enc1, p);
                new FileChangeWatcher(Paths.get(fn1), () -> {
                    peekFromFileNameAndEncoding.remove(fn1);
                    validateDatasetsTabTopPart();
                }, true).enable();
            }
        }
        DatasetPeekResult result = p.getResult();
        if (result != DatasetPeekResult.SUCCESS) {
            errorCount++;
            datasetsTabPanel.getFirstDatasetWarningLbl().setToolTipText(getDatasetHeaderErrorMessage(result, fn1));
            datasetsTabPanel.getFirstDatasetWarningLbl().setVisible(true);
        } else
            datasetsTabPanel.getFirstDatasetWarningLbl().setVisible(false);
        manager.setFirstDatasetColumnNames(p.getColumnNames());
        if (!peekFromFileNameAndEncoding.containsKey(fn2))
            peekFromFileNameAndEncoding.put(fn2, new ConcurrentHashMap<String, DatasetPeek>());
        m = peekFromFileNameAndEncoding.get(fn2);
        if (m.containsKey(enc2)) {
            p = m.get(enc2);
        } else {
            p = new DatasetPeek(dir, fn2, enc2);
            p.peek();
            if (fn2 != null && !fn2.isEmpty()) {
                m.put(enc2, p);
                new FileChangeWatcher(Paths.get(fn2), () -> {
                    peekFromFileNameAndEncoding.remove(fn2);
                    validateDatasetsTabTopPart();
                }, true).enable();
            }
        }
        result = p.getResult();
        if (result != DatasetPeekResult.SUCCESS) {
            errorCount++;
            datasetsTabPanel.getSecondDatasetWarningLbl().setToolTipText(getDatasetHeaderErrorMessage(result, fn2));
            datasetsTabPanel.getSecondDatasetWarningLbl().setVisible(true);
        } else
            datasetsTabPanel.getSecondDatasetWarningLbl().setVisible(false);
        manager.setSecondDatasetColumnNames(p.getColumnNames());
        if (manager.getColumnPairCount() > 0)
            manager.getTableModel().fireTableRowsUpdated(0, manager.getColumnPairCount() - 1);
        validateLinkageColsTab();
        tabbedPane.setIconAt(tabbedPane.indexOfComponent(datasetsTabPanel), errorCount == 0 ? null : getTabErrorIcon());
        return errorCount;
    }

    public synchronized int validateDatasetsTabBottomPart() {
        if (skipValidation)
            return -1;

        int errorCount = 0;
        @SuppressWarnings("rawtypes")
        Map<String, SettingItem> items = cf.getSettingItems();
        String s1, s2;

        // Suffixes
        s1 = (String) items.get("suffix_a").getCurrentValue();
        s2 = (String) items.get("suffix_b").getCurrentValue();
        if (s1 == null || s1.isEmpty())
            s1 = (String) items.get("suffix_a").getDefaultValue();
        if (s2 == null || s2.isEmpty())
            s2 = (String) items.get("suffix_b").getDefaultValue();
        if (s1.equals(s2)) {
            String msg = MessageProvider.getMessage("datasets.identicalprefixes");
            datasetsTabPanel.getFirstDatasetSuffixWarningLbl().setToolTipText(msg);
            datasetsTabPanel.getSecondDatasetSuffixWarningLbl().setToolTipText(msg);
            datasetsTabPanel.getFirstDatasetSuffixWarningLbl().setVisible(true);
            datasetsTabPanel.getSecondDatasetSuffixWarningLbl().setVisible(true);
            errorCount++;
        } else {
            datasetsTabPanel.getFirstDatasetSuffixWarningLbl().setVisible(false);
            datasetsTabPanel.getSecondDatasetSuffixWarningLbl().setVisible(false);
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
            String msg = MessageProvider.getMessage("datasets.identicalrownumcolnames");
            datasetsTabPanel.getFirstDatasetRowNumColWarningLbl().setToolTipText(msg);
            datasetsTabPanel.getSecondDatasetRowNumColWarningLbl().setToolTipText(msg);
            datasetsTabPanel.getFirstDatasetRowNumColWarningLbl().setVisible(true);
            datasetsTabPanel.getSecondDatasetRowNumColWarningLbl().setVisible(true);
            errorCount++;
        } else {
            datasetsTabPanel.getFirstDatasetRowNumColWarningLbl().setVisible(false);
            datasetsTabPanel.getSecondDatasetRowNumColWarningLbl().setVisible(false);
        }
        return errorCount;
    }

    public synchronized int validateOptionsTab() {
        if (skipValidation)
            return -1;
        return 0;
    }

    public synchronized int validateLinkageColsTabSelectedRow(int rowIndex, String key) {
        if (skipValidation)
            return -1;
        boolean error = rowIndex >= 0 && !columnPairTableModel.isCellValid(rowIndex, key);
        JLabel lbl;
        switch (key) {
        case "index_a":
            lbl = linkageColsEditingPanel.getFirstNameWarningLbl();
            lbl.setVisible(error);
            lbl.setToolTipText(error ? MessageProvider.getMessage("columns.badcolumn") : "");
            break;
        case "index_b":
            lbl = linkageColsEditingPanel.getSecondNameWarningLbl();
            lbl.setVisible(error);
            lbl.setToolTipText(error ? MessageProvider.getMessage("columns.badcolumn") : "");
            break;
        case "type":
            lbl = linkageColsEditingPanel.getTypeWarningLbl();
            lbl.setVisible(error);
            lbl.setToolTipText(error ? MessageProvider.getMessage("columns.badtype") : "");
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

    public synchronized int validateLinkageColsTabSelectedRow(int index) {
        if (skipValidation)
            return -1;
        int errorCount = 0;
        String[] keys = { "number", "index_a", "index_b", "type", "weight", "phon_weight", "rename_a", "rename_b" };
        for (String key : keys)
            errorCount += validateLinkageColsTabSelectedRow(index, key);
        return errorCount;
    }

    public synchronized int validateLinkageColsTab() {
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

    public int validateAllTabs() {
        return validateDatasetsTabTopPart() + validateDatasetsTabBottomPart() + validateOptionsTab()
                + validateLinkageColsTab();
    }

    public static String getDatasetHeaderErrorMessage(DatasetPeekResult result, String fileName) {
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

    @SuppressWarnings("unchecked")
    public void clearAllFields() {
        for (SettingItem<?, ?> item : cf.getSettingItems().values()) {
            JComponent component = item.getGuiComponent();
            if (component instanceof JTextField)
                ((JTextField) component).setText("");
            else if (component instanceof JComboBox<?> && ((JComboBox<String>) component).isEditable())
                ((JComboBox<String>) item.getGuiComponent()).setSelectedItem("");
            else if (component instanceof JSpinnerWithBlankValue)
                ((JSpinnerWithBlankValue) component).setValue(((JSpinnerWithBlankValue) component).getBlankValue());
        }
        columnPairTableModel.setRowCount(0);
    }

    protected static boolean isValidEncoding(String enc) {
        if (enc == null)
            return false;
        try {
            return enc.toUpperCase().replaceAll("[^A-Z0-9]", "").equals("ANSI") || Charset.isSupported(enc);
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
            else if (ans == JOptionPane.CANCEL_OPTION || ans == JOptionPane.CLOSED_OPTION)
                return;
        }
        clearAllFields();
        executionTabPanel.clear();
        updateConfigFileName(null);
        history.clearAll();
        manager.reset();
    }

    protected synchronized void doOpen() {
        if (dirty) {
            int ans = promptToSaveChanges();
            if (ans == JOptionPane.YES_OPTION)
                doSave();
            else if (ans == JOptionPane.CANCEL_OPTION || ans == JOptionPane.CLOSED_OPTION)
                return;
        }
        String newConfigFileName = selectConfigFile(
                currentFileName == null ? null : Paths.get(currentFileName).toAbsolutePath().toString());
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
            JOptionPane.showMessageDialog(frame, MessageProvider.getMessage("menu.file.open.cantopen"),
                    MessageProvider.getMessage("menu.file.open.error"), JOptionPane.ERROR_MESSAGE);
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
                JOptionPane.showMessageDialog(frame, MessageProvider.getMessage("menu.file.save.containserrors"),
                        MessageProvider.getMessage("menu.file.save.warning"), JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, MessageProvider.getMessage("menu.file.save.cantsave"),
                    MessageProvider.getMessage("menu.file.save.error"), JOptionPane.ERROR_MESSAGE);
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
        JOptionPane.showMessageDialog(frame, MessageProvider.getMessage("menu.file.save.duplicatenumbers"),
                MessageProvider.getMessage("menu.file.save.error"), JOptionPane.ERROR_MESSAGE);
    }

    protected void doExit() {
        if (dirty) {
            int ans = promptToSaveChanges();
            if (ans == JOptionPane.YES_OPTION)
                doSave();
            else if (ans == JOptionPane.CANCEL_OPTION || ans == JOptionPane.CLOSED_OPTION)
                return;
        }
        frame.dispose();
    }

    protected void doRun() {
        mainToolBar.getRunBtn().setEnabled(false);
        menuBar.getRunMenuItem().setEnabled(false);
        if (dirty)
            doSave();
        Execution ex = new Execution(executionTabPanel.addExecutionPanel(), currentFileName);
        tabbedPane.setSelectedComponent(executionTabPanel);
        CompletableFuture<Boolean> f = ex.start();
        f.thenAccept((Boolean success) -> {
            SwingUtilities.invokeLater(() -> {
                mainToolBar.getRunBtn().setEnabled(true);
                menuBar.getRunMenuItem().setEnabled(true);
                f.join();
            });
        });
    }

    protected void doAbout() {
        new AboutWindow(frame).setVisible(true);
    }

    protected synchronized void updateConfigFileName(String fn) {
        if (currentFileChangeWatcher != null)
            currentFileChangeWatcher.disable();
        if (fn != null) {
            if (!fn.equals(currentFileName) || currentFileChangeWatcher == null)
                currentFileChangeWatcher = new FileChangeWatcher(Paths.get(fn), () -> {
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
        String msg = (currentFileName == null ? MessageProvider.getMessage("menu.file.save.savechangesnofile")
                : MessageFormat.format(MessageProvider.getMessage("menu.file.save.savechangesfile"), currentFileName));
        return JOptionPane.showOptionDialog(this.frame, msg, MessageProvider.getMessage("menu.file.save.savechanges"),
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
    }

    protected int promptToSaveChangesAfterChangeOnDisk() {
        String[] opt = new String[] {
                MessageProvider.getMessage("menu.file.save." + (dirty ? "saveandoverwrite" : "overwrite")),
                MessageProvider.getMessage("menu.file.save." + (dirty ? "discardandreload" : "reload")) };
        String msg = MessageFormat.format(MessageProvider.getMessage("menu.file.save.changedondisk"),
                currentFileName == null ? "" : Paths.get(currentFileName).getFileName());
        String title = MessageProvider.getMessage("menu.file.save.changedexternally");
        int result;
        do {
            result = JOptionPane.showOptionDialog(this.frame, msg, title, JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE, null, opt, null);
        } while (result == JOptionPane.CLOSED_OPTION);
        return result;
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setPreferredSize(new Dimension(900, 500));
        frame.setMinimumSize(new Dimension(900, 400));
        frame.setBounds(100, 100, 799, 481);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        datasetsTabPanel = new DatasetsTabPanel();
        tabbedPane.addTab("_Datasets", null, datasetsTabPanel, null);

        datasetsTabPanel.getFirstDatasetBtn().addActionListener(e -> {
            String current = datasetsTabPanel.getFirstDatasetField().getText();
            String result = selectDatasetFile(current.isEmpty() ? null : current);
            if (result != null)
                datasetsTabPanel.getFirstDatasetField().setText(result);
        });
        datasetsTabPanel.getSecondDatasetBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String current = datasetsTabPanel.getSecondDatasetField().getText();
                String result = selectDatasetFile(current.isEmpty() ? null : current);
                if (result != null)
                    datasetsTabPanel.getSecondDatasetField().setText(result);
            }
        });

        optionsTabPanel = new JPanel();
        tabbedPane.addTab("_Options", null, optionsTabPanel, null);
        GridBagLayout gbl_optionsTabPanel = new GridBagLayout();
        gbl_optionsTabPanel.columnWidths = new int[] { 250, 500 };
        gbl_optionsTabPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
        gbl_optionsTabPanel.columnWeights = new double[] { 0.0, 1.0 };
        gbl_optionsTabPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        optionsTabPanel.setLayout(gbl_optionsTabPanel);

        Component optionsTabTopMargin = Box.createRigidArea(new Dimension(20, 20));
        GridBagConstraints gbc_optionsTabTopMargin = new GridBagConstraints();
        gbc_optionsTabTopMargin.insets = new Insets(0, 0, 5, 5);
        gbc_optionsTabTopMargin.gridx = 0;
        gbc_optionsTabTopMargin.gridy = 0;
        optionsTabPanel.add(optionsTabTopMargin, gbc_optionsTabTopMargin);

        linkageDirLbl = new JLabel("_Linkage location");
        linkageDirLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_linkageDirLbl = new GridBagConstraints();
        gbc_linkageDirLbl.insets = new Insets(0, 0, 5, 5);
        gbc_linkageDirLbl.anchor = GridBagConstraints.EAST;
        gbc_linkageDirLbl.gridx = 0;
        gbc_linkageDirLbl.gridy = 1;
        optionsTabPanel.add(linkageDirLbl, gbc_linkageDirLbl);

        JPanel linkageDirContainer = new JPanel();
        GridBagConstraints gbc_linkageDirContainer = new GridBagConstraints();
        gbc_linkageDirContainer.fill = GridBagConstraints.BOTH;
        gbc_linkageDirContainer.insets = new Insets(0, 0, 5, 0);
        gbc_linkageDirContainer.gridx = 1;
        gbc_linkageDirContainer.gridy = 1;
        optionsTabPanel.add(linkageDirContainer, gbc_linkageDirContainer);
        linkageDirContainer.setLayout(new BoxLayout(linkageDirContainer, BoxLayout.X_AXIS));

        JLabel linkageDirWarningLbl = new WarningIcon();
        linkageDirContainer.add(linkageDirWarningLbl);

        linkageDirField = new JTextFieldWithPlaceholder();
        linkageDirField.setHorizontalAlignment(SwingConstants.TRAILING);
        linkageDirContainer.add(linkageDirField);
        linkageDirField.setColumns(10);

        linkageDirBtn = new JButton("_Select...");
        linkageDirContainer.add(linkageDirBtn);

        SpinnerNumberModel minScoreModel = new SpinnerNumberModel(0.0, 0.0, 100, 0.001);

        SpinnerNumberModel maxRowsModel = new SpinnerNumberModel(Integer.MAX_VALUE, 0, Integer.MAX_VALUE, 1);

        SpinnerNumberModel threadsModel = new SpinnerNumberModel(0, 0, 256, 1);

        indexDirLbl = new JLabel("_Index location");
        indexDirLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_indexDirLbl = new GridBagConstraints();
        gbc_indexDirLbl.anchor = GridBagConstraints.EAST;
        gbc_indexDirLbl.insets = new Insets(0, 0, 5, 5);
        gbc_indexDirLbl.gridx = 0;
        gbc_indexDirLbl.gridy = 2;
        optionsTabPanel.add(indexDirLbl, gbc_indexDirLbl);

        JPanel indexDirContainer = new JPanel();
        GridBagConstraints gbc_indexDirContainer = new GridBagConstraints();
        gbc_indexDirContainer.fill = GridBagConstraints.BOTH;
        gbc_indexDirContainer.insets = new Insets(0, 0, 5, 0);
        gbc_indexDirContainer.gridx = 1;
        gbc_indexDirContainer.gridy = 2;
        optionsTabPanel.add(indexDirContainer, gbc_indexDirContainer);
        indexDirContainer.setLayout(new BoxLayout(indexDirContainer, BoxLayout.X_AXIS));

        JLabel indexDirWarningLbl = new WarningIcon();
        indexDirContainer.add(indexDirWarningLbl);

        indexDirField = new JTextFieldWithPlaceholder();
        indexDirField.setHorizontalAlignment(SwingConstants.TRAILING);
        indexDirContainer.add(indexDirField);
        indexDirField.setColumns(10);

        indexDirBtn = new JButton("_Select...");
        indexDirContainer.add(indexDirBtn);

        minScoreLbl = new JLabel("_Minimum score");
        minScoreLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_minScoreLbl = new GridBagConstraints();
        gbc_minScoreLbl.fill = GridBagConstraints.HORIZONTAL;
        gbc_minScoreLbl.insets = new Insets(0, 0, 5, 5);
        gbc_minScoreLbl.gridx = 0;
        gbc_minScoreLbl.gridy = 3;
        optionsTabPanel.add(minScoreLbl, gbc_minScoreLbl);
        minScoreField = new JSpinnerWithBlankValue(minScoreModel);
        ((JSpinnerWithBlankValue) minScoreField).setBlankValue(Double.valueOf(0.0));
        GridBagConstraints gbc_minScoreField = new GridBagConstraints();
        gbc_minScoreField.anchor = GridBagConstraints.WEST;
        gbc_minScoreField.insets = new Insets(0, 0, 5, 0);
        gbc_minScoreField.gridx = 1;
        gbc_minScoreField.gridy = 3;
        optionsTabPanel.add(minScoreField, gbc_minScoreField);

        JSpinner.NumberEditor ne_minScoreField = new JSpinner.NumberEditor(minScoreField, "0.000");
        ne_minScoreField.setPreferredSize(new Dimension(122, 29));
        ne_minScoreField.setRequestFocusEnabled(false);
        minScoreField.setEditor(ne_minScoreField);

        threadsLbl = new JLabel("_Number of threads");
        threadsLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_threadsLbl = new GridBagConstraints();
        gbc_threadsLbl.anchor = GridBagConstraints.EAST;
        gbc_threadsLbl.insets = new Insets(0, 0, 5, 5);
        gbc_threadsLbl.gridx = 0;
        gbc_threadsLbl.gridy = 4;
        optionsTabPanel.add(threadsLbl, gbc_threadsLbl);

        threadsPanel = new JPanel();
        threadsPanel.setBorder(null);
        GridBagConstraints gbc_threadsPanel = new GridBagConstraints();
        gbc_threadsPanel.anchor = GridBagConstraints.WEST;
        gbc_threadsPanel.fill = GridBagConstraints.VERTICAL;
        gbc_threadsPanel.insets = new Insets(0, 0, 5, 0);
        gbc_threadsPanel.gridx = 1;
        gbc_threadsPanel.gridy = 4;
        optionsTabPanel.add(threadsPanel, gbc_threadsPanel);
        threadsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 5));

        threadsField = new JSpinnerWithBlankValue(threadsModel);
        threadsPanel.add(threadsField);
        threadsField.setBlankValue(Integer.valueOf(0));

        JSpinner.NumberEditor ne_threadsField = new JSpinner.NumberEditor(threadsField, "0");
        ne_threadsField.setPreferredSize(new Dimension(122, 29));
        ne_threadsField.setRequestFocusEnabled(false);
        threadsField.setEditor(ne_threadsField);

        maxRowsLbl = new JLabel("_Only read first rows (A)");
        maxRowsLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        maxRowsLbl.setPreferredSize(new Dimension(150, 17));
        GridBagConstraints gbc_maxRowsLbl = new GridBagConstraints();
        gbc_maxRowsLbl.fill = GridBagConstraints.HORIZONTAL;
        gbc_maxRowsLbl.insets = new Insets(0, 0, 0, 5);
        gbc_maxRowsLbl.gridx = 0;
        gbc_maxRowsLbl.gridy = 5;
        optionsTabPanel.add(maxRowsLbl, gbc_maxRowsLbl);
        maxRowsField = new JSpinnerWithBlankValue(maxRowsModel);
        ((JSpinnerWithBlankValue) maxRowsField).setBlankValue(Integer.MAX_VALUE);
        GridBagConstraints gbc_maxRowsField = new GridBagConstraints();
        gbc_maxRowsField.anchor = GridBagConstraints.WEST;
        gbc_maxRowsField.gridx = 1;
        gbc_maxRowsField.gridy = 5;
        optionsTabPanel.add(maxRowsField, gbc_maxRowsField);

        JSpinner.NumberEditor ne_maxRowsField = new JSpinner.NumberEditor(maxRowsField, "0");
        maxRowsField.setEditor(ne_maxRowsField);

        linkageColsTabPanel = new JPanel();
        tabbedPane.addTab("_Columns", null, linkageColsTabPanel, null);

        linkageColsTable = new JTable();
        linkageColsTable.getTableHeader().setReorderingAllowed(false);
        linkageColsTable.setShowVerticalLines(true);
        linkageColsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        columnPairTableModel = new ColumnPairTableModel();
        linkageColsTabPanel.setLayout(new BorderLayout(0, 0));
        linkageColsTable.setModel(columnPairTableModel);
        TableRowSorter<ColumnPairTableModel> sorter = new TableRowSorter<>(columnPairTableModel);
        sorter.setSortKeys(Collections.singletonList(new RowSorter.SortKey(0, SortOrder.ASCENDING)));
        linkageColsTable.setRowSorter(sorter);
        linkageColsTable.setUpdateSelectionOnSort(true);
        linkageColsTable.setRowHeight(linkageColsTable.getRowHeight() * 15 / 10);
        sorter.setSortsOnUpdates(true);
        linkageColsTable.getColumnModel().getColumn(columnPairTableModel.getColumnIndex("weight"))
                .setCellRenderer(new WeightColumnPairCellRenderer());
        linkageColsTable.getColumnModel().getColumn(columnPairTableModel.getColumnIndex("phon_weight"))
                .setCellRenderer(new PhonWeightColumnPairCellRenderer());
        linkageColsTable.getColumnModel().getColumn(columnPairTableModel.getColumnIndex("rename_a"))
                .setCellRenderer(new RenameColumnPairCellRenderer("index_a"));
        linkageColsTable.getColumnModel().getColumn(columnPairTableModel.getColumnIndex("rename_b"))
                .setCellRenderer(new RenameColumnPairCellRenderer("index_b"));
        linkageColsTable.getColumnModel().getColumn(columnPairTableModel.getColumnIndex("index_a"))
                .setCellRenderer(new NameColumnPairCellRenderer());
        linkageColsTable.getColumnModel().getColumn(columnPairTableModel.getColumnIndex("index_b"))
                .setCellRenderer(new NameColumnPairCellRenderer());
        linkageColsTable.getColumnModel().getColumn(columnPairTableModel.getColumnIndex("type"))
                .setCellRenderer(new TypeColumnPairCellRenderer());
        linkageColsTable.getColumnModel().getColumn(columnPairTableModel.getColumnIndex("number"))
                .setCellRenderer(new NumberColumnPairCellRenderer());

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
        chooser.setCurrentDirectory(currentName != null ? dir.resolve(currentName).getParent().toFile() : dir.toFile());
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                MessageProvider.getMessage("datasets.supportedformats"), "csv", "dbf");
        chooser.setFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            Path here = currentFileName == null ? null : Paths.get(currentFileName).toAbsolutePath().getParent();
            String f = chooser.getSelectedFile().getAbsolutePath();
            if (here != null && f.startsWith(here.toString() + File.separator))
                return here.relativize(Paths.get(f)).toString();
            else
                return f;
        }
        return null;
    }

    private String selectConfigFile(String fileName, boolean save) {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(
                fileName != null ? new File(fileName).getParentFile() : new File(".").getAbsoluteFile());
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                MessageProvider.getMessage(save ? "menu.file.save.propertiesfile" : "menu.file.open.propertiesfile"),
                "properties", "ini");
        chooser.setFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);
        int result = save ? chooser.showSaveDialog(frame) : chooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION)
            return chooser.getSelectedFile().getAbsolutePath();
        return null;
    }

    private String selectDir(String fileName) {
        JFileChooser chooser = new JFileChooser();
        Path dir = currentFileName == null ? Paths.get(".").toAbsolutePath()
                : Paths.get(currentFileName).toAbsolutePath().getParent();
        chooser.setCurrentDirectory(fileName != null ? dir.resolve(fileName).getParent().toFile() : dir.toFile());
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
        menuBar.getCurrentFileLbl()
                .setText((dirty ? "[*] " : "") + (currentFileName == null ? unsaved : currentFileName));
        frame.setTitle((dirty ? "[*] " : "") + (currentFileName == null ? unsaved : new File(currentFileName).getName())
                + " - " + PROGRAM_NAME);
    }


    public void updateUndoMenuText(String summary) {
        String txt = MessageProvider.getMessage("menu.edit.undo")
                + (summary != null ? String.format(" (%s)", summary) : "");
        menuBar.getUndoMenuItem().setText(txt);
        mainToolBar.getUndoBtn().setToolTipText(txt);
    }

    public void updateRedoMenuText(String summary) {
        String txt = MessageProvider.getMessage("menu.edit.redo")
                + (summary != null ? String.format(" (%s)", summary) : "");
        menuBar.getRedoMenuItem().setText(txt);
        mainToolBar.getRedoBtn().setToolTipText(txt);
    }

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
        indexDirLbl.setText(MessageProvider.getMessage("options.indexlocation"));
        indexDirBtn.setText(MessageProvider.getMessage("options.indexlocation.select"));
        linkageDirLbl.setText(MessageProvider.getMessage("options.linkagelocation"));
        linkageDirBtn.setText(MessageProvider.getMessage("options.linkagelocation.select"));
        maxRowsLbl.setText(MessageProvider.getMessage("options.maxrows"));
        minScoreLbl.setText(MessageProvider.getMessage("options.minscore"));
        threadsLbl.setText(MessageProvider.getMessage("options.threads"));
        coresLabel.setText(MessageFormat.format(MessageProvider.getMessage("options.threads.cores"),
                Runtime.getRuntime().availableProcessors()));

        // tab - columns
        tabbedPane.setTitleAt(tabbedPane.indexOfComponent(linkageColsTabPanel),
                " " + MessageProvider.getMessage("columns") + " ");
        linkageColsEditingPanel.updateLocalisedStrings();
        linkageColsButtonPanel.updateLocalisedStrings();
        ColumnPairTableModel model = (ColumnPairTableModel) linkageColsTable.getModel();
        model.updateLocalisedStrings(linkageColsTable.getColumnModel());

        // tab - execution
        tabbedPane.setTitleAt(tabbedPane.indexOfComponent(executionTabPanel),
                " " + MessageProvider.getMessage("execution") + " ");
        executionTabPanel.updateLocalisedStrings();

        // Swing-provided buttons such as 'Ok', 'Open', 'Cancel'
        JComponent.setDefaultLocale(MessageProvider.getLocale());

        // re-create validation messages
        validateAllTabs();
    }

}
