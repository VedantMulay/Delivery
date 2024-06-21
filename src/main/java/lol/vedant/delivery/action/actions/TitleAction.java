/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.action.actions;

import lol.vedant.delivery.Delivery;
import lol.vedant.delivery.action.Action;
import lol.vedant.delivery.utils.Utils;
import org.bukkit.entity.Player;

public class TitleAction implements Action {

    @Override
    public String getId() {
        return "TITLE";
    }

    @Override
    public void run(Delivery plugin, Player player, String data) {
        String[] args = data.split(";");

        String mainTitle = Utils.cc(args[0]);
        String subTitle = Utils.cc(args[1]);

        int fadeIn;
        int stay;
        int fadeOut;
        try {
            fadeIn = Integer.parseInt(args[2]);
            stay = Integer.parseInt(args[3]);
            fadeOut = Integer.parseInt(args[4]);
        } catch (NumberFormatException e) {
            fadeIn = 1;
            stay = 3;
            fadeOut = 1;
        }

        player.sendTitle(mainTitle, subTitle, fadeIn * 20, stay * 20, fadeOut * 20);

    }
}
