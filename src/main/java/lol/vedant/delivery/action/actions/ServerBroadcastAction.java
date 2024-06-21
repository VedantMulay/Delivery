/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.action.actions;

import lol.vedant.delivery.Delivery;
import lol.vedant.delivery.action.Action;
import lol.vedant.delivery.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ServerBroadcastAction implements Action {

    @Override
    public String getId() {
        return "BROADCAST";
    }

    @Override
    public void run(Delivery plugin, Player player, String data) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(Utils.cc(data));
        }
    }
}
