/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.config;

import lol.vedant.delivery.Delivery;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {

    private final PluginConfig config;
    private final PluginConfig lang;
    private final PluginConfig gui;
    private final Delivery plugin;

    public ConfigManager(Delivery plugin) {
        this.plugin = plugin;
        this.config = new PluginConfig(plugin, "config.yml");
        this.lang = new PluginConfig(plugin, "lang.yml");
        this.gui = new PluginConfig(plugin, "deliveries.yml");
    }

    public YamlConfiguration getConfig() {
        return this.config.getConfig();
    }

    public YamlConfiguration getLang() {
        return this.lang.getConfig();
    }

    public YamlConfiguration getGUI() {
        return this.gui.getConfig();
    }

}
