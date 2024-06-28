/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.core;

import org.bukkit.inventory.ItemStack;

import java.time.Duration;

public class PlayerDelivery {

    private final String id;

    private ItemStack claimItem;
    private ItemStack noPermissionItem;
    private ItemStack claimedItem;
    private ItemStack fillerItem;

    private int slot;
    private String permission;
    private Duration claimInterval;


    public PlayerDelivery(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public ItemStack getClaimItem() {
        return claimItem;
    }

    public void setClaimItem(ItemStack claimItem) {
        this.claimItem = claimItem;
    }

    public ItemStack getNoPermissionItem() {
        return noPermissionItem;
    }

    public void setNoPermissionItem(ItemStack noPermissionItem) {
        this.noPermissionItem = noPermissionItem;
    }

    public ItemStack getClaimedItem() {
        return claimedItem;
    }

    public void setClaimedItem(ItemStack claimedItem) {
        this.claimedItem = claimedItem;
    }

    public ItemStack getFillerItem() {
        return fillerItem;
    }

    public void setFillerItem(ItemStack fillerItem) {
        this.fillerItem = fillerItem;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Duration getClaimInterval() {
        return claimInterval;
    }

    public void setClaimInterval(Duration claimInterval) {
        this.claimInterval = claimInterval;
    }
}
