package components;

import boardDesign.Board;

import java.util.ArrayList;

public class GameEngine {
    Player[] players;
    Angel angel;
    Robber robber;
    Board board;
    ArrayList<Building> buildings;
    Resource allResources;

    GameEngine(String type){
        allResources = new Resource(19,19,19,19,19);
        if(type.equals("robber"))
        {
            for(int i = 0; i < 4; i++) {
                players[i] = new Player(i);
            }


        }
        else if(type.equals("angel")){

        }
        else
            System.out.println("ERROR! WRONG GAME TYPE");
    }

}
