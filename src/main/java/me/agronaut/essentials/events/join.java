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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

public class join implements Listener {
    private final Logger logger = Bukkit.getLogger();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // Title when join to server
        String title = Essentials.helloMsg;
        String subTitle = Essentials.helloSubMsg;

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

        // edit tab menu name
        Essentials.updateTabList(player);

        // permission setup
        PermissionAttachment attachment = player.addAttachment(Bukkit.getPluginManager().getPlugin("Essentials"));
        Essentials.playersPerms.put(player.getUniqueId(), attachment);
        if (Essentials.playersGroups.containsKey(player.getUniqueId()))
        {
            for (String group : Essentials.playersGroups.get(player.getUniqueId()))
            {
                for (String perms : Essentials.groupPermissions.get(group))
                {
                    logger.info("groups: " + group);
                    logger.info(String.format("permission beallitasa %s: %s", player.getDisplayName(), perms));
                    Essentials.playersPerms.get(player.getUniqueId()).setPermission(perms, true);
                }
            }
        }
        // if whitelisted automatically add to user group
        if (player.isWhitelisted() && !Essentials.playersGroups.containsKey(player.getUniqueId()))
        {
            Essentials.playersGroups.put(player.getUniqueId(), new ArrayList<>(Arrays.asList("user")));
        }

        // scoreboard
        baseScoreBoard board = new baseScoreBoard();
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
