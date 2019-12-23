package game;

import map.*;

import java.util.ArrayList;
import java.util.Collections;

public class GameEngine {

    private Map map;
    private String mode;
    private ArrayList<Player> players;
    private Cards cards;

    public GameEngine(ArrayList<Player> players, String mode) {
        this.mode = mode;
        Collections.shuffle(players);
        this.players = players;
        map = new Map(mode);
        cards = new Cards();
        Main.setFirstPlayer();
    }

    public GameEngine(ArrayList<Player> players) {
        Collections.shuffle(players);
        this.players = players;
        map = new Map(mode);
        cards = new Cards();
        Main.setFirstPlayer();
    }

    public Player gameWinner() {
        Player winner = null;

        if(checkRoadLeader() != null) {
            if (checkRoadLeader().getRoadWinner() == true) {
            } else {
                for (int i = 0; i < 4; i++) {
                    players.get(i).setRoadWinner(false);
                }
                checkRoadLeader().setRoadWinner(true);
            }
        }

        for (Player player : players) {
            if (player.getVictoryPoints() >= 10) {
                winner = player;
            }
        }
        return winner;
    }

    public int roll() {

        int value = Dice.roll();
        if (value != 7) {
            map.distributeResources(value);
        }
        return value;
    }

    public Player checkRoadLeader() {
        ArrayList<Integer> roads = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            roads.add(players.get(i).getNoOfRoads());
        }
        int maxValue = Collections.max(roads);
        int count = 0;
        Player result = null;
        for(int i = 0; i<4; i++) {
            if(players.get(i).getNoOfRoads() == maxValue) {
                count++;
                result = players.get(i);
            }
        }
        if(count == 1) {
            return result;
        }

