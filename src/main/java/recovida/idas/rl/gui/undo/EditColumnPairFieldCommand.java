package recovida.idas.rl.gui.undo;

import java.util.Objects;

import recovida.idas.rl.gui.pair.ColumnPairManager;
import recovida.idas.rl.gui.ui.table.ColumnPairTable;

/**
 * This class represents the command of editing a field in a
 * {@link ColumnPairTable}.
 *
 * @param <T> the type of the field
 */
public class EditColumnPairFieldCommand<T> extends AbstractCommand {

    private final ColumnPairManager manager;

    int index = 0;

    private final T oldValue;

    private T newValue;

    private boolean skipField;

    private final String key;

    /**
     * Creates an instance of this command.
     *
     * @param manager              the column pair manager
     * @param index                the row index (in the model)
     * @param key                  the key of the field
     * @param oldValue             value prior to the change
     * @param newValue             value after the change
     * @param skipFieldOnFirstTime whether the first {@link #redo()} call should
     *                             have no effect (this is useful to avoid an
     *                             infinite loop when the change was already
     *                             made by the user)
     */
    public EditColumnPairFieldCommand(ColumnPairManager manager, int index,
            String key, T oldValue, T newValue, boolean skipFieldOnFirstTime) {
        this.manager = manager;
        this.index = index;
        this.key = key;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.skipField = skipFieldOnFirstTime;
    }

    @Override
    public void undo() {
        manager.setValue(index, key, oldValue);
    }

    @Override
    public void redo() {
        manager.setValue(index, key, newValue, skipField);
        skipField = false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean merge(AbstractCommand that) {
        if (!(that instanceof EditColumnPairFieldCommand<?>))
            return false;
        EditColumnPairFieldCommand<T> t = (EditColumnPairFieldCommand<T>) that;
        if (this.manager != t.manager || this.index != t.index
                || !this.key.equals(t.key))
            return false;
        this.newValue = ((EditColumnPairFieldCommand<T>) that).newValue;
        return true;
    }

    @Override
    public String getSummary() {
        return key + ": ???" + Objects.toString(oldValue, "") + "??? -> ???"
                + Objects.toString(newValue, "") + "???";
    }

}
