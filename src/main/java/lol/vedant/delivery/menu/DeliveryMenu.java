/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.menu;

import lol.vedant.delivery.Delivery;
import lol.vedant.delivery.api.item.ItemCreator;
import lol.vedant.delivery.api.item.PluginItem;
import lol.vedant.delivery.api.menu.Menu;
import lol.vedant.delivery.data.PlayerDelivery;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;
import java.util.Set;


public class DeliveryMenu extends Menu {

    private static Delivery plugin = Delivery.getInstance();


    public DeliveryMenu(Player player, String menu) {
        super("Delivery", 27);



        MenuPage page = plugin.getMenuLoader().getPage("delivery");

        String title = page.getTitle();
        int size = page.getRows() * 9;

        setTitle(title);
        setSize(size);
        List<MenuItem> pItems = page.getItems();

        this.inventory = Bukkit.createInventory(this, getSize(), getTitle());

        for (MenuItem item : pItems) {
            if(item.getItemType() == ItemType.ITEM) {
                getInventory().setItem(item.getSlot(), item.getNormalItem());
            } else if(item.getItemType() == ItemType.DELIVERY) {
                if(player.hasPermission("permission")) {
                    getInventory().setItem(item.getSlot(), item.getClaimItem());
                } else {
                    getInventory().setItem(item.getSlot(), item.getNoPermissionItem());
                }
            }
        }


    }



    @Override
    public void handleMenuClick(InventoryClickEvent e) {

    }
}
