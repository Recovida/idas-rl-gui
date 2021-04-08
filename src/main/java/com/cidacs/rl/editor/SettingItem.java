package com.cidacs.rl.editor;

import javax.swing.JComponent;

public abstract class SettingItem<T1, T2 extends JComponent> {

    String key;
    T1 currentValue;
    T1 defaultValue;
    T2 guiComponent;

    public SettingItem(String key, T1 currentValue, T1 defaultValue,
            T2 guiComponent) {
        this.key = key;
        this.currentValue = currentValue;
        this.guiComponent = guiComponent;
        this.defaultValue = defaultValue;
    }

    public abstract void setValue(T1 newValue);

    abstract void onChange(T1 newValue);

    abstract void onLeave();

}
