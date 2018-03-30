package edu.oregonstate.trevorspear.battleship;

import spark.Request;
import static edu.oregonstate.trevorspear.battleship.Main.Hit;
import static edu.oregonstate.trevorspear.battleship.Main.alreadyShot;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TrevorSpear on 2/28/17.
 */

public class BattleshipModelNormal extends BattleshipModel {

    //                  Member variables
    //private Ship playerAircraftCarrier;
    private Ship playerBattleship;
    private Ship playerSubmarine;
    private Ship playerCruiser;
    private Ship playerDestroyer;


    //private Ship computerAircraftCarrier;
    private Ship computerBattleship;
    private Ship computerSubmarine;
    private Ship computerCruiser;
    private Ship computerDestroyer;

    //                  Constructor
    public BattleshipModelNormal() {

        playerAircraftCarrier = new Ship("playerAircraftCarrier");
        playerBattleship = new Ship("playerBattleship");
        playerSubmarine = new Ship("playerSubmarine");
        playerCruiser = new Ship("playerCruiser");
        playerDestroyer = new Ship("playerDestroyer");


        computerAircraftCarrier = new Ship("computerAircraftCarrier");
        computerBattleship = new Ship("computerBattleship");
        computerSubmarine = new Ship("computerSubmarine");
        computerCruiser = new Ship("computerCruiser");
        computerDestroyer = new Ship("computerDestroyer");

        playerHits = new ArrayList<Point>();
        playerMisses = new ArrayList<Point>();
        computerHits = new ArrayList<Point>();
        computerMisses = new ArrayList<Point>();

        playerArray[1] = playerBattleship;
        playerArray[2] = playerSubmarine;
        playerArray[3] = playerCruiser;
        playerArray[4] = playerDestroyer;

        computerArray[1] = computerBattleship;
        computerArray[2] = computerSubmarine;
        computerArray[3] = computerCruiser;
        computerArray[4] = computerDestroyer;
    }

    //          Get ship by ID function, assumes user only selects from drop down menu on front end
    public Ship getShipByID(String id) {

        if(id.toLowerCase().contains("computer")) {

            if (id.toLowerCase().contains("aircraftcarrier")) {
                return getComputerAircraftCarrier();
            }

            else if (id.toLowerCase().contains("battleship")) {
                return getComputerBattleship();
            }

            else if (id.toLowerCase().contains("submarine") ) {
                return getComputerSubmarine();
            }

            else if (id.toLowerCase().contains("cruiser")) {
                return getComputerCruiser();
            }

            else if (id.toLowerCase().contains("destroyer")) {
                return getComputerDestroyer();
            }
        }

        else {

            if (id.toLowerCase().contains("aircraftcarrier")) {
                return getPlayerAircraftCarrier();
            }

            else if (id.toLowerCase().contains("battleship")) {
                return getPlayerBattleship();
            }

            else if (id.toLowerCase().contains("submarine") ) {
                return getPlayerSubmarine();
            }

            else if (id.toLowerCase().contains("cruiser")) {
                return getPlayerCruiser();
            }

            else if (id.toLowerCase().contains("destroyer")) {
                return getPlayerDestroyer();
            }
        }

        // DEFAULTS TO THIS IF ID FORMAT IS INCORRECT:
        Ship PizzaShip = new Ship("PizzaShip");
        PizzaShip.setName("PizzaShip");
        return PizzaShip;
    }

    //              Getters
    public Ship getPlayerBattleship() {
        return playerBattleship;
    }

    public Ship getPlayerSubmarine() {
        return playerSubmarine;
    }

    public Ship getPlayerCruiser() {
        return playerCruiser;
    }

    public Ship getPlayerDestroyer() {
        return playerDestroyer;
    }

    public Ship getComputerBattleship() {
        return computerBattleship;
    }

    public Ship getComputerSubmarine() {
        return computerSubmarine;
    }

    public Ship getComputerCruiser() {
        return computerCruiser;
    }

    public Ship getComputerDestroyer() {
        return computerDestroyer;
    }

