package recovida.idas.rl.gui.undo;

import java.util.EventListener;

/**
 * An event listener for {@link UndoHistory} state.
 */
public interface HistoryPropertyChangeEventListener extends EventListener {

    /**
     * Called when the ability to undo has changed.
     *
     * @param canUndo whether it is possible to undo
     */
    void canUndoChanged(boolean canUndo);

    /**
     * Called when the ability to redo has changed.
     *
     * @param canRedo whether it is possible to redo
     */
    void canRedoChanged(boolean canRedo);

    /**
     * Called when the clean state has changed.
     *
     * @param isClean whether the history is clean (i.e. the current contents
     *                are saved)
     */
    void cleanChanged(boolean isClean);

    /**
     * Called when the short description of the action that can be undone has
     * changed.
     *
     * @param summary a summary of the action that can be undone, or
     *                <code>null</code> if such action does not exist
     */
    void undoSummaryChanged(String summary);

    /**
     * Called when the short description of the action that can be redone has
     * changed.
     *
     * @param summary a summary of the action that can be redone, or
     *                <code>null</code> if such action does not exist
     */
    void redoSummaryChanged(String summary);

}
