package me.agronaut.essentials.Classes;

import me.agronaut.essentials.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.UUID;

public class baseScoreBoard {
    private Essentials plugin;
    private final ScoreboardManager manager = Bukkit.getScoreboardManager();
    private final Scoreboard scoreboard = manager.getNewScoreboard();

    public baseScoreBoard(Essentials plugin) {
        this.plugin = plugin;
    }

    private void init( Player player)
    {
        if(player.getScoreboard().getObjective("test") != null)
        {
            player.getScoreboard().getObjective("test").unregister();
        }

        Objective objective = scoreboard.registerNewObjective("test", "money", ChatColor.AQUA + "STATS");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score score = objective.getScore(ChatColor.GOLD + "money: $ " + ChatColor.GREEN + plugin.playersMoney.get(player.getUniqueId()).intValue());
        score.setScore(1);

        player.setScoreboard(scoreboard);
    }

    public void removeScoreboard( Player player)
    {
        player.setScoreboard(manager.getMainScoreboard());
    }

    public void showScoreboard( Player player)
    {
        init(player);
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }
}
