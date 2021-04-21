package com.cidacs.rl.editor;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JComboBox;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;

import com.cidacs.rl.editor.gui.FieldWithPlaceholder;

public class StringSettingItemWithList
        extends SettingItem<String, JComboBox<String>> {

    public StringSettingItemWithList(String currentValue, String defaultValue,
            JComboBox<String> guiComponent) {
        super(currentValue, defaultValue, guiComponent);
        guiComponent.setSelectedItem(currentValue);
        if (defaultValue != null && defaultValue.length() > 0
                && guiComponent instanceof FieldWithPlaceholder) {
            ((FieldWithPlaceholder) guiComponent).setPlaceholder(defaultValue);
        }
        guiComponent.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                onLeave();
            }

        });
        ((JTextComponent) guiComponent.getEditor().getEditorComponent())
                .getDocument().addDocumentListener(new DocumentListener() {

                    @Override
                    public void removeUpdate(DocumentEvent e) {
                        changedUpdate(e);
                    }

                    @Override
                    public void insertUpdate(DocumentEvent e) {
                        changedUpdate(e);
                    }

                    @Override
                    public void changedUpdate(DocumentEvent e) {
                        try {
                            String value = e.getDocument().getText(0,
                                    e.getDocument().getLength());
                            onChange(value);
                        } catch (BadLocationException e1) {
                        }
                    }
                });
    }

    public StringSettingItemWithList(String currentValue, String defaultValue,
            JComboBox<String> guiComponent,
            SettingItemChangeEventListener<String> listener) {
        this(currentValue, defaultValue, guiComponent);
        this.listeners.add(listener);
    }

    @Override
    public void onChange(String newValue) {
        if (newValue.equals(currentValue))
            return;
        currentValue = newValue;
        for (SettingItemChangeEventListener<String> listener : listeners)
            if (listener != null)
                listener.changed(newValue);
    }

    @Override
    public void setValue(String newValue) {
        guiComponent.setSelectedItem(newValue);
    }

    @Override
    public void setValueFromString(String newValue) {
        setValue(newValue);
    }

}
