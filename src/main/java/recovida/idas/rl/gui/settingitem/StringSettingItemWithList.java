package recovida.idas.rl.gui.settingitem;

import javax.swing.JComboBox;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;

import recovida.idas.rl.gui.listener.SettingItemChangeListener;
import recovida.idas.rl.gui.ui.field.FieldWithPlaceholder;
import recovida.idas.rl.gui.undo.SetOptionCommand;
import recovida.idas.rl.gui.undo.UndoHistory;

public class StringSettingItemWithList
        extends AbstractSettingItem<String, JComboBox<String>> {

    protected boolean supressDocumentListener = false;

    public StringSettingItemWithList(UndoHistory history, String currentValue,
            String defaultValue, JComboBox<String> guiComponent) {
        super(history, currentValue, defaultValue, guiComponent);
        guiComponent.setSelectedItem(currentValue);
        if (defaultValue != null && defaultValue.length() > 0
                && guiComponent instanceof FieldWithPlaceholder) {
            ((FieldWithPlaceholder) guiComponent).setPlaceholder(defaultValue);
        }
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
                            if (!supressDocumentListener)
                                history.push(new SetOptionCommand<>(
                                        StringSettingItemWithList.this,
                                        getCurrentValue(), value, true));
                            onChange(value);
                        } catch (BadLocationException ex) {
                        }
                    }
                });
    }

    public StringSettingItemWithList(UndoHistory history, String currentValue,
            String defaultValue, JComboBox<String> guiComponent,
            SettingItemChangeListener listener) {
        this(history, currentValue, defaultValue, guiComponent);
        this.listeners.add(listener);
    }

    @Override
    public void onChange(String newValue) {
        if (newValue.equals(currentValue))
            return;
        currentValue = newValue;
        for (SettingItemChangeListener listener : listeners)
            if (listener != null)
                listener.changed(newValue);
    }

    @Override
    public void setValue(String newValue) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                supressDocumentListener = true;
                guiComponent.setSelectedIndex(-1);
                guiComponent.setSelectedItem(newValue);
                guiComponent.grabFocus();
                supressDocumentListener = false;
            }
        });
    }

    @Override
    public void setValueFromString(String newValue) {
        setValue(newValue);
    }

}
