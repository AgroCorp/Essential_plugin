package me.agronaut.essentials.commands;

import me.agronaut.essentials.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Heal implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                if (player.hasPermission("essentials.heal.self")) {
                    while (player.getHealth() < player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue()) {
                        player.setHealth(player.getHealth() + 2);
                    }
                    player.sendMessage(ChatColor.GREEN + "Életed feltöltődött");
                }
                else {player.sendMessage(Essentials.permissionMsg);}
            } else if (args.length == 1) {
                Player target = Bukkit.getPlayerExact(args[0]);
                if (target != null) {
                    if (player.hasPermission("essentials.heal.other")) {
                        while (target.getHealth() < target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue()) {
                            target.setHealth(target.getHealth() + 2);
                        }
                        player.sendMessage(ChatColor.GREEN + target.getDisplayName() + " élete maxra töltődött");
                    }
                    else {player.sendMessage(Essentials.permissionMsg);}
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
