import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;

/**
 *
 */

/**
 *
 */
public class MainWindow {

    private JFrame frame;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;
    private JTextField textField_5;
    private JTable table;
    private JTextField textField_6;
    private JTextField textField_7;

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
        try {
            String os = System.getProperty("os.name");
            if (os != null && os.toUpperCase().startsWith("LINUX"))
                UIManager.setLookAndFeel(
                        "com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
            else
                UIManager.setLookAndFeel(
                        UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setMinimumSize(new Dimension(500, 300));
        frame.setBounds(100, 100, 611, 389);
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

        Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
        GridBagConstraints gbc_rigidArea = new GridBagConstraints();
        gbc_rigidArea.insets = new Insets(0, 0, 5, 5);
        gbc_rigidArea.gridx = 1;
        gbc_rigidArea.gridy = 0;
        datasetsTabPanel.add(rigidArea, gbc_rigidArea);

        JLabel lblNewLabel_1 = new JLabel("A");
        GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
        gbc_lblNewLabel_1.weightx = 1.0;
        gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_1.gridx = 0;
        gbc_lblNewLabel_1.gridy = 1;
        datasetsTabPanel.add(lblNewLabel_1, gbc_lblNewLabel_1);

        JLabel lblNewLabel_2 = new JLabel("B");
        GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
        gbc_lblNewLabel_2.weightx = 1.0;
        gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 0);
        gbc_lblNewLabel_2.gridx = 2;
        gbc_lblNewLabel_2.gridy = 1;
        datasetsTabPanel.add(lblNewLabel_2, gbc_lblNewLabel_2);

        JPanel panel = new JPanel();
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.insets = new Insets(0, 0, 5, 5);
        gbc_panel.fill = GridBagConstraints.HORIZONTAL;
        gbc_panel.gridx = 0;
        gbc_panel.gridy = 2;
        datasetsTabPanel.add(panel, gbc_panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        textField_4 = new JTextField();
        panel.add(textField_4);
        textField_4.setColumns(10);

        JButton btnNewButton = new JButton("Select...");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        panel.add(btnNewButton);

        JLabel lblNewLabel_4 = new JLabel("File");
        GridBagConstraints gbc_lblNewLabel_4 = new GridBagConstraints();
        gbc_lblNewLabel_4.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_4.gridx = 1;
        gbc_lblNewLabel_4.gridy = 2;
        datasetsTabPanel.add(lblNewLabel_4, gbc_lblNewLabel_4);

        JPanel panel_3 = new JPanel();
        GridBagConstraints gbc_panel_3 = new GridBagConstraints();
        gbc_panel_3.insets = new Insets(0, 0, 5, 0);
        gbc_panel_3.fill = GridBagConstraints.HORIZONTAL;
        gbc_panel_3.gridx = 2;
        gbc_panel_3.gridy = 2;
        datasetsTabPanel.add(panel_3, gbc_panel_3);
        panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));

        JButton btnNewButton_1 = new JButton("Select...");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        panel_3.add(btnNewButton_1);

        textField_5 = new JTextField();
        panel_3.add(textField_5);
        textField_5.setColumns(10);

        textField = new JTextField();
        GridBagConstraints gbc_textField = new GridBagConstraints();
        gbc_textField.insets = new Insets(0, 0, 5, 5);
        gbc_textField.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField.gridx = 0;
        gbc_textField.gridy = 3;
        datasetsTabPanel.add(textField, gbc_textField);
        textField.setColumns(10);

        JLabel lblNewLabel_5 = new JLabel("Suffix");
        GridBagConstraints gbc_lblNewLabel_5 = new GridBagConstraints();
        gbc_lblNewLabel_5.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_5.gridx = 1;
        gbc_lblNewLabel_5.gridy = 3;
        datasetsTabPanel.add(lblNewLabel_5, gbc_lblNewLabel_5);

        textField_1 = new JTextField();
        GridBagConstraints gbc_textField_1 = new GridBagConstraints();
        gbc_textField_1.insets = new Insets(0, 0, 5, 0);
        gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_1.gridx = 2;
        gbc_textField_1.gridy = 3;
        datasetsTabPanel.add(textField_1, gbc_textField_1);
        textField_1.setColumns(10);

        textField_2 = new JTextField();
        GridBagConstraints gbc_textField_2 = new GridBagConstraints();
        gbc_textField_2.insets = new Insets(0, 0, 5, 5);
        gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_2.gridx = 0;
        gbc_textField_2.gridy = 4;
        datasetsTabPanel.add(textField_2, gbc_textField_2);
        textField_2.setColumns(10);

        JLabel lblNewLabel_6 = new JLabel("Row number column name");
        GridBagConstraints gbc_lblNewLabel_6 = new GridBagConstraints();
        gbc_lblNewLabel_6.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_6.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_6.gridx = 1;
        gbc_lblNewLabel_6.gridy = 4;
        datasetsTabPanel.add(lblNewLabel_6, gbc_lblNewLabel_6);

        textField_3 = new JTextField();
        GridBagConstraints gbc_textField_3 = new GridBagConstraints();
        gbc_textField_3.insets = new Insets(0, 0, 5, 0);
        gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_3.gridx = 2;
        gbc_textField_3.gridy = 4;
        datasetsTabPanel.add(textField_3, gbc_textField_3);
        textField_3.setColumns(10);

        Component verticalGlue = Box.createVerticalGlue();
        GridBagConstraints gbc_verticalGlue = new GridBagConstraints();
        gbc_verticalGlue.fill = GridBagConstraints.VERTICAL;
        gbc_verticalGlue.insets = new Insets(0, 0, 0, 5);
        gbc_verticalGlue.gridx = 1;
        gbc_verticalGlue.gridy = 5;
        datasetsTabPanel.add(verticalGlue, gbc_verticalGlue);

        JPanel panel_4 = new JPanel();
        tabbedPane.addTab("Options", null, panel_4, null);
        GridBagLayout gbl_panel_4 = new GridBagLayout();
        gbl_panel_4.columnWidths = new int[] { 0, 0, 0 };
        gbl_panel_4.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
        gbl_panel_4.columnWeights = new double[] { 0.0, 1.0, Double.MIN_VALUE };
        gbl_panel_4.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0,
                Double.MIN_VALUE };
        panel_4.setLayout(gbl_panel_4);

        Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
        GridBagConstraints gbc_rigidArea_1 = new GridBagConstraints();
        gbc_rigidArea_1.insets = new Insets(0, 0, 5, 5);
        gbc_rigidArea_1.gridx = 0;
        gbc_rigidArea_1.gridy = 0;
        panel_4.add(rigidArea_1, gbc_rigidArea_1);

