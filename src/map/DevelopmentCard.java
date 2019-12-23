package map;

public class DevelopmentCard {
    private final DevelopmentType type;
    //road,plenty,monopoly
    public DevelopmentCard(DevelopmentType type) {
        this.type = type;
    }

    public DevelopmentType getType() {
        return type;
    }

}
