/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.action.actions;

import lol.vedant.delivery.Delivery;
import lol.vedant.delivery.action.Action;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class GamemodeAction implements Action {

    @Override
    public String getId() {
        return "GAMEMODE";
    }

    @Override
    public void run(Delivery plugin, Player player, String data) {
        try {
            player.setGameMode(GameMode.valueOf(data.toUpperCase()));

        } catch (IllegalArgumentException e) {
            plugin.getLogger().severe("Invalid Gamemode: " + data);
        }
    }
}
