package recovida.idas.rl.editor.settingitem;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JComponent;

import recovida.idas.rl.editor.listener.SettingItemChangeListener;
import recovida.idas.rl.editor.undo.UndoHistory;

public abstract class SettingItem<T1, T2 extends JComponent> {

    public T1 getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(T1 currentValue) {
        this.currentValue = currentValue;
    }

    public T1 getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(T1 defaultValue) {
        this.defaultValue = defaultValue;
    }

    public T2 getGuiComponent() {
        return guiComponent;
    }

    public void setGuiComponent(T2 guiComponent) {
        this.guiComponent = guiComponent;
    }

    protected T1 currentValue;
    protected T1 defaultValue;
    protected T2 guiComponent;
    protected List<SettingItemChangeListener> listeners;
    protected UndoHistory history;

    public SettingItem(UndoHistory history, T1 currentValue, T1 defaultValue,
            T2 guiComponent) {
        this.currentValue = currentValue;
        this.guiComponent = guiComponent;
        this.defaultValue = defaultValue;
        this.history = history;
        this.listeners = new LinkedList<>();
    }

    public SettingItem(UndoHistory history, T1 currentValue, T1 defaultValue,
            T2 guiComponent, SettingItemChangeListener listener) {
        this(history, currentValue, defaultValue, guiComponent);
        this.listeners.add(listener);
    }

    public abstract void setValue(T1 newValue);

    public abstract void setValueFromString(String newValue);

    public abstract void onChange(T1 newValue);

    public void addChangeListener(SettingItemChangeListener listener) {
        listeners.add(listener);
    }

    public void onLeave() {
    }

}