package recovida.idas.rl.gui;

import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class FileChangeWatcher {

    WatchService watchService;
    Path dir;
    Path file;
    WatchKey registeredKey;
    Thread t;
    boolean enabled = false;
    private Runnable callback;
    private boolean stopAfterCallback = false;

    public FileChangeWatcher(Path p, Runnable callback,
            boolean stopAfterCallback) {
        file = p.toAbsolutePath();
        dir = file.getParent();
        this.callback = callback;
        this.stopAfterCallback = stopAfterCallback;
    }

    public FileChangeWatcher(Path p, Runnable callback) {
        this(p, callback, false);
    }

    protected void createThread(Runnable callback) {
        t = new Thread(() -> {
            try {
                watchService = FileSystems.getDefault().newWatchService();
                registeredKey = dir.register(watchService,
                        StandardWatchEventKinds.ENTRY_CREATE,
                        StandardWatchEventKinds.ENTRY_DELETE,
                        StandardWatchEventKinds.ENTRY_MODIFY);
                WatchKey key;
                while (enabled && (key = watchService.take()) != null) {
                    for (WatchEvent<?> event : key.pollEvents()) {
                        if (enabled && dir.resolve((Path) event.context())
                                .toAbsolutePath().equals(file)) {
                            callback.run();
                            if (stopAfterCallback)
                                disable();
                        }
                    }
                    if (!key.reset()) {
                        break;
                    }
                }
            } catch (InterruptedException | ClosedWatchServiceException
                    | IOException e) {
            }
        });
    }

    public synchronized void enable() {
        if (!enabled) {
            enabled = true;
            start();
        }
    }

    public synchronized void disable() {
        if (enabled) {
            enabled = false;
            stop();
        }
    }

    public boolean isEnabled() {
        return enabled;
    }

    protected void start() {
        createThread(callback);
        t.start();
    }

    protected void stop() {
        try {
            watchService.close();
            if (t != null && t.isAlive()) {
                t.interrupt();
                t.join();
            }
        } catch (IOException | InterruptedException e) {
        }
        t = null;
    }
}
