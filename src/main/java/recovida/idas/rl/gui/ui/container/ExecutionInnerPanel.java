package recovida.idas.rl.gui.ui.container;

import java.awt.BorderLayout;
import java.awt.LayoutManager;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import recovida.idas.rl.gui.lang.MessageProvider;

public class ExecutionInnerPanel extends JPanel {
    private static final long serialVersionUID = -1501370560575937471L;
    private JTable table;
    private JButton copyLogBtn;
    private JButton saveLogBtn;

    public ExecutionInnerPanel() {
        setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        add(panel, BorderLayout.NORTH);

        copyLogBtn = new JButton("_Copy log");
        panel.add(copyLogBtn);

        saveLogBtn = new JButton("_Save log");
        panel.add(saveLogBtn);

        JPanel panel_1 = new JPanel();
        add(panel_1, BorderLayout.SOUTH);
        panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));

        JProgressBar progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        panel_1.add(progressBar);

        DefaultTableModel model = new DefaultTableModel() {

            private static final long serialVersionUID = -8612722584646979122L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        table = new JTable(model);
        model.addColumn("Col1");
        model.addColumn("Col2");
        model.addColumn("Col3");
        model.addRow(new String[] { "Column 1", "Column 2", "Column 3" });
        model.addRow(new String[] { "Column 1", "Column 2", "Column 3" });
        model.addRow(new String[] { "Column 1", "Column 2", "Column 3" });
        model.addRow(new String[] { "Column 1", "Column 2", "Column 3" });
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public ExecutionInnerPanel(LayoutManager layout) {
        super(layout);
    }

    public void updateLocalisedStrings() {
        copyLogBtn.setText(MessageProvider.getMessage("execution.copylog"));
        saveLogBtn.setText(MessageProvider.getMessage("execution.savelog"));
    }
}
