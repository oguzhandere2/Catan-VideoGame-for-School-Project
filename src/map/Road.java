package map;
import game.Player;

public class Road {
    private boolean visited = false;
    private Edge location;
    private Player player = null;


    public Road(int x, int y, int o) {
        location = new Edge(x, y, o);
    }

    public void visit() {
        visited = true;
    }

    public Edge getLocation() {
        return location;
    }

    public boolean isVisited() {
        return visited;
    }

    public Player getPlayer() {
        return player;
    }

    public void resetVisited() {
        visited = false;
    }

    public void setPlayer(Player p) {
        if (player == null) {
            player = p;
        }
        p.addRoad(this);
    }
}
