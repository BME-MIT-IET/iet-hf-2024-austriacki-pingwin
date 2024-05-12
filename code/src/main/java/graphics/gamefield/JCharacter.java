package graphics.gamefield;

import model.characters.Character;
import model.fields.Field;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

/**
 * A Charactert reprezentáló grafikus osztály
 */
public abstract class JCharacter extends GraphicalObjects {
    protected Character observedCharacter;
    private Field previousField;

    protected String playerName;        //Játékos neve

    Point vector;   //center-től eltolásvektor

    /**
     * Kirajzolja magát
     *
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.drawImage(graphicalImage.getImage(), center.x - graphicalImage.getIconWidth()/2, center.y - graphicalImage.getIconHeight()/2, null);

        //ha ki van jelölve, jelzi
        if (highlight) {
            ImageIcon arrowImageIcon=new ImageIcon("res/graphics/arrow.png");
            g2.drawImage(arrowImageIcon.getImage(), center.x-arrowImageIcon.getIconWidth()/2, center.y-graphicalImage.getIconHeight()/2-arrowImageIcon.getIconHeight(), null);

        }
    }

    /**
     * Adott pontra, vagy annak közelébe helyezi a karaktert
     *
     * @param p pont
     */
    public void placeTo(Point p) {
        int epsilon = 10;

        //Ha másik elemre lépett rakjuk picit arrébb
        if (observedCharacter.getActField() != previousField||previousField==null) {
            vector = new Point(new Random().nextInt(2 * epsilon) - epsilon, new Random().nextInt(2 * epsilon) - epsilon);
            previousField = observedCharacter.getActField();
        }

        center = new Point(p.x + vector.x, p.y + vector.y);

    }

    public ImageIcon getPlayerIcon(){
        return graphicalImage;
    }

}