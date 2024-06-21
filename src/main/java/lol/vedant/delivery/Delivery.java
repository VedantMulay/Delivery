/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery;

import io.th0rgal.oraxen.compatibilities.CompatibilitiesManager;
import lol.vedant.delivery.api.menu.MenuListener;
import lol.vedant.delivery.commands.DeliveryCommand;
import lol.vedant.delivery.api.menu.MenuManager;
import lol.vedant.delivery.config.ConfigManager;
import lol.vedant.delivery.database.Database;
import lol.vedant.delivery.database.MySQL;
import lol.vedant.delivery.database.SQLite;
import lol.vedant.delivery.utils.OraxenSupport;
import me.despical.commandframework.CommandFramework;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Delivery extends JavaPlugin  {

    private CommandFramework commandFramework;
    private MenuManager menuManager;
    private ConfigManager configManager;
    private Database database;

    private static Delivery instance;


    @Override
    public void onEnable() {

        CompatibilitiesManager.addCompatibility("Delivery", OraxenSupport.class);

        instance = this;
        commandFramework = new CommandFramework(this);
        commandFramework.registerCommands(new DeliveryCommand());

        this.menuManager = new MenuManager(this);
        this.configManager = new ConfigManager(this);

        getServer().getPluginManager().registerEvents(new MenuListener(this.menuManager), this);


        if(getConfiguration().getBoolean("database.enabled")) {
            database = new MySQL(this);
        } else {
            database = new SQLite(this);
        }
    }

    @Override
    public void onDisable() {

    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public YamlConfiguration getConfiguration() {
        return configManager.getConfig();
    }

    public YamlConfiguration getLang() {
        return configManager.getLang();
    }

    public YamlConfiguration getDeliveries() {
        return configManager.getLang();
    }

    public static Delivery getInstance() {
        return instance;
    }
}
