/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.action;

import lol.vedant.delivery.Delivery;
import lol.vedant.delivery.action.actions.*;
import lol.vedant.delivery.utils.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionManager {

    private final Delivery plugin;
    private final Map<String, Action> actions;

    public ActionManager(Delivery plugin) {
        this.plugin = plugin;
        actions = new HashMap<>();
        load();
    }


    private void load() {
        registerAction(
                new ActionBarAction(),
                new BungeeAction(),
                new CommandAction(),
                new ConsoleCommandAction(),
                new GamemodeAction(),
                new MessageAction(),
                new PotionEffectAction(),
                new ServerBroadcastAction(),
                new SoundAction(),
                new TitleAction(),
                new OpenAction(),
                new CloseAction()
        );
    }

    public void registerAction(Action... actions) {
        Arrays.asList(actions).forEach(action -> this.actions.put(action.getId(), action));
    }

    public void runActions(Player player, List<String> items) {
        items.forEach(item -> {
            String actionName = StringUtils.substringBetween(item, "[", "]");
            Action action = actionName == null ? null : actions.get(actionName.toUpperCase());

            if(action != null) {
                item = item.contains(" ") ? item.split(" ", 2)[1] : "";
                item = Utils.placeholder(player, item);
                action.run(plugin, player, item);


            } else {
                plugin.getLogger().warning("Could not execute action: " + item);
            }
        });
    }



}
