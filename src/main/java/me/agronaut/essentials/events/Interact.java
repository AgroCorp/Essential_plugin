package me.agronaut.essentials.events;

import me.agronaut.essentials.Essentials;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class Interact implements Listener {
    Essentials plugin;

    public Interact(Essentials plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event)
    {
        Player player = event.getPlayer();
        if (event.getClickedBlock().getType() == Material.CHEST)
        {
            if (plugin.playersGroups.containsKey(player.getUniqueId()))
            {
                if (!plugin.playersGroups.get(player.getUniqueId()).contains("user"))
                {
                    event.setCancelled(true);
                }
            }
        }
    }
}
