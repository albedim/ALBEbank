package me.albedim.bank.listener;

import me.albedim.bank.Main;
import me.albedim.bank.classes.Atm;
import me.albedim.bank.classes.Items;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import static me.albedim.bank.Main.userdata;

/*
 *  Created by @albedim (Github: github.com/albedim) on 30/08/22
 *  Last Update 01/09/22
 */

public class InventoryEvent implements Listener 
{

    private Items items = new Items();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) 
    {

        Player player = (Player) e.getWhoClicked();

        if (e.getView().getTitle().equals("§8ATM") || e.getView().getTitle().equals("§8ATM Preleva"))
            e.setCancelled(true);

        if (e.getCurrentItem() == null)
            return;

        if (e.getView().getTitle().equals("§8ATM"))
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§7Preleva")) {
                Atm atm = new Atm(player);
                atm.openMoneyPage();
            }

        if (e.getView().getTitle().equals("§8ATM"))
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals("§eRichiedi estratto conto"))
                player.getInventory().addItem(items.createBankStmt(this.getCardIban(player)));

        if (e.getView().getTitle().equals("§8ATM Preleva")) {

            switch (e.getCurrentItem().getItemMeta().getDisplayName()) {
                case "§7€5":
                    if (userdata.hasMoney(this.getCardIban(player), 5)) {
                        userdata.takeMoney(this.getCardIban(player), 5);
                        items.addMoney(player, items.createMoney("5EUR"));
                        player.sendMessage(Main.getInstance().getConfig().getString("messages.withdrawn-money").replace("%money%", "€5"));
                    } else {
                        player.sendMessage(Main.getInstance().getConfig().getString("messages.no-enough-money"));
                    }
                    break;
                case "§4€10":
                    if (userdata.hasMoney(this.getCardIban(player), 10)) {
                        userdata.takeMoney(this.getCardIban(player), 10);
                        items.addMoney(player, items.createMoney("10EUR"));
                        player.sendMessage(Main.getInstance().getConfig().getString("messages.withdrawn-money").replace("%money%", "€10"));
                    } else {
                        player.sendMessage(Main.getInstance().getConfig().getString("messages.no-enough-money"));
                    }
                    break;
                case "§9€20":
                    if (userdata.hasMoney(this.getCardIban(player), 20)) {
                        userdata.takeMoney(this.getCardIban(player), 20);
                        items.addMoney(player, items.createMoney("20EUR"));
                        player.sendMessage(Main.getInstance().getConfig().getString("messages.withdrawn-money").replace("%money%", "€20"));
                    } else {
                        player.sendMessage(Main.getInstance().getConfig().getString("messages.no-enough-money"));
                    }
                    break;
                case "§6€50":
                    if (userdata.hasMoney(this.getCardIban(player), 50)) {
                        userdata.takeMoney(this.getCardIban(player), 50);
                        items.addMoney(player, items.createMoney("50EUR"));
                        player.sendMessage(Main.getInstance().getConfig().getString("messages.withdrawn-money").replace("%money%", "€50"));
                    } else {
                        player.sendMessage(Main.getInstance().getConfig().getString("messages.no-enough-money"));
                    }
                    break;
                case "§a€100":
                    if (userdata.hasMoney(this.getCardIban(player), 100)) {
                        userdata.takeMoney(this.getCardIban(player), 100);
                        items.addMoney(player, items.createMoney("100EUR"));
                        player.sendMessage(Main.getInstance().getConfig().getString("messages.withdrawn-money").replace("%money%", "€100"));
                    } else {
                        player.sendMessage(Main.getInstance().getConfig().getString("messages.no-enough-money"));
                    }
                    break;
                case "§e€200":
                    if (userdata.hasMoney(this.getCardIban(player), 200)) {
                        userdata.takeMoney(this.getCardIban(player), 200);
                        items.addMoney(player, items.createMoney("200EUR"));
                        player.sendMessage(Main.getInstance().getConfig().getString("messages.withdrawn-money").replace("%money%", "€200"));
                    } else {
                        player.sendMessage(Main.getInstance().getConfig().getString("messages.no-enough-money"));
                    }
                    break;
                case "§d€500":
                    if (userdata.hasMoney(this.getCardIban(player), 500)) {
                        userdata.takeMoney(this.getCardIban(player), 500);
                        items.addMoney(player, items.createMoney("500EUR"));
                        player.sendMessage(Main.getInstance().getConfig().getString("messages.withdrawn-money").replace("%money%", "€500"));
                    } else {
                        player.sendMessage(Main.getInstance().getConfig().getString("messages.no-enough-money"));
                    }
                    break;
            }
        }

    }

    public String getCardIban(Player player) 
    {
        return player.getInventory().getItemInMainHand().getItemMeta().getLore().get(0).substring(6);
    }

}
