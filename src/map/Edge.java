package map;

public class Edge extends Location {
    private final int place;
    public Edge(int x, int y, int place) {
        super(x,y);
        this.place = place;
    }

    public int getPlace(){
        return place;
    }

}
