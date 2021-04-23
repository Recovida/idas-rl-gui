package com.cidacs.rl.editor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import com.cidacs.rl.editor.gui.JComboBoxWithPlaceholder;
import com.cidacs.rl.editor.gui.JSpinnerWithBlankValue;
import com.cidacs.rl.editor.gui.JTextFieldWithPlaceholder;
import com.cidacs.rl.editor.gui.LinkageColumnPanel;
import com.cidacs.rl.editor.settingitem.NumberSettingItem;
import com.cidacs.rl.editor.settingitem.SettingItem;
import com.cidacs.rl.editor.settingitem.SettingItemChangeEventListener;
import com.cidacs.rl.editor.settingitem.StringSettingItem;
import com.cidacs.rl.editor.settingitem.StringSettingItemWithList;
import com.cidacs.rl.editor.undo.HistoryPropertyChangeEventListener;
import com.cidacs.rl.editor.undo.UndoHistory;

/**
 *
 */

/**
 *
 */
public class MainWindow {

    /* Non-GUI attributes */
    UndoHistory history = new UndoHistory();
    ConfigurationFile cf = new ConfigurationFile();
    String currentFileName = null;
    private JLabel currentFileLbl;
    boolean dirty = true;
    boolean skipValidation = false; // while filling in values read from file

    /* Non-GUI components */
    private JFrame frame;
    private JTextFieldWithPlaceholder firstDatasetSuffixField;
    private JTextFieldWithPlaceholder secondDatasetSuffixField;
    private JTextFieldWithPlaceholder firstDatasetRowNumColField;
    private JTextFieldWithPlaceholder secondDatasetRowNumColField;
    private JTextField firstDatasetField;
    private JTextField secondDatasetField;
    private JTable linkageColsTable;
    private JTextFieldWithPlaceholder linkageDirField;
    private JTextFieldWithPlaceholder indexDirField;
    private JSpinner minScoreField;
    private JSpinner maxRowsField;
    private JMenuItem exitMenuItem;
    private JMenuItem newFileMenuItem;
    private JMenuItem openFileMenuItem;
    private JMenuItem saveFileMenuItem;
    private JMenuItem saveAsFileMenuItem;
    private JLabel firstDatasetSuffixWarningLbl;
    private JLabel secondDatasetSuffixWarningLbl;
    private JLabel secondDatasetRowNumColWarningLbl;
    private JLabel firstDatasetRowNumColWarningLbl;
    private JComboBoxWithPlaceholder firstEncodingField;
    private JComboBoxWithPlaceholder secondEncodingField;
    private JLabel firstEncodingWarningLbl;
    private JLabel secondEncodingWarningLbl;
    private JMenuItem undoMenuItem;
    private JMenuItem redoMenuItem;
    private JMenu helpMenu;
    private JMenuItem aboutMenuItem;
    private JComboBox<String> languageCbox;
    private JTabbedPane tabbedPane;
    private JPanel datasetsTabPanel;
    private JPanel optionsTabPanel;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    MainWindow window = new MainWindow();
                    window.frame.setVisible(true);
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
        JComboBoxWithPlaceholder[] encodingCboxes = { firstEncodingField,
                secondEncodingField };
        fillEncodings(encodingCboxes);
        SettingItemChangeEventListener<String> datasetsTabEventListener = new SettingItemChangeEventListener<String>() {

            @Override
            public void changed(String newValue) {
                tabbedPane.setSelectedComponent(datasetsTabPanel);
                validateDatasetsTab();
            }
        };
        cf.addSettingItem("db_a", new StringSettingItem(history, "", "",
                firstDatasetField, datasetsTabEventListener));
        cf.addSettingItem("db_b", new StringSettingItem(history, "", "",
                secondDatasetField, datasetsTabEventListener));
        cf.addSettingItem("suffix_a", new StringSettingItem(history, "", "_dsa",
                firstDatasetSuffixField, datasetsTabEventListener));
        cf.addSettingItem("suffix_b", new StringSettingItem(history, "", "_dsb",
                secondDatasetSuffixField, datasetsTabEventListener));
        cf.addSettingItem("row_num_col_a", new StringSettingItem(history, "",
                "#A", firstDatasetRowNumColField, datasetsTabEventListener));
        cf.addSettingItem("row_num_col_b", new StringSettingItem(history, "",
                "#B", secondDatasetRowNumColField, datasetsTabEventListener));
        cf.addSettingItem("encoding_a", new StringSettingItemWithList(history,
                "", "UTF-8", firstEncodingField, datasetsTabEventListener));
        cf.addSettingItem("encoding_b", new StringSettingItemWithList(history,
                "", "UTF-8", secondEncodingField, datasetsTabEventListener));

