package graphics.panels;

import graphics.MainFrame;
import graphics.gamefield.*;
import model.Game;
import model.Steppable;
import model.characters.Character;
import model.characters.Plumber;
import model.characters.Saboteur;
import model.fields.Cistern;
import model.fields.Pipe;
import model.fields.Pump;
import model.fields.WaterSource;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Játékot megjelenítő panel
 */
public class GameFieldPanel extends JPanel {
    Image backgroundImage;      //Háttérkép
    private HashMap<Steppable, Vertex> vertices = new HashMap<>();  //grafikus csúcsok tárolása
    private HashMap<Pipe, Edge> edges = new HashMap<>();    //grafikus élek tárolása
    private HashMap<Character, JCharacter> players = new HashMap<>();   //grafikus játékosok tárolása
    MainFrame frame;    //parent Frame
    Dimension winSize = new Dimension(1000, 800);   //frame mérete

    JPanel infobar = new JPanel();


    private static GameFieldPanel single_instance = null;    //Singleton típusú single_instance statikus változó referenciája

    /**
     * Singleton példány lekérdezése
     *
     * @return singleton példány
     */
    public static synchronized GameFieldPanel getInstance() {
        return single_instance;
    }

    /**
     * Panel újratöltése új játék esetén
     */
    public static synchronized void start(ArrayList<ImageIcon> plumberImages, ArrayList<ImageIcon> saboteurImages) {
        single_instance = new GameFieldPanel(plumberImages, saboteurImages);
    }

    /**
     * GameField inicaializálása, a modellbeli elemek megfeleltetése a grafikus másának, azok megfelelő tárolása
     *
     * @param plumberImages Szerelő képek
     * @param saboteurImages Szabotőr képek
     */
    private GameFieldPanel(ArrayList<ImageIcon> plumberImages, ArrayList<ImageIcon> saboteurImages) {
        setPreferredSize(winSize);
        setLayout(new BorderLayout());

        //háttér
        try {
            backgroundImage = ImageIO.read(new File("res/graphics/sivatag.png"));
        } catch (Exception e) {
            System.out.println("Error");
        }

        //számolás hogy éppen hanyadik elem
        int wsCounter = 0;
        int puCounter = 0;
        int cCounter = 0;

        //Elemek betöltése
        for (Steppable steppable : Game.getInstance().getSteppables()) {
            //helyek fixálása
            if (steppable instanceof WaterSource ws) {
                switch (wsCounter) {
                    case 0 -> vertices.put(steppable, new JWaterSource(new Point(62, 210), ws));
                    case 1 -> vertices.put(steppable, new JWaterSource(new Point(82, 615), ws));
                    case 2 -> vertices.put(steppable, new JWaterSource(new Point(810, 590), ws));
                    default ->
                            vertices.put(steppable, new JWaterSource(new Point(new Random().nextInt(winSize.width), new Random().nextInt(winSize.width)), ws));
                }
                wsCounter++;
            }
            if (steppable instanceof Cistern cist) {
                switch (cCounter) {
                    case 0 -> vertices.put(steppable, new JCistern(new Point(830, 220), cist));
                    case 1 -> vertices.put(steppable, new JCistern(new Point(600, 650), cist));
                    default ->
                            vertices.put(steppable, new JCistern(new Point(new Random().nextInt(winSize.width), new Random().nextInt(winSize.width)), cist));
                }
                cCounter++;
            }
            if (steppable instanceof Pump pump) {
                switch (puCounter) {
                    case 0 -> vertices.put(steppable, new JPump(new Point(275, 210), pump));
                    case 1 -> vertices.put(steppable, new JPump(new Point(600, 210), pump));
                    case 2 -> vertices.put(steppable, new JPump(new Point(820, 430), pump));
                    case 3 -> vertices.put(steppable, new JPump(new Point(320, 606), pump));
                    default ->
                            vertices.put(steppable, new JPump(new Point(new Random().nextInt(winSize.width), new Random().nextInt(winSize.width)), pump));
                }
                puCounter++;
            }

            addMouseListener(vertices.get(steppable));
            addMouseMotionListener(vertices.get(steppable));
            vertices.get(steppable).setParentPanel(this);

        }
        for (Pipe pipe : Game.getInstance().getPipes()) {
            Vertex v1 = vertices.get(pipe.getNeighbours().get(0));
            Vertex v2 = (pipe.getNeighbours().size() > 1 ? vertices.get(pipe.getNeighbours().get(1)) : null);
            edges.put(pipe, new Edge(v1, v2, pipe));

        }
        int plumberCounter = 0;
        int saboteurCounter = 0;

        for (Character character : Game.getInstance().getPlayers()) {
            if (character instanceof Plumber pu) {
                players.put(character, new JPlumber(pu, plumberImages.get(plumberCounter)));
                plumberCounter++;
            }
            if (character instanceof Saboteur sa) {
                players.put(character, new JSaboteur(sa, saboteurImages.get(saboteurCounter)));
                saboteurCounter++;
            }

            if (vertices.containsKey(character.getActField()))
                players.get(character).placeTo(vertices.get(character.getActField()).getCenter());
            else if (edges.containsKey(character.getActField()))
                players.get(character).placeTo(edges.get(character.getActField()).getCenter());

            infobar.setOpaque(false);
            add(infobar, BorderLayout.SOUTH);
        }

    }

