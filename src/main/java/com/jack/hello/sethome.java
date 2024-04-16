package com.jack.hello;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
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
    private ArrayList<String> homes = new ArrayList<>();

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
            String createhome = playerUUID + "," + homeName + "," + x + "," + y + "," + z;

            // Load existing homes from JSON file
            try (FileReader reader = new FileReader(plugin.getDataFolder() + "/data.json")) {
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<String>>() {}.getType();
                homes = gson.fromJson(reader, type);
                if (homes == null) {
                    homes = new ArrayList<>();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Check if a home with the same name already exists for the player
            for (int i = 0; i < homes.size(); i++) {
                String[] parts = homes.get(i).split(",");
                if (parts.length >= 5 && parts[0].equals(playerUUID.toString()) && parts[1].equals(homeName)) {
                    // Remove the old home
                    homes.remove(i);
                    break;
                }
            }

            // Add new home
            homes.add(createhome);

            // Convert the homes list to JSON
            Gson gson = new Gson();

            // Write the JSON to a file
            try (FileWriter file = new FileWriter(plugin.getDataFolder() + "/data.json")) { // Overwrite the file
                String data = gson.toJson(homes);
                file.write(data + "\n"); // No newline character
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        }
        return false;
    }
}