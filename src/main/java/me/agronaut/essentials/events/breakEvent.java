package me.agronaut.essentials.events;

import me.agronaut.essentials.Essentials;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class breakEvent implements Listener {

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event)
    {
        Player player = event.getPlayer();
        if (!player.hasPermission("essentials.break.block"))
        {
            player.sendMessage(Essentials.permissionMsg);
            event.setCancelled(true);
        }
    }
}
