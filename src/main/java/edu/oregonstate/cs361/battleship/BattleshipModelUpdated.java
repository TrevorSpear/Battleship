package edu.oregonstate.cs361.battleship;

import spark.Request;
import static edu.oregonstate.cs361.battleship.Main.Hit;
import static edu.oregonstate.cs361.battleship.Main.alreadyShot;
import java.util.ArrayList;
import java.util.List;

public class BattleshipModelUpdated extends BattleshipModel {

    //                  Member variables
    //private Ship playerAircraftCarrier;
    private Ship_Stealth playerBattleship;
    private Ship_Stealth playerSubmarine;
    private Ship_Civilian playerClipper;
    private Ship_Civilian playerDinghy;


    //private Ship computerAircraftCarrier;
    private Ship_Stealth computerBattleship;
    private Ship_Stealth computerSubmarine;
    private Ship_Civilian computerClipper;
    private Ship_Civilian computerDinghy;

    private boolean scanned;

    //                  Constructor
    public BattleshipModelUpdated() {

        playerAircraftCarrier = new Ship("playerAircraftCarrier");
        playerBattleship = new Ship_Stealth("playerBattleship");
        playerSubmarine = new Ship_Stealth("playerSubmarine");
        playerClipper = new Ship_Civilian("playerClipper");
        playerDinghy = new Ship_Civilian("playerDinghy");


        computerAircraftCarrier = new Ship("computerAircraftCarrier");
        computerBattleship = new Ship_Stealth("computerBattleship");
        computerSubmarine = new Ship_Stealth("computerSubmarine");
        computerClipper = new Ship_Civilian("computerClipper");
        computerDinghy = new Ship_Civilian("computerDinghy");

        playerHits = new ArrayList<Point>();
        playerMisses = new ArrayList<Point>();
        computerHits = new ArrayList<Point>();
        computerMisses = new ArrayList<Point>();

        playerArray[1] = playerBattleship;
        playerArray[2] = playerSubmarine;
        playerArray[3] = playerClipper;
        playerArray[4] = playerDinghy;

        computerArray[1] = computerBattleship;
        computerArray[2] = computerSubmarine;
        computerArray[3] = computerClipper;
        computerArray[4] = computerDinghy;

        scanned = false;
    }

    //          Get ship by ID function, assumes user only selects from drop down menu on front end
    public Ship getShipByID(String id) {

        if(id.toLowerCase().contains("computer")) {

            if (id.toLowerCase().contains("aircraftcarrier")) {
                return getComputerAircraftCarrier();

            } else if (id.toLowerCase().contains("clipper")) {
                return getComputerClipper();

            } else if (id.toLowerCase().contains("dinghy")) {
                return getComputerDinghy();

            } else if (id.toLowerCase().contains("battleship")) {
                return getComputerBattleship();

            } else if (id.toLowerCase().contains("submarine") ) {
                return getComputerSubmarine();

            }

        } else {

            if (id.toLowerCase().contains("aircraftcarrier")) {
                return getPlayerAircraftCarrier();

            } else if (id.toLowerCase().contains("clipper")) {
                return getPlayerClipper();

            } else if (id.toLowerCase().contains("dinghy")) {
                return getPlayerDinghy();

            } else if (id.toLowerCase().contains("battleship")) {
                return getPlayerBattleship();

            } else if (id.toLowerCase().contains("submarine") ) {
                return getPlayerSubmarine();

            }

        }

        // DEFAULTS TO THIS IF ID FORMAT IS INCORRECT:
        Ship PizzaShip = new Ship("PizzaShip");
        PizzaShip.setName("PizzaShip");
        return PizzaShip;
    }

    public Ship_Stealth getPlayerBattleship() {
        return playerBattleship;
    }

    public Ship_Stealth getPlayerSubmarine() {
        return playerSubmarine;
    }

    public Ship_Civilian getPlayerClipper() {
        return playerClipper;
    }

    public Ship_Civilian getPlayerDinghy() {
        return playerDinghy;
    }

    public Ship_Stealth getComputerBattleship() {
        return computerBattleship;
    }

    public Ship_Stealth getComputerSubmarine() {
        return computerSubmarine;
    }

    public Ship_Civilian getComputerClipper() {
        return computerClipper;
    }

    public Ship_Civilian getComputerDinghy() {
        return computerDinghy;
    }

    public void setScanned(boolean scanned) {
            this.scanned = scanned;
    }

