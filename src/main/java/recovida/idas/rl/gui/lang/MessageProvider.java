package recovida.idas.rl.gui.lang;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MessageProvider {

    public final static List<String> SUPPORTED_LANGUAGES = Collections
            .unmodifiableList(
                    Arrays.asList(new String[] { "en", "es", "pt-BR" }));

    public static final String DEFAULT_LANGUAGE = SUPPORTED_LANGUAGES
            .get(Math.max(Math.max(
                    // try exact match
                    SUPPORTED_LANGUAGES
                            .indexOf(Locale.getDefault().toLanguageTag()),
                    // try language match, ignoring country
                    SUPPORTED_LANGUAGES.stream().map(x -> x.split("-", 2)[0])
                            .collect(Collectors.toList())
                            .indexOf(Locale.getDefault().getLanguage())),
                    // as a last resort, use English as a fallback
                    0));

    protected static Locale currentLocale = new Locale(DEFAULT_LANGUAGE);
    protected static ResourceBundle bundle = createBundle(currentLocale);

    public static void setLocale(Locale locale) {
        bundle = createBundle(locale);
        currentLocale = locale;
    }

    public static Locale getLocale() {
        return currentLocale;
    }

    protected static ResourceBundle createBundle(Locale locale) {
        return ResourceBundle.getBundle("lang.gui_messages", locale);
    }

    public static String getMessage(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return "?????";
        }
    }

}
