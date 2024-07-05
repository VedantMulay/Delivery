/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.commands;


import lol.vedant.delivery.Delivery;
import lol.vedant.delivery.menu.DeliveryMenu;
import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;
import org.bukkit.entity.Player;



public class DeliveryCommand {


    private Delivery plugin = Delivery.getInstance();

    @Command(
            name = "delivery",
            desc = "Get your deliveries",
            senderType = Command.SenderType.PLAYER
    )

    public void execute(CommandArguments args) {
        if(!(args.getSender() instanceof Player)) {
            return;
        }
        Player sender = args.getSender();

        if(args.isSenderConsole()) {
            sender.sendMessage("This command can only be executed by a player.");
            return;
        }

        if(args.getArguments().length == 0) {
            Delivery.getInstance().getMenuManager().openMenu(sender, new DeliveryMenu(sender, "delivery"));
        } else {
            if(plugin.getMenuLoader().getPage(args.getArgument(0)) != null) {
                Delivery.getInstance().getMenuManager().openMenu(sender, new DeliveryMenu(sender, args.getArgument(0)));
            } else {
                // Page not found message
            }
        }


    }
}
