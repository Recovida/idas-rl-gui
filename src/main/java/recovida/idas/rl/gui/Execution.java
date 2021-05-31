package recovida.idas.rl.gui;

import java.util.concurrent.CompletableFuture;

import javax.swing.SwingUtilities;

import recovida.idas.rl.core.Main;
import recovida.idas.rl.core.util.StatusReporter;
import recovida.idas.rl.gui.ui.container.ExecutionInnerPanel;

public class Execution {

    protected ExecutionInnerPanel panel;

    protected String configFileName;

    protected Main main;

    protected boolean alreadyExecuted = false;

    public Execution(ExecutionInnerPanel panel, String configFileName) {
        this.panel = panel;
        this.configFileName = configFileName;
        this.main = new Main(configFileName, 100);
    }

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
                panel.success(resultPath);
            }

            @Override
            public void errorUnexpectedError(String message) {
                super.errorUnexpectedError(message);
                panel.showProgress(false);
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

    public void cancel() {
        if (main != null)
            main.interrupt();
    }

}