    public BattleshipModelNormal resetArrayNormal(BattleshipModelNormal model) {

        model.playerArray[0] = model.getPlayerAircraftCarrier();
        model.playerArray[1] = model.getPlayerBattleship();
        model.playerArray[2] = model.getPlayerSubmarine();
        model.playerArray[3] = model.getPlayerCruiser();
        model.playerArray[4] = model.getPlayerDestroyer();

        model.computerArray[0] = model.getComputerAircraftCarrier();
        model.computerArray[1] = model.getComputerBattleship();
        model.computerArray[2] = model.getComputerSubmarine();
        model.computerArray[3] = model.getComputerCruiser();
        model.computerArray[4] = model.getComputerDestroyer();

        return model;
    }

    public BattleshipModelNormal PlaceShip(BattleshipModelNormal model, Request req){

        String orientation = req.params("orientation"); //horizontal/vertical
        String id = req.params("id");  //name of what ship with which player in front of it

        List PlayerFireMiss = model.getPlayerMisses();
        List PlayerFireHit = model.getPlayerHits();

        if(!PlayerFireHit.isEmpty() || !PlayerFireMiss.isEmpty()) {//list is empty
            return model;
        }

        int rows = Integer.parseInt( req.params("row") );   //row #
        int column = Integer.parseInt( req.params("col") ); //col #

        model.getShipByID(id).setEnd(0, 0);
        model.getShipByID(id).setStart(0, 0);

        model = model.resetArrayNormal( model );

        int sizeOfShip = model.getShipByID(id).getLength();
        int stop = 0;
        Point cord = new Point();

        if (orientation.equals("horizontal") && (rows + sizeOfShip - 1) < 11 && rows > 0 && column < 11 && column > 0) {
            for (int i = rows; i < ( rows + sizeOfShip ); i++) {
                cord.setAcross(i);
                cord.setDown(column);

                //if ship lands on another ship then
                for(int j = 0; j < 5; j++){
                    if( Hit(model.playerArray[j].getStart(), model.playerArray[j].getEnd(), cord) ){
                        stop = 1;
                    }
                }
            }
            //Sets the placement of the ship
            if (stop == 0) {
                model.getShipByID(id).setStart(rows, column);
                model.getShipByID(id).setEnd(rows + sizeOfShip - 1, column);
            }

        } else if (orientation.equals("vertical") && rows < 11 && rows > 0 && column + sizeOfShip - 1 < 11 && column > 0) {
            for (int k = column; k < ( column + sizeOfShip ); k++) {
                cord.setAcross(rows);
                cord.setDown(k);

                //if ship lands on another ship then
                for(int i = 0; i < 5; i++){
                    if( Hit(model.playerArray[i].getStart(), model.playerArray[i].getEnd(), cord)){
                        stop = 1;
                    }
                }
            }

            if (stop == 0) {
                model.getShipByID(id).setStart(rows, column);
                model.getShipByID(id).setEnd(rows, column + sizeOfShip - 1);
            }
        }

        //Computer placement of the same ship
        Point computerStart;
        Point computerEnd;
        int computer_x, computer_y, horizontal;

        //If the player sets down a ship the computer sets down the same ship
        model.getShipByID("computer" + id).setStart( 0, 0);
        model.getShipByID("computer" + id).setEnd( 0, 0);

        //Ship[] CArray = model.resetArrayNormal( model, false);

        int movingPoint = 0, stoppedPoint = 0;

        while(stop == 0) {
            computer_x = (int) (Math.random() * 10 + 1);
            computer_y = (int) (Math.random() * 10 + 1);
            horizontal = (int) (Math.random() * 2 + 1);

            computerStart = new Point(computer_x, computer_y);
            computerEnd = new Point(0, 0);
            stop = 1;

            if (horizontal == 1 && computer_x + sizeOfShip - 1 < 11) { //horizontal

                computerEnd = new Point(computer_x + sizeOfShip - 1, computer_y);
                movingPoint = computer_x;
                stoppedPoint = computer_y;

            } else if (horizontal == 2 && computer_y + sizeOfShip - 1 < 11) { //vertical

                computerEnd = new Point(computer_x, computer_y + sizeOfShip - 1);
                movingPoint = computer_y;
                stoppedPoint = computer_x;

            } else {
                stop = 0;
            }

            if (stop == 1) {
                for (; movingPoint < (computerEnd.getDown()); movingPoint++) {
                    if (horizontal == 1) {
                        cord.setAcross(movingPoint);
                        cord.setDown(stoppedPoint);

                    } else {
                        cord.setAcross(stoppedPoint);
                        cord.setDown(movingPoint);
                    }

                    //if ship lands on another ship then
                    for(int i = 0; i < 5; i++){
                        if( Hit( model.computerArray[i].getStart(), model.computerArray[i].getEnd(), cord)){
                            stop = 0;
                        }
                    }
                }

                //Sets placement of the ship
                if(stop == 1) {
                    model.getShipByID("computer" + id).setStart( computerStart.getAcross(), computerStart.getDown());
                    model.getShipByID("computer" + id).setEnd( computerEnd.getAcross(), computerEnd.getDown());
                }
            }
        }

        return model;

    }

