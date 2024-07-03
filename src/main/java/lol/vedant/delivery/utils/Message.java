/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.utils;

import lol.vedant.delivery.Delivery;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;

public enum Message {

    PREFIX("GENERAL.PREFIX"),
    NO_PERMISSION("GENERAL.NO_PERMISSION"),
    PLAYER_ONLY("GENERAL.PLAYER_ONLY"),
    HELP_1("HELP.1"),
    RELOAD("GENERAL.RELOAD"),

    DELIVERY_CLAIM("DELIVERY.CLAIM"),
    DELIVERY_NO_PERMISSION("DELIVERY.NO_PERMISSION"),
    DELIVERY_WAIT("DELIVERY.WAIT"),

    PACK_27_TITLE("RESOURCE_PACK.27_GUI_TITLE"),
    PACK_54_TITLE("RESOURCE_PACK.5_GUI_TITLE");


    private final String path;
    private static FileConfiguration config;

    Message(String path) {
        this.path = path;
    }

    public static void setConfiguration(FileConfiguration c) {
        config = c;
    }

    public void send(CommandSender receiver, Object... replacements) {
        Object value = config.get(this.path);

        String message;
        if (value == null) {
            message = "BattleBox: message not found (" + this.path + ")";
        } else {
            message = value instanceof List ? Utils.fromList((List<?>) value) : value.toString();
        }

        if (!message.isEmpty()) {
            if(Delivery.PLACEHOLDER_API) {
                if(receiver instanceof Player) {
                    receiver.sendMessage(PlaceholderAPI.setPlaceholders((Player) receiver, Utils.cc(replace(message, replacements))));
                } else {
                    receiver.sendMessage(Utils.cc(replace(message, replacements)));
                }
            } else {
                receiver.sendMessage(Utils.cc(replace(message, replacements)));
            }
        }
    }


    private String replace(String message, Object... replacements) {
        for (int i = 0; i < replacements.length; i += 2) {
            if (i + 1 >= replacements.length) break;
            message = message.replace(String.valueOf(replacements[i]), String.valueOf(replacements[i + 1]));
        }

        String prefix = config.getString(PREFIX.getPath());

        return message
                .replace("{prefix}", prefix != null && !prefix.isEmpty() ? prefix : "");
    }

    public String getPath() {
        return this.path;
    }

    public String asString() {
        return Utils.cc(replace(config.getString(this.path)));
    }


}
