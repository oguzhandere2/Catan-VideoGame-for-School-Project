package gui;

import game.*;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;

import lib.GraphPaperLayout;


public class MainWindow {

    private MapDesign mapDesign;
    private ControllerBar controllerBar;
    private LeftBar leftBar;
    private TopBar topBar;

    private MainMenu mainMenu;
    private ModeSelectMenu modeSelectMenu;
    private SettingsMenu settingsMenu;
    private HowToPlayMenu howToPlayMenu;
    private Container content;
    private Clip clip;
    private boolean isStarted = false;
    JFrame frame;
    ArrayList<Player> players;

    public final static int INTERVAL = 20;


    final static int SCRSIZE = 1000;

    public MainWindow(ArrayList<Player> players) {
        createAndShowGUI(players);
        startMusic();
        mapDesign = new MapDesign(players,"robber");
    }

    private void createAndShowGUI(ArrayList<Player> players) {
        this.players = players;
        frame = new JFrame("Catan the Game");

        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        try {
            frame.setContentPane(new JLabel(new ImageIcon(ImageIO.read(new File("images/background.jpg")))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.pack();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension d = new Dimension(5, 6);


        content = frame.getContentPane();
        content.setLayout(new GraphPaperLayout(d));


        mainMenu = new MainMenu(this);
        mainMenu.setMenuVisible(true);
        content.add(mainMenu.getMainMenuPanel(), new Rectangle(0, 0, 5, 6));

        modeSelectMenu = new ModeSelectMenu(this);
        modeSelectMenu.getModeSelectMenu().setVisible(false);
        content.add(modeSelectMenu.getModeSelectMenu(), new Rectangle(0, 0, 5, 6));

        settingsMenu = new SettingsMenu(this);
        settingsMenu.getSettingsPanel().setVisible(false);
        content.add(settingsMenu.getSettingsPanel(), new Rectangle(0, 0, 5, 6));

        howToPlayMenu = new HowToPlayMenu(this);
        howToPlayMenu.getHowToPlayPanel().setVisible(false);
        content.add(howToPlayMenu.getHowToPlayPanel(), new Rectangle(0, 0, 5, 6));

        frame.setResizable(true);
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);
    }

    public void setMapDesign(MapDesign mapDesign) {
        this.mapDesign = mapDesign;
    }

    public ControllerBar getControllerBar() {
        return controllerBar;
    }

    public void setControllerBar(ControllerBar controllerBar) {
        this.controllerBar = controllerBar;
    }

    public LeftBar getLeftBar() {
        return leftBar;
    }

    public void setLeftBar(LeftBar leftBar) {
        this.leftBar = leftBar;
    }

    public static int getINTERVAL() {
        return INTERVAL;
    }

    public static int getSCRSIZE() {
        return SCRSIZE;
    }

    public MapDesign getMapDesign() {
        return mapDesign;
    }

    public void startGame(String mode)
    {
        mainMenu.setMenuVisible(false);
        modeSelectMenu.getModeSelectMenu().setVisible(false);

        mapDesign = new MapDesign(players,mode);
        mapDesign.setVisible(false);
        content.add(mapDesign,new Rectangle(1,1,3,5));

        leftBar = new LeftBar();
        leftBar.getMainPanel().setVisible(false);
        content.add(leftBar.getMainPanel() ,new Rectangle(0,0,1,6));

        controllerBar = new ControllerBar(this);
        controllerBar.setVisible(false);
        content.add(controllerBar, new Rectangle(4,0,1,6));


        topBar = new TopBar();
        topBar.getMainPanel().setVisible(false);
        content.add(topBar.getMainPanel(), new Rectangle(1, 0, 3, 1));


        topBar.getMainPanel().setVisible(true);
        controllerBar.setVisible(true);
        leftBar.getMainPanel().setVisible(true);
        mapDesign.setVisible(true);
        settingsMenu.getSettingsPanel().setVisible(false);
        howToPlayMenu.getHowToPlayPanel().setVisible(false);
        mapDesign.repaint();
        Timer timer = new Timer(INTERVAL,
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        // Refresh the board
                        mapDesign.repaint();

                    }
                });

        timer.start();
    }

    public void startModeSelectMenu()
    {
        mainMenu.setMenuVisible(false);
        settingsMenu.getSettingsPanel().setVisible(false);
        howToPlayMenu.getHowToPlayPanel().setVisible(false);
        modeSelectMenu.getModeSelectMenu().setVisible(true);
    }

    public void startMainMenu()
    {

        modeSelectMenu.getModeSelectMenu().setVisible(false);
        settingsMenu.getSettingsPanel().setVisible(false);
        howToPlayMenu.getHowToPlayPanel().setVisible(false);
        mainMenu.setMenuVisible(true);
    }

    public void startMusic()
    {
            try {
                File yourFile = new File("images/background_music.wav");
                AudioInputStream stream;

                AudioFormat format;
                DataLine.Info info;

                stream = AudioSystem.getAudioInputStream(yourFile);
                format = stream.getFormat();
                info = new DataLine.Info(Clip.class, format);
                clip = (Clip) AudioSystem.getLine(info);
                clip.open(stream);
                clip.loop(10);
                clip.start();
                isStarted = true;

            } catch (Exception e) {

            }

    }

    public void stopMusic()
    {
        clip.stop();
        isStarted = false;
    }

    public void startOptionsMenu()
    {
        mainMenu.setMenuVisible(false);
        settingsMenu.getSettingsPanel().setVisible(true);

    }

    public void closeGame()
    {
        System.exit(0);
    }

    public void startHowToPlayMenu()
    {

        mainMenu.setMenuVisible(false);
        modeSelectMenu.getModeSelectMenu().setVisible(false);
        settingsMenu.getSettingsPanel().setVisible(false);
        howToPlayMenu.getHowToPlayPanel().setVisible(true);
    }
    public boolean getIsStarted ()
    {
        return isStarted;
    }

    public void returnMainMenu()
    {
        topBar.getMainPanel().setVisible(false);
        controllerBar.setVisible(false);
        leftBar.getMainPanel().setVisible(false);
        mapDesign.setVisible(false);
        mainMenu.setMenuVisible(true);
    }
}
