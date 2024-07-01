package lol.vedant.delivery.menu;

import de.tr7zw.changeme.nbtapi.NBT;
import lol.vedant.delivery.Delivery;
import lol.vedant.delivery.api.menu.Menu;
import lol.vedant.delivery.core.DeliveryManager;
import lol.vedant.delivery.core.PlayerDelivery;
import lol.vedant.delivery.utils.Message;
import lol.vedant.delivery.utils.PlaceholderParse;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

public class DeliveryMenu extends Menu {

    private static final Delivery plugin = Delivery.getInstance();
    private final Player player;
    private final String menu;
    private List<MenuItem> items;
    private int reloadTask;

    public DeliveryMenu(Player player, String menu) {
        super("Delivery", 27);
        this.player = player;
        this.menu = menu;

        BukkitTask task = Bukkit.getScheduler().runTaskTimer(plugin, this::setMenuItems, 0L, 20L);
        this.reloadTask = task.getTaskId();
    }

    @Override
    public void handleMenuClick(InventoryClickEvent e) {
        if (e.getCurrentItem() != null) {
            NBT.get(e.getCurrentItem(), nbt -> {
                String typeStr = nbt.getString("Delivery.Type");
                String delId = nbt.getString("Delivery.Id");

                if (typeStr.equals("DELIVERY")) {
                    processDeliveryClick(delId);
                } else {
                    processItemClick(e.getSlot());
                }
            });
            reloadMenu();
        }
    }

    @Override
    public void handleMenuClose(InventoryCloseEvent event) {
        player.sendMessage("You closed the menu. The scheduler has been closed.");
        Bukkit.getScheduler().cancelTask(reloadTask);
    }

    public void processDeliveryClick(String deliveryId) {
        DeliveryManager manager = plugin.getDeliveryManager();
        PlayerDelivery delivery = DeliveryManager.deliveries.get(deliveryId);

        if (player.hasPermission(delivery.getPermission())) {
            if (manager.canClaim(player, deliveryId)) {
                Message.DELIVERY_CLAIM.send(player);

                if (delivery.getFixedActions() != null) {
                    plugin.getActionManager().runActions(player, delivery.getFixedActions());
                }

                List<String> randomActions = DeliveryManager.getRandomAction(delivery);
                if (randomActions != null) {
                    plugin.getActionManager().runActions(player, randomActions);
                }

                plugin.getDatabase().setClaimed(player, deliveryId);
            } else {
                Message.DELIVERY_WAIT.send(player, "{time}", manager.getTimeUntilClaim(player, deliveryId));
            }
        } else {
            Message.DELIVERY_NO_PERMISSION.send(player);
        }
    }

    public void processItemClick(int slot) {
        List<String> actions = items.stream()
                .filter(item -> item.getSlot() == slot)
                .findFirst()
                .map(MenuItem::getActions)
                .orElse(null);

        if (actions != null) {
            plugin.getActionManager().runActions(player, actions);
        }
    }

    @Override
    public void setMenuItems() {
        MenuPage page = plugin.getMenuLoader().getPage(menu);
        items = page.getItems();


        for (MenuItem item : items) {

            if (item.getItemType() == ItemType.ITEM) {
                getInventory().setItem(item.getSlot(), item.getNormalItem());
            } else if (item.getItemType() == ItemType.DELIVERY) {
                PlayerDelivery delivery = DeliveryManager.deliveries.get(NBT.readNbt(item.getClaimedItem()).getString("Delivery.Id"));
                DeliveryManager manager = plugin.getDeliveryManager();

                if (player.hasPermission(delivery.getPermission())) {
                    if (manager.canClaim(player, delivery.getId())) {
                        getInventory().setItem(item.getSlot(), PlaceholderParse.parsePlaceholders(item.getClaimItem(), player));

                    } else {
                        getInventory().setItem(item.getSlot(), PlaceholderParse.parsePlaceholders(item.getClaimedItem(), player));
                        System.out.println(item.getClaimedItem());
                    }
                } else {
                    getInventory().setItem(item.getSlot(), PlaceholderParse.parsePlaceholders(item.getNoPermissionItem(), player));
                    System.out.println(item.getNoPermissionItem());
                }
            }
        }
    }

}