        // Tab: OPTIONS
        SettingItemChangeEventListener<String> optionsTabEventListener = new SettingItemChangeEventListener<String>() {

            @Override
            public void changed(String newValue) {
                tabbedPane.setSelectedComponent(optionsTabPanel);
                validateOptionsTab();
            }
        };
        cf.addSettingItem("db_index", new StringSettingItem(history, "", "",
                indexDirField, optionsTabEventListener));
        cf.addSettingItem("linkage_folder", new StringSettingItem(history, "",
                "", linkageDirField, optionsTabEventListener));
        cf.addSettingItem("max_rows", new NumberSettingItem(history,
                Integer.MAX_VALUE, Integer.MAX_VALUE, maxRowsField));
        cf.addSettingItem("min_score",
                new NumberSettingItem(history, 0.0, 0.0, minScoreField));

        // Menus

        exitMenuItem.addActionListener(e -> doExit());
        openFileMenuItem.addActionListener(e -> doOpen());
        saveFileMenuItem.addActionListener(e -> doSave());
        saveAsFileMenuItem.addActionListener(e -> doSaveAs());
        redoMenuItem.addActionListener(e -> doRedo());
        undoMenuItem.addActionListener(e -> doUndo());

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
                        undoMenuItem.setEnabled(canUndo);
                    }

                    @Override
                    public void canRedoChanged(boolean canRedo) {
                        redoMenuItem.setEnabled(canRedo);
                    }
                });

    }

    public void validateDatasetsTab() {
        if (skipValidation)
            return;
        @SuppressWarnings("unused")
        int errorCount = 0;
        @SuppressWarnings("rawtypes")
        Map<String, SettingItem> items = cf.getSettingItems();
        String s1, s2;

        // Prefixes
        s1 = (String) items.get("suffix_a").getCurrentValue();
        s2 = (String) items.get("suffix_b").getCurrentValue();
        if (s1 == null || s1.isEmpty())
            s1 = (String) items.get("suffix_a").getDefaultValue();
        if (s2 == null || s2.isEmpty())
            s2 = (String) items.get("suffix_b").getDefaultValue();
        if (s1.equals(s2)) {
            String msg = "The prefixes should be different.";
            firstDatasetSuffixWarningLbl.setToolTipText(msg);
            secondDatasetSuffixWarningLbl.setToolTipText(msg);
            firstDatasetSuffixWarningLbl.setVisible(true);
            secondDatasetSuffixWarningLbl.setVisible(true);
            errorCount++;
        } else {
            firstDatasetSuffixWarningLbl.setVisible(false);
            secondDatasetSuffixWarningLbl.setVisible(false);
        }

        // Row number column names
        s1 = (String) items.get("row_num_col_a").getCurrentValue();
        s2 = (String) items.get("row_num_col_b").getCurrentValue();
        if (s1 == null || s1.isEmpty())
            s1 = (String) items.get("row_num_col_a").getDefaultValue();
        if (s2 == null || s2.isEmpty())
            s2 = (String) items.get("row_num_col_b").getDefaultValue();
        if (s1.equals(s2)) {
            String msg = "The row number column names should be different.";
            firstDatasetRowNumColWarningLbl.setToolTipText(msg);
            secondDatasetRowNumColWarningLbl.setToolTipText(msg);
            firstDatasetRowNumColWarningLbl.setVisible(true);
            secondDatasetRowNumColWarningLbl.setVisible(true);
            errorCount++;
        } else {
            firstDatasetRowNumColWarningLbl.setVisible(false);
            secondDatasetRowNumColWarningLbl.setVisible(false);
        }

        // Encodings
        s1 = (String) items.get("encoding_a").getCurrentValue();
        s2 = (String) items.get("encoding_b").getCurrentValue();
        if (s1 == null || s1.isEmpty())
            s1 = (String) items.get("encoding_a").getDefaultValue();
        if (s2 == null || s2.isEmpty())
            s2 = (String) items.get("encoding_a").getDefaultValue();
        if (!isValidEncoding(s1)) {
            firstEncodingWarningLbl.setToolTipText("Unsupported encoding.");
            firstEncodingWarningLbl.setVisible(true);
        } else {
            firstEncodingWarningLbl.setVisible(false);
        }
        if (!isValidEncoding(s2)) {
            secondEncodingWarningLbl.setToolTipText("Unsupported encoding.");
            secondEncodingWarningLbl.setVisible(true);
        } else {
            secondEncodingWarningLbl.setVisible(false);
        }

    }

    public void validateOptionsTab() {
        if (skipValidation)
            return;
    }

    public void validateAllTabs() {
        validateDatasetsTab();
        validateOptionsTab();
    }

    @SuppressWarnings("unchecked")
    public void clearAllFields() {
        for (SettingItem<?, ?> item : cf.getSettingItems().values()) {
            JComponent component = item.getGuiComponent();
            if (component instanceof JTextField)
                ((JTextField) component).setText("");
            else if (component instanceof JComboBox<?>
                    && ((JComboBox<String>) component).isEditable())
                ((JComboBox<String>) item.getGuiComponent())
                        .setSelectedItem("");
        }
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

    protected void doOpen() {
        if (dirty) {
            promptToSave();
        }
        String newConfigFileName = selectConfigFile(currentFileName == null
                ? null
                : Paths.get(currentFileName).toAbsolutePath().toString());
        if (newConfigFileName != null) {
            skipValidation = true;
            clearAllFields();
            try {
                cf.load(newConfigFileName);
                SwingUtilities.invokeLater(new Runnable() {

                    @Override
                    public void run() {
                        tabbedPane.setSelectedComponent(datasetsTabPanel);
                    }

                });
                currentFileName = newConfigFileName;
                dirty = false;
                updateConfigFileLabel();
            } catch (IOException e1) {
                System.err.println("ERROR");
            } finally {
                skipValidation = false;
                validateAllTabs();
            }
        }
    }

    protected void doSave() {
        if (currentFileName == null) {
            doSaveAs();
        } else {

        }
    }

    protected void doSaveAs() {

    }

    protected void doExit() {
        if (dirty) {
            promptToSave();
        }
        frame.dispose();
    }

    protected int promptToSave() {
        String msg = (currentFileName == null
                ? "Save the contents of this currently unsaved file?"
                : String.format("Save the changes made to the file “%s”?",
                        currentFileName));
        return JOptionPane.showOptionDialog(this.frame, msg, "Save changes?",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, null, null);
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setPreferredSize(new Dimension(900, 500));
        frame.setMinimumSize(new Dimension(900, 400));
        frame.setBounds(100, 100, 586, 377);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        datasetsTabPanel = new JPanel();
        tabbedPane.addTab("Datasets", null, datasetsTabPanel, null);
        GridBagLayout gbl_datasetsTabPanel = new GridBagLayout();
        gbl_datasetsTabPanel.columnWidths = new int[] { 200, 100, 200 };
        gbl_datasetsTabPanel.rowHeights = new int[] { 10, 20, 30, 0, 30, 30,
                30 };
        gbl_datasetsTabPanel.columnWeights = new double[] { 0.0, 0.0, 0.0 };
        gbl_datasetsTabPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 1.0 };
        datasetsTabPanel.setLayout(gbl_datasetsTabPanel);

        Component datasetsTabTopMargin = Box
                .createRigidArea(new Dimension(20, 20));
        GridBagConstraints gbc_datasetsTabTopMargin = new GridBagConstraints();
        gbc_datasetsTabTopMargin.insets = new Insets(0, 0, 5, 5);
        gbc_datasetsTabTopMargin.gridx = 1;
        gbc_datasetsTabTopMargin.gridy = 0;
        datasetsTabPanel.add(datasetsTabTopMargin, gbc_datasetsTabTopMargin);

        JLabel firstDatasetLabel = new JLabel("Dataset A");
        firstDatasetLabel
                .setFont(firstDatasetLabel.getFont().deriveFont(Font.BOLD));
        GridBagConstraints gbc_firstDatasetLabel = new GridBagConstraints();
        gbc_firstDatasetLabel.weightx = 1.0;
        gbc_firstDatasetLabel.insets = new Insets(0, 0, 5, 5);
        gbc_firstDatasetLabel.gridx = 0;
        gbc_firstDatasetLabel.gridy = 1;
        datasetsTabPanel.add(firstDatasetLabel, gbc_firstDatasetLabel);

        JLabel secondDatasetLabel = new JLabel("Dataset B");
        secondDatasetLabel
                .setFont(secondDatasetLabel.getFont().deriveFont(Font.BOLD));
        GridBagConstraints gbc_secondDatasetLabel = new GridBagConstraints();
        gbc_secondDatasetLabel.weightx = 1.0;
        gbc_secondDatasetLabel.insets = new Insets(0, 0, 5, 0);
        gbc_secondDatasetLabel.gridx = 2;
        gbc_secondDatasetLabel.gridy = 1;
        datasetsTabPanel.add(secondDatasetLabel, gbc_secondDatasetLabel);

        JPanel firstDatasetContainer = new JPanel();
        GridBagConstraints gbc_firstDatasetContainer = new GridBagConstraints();
        gbc_firstDatasetContainer.insets = new Insets(0, 0, 5, 5);
        gbc_firstDatasetContainer.fill = GridBagConstraints.HORIZONTAL;
        gbc_firstDatasetContainer.gridx = 0;
        gbc_firstDatasetContainer.gridy = 2;
        datasetsTabPanel.add(firstDatasetContainer, gbc_firstDatasetContainer);
        firstDatasetContainer.setLayout(
                new BoxLayout(firstDatasetContainer, BoxLayout.X_AXIS));

        JLabel firstDatasetWarningLbl = new JLabel(" ");
        firstDatasetContainer.add(firstDatasetWarningLbl);

        firstDatasetField = new JTextField();
        firstDatasetField.setHorizontalAlignment(SwingConstants.TRAILING);
        firstDatasetContainer.add(firstDatasetField);
        firstDatasetField.setColumns(10);

        JButton firstDatasetBtn = new JButton("Select...");
        firstDatasetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String current = firstDatasetField.getText();
                String result = selectDatasetFile(
                        current.isEmpty() ? null : current);
                if (result != null)
                    firstDatasetField.setText(result);
            }
        });
        firstDatasetContainer.add(firstDatasetBtn);

        JLabel fileNameLbl = new JLabel("File");
        GridBagConstraints gbc_fileNameLbl = new GridBagConstraints();
        gbc_fileNameLbl.insets = new Insets(0, 0, 5, 5);
        gbc_fileNameLbl.gridx = 1;
        gbc_fileNameLbl.gridy = 2;
        datasetsTabPanel.add(fileNameLbl, gbc_fileNameLbl);

        JPanel secondDatasetContainer = new JPanel();
        GridBagConstraints gbc_secondDatasetContainer = new GridBagConstraints();
        gbc_secondDatasetContainer.insets = new Insets(0, 0, 5, 0);
        gbc_secondDatasetContainer.fill = GridBagConstraints.HORIZONTAL;
        gbc_secondDatasetContainer.gridx = 2;
        gbc_secondDatasetContainer.gridy = 2;
        datasetsTabPanel.add(secondDatasetContainer,
                gbc_secondDatasetContainer);
        secondDatasetContainer.setLayout(
                new BoxLayout(secondDatasetContainer, BoxLayout.X_AXIS));

        JButton secondDatasetBtn = new JButton("Select...");
        secondDatasetBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String current = secondDatasetField.getText();
                String result = selectDatasetFile(
                        current.isEmpty() ? null : current);
                if (result != null)
                    secondDatasetField.setText(result);
            }
        });
        secondDatasetContainer.add(secondDatasetBtn);

        secondDatasetField = new JTextField();
        secondDatasetField.setHorizontalAlignment(SwingConstants.TRAILING);
        secondDatasetContainer.add(secondDatasetField);
        secondDatasetField.setColumns(10);

        JLabel secondDatasetWarningLbl = new JLabel(" ");
        secondDatasetContainer.add(secondDatasetWarningLbl);

        JPanel firstEncodingContainer = new JPanel();
        GridBagConstraints gbc_firstEncodingContainer = new GridBagConstraints();
        gbc_firstEncodingContainer.fill = GridBagConstraints.HORIZONTAL;
        gbc_firstEncodingContainer.insets = new Insets(0, 0, 5, 5);
        gbc_firstEncodingContainer.gridx = 0;
        gbc_firstEncodingContainer.gridy = 3;
        datasetsTabPanel.add(firstEncodingContainer,
                gbc_firstEncodingContainer);
        firstEncodingContainer.setLayout(
                new BoxLayout(firstEncodingContainer, BoxLayout.X_AXIS));

        firstEncodingWarningLbl = new JLabel(" ");
        firstEncodingContainer.add(firstEncodingWarningLbl);

        firstEncodingField = new JComboBoxWithPlaceholder();
        firstEncodingField.setEditable(true);
        firstEncodingContainer.add(firstEncodingField);

        JLabel encodingLbl = new JLabel("Encoding");
        GridBagConstraints gbc_encodingLbl = new GridBagConstraints();
        gbc_encodingLbl.gridx = 1;
        gbc_encodingLbl.gridy = 3;
        datasetsTabPanel.add(encodingLbl, gbc_encodingLbl);

        JPanel secondEncodingContainer = new JPanel();
        GridBagConstraints gbc_secondEncodingContainer = new GridBagConstraints();
        gbc_secondEncodingContainer.fill = GridBagConstraints.HORIZONTAL;
        gbc_secondEncodingContainer.gridx = 2;
        gbc_secondEncodingContainer.gridy = 3;
        datasetsTabPanel.add(secondEncodingContainer,
                gbc_secondEncodingContainer);
        secondEncodingContainer.setLayout(
                new BoxLayout(secondEncodingContainer, BoxLayout.X_AXIS));

        secondEncodingField = new JComboBoxWithPlaceholder();
        secondEncodingField.setEditable(true);
        secondEncodingContainer.add(secondEncodingField);

        secondEncodingWarningLbl = new JLabel(" ");
        secondEncodingContainer.add(secondEncodingWarningLbl);

        JPanel firstDatasetSuffixContainer = new JPanel();
        GridBagConstraints gbc_firstDatasetSuffixContainer = new GridBagConstraints();
        gbc_firstDatasetSuffixContainer.fill = GridBagConstraints.BOTH;
        gbc_firstDatasetSuffixContainer.insets = new Insets(0, 0, 5, 5);
        gbc_firstDatasetSuffixContainer.gridx = 0;
        gbc_firstDatasetSuffixContainer.gridy = 4;
        datasetsTabPanel.add(firstDatasetSuffixContainer,
                gbc_firstDatasetSuffixContainer);
        firstDatasetSuffixContainer.setLayout(
                new BoxLayout(firstDatasetSuffixContainer, BoxLayout.X_AXIS));

        firstDatasetSuffixWarningLbl = new JLabel(" ");
        firstDatasetSuffixContainer.add(firstDatasetSuffixWarningLbl);

        firstDatasetSuffixField = new JTextFieldWithPlaceholder();
        firstDatasetSuffixContainer.add(firstDatasetSuffixField);
        firstDatasetSuffixField.setColumns(10);

        JLabel suffixLbl = new JLabel("Suffix");
        GridBagConstraints gbc_suffixLbl = new GridBagConstraints();
        gbc_suffixLbl.insets = new Insets(0, 0, 5, 5);
        gbc_suffixLbl.gridx = 1;
        gbc_suffixLbl.gridy = 4;
        datasetsTabPanel.add(suffixLbl, gbc_suffixLbl);

        JPanel secondDatasetSuffixContainer = new JPanel();
        GridBagConstraints gbc_secondDatasetSuffixContainer = new GridBagConstraints();
        gbc_secondDatasetSuffixContainer.fill = GridBagConstraints.BOTH;
        gbc_secondDatasetSuffixContainer.insets = new Insets(0, 0, 5, 0);
        gbc_secondDatasetSuffixContainer.gridx = 2;
        gbc_secondDatasetSuffixContainer.gridy = 4;
        datasetsTabPanel.add(secondDatasetSuffixContainer,
                gbc_secondDatasetSuffixContainer);
        secondDatasetSuffixContainer.setLayout(
                new BoxLayout(secondDatasetSuffixContainer, BoxLayout.X_AXIS));

        secondDatasetSuffixField = new JTextFieldWithPlaceholder();
        secondDatasetSuffixContainer.add(secondDatasetSuffixField);
        secondDatasetSuffixField.setColumns(10);

        secondDatasetSuffixWarningLbl = new JLabel(" ");
        secondDatasetSuffixContainer.add(secondDatasetSuffixWarningLbl);

        JPanel firstDatasetRowNumColContainer = new JPanel();
        GridBagConstraints gbc_firstDatasetRowNumColContainer = new GridBagConstraints();
        gbc_firstDatasetRowNumColContainer.fill = GridBagConstraints.BOTH;
        gbc_firstDatasetRowNumColContainer.insets = new Insets(0, 0, 5, 5);
        gbc_firstDatasetRowNumColContainer.gridx = 0;
        gbc_firstDatasetRowNumColContainer.gridy = 5;
        datasetsTabPanel.add(firstDatasetRowNumColContainer,
                gbc_firstDatasetRowNumColContainer);
        firstDatasetRowNumColContainer.setLayout(new BoxLayout(
                firstDatasetRowNumColContainer, BoxLayout.X_AXIS));

        firstDatasetRowNumColWarningLbl = new JLabel(" ");
        firstDatasetRowNumColContainer.add(firstDatasetRowNumColWarningLbl);

        firstDatasetRowNumColField = new JTextFieldWithPlaceholder();
        firstDatasetRowNumColContainer.add(firstDatasetRowNumColField);
        firstDatasetRowNumColField.setColumns(10);

        JLabel rowNumColLbl = new JLabel("Row number column name");
        GridBagConstraints gbc_rowNumColLbl = new GridBagConstraints();
        gbc_rowNumColLbl.anchor = GridBagConstraints.EAST;
        gbc_rowNumColLbl.insets = new Insets(0, 0, 5, 5);
        gbc_rowNumColLbl.gridx = 1;
        gbc_rowNumColLbl.gridy = 5;
        datasetsTabPanel.add(rowNumColLbl, gbc_rowNumColLbl);

        JPanel secondDatasetRowNumColContainer = new JPanel();
        GridBagConstraints gbc_secondDatasetRowNumColContainer = new GridBagConstraints();
        gbc_secondDatasetRowNumColContainer.fill = GridBagConstraints.BOTH;
        gbc_secondDatasetRowNumColContainer.insets = new Insets(0, 0, 5, 0);
        gbc_secondDatasetRowNumColContainer.gridx = 2;
        gbc_secondDatasetRowNumColContainer.gridy = 5;
        datasetsTabPanel.add(secondDatasetRowNumColContainer,
                gbc_secondDatasetRowNumColContainer);
        secondDatasetRowNumColContainer.setLayout(new BoxLayout(
                secondDatasetRowNumColContainer, BoxLayout.X_AXIS));

        secondDatasetRowNumColField = new JTextFieldWithPlaceholder();
        secondDatasetRowNumColContainer.add(secondDatasetRowNumColField);
        secondDatasetRowNumColField.setColumns(10);

        secondDatasetRowNumColWarningLbl = new JLabel(" ");
        secondDatasetRowNumColContainer.add(secondDatasetRowNumColWarningLbl);

        Component datasetsTabBottomMargin = Box.createVerticalGlue();
        GridBagConstraints gbc_datasetsTabBottomMargin = new GridBagConstraints();
        gbc_datasetsTabBottomMargin.fill = GridBagConstraints.VERTICAL;
        gbc_datasetsTabBottomMargin.insets = new Insets(0, 0, 0, 5);
        gbc_datasetsTabBottomMargin.gridx = 1;
        gbc_datasetsTabBottomMargin.gridy = 6;
        datasetsTabPanel.add(datasetsTabBottomMargin,
                gbc_datasetsTabBottomMargin);

        optionsTabPanel = new JPanel();
        tabbedPane.addTab("Options", null, optionsTabPanel, null);
        GridBagLayout gbl_optionsTabPanel = new GridBagLayout();
        gbl_optionsTabPanel.columnWidths = new int[] { 0, 0, 0 };
        gbl_optionsTabPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
        gbl_optionsTabPanel.columnWeights = new double[] { 0.0, 1.0,
                Double.MIN_VALUE };
        gbl_optionsTabPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
                Double.MIN_VALUE };
        optionsTabPanel.setLayout(gbl_optionsTabPanel);

        Component optionsTabTopMargin = Box
                .createRigidArea(new Dimension(20, 20));
        GridBagConstraints gbc_optionsTabTopMargin = new GridBagConstraints();
        gbc_optionsTabTopMargin.insets = new Insets(0, 0, 5, 5);
        gbc_optionsTabTopMargin.gridx = 0;
        gbc_optionsTabTopMargin.gridy = 0;
        optionsTabPanel.add(optionsTabTopMargin, gbc_optionsTabTopMargin);

        JLabel linkageDirLbl = new JLabel("Linkage location");
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
        linkageDirContainer.setLayout(
                new BoxLayout(linkageDirContainer, BoxLayout.X_AXIS));

        JLabel linkageDirWarningLbl = new JLabel(" ");
        linkageDirContainer.add(linkageDirWarningLbl);

        linkageDirField = new JTextFieldWithPlaceholder();
        linkageDirField.setHorizontalAlignment(SwingConstants.TRAILING);
        linkageDirContainer.add(linkageDirField);
        linkageDirField.setColumns(10);

        JButton linkageDirBtn = new JButton("Select...");
        linkageDirBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        linkageDirContainer.add(linkageDirBtn);

        SpinnerNumberModel minScoreModel = new SpinnerNumberModel(0.0, 0.0, 100,
                0.001);

        SpinnerNumberModel maxRowsModel = new SpinnerNumberModel(
                Integer.MAX_VALUE, 0, Integer.MAX_VALUE, 1);

        JLabel indexDirLbl = new JLabel("Index location");
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
        indexDirContainer
                .setLayout(new BoxLayout(indexDirContainer, BoxLayout.X_AXIS));

        JLabel indexDirWarningLbl = new JLabel(" ");
        indexDirContainer.add(indexDirWarningLbl);

        indexDirField = new JTextFieldWithPlaceholder();
        indexDirField.setHorizontalAlignment(SwingConstants.TRAILING);
        indexDirContainer.add(indexDirField);
        indexDirField.setColumns(10);

        JButton indexDirBtn = new JButton("Select...");
        indexDirBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        indexDirContainer.add(indexDirBtn);

        JLabel minScoreLbl = new JLabel("Minimum score");
        minScoreLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        GridBagConstraints gbc_minScoreLbl = new GridBagConstraints();
        gbc_minScoreLbl.fill = GridBagConstraints.HORIZONTAL;
        gbc_minScoreLbl.insets = new Insets(0, 0, 5, 5);
        gbc_minScoreLbl.gridx = 0;
        gbc_minScoreLbl.gridy = 3;
        optionsTabPanel.add(minScoreLbl, gbc_minScoreLbl);
        minScoreField = new JSpinnerWithBlankValue(minScoreModel);
        ((JSpinnerWithBlankValue) minScoreField)
                .setBlankValue(Double.valueOf(0.0));
        GridBagConstraints gbc_minScoreField = new GridBagConstraints();
        gbc_minScoreField.anchor = GridBagConstraints.WEST;
        gbc_minScoreField.insets = new Insets(0, 0, 5, 0);
        gbc_minScoreField.gridx = 1;
        gbc_minScoreField.gridy = 3;
        optionsTabPanel.add(minScoreField, gbc_minScoreField);

        JSpinner.NumberEditor ne_minScoreField = new JSpinner.NumberEditor(
                minScoreField, "0.000");
        ne_minScoreField.setPreferredSize(new Dimension(122, 29));
        ne_minScoreField.setRequestFocusEnabled(false);
        minScoreField.setEditor(ne_minScoreField);

        JLabel maxRowsLbl = new JLabel("Only read first rows (A)");
        maxRowsLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        maxRowsLbl.setPreferredSize(new Dimension(150, 17));
        GridBagConstraints gbc_maxRowsLbl = new GridBagConstraints();
        gbc_maxRowsLbl.insets = new Insets(0, 0, 0, 5);
        gbc_maxRowsLbl.gridx = 0;
        gbc_maxRowsLbl.gridy = 4;
        optionsTabPanel.add(maxRowsLbl, gbc_maxRowsLbl);
        maxRowsField = new JSpinnerWithBlankValue(maxRowsModel);
        ((JSpinnerWithBlankValue) maxRowsField)
                .setBlankValue(Integer.MAX_VALUE);
        GridBagConstraints gbc_maxRowsField = new GridBagConstraints();
        gbc_maxRowsField.anchor = GridBagConstraints.WEST;
        gbc_maxRowsField.gridx = 1;
        gbc_maxRowsField.gridy = 4;
        optionsTabPanel.add(maxRowsField, gbc_maxRowsField);

        JSpinner.NumberEditor ne_maxRowsField = new JSpinner.NumberEditor(
                maxRowsField, "0");
        maxRowsField.setEditor(ne_maxRowsField);

        JPanel linkageColsTabPanel = new JPanel();
        tabbedPane.addTab("Linkage columns", null, linkageColsTabPanel, null);

        linkageColsTable = new JTable();
        linkageColsTable.setShowVerticalLines(true);
        linkageColsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        DefaultTableModel model = new DefaultTableModel() {

            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        model.addColumn("Number");
        model.addColumn("ID");
        model.addColumn("Type");
        model.addColumn("Weight");
        model.addColumn("Phon. weight");
        model.addColumn("Column (A)");
        model.addColumn("Renamed (A)");
        model.addColumn("Column (B)");
        model.addColumn("Renamed (B)", new Object[] { "item1", "item2", "item3",
                "item4", "item5", "item6", "item7", "item8", "item9", "item10",
                "item11", "item12", "item13", "item14", "item15", "item16",
                "item17", "item18", "item19", "item20", "item1", "item2",
                "item3", "item4", "item5", "item6", "item7", "item8", "item9",
                "item10", "item11", "item12", "item13", "item14", "item15",
                "item16", "item17", "item18", "item19", "item20", "item1",
                "item2", "item3", "item4", "item5", "item6", "item7", "item8",
                "item9", "item10", "item11", "item12", "item13", "item14",
                "item15", "item16", "item17", "item18", "item19", "item20" });
        linkageColsTabPanel.setLayout(new BorderLayout(0, 0));
        linkageColsTable.setModel(model);

        JScrollPane linkageColsScrollPane = new JScrollPane(linkageColsTable);
        linkageColsTabPanel.add(linkageColsScrollPane);

        LinkageColumnPanel linkageColsEditingPanel = new LinkageColumnPanel();
        linkageColsTabPanel.add(linkageColsEditingPanel, BorderLayout.SOUTH);

        JPanel extraColsTabPanel = new JPanel();
        tabbedPane.addTab("Extra columns", null, extraColsTabPanel, null);
        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        newFileMenuItem = new JMenuItem("New");
        fileMenu.add(newFileMenuItem);

        JSeparator separator_1 = new JSeparator();
        fileMenu.add(separator_1);

        openFileMenuItem = new JMenuItem("Open...");
        fileMenu.add(openFileMenuItem);

        JSeparator separator_2 = new JSeparator();
        fileMenu.add(separator_2);

        saveFileMenuItem = new JMenuItem("Save");
        fileMenu.add(saveFileMenuItem);

        saveAsFileMenuItem = new JMenuItem("Save as...");
        fileMenu.add(saveAsFileMenuItem);

        JSeparator separator = new JSeparator();
        fileMenu.add(separator);

        exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(exitMenuItem);

        JMenu editMenu = new JMenu("Edit");
        menuBar.add(editMenu);

        undoMenuItem = new JMenuItem("Undo");
        undoMenuItem.setEnabled(false);
        editMenu.add(undoMenuItem);

        redoMenuItem = new JMenuItem("Redo");
        redoMenuItem.setEnabled(false);
        editMenu.add(redoMenuItem);

        helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);

        aboutMenuItem = new JMenuItem("About");
        helpMenu.add(aboutMenuItem);

        languageCbox = new JComboBox<>();
        languageCbox.setMaximumSize(new Dimension(1000, 32767));
        languageCbox.setModel(
                new DefaultComboBoxModel<>(new String[] { "English" }));
        menuBar.add(languageCbox);

        Component menuGlue = Box.createGlue();
        menuBar.add(menuGlue);

        currentFileLbl = new JLabel("[Unsaved file]");
        currentFileLbl
                .setFont(currentFileLbl.getFont().deriveFont(Font.ITALIC));
        currentFileLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        menuBar.add(currentFileLbl);

        Component currentFileSpacer = Box
                .createRigidArea(new Dimension(20, 20));
        menuBar.add(currentFileSpacer);

        ToolTipManager.sharedInstance().setInitialDelay(100);

        frame.pack();

        JLabel[] warningLabels = { firstDatasetWarningLbl,
                secondDatasetWarningLbl, firstDatasetSuffixWarningLbl,
                secondDatasetSuffixWarningLbl, firstDatasetRowNumColWarningLbl,
                secondDatasetRowNumColWarningLbl, indexDirWarningLbl,
                linkageDirWarningLbl, firstEncodingWarningLbl,
                secondEncodingWarningLbl };
        Icon warningIcon = UIManager.getIcon("OptionPane.errorIcon");
        for (JLabel label : warningLabels)
            setLabelIcon(label, warningIcon, false);
    }

    private String selectDatasetFile(String currentFileName) {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(currentFileName != null
                ? new File(currentFileName).getParentFile()
                : new File(".").getAbsoluteFile());
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Supported datasets (csv, dbf)", "csv", "dbf");
        chooser.setFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            Path here = Paths.get(".").toAbsolutePath().getParent();
            String f = chooser.getSelectedFile().getAbsolutePath();
            if (f.startsWith(here.toString() + File.separator))
                return here.relativize(Paths.get(f)).toString();
            else
                return f;
        }
        return null;
    }

    private String selectConfigFile(String fileName) {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(
                fileName != null ? new File(fileName).getParentFile()
                        : new File(".").getAbsoluteFile());
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Properties file (properties)", "properties", "ini");
        chooser.setFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile().getAbsolutePath();
        }
        return null;
    }

    private void setLabelIcon(JLabel label, Icon icon, boolean visible) {
        BufferedImage bufferedImage = new BufferedImage(icon.getIconWidth(),
                icon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        icon.paintIcon(null, g, 0, 0);
        g.dispose();
        int d = label.getSize().height; // square
        label.setIcon(new ImageIcon(
                bufferedImage.getScaledInstance(d, d, Image.SCALE_SMOOTH)));
        label.setText("");
        label.setVisible(visible);
    }

    private void updateConfigFileLabel() {
        currentFileLbl.setText((dirty ? "[*] " : "")
                + (currentFileName == null ? "[Unsaved file]"
                        : currentFileName));
    }

    private void fillEncodings(JComboBox<String>[] comboboxes) {
        Set<String> allEncodings = new HashSet<>();
        for (Entry<String, Charset> entry : Charset.availableCharsets()
                .entrySet()) {
            allEncodings.add(entry.getKey());
            allEncodings.addAll(entry.getValue().aliases().stream()
                    .filter(enc -> enc.length() <= 10)
                    .collect(Collectors.toList()));
        }
        List<String> sortedEncodings = new ArrayList<>(allEncodings);
        Collections.sort(sortedEncodings, new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                if (Character.isDigit(o1.charAt(0)) == Character
                        .isDigit(o2.charAt(0)))
                    return o1.compareToIgnoreCase(o2);
                return Character.isDigit(o1.charAt(0)) ? 1 : -1;
            }
        });
        sortedEncodings.add(0, "ANSI");
        sortedEncodings.add(0, "UTF-8"); // add again to the beginning
        for (JComboBox<String> cb : comboboxes)
            for (String enc : sortedEncodings)
                cb.addItem(enc);

    }
}
