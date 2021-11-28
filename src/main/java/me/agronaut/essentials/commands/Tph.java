package me.agronaut.essentials.commands;

import me.agronaut.essentials.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Tph implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("essentials.tph")) {
                if (args.length == 1) {
                    Player target = Bukkit.getPlayerExact(args[0]);
                    if (target != null) {
                        target.teleport(player.getLocation());
                    } else {
                        player.sendMessage(ChatColor.YELLOW + "Játékos nem található");
                    }
                } else {
                    player.sendMessage(ChatColor.YELLOW + "Nem adtál meg játékos nevet");
                    return false;
                }
            } else {player.sendMessage(Essentials.permissionMsg);}
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> onlinePlayersName = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                onlinePlayersName.add(player.getDisplayName());
            }
            return onlinePlayersName;
        }

        return null;
    }
}
