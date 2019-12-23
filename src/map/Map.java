package map;

import game.Player;

import java.util.ArrayList;
import java.util.Collections;

public class Map {
    private Location locationOfModeFigure;
    private Road[][][] roads;
    private Vertex start;
    private final int size = 7;
    private final int roadPlaces = 3;
    private Road endpoint = null;
    private final int buildingPlaces = 2;
    private Building[][][] buildings;
    private Hexa[][] hexas;

    public Map(String mode) {

        hexas = new Hexa[size][size];
        buildings = new Building[size][size][buildingPlaces];
        roads = new Road[size][size][roadPlaces];
        Hexa desert;
        if(mode.equals("robber")) {
            desert = new Hexa(HexaType.DESERT, true);
        }
        else{
            desert = new Hexa(true,HexaType.DESERT);
        }

        ArrayList<Integer> numbersOnHexas = new ArrayList<>();
        numbersOnHexas.add(2);
        numbersOnHexas.add(3);
        numbersOnHexas.add(3);
        numbersOnHexas.add(4);
        numbersOnHexas.add(4);
        numbersOnHexas.add(5);
        numbersOnHexas.add(5);
        numbersOnHexas.add(6);
        numbersOnHexas.add(6);
        numbersOnHexas.add(8);
        numbersOnHexas.add(8);
        numbersOnHexas.add(9);
        numbersOnHexas.add(9);
        numbersOnHexas.add(10);
        numbersOnHexas.add(10);
        numbersOnHexas.add(11);
        numbersOnHexas.add(11);
        numbersOnHexas.add(12);

        Collections.shuffle(numbersOnHexas);

        ArrayList<Hexa> hexaList = new ArrayList<Hexa>();
        hexaList.add(new Hexa(HexaType.LUMBER, numbersOnHexas.get(0)));
        hexaList.add(new Hexa(HexaType.LUMBER, numbersOnHexas.get(1)));
        hexaList.add(new Hexa(HexaType.LUMBER, numbersOnHexas.get(2)));
        hexaList.add(new Hexa(HexaType.LUMBER, numbersOnHexas.get(3)));
        hexaList.add(new Hexa(HexaType.BRICK, numbersOnHexas.get(4)));
        hexaList.add(new Hexa(HexaType.BRICK, numbersOnHexas.get(5)));
        hexaList.add(new Hexa(HexaType.BRICK, numbersOnHexas.get(6)));
        hexaList.add(new Hexa(HexaType.GRAIN, numbersOnHexas.get(7)));
        hexaList.add(new Hexa(HexaType.GRAIN, numbersOnHexas.get(8)));
        hexaList.add(new Hexa(HexaType.GRAIN, numbersOnHexas.get(9)));
        hexaList.add(new Hexa(HexaType.GRAIN, numbersOnHexas.get(10)));
        hexaList.add(new Hexa(HexaType.WOOL, numbersOnHexas.get(11)));
        hexaList.add(new Hexa(HexaType.WOOL, numbersOnHexas.get(12)));
        hexaList.add(new Hexa(HexaType.WOOL, numbersOnHexas.get(13)));
        hexaList.add(new Hexa(HexaType.WOOL, numbersOnHexas.get(14)));
        hexaList.add(new Hexa(HexaType.ORE, numbersOnHexas.get(15)));
        hexaList.add(new Hexa(HexaType.ORE, numbersOnHexas.get(16)));
        hexaList.add(new Hexa(HexaType.ORE, numbersOnHexas.get(17)));
        hexaList.add(desert);

        Collections.shuffle(hexaList);

        int counter = 0;


        for (int y = 1; y < 6; y++) {
            if (y == 2) {
                for (int x = 1; x < 5; x++) {
                    hexas[x][y] = hexaList.get(counter);
                    hexas[x][y].setLocation(x,y);
                    counter++;
                }
            }
            if (y == 4) {
                for (int x = 2; x < 6; x++) {
                    hexas[x][y] = hexaList.get(counter);
                    hexas[x][y].setLocation(x,y);
                    counter++;
                }
            }
            if (y == 1) {
                for (int x = 1; x < 4; x++) {
                    hexas[x][y] = hexaList.get(counter);
                    hexas[x][y].setLocation(x,y);
                    counter++;
                }
            }

            if (y == 5) {
                for (int x = 3; x < 6; x++) {
                    hexas[x][y] = hexaList.get(counter);
                    hexas[x][y].setLocation(x,y);
                    counter++;
                }
            }
            if (y == 3) {
                for (int x = 1; x < 6; x++) {
                    hexas[x][y] = hexaList.get(counter);
                    hexas[x][y].setLocation(x,y);
                    counter++;
                }
            }

            locationOfModeFigure = desert.getLocation();
        }

        for (int i = 0; i < hexas.length; i++) {
            for (int j = 0; j < hexas[0].length; j++) {
                if (hexas[i][j] == null) {
                    hexas[i][j] = new Hexa(i, j, 0, HexaType.SEA);
                    hexas[i][j].setLocation(i,j);//new
                }
            }
        }

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                for (int place = 0; place < buildingPlaces; place++) {
                    buildings[x][y][place] = new Settlement(x, y, place);
                    buildings[x][y][place].setType(BuildingType.SETTLEMENT);
                }
            }
        }

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                for (int place = 0; place < roadPlaces; place++) {
                    roads[x][y][place] = new Road(x, y, place);
                    roads[x][y][place].resetVisited();//new
                }
            }
        }
    }

    public void distributeResources(int number) {
        for (int i = 1; i < size; i++) {
            for (int j = 1; j < size; j++) {
                if (number == hexas[i][j].getNo()) {
                    Hexa currentHexa = hexas[i][j];
                    if (!(currentHexa.isWithRobber() || currentHexa.getType() == HexaType.DESERT)) {
                        Location location = currentHexa.getLocation();
                        ArrayList<Building> buildingsOnCurrent = new ArrayList<>();
                        int xPlus = location.getX() + 1;
                        int xMinus = location.getX() - 1;
                        int yPlus = location.getY() + 1;
                        int yMinus = location.getY() - 1;
                        buildingsOnCurrent.add(buildings[location.getX()][location.getY()][1]);
                        //buildingsOnCurrent.add(buildings[location.getX()][location.getY()-1][0]);
                        buildingsOnCurrent.add(buildings[location.getX()][yMinus][0]);
                        //buildingsOnCurrent.add(buildings[location.getX()-1][location.getY()-1][0]);
                        buildingsOnCurrent.add(buildings[xMinus][yMinus][0]);
                        //buildingsOnCurrent.add(buildings[location.getX()+1][location.getY()+1][1]);
                        buildingsOnCurrent.add(buildings[xPlus][yPlus][1]);
                        //buildingsOnCurrent.add(buildings[location.getX()][location.getY()+1][1]);
                        buildingsOnCurrent.add(buildings[location.getX()][yPlus][1]);
                        buildingsOnCurrent.add(buildings[location.getX()][location.getY()][0]);
                        if(!currentHexa.isWithAngel()) {
                            for (int k = 0; k < buildingsOnCurrent.size(); k++) {
                                if (buildingsOnCurrent.get(k).getPlayer() != null) {
                                    buildingsOnCurrent.get(k).giveResources(currentHexa.getType());
                                }
                            }
                        }
                        else {
                            for (int k = 0; k < buildingsOnCurrent.size(); k++) {
                                if (buildingsOnCurrent.get(k).getPlayer() != null) {
                                    buildingsOnCurrent.get(k).giveResources(currentHexa.getType());
                                    buildingsOnCurrent.get(k).giveResources(currentHexa.getType());
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public Building getBuildings(Vertex vertex) {
        int x = vertex.getX();
        int y = vertex.getY();
        int place = vertex.getPlace();
        return buildings[x][y][place];
    }

    public Road getRoad(Edge edge) {
        return roads[edge.getX()][edge.getY()][edge.getPlace()];
    }

    public void setBuildings(Vertex vertex, Building building) {
        buildings[vertex.getX()][vertex.getY()][vertex.getPlace()] = building;
    }


    public boolean putFirstBuildings(Vertex loc, Player player) {

        if (!(buildings[loc.getX()][loc.getY()][loc.getPlace()].getPlayer() == null)) {
            return false;
        }

        int x = loc.getX();
        int y = loc.getY();
        int xPlus = loc.getX()+1;
        int xMinus = loc.getX()-1;
        int yPlus = loc.getY()+1;
        int yMinus = loc.getY()-1;
        if (loc.getPlace() == 0) {
            if (buildings[x][yPlus][1].getPlayer() == null &&
                    buildings[xPlus][yPlus][1].getPlayer() == null &&
                    !(loc.getY() + 2 <= 6 && !(buildings[xPlus][y + 2][1].getPlayer() == null))) {
                if (harborCheck(loc) != -1)
                    player.addHarbor(harborCheck(loc));
                buildings[x][y][loc.getPlace()].setPlayer(player);
                return true;
            } else {
                return false;
            }
        } else {
            if (buildings[x][yMinus][0].getPlayer() == null &&
                    buildings[xMinus][yMinus][0].getPlayer() == null &&
                    !(x - 2 >= 0 && !(buildings[xMinus][y - 2][0].getPlayer() == null))) {
                if (harborCheck(loc) != -1)
                    player.addHarbor(harborCheck(loc));
                buildings[x][y][loc.getPlace()].setPlayer(player);
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean putBuilding(Vertex location, Player player) {

        if (buildings[location.getX()][location.getY()][location.getPlace()].getPlayer() != null) { //Vertex is already occupied
            return false;
        }

        int x = location.getX();
        int xPlus = location.getX()+1;
        int xMinus = location.getX()-1;
        int y = location.getY();
        int yPlus = location.getY()+1;
        int yMinus = location.getY()-1;

        if (location.getPlace() == 0) {
            if ((player.equals(roads[x][y][0].getPlayer()) ||
                    player.equals(roads[x][y][1].getPlayer()) ||
                    player.equals(roads[y][yPlus][2].getPlayer()))
                    &&
                    (buildings[x][yPlus][1].getPlayer() == null &&
                            buildings[xPlus][yPlus][1].getPlayer() == null &&
                            !(y + 2 <= 6 && !(buildings[x + 1][y + 2][1].getPlayer() == null)))) {
                if (harborCheck(location) != -1)
                    player.addHarbor(harborCheck(location));
                buildings[x][yMinus+1][location.getPlace()].setPlayer(player);//new
                return true;
            } else {
                return false;
            }
        } else {
            if ((player.equals(roads[x][yMinus][0].getPlayer()) ||
                    player.equals(roads[xMinus][yMinus][1].getPlayer()) ||
                    player.equals(roads[xMinus][yMinus][2].getPlayer()))
                    &&
                    (buildings[x][yMinus][0].getPlayer() == null &&
                            buildings[xMinus][yMinus][0].getPlayer() == null &&
                            !(y - 2 >= 0 && !(buildings[x - 1][y - 2][0].getPlayer() == null)))) {
                if (harborCheck(location) != -1)
                    player.addHarbor(harborCheck(location));
                buildings[x][y][location.getPlace()].setPlayer(player);
                return true;
            } else {
                return false;
            }
        }
    }


    public boolean putRoad(Edge edge, Player player) {

        if (roads[edge.getX()][edge.getY()][edge.getPlace()].getPlayer() != null) {
            return false;
        }

        int xPlus = edge.getX()+1;
        int yPlus = edge.getY()+1;
        int xMinus = edge.getX()-1;
        int yMinus = edge.getY()-1;

        if (edge.getPlace() == 1) {
            if (
                    player.equals(roads[xPlus][edge.getY()][0].getPlayer()) ||
                player.equals(buildings[xPlus][edge.getY() + 1][1].getPlayer()) ||
                player.equals(roads[edge.getX()][edge.getY()][2].getPlayer()) || false ||
                player.equals(roads[edge.getX()][edge.getY()][0].getPlayer()) ||
                player.equals(roads[edge.getX()][yPlus][2].getPlayer()) ||
                player.equals(buildings[edge.getX()][edge.getY()][0].getPlayer())) {
                roads[edge.getX()][edge.getY()][edge.getPlace()].setPlayer(player);
                return true;
            } else {
                return false;
            }
        }  else if (edge.getPlace() == 0) {
            if (player.equals(buildings[edge.getX()][yPlus][1].getPlayer()) ||
                    player.equals(roads[edge.getX()][edge.getY()][1].getPlayer()) ||
                    player.equals(buildings[edge.getX()][edge.getY()][0].getPlayer()) ||
                    player.equals(roads[xMinus][edge.getY()][2].getPlayer()) || false ||
                    player.equals(roads[xMinus][edge.getY()][1].getPlayer()) ||
                    player.equals(roads[edge.getX()][yPlus][2].getPlayer())) {
                roads[edge.getX()][edge.getY()][edge.getPlace()].setPlayer(player);
                return true;
            } else {
                return false;
            }
        }else {
            if (player.equals(buildings[edge.getX()][yMinus][0].getPlayer()) ||
                   player.equals(buildings[xPlus][yPlus][1].getPlayer()) ||
                   player.equals(roads[edge.getX()][edge.getY()][1].getPlayer()) ||
                   player.equals(roads[xPlus][edge.getY()][0].getPlayer()) ||
                   player.equals(roads[edge.getX()][yMinus][0].getPlayer()) ||
                   player.equals(roads[edge.getX()][yMinus][1].getPlayer())) {
                roads[edge.getX()][edge.getY()][edge.getPlace()].setPlayer(player);
                return true;
            } else {
                return false;
            }
        }
    }



    public Hexa getHexa(Location location) {
        return hexas[location.getX()][location.getY()];
    }


    private int harborCheck(Vertex location) {
        if ((location.getX() == 0 && location.getY() == 0 && location.getPlace() == 0) ||
                (location.getX() == 1 && location.getY() == 1 && location.getPlace() == 1) ||
                (location.getX() == 5 && location.getY() == 2 && location.getPlace() == 0) ||
                (location.getX() == 6 && location.getY() == 4 && location.getPlace() == 1) ||
                (location.getX() == 5 && location.getY() == 4 && location.getPlace() == 0) ||
                (location.getX() == 6 && location.getY() == 5 && location.getPlace() == 1) ||
                (location.getX() == 3 && location.getY() == 6 && location.getPlace() == 1) ||
                (location.getX() == 3 && location.getY() == 5 && location.getPlace() == 0)) {
            return 0;
        } else if ((location.getX() == 1 && location.getY() == 3 && location.getPlace() == 0) ||
                (location.getX() == 2 && location.getY() == 5 && location.getPlace() == 1)) {
            return 3;
        } else if ((location.getX() == 4 && location.getY() == 5 && location.getPlace() == 0) ||
                (location.getX() == 5 && location.getY() == 6 && location.getPlace() == 1)) {
            return 2;
        }  else if ((location.getX() == 2 && location.getY() == 0 && location.getPlace() == 0) ||
                (location.getX() == 2 && location.getY() == 1 && location.getPlace() == 1)) {
            return 5;
        } else if  ((location.getX() == 0 && location.getY() == 1 && location.getPlace() == 0) ||
                (location.getX() == 1 && location.getY() == 3 && location.getPlace() == 1)) {
            return 4;
        } else if ((location.getX() == 4 && location.getY() == 1 && location.getPlace() == 0) ||
                (location.getX() == 4 && location.getY() == 2 && location.getPlace() == 1)) {
            return 1;
        } else {
            return -1;
        }
    }


    public Hexa[][] getHexas() {
        return hexas;
    }

    public void setHexas(Hexa[][] hexas) {
        this.hexas = hexas;
    }

    public boolean moveRobber(Location location) {
        Location current = getLocationOfModeFigure();
        if (!(location.getX() == current.getX() &&
                location.getY() == current.getY())) {
            hexas[current.getX()][current.getY()].putRobber(false);
            setLocationOfModeFigure(location);
            hexas[location.getX()][location.getY()].putRobber(true);
            return true;
        }
        return false;
    }

    public Building[][][] getBuildings() {
        return buildings;
    }

    public void setBuildings(Building[][][] buildings) {
        this.buildings = buildings;
    }

    public boolean moveAngel(Location location){
        Location current= getLocationOfModeFigure();
        if(location.getX() == current.getX() && location.getY() == current.getY()) {
            return false;
        }
        else {
            hexas[current.getX()][current.getY()].putAngel(false);
            setLocationOfModeFigure(location);
            hexas[location.getX()][location.getY()].putAngel(true);
            return true;
        }
    }

    public void setRoads(Road[][][] roads) {
        this.roads = roads;
    }

    public Road getEndpoint() {
        return endpoint;
    }


    public Location getLocationOfModeFigure() {
        return locationOfModeFigure;
    }

    public void setLocationOfModeFigure(Location location) {
        locationOfModeFigure = location;
    }

    public void setEndpoint(Road endpoint) {
        this.endpoint = endpoint;
    }

    public ArrayList<Hexa> getAdjacentHexasOfBuilding(Vertex location) {
        ArrayList<Hexa> result = new ArrayList<>();
        int xPlus = location.getX()+1;
        int yPlus = location.getY()+1;
        int xMinus = location.getX()-1;
        int yMinus = location.getY()-1;
        if (!(location.getPlace() == 0)) {
            Hexa a = hexas[location.getX()][location.getY()];
            Hexa b = hexas[location.getX()][yMinus];
            Hexa c = hexas[xMinus][yMinus];
            if (b.getType() != null) {
                b.getType();
                result.add(b);
            }
            if (a.getType() != null) {
                a.getType();
                result.add(a);
            }
            if (c.getType() != null) {
                c.getType();
                result.add(c);
            }
        } else {
            Hexa a = hexas[location.getX()][location.getY()];
            Hexa b = hexas[location.getX()][yPlus];
            Hexa c = hexas[xPlus][yPlus];
            if (a.getType() != null) {
                a.getLocation();
                result.add(a);
            }
            if (c.getType() != null)
                result.add(c);
            if (b.getType() != null) {
                result.add(b);
                b.getLocation();
            }

        }
        return result;
    }


    public Vertex getStart() {
        return start;
    }

    public void setStart(Vertex start) {
        this.start = start;
    }

    public int getSize() {
        return size;
    }

    public ArrayList<Player> getRobberAffectedPlayers() {
        ArrayList<Building> cornersOfRobber = new ArrayList<>();
        ArrayList<Player> players = new ArrayList<>();
        int x = locationOfModeFigure.getX();
        int y = locationOfModeFigure.getY();
        int xPlus = locationOfModeFigure.getX()+1;
        int yPlus = locationOfModeFigure.getY()+1;
        int xMinus = locationOfModeFigure.getX()-1;
        int yMinus = locationOfModeFigure.getY()-1;
        cornersOfRobber.add(buildings[x][y][0]);
        cornersOfRobber.add(buildings[xPlus][yPlus][1]);
        cornersOfRobber.add(buildings[x][yMinus][0]);
        cornersOfRobber.add(buildings[x][y][1]);
        cornersOfRobber.add(buildings[xMinus][yMinus][0]);
        cornersOfRobber.add(buildings[x][yPlus][1]);
        for (int i = 0; i < cornersOfRobber.size(); i++) {
            if (cornersOfRobber.get(i).getPlayer() != null) {
                if (Collections.frequency(players, cornersOfRobber.get(i).getPlayer()) < 1) {
                    players.add(cornersOfRobber.get(i).getPlayer());
                }
            }
        }
        return players;
    }

    public int getRoadPlaces() {
        return roadPlaces;
    }


    public boolean putCity(Vertex vertex, Player player) {
        if (player.getNo() == buildings[vertex.getX()][vertex.getY()][vertex.getPlace()].getPlayer().getNo() &&
                buildings[vertex.getX()][vertex.getY()][vertex.getPlace()].getType() == BuildingType.SETTLEMENT) {
            buildings[vertex.getX()][vertex.getY()][vertex.getPlace()].setType(BuildingType.CITY);
            return true;
        } else
            return false;
    }

    public int getBuildingPlaces() {
        return buildingPlaces;
    }

    public Road[][][] getRoads() {
        return roads;
    }

}
