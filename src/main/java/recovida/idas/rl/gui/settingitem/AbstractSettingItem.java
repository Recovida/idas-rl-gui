package recovida.idas.rl.gui.settingitem;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;

import recovida.idas.rl.gui.listener.SettingItemChangeListener;
import recovida.idas.rl.gui.undo.UndoHistory;

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

    public AbstractSettingItem(UndoHistory history, V currentValue,
            V defaultValue, F guiComponent) {
        this.currentValue = currentValue;
        this.guiComponent = guiComponent;
        this.defaultValue = defaultValue;
        this.history = history;
        this.listeners = new LinkedList<>();
    }

    public AbstractSettingItem(UndoHistory history, V currentValue,
            V defaultValue, F guiComponent,
            SettingItemChangeListener listener) {
        this(history, currentValue, defaultValue, guiComponent);
        this.listeners.add(listener);
    }

    public abstract void setValue(V newValue);

    public abstract void setValueFromString(String newValue);

    public abstract void onChange(V newValue);

    public void addChangeListener(SettingItemChangeListener listener) {
        listeners.add(listener);
    }

    public void onLeave() {
    }

}
