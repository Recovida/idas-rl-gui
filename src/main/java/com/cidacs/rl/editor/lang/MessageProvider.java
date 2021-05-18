package com.cidacs.rl.editor.lang;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MessageProvider {

    public final static List<String> SUPPORTED_LANGUAGES = Collections
            .unmodifiableList(Arrays.asList(new String[] { "en", "es", "pt" }));
    public static final String DEFAULT_LANGUAGE = SUPPORTED_LANGUAGES.get(0);
    protected static Locale currentLocale = new Locale(DEFAULT_LANGUAGE);
    protected static ResourceBundle bundle = createBundle(currentLocale);

    public static void setLocale(Locale locale) {
        bundle = createBundle(locale);
    }

    protected static ResourceBundle createBundle(Locale locale) {
        return ResourceBundle.getBundle("lang.messages", locale);
    }

    public static String getMessage(String key) {
        try {
            return bundle.getString(key);
        } catch (MissingResourceException e) {
            return "?????";
        }
    }

}
