package me.agronaut.essentials.events;

import me.agronaut.essentials.Essentials;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class damage implements Listener {
    Essentials plugin;

    public damage(Essentials plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event)
    {
        if (event.getDamager() instanceof Player)
        {
            Player player = (Player) event.getDamager();
            if (event.getEntity() instanceof LivingEntity)
            {
                plugin.actionBar(player);
            }



        }
    }
}
