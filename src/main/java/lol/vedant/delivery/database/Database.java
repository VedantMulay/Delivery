/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.database;

import org.bukkit.entity.Player;

import java.time.Duration;


public interface Database {

    void init();

    void createTables();

    void createUser(Player player);

    boolean exists(Player player, String deliveryId);

    boolean canClaim(Player player, String deliveryId);

    void setClaimed(Player player, String deliveryId);

    Duration getTimeUntilClaim(Player player, String deliveryId);

}
