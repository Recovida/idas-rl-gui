package recovida.idas.rl.gui.listener;

import java.util.EventListener;

import recovida.idas.rl.gui.ui.table.ColumnPairTable;

/**
 * An {@link EventListener} that is notified whenever the value of a cell in a
 * {@link ColumnPairTable} is changed.
 */
public interface ColumnPairValueChangeListener extends EventListener {

    /**
     * Called whenever the value of a cell changes.
     *
     * @param rowIndex the row index (as in the model)
     * @param key      the key of the changed field
     * @param value    the new value
     */
    void changed(int rowIndex, String key, Object value);
}
