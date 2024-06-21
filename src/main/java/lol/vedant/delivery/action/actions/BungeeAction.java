/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.action.actions;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import lol.vedant.delivery.Delivery;
import lol.vedant.delivery.action.Action;
import org.bukkit.entity.Player;

public class BungeeAction implements Action {

    @Override
    public String getId() {
        return "BUNGEE";
    }

    @Override
    public void run(Delivery plugin, Player player, String data) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("ConnectOther");
        out.writeUTF(player.getName());
        out.writeUTF(data);
        player.sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
    }
}
