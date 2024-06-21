package lol.vedant.delivery.menu;

import lol.vedant.delivery.Delivery;
import lol.vedant.delivery.api.item.ItemCreator;
import lol.vedant.delivery.api.item.PluginItem;
import lol.vedant.delivery.api.menu.Menu;
import org.bukkit.event.inventory.InventoryClickEvent;


public class DeliveryMenu extends Menu {

    private static Delivery plugin = Delivery.getInstance();

    public DeliveryMenu(String delivery) {
        super("Delivery", 27);





    }



    @Override
    public void handleMenuClick(InventoryClickEvent e) {

    }
}
