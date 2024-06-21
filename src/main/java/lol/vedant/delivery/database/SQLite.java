/*
 * Copyright (c) 2024 Vedant Mulay. All rights reserved.
 */

package lol.vedant.delivery.database;

import lol.vedant.delivery.Delivery;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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



    }

    @Override
    public void init() {

    }

    @Override
    public void createTables() {

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
