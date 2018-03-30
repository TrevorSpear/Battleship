package edu.oregonstate.cs361.battleship;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BattleshipModelTest {

    @Test
    public void TestPlayerAircraftCarrier() {

        BattleshipModelNormal test = new BattleshipModelNormal();

        //      Aircraft Carrier
        Ship boat = test.getShipByID("playerAircraftCarrier");
        boat.setEnd(1, 1);
        boat.setStart(1, 1);

        Point X = boat.getStart();
        Point Y = boat.getEnd();

        assertEquals("playerAircraftCarrier", boat.getName());
        assertEquals(5, boat.getLength());
        assertEquals(1, X.getDown());
        assertEquals(1, X.getAcross());
        assertEquals(1, Y.getDown());
        assertEquals(1, Y.getAcross());
    }

    @Test
    public void TestPlayerBattleship() {

        BattleshipModelNormal test = new BattleshipModelNormal();

        //      Battleship
        Ship boat = test.getShipByID("playerBattleship");

        boat.setEnd(1, 1);
        boat.setStart(1, 1);

        Point X = boat.getStart();
        Point Y = boat.getEnd();

        assertEquals("playerBattleship", boat.getName());
        assertEquals(4, boat.getLength());
        assertEquals(1, X.getDown());
        assertEquals(1, X.getAcross());
        assertEquals(1, Y.getDown());
        assertEquals(1, Y.getAcross());

    }

    @Test
    public void TestPlayerSubmarine() {

        BattleshipModelNormal test = new BattleshipModelNormal();

        //      Submarine
        Ship boat = test.getShipByID("playerSubmarine");
        boat.setEnd(1, 1);
        boat.setStart(1, 1);

        Point X = boat.getStart();
        Point Y = boat.getEnd();

        assertEquals("playerSubmarine", boat.getName());
        assertEquals(3, boat.getLength());
        assertEquals(1, X.getDown());
        assertEquals(1, X.getAcross());
        assertEquals(1, Y.getDown());
        assertEquals(1, Y.getAcross());

    }

    @Test
    public void TestPlayerCruiser() {

        BattleshipModelNormal test = new BattleshipModelNormal();

        //      Cruiser
        Ship boat = test.getShipByID("playerCruiser");
        boat.setEnd(1, 1);
        boat.setStart(1, 1);

        Point X = boat.getStart();
        Point Y = boat.getEnd();

        assertEquals("playerCruiser", boat.getName());
        assertEquals(3, boat.getLength());
        assertEquals(1, X.getDown());
        assertEquals(1, X.getAcross());
        assertEquals(1, Y.getDown());
        assertEquals(1, Y.getAcross());

    }

    @Test
    public void TestPlayerDestroyer() {

        BattleshipModelNormal test = new BattleshipModelNormal();

        //      Destroyer
        Ship boat = test.getShipByID("playerDestroyer");
        boat.setEnd(1, 1);
        boat.setStart(1, 1);

        Point X = boat.getStart();
        Point Y = boat.getEnd();

        assertEquals("playerDestroyer", boat.getName());
        assertEquals(2, boat.getLength());
        assertEquals(1, X.getDown());
        assertEquals(1, X.getAcross());
        assertEquals(1, Y.getDown());
        assertEquals(1, Y.getAcross());

    }

    @Test
    public void TestComputerAircraftCarrier() {

        BattleshipModelNormal test = new BattleshipModelNormal();

        //      Aircraft Carrier
        Ship boat = test.getShipByID("computerAircraftCarrier");
        boat.setEnd(1, 1);
        boat.setStart(1, 1);

        Point X = boat.getStart();
        Point Y = boat.getEnd();

        assertEquals("computerAircraftCarrier", boat.getName());
        assertEquals(5, boat.getLength());
        assertEquals(1, X.getDown());
        assertEquals(1, X.getAcross());
        assertEquals(1, Y.getDown());
        assertEquals(1, Y.getAcross());
    }

    @Test
    public void TestComputerBattleship() {

        BattleshipModelNormal test = new BattleshipModelNormal();

        //      Battleship
        Ship boat = test.getShipByID("computerBattleship");
        boat.setEnd(1, 1);
        boat.setStart(1, 1);

        Point X = boat.getStart();
        Point Y = boat.getEnd();

        assertEquals("computerBattleship", boat.getName());
        assertEquals(4, boat.getLength());
        assertEquals(1, X.getDown());
        assertEquals(1, X.getAcross());
        assertEquals(1, Y.getDown());
        assertEquals(1, Y.getAcross());

    }

    @Test
    public void TestComputerSubmarine() {

        BattleshipModelNormal test = new BattleshipModelNormal();

        //      Submarine
        Ship boat = test.getShipByID("computerSubmarine");
        boat.setEnd(1, 1);
        boat.setStart(1, 1);

        Point X = boat.getStart();
        Point Y = boat.getEnd();

        assertEquals("computerSubmarine", boat.getName());
        assertEquals(3, boat.getLength());
        assertEquals(1, X.getDown());
        assertEquals(1, X.getAcross());
        assertEquals(1, Y.getDown());
        assertEquals(1, Y.getAcross());

    }

    @Test
    public void TestComputerCruiser() {

        BattleshipModelNormal test = new BattleshipModelNormal();

        //      Cruiser
        Ship boat = test.getShipByID("computerCruiser");
        boat.setEnd(1, 1);
        boat.setStart(1, 1);

        Point X = boat.getStart();
        Point Y = boat.getEnd();

        assertEquals("computerCruiser", boat.getName());
        assertEquals(3, boat.getLength());
        assertEquals(1, X.getDown());
        assertEquals(1, X.getAcross());
        assertEquals(1, Y.getDown());
        assertEquals(1, Y.getAcross());

    }

    @Test
    public void TestComputerDestroyer() {

        BattleshipModelNormal test = new BattleshipModelNormal();

        //      Destroyer
        Ship boat = test.getShipByID("computerDestroyer");
        boat.setEnd(1, 1);
        boat.setStart(1, 1);

        Point X = boat.getStart();
        Point Y = boat.getEnd();

        assertEquals("computerDestroyer", boat.getName());
        assertEquals(2, boat.getLength());
        assertEquals(1, X.getDown());
        assertEquals(1, X.getAcross());
        assertEquals(1, Y.getDown());
        assertEquals(1, Y.getAcross());

    }

    @Test
    public void TestUnknownShip(){

        BattleshipModelNormal test = new BattleshipModelNormal();

        //      Unknown ship
        Ship boat = test.getShipByID("PizzaBoat");

        assertEquals("PizzaShip", boat.getName());
        assertEquals(0, boat.getLength());

    }

    @Test
    public void TestGoodPoint(){
        BattleshipModel test = new BattleshipModel();
        Point TestPoint = new Point(5,6);

        assertEquals(true, test.addPointtoArray(TestPoint,test.getPlayerHits()));

        TestPoint = new Point(11,-1);
        assertEquals(false, test.addPointtoArray(TestPoint,test.getPlayerMisses()));
        assertEquals(false, test.addPointtoArray(TestPoint,test.getComputerMisses()));
        assertEquals(false, test.addPointtoArray(TestPoint,test.getComputerHits()));

        assertEquals(5,test.getPlayerHits().get(0).getAcross());
        assertEquals(6,test.getPlayerHits().get(0).getDown());
    }

}

