package me.agronaut.essentials.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class opingPlayer implements Listener {

    @EventHandler
    public void onOpCommand(PlayerCommandPreprocessEvent event)
    {
        if (event.getMessage().startsWith("/op"))
        {
            String targetName = event.getMessage().split(" ")[1];
            Player target = Bukkit.getPlayerExact(targetName);
            target.setPlayerListName(ChatColor.RED + "[Admin] " + ChatColor.RESET + target.getDisplayName());
        } else if (event.getMessage().startsWith("/deop"))
        {
            String targetName = event.getMessage().split(" ")[1];
            Player target = Bukkit.getPlayerExact(targetName);
            target.setPlayerListName(ChatColor.AQUA + "[Tag] " + ChatColor.RESET + target.getDisplayName());
        }
    }
}
