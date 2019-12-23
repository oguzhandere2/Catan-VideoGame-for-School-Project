package gui;

import game.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import game.Player;

public class LeftBar {

    public final static int INTERVAL = 50;

    private JPanel panel1;
    private JPanel panel2;
    private JPanel mainPanelNewBottom;
    private JPanel panel_player1;
    private JPanel panel_player2;
    private JPanel panel_player3;
    private JPanel panel_player4;
    private JLabel player1_longestRoad;
    private JLabel player2_longestRoad;
    private JLabel player3_longestRoad;
    private JLabel player4_longestRoad;
    private JLabel player1_knights;
    private JLabel player2_knights;
    private JLabel player3_knights;
    private JLabel player4_knights;
    private JLabel player1_victory_point;
    private JLabel player2_victory_point;
    private JLabel player3_victory_point;
    private JLabel player4_victory_point;
    private JLabel player1_resource_cards;
    private JLabel player2_resource_cards;
    private JLabel player3_resource_cards;
    private JLabel player4_resource_cards;
    private JLabel player1_dev_cards;
    private JLabel player3_dev_cards;
    private JLabel player2_dev_cards;
    private JLabel player4_dev_cards;
    private JPanel panel_main_player1;
    private JPanel panel_main_player2;
    private JPanel panel_main_player3;
    private JPanel panel_main_player4;
    private JLabel playerLabel1;
    private JLabel playerLabel2;
    private JLabel playerLabel3;
    private JLabel playerLabel4;
    private JLabel costs;
    private Timer timer;


