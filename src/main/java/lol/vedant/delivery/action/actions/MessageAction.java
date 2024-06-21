/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.action.actions;

import lol.vedant.delivery.Delivery;
import lol.vedant.delivery.action.Action;
import lol.vedant.delivery.utils.Utils;
import org.bukkit.entity.Player;

public class MessageAction implements Action {
    @Override
    public String getId() {
        return "MESSAGE";
    }

    @Override
    public void run(Delivery plugin, Player player, String data) {
        player.sendMessage(Utils.cc(data));
    }
}
