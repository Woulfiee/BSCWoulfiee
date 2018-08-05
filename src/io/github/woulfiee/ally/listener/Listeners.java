package io.github.woulfiee.ally.listener;

import io.github.woulfiee.ally.Ally;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Copyright (c) 2018 by Woulfiee. Created on 8/5/2018.
 * You are not permitted to use this code without my permission.
 * Contact: woulfieejd@outlook.com
 */

public class Listeners implements Listener {

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            if (areAllies((Player) event.getEntity(), (Player) event.getDamager())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInvInteract(InventoryClickEvent event) {
        if (event.getInventory().getName().equals("Twoje sojusze")) {
            event.setResult(Event.Result.DENY);
            if (event.getCurrentItem() != null && event.getCurrentItem().getType() != Material.AIR) {
                if (Ally.getInstance().getConfig().getStringList(event.getWhoClicked().getName()).contains(event.getCurrentItem().getItemMeta().getDisplayName())) {
                    if (Bukkit.getPlayer(event.getCurrentItem().getItemMeta().getDisplayName()) != null) {
                        Player player = Bukkit.getPlayer(event.getCurrentItem().getItemMeta().getDisplayName());
                        Location location = player.getLocation();
                        event.getWhoClicked().closeInventory();
                        event.getWhoClicked().teleport(location);
                        event.getWhoClicked().sendMessage("§6[Sojusz] §aPrzeteleportowano do gracza §e" + player.getName() + "§a.");
                        player.sendMessage("§6[Sojusz] §aGracz §e" + player.getName() + " §aprzeteleportowal sie do Ciebie");
                    } else {
                        event.getWhoClicked().sendMessage("§6[Sojusz] §aTego gracza nie ma na serwerze.");
                    }
                }
            }
        }
    }


    private boolean areAllies(Player player1, Player player2) {
        return (Ally.getInstance().getConfig().getStringList(player1.getName()).contains(player2.getName())
                && Ally.getInstance().getConfig().getStringList(player2.getName()).contains(player1.getName()));
    }

}
