package map;

public class Settlement extends Building {

    public Settlement(int x, int y, int place) {
        setType(BuildingType.SETTLEMENT);
        setLocation(new Vertex(x, y, place));
    }


    public void giveResources(HexaType type) {
        getPlayer().setNumberResourcesType(type, getPlayer().getNumberResourcesType(type) + 1);
    }

}
