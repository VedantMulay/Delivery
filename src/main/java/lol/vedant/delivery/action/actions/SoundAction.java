/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.action.actions;

import lol.vedant.delivery.Delivery;
import lol.vedant.delivery.action.Action;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundAction implements Action {

    @Override
    public String getId() {
        return "SOUND";
    }

    @Override
    public void run(Delivery plugin, Player player, String data) {
        player.playSound(player.getLocation(), Sound.valueOf(data), 1L, 1L);
    }
}
