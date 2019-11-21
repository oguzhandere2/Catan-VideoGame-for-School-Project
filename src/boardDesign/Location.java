package boardDesign;

import java.util.ArrayList;

public class Location {
    //private int x;
    //private int y;
    private int hexa;
    private int part;

    public Location(int hexa, int part) {
        this.hexa = hexa;
        this.part = part;
    }


    /*public boardDesign.Location(int x, int y) {
        this.x = x;
        this.y = y;

    }

    public ArrayList<boardDesign.Location> getAdjacents() {
        return adjacents;
    }

    public void setAdjacents(ArrayList<boardDesign.Location> adjacents) {
        this.adjacents = adjacents;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }*/
}