    public LeftBar() {
        mainPanelNewBottom.setOpaque(false);

        panel_player1.setOpaque(false);
        panel_player2.setOpaque(false);
        panel_player3.setOpaque(false);
        panel_player4.setOpaque(false);

        panel2.setOpaque(false);
        panel1.setOpaque(false);

        playerLabel1.setText("Player "+(Main.getPlayer(0).getNo()+1));
        playerLabel2.setText("Player "+(Main.getPlayer(1).getNo()+1));
        playerLabel3.setText("Player "+(Main.getPlayer(2).getNo()+1));
        playerLabel4.setText("Player "+(Main.getPlayer(3).getNo()+1));

        panel_main_player1.setBackground( Main.getPlayer(0).getColor());

        panel_main_player2.setBackground( Main.getPlayer(1).getColor());
        panel_main_player3.setBackground( Main.getPlayer(2).getColor());
        panel_main_player4.setBackground( Main.getPlayer(3).getColor());
        //DEFAULT VALUES OF TEXTS
        player1_dev_cards.setText("undefined");
        player2_dev_cards.setText("undefined");
        player3_dev_cards.setText("undefined");
        player4_dev_cards.setText("undefined");

        player1_resource_cards.setText("undefined");
        player2_resource_cards.setText("undefined");
        player3_resource_cards.setText("undefined");
        player4_resource_cards.setText("undefined");

        player1_victory_point.setText("undefined");
        player2_victory_point.setText("undefined");
        player3_victory_point.setText("undefined");
        player4_victory_point.setText("undefined");

        player1_knights.setText("undefined");
        player2_knights.setText("undefined");
        player3_knights.setText("undefined");
        player4_knights.setText("undefined");

        player1_longestRoad.setText("undefined");
        player2_longestRoad.setText("undefined");
        player3_longestRoad.setText("undefined");
        player4_longestRoad.setText("undefined");

        BufferedImage img = null;


        try {
            img = ImageIO.read(new File("images/costs.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image dimg2 = img.getScaledInstance(300, 300,
                Image.SCALE_AREA_AVERAGING);

        ImageIcon imageIcon2 = new ImageIcon(dimg2);
        costs.setIcon(imageIcon2);
        //SETTING THE KNIGHT ICON
        try {
            img = ImageIO.read(new File("images/knight_icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image dimg = img.getScaledInstance(25, 25,
                Image.SCALE_AREA_AVERAGING);

        ImageIcon imageIcon = new ImageIcon(dimg);

        player1_knights.setIcon(imageIcon);
        player2_knights.setIcon(imageIcon);
        player3_knights.setIcon(imageIcon);
        player4_knights.setIcon(imageIcon);

        //SETTING THE ROAD ICON
        try {
            img = ImageIO.read(new File("images/road.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        dimg = img.getScaledInstance(25, 25, Image.SCALE_AREA_AVERAGING);
        imageIcon = new ImageIcon(dimg);

        player1_longestRoad.setIcon(imageIcon);
        player2_longestRoad.setIcon(imageIcon);
        player3_longestRoad.setIcon(imageIcon);
        player4_longestRoad.setIcon(imageIcon);


        //SETTING THE 'VICTORY CUP' ICON
        try {
            img = ImageIO.read(new File("images/victory_cup.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        dimg = img.getScaledInstance(25, 25, Image.SCALE_AREA_AVERAGING);
        imageIcon = new ImageIcon(dimg);

        player1_victory_point.setIcon(imageIcon);
        player2_victory_point.setIcon(imageIcon);
        player3_victory_point.setIcon(imageIcon);
        player4_victory_point.setIcon(imageIcon);

        //SETTIN DEV CARD ICON
        try {
            img = ImageIO.read(new File("images/dev_card.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        dimg = img.getScaledInstance(25, 25, Image.SCALE_AREA_AVERAGING);
        imageIcon = new ImageIcon(dimg);

        player1_dev_cards.setIcon(imageIcon);
        player2_dev_cards.setIcon(imageIcon);
        player3_dev_cards.setIcon(imageIcon);
        player4_dev_cards.setIcon(imageIcon);

        //SETTIN RES CARD ICON

        try {
            img = ImageIO.read(new File("images/res_card.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        dimg = img.getScaledInstance(25, 25, Image.SCALE_AREA_AVERAGING);
        imageIcon = new ImageIcon(dimg);

        player1_resource_cards.setIcon(imageIcon);
        player2_resource_cards.setIcon(imageIcon);
        player3_resource_cards.setIcon(imageIcon);
        player4_resource_cards.setIcon(imageIcon);
/*
        Player winner = Main.getWinner();
        if( winner != null)
        {
            panel1.removeAll();
            JButton finishButton;
            finishButton = new JButton("WINNER IS" + winner.getName());

        }*/
        //SETTING PLAYER RESOURCES
        timer = new Timer(INTERVAL,
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        for (int i = 0; i < Main.getNumbPlayers(); i++) {
                            updatePlayer(Main.getPlayer(i), i);
                        }

                    }
                });
        timer.start();
    }

    public JPanel getMainPanel()
    {
        return mainPanelNewBottom;
    }

    private void updatePlayer(Player player, int playerIndex) {
        int totalNumberOfDevCards = player.getDevList().size();

        switch (playerIndex) {
            case 0:
                player1_resource_cards.setText(" " + player.getNoOfAllResources());
                player1_knights.setText(" " + player.getNoOfKnights());
                player1_dev_cards.setText(" " + totalNumberOfDevCards);
                player1_victory_point.setText(" " + player.getVictoryPoints());
                player1_longestRoad.setText(" " + player.getRoads().size());
                break;

            case 1:
                player2_resource_cards.setText(" " + player.getNoOfAllResources());
                player2_knights.setText(" " + player.getNoOfKnights());
                player2_dev_cards.setText(" " + totalNumberOfDevCards);
                player2_victory_point.setText(" " + player.getVictoryPoints());
                player2_longestRoad.setText(" " + player.getRoads().size());
                break;

            case 2:
                player3_resource_cards.setText(" " + player.getNoOfAllResources());
                player3_knights.setText(" " + player.getNoOfKnights());
                player3_dev_cards.setText(" " + totalNumberOfDevCards);
                player3_victory_point.setText(" " + player.getVictoryPoints());
                player3_longestRoad.setText(" " + player.getRoads().size());
                break;

            case 3:
                player4_resource_cards.setText(" " + player.getNoOfAllResources());
                player4_knights.setText(" " + player.getNoOfKnights());
                player4_dev_cards.setText(" " + totalNumberOfDevCards);
                player4_victory_point.setText(" " + player.getVictoryPoints());
                player4_longestRoad.setText(" " + player.getRoads().size());
                break;

            default:
                System.out.println("ERROR. WRONG PLAYER INDEX");
                break;
        }
    }

    public static int getINTERVAL() {
        return INTERVAL;
    }

    public JPanel getPanel1() {
        return panel1;
    }

    public void setPanel1(JPanel panel1) {
        this.panel1 = panel1;
    }

    public JPanel getPanel2() {
        return panel2;
    }

    public void setPanel2(JPanel panel2) {
        this.panel2 = panel2;
    }

    public JPanel getMainPanelNewBottom() {
        return mainPanelNewBottom;
    }

    public void setMainPanelNewBottom(JPanel mainPanelNewBottom) {
        this.mainPanelNewBottom = mainPanelNewBottom;
    }

    public JPanel getPanel_player1() {
        return panel_player1;
    }

    public void setPanel_player1(JPanel panel_player1) {
        this.panel_player1 = panel_player1;
    }

    public JPanel getPanel_player2() {
        return panel_player2;
    }

    public void setPanel_player2(JPanel panel_player2) {
        this.panel_player2 = panel_player2;
    }

    public JPanel getPanel_player3() {
        return panel_player3;
    }

    public void setPanel_player3(JPanel panel_player3) {
        this.panel_player3 = panel_player3;
    }

    public JPanel getPanel_player4() {
        return panel_player4;
    }

    public void setPanel_player4(JPanel panel_player4) {
        this.panel_player4 = panel_player4;
    }

    public JLabel getPlayer1_longestRoad() {
        return player1_longestRoad;
    }

    public void setPlayer1_longestRoad(JLabel player1_longestRoad) {
        this.player1_longestRoad = player1_longestRoad;
    }

    public JLabel getPlayer2_longestRoad() {
        return player2_longestRoad;
    }

    public void setPlayer2_longestRoad(JLabel player2_longestRoad) {
        this.player2_longestRoad = player2_longestRoad;
    }

    public JLabel getPlayer3_longestRoad() {
        return player3_longestRoad;
    }

    public void setPlayer3_longestRoad(JLabel player3_longestRoad) {
        this.player3_longestRoad = player3_longestRoad;
    }

    public JLabel getPlayer4_longestRoad() {
        return player4_longestRoad;
    }

    public void setPlayer4_longestRoad(JLabel player4_longestRoad) {
        this.player4_longestRoad = player4_longestRoad;
    }

    public JLabel getPlayer1_knights() {
        return player1_knights;
    }

    public void setPlayer1_knights(JLabel player1_knights) {
        this.player1_knights = player1_knights;
    }

    public JLabel getPlayer2_knights() {
        return player2_knights;
    }

    public void setPlayer2_knights(JLabel player2_knights) {
        this.player2_knights = player2_knights;
    }

    public JLabel getPlayer3_knights() {
        return player3_knights;
    }

    public void setPlayer3_knights(JLabel player3_knights) {
        this.player3_knights = player3_knights;
    }

    public JLabel getPlayer4_knights() {
        return player4_knights;
    }

    public void setPlayer4_knights(JLabel player4_knights) {
        this.player4_knights = player4_knights;
    }

    public JLabel getPlayer1_victory_point() {
        return player1_victory_point;
    }

    public void setPlayer1_victory_point(JLabel player1_victory_point) {
        this.player1_victory_point = player1_victory_point;
    }

    public JLabel getPlayer2_victory_point() {
        return player2_victory_point;
    }

    public void setPlayer2_victory_point(JLabel player2_victory_point) {
        this.player2_victory_point = player2_victory_point;
    }

    public JLabel getPlayer3_victory_point() {
        return player3_victory_point;
    }

    public void setPlayer3_victory_point(JLabel player3_victory_point) {
        this.player3_victory_point = player3_victory_point;
    }

    public JLabel getPlayer4_victory_point() {
        return player4_victory_point;
    }

    public void setPlayer4_victory_point(JLabel player4_victory_point) {
        this.player4_victory_point = player4_victory_point;
    }

    public JLabel getPlayer1_resource_cards() {
        return player1_resource_cards;
    }

    public void setPlayer1_resource_cards(JLabel player1_resource_cards) {
        this.player1_resource_cards = player1_resource_cards;
    }

    public JLabel getPlayer2_resource_cards() {
        return player2_resource_cards;
    }

    public void setPlayer2_resource_cards(JLabel player2_resource_cards) {
        this.player2_resource_cards = player2_resource_cards;
    }

    public JLabel getPlayer3_resource_cards() {
        return player3_resource_cards;
    }

    public void setPlayer3_resource_cards(JLabel player3_resource_cards) {
        this.player3_resource_cards = player3_resource_cards;
    }

    public JLabel getPlayer4_resource_cards() {
        return player4_resource_cards;
    }

    public void setPlayer4_resource_cards(JLabel player4_resource_cards) {
        this.player4_resource_cards = player4_resource_cards;
    }

    public JLabel getPlayer1_dev_cards() {
        return player1_dev_cards;
    }

    public void setPlayer1_dev_cards(JLabel player1_dev_cards) {
        this.player1_dev_cards = player1_dev_cards;
    }

    public JLabel getPlayer3_dev_cards() {
        return player3_dev_cards;
    }

    public void setPlayer3_dev_cards(JLabel player3_dev_cards) {
        this.player3_dev_cards = player3_dev_cards;
    }

    public JLabel getPlayer2_dev_cards() {
        return player2_dev_cards;
    }

    public void setPlayer2_dev_cards(JLabel player2_dev_cards) {
        this.player2_dev_cards = player2_dev_cards;
    }

    public JLabel getPlayer4_dev_cards() {
        return player4_dev_cards;
    }

    public void setPlayer4_dev_cards(JLabel player4_dev_cards) {
        this.player4_dev_cards = player4_dev_cards;
    }

    public JPanel getPanel_main_player1() {
        return panel_main_player1;
    }

    public void setPanel_main_player1(JPanel panel_main_player1) {
        this.panel_main_player1 = panel_main_player1;
    }

    public JPanel getPanel_main_player2() {
        return panel_main_player2;
    }

    public void setPanel_main_player2(JPanel panel_main_player2) {
        this.panel_main_player2 = panel_main_player2;
    }

    public JPanel getPanel_main_player3() {
        return panel_main_player3;
    }

    public void setPanel_main_player3(JPanel panel_main_player3) {
        this.panel_main_player3 = panel_main_player3;
    }

    public JPanel getPanel_main_player4() {
        return panel_main_player4;
    }

    public void setPanel_main_player4(JPanel panel_main_player4) {
        this.panel_main_player4 = panel_main_player4;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }
}
