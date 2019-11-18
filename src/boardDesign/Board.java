package boardDesign;

import components.Angel;
import components.Robber;

import java.util.ArrayList;

public class Board {
    private Robber robber;
    private Angel angel;
    private ArrayList<Hexa> hexas;

    public Board(Angel angel) {
        this.angel = angel;
        hexas = new ArrayList<>();

    }

    public Board(Robber robber) {
        this.robber = robber;
    }

    public Robber getRobber() {
        return robber;
    }

    public Angel getAngel() {
        return angel;
    }

    public ArrayList<Hexa> getHexas() {
        return hexas;
    }
}
