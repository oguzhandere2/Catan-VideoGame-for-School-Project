package gui;

import java.awt.event.MouseListener;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.awt.event.MouseAdapter;
import javax.imageio.ImageIO;

import game.*;

import javax.swing.*;
import java.awt.geom.Ellipse2D;

import map.*;

import java.awt.geom.AffineTransform;


public class MapDesign extends JPanel {

    private GameEngine game;
    private int state = 0;
    private int hexagonSide;
    private int boardHeight;
    private int widthMargin;
    private int heightMargin = 100;
    private final int structSize = 12;
    private final double sqrt3div2 = 0.86602540378;
    private Image image1, image2, image3, image4, image5, image6;
    private final int roadSize = 20;
    private Hexa[][] hexas;
    private ArrayList<Player> players;
    private Building[][][] buildings;
    private Road[][][] roads;
    private Image generalPortImage;
    private Image portImage;
    private Image oreImage;
    private Image brickImage;
    private Image grainImage;
    private Image woolImage;
    private String mode;
    private Image lumberImage;
    //private Graphics g;

    private int index;
    private boolean capitol = false;


    public MapDesign(ArrayList<Player> players, String mode) {
        setOpaque(false);
        game = new GameEngine(players, mode);

        hexas = game.getMap().getHexas();
        this.players = players;
        roads = game.getMap().getRoads();
        mode = game.getMode();
        buildings = game.getMap().getBuildings();

        setBackground(new Color(164, 200, 218)); //TODO add background

        this.addComponentListener(new ComponentListener() {

            public void componentShown(ComponentEvent e) {
            }

            public void componentResized(ComponentEvent e) {
                boardHeight = getHeight();
                getHeight();
                hexagonSide = (boardHeight - 2 * heightMargin) / 8;
                getHeight();
                widthMargin = (getWidth() - (int) (10 * hexagonSide * sqrt3div2)) / 2;
            }

            public void componentMoved(ComponentEvent e) {
            }

            public void componentHidden(ComponentEvent e) {
            }
        });

        MouseListener m = new AMouseListener();
        addMouseListener(m);
        //addMouseMotionListener((MouseMotionListener) m);
    }

    public void setState(int newState) {
        state = newState;
    }

    public void paintComponent(Graphics g) {

        boardHeight = getHeight();

        Graphics2D g2 = (Graphics2D) g;
        hexagonSide = (boardHeight - 2 * heightMargin) / 8;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
        widthMargin = (getWidth() - (int) (10 * hexagonSide * sqrt3div2)) / 2;
        super.paintComponent(g2);
        getHeight();

        for (int x = 1; x <= 3; x++) {
            drawHex(hexas[x][1], g2);
        }

        for (int x = 1; x <= 4; x++) {
            drawHex(hexas[x][2], g2);
        }

        for (int x = 1; x <= 5; x++) {
            drawHex(hexas[x][3], g2);
        }

        for (int x = 2; x <= 5; x++) {
            drawHex(hexas[x][4], g2);
        }

        for (int x = 3; x <= 5; x++) {
            drawHex(hexas[x][5], g2);
        }
        //draw numbers
        for (int x = 1; x <= 3; x++) {
            drawNumber(hexas[x][1], g2);
        }

        for (int x = 1; x <= 4; x++) {
            drawNumber(hexas[x][2], g2);
        }

        for (int x = 1; x <= 5; x++) {
            drawNumber(hexas[x][3], g2);
        }

        for (int x = 2; x <= 5; x++) {
            drawNumber(hexas[x][4], g2);
        }

        for (int x = 3; x <= 5; x++) {
            drawNumber(hexas[x][5], g2);
        }

        //draw robber
        for (int x = 1; x <= 3; x++) {
            drawModeFigure(hexas[x][1], g2);
        }

        for (int x = 1; x <= 4; x++) {
            drawModeFigure(hexas[x][2], g2);
        }

        for (int x = 1; x <= 5; x++) {
            drawModeFigure(hexas[x][3], g2);
        }

        for (int x = 2; x <= 5; x++) {
            drawModeFigure(hexas[x][4], g2);
        }

        for (int x = 3; x <= 5; x++) {
            drawModeFigure(hexas[x][5], g2);
        }

        int size1 = roads.length;
        int size2 = roads[0].length;
        int size3 = roads[0][0].length;

        //Draw roads
        for (int i = 0; i < size1; i++) {
            for (int k = 0; k < size2; k++) {
                for (int o = 0; o < size3; o++) {
                    drawRoad(roads[i][k][o], false, g2);
                }
            }
        }

        int size4 = buildings.length;
        int size5 = buildings[0].length;
        int size6 = buildings[0][0].length;

        //Draw structures
        for (int i = 0; i < size4; i++) {
            for (int k = 0; k < size5; k++) {
                for (int o = 0; o < size6; o++) {
                    drawBuilding(buildings[i][k][o], false, g2);
                }
            }
        }

        Point p = MouseInfo.getPointerInfo().getLocation();
        getGame();
        SwingUtilities.convertPointFromScreen(p, this);

        if (state == 1) {
            Location locatio = pointToHexa(p);
            if (!(locatio == null)) {
                highlightTile(locatio, g2);
            }
        } else if (state == 2 || state == 4 || state == 5) {
            Vertex vertex = pointToBuilding(p);

            if (vertex != null) {
                int x = vertex.getX();
                int y = vertex.getY();
                drawBuilding(buildings[x][y][vertex.getPlace()], true, g2);
            }
        } else if (state == 3) {
            Edge edge = pointToRoad(p);
            if (!(edge == null)) {
                int x = edge.getX();
                int y = edge.getY();
                drawRoad(roads[x][y][edge.getPlace()], true, g2);
            }
        }

        labelPorts(g2);
    }

