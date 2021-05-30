package recovida.idas.rl.gui.ui.container;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import recovida.idas.rl.gui.lang.MessageProvider;

public class ExecutionPanel extends JPanel {

    private static final long serialVersionUID = -3897094344632347178L;
    private JLabel logWontBeSavedLbl;
    private JTabbedPane tabbedPane;
    private AtomicInteger tabCounter = new AtomicInteger(0);

    public ExecutionPanel() {
        
        setLayout(new BorderLayout(0, 0));

        logWontBeSavedLbl = new JLabel("_This log won't be saved!");
        logWontBeSavedLbl.setHorizontalAlignment(SwingConstants.CENTER);
        add(logWontBeSavedLbl, BorderLayout.NORTH);

        tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        add(tabbedPane, BorderLayout.CENTER);

    }

    public ExecutionInnerPanel addExecutionPanel() {
        ExecutionInnerPanel p = new ExecutionInnerPanel();
        p.updateLocalisedStrings();
        tabbedPane.addTab("" + tabCounter.incrementAndGet(), p);
        tabbedPane.setSelectedComponent(p);
        if (tabbedPane.getTabCount() == 1)
            updateLocalisedStrings();
        return p;
    }

    public void updateLocalisedStrings() {
        logWontBeSavedLbl.setText(MessageProvider.getMessage(
                tabbedPane.getTabCount() == 0 ? "execution.noexecutions"
                        : "execution.logisntsaved"));
        for (Component p : tabbedPane.getComponents()) {
            if (p instanceof ExecutionInnerPanel)
                ((ExecutionInnerPanel) p).updateLocalisedStrings();
        }
    }

    public void clear() {
        tabbedPane.removeAll();
        tabCounter.set(0);
        updateLocalisedStrings();
    }

}
