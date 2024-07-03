/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.api.menu;

import lol.vedant.delivery.menu.MenuItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public abstract class Menu implements InventoryHolder {

    private final String title;
    private final int size;
    protected Inventory inventory;
    protected Player player;
    protected Map<Integer, MenuItem> menuItems;

    public Menu(String title, int size) {
        this.title = title;
        this.size = size;
        this.menuItems = new HashMap<>();
        this.inventory = Bukkit.createInventory(this, size, title);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public String getTitle() {
        return title;
    }

    public int getSize() {
        return size;
    }

    public void setTitle(String title) {
        inventory = Bukkit.createInventory(this, size, title);
    }

    public void setSize(int size) {
        inventory = Bukkit.createInventory(this, size, title);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void addItem(MenuItem item) {
        menuItems.put(item.getSlot(), item);
        updateItem(item);
    }

    public void updateItem(MenuItem item) {
        // Subclasses should implement this method to update inventory based on MenuItem properties
    }

    public void removeItem(int slot) {
        menuItems.remove(slot);
        inventory.setItem(slot, null);
    }

    public void clearMenu() {
        menuItems.clear();
        inventory.clear();
    }

    public abstract void handleMenuClick(InventoryClickEvent event);

    public abstract void handleMenuClose(InventoryCloseEvent event);

    public abstract void setMenuItems();

    public void reloadMenu() {
        clearMenu();
        setMenuItems();
    }
}