    public void labelPorts(Graphics2D g2) {
        Graphics2D g2c = (Graphics2D) g2.create();
        AffineTransform transformer = new AffineTransform();
        double multiplaxer = 4.2;
        transformer.translate(widthMargin + (int) (hexagonSide * multiplaxer), heightMargin - (int) (hexagonSide * 0.2));

        g2c.transform(transformer);
        AffineTransform rotate = new AffineTransform();
        getGame();
        rotate.rotate(Math.toRadians(30));
        g2c.transform(rotate);
        g2c.setColor(Color.BLACK);
        g2c.drawString(" 2:1", 45, -115);


        try {
            portImage = ImageIO.read(new File("images/port.png"));
            woolImage = ImageIO.read(new File("images/resource_wool.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        g2c.drawImage(portImage, 35, -98, 50, 50, null);
        g2c.drawImage(woolImage, 45, -42, 40, 40, null);

        g2c = (Graphics2D) g2.create();

        //General 3:1 sağ üst
        transformer = new AffineTransform();
        transformer.translate(widthMargin + (int) (hexagonSide * 7.0), heightMargin + (int) (hexagonSide * 1.5));
        //transformer.translate(-(int) (0.4 * hexagonSide),-height/2);
        getGame();
        g2c.transform(transformer);
        rotate = new AffineTransform();
        rotate.rotate(Math.toRadians(30));
        g2c.transform(rotate);
        g2c.setColor(Color.BLACK);
        g2c.drawString(" 3:1", 20, -120);

        try {
            portImage = ImageIO.read(new File("images/port.png"));
            generalPortImage = ImageIO.read(new File("images/general_port.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        g2c.drawImage(portImage, 10, -115, 50, 50, null);
        g2c.drawImage(generalPortImage, 15, -52, 35, 35, null);

        g2c = (Graphics2D) g2.create();

        //General 3:1 en sağ
        transformer = new AffineTransform();
        transformer.translate(widthMargin + (int) (hexagonSide * 8.8), heightMargin + (int) (hexagonSide * 3.2));
        //transformer.translate(-(int) (0.4 * hexagonSide),-height/2);
        g2c.transform(transformer);
        getGame();
        rotate = new AffineTransform();
        rotate.rotate(Math.toRadians(90));
        g2c.transform(rotate);
        g2c.setColor(Color.BLACK);
        g2c.drawString("    3:1", 25, -95);

        try {
            portImage = ImageIO.read(new File("images/port.png"));
            generalPortImage = ImageIO.read(new File("images/general_port.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        g2c.drawImage(portImage, 35, -90, 50, 50, null);
        g2c.drawImage(generalPortImage, 45, -35, 35, 35, null);
        g2c = (Graphics2D) g2.create();

        //Brick 2:1 brick
        transformer = new AffineTransform();
        transformer.translate(widthMargin + (int) (hexagonSide * 7.0), heightMargin + (int) (hexagonSide * 7.0));
        //transformer.translate(-(int) (0.4 * hexagonSide),-height/2);
        g2c.transform(transformer);
        getGame();
        rotate = new AffineTransform();
        rotate.rotate(Math.toRadians(-30));
        g2c.transform(rotate);
        g2c.setColor(Color.BLACK);
        g2c.drawString(" 2:1", 35, 5);

        try {
            portImage = ImageIO.read(new File("images/port.png"));
            brickImage = ImageIO.read(new File("images/resource_brick.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        g2c.drawImage(portImage, 35, 15, 50, 50, null);
        g2c.drawImage(brickImage, 45, 75, 35, 35, null);

        g2c = (Graphics2D) g2.create();

        //Lumber 2:1
        transformer = new AffineTransform();
        transformer.translate(widthMargin + (int) (hexagonSide * multiplaxer), heightMargin + (int) (hexagonSide * 8.5));
        //transformer.translate(-(int) (0.4 * hexagonSide),-height/2);
        g2c.transform(transformer);
        getGame();
        rotate = new AffineTransform();
        rotate.rotate(Math.toRadians(-30));
        g2c.transform(rotate);
        g2c.setColor(Color.BLACK);
        g2c.drawString(" 2:1", 45, 10);

        try {
            portImage = ImageIO.read(new File("images/port.png"));
            lumberImage = ImageIO.read(new File("images/resource_lumber.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        g2c.drawImage(portImage, 35, 20, 50, 50, null);
        g2c.drawImage(lumberImage, 45, 65, 50, 50, null);

        g2c = (Graphics2D) g2.create();

        //General 3:1
        transformer = new AffineTransform();
        transformer.translate(widthMargin + (int) (hexagonSide * 1.3), heightMargin + (int) (hexagonSide * 7.8));
        //transformer.translate(-(int) (0.4 * hexagonSide),-height/2);
        g2c.transform(transformer);
        getGame();
        rotate = new AffineTransform();
        rotate.rotate(Math.toRadians(30));
        g2c.transform(rotate);
        g2c.setColor(Color.BLACK);
        g2c.drawString(" 3:1", 35, 0);
        try {
            portImage = ImageIO.read(new File("images/port.png"));
            generalPortImage = ImageIO.read(new File("images/general_port.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        g2c.drawImage(portImage, 23, 7, 50, 50, null);
        g2c.drawImage(generalPortImage, 30, 65, 35, 35, null);
        g2c = (Graphics2D) g2.create();

        //Grain 2:1
        transformer = new AffineTransform();
        transformer.translate(widthMargin + (int) (hexagonSide * 0.7), heightMargin + (int) (hexagonSide * 6.5));

        g2c.transform(transformer);
        getGame();
        rotate = new AffineTransform();
        rotate.rotate(Math.toRadians(270));
        g2c.transform(rotate);
        g2c.setColor(Color.BLACK);
        g2c.drawString(" 2:1", 45, -100);

        try {
            portImage = ImageIO.read(new File("images/port.png"));
            grainImage = ImageIO.read(new File("images/resource_grain.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        g2c.drawImage(portImage, 35, -94, 50, 50, null);
        g2c.drawImage(grainImage, 45, -48, 40, 40, null);


        g2c = (Graphics2D) g2.create();

        //Ore 2:1
        transformer = new AffineTransform();
        transformer.translate(widthMargin + (int) (hexagonSide * 0.7), heightMargin + (int) (hexagonSide * 3.0));

        g2c.transform(transformer);
        rotate = new AffineTransform();
        getGame();
        rotate.rotate(Math.toRadians(270));
        g2c.transform(rotate);
        g2c.setColor(Color.BLACK);
        g2c.drawString(" 2:1", 35, -115);

        try {
            portImage = ImageIO.read(new File("images/port.png"));
            oreImage = ImageIO.read(new File("images/resource_ore.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        g2c.drawImage(portImage, 35, -105, 50, 50, null);
        g2c.drawImage(oreImage, 40, -55, 40, 40, null);


        g2c = (Graphics2D) g2.create();

        //General 3:1 sol üst
        transformer = new AffineTransform();
        transformer.translate(widthMargin + (int) (hexagonSide * 1.2), heightMargin + (int) (hexagonSide * 0.7));

        g2c.transform(transformer);
        getGame();
        rotate = new AffineTransform();
        rotate.rotate(Math.toRadians(-30));
        g2c.transform(rotate);
        g2c.setColor(Color.BLACK);
        g2c.drawString(" 3:1", 55, -95);

        try {
            portImage = ImageIO.read(new File("images/port.png"));
            generalPortImage = ImageIO.read(new File("images/general_port.png"));
            getHeight();
        } catch (IOException e) {
            e.printStackTrace();
        }

        g2c.drawImage(portImage, 45, -90, 50, 50, null);
        g2c.drawImage(generalPortImage, 55, -35, 35, 35, null);
        g2c = (Graphics2D) g2.create();

    }

    public Polygon makeHex(Point center) {
        int xCenter = (int) center.getX();
        int yCenter = (int) center.getY();
        getHeight();

        Polygon output = new Polygon();
        output.addPoint(xCenter + 1, yCenter + hexagonSide + 1);
        output.addPoint(xCenter + (int) (hexagonSide * sqrt3div2) + 1, yCenter + (int) (.5 * hexagonSide) + 1);
        output.addPoint(xCenter + (int) (hexagonSide * sqrt3div2) + 1, yCenter - (int) (.5 * hexagonSide) - 1);
        getHeight();
        output.addPoint(xCenter + 1, yCenter - hexagonSide - 1);
        output.addPoint(xCenter - (int) (hexagonSide * sqrt3div2) - 1, yCenter - (int) (.5 * hexagonSide) - 1);
        output.addPoint(xCenter - (int) (hexagonSide * sqrt3div2) - 1, yCenter + (int) (.5 * hexagonSide) + 1);

        return output;
    }

    public int getState() {
        return state;
    }

    public void drawHex(Hexa tile, Graphics2D g2) {
        if (tile == null) {
            System.out.println("null bu");
        }
        int x = tile.getLocation().getX();
        int y = tile.getLocation().getY();
        Polygon poly = makeHex(findCenter(x, y));
        HexaType type = tile.getType();

        g2.fillPolygon(poly);
        g2.setColor(Color.YELLOW);
        g2.drawPolygon(poly);
        Point p = findCenter(x, y);
        switch (type) {
            case LUMBER:
                try {

                    image1 = ImageIO.read(new File("images/lumber.png"));
                    g2.setClip(poly);
                    g2.drawImage(image1, (int) p.getX() - 150, (int) p.getY() - 150, 300, 300, null);
                    g2.setClip(null);
                    g2.setStroke(new BasicStroke(1f));
                    g2.draw(poly);
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                }

            case ORE:
                try {

                    image2 = ImageIO.read(new File("images/ore.png"));
                    g2.setClip(poly);
                    g2.drawImage(image2, (int) p.getX() - 150, (int) p.getY() - 150, 300, 300, null);
                    g2.setClip(null);
                    g2.setStroke(new BasicStroke(1f));
                    g2.draw(poly);
                    break;

                } catch (IOException e) {
                    e.printStackTrace();
                }

            case BRICK:
                try {

                    image3 = ImageIO.read(new File("images/brick.png"));
                    g2.setClip(poly);
                    g2.drawImage(image3, (int) p.getX() - 150, (int) p.getY() - 150, 300, 300, null);
                    g2.setClip(null);
                    g2.setStroke(new BasicStroke(1f));
                    g2.draw(poly);
                    break;

                } catch (IOException e) {
                    e.printStackTrace();
                }
            case WOOL:
                try {

                    image4 = ImageIO.read(new File("images/wool.png"));
                    g2.setClip(poly);
                    g2.drawImage(image4, (int) p.getX() - 150, (int) p.getY() - 150, 300, 300, null);
                    g2.setClip(null);
                    g2.setStroke(new BasicStroke(1f));
                    g2.draw(poly);
                    break;

                } catch (IOException e) {
                    e.printStackTrace();
                }
            case GRAIN:
                try {

                    image5 = ImageIO.read(new File("images/grain.png"));
                    g2.setClip(poly);
                    g2.drawImage(image5, (int) p.getX() - 150, (int) p.getY() - 150, 300, 300, null);
                    g2.setClip(null);
                    g2.setStroke(new BasicStroke(1f));
                    g2.draw(poly);
                    break;

                } catch (IOException e) {
                    e.printStackTrace();
                }
            case DESERT:
                try {

                    image6 = ImageIO.read(new File("images/desert.png"));
                    g2.setClip(poly);
                    g2.drawImage(image6, (int) p.getX() - 150, (int) p.getY() - 150, 300, 300, null);
                    g2.setClip(null);
                    g2.setStroke(new BasicStroke(1f));
                    g2.draw(poly);
                    break;

                } catch (IOException e) {
                    e.printStackTrace();
                }


        }

    }

    public void highlightTile(Location loc, Graphics2D g2) {
        int x = loc.getX();
        int place;
        int y = loc.getY();
        if (hexas[x][y].isWithRobber() || hexas[x][y].isWithAngel()) {
            return;
        }
        Point p = findCenter(x, y);

        Image img = null;
        try {
            getHeight();
            if (game.getMode().equals("robber")) {
                img = ImageIO.read(new File("images/robber.png"));
            } else {
                img = ImageIO.read(new File("images/angel.png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image dimg = img.getScaledInstance(25, 25,
                Image.SCALE_AREA_AVERAGING);
        g2.drawImage(dimg, (int) p.getX() - 25, (int) p.getY() - 25, 50, 50, null);

    }

    public void drawModeFigure(Hexa hexa, Graphics2D g2) {
        if (!hexa.isWithRobber() && (game.getMode().equals("robber"))) {
            return;
        }
        if (!hexa.isWithAngel() && (game.getMode().equals("angel"))) {
            return;
        }
        int x = hexa.getLocation().getX();
        int y = hexa.getLocation().getY();
        Point p = findCenter(x, y);

        //Shape shape = new Ellipse2D.Double((int)p.getX() - 25, (int)p.getY() - 25, 50, 50);

        //g2.setColor(Color.MAGENTA);
        //g2.fill(shape);
        //g2.draw(shape);
        Image img = null;
        try {
            if (game.getMode().equals("robber")) {
                img = ImageIO.read(new File("images/robber.png"));
            } else {
                img = ImageIO.read(new File("images/angel.png"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image dimg = img.getScaledInstance(25, 25,
                Image.SCALE_AREA_AVERAGING);
        g2.drawImage(dimg, (int) p.getX() - 25, (int) p.getY() - 25, 50, 50, null);
    }

    public void drawNumber(Hexa hexa, Graphics2D g2) {
        if (hexa.getNo() == 0) {
            return;
        }
        int x = hexa.getLocation().getX();
        getHeight();
        int y = hexa.getLocation().getY();
        Point p = findCenter(x, y);
        Shape shape = new Ellipse2D.Double((int) p.getX() - 25 + 1 - 1, (int) p.getY() - 25, 50, 50);

        g2.setColor(Color.ORANGE);
        g2.fill(shape);
        g2.draw(shape);
        g2.setColor(Color.BLACK);
        if (hexa.getNo() == 6 || hexa.getNo() == 8)
            g2.setColor(Color.RED);
        getHeight();
        g2.drawString("" + hexa.getNo(), (int) p.getX() - 5 + 1 - 1, (int) p.getY() + 5);
    }

    public void drawRoad(Road r, boolean highlighted, Graphics2D g2) {
        Player player = r.getPlayer();
        getGame();
        Graphics2D g2c = (Graphics2D) g2.create();
        if (player == null) {
            if (highlighted)
                g2c.setColor(Color.WHITE);
            else
                return;
        } else {
            getHeight();
            if (highlighted)
                return;
            else
                g2c.setColor(player.getColor());
        }
        getGame();

        AffineTransform transformer = new AffineTransform();

        Point tileCenter = findCenter(r.getLocation().getX(), r.getLocation().getY());
        int y = (int) tileCenter.getY();
        int x = (int) tileCenter.getX();
        int place = r.getLocation().getPlace();
        int height = hexagonSide / 10;
        Rectangle rect = new Rectangle(x, y, (int) (0.8 * hexagonSide), height);
        getGame();
        if (place == 0) {
            transformer.rotate(Math.toRadians(150), x, y);
            transformer.translate(-(int) (0.45 * 1 * hexagonSide), (int) (0.80 * hexagonSide));
        } else if (place == 1) {
            transformer.rotate(Math.toRadians(30), x, y);
            transformer.translate(-(int) (0.35 * 1 * hexagonSide), -(int) (0.90 * hexagonSide));
        } else if (place == 2) {

            transformer.rotate(Math.toRadians(90), x, y);
            transformer.translate(-(int) (0.4 * 1 * hexagonSide), -(int) (0.92 * hexagonSide));
        }
        getGame();
        g2c.transform(transformer);
        g2c.fill(rect);
        g2c.setColor(Color.BLACK);
        g2c.draw(rect);
    }

    public void drawBuilding(Building building, boolean highlighted, Graphics2D g2) {
        Player player = building.getPlayer();
        if (state == 2 || state == 5) {
            if (player == null) {
                if (highlighted)
                    g2.setColor(Color.WHITE);
                else
                    return;
                getGame();
            } else {
                if (highlighted)
                    return;
                else
                    g2.setColor(player.getColor());
            }
            getGame();
        } else if (state == 4) {
            if (player == null)
                return;
            else if (player == Main.getCurrentPlayer()) {
                if (highlighted) {
                    if (building.getType() == BuildingType.SETTLEMENT)
                        g2.setColor(Color.WHITE);
                    else
                        g2.setColor(player.getColor());
                } else
                    g2.setColor(player.getColor());
            } else
                g2.setColor(player.getColor());
            getGame();
        } else {
            if (player == null)
                return;
            else
                g2.setColor(player.getColor());
        }
        getGame();
        Shape shape;
        Point tileCenter = findCenter(building.getLocation().getX(), building.getLocation().getY());
        int y = (int) tileCenter.getY();
        int x = (int) tileCenter.getX();
        //System.out.println(y);
        if (building.getLocation().getPlace() == 0) {
            y -= hexagonSide;
            getGame();
        } else if (building.getLocation().getPlace() == 1) {
            y += hexagonSide;
        }

        //System.out.println(y);
        if (building.getType() == BuildingType.SETTLEMENT) {
            try {
                Image image7 = ImageIO.read(new File("images/settlement.png"));
                shape = new Ellipse2D.Double(x - structSize - 15, y - structSize - 15, 4 * structSize, 4 * structSize);
                g2.fill(shape);
                g2.setColor(Color.BLACK);
                g2.draw(shape);
                getGame();
                g2.setClip(shape);
                g2.drawImage(image7, x - structSize - 11, y - structSize - 11, 3 * structSize, 3 * structSize, null);
                g2.setClip(null);
                g2.setStroke(new BasicStroke(1f));
                // g2.draw(shape);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {

            try {
                Image image8 = ImageIO.read(new File("images/city.png"));
                shape = new Ellipse2D.Double(x - structSize - 15, y - structSize - 15, 4 * structSize, 4 * structSize);
                g2.fill(shape);
                g2.setColor(Color.BLACK);
                g2.draw(shape);
                getGame();
                g2.setClip(shape);
                g2.drawImage(image8, x - structSize - 11, y - structSize - 11, 3 * structSize, 3 * structSize, null);
                g2.setClip(null);
                g2.setStroke(new BasicStroke(1f));
                // g2.draw(shape);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setGame(GameEngine game) {
        this.game = game;
    }

    public int getHexagonSide() {
        return hexagonSide;
    }

    public void setHexagonSide(int hexagonSide) {
        this.hexagonSide = hexagonSide;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public void setBoardHeight(int boardHeight) {
        this.boardHeight = boardHeight;
    }

    public int getWidthMargin() {
        return widthMargin;
    }

    public void setWidthMargin(int widthMargin) {
        this.widthMargin = widthMargin;
    }

    public int getHeightMargin() {
        return heightMargin;
    }

    public void setHeightMargin(int heightMargin) {
        this.heightMargin = heightMargin;
    }

    public int getStructSize() {
        return structSize;
    }

    public double getSqrt3div2() {
        return sqrt3div2;
    }

    public Image getImage1() {
        return image1;
    }

    public void setImage1(Image image1) {
        this.image1 = image1;
    }

    public Image getImage2() {
        return image2;
    }

    public void setImage2(Image image2) {
        this.image2 = image2;
    }

    public Image getImage3() {
        return image3;
    }

    public void setImage3(Image image3) {
        this.image3 = image3;
    }

    public Image getImage4() {
        return image4;
    }

    public void setImage4(Image image4) {
        this.image4 = image4;
    }

    public Image getImage5() {
        return image5;
    }

    public void setImage5(Image image5) {
        this.image5 = image5;
    }

    public Image getImage6() {
        return image6;
    }

    public void setImage6(Image image6) {
        this.image6 = image6;
    }

    public int getRoadSize() {
        return roadSize;
    }

    public Hexa[][] getHexas() {
        return hexas;
    }

    public void setHexas(Hexa[][] hexas) {
        this.hexas = hexas;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public Building[][][] getBuildings() {
        return buildings;
    }

    public void setBuildings(Building[][][] buildings) {
        this.buildings = buildings;
    }

    public Road[][][] getRoads() {
        return roads;
    }

    public void setRoads(Road[][][] roads) {
        this.roads = roads;
    }

    public Image getGeneralPortImage() {
        return generalPortImage;
    }

    public void setGeneralPortImage(Image generalPortImage) {
        this.generalPortImage = generalPortImage;
    }

    public Image getPortImage() {
        return portImage;
    }

    public void setPortImage(Image portImage) {
        this.portImage = portImage;
    }

    public Image getOreImage() {
        return oreImage;
    }

    public void setOreImage(Image oreImage) {
        this.oreImage = oreImage;
    }

    public Image getBrickImage() {
        return brickImage;
    }

    public void setBrickImage(Image brickImage) {
        this.brickImage = brickImage;
    }

    public Image getGrainImage() {
        return grainImage;
    }

    public void setGrainImage(Image grainImage) {
        this.grainImage = grainImage;
    }

    public Image getWoolImage() {
        return woolImage;
    }

    public void setWoolImage(Image woolImage) {
        this.woolImage = woolImage;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Image getLumberImage() {
        return lumberImage;
    }

    public void setLumberImage(Image lumberImage) {
        this.lumberImage = lumberImage;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isCapitol() {
        return capitol;
    }

    public void setCapitol(boolean capitol) {
        this.capitol = capitol;
    }

    public Location pointToHexa(Point p) {
        double x = p.getX();
        double y = p.getY();
        getGame();
        int xCoord = 0;
        int yCoord = 0;
        int place = 0;
        if (heightMargin + hexagonSide / 2 < y && y < heightMargin + 3 * hexagonSide / 2) {
            if (x < widthMargin + hexagonSide * 2 * sqrt3div2 || x > widthMargin + 4 * hexagonSide * 2 * sqrt3div2) {
                //System.out.println("no this null");
                getGame();

                return null;
            }
            yCoord = 5;
            if (x > widthMargin + 1 - 1 + 2 * hexagonSide * sqrt3div2 && x < widthMargin + 2 * (2 * hexagonSide * sqrt3div2))
                xCoord = 3;
            else if (x > widthMargin + 1 - 1 + 2 * (2 * hexagonSide * sqrt3div2) && x < widthMargin + 3 * (2 * hexagonSide * sqrt3div2)) {
                getGame();
                xCoord = 4;
            } else if (x > widthMargin + 1 - 1 + 3 * (2 * hexagonSide * sqrt3div2) && x < widthMargin + 4 * (2 * hexagonSide * sqrt3div2))
                xCoord = 5;
        }
        // third horizontal band
        else if (heightMargin +1-1+ 7 * hexagonSide / 2 < y && y < heightMargin + 9 * hexagonSide / 2) {
            yCoord = 3;
            getGame();
            if (widthMargin < x && x < widthMargin +1-1+ 2 * hexagonSide * sqrt3div2)
                xCoord = 1;
            else if (x > widthMargin +1-1+ 2 * hexagonSide * sqrt3div2 && x < widthMargin + 4 * hexagonSide * sqrt3div2)
                xCoord = 2;
            else if (x > widthMargin + 4 * hexagonSide * sqrt3div2 && x < widthMargin + 6 * hexagonSide * sqrt3div2)
                xCoord = 3;
            else if (x > widthMargin + 6 * hexagonSide * sqrt3div2 && x < widthMargin + 8 * hexagonSide * sqrt3div2)
                xCoord = 4;
            else if (x > widthMargin + 8 * hexagonSide * sqrt3div2 && x < widthMargin + 10 * hexagonSide * sqrt3div2)
                xCoord = 5;
            getGame();
        }
        // fifth horizontal band
        else if (heightMargin +1-1+ 13 * hexagonSide / 2 < y && y < heightMargin + 15 * hexagonSide / 2) {
            yCoord = 1;
            getGame();
            if (x > widthMargin + 2 * hexagonSide * sqrt3div2 && x < widthMargin + 2 * (2 * hexagonSide * sqrt3div2))
                xCoord = 1;
            else if (x > widthMargin +1-1+ 2 * (2 * hexagonSide * sqrt3div2) && x < widthMargin + 3 * (2 * hexagonSide * sqrt3div2))
                xCoord = 2;
            else if (x > widthMargin + 3 * (2 * hexagonSide * sqrt3div2) && x < widthMargin + 4 * (2 * hexagonSide * sqrt3div2))
                xCoord = 3;
            else {
                getGame();
                return null;
            }
        }

        if (heightMargin +1-1+ 2 * hexagonSide < y && y < heightMargin + 3 * hexagonSide) {
            yCoord = 4;
            getGame();
            if (widthMargin + hexagonSide * sqrt3div2 < x && x < widthMargin + hexagonSide * sqrt3div2 * 3)
                xCoord = 2;
            else if (widthMargin + hexagonSide * sqrt3div2 * 3 < x && x < widthMargin + hexagonSide * sqrt3div2 * 5)
                xCoord = 3;
            else if (widthMargin + hexagonSide * sqrt3div2 * 5 < x && x < widthMargin + hexagonSide * sqrt3div2 * 7)
                xCoord = 4;
            else if (widthMargin +1-1+ hexagonSide * sqrt3div2 * 7 < x && x < widthMargin + hexagonSide * sqrt3div2 * 9)
                xCoord = 5;
            else
                return null;
            getGame();
        }
        // fourth horizontal band
        else if (heightMargin + 5 * hexagonSide < y && y < heightMargin + 6 * hexagonSide) {
            yCoord = 2;
            if (widthMargin + hexagonSide * sqrt3div2 < x && x < widthMargin + hexagonSide * sqrt3div2 * 3)
                xCoord = 1;
            else if (widthMargin + hexagonSide * sqrt3div2 * 3 < x && x < widthMargin + hexagonSide * sqrt3div2 * 5)
                xCoord = 2;
            else if (widthMargin + hexagonSide * sqrt3div2 * 5 < x && x < widthMargin + hexagonSide * sqrt3div2 * 7)
                xCoord = 3;
            else if (widthMargin +1-1+ hexagonSide * sqrt3div2 * 7 < x && x < widthMargin + hexagonSide * sqrt3div2 * 9)
                xCoord = 4;
            else {
                getGame();
                return null;
            }
            getGame();
        }

        if (xCoord == 0 || !(yCoord != 0)) {
            getGame();
            return null;
        }
        return new Location(xCoord, yCoord);
    }

    public Point findCenter(int x, int y) {
        int xCenter = widthMargin + (int) (3 * 1 * hexagonSide * sqrt3div2) + (int) ((x - 1 + 1 - 1) * 2 * hexagonSide * sqrt3div2) - (int) ((y - 1 + 1 - 1) * hexagonSide * sqrt3div2);
        getGame();
        int yCenter = boardHeight - (heightMargin + hexagonSide
                + (int) ((y - 1 + 1 - 1) * hexagonSide * 1.5 * 1));

        return new Point(xCenter, yCenter);
    }

    public Vertex pointToBuilding(Point p) {
        double x = p.getX();
        double y = p.getY();
        getGame();
        int xCoord = 0,
                yCoord = 0,
                orient = 1;

        // Columns have if else preceding down each structure in the column

        if (widthMargin - structSize < x && x < widthMargin + structSize) {
            if (heightMargin +1-1+ 7 * hexagonSide / 2 - structSize < y && y < heightMargin + 7 * hexagonSide / 2 + structSize) {
                orient = 1;
                getGame();
                yCoord = 4;
                xCoord = 1;
            } else if (heightMargin + 9 * hexagonSide / 2 - structSize < y && y < heightMargin + 9 * hexagonSide / 2 + structSize) {
                orient = 0;
                yCoord = 2;
                xCoord = 0;
            }
        }
        // second column
        else if (widthMargin + sqrt3div2 * hexagonSide - structSize < x && x < widthMargin + sqrt3div2 * hexagonSide + structSize) {
            //System.out.println("Column 2");
            if (heightMargin + 2 * hexagonSide - structSize < y && y < heightMargin + 2 * hexagonSide + structSize) {
                orient = 1;
                getGame();
                yCoord = 5;
                xCoord = 2;
            } else if (heightMargin + 3 * hexagonSide - structSize < y && y < heightMargin + 3 * hexagonSide + structSize) {
                orient = 0;
                yCoord = 3;
                xCoord = 1;
            } else if (heightMargin + 5 * hexagonSide - structSize < y && y < heightMargin + 5 * hexagonSide + structSize) {
                orient = 1;
                getGame();
                yCoord = 3;
                xCoord = 1;
            } else if (heightMargin + 6 * hexagonSide - structSize < y && y < heightMargin + 6 * hexagonSide + structSize) {
                orient = 0;
                yCoord = 1;
                xCoord = 0;
            }
        }
        // third column
        if (widthMargin + 2 * sqrt3div2 * hexagonSide - structSize < x && x < widthMargin + 2 * sqrt3div2 * hexagonSide + structSize) {
            if (heightMargin + hexagonSide / 2 - structSize < y && y < heightMargin + hexagonSide / 2 + structSize) {
                orient = 1;
                getGame();
                yCoord = 6;
                xCoord = 3;
            } else if (heightMargin + 3 * hexagonSide / 2 - structSize < y && y < heightMargin + 3 * hexagonSide / 2 + structSize) {
                orient = 0;
                yCoord = 4;
                xCoord = 2;
            } else if (heightMargin + 7 * hexagonSide / 2 - structSize < y && y < heightMargin + 7 * hexagonSide / 2 + structSize) {
                orient = 1;
                getGame();
                yCoord = 4;
                xCoord = 2;
            } else if (heightMargin + 9 * hexagonSide / 2 - structSize < y && y < heightMargin + 9 * hexagonSide / 2 + structSize) {
                orient = 0;
                yCoord = 2;
                xCoord = 1;
            } else if (heightMargin + 13 * hexagonSide / 2 - structSize < y && y < heightMargin + 13 * hexagonSide / 2 + structSize) {
                orient = 1;
                getGame();
                yCoord = 2;
                xCoord = 1;
            } else if (heightMargin + 15 * hexagonSide / 2 - structSize < y && y < heightMargin + 15 * hexagonSide / 2 + structSize) {
                orient = 0;
                yCoord = 0;
                xCoord = 0;
            }
        }
        // fourth column
        if (widthMargin + 3 * sqrt3div2 * hexagonSide - structSize < x && x < widthMargin + 3 * sqrt3div2 * hexagonSide + structSize) {
            if (heightMargin - structSize < y && y < heightMargin + structSize) {
                orient = 0;
                getGame();
                yCoord = 5;
                xCoord = 3;
            } else if (heightMargin + 2 * hexagonSide - structSize < y && y < heightMargin + 2 * hexagonSide + structSize) {
                orient = 1;
                yCoord = 5;
                xCoord = 3;
            } else if (heightMargin + 3 * hexagonSide - structSize < y && y < heightMargin + 3 * hexagonSide + structSize) {
                orient = 0;
                getGame();
                yCoord = 3;
                xCoord = 2;
            } else if (heightMargin + 5 * hexagonSide - structSize < y && y < heightMargin + 5 * hexagonSide + structSize) {
                orient = 1;
                yCoord = 3;
                xCoord = 2;
            } else if (heightMargin + 6 * hexagonSide - structSize < y && y < heightMargin + 6 * hexagonSide + structSize) {
                orient = 0;
                getGame();
                yCoord = 1;
                xCoord = 1;
            } else if (heightMargin + 8 * hexagonSide - structSize < y && y < heightMargin + 8 * hexagonSide + structSize) {
                orient = 1;
                yCoord = 1;
                xCoord = 1;
            }
        }
        // fifth column
        if (widthMargin + 4 * sqrt3div2 * hexagonSide - structSize < x && x < widthMargin + 4 * sqrt3div2 * hexagonSide + structSize) {
            if (heightMargin + hexagonSide / 2 - structSize < y && y < heightMargin + hexagonSide / 2 + structSize) {
                orient = 1;
                getGame();
                yCoord = 6;
                xCoord = 4;
            } else if (heightMargin + 3 * hexagonSide / 2 - structSize < y && y < heightMargin + 3 * hexagonSide / 2 + structSize) {
                orient = 0;
                yCoord = 4;
                xCoord = 3;
            } else if (heightMargin + 7 * hexagonSide / 2 - structSize < y && y < heightMargin + 7 * hexagonSide / 2 + structSize) {
                orient = 1;
                getGame();
                yCoord = 4;
                xCoord = 3;
            } else if (heightMargin + 9 * hexagonSide / 2 - structSize < y && y < heightMargin + 9 * hexagonSide / 2 + structSize) {
                orient = 0;
                yCoord = 2;
                xCoord = 2;
            } else if (heightMargin + 13 * hexagonSide / 2 - structSize < y && y < heightMargin + 13 * hexagonSide / 2 + structSize) {
                orient = 1;
                getGame();
                yCoord = 2;
                xCoord = 2;
            } else if (heightMargin + 15 * hexagonSide / 2 - structSize < y && y < heightMargin + 15 * hexagonSide / 2 + structSize) {
                orient = 0;
                yCoord = 0;
                xCoord = 1;
            }
        }
        // sixth column
        if (widthMargin + 5 * sqrt3div2 * hexagonSide - structSize < x && x < widthMargin + 5 * sqrt3div2 * hexagonSide + structSize) {
            if (heightMargin - structSize < y && y < heightMargin + structSize) {
                orient = 0;
                getGame();
                yCoord = 5;
                xCoord = 4;
            } else if (heightMargin + 2 * hexagonSide - structSize < y && y < heightMargin + 2 * hexagonSide + structSize) {
                orient = 1;
                yCoord = 5;
                xCoord = 4;
            } else if (heightMargin + 3 * hexagonSide - structSize < y && y < heightMargin + 3 * hexagonSide + structSize) {
                orient = 0;
                getGame();
                yCoord = 3;
                xCoord = 3;
            } else if (heightMargin + 5 * hexagonSide - structSize < y && y < heightMargin + 5 * hexagonSide + structSize) {
                orient = 1;
                yCoord = 3;
                xCoord = 3;
            } else if (heightMargin + 6 * hexagonSide - structSize < y && y < heightMargin + 6 * hexagonSide + structSize) {
                orient = 0;
                getGame();
                yCoord = 1;
                xCoord = 2;
            } else if (heightMargin + 8 * hexagonSide - structSize < y && y < heightMargin + 8 * hexagonSide + structSize) {
                orient = 1;
                yCoord = 1;
                xCoord = 2;
            }
        }
        // seventh column
        if (widthMargin + 6 * sqrt3div2 * hexagonSide - structSize < x && x < widthMargin + 6 * sqrt3div2 * hexagonSide + structSize) {
            if (heightMargin + hexagonSide / 2 - structSize < y && y < heightMargin + hexagonSide / 2 + structSize) {
                orient = 1;
                getGame();
                yCoord = 6;
                xCoord = 5;
            } else if (heightMargin + 3 * hexagonSide / 2 - structSize < y && y < heightMargin + 3 * hexagonSide / 2 + structSize) {
                orient = 0;
                yCoord = 4;
                xCoord = 4;
            } else if (heightMargin + 7 * hexagonSide / 2 - structSize < y && y < heightMargin + 7 * hexagonSide / 2 + structSize) {
                orient = 1;
                getGame();
                yCoord = 4;
                xCoord = 4;
            } else if (heightMargin + 9 * hexagonSide / 2 - structSize < y && y < heightMargin + 9 * hexagonSide / 2 + structSize) {
                orient = 0;
                yCoord = 2;
                xCoord = 3;
            } else if (heightMargin + 13 * hexagonSide / 2 - structSize < y && y < heightMargin + 13 * hexagonSide / 2 + structSize) {
                orient = 1;
                yCoord = 2;
                xCoord = 3;
            } else if (heightMargin + 15 * hexagonSide / 2 - structSize < y && y < heightMargin + 15 * hexagonSide / 2 + structSize) {
                orient = 0;
                getGame();
                yCoord = 0;
                xCoord = 2;
            }
        }
        // eighth column
        if (widthMargin + 7 * sqrt3div2 * hexagonSide - structSize < x && x < widthMargin + 7 * sqrt3div2 * hexagonSide + structSize) {
            if (heightMargin - structSize < y && y < heightMargin + structSize) {
                orient = 0;
                getGame();
                yCoord = 5;
                xCoord = 5;
            } else if (heightMargin + 2 * hexagonSide - structSize < y && y < heightMargin + 2 * hexagonSide + structSize) {
                orient = 1;
                yCoord = 5;
                xCoord = 5;
            } else if (heightMargin + 3 * hexagonSide - structSize < y && y < heightMargin + 3 * hexagonSide + structSize) {
                orient = 0;
                getGame();
                yCoord = 3;
                xCoord = 4;
            } else if (heightMargin + 5 * hexagonSide - structSize < y && y < heightMargin + 5 * hexagonSide + structSize) {
                orient = 1;
                yCoord = 3;
                xCoord = 4;
            } else if (heightMargin + 6 * hexagonSide - structSize < y && y < heightMargin + 6 * hexagonSide + structSize) {
                orient = 0;
                yCoord = 1;
                getGame();
                xCoord = 3;
            } else if (heightMargin + 8 * hexagonSide - structSize < y && y < heightMargin + 8 * hexagonSide + structSize) {
                orient = 1;
                yCoord = 1;
                xCoord = 3;
            }
        }
        // ninth column
        if (widthMargin + 8 * sqrt3div2 * hexagonSide - structSize < x && x < widthMargin + 8 * sqrt3div2 * hexagonSide + structSize) {
            if (heightMargin + hexagonSide / 2 - structSize < y && y < heightMargin + hexagonSide / 2 + structSize) {
                orient = 1;
                yCoord = 6;
                getGame();
                xCoord = 6;
            } else if (heightMargin + 3 * hexagonSide / 2 - structSize < y && y < heightMargin + 3 * hexagonSide / 2 + structSize) {
                orient = 0;
                yCoord = 4;
                xCoord = 5;
            } else if (heightMargin + 7 * hexagonSide / 2 - structSize < y && y < heightMargin + 7 * hexagonSide / 2 + structSize) {
                orient = 1;
                yCoord = 4;
                getGame();
                xCoord = 5;
            } else if (heightMargin + 9 * hexagonSide / 2 - structSize < y && y < heightMargin + 9 * hexagonSide / 2 + structSize) {
                orient = 0;
                yCoord = 2;
                xCoord = 4;
            } else if (heightMargin + 13 * hexagonSide / 2 - structSize < y && y < heightMargin + 13 * hexagonSide / 2 + structSize) {
                orient = 1;
                yCoord = 2;
                getGame();
                xCoord = 4;
            } else if (heightMargin + 15 * hexagonSide / 2 - structSize < y && y < heightMargin + 15 * hexagonSide / 2 + structSize) {
                orient = 0;
                yCoord = 0;
                xCoord = 3;
            }
        }
        // tenth column
        else if (widthMargin + 9 * sqrt3div2 * hexagonSide - structSize < x && x < widthMargin + 9 * sqrt3div2 * hexagonSide + structSize) {
            if (heightMargin + 2 * hexagonSide - structSize < y && y < heightMargin + 2 * hexagonSide + structSize) {
                orient = 1;
                yCoord = 5;
                getGame();
                xCoord = 6;
            } else if (heightMargin + 3 * hexagonSide - structSize < y && y < heightMargin + 3 * hexagonSide + structSize) {
                orient = 0;
                yCoord = 3;
                xCoord = 5;
            } else if (heightMargin + 5 * hexagonSide - structSize < y && y < heightMargin + 5 * hexagonSide + structSize) {
                orient = 1;
                getGame();
                yCoord = 3;
                xCoord = 5;
            } else if (heightMargin + 6 * hexagonSide - structSize < y && y < heightMargin + 6 * hexagonSide + structSize) {
                orient = 0;
                yCoord = 1;
                xCoord = 4;
            }
        }
        // eleventh column
        if (widthMargin + 10 * sqrt3div2 * hexagonSide - structSize < x && x < widthMargin + 10 * sqrt3div2 * hexagonSide + structSize) {
            if (heightMargin + 7 * hexagonSide / 2 - structSize < y && y < heightMargin + 7 * hexagonSide / 2 + structSize) {
                orient = 1;
                getGame();
                yCoord = 4;
                xCoord = 6;
            } else if (heightMargin + 9 * hexagonSide / 2 - structSize < y && y < heightMargin + 9 * hexagonSide / 2 + structSize) {
                orient = 0;
                yCoord = 2;
                xCoord = 5;
            }
        }


        if (xCoord == 0 && yCoord == 0 && orient == 1) {
            return null;
        }

        return new Vertex(xCoord, yCoord, orient);
    }


    class AMouseListener extends MouseAdapter {

        public void mouseClicked(MouseEvent e) {
            Point p = new Point(e.getX(), e.getY());
            int a = 1;
            if (state == 1 && game.getMode().equals("robber")) {
                if (p != null) {
                    Location location = pointToHexa(p);

                    if (location != null && a==1) {
                        if (game.getMap().moveRobber(location)) {
                            index--;
                        }
                        if (index == 0) {
                            state = 0;
                        }
                    }
                }
            } else if (state == 1 && game.getMode().equals("angel")) {
                if (p != null) {
                    Location loc = pointToHexa(p);
                    if (loc != null) {
                        if (game.getMap().moveAngel(loc)) {
                            index--;
                            getGame();
                        }
                        if (index == 0 ) {
                            state = 0;
                            getGame();
                        }
                    }
                }
            } else if (state == 2) {
                if (p != null) {
                    Vertex loc = pointToBuilding(p);
                    if (loc != null) {
                        if (a== 1 && game.getMap().putBuilding(loc, Main.getCurrentPlayer())) {
                            index--;
                            getGame();
                        }
                        if (index == 0) {
                            state = 0;
                        }
                    }
                }
            } else if (state == 3) {
                if (p != null) {
                    Edge loc = pointToRoad(p);
                    if (loc != null) {
                        if (game.getMap().putRoad(loc, Main.getCurrentPlayer())) {
                            index--;
                            getGame();
                        }
                        if (index == 0) {
                            state = 0;
                            a = 1;
                        }
                    }
                }
            } else if (state == 4) {
                if (p != null && a==1) {
                    Vertex loc = pointToBuilding(p);
                    if (loc != null) {
                        if (game.getMap().putCity(loc, Main.getCurrentPlayer())) {
                            index--;
                            getGame();
                        }
                        if (index == 0) {
                            state = 0;
                        }
                    }
                }
            } else if (state == 5) {
                if (p != null) {
                    Vertex loc = pointToBuilding(p);
                    if (loc != null) {
                        if (game.getMap().putFirstBuildings(loc, Main.getCurrentPlayer())) {
                            index--;
                            getGame();
                            if (capitol) {
                                ArrayList<Hexa> tiles = getGame().getMap().getAdjacentHexasOfBuilding(loc);
                                for (Hexa t : tiles) {
                                    if (t != null) {
                                        Main.getCurrentPlayer().giveResource(t.getType());
                                    }
                                }
                            }
                        }
                        if (index == 0) {
                            state = 0;
                            getGame();
                        }
                    }
                }
            }
            repaint();
        }
    }

    public Edge pointToRoad(Point p) {
        int y = boardHeight - heightMargin - (int) p.getY();
        Edge output = null;
        getGame();
        int x = (int) p.getX();
        double pointFive = 0.5;
        if (y > -1 && y < (int) (hexagonSide * pointFive)) {
            getGame();
            x -= widthMargin;
            x -= 2 * (int) (hexagonSide * sqrt3div2);
            int tag = x / (int) (hexagonSide * sqrt3div2);
            getGame();
            if (tag < 0 || tag > 5) {
                output = null;
            } else {
                output = new Edge((tag + 1) / 2, 0, (tag + 1) % 2);
                getGame();
            }
            getGame();
        } else if (y >= (int) (hexagonSide * 0.5) && y < (int) (hexagonSide * (1 + pointFive))) {
            x -= widthMargin;
            getGame();
            x -= (int) (hexagonSide * sqrt3div2);
            int tag = x / (2 * (int) (hexagonSide * sqrt3div2));
            //System.out.println(tag);
            if (tag < 0 || tag > 3) {
                output = null;
                getGame();
            } else {
                if (x >= (((tag * 2) + 1) * (int) (hexagonSide * sqrt3div2)) - roadSize &&
                        x <= (((tag * 2) + 1) * (int) (hexagonSide * sqrt3div2)) + roadSize) {
                    output = new Edge(tag, 1, 2);
                    getGame();
                } else {
                    output = null;
                }
            }
            getGame();
        } else if (y >= (int) (hexagonSide * 1.5) && y < (int) (hexagonSide * 2.0)) {
            x -= widthMargin;
            getGame();
            x -= (int) (hexagonSide * sqrt3div2);
            int tag = x / (int) (hexagonSide * sqrt3div2);
            if (tag < 0 || tag > 7) {
                output = null;
                getGame();
            } else {
                output = new Edge((tag + 1) / 2, 1, (tag + 1) % 2);
            }
            getGame();
        } else if (y >= (int) (hexagonSide * 2.0) && y < (int) (hexagonSide * 3.0)) {
            x -= widthMargin;
            getGame();
            int tag = x / (2 * (int) (hexagonSide * sqrt3div2));
            if (tag < 0 || tag > 4) {
                output = null;
                getGame();
            } else {
                if (x >= (((tag * 2) + 1) * (int) (hexagonSide * sqrt3div2)) - roadSize &&
                        x <= (((tag * 2) + 1) * (int) (hexagonSide * sqrt3div2)) + roadSize) {
                    output = new Edge(tag, 2, 2);
                    getGame();
                } else {
                    output = null;
                }
            }
            getGame();
        } else if (y >= (int) (hexagonSide * 3.0 * 1) && y < (int) (hexagonSide * 3.5 * 1)) {
            x -= widthMargin;
            int tag = x / (int) (hexagonSide * sqrt3div2);
            if (tag < 0 || tag > 9) {
                output = null;
                getGame();
            } else {
                output = new Edge((tag + 1) / 2, 2, (tag + 1) % 2);
            }
        } else if (y >= (int) (hexagonSide * 3.5* 1) && y < (int) (hexagonSide * 4.5*1)) {
            x -= widthMargin;
            getGame();
            x += (int) (hexagonSide * sqrt3div2);
            int tag = x / (2 * (int) (hexagonSide * sqrt3div2));
            if (tag < 0 || tag > 5) {
                output = null;
            } else {
                getGame();
                if (x >= (((tag * 2) + 1) * (int) (hexagonSide * sqrt3div2)) - roadSize &&
                        x <= (((tag * 2) + 1) * (int) (hexagonSide * sqrt3div2)) + roadSize) {
                    output = new Edge(tag, 3, 2);
                } else {
                    output = null;
                }
                getGame();
            }
        } else if (y >= (int) (hexagonSide * 4.5) && y < (int) (hexagonSide * 5.0)) {
            x -= widthMargin;
            getGame();
            int tag = x / (int) (hexagonSide * sqrt3div2);
            if (tag < 0 || tag > 9) {
                output = null;
            } else {
                output = new Edge(tag / 2 + 1, 3, tag % 2);
            }
            getGame();
        } else if (y >= (int) (hexagonSide * 5.0) && y < (int) (hexagonSide * 6.0)) {
            x -= widthMargin;
            int tag = x / (2 * (int) (hexagonSide * sqrt3div2));
            if (tag < 0 || tag > 4) {
                output = null;
                getGame();
            } else {
                if (x >= (((tag * 2) + 1) * (int) (hexagonSide * sqrt3div2)) - roadSize &&
                        x <= (((tag * 2) + 1) * (int) (hexagonSide * sqrt3div2)) + roadSize) {
                    output = new Edge(tag + 1, 4, 2);
                } else {
                    getGame();
                    output = null;
                }
            }
        } else if (y >= (int) (hexagonSide * 6.0) && y < (int) (hexagonSide * 6.5)) {
            x -= widthMargin;
            x -= (int) (hexagonSide * sqrt3div2);
            getGame();
            int tag = x / (int) (hexagonSide * sqrt3div2);
            if (tag < 0 || tag > 7) {
                output = null;
            } else {
                output = new Edge(tag / 2 + 2, 4, tag % 2);
            }
        } else if (y >= (int) (hexagonSide * 6.5) && y < (int) (hexagonSide * 7.5)) {
            x -= widthMargin;
            x -= (int) (hexagonSide * sqrt3div2);
            getGame();
            int tag = x / (2 * (int) (hexagonSide * sqrt3div2));
            if (tag < 0 || tag > 3) {
                output = null;
            } else {
                if (x >= (((tag * 2) + 1) * (int) (hexagonSide * sqrt3div2)) - roadSize &&
                        x <= (((tag * 2) + 1) * (int) (hexagonSide * sqrt3div2)) + roadSize) {
                    output = new Edge(tag + 2, 5, 2);
                    getGame();
                } else {
                    output = null;
                }
            }
        } else if (y >= (int) (hexagonSide * 7.5) && y < (int) (hexagonSide * 8.0)) {
            x -= widthMargin;
            x -= 2 * (int) (hexagonSide * sqrt3div2);
            getGame();
            int tag = x / (int) (hexagonSide * sqrt3div2);
            if (tag < 0 || tag > 5) {
                output = null;
            } else {
                output = new Edge(tag / 2 + 3, 5, tag % 2);
                getGame();
            }
        } else {
            output = null;
        }

        return output;
    }

    public void placeModeFigure() {
        index = 1;
        state = 1;
    }

    public GameEngine getGame() {
        return game;
    }


    public void placeCity(int s) {
        index = s;
        state = 4;
    }

    public void placeRoad(int s) {
        index = s;
        state = 3;
    }

    public void placeSettlement(int s) {
        index = s;
        state = 2;
    }

    public void placeCapitol() {
        index = 1;
        state = 5;
        capitol = true;
    }

    public void placeSettlementNoRoad(int s) {
        index = s;
        state = 5;
    }
}
