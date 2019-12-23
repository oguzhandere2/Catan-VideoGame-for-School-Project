package gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SettingsMenu {
    private JButton soundOffButton;
    private JButton soundOnButton;
    private JButton backButton;
    private JPanel settingsPanel;
    private JLabel settingsTitle;
    private  final Font font = new Font( "Calibri", 3, 50);


    public SettingsMenu( MainWindow mainWindow)
    {
        settingsPanel.setOpaque(false);
        settingsTitle.setText("Settings");
        settingsTitle.setFont(font);
        soundOnButton.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ( !mainWindow.getIsStarted() )
                {
                    mainWindow.startMusic();
                }

            }
        });

        BufferedImage img = null;


        try {
            img = ImageIO.read(new File("images/sound_on.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image dimg2 = img.getScaledInstance(100, 100,
                Image.SCALE_AREA_AVERAGING);

        ImageIcon imageIcon2 = new ImageIcon(dimg2);
        soundOnButton.setIcon(imageIcon2);
        soundOnButton.setOpaque(false);
        soundOnButton.setContentAreaFilled(false);
        soundOnButton.setBorderPainted(false);

        soundOffButton.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.stopMusic();
            }
        });

        BufferedImage img2 = null;


        try {
            img2 = ImageIO.read(new File("images/mute.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image dimg3 = img2.getScaledInstance(100, 100,
                Image.SCALE_AREA_AVERAGING);

        ImageIcon imageIcon3 = new ImageIcon(dimg3);
        soundOffButton.setIcon(imageIcon3);
        soundOffButton.setOpaque(false);
        soundOffButton.setContentAreaFilled(false);
        soundOffButton.setBorderPainted(false);

        backButton.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainWindow.startMainMenu();
            }
        });
        BufferedImage img68 = null;
        try {
            img68 = ImageIO.read(new File("images/back.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg68 = img68.getScaledInstance(90, 90,
                Image.SCALE_AREA_AVERAGING);
        ImageIcon imageIcon68 = new ImageIcon(dimg68);
        imageIcon68 = new ImageIcon(dimg68);
        backButton.setIcon(imageIcon68);
        backButton.setOpaque(false);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
    }

    public JPanel getSettingsPanel()
    {
        return settingsPanel;
    }
}
