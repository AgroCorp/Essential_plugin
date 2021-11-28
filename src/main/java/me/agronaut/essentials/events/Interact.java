package me.agronaut.essentials.events;

import me.agronaut.essentials.Essentials;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class Interact implements Listener {
    Essentials plugin;

    public Interact(Essentials plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event)
    {
        plugin.getLogger().info("onInteract start");
        Player player = event.getPlayer();
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getClickedBlock().getType() == Material.CHEST || event.getClickedBlock().getType().equals(Material.CHEST_MINECART))
        {
            plugin.getLogger().info("belepett az elso if be");
            if (Essentials.playersGroups.containsKey(player.getUniqueId()))
            {
                if (!Essentials.playersGroups.get(player.getUniqueId()).contains("user"))
                {
                    player.sendMessage(Essentials.permissionMsg);
                    event.setCancelled(true);
                }
            }
            else{
                player.sendMessage(Essentials.permissionMsg);
                event.setCancelled(true);
            }
        }
    }
}
