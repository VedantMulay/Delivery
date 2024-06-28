package lol.vedant.delivery.menu;


import de.tr7zw.changeme.nbtapi.NBT;
import lol.vedant.delivery.Delivery;
import lol.vedant.delivery.api.item.ItemCreator;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuLoader {

    private YamlConfiguration config;
    private Map<String, MenuPage> menus = new HashMap<>();
    private static final Logger logger = Logger.getLogger(MenuLoader.class.getName());

    public MenuLoader(Delivery plugin) {
        this.config = plugin.getMenu();

        Set<String> menuIds = config.getKeys(false);

        for (String menu : menuIds) {
            ConfigurationSection menuSection = config.getConfigurationSection(menu);
            Set<String> itemIds = menuSection.getConfigurationSection("items").getKeys(false);

            logger.log(Level.INFO, "Loading menu {0}", menu);

            MenuPage page = new MenuPage(menuSection.getString("title"), menuSection.getInt("rows"));
            page.setId(menu);

            for (String itemId : itemIds) {
                ConfigurationSection item = menuSection.getConfigurationSection("items." + itemId);

                int slot = item.getInt("slot");
                String itemType = item.getString("type");

                try {
                    if (ItemType.valueOf(itemType) == ItemType.ITEM) {
                        ItemStack itemStack = new ItemCreator(item).build();

                        NBT.modify(itemStack, nbt -> {
                            nbt.setString("Delivery.Type", "ITEM");
                        });

                        MenuItem mItem = new MenuItem(ItemType.ITEM, slot, itemStack);
                        mItem.setActions(item.getStringList("actions"));
                        page.addItem(mItem);

                    } else if (ItemType.valueOf(itemType) == ItemType.DELIVERY) {
                        String deliveryId = item.getString("delivery-id");

                        page.addItem(new MenuItem(ItemType.DELIVERY, slot, deliveryId));
                    } else {
                        logger.log(Level.WARNING, "Unknown item type {0} in menu {1}, skipping", new Object[]{itemType, menu});
                    }
                } catch (Exception e) {
                    logger.log(Level.SEVERE, "Error loading item in menu {0}, slot {1}", new Object[]{menu, slot});
                    e.printStackTrace();
                }
            }

            menus.put(page.getId(), page);
        }

        logger.log(Level.INFO, "Menu loading complete. Loaded {0} menus.", menus.size());
    }

    public MenuPage getPage(String id) {
        return this.menus.get(id);
    }
}
