package recovida.idas.rl.gui.undo;

import java.util.EventListener;

public interface HistoryPropertyChangeEventListener extends EventListener {

    void canUndoChanged(boolean canUndo);

    void canRedoChanged(boolean canRedo);

    void cleanChanged(boolean isClean);

    void undoSummaryChanged(String summary);

    void redoSummaryChanged(String summary);

}
