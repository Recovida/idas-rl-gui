package recovida.idas.rl.gui.ui.container;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.function.Supplier;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import recovida.idas.rl.gui.lang.MessageProvider;
import recovida.idas.rl.util.StatusReporter.StatusLogger;

public class ExecutionInnerPanel extends JPanel implements StatusLogger {
    private static final long serialVersionUID = -1501370560575937471L;
    private JTable table;
    private JButton copyLogBtn;
    private JButton saveLogBtn;
    private DefaultTableModel model;
    private JProgressBar progressBar;

    public ExecutionInnerPanel() {
        setLayout(new BorderLayout(0, 0));

        JPanel topPanel = new JPanel();
        add(topPanel, BorderLayout.NORTH);

        copyLogBtn = new JButton("_Copy log");
        topPanel.add(copyLogBtn);

        saveLogBtn = new JButton("_Save log");
        topPanel.add(saveLogBtn);

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
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public ExecutionInnerPanel(LayoutManager layout) {
        super(layout);
    }

    public void updateLocalisedStrings() {
        copyLogBtn.setText(MessageProvider.getMessage("execution.copylog"));
        saveLogBtn.setText(MessageProvider.getMessage("execution.savelog"));
        model.setColumnIdentifiers(new String[] {
                MessageProvider.getMessage("execution.table.time"),
                MessageProvider.getMessage("execution.table.type"),
                MessageProvider.getMessage("execution.table.message") });
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
        SwingUtilities.invokeLater(() -> model.addRow(new Object[] { getTime(),
                getLogEntryTypeObject(type), getMessageObject(message) }));
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
