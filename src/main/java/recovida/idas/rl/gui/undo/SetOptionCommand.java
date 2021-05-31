package recovida.idas.rl.gui.undo;

import java.util.Objects;

import javax.swing.JComponent;

import recovida.idas.rl.gui.settingitem.AbstractSettingItem;

public class SetOptionCommand<T> extends AbstractCommand {

    private T oldValue;
    private T newValue;
    private AbstractSettingItem<T, JComponent> settingItem;
    private boolean skip;

    @SuppressWarnings("unchecked")
    public SetOptionCommand(AbstractSettingItem<T, ?> settingItem, T oldValue,
            T newValue, boolean skipFirst) {
        this.settingItem = (AbstractSettingItem<T, JComponent>) settingItem;
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
    public boolean merge(AbstractCommand that) {
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
