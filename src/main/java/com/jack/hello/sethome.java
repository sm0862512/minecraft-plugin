package com.jack.hello;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.ArrayList;

public class sethome implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {

        ArrayList<String> homes = new ArrayList<String>();
        if (sender instanceof Player) {
            String homename = null;
            if (strings.length > 0) {
                homename = strings[0];
                // You can continue this for as many arguments as you expect
            }
            Player player = (Player) sender;
            String home = String.valueOf(player.getLocation());
            String x = String.valueOf(player.getLocation().getX());
            String y = String.valueOf(player.getLocation().getY());
            String z = String.valueOf(player.getLocation().getZ());
            player.sendMessage(" X: " + x + " Y: " + y + " Z: " + z);
            Location loc = player.getLocation();
            String createhome = player + homename + x + y + z;
            homes.add(createhome);


        }
        return false;
    }
}