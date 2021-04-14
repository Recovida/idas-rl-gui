package com.cidacs.rl.editor;

import java.util.EventListener;

public interface SettingItemChangeEventListener<T> extends EventListener {

    void changed(T newValue);

}
