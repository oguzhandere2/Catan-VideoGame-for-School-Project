package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ModeSelectMenu {
    private JPanel modeSelectMenu;
    private JButton angelModeButton;
    private JButton robberModeButton;
    private JButton backButton;
    private JLabel title_label;
    private final Font font = new Font("Calibri", 3, 20);
    private  final Font titleFont = new Font( "Calibri", 3, 50);
    public ModeSelectMenu( MainWindow mainWindow)
    {
        title_label.setFont(titleFont);

        modeSelectMenu.setOpaque(false);
        angelModeButton.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.startGame("angel");
            }
        });

        BufferedImage img = null;


        try {
            img = ImageIO.read(new File("images/angel.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image dimg2 = img.getScaledInstance(100, 100,
                Image.SCALE_AREA_AVERAGING);

        ImageIcon imageIcon2 = new ImageIcon(dimg2);
        angelModeButton.setIcon(imageIcon2);
        angelModeButton.setOpaque(false);
        angelModeButton.setContentAreaFilled(false);
        angelModeButton.setBorderPainted(false);
        angelModeButton.setText("Angel Mode");
        angelModeButton.setFont(font);



        robberModeButton.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.startGame("robber");
            }
        });
        robberModeButton.setText("Robber Mode");

        try {
            img = ImageIO.read(new File("images/robber.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

         dimg2 = img.getScaledInstance(100, 100,
                Image.SCALE_AREA_AVERAGING);

        imageIcon2 = new ImageIcon(dimg2);
        robberModeButton.setIcon(imageIcon2);
        robberModeButton.setOpaque(false);
        robberModeButton.setContentAreaFilled(false);
        robberModeButton.setBorderPainted(false);
        robberModeButton.setFont(font);

        backButton.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.startMainMenu();
            }
        });


        try {
            img = ImageIO.read(new File("images/back.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        dimg2 = img.getScaledInstance(100, 100,
                Image.SCALE_AREA_AVERAGING);

        imageIcon2 = new ImageIcon(dimg2);
        backButton.setIcon(imageIcon2);
        backButton.setOpaque(false);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setFont(font);
    }

    public JPanel getModeSelectMenu()
    {
        return modeSelectMenu;
    }
}
