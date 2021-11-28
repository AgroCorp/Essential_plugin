package me.agronaut.essentials.events;

import me.agronaut.essentials.Essentials;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveEvent implements Listener {
    Essentials plugin;

    public LeaveEvent(Essentials plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        plugin.playersPerms.remove(player.getUniqueId());
    }
}
