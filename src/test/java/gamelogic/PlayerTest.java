package gamelogic;

import org.junit.Assert;
import org.junit.Test;

public class PlayerTest {

    @Test
    public void testGetSetUsername() {
        Player player = new Player();
        player.setUsername("testPlayer");
        Assert.assertEquals("testPlayer", player.getUsername());
    }

    @Test
    public void testGetSetID() {
        Player player = new Player();
        player.setID("1");
        Assert.assertEquals(1, player.getID());
    }

    @Test
    public void testGetSetColor() {
        Player player = new Player(1,"testPlayer");

        player.setColor('R');
        Assert.assertEquals('R', player.color);
        Assert.assertEquals('R', player.getColor());
        player.setColor('B');
        Assert.assertEquals('B', player.color);
        Assert.assertEquals('B', player.getColor());
        player.setColor('Y');
        Assert.assertEquals('Y', player.color);
        Assert.assertEquals('Y', player.getColor());
        player.setColor('G');
        Assert.assertEquals('G', player.color);
        Assert.assertEquals('G', player.getColor());
    }

    @Test
    public void testGetSetPoints() {
        Player player = new Player();

        player.setPoints(1234);
        Assert.assertEquals(1234, player.points);
        Assert.assertEquals(1234, player.getPoints());
    }

    @Test
    public void testIsBlue() {
        Player player = new Player();

        player.setColor('B');
        Assert.assertTrue(player.isBlue());
        player.setColor('R');
        Assert.assertFalse(player.isBlue());
        player.setColor('Y');
        Assert.assertFalse(player.isBlue());
        player.setColor('G');
        Assert.assertFalse(player.isBlue());
    }

    @Test
    public void testIsRed() {
        Player player = new Player();

        player.setColor('R');
        Assert.assertTrue(player.isRed());
        player.setColor('B');
        Assert.assertFalse(player.isRed());
        player.setColor('Y');
        Assert.assertFalse(player.isRed());
        player.setColor('G');
        Assert.assertFalse(player.isRed());
    }

    @Test
    public void testIsGreen() {
        Player player = new Player();

        player.setColor('G');
        Assert.assertTrue(player.isGreen());
        player.setColor('R');
        Assert.assertFalse(player.isGreen());
        player.setColor('Y');
        Assert.assertFalse(player.isGreen());
        player.setColor('B');
        Assert.assertFalse(player.isGreen());
    }
}