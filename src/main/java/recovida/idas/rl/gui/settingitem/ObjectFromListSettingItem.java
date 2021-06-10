package recovida.idas.rl.gui.settingitem;

import javax.swing.JComboBox;
import javax.swing.SwingUtilities;

import recovida.idas.rl.gui.listener.SettingItemChangeListener;
import recovida.idas.rl.gui.undo.SetOptionCommand;
import recovida.idas.rl.gui.undo.UndoHistory;

/**
 * Represents and deals with a setting item whose type is a given list of
 * values, alongside its field and value.
 *
 * @param <T> the type of the options
 */
public class ObjectFromListSettingItem<T>
        extends AbstractSettingItem<T, JComboBox<T>> {

    protected boolean supressDocumentListener = false;

    /**
     * Creates an instance.
     *
     * @param history      the undo history
     * @param currentValue the current value
     * @param defaultValue the default value
     * @param guiComponent the field
     */
    public ObjectFromListSettingItem(UndoHistory history, T currentValue,
            T defaultValue, JComboBox<T> guiComponent) {
        super(history, currentValue, defaultValue, guiComponent);
        guiComponent.setSelectedItem(currentValue);
        guiComponent.addActionListener(e -> {
            @SuppressWarnings("unchecked")
            T value = (T) guiComponent.getSelectedItem();
            if (value == null)
                return;
            if (!supressDocumentListener)
                history.push(
                        new SetOptionCommand<>(ObjectFromListSettingItem.this,
                                getCurrentValue(), value, true));
            onChange(value);
        });
    }

    /**
     * Creates an instance and adds a set of values to the combobox.
     *
     * @param history      the undo history
     * @param currentValue the current value
     * @param defaultValue the default value
     * @param guiComponent the field
     * @param values       the allowed values to be added to the field
     */
    public ObjectFromListSettingItem(UndoHistory history, T currentValue,
            T defaultValue, JComboBox<T> guiComponent, Iterable<T> values) {
        this(history, currentValue, defaultValue, guiComponent);
        fillComboBox(values);
    }

    /**
     *
     * Fills this setting item's combobox with the given values.
     *
     * @param values the values to fill
     */
    public final void fillComboBox(Iterable<T> values) {
        guiComponent.removeAllItems();
        for (T v : values)
            guiComponent.addItem(v);
    }

    /**
     * Creates an instance.
     *
     * @param history      the undo history
     * @param currentValue the current value
     * @param defaultValue the default value
     * @param guiComponent the field
     * @param listener     a change listener
     */
    public ObjectFromListSettingItem(UndoHistory history, T currentValue,
            T defaultValue, JComboBox<T> guiComponent,
            SettingItemChangeListener listener) {
        this(history, currentValue, defaultValue, guiComponent);
        listeners.add(listener);
    }

    /**
     * Creates an instance and adds a set of values to the combobox.
     *
     * @param history      the undo history
     * @param currentValue the current value
     * @param defaultValue the default value
     * @param guiComponent the field
     * @param listener     a change listener
     * @param values       the allowed values to be added to the field
     */
    public ObjectFromListSettingItem(UndoHistory history, T currentValue,
            T defaultValue, JComboBox<T> guiComponent,
            SettingItemChangeListener listener, Iterable<T> values) {
        this(history, currentValue, defaultValue, guiComponent, values);
        listeners.add(listener);
    }

    @Override
    public void onChange(T newValue) {
        if (newValue.equals(currentValue))
            return;
        currentValue = newValue;
        for (SettingItemChangeListener listener : listeners)
            if (listener != null)
                listener.changed(newValue);
    }

    @Override
    public void setValue(T newValue) {
        SwingUtilities.invokeLater(() -> {
            supressDocumentListener = true;
            guiComponent.setSelectedIndex(-1);
            guiComponent.setSelectedItem(newValue);
            guiComponent.grabFocus();
            supressDocumentListener = false;
        });
    }

    @Override
    public void setValueFromString(String newValue) {
        // TODO:
        // setValue(newValue);
    }

}
