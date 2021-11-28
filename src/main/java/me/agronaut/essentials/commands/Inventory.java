package me.agronaut.essentials.commands;

import me.agronaut.essentials.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Inventory implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command,String label, String[] args) {
        if (sender instanceof Player)
        {
            Player player = (Player) sender;
            if (player.hasPermission("eseentials.inventory")) {
                if (args.length == 1) {
                    Player target = Bukkit.getPlayerExact(args[0]);
                    if (target != null) {
                        player.openInventory(target.getInventory());
                        return true;
                    } else {
                        player.sendMessage(ChatColor.RED + "A player nem talalhato a serveren.");
                        return false;
                    }
                }
            }
            else {player.sendMessage(Essentials.permissionMsg);}
        } else {
            sender.sendMessage("csak playerek hasznalhatjak a parancsot!!!");
        }

        return false;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
         if (args.length == 1)
        {
            List<String> onlinePLayerNames = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                onlinePLayerNames.add(player.getDisplayName());
            }
            return onlinePLayerNames;
        }
        return null;
    }
}
