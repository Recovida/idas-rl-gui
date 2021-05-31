package recovida.idas.rl.gui.listener;

import java.util.EventListener;

import recovida.idas.rl.gui.ui.table.ColumnPairTable;

/**
 * An {@link EventListener} that is notified whenever a column pair (represented
 * by a row) in a {@link ColumnPairTable} is added or deleted.
 */
public interface ColumnPairInclusionExclusionListener extends EventListener {

    /**
     * Called whenever a row is added.
     *
     * @param index          the index of the added row (as in the model)
     * @param columnPairData an array with the data of the added row
     */
    void insertedColumnPair(int index, Object[] columnPairData);

    /**
     * Called whenever a row is deleted.
     *
     * @param index          the index of the deleted row (as in the model)
     * @param columnPairData an array with the data of the deleted row
     */
    void deletedColumnPair(int index, Object[] columnPairData);

}
