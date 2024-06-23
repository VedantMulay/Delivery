package lol.vedant.delivery.api.menu;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

public abstract class Menu implements InventoryHolder {

    protected Inventory inventory;
    protected String title;
    protected int size;

    public Menu(String title, int size) {
        this.title = title;
        this.size = size;
        this.inventory = Bukkit.createInventory(this, size, title);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setTitle(String title) {
        this.title = title;
        if (inventory != null) {
            inventory = Bukkit.createInventory(this, size, title);
        }
    }

    public String getTitle() {
        return title;
    }

    public void setSize(int size) {
        this.size = size;
        if (inventory != null) {
            inventory = Bukkit.createInventory(this, size, title);
        }
    }

    public int getSize() {
        return size;
    }

    public abstract void handleMenuClick(InventoryClickEvent e);
}
