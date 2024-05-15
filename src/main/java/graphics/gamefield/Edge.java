package graphics.gamefield;

import graphics.panels.GameFieldPanel;
import model.fields.Pipe;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.util.Random;

/**
 * Csöveket reprezentáló grafikus osztály
 */
public class Edge extends GraphicalObjects {
    Vertex v1;
    Vertex v2; //csomópontok amiket összeköt

    //fel vannak-e véve a végek
    boolean v1PickedUp = false;
    boolean v2PickedUp = false;
    Pipe observedPipe;
    int thickness=10; //vastagsága
    Stroke actStroke; //vonal jellege (sima, szaggatott)

    /**
     * Inicializálja magát
     * @param v1 egyik csomópont amit összeköt
     * @param v2 másik csomópont amit összeköt
     */
    public Edge(Vertex v1, Vertex v2, Pipe pipe) {
        this.v1=v1;
        this.v2=v2;
        observedPipe=pipe;
        int epsilon = 150;
        Random rnd=new Random();
        //ha van üres vége, random helyezi el
        if(this.v1==null) this.v1=new Vertex(new Point(rnd.nextInt(v1.getX() +rnd.nextInt(2*epsilon) - epsilon),rnd.nextInt(v1.getY() +rnd.nextInt(2*epsilon) - epsilon)));
        if(this.v2==null) this.v2=new Vertex(new Point( (v1.center.x+rnd.nextInt(2*epsilon) - epsilon),  (v1.center.y+rnd.nextInt(2*epsilon) - epsilon)));


        actStroke=new BasicStroke(thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        color = new Color(50,50,50);
    }

    /**
     * Két pont között kirajzolja magát
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        damageCheck();
        Point start = v1.center;
        Point end = v2.center;

        center=new Point((this.v1.center.x+this.v2.center.x)/2,(this.v1.center.y+this.v2.center.y)/2);

        if(highlight) g.setColor(Color.GREEN);
        else {
            double colorRatio = 255 * (double) observedPipe.getFluidLevel() / (double) observedPipe.getFluidCapacity();
            int bluecolor=(int) colorRatio>=255?255:(int) colorRatio+50;
            color = new Color(50, 50, bluecolor);
            g.setColor(color);
        }

        g2.setStroke(actStroke);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.draw(new Line2D.Float(start, end));
    }

    /**
     * Kirajzolja egy pumpának a bementi és kimeneti csövének a nyilát
     *
     * @param g Graphics
     * @param OutPut kimeneti nyíl vagy bemenit nyíl kell
     * @param v adott pump
     */
    public void drawArrow(Graphics g,boolean OutPut, Vertex v) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.lightGray);
        g2.setStroke(new BasicStroke(thickness / 1.5f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER));

        Point firstPump;
        Point secondPump;
        if(OutPut){ //ha a nyíl outputot jelez
            firstPump=v.center; //output
            secondPump=(v.equals(v1)) ? v2.center : v1.center;//input
        }else { //ha a nyíl inputot jelez
            firstPump=(v.equals(v1)) ? v2.center : v1.center; //input
            secondPump=v.center; //output
        }

        double l = Math.sqrt(Math.pow((secondPump.getX() - firstPump.getX()), 2) + Math.pow((secondPump.getY() - firstPump.getY()), 2));//  line length
        double d =(OutPut) ? (l / 1.2) : (l / 4); //ha input, közel kell legyen, ha output távolabb // arrowhead distance from end of line. you can use your own value.

        double newX = ((secondPump.getX() + (((firstPump.getX() - secondPump.getX()) / (l) * d)))); // new x of arrowhead position on the line with d distance from end of the line.
        double newY = ((secondPump.getY() + (((firstPump.getY() - secondPump.getY()) / (l) * d)))); // new y of arrowhead position on the line with d distance from end of the line.

        double dx = secondPump.getX() - firstPump.getX();
        double dy = secondPump.getY() - firstPump.getY();
        double angle = (Math.atan2(dy, dx)); //get angle (Radians) between ours line and x vectors line. (counterclockwise)
        angle = (-1) * Math.toDegrees(angle);// cconvert to degree and reverse it to round clock for better understand what we need to do.
        if (angle < 0) {
            angle = 360 + angle; // convert negative degrees to posative degree
        }
        angle = (-1) * angle; // convert to counterclockwise mode
        angle = Math.toRadians(angle);//  convert Degree to Radians
        AffineTransform at = new AffineTransform();
        at.translate(newX, newY);// transport cursor to draw arrowhead position.
        at.rotate(angle);
        g2.transform(at);

        Polygon arrowHead = new Polygon();
        arrowHead.addPoint(5, 0);
        arrowHead.addPoint(-5, 5);
        arrowHead.addPoint(-2, -0);
        arrowHead.addPoint(-5, -5);
        g2.fill(arrowHead);
        g2.drawPolygon(arrowHead);
    }

    /**
     * Átrajzolja szagatott vonallá (hibássá) a csövet, ha lyukas
     */
    private void damageCheck() {
        if(observedPipe.isDamaged()) actStroke=new BasicStroke(thickness / 1.5f, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_MITER, 10.0f, new float[]{16.0f, 20.0f}, 0.0f); //szaggatott vonal
        else actStroke=new BasicStroke(thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
    }

    /**
     * Grafikusan megjeleníti ha egy cső fel lett véve
     * beállítja a megfelelő végét felvettre
     * @param actVertex a mező, amiről megvhívták
     */
    public void pickedUpPipe(Vertex actVertex){
        thickness = 4;
        //mindkét vége fel lett véve a csőnek
        if(observedPipe.getNeighbours().isEmpty()){
            //csőnek az 1 szomszédja
            if(v1PickedUp) v2PickedUp = true;
            else v1PickedUp = true;
            return;
        }
        Vertex pipeEnd = GameFieldPanel.getInstance().getVertices().get(observedPipe.getNeighbours().get(0));
        //ha a szomszédjai között van az aktuális elem szabadvégű volt a cső
        if(pipeEnd.equals(actVertex)){
            //a másik végét veszi fel a játékos
            if(actVertex.center.equals(v1.center)) v2PickedUp = true;
            if(actVertex.center.equals(v2.center)) v1PickedUp = true;
        } else{ //ha nincs a szomszédjai között, be volt kötve a cső
            //az aktuális végét veszi fel a játékos
            if(actVertex.center.equals(v1.center)) v1PickedUp = true;
            if(actVertex.center.equals(v2.center)) v2PickedUp = true;
        }
    }

    /**
     * Grafikusan megjeleníti ha egy cső le lett rakva
     * beállítja a megfelelő végét lerakottra
     * @param actVertex a mező, amiről megvhívták
     */
    public void placedDownPipe(Vertex actVertex){
        if(v1PickedUp){
            v1 = actVertex;
            v1PickedUp = false;
            thickness = 10;
        }
        else if(v2PickedUp) {
            v2 = actVertex;
            v2PickedUp = false;
            thickness = 10;
        }
        if(v1PickedUp || v2PickedUp) thickness = 4;
    }
}
