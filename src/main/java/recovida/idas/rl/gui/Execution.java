package recovida.idas.rl.gui;

import java.util.concurrent.CompletableFuture;

import javax.swing.SwingUtilities;

import recovida.idas.rl.core.Main;
import recovida.idas.rl.core.util.StatusReporter;
import recovida.idas.rl.gui.ui.container.ExecutionInnerPanel;

/**
 * Represents and deals with an execution of the algorithm.
 */
public class Execution {

    protected ExecutionInnerPanel panel;

    protected String configFileName;

    protected Main main;

    protected boolean alreadyExecuted = false;

    /**
     * Creates an instance.
     *
     * @param panel          the execution panel corresponding to this execution
     * @param configFileName the name of the configuration file
     */
    public Execution(ExecutionInnerPanel panel, String configFileName) {
        this.panel = panel;
        this.configFileName = configFileName;
        this.main = new Main(configFileName, 100);
    }

    /**
     * Starts the linkage algorithm.
     *
     * @return a {@link CompletableFuture} object that will hold a boolean
     *         representing the success result
     */
    public synchronized CompletableFuture<Boolean> start() {
        if (alreadyExecuted)
            throw new IllegalStateException();
        alreadyExecuted = true;
        StatusReporter reporter = new StatusReporter() {

            @Override
            public void infoLinkageProgress(float done) {
                super.infoLinkageProgress(done);
                SwingUtilities.invokeLater(() -> panel.setProgress(done));
            }

            @Override
            public void infoCompleted(String resultPath) {
                super.infoCompleted(resultPath);
                SwingUtilities.invokeLater(() -> panel.success(resultPath));
            }

            @Override
            public void errorUnexpectedError(String message) {
                super.errorUnexpectedError(message);
                SwingUtilities.invokeLater(() -> panel.showProgress(false));
            }

        };
        reporter.setLogger(panel);
        StatusReporter.set(reporter);

        CompletableFuture<Boolean> f = CompletableFuture.supplyAsync(() -> {
            boolean result = main.execute();
            if (!result)
                SwingUtilities.invokeLater(() -> panel.showProgress(false));
            return result;
        }).exceptionally(e -> {
            e.printStackTrace();
            SwingUtilities.invokeLater(() -> panel.showProgress(false));
            return false;
        });

        return f;
    }

    /**
     * Cancels the execution.
     */
    public void cancel() {
        if (main != null) {
            Thread t = new Thread(() -> main.interrupt());
            t.start();
            try {
                t.join();
            } catch (InterruptedException e) {
            }
        }
    }

}
