package me.albedim.bank.listener;

import me.albedim.bank.Main;
import me.albedim.bank.classes.Atm;
import me.albedim.bank.classes.Items;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import static me.albedim.bank.Main.userdata;

/*
 *  Created by @albedim (Github: github.com/albedim) on 30/08/22
 *  Last Update 01/09/22
 */

public class InteractEvent implements Listener {

    private Items items = new Items();

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {

        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (e.getClickedBlock().getType().equals(Material.WARPED_PLANKS)) {
                if (e.getHand() == EquipmentSlot.OFF_HAND)
                    return;
                if (e.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.AIR))
                    return;
                if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equals("§aCarta di credito")) {
                    Atm atm = new Atm(e.getPlayer());
                    atm.openHomePage();
                } else {
                    switch (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName()) {
                        case "§7€5":
                            userdata.addMoney(userdata.getIban(e.getPlayer().getName()), 5);
                            e.getPlayer().sendMessage(Main.getInstance().getConfig().getString("messages.deposited-money").replace("%money%", "€5"));
                            items.subtractMoney(e.getPlayer());
                            break;
                        case "§4€10":
                            userdata.addMoney(userdata.getIban(e.getPlayer().getName()), 10);
                            e.getPlayer().sendMessage(Main.getInstance().getConfig().getString("messages.deposited-money").replace("%money%", "€10"));
                            items.subtractMoney(e.getPlayer());
                            break;
                        case "§9€20":
                            userdata.addMoney(userdata.getIban(e.getPlayer().getName()), 20);
                            e.getPlayer().sendMessage(Main.getInstance().getConfig().getString("messages.deposited-money").replace("%money%", "€20"));
                            items.subtractMoney(e.getPlayer());
                            break;
                        case "§6€50":
                            userdata.addMoney(userdata.getIban(e.getPlayer().getName()), 50);
                            e.getPlayer().sendMessage(Main.getInstance().getConfig().getString("messages.deposited-money").replace("%money%", "€50"));
                            items.subtractMoney(e.getPlayer());
                            break;
                        case "§a€100":
                            userdata.addMoney(userdata.getIban(e.getPlayer().getName()), 100);
                            e.getPlayer().sendMessage(Main.getInstance().getConfig().getString("messages.deposited-money").replace("%money%", "€100"));
                            items.subtractMoney(e.getPlayer());
                            break;
                        case "§e€200":
                            userdata.addMoney(userdata.getIban(e.getPlayer().getName()), 200);
                            e.getPlayer().sendMessage(Main.getInstance().getConfig().getString("messages.deposited-money").replace("%money%", "€200"));
                            items.subtractMoney(e.getPlayer());
                            break;
                        case "§d€500":
                            userdata.addMoney(userdata.getIban(e.getPlayer().getName()), 500);
                            e.getPlayer().sendMessage(Main.getInstance().getConfig().getString("messages.deposited-money").replace("%money%", "€500"));
                            items.subtractMoney(e.getPlayer());
                            break;
                        default:
                            return;
                    }
                }
            }
        }
    }

}
