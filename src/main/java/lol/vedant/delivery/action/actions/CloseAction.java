/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.action.actions;

import lol.vedant.delivery.Delivery;
import lol.vedant.delivery.action.Action;
import org.bukkit.entity.Player;

public class CloseAction implements Action {
    @Override
    public String getId() {
        return "CLOSE";
    }

    @Override
    public void run(Delivery plugin, Player player, String data) {
        if(plugin.getMenuManager().isMenuOpen(player)) {
            plugin.getMenuManager().closeMenu(player);
        }
    }
}
