/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.listeners;

import lol.vedant.delivery.Delivery;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {


    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent e) {
        Delivery plugin = Delivery.getInstance();
        plugin.getDatabase().createUser(e.getPlayer());
    }

}
