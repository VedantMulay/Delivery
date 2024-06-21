package lol.vedant.delivery.api.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class MenuManager implements Listener {

    private final JavaPlugin plugin;
    private final Map<Player, Menu> openMenus;

    public MenuManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.openMenus = new HashMap<>();
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void openMenu(Player player, Menu menu) {
        openMenus.put(player, menu);
        player.openInventory(menu.getInventory());
    }

    public void closeMenu(Player player) {
        openMenus.remove(player);
        player.closeInventory();
    }

    public boolean isMenuOpen(Player player) {
        return openMenus.containsKey(player);
    }

    public Menu getOpenMenu(Player player) {
        return openMenus.get(player);
    }
}
