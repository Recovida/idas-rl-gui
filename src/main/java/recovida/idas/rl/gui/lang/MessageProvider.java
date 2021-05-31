package recovida.idas.rl.gui.lang;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * A class that deals with i18n and provides translated messages.
 */
public class MessageProvider {

    /**
     * List of supported language tags.
     */
    public static final List<String> SUPPORTED_LANGUAGES = Collections
            .unmodifiableList(Arrays.asList(new String[] { // currently:
                    "en", // English
                    "es", // Spanish
                    "pt-BR" // Brazilian Portuguese
            }));

    /**
     * The language tag of the system language, if present
     * {@link #SUPPORTED_LANGUAGES}; or the some language tag in
     * {@link #SUPPORTED_LANGUAGES} that matches the system language (even if
     * the country does not match), if available; or English ("en"), as a
     * fallback.
     */
    public static final String DEFAULT_LANGUAGE = getBestLanguage();

    protected static Locale currentLocale = Locale
            .forLanguageTag(DEFAULT_LANGUAGE);

    protected static ResourceBundle bundle = createBundle(currentLocale);

    public static void setLocale(Locale locale) {
        bundle = createBundle(locale);
        currentLocale = locale;
    }

    public static Locale getLocale() {
        return currentLocale;
    }

    protected static String getBestLanguage() {
        Locale defaultLocale = Locale.getDefault();
        // try exact match
        int idx = SUPPORTED_LANGUAGES.indexOf(defaultLocale.toLanguageTag());
        if (idx < 0) // try language match, ignoring country
            idx = SUPPORTED_LANGUAGES.stream().map(x -> x.split("-", 2)[0])
                    .collect(Collectors.toList())
                    .indexOf(defaultLocale.getLanguage());
        // as a last resort, use English as a fallback
        return SUPPORTED_LANGUAGES.get(Math.max(0, idx));
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
