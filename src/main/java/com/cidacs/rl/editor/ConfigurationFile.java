package com.cidacs.rl.editor;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class ConfigurationFile {

    Map<String, SettingItem> settingItems;

    public ConfigurationFile() {
        settingItems = new HashMap<>();
    }

    public void addSettingItem(String key, SettingItem settingItem) {
        settingItems.put(key, settingItem);
    }

}
