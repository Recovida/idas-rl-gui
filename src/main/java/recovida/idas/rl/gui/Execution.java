package recovida.idas.rl.gui;

import java.util.concurrent.CompletableFuture;

import javax.swing.SwingUtilities;

import recovida.idas.rl.Main;
import recovida.idas.rl.gui.ui.container.ExecutionInnerPanel;
import recovida.idas.rl.util.StatusReporter;

public class Execution {

    protected ExecutionInnerPanel panel;
    protected String configFileName;

    public Execution(ExecutionInnerPanel panel, String configFileName) {
        this.panel = panel;
        this.configFileName = configFileName;
    }

    public CompletableFuture<Boolean> start() {
        StatusReporter originalReporter = StatusReporter.get();
        StatusReporter reporter = new StatusReporter() {

            @Override
            public void infoLinkageProgress(float done) {
                super.infoLinkageProgress(done);
                SwingUtilities.invokeLater(() -> panel.setProgress(done));
            }

        };
        reporter.setLogger(panel);
        StatusReporter.set(reporter);

        CompletableFuture<Boolean> f = CompletableFuture.supplyAsync(() -> {
            return Main.execute(configFileName, 100);
        }).exceptionally(e -> {
            e.printStackTrace();
            SwingUtilities.invokeLater(() -> panel.showProgress(false));
            return false;
        });

        return f;
    }

}
