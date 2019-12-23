package game;

import java.util.ArrayList;
import gui.MainWindow;
import javax.swing.*;
import java.awt.*;


public class Main {

    private static Player currentPlayer;
    private static final int noOfPlayers = 4;
    private static int index = 0;
    private static ArrayList<Player> players = new ArrayList<>();
    private static GameEngine gameEngine;
    private static Player winner;

    public static void main(String[] args) {


        players.add(new Player(0,Color.magenta));
        players.add(new Player(1,Color.GREEN));
        players.add(new Player(2,Color.orange));
        players.add(new Player(3,Color.PINK));

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainWindow tmp = new MainWindow(players);
                gameEngine = tmp.getMapDesign().getGame();
            }
        });

    }

    public static void nextTurn(){
        index = (index + 1) % noOfPlayers;
        currentPlayer = players.get(index);

    }

    public static void setFirstPlayer() {
        currentPlayer = players.get(0);
    }


    public static Player getWinner() {
        return winner;
    }

    public static void setWinner(Player player) {
        winner = player;
    }

    public static void previousPlayer() {
        currentPlayer = players.get((index - 1) % noOfPlayers);
        index = (index - 1) % noOfPlayers;
    }

    public static Player getPlayer(int i) {
        return players.get(i);
    }

    public static int getNumbPlayers() {
        return noOfPlayers;
    }

    public static Player getCurrentPlayer(){
        return currentPlayer;
    }


}
