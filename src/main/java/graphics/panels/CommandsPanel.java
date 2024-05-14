package graphics.panels;

import graphics.MainFrame;
import graphics.gamefield.Edge;
import graphics.gamefield.GraphicalObjects;
import graphics.gamefield.Vertex;
import model.Game;
import model.characters.Plumber;
import model.fields.ActiveField;
import model.fields.Field;
import model.fields.Pipe;
import proto.CommandHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Objects;

/**
 * A játék vezérlő gombjait tartalmazó, kezelő osztály
 */
public class CommandsPanel extends JPanel implements ActionListener, MouseListener {
    CommandHelper ch = new CommandHelper();
    JButton bFix = new JButton();
    JButton bChange = new JButton();
    JButton bDamage = new JButton();
    JButton bSlippery = new JButton();
    JButton bSticky = new JButton();
    JButton bPickUpPipe = new JButton();
    JButton bPlacePipe = new JButton();
    JButton bPickUpPump = new JButton();
    JButton bPlacePump = new JButton();
    JButton bNextPlayer = new JButton();
    ArrayList<JButton> chooseButtons = new ArrayList<>();
    JButton bMove = new JButton();

    JPanel choosePanel1 = new JPanel();
    JPanel choosePanel2 = new JPanel();

    JPanel buttonPanel = new JPanel();
    JPanel numPanel = new JPanel();
    JPanel actPlayerPanel = new JPanel(new GridLayout(1, 1)) {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            ImageIcon background = new ImageIcon(Objects.requireNonNull(getClass().getResource("/graphics/hotbarIcons/hotbar_clear.png")));
            g.drawImage(background.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
    };

    MainFrame frame;
    boolean change = false;
    boolean pickUpPipe = false;
    boolean move = false;
    String choosenInput = "-1";
    String choosenOutput = "-1";

    int w = 135;
    int h = 135;

    /**
     * Panel létrehozása, rá kerülő dolgok init állapotának beállítása
     */
    public CommandsPanel() {
        this.setPreferredSize(new Dimension(w * 2, h * 6 + h / 2));

        add(buttonPanel);
        buttonPanel.setPreferredSize(new Dimension(w * 2, h * 6));
        buttonPanel.setLayout(new GridLayout(0, 2));

        add(numPanel);
        numPanel.setPreferredSize(new Dimension(w * 2, h / 3));
        numPanel.setLayout(new GridLayout(0, 2));

        initButtons(buttonPanel, numPanel);
        importButtonImages();
        checkButtons();

    }

    /**
     * Inicializálja a gombokat és hozzáadja a panelhez
     */
    private void initButtons(JPanel buttonPanel, JPanel numPanel) {
        bFix.addActionListener(this);
        bChange.addActionListener(this);
        bDamage.addActionListener(this);
        bSlippery.addActionListener(this);
        bSticky.addActionListener(this);
        bPickUpPipe.addActionListener(this);
        bPlacePipe.addActionListener(this);
        bPickUpPump.addActionListener(this);
        bPlacePump.addActionListener(this);
        bNextPlayer.addActionListener(this);
        bMove.addActionListener(this);

        buttonPanel.add(actPlayerPanel);

        buttonPanel.add(bChange);
        buttonPanel.add(bFix);
        buttonPanel.add(bDamage);
        buttonPanel.add(bSlippery);
        buttonPanel.add(bSticky);
        buttonPanel.add(bPickUpPipe);
        buttonPanel.add(bPlacePipe);
        buttonPanel.add(bPickUpPump);
        buttonPanel.add(bPlacePump);
        buttonPanel.add(bMove);
        buttonPanel.add(bNextPlayer);

        numPanel.add(choosePanel1);
        choosePanel1.setLayout(new GridLayout(1, 3));
        choosePanel1.setPreferredSize(new Dimension(w, h / 3));
        for (int i = 0; i < 3; i++) {
            JButton tmp = new JButton();
            tmp.addActionListener(this);
            tmp.addMouseListener(this);
            choosePanel1.add(tmp);
            chooseButtons.add(tmp);
        }
        numPanel.add(choosePanel2);
        choosePanel2.setLayout(new GridLayout(1, 3));
        choosePanel2.setPreferredSize(new Dimension(w, h / 3));
        for (int i = 3; i < 6; i++) {
            JButton tmp = new JButton();
            tmp.addActionListener(this);
            tmp.addMouseListener(this);
            choosePanel2.add(tmp);
            chooseButtons.add(tmp);
        }

        ImageIcon img = GameFieldPanel.getInstance().getPlayers().get(Game.getInstance().getActCharacter()).getPlayerIcon();
        JLabel text = new JLabel("Játékos: " + img.getDescription() + "        Szerelők pontjai: " + Game.getInstance().getPpo().getCisternWater() + "        Szabotőrök pontjai: " + Game.getInstance().getSpo().getDesertWater());
        text.setFont(new Font("Aerial", Font.BOLD, 30));
        text.setForeground(Color.white);
        GameFieldPanel.getInstance().infobar.add(text);
    }

    /**
     * Leelenőrzi, hogy az aktuális játékosnak melyik gombok érhetőek el és ezek alapján teszi használhatóvá őket
     */
    private void checkButtons() {
        ArrayList<String> playerCommands = Game.getInstance().getActCharacter().getCommands();
        ArrayList<String> canDoCommand = Game.getInstance().getActCharacter().getActField().getCommands();

        boolean done = Game.getInstance().getActCharacter().getActionPerformed();

        bFix.setEnabled(playerCommands.contains("fix") && !done && canDoCommand.contains("fix") && Game.getInstance().getActCharacter().getActField().isDamaged() && !Game.getInstance().getActCharacter().getActField().isPickedUp());
        bChange.setEnabled(playerCommands.contains("change") && !done && canDoCommand.contains("change"));
        bDamage.setEnabled(playerCommands.contains("damage") && !done && canDoCommand.contains("damage") && !Game.getInstance().getActCharacter().getActField().isDamaged() && !Game.getInstance().getActCharacter().getActField().isPickedUp());
        bSlippery.setEnabled(playerCommands.contains("slippery") && !done && canDoCommand.contains("slippery") && !Game.getInstance().getActCharacter().getActField().isSlippery() && !Game.getInstance().getActCharacter().getActField().isPickedUp());
        bSticky.setEnabled(playerCommands.contains("sticky") && !done && canDoCommand.contains("sticky") && !Game.getInstance().getActCharacter().getActField().isSticky() && !Game.getInstance().getActCharacter().getActField().isPickedUp());
        bPickUpPipe.setEnabled(playerCommands.contains("pickupPipe") && !done && canDoCommand.contains("pickupPipe") && !Game.getInstance().getActCharacter().hasPipe());
        bPlacePipe.setEnabled(playerCommands.contains("placedownPipe") && !done && canDoCommand.contains("placedownPipe") && Game.getInstance().getActCharacter().hasPipe() && !Game.getInstance().getActCharacter().getActField().hasMaxNeighbours());
        bPickUpPump.setEnabled(playerCommands.contains("pickupPump") && !done && canDoCommand.contains("pickupPump") && !Game.getInstance().getActCharacter().hasPump());
        bPlacePump.setEnabled(playerCommands.contains("placedownPump") && !done && canDoCommand.contains("placedownPump") && Game.getInstance().getActCharacter().hasPump() && !Game.getInstance().getActCharacter().getActField().isPickedUp());
        bMove.setEnabled(!Game.getInstance().getActCharacter().getMovePerformed());

        if (!change && !move && !pickUpPipe) {
            hideButtons();
        }

        if (GameFieldPanel.getInstance() != null) {
            ImageIcon img = GameFieldPanel.getInstance().getPlayers().get(Game.getInstance().getActCharacter()).getPlayerIcon();
            actPlayerPanel.removeAll();
            ImageIcon img1 = new ImageIcon(img.getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
            actPlayerPanel.add(new JLabel(img1));
        }
        revalidate();
        repaint();
    }

    /**
     * Gombok lenyomásának kezelése
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        if (clickedButton == bFix) ch.fix(null);
        if (clickedButton == bDamage) ch.damage(null);
        if (clickedButton == bSlippery) ch.slippery(null);
        if (clickedButton == bSticky) ch.sticky(null);
        if (clickedButton == bPlacePipe) {
            Field actField = Game.getInstance().getActCharacter().getActField();
            Vertex actVertex = GameFieldPanel.getInstance().getVertices().get(actField); //aktuális mező grafikus objektuma
            //aktuális grafikus cső és játékos lekérdezése
            Pipe actPipe = ((Plumber) Game.getInstance().getActCharacter()).getPipe();
            Edge actJPipe = GameFieldPanel.getInstance().getEdges().get(actPipe);
            //ha nincs tele a mező lerakjuk a csövet
            if (actJPipe != null && !((ActiveField) Game.getInstance().getActCharacter().getActField()).isFull()) {
                actJPipe.placedDownPipe(actVertex);
            }
            ch.placedownPipe(null);
            GameFieldPanel.getInstance().repaint();
        }
        if (clickedButton == bPickUpPump) ch.pickupPump(null);
        if (clickedButton == bPlacePump) ch.placedownPump(null);

        if (clickedButton == bChange) {
            changeButtonPressed();
            change = true;
        }
        if (clickedButton == bPickUpPipe) {
            pickupPipeButtonPressed();
            pickUpPipe = true;
        }
        if (clickedButton == bMove) {
            moveButtonPressed();
            move = true;
        }

        if (chooseButtons.contains(clickedButton)) {
            chooseButtonPressed(chooseButtons.indexOf(clickedButton));
            GameFieldPanel.getInstance().turnOffHighlight();
        }

        if (clickedButton == bNextPlayer) { //Akció gombok resetelése és játék léptetése
            change = false;
            pickUpPipe = false;
            move = false;
            ch.nextplayer(null);

            GameFieldPanel.getInstance().infobar.removeAll();
            ImageIcon img = GameFieldPanel.getInstance().getPlayers().get(Game.getInstance().getActCharacter()).getPlayerIcon();
            JLabel text = new JLabel("Játékos: " + img.getDescription() + "        Szerelők pontjai: " + Game.getInstance().getPpo().getCisternWater() + "        Szabotőrök pontjai: " + Game.getInstance().getSpo().getDesertWater());
            text.setFont(new Font("Aerial", Font.BOLD, 30));
            text.setForeground(Color.white);
            GameFieldPanel.getInstance().infobar.add(text);

            if (Game.getInstance().isGameOver()) {
                frame.getContentPane().removeAll();

                frame.add(new ScorePanel(frame));
                frame.revalidate();
                frame.repaint();
                frame.pack();
            }
        }
        if (clickedButton != bChange && clickedButton != bMove && clickedButton != bPickUpPipe && !chooseButtons.contains(clickedButton)) { //chooseButtonok reseteléséhez, ha pl move után nem chooseButtont nyom
            change = false;
            pickUpPipe = false;
            move = false;
        }
        GameFieldPanel.getInstance().repaint();
        checkButtons(); //Lehetséges akciók alapján gombok frissítése
    }

    /**
     * Mozgás kiválasztásakor szomszádmennyiségű gomb aktiválása
     */
    private void moveButtonPressed() {
        hideButtons();
        pickUpPipe = false;
        change = false;

        int neighBourSize = Game.getInstance().getActCharacter().getActField().getNeighbours().size();
        revealButtons(neighBourSize);
    }

    /**
     * Csőfelvétel kiválasztásakor szomszádmennyiségű gomb aktiválása
     */
    private void pickupPipeButtonPressed() {
        hideButtons();
        move = false;
        change = false;
        int neighBourSize = Game.getInstance().getActCharacter().getActField().getNeighbours().size();
        revealButtons(neighBourSize);
    }

    /**
     * Pumpabeállításk kiválasztásakor szomszédmennyiségű gomb aktiválása
     */
    private void changeButtonPressed() {
        hideButtons();
        pickUpPipe = false;
        move = false;
        int neighBourSize = Game.getInstance().getActCharacter().getActField().getNeighbours().size();
        revealButtons(neighBourSize);
    }

    /**
     * ChooseButtonok elrejtése, ha nincs szükség rájuk
     */
    void hideButtons() {
        for (JButton b : chooseButtons) {
            b.setEnabled(false);
            b.setVisible(false);
        }
    }

    /**
     * ChooseButtonok felfedése a szomszédoknak megfelelő számban
     *
     * @param neighBourSize Szomszédok mérete alapján állítjuk a gombokat
     */
    void revealButtons(int neighBourSize) {
        for (int i = 0; i < neighBourSize; i++) {
            JButton b = chooseButtons.get(i);
            b.setEnabled(true);
            b.setVisible(true);
        }
    }

    /**
     * A választható paraméterű függvényhívások meghívására
     * (change, pickupPipe, move)
     *
     * @param buttonNum Gomb száma, amelyik kiválasztásra került
     */
    void chooseButtonPressed(int buttonNum) {
        String bNum = Integer.toString(buttonNum);
        if (change) {
            if (choosenInput.equals("-1")) {
                choosenInput = bNum;
                chooseButtons.get(buttonNum).setEnabled(false);
            } else if (choosenOutput.equals("-1")) {
                choosenOutput = bNum;
                ch.change(new String[]{choosenInput, choosenOutput});
                change = false;
                choosenInput = "-1";
                choosenOutput = "-1";
            }
        }
        if (pickUpPipe) {
            ch.pickupPipe(new String[]{bNum});
            //cső felvétele látszódjon
            Pipe actPipe = ((Plumber) Game.getInstance().getActCharacter()).getPipe();
            Edge actJPipe = GameFieldPanel.getInstance().getEdges().get(actPipe);
            if (actJPipe != null) {
                Vertex actVertex = GameFieldPanel.getInstance().getVertices().get(Game.getInstance().getActCharacter().getActField()); //aktuális mező grafikus objektuma
                actJPipe.pickedUpPipe(actVertex); //felveszzük a csövet
            }
            pickUpPipe = false;
        }
        if (move) {
            ch.move(new String[]{bNum});

            move = false;
        }
    }

    /**
     * Mainframe beállításas
     *
     * @param mf hivatkozás mf-re
     */
    public void addMainFrame(MainFrame mf) {
        frame = mf;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    /**
     * A választó gombok fölé kerülő egérmozgásra kijelöli az annak megfelelő objektumot
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {
        JButton enteredButton = (JButton) e.getSource();

        GraphicalObjects choosenObject;

        for (int i = 0; i < chooseButtons.size(); i++) {
            if (enteredButton == chooseButtons.get(i)) {
                if (GameFieldPanel.getInstance().getVertices().containsKey(Game.getInstance().getActCharacter().getActField().getNeighbours().get(i)))
                    choosenObject = GameFieldPanel.getInstance().getVertices().get(Game.getInstance().getActCharacter().getActField().getNeighbours().get(i));
                else {
                    choosenObject = GameFieldPanel.getInstance().getEdges().get(Game.getInstance().getActCharacter().getActField().getNeighbours().get(i));
                }
                choosenObject.highlightOn(true);
                break;
            }
        }
        GameFieldPanel.getInstance().repaint();
    }

    /**
     * Kezeli ha már nincs az adott gomb felett az egérmutató
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {
        GameFieldPanel.getInstance().turnOffHighlight();
    }

    /**
     * Gombok képeinek betöltésére segédfüggvény
     *
     * @param button a gomb amire a képeket akarjuk betölteni
     * @param name   féjlnév
     */
    public void loadButtonPicturepack(JButton button, String name, int Scale) {
        int scale = 1;
        if (Scale != 0) {
            scale = Scale;
        }

        ImageIcon normalIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/graphics/hotbarIcons/hotbar_" + name + ".png")));
        button.setIcon(new ImageIcon(normalIcon.getImage().getScaledInstance(w / scale, h / scale, Image.SCALE_SMOOTH)));
    }

    /**
     * Képek beimportálása a gombokhoz
     */
    private void importButtonImages() {
        loadButtonPicturepack(bFix, "fix", 0);
        loadButtonPicturepack(bChange, "change", 0);
        loadButtonPicturepack(bDamage, "damage", 0);
        loadButtonPicturepack(bSlippery, "slippery", 0);
        loadButtonPicturepack(bSticky, "sticky", 0);
        loadButtonPicturepack(bPickUpPipe, "pickuppipe", 0);
        loadButtonPicturepack(bPlacePipe, "placepipe", 0);
        loadButtonPicturepack(bPickUpPump, "pickuppump", 0);
        loadButtonPicturepack(bPlacePump, "placepump", 0);
        loadButtonPicturepack(bMove, "move", 0);
        loadButtonPicturepack(bNextPlayer, "nextplayer", 0);
        loadButtonPicturepack(chooseButtons.get(0), "1", 3);
        loadButtonPicturepack(chooseButtons.get(1), "2", 3);
        loadButtonPicturepack(chooseButtons.get(2), "3", 3);
        loadButtonPicturepack(chooseButtons.get(3), "4", 3);
        loadButtonPicturepack(chooseButtons.get(4), "5", 3);
    }
}
