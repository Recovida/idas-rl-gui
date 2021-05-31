package recovida.idas.rl.gui.listener;

import java.util.EventListener;

import recovida.idas.rl.gui.ui.table.ColumnPairTable;

/**
 * An {@link EventListener} that is notified whenever a row in a
 * {@link ColumnPairTable} is selected.
 */
public interface ColumnPairSelectionListener extends EventListener {

    /**
     * Called whenever a row is selected.
     *
     * @param index the index of the selected row (as in the model)
     */
    void selected(int index);

}
