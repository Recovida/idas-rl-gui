package com.cidacs.rl.editor;

import javax.swing.JComponent;

public abstract class SettingItem<T1, T2 extends JComponent> {

    protected T1 currentValue;
    protected T1 defaultValue;
    protected T2 guiComponent;

    public SettingItem(T1 currentValue, T1 defaultValue, T2 guiComponent) {
        this.currentValue = currentValue;
        this.guiComponent = guiComponent;
        this.defaultValue = defaultValue;
    }

    public abstract void setValue(T1 newValue);

    public abstract void setValueFromString(String newValue);

    abstract void onChange(T1 newValue);

    abstract void onLeave();

}
