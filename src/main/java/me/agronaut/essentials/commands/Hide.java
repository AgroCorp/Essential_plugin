package me.agronaut.essentials.commands;

import me.agronaut.essentials.Essentials;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Hide implements CommandExecutor {
    Essentials plugin;

    public Hide(Essentials plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("essentials.hide")) {
                if (args.length == 0) {
                    if (plugin.hiddenPlayers.contains(player.getUniqueId())) {
                        plugin.hiddenPlayers.remove(player.getUniqueId());
                        player.sendMessage(ChatColor.YELLOW + "Látható vagy mások számára");
                        plugin.showPlayer(player);
                    } else {
                        plugin.hiddenPlayers.add(player.getUniqueId());
                        player.sendMessage(ChatColor.YELLOW + "Láthatalan vagy mások számára");
                        plugin.hidePlayer(player);
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Nem adhatsz meg paramétert");
                    return false;
                }
            }
            else {player.sendMessage(Essentials.permissionMsg);}
        }
        return true;
    }
}
