package graphics.gamefield;

import graphics.panels.GameFieldPanel;
import model.fields.Pump;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Pumpát reprezentáló grafikus osztály
 */
public class JPump extends Vertex {
    Pump observedPump;
    ImageIcon damagedImage;


    /**
     * Létrehozza magát a kapott helyere
     * @param center középpontja
     */
    public JPump(Point center, Pump pump) {
        super(center);
        observedPump = pump;
        color = Color.white;

        //Alap és damaged textura betöltése
        ImageIcon tmpIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/graphics/objects/waterpump.png")));
        Image resizedTmpImage = tmpIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        graphicalImage = new ImageIcon(resizedTmpImage);

        tmpIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/graphics/objects/waterpump_damaged.png")));
        resizedTmpImage = tmpIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        damagedImage = new ImageIcon(resizedTmpImage);
    }

    /**
     * Kirajzolja magát
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        double colorRatio = 255 * (double) observedPump.getTankLevel() / (double) observedPump.getTankCapacity();
        int c = (int) (255 - colorRatio);
        color = new Color(c, c, 255);
        g2.setColor(color);
        if (observedPump.isDamaged()) color = Color.black;

        super.paintComponent(g2);

        if (observedPump.isDamaged())
            g2.drawImage(damagedImage.getImage(), center.x - damagedImage.getIconWidth() / 2, center.y - damagedImage.getIconHeight() / 2, null);
        else
            g2.drawImage(graphicalImage.getImage(), center.x - graphicalImage.getIconWidth() / 2, center.y - graphicalImage.getIconHeight() / 2, null);

        //Input és OutPut vonalak felnyilazása
        GameFieldPanel.getInstance().getEdges().get((observedPump).getOutput()).drawArrow(g2, true, this);
        GameFieldPanel.getInstance().getEdges().get((observedPump).getInput()).drawArrow(g2, false, this);

    }
}
