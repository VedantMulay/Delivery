package lol.vedant.delivery.api.item;

import lol.vedant.delivery.hook.OraxenHook;
import lol.vedant.delivery.utils.Utils;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemCreator {


    private final ItemStack itemStack;
    private final ItemMeta meta;

    private static final Map<String, Enchantment> ENCHANTMENT_MAP = new HashMap<>();

    static {
        ENCHANTMENT_MAP.put("PROTECTION", Enchantment.PROTECTION_ENVIRONMENTAL);
        ENCHANTMENT_MAP.put("FIRE_PROTECTION", Enchantment.PROTECTION_FIRE);
        ENCHANTMENT_MAP.put("FEATHER_FALLING", Enchantment.PROTECTION_FALL);
        ENCHANTMENT_MAP.put("BLAST_PROTECTION", Enchantment.PROTECTION_EXPLOSIONS);
        ENCHANTMENT_MAP.put("PROJECTILE_PROTECTION", Enchantment.PROTECTION_PROJECTILE);
        ENCHANTMENT_MAP.put("RESPIRATION", Enchantment.OXYGEN);
        ENCHANTMENT_MAP.put("AQUA_AFFINITY", Enchantment.WATER_WORKER);
        ENCHANTMENT_MAP.put("THORNS", Enchantment.THORNS);
        ENCHANTMENT_MAP.put("DEPTH_STRIDER", Enchantment.DEPTH_STRIDER);
        ENCHANTMENT_MAP.put("FROST_WALKER", Enchantment.FROST_WALKER);
        ENCHANTMENT_MAP.put("BINDING_CURSE", Enchantment.BINDING_CURSE);
        ENCHANTMENT_MAP.put("SHARPNESS", Enchantment.DAMAGE_ALL);
        ENCHANTMENT_MAP.put("SMITE", Enchantment.DAMAGE_UNDEAD);
        ENCHANTMENT_MAP.put("BANE_OF_ARTHROPODS", Enchantment.DAMAGE_ARTHROPODS);
        ENCHANTMENT_MAP.put("KNOCKBACK", Enchantment.KNOCKBACK);
        ENCHANTMENT_MAP.put("FIRE_ASPECT", Enchantment.FIRE_ASPECT);
        ENCHANTMENT_MAP.put("LOOTING", Enchantment.LOOT_BONUS_MOBS);
        ENCHANTMENT_MAP.put("SWEEPING_EDGE", Enchantment.SWEEPING_EDGE);
        ENCHANTMENT_MAP.put("EFFICIENCY", Enchantment.DIG_SPEED);
        ENCHANTMENT_MAP.put("SILK_TOUCH", Enchantment.SILK_TOUCH);
        ENCHANTMENT_MAP.put("UNBREAKING", Enchantment.DURABILITY);
        ENCHANTMENT_MAP.put("FORTUNE", Enchantment.LOOT_BONUS_BLOCKS);
        ENCHANTMENT_MAP.put("POWER", Enchantment.ARROW_DAMAGE);
        ENCHANTMENT_MAP.put("PUNCH", Enchantment.ARROW_KNOCKBACK);
        ENCHANTMENT_MAP.put("FLAME", Enchantment.ARROW_FIRE);
        ENCHANTMENT_MAP.put("INFINITY", Enchantment.ARROW_INFINITE);
        ENCHANTMENT_MAP.put("LUCK_OF_THE_SEA", Enchantment.LUCK);
        ENCHANTMENT_MAP.put("LURE", Enchantment.LURE);
        ENCHANTMENT_MAP.put("LOYALTY", Enchantment.LOYALTY);
        ENCHANTMENT_MAP.put("IMPALING", Enchantment.IMPALING);
        ENCHANTMENT_MAP.put("RIPTIDE", Enchantment.RIPTIDE);
        ENCHANTMENT_MAP.put("CHANNELING", Enchantment.CHANNELING);
        ENCHANTMENT_MAP.put("MULTISHOT", Enchantment.MULTISHOT);
        ENCHANTMENT_MAP.put("QUICK_CHARGE", Enchantment.QUICK_CHARGE);
        ENCHANTMENT_MAP.put("PIERCING", Enchantment.PIERCING);
        ENCHANTMENT_MAP.put("MENDING", Enchantment.MENDING);
        ENCHANTMENT_MAP.put("VANISHING_CURSE", Enchantment.VANISHING_CURSE);
        ENCHANTMENT_MAP.put("SOUL_SPEED", Enchantment.SOUL_SPEED);
    }

    public ItemCreator(ConfigurationSection config) throws Exception {

        String material = config.getString("material");

        // Handle if the item is an Oraxen Item
        if (material.startsWith("oraxen-")) {
            String oraxenId = material.replace("oraxen-", "");

            //Check for invalid ID's for Oraxen
            if (!OraxenHook.exists(oraxenId)) {
                itemStack = new ItemStack(Material.BARRIER);
                meta = itemStack.getItemMeta();
                meta.setDisplayName(Utils.cc("&cInvalid Oraxen Material ID"));
                itemStack.setItemMeta(meta);
                return;
            }

            this.itemStack = OraxenHook.getItem(oraxenId);

        } else {
            Material bukkitMaterial = Material.getMaterial(material.toUpperCase());

            //Check for invalid ID's for Bukkit
            if (bukkitMaterial == null || !bukkitMaterial.isItem()) {
                itemStack = new ItemStack(Material.BARRIER);
                meta = itemStack.getItemMeta();
                meta.setDisplayName(Utils.cc("&cInvalid Bukkit Material ID"));
                itemStack.setItemMeta(meta);
                return;
            }

            this.itemStack = new ItemStack(bukkitMaterial);
        }

        this.meta = itemStack.getItemMeta();

        if (config.contains("name")) {
            meta.setDisplayName(Utils.cc(config.getString("name")));
        }

        if (config.contains("lore")) {
            meta.setLore(Utils.cc(config.getStringList("lore")));
        }

        if (config.contains("enchantments")) {
            List<String> enchantments = config.getStringList("enchantments");

            for (String enchant : enchantments) {
                String[] enchants = enchant.split(";");

                if (enchants.length == 2) {
                    Enchantment enchantment = ENCHANTMENT_MAP.get(enchants[0].toUpperCase());

                    if (enchantment != null) {
                        int level;
                        try {
                            level = Integer.parseInt(enchants[1]);
                        } catch (NumberFormatException e) {
                            throw new Exception("Invalid enchantment level for " + enchants[0], e);
                        }

                        itemStack.addUnsafeEnchantment(enchantment, level);
                    } else {
                        throw new Exception("Invalid enchantment name: " + enchants[0]);
                    }

                } else {
                    throw new Exception("Enchantment format should be 'ENCHANTMENT_NAME;LEVEL'");
                }
            }
        }

        if (config.contains("item_flags")) {
            List<String> flags = config.getStringList("item_flags");
            for (String flag : flags) {
                try {
                    meta.addItemFlags(ItemFlag.valueOf(flag.toUpperCase()));
                } catch (IllegalArgumentException e) {
                    throw new Exception("Invalid item flag: " + flag, e);
                }
            }
        }

        if (config.contains("custom_model_id")) {
            meta.setCustomModelData(config.getInt("custom_model_id"));
        }

        this.itemStack.setItemMeta(this.meta);
    }

    public ItemStack build() {
        return this.itemStack;
    }
}
