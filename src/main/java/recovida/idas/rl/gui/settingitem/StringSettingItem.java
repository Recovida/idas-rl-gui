package recovida.idas.rl.gui.settingitem;

import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import recovida.idas.rl.gui.listener.SettingItemChangeListener;
import recovida.idas.rl.gui.ui.field.FieldWithPlaceholder;
import recovida.idas.rl.gui.undo.SetOptionCommand;
import recovida.idas.rl.gui.undo.UndoHistory;

/**
 * Represents and deals with a string setting item, its field and value.
 */
public class StringSettingItem extends AbstractSettingItem<String, JTextField> {

    protected boolean supressDocumentListener = false;

    /**
     * Creates an instance.
     *
     * @param history      the undo history
     * @param currentValue the current value
     * @param defaultValue the default value
     * @param guiComponent the field
     */
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
                } catch (BadLocationException ex) {
                }

            }
        });
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
    public StringSettingItem(UndoHistory history, String currentValue,
            String defaultValue, JTextField guiComponent,
            SettingItemChangeListener listener) {
        this(history, currentValue, defaultValue, guiComponent);
        listeners.add(listener);
    }

    @Override
    public synchronized void setValue(String newValue) {
        SwingUtilities.invokeLater(() -> {
            supressDocumentListener = true;
            guiComponent.setText(newValue);
            guiComponent.grabFocus();
            supressDocumentListener = false;
        });
    }

    @Override
    public void setValueFromString(String newValue) {
        setValue(newValue);
    }

}
