/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.commands;

import lol.vedant.delivery.utils.DurationParser;
import lol.vedant.delivery.utils.TimeUtils;
import me.despical.commandframework.Command;
import me.despical.commandframework.CommandArguments;
import org.bukkit.command.CommandSender;

public class TimeTestCommand {

    @Command(
            name="time-test"
    )
    public void execute(CommandArguments args) {
        CommandSender sender = args.getSender();

        sender.sendMessage(TimeUtils.formatDuration(DurationParser.parseDuration(args.getArgument(0))));
    }

}
