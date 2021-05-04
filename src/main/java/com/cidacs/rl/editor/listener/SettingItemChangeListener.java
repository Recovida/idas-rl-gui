package com.cidacs.rl.editor.listener;

import java.util.EventListener;

public interface SettingItemChangeListener extends EventListener {

    void changed(Object newValue);

}
