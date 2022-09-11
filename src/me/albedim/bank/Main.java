package me.albedim.bank;

import me.albedim.bank.classes.Config;
import me.albedim.bank.classes.UserData;
import me.albedim.bank.executor.Commands;
import me.albedim.bank.listener.InteractEvent;
import me.albedim.bank.listener.InventoryEvent;
import me.albedim.bank.listener.JoinEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 *  Created by @albedim (Github: github.com/albedim) on 29/08/22
 *  Last Update 01/09/22
 */

public class Main extends JavaPlugin 
{

    public static Main plugin;
    public static Config config;
    public static UserData userdata;
    public static ArrayList<String> inTransfer = new ArrayList<String>();

    public void onEnable() 
    {

        plugin = this;
        this.config = new Config();
        getCommand("card").setExecutor(new Commands());
        getCommand("transfer").setExecutor(new Commands());
        getCommand("bank").setExecutor(new Commands());
        getCommand("confirmTransfer").setExecutor(new Commands());
        getCommand("rejectTransfer").setExecutor(new Commands());
        Bukkit.getPluginManager().registerEvents(new InventoryEvent(), this);
        Bukkit.getPluginManager().registerEvents(new InteractEvent(), this);
        Bukkit.getPluginManager().registerEvents(new JoinEvent(), this);
        saveDefaultConfig();

        try {
            config.connect();
            this.userdata = new UserData();
            System.out.println("[ALBEphone] Plugin succefully activated and connected to the database '" + getConfig().getString("settings.database-name") + "'.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    public void onDisable() 
    {
        config.disconnect();
    }

    public static Main getInstance() 
    {
        return plugin;
    }

}
