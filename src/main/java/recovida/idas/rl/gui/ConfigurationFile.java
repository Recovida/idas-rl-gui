package recovida.idas.rl.gui;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Properties;
import java.util.regex.Pattern;

import recovida.idas.rl.core.io.Separator;
import recovida.idas.rl.gui.pair.ColumnPairManager;
import recovida.idas.rl.gui.settingitem.AbstractSettingItem;

/**
 * Represents a configuration file and provides methods for reading and saving
 * configuration files.
 */
@SuppressWarnings("rawtypes")
public class ConfigurationFile {

    /**
     * A custom {@link FilterOutputStream} that ignores the first line. It is
     * used to avoid the timestamp that {@link Properties} generates.
     */
    private final class SkipFirstLineOutputStream extends FilterOutputStream {
        private boolean ignore = true;

        /**
         * Creates an instance.
         *
         * @param out the original stream
         */
        private SkipFirstLineOutputStream(OutputStream out) {
            super(out);
        }

        @Override
        public void write(final int b) throws IOException {
            if (!ignore)
                super.write(b);
            else if (b == '\n')
                ignore = false;
        }

        public void ignoreNextLine(boolean ignore) {
            this.ignore = ignore;
        }
    }

    Map<String, AbstractSettingItem> settingItems;

    ColumnPairManager pairManager;

    /**
     * The maximum number a column pair may use. The numbers are used as
     * prefixes of the keys.
     */
    public static final int MAX_NUMBER = 999;

    protected static final String[] COL_KEYS = { "type", "index_a", "index_b",
            "rename_a", "rename_b", "weight", "phon_weight" };

    protected static final Pattern COL_KEY_PATTERN = Pattern
            .compile("^[0-9]+_(" + String.join("|", COL_KEYS) + ")$");

    /**
     * Creates an instance.
     */
    public ConfigurationFile() {
        settingItems = new LinkedHashMap<>();
    }

    /**
     * Adds a setting item to the configuration file.
     *
     * @param key         the setting key
     * @param settingItem the setting item
     */
    public void addSettingItem(String key, AbstractSettingItem settingItem) {
        settingItems.put(key, settingItem);
    }

    /**
     * Sets the column pair manager.
     *
     * @param pairManager the pair manager
     */
    public void setPairPanager(ColumnPairManager pairManager) {
        this.pairManager = pairManager;
    }

    /**
     * Loads the contents of a configuration file.
     *
     * @param fileName the file name
     * @return whether the file was successfully read
     */
    public boolean load(String fileName) {
        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream(fileName)) {
            props.load(in);
        } catch (Exception e) {
            return false;
        }
        // number --> row index in the table
        Map<Integer, Integer> cols = new HashMap<>();
        for (String key : props.stringPropertyNames()) {
            if (settingItems.containsKey(key))
                settingItems.get(key)
                        .setValueFromString(props.getProperty(key));
            else if (COL_KEY_PATTERN.matcher(key).matches()) {
                String[] numberAndKey = key.split("_", 2);
                int number = Integer.parseInt(numberAndKey[0]);
                if (number > MAX_NUMBER)
                    continue;
                String k = numberAndKey[1];
                int index;
                if (!cols.containsKey(number)) {
                    index = pairManager.addColumnPair();
                    cols.put(number, index);
                    pairManager.onChange(index, "number", number);
                } else {
                    index = cols.get(number);
                }
                if ("weight".equals(k) || "phon_weight".equals(k)) { // double
                    try {
                        pairManager.onChange(index, k,
                                Math.max(
                                        Double.valueOf(
                                                props.getProperty(key, "0")),
                                        0.0));
                    } catch (NumberFormatException e) { // ignore invalid values
                    }
                } else { // string
                    pairManager.onChange(index, k, props.getProperty(key, ""));
                }
            }
        }
        return true;
    }

    public Map<String, AbstractSettingItem> getSettingItems() {
        return Collections.unmodifiableMap(settingItems);
    }

    /**
     * Saves the contents of this configuration file to a .properties file.
     *
     * @param fileName the output file name
     * @return whether the file was successfully saved
     */
    public boolean save(String fileName) {
        try (OutputStream output = new FileOutputStream(fileName)) {
            SkipFirstLineOutputStream skipTimestampOutput = new SkipFirstLineOutputStream(
                    output);
            Properties p = new Properties();
            for (Entry<String, AbstractSettingItem> entry : settingItems
                    .entrySet()) {
                String key = entry.getKey();
                Object value = entry.getValue().getCurrentValue();
                if (value == null || "".equals(value))
                    continue; // don't save empty values
                if ("max_rows".equals(key)
                        && Integer.valueOf(Integer.MAX_VALUE).equals(value))
                    continue; // don't save default value
                if ("min_score".equals(key)
                        && Double.valueOf(0.0).equals(value))
                    continue; // don't save default value
                else if ("num_threads".equals(key)
                        && Integer.valueOf(0).equals(value))
                    continue; // don't save default value
                else if ("output_dec_sep".equals(key)
                        && Objects.equals(value, Separator.DEFAULT_DEC_SEP))
                    continue; // don't save default value
                else if ("output_col_sep".equals(key)
                        && Objects.equals(value, Separator.DEFAULT_COL_SEP))
                    continue; // don't save default value
                String valueStr = value == null ? null : value.toString();
                p.put(key, valueStr);
                p.store(skipTimestampOutput, null);
                p.clear();
                skipTimestampOutput.ignoreNextLine(true);
            }
            output.write(new byte[] { '\n' });
            for (int number = 0; number <= MAX_NUMBER; number++) {
                Collection<Integer> idx = pairManager
                        .getColIdxWithNumber(number);
                if (idx.size() != 1)
                    continue;
                Map<String, Object> c = pairManager
                        .getColumnPair(idx.iterator().next());
                String type = (String) c.getOrDefault("type", "");
                for (Entry<String, Object> entry : c.entrySet()) {
                    String key = entry.getKey();
                    if ("number".equals(key))
                        continue;
                    Object value = entry.getValue();
                    if (value == null || "".equals(value))
                        continue; // don't save empty values
                    if ("phon_weight".equals(key) && (!"name".equals(type)
                            || Double.valueOf(0.0).equals(value)))
                        continue; // don't save it if zero or type isn't name
                    if ("copy".equals(type) && "weight".equals(key))
                        continue; // don't save it if type is "copy"
                    String valueStr = value.toString();
                    p.put(number + "_" + key, valueStr);
                    p.store(skipTimestampOutput, null);
                    p.clear();
                    skipTimestampOutput.ignoreNextLine(true);
                }
                output.write(new byte[] { '\n' });
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }

}
