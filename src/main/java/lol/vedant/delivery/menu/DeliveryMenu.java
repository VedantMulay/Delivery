/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.menu;

import lol.vedant.delivery.Delivery;
import lol.vedant.delivery.api.item.ItemCreator;
import lol.vedant.delivery.api.item.PluginItem;
import lol.vedant.delivery.api.menu.Menu;
import lol.vedant.delivery.data.PlayerDelivery;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;
import java.util.Set;


public class DeliveryMenu extends Menu {

    private static Delivery plugin = Delivery.getInstance();

    public DeliveryMenu(Player player, String delivery) {
        super("Delivery", 27);

        List<PlayerDelivery> deliveries = plugin.getDeliveryManager().getDeliveriesByParentId(delivery);

        for (PlayerDelivery pDelivery : deliveries) {
            if(!player.hasPermission(pDelivery.getPermission())) {
                getInventory().setItem(pDelivery.getSlot(), pDelivery.getNoPermissionItem());
            } else {
                getInventory().setItem(pDelivery.getSlot(), pDelivery.getClaimItem());
            }
        }

    }



    @Override
    public void handleMenuClick(InventoryClickEvent e) {

    }
}
