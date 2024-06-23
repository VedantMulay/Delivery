/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.api.item;

import lol.vedant.delivery.menu.ItemType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;

import java.util.List;

public class PluginItem {

    private String displayName;
    private List<String> lore;
    private List<Enchantment> enchantment;
    private List<ItemFlag> itemFlags;
    private int customModelId;
    private String material;
    private int amount;
    private ItemType type;


    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public List<Enchantment> getEnchantment() {
        return enchantment;
    }

    public void setEnchantment(List<Enchantment> enchantment) {
        this.enchantment = enchantment;
    }

    public List<ItemFlag> getItemFlags() {
        return itemFlags;
    }

    public void setItemFlags(List<ItemFlag> itemFlags) {
        this.itemFlags = itemFlags;
    }

    public int getCustomModelId() {
        return customModelId;
    }

    public void setCustomModelId(int customModelId) {
        this.customModelId = customModelId;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
