package recovida.idas.rl.gui.ui.container;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import recovida.idas.rl.core.io.write.CSVDatasetWriter;
import recovida.idas.rl.core.io.write.DatasetWriter;
import recovida.idas.rl.core.util.StatusReporter.StatusLogger;
import recovida.idas.rl.gui.lang.MessageProvider;
import recovida.idas.rl.gui.ui.Translatable;

/**
 * A panel to display information about a specific execution of the algorithm.
 */
public class ExecutionInnerPanel extends JPanel
        implements StatusLogger, Translatable {
    private static final long serialVersionUID = -1501370560575937471L;

    private JTable table;

    private JButton copyLogBtn;

    private JButton saveLogBtn;

    private DefaultTableModel model;

    private JProgressBar progressBar;

    private JButton openDirBtn;

    /**
     * Represents the status of the log export (copy/save) operation.
     */
    protected enum LogExportStatus {
        NONE, DOING, DONE, FAILED
    }

    private LogExportStatus logCopyingStatus = LogExportStatus.NONE;

    private LogExportStatus logSavingStatus = LogExportStatus.NONE;

    /**
     * Creates an instance of the panel.
     */
    public ExecutionInnerPanel() {

        setLayout(new BorderLayout(0, 0));

        JPanel topPanel = new JPanel();
        topPanel.setMinimumSize(new Dimension(10, 40));
        JScrollPane topPanelScrollPane = new JScrollPane(topPanel);
        topPanelScrollPane.setVerticalScrollBarPolicy(
                ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        add(topPanelScrollPane, BorderLayout.NORTH);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        copyLogBtn = new JButton("_Copy log");
        copyLogBtn.setIcon(new ImageIcon(ExecutionInnerPanel.class
                .getResource("/toolbarButtonGraphics/general/Copy24.gif")));
        copyLogBtn.setMaximumSize(new Dimension(500, 50));
        copyLogBtn.setMinimumSize(new Dimension(250, 30));
        topPanel.add(copyLogBtn);

        saveLogBtn = new JButton("_Save log");
        saveLogBtn.setIcon(new ImageIcon(ExecutionInnerPanel.class
                .getResource("/toolbarButtonGraphics/general/Export24.gif")));
        saveLogBtn.setMaximumSize(new Dimension(500, 50));
        saveLogBtn.setMinimumSize(new Dimension(250, 30));
        topPanel.add(saveLogBtn);

        openDirBtn = new JButton("_Show result");
        openDirBtn.setIcon(new ImageIcon(ExecutionInnerPanel.class
                .getResource("/toolbarButtonGraphics/general/History24.gif")));
        openDirBtn.setMaximumSize(new Dimension(500, 50));
        openDirBtn.setMinimumSize(new Dimension(250, 30));
        openDirBtn.setEnabled(false);
        topPanel.add(openDirBtn);

        JPanel bottomPanel = new JPanel();
        add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));

        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setVisible(false);
        bottomPanel.add(progressBar);

        model = new DefaultTableModel() {

            private static final long serialVersionUID = -8612722584646979122L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        model.setColumnIdentifiers(new String[] { "", "", "" }); // temporary
        table = new JTable(model);
        table.setShowVerticalLines(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        table.setRowHeight(table.getRowHeight() * 18 / 10);
        table.getColumnModel().getColumn(0).setMinWidth(70);
        table.getColumnModel().getColumn(0).setMaxWidth(100);
        table.getColumnModel().getColumn(1).setMinWidth(125);
        table.getColumnModel().getColumn(1).setMaxWidth(200);
        table.getColumnModel().getColumn(1).setCellRenderer(
                (table, value, isSelected, hasFocus, row, column) -> {
                    DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                    String type = (String) value;
                    value = MessageProvider
                            .getMessage("execution.table." + value);
                    Icon icon = UIManager
                            .getIcon("OptionPane." + type + "Icon");
                    Component comp = renderer.getTableCellRendererComponent(
                            table, value, isSelected, hasFocus, row, column);
                    if (icon != null && comp instanceof JLabel) {
                        BufferedImage bi = new BufferedImage(
                                icon.getIconWidth(), icon.getIconHeight(),
                                BufferedImage.TYPE_INT_ARGB);
                        int d = table.getRowHeight() * 4 / 5;
                        Graphics2D g = bi.createGraphics();
                        icon.paintIcon(null, g, 0, 0);
                        g.dispose();
                        ((JLabel) comp).setIcon(new ImageIcon(bi
                                .getScaledInstance(d, d, Image.SCALE_SMOOTH)));
                    }
                    return comp;
                });
        table.getColumnModel().getColumn(2)
                .setCellRenderer(new DefaultTableCellRenderer() {

                    private static final long serialVersionUID = 3029420337508076381L;

                    @Override
                    public Component getTableCellRendererComponent(JTable table,
                            Object value, boolean isSelected, boolean hasFocus,
                            int row, int column) {
                        String v = Optional.ofNullable(value).orElse("")
                                .toString();
                        boolean multiline = v.chars().filter(c -> c == '\n')
                                .skip(1).findFirst().isPresent();
                        if (!multiline)
                            return super.getTableCellRendererComponent(table, v,
                                    isSelected, hasFocus, row, column);
                        JTextArea ta = new JTextArea(v);
                        ta.setFont(getFont());
                        if (isSelected) {
                            ta.setBackground(table.getSelectionBackground());
                            ta.setForeground(table.getSelectionForeground());
                        }
                        int h = ta.getPreferredSize().height;
                        if (table.getRowHeight(row) < h) {
                            table.setRowHeight(row, h);
                        }
                        return ta;
                    }

                });

        add(new JScrollPane(table), BorderLayout.CENTER);

        copyLogBtn.addActionListener(e -> {
            copyLogBtn.setEnabled(false);
            requestFocus();
            logCopyingStatus = LogExportStatus.DOING;
            updateCopyBtnText();
            CompletableFuture<Boolean> f = CompletableFuture
                    .supplyAsync(() -> copyLogToClipboard(getLogRowsAsText()));
            f.thenAccept(c -> {
                logCopyingStatus = c ? LogExportStatus.DONE
                        : LogExportStatus.FAILED;
                updateCopyBtnText();
                Timer t = new javax.swing.Timer(2000, ee -> {
                    logCopyingStatus = LogExportStatus.NONE;
                    updateCopyBtnText();
                    copyLogBtn.setEnabled(true);
                });
                t.setRepeats(false);
                t.start();
            });
        });

        saveLogBtn.addActionListener(e -> {
            String fn = selectLogFile();
            saveLogBtn.setEnabled(false);
            requestFocus();
            logSavingStatus = LogExportStatus.DOING;
            updateSaveBtnText();
            CompletableFuture<Boolean> f = CompletableFuture
                    .supplyAsync(() -> saveLogToFile(getLogRowsAsText(), fn));
            f.thenAccept(c -> {
                logSavingStatus = c ? LogExportStatus.DONE
                        : LogExportStatus.FAILED;
                updateSaveBtnText();
                Timer t = new javax.swing.Timer(2000, ee -> {
                    logSavingStatus = LogExportStatus.NONE;
                    updateSaveBtnText();
                    saveLogBtn.setEnabled(true);
                });
                t.setRepeats(false);
                t.start();
                f.join();
            });
        });
    }

    /**
     * Copies a string to the system clipboard.
     *
     * @param s the string to copy
     * @return whether the operation succeeded
     */
    public static boolean copyToClipboard(String s) {
        try {
            Toolkit.getDefaultToolkit().getSystemClipboard()
                    .setContents(new StringSelection(s), null);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Copies the execution log to the system clipboard.
     *
     * @param log the log lines to copy
     * @return whether the operation succeeded
     */
    public static boolean copyLogToClipboard(String[] log) {
        return copyToClipboard(String.join(System.lineSeparator(), log));
    }

    /**
     * Exports the execution log to a text file.
     *
     * @param log      the log lines
     * @param fileName the file name where the log will be saved
     * @return whether the operation succeeded
     */
    public static boolean saveLogToFile(String[] log, String fileName) {
        try (DatasetWriter writer = new CSVDatasetWriter(fileName, '\t')) {
            for (String l : log)
                if (!writer.writeRow(l))
                    return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private String selectLogFile() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                MessageProvider.getMessage("execution.savelog.formats"), "log",
                "txt");
        chooser.setFileFilter(filter);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
            return chooser.getSelectedFile().getAbsolutePath();
        return null;
    }

    protected void updateCopyBtnText() {
        String sfx;
        switch (logCopyingStatus) {
        case DOING:
            sfx = ".doing";
            break;
        case DONE:
            sfx = ".done";
            break;
        case FAILED:
            sfx = ".failed";
            break;
        case NONE:
        default:
            sfx = "";
            break;
        }
        SwingUtilities.invokeLater(() -> copyLogBtn.setText(
                MessageProvider.getMessage("execution.copylog" + sfx)));

    }

    protected void updateSaveBtnText() {
        String sfx;
        switch (logSavingStatus) {
        case DOING:
            sfx = ".doing";
            break;
        case DONE:
            sfx = ".done";
            break;
        case FAILED:
            sfx = ".failed";
            break;
        case NONE:
        default:
            sfx = "";
            break;
        }
        SwingUtilities.invokeLater(() -> saveLogBtn.setText(
                MessageProvider.getMessage("execution.savelog" + sfx)));

    }

    @Override
    public void updateLocalisedStrings() {
        updateCopyBtnText();
        saveLogBtn.setText(MessageProvider.getMessage("execution.savelog"));
        openDirBtn.setText(MessageProvider.getMessage("execution.showresult"));
        String[] headers = { "time", "type", "message" };
        IntStream.range(0, 3)
                .forEach(i -> table.getColumnModel().getColumn(i)
                        .setHeaderValue(MessageProvider
                                .getMessage("execution.table." + headers[i])));
    }

    public void setProgress(float progress) {
        int a = progressBar.getMinimum();
        int b = progressBar.getMaximum();
        progressBar.setValue(a + (int) (progress * (b - a)));
        progressBar.setVisible(true);
    }

    /**
     * Changes the visibility state of the progress bar.
     *
     * @param show whether the progress bar should be visible
     */
    public void showProgress(boolean show) {
        progressBar.setVisible(show);
    }

    /**
     * Called when the algorithm finishes successfully. Enables the "open
     * result" button.
     *
     * @param resultDir the directory that contains the output
     */
    public void success(String resultDir) {
        openDirBtn.addActionListener(l -> {
            try {
                Desktop.getDesktop().open(new File(resultDir));
            } catch (IOException e) {
            }
        });
        openDirBtn.setEnabled(true);
    }

    protected Object getTime() {
        LocalDateTime t = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        return new Object() {
            @Override
            public String toString() {
                return t.format(DateTimeFormatter.ISO_LOCAL_TIME);
            }
        };
    }

    protected Object getMessageObject(Supplier<String> message) {
        return new Object() {
            @Override
            public String toString() {
                return message.get();
            }
        };
    }

    protected void addMessageRow(String type, Supplier<String> message) {
        SwingUtilities.invokeLater(() -> {
            model.addRow(new Object[] { getTime(), type,
                    getMessageObject(message) });
            table.setRowSelectionInterval(table.getRowCount() - 1,
                    table.getRowCount() - 1);
            SwingUtilities.invokeLater(() -> {
                table.scrollRectToVisible(
                        table.getCellRect(table.getRowCount() - 1, 0, true));
            });
        }

        );
    }

    public String[] getLogRowsAsText() {
        String[] lines = new String[table.getRowCount()];
        Arrays.setAll(lines,
                i -> IntStream.range(0, table.getColumnCount())
                        .mapToObj(j -> table.getValueAt(i, j).toString())
                        .collect(Collectors.joining("\t")));
        return lines;
    }

    @Override
    public void warn(Supplier<String> message) {
        addMessageRow("warning", message);
    }

    @Override
    public void info(Supplier<String> message) {
        addMessageRow("information", message);
    }

    @Override
    public void infoWithoutLineBreak(Supplier<String> message) {
        // Not used
    }

    @Override
    public void error(Supplier<String> message) {
        addMessageRow("error", message);
    }

    @Override
    public void debug(Supplier<String> message) {
        addMessageRow("debugging", message);
    }

}
