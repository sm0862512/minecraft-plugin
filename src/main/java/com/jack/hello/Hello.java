package com.jack.hello;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.io.IOException;

public final class Hello extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("enabled");
        Bukkit.getPluginManager().registerEvents(this,this);
        getCommand("heal").setExecutor(new HealCommand());// Pass 'this' to HealCommand
        getCommand("sethome").setExecutor(new sethome(this)); // Pass 'this' to sethome
        getCommand("delhome").setExecutor(new delhome(this));// Pass 'this' to delhome
        getCommand("home").setExecutor(new home(this)); // Pass 'this' to home
        getCommand("listhomes").setExecutor(new listhomes(this)); // Pass 'this' to listhomes

        // Create a new folder named 'homeplugin' in the plugin's data folder
        File homePluginFolder = new File(String.valueOf(getDataFolder()));
        homePluginFolder.mkdirs();

        // Create a new data file in the plugin's data folder
        File dataFile = new File(getDataFolder(), "data.json");
        try {
            if (!dataFile.exists()) {
                dataFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}