    public BattleshipModelUpdated resetArrayUpdated(BattleshipModelUpdated model) {

        playerArray[0] = model.getPlayerAircraftCarrier();
        playerArray[1] = model.getPlayerBattleship();
        playerArray[2] = model.getPlayerSubmarine();
        playerArray[3] = model.getPlayerClipper();
        playerArray[4] = model.getPlayerDinghy();

        computerArray[0] = model.getComputerAircraftCarrier();
        computerArray[1] = model.getComputerBattleship();
        computerArray[2] = model.getComputerSubmarine();
        computerArray[3] = model.getComputerClipper();
        computerArray[4] = model.getComputerDinghy();

        return model;
    }



    //-------------------------------------------------------------------------------
    //
    // Place Ship
    //
    //-------------------------------------------------------------------------------



    public BattleshipModelUpdated PlaceShip(BattleshipModelUpdated model, Request req){

        String id = req.params("id");  //name of what ship with which player in front of it
        String orientation = req.params("orientation"); //horizontal/vertical

        List PlayerFireMiss = model.getPlayerMisses();
        List PlayerFireHit = model.getPlayerHits();


        if (!PlayerFireHit.isEmpty() || !PlayerFireMiss.isEmpty()) {//list is empty
            return model;
        }

        int rows = Integer.parseInt(req.params("row"));
        int column = Integer.parseInt(req.params("col"));

        model.getShipByID(id).setEnd(0, 0);
        model.getShipByID(id).setStart(0, 0);

        int size = model.getShipByID(id).getLength();
        int stop = 0;
        Point cord = new Point();

        if (orientation.equals("horizontal") && (rows + size - 1) < 11 && rows > 0 && column < 11 && column > 0) {
            for (int i = rows; i < (rows + size); i++) {
                cord.setAcross(i);
                cord.setDown(column);

                //if ship lands on another ship then
                for (int l = 0; l < 5; l++) {
                    if (Hit( model.playerArray[l].getStart(), model.playerArray[l].getEnd(), cord)) {
                        stop = 1;
                    }
                }
            }

            if (stop == 0) {
                model.getShipByID(id).setStart(rows, column);
                model.getShipByID(id).setEnd(rows + size - 1, column);
            }

        } else if (orientation.equals("vertical") && rows < 11 && rows > 0 && column + size - 1 < 11 && column > 0) {
            for (int k = column; k < (column + size); k++) {
                cord.setAcross(rows);
                cord.setDown(k);

                //if ship lands on another ship then
                for (int l = 0; l < 5; l++) {
                    if (Hit( model.playerArray[l].getStart(), model.playerArray[l].getEnd(), cord)) {
                        stop = 1;
                    }
                }
            }

            if (stop == 0) {    //Sets the start and end location of the ship
                model.getShipByID(id).setStart(rows, column);
                model.getShipByID(id).setEnd(rows, column + size - 1);
            }
        }

        if(stop == 0) {
            if (req.params("hard").equals("Hard")) {
                model = placeShipHard(model);
            } else{
                model = placeShipEasy(model);
            }
        }

        return model;
    }



