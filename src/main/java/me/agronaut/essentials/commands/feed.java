package me.agronaut.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;

public class feed implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                player.setFoodLevel(20);
                player.sendMessage(ChatColor.GREEN + "Éhséged feltöltődött");
            } else if (args.length == 1) {
                Player target = Bukkit.getPlayerExact(args[0]);
                if (target != null) {
                    target.setFoodLevel(20);
                    player.sendMessage(ChatColor.GREEN + target.getDisplayName() + " éhsége maxra töltődött");
                }
            } else {
                player.sendMessage(ChatColor.RED + "Túl sok paramétert adtál meg");
                return false;
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> onlinePlayerNames = new ArrayList<>();
            for (Player online : Bukkit.getOnlinePlayers()) {
                onlinePlayerNames.add(online.getDisplayName());
            }
            return onlinePlayerNames;
        }

        return null;
    }
}
