package me.agronaut.essentials.events;

import me.agronaut.essentials.Essentials;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlaceBlock implements Listener {
    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event)
    {
        Player player = event.getPlayer();
        if (!player.hasPermission("essentials.place.block"))
        {
            player.sendMessage(Essentials.permissionMsg);
            event.setCancelled(true);
        }
    }
}
