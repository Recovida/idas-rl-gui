package recovida.idas.rl.gui.ui.field;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxEditor;
import javax.swing.JComboBox;

/**
 * An editable {@link JComboBox} that shows a placeholder when empty.
 */
public class JComboBoxWithPlaceholder extends JComboBox<String>
        implements FieldWithPlaceholder {

    private static final long serialVersionUID = 6919614017717410064L;

    protected String placeholder = "";

    /**
     * A custom editor for the combobox.
     */
    public class ComboBoxEditorWithPlaceholder implements ComboBoxEditor {

        private final JTextFieldWithPlaceholder component;

        /**
         * Creates an instance.
         */
        public ComboBoxEditorWithPlaceholder() {
            component = new JTextFieldWithPlaceholder();
            component.setVerticalPlaceholderOffset(3);
            component.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
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

    /**
     * Creates an instance of the combobox.
     */
    public JComboBoxWithPlaceholder() {
        setEditor(new ComboBoxEditorWithPlaceholder());
        setEditable(true);
    }

    @Override
    public String getPlaceholder() {
        return placeholder;
    }

    @Override
    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        ((JTextFieldWithPlaceholder) getEditor().getEditorComponent())
                .setPlaceholder(placeholder);
    }

}
