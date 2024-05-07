package com.jack.hello;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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

public class delhome implements CommandExecutor {

    private final Hello plugin;// Create a new instance of the Hello class
    private ArrayList<String> homes = new ArrayList<>();

    public delhome(Hello plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {// CommandSender is the interface for senders of commands
        if (sender instanceof Player) {
            Player player = (Player) sender;
            UUID playerUUID = player.getUniqueId();
            String homeName = "default"; // Default home name
            if (strings.length > 0) {
                homeName = strings[0]; // If a home name is provided, use it
            }

            // Load existing homes from JSON file
            try (FileReader reader = new FileReader(plugin.getDataFolder() + "/data.json")) {// Read the JSON file
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<String>>() {}.getType();// Create a new TypeToken
                homes = gson.fromJson(reader, type);
                if (homes == null) {
                    homes = new ArrayList<>();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // Check if a home with the same name exists for the player
            for (int i = 0; i < homes.size(); i++) {
                String[] parts = homes.get(i).split(",");
                if (parts.length >= 5 && parts[0].equals(playerUUID.toString()) && parts[1].equals(homeName)) {
                    // Remove the home
                    homes.remove(i);
                    break;
                }
            }

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