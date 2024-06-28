/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lol.vedant.delivery.Delivery;
import lol.vedant.delivery.core.DeliveryManager;
import lol.vedant.delivery.core.PlayerDelivery;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class MySQL implements Database {

    private HikariDataSource dataSource;
    private final Delivery plugin;
    private final String host;
    private final String database;
    private final String user;
    private final String pass;
    private final int port;
    private final boolean ssl;
    private final boolean certificateVerification;
    private final int poolSize;
    private final int maxLifetime;
    private YamlConfiguration config;

    public MySQL(Delivery plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfiguration();
        this.host = config.getString("database.host");
        this.database = config.getString("database.database");
        this.user = config.getString("database.user");
        this.pass = config.getString("database.pass");
        this.port = config.getInt("database.port");
        this.ssl = config.getBoolean("database.ssl");
        this.certificateVerification = config.getBoolean("database.verify-certificate", true);
        this.poolSize = config.getInt("database.pool-size", 10);
        this.maxLifetime = config.getInt("database.max-lifetime", 1800);
        init();
    }

    @Override
    public void init() {
        HikariConfig hikariConfig = new HikariConfig();

        hikariConfig.setPoolName("Delivery-pool");

        hikariConfig.setMaximumPoolSize(poolSize);
        hikariConfig.setMaxLifetime(maxLifetime);

        hikariConfig.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);

        hikariConfig.setUsername(user);
        hikariConfig.setPassword(pass);

        hikariConfig.addDataSourceProperty("useSSL", String.valueOf(ssl));

        if(!certificateVerification) {
            hikariConfig.addDataSourceProperty("verifyServerCertificate", String.valueOf(false));
        }

        hikariConfig.addDataSourceProperty("characterEncoding", "utf8");
        hikariConfig.addDataSourceProperty("encoding", "UTF-8");
        hikariConfig.addDataSourceProperty("useUnicode", "true");

        hikariConfig.addDataSourceProperty("rewriteBatchedStatements", "true");
        hikariConfig.addDataSourceProperty("jdbcCompliantTruncation", "false");

        hikariConfig.addDataSourceProperty("cachePrepStmts", "true");
        hikariConfig.addDataSourceProperty("prepStmtCacheSize", "275");
        hikariConfig.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        hikariConfig.addDataSourceProperty("socketTimeout", String.valueOf(TimeUnit.SECONDS.toMillis(30)));

        dataSource = new HikariDataSource(hikariConfig);

        try {
            dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        createTables();

    }

    @Override
    public void createTables() {
        String sql = "CREATE TABLE IF NOT EXISTS player_deliveries (" +
                "id INT(6) AUTO_INCREMENT PRIMARY KEY," +
                "uuid VARCHAR(36) UNIQUE NOT NULL," +
                "name VARCHAR(32)," +
                "delivery_id VARCHAR(255)," +
                "last_claim DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP";

        try (Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createUser(Player player) {
        String sql = "INSERT INTO player_deliveries (" +
                "uuid, name, delivery_i" +
                "d, last_claim) VALUES (" +
                "?, ?, ?, ?";
        for (PlayerDelivery delivery : DeliveryManager.deliveries.values()) {

            try(Connection connection = dataSource.getConnection()) {

                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, player.getUniqueId().toString());
                ps.setString(2, player.getName());
                ps.setString(3, delivery.getId());
                ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
                ps.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public boolean canClaim(UUID uuid, String deliveryId) {
        String sql = "SELECT * FROM player_deliveries WHERE uuid?";
        if(exists(uuid, deliveryId)) {

        }

        return false;
    }

    public boolean exists(UUID uuid, String deliveryId) {
        String sql = "SELECT * FROM player_deliveries WHERE uuid=? AND delivery_id=?";

        try(Connection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, uuid.toString());
            ps.setString(2, deliveryId);

            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void setClaimed(Player player, String deliveryId) {
        String sql;
        try (Connection connection = dataSource.getConnection()) {
            if(!exists(player.getUniqueId(), deliveryId)) {
                sql = "INSERT INTO player_deliveries (" +
                        "uuid, name, delivery_id, last_claim)" +
                        "VALUES (?, ?, ?, ?)";

                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setString(1, player.getUniqueId().toString());
                ps.setString(2, player.getName());
                ps.setString(3, deliveryId);
                ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
                ps.executeUpdate();
            } else {
                sql = "UPDATE player_deliveries SET " +
                        "";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }





}
