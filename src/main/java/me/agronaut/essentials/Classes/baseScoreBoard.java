package me.agronaut.essentials.Classes;

import me.agronaut.essentials.Essentials;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.ArrayList;
import java.util.UUID;

public class baseScoreBoard {
    private final ScoreboardManager manager = Bukkit.getScoreboardManager();
    private final Scoreboard scoreboard = manager.getNewScoreboard();

    public baseScoreBoard(){}

    /**
     * Initializing player scoreboards. Create and show joined player's scoreboard
     * @param player
     * @param hiddenToo - turn on scoreboard for all players or just who enabled it (if player disable scoreboard, we dont turn on again randomly)
     */
    private void init( Player player, boolean hiddenToo)
    {
        // if player disable scoreboard dont turn on automatically again example: get money after 30 min
        if (!hiddenToo && player.getScoreboard().getObjective(DisplaySlot.SIDEBAR) == null)
        {
            return;
        }

        if(player.getScoreboard().getObjective("test") != null)
        {
            player.getScoreboard().getObjective("test").unregister();
        }

        Objective objective = scoreboard.registerNewObjective("test", "money", ChatColor.AQUA + "STATS");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score score = objective.getScore(ChatColor.GOLD + "money: $ " + ChatColor.GREEN + (Essentials.playersMoney.get(player.getUniqueId()) != null ? Essentials.playersMoney.get(player.getUniqueId()).intValue(): 0));
        score.setScore(1);

        player.setScoreboard(scoreboard);
    }

    /**
     * Remove scoreboard for player
     * @param player
     */
    public void removeScoreboard( Player player)
    {
        player.setScoreboard(manager.getMainScoreboard());
    }

    /**
     * call showScoreboard with disabled=false
     * @param player
     */
    public void showScoreboard( Player player)
    {
        showScoreboard(player, false);
    }

    /**
     * show scoreboard for player
     * @param player
     * @param disabled - if true player's scoreboard turned on everytime. If false turn scoreboard only if player turned on before.
     */
    public void showScoreboard(Player player, boolean disabled)
    {
        init(player, disabled);
    }

    /**
     *
     * @return scoreboard
     */
    public Scoreboard getScoreboard() {
        return scoreboard;
    }
}
