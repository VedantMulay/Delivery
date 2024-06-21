package lol.vedant.delivery.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class MenuListener implements Listener {

    private final MenuManager menuManager;

    public MenuListener(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (menuManager.isMenuOpen(player)) {
                Menu menu = menuManager.getOpenMenu(player);
                if (event.getInventory().getHolder() instanceof Menu) {
                    event.setCancelled(true);
                    menu.handleMenuClick(event);
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player) {
            Player player = (Player) event.getPlayer();
            if (menuManager.isMenuOpen(player)) {
                menuManager.closeMenu(player);
            }
        }
    }
}