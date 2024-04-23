package com.jack.hello;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class home implements CommandExecutor {

    private final Hello plugin;

    public home(Hello plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            UUID playerUUID = player.getUniqueId();
            String homename = null;
            if (strings.length > 0) {
                homename = strings[0];
            }

            // Connect to SQLite database
            try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + plugin.getDataFolder() + "/homes.db")) {
                // Prepare SQL query
                String sql = "SELECT x, y, z FROM homes WHERE uuid = ? AND homeName = ?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, playerUUID.toString());
                    pstmt.setString(2, homename);

                    // Execute query and get the result
                    try (ResultSet rs = pstmt.executeQuery()) {
                        // If a home is found
                        if (rs.next()) {
                            // Parse the coordinates
                            double x = rs.getDouble("x");
                            double y = rs.getDouble("y");
                            double z = rs.getDouble("z");

                            // Get the world of the player
                            World world = player.getWorld();

                            // Create a new location and teleport the player
                            Location loc = new Location(world, x, y, z);
                            player.teleport(loc);
                            return true;
                        }
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}