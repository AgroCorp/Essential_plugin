import me.agronaut.essentials.DAO.Groups;
import me.agronaut.essentials.DAO.Player;
import me.agronaut.essentials.Service.GroupService;
import me.agronaut.essentials.Service.PlayerService;
import me.agronaut.essentials.Utils.HibernateUtils;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

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
        Groups group = new Groups();

        group.setName("user");

        LinkedList <String> permissions = new LinkedList<>();
        permissions.add("essentials.heal.self");
        permissions.add("essentials.feed.self");
        permissions.add("essentials.hide");
        permissions.add("essentials.fly");

        group.setPermissions(permissions);

        //groupSD.save(group);

        player.setName("Agronaut");
        player.setGroup(group);

        playerSD.save(player);
    }
}
