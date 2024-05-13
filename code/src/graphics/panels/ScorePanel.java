package graphics.panels;

import graphics.MainFrame;
import model.Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ScorePanel extends JPanel implements ActionListener {
    Image backgroundImage; //háttérkép
    private MainFrame frame; //főablak
    private JButton bReturnToMenu = new JButton("Főmenübe"); //vissza a menübe gomb

    JLabel lResults = new JLabel("Eredmény"); //eredmény szövege
    JLabel lWinner = new JLabel(); //győztes csapat szövege
    JLabel lPlumberPoints = new JLabel(); //szerelők pontjai szöveg
    JLabel lSaboteurPoints = new JLabel(); //szabotőrök pontjai szöveg

    Dimension size = new Dimension(800, 800);

    /**
     * Inicializálja a panelt, kiírja a pontokat és a győztest
     */
    public ScorePanel() {
        setPreferredSize(size);
        setLayout(new BorderLayout());

        //háttér beolvasása
        try {
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/graphics/scoremenu.jpg"));
            backgroundImage = backgroundImage.getScaledInstance(size.width, size.height, Image.SCALE_SMOOTH);
        } catch (Exception e) {
            System.out.println("ScorePanel Error: "+e.getMessage());
        }

        //felső felirat
        lResults.setFont(new Font("Arial", Font.BOLD, 50));
        JPanel pResult = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, FlowLayout.CENTER)); //középen legyen
        pResult.setOpaque(false); //átlátszó legyen
        pResult.add(lResults);
        add(pResult, BorderLayout.NORTH);

        //pontoknak és győztesnek panel
        JPanel pWinnerAndPoints = new JPanel(new BorderLayout());
        pWinnerAndPoints.setOpaque(false); //átlátszó

        //győztes kiírása
        Game game = Game.getInstance();
        int plumberPoints = game.getPpo().getCisternWater();
        int saboteurPoints = game.getSpo().getDesertWater();
        lWinner.setFont(new Font("Arial", Font.BOLD, 50));
        if (plumberPoints > saboteurPoints) {
            lWinner.setForeground(new Color(30, 48, 176));
            lWinner.setText("Szerelők nyertek");
        } else if (plumberPoints < saboteurPoints) {
            lWinner.setForeground(new Color(187, 0, 0));
            lWinner.setText("Szabotőrök nyertek");
        } else {
            lWinner.setForeground(Color.orange);
            lWinner.setText("Döntetlen");
        }
        JPanel pWinner = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, FlowLayout.CENTER)); //középen legyen
        pWinner.setOpaque(false); //átlátszó legyen
        pWinner.add(lWinner);
        pWinnerAndPoints.add(pWinner, BorderLayout.NORTH);

        //pontok kiírása
        JPanel pPoints = new JPanel(new FlowLayout());
        pPoints.setOpaque(false);
        lPlumberPoints.setText("Szerelők pontjai: " + plumberPoints);
        lPlumberPoints.setFont(new Font("Arial", Font.BOLD, 30));
        lPlumberPoints.setForeground(Color.white);
        lSaboteurPoints.setText("Szabotőrök pontjai: " + saboteurPoints);
        lSaboteurPoints.setFont(new Font("Arial", Font.BOLD, 30));
        lSaboteurPoints.setForeground(Color.white);
        pWinnerAndPoints.add(lPlumberPoints, BorderLayout.WEST);
        pWinnerAndPoints.add(lSaboteurPoints, BorderLayout.EAST);

        add(pWinnerAndPoints, BorderLayout.CENTER);

        //gomb hozzáadása
        bReturnToMenu.addActionListener(this);
        bReturnToMenu.setBackground(new Color(24, 141, 255));
        bReturnToMenu.setFont(new Font("Arial", Font.BOLD, 30));
        bReturnToMenu.setForeground(Color.white);
        JPanel pButton = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, FlowLayout.CENTER)); //középen legyen
        pButton.setOpaque(false); //átlátszó legyen
        pButton.add(bReturnToMenu);
        add(pButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * paraméteres konstruktor
     * @param mf a fő frame, amibe elhelyezkedik
     */
    public ScorePanel(MainFrame mf) {
        this();
        frame = mf;
    }

    /**
     * Kirajzolja a hátterét a panelnak
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Rajzoljuk ki a háttérképet a JPanel-en
        g2.drawImage(backgroundImage, 0, 0, this);
    }

    /**
     * Meghívja a mainframe függvényét, hogy váltson menüpanelre
     * A "Menübe" gombot kezeli
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        frame.changeToMenuPanel();
    }
}