        return null;
    }

    public void removeResource(Player player, ArrayList<HexaType> resources) {
        player.removeResources(resources);
    }

    // player'ın metodu olabilir.
    public void stealCard(Player thief, Player victim) {
        ArrayList<HexaType> resource = new ArrayList<HexaType>();
        for (int i = 0; i < victim.getNumberResourcesType(HexaType.WOOL); i++) {
            resource.add(HexaType.WOOL);
        }
        for (int i = 0; i < victim.getNumberResourcesType(HexaType.BRICK); i++) {
            resource.add(HexaType.BRICK);
        }
        for (int i = 0; i < victim.getNumberResourcesType(HexaType.ORE); i++) {
            resource.add(HexaType.ORE);
        }
        for (int i = 0; i < victim.getNumberResourcesType(HexaType.GRAIN); i++) {
            resource.add(HexaType.GRAIN);
        }
        for (int i = 0; i < victim.getNumberResourcesType(HexaType.LUMBER); i++) {
            resource.add(HexaType.LUMBER);
        }

        if (resource.size() <= 0) {
            return;
        }

        Collections.shuffle(resource);
        Collections.shuffle(resource);

        HexaType result = resource.get(0);

        thief.setNumberResourcesType(result, thief.getNumberResourcesType(result) + 1);
        victim.setNumberResourcesType(result, victim.getNumberResourcesType(result) - 1);
    }

    public void playMonopoly(Player player, HexaType type) {
        for (int i = 0; i < players.size(); i++) {
            Player otherPlayer = players.get(i);
            if (otherPlayer.getNo() != player.getNo()) {
                int noOfSelectedResources = otherPlayer.getNumberResourcesType(type);
                player.setNumberResourcesType(type, player.getNumberResourcesType(type) + noOfSelectedResources);
                otherPlayer.setNumberResourcesType(type, 0);
            }
        }
    }

    public void playJudge() {
        ArrayList<HexaType> allResources = new ArrayList<>();
        for (int i = 0; i < players.size(); i++) {
            for (int j = 0; j < players.get(i).getResources().get(HexaType.GRAIN); j++) {
                allResources.add(HexaType.GRAIN);
            }
            players.get(i).setNumberResourcesType(HexaType.GRAIN, 0);
            for (int j = 0; j < players.get(i).getResources().get(HexaType.BRICK); j++) {
                allResources.add(HexaType.BRICK);
            }
            players.get(i).setNumberResourcesType(HexaType.BRICK, 0);
            for (int j = 0; j < players.get(i).getResources().get(HexaType.LUMBER); j++) {
                allResources.add(HexaType.LUMBER);
            }
            players.get(i).setNumberResourcesType(HexaType.LUMBER, 0);
            for (int j = 0; j < players.get(i).getResources().get(HexaType.ORE); j++) {
                allResources.add(HexaType.ORE);
            }
            players.get(i).setNumberResourcesType(HexaType.ORE, 0);
            for (int j = 0; j < players.get(i).getResources().get(HexaType.WOOL); j++) {
                allResources.add(HexaType.WOOL);
            }
            players.get(i).setNumberResourcesType(HexaType.WOOL, 0);
        }
        Collections.shuffle(allResources);

        for (int i = 0; i < allResources.size(); i++) {
            int target = i % 4;
            players.get(target).setNumberResourcesType(allResources.get(i), players.get(target).getNumberResourcesType(allResources.get(i))+1);
        }
    }

    public void duplicateResources(Hexa hexa) {

    }

    public boolean tradeBwPlayers(Player player1, Player player2, ArrayList<HexaType> resources1, ArrayList<HexaType> resources2) {
        if (!player2.hasEnoughResources(resources2) ||!player1.hasEnoughResources(resources1) ) {
            return false;
        } else {
            for (int i = 0; i < resources1.size(); i++) {
                HexaType resource = resources1.get(i);
                player2.setNumberResourcesType(resource, 1+player2.getNumberResourcesType(resource));
                player1.setNumberResourcesType(resource, -1 + player1.getNumberResourcesType(resource));
            }
            for (int i = 0; i < resources2.size(); i++) {
                HexaType resource = resources2.get(i);
                player2.setNumberResourcesType(resource, player2.getNumberResourcesType(resource) - 1);
                player1.setNumberResourcesType(resource, player1.getNumberResourcesType(resource) + 1);
            }
            player1.hasEnoughResources(resources1);
            return true;
        }
    }

    public boolean purchaseRoad(Player player) {
        if (player.getNumberResourcesType(HexaType.BRICK) >= 1 && player.getNumberResourcesType(HexaType.LUMBER) >= 1 && player.getNoOfRoads() <= 15) {
            player.setNumberResourcesType(HexaType.BRICK, player.getNumberResourcesType(HexaType.BRICK) - 1);
            player.setNumberResourcesType(HexaType.LUMBER, player.getNumberResourcesType(HexaType.LUMBER) - 1);
            player.setNoOfRoads(player.getNoOfRoads() + 1);
            for(int i = 0; i < 4; i++) {

            }
            return true;
        }
        return false;
    }

    public int tradeWithBank(Player player, HexaType request, ArrayList<HexaType> ownResource) {
        if (!player.hasEnoughResources(ownResource))
            return 1;

        ArrayList<Integer> resources = new ArrayList<Integer>();
        boolean[] ports = player.getHarbors();
        resources.add(Collections.frequency(ownResource,HexaType.BRICK));
        resources.add(Collections.frequency(ownResource,HexaType.WOOL));
        resources.add(Collections.frequency(ownResource,HexaType.ORE));
        resources.add(Collections.frequency(ownResource,HexaType.GRAIN));
        resources.add(Collections.frequency(ownResource,HexaType.LUMBER));

        ArrayList<HexaType> returnedResource = new ArrayList<>();

        for (int i = 0; i < resources.size(); i++) {
            if (!(resources.get(i) == 0)) {
                if (ports[1+i]) {
                    if (resources.get(i) % 2 == 0) {
                        for (int k = 0; k < resources.get(i) / 2; k++) {
                            resources.size();
                            returnedResource.add(request);
                        }
                    }
                    else {
                        return 2;
                    }
                }
                else if (ports[0]) {
                    if (resources.get(i) % 3 == 0) {
                        for (int k = 0; k < resources.get(i) / 3; k++) {
                            resources.size();
                            returnedResource.add(request);
                        }
                    }
                    else {
                        return 2;
                    }
                }
                else {
                    if (resources.get(i) % 4 == 0) {
                        for (int k = 0; k < resources.get(i) / 4; k++) {
                            resources.size();
                            returnedResource.add(request);
                        }
                    }
                    else {
                        resources.size();
                        return 2;
                    }
                }
            }
        }


        for (int i = 0; i < ownResource.size();i++) {
            HexaType res = ownResource.get(i);
            player.setNumberResourcesType(res, player.getNumberResourcesType(res) - 1);
        }
        for (int i = 0; i < returnedResource.size();i++) {
            HexaType res = returnedResource.get(i);
            player.setNumberResourcesType(res, player.getNumberResourcesType(res) + 1);
        }

        return 0;
    }

    public boolean purchaseSettlement(Player player) {

        if (player.getNoOfSettlements() <= 5 && player.getNumberResourcesType(HexaType.BRICK) >= 1 && player.getNumberResourcesType(HexaType.GRAIN) >= 1 && player.getNumberResourcesType(HexaType.WOOL) >= 1 && player.getNumberResourcesType(HexaType.LUMBER) >= 1) {
            player.setNumberResourcesType(HexaType.BRICK, player.getNumberResourcesType(HexaType.BRICK) - 1);
            player.setNumberResourcesType(HexaType.LUMBER, player.getNumberResourcesType(HexaType.LUMBER) - 1);
            player.setNumberResourcesType(HexaType.GRAIN, player.getNumberResourcesType(HexaType.GRAIN) - 1);
            player.setNumberResourcesType(HexaType.WOOL, player.getNumberResourcesType(HexaType.WOOL) - 1);
            player.incrementSettlement();
            player.setVictoryPoints(player.getVictoryPoints() + 1);
            return true;
        }
        return false;
    }

    public boolean purchaseCity(Player player) {

        if (player.getNoOfCities() <= 4 && player.getNumberResourcesType(HexaType.GRAIN) >= 2 && player.getNumberResourcesType(HexaType.ORE) >= 3) {

            player.setNumberResourcesType(HexaType.ORE, player.getNumberResourcesType(HexaType.ORE) - 3);
            player.setNumberResourcesType(HexaType.GRAIN, player.getNumberResourcesType(HexaType.GRAIN) - 2);
            player.upgradeSettlementToCity();
            player.setVictoryPoints(player.getVictoryPoints() + 1);
            return true;
        }

        return false;
    }

    public boolean purchaseDevelopmentCard(Player player) {
        if (player.getNumberResourcesType(HexaType.WOOL) >= 1 && player.getNumberResourcesType(HexaType.ORE) >= 1 && player.getNumberResourcesType(HexaType.GRAIN) >= 1 && !cards.isEmpty()) {
            player.setNumberResourcesType(HexaType.WOOL, player.getNumberResourcesType(HexaType.WOOL) - 1);
            player.setNumberResourcesType(HexaType.GRAIN, player.getNumberResourcesType(HexaType.GRAIN) - 1);
            player.setNumberResourcesType(HexaType.ORE, player.getNumberResourcesType(HexaType.ORE) - 1);
            cards.shuffle();
            return true;
        }
        return false;
    }
/*
    public boolean placeRoad(Player player, Edge edge) {
        return map.putRoad(edge, player);
    }

    public boolean placeSettlement(Player player, Vertex vertex) {
        return map.putBuilding(vertex, player);
    }

    public boolean placeCity(Player player, Vertex vertex) {
        Building city = new City(vertex);
        city.setPlayer(map.getBuildings(vertex).getPlayer());
        map.setBuildings(vertex, city);
        return true;
    }
*/
    public Cards getCards() {
        return cards;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Map getMap() {
        return map;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public void setCards(Cards cards) {
        this.cards = cards;
    }

    public String getMode(){
        return mode;
    }

    public void setMode(String mode) {
        this.mode= mode;
    }

    public Player getRoadLeader(){
        for(int i = 1; i < 4; i++) {
            //BURASI YAPILACAK
        }
        return null;
    }
}