        JLabel lblNewLabel_3 = new JLabel("Linkage location");
        GridBagConstraints gbc_lblNewLabel_3 = new GridBagConstraints();
        gbc_lblNewLabel_3.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_3.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_3.gridx = 0;
        gbc_lblNewLabel_3.gridy = 1;
        panel_4.add(lblNewLabel_3, gbc_lblNewLabel_3);

        JPanel panel_5 = new JPanel();
        GridBagConstraints gbc_panel_5 = new GridBagConstraints();
        gbc_panel_5.fill = GridBagConstraints.BOTH;
        gbc_panel_5.insets = new Insets(0, 0, 5, 0);
        gbc_panel_5.gridx = 1;
        gbc_panel_5.gridy = 1;
        panel_4.add(panel_5, gbc_panel_5);
        panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.X_AXIS));

        textField_6 = new JTextField();
        panel_5.add(textField_6);
        textField_6.setColumns(10);

        JButton btnNewButton_2 = new JButton("Select...");
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        panel_5.add(btnNewButton_2);

        SpinnerNumberModel minScoreModel = new SpinnerNumberModel(1.0, 0.0,
                Double.MAX_VALUE, 0.01);

        SpinnerNumberModel maxRowsModel = new SpinnerNumberModel(2000000000, 0,
                2000000000, 1);

        JLabel lblNewLabel_7 = new JLabel("Index location");
        GridBagConstraints gbc_lblNewLabel_7 = new GridBagConstraints();
        gbc_lblNewLabel_7.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_7.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_7.gridx = 0;
        gbc_lblNewLabel_7.gridy = 2;
        panel_4.add(lblNewLabel_7, gbc_lblNewLabel_7);

        JPanel panel_6 = new JPanel();
        GridBagConstraints gbc_panel_6 = new GridBagConstraints();
        gbc_panel_6.fill = GridBagConstraints.BOTH;
        gbc_panel_6.insets = new Insets(0, 0, 5, 0);
        gbc_panel_6.gridx = 1;
        gbc_panel_6.gridy = 2;
        panel_4.add(panel_6, gbc_panel_6);
        panel_6.setLayout(new BoxLayout(panel_6, BoxLayout.X_AXIS));

        textField_7 = new JTextField();
        panel_6.add(textField_7);
        textField_7.setColumns(10);

        JButton btnNewButton_3 = new JButton("Select...");
        btnNewButton_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        panel_6.add(btnNewButton_3);

        JLabel lblNewLabel_8 = new JLabel("Minimum score");
        GridBagConstraints gbc_lblNewLabel_8 = new GridBagConstraints();
        gbc_lblNewLabel_8.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_8.gridx = 0;
        gbc_lblNewLabel_8.gridy = 3;
        panel_4.add(lblNewLabel_8, gbc_lblNewLabel_8);
        JSpinner spinner = new JSpinner(minScoreModel);
        GridBagConstraints gbc_spinner = new GridBagConstraints();
        gbc_spinner.anchor = GridBagConstraints.WEST;
        gbc_spinner.insets = new Insets(0, 0, 5, 0);
        gbc_spinner.gridx = 1;
        gbc_spinner.gridy = 3;
        panel_4.add(spinner, gbc_spinner);

        JSpinner.NumberEditor minScoreNumberEditor = new JSpinner.NumberEditor(
                spinner, "0.0000000");
        minScoreNumberEditor.setPreferredSize(new Dimension(117, 21));
        minScoreNumberEditor.setRequestFocusEnabled(false);
        spinner.setEditor(minScoreNumberEditor);

        JLabel lblNewLabel_9 = new JLabel("Only read first rows (A)");
        GridBagConstraints gbc_lblNewLabel_9 = new GridBagConstraints();
        gbc_lblNewLabel_9.insets = new Insets(0, 0, 0, 5);
        gbc_lblNewLabel_9.gridx = 0;
        gbc_lblNewLabel_9.gridy = 4;
        panel_4.add(lblNewLabel_9, gbc_lblNewLabel_9);
        JSpinner spinner_1 = new JSpinner(maxRowsModel);
        GridBagConstraints gbc_spinner_1 = new GridBagConstraints();
        gbc_spinner_1.anchor = GridBagConstraints.WEST;
        gbc_spinner_1.gridx = 1;
        gbc_spinner_1.gridy = 4;
        panel_4.add(spinner_1, gbc_spinner_1);

        JSpinner.NumberEditor maxRowsNumberEditor = new JSpinner.NumberEditor(
                spinner_1, "0");
        spinner_1.setEditor(maxRowsNumberEditor);

        JPanel panel_1 = new JPanel();
        tabbedPane.addTab("Linkage columns", null, panel_1, null);
        panel_1.setLayout(new BorderLayout(0, 0));

        table = new JTable();
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addColumn("Number");
        model.addColumn("ID");
        model.addColumn("Type");
        model.addColumn("Weight");
        model.addColumn("Phon. weight");
        model.addColumn("Column name");
        model.addColumn("Renamed");
        model.addColumn("Column name");
        model.addColumn("Renamed", new Object[] { "item1" });

        JScrollPane scrollPane = new JScrollPane(table);
        panel_1.add(scrollPane, BorderLayout.CENTER);

        JPanel panel_2 = new JPanel();
        tabbedPane.addTab("Extra columns", null, panel_2, null);
        frame.getContentPane().add(tabbedPane, BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu mnNewMenu = new JMenu("File");
        menuBar.add(mnNewMenu);

        JMenuItem mntmNewMenuItem = new JMenuItem("New");
        mnNewMenu.add(mntmNewMenuItem);

        JMenuItem mntmNewMenuItem_1 = new JMenuItem("Open...");
        mnNewMenu.add(mntmNewMenuItem_1);

        JMenuItem mntmNewMenuItem_2 = new JMenuItem("Save");
        mnNewMenu.add(mntmNewMenuItem_2);

        JMenuItem mntmNewMenuItem_3 = new JMenuItem("Save as...");
        mnNewMenu.add(mntmNewMenuItem_3);

        JMenuItem mntmExit = new JMenuItem("Exit");
        mnNewMenu.add(mntmExit);

        Component glue = Box.createGlue();
        menuBar.add(glue);

        JLabel lblNewLabel = new JLabel("New label");
        lblNewLabel.setFont(new Font("Dialog", Font.ITALIC, 12));
        menuBar.add(lblNewLabel);

        Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));
        menuBar.add(rigidArea_2);
    }

}
