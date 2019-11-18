package components;

import boardDesign.Location;

public class Robber {
    private Location location;

    public Robber(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

    public void replace(Location location) {
        this.location = location;
    }
}
