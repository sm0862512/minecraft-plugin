package com.jack.hello;

import com.google.gson.Gson;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class sethome implements CommandExecutor {

    private final Hello plugin;

    public sethome(Hello plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {


        ArrayList<String> homes = new ArrayList<String>();
        String homename = null;
        if (sender instanceof Player) {
            homename = null;
            if (strings.length > 0) {
                homename = strings[0];
            }
            // rest of your code
        }
        Player player = (Player) sender;
        UUID playerUUID = player.getUniqueId();
        String home = String.valueOf(player.getLocation());
        String x = String.valueOf(player.getLocation().getX());
        String y = String.valueOf(player.getLocation().getY());
        String z = String.valueOf(player.getLocation().getZ());
        player.sendMessage(" X: " + x + " Y: " + y + " Z: " + z);
        Location loc = player.getLocation();
        String createhome = playerUUID + "," + homename + "," +  x + "," + y + "," + z;
        homes.add(createhome);

        // Convert the homes list to JSON
        Gson gson = new Gson();
        String json = gson.toJson(homes);

        // Write the JSON to a file
        try (FileWriter file = new FileWriter(plugin.getDataFolder() + "/data.json")) { // Use plugin.getDataFolder()
            file.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }
}
