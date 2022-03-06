package me.agronaut.essentials.events;

import me.agronaut.essentials.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class damage implements Listener {
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event)
    {
        if (event.getDamager() instanceof Player)
        {
            Player player = (Player) event.getDamager();
            if (player.hasPermission("essentials.damage"))
            {
                if (event.getEntity() instanceof LivingEntity)
                {
                    Essentials.actionBar(player);
                }
            }
            else
            {
                player.sendMessage(Essentials.permissionMsg);
                event.setCancelled(true);
            }
        }
    }
}
