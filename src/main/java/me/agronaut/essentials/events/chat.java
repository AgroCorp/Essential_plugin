package me.agronaut.essentials.events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class chat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        event.setFormat(ChatColor.GREEN + "[" + player.getWorld().getName() + "] " + (player.isOp() ? ChatColor.RED : ChatColor.AQUA) + player.getDisplayName() + ": " + ChatColor.WHITE + event.getMessage());
    }
}
