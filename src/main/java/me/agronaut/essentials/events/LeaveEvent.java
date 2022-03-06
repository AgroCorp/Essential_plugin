package me.agronaut.essentials.events;

import me.agronaut.essentials.Essentials;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class LeaveEvent implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        Essentials.playersPerms.remove(player.getUniqueId());
        Essentials.hiddenPlayers.remove(player.getUniqueId());
    }
}
