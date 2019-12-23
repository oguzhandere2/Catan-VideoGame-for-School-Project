package gui;

import map.DevelopmentCard;
import map.DevelopmentType;
import map.HexaType;
import game.GameEngine;
import game.Main;
import game.Player;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


import javax.imageio.ImageIO;
import javax.swing.*;

import lib.GraphPaperLayout;

public class ControllerBar extends JPanel {

    private ComponentsList rollPanel = new ComponentsList();
    private ComponentsList mainPanel = new ComponentsList();
    private ComponentsList winPanel = new ComponentsList();
    private JButton quitButton = new JButton();
    private ComponentsList buyPanel = new ComponentsList();
    private ComponentsList tradePanel = new ComponentsList();
    private ComponentsList choosePlayerPanel = new ComponentsList();
    private ComponentsList errorPanel = new ComponentsList();
    private ComponentsList devPanel = new ComponentsList();
    private ComponentsList resPanel = new ComponentsList();
    private HexaType resSelection;
    private boolean done = false;
    private ComponentsList stealPanel = new ComponentsList();
    private ComponentsList placePanel = new ComponentsList();
    private ComponentsList placePanel2 = new ComponentsList();

    private ComponentsList setupPanel = new ComponentsList();
    private ComponentsList inputResourcesPanel = new ComponentsList();
    private ArrayList<HexaType> inputResources = new ArrayList<>();
    private ArrayList<HexaType> offerResources = new ArrayList<>();
    private ArrayList<HexaType> sellResources = new ArrayList<>();
    private Player tradeChoice;
    //private final ExecutorService pool;
    private boolean IRPdone = true;
    private boolean secondRound = false;
    private int count = 0;

    private BuildingComponent currentPlayerBox;
    private final MainWindow display;
    private final Font font = new Font("Calibri", 3, 20);
    private int flag = 0;
    // For tracking where we are in turn; 0 = main panel or roll, 1 = trade panel, 2 = buy panel, 3 = dev card panel

    public final static int INTERVAL = 50;
    private Timer timer;

