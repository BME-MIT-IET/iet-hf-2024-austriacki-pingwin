package graphics.gamefield;

import model.fields.WaterSource;

import javax.swing.*;
import java.awt.*;

/**
 * WaterSourcet reprezentáló grafikus osztály
 */
public class JWaterSource extends Vertex{
    WaterSource observedWaterSource;

    /**
     * Létrehozza magát a kapott helyere
     * @param center középpontja
     */
    public JWaterSource(Point center, WaterSource ws){
        super(center);
        observedWaterSource=ws;
        color= Color.blue;

        //Alap textura betöltése
        ImageIcon tmpIcon = new ImageIcon("res/graphics/objects/watersource.png");
        Image resizedTmpImage = tmpIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        graphicalImage = new ImageIcon(resizedTmpImage);
    }


    /**
     * Kirajzolja magát
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g2);
        g2.drawImage(graphicalImage.getImage(), center.x - graphicalImage.getIconWidth() / 2, center.y - graphicalImage.getIconHeight() / 2, null);
    }
}
