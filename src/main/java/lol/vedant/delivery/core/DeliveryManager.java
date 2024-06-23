/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.core;

import lol.vedant.delivery.Delivery;
import lol.vedant.delivery.api.item.ItemCreator;
import lol.vedant.delivery.data.PlayerDelivery;
import lol.vedant.delivery.utils.TimeUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;


import java.util.*;
import java.util.stream.Collectors;

public class DeliveryManager {

    private Delivery plugin;
    public static Map<String, PlayerDelivery> deliveries = new HashMap<>();
    private YamlConfiguration deliveryFile;


    public DeliveryManager(Delivery plugin) {
        this.plugin = plugin;
        this.deliveryFile = plugin.getDeliveries();

        try {
            load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load() throws Exception {
        plugin.getLogger().info("Loading deliveries data...");

            Set<String> deliveryIds = deliveryFile.getKeys(false);

            for (String deliveryId : deliveryIds) {
                ConfigurationSection delivery = deliveryFile.getConfigurationSection(deliveryId);
                if (delivery == null) {
                    plugin.getLogger().warning("Delivery section is null for deliveryId: " + deliveryId);
                    continue;
                }

                ConfigurationSection claimItem = delivery.getConfigurationSection("item");
                ConfigurationSection noPermissionItem = delivery.getConfigurationSection("no_permission_item");
                ConfigurationSection claimedItem = delivery.getConfigurationSection("claimed_item");

                PlayerDelivery playerDelivery = new PlayerDelivery(deliveryId);

                if (claimItem != null) {
                    playerDelivery.setClaimItem(new ItemCreator(claimItem).build());
                } else {
                    plugin.getLogger().warning("Claim item section is null for deliveryId: " + deliveryId);
                }

                if (noPermissionItem != null) {
                    playerDelivery.setNoPermissionItem(new ItemCreator(noPermissionItem).build());
                } else {
                    plugin.getLogger().warning("No permission item section is null for deliveryId: " + deliveryId);
                }

                if (claimedItem != null) {
                    playerDelivery.setClaimedItem(new ItemCreator(claimedItem).build());
                } else {
                    plugin.getLogger().warning("Claimed item section is null for deliveryId: " + deliveryId);
                }

                playerDelivery.setSlot(delivery.getInt("slot"));

                if (delivery.contains("permission")) {
                    playerDelivery.setPermission(delivery.getString("permission"));
                }

                if (delivery.contains("filler_item")) {
                    ConfigurationSection fillerItem = delivery.getConfigurationSection("filler_item");
                    if (fillerItem != null) {
                        playerDelivery.setFillerItem(new ItemCreator(fillerItem).build());
                    }
                }

                playerDelivery.setClaimInterval(TimeUtils.parseDuration(delivery.getString("claim_interval")));

                deliveries.put(deliveryId, playerDelivery);
            }

        plugin.getLogger().info("Loaded all deliveries...");
    }


    public PlayerDelivery getDelivery(String id) {
        return deliveries.get(id);
    }


}
