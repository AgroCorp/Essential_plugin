import me.agronaut.essentials.DAO.Permission;
import me.agronaut.essentials.DAO.PermissionGroup;
import me.agronaut.essentials.DAO.Player;
import me.agronaut.essentials.Service.GroupService;
import me.agronaut.essentials.Service.PlayerService;
import me.agronaut.essentials.Utils.HibernateUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.UUID;

public class testPlayerService {
    private PlayerService playerSD;
    private GroupService groupSD;

    @Before
    public void setUp(){
        HibernateUtils.initialize();
        playerSD = new PlayerService();
        groupSD = new GroupService();
    }

    @Test
    public void saveTest() {
        Player player = new Player();
        PermissionGroup group = new PermissionGroup();

        group.setName("user");

        LinkedList <Permission> permissions = new LinkedList<>();
        permissions.add(new Permission("essentials.heal.self", group));
        permissions.add(new Permission("essentials.feed.self", group));
        permissions.add(new Permission("essentials.hide", group));
        permissions.add(new Permission("essentials.fly", group));

        group.setPermissions(permissions);

        //groupSD.save(group);

        player.setName("Agronaut");
        player.setUuid(UUID.randomUUID());
        player.setGroup(group);

        playerSD.save(player);
    }
}
