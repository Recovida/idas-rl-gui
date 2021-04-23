package com.cidacs.rl.editor.settingitem;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.text.ParseException;

import javax.swing.JSpinner;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.cidacs.rl.editor.undo.SetOptionCommand;
import com.cidacs.rl.editor.undo.UndoHistory;

public class NumberSettingItem extends SettingItem<Number, JSpinner> {

    protected boolean supressDocumentListener = false;

    public NumberSettingItem(UndoHistory history, Number currentValue,
            Number defaultValue, JSpinner guiComponent) {
        super(history, currentValue, defaultValue, guiComponent);
        guiComponent.setValue(currentValue);
        guiComponent.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                onLeave();
            }

        });
        SettingItem<Number, ?> settingItem = this;
        guiComponent.addChangeListener(new ChangeListener() {

            Number oldValue = (Number) guiComponent.getValue();

            @Override
            public void stateChanged(ChangeEvent e) {
                Number newValue = (Number) guiComponent.getValue();
                if (!supressDocumentListener && !newValue.equals(oldValue))
                    history.push(new SetOptionCommand<>(settingItem, oldValue,
                            newValue, true));
                onChange(newValue);
                oldValue = newValue;
            }
        });
    }

    public NumberSettingItem(UndoHistory history, Number currentValue,
            Number defaultValue, JSpinner guiComponent,
            SettingItemChangeEventListener<Number> listener) {
        this(history, currentValue, defaultValue, guiComponent);
        this.listeners.add(listener);
    }

    @Override
    public void onChange(Number newValue) {
        if (newValue.equals(currentValue))
            return;
        currentValue = newValue;
        for (SettingItemChangeEventListener<Number> listener : listeners)
            if (listener != null)
                listener.changed(newValue);
    }

    @Override
    public synchronized void setValue(Number newValue) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                supressDocumentListener = true;
                guiComponent.setValue(newValue);
                guiComponent.grabFocus();
                supressDocumentListener = false;
            }
        });
    }

    @Override
    public void setValueFromString(String newValue) {
        try {
            setValue(
                    (Number) ((JSpinner.DefaultEditor) guiComponent.getEditor())
                            .getTextField().getFormatter()
                            .stringToValue(newValue));
        } catch (ParseException e) {
        }
    }

}