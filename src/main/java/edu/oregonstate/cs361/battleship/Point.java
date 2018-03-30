package edu.oregonstate.cs361.battleship;

/**
 * Created by TrevorSpear on 1/30/17.
 */
public class Point {

    //              Member variables
    int Across;
    int Down;

    //              Constructor
    public Point() {
        setAcross(0);
        setDown(0);
    }

    public Point(int X_Coordinate, int Y_Coordinate) {
        setAcross(X_Coordinate);
        setDown(Y_Coordinate);
    }

    //              Getters and Setters
    public int getAcross() {
        return Across;
    }

    public void setAcross(int across) {
        Across = across;
    }

    public int getDown() {
        return Down;
    }

    public void setDown(int down) {
        Down = down;
    }

}
