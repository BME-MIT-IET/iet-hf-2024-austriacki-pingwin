package graphics.gamefield;

import model.fields.Cistern;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Ciszternákat reprezentáló grafikus osztály
 */
public class JCistern extends Vertex {
    Cistern observedCistern;


    /**
     * Létrehozza magát paraméterrel
     *
     * @param center hova legyen kirajzolva
     */
    public JCistern(Point center, Cistern cist) {
        this.center=center;
        observedCistern = cist;
        color = Color.orange;

        //Alap textura betöltése
        ImageIcon tmpIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/graphics/objects/cistern.png")));
        Image resizedTmpImage = tmpIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        graphicalImage = new ImageIcon(resizedTmpImage);
    }


    /**
     * Kirajzolja magát
     *
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        g2.drawImage(graphicalImage.getImage(), center.x - graphicalImage.getIconWidth() / 2, center.y - graphicalImage.getIconHeight() / 2, null);
    }
}
