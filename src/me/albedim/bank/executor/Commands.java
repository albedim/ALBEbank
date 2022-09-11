package me.albedim.bank.executor;

import me.albedim.bank.Main;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;

import static me.albedim.bank.Main.inTransfer;
import static me.albedim.bank.Main.userdata;

/*
 *  Created by @albedim (Github: github.com/albedim) on 29/08/22
 *  Last Update 01/09/22
 */

public class Commands implements CommandExecutor 
{

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) 
    {

        if (cmd.getName().equals("card")) {
            if (!sender.hasPermission(Main.getInstance().getConfig().getString("configuration.permissions-name"))) {
                sender.sendMessage(Main.getInstance().getConfig().getString("messages.no-permissions"));
                return false;
            }
            if (args.length == 1) {
                if (userdata.hasAccount(args[0])) {
                    Player player = Bukkit.getPlayerExact(sender.getName());
                    player.getInventory().addItem(this.createCard(args[0]));
                }
            } else {
                sender.sendMessage(Main.getInstance().getConfig().getString("messages.wrong-command-use"));
            }
            return true;
        }

        if (cmd.getName().equals("transfer")) {
            if (args.length == 2) {
                if (userdata.ibanExists(args[0])) {
                    if (args[0].equals(userdata.getIban(sender.getName()))) {
                        sender.sendMessage(Main.getInstance().getConfig().getString("messages.auto-transfer"));
                        return false;
                    }
                    inTransfer.add(sender.getName());

                    sender.sendMessage("§7 - - - - §aBonifico Bancario §7- - - -");
                    sender.sendMessage("");
                    sender.sendMessage("§a• §7Ammonto: §a€" + args[1]);
                    sender.sendMessage("§a• §7Destinatario: §a" + args[0]);
                    sender.sendMessage("");

                    TextComponent confirmButton = new TextComponent("§a[Conferma]");
                    TextComponent space = new TextComponent("     ");
                    TextComponent rejectButton = new TextComponent("§c[Rifiuta]");
                    confirmButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/confirmTransfer " + args[0] + " " + args[1]));
                    rejectButton.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/rejectTransfer"));

                    sender.spigot().sendMessage(space, confirmButton, space, rejectButton);
                    sender.sendMessage("");
                } else {
                    sender.sendMessage(Main.getInstance().getConfig().getString("messages.iban-not-exists"));
                }
            } else {
                sender.sendMessage(Main.getInstance().getConfig().getString("messages.wrong-command-use"));
            }
        }

        if (cmd.getName().equals("confirmTransfer")) {
            if (args.length == 2) {
                if (!inTransfer.contains(sender.getName()))
                    return false;
                if (userdata.hasMoney(userdata.getIban(sender.getName()), Integer.parseInt(args[1]))) {
                    userdata.takeMoney(userdata.getIban(sender.getName()), Integer.parseInt(args[1]));
                    userdata.addMoney(args[0], Integer.parseInt(args[1]));
                    inTransfer.remove(sender.getName());
                    sender.sendMessage(Main.getInstance().getConfig().getString("messages.transfer-completed"));

                    Player target = Bukkit.getPlayerExact(userdata.getUserByIban(args[0]));
                    target.sendMessage("§7 - - - - §aBonifico Bancario §7- - - -");
                    target.sendMessage("");
                    target.sendMessage("§a• §7Ammonto: §a€" + args[1]);
                    target.sendMessage("§a• §7Emittente: §a" + userdata.getIban(sender.getName()) + " §7[§a" + sender.getName() + "§7]");
                    target.sendMessage("§a• §7Stato: §a§o ✓ Completato");
                    target.sendMessage("");

                } else {
                    sender.sendMessage(Main.getInstance().getConfig().getString("messages.no-enough-money"));
                }
            } else {
                sender.sendMessage(Main.getInstance().getConfig().getString("messages.wrong-command-use"));
            }
        }

        if (cmd.getName().equals("rejectTransfer")) {
            if (!inTransfer.contains(sender.getName()))
                return false;
            sender.sendMessage(Main.getInstance().getConfig().getString("messages.transfer-deleted"));
            inTransfer.remove(sender.getName());
        }

        if (cmd.getName().equals("bank")) {
            if (sender.hasPermission(Main.getInstance().getConfig().getString("configuration.permissions-name"))) {
                if (args.length > 0) {
                    if (args[0].equals("add")) {
                        if (args.length == 3) {
                            userdata.addMoney(userdata.getIban(args[1]), Integer.parseInt(args[2]));
                            sender.sendMessage(Main.getInstance().getConfig().getString("messages.money-added"));
                        } else {
                            sender.sendMessage(Main.getInstance().getConfig().getString("messages.wrong-command-use"));
                        }
                    } else if (args[0].equals("take")) {
                        if (args.length == 3) {
                            if (userdata.hasMoney(userdata.getIban(args[1]), Integer.parseInt(args[2]))) {
                                userdata.takeMoney(userdata.getIban(args[1]), Integer.parseInt(args[2]));
                                sender.sendMessage(Main.getInstance().getConfig().getString("messages.money-taken"));
                            } else {
                                sender.sendMessage(Main.getInstance().getConfig().getString("messages.user-no-enough-money"));
                            }
                        } else {
                            sender.sendMessage(Main.getInstance().getConfig().getString("messages.wrong-command-use"));
                        }
                    } else {
                        sender.sendMessage(Main.getInstance().getConfig().getString("messages.wrong-command-use"));
                    }
                } else {
                    sender.sendMessage(Main.getInstance().getConfig().getString("messages.wrong-command-use"));
                }
            } else {
                sender.sendMessage(Main.getInstance().getConfig().getString("messages.no-permissions"));
            }
        }

        return false;
    }

    public ItemStack createCard(String player) 
    {

        ItemStack item = new ItemStack(Material.PAPER, 1, (short) 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§aCarta di credito");
        ArrayList<String> lore = new ArrayList<String>();
        lore.add("§2• §7" + userdata.getIban(player));
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }
}
