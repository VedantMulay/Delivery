/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.database;

import lol.vedant.delivery.Delivery;
import lol.vedant.delivery.core.DeliveryManager;
import lol.vedant.delivery.core.PlayerDelivery;
import lol.vedant.delivery.utils.TimeUtils;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;

public class SQLite implements Database {

    private final Delivery plugin;
    private Connection connection;
    private final String url;

    public SQLite(Delivery plugin) {
        this.plugin = plugin;
        File database = new File(plugin.getDataFolder(), "database.db");

        if (!database.exists()) {
            try {
                database.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.url = "jdbc:sqlite:" + database.getAbsolutePath();
        init();
    }

    @Override
    public void init() {
        try {
            Class.forName("org.sqlite.JDBC");
            this.connection = DriverManager.getConnection(url);
        } catch (SQLException | ClassNotFoundException e) {
            if (e instanceof ClassNotFoundException) {
                plugin.getLogger().severe("SQLite driver not found on your system!");
                plugin.getLogger().severe("Disabling Delivery Plugin...");
                plugin.getPluginLoader().disablePlugin(plugin);
            }
            e.printStackTrace();
        }
        createTables();
    }

    @Override
    public void createTables() {
        final String sql = "CREATE TABLE IF NOT EXISTS player_deliveries (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "uuid VARCHAR(36) NOT NULL," +
                "name VARCHAR(32)," +
                "delivery_id VARCHAR(255) NOT NULL," +
                "last_claim TIMESTAMP DEFAULT NULL," +
                "UNIQUE (uuid, delivery_id))";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createUser(Player player) {
        final String sql = "INSERT OR IGNORE INTO player_deliveries (uuid, name, delivery_id) VALUES (?, ?, ?)";

        for (PlayerDelivery delivery : DeliveryManager.deliveries.values()) {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, player.getUniqueId().toString());
                ps.setString(2, player.getName());
                ps.setString(3, delivery.getId());
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean exists(Player player, String deliveryId) {
        final String sql = "SELECT * FROM player_deliveries WHERE uuid=? AND delivery_id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, player.getUniqueId().toString());
            ps.setString(2, deliveryId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean canClaim(Player player, String deliveryId) {
        final String sql = "SELECT * FROM player_deliveries WHERE uuid=? AND delivery_id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, player.getUniqueId().toString());
            ps.setString(2, deliveryId);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.getTimestamp("last_claim") == null) {
                    return true;
                }
                if (rs.next() && TimeUtils.isCooldownOver(rs.getTimestamp("last_claim").toLocalDateTime(), DeliveryManager.deliveries.get(deliveryId).getClaimInterval())) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void setClaimed(Player player, String deliveryId) {
        String sql = "UPDATE player_deliveries SET last_claim=? WHERE uuid=? AND delivery_id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            plugin.getLogger().info("Updated Player Claim Info");

            ps.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            ps.setString(2, player.getUniqueId().toString());
            ps.setString(3, deliveryId);

            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Duration getTimeUntilClaim(Player player, String deliveryId) {
        String sql = "SELECT last_claim FROM player_deliveries WHERE uuid=? AND delivery_id=?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, player.getUniqueId().toString());
            ps.setString(2, deliveryId);
            try (ResultSet rs = ps.executeQuery()) {
                if(rs.getTimestamp("last_claim") == null) {
                    return Duration.ZERO;
                }
                if (rs.next()) {
                    LocalDateTime lastClaim = rs.getTimestamp("last_claim").toLocalDateTime();
                    Duration claimInterval = DeliveryManager.deliveries.get(deliveryId).getClaimInterval();
                    return TimeUtils.getRemainingCooldown(lastClaim, claimInterval);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Duration.ZERO;
    }

    @Override
    public void shutdown() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
