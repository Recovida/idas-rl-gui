package com.cidacs.rl.editor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
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
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import com.cidacs.rl.editor.gui.LinkageColumnPanel;

/**
 *
 */

/**
 *
 */
public class MainWindow {

    private JFrame frame;
    private JTextField firstDatasetSuffixField;
    private JTextField secondDatasetSuffixField;
    private JTextField firstDatasetRowNumColField;
    private JTextField secondDatasetRowNumColField;
    private JTextField firstDatasetField;
    private JTextField secondDatasetField;
    private JTable linkageColsTable;
    private JTextField linkageDirField;
    private JTextField indexDirField;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
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
//        try {
//            String os = System.getProperty("os.name");
//            if (os != null && os.toUpperCase().startsWith("LINUX"))
//                UIManager.setLookAndFeel(
//                        "com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
//            else
//                UIManager.setLookAndFeel(
//                        UIManager.getSystemLookAndFeelClassName());
//        } catch (Exception e) {
//        }
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
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setMinimumSize(new Dimension(700, 400));
        frame.setBounds(100, 100, 586, 377);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        JPanel datasetsTabPanel = new JPanel();
        tabbedPane.addTab("Datasets", null, datasetsTabPanel, null);
        GridBagLayout gbl_datasetsTabPanel = new GridBagLayout();
        gbl_datasetsTabPanel.columnWidths = new int[] { 200, 100, 200 };
        gbl_datasetsTabPanel.rowHeights = new int[] { 10, 20, 30, 30, 30, 30 };
        gbl_datasetsTabPanel.columnWeights = new double[] { 1.0, 0.0, 1.0 };
        gbl_datasetsTabPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
                0.0, 1.0 };
        datasetsTabPanel.setLayout(gbl_datasetsTabPanel);

        Component datasetsTabTopMargin = Box
                .createRigidArea(new Dimension(20, 20));
        GridBagConstraints gbc_datasetsTabTopMargin = new GridBagConstraints();
        gbc_datasetsTabTopMargin.insets = new Insets(0, 0, 5, 5);
        gbc_datasetsTabTopMargin.gridx = 1;
        gbc_datasetsTabTopMargin.gridy = 0;
        datasetsTabPanel.add(datasetsTabTopMargin, gbc_datasetsTabTopMargin);

        JLabel firstDatasetLabel = new JLabel("<html><b>Dataset A");
        GridBagConstraints gbc_firstDatasetLabel = new GridBagConstraints();
        gbc_firstDatasetLabel.weightx = 1.0;
        gbc_firstDatasetLabel.insets = new Insets(0, 0, 5, 5);
        gbc_firstDatasetLabel.gridx = 0;
        gbc_firstDatasetLabel.gridy = 1;
        datasetsTabPanel.add(firstDatasetLabel, gbc_firstDatasetLabel);

        JLabel secondDatasetLabel = new JLabel("<html><b>Dataset B");
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

        firstDatasetField = new JTextField();
        firstDatasetField.setHorizontalAlignment(SwingConstants.TRAILING);
        firstDatasetContainer.add(firstDatasetField);
        firstDatasetField.setColumns(10);

        JButton firstDatasetBtn = new JButton("Select...");
        firstDatasetBtn.addActionListener(new ActionListener() {
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

        firstDatasetSuffixField = new JTextField();
        GridBagConstraints gbc_firstDatasetSuffixField = new GridBagConstraints();
        gbc_firstDatasetSuffixField.insets = new Insets(0, 0, 5, 5);
        gbc_firstDatasetSuffixField.fill = GridBagConstraints.HORIZONTAL;
        gbc_firstDatasetSuffixField.gridx = 0;
        gbc_firstDatasetSuffixField.gridy = 3;
        datasetsTabPanel.add(firstDatasetSuffixField,
                gbc_firstDatasetSuffixField);
        firstDatasetSuffixField.setColumns(10);

        JLabel suffixLbl = new JLabel("Suffix");
        GridBagConstraints gbc_suffixLbl = new GridBagConstraints();
        gbc_suffixLbl.insets = new Insets(0, 0, 5, 5);
        gbc_suffixLbl.gridx = 1;
        gbc_suffixLbl.gridy = 3;
        datasetsTabPanel.add(suffixLbl, gbc_suffixLbl);

        secondDatasetSuffixField = new JTextField();
        GridBagConstraints gbc_secondDatasetSuffixField = new GridBagConstraints();
        gbc_secondDatasetSuffixField.insets = new Insets(0, 0, 5, 0);
        gbc_secondDatasetSuffixField.fill = GridBagConstraints.HORIZONTAL;
        gbc_secondDatasetSuffixField.gridx = 2;
        gbc_secondDatasetSuffixField.gridy = 3;
        datasetsTabPanel.add(secondDatasetSuffixField,
                gbc_secondDatasetSuffixField);
        secondDatasetSuffixField.setColumns(10);

        firstDatasetRowNumColField = new JTextField();
        GridBagConstraints gbc_firstDatasetRowNumColField = new GridBagConstraints();
        gbc_firstDatasetRowNumColField.insets = new Insets(0, 0, 5, 5);
        gbc_firstDatasetRowNumColField.fill = GridBagConstraints.HORIZONTAL;
        gbc_firstDatasetRowNumColField.gridx = 0;
        gbc_firstDatasetRowNumColField.gridy = 4;
        datasetsTabPanel.add(firstDatasetRowNumColField,
                gbc_firstDatasetRowNumColField);
        firstDatasetRowNumColField.setColumns(10);

        JLabel rowNumColLbl = new JLabel("Row number column name");
        GridBagConstraints gbc_rowNumColLbl = new GridBagConstraints();
        gbc_rowNumColLbl.anchor = GridBagConstraints.EAST;
        gbc_rowNumColLbl.insets = new Insets(0, 0, 5, 5);
        gbc_rowNumColLbl.gridx = 1;
        gbc_rowNumColLbl.gridy = 4;
        datasetsTabPanel.add(rowNumColLbl, gbc_rowNumColLbl);

        secondDatasetRowNumColField = new JTextField();
        GridBagConstraints gbc_secondDatasetRowNumColField = new GridBagConstraints();
        gbc_secondDatasetRowNumColField.insets = new Insets(0, 0, 5, 0);
        gbc_secondDatasetRowNumColField.fill = GridBagConstraints.HORIZONTAL;
        gbc_secondDatasetRowNumColField.gridx = 2;
        gbc_secondDatasetRowNumColField.gridy = 4;
        datasetsTabPanel.add(secondDatasetRowNumColField,
                gbc_secondDatasetRowNumColField);
        secondDatasetRowNumColField.setColumns(10);

        Component datasetsTabBottomMargin = Box.createVerticalGlue();
        GridBagConstraints gbc_datasetsTabBottomMargin = new GridBagConstraints();
        gbc_datasetsTabBottomMargin.fill = GridBagConstraints.VERTICAL;
        gbc_datasetsTabBottomMargin.insets = new Insets(0, 0, 0, 5);
        gbc_datasetsTabBottomMargin.gridx = 1;
        gbc_datasetsTabBottomMargin.gridy = 5;
        datasetsTabPanel.add(datasetsTabBottomMargin,
                gbc_datasetsTabBottomMargin);

        JPanel optionsTabPanel = new JPanel();
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

        linkageDirField = new JTextField();
        linkageDirField.setHorizontalAlignment(SwingConstants.TRAILING);
        linkageDirContainer.add(linkageDirField);
        linkageDirField.setColumns(10);

        JButton linkageDirBtn = new JButton("Select...");
        linkageDirBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        linkageDirContainer.add(linkageDirBtn);

        SpinnerNumberModel minScoreModel = new SpinnerNumberModel(1.0, 0.0,
                Double.MAX_VALUE, 0.01);

        SpinnerNumberModel maxRowsModel = new SpinnerNumberModel(2000000000, 0,
                2000000000, 1);

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

        indexDirField = new JTextField();
        indexDirField.setHorizontalAlignment(SwingConstants.TRAILING);
        indexDirContainer.add(indexDirField);
        indexDirField.setColumns(10);

        JButton indexDirBtn = new JButton("Select...");
        indexDirBtn.addActionListener(new ActionListener() {
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
        JSpinner minScoreField = new JSpinner(minScoreModel);
        GridBagConstraints gbc_minScoreField = new GridBagConstraints();
        gbc_minScoreField.anchor = GridBagConstraints.WEST;
        gbc_minScoreField.insets = new Insets(0, 0, 5, 0);
        gbc_minScoreField.gridx = 1;
        gbc_minScoreField.gridy = 3;
        optionsTabPanel.add(minScoreField, gbc_minScoreField);

        JSpinner.NumberEditor ne_minScoreField = new JSpinner.NumberEditor(
                minScoreField, "0.0000000");
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
        JSpinner maxRowsField = new JSpinner(maxRowsModel);
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

        JMenuItem newFileMenuItem = new JMenuItem("New");
        fileMenu.add(newFileMenuItem);

        JSeparator separator_1 = new JSeparator();
        fileMenu.add(separator_1);

        JMenuItem openFileMenuItem = new JMenuItem("Open...");
        fileMenu.add(openFileMenuItem);

        JSeparator separator_2 = new JSeparator();
        fileMenu.add(separator_2);

        JMenuItem saveFileMenuItem = new JMenuItem("Save");
        fileMenu.add(saveFileMenuItem);

        JMenuItem saveAsFileMenuItem = new JMenuItem("Save as...");
        fileMenu.add(saveAsFileMenuItem);

        JSeparator separator = new JSeparator();
        fileMenu.add(separator);

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(exitMenuItem);

        JMenu editMenu = new JMenu("Edit");
        menuBar.add(editMenu);

        JMenuItem undoMenuItem = new JMenuItem("Undo");
        editMenu.add(undoMenuItem);

        JMenuItem redoMenuItem = new JMenuItem("Redo");
        editMenu.add(redoMenuItem);

        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);

        JMenuItem aboutMenuItem = new JMenuItem("About");
        helpMenu.add(aboutMenuItem);

        Component menuGlue = Box.createGlue();
        menuBar.add(menuGlue);

        JLabel currentFileLbl = new JLabel("<html><i>[New file] *");
        currentFileLbl.setHorizontalAlignment(SwingConstants.TRAILING);
        menuBar.add(currentFileLbl);

        Component currentFileSpacer = Box
                .createRigidArea(new Dimension(20, 20));
        menuBar.add(currentFileSpacer);

        new StringSettingItem("db_a", "", "", firstDatasetField);
        new StringSettingItem("db_b", "", "", secondDatasetField);
        new StringSettingItem("suffix_a", "_dsa", "_dsa",
                firstDatasetSuffixField).setReplaceBlankWithDefaultValue(true);
        new StringSettingItem("suffix_b", "_dsb", "_dsb",
                secondDatasetSuffixField).setReplaceBlankWithDefaultValue(true);
        new StringSettingItem("row_num_col_a", "#A", "#A",
                firstDatasetRowNumColField)
                        .setReplaceBlankWithDefaultValue(true);
        new StringSettingItem("row_num_col_b", "#B", "#B",
                secondDatasetRowNumColField)
                        .setReplaceBlankWithDefaultValue(true);
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
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            Path here = Paths.get(".").toAbsolutePath().getParent();
            String f = chooser.getSelectedFile().getAbsolutePath();
            if (f.startsWith(here.toString() + File.separator))
                return here.relativize(Paths.get(f)).toString();
            else
                return f;
        }
        return null;
    }

}
