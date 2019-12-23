package map;

public class City extends Building {

    public City(Vertex loc) {
        setLocation(loc);
        setType(BuildingType.CITY);
    }

    public void giveResources(HexaType type) {
        getPlayer().setNumberResourcesType(type, getPlayer().getNumberResourcesType(type) + 2);
    }
}
