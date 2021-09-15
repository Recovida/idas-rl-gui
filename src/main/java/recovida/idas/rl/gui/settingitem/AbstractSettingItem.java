package recovida.idas.rl.gui.settingitem;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;

import recovida.idas.rl.gui.listener.SettingItemChangeListener;
import recovida.idas.rl.gui.undo.UndoHistory;

/**
 * Represents and deals with a setting item, its field and value.
 *
 * @param <V> the type of the item
 * @param <F> the type of the field
 */
public abstract class AbstractSettingItem<V, F extends JComponent> {

    public V getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(V currentValue) {
        this.currentValue = currentValue;
    }

    public V getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(V defaultValue) {
        this.defaultValue = defaultValue;
    }

    public F getGuiComponent() {
        return guiComponent;
    }

    public void setGuiComponent(F guiComponent) {
        this.guiComponent = guiComponent;
    }

    protected V currentValue;

    protected V defaultValue;

    protected F guiComponent;

    protected List<SettingItemChangeListener> listeners;

    protected UndoHistory history;

    /**
     * Creates an instance.
     *
     * @param history      the undo history
     * @param currentValue the current value
     * @param defaultValue the default value
     * @param guiComponent the field
     */
    public AbstractSettingItem(UndoHistory history, V currentValue,
            V defaultValue, F guiComponent) {
        this.currentValue = currentValue;
        this.guiComponent = guiComponent;
        this.defaultValue = defaultValue;
        this.history = history;
        this.listeners = new LinkedList<>();
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
    public AbstractSettingItem(UndoHistory history, V currentValue,
            V defaultValue, F guiComponent,
            SettingItemChangeListener listener) {
        this(history, currentValue, defaultValue, guiComponent);
        this.listeners.add(listener);
    }

    public abstract void setValue(V newValue);

    public abstract void setValueFromString(String newValue);

    /**
     * Sets the new value and notifies listeners.
     *
     * @param newValue the new value
     */
    public void onChange(V newValue) {
        if (newValue.equals(currentValue))
            return;
        currentValue = newValue;
        for (SettingItemChangeListener listener : listeners)
            if (listener != null) {
                listener.changed(newValue);
            }
    }

    /**
     * Adds a listener to be notified whenever the value of this setting item
     * changes.
     *
     * @param listener the listener to add
     */
    public void addChangeListener(SettingItemChangeListener listener) {
        listeners.add(listener);
    }

}
