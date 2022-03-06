package me.agronaut.essentials.commands;

import me.agronaut.essentials.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Hide implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("essentials.hide")) {
                if (args.length == 0) {
                    if (Essentials.hiddenPlayers.contains(player.getUniqueId())) {
                        Essentials.hiddenPlayers.remove(player.getUniqueId());
                        player.sendMessage(ChatColor.YELLOW + "Látható vagy mások számára");
                        showPlayer(player);
                    } else {
                        Essentials.hiddenPlayers.add(player.getUniqueId());
                        player.sendMessage(ChatColor.YELLOW + "Láthatalan vagy mások számára");
                        hidePlayer(player);
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

    public void showPlayer(Player player) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.showPlayer(Bukkit.getPluginManager().getPlugin("Essentials"), player);
        }
    }

    public void hidePlayer(Player player) {
        for (Player online : Bukkit.getOnlinePlayers()) {
            online.hidePlayer( player);
        }
    }
}
