package me.agronaut.essentials.commands;


import me.agronaut.essentials.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;

import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


public class Tp implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("essentials.tp")) {
                if (args.length == 1) {
                    Player target = Bukkit.getPlayerExact(args[0]);
                    if (target != null) {
                        player.teleport(target.getLocation());
                    } else {
                        player.sendMessage(ChatColor.YELLOW + "A játékos nem található");
                    }
                } else {
                    player.sendMessage(ChatColor.RED + "Nem adtál meg paramétert");
                    return false;
                }
            } else {player.sendMessage(Essentials.permissionMsg);}
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> onlinePLayerNames = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                onlinePLayerNames.add(player.getDisplayName());
            }
            return onlinePLayerNames;
        }
        return null;
    }
}
