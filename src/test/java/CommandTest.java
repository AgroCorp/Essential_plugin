import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import me.agronaut.essentials.Essentials;
import org.bukkit.entity.Player;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CommandTest {
    private ServerMock server;
    private Essentials plugin;
    private PlayerMock player;

    @Before
    public void setUp() throws Exception {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(Essentials.class);
        player = server.addPlayer();
    }

    @After
    public void tearDown(){
        MockBukkit.unmock();
    }

    @Test
    public void testPayments(){
        Player target = server.addPlayer();
        // test adding payments to list
        player.performCommand("pay 100 player1");

        Assert.assertEquals(1, Essentials.payments.size());
        Assert.assertEquals(player, Essentials.payments.get(0).getFrom());
        Assert.assertEquals(target, Essentials.payments.get(0).getTo());
        Assert.assertEquals(100, Essentials.payments.get(0).getAmount());
        Assert.assertFalse(Essentials.payments.get(0).isConfirmed());

        // test when target null
        player.performCommand("pay 100 test");
        Assert.assertEquals(1, Essentials.payments.size());

        // test removing payments from list
        player.performCommand("pay decline player1");

        Assert.assertEquals(0, Essentials.payments.size());

        // test confirm payments

        player.performCommand("pay 100 player1");


    }
}
