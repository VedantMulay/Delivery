/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.database;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface Database {

    void init();

    void createTables();

    void createUser(Player player);

    boolean exists(UUID uuid, String deliveryId);

    boolean canClaim(UUID uuid, String deliveryId);





}
