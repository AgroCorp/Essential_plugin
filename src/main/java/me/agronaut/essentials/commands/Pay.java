package me.agronaut.essentials.commands;

import me.agronaut.essentials.Classes.Payment;
import me.agronaut.essentials.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Pay implements CommandExecutor {
    private final Logger logger = Bukkit.getLogger();

    @Override
    public boolean onCommand(@org.jetbrains.annotations.NotNull CommandSender commandSender, @org.jetbrains.annotations.NotNull Command command, @org.jetbrains.annotations.NotNull String s, @org.jetbrains.annotations.NotNull String[] strings) {
        if (commandSender instanceof Player)
        {
            Player player = (Player) commandSender;
            if (strings.length == 2)
            {
                Player target = Bukkit.getPlayerExact(strings[1]);
                if (target != null) {
                    Payment pay = Essentials.payments.stream().filter(n -> n.getFrom() == player && n.getTo() == target && !n.isConfirmed()).findFirst().orElse(null);
                    if (pay != null) {
                        if ("confirm".equalsIgnoreCase(strings[0])) {
                            long playerMoney = Essentials.playersMoney.get(player.getUniqueId()).longValue();
                            long targetMoney = Essentials.playersMoney.get(target.getUniqueId()).longValue();
                            playerMoney -= pay.getAmount();
                            targetMoney += pay.getAmount();
                            player.sendMessage(ChatColor.GREEN + "You successfully transfer" + pay.getAmount() + Essentials.moneySign + " to " + target.getDisplayName());
                            target.sendMessage(ChatColor.GREEN + player.getDisplayName() + " successfully transfer " + pay.getAmount() + Essentials.moneySign + "to you");
                            Essentials.playersMoney.put(player.getUniqueId(), playerMoney);
                            Essentials.playersMoney.put(target.getUniqueId(), targetMoney);
                            Essentials.payments.remove(pay);
                        }

                        else if ("decline".equalsIgnoreCase(strings[0])) {
                            Essentials.payments.remove(pay);
                        } else {
                            player.sendMessage(ChatColor.YELLOW + "Payment not found, first you need to start payment to player and confirm that");
                        }
                    } else {
                        int amount;
                        try{
                            amount = Integer.parseInt(strings[0]);
                        } catch (NumberFormatException e)
                        {
                            player.sendMessage(ChatColor.RED + "Bad amount please give an integer");
                            return true;
                        }
                        Payment payment = new Payment(player, target,amount,false);
                        //check payment not in accepting state
                        if (Essentials.payments.contains(payment))
                        {
                            player.sendMessage(ChatColor.YELLOW + "Payment in confirmation state please decline or confirm first and after that start new payment");
                            return true;
                        }
                        Essentials.payments.add(payment);
                    }
                } else player.sendMessage(Essentials.playerNotFoundMsg);
            } else if (strings.length == 1 && strings[0].equalsIgnoreCase("list"))
            {
                player.sendMessage(ChatColor.AQUA + "your payments:");
                player.sendMessage(ChatColor.BLUE + Essentials.payments.stream().filter(n->n.getFrom() == player && !n.isConfirmed()).collect(Collectors.toList()).toString());
            }
            else return false;
        } else commandSender.sendMessage(Essentials.onlyPlayer);
        return true;
    }
}
