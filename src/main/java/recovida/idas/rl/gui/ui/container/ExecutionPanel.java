package recovida.idas.rl.gui.ui.container;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;

import recovida.idas.rl.gui.lang.MessageProvider;
import recovida.idas.rl.gui.ui.Translatable;

/**
 * A panel to show the execution result to the user.
 */
public class ExecutionPanel extends JPanel implements Translatable {

    private static final long serialVersionUID = -3897094344632347178L;

    private JLabel logWontBeSavedLbl;

    private JTabbedPane tabbedPane;

    private AtomicInteger tabCounter = new AtomicInteger(0);

    /**
     * Creates an instance of the panel.
     */
    public ExecutionPanel() {

        setLayout(new BorderLayout(0, 0));

        logWontBeSavedLbl = new JLabel("_This log won't be saved!");
        logWontBeSavedLbl.setHorizontalAlignment(SwingConstants.CENTER);
        add(logWontBeSavedLbl, BorderLayout.NORTH);

        tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
        add(tabbedPane, BorderLayout.CENTER);

    }

    /**
     * Creates and adds an execution panel, for a new execution.
     *
     * @return the created execution panel
     */
    public ExecutionInnerPanel addExecutionPanel() {
        ExecutionInnerPanel p = new ExecutionInnerPanel();
        p.updateLocalisedStrings();
        tabbedPane.addTab("" + tabCounter.incrementAndGet(), p);
        tabbedPane.setSelectedComponent(p);
        if (tabbedPane.getTabCount() == 1)
            updateLocalisedStrings();
        return p;
    }

    @Override
    public void updateLocalisedStrings() {
        logWontBeSavedLbl.setText(MessageProvider.getMessage(
                tabbedPane.getTabCount() == 0 ? "execution.noexecutions"
                        : "execution.logisntsaved"));
        for (Component p : tabbedPane.getComponents()) {
            if (p instanceof ExecutionInnerPanel)
                ((ExecutionInnerPanel) p).updateLocalisedStrings();
        }
    }

    /**
     * Removes the inner panels.
     */
    public void clear() {
        tabbedPane.removeAll();
        tabCounter.set(0);
        updateLocalisedStrings();
    }

}
