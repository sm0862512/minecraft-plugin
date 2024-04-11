package com.jack.hello;

import org.bukkit.Bukkit;
import org.checkerframework.framework.qual.RelevantJavaTypes;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.*;

public final class Hello extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("enabled");
        Bukkit.getPluginManager().registerEvents(this,this);
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("sethome").setExecutor(new sethome());
        getCommand("delhome").setExecutor(new delhome());


    }




}
