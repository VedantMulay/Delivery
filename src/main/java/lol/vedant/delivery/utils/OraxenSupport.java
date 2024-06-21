package lol.vedant.delivery.utils;

import io.th0rgal.oraxen.api.OraxenItems;
import io.th0rgal.oraxen.compatibilities.CompatibilityProvider;
import lol.vedant.delivery.Delivery;
import org.bukkit.inventory.ItemStack;

public class DeliveryPluginSupport extends CompatibilityProvider<Delivery> {

    public static ItemStack getItem(String id) {
        return OraxenItems.getItemById(id).build();
    }

}
