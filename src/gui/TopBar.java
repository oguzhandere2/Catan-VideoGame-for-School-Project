package gui;

import map.HexaType;
import game.Main;
import game.Player;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TopBar {
    private JPanel mainPanel;
    private JLabel brick_label;
    private JLabel wool_label;
    private JLabel ore_label;
    private JLabel grain_label;
    private JLabel lumber_label;
    private Timer timer;
    public final static int INTERVAL = 50;
    private final Font font = new Font("Calibri", 3, 23);

    public TopBar()
    {
        brick_label.setFont(font);
        wool_label.setFont(font);
        ore_label.setFont(font);
        grain_label.setFont(font);
        lumber_label.setFont(font);

        BufferedImage img = null;
        mainPanel.setOpaque(false);

        //SETTING THE BRICK RESOURCE ICON
        try {
            img = ImageIO.read(new File("images/resource_brick.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image dimg = img.getScaledInstance(50, 50,
                Image.SCALE_AREA_AVERAGING);

        ImageIcon imageIcon = new ImageIcon(dimg);
        brick_label.setIcon(imageIcon);

        img = null;

        //SETTING THE GRAIN RESOURCE ICON
        try {
            img = ImageIO.read(new File("images/resource_grain.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        dimg = img.getScaledInstance(50, 50,
                Image.SCALE_AREA_AVERAGING);

        imageIcon = new ImageIcon(dimg);
        grain_label.setIcon(imageIcon);

        //SETTING THE LUMBER RESOURCE ICON
        try {
            img = ImageIO.read(new File("images/resource_lumber.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        dimg = img.getScaledInstance(50, 50,
                Image.SCALE_AREA_AVERAGING);

        imageIcon = new ImageIcon(dimg);
        lumber_label.setIcon(imageIcon);

        //SETTING THE ORE RESOURCE ICON
        try {
            img = ImageIO.read(new File("images/resource_ore.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        dimg = img.getScaledInstance(50, 50,
                Image.SCALE_AREA_AVERAGING);

        imageIcon = new ImageIcon(dimg);
        ore_label.setIcon(imageIcon);

        //SETTING THE WOOL RESOURCE ICON
        try {
            img = ImageIO.read(new File("images/resource_wool.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        dimg = img.getScaledInstance(50, 50,
                Image.SCALE_AREA_AVERAGING);

        imageIcon = new ImageIcon(dimg);
        wool_label.setIcon(imageIcon);



        timer = new Timer(INTERVAL,
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        for (int i = 0; i < Main.getNumbPlayers(); i++) {
                            updatePlayer(Main.getCurrentPlayer());
                        }
                    }
                });
        timer.start();
    }
    private void updatePlayer(Player player) {
        brick_label.setText( ": " + player.getNumberResourcesType(HexaType.BRICK));
        wool_label.setText( ": " + player.getNumberResourcesType(HexaType.WOOL));
        ore_label.setText( ": " + player.getNumberResourcesType(HexaType.ORE));
        grain_label.setText( ": " + player.getNumberResourcesType(HexaType.GRAIN));
        lumber_label.setText( ": " + player.getNumberResourcesType(HexaType.LUMBER));
    }
    public JPanel getMainPanel()
    {
        return mainPanel;
    }
}


