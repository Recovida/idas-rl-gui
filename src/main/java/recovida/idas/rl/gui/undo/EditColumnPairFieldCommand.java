package recovida.idas.rl.gui.undo;

import java.util.Objects;

import recovida.idas.rl.gui.pair.ColumnPairManager;

public class EditColumnPairFieldCommand<T> extends AbstractCommand {

    private ColumnPairManager manager;

    int index = 0;

    private T oldValue;

    private T newValue;

    private boolean skipField;

    private String key;

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
        return key + ": “" + Objects.toString(oldValue, "") + "” -> “"
                + Objects.toString(newValue, "") + "”";
    }

}
