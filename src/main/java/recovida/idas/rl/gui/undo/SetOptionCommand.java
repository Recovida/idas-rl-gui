package recovida.idas.rl.gui.undo;

import java.util.Objects;

import javax.swing.JComponent;

import recovida.idas.rl.gui.settingitem.SettingItem;

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
        if (Objects.equals(this.oldValue,
                ((SetOptionCommand<T>) that).newValue))
            return false;
        this.newValue = ((SetOptionCommand<T>) that).newValue;
        return true;
    }

    @Override
    public String getSummary() {
        return "“" + Objects.toString(oldValue, "") + "” -> “"
                + Objects.toString(newValue, "") + "”";
    }

}
