package edu.oregonstate.cs361.battleship;

import spark.Request;
import java.util.ArrayList;
import java.util.List;
import static edu.oregonstate.cs361.battleship.Main.Hit;

public class BattleshipModel {

    //                  Member variables
    protected Ship[] playerArray;
    protected Ship playerAircraftCarrier;

    protected Ship[] computerArray;
    protected Ship computerAircraftCarrier;

    //protected boolean hard;

    // all ships for player or computer are collected in these lists
    protected List<Point> playerHits;
    protected List<Point> playerMisses;
    protected List<Point> computerHits;
    protected List<Point> computerMisses;

    //                  Constructor
    public BattleshipModel () {

        playerAircraftCarrier = new Ship("playerAircraftCarrier");

        computerAircraftCarrier = new Ship("computerAircraftCarrier");

        playerHits = new ArrayList<Point>();
        playerMisses = new ArrayList<Point>();
        computerHits = new ArrayList<Point>();
        computerMisses = new ArrayList<Point>();

        playerArray = new Ship[5];
        playerArray[0] = playerAircraftCarrier;

        computerArray = new Ship[5];
        computerArray[0] = computerAircraftCarrier;

        //setHard(true);
    }


    //          Add Point object to an array function
    protected boolean addPointtoArray(Point somePoint, List someArray) {

        if (somePoint.getAcross() > 10 || somePoint.getAcross() < 1 || somePoint.getDown() > 10 || somePoint.getDown() < 1)

            return false;

        else {

            someArray.add(somePoint);
            return true;

        }
    }

    //          Getters
    protected Ship getPlayerAircraftCarrier() {
        return playerAircraftCarrier;
    }

    protected Ship getComputerAircraftCarrier() {
        return computerAircraftCarrier;
    }

    public List<Point> getPlayerHits() {
        return playerHits;
    }

    public List<Point> getPlayerMisses() {
        return playerMisses;
    }

    public List<Point> getComputerHits() {
        return computerHits;
    }

    public List<Point> getComputerMisses() {
        return computerMisses;
    }

    public BattleshipModel PlaceShip(BattleshipModel model, Request req){

        String orientation = req.params("orientation"); //horizontal/vertical

        int rows = Integer.parseInt( req.params("row") );   //row #
        int column = Integer.parseInt( req.params("col") ); //col #

        List PlayerFireMiss = model.getPlayerMisses();
        List PlayerFireHit = model.getPlayerHits();

        if(!PlayerFireHit.isEmpty() || !PlayerFireMiss.isEmpty()) {//list is empty
            return model;
        }

        int sizeOfShip = model.playerAircraftCarrier.getLength();
        int stop = 0;
        Point cord = new Point();

        if (orientation.equals("horizontal") && (rows + sizeOfShip - 1) < 11 && rows > 0 && column < 11 && column > 0) {
            for (int i = rows; i < ( rows + sizeOfShip ); i++) {
                cord.setAcross(i);
                cord.setDown(column);

                //if ship lands on another ship then
                if( Hit(model.playerArray[0].getStart(), model.playerArray[0].getEnd(), cord) ){
                    stop = 1;
                }
            }
            //Sets the placement of the ship
            if (stop == 0) {
                model.playerAircraftCarrier.setStart(rows, column);
                model.playerAircraftCarrier.setEnd(rows + sizeOfShip - 1, column);
            }

        } else if (orientation.equals("vertical") && rows < 11 && rows > 0 && column + sizeOfShip - 1 < 11 && column > 0) {
            for (int k = column; k < ( column + sizeOfShip ); k++) {
                cord.setAcross(rows);
                cord.setDown(k);

                //if ship lands on another ship then
                if( Hit(model.playerArray[0].getStart(), model.playerArray[0].getEnd(), cord)){
                    stop = 1;
                }
            }

            if (stop == 0) {
                model.playerAircraftCarrier.setStart(rows, column);
                model.playerAircraftCarrier.setEnd(rows, column + sizeOfShip - 1);
            }
        }

        return model;

    }

    public BattleshipModel Fire(BattleshipModel model, Request req){

        return model;

    }
}
