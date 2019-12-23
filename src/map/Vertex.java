package map;
import java.util.ArrayList;
public class Vertex extends Location{

    private final int place;

    public Vertex(int x, int y, int place) {
        super(x, y);
        this.place = place;
    }

    public int getPlace(){
        return place;
    }

}
