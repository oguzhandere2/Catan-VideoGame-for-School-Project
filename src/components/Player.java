package components;

import java.util.ArrayList;

public class Player {
    private int playerNo;
    private int points;
    private Resource resource; //brick, ore, grain, wool, lumber
    private Development development; //knight, judge, victory, progress
    private ArrayList<Building> buildings;

    public Player(int playerNo) {
        points = 0;
        this.playerNo = playerNo;
        resource = new Resource();
        development = new Development();
    }

    public int rollDice(){return Dice.roll();}

    public Trade trade(int no, Resource offers, Resource requests) {
        return new Trade(playerNo, no, offers, requests);
    }

    public boolean tradeBank( ResourceType offer, ResourceType request){
        if(resource.getResource(offer) >= 4){
            resource.updateResource(request, resource.getResource(request) + 1);
            resource.updateResource(offer,resource.getResource(offer) - 4);
            return true;
        }
        else
            return false;
    }

    public boolean build(Building building) {
        if(building.getType() == BuildingType.ROAD) {
            boolean enoughResource = Resource.checkResource(resource,building.getPrice());
            if(enoughResource) {
                buildings.add(building);
                for(int i = 0; i < 5; i++) {
                    int newValue = resource.getResource(i) - building.getPrice().getResource(i);
                    resource.updateResource(i, newValue);
                }
                return true;
            }
            return false;
        }
        else if(building.getType() == BuildingType.SETTLEMENT){
            boolean enoughResource = Resource.checkResource(resource,building.getPrice());
            if(enoughResource) {
                buildings.add(building);
                for(int i = 0; i < 5; i++) {
                    int newValue = resource.getResource(i) - building.getPrice().getResource(i);
                    resource.updateResource(i, newValue);
                }
                return true;
            }
            return false;
        }
        else {
            System.out.println("Else'e girdi. Problem var. player.build()");
            return false;
        }
    }

    public boolean responseTrade(Trade trade, boolean response){
        if(response){
            boolean enoughResource = true;
            for(int i = 0; i < 5; i++ ) {
                if (trade.getRequests().getResource(i) > resource.getResource(i))
                    enoughResource = false;
            }
            return enoughResource;
        }
        else {
            return false;
        }
    }

    public boolean develop(Building building) {
        if(building.getType()==BuildingType.SETTLEMENT) {
            boolean enoughResources = true;
            for(int i = 0; i < 5; i++) {
                if(building.getPrice().getResource(i) > resource.getResource(i))
                    enoughResources = false;
            }
            return enoughResources;
        }
        else
            return false;
    }

    public ArrayList<Building> getBuildings(){
        return buildings;
    }

    /**
     * buraya bakılacak
     * @return
     */
    public int nextTurn(){
        return playerNo;
    }

    public void setPlayerNo(int playerNo) {
        this.playerNo = playerNo;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setBuildings(ArrayList<Building> buildings) {
        this.buildings = buildings;
    }

}