    //Places the ships at random locations
    public static BattleshipModelUpdated placeShipHard(BattleshipModelUpdated model) {

        model = model.resetArrayUpdated(model);
        Point computerStart;
        Point computerEnd;
        Point cord = new Point(0,0);

        int computer_x, computer_y, horizontal;

        for(int i = 0; i < 5; i++) {
            int stop = 0;

            int computer_length_increase = 0;
            int computerLength = model.getShipByID( model.computerArray[i].getName()).getLength() - 1;

            model.getShipByID( model.computerArray[i].getName() ).setStart(0, 0);
            model.getShipByID( model.computerArray[i].getName() ).setEnd(0, 0);

            model = model.resetArrayUpdated(model);

            int movingPoint = 0, stoppedPoint = 0;

            while (stop == 0) {
                //Gets a random starting location and horizontal/vertical
                computer_x = (int) (Math.random() * 10 + 1);
                computer_y = (int) (Math.random() * 10 + 1);
                horizontal = (int) (Math.random() * 2 + 1);

                computerStart = new Point(computer_x, computer_y);
                computerEnd = new Point(0, 0);
                stop = 1;

                if (horizontal == 1 && computer_x + computerLength < 11) { //horizontal
                    computerEnd = new Point(computer_x + computerLength, computer_y);
                    computer_length_increase = computerEnd.getAcross();

                    movingPoint = computer_x;
                    stoppedPoint = computer_y;

                } else if (horizontal == 2 && computer_y + computerLength < 11) { //vertical
                    computerEnd = new Point(computer_x, computer_y + computerLength);
                    computer_length_increase = computerEnd.getDown();

                    movingPoint = computer_y;
                    stoppedPoint = computer_x;

                } else {
                    stop = 0;
                }

                if (stop == 1) {
                    for (movingPoint = movingPoint - 1; movingPoint < (computer_length_increase + 2); movingPoint++) {
                        if (horizontal == 1) {  //Horizontal
                            cord.setAcross(movingPoint);
                            cord.setDown(stoppedPoint);

                        } else {                //Vertical
                            cord.setAcross(stoppedPoint);
                            cord.setDown(movingPoint);
                        }

                        //if ship lands on another ship then
                        for (int j = 0; j < 5; j++) {
                            if ( Hit( model.computerArray[j].getStart(), model.computerArray[j].getEnd(), cord)) {
                                stop = 0;
                            }
                        }
                    }

                    if (stop == 1) {
                        model.getShipByID( model.computerArray[i].getName()).setStart(computerStart.getAcross(), computerStart.getDown());
                        model.getShipByID( model.computerArray[i].getName()).setEnd(computerEnd.getAcross(), computerEnd.getDown());
                        model = model.resetArrayUpdated(model);
                    }
                }
            }
        }

        return model;
    }

    public static BattleshipModelUpdated placeShipEasy(BattleshipModelUpdated model) {

        for(int i = 0; i < 5; i++) {
            model = model.resetArrayUpdated(model);
            int computerLength = model.getShipByID( model.computerArray[i].getName()).getLength() - 1;

            model.getShipByID( model.computerArray[i].getName()).setStart(i + 1, i + 1);
            model.getShipByID( model.computerArray[i].getName()).setEnd(i + computerLength + 1, i + 1);
        }

        return model;
    }



    //-------------------------------------------------------------------------------
    //
    // Fire
    //
    //-------------------------------------------------------------------------------



    public BattleshipModelUpdated Fire(BattleshipModelUpdated model, Request req){

        Point FireSpot = new Point( Integer.parseInt(req.params("row") ), Integer.parseInt( req.params("col") ));

        for(int i = 0; i < 5; i++){
            if( model.playerArray[i].getHealth() == 0){
                model.getShipByID(model.playerArray[i].getName()).setHealth(-1);
            }
            if( model.computerArray[i].getHealth() == 0){
                model.getShipByID(model.computerArray[i].getName()).setHealth(-1);
            }
        }

        //Player has shot at this place already
        if( alreadyShot( FireSpot, model, true) ){
            return model;
        }

        int sizeOfHitArray = model.getPlayerHits().size();

        // The following branch tree checks if a point fired at
        // BY A PLAYER has hit a COMPUTER ship and adds the point to the array of hits if so
        for (int i = 0; i < 5; i++) {
            model = model.playerArray[i].HitShip( model, i, model.computerArray, FireSpot, true);
        }

        if( sizeOfHitArray == model.getPlayerHits().size() ){
            model.addPointtoArray( FireSpot, model.getComputerMisses() );
        }

        if( req.params("hard").equals("Hard") ){
            model = model.computerFireHard( model );

        }else{
            model = model.computerFireEasy( model );
        }

        return model;

    }



