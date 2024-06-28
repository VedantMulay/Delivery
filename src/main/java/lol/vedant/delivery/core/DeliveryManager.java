/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.core;


import de.tr7zw.changeme.nbtapi.NBT;
import lol.vedant.delivery.Delivery;
import lol.vedant.delivery.api.item.ItemCreator;
import lol.vedant.delivery.utils.TimeUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


import java.util.*;

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
                    ItemStack claimItemStack = new ItemCreator(claimItem).build();

                    NBT.modify(claimItemStack, nbt -> {
                        nbt.setString("Delivery.Type", "DELIVERY");
                        nbt.setString("Delivery.Id", deliveryId);
                    });

                    playerDelivery.setClaimItem(claimItemStack);
                } else {
                    plugin.getLogger().warning("Claim item section is null for deliveryId: " + deliveryId);
                }

                if (noPermissionItem != null) {
                    ItemStack noPermItem = new ItemCreator(noPermissionItem).build();

                    NBT.modify(noPermItem, nbt -> {
                        nbt.setString("Delivery.Type", "DELIVERY");
                        nbt.setString("Delivery.Id", deliveryId);
                    });

                    playerDelivery.setNoPermissionItem(noPermItem);
                } else {
                    plugin.getLogger().warning("No permission item section is null for deliveryId: " + deliveryId);
                }

                if (claimedItem != null) {
                    ItemStack claimedItemStack = new ItemCreator(claimedItem).build();

                    NBT.modify(claimedItemStack, nbt -> {
                        nbt.setString("Delivery.Type", "DELIVERY");
                        nbt.setString("Delivery.Id", deliveryId);
                    });

                    playerDelivery.setClaimedItem(claimedItemStack);
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
                        ItemStack fillerItemStack = new ItemCreator(fillerItem).build();

                        NBT.modify(fillerItemStack, nbt -> {
                            nbt.setString("Delivery.Type", "ITEM");
                            nbt.setString("Delivery.Filler", "TRUE");
                        });

                        playerDelivery.setFillerItem(fillerItemStack);
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

    public boolean canClaim(Player player, String deliveryId) {
        return plugin.getDatabase().canClaim(player, deliveryId);
    }



}
