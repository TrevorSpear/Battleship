package edu.oregonstate.cs361.battleship;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    @Test
    public void testAcross() {
        Point Testing = new Point();
        assertEquals(0,Testing.getAcross());
    }

    @Test
    public void testDown(){
        Point Testing = new Point();
        assertEquals(0,Testing.getDown());
    }

    @Test
    public void testAcross2(){
        Point Testing = new Point();
        Testing.setAcross(1);
        assertEquals(1,Testing.getAcross());
    }

    @Test
    public void testDown2(){
        Point Testing = new Point();
        Testing.setDown(1);
        assertEquals(1,Testing.getDown());
    }
}