package com.cidacs.rl.editor.settingitem;

import java.util.EventListener;

public interface SettingItemChangeEventListener<T> extends EventListener {

    void changed(T newValue);

}
