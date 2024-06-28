/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.commands;

import lol.vedant.delivery.core.DeliveryManager;
import lol.vedant.delivery.core.PlayerDelivery;
import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;
import org.bukkit.entity.Player;

import java.util.Map;

public class GetDeliveryItemCommand {

    @Command(
            name = "delivery-items",
            desc = "Get your deliveries",
            senderType = Command.SenderType.PLAYER,
            permission = "delivery.menu.open"
    )
    public void execute(CommandArguments args) {
        if(!(args.getSender() instanceof Player)) {
            return;
        }
        Player sender = (Player) args.getSender();

        Map.Entry<String, PlayerDelivery> entry = DeliveryManager.deliveries.entrySet().iterator().next();

        String key = entry.getKey();
        PlayerDelivery delivery  = entry.getValue();

        sender.getInventory().addItem(delivery.getClaimedItem(), delivery.getClaimItem(), delivery.getNoPermissionItem());
        sender.sendMessage("Delivery ID: " + key);
    }

}
