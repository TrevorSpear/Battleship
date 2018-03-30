package edu.oregonstate.cs361.battleship;

import static edu.oregonstate.cs361.battleship.Main.Hit;

/**
 * Created by TrevorSpear on 2/28/17.
 */
public class Ship_Stealth extends Ship {

    //          Constructor
    public Ship_Stealth(String name) {

        setName(name);

        // size is based on name of ship, if unrecognized ship type the function sets size to 0
        if (name.toLowerCase().contains("battleship")) {
            setLength(4);
            setHealth(4);

            start = new Point();
            end = new Point();


        }else if (name.toLowerCase().contains("submarine")) {
            setLength(3);
            setHealth(3);

            start = new Point();
            end = new Point();


        }else {
            setLength(0);
            setHealth(0);

            start = new Point();
            end = new Point();
        }
    }

    public Boolean ScanShip( int i, Ship[] Array,  Point FireSpot ){
        return false;
    }
}

