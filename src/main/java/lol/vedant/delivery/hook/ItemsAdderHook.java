/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.hook;

import dev.lone.itemsadder.api.CustomStack;
import dev.lone.itemsadder.api.NotActuallyItemsAdderException;
import org.bukkit.inventory.ItemStack;

public class ItemsAdderHook {


    public static boolean exists(String id) {
        return CustomStack.isInRegistry(id);
    }

    public static ItemStack getItemById(String id) {
        try {
            return CustomStack.getInstance(id).getItemStack();
        } catch (NotActuallyItemsAdderException e) {
            return null;
        }
    }


}
