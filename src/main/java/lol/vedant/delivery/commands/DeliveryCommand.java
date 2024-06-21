/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.commands;


import lol.vedant.delivery.Delivery;
import lol.vedant.delivery.menu.DeliveryMenu;
import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeliveryCommand {



    @Command(
            name = "delivery",
            desc = "Get your deliveries",
            senderType = Command.SenderType.PLAYER,
            permission = "delivery.menu.open"
    )

    public void execute(CommandArguments args) {

        CommandSender sender = args.getSender();

        if(args.isSenderConsole()) {
            sender.sendMessage("This command can only be executed by a player.");
            return;
        }

        Delivery.getInstance().getMenuManager().openMenu((Player) sender, new DeliveryMenu("default"));


    }
}
