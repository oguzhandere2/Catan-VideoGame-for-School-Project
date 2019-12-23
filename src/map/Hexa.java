package map;

public class Hexa {

    private Location location;
    private boolean withAngel = false;
    private int no = 0;
    private final HexaType type;
    private boolean withRobber = false;

    public Hexa(int x, int y, int n, HexaType type) {
        location = new Location(x, y);
        no = n;
        this.type = type;
    }

    public Hexa(HexaType type, int no) {
        this.type = type;
        this.no = no;
    }

    public Hexa(HexaType type, boolean bool) {
        withRobber = bool;
        this.type = type;
    }

    public Hexa(boolean bool, HexaType type) {
        withAngel = bool;
        this.type = type;
    }

    public void setLocation(int x, int y) {
        location = new Location(x, y);
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setWithRobber(boolean withRobber) {
        this.withRobber = withRobber;
    }

    public void setWithAngel(boolean withAngel) {
        this.withAngel = withAngel;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int number) {
        no = number;
    }

    public HexaType getType() {
        return type;
    }

    public void putRobber(boolean b) {
        withRobber = b;
    }

    public boolean isWithRobber() {
        return withRobber;
    }

    public void putAngel(boolean b) {
        withAngel = b;
    }

    public boolean isWithAngel(){
        return withAngel;
    }
}
