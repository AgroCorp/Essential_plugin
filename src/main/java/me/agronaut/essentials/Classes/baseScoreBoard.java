package me.agronaut.essentials.Classes;

import me.agronaut.essentials.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class baseScoreBoard {
    private Essentials plugin;
    private final ScoreboardManager manager = Bukkit.getScoreboardManager();
    private final Scoreboard scoreboard = manager.getNewScoreboard();

    public baseScoreBoard(Essentials plugin, Player player) {
        this.plugin = plugin;

        Objective objective = scoreboard.registerNewObjective("test", "money", ChatColor.AQUA + "STATS");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score score = objective.getScore(ChatColor.GOLD + "money: $ " + ChatColor.GREEN + plugin.playersMoney.get(player.getUniqueId()).intValue());
        score.setScore(1);
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }
}
