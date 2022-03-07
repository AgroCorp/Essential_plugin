package me.agronaut.essentials;

import me.agronaut.essentials.Classes.Payment;
import me.agronaut.essentials.Classes.SQLcontroller;
import me.agronaut.essentials.Classes.baseScoreBoard;
import me.agronaut.essentials.commands.*;
import me.agronaut.essentials.events.*;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class Essentials extends JavaPlugin {
    private File moneyConfigFile;
    private FileConfiguration moneyConfig;
    private File permissionFile;
    private FileConfiguration permissionConfig;

    public static String permissionMsg;
    public static String playerNotFoundMsg;
    public static String moneySign;
    public static String helloMsg;
    public static String helloSubMsg;
    public final static String onlyPlayer = ChatColor.RED + "This command only can use players";

    public static ArrayList<UUID> hiddenPlayers = new ArrayList<>();
    public static HashMap<UUID, Long> playersMoney = new HashMap<>();
    public static HashMap<String, ArrayList<String>> groupPermissions = new HashMap<>();
    public static HashMap<UUID, ArrayList<String>> playersGroups = new HashMap<>();
    public static HashMap<UUID, PermissionAttachment> playersPerms = new HashMap<>();
    public static ArrayList<Payment> payments = new ArrayList<>();

    public Essentials() {
        super();
    }

    protected Essentials(JavaPluginLoader loader, PluginDescriptionFile descriptionFile, File dataFolder, File file) {
        super(loader, descriptionFile, dataFolder, file);
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        getLogger().info("commands initialize");
        // register Commands
        getCommand("tp").setExecutor(new Tp());
        getCommand("tph").setExecutor(new Tph());
        getCommand("feed").setExecutor(new Feed());
        getCommand("fly").setExecutor(new Fly());
        getCommand("hide").setExecutor(new Hide());
        getCommand("heal").setExecutor(new Heal());
        getCommand("inventory").setExecutor(new Inventory());
        getCommand("scoreboard").setExecutor(new Scoreboard());
        getCommand("permissions").setExecutor(new PermissionCommand());
        getCommand("permissions-group").setExecutor(new PermissionGroupCommand());
        getCommand("money").setExecutor(new Money());
        getCommand("pay").setExecutor(new Pay());

        getLogger().info("events initialize");
        // register Listeners
        getServer().getPluginManager().registerEvents(new join(), this);
        getServer().getPluginManager().registerEvents(new move(), this);
        getServer().getPluginManager().registerEvents(new chat(), this);
        getServer().getPluginManager().registerEvents(new damage(), this);
        getServer().getPluginManager().registerEvents(new opingPlayer(), this);
        getServer().getPluginManager().registerEvents(new breakEvent(), this);
        getServer().getPluginManager().registerEvents(new Interact(), this);
        getServer().getPluginManager().registerEvents(new PlaceBlock(),this);

        getServer().getPluginManager().registerEvents(new LeaveEvent(), this);

        getLogger().info("save default config");
        // default config save
        getConfig().options().copyDefaults();
        createMoneyConfig();
        saveDefaultConfig();

        //statics
        permissionMsg = ChatColor.RED + getConfig().getString("permission-msg", "You dont have permission!");
        playerNotFoundMsg = ChatColor.RED + getConfig().getString("player-not-found-msg", "Player not found!");
        moneySign = getConfig().getString("money-sign", "$");
        helloMsg = getConfig().getString("hello-msg", "&5Üdv &3%player% &5!");
        helloSubMsg = getConfig().getString("hello-submsg", "&2Szerveren épp &4%daytime% &2van");

        getLogger().info("playersMoney initialize");
        // read in players money
        if (moneyConfig.getConfigurationSection("money") != null) {
            for (String key : moneyConfig.getConfigurationSection("money").getKeys(false)) {
                getLogger().info("Player UUID: " + key + ", money: " + moneyConfig.getLong("money." + key + ".money"));
                playersMoney.put(UUID.fromString(key), moneyConfig.getLong("money." + key + ".money"));
            }
        }

        if (!isJUnitTest()) {
            createPermissionConfig();

            // permissions beolvasasa
            if (permissionConfig.getConfigurationSection("groups") != null) {
                for (String group : permissionConfig.getConfigurationSection("groups").getKeys(false)) {
                    // read in groups permissions
                    ArrayList<String> listOfPermissions = new ArrayList<>(permissionConfig.getStringList("groups." + group));
                    groupPermissions.put(group, listOfPermissions);
                }
            }
            if (permissionConfig.getConfigurationSection("users") != null) {
                for (String playerUUID : permissionConfig.getConfigurationSection("users").getKeys(false)) {
                    ArrayList<String> groups = new ArrayList<>(permissionConfig.getStringList("users." + playerUUID + ".group"));
                    playersGroups.put(UUID.fromString(playerUUID), groups);
                }
            }
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if(!isJUnitTest()) {
            getLogger().info("save playersMoney");
            //save playersMoney
            for (Map.Entry<UUID, Long> iter : playersMoney.entrySet()) {
                getLogger().info("player UUID: " + iter.getKey().toString() + ", money: " + iter.getValue());
                moneyConfig.set("money." + iter.getKey().toString() + ".money", iter.getValue());
            }
            saveMoneyConfig();


            // save permissions
            getLogger().info("save permissions");
            for (String group : groupPermissions.keySet()) {
                getLogger().info(group + " group permission: " + groupPermissions.get(group).toString());
                permissionConfig.set("groups." + group, groupPermissions.get(group));
            }
            for (UUID player : playersGroups.keySet()) {
                getLogger().info(player.toString() + " permissions: " + playersGroups.get(player).toString());
                if (playersGroups.get(player).size() > 0) {
                    permissionConfig.set("users." + player.toString() + ".group", playersGroups.get(player));
                }
            }
            savePermissions();
        }
    }

    public static void actionBar(Player player) {
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

    private static ChatColor getHealthColor(double health, double max) {
        if (health <= max * 0.75) return ChatColor.YELLOW;
        else if (health <= max * 0.5) return ChatColor.GOLD;
        else if (health <= max * 0.25) return ChatColor.RED;
        else if (health <= max * 0.1) return ChatColor.DARK_RED;
        else return ChatColor.GREEN;
    }

    private static String healthToSquare(double health, double max) {
        return Character.toString((char) 9632).repeat((int) (health / max * 10));
    }

    private static boolean getLookingAt(Player player, Entity entity) {
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
            saveResource("money.yml", false);
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

    private void createPermissionConfig()
    {
        permissionFile = new File(getServer().getWorldContainer(), "permissions.yml");
        permissionConfig = new YamlConfiguration();

        try
        {
            permissionConfig.load(permissionFile);
        } catch (IOException | InvalidConfigurationException e)
        {
            getLogger().warning("hiba a permission config inicializalasa kozben");
            e.printStackTrace();
        }
    }

    private void savePermissions()
    {
        try
        {
            permissionConfig.save(permissionFile);
        } catch (IOException e) {
            getLogger().warning("Saving permissions file failed");
            e.printStackTrace();
        }
    }

    public static void updateTabList(Player player)
    {
        if (player != null) {
            if (player.isOp()) {
                player.setPlayerListName(ChatColor.RED + "[Admin] " + ChatColor.RESET + player.getDisplayName());
            } else if (playersGroups.containsKey(player.getUniqueId()) && playersGroups.get(player.getUniqueId()).contains("user")) {
                player.setPlayerListName(ChatColor.AQUA + "[Tag] " + ChatColor.RESET + player.getDisplayName());
            } else {
                player.setPlayerListName(ChatColor.GREEN + "[Vendeg] " + ChatColor.RESET + player.getDisplayName());
            }
        }
    }

    public static boolean isJUnitTest() {
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            if (element.getClassName().startsWith("org.junit.")) {
                return true;
            }
        }
        return false;
    }
}
