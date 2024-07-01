/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.core;


import de.tr7zw.changeme.nbtapi.NBT;
import lol.vedant.delivery.Delivery;
import lol.vedant.delivery.api.item.ItemCreator;
import lol.vedant.delivery.utils.DurationParser;
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

                if(delivery.contains("actions")) {
                    playerDelivery.setFixedActions(delivery.getStringList("actions"));
                }

                if(delivery.contains("random_actions")) {
                    ConfigurationSection randomActionsSection = delivery.getConfigurationSection("random_actions");
                    Map<String, List<String>> randomActions = new HashMap<>();
                    if(randomActionsSection == null) {
                        break;
                    }
                    for (String key : randomActionsSection.getKeys(false)) {
                        randomActions.put(key, randomActionsSection.getStringList(key));
                    }
                    playerDelivery.setRandomActions(randomActions);
                }

                playerDelivery.setClaimInterval(DurationParser.parseDuration(delivery.getString("claim_interval")));

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

    public String getTimeUntilClaim(Player player, String deliveryId) {
        return TimeUtils.formatDuration(plugin.getDatabase().getTimeUntilClaim(player, deliveryId));
    }

    public void claimDelivery(Player player, String deliveryId) {
        PlayerDelivery delivery = deliveries.get(deliveryId);

        if(player.hasPermission(delivery.getPermission())) {
            if (canClaim(player, deliveryId)) {
                plugin.getDatabase().setClaimed(player, deliveryId);


            } else {
                //Send the message of remaining time to the player
            }


        } else {
            // No Permission Message
        }


    }

    public static List<String> getRandomAction(PlayerDelivery delivery) {
        Map<String, List<String>> randomActions = delivery.getRandomActions();
        if (randomActions != null && !randomActions.isEmpty()) {
            List<String> keys = new ArrayList<>(randomActions.keySet());
            String randomKey = keys.get(new Random().nextInt(keys.size()));
            return randomActions.get(randomKey);
        }
        return null;
    }



}
