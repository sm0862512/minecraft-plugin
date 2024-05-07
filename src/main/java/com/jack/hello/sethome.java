package com.jack.hello;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import java.util.UUID;

public class sethome implements CommandExecutor {

    private final Hello plugin;

    public sethome(Hello plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            UUID playerUUID = player.getUniqueId();
            String homeName = "default"; // Default home name
            if (strings.length > 0) {
                homeName = strings[0]; // If a home name is provided, use it
            }

            String x = String.valueOf(player.getLocation().getX());
            String y = String.valueOf(player.getLocation().getY());
            String z = String.valueOf(player.getLocation().getZ());
            player.sendMessage(" X: " + x + " Y: " + y + " Z: " + z);

            // Connect to SQLite database
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + plugin.getDataFolder() + "/homes.db")) {
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

                // Insert or update home
                sql = "INSERT OR REPLACE INTO homes (uuid, homeName, x, y, z) VALUES(?, ?, ?, ?, ?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, playerUUID.toString());
                    pstmt.setString(2, homeName);
                    pstmt.setDouble(3, Double.parseDouble(x));
                    pstmt.setDouble(4, Double.parseDouble(y));
                    pstmt.setDouble(5, Double.parseDouble(z));
                    pstmt.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return true;
        }
        return false;
    }
}