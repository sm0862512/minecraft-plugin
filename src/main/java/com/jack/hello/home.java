package com.jack.hello;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
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

            // Read the JSON data
            try (FileReader reader = new FileReader(plugin.getDataFolder() + "/data.json")) {
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<String>>() {}.getType();
                ArrayList<String> homes = gson.fromJson(reader, type);

                // Iterate over the homes
                for (String home : homes) {
                    String[] parts = home.split(",");
                    if (parts.length >= 5 && parts[0].equals(playerUUID.toString()) && parts[1].equals(homename)) {
                        // Parse the coordinates
                        double x = Double.parseDouble(parts[2]);
                        double y = Double.parseDouble(parts[3]);
                        double z = Double.parseDouble(parts[4]);

                        // Get the world of the player
                        World world = player.getWorld();

                        // Create a new location and teleport the player
                        Location loc = new Location(world, x, y, z);
                        player.teleport(loc);
                        return true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}