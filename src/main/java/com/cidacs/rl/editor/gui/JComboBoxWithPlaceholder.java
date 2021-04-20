package com.cidacs.rl.editor.gui;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.ComboBoxEditor;
import javax.swing.JComboBox;

public class JComboBoxWithPlaceholder extends JComboBox<String> {

    private static final long serialVersionUID = 6919614017717410064L;

    protected String placeholder = "";

    public class ComboBoxEditorWithPlaceholder implements ComboBoxEditor {

        private JTextFieldWithPlaceholder component;

        public ComboBoxEditorWithPlaceholder() {
            component = new JTextFieldWithPlaceholder();
        }

        @Override
        public Component getEditorComponent() {
            return component;
        }

        @Override
        public void setItem(Object anObject) {
            if (anObject == null)
                component.setText("");
            else
                component.setText(anObject.toString());
        }

        @Override
        public String getItem() {
            return component.getText();
        }

        @Override
        public void selectAll() {
            component.selectAll();
        }

        @Override
        public void addActionListener(ActionListener l) {
            component.addActionListener(l);
        }

        @Override
        public void removeActionListener(ActionListener l) {
            component.removeActionListener(l);
        }

    }

    public JComboBoxWithPlaceholder() {
        super();
        setEditor(new ComboBoxEditorWithPlaceholder());
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        ((JTextFieldWithPlaceholder) getEditor().getEditorComponent())
                .setPlaceholder(placeholder);
    }

}
