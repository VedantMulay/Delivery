package lol.vedant.delivery.menu;

import lol.vedant.delivery.Delivery;
import lol.vedant.delivery.data.PlayerDelivery;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuItem {

    private final ItemType itemType;
    private ItemStack noPermissionItem;
    private ItemStack claimItem;
    private ItemStack claimedItem;
    private ItemStack normalItem;
    private static Delivery plugin = Delivery.getInstance();
    private final int slot;
    private static final Logger logger = Logger.getLogger(MenuItem.class.getName());

    // Constructor for ITEM type
    public MenuItem(ItemType itemType, int slot, ItemStack item) {
        if (itemType != ItemType.ITEM) {
            throw new IllegalArgumentException("Invalid item type.");
        }

        this.itemType = itemType;
        this.slot = slot;
        this.normalItem = item;

        logger.log(Level.FINE, "Created MenuItem of type ITEM at slot {0}", slot);
        logItemDetails();
    }

    // Constructor for DELIVERY type
    public MenuItem(ItemType itemType, int slot, String deliveryId) {
        if (itemType != ItemType.DELIVERY) {
            throw new IllegalArgumentException("Invalid item type for this constructor.");
        }
        this.itemType = itemType;
        this.slot = slot;

        PlayerDelivery delivery = plugin.getDeliveryManager().getDelivery(deliveryId);
        this.claimedItem = delivery.getClaimedItem();
        this.claimItem = delivery.getClaimItem();
        this.noPermissionItem = delivery.getNoPermissionItem();

        logger.log(Level.FINE, "Created MenuItem of type DELIVERY for delivery ID {0} at slot {1}", new Object[]{deliveryId, slot});
        logItemDetails();
    }

    // Logging method to print detailed item information
    private void logItemDetails() {
        logger.log(Level.FINEST, "Item details:");
        logger.log(Level.FINEST, " - Slot: {0}", slot);
        logger.log(Level.FINEST, " - ItemType: {0}", itemType);
        logger.log(Level.FINEST, " - NormalItem: {0}", normalItem);
        logger.log(Level.FINEST, " - ClaimItem: {0}", claimItem);
        logger.log(Level.FINEST, " - ClaimedItem: {0}", claimedItem);
        logger.log(Level.FINEST, " - NoPermissionItem: {0}", noPermissionItem);
    }

    public ItemType getItemType() {
        return itemType;
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack getNoPermissionItem() {
        return noPermissionItem;
    }

    public ItemStack getClaimItem() {
        return claimItem;
    }

    public ItemStack getClaimedItem() {
        return claimedItem;
    }

    public ItemStack getNormalItem() {
        return normalItem;
    }
}
