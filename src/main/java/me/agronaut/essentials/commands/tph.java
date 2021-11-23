package me.agronaut.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class tph implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 1) {
                Player target = Bukkit.getPlayerExact(args[0]);
                if (target != null) {
                    player.teleport(target.getLocation());
                } else {
                    player.sendMessage(ChatColor.YELLOW + "Játékos nem található");
                }
            } else {
                player.sendMessage(ChatColor.YELLOW + "Nem adtál meg játékos nevet");
                return false;
            }
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
