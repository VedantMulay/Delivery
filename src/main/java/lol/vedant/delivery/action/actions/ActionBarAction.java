/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.action.actions;

import lol.vedant.delivery.Delivery;
import lol.vedant.delivery.action.Action;
import lol.vedant.delivery.utils.Utils;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class ActionBar implements Action {
    @Override
    public String getId() {
        return "ACTIONBAR";
    }

    @Override
    public void run(Delivery plugin, Player player, String data) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(Utils.cc(data)));
    }
}
