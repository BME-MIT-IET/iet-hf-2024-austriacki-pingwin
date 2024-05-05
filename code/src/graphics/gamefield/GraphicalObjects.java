package graphics.gamefield;

import javax.swing.*;
import java.awt.*;

/**
 * Absztrakt osztály, ami a kirajzolható elemeket reprezentálja
 */
public abstract class GraphicalObjects extends JComponent {
    protected Point center = new Point();
    protected Color color;
    protected ImageIcon graphicalImage;
    protected JPanel parentPanel;

    protected boolean highlight;  //kiemeli az aktuális játékost

    @Override
    public int getX() {
        return center.x;
    }

    @Override
    public int getY() {
        return center.y;
    }

    public abstract void paintComponent(Graphics g);

    public void setParentPanel(JPanel parentPanel) {
        this.parentPanel = parentPanel;
    }

    public JPanel getParentPanel() {
        return parentPanel;
    }

    public Point getCenter() {
        return center;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void highlightOn(boolean value) {
        highlight = value;
    }
}

