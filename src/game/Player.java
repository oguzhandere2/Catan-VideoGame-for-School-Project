package game;

import map.DevelopmentCard;
import map.DevelopmentType;
import map.HexaType;
import map.Road;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Player {

    private Color color;
    private final int no;
    private int noOfRoads = 2;
    private int noOfKnights = 0;
    private int noOfCities = 0;
    private ArrayList<DevelopmentCard> devList;
    private int victoryPoints = 2;
    private ArrayList<Road> roads;
    private boolean[] harbors = {false, false, false, false, false, false};
    private HashMap<HexaType, Integer> resources;
    private int noOfSettlements = 2;
    private boolean isLargestArmy;
    private boolean roadWinner = false;

    // harbors
    // 0 = general
    // 1 = brick
    // 2 = wool
    // 3 = ore
    // 4 = grain
    // 5 = lumber


    public Player(int no,Color color) {
        this.no = no;
        roads = new ArrayList<Road>();
        this.color = color;
        resources = new HashMap<HexaType, Integer>(5);
        resources.put(HexaType.BRICK, 0);
        resources.put(HexaType.WOOL, 0);
        resources.put(HexaType.ORE, 0);
        resources.put(HexaType.GRAIN, 0);
        resources.put(HexaType.LUMBER, 0);

        devList = new ArrayList<DevelopmentCard>();
    }


    public void setRoadWinner(boolean roadWinner) {
        if(this.roadWinner && !roadWinner) {
            System.out.println("if1");
            victoryPoints--;
            this.roadWinner = roadWinner;
        }
        else if(!this.roadWinner && roadWinner){
            System.out.println("if2");
            victoryPoints++;
            this.roadWinner = roadWinner;
        }
        else {
            System.out.println("if3");
            this.roadWinner = roadWinner;
        }
    }





    public boolean getRoadWinner(){
        return roadWinner;
    }

    public Player(int no,Color color, int noOfBrick, int noOfWool, int noOfOre, int noOfGrain, int noOfLumber, int victoryPoints) {

        this(no,color);

        setNumberResourcesType(HexaType.BRICK, noOfBrick);
        setNumberResourcesType(HexaType.WOOL, noOfWool);
        setNumberResourcesType(HexaType.ORE, noOfOre);
        setNumberResourcesType(HexaType.GRAIN, noOfGrain);
        setNumberResourcesType(HexaType.LUMBER, noOfLumber);

        this.victoryPoints = victoryPoints;
    }

    public void addDevelopmentCard(DevelopmentCard card) {
        devList.add(card);
        if (card.getType() == DevelopmentType.VICTORY) {
            victoryPoints++;
        }
    }

    public void addKnight() {
        noOfKnights++;
    }

    public void setNumberResourcesType(HexaType type, int n) {
        resources.put(type, n);
    }

    public int getNumberResourcesType(HexaType type) {
        if (type == HexaType.SEA || type == HexaType.DESERT)
            return 0;
        return resources.get(type);
    }

    public boolean hasEnoughResources(ArrayList<HexaType> resource) {
        int wool = 0;
        int ore = 0;
        int lumber = 0;
        int brick = 0;
        int grain = 0;

        for (int i = 0; i < resource.size(); i++) {
            if (resource.get(i) == HexaType.WOOL)
                wool++;
            else if (resource.get(i) == HexaType.ORE)
                ore++;
            else if (resource.get(i) == HexaType.LUMBER)
                lumber++;
            else if (resource.get(i) == HexaType.BRICK)
                brick++;
            else if (resource.get(i) == HexaType.GRAIN)
                grain++;
        }

        if (wool > resources.get(HexaType.WOOL) || ore > resources.get(HexaType.ORE) || lumber > resources.get(HexaType.LUMBER) || brick > resources.get(HexaType.BRICK) || grain > resources.get(HexaType.GRAIN))
            return false;
        else
            return true;
    }

    public boolean hasDevelopmentCard(DevelopmentType type) {
        for (DevelopmentCard card : devList) {
            if (card.getType() == type)
                return true;
        }
        return false;
    }

    public void removeDevelopmentCard(DevelopmentType type) {
        Iterator<DevelopmentCard> iter = devList.iterator();
        boolean once = false;
        while(iter.hasNext() && !once) {
            DevelopmentCard  card = iter.next();
            if(card.getType() == type) {
                iter.remove();
                once = true;
            }
        }
/*
        if(Collections.frequency(devList,type)>0) {
            devList.remove(type);
        }*/

    }

    public void addHarbor(int harbor) {
        harbors[harbor] = true;
    }

    public void incrementSettlement() {
        noOfSettlements++;
    }

    public void upgradeSettlementToCity() {
        noOfSettlements--;
        noOfCities++;
    }

    public void addRoad(Road road) {
        roads.add(road);
    }

    public void giveResource(HexaType type) {
        if (type == HexaType.SEA || type == HexaType.DESERT) {
            return;
        }
        resources.put(type, resources.get(type) + 1);
    }

    public void removeResources(ArrayList<HexaType> resource) {
        for (int i = 0; i < resource.size(); i++) {
            setNumberResourcesType(resource.get(i), getNumberResourcesType(resource.get(i)) - 1);
        }
    }

    public void addResource(ArrayList<HexaType> resource) {
        for (HexaType res : resource) {
            setNumberResourcesType(res, getNumberResourcesType(res) + 1);
        }
    }

    public int getNoOfAllResources() {
        return getNumberResourcesType(HexaType.BRICK) +
                getNumberResourcesType(HexaType.WOOL) +
                getNumberResourcesType(HexaType.ORE) +
                getNumberResourcesType(HexaType.GRAIN) +
                getNumberResourcesType(HexaType.LUMBER);
    }

    public int getNoOfDevelopmentType(DevelopmentType type) {
        int count = 0;
        for (DevelopmentCard card : devList) {
            if (card.getType() == type )
                count++;
        }

        return count;
    }

    public int getNo() {
        return no;
    }

    public HashMap<HexaType, Integer> getResources() {
        return resources;
    }

    public void setResources(HashMap<HexaType, Integer> resources) {
        this.resources = resources;
    }

    public ArrayList<DevelopmentCard> getDevList() {
        return devList;
    }

    public void setDevList(ArrayList<DevelopmentCard> devList) {
        this.devList = devList;
    }

    public ArrayList<Road> getRoads() {
        return roads;
    }

    public void setRoads(ArrayList<Road> roads) {
        this.roads = roads;
    }

    public int getNoOfKnights() {
        return noOfKnights;
    }

    public void setNoOfKnights(int noOfKnights) {
        this.noOfKnights = noOfKnights;
    }

    public int getNoOfSettlements() {
        return noOfSettlements;
    }

    public String getName(){
        return (no +1)+"";
    }

    public Color getColor(){
        return color;
    }


    public void setNoOfSettlements(int noOfSettlements) {
        this.noOfSettlements = noOfSettlements;
    }

    public int getVictoryPoints() {
        return victoryPoints;
    }

    public void setVictoryPoints(int victoryPoints) {
        this.victoryPoints = victoryPoints;
    }

    public int getNoOfRoads() {
        return noOfRoads;
    }

    public void setNoOfRoads(int noOfRoads) {
        this.noOfRoads = noOfRoads;
    }

    public int getNoOfCities() {
        return noOfCities;
    }

    public void setNoOfCities(int noOfCities) {
        this.noOfCities = noOfCities;
    }

    public boolean isLargestArmy() {
        return isLargestArmy;
    }

    public void setLargestArmy(boolean largestArmy) {
        if (isLargestArmy == true && largestArmy == false)
            victoryPoints -= 2;
        else if (isLargestArmy == false && largestArmy == true)
            victoryPoints += 2;
        isLargestArmy = largestArmy;
    }

    public boolean[] getHarbors() {
        return harbors;
    }

    public void setHarbors(boolean[] harbors) {
        this.harbors = harbors;
    }

    public String toString(){
        return "Player " + (no+1);
    }

}
