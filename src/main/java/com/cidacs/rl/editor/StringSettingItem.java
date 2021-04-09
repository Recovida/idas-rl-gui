package com.cidacs.rl.editor;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

public class StringSettingItem extends SettingItem<String, JTextField> {

    protected boolean replaceBlankWithDefaultValue = false;

    public boolean isReplaceBlankWithDefaultValue() {
        return replaceBlankWithDefaultValue;
    }

    public void setReplaceBlankWithDefaultValue(
            boolean replaceBlankWithDefaultValue) {
        this.replaceBlankWithDefaultValue = replaceBlankWithDefaultValue;
    }

    public StringSettingItem(String currentValue, String defaultValue,
            JTextField guiComponent) {
        super(currentValue, defaultValue, guiComponent);
        guiComponent.setText(currentValue);
        guiComponent.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
            }

            @Override
            public void focusLost(FocusEvent e) {
                onLeave();
            }

        });
        guiComponent.getDocument().addDocumentListener(new DocumentListener() {

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
                    e1.printStackTrace();
                }

            }
        });
    }

    @Override
    void onChange(String newValue) {
        if (newValue.equals(currentValue))
            return;
        if (replaceBlankWithDefaultValue && currentValue.isEmpty())
            currentValue = defaultValue;
        else
            currentValue = newValue;
        System.out.println(newValue);
    }

    @Override
    void onLeave() {
        if (replaceBlankWithDefaultValue && guiComponent.getText().isEmpty())
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    guiComponent.setText(defaultValue);
                }
            });

    }

    @Override
    public void setValue(String newValue) {
        guiComponent.setText(newValue);
    }

}
