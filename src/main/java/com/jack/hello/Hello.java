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
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("sethome").setExecutor(new sethome(this)); // Pass 'this' to sethome
        getCommand("delhome").setExecutor(new delhome());

        // Create a new folder named 'homeplugin' in the plugin's data folder
        File homePluginFolder = new File(getDataFolder(), "homeplugin");
        homePluginFolder.mkdirs();

        // Create a new data file in the 'homeplugin' folder
        File dataFile = new File(homePluginFolder, "data.json");
        try {
            if (!dataFile.exists()) {
                dataFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}