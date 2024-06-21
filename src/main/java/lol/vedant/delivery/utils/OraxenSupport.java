package lol.vedant.delivery.utils;

import io.th0rgal.oraxen.api.OraxenItems;
import io.th0rgal.oraxen.compatibilities.CompatibilityProvider;
import lol.vedant.delivery.Delivery;
import org.bukkit.inventory.ItemStack;

public class OraxenSupport extends CompatibilityProvider<Delivery> {

    public static ItemStack getItem(String id) {
        return OraxenItems.getItemById(id).build();
    }

    public static boolean exists(String id) {
        return OraxenItems.exists(id);
    }
}
