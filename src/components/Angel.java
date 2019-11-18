package components;

import boardDesign.Location;

public class Angel {
    private Location location;



    public Angel(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void replace(Location location) {
        this.location = location;
    }

}
