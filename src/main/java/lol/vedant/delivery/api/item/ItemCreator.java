package lol.vedant.delivery.api.item;

import io.th0rgal.oraxen.api.OraxenItems;
import lol.vedant.delivery.utils.OraxenSupport;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemCreator {

    private final PluginItem item;
    private final ItemStack itemStack;
    private final ItemMeta meta;

    public ItemCreator(PluginItem item) throws Exception {
        this.item = item;
        String material = item.getMaterial();
        //Handle if the item is an Oraxen Item
        if(material.startsWith("oraxen-")) {
            String oraxenId = material.replace("oraxen-", "");

            if(!OraxenSupport.exists(oraxenId)) {
                throw new Exception("Invalid Oraxen Item. Please check the ID and try again");
            }
            this.itemStack = OraxenSupport.getItem(oraxenId);

        } else if(Material.valueOf(item.getMaterial()).isItem()) {

            this.itemStack = new ItemStack(Material.valueOf(material));
        } else {
            throw new Exception("Invalid Bukkit Material ID. Please check the ID and try again.");
        }

        this.meta = itemStack.getItemMeta();
        if(meta == null) {
            return;
        }

        //Change the values of the item if the values are provided
        if(item.getDisplayName() != null) {
            meta.setDisplayName(item.getDisplayName());
        }

        if(item.getLore() != null) {
            meta.setLore(item.getLore());
        }

        if(item.getItemFlags() != null) {
            for (ItemFlag flag : item.getItemFlags()) {
                meta.addItemFlags(flag);
            }
        }

        if(item.getEnchantment() != null) {
            for(Enchantment enchant : item.getEnchantment()) {
                meta.addEnchant(enchant, meta.getEnchantLevel(enchant), true);
            }
        }

        if(item.getAmount() == 0) {
            itemStack.setAmount(1);
        } else {
            itemStack.setAmount(item.getAmount());
        }

        //Should be a non-zero value
        if(item.getCustomModelId() != 0) {
            meta.setCustomModelData(item.getCustomModelId());
        }

    }

    public ItemStack build() {
        this.itemStack.setItemMeta(this.meta);
        return this.itemStack;
    }

}