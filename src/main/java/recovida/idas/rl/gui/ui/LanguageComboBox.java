package recovida.idas.rl.gui.ui;

import java.awt.Component;
import java.util.Locale;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JComboBox;
import javax.swing.JList;

import recovida.idas.rl.gui.lang.MessageProvider;

/**
 * A {@link JComboBox} filled with the supported languages.
 *
 * @see MessageProvider
 */
public class LanguageComboBox extends JComboBox<Locale> {

    private static final long serialVersionUID = -3549865108104546786L;

    /**
     * Creates an instance of the combo box.
     */
    public LanguageComboBox() {
        setRenderer(new DefaultListCellRenderer() {

            private static final long serialVersionUID = 3504291023159861529L;

            @Override
            public Component getListCellRendererComponent(JList<?> list,
                    Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                if (value instanceof Locale) {
                    String lang = ((Locale) value)
                            .getDisplayName((Locale) value);
                    value = lang.substring(0, 1).toUpperCase()
                            + lang.substring(1);
                }
                super.getListCellRendererComponent(list, value, index,
                        isSelected, cellHasFocus);
                return this;
            }

        });
        for (String l : MessageProvider.SUPPORTED_LANGUAGES)
            addItem(Locale.forLanguageTag(l));
        setSelectedItem(
                Locale.forLanguageTag(MessageProvider.DEFAULT_LANGUAGE));
    }

}
