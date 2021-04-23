package com.cidacs.rl.editor.undo;

import javax.swing.JComponent;

import com.cidacs.rl.editor.settingitem.SettingItem;

public class SetOptionCommand<T> extends Command {

    private T oldValue;
    private T newValue;
    private SettingItem<T, JComponent> settingItem;
    private boolean skip;

    @SuppressWarnings("unchecked")
    public SetOptionCommand(SettingItem<T, ?> settingItem, T oldValue,
            T newValue, boolean skipFirst) {
        this.settingItem = (SettingItem<T, JComponent>) settingItem;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.skip = skipFirst;
    }

    @Override
    public void undo() {
        settingItem.setValue(oldValue);
    }

    @Override
    public void redo() {
        if (skip)
            skip = false;
        else
            settingItem.setValue(newValue);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean merge(Command that) {
        if (!(that instanceof SetOptionCommand<?>))
            return false;
        if (this.settingItem != ((SetOptionCommand<T>) that).settingItem)
            return false;
        this.newValue = ((SetOptionCommand<T>) that).newValue;
        return true;
    }

}
