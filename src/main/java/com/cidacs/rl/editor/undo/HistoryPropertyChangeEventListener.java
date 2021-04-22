package com.cidacs.rl.editor.undo;

import java.util.EventListener;

public interface HistoryPropertyChangeEventListener extends EventListener {

    void canUndoChanged(boolean canUndo);

    void canRedoChanged(boolean canRedo);

    void cleanChanged(boolean isClean);

}
