/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.action;

import lol.vedant.delivery.Delivery;
import org.bukkit.entity.Player;

public interface Action {

    String getId();

    void run(Delivery plugin, Player player, String data);

}
