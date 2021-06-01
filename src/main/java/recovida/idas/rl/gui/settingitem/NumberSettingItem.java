package recovida.idas.rl.gui.settingitem;

import java.text.ParseException;

import javax.swing.JSpinner;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import recovida.idas.rl.gui.listener.SettingItemChangeListener;
import recovida.idas.rl.gui.undo.SetOptionCommand;
import recovida.idas.rl.gui.undo.UndoHistory;

/**
 * Represents and deals with a numeric setting item, its field and value.
 */
public class NumberSettingItem extends AbstractSettingItem<Number, JSpinner> {

    protected boolean supressDocumentListener = false;

    /**
     * Creates an instance.
     *
     * @param history      the undo history
     * @param currentValue the current value
     * @param defaultValue the default value
     * @param guiComponent the field
     */
    public NumberSettingItem(UndoHistory history, Number currentValue,
            Number defaultValue, JSpinner guiComponent) {
        super(history, currentValue, defaultValue, guiComponent);
        guiComponent.setValue(currentValue);
        guiComponent.addChangeListener(new ChangeListener() {

            Number oldValue = (Number) guiComponent.getValue();

            @Override
            public void stateChanged(ChangeEvent e) {
                Number newValue = (Number) guiComponent.getValue();
                if (!supressDocumentListener && !newValue.equals(oldValue))
                    history.push(new SetOptionCommand<>(NumberSettingItem.this,
                            oldValue, newValue, true));
                onChange(newValue);
                oldValue = newValue;
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
    public NumberSettingItem(UndoHistory history, Number currentValue,
            Number defaultValue, JSpinner guiComponent,
            SettingItemChangeListener listener) {
        this(history, currentValue, defaultValue, guiComponent);
        listeners.add(listener);
    }

    @Override
    public void onChange(Number newValue) {
        if (newValue.equals(currentValue))
            return;
        currentValue = newValue;
        for (SettingItemChangeListener listener : listeners)
            if (listener != null)
                listener.changed(newValue);
    }

    @Override
    public synchronized void setValue(Number newValue) {
        SwingUtilities.invokeLater(() -> {
            supressDocumentListener = true;
            guiComponent.setValue(newValue);
            guiComponent.grabFocus();
            supressDocumentListener = false;
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
