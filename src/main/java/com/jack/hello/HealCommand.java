package com.jack.hello;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (sender instanceof Player){
            Player player = (Player) sender;
            player.sendMessage("healllll!!!!!!!!!!!");
            player.setHealth(20);
            player.teleport(player);
        }
        return false;
    }
}
