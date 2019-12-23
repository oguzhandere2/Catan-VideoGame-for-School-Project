package map;


import game.Player;

public abstract class Building {

    private Player player = null;
    private Vertex location;
    private BuildingType type;

    public abstract void giveResources(HexaType type);

    public void setPlayer(Player player) {
        if(this.player == null)
            this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setLocation(Vertex location) {
        this.location = location;
    }

    public Vertex getLocation() {
        return location;
    }

    public BuildingType getType() {
        return type;
    }

    public void setType(BuildingType type) {
        this.type = type;
    }
}
