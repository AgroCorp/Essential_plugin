package me.agronaut.essentials.commands;

import me.agronaut.essentials.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Money implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length == 0) // /money
        {
            if (commandSender instanceof Player)
            {
                Player player = (Player) commandSender;

                if(player.hasPermission("essentials.money.self"))
                    player.sendMessage(ChatColor.YELLOW + "Your balance: " + (Essentials.playersMoney.get(player.getUniqueId()) != null ? Essentials.playersMoney.get(player.getUniqueId()) : 0) + Essentials.moneySign);
                else
                    player.sendMessage(Essentials.permissionMsg);
            }
        }
        else if (strings.length == 1) // /money felhasznalonev
        {
            if (commandSender instanceof Player)
            {
                Player player = (Player) commandSender;
                Player target = Bukkit.getPlayerExact(strings[0]);

                if (target != null)
                {
                    if (player.hasPermission("essentials.money.other"))
                        player.sendMessage(ChatColor.YELLOW + target.getDisplayName() +"'s balance: " + (Essentials.playersMoney.get(target.getUniqueId()) != null ? Essentials.playersMoney.get(target.getUniqueId()) : 0) + Essentials.moneySign);
                    else
                        player.sendMessage(Essentials.permissionMsg);
                } else player.sendMessage(Essentials.playerNotFoundMsg);
            } else
                commandSender.sendMessage(Essentials.onlyPlayer);
        }


        return true;
    }
}
