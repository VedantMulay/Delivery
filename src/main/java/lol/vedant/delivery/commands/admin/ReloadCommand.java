/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.commands.admin;

import lol.vedant.delivery.Delivery;
import lol.vedant.delivery.utils.Message;
import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;

public class ReloadCommand {

    private Delivery plugin;

    public ReloadCommand(Delivery plugin) {
        this.plugin = plugin;
    }

    @Command(
            name = "deliverymaster.reload",
            aliases = {
                    "delivery-master.reload"
            },
            desc = "Reload the configurations files"
    )

    public void execute(CommandArguments args) {
        plugin.reload();
        Message.RELOAD.send(args.getSender());
    }


}
