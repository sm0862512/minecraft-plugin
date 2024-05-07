package com.jack.hello;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.UUID;

public class listhomes implements CommandExecutor {

    private final Hello plugin;
    private ArrayList<String> homes = new ArrayList<>();

    public listhomes(Hello plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            UUID playerUUID = player.getUniqueId();

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

            // Check if a home with the same name exists for the player
            for (String home : homes) {
                String[] parts = home.split(",");
                if (parts.length >= 5 && parts[0].equals(playerUUID.toString())) {
                    // Display the home
                    player.sendMessage("Home: " + parts[1]);
                }
            }

            return true;
        }
        return false;
    }
}