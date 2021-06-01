package recovida.idas.rl.gui.listener;

import java.util.EventListener;

/**
 * An {@link EventListener} that is triggered whenever a setting item is
 * changed.
 */
public interface SettingItemChangeListener extends EventListener {

    /**
     *
     * Called whenever the value of the setting item changes.
     *
     * @param newValue the value after the change
     */
    void changed(Object newValue);

}
