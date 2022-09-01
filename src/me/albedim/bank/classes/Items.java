package me.albedim.bank.classes;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static me.albedim.bank.Main.userdata;

/*
 *  Created by @albedim (Github: github.com/albedim) on 01/09/22
 *  Last Update -
 */

public class Items {

    public ItemStack createMoney(String money) {

        Material id = Material.AIR;
        String display = "";

        switch (money) {
            case "5EUR":
                id = Material.GRAY_DYE;
                display = "§7€5";
                break;
            case "10EUR":
                id = Material.RED_DYE;
                display = "§4€10";
                break;
            case "20EUR":
                id = Material.BLUE_DYE;
                display = "§9€20";
                break;
            case "50EUR":
                id = Material.ORANGE_DYE;
                display = "§6€50";
                break;
            case "100EUR":
                id = Material.GREEN_DYE;
                display = "§a€100";
                break;
            case "200EUR":
                id = Material.YELLOW_DYE;
                display = "§e€200";
                break;
            case "500EUR":
                id = Material.PINK_DYE;
                display = "§d€500";
                break;
        }

        @SuppressWarnings("deprecation")
        ItemStack item = new ItemStack(id, 1, (short) 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(display);
        item.setItemMeta(meta);

        return item;

    }

    public ItemStack createBankStmt(String iban) {

        @SuppressWarnings("deprecation")
        ItemStack item = new ItemStack(Material.PAPER, 1, (short) 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§aEstratto conto");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("§2• §7Conto: §2" + iban);
        lore.add("§2• §7Bilancio: §2€" + userdata.getBalance(iban));
        Date date = new Date();
        SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy");
        lore.add("§2• §7Data: §2§o" + date_format.format(date));
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;

    }

    public void subtractMoney(Player player) {
        player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
    }

    public void addMoney(Player player, ItemStack item) {
        player.getInventory().addItem(item);
    }

}