    public BattleshipModelUpdated computerFireHard(BattleshipModelUpdated model) {

        // Create two random coordinates for computer to shoot at and make a point object of them
        model = model.resetArrayUpdated(model);
        Point FireSpotComputer;
        int shootX, shootY, sizeOfHitArray;

        do{ // Keeps getting random location until it hasn't fired at that spot
            shootX = (int )(Math.random() * 10 + 1);
            shootY = (int )(Math.random() * 10 + 1);
            FireSpotComputer = new Point(shootX, shootY);
        } while ( alreadyShot( FireSpotComputer, model,false));

        for(int i = 0; i < model.getPlayerHits().size(); i++){
            Point checkPoint = model.getPlayerHits().get(i);

            for (int k = 0; k < 5; k++) {
                if ( Hit( model.playerArray[k].getStart(), model.playerArray[k].getEnd(), checkPoint)) { //Finds the ship
                    if( model.playerArray[k].getHealth() > 0){ //Makes sure that the ship hasn't been sunk before (user knows this)

                        Point[] LRDU = new Point[] {
                                new Point(checkPoint.getAcross() - 1, checkPoint.getDown()),    //Left
                                new Point(checkPoint.getAcross() + 1, checkPoint.getDown()),    //Right
                                new Point(checkPoint.getAcross(), checkPoint.getDown() - 1),    //Down
                                new Point(checkPoint.getAcross(), checkPoint.getDown() + 1)     //Up
                        };

                        for(int j = 0; j < 4; j++){
                            if( !alreadyShot( LRDU[j], model,false) && LRDU[j].getAcross() > 0 && LRDU[j].getAcross() < 11
                                    && LRDU[j].getDown() > 0 && LRDU[j].getDown() < 11) {
                                FireSpotComputer = new Point(LRDU[j].getAcross(), LRDU[j].getDown());
                                break;
                            }
                        }
                    }
                }
            }
        }

        sizeOfHitArray = model.getPlayerHits().size();

        // The following branch tree checks if a point fired at
        // BY A PLAYER has hit a COMPUTER ship and adds the point to the array of hits if so
        for (int i = 0; i < 5; i++) {
            model = model.computerArray[i].HitShip( model, i, model.playerArray, FireSpotComputer, false);
        }

        //If the size of the hit list doesn't increase (no hits were added) then add one to miss
        if(sizeOfHitArray == model.getPlayerHits().size()){
            model.addPointtoArray(FireSpotComputer, model.getPlayerMisses());
        }

        return model;
    }



    public BattleshipModelUpdated computerFireEasy(BattleshipModelUpdated model) {

        // Create two random coordinates for computer to shoot at and make a point object of them
        model = model.resetArrayUpdated(model);
        Point FireSpotComputer = new Point( 0, 0);
        int sizeOfHitArray;

        // Keeps getting random location until it hasn't fired at that spot
        for(int x = 1; x < 11; x++){
            for(int y = 1; y < 11; y++){
                FireSpotComputer = new Point( x, y);

                if(!alreadyShot( FireSpotComputer, model,false)){
                    x = 12;
                    break;
                }
            }
        }

        sizeOfHitArray = model.getPlayerHits().size();

        // The following branch tree checks if a point fired at
        // BY A PLAYER has hit a COMPUTER ship and adds the point to the array of hits if so
        for (int i = 0; i < 5; i++) {
            model = model.computerArray[i].HitShip( model, i, model.playerArray, FireSpotComputer, false);
        }

        //If the size of the hit list doesn't increase (no hits were added) then add one to miss
        if(sizeOfHitArray == model.getPlayerHits().size()){
            model.addPointtoArray(FireSpotComputer, model.getPlayerMisses());
        }

        return model;
    }

    public BattleshipModelUpdated Scan( BattleshipModelUpdated model, Request req ){

        int row = Integer.parseInt( req.params("row") );
        int col = Integer.parseInt( req.params("col") );

        // Make point object from coordinates
        Point[] FireSpot = new Point[] {new Point(row, col), new Point((row-1), col), new Point((row+1), col), new Point(row,(col-1)), new Point(row, (col+1))};

        // Resets the player and computer ships from current model into the list
        model = model.resetArrayUpdated(model);

        //Won't scan unless all ships are placed down
        for(int i = 0; i < 5; i++){
            if( model.playerArray[i].getStart().getAcross() < 1){
                return model;
            }
        }

        //The view says that the ship sunk if it has 0 health so what this does is change it so that the view doesn't have to.
        for(int i = 0; i < 5; i++){
            if( model.playerArray[i].getHealth() == 0){
                model.getShipByID( model.playerArray[i].getName() ).setHealth(-1);
            }
            if( model.computerArray[i].getHealth() == 0){
                model.getShipByID( model.computerArray[i].getName() ).setHealth(-1);
            }
        }

        // The following branch tree checks if a point fired at
        // BY A PLAYER has hit a COMPUTER ship and adds the point to the array of hits if so
        for(int j = 0; j < 5; j++) {
            for(int i = 0; i < 5; i++) {
                if (model.computerArray[j].ScanShip(j, model.computerArray, FireSpot[i])){
                    model.setScanned(true);
                }
            }
        }

        //returns the model with computer having fired either hard or easy.
        if( req.params("hard").equals("Hard") ) {
            model = model.computerFireHard(model);
        }else{
            model = model.computerFireEasy(model);
        }

        return model;
    }
}
