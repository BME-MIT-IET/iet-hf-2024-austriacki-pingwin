package graphics.panels;

import graphics.MainFrame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainMenuPanel extends JPanel implements ActionListener {
    Image backgroundImage; //háttér
    private JButton bStart; //start gomb
    private MainFrame frame; //főablak

    /**
     * Inicializálja a panelt
     * beolvassa a háttérképét és rárakja a start gombot
     * @param frame keret megadása
     */
    public MainMenuPanel(MainFrame frame) {
        this.frame = frame;
        setLayout(new FlowLayout());

        bStart = new JButton("START");
        bStart.setPreferredSize(new Dimension(frame.getSize().width/5,frame.getSize().height/10));
        bStart.setBackground(new Color(98, 0, 0));
        bStart.setFont(new Font("Arial", Font.BOLD, 20));
        bStart.setForeground(Color.white);
        bStart.addActionListener(this);

        //háttér
        try { 
            backgroundImage = ImageIO.read(getClass().getResourceAsStream("/graphics/mainmenu.png"));
            backgroundImage = backgroundImage.getScaledInstance(frame.getSize().width, frame.getSize().height, Image.SCALE_SMOOTH);
            setPreferredSize(frame.getSize());
        } catch (Exception e) {
            System.out.println("MainMenuPanel Error: "+e.getMessage());
        }
        setLayout(new FlowLayout(FlowLayout.CENTER, 0, (int) (frame.getSize().height*0.6)));

        add(bStart);
    }

    /**
     * Kirajzolja a hátteret
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
     * START-ra elindítja a játékot
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
            if (clickedButton == bStart) {
                frame.startGame();
            }
    }

}
