package graphics;

import graphics.panels.CommandsPanel;
import graphics.panels.GameFieldPanel;
import graphics.panels.MainMenuPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Főablakot reprezentáló osztály
 */
public class MainFrame extends JFrame {
    MainMenuPanel mainMenuPanel; //menüpanel
    CommandsPanel commandsPanel; //parancsgombok panelje

    /**
     * Főablak létrehozása és beállítása
     */
    public MainFrame() {
        super("Lords of Drukmákor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        try {
            ImageIcon icon = new ImageIcon("res/graphics/icon.png");
            setIconImage(icon.getImage());
        } catch (Exception e) {
            System.out.println("Error");
        }

        mainMenuPanel = new MainMenuPanel(this);

        add(mainMenuPanel);
        pack();


        setVisible(true);
    }

    /**
     * Elindít egy új játékot
     * Felugró ablakban megkérdezi a játékosok számát
     */
    public void startGame() {
        InitGameFrame igf = new InitGameFrame(this);
        igf.setVisible(true);

        GameFieldPanel.start(igf.getPlumberImages(),igf.getSaboteurImages());
        GameFieldPanel.getInstance().addMainFrame(this);
        commandsPanel = new CommandsPanel();
        commandsPanel.addMainFrame(this);

        //panel cserélése
        changeToGamePanel();
    }

    /**
     * Lecseréli a benne lévő panelt a gameFieldPanel-re
     */
    public void changeToGamePanel() {
        getContentPane().removeAll();

        add(commandsPanel, BorderLayout.WEST);
        add(GameFieldPanel.getInstance());

        revalidate();
        repaint();
        pack();
    }

    /**
     * Lecseréli a benne lévő panelt a mainMenuPanel-re
     */
    public void changeToMenuPanel() {
        getContentPane().removeAll();
        add(mainMenuPanel);
        revalidate();
        repaint();
        pack();
    }

}
