package ru.ifmo.snake;

import org.junit.Ignore;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;


public class BoardTest extends Board {
    @Ignore("not ready yet")
    @Test
    public void jj() {
        Board boardIgnoreObj = new Board();
       // boardIgnoreObj.appleEscaping();
    }

    @Test
    public void testCheckApple() {
        Board boardObj = new Board();
        x[0] = 50;
        y[0] = 50;
      //  boardObj.checkApple(70, 70);
        assertTrue(isNeedSearchAppleZone);
    }

    @Test
    public void test() {
    //    checkApple(100, 100);
        for (int i = 0; i < 16; i++) {
            assertTrue("contains", appleSearchZoneXYList.contains(appleSearchZoneXYList.get((int) (Math.random() * 16))));
        }
    }

}





