package graphics.gamefield;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Csomópontokat reprezentáló grafikus osztály
 */
public class Vertex extends GraphicalObjects implements MouseListener, MouseMotionListener {

    int radius = 30;
    boolean catched = false; //egérrel rá van-e kattintva

    /**
     * Létrehozza magát
     */
    public Vertex() {
        color = new Color(0, 0, 0, 0);
    }

    /**
     * Létrehozza magát paraméterrel
     * @param center közepe
     */
    public Vertex(Point center) {
        this();
        this.center=center;
    }

    /**
     * Kirajzolja magát (egy kört)
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        if(highlight) g2.setColor(Color.GREEN);
        else g2.setColor(color);

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.fillOval(center.x - radius, center.y - radius, 2 * radius, 2 * radius);
    }

    /**
     * Kattintás esetén kiírja a kattintás helyét
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
    }

    /**
     * Kezeli ha rákattintottak
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        Point p = new Point(e.getX(), e.getY());
        if (nearlyEqual(p.x, center.x, radius/2) && nearlyEqual(p.y, center.y, radius/2)) {
            catched = true;
        }
    }

    /**
     * Kezeli ha már nincs rákattintva
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        catched = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
        catched = false;
    }

    /**
     * Ha rákattintottak és arrébbhúzzák, arrébbrajzolja magát
     * @param e the event to be processed
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        if (catched) {
            center = e.getPoint();
            this.getParentPanel().repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }


    /**
     * Közelítőleg egyenlőség vizsgálata
     *
     * @param a egyik szám
     * @param b másik szám
     * @param max maximális eltérés (határérték)
     * @return egyenlőek-e egy határértéken belül
     */
    private boolean nearlyEqual(int a, int b, int max) {
        return Math.abs(a - b) <= max;
    }


}
