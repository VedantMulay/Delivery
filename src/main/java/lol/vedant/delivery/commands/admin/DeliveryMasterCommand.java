/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.commands.admin;

import lol.vedant.delivery.Delivery;
import lol.vedant.delivery.utils.Message;
import lol.vedant.delivery.utils.Utils;
import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;
import org.bukkit.command.CommandSender;

public class DeliveryMasterCommand {

    @Command(
            name = "deliverymaster",
            aliases = {
                    "delivery-master",
            },
            desc = "Delivery master admin command"

    )
    public void execute(CommandArguments args) {
        CommandSender sender  = args.getSender();
        if(!sender.hasPermission("delivery.commands.admin")) {
            sender.sendMessage(Utils.cc("&fRunning &cDelivery Master &7v" + Delivery.getInstance().getDescription().getVersion()));
            return;
        }

        Message.HELP_1.send(sender);
    }

}
