/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.stream.Collectors;

public class PlaceholderParse {

    /**
     * Parses placeholders in the given ItemStack for the specified player.
     *
     * @param item   The ItemStack to parse.
     * @param player The player for whom to parse the placeholders.
     * @return A new ItemStack with parsed placeholders.
     */
    public static ItemStack parsePlaceholders(ItemStack item, Player player) {
        if (item == null) {
            return null;
        }

        ItemStack clonedItem = item.clone();
        ItemMeta meta = clonedItem.getItemMeta();

        if (meta != null) {
            meta.setDisplayName(parsePlaceholder(meta.getDisplayName(), player));
            meta.setLore(parseLore(meta.getLore(), player));
            clonedItem.setItemMeta(meta);
        }

        return clonedItem;
    }

    /**
     * Parses a single placeholder in the given text for the specified player.
     *
     * @param text   The text to parse.
     * @param player The player for whom to parse the placeholder.
     * @return The text with parsed placeholders.
     */
    private static String parsePlaceholder(String text, Player player) {
        if (text != null && text.contains("%")) { // Check if the text contains a placeholder
            return Utils.placeholder(player, text); // Replace with your placeholder parsing method
        }
        return text;
    }

    /**
     * Parses placeholders in a list of lore lines for the specified player.
     *
     * @param lore   The list of lore lines to parse.
     * @param player The player for whom to parse the placeholders.
     * @return The list of lore lines with parsed placeholders.
     */
    private static List<String> parseLore(List<String> lore, Player player) {
        if (lore != null) {
            return lore.stream()
                    .map(line -> parsePlaceholder(line, player))
                    .collect(Collectors.toList());
        }
        return lore;
    }
}
