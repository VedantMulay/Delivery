package lol.vedant.delivery.menu;


import de.tr7zw.changeme.nbtapi.NBT;
import lol.vedant.delivery.Delivery;
import lol.vedant.delivery.api.menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.List;

public class DeliveryMenu extends Menu {

    private static Delivery plugin = Delivery.getInstance();
    private Player player;
    private String menu;

    public DeliveryMenu(Player player, String menu) {
        super("Delivery", 27);
        this.player = player;
        this.menu = menu;

        // Initialize the inventory with correct size and title
        MenuPage page = plugin.getMenuLoader().getPage("delivery");
        setTitle(page.getTitle());
        setSize(page.getRows() * 9);
        this.inventory = Bukkit.createInventory(this, getSize(), getTitle());

        // Set the menu items
        setMenuItems();
    }

    @Override
    public void handleMenuClick(InventoryClickEvent e) {

        if(e.getCurrentItem() != null) {
            NBT.get(e.getCurrentItem(), nbt -> {
                ItemType.valueOf(nbt.getOrDefault("Delivery.Type", null));

            });
        }
    }

    @Override
    public void setMenuItems() {
        MenuPage page = plugin.getMenuLoader().getPage("delivery");
        List<MenuItem> pItems = page.getItems();

        for (MenuItem item : pItems) {
            if (item.getItemType() == ItemType.ITEM) {
                getInventory().setItem(item.getSlot(), item.getNormalItem());
            } else if (item.getItemType() == ItemType.DELIVERY) {
                if (player.hasPermission("permission")) {
                    getInventory().setItem(item.getSlot(), item.getClaimItem());
                } else {
                    getInventory().setItem(item.getSlot(), item.getNoPermissionItem());
                }
            }
        }
    }
}
