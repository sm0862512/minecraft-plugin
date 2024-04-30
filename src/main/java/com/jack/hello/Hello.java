package com.jack.hello;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public final class Hello extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("enabled");
        Bukkit.getPluginManager().registerEvents(this,this);
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("sethome").setExecutor(new sethome(this)); // Pass 'this' to sethome
        getCommand("delhome").setExecutor(new delhome());
        getCommand("home").setExecutor(new home(this)); // Pass 'this' to home

        // Connect to SQLite database
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + getDataFolder() + "/homes.db")) {
            // Create table if not exists
            String sql = "CREATE TABLE IF NOT EXISTS homes ("
                    + "uuid text NOT NULL,"
                    + "homeName text NOT NULL,"
                    + "x real NOT NULL,"
                    + "y real NOT NULL,"
                    + "z real NOT NULL,"
                    + "PRIMARY KEY (uuid, homeName)"
                    + ");";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}