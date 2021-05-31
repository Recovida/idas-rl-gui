package recovida.idas.rl.gui.undo;

import java.util.Objects;

import javax.swing.JComponent;

import recovida.idas.rl.gui.settingitem.AbstractSettingItem;

/**
 * This class represents the command of setting the value of a field (this is
 * not used for column-related fields).
 *
 * @param <T> the type of the field
 */
public class SetOptionCommand<T> extends AbstractCommand {

    private T oldValue;

    private T newValue;

    private AbstractSettingItem<T, JComponent> settingItem;

    private boolean skip;

    /**
     * Creates an instance of this command.
     *
     * @param settingItem the corresponding setting item
     * @param oldValue    value of the field prior to the change
     * @param newValue    value of the field after the change
     * @param skipFirst   whether the first {@link #redo()} call should have no
     *                    effect (this is useful to avoid an infinite loop when
     *                    the change was already made by the user)
     */
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
