/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.api.npc;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import org.bukkit.Location;

import java.util.List;
import java.util.UUID;

public class DeliveryNPC {

    List<String> hologramLines;

    Location location;

    public DeliveryNPC(Location location, List<String> hologramLines) {
        this.location = location;
        this.hologramLines = hologramLines;
    }

    public List<String> getHologramLines() {
        return hologramLines;
    }

    public Location getLocation() {
        return location;
    }

    public void create() {
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();

        int entityId = (int) (Math.random() * 1000) + 2000; // Generate a unique entity ID
        UUID uuid = UUID.randomUUID(); // Generate a unique UUID for the NPC
        WrappedGameProfile gameProfile = new WrappedGameProfile(uuid, hologramLines.get(0));

        PacketContainer spawnPacket = manager.createPacket(PacketType.Play.Server.NAMED_ENTITY_SPAWN);
        spawnPacket.getIntegers().write(0, entityId);
        spawnPacket.getUUIDs().write(0, uuid);
        spawnPacket.getDoubles().write(0, location.getX());
        spawnPacket.getDoubles().write(1, location.getY());
        spawnPacket.getDoubles().write(2, location.getZ());
        spawnPacket.getBytes().write(0, (byte) ((location.getYaw() * 256.0F) / 360.0F));
        spawnPacket.getBytes().write(1, (byte) ((location.getPitch() * 256.0F) / 360.0F));

        PacketContainer metadataPacket = manager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
        metadataPacket.getIntegers().write(0, entityId);
        WrappedDataWatcher watcher = new WrappedDataWatcher();
        WrappedDataWatcher.WrappedDataWatcherObject pose = new WrappedDataWatcher.WrappedDataWatcherObject(6, WrappedDataWatcher.Registry.get(EnumWrappers.EntityPose.class));
        watcher.setObject(pose, EnumWrappers.EntityPose.STANDING);
        metadataPacket.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());

        
    }
}
