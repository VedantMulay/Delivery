/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.npc;


import lol.vedant.delivery.Delivery;

import lol.vedant.delivery.utils.Utils;
import net.jitse.npclib.api.NPC;
import net.jitse.npclib.api.skin.MineSkinFetcher;
import net.jitse.npclib.api.skin.Skin;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class NPCManager {

    private final Delivery plugin;
    private final YamlConfiguration config;
    private List<NPC> loadedNPC = new ArrayList<>();

    public NPCManager(Delivery plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfiguration();
    }

    public void load() {
        if(!plugin.getConfiguration().contains("npc")) {
            return;
        }

        Set<String> npcIds = config.getConfigurationSection("npc").getKeys(false);

        for (String npcId : npcIds) {
            ConfigurationSection npc = config.getConfigurationSection("npc." + npcId);

            NPC pNPC = plugin.getNPCLib().createNPC(npc.getStringList("hologram"));

            MineSkinFetcher.fetchSkinFromIdAsync(1327085752 , skin -> {
                pNPC.setSkin(new Skin(skin.getValue(), skin.getSignature()));
            });
            pNPC.setLocation(Utils.parseLoc(npc.getString("location")));
            pNPC.create();

            loadedNPC.add(pNPC);

        }
    }

    public void reload() {
        for (NPC npc : loadedNPC) {
            npc.destroy();
            loadedNPC.remove(npc);
        }

        load();
    }


}
