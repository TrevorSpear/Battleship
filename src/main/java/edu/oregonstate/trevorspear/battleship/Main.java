package edu.oregonstate.trevorspear.battleship;

import com.google.gson.Gson;
import spark.Request;
import java.util.List;
import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {

        //This will allow us to server the static pages such as index.html, app.js, etc.
        staticFiles.location("/public");

        //This will listen to GET requests to /model and return a clean new model
        get("/model/:Version", (req, res) -> newModel( req ));

        //This will listen to POST requests and expects to receive a game model, as well as location to fire to
        post("/fire/:Version/:row/:col/:hard", (req, res) -> fireAt( req ));
        //This will handle the scan feature
        post("/scan/:Version/:row/:col/:hard", (req, res) -> scan( req ));

        //This will listen to POST requests and expects to receive a game model, as well as location to place the ship
        post("/placeShip/:Version/:id/:row/:col/:orientation/:hard", (req, res) -> placeShip( req ));
    }



    //-------------------------------------------------------------------------------
    //
    // Model
    //
    //-------------------------------------------------------------------------------



    //This function should return a new model
    private static String newModel(Request req) {

        if( req.params("Version").equals("Updated") ) {

            // make new model, make gson object, convert model to json using gson
            BattleshipModelUpdated game = new BattleshipModelUpdated();
            Gson gson = new Gson();
            return gson.toJson( game );

        }else{

            // make new model, make gson object, convert model to json using gson
            BattleshipModelNormal game = new BattleshipModelNormal();
            Gson gson = new Gson();
            return gson.toJson( game );

        }
    }



    //This function should accept an HTTP request and deserialize it into an actual Java object.
    private static BattleshipModel getModelFromReq(Request req){

        String data = req.body();
        Gson gson = new Gson();

        if( req.params("Version").equals("Updated") ) {

            return gson.fromJson(data, BattleshipModelUpdated.class);

        }else{

            return gson.fromJson(data, BattleshipModelNormal.class);

        }

    }



    //-------------------------------------------------------------------------------
    //
    // Placing ship
    //
    //-------------------------------------------------------------------------------



    //This controller should take a json object from the front end, and place the ship as requested, and then return the object.
    private static String placeShip(Request req) {

        if( req.params("Version").equals("Updated") ) {

            BattleshipModelUpdated model = (BattleshipModelUpdated) getModelFromReq( req );

            model.resetArrayUpdated( model );
            model = model.PlaceShip( model, req );
            model.resetArrayUpdated( model );

            Gson gson = new Gson();
            return gson.toJson( model );

        }else{

            BattleshipModelNormal model = (BattleshipModelNormal) getModelFromReq( req );

            model.resetArrayNormal( model );
            model = model.PlaceShip( model, req );
            model.resetArrayNormal( model );

            Gson gson = new Gson();
            return gson.toJson( model );

        }
    }



    //-------------------------------------------------------------------------------
    //
    // Fire
    //
    //-------------------------------------------------------------------------------



    //Similar to placeShip, but with firing.
    private static String fireAt(Request req) {

        if(req.params("Version").equals("Updated")) {

            // Generate model from json, get coordinates from fire request
            BattleshipModelUpdated model = (BattleshipModelUpdated) getModelFromReq( req );

            model.resetArrayUpdated( model );
            model = model.Fire( model, req );
            model.resetArrayUpdated( model );

            Gson gson = new Gson();
            return gson.toJson( model );

        }else{

            // Generate model from json, get coordinates from fire request
            BattleshipModelNormal model = (BattleshipModelNormal) getModelFromReq( req );

            model.resetArrayNormal( model );
            model = model.Fire( model, req );
            model.resetArrayNormal( model );

            Gson gson = new Gson();
            return gson.toJson( model );

        }
    }



    //Scans the area for ships
    private static String scan(Request req) {

        // Generate model from json, get coordinates from fire request
        BattleshipModelUpdated model = (BattleshipModelUpdated) getModelFromReq( req );

        model.resetArrayUpdated( model );
        model = model.Scan( model, req );
        model.resetArrayUpdated( model );

        model = model.resetArrayUpdated( model );
        Gson gson = new Gson();
        return gson.toJson( model );
    }



    //-------------------------------------------------------------------------------
    //
    // Helper functions
    //
    //-------------------------------------------------------------------------------



    //Checks a list for either player/computer and sees if the same point is already in the list
    static boolean alreadyShot(Point shotPoint, BattleshipModel model, boolean player){
        List<Point> checkHits;
        List<Point> checkMisses;

        int sizeHits;
        int sizeMisses;

        //if player
        if(player) {
            checkHits = model.getComputerHits();        //Grabs the point list for player
            checkMisses = model.getComputerMisses();

            sizeHits = model.getComputerHits().size();
            sizeMisses = model.getComputerMisses().size();

        }else{
            checkHits = model.getPlayerHits();          //Grabs the point list for computer
            checkMisses = model.getPlayerMisses();

            sizeHits = model.getPlayerHits().size();
            sizeMisses = model.getPlayerMisses().size();
        }

        for(int i = 0; i < sizeHits; i++){      //checks the Hit list for the same point
            if(shotPoint.getAcross() == checkHits.get(i).getAcross() && shotPoint.getDown() == checkHits.get(i).getDown()){
                return true;
            }
        }

        for(int i = 0; i < sizeMisses; i++){    //checks the Hit list for the same point
            if(shotPoint.getAcross() == checkMisses.get(i).getAcross() && shotPoint.getDown() == checkMisses.get(i).getDown() ){
                return true;
            }
        }

        return false;
    }



    //Returns true if the shot point is between shipStart and shipEnd otherwise return false
    static boolean Hit(Point shipStart, Point shipEnd, Point shotPoint){

        if(shipStart.getDown() == shipEnd.getDown()){     // if start and end on same y coordinate, ship is horizontal
            int y = shipStart.getDown();

            for (int x = shipStart.getAcross(); x <= shipEnd.getAcross(); x++){  // loop from left to right of ship position
                if(x == shotPoint.getAcross() && y == shotPoint.getDown())
                    return true;   // if the coordinates of current point match shot, you hit!
            }

        } else if (shipStart.getAcross() == shipEnd.getAcross()) { // if start and end on same x coordinate, ship is vertical
            int x = shipStart.getAcross();

            for (int y = shipStart.getDown(); y <= shipEnd.getDown(); y++) {
                if (x == shotPoint.getAcross() && y == shotPoint.getDown())
                    return true;   // if the coordinates of current point match shot, you hit!
            }
        }

        return false; // points given are not horizontal or vertical and not valid, can't hit diagonally
    }
}