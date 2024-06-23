/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.action.actions;

import lol.vedant.delivery.Delivery;
import lol.vedant.delivery.action.Action;
import lol.vedant.delivery.menu.DeliveryMenu;
import org.bukkit.entity.Player;

public class OpenAction implements Action {
    @Override
    public String getId() {
        return "OPEN";
    }

    @Override
    public void run(Delivery plugin, Player player, String data) {
        if(plugin.getMenuManager().isMenuOpen(player)) {
            plugin.getMenuManager().closeMenu(player);
        }
        plugin.getMenuManager().openMenu(player, new DeliveryMenu(player, data));
    }
}
