package me.agronaut.essentials.events;

import me.agronaut.essentials.Essentials;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class join implements Listener {
    Essentials plugin;

    public join(Essentials plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Title when join to server
        String title = plugin.getConfig().getString("hello-msg");
        String subTitle = plugin.getConfig().getString("hello-submsg");

        if (title.contains("%player%")) {
            title = title.replaceAll("%player%", player.getDisplayName());
        }
        if (title.contains("%daytime%")) {
            title = title.replaceAll("%daytime%", convertTimeToString(player.getWorld().getTime()));
        }
        if (subTitle.contains("%player%")) {
            subTitle = subTitle.replaceAll("%player%", player.getDisplayName());
        }
        if (subTitle.contains("%daytime%")) {
            subTitle = subTitle.replaceAll("%daytime%", convertTimeToString(player.getWorld().getTime()));
        }

        player.sendTitle(ChatColor.translateAlternateColorCodes('&', title),ChatColor.translateAlternateColorCodes('&', subTitle),10,70,20);

        // check player visibile
        if (plugin.hiddenPlayers.contains(player.getUniqueId())) {
            plugin.hidePlayer(player);
        }

        // edit tab menu name
        if (player.isOp()) {
            player.setPlayerListName(ChatColor.RED + "[Admin] " + ChatColor.RESET + player.getDisplayName());
        } else if (player.isWhitelisted()) {
            player.setPlayerListName(ChatColor.AQUA + "[Tag] " + ChatColor.RESET + player.getDisplayName());
        } else {
            player.setPlayerListName(ChatColor.GREEN + "[Vendeg] " + ChatColor.RESET + player.getDisplayName());
        }
    }

    private String convertTimeToString(Long tickTime) {
        if (tickTime >= 23000) return "reggel";
        else if (tickTime >= 18000 && tickTime < 23000) return "éjjel";
        else if (tickTime >= 12000 && tickTime < 18000) return "este";
        else if (tickTime >= 9000 && tickTime < 12000) return "délután";
        else if (tickTime >= 6000 && tickTime < 9000) return "dél";
        else if (tickTime >= 1000 && tickTime < 6000) return "délelött";
        else if (tickTime < 1000) return "reggel";
        else return "time";
    }
}
