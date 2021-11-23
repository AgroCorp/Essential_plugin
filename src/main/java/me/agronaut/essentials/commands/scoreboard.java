package me.agronaut.essentials.commands;

import me.agronaut.essentials.Classes.baseScoreBoard;
import me.agronaut.essentials.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.logging.Level;

public class scoreboard implements CommandExecutor {
    Essentials plugin;

    public scoreboard(Essentials plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //plugin.getLogger().info("scoreboard command start");
        if (sender instanceof Player)
        {
            Player player = (Player) sender;
            ScoreboardManager manager = Bukkit.getScoreboardManager();
            if(manager != null)
            {
                //plugin.getLogger().info("getObjectives length: " + player.getScoreboard().getObjectives().size());
                if (player.getScoreboard().getObjectives().isEmpty()) {
                    baseScoreBoard baseScoreBoard = new baseScoreBoard(plugin, player);

                    player.setScoreboard(baseScoreBoard.getScoreboard());
                } else
                {
                    //plugin.getLogger().info("scoreboard eltuntetese");
                    player.setScoreboard(manager.getMainScoreboard());
                }
            } else {
                player.sendMessage(ChatColor.RED + "Hiba a scoreboard parancs futasa kozben");
            }
            return true;
        } else
        {
            sender.sendMessage("This command use only player");
        }
        //plugin.getLogger().info("scoreboard command end");
        return false;
    }
}
