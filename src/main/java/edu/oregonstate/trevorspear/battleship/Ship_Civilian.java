package edu.oregonstate.trevorspear.battleship;

import static edu.oregonstate.trevorspear.battleship.Main.alreadyShot;
import static edu.oregonstate.trevorspear.battleship.Main.Hit;

/**
 * Created by TrevorSpear on 2/28/17.
 */

public class Ship_Civilian extends Ship {

    //          Constructor
    public Ship_Civilian (String name) {

        setName(name);

        // size is based on name of ship, if unrecognized ship type the function sets size to 0
        if (name.toLowerCase().contains("clipper")) {
            setLength(3);
            setHealth(3);

            start = new Point();
            end = new Point();
        }

        else if (name.toLowerCase().contains("dinghy")) {
            setLength(1);
            setHealth(1);

            start = new Point();
            end = new Point();
        }

        else {
            setLength(0);
            setHealth(0);

            start = new Point();
            end = new Point();
        }

    }

    public BattleshipModelUpdated HitShip(BattleshipModelUpdated model, int i, Ship[] Array,  Point FireSpot, boolean player){

        if ( Hit( Array[i].getStart(), Array[i].getEnd(), FireSpot)) {

            if( Array[i].getStart().getDown() == Array[i].getEnd().getDown() ){
                int y = Array[i].getStart().getDown();

                for ( int x = Array[i].getStart().getAcross(); x <= Array[i].getEnd().getAcross(); x++ ){
                    Point Shot = new Point(x, y);

                    if( !(alreadyShot(Shot, model, player)) ){

                        if(player){
                            model.addPointtoArray( Shot, model.getComputerHits() );
                        }else{
                            model.addPointtoArray( Shot, model.getPlayerHits() );
                        }
                    }
                }

            } else if ( Array[i].getStart().getAcross() == Array[i].getEnd().getAcross() ) {
                int x = Array[i].getStart().getAcross();

                for ( int y = Array[i].getStart().getDown(); y <= Array[i].getEnd().getDown(); y++ ) {
                    Point Shot = new Point( x, y);

                    if( !(alreadyShot(Shot, model, player)) ){

                        if(player){
                            model.addPointtoArray( Shot, model.getComputerHits() );
                        }else{
                            model.addPointtoArray( Shot, model.getPlayerHits() );
                        }
                    }
                }
            }

            model.getShipByID(Array[i].getName()).setHealth(0);

        }

        return model; // points given are not horizontal or vertical and not valid, can't hit diagonally
    }
}
