package com.cidacs.rl.editor.listener;

import java.util.EventListener;

public interface ColumnPairValueChangeListener extends EventListener {

    void changed(int rowIndex, String key, Object value);
}
