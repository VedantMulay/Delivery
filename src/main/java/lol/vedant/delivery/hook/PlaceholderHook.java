/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.hook;

import lol.vedant.delivery.Delivery;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class PlaceholderHook extends PlaceholderExpansion {

    private Delivery plugin;

    public PlaceholderHook(Delivery plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "delivery";
    }

    @Override
    public @NotNull String getAuthor() {
        return "COMPHACK (Vedant)";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, @NotNull String params) {
        String[] args = params.split("_");

        if(args[0].equals("remaining")) {
            return plugin.getDeliveryManager().getTimeUntilClaim((Player) player, args[1]);
        }
        return null;
    }
}
