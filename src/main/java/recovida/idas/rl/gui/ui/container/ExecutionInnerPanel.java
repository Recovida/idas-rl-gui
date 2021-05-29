package recovida.idas.rl.gui.ui.container;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import recovida.idas.rl.core.util.StatusReporter.StatusLogger;
import recovida.idas.rl.gui.lang.MessageProvider;

public class ExecutionInnerPanel extends JPanel implements StatusLogger {
    private static final long serialVersionUID = -1501370560575937471L;
    private JTable table;
    private JButton copyLogBtn;
    private JButton saveLogBtn;
    private DefaultTableModel model;
    private JProgressBar progressBar;
    private JButton openDirBtn;
    private Component horizontalStrut;

    public ExecutionInnerPanel() {
        setLayout(new BorderLayout(0, 0));

        JPanel topPanel = new JPanel();
        add(topPanel, BorderLayout.NORTH);

        copyLogBtn = new JButton("_Copy log");
        topPanel.add(copyLogBtn);

        saveLogBtn = new JButton("_Save log");
        topPanel.add(saveLogBtn);

        horizontalStrut = Box.createHorizontalStrut(20);
        topPanel.add(horizontalStrut);

        openDirBtn = new JButton("_Show result");
        openDirBtn.setEnabled(false);
        topPanel.add(openDirBtn);

        JPanel bottomPanel = new JPanel();
        add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));

        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setVisible(false);
        progressBar.setMaximum(10000);
        bottomPanel.add(progressBar);

        model = new DefaultTableModel() {

            private static final long serialVersionUID = -8612722584646979122L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        table = new JTable(model);
        table.setShowVerticalLines(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
        add(new JScrollPane(table), BorderLayout.CENTER);

        copyLogBtn.addActionListener(
                e -> Toolkit.getDefaultToolkit().getSystemClipboard()
                        .setContents(new StringSelection(String.join(
                                System.lineSeparator(), getLogRowsAsText())),
                                null));
    }

    public ExecutionInnerPanel(LayoutManager layout) {
        super(layout);
    }

    public void updateLocalisedStrings() {
        copyLogBtn.setText(MessageProvider.getMessage("execution.copylog"));
        saveLogBtn.setText(MessageProvider.getMessage("execution.savelog"));
        openDirBtn.setText(MessageProvider.getMessage("execution.showresult"));
        model.setColumnIdentifiers(new String[] {
                MessageProvider.getMessage("execution.table.time"),
                MessageProvider.getMessage("execution.table.type"),
                MessageProvider.getMessage("execution.table.message") });
        table.getColumnModel().getColumn(0).setMinWidth(70);
        table.getColumnModel().getColumn(0).setMaxWidth(100);
        table.getColumnModel().getColumn(1).setMinWidth(100);
        table.getColumnModel().getColumn(1).setMaxWidth(200);
    }

    public void setProgress(float progress) {
        int a = progressBar.getMinimum();
        int b = progressBar.getMaximum();
        progressBar.setValue(a + (int) (progress * (b - a)));
        progressBar.setVisible(true);
    }

    public void showProgress(boolean show) {
        progressBar.setVisible(show);
    }

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

    protected Object getLogEntryTypeObject(String type) {
        return new Object() {
            @Override
            public String toString() {
                return MessageProvider.getMessage("execution.table." + type);
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
            model.addRow(new Object[] { getTime(), getLogEntryTypeObject(type),
                    getMessageObject(message) });
            table.setRowSelectionInterval(table.getRowCount() - 1,
                    table.getRowCount() - 1);
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