    public ControllerBar(final MainWindow display) {
        setOpaque(false);
        //pool = Executors.newSingleThreadExecutor();

        this.display = display;
        this.setLayout(new GraphPaperLayout(new Dimension(14, 24)));

        // Current player title (always in sidebar)
        //-------------------------------------------------------------------

        currentPlayerBox = new BuildingComponent(new JLabel(""), new Rectangle(2, 0, 10, 1));
        currentPlayerBox.getComponent().setFont(font);
        currentPlayerBox.getComponent().setForeground(Color.WHITE);
        setCurrentPlayer(Main.getCurrentPlayer());
        add(currentPlayerBox.getComponent(), currentPlayerBox.getRectangle());

        // Roll panel:
        //-------------------------------------------------------------------

        JButton roll = new JButton();
        roll.setOpaque(false);
        roll.setContentAreaFilled(false);
        roll.setBorderPainted(false);
        roll.setAction(new AbstractAction() {
            public void actionPerformed(ActionEvent a) {
                GameEngine g = display.getMapDesign().getGame();
/*
                if (g.gameWinner() != null) {
                    Main.setWinner(g.gameWinner());
                    System.out.println("game winnera girdi");
                    winPanel();
                    flag = 4;
                }*/
                if (flag != 4) {
                    int roll = g.roll();
                    if (g.getMode().equals("robber")) {
                        if (roll != 7) {
                            mainPanel();
                        } else {
                            int remove = 0;
                            if (Main.getPlayer(0).getNoOfAllResources() > 7)
                                remove = Main.getPlayer(0).getNoOfAllResources() / 2;
                            inputResourcesPanel(remove, Main.getPlayer(0), "Remove " + remove + " resources", false);
                            timer = new Timer(INTERVAL,
                                    new ActionListener() {
                                        public void actionPerformed(ActionEvent evt) {
                                            if (IRPdone) {
                                                timer.stop();
                                                Main.getPlayer(0).removeResources(inputResources);
                                                int remove = 0;
                                                if (Main.getPlayer(1).getNoOfAllResources() > 7)
                                                    remove = Main.getPlayer(1).getNoOfAllResources() / 2;
                                                inputResourcesPanel(remove, Main.getPlayer(1), "Remove " + remove + " resources", false);
                                                timer = new Timer(INTERVAL,
                                                        new ActionListener() {
                                                            public void actionPerformed(ActionEvent evt) {
                                                                if (IRPdone) {
                                                                    timer.stop();
                                                                    Main.getPlayer(1).removeResources(inputResources);
                                                                    int remove = 0;
                                                                    if (Main.getPlayer(2).getNoOfAllResources() > 7)
                                                                        remove = Main.getPlayer(2).getNoOfAllResources() / 2;
                                                                    inputResourcesPanel(remove, Main.getPlayer(2), "Remove " + remove + " resources", false);
                                                                    timer = new Timer(INTERVAL,
                                                                            new ActionListener() {
                                                                                public void actionPerformed(ActionEvent evt) {
                                                                                    if (IRPdone) {
                                                                                        timer.stop();
                                                                                        Main.getPlayer(2).removeResources(inputResources);
                                                                                        int remove = 0;
                                                                                        if (Main.getPlayer(3).getNoOfAllResources() > 7)
                                                                                            remove = Main.getPlayer(3).getNoOfAllResources() / 2;
                                                                                        inputResourcesPanel(remove, Main.getPlayer(3), "Remove " + remove + " resources", false);
                                                                                        timer = new Timer(INTERVAL,
                                                                                                new ActionListener() {
                                                                                                    public void actionPerformed(ActionEvent evt) {
                                                                                                        if (IRPdone) {
                                                                                                            timer.stop();
                                                                                                            Main.getPlayer(3).removeResources(inputResources);
                                                                                                            display.getMapDesign().placeModeFigure();
                                                                                                            placePanel("Move the angel...");
                                                                                                            timer = new Timer(INTERVAL,
                                                                                                                    new ActionListener() {
                                                                                                                        public void actionPerformed(ActionEvent evt) {
                                                                                                                            if (display.getMapDesign().getState() == 1) {
                                                                                                                            } else {
                                                                                                                                timer.stop();
                                                                                                                                stealPanel();
                                                                                                                            }
                                                                                                                        }
                                                                                                                    });
                                                                                                            timer.start();
                                                                                                        }
                                                                                                    }
                                                                                                });
                                                                                        timer.start();
                                                                                    }
                                                                                }
                                                                            });
                                                                    timer.start();
                                                                }
                                                            }
                                                        });
                                                timer.start();
                                            }
                                        }
                                    });
                            timer.start();

                        }
                    } else {
                        if (roll != 7) {
                            mainPanel();
                        } else {

                            ArrayList<HexaType> giftResources = new ArrayList<>();
                            giftResources.add(HexaType.BRICK);
                            giftResources.add(HexaType.WOOL);
                            giftResources.add(HexaType.GRAIN);
                            giftResources.add(HexaType.LUMBER);
                            giftResources.add(HexaType.ORE);

                            int give = 5;
                            if (Main.getPlayer(0).getNoOfAllResources() < 9) {
                                Main.getPlayer(0).addResource(giftResources);
                            }

                            timer = new Timer(INTERVAL,
                                    new ActionListener() {
                                        public void actionPerformed(ActionEvent evt) {
                                            if (IRPdone) {
                                                timer.stop();
                                                //Main.getPlayer(0).removeResources(inputResources);
                                                int give = 5;
                                                if (Main.getPlayer(1).getNoOfAllResources() < 9) {
                                                    Main.getPlayer(1).addResource(giftResources);
                                                }
                                                timer = new Timer(INTERVAL,
                                                        new ActionListener() {
                                                            public void actionPerformed(ActionEvent evt) {
                                                                if (IRPdone) {
                                                                    timer.stop();
                                                                    //Main.getPlayer(1).removeResources(inputResources);
                                                                    int give = 5;
                                                                    if (Main.getPlayer(2).getNoOfAllResources() < 9) {
                                                                        Main.getPlayer(2).addResource(giftResources);
                                                                    }
                                                                    timer = new Timer(INTERVAL,
                                                                            new ActionListener() {
                                                                                public void actionPerformed(ActionEvent evt) {
                                                                                    if (IRPdone) {
                                                                                        timer.stop();
                                                                                        int give = 5;
                                                                                        if (Main.getPlayer(3).getNoOfAllResources() < 9) {
                                                                                            Main.getPlayer(3).addResource(giftResources);
                                                                                        }
                                                                                        timer = new Timer(INTERVAL,
                                                                                                new ActionListener() {
                                                                                                    public void actionPerformed(ActionEvent evt) {
                                                                                                        if (IRPdone) {
                                                                                                            timer.stop();
                                                                                                            display.getMapDesign().placeModeFigure();
                                                                                                            placePanel("Move the angel...");
                                                                                                            timer = new Timer(INTERVAL, new ActionListener() {
                                                                                                                public void actionPerformed(ActionEvent evt) {
                                                                                                                    if (display.getMapDesign().getState() == 1) {
                                                                                                                    } else {
                                                                                                                        timer.stop();
                                                                                                                        mainPanel();
                                                                                                                    }
                                                                                                                }
                                                                                                            });
                                                                                                            timer.start();
                                                                                                        }
                                                                                                    }
                                                                                                });
                                                                                        timer.start();
                                                                                    }
                                                                                }
                                                                            });
                                                                    timer.start();
                                                                }
                                                            }
                                                        });
                                                timer.start();
                                            }
                                        }
                                    });
                            timer.start();
                        }
                    }

                    JLabel rollNumb = new JLabel("Roll value: " + roll);
                    rollNumb.setFont(font);
                    add(rollNumb, new Rectangle(2, 2, 10, 1));
                    repaint();
                    validate();
                }
            }
        });

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("images/dice_icon.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg = img.getScaledInstance(90, 90,
                Image.SCALE_AREA_AVERAGING);
        ImageIcon imageIcon = new ImageIcon(dimg);
        imageIcon = new ImageIcon(dimg);
        roll.setIcon(imageIcon);
        //roll.setText("roll the dice");
        rollPanel.add(new BuildingComponent(roll, new Rectangle(3, 5, 8, 3)));
        JLabel rolltheDice = new JLabel("Roll the dice!");
        rolltheDice.setFont(font);
        rollPanel.add(new BuildingComponent(rolltheDice,new Rectangle(4,3,6,2)));
        // Main panel:
        //-------------------------------------------------------------------

        JButton buy = new JButton(new AbstractAction() {
            public void actionPerformed(ActionEvent a) {
                buyPanel();
            }
        });
        buy.setOpaque(false);
        buy.setContentAreaFilled(false);
        buy.setBorderPainted(false);
        BufferedImage img6 = null;
        try {
            img6 = ImageIO.read(new File("images/money.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg6 = img6.getScaledInstance(90, 90,
                Image.SCALE_AREA_AVERAGING);
        ImageIcon imageIcon6 = new ImageIcon(dimg6);
        imageIcon6 = new ImageIcon(dimg6);
        buy.setIcon(imageIcon6);
        mainPanel.add(new BuildingComponent(buy, new Rectangle(3, 5, 8, 3)));

        quitButton.setAction(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
               //System.exit(0);
                display.closeGame();
            }
        });
        BufferedImage img67 = null;
        try {
            img67 = ImageIO.read(new File("images/quit.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg67 = img67.getScaledInstance(50, 50,
                Image.SCALE_AREA_AVERAGING);
        ImageIcon imageIcon67 = new ImageIcon(dimg67);
        imageIcon67 = new ImageIcon(dimg67);
        quitButton.setIcon(imageIcon67);
        quitButton.setOpaque(false);

        quitButton.setContentAreaFilled(false);
        quitButton.setBorderPainted(false);

        mainPanel.add(new BuildingComponent(quitButton, new Rectangle(9, 21, 3, 3)));
        JButton trade = new JButton(new AbstractAction() {
            public void actionPerformed(ActionEvent a) {
                tradePanel();
            }
        });
        trade.setOpaque(false);
        trade.setContentAreaFilled(false);
        trade.setBorderPainted(false);
        BufferedImage img3 = null;
        try {
            img3 = ImageIO.read(new File("images/trade.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg3 = img3.getScaledInstance(90, 90,
                Image.SCALE_AREA_AVERAGING);
        ImageIcon imageIcon3 = new ImageIcon(dimg3);
        imageIcon3 = new ImageIcon(dimg3);
        trade.setIcon(imageIcon3);
        trade.setOpaque(false);
        mainPanel.add(new BuildingComponent(trade, new Rectangle(3, 9, 8, 3)));

        JButton dev = new JButton(new AbstractAction() {
            public void actionPerformed(ActionEvent a) {
                devPanel();
            }
        });
        dev.setOpaque(false);
        dev.setContentAreaFilled(false);
        dev.setBorderPainted(false);
        BufferedImage img12 = null;
        try {
            img12 = ImageIO.read(new File("images/play_dev_card.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg12 = img12.getScaledInstance(90, 90,
                Image.SCALE_AREA_AVERAGING);
        ImageIcon imageIcon12 = new ImageIcon(dimg12);
        imageIcon12 = new ImageIcon(dimg12);
        dev.setIcon(imageIcon12);
        dev.setOpaque(false);
        mainPanel.add(new BuildingComponent(dev, new Rectangle(3, 13, 8, 3)));

        JButton endTurn = new JButton(new AbstractAction() {
            public void actionPerformed(ActionEvent a) {

                GameEngine g = display.getMapDesign().getGame();


                if (g.gameWinner() != null) {
                    Main.setWinner(g.gameWinner());
                    winPanel();
                    flag = 4;
                }
                if (flag != 4) {
                    // Check for largest army
                    if (Main.getCurrentPlayer().getNoOfKnights() >= 3) {

                        int currMax = Main.getCurrentPlayer().getNoOfKnights();
                        Player largestArmy = Main.getCurrentPlayer();
                        Player oldLargestArmy = null;

                        for (int i = 0; i < Main.getNumbPlayers(); i++) {
                            Player p = Main.getPlayer(i);

                            if (p.isLargestArmy()) {
                                oldLargestArmy = p;
                            }

                            if (p.getNoOfKnights() > currMax) {
                                largestArmy = p;
                                currMax = p.getNoOfKnights();
                            }
                        }

                        if (oldLargestArmy != null && oldLargestArmy != largestArmy) {
                            oldLargestArmy.setLargestArmy(false);
                        }

                        largestArmy.setLargestArmy(true);
                    }


                /*if (g.gameWinner()!=null) {
                    Main.setWinner(g.gameWinner());

                    //winPanel();

                }*/

                    Main.nextTurn();
                    rollPanel();
                }
            }
        });
        endTurn.setOpaque(false);
        endTurn.setContentAreaFilled(false);
        endTurn.setBorderPainted(false);
        BufferedImage img13 = null;
        try {
            img13 = ImageIO.read(new File("images/next_turn.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg13 = img13.getScaledInstance(90, 90,
                Image.SCALE_AREA_AVERAGING);
        ImageIcon imageIcon13 = new ImageIcon(dimg13);
        imageIcon13 = new ImageIcon(dimg13);
        endTurn.setIcon(imageIcon13);
        endTurn.setOpaque(false);
        mainPanel.add(new BuildingComponent(endTurn, new Rectangle(3, 18, 8, 3)));

        // Trade panel:
        //-------------------------------------------------------------------

        JLabel tradeText = new JLabel("Trade with...");
        tradePanel.add(new BuildingComponent(tradeText, new Rectangle(4, 4, 6, 2)));

        // Trade with players
        JButton tradePlayer = new JButton(new AbstractAction() {
            public void actionPerformed(ActionEvent a) {
                choosePlayerPanel();
            }
        });
        tradePlayer.setOpaque(false);
        tradePlayer.setContentAreaFilled(false);
        tradePlayer.setBorderPainted(false);
        BufferedImage img4 = null;
        try {
            img4 = ImageIO.read(new File("images/tradeBwPlayer.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg4 = img4.getScaledInstance(90, 90, Image.SCALE_AREA_AVERAGING);
        ImageIcon imageIcon4 = new ImageIcon(dimg4);
        imageIcon4 = new ImageIcon(dimg4);
        tradePlayer.setIcon(imageIcon4);
        tradePanel.add(new BuildingComponent(tradePlayer, new Rectangle(1, 6, 6, 2)));

        // Trade with stock
        JButton tradeStock = new JButton(new AbstractAction() {
            public void actionPerformed(ActionEvent a) {
                resPanel();
                timer = new Timer(INTERVAL,
                        new ActionListener() {
                            public void actionPerformed(ActionEvent evt) {
                                if (done) {
                                    timer.stop();
                                    done = false;
                                    final HexaType res = resSelection;
                                    inputResourcesPanel(-1, Main.getCurrentPlayer(), "Sell resources", false);
                                    timer = new Timer(INTERVAL,
                                            new ActionListener() {
                                                public void actionPerformed(ActionEvent evt) {
                                                    if (IRPdone) {
                                                        timer.stop();
                                                        display.getMapDesign().getGame().tradeWithBank(Main.getCurrentPlayer(), res, inputResources);
                                                        mainPanel();
                                                    }
                                                }
                                            });
                                    timer.start();
                                }
                            }
                        });
                timer.start();
            }
        });
        tradeStock.setOpaque(false);
        tradeStock.setContentAreaFilled(false);
        tradeStock.setBorderPainted(false);
        BufferedImage img5 = null;
        try {
            img5 = ImageIO.read(new File("images/bank.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg5 = img5.getScaledInstance(90, 90,
                Image.SCALE_AREA_AVERAGING);
        ImageIcon imageIcon5 = new ImageIcon(dimg5);
        imageIcon5 = new ImageIcon(dimg5);
        tradeStock.setIcon(imageIcon5);
        tradePanel.add(new BuildingComponent(tradeStock, new Rectangle(7, 6, 6, 2)));

        // Return to main panel
        JButton returnMain = new JButton(new AbstractAction() {
            public void actionPerformed(ActionEvent a) {
                mainPanel();
            }
        });
        returnMain.setOpaque(false);
        returnMain.setContentAreaFilled(false);
        returnMain.setBorderPainted(false);
        BufferedImage img11 = null;
        try {
            img11 = ImageIO.read(new File("images/back.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg11 = img11.getScaledInstance(90, 90,
                Image.SCALE_AREA_AVERAGING);
        ImageIcon imageIcon11 = new ImageIcon(dimg11);
        imageIcon11 = new ImageIcon(dimg11);
        returnMain.setIcon(imageIcon11);
        tradePanel.add(new BuildingComponent(returnMain, new Rectangle(3, 11, 8, 2)));

        // Buy panel:
        //-------------------------------------------------------------------

        JLabel buyText = new JLabel("Buy a...");
        buyPanel.add(new BuildingComponent(buyText, new Rectangle(4, 4, 6, 2)));

        // Buy settlement
        JButton buySettlement = new JButton(new AbstractAction() {
            public void actionPerformed(ActionEvent a) {
                GameEngine g = display.getMapDesign().getGame();

                boolean bought = g.purchaseSettlement(Main.getCurrentPlayer());

                if (bought) {
                    display.getMapDesign().placeSettlement(1);
                    placePanel("Place a settlement...");
                    //placePanel(0);
                    timer = new Timer(INTERVAL,
                            new ActionListener() {
                                public void actionPerformed(ActionEvent evt) {
                                    if (display.getMapDesign().getState() == 2) {

                                    } else {
                                        buyPanel();
                                        timer.stop();
                                    }
                                }
                            });
                    timer.start();
                } else {
                    errorPanel("Not enough resource!!");
                }

            }

        });
        buySettlement.setOpaque(false);
        buySettlement.setContentAreaFilled(false);
        buySettlement.setBorderPainted(false);
        BufferedImage img7 = null;
        try {
            img7 = ImageIO.read(new File("images/settlement.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg7 = img7.getScaledInstance(90, 90,
                Image.SCALE_AREA_AVERAGING);
        ImageIcon imageIcon7 = new ImageIcon(dimg7);
        imageIcon7 = new ImageIcon(dimg7);
        buySettlement.setIcon(imageIcon7);
        buyPanel.add(new BuildingComponent(buySettlement, new Rectangle(1, 6, 6, 2)));

        // Buy city
        JButton buyCity = new JButton(new AbstractAction() {
            public void actionPerformed(ActionEvent a) {
                GameEngine g = display.getMapDesign().getGame();

                boolean bought = g.purchaseCity(Main.getCurrentPlayer());

                if (bought) {
                    display.getMapDesign().placeCity(1);
                    placePanel("Select a settlement...");
                    //placePanel(1);
                    timer = new Timer(INTERVAL,
                            new ActionListener() {
                                public void actionPerformed(ActionEvent evt) {
                                    if (display.getMapDesign().getState() == 4) {

                                    } else {
                                        buyPanel();
                                        timer.stop();
                                    }
                                }
                            });
                    timer.start();
                } else {
                    errorPanel("Not enough resource!!");
                }
            }
        });
        buyCity.setOpaque(false);
        buyCity.setContentAreaFilled(false);
        buyCity.setBorderPainted(false);
        BufferedImage img8 = null;
        try {
            img8 = ImageIO.read(new File("images/city.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg8 = img8.getScaledInstance(90, 90,
                Image.SCALE_AREA_AVERAGING);
        ImageIcon imageIcon8 = new ImageIcon(dimg8);
        imageIcon8 = new ImageIcon(dimg8);
        buyCity.setIcon(imageIcon8);

        buyPanel.add(new BuildingComponent(buyCity, new Rectangle(7, 6, 6, 2)));

        // Buy road
        JButton buyRoad = new JButton(new AbstractAction() {
            public void actionPerformed(ActionEvent a) {
                GameEngine g = display.getMapDesign().getGame();

                boolean bought = g.purchaseRoad(Main.getCurrentPlayer());

                if (bought) {
                    display.getMapDesign().placeRoad(1);
                    placePanel("Place a road...");
                    //placePanel(2);
                    timer = new Timer(INTERVAL,
                            new ActionListener() {
                                public void actionPerformed(ActionEvent evt) {
                                    if (display.getMapDesign().getState() == 3) {

                                    } else {
                                        buyPanel();
                                        timer.stop();
                                    }
                                }
                            });
                    timer.start();
                } else {
                    errorPanel("Not enough resource!!");
                }
            }
        });
        buyRoad.setOpaque(false);
        buyRoad.setContentAreaFilled(false);
        buyRoad.setBorderPainted(false);
        BufferedImage img9 = null;
        try {
            img9 = ImageIO.read(new File("images/road.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg9 = img9.getScaledInstance(90, 90,
                Image.SCALE_AREA_AVERAGING);
        ImageIcon imageIcon9 = new ImageIcon(dimg9);
        imageIcon9 = new ImageIcon(dimg9);
        buyRoad.setIcon(imageIcon9);
        buyPanel.add(new BuildingComponent(buyRoad, new Rectangle(1, 9, 6, 2)));

        // Buy devcard
        JButton buyCard = new JButton(new AbstractAction() {
            public void actionPerformed(ActionEvent a) {
                GameEngine g = display.getMapDesign().getGame();

                boolean bought = g.purchaseDevelopmentCard(Main.getCurrentPlayer());

                if (bought) {
                    DevelopmentCard dC = g.getCards().drawCard();
                    Main.getCurrentPlayer().addDevelopmentCard(dC);

                    buyPanel();
                } else {
                    errorPanel("Not enough resource!!");
                }
            }
        });
        buyCard.setOpaque(false);
        buyCard.setContentAreaFilled(false);
        buyCard.setBorderPainted(false);
        BufferedImage img10 = null;
        try {
            img10 = ImageIO.read(new File("images/dev_card.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg10 = img10.getScaledInstance(90, 90,
                Image.SCALE_AREA_AVERAGING);
        ImageIcon imageIcon10 = new ImageIcon(dimg10);
        imageIcon10 = new ImageIcon(dimg10);
        buyCard.setIcon(imageIcon10);
        buyPanel.add(new BuildingComponent(buyCard, new Rectangle(7, 9, 6, 2)));

        // Return to main panel
        buyPanel.add(new BuildingComponent(returnMain, new Rectangle(3, 15, 8, 2)));

        // Error panel:
        //-------------------------------------------------------------------

        JLabel message = new JLabel("");
        message.setFont(font);
        errorPanel.add(message, new Rectangle(2, 4, 10, 1));

        JButton accept = new JButton(new AbstractAction() {
            public void actionPerformed(ActionEvent a) {
                switch (flag) {
                    case 0:
                        setPanel(mainPanel);
                        break;
                    case 1:
                        setPanel(tradePanel);
                        break;
                    case 2:
                        setPanel(buyPanel);
                        break;
                    case 3:
                        setPanel(devPanel);
                        break;
                    case 4:
                        setPanel(winPanel);
                        break;
                    default:
                        setPanel(mainPanel);
                        break;
                }
            }
        });
        accept.setOpaque(false);
        accept.setContentAreaFilled(false);
        accept.setBorderPainted(false);
        BufferedImage img140 = null;
        try {
            img140 = ImageIO.read(new File("images/back.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg140 = img140.getScaledInstance(90, 90,
                Image.SCALE_AREA_AVERAGING);
        ImageIcon imageIcon140 = new ImageIcon(dimg140);
        imageIcon140 = new ImageIcon(dimg140);
        accept.setIcon(imageIcon140);
        errorPanel.add(accept, new Rectangle(3, 7, 9, 2));

        // Dev card panel:
        //-------------------------------------------------------------------

        JLabel devCard = new JLabel("Play a...");
        message.setFont(font);
        devPanel.add(devCard, new Rectangle(4, 4, 6, 2));

        // Play a knight card
        JButton knight = new JButton(new AbstractAction() {
            public void actionPerformed(ActionEvent a) {
                GameEngine g = display.getMapDesign().getGame();

                if (Main.getCurrentPlayer().hasDevelopmentCard(DevelopmentType.KNIGHT)) {
                    //Main.getCurrentPlayer().removeDevelopmentCard(DevelopmentType.KNIGHT);
                    //Main.getCurrentPlayer().addKnight();

                    display.getMapDesign().placeModeFigure();
                    placePanel("Move the robber...");
                    timer = new Timer(INTERVAL,
                            new ActionListener() {
                                public void actionPerformed(ActionEvent evt) {
                                    if (display.getMapDesign().getState() == 1) {
                                    } else {
                                        timer.stop();
                                        Main.getCurrentPlayer().removeDevelopmentCard(DevelopmentType.KNIGHT);
                                        Main.getCurrentPlayer().addKnight();

                                        //Choose player to steal from (JComboBox)

                                        stealPanel();
                                    }
                                }
                            });
                    timer.start();
                } else {
                    errorPanel("You don't own this card!");
                }
            }


        });
        knight.setOpaque(false);
        knight.setContentAreaFilled(false);
        knight.setBorderPainted(false);
        BufferedImage img14 = null;
        try {
            img14 = ImageIO.read(new File("images/knight.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg14 = img14.getScaledInstance(90, 90,
                Image.SCALE_AREA_AVERAGING);
        ImageIcon imageIcon14 = new ImageIcon(dimg14);
        imageIcon14 = new ImageIcon(dimg14);
        knight.setIcon(imageIcon14);
        devPanel.add(knight, new Rectangle(1, 6, 6, 2));

        // Play a monopoly card
        JButton monopoly = new JButton(new AbstractAction() {
            public void actionPerformed(ActionEvent a) {
                if (Main.getCurrentPlayer().hasDevelopmentCard(DevelopmentType.MONOPOLY)) {
                    Main.getCurrentPlayer().removeDevelopmentCard(DevelopmentType.MONOPOLY);

                    resPanel();
                    timer = new Timer(INTERVAL,
                            new ActionListener() {
                                public void actionPerformed(ActionEvent evt) {
                                    if (done) {
                                        timer.stop();
                                        done = false;

                                        GameEngine g = display.getMapDesign().getGame();
                                        g.playMonopoly(Main.getCurrentPlayer(), resSelection);

                                        mainPanel();
                                    }
                                }
                            });
                    timer.start();

                } else {
                    errorPanel("You don't own this card!");
                }
            }
        });
        monopoly.setOpaque(false);
        monopoly.setContentAreaFilled(false);
        monopoly.setBorderPainted(false);
        BufferedImage img15 = null;
        try {
            img15 = ImageIO.read(new File("images/monopoly.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg15 = img15.getScaledInstance(90, 90,
                Image.SCALE_AREA_AVERAGING);
        ImageIcon imageIcon15 = new ImageIcon(dimg15);
        imageIcon15 = new ImageIcon(dimg15);
        monopoly.setIcon(imageIcon15);
        devPanel.add(new BuildingComponent(monopoly, new Rectangle(8, 6, 6, 2)));

        // Play a year of plenty card
        JButton year = new JButton(new AbstractAction() {
            public void actionPerformed(ActionEvent a) {
                if (Main.getCurrentPlayer().hasDevelopmentCard(DevelopmentType.PLENTY)) {
                    Main.getCurrentPlayer().removeDevelopmentCard(DevelopmentType.PLENTY);
                    inputResourcesPanel(2, Main.getCurrentPlayer(), "Choose 2 resources", true);
                    timer = new Timer(INTERVAL,
                            new ActionListener() {
                                public void actionPerformed(ActionEvent evt) {
                                    if (IRPdone) {
                                        timer.stop();
                                        Main.getCurrentPlayer().addResource(inputResources);
                                        mainPanel();
                                    }
                                }
                            });
                    timer.start();
                } else {
                    errorPanel("You don't own this card!");
                }
            }
        });
        year.setOpaque(false);
        year.setContentAreaFilled(false);
        year.setBorderPainted(false);
        BufferedImage img18 = null;
        try {
            img18 = ImageIO.read(new File("images/plenty.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg18 = img18.getScaledInstance(90, 90,
                Image.SCALE_AREA_AVERAGING);
        ImageIcon imageIcon18 = new ImageIcon(dimg18);
        imageIcon18 = new ImageIcon(dimg18);
        year.setIcon(imageIcon18);
        devPanel.add(new BuildingComponent(year, new Rectangle(1, 9, 6, 2)));

        // Play a road building card
        JButton road = new JButton(new AbstractAction() {
            public void actionPerformed(ActionEvent a) {
                GameEngine g = display.getMapDesign().getGame();
                if (Main.getCurrentPlayer().hasDevelopmentCard(DevelopmentType.ROADBUILDING)) {
                    Main.getCurrentPlayer().removeDevelopmentCard(DevelopmentType.ROADBUILDING);

                    display.getMapDesign().placeRoad(2);
                    placePanel("Place 2 roads...");

                    timer = new Timer(INTERVAL,
                            new ActionListener() {
                                public void actionPerformed(ActionEvent evt) {
                                    if (display.getMapDesign().getState() == 3) {
                                    } else {
                                        mainPanel();
                                        timer.stop();
                                    }
                                }
                            });
                    timer.start();
                } else {
                    errorPanel("You don't own this card!");
                }
            }
        });
        road.setOpaque(false);
        road.setContentAreaFilled(false);
        road.setBorderPainted(false);
        BufferedImage img17 = null;
        try {
            img17 = ImageIO.read(new File("images/road.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg17 = img17.getScaledInstance(90, 90,
                Image.SCALE_AREA_AVERAGING);
        ImageIcon imageIcon17 = new ImageIcon(dimg17);
        imageIcon17 = new ImageIcon(dimg17);
        road.setIcon(imageIcon17);
        devPanel.add(new BuildingComponent(road, new Rectangle(8, 9, 6, 2)));

        JButton judge = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameEngine g = display.getMapDesign().getGame();
                if (Main.getCurrentPlayer().hasDevelopmentCard(DevelopmentType.JUDGE)) {
                    g.playJudge();
                } else {
                    errorPanel("You don't own this card!");
                }
            }
        });
        judge.setOpaque(false);
        judge.setContentAreaFilled(false);
        judge.setBorderPainted(false);
        BufferedImage img16 = null;
        try {
            img16 = ImageIO.read(new File("images/judge.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg16 = img16.getScaledInstance(90, 90,
                Image.SCALE_AREA_AVERAGING);
        ImageIcon imageIcon16 = new ImageIcon(dimg16);
        imageIcon16 = new ImageIcon(dimg16);
        judge.setIcon(imageIcon16);
        devPanel.add(new BuildingComponent(judge, new Rectangle(4, 12, 8, 2)));
        if(getHeight() > 5) {
            getHeight();
        }
        else{
        }
        devPanel.add(new BuildingComponent(returnMain, new Rectangle(4, 15, 8, 2)));

        JComboBox<Player> playerStealBox = new JComboBox<Player>();
        playerStealBox.setAction(new AbstractAction() {
            public void actionPerformed(ActionEvent a) {
                JComboBox<Player> cb = (JComboBox) a.getSource();
                Player playerSteal = (Player) cb.getSelectedItem();
                display.getMapDesign().getGame().stealCard(Main.getCurrentPlayer(), playerSteal);
                mainPanel();
            }
        });
        if(getHeight() > 5) {
            getHeight();
        }
        else{
        }

        stealPanel.add(playerStealBox, new Rectangle(3, 6, 8, 2));

        JComboBox<Player> playerTradeBox = new JComboBox<Player>();
        playerTradeBox.setAction(new AbstractAction() {
            public void actionPerformed(ActionEvent a) {
                JComboBox<Player> cb = (JComboBox) a.getSource();
                tradeChoice = (Player) cb.getSelectedItem();
                inputResourcesPanel(-1, Main.getCurrentPlayer(), "Offer resources", false);
                timer = new Timer(INTERVAL,
                        new ActionListener() {
                            public void actionPerformed(ActionEvent evt) {
                                if (IRPdone) {
                                    timer.stop();
                                    if(getHeight() > 5) {
                                        getHeight();
                                    }
                                    else{
                                    }
                                    offerResources = inputResources;
                                    inputResourcesPanel(-1, tradeChoice, "Sell resources", false);
                                    timer = new Timer(INTERVAL,
                                            new ActionListener() {
                                                public void actionPerformed(ActionEvent evt) {
                                                    if (IRPdone) {
                                                        timer.stop();
                                                        sellResources = inputResources;
                                                        if(getHeight() > 5) {
                                                            getHeight();
                                                        }
                                                        else{
                                                        }
                                                        display.getMapDesign().getGame().tradeBwPlayers(Main.getCurrentPlayer(), tradeChoice, offerResources, sellResources);
                                                        mainPanel();
                                                    }
                                                }
                                            });
                                    timer.start();
                                }
                            }
                        });
                if(getHeight() > 5) {
                    getHeight();
                }
                else{
                }
                timer.start();
            }
        });

        playerTradeBox.setOpaque(false);
        choosePlayerPanel.add(playerTradeBox, new Rectangle(3, 6, 8, 2));


        JButton wool = new JButton(new AbstractAction() {
            public void actionPerformed(ActionEvent a) {
                resSelection = HexaType.WOOL;
                done = true;
            }
        });
        wool.setOpaque(false);
        wool.setContentAreaFilled(false);
        wool.setBorderPainted(false);
        BufferedImage img19 = null;
        try {
            img19 = ImageIO.read(new File("images/resource_wool.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg19 = img19.getScaledInstance(90, 90,
                Image.SCALE_AREA_AVERAGING);
        ImageIcon imageIcon19 = new ImageIcon(dimg19);
        imageIcon19 = new ImageIcon(dimg19);
        wool.setIcon(imageIcon19);
        resPanel.add(wool, new Rectangle(4, 6, 6, 2));

        JButton grain = new JButton(new AbstractAction() {
            public void actionPerformed(ActionEvent a) {
                resSelection = HexaType.GRAIN;
                done = true;
            }
        });
        grain.setOpaque(false);
        grain.setContentAreaFilled(false);
        grain.setBorderPainted(false);
        BufferedImage img20 = null;
        try {
            img20 = ImageIO.read(new File("images/resource_grain.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg20 = img20.getScaledInstance(90, 90,
                Image.SCALE_AREA_AVERAGING);
        ImageIcon imageIcon20 = new ImageIcon(dimg20);
        imageIcon20 = new ImageIcon(dimg20);
        grain.setIcon(imageIcon20);

        resPanel.add(grain, new Rectangle(4, 8, 6, 2));

        JButton lumber = new JButton(new AbstractAction() {
            public void actionPerformed(ActionEvent a) {
                resSelection = HexaType.LUMBER;
                done = true;
            }
        });
        lumber.setOpaque(false);
        lumber.setContentAreaFilled(false);
        lumber.setBorderPainted(false);
        BufferedImage img23 = null;
        try {
            img23 = ImageIO.read(new File("images/resource_lumber.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg23 = img23.getScaledInstance(90, 90,
                Image.SCALE_AREA_AVERAGING);
        ImageIcon imageIcon23 = new ImageIcon(dimg23);
        imageIcon23 = new ImageIcon(dimg23);
        lumber.setIcon(imageIcon23);

        resPanel.add(lumber, new Rectangle(4, 10, 6, 2));

        JButton ore = new JButton(new AbstractAction() {
            public void actionPerformed(ActionEvent a) {
                resSelection = HexaType.ORE;
                done = true;
            }
        });
        ore.setOpaque(false);
        ore.setContentAreaFilled(false);
        ore.setBorderPainted(false);
        BufferedImage img21 = null;
        try {
            img21 = ImageIO.read(new File("images/resource_ore.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg21 = img21.getScaledInstance(90, 90,
                Image.SCALE_AREA_AVERAGING);
        ImageIcon imageIcon21 = new ImageIcon(dimg21);
        imageIcon21 = new ImageIcon(dimg21);
        ore.setIcon(imageIcon21);
        resPanel.add(ore, new Rectangle(4, 12, 6, 2));

        JButton brick = new JButton(new AbstractAction() {
            public void actionPerformed(ActionEvent a) {
                resSelection = HexaType.BRICK;
                done = true;
            }
        });
        brick.setOpaque(false);
        brick.setContentAreaFilled(false);
        brick.setBorderPainted(false);
        BufferedImage img22 = null;
        try {
            img22 = ImageIO.read(new File("images/resource_brick.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg22 = img22.getScaledInstance(90, 90,
                Image.SCALE_AREA_AVERAGING);
        ImageIcon imageIcon22 = new ImageIcon(dimg22);
        imageIcon22 = new ImageIcon(dimg22);
        brick.setIcon(imageIcon22);
        resPanel.add(brick, new Rectangle(4, 14, 6, 2));
        JLabel tradeRequest = new JLabel("Request resource");
        tradeRequest.setFont(font);
        resPanel.add(new BuildingComponent(tradeRequest,new Rectangle(4,3,6,2)));
        // Place panel
        //-------------------------------------------------------------------

        JLabel mess = new JLabel();
        mess.setFont(font);
        placePanel.add(mess, new Rectangle(2, 8, 10, 4));

        // Setup panel
        //-------------------------------------------------------------------

        final JLabel start = new JLabel("Player " + Main.getCurrentPlayer().getName() + ", start the journey!");
        start.setFont(font);
        setupPanel.add(new BuildingComponent(start, new Rectangle(2, 3, 10, 2)));
        setupPanel.add(new BuildingComponent(quitButton, new Rectangle(5, 20, 5, 2)));
        JButton begin = new JButton();

        begin.setAction(new AbstractAction() {
            public void actionPerformed(ActionEvent a) {
                //System.out.println(count);
                if (!secondRound) {

                    if (count == Main.getNumbPlayers() - 1) {

                        secondRound = true;
                        count++;
                        //Place capitol commandblock
                        display.getMapDesign().placeSettlementNoRoad(1);
                        placePanel("Place first settlement!");

                        timer = new Timer(INTERVAL,
                                new ActionListener() {
                                    public void actionPerformed(ActionEvent evt) {
                                        if (display.getMapDesign().getState() == 5) {

                                        } else {
                                            timer.stop();
                                            //Place Road commandblock
                                            display.getMapDesign().placeRoad(1);
                                            placePanel("Place a road!");
                                            //placePanel(2);
                                            timer = new Timer(INTERVAL,
                                                    new ActionListener() {
                                                        public void actionPerformed(ActionEvent evt) {
                                                            if (display.getMapDesign().getState() == 3) {

                                                            } else {
                                                                timer.stop();
                                                                setCurrentPlayer(Main.getCurrentPlayer());
                                                                start.setText("Place your town, Player " + Main.getCurrentPlayer().getName() + "!");
                                                                setupPanel();
                                                            }
                                                        }
                                                    });
                                            timer.start();
                                            //
                                        }
                                    }
                                });
                        timer.start();
                        //
                    } else {
                        count++;
                        //Place SettlementNoRoad commandblock
                        display.getMapDesign().placeSettlementNoRoad(1);
                        placePanel("Place first settlement!");
                        timer = new Timer(INTERVAL,
                                new ActionListener() {
                                    public void actionPerformed(ActionEvent evt) {
                                        if (display.getMapDesign().getState() == 5) {

                                        } else {
                                            timer.stop();
                                            //Place Road commandblock
                                            display.getMapDesign().placeRoad(1);
                                            placePanel("Place a road!");
                                            timer = new Timer(INTERVAL,
                                                    new ActionListener() {
                                                        public void actionPerformed(ActionEvent evt) {
                                                            if (display.getMapDesign().getState() == 3) {

                                                            } else {
                                                                timer.stop();
                                                                Main.nextTurn();
                                                                setCurrentPlayer(Main.getCurrentPlayer());
                                                                start.setText("Place your town, Player " + Main.getCurrentPlayer().getName() + "!");
                                                                setupPanel();
                                                            }
                                                        }
                                                    });
                                            timer.start();
                                            //
                                        }
                                    }
                                });
                        timer.start();
                        //
                    }
                } else {

                    if (count == 1) {
                        //Place capitol commandblock
                        display.getMapDesign().placeCapitol();
                        placePanel("Place your settlement!");
                        timer = new Timer(INTERVAL,
                                new ActionListener() {
                                    public void actionPerformed(ActionEvent evt) {
                                        if (display.getMapDesign().getState() == 5) {

                                        } else {
                                            timer.stop();
                                            //Place Road commandblock
                                            display.getMapDesign().placeRoad(1);
                                            placePanel("Place a road!");
                                            timer = new Timer(INTERVAL,
                                                    new ActionListener() {
                                                        public void actionPerformed(ActionEvent evt) {
                                                            if (display.getMapDesign().getState() == 3) {

                                                            } else {
                                                                timer.stop();
                                                                //Collections.shuffle(GameRunner.players);
                                                                setCurrentPlayer(Main.getCurrentPlayer());
                                                                rollPanel();
                                                            }
                                                        }
                                                    });
                                            timer.start();
                                            //
                                        }
                                    }
                                });
                        timer.start();
                        //
                    } else {
                        count--;
                        //Place capitol commandblock
                        display.getMapDesign().placeCapitol();
                        placePanel("Place your settlement!");
                        timer = new Timer(INTERVAL,
                                new ActionListener() {
                                    public void actionPerformed(ActionEvent evt) {
                                        if (display.getMapDesign().getState() == 5) {

                                        } else {
                                            timer.stop();
                                            //Place Road commandblock
                                            display.getMapDesign().placeRoad(1);
                                            placePanel("Place a road!");
                                            timer = new Timer(INTERVAL,
                                                    new ActionListener() {
                                                        public void actionPerformed(ActionEvent evt) {
                                                            if (display.getMapDesign().getState() == 3) {

                                                            } else {
                                                                timer.stop();
                                                                Main.previousPlayer();
                                                                setCurrentPlayer(Main.getCurrentPlayer());
                                                                start.setText("Place your town, Player " + Main.getCurrentPlayer().getName() + "!");
                                                                setupPanel();
                                                            }
                                                        }
                                                    });
                                            timer.start();
                                            //
                                        }
                                    }
                                });
                        timer.start();
                        //
                    }
                }
            }
        });
        begin.setOpaque(false);
        begin.setContentAreaFilled(false);
        begin.setBorderPainted(false);
        BufferedImage img2 = null;
        try {
            img2 = ImageIO.read(new File("images/hammer.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg2 = img2.getScaledInstance(90, 90,
                Image.SCALE_AREA_AVERAGING);
        ImageIcon imageIcon2 = new ImageIcon(dimg2);
        imageIcon2 = new ImageIcon(dimg2);
        begin.setIcon(imageIcon2);
        begin.setOpaque(false);
        setupPanel.add(new BuildingComponent(begin, new Rectangle(4, 6, 6, 3)));

        // Input Resources Panel
        //-------------------------------------------------------------------

        JComboBox<Integer> brickCombo = new JComboBox<Integer>();
        brickCombo.setAction(new AbstractAction() {
            public void actionPerformed(ActionEvent a) {
            }
        });

        inputResourcesPanel.add(brickCombo, new Rectangle(2, 6, 3, 1));

        JComboBox<Integer> woolCombo = new JComboBox<Integer>();
        woolCombo.setAction(new AbstractAction() {
            public void actionPerformed(ActionEvent a) {
            }
        });

        inputResourcesPanel.add(woolCombo, new Rectangle(6, 6, 3, 1));

        JComboBox<Integer> oreCombo = new JComboBox<Integer>();
        oreCombo.setAction(new AbstractAction() {
            public void actionPerformed(ActionEvent a) {
            }
        });

        inputResourcesPanel.add(oreCombo, new Rectangle(10, 6, 3, 1));

        JComboBox<Integer> grainCombo = new JComboBox<Integer>();
        grainCombo.setAction(new AbstractAction() {
            public void actionPerformed(ActionEvent a) {
            }
        });

        inputResourcesPanel.add(grainCombo, new Rectangle(4, 10, 3, 1));

        JComboBox<Integer> lumberCombo = new JComboBox<Integer>();
        lumberCombo.setAction(new AbstractAction() {
            public void actionPerformed(ActionEvent a) {
            }
        });

        inputResourcesPanel.add(lumberCombo, new Rectangle(8, 10, 3, 1));

        JLabel playerLabel = new JLabel("Player: ");
        start.setFont(font);
        inputResourcesPanel.add(new BuildingComponent(playerLabel, new Rectangle(2, 3, 15, 1)));

        JButton submitResources = new JButton();
        submitResources.setText("Submit");
        inputResourcesPanel.add(submitResources, new Rectangle(3, 15, 9, 2));

        JLabel brickLabel = new JLabel();
        start.setFont(font);
        BufferedImage img25 = null;
        try {
            img25 = ImageIO.read(new File("images/resource_brick.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image dimg25 = img25.getScaledInstance(50, 50,
                Image.SCALE_AREA_AVERAGING);

        ImageIcon imageIcon25 = new ImageIcon(dimg25);
        brickLabel.setIcon(imageIcon25);
        inputResourcesPanel.add(new BuildingComponent(brickLabel, new Rectangle(2, 5, 4, 1)));

        JLabel woolLabel = new JLabel();
        start.setFont(font);
        BufferedImage img26 = null;
        try {
            img26 = ImageIO.read(new File("images/resource_wool.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image dimg26 = img26.getScaledInstance(50, 50,
                Image.SCALE_AREA_AVERAGING);

        ImageIcon imageIcon26 = new ImageIcon(dimg26);
        woolLabel.setIcon(imageIcon26);
        inputResourcesPanel.add(new BuildingComponent(woolLabel, new Rectangle(6, 5, 4, 1)));

        JLabel oreLabel = new JLabel();
        start.setFont(font);
        BufferedImage img27 = null;
        try {
            img27 = ImageIO.read(new File("images/resource_ore.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image dimg27 = img27.getScaledInstance(50, 50,
                Image.SCALE_AREA_AVERAGING);

        ImageIcon imageIcon27 = new ImageIcon(dimg27);
        oreLabel.setIcon(imageIcon27);
        inputResourcesPanel.add(new BuildingComponent(oreLabel, new Rectangle(10, 5, 4, 1)));

        JLabel grainLabel = new JLabel();
        start.setFont(font);
        BufferedImage img28 = null;
        try {
            img28 = ImageIO.read(new File("images/resource_grain.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image dimg28 = img28.getScaledInstance(50, 50,
                Image.SCALE_AREA_AVERAGING);

        ImageIcon imageIcon28 = new ImageIcon(dimg28);
        grainLabel.setIcon(imageIcon28);
        inputResourcesPanel.add(new BuildingComponent(grainLabel, new Rectangle(4, 9, 4, 1)));
        BufferedImage img29 = null;

        start.setFont(font);
        JLabel lumberLabel = new JLabel();
        try {
            img29 = ImageIO.read(new File("images/resource_lumber.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image dimg29 = img29.getScaledInstance(50, 50,
                Image.SCALE_AREA_AVERAGING);

        ImageIcon imageIcon29 = new ImageIcon(dimg29);
        lumberLabel.setIcon(imageIcon29);
        inputResourcesPanel.add(new BuildingComponent(lumberLabel, new Rectangle(8, 9, 4, 1)));
        if(getHeight() > 5) {
            getHeight();
        }
        else{
        }
        setupPanel();
    }

    public void buyPanel() {
        setPanel(buyPanel);
        flag = 2;
    }

    private void setPanel(ComponentsList cL) {
        this.removeAll();
        this.add(currentPlayerBox.getComponent(), currentPlayerBox.getRectangle());

        for (int i = 0; i < cL.size(); i++) {
            this.add(cL.get(i).getComponent(), cL.get(i).getRectangle());
        }

        repaint();
        validate();
    }

    public void rollPanel() {
        setCurrentPlayer(Main.getCurrentPlayer());
        setPanel(rollPanel);
        flag = 0;
    }

    public void mainPanel() {
        setPanel(mainPanel);
        flag = 0;
    }

    public void errorPanel(String str) {
        ((JLabel) errorPanel.get(0).getComponent()).setText(str);
        setPanel(errorPanel);
    }

    public void devPanel() {
        setPanel(devPanel);
        flag = 3;
    }



    public void resPanel() {
        setPanel(resPanel);
    }

    public void stealPanel() {
        //JComboBox<Player> newBox = new JComboBox<Player>();
        AbstractAction action = (AbstractAction) ((JComboBox<Player>) stealPanel.get(0).getComponent()).getAction();
        ((JComboBox<Player>) stealPanel.get(0).getComponent()).setAction(new AbstractAction() {
            public void actionPerformed(ActionEvent a) {

            }
        });
        ((JComboBox<Player>) stealPanel.get(0).getComponent()).removeAllItems();
        for (int i = 0; i < display.getMapDesign().getGame().getMap().getRobberAffectedPlayers().size(); i++) {
            if (display.getMapDesign().getGame().getMap().getRobberAffectedPlayers().get(i).equals(Main.getCurrentPlayer())) {
            } else {
                ((JComboBox<Player>) stealPanel.get(0).getComponent()).addItem(display.getMapDesign().getGame().getMap().getRobberAffectedPlayers().get(i));
            }
        }

        ((JComboBox<Player>) stealPanel.get(0).getComponent()).setAction(action);
        //System.out.println(((JComboBox<Player>) stealPanel.get(0).getComponent()).getItemCount());
        //System.out.println(((JComboBox<Player>) stealPanel.get(0).getComponent()).getItemAt(0));

        if (((JComboBox<Player>) stealPanel.get(0).getComponent()).getItemCount() <= 0) {
            //System.out.println("IF");
            errorPanel("No one to steal from");
        } else {
            //System.out.println("ELSE");
            setPanel(stealPanel);
        }
    }

    public void choosePlayerPanel() {
        AbstractAction action = (AbstractAction) ((JComboBox<Player>) choosePlayerPanel.get(0).getComponent()).getAction();
        // Remove action, so that removeAllItems() does not trigger an event
        ((JComboBox<Player>) choosePlayerPanel.get(0).getComponent()).setAction(new AbstractAction() {
            public void actionPerformed(ActionEvent a) {

            }
        });
        // Depopulate combo box
        ((JComboBox<Player>) choosePlayerPanel.get(0).getComponent()).removeAllItems();
        // Populate combo box
        for (int i = 0; i < Main.getNumbPlayers(); i++) {
            if (Main.getPlayer(i).equals(Main.getCurrentPlayer())) {
            } else {
                ((JComboBox<Player>) choosePlayerPanel.get(0).getComponent()).addItem(Main.getPlayer(i));
            }
        }
        // Reset action
        ((JComboBox<Player>) choosePlayerPanel.get(0).getComponent()).setAction(action);
        setPanel(choosePlayerPanel);
    }

    public void tradePanel() {
        setPanel(tradePanel);
        flag = 1;
    }

    public void placePanel(String str) {
        ((JLabel) placePanel.get(0).getComponent()).setText(str);
        setPanel(placePanel);
    }



    /**
     * @param n   Number of resources to be selected, -1 for any
     * @param p   the player inputting resources
     * @param str to display on submit button
     * @return ArrayList<String> of resources selected
     */
    public void inputResourcesPanel(final int n, final Player p, String str, final boolean YOP) {
        //final ArrayList<String> output = new ArrayList<String>();
        IRPdone = false;

        // Depopulates / Repopulates the combo boxes
        for (int i = 0; i < 5; i++) {
            ((JComboBox<Integer>) inputResourcesPanel.get(i).getComponent()).removeAllItems();
        }
        if (YOP) {
            for (int r = 0; r <= 2; r++) {
                ((JComboBox<Integer>) inputResourcesPanel.get(0).getComponent()).addItem(new Integer(r));
            }
            for (int r = 0; r <= 2; r++) {
                ((JComboBox<Integer>) inputResourcesPanel.get(1).getComponent()).addItem(new Integer(r));
            }
            for (int r = 0; r <= 2; r++) {
                ((JComboBox<Integer>) inputResourcesPanel.get(2).getComponent()).addItem(new Integer(r));
            }
            for (int r = 0; r <= 2; r++) {
                ((JComboBox<Integer>) inputResourcesPanel.get(3).getComponent()).addItem(new Integer(r));
            }
            for (int r = 0; r <= 2; r++) {
                ((JComboBox<Integer>) inputResourcesPanel.get(4).getComponent()).addItem(new Integer(r));
            }
        } else {
            for (int r = 0; r <= p.getNumberResourcesType(HexaType.BRICK); r++) {
                ((JComboBox<Integer>) inputResourcesPanel.get(0).getComponent()).addItem(new Integer(r));
            }
            for (int r = 0; r <= p.getNumberResourcesType(HexaType.WOOL); r++) {
                ((JComboBox<Integer>) inputResourcesPanel.get(1).getComponent()).addItem(new Integer(r));
            }
            for (int r = 0; r <= p.getNumberResourcesType(HexaType.ORE); r++) {
                ((JComboBox<Integer>) inputResourcesPanel.get(2).getComponent()).addItem(new Integer(r));
            }
            for (int r = 0; r <= p.getNumberResourcesType(HexaType.GRAIN); r++) {
                ((JComboBox<Integer>) inputResourcesPanel.get(3).getComponent()).addItem(new Integer(r));
            }
            for (int r = 0; r <= p.getNumberResourcesType(HexaType.LUMBER); r++) {
                ((JComboBox<Integer>) inputResourcesPanel.get(4).getComponent()).addItem(new Integer(r));
            }
        }

        //Sets player
        ((JLabel) inputResourcesPanel.get(5).getComponent()).setText("Player: " + p.getName());

        // Sets action according to flag and resourceCount
        ((JButton) inputResourcesPanel.get(6).getComponent()).setAction(new AbstractAction() {
            public void actionPerformed(ActionEvent a) {
                int sum = 0;
                for (int i = 0; i < 5; i++) {
                    sum += ((JComboBox<Integer>) inputResourcesPanel.get(i).getComponent()).getSelectedIndex();
                }
                //System.out.println(sum);
                if (n != -1) {
                    if (sum != n) {
                        return;
                    }
                }
                ArrayList<HexaType> output2 = new ArrayList<>();

                for (int i = 0; i < ((JComboBox<Integer>) inputResourcesPanel.get(0).getComponent()).getSelectedIndex(); i++) {
                    output2.add(HexaType.BRICK);
                }
                for (int i = 0; i < ((JComboBox<Integer>) inputResourcesPanel.get(1).getComponent()).getSelectedIndex(); i++) {
                    output2.add(HexaType.WOOL);
                }
                for (int i = 0; i < ((JComboBox<Integer>) inputResourcesPanel.get(2).getComponent()).getSelectedIndex(); i++) {
                    output2.add(HexaType.ORE);
                }
                for (int i = 0; i < ((JComboBox<Integer>) inputResourcesPanel.get(3).getComponent()).getSelectedIndex(); i++) {
                    output2.add(HexaType.GRAIN);
                }
                for (int i = 0; i < ((JComboBox<Integer>) inputResourcesPanel.get(4).getComponent()).getSelectedIndex(); i++) {
                    output2.add(HexaType.LUMBER);
                }

                inputResources = output2;
                IRPdone = true;
            }
        });

        // Sets submit button text
        ((JButton) inputResourcesPanel.get(6).getComponent()).setText(str);

        setPanel(inputResourcesPanel);


    }

    public void setCurrentPlayer(Player p) {
        JLabel label = (JLabel) currentPlayerBox.getComponent();
        label.setText("Player " + p.getName());
        label.setOpaque(false);
        label.setFont(font);
        label.setForeground(p.getColor());
    }

    public void setupPanel() {
        setPanel(setupPanel);
    }

    public void winPanel() {
        this.removeAll();
        JLabel win = new JLabel("Player " + Main.getWinner().getName() + " " + " wins!");
        win.setFont(new Font("Arial", 1, 24));
        winPanel.add(new BuildingComponent(win, new Rectangle(3, 5, 8, 3)));
        //this.add(win, new Rectangle(2, 4, 10, 5));
        setPanel(winPanel);
    }
}
