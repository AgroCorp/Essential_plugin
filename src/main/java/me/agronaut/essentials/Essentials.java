package me.agronaut.essentials;

import me.agronaut.essentials.Classes.baseScoreBoard;
import me.agronaut.essentials.commands.*;
import me.agronaut.essentials.events.chat;
import me.agronaut.essentials.events.damage;
import me.agronaut.essentials.events.join;
import me.agronaut.essentials.events.move;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Essentials extends JavaPlugin {
    private File moneyConfigFile;
    private FileConfiguration moneyConfig;

    public ArrayList<UUID> hiddenPlayers = new ArrayList<>();
    public HashMap<UUID, Long> playersMoney = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic

        getLogger().info("commands initialize");
        // register Commands
        getCommand("tp").setExecutor(new tp());
        getCommand("tph").setExecutor(new tph());
        getCommand("feed").setExecutor(new feed());
        getCommand("fly").setExecutor(new fly());
        getCommand("hide").setExecutor(new hide(this));
        getCommand("heal").setExecutor(new heal());
        getCommand("inventory").setExecutor(new inventory());
        getCommand("scoreboard").setExecutor(new scoreboard(this));

        getLogger().info("events initialize");
        // register Listeners
        getServer().getPluginManager().registerEvents(new join(this), this);
        getServer().getPluginManager().registerEvents(new move(this), this);
        getServer().getPluginManager().registerEvents(new chat(), this);
        getServer().getPluginManager().registerEvents(new damage(this), this);


        getLogger().info("save default config");
        // default config save
        getConfig().options().copyDefaults();
        createMoneyConfig();
        saveDefaultConfig();

        getLogger().info("playersMoney initialize");
        // read in players money
        if (moneyConfig.getConfigurationSection("money") != null)
        {
            for (String key : moneyConfig.getConfigurationSection("money").getKeys(false))
            {
                getLogger().info("Player UUID: " + key + ", money: " + String.valueOf(getConfig().getLong("money." + key + ".money")));
                playersMoney.put(UUID.fromString(key), moneyConfig.getLong("money." + key + ".money"));
            }
        }


        getLogger().info("runnable initialize");
        // add money after 30 minute
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player iter : getServer().getOnlinePlayers())
                {
                    iter.sendMessage(ChatColor.YELLOW + "online idod miatt kapsz 100$-t");
                    if (playersMoney.containsKey(iter.getUniqueId()))
                    {
                        playersMoney.put(iter.getUniqueId(), playersMoney.get(iter.getUniqueId()) + 100L);
                    } else {
                        playersMoney.put(iter.getUniqueId(), 100L);
                    }
                    // after add money generate new scoreboard to players
                    baseScoreBoard board = new baseScoreBoard(Essentials.this);
                    board.showScoreboard(iter);
                }
            }
        }.runTaskTimer(this, 0L, 20 * /*60 **/ 30);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        getLogger().info("save playersMoney");
        //save playersMoney
        for (Map.Entry<UUID, Long> iter : playersMoney.entrySet())
        {
            getLogger().info("player UUID: " + iter.getKey().toString() + ", money: " + iter.getValue());
            moneyConfig.set("money." + iter.getKey().toString() + ".money", iter.getValue());
        }
        saveMoneyConfig();
    }

    public void showPlayer(Player player) {
        for (Player online : getServer().getOnlinePlayers()) {
            online.showPlayer(this, player);
        }
    }

    public void hidePlayer(Player player) {
        for (Player online : getServer().getOnlinePlayers()) {
            online.hidePlayer(this, player);
        }
    }

    public void actionBar(Player player) {
        for (Entity ent : player.getNearbyEntities(5,5,5))
        {
            if (ent instanceof LivingEntity && getLookingAt(player, ent))
            {
                LivingEntity target = (LivingEntity) ent;
                //String actionBar = ChatColor.GREEN + livingEnt.getType().toString() + ": " + getHealthColor(livingEnt.getHealth(), livingEnt.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue()) + livingEnt.getHealth() + ChatColor.GREEN + "/" + livingEnt.getAttribute(Attribute.GENERIC_MAX_HEALTH).getDefaultValue();
                String test = ChatColor.GREEN + target.getType().toString() + ": " +
                        getHealthColor(target.getHealth(), target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue()) +
                        healthToSquare(target.getHealth(), target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(test));
            }
        }
    }

    private ChatColor getHealthColor(double health, double max) {
        if (health <= max * 0.75) return ChatColor.YELLOW;
        else if (health <= max * 0.5) return ChatColor.GOLD;
        else if (health <= max * 0.25) return ChatColor.RED;
        else if (health <= max * 0.1) return ChatColor.DARK_RED;
        else return ChatColor.GREEN;
    }

    private String healthToSquare(double health, double max) {
        return Character.toString((char) 9632).repeat((int) (health / max * 10));
    }

    private boolean getLookingAt(Player player, Entity entity) {
        Location eye = player.getEyeLocation();
        Vector toEntity = entity.getLocation().toVector().subtract(eye.toVector());
        double dot = toEntity.normalize().dot(eye.getDirection());

        return dot > 0.99D;
    }

    private void saveMoneyConfig() {
        try
        {
            moneyConfig.save(moneyConfigFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createMoneyConfig()
    {
        moneyConfigFile = new File(getDataFolder(), "money.yml");
        if (!moneyConfigFile.exists())
        {
            if (moneyConfigFile.getParentFile().mkdirs())
            {
                saveResource("money.yml", false);
            } else
            {
                getLogger().warning("failed to create folders and save money.yml");
            }
        }

        moneyConfig = new YamlConfiguration();

        try
        {
            moneyConfig.load(moneyConfigFile);
        } catch (IOException | InvalidConfigurationException e)
        {
            e.printStackTrace();
        }
    }
}
