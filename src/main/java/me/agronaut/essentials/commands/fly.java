package me.agronaut.essentials.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class fly implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length == 0) {
                if (player.isFlying()) {
                    player.sendMessage(ChatColor.YELLOW + "Repülés kikapcsolva");
                    player.setFlying(false);
                } else {
                    player.sendMessage(ChatColor.YELLOW + "Repülés bekapcsolva");
                    player.setFlying(true);
                }
            } else {
                player.sendMessage(ChatColor.YELLOW + "Nem adhatsz meg paramétert");
            }
        }
        return true;
    }
}
