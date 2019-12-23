package gui;

import game.GameEngine;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainMenu {
    private JPanel mainMenuPanel;
    private JButton playButton;
    private JButton optionsButton;
    private JButton howtoplayButton;
    private JButton exitButton;
    private JLabel titleLabel1;
    private GameEngine gameEngine;
    private final Font font = new Font("Arial", 5, 20);
    public MainMenu(MainWindow mainWindow)
    {
        BufferedImage img = null;

        //SETTING THE TITLE ICON
        try {
            img = ImageIO.read(new File("images/catan_title.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image dimg = img.getScaledInstance(250, 100,
                Image.SCALE_AREA_AVERAGING);

        ImageIcon imageIcon = new ImageIcon(dimg);
        titleLabel1.setIcon(imageIcon);

        mainMenuPanel.setOpaque(false);

        playButton.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.startModeSelectMenu();
            }

        });
        playButton.setText("Play a New Game");
        playButton.setOpaque(true);
        //playButton.setContentAreaFilled(false);
        playButton.setBorderPainted(true);
        playButton.setFont(font);
        playButton.setBackground(Color.yellow);
        playButton.setBorder(BorderFactory.createLineBorder(Color.black));


        optionsButton.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.startOptionsMenu();
            }
        });
        optionsButton.setText("Settings");
        optionsButton.setOpaque(true);
        //playButton.setContentAreaFilled(false);
        optionsButton.setBorderPainted(true);
        optionsButton.setFont(font);
        optionsButton.setBackground(Color.yellow);
        optionsButton.setBorder(BorderFactory.createLineBorder(Color.black));

        howtoplayButton.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.startHowToPlayMenu();
            }
        });
        howtoplayButton.setText("How to play");
        howtoplayButton.setOpaque(true);
        //playButton.setContentAreaFilled(false);
        howtoplayButton.setBorderPainted(true);
        howtoplayButton.setFont(font);
        howtoplayButton.setBackground(Color.yellow);
        howtoplayButton.setBorder(BorderFactory.createLineBorder(Color.black));

        exitButton.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.closeGame();
            }
        });
        exitButton.setText("Exit");
        exitButton.setOpaque(true);
        //playButton.setContentAreaFilled(false);
        exitButton.setBorderPainted(true);
        exitButton.setFont(font);
        exitButton.setBackground(Color.yellow);
        exitButton.setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public JPanel getMainMenuPanel()
    {
        return mainMenuPanel;
    }

    public GameEngine getGameEngine()
    {
        return gameEngine;
    }

    public void setMenuVisible(boolean bool)
    {
        mainMenuPanel.setVisible(bool);
    }

}


