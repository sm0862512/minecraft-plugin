package com.jack.hello;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class sethome implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            String home = String.valueOf(player.getLocation());
            String x = String.valueOf(player.getLocation().getX());
            String y = String.valueOf(player.getLocation().getY());
            String z = String.valueOf(player.getLocation().getZ());
            player.sendMessage(" X "+x+" Y "+y+" Z "+z);
            Location loc = player.getLocation();
            try {
                File dataFolder = this.getDataFolder();
                if (!dataFolder.exists()) {
                    dataFolder.mkdir();
                }
                File locationFile = new File(dataFolder, player.getName() + ".txt");
                FileWriter writer = new FileWriter(locationFile);
                writer.write(" World: " + loc.getWorld().getName() + "\n");
                writer.write(" X: " + loc.getX() + "\n");
                writer.write(" Y: " + loc.getY() + "\n");
                writer.write(" Z: " + loc.getZ() + "\n");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }



        }
        return false;
    }
}