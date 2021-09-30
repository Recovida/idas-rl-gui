package recovida.idas.rl.gui.settingitem;

import java.awt.event.ItemEvent;
import java.util.regex.Pattern;

import javax.swing.JCheckBox;
import javax.swing.SwingUtilities;

import recovida.idas.rl.gui.undo.SetOptionCommand;
import recovida.idas.rl.gui.undo.UndoHistory;

/**
 * Represents and deals with a boolean setting item, its check box and value.
 */
public class BooleanSettingItem
        extends AbstractSettingItem<Boolean, JCheckBox> {

    boolean suppressListener = false;

    static final Pattern TRUE_PATTERN = Pattern.compile("\\s*(true|yes|1)\\s*",
            Pattern.CASE_INSENSITIVE);

    /**
     * Creates an instance.
     *
     * @param history      the undo history
     * @param currentValue the current value
     * @param defaultValue the default value
     * @param guiComponent the field
     */
    public BooleanSettingItem(UndoHistory history, Boolean currentValue,
            Boolean defaultValue, JCheckBox guiComponent) {
        super(history, currentValue, defaultValue, guiComponent);
        guiComponent.setSelected(currentValue);
        guiComponent.addItemListener(e -> {
            boolean v = e.getStateChange() == ItemEvent.SELECTED;
            if (!suppressListener)
                history.push(new SetOptionCommand<>(BooleanSettingItem.this,
                        getCurrentValue(), v, true));
            onChange(v);
        });
    }

    @Override
    public void setValueFromString(String newValue) {
        setValue(newValue != null && TRUE_PATTERN.matcher(newValue).matches());
    }

    @Override
    public void setValue(Boolean newValue) {
        SwingUtilities.invokeLater(() -> {
            suppressListener = true;
            guiComponent.setSelected(newValue);
            guiComponent.grabFocus();
            suppressListener = false;
        });
    }

}
