/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.config;

import lol.vedant.delivery.Delivery;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class PluginConfig {

    private Delivery plugin;
    private YamlConfiguration config;
    private File file;

    public PluginConfig(Delivery plugin, String name) {
        this.plugin = plugin;

        if(!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }
        plugin.saveResource(name, false);

        this.file = new File(plugin.getDataFolder(), name);

        this.config = YamlConfiguration.loadConfiguration(file);

    }

    public YamlConfiguration getConfig() {
        return this.config;
    }

    public void reloadConfig() {
        this.config = YamlConfiguration.loadConfiguration(file);
    }


}
