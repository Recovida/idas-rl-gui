package recovida.idas.rl.editor.settingitem;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import recovida.idas.rl.editor.gui.FieldWithPlaceholder;
import recovida.idas.rl.editor.listener.SettingItemChangeListener;
import recovida.idas.rl.editor.undo.SetOptionCommand;
import recovida.idas.rl.editor.undo.UndoHistory;

public class StringSettingItem extends SettingItem<String, JTextField> {

    protected boolean supressDocumentListener = false;

    public StringSettingItem(UndoHistory history, String currentValue,
            String defaultValue, JTextField guiComponent) {
        super(history, currentValue, defaultValue, guiComponent);
        guiComponent.setText(currentValue);
        if (defaultValue != null && defaultValue.length() > 0
                && guiComponent instanceof FieldWithPlaceholder) {
            ((FieldWithPlaceholder) guiComponent).setPlaceholder(defaultValue);
        }
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
                    if (!supressDocumentListener)
                        history.push(
                                new SetOptionCommand<>(StringSettingItem.this,
                                        getCurrentValue(), value, true));
                    onChange(value);
                } catch (BadLocationException e1) {
                }

            }
        });
    }

    public StringSettingItem(UndoHistory history, String currentValue,
            String defaultValue, JTextField guiComponent,
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
    public synchronized void setValue(String newValue) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                supressDocumentListener = true;
                guiComponent.setText(newValue);
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