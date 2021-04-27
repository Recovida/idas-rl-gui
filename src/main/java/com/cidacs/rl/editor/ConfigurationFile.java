package com.cidacs.rl.editor;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.cidacs.rl.editor.settingitem.SettingItem;

@SuppressWarnings("rawtypes")
public class ConfigurationFile {

    Map<String, SettingItem> settingItems;
    List<ColumnPair> columnPairs;

    public ConfigurationFile() {
        settingItems = new HashMap<>();
        columnPairs = new LinkedList<>();
    }

    public void addSettingItem(String key, SettingItem settingItem) {
        settingItems.put(key, settingItem);
    }

    public void load(String fileName) throws IOException {
        Properties props = new Properties();
        FileInputStream in = new FileInputStream(fileName);
        props.load(in);
        for (String key : props.stringPropertyNames())
            if (settingItems.containsKey(key))
                settingItems.get(key)
                        .setValueFromString(props.getProperty(key));
        in.close();
    }

    public Map<String, SettingItem> getSettingItems() {
        return Collections.unmodifiableMap(settingItems);
    }

}
