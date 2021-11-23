import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class testHealthToSquare {

    @Test
    public void testHealthToSquareFunction()
    {
        int health = 10;
        int maxHealth = 10;
        String text = healthToSquare(health, maxHealth);
        System.out.println(text);
        assertEquals("■■■■■■■■■■", text);
    }

    private String healthToSquare(double health, double max) {
        return Character.toString((char) 9632).repeat((int) (health / max * 10));
    }
}
