package me.agronaut.essentials.events;

import me.agronaut.essentials.Classes.baseScoreBoard;
import me.agronaut.essentials.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.*;

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
        } else if (plugin.playersGroups.containsKey(player.getUniqueId())) {
            player.setPlayerListName(ChatColor.AQUA + "[Tag] " + ChatColor.RESET + player.getDisplayName());
        } else {
            player.setPlayerListName(ChatColor.GREEN + "[Vendeg] " + ChatColor.RESET + player.getDisplayName());
        }

        // permission setup
        PermissionAttachment attachment = player.addAttachment(plugin);
        plugin.playersPerms.put(player.getUniqueId(), attachment);
        if (plugin.playersGroups.containsKey(player.getUniqueId()))
        {
            for (String group : plugin.playersGroups.get(player.getUniqueId()))
            {
                for (String perms : plugin.groupPermissions.get(group))
                {
                    plugin.getLogger().info("groups: " + group);
                    plugin.getLogger().info(String.format("permission beallitasa %s: %s", player.getDisplayName(), perms));
                    plugin.playersPerms.get(player.getUniqueId()).setPermission(perms, true);
                }
            }
        }

        // scoreboard
        baseScoreBoard board = new baseScoreBoard(plugin);
        board.showScoreboard(player);

    }

    private String convertTimeToString(Long tickTime) {
        if (tickTime >= 23000) return "reggel";
        else if (tickTime >= 18000) return "éjjel";
        else if (tickTime >= 12000) return "este";
        else if (tickTime >= 9000) return "délután";
        else if (tickTime >= 6000) return "dél";
        else if (tickTime >= 1000) return "délelött";
        else return "reggel";
    }
}
