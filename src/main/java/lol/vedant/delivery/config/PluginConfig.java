/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class PluginConfig {

    // Path to the config file
    private Path file;

    // Path to the data directory
    private Path dir;

    // The configuration for the config
    private YamlConfiguration yaml;

    public PluginConfig(JavaPlugin plugin, String name) {

        // Sets the directories location
        dir = plugin.getDataFolder().toPath();

        // If it doesn't exist, create it, and replace the value
        // with the newly created directory
        if (!Files.exists(dir)) {
            try {
                dir = Files.createDirectory(dir);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Saves the file from the resources directory to the data directory
        plugin.saveResource(name, false);

        // Creates the file in the data directory
        file = dir.resolve(name);

        // New configuration
        yaml = new YamlConfiguration();

        // Loads the file data into the configuration
        try {
            yaml.load(file.toString());
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public void checkDefaults(JavaPlugin plugin, String name) {
        // Load default configuration from resources
        InputStream defaultConfigStream = plugin.getResource(name);
        if (defaultConfigStream != null) {
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defaultConfigStream));

            // Check if each default value exists in the current configuration
            for (String key : defaultConfig.getKeys(true)) {
                if (!yaml.contains(key)) {
                    // Copy default value to current configuration
                    yaml.set(key, defaultConfig.get(key));
                }
            }
            save();
        } else {
            plugin.getLogger().warning("Unable to find default configuration file: " + name);
        }
    }

    // Saves the configuration data
    public void save() {
        try {
            yaml.save(file.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Simple Getter
    public YamlConfiguration getConfig() {
        return yaml;
    }

    public void reload() {
        try {
            yaml.load(file.toString());
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    // Gets the path to the data directory
    public Path getDataPath() {
        return dir;
    }



}
