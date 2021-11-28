package me.agronaut.essentials.commands;

import me.agronaut.essentials.Essentials;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;

public class PermissionGroupCommand implements CommandExecutor {
    private Essentials plugin;

    public PermissionGroupCommand(Essentials plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("essentials.permissions")) {
            ArrayList<String> group = plugin.groupPermissions.get(args[1]);
            if (group != null) {
                if ((args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("remove") && args.length == 3)) {
                    String param = args[0];
                    String permission = args[2];

                    if ("add".equalsIgnoreCase(param)) {
                        group.add(permission);
                        sender.sendMessage(ChatColor.YELLOW + permission + " permission added to " + group + " group.");
                    } else if ("remove".equalsIgnoreCase(param)) {
                        group.remove(permission);
                        sender.sendMessage(ChatColor.YELLOW + permission + " permission removed from " + group + " group.");
                    }
                    return true;
                } else if ("list".equalsIgnoreCase(args[0]) && args.length == 2) {
                    StringBuilder sb = new StringBuilder();
                    for (String perm : group) {
                        sb.append(perm + ", ");
                    }
                    sender.sendMessage(ChatColor.YELLOW + args[1] + " has permissions:");
                    sender.sendMessage(ChatColor.AQUA + (sb.toString().length() > 0 ? sb.toString().substring(0, sb.toString().length() - 2) : ""));
                    return true;
                }
            } else {sender.sendMessage(Essentials.playerNotFoundMsg);}
            return false;
        }
        return true;
    }
}
