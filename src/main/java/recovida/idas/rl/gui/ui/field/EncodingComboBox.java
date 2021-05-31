package recovida.idas.rl.gui.ui.field;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A {@link JComboBoxWithPlaceholder} filled with the supported encodings
 * (including "ANSI", an unofficial alias for "Cp1252"), and showing "UTF-8" as
 * a default value when blank.
 */
public class EncodingComboBox extends JComboBoxWithPlaceholder {

    private static final long serialVersionUID = 4839916568723114826L;

    /**
     * Creates an instance.
     */
    public EncodingComboBox() {
        fillEncodings();
    }

    protected void fillEncodings() {
        Set<String> allEncodings = new HashSet<>();
        for (Entry<String, Charset> entry : Charset.availableCharsets()
                .entrySet()) {
            allEncodings.add(entry.getKey());
            allEncodings.addAll(entry.getValue().aliases().stream()
                    .filter(enc -> enc.length() <= 10)
                    .collect(Collectors.toList()));
        }
        List<String> sortedEncodings = new ArrayList<>(allEncodings);
        Collections.sort(sortedEncodings, new Comparator<String>() {

            @Override
            public int compare(String o1, String o2) {
                if (Character.isDigit(o1.charAt(0)) == Character
                        .isDigit(o2.charAt(0)))
                    return o1.compareToIgnoreCase(o2);
                return Character.isDigit(o1.charAt(0)) ? 1 : -1;
            }
        });
        sortedEncodings.add(0, "ANSI");
        sortedEncodings.add(0, "UTF-8"); // add again to the beginning
        for (String enc : sortedEncodings)
            addItem(enc);
    }

}
