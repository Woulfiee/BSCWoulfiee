package io.github.woulfiee.ally.commands;

import io.github.woulfiee.ally.Ally;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 2018 by Woulfiee. Created on 8/5/2018.
 * You are not permitted to use this code without my permission.
 * Contact: woulfieejd@outlook.com
 */

public class AllyCommand implements CommandExecutor {

    private Map<Player, Player> requestAlly = new HashMap<>();
    private Inventory allyInventory;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("sojusz")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;

                if (args.length == 0) {
                    int i = 0;

                    if (Ally.getInstance().getConfig().getStringList(player.getName()) != null) {
                        i = Ally.getInstance().getConfig().getStringList(player.getName()).size();
                    }

                    if (i != 0) {
                        allyInventory = Bukkit.createInventory(null, 27, "Twoje sojusze");
                        if (Ally.getInstance().getConfig().getStringList(player.getName()) != null) {
                            for (String ally : Ally.getInstance().getConfig().getStringList(player.getName())) {
                                ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
                                SkullMeta meta = (SkullMeta) skull.getItemMeta();
                                meta.setOwningPlayer(Bukkit.getOfflinePlayer(ally));
                                meta.setDisplayName(ally);
                                skull.setItemMeta(meta);
                                allyInventory.addItem(skull);
                            }
                            player.openInventory(allyInventory);
                        }
                    }

                } else if (args.length == 1) {
                    if (Bukkit.getPlayer(args[0]) != null) {
                        Player targetPlayer = Bukkit.getPlayer(args[0]);
                        if (targetPlayer != requestAlly.get(player) && player != requestAlly.get(targetPlayer)) {
                            player.sendMessage("§6[Sojusz] §aWyslano prosbe o potwierdzenie.");
                            targetPlayer.sendMessage("§6[Sojusz] §aGracz §e" + player.getName() + " §achce zawrzec z Toba sojusz. Aby potwierdzic, wpisz §e/ally " + player.getName() + "§a.");

                            requestAlly.put(player, targetPlayer);
                        } else if (targetPlayer != requestAlly.get(player) && player == requestAlly.get(targetPlayer)) {
                            player.sendMessage("§6[Sojusz] §aZawarto sojusz z graczem §e" + targetPlayer.getName() + "§a.");
                            targetPlayer.sendMessage("§6[Sojusz] §aZawarto sojusz z graczem §e" + player.getName() + "§a.");

                            requestAlly.put(player, targetPlayer);

                            List<String> playerAlliesList = Ally.getInstance().getConfig().getStringList(player.getName());
                            List<String> targetAlliesList = Ally.getInstance().getConfig().getStringList(targetPlayer.getName());

                            playerAlliesList.add(targetPlayer.getName());
                            targetAlliesList.add(player.getName());

                            Ally.getInstance().getConfig().set(player.getName(), playerAlliesList);
                            Ally.getInstance().getConfig().set(targetPlayer.getName(), targetAlliesList);

                            Ally.getInstance().saveConfig();
                        } else {
                            player.sendMessage("§6[Sojusz] §cJestes juz w sojuszu z tym graczem.");
                        }
                    }
                }

            }
        }
        return false;
    }
}
