package components;

import boardDesign.Location;

/**
 * @author = fatih
 * @date = 10.11.2019
 */
public class Building {
    private int point;
    private Location location;
    private ResourceType[] resources;
    private BuildingType type;
    private Resource price;
    private int [] developPrice;

    public Building(Location location, ResourceType[] resources, BuildingType type) {
        this.location = location;
        this.resources = resources;
        this.type = type;
        if(type == BuildingType.SETTLEMENT) {
            point = 1;
            price = new Resource(1,0,1,1,1);
            developPrice = new int[]{};
        }
        if( type == BuildingType.ROAD) {
            point = 0;
            price = new Resource(1,0,0,0,1);
        }
    }

    public void develop(){
        if(type == BuildingType.SETTLEMENT){
            setPoint(2);
            setType(BuildingType.CITY);
        }
        else
            System.out.println("building.develop() else döndü!");
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public Location getLocation() {
        return location;
    }

    private void setLocation(Location location) {
        this.location = location;
    }

    public ResourceType[] getResources() {
        return resources;
    }

    public void setResources(ResourceType[] resources) {
        this.resources = resources;
    }

    public void setPrice(Resource price) {
        this.price = price;
    }

    public int[] getDevelopPrice() {
        return developPrice;
    }

    public void setDevelopPrice(int[] developPrice) {
        this.developPrice = developPrice;
    }

    public BuildingType getType() {
        return type;
    }

    public void setType(BuildingType type) {
        this.type = type;
    }

    public Resource getPrice() {
        return price;
    }
}
