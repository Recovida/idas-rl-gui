package com.cidacs.rl.editor;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import com.cidacs.rl.editor.pair.ColumnPairManager;
import com.cidacs.rl.editor.settingitem.SettingItem;

@SuppressWarnings("rawtypes")
public class ConfigurationFile {

    Map<String, SettingItem> settingItems;
    ColumnPairManager pairManager;

    public final int MAX_NUMBER = 999;
    protected final String[] COL_KEYS = { "type", "index_a", "index_b",
            "rename_a", "rename_b", "weight", "phon_weight" };
    protected final Pattern COL_KEY_PATTERN = Pattern
            .compile("^[0-9]+_(" + String.join("|", COL_KEYS) + ")$");

    public ConfigurationFile() {
        settingItems = new HashMap<>();
    }

    public void addSettingItem(String key, SettingItem settingItem) {
        settingItems.put(key, settingItem);
    }

    public void setPairPanager(ColumnPairManager pairManager) {
        this.pairManager = pairManager;
    }

    public void load(String fileName) throws IOException {
        Properties props = new Properties();
        try (FileInputStream in = new FileInputStream(fileName)) {
            props.load(in);
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
                if (!cols.containsKey(number))
                    cols.put(number, pairManager.addColumnPair());
                int index = cols.get(number);
                if ("weight".equals(k) || "phon_weight".equals(k)) { // double
                    try {
                        pairManager.setValue(index, k,
                                Math.max(
                                        Double.valueOf(
                                                props.getProperty(key, "0")),
                                        0.0));
                    } catch (NumberFormatException e) { // ignore invalid values
                    }
                } else { // string
                    pairManager.setValue(index, k, props.getProperty(key, ""));
                }
            }
        }

    }

    public Map<String, SettingItem> getSettingItems() {
        return Collections.unmodifiableMap(settingItems);
    }

}
