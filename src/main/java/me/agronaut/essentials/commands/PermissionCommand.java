package me.agronaut.essentials.commands;

import me.agronaut.essentials.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PermissionCommand implements CommandExecutor {
    private Essentials plugin;

    public PermissionCommand(Essentials plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label,  String[] args) {
        if (sender.hasPermission("essentials.permissions")) {
            Player player = Bukkit.getPlayerExact(args[1]);
            if (player != null) {
                if ((args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove") && args.length == 3)) {
                    String param = args[0];
                    String permission = args[2];

                    if ("add".equalsIgnoreCase(param)) {
                        plugin.playersPerms.get(player.getUniqueId()).setPermission(permission, true);
                        sender.sendMessage(ChatColor.YELLOW + permission + " permission added to user: " + player.getDisplayName());
                    } else if ("remove".equalsIgnoreCase(param)) {
                        plugin.playersPerms.get(player.getUniqueId()).unsetPermission(permission);
                        sender.sendMessage(ChatColor.YELLOW + permission + " permission removed from user: " + player.getDisplayName());
                    }
                    return true;
                } else if ("list".equalsIgnoreCase(args[0]) && args.length == 2) {
                    StringBuilder sb = new StringBuilder();
                    for (String perm : plugin.playersPerms.get(player.getUniqueId()).getPermissions().keySet()) {
                        if (plugin.playersPerms.get(player.getUniqueId()).getPermissions().get(perm)) {
                            sb.append(perm + ", ");
                        }
                    }
                    sender.sendMessage(ChatColor.YELLOW + player.getDisplayName() + " has permissions:");
                    sender.sendMessage(ChatColor.AQUA + (sb.toString().length() > 0 ? sb.toString().substring(0,  sb.toString().length() - 2) : ""));
                    return true;
                }
            } else {sender.sendMessage(Essentials.playerNotFoundMsg);}
            return false;
        }
        return true;
    }
}
