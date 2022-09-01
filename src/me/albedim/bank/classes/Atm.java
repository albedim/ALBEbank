package me.albedim.bank.classes;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;

/*
 *  Created by @albedim (Github: github.com/albedim) on 30/08/22
 *  Last Update -
 */

public class Atm {

    private Player player;

    public Atm(Player player) {
        this.player = player;
    }

    public ItemStack createButton(Material id, short data, int amount, List<String> lore, String display) {

        @SuppressWarnings("deprecation")
        ItemStack item = new ItemStack(id, amount, data);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(display);
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;

    }

    public void openHomePage() {

        Inventory inv = Bukkit.createInventory(null, 45, "§8ATM");
        inv.setItem(21, createButton(Material.GHAST_TEAR, (short) 1, 1, null, "§7Preleva"));
        inv.setItem(23, createButton(Material.GOLD_NUGGET, (short) 1, 1, null, "§eRichiedi estratto conto"));

        this.player.openInventory(inv);

    }

    public void openMoneyPage() {

        Inventory inv = Bukkit.createInventory(null,45, "§8ATM Preleva");
        inv.setItem(19, createButton(Material.GRAY_DYE, (short) 1, 1, null, "§7€5"));
        inv.setItem(20, createButton(Material.RED_DYE, (short) 1, 1, null, "§4€10"));
        inv.setItem(21, createButton(Material.BLUE_DYE, (short) 1, 1, null, "§9€20"));
        inv.setItem(22, createButton(Material.ORANGE_DYE, (short) 1, 1, null, "§6€50"));
        inv.setItem(23, createButton(Material.GREEN_DYE, (short) 1, 1, null, "§a€100"));
        inv.setItem(24, createButton(Material.YELLOW_DYE, (short) 1, 1, null, "§e€200"));
        inv.setItem(25, createButton(Material.PINK_DYE, (short) 1, 1, null, "§d€500"));

        this.player.openInventory(inv);

    }

}
