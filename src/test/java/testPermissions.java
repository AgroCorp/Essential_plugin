import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class testPermissions {
    private File permissionFile;
    private FileConfiguration permissionConfig;
    private HashMap<String, ArrayList<String>> groupPermissions = new HashMap<>();
    private HashMap<UUID, ArrayList<String>> playersGroups = new HashMap<>();

    @Test
    public void testPermissionReadIn() throws IOException, InvalidConfigurationException {
        permissionFile = new File("G:\\permissions.yml");
        permissionConfig = new YamlConfiguration();
        permissionConfig.load(permissionFile);


        for (String group : permissionConfig.getConfigurationSection("groups").getKeys(false))
        {
            // read in groups permissions
            ArrayList<String> listOfPermissions = new ArrayList<>(permissionConfig.getStringList("groups." + group));
            groupPermissions.put(group, listOfPermissions);
        }

        for (String playerUUID : permissionConfig.getConfigurationSection("users").getKeys(false))
        {
            ArrayList<String> groups = new ArrayList<>(permissionConfig.getStringList("users." + playerUUID + ".group"));
            playersGroups.put(UUID.fromString(playerUUID), groups);
        }
        Assert.assertNotNull(playersGroups);
    }
}
