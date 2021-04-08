package com.cidacs.rl.editor.undo;

import javax.swing.JComponent;

import com.cidacs.rl.editor.SettingItem;

public class SetOptionCommand<T> extends Command {

    private T oldValue;
    private T newValue;
    private SettingItem<T, JComponent> settingItem;

    public SetOptionCommand(SettingItem<T, JComponent> settingItem, T oldValue,
            T newValue) {
        this.settingItem = settingItem;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    @Override
    public void undo() {
        settingItem.setValue(oldValue);
    }

    @Override
    public void redo() {
        settingItem.setValue(newValue);
    }

    @Override
    public boolean merge(Command that) {
        return false;
    }

}
