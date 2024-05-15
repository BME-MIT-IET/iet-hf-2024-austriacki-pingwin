package graphics;

import model.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class InitGameFrame extends JDialog implements ActionListener {
    private JPanel contentPanel = new JPanel();     //Fő panel
    private JPanel saboteurButtonsPanel = new JPanel(new FlowLayout()); //Szabotőr gombjait tartalmazó panel
    private JPanel plumberButtonsPanel = new JPanel(new FlowLayout()); //Szerelő gombjait tartalmazó panel
    private JPanel saboteurPanel = new JPanel(); //Szabotőr főpanel
    private JPanel plumberPanel = new JPanel(); //Szerelő főpanel

    private HashMap<ImageIcon, JTextField> plumberPlayers = new HashMap<>(); //Szabotőrök képeit és textfieldjét tartalmazza
    private HashMap<ImageIcon, JTextField> saboteurPlayers = new HashMap<>(); //Szerelők képeit és textfieldjét tartalmazza
    private HashMap<JButton, ImageIcon> saboteurButtons = new HashMap<>(); //Szabotőrök gombjait és képeit tartalmazza
    private HashMap<JButton, ImageIcon> plumberButtons = new HashMap<>(); //Szerelők gombjait és képeit tartalmazza

    private Random random = new Random(); 

    /**
     * konstruktor
     *
     * @param parentFrame frame, amely meghívta
     */
    public InitGameFrame(JFrame parentFrame) {
        //Elemek inicializálása, fő beállítások végrehajtása
        super(parentFrame, "Írja be a játékosok neveit!", true);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(new Dimension(600, 300));
        setResizable(false);
        initButtons(true);
        initButtons(false);


        // Gomb létrehozása
        JButton startButton = new JButton("Indítás");
        startButton.setBackground(new Color(24, 141, 255));
        startButton.setFont(new Font("Arial", Font.BOLD, 16));
        startButton.setForeground(Color.white);
        startButton.setActionCommand("start");
        startButton.addActionListener(this);


        //Panel elemeinek felpakolása és beállítása
        contentPanel.setLayout(new GridBagLayout());
        saboteurPanel.setLayout(new BoxLayout(saboteurPanel, BoxLayout.Y_AXIS));
        plumberPanel.setLayout(new BoxLayout(plumberPanel, BoxLayout.Y_AXIS));


        //Szerelő és Szabotőr főpanelek
        TitledBorder plumberBorder = BorderFactory.createTitledBorder("Szerelők:");
        plumberBorder.setTitleFont(plumberBorder.getTitleFont().deriveFont(Font.BOLD, 16));
        TitledBorder saboteurBorder = BorderFactory.createTitledBorder("Szabotőrök:");
        saboteurBorder.setTitleFont(saboteurBorder.getTitleFont().deriveFont(Font.BOLD, 16));

        plumberPanel.setBorder(plumberBorder);
        saboteurPanel.setBorder(saboteurBorder);
        plumberPanel.add(plumberButtonsPanel);
        saboteurPanel.add(saboteurButtonsPanel);


        //Fő layout kiosztása
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.weightx = 1.0;
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPanel.add(plumberPanel, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        contentPanel.add(saboteurPanel, gbc);


        //textfield-ek kezdeti értékének betöltése
        updatePanel(plumberPanel, plumberButtonsPanel, plumberPlayers, plumberButtons);
        updatePanel(saboteurPanel, saboteurButtonsPanel, saboteurPlayers, saboteurButtons);


        // Frame megjelenítése
        add(contentPanel);
        add(startButton, BorderLayout.PAGE_END);
        setModal(true);
        setAlwaysOnTop(true);
        pack();
        setLocationRelativeTo(parentFrame);
    }

    /**
     * Játékosok panel frissítése
     *
     * @param mainPanel fő panel amelyekre helyezzük az elemeket
     * @param buttonPanel gombokat tartalmazó panel
     * @param playersMap játékosok képeit és textField-jét tartalmazó hashmap
     * @param buttonMap gombokat és képüket tartalmazó hashmap
     */
    private void updatePanel(JPanel mainPanel, JPanel buttonPanel, HashMap<ImageIcon, JTextField> playersMap, HashMap<JButton, ImageIcon> buttonMap) {
        //Panel letisztítása
        mainPanel.removeAll();
        mainPanel.add(buttonPanel);
        playersMap.clear();

        // Selected gombok értékének megfelelő számú textmező létrehozása és hozzáadása a panelhez
        for (JButton b : buttonMap.keySet()) {
            if (b.isSelected()) {
                ImageIcon image = buttonMap.get(b);
                JTextField textField = new JTextField(image.getDescription());
                JLabel pic = new JLabel(image);

                //Alapértelmezett érték a textField-hez
                textField.setForeground(Color.GRAY);
                textField.addFocusListener(new FocusAdapter() {
                    @Override
                    public void focusGained(FocusEvent e) {
                        if (textField.getText().equals(image.getDescription())) {
                            textField.setText("");
                            textField.setForeground(Color.BLACK);
                        }
                    }

                    @Override
                    public void focusLost(FocusEvent e) {
                        if (textField.getText().isEmpty()) {
                            textField.setText(image.getDescription());
                            textField.setForeground(Color.GRAY);
                        }
                    }
                });

                //Egy sorban lévő elemek
                JPanel linePanel = new JPanel(new BorderLayout());
                linePanel.add(pic, BorderLayout.WEST);
                linePanel.add(textField, BorderLayout.CENTER);

                //Elemek hozzáadása
                playersMap.put(image, textField);
                mainPanel.add(linePanel, BorderLayout.NORTH);
            }
        }

        // Panel frissítése
        contentPanel.revalidate();
        contentPanel.repaint();
        pack();
    }

    /**
     * Startgomb kezelése
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("start")) {

            Game.initGame(plumberPlayers.size(), saboteurPlayers.size());
            dispose();
        }
    }


    /**
     * Gombok inicializálása
     *
     * @param plumbers plumber vagy szabotőr gombokat akarjuk-e inicializálni
     */
    public void initButtons(boolean plumbers) {
        //Elemek inicializálása a boolean-nak megfelelően
        String filename;
        HashMap<JButton, ImageIcon> buttons;
        JPanel panel;
        if (plumbers) {
            filename = "plumber";
            buttons=plumberButtons;
            panel=plumberButtonsPanel;
        } else {
            filename = "saboteur";
            buttons=saboteurButtons;
            panel=saboteurButtonsPanel;
        }

        //Gombok elkészítése
        for (int i = 0; i < 5; i++) {
            ImageIcon blackandwhiteIcon = null;
            ImageIcon normalIcon = null;
            try {
                Image normalImage = ImageIO.read(getClass().getResourceAsStream("/graphics/players/" + filename + "_" + i + ".png"));

                Image blackandwhiteImage = ImageIO.read(getClass().getResourceAsStream("/graphics/players/" + filename + "_bw.png"));

                normalImage = normalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                blackandwhiteImage = blackandwhiteImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);

                // ImageIcon létrehozása az átméretezett képből
                normalIcon = new ImageIcon(normalImage);
                blackandwhiteIcon = new ImageIcon(blackandwhiteImage);

                //Random nevek a karakterek képeihez
                String[] names = new String[]{"Mátyás", "János", "Imre", "Géza", "Béla", "Ubul", "Győző", "Lajos", "Pityu", "Sanyi", "Józsi", "Gyula", "Álmos", "Szundi","Balukapitány","Axel","Benjoe","Botond","Dominika","Bálint1","Bálint2","Domi"};
                normalIcon.setDescription(names[this.random.nextInt(names.length)]);

            } catch (IOException e) {
                System.err.println("Hiba az ikonok betöltése során!");
            }

            //Gomb beállítása
            JButton button = new JButton("", blackandwhiteIcon);
            if (i <= 1) button.setSelected(true);  //első kettő legyen alapból benyomva
            button.setRolloverIcon(normalIcon);
            button.setPressedIcon(normalIcon);
            button.setSelectedIcon(normalIcon);
            button.addActionListener(e -> {
                if (button.isSelected() && calcSelectedButtons(buttons) == 2) {
                    Toolkit.getDefaultToolkit().beep(); //legalább 2 mindig legyen
                    this.setTitle("Válasszon legalább 2-2 játékost!");
                } else {
                    button.setSelected(!button.isSelected());
                    this.setTitle("Írja be a játékosok neveit!");   //Állítsuk vissza a frame címét
                    //Frissítsük a listát
                    if (plumbers) updatePanel(plumberPanel, plumberButtonsPanel, plumberPlayers, plumberButtons);
                    else updatePanel(saboteurPanel, saboteurButtonsPanel, saboteurPlayers, saboteurButtons);

                }
            });
            buttons.put(button, normalIcon);
            panel.add(button);
        }
    }


    /**
     * Szerelők képét és nevét lehet lekérdezni vele
     *
     * @return ImageIcon, mely desctiption-ben tartalmazza a játékos nevét
     */
    public ArrayList<ImageIcon> getPlumberImages() {
        ArrayList<ImageIcon> players = new ArrayList<>();
        for (ImageIcon img : plumberPlayers.keySet()) {
            img.setDescription(plumberPlayers.get(img).getText()); //képhez játékosnév beállítása a leírásba
            players.add(img);
        }
        return players;
    }

    /**
     * Szabotőrök képét és nevét lehet lekérdezni vele
     *
     * @return ImageIcon, mely desctiption-ben tartalmazza a játékos nevét
     */
    public ArrayList<ImageIcon> getSaboteurImages() {
        ArrayList<ImageIcon> players = new ArrayList<>();
        for (ImageIcon img : saboteurPlayers.keySet()) {
            img.setDescription(saboteurPlayers.get(img).getText()); //képhez játékosnév beállítása a leírásba
            players.add(img);
        }
        return players;
    }

    /**
     * Megszámolja hogy hány gomb aktív
     *
     * @param buttonMap gombokat tartalmazó map
     * @return aktív (lenyomott) gombok száma
     */
    private int calcSelectedButtons(HashMap<JButton, ImageIcon> buttonMap) {
        int val = 0;
        for (JButton b : buttonMap.keySet())
            if (b.isSelected()) val++;
        return val;
    }
}
