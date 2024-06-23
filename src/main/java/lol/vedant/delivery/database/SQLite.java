/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.database;

import lol.vedant.delivery.Delivery;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLite implements Database {

    private Delivery plugin;
    private Connection connection;
    private String url;

    public SQLite(Delivery plugin) {
        this.plugin = plugin;

        File database = new File(plugin.getDataFolder().getPath(), "database.db");

        if(!database.exists()) {
            try {
                database.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        url = "jdbc:sqlite:" + database;

        try {
            Class.forName("org.sqlite.JDBC");
            DriverManager.getConnection(url);
        } catch (SQLException | ClassNotFoundException e) {
            if (e instanceof ClassNotFoundException) {
                plugin.getLogger().severe("SQLite driver not found on your system!");
            }
            e.printStackTrace();
        }

        createTables();

    }

    @Override
    public void init() {
        //Nothing
    }

    @Override
    public void createTables() {
        String sql = "CREATE TABLE IF NOT EXISTS player_deliveries (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "uuid VARCHAR(36) UNIQUE NOT NULL," +
                "name VARCHAR(32)," +
                "delivery_id VARCHAR(255)," +
                "last_claim TIMESTAMP DEFAULT NULL)";

        String triggerSql = "CREATE TRIGGER IF NOT EXISTS update_last_claim " +
                "AFTER UPDATE ON player_deliveries " +
                "FOR EACH ROW " +
                "BEGIN " +
                "UPDATE player_deliveries SET last_claim = CURRENT_TIMESTAMP WHERE id = OLD.id; " +
                "END;";

        try {
            checkConnection();

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.executeUpdate();

            //Create a SQLite trigger to update the timestamp
            ps = connection.prepareStatement(triggerSql);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void checkConnection() throws SQLException {
        boolean renew = false;

        if (this.connection == null)
            renew = true;
        else
        if (this.connection.isClosed())
            renew = true;

        if (renew)
            this.connection = DriverManager.getConnection(url);
    }

}
