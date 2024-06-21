/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.action.actions;

import lol.vedant.delivery.Delivery;
import lol.vedant.delivery.action.Action;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PotionEffectAction implements Action {
    @Override
    public String getId() {
        return "EFFECT";
    }

    @Override
    public void run(Delivery plugin, Player player, String data) {
        String[] args = data.split(";");
        player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(args[0]), Integer.MAX_VALUE, Integer.parseInt(args[1]) - 1));
    }
}