    /**
     * kikapcsolja az összes elemen a highlightot
     */
    public void turnOffHighlight() {
        for (Steppable steppable : Game.getInstance().getSteppables()) {
            vertices.get(steppable).highlightOn(false);
        }
        for (Pipe pipe : Game.getInstance().getPipes()) {
            edges.get(pipe).highlightOn(false);
        }
        this.repaint();
    }

    /**
     * megadható vele az a frame, amelyen a panelt elhelyeztük
     *
     * @param mf Frame
     */
    public void addMainFrame(MainFrame mf) {
        frame = mf;
    }


    /**
     * Panel elemeinek kirajzolása
     *
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(backgroundImage, 0, 0, this);

        //Változások keresése:
        for (Steppable steppable : Game.getInstance().getSteppables()) {
            if (!vertices.containsKey(steppable)) {
                if (steppable instanceof Pump pump) {
                    if (pump.getNeighbours().size() >= 2) {
                        //Szomszédos pumpák helyeinek kiszámolása
                        Point nV1, nV2 = new Point(400, 400);  //Legyen valami kezdeti értékük
                        nV1 = vertices.get(pump.getNeighbours().get(0).getOtherNeighbour(pump)).getCenter();
                        if (pump.getNeighbours().get(1).getOtherNeighbour(pump) != null)
                            nV2 = vertices.get(pump.getNeighbours().get(1).getOtherNeighbour(pump)).getCenter();
                        vertices.put(steppable, new JPump(new Point((nV1.x + nV2.x) / 2, (nV1.y + nV2.y) / 2), pump));
                    } else {
                        vertices.put(steppable, new JPump(new Point(new Random().nextInt(winSize.width), new Random().nextInt(winSize.width)), pump));
                    }
                }

                //MouseListener és parentPanel beállítása
                addMouseListener(vertices.get(steppable));
                addMouseMotionListener(vertices.get(steppable));
                vertices.get(steppable).setParentPanel(this);
            }
        }
        for (Pipe pipe : Game.getInstance().getPipes()) {
            if (!edges.containsKey(pipe)) {
                Vertex v1 = vertices.get(pipe.getNeighbours().get(0));
                Vertex v2 = (pipe.getNeighbours().size() > 1 ? vertices.get(pipe.getNeighbours().get(1)) : null);
                edges.put(pipe, new Edge(v1, v2, pipe));
            }
        }


        //Élek kirajzolása
        for (Pipe pipe : Game.getInstance().getPipes()) {
            edges.get(pipe).paintComponent(g2);
        }
        //Csúcsok kirajzolása:
        for (Steppable steppable : Game.getInstance().getSteppables()) {
            vertices.get(steppable).paintComponent(g2);
        }
        //Játékosok kirajzolása
        for (Character character : Game.getInstance().getPlayers()) {
            players.get(character).highlightOn(false);      //kapcsoljuk ki a kiemelést mindenkin
            if (vertices.containsKey(character.getActField())) {
                players.get(character).placeTo(vertices.get(character.getActField()).getCenter());

            } else if (edges.containsKey(character.getActField()))
                players.get(character).placeTo(edges.get(character.getActField()).getCenter());

            players.get(character).paintComponent(g2);
        }
        //Aktuális játékost rajzoljuk ki újra, hogy felül legyen illetve emeljük ki
        players.get(Game.getInstance().getActCharacter()).highlightOn(true);
        players.get(Game.getInstance().getActCharacter()).paintComponent(g2);
    }

    /**
     * Lekerdezhező vele a vertices hashMap
     *
     * @return vertices
     */
    public HashMap<Steppable, Vertex> getVertices() {
        return vertices;
    }

    /**
     * Lekerdezhező vele a edges hashMap
     *
     * @return edges
     */
    public HashMap<Pipe, Edge> getEdges() {
        return edges;
    }

    /**
     * Lekerdezhező vele a players hashMap
     *
     * @return players
     */
    public HashMap<Character, JCharacter> getPlayers() {
        return players;
    }
}
