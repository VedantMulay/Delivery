/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery;

import io.th0rgal.oraxen.compatibilities.CompatibilitiesManager;
import lol.vedant.delivery.action.ActionManager;
import lol.vedant.delivery.api.menu.MenuListener;
import lol.vedant.delivery.api.menu.MenuManager;
import lol.vedant.delivery.commands.DeliveryCommand;
import lol.vedant.delivery.config.ConfigManager;
import lol.vedant.delivery.core.DeliveryManager;
import lol.vedant.delivery.database.Database;
import lol.vedant.delivery.database.MySQL;
import lol.vedant.delivery.database.SQLite;
import lol.vedant.delivery.hook.OraxenHook;
import lol.vedant.delivery.menu.MenuLoader;
import me.despical.commandframework.CommandFramework;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Delivery extends JavaPlugin  {

    private CommandFramework commandManager;
    private MenuManager menuManager;
    private MenuLoader menuLoader;
    private ConfigManager configManager;
    private ActionManager actionManager;
    private DeliveryManager deliveryManager;
    private Database database;

    private static Delivery instance;


    @Override
    public void onEnable() {
        CompatibilitiesManager.addCompatibility("Delivery", OraxenHook.class);

        instance = this;
        //Load the managers
        configManager = new ConfigManager(this);
        deliveryManager = new DeliveryManager(this);
        commandManager = new CommandFramework(this);
        commandManager.registerCommands(new DeliveryCommand());
        actionManager = new ActionManager(this);
        menuManager = new MenuManager(this);


        //Load Database
        if(getConfiguration().getBoolean("database.enabled")) {
            database = new MySQL(this);
        } else {
            database = new SQLite(this);
        }

        menuLoader = new MenuLoader(this);

        registerEvents();
        hooks();
    }


    @Override
    public void onDisable() {

    }

    public void hooks() {
        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            getLogger().info("Hooked into PlaceholderAPI");
        }

        if(Bukkit.getPluginManager().isPluginEnabled("Oraxen")) {
            getLogger().info("Hooked into Oraxen");
        }

        if(Bukkit.getPluginManager().isPluginEnabled("ItemsAdder")) {
            getLogger().info("Hooked into ItemsAdder");
        }
    }


    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new MenuListener(menuManager), this);
    }

    public MenuManager getMenuManager() {
        return menuManager;
    }

    public DeliveryManager getDeliveryManager() {
        return deliveryManager;
    }

    public YamlConfiguration getConfiguration() {
        return configManager.getConfig();
    }

    public YamlConfiguration getLang() {
        return configManager.getLang();
    }

    public YamlConfiguration getDeliveries() {
        return configManager.getGUI();
    }

    public YamlConfiguration getMenu() {
        return configManager.getMenu();
    }

    public static Delivery getInstance() {
        return instance;
    }

    public MenuLoader getMenuLoader() {
        return menuLoader;
    }
}
