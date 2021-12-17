package me.agronaut.essentials.commands;

import me.agronaut.essentials.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PermissionGroupCommand implements CommandExecutor {
    private final Essentials plugin;

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
                } else if ("addto".equalsIgnoreCase(args[0]) || "removefrom".equalsIgnoreCase(args[0]) && args.length == 3)
                {
                    Player target = Bukkit.getPlayerExact(args[2]);
                    String addToGroup = args[1];

                    if (target == null) {sender.sendMessage(Essentials.playerNotFoundMsg); return true;}

                    if ("addto".equalsIgnoreCase(args[0])) {
                        if (Essentials.playersGroups.containsKey(target.getUniqueId())) {
                            if (!Essentials.playersGroups.get(target.getUniqueId()).contains(addToGroup)) {
                                Essentials.playersGroups.get(target.getUniqueId()).add(addToGroup);
                                if (Essentials.playersGroups.containsKey(target.getUniqueId()))
                                {
                                    for (String iter : Essentials.playersGroups.get(target.getUniqueId()))
                                    {
                                        for (String perms : plugin.groupPermissions.get(iter))
                                        {
                                            plugin.getLogger().info("groups: " + iter);
                                            plugin.getLogger().info(String.format("permission beallitasa %s: %s", target.getDisplayName(), perms));
                                            plugin.playersPerms.get(target.getUniqueId()).setPermission(perms, true);
                                        }
                                    }
                                }
                                sender.sendMessage(ChatColor.YELLOW + "User " + target.getDisplayName() + " added to group: " + addToGroup);
                            }
                            else {
                                sender.sendMessage(ChatColor.YELLOW + "This user already member of group: " + addToGroup);
                            }
                        } else
                        {
                            ArrayList<String> temp = new ArrayList<>();
                            temp.add(addToGroup);
                            Essentials.playersGroups.put(target.getUniqueId(), temp);
                            sender.sendMessage(ChatColor.YELLOW + "User " + target.getDisplayName() + " added to group: " + addToGroup);
                        }
                    } else if ("removefrom".equalsIgnoreCase(args[0]))
                    {
                        if (Essentials.playersGroups.containsKey(target.getUniqueId()))
                        {
                            if (Essentials.playersGroups.get(target.getUniqueId()).contains(addToGroup))
                            {
                                if (Essentials.playersGroups.containsKey(target.getUniqueId()))
                                {
                                    for (String iter : Essentials.playersGroups.get(target.getUniqueId()))
                                    {
                                        for (String perms : plugin.groupPermissions.get(iter))
                                        {
                                            plugin.getLogger().info("groups: " + iter);
                                            plugin.getLogger().info(String.format("permission beallitasa %s: %s", target.getDisplayName(), perms));
                                            plugin.playersPerms.get(target.getUniqueId()).unsetPermission(perms);
                                        }
                                    }
                                }
                                Essentials.playersGroups.get(target.getUniqueId()).remove(addToGroup);
                                sender.sendMessage(ChatColor.YELLOW + String.format("User %s removed from group: %s", target.getDisplayName(), addToGroup));
                            }
                            else
                            {
                                sender.sendMessage(ChatColor.YELLOW + String.format("User not in group: %s", addToGroup));
                            }
                        }
                        else {
                            sender.sendMessage(Essentials.playerNotFoundMsg);
                        }
                    }
                    Essentials.updateTabList(target);
                }
            } else {sender.sendMessage(ChatColor.RED + "Group not found in database!");}
            return true;
        }
        return true;
    }

    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args)
    {
        if (args.length == 0)
        {
            return new ArrayList<>(Arrays.asList("add", "addto", "remove", "removefrom", "list"));
        } else if (args.length == 1)
        {
            return new ArrayList<>(plugin.groupPermissions.keySet());
        }
        else {
            ArrayList<String> names = new ArrayList<>();
            for (Player iter : Bukkit.getOnlinePlayers())
            {
                names.add(iter.getName());
            }
            return names;
        }
    }
}
