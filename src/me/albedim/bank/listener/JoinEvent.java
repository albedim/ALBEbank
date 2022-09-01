package me.albedim.bank.listener;

import me.albedim.bank.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static me.albedim.bank.Main.userdata;

/*
 *  Created by @albedim (Github: github.com/albedim) on 29/08/22
 *  Last Update 01/09/22
 */

public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        if (!userdata.hasAccount(e.getPlayer().getName()))
            userdata.addAccount(
                    e.getPlayer().getName(),
                    Double.parseDouble(Main.getInstance().getConfig().getString("configuration.default-balance"))
            );
    }

}