    public BattleshipModelNormal Fire(BattleshipModelNormal model, Request req){

        // Make point object from coordinates
        Point FireSpot = new Point(Integer.parseInt( req.params("row") ) , Integer.parseInt( req.params("col") ) );

        // Grab player and computer ships from current model
        model = model.resetArrayNormal( model );

        //Sets the health of sunk ships to -1
        //So that the view doesn't keep saying it sunk
        for (int i = 0; i < 5; i++) {
            if ( model.playerArray[i].getHealth() == 0) {
                model.getShipByID( model.playerArray[i].getName()).setHealth(-1);
            }
            if ( model.computerArray[i].getHealth() == 0) {
                model.getShipByID( model.computerArray[i].getName()).setHealth(-1);
            }
        }

        //Player has shot at this place already
        if( alreadyShot( FireSpot, model, true) ){
            return model;
        }

        //Won't fire unless all ships are placed down
        for(int i = 0; i < 5; i++){
            if( model.playerArray[i].getStart().getAcross() < 1){
                return model;
            }
        }

        int j = 0;

        // The following branch tree checks if a point fired at
        // BY A PLAYER has hit a COMPUTER ship and adds the point to the array of hits if so
        for( int i = 0; i < 5; i++){
            if( Hit( model.computerArray[i].getStart(), model.computerArray[i].getEnd(), FireSpot ) ){
                model.addPointtoArray(FireSpot, model.getComputerHits());
                model.getShipByID( model.computerArray[i].getName()).setHealth( model.computerArray[i].getHealth()-1);
                j = 1;
            }
        }

        if(j == 0){
            model.addPointtoArray(FireSpot, model.getComputerMisses());
        }

        // Create two random coordinates for computer to shoot at and make a point object of them
        int shootX, shootY;
        Point FireSpotComputer;

        do {
            shootX = (int )(Math.random() * 10 + 1);
            shootY = (int )(Math.random() * 10 + 1);
            FireSpotComputer = new Point(shootX, shootY);
        } while( alreadyShot( FireSpotComputer, model,false) );

        j = 0;

        // Following branch tree checks if a point fired at BY THE COMPUTER has hit a PLAYER ship
        // And adds the point to the array of hits if so
        for(int i = 0; i < 5; i++){
            if( Hit( model.playerArray[i].getStart(), model.playerArray[i].getEnd(), FireSpotComputer ) ){
                model.addPointtoArray(FireSpotComputer, model.getPlayerHits());
                model.getShipByID( model.playerArray[i].getName()).setHealth( model.playerArray[i].getHealth()-1);
                j = 1;
            }
        }

        //if player missed
        if(j == 0){
            model.addPointtoArray(FireSpotComputer, model.getPlayerMisses());
        }

        return model;

    }

}
