/**
 * CS349 Winter 2014
 * Assignment 3 Demo Code
 * Jeff Avery & Michael Terry
 */
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.geom.Line2D.Double;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/*
 * View of the main play area.
 * Displays pieces of fruit, and allows players to slice them.
 */
public class View extends JPanel implements ModelListener {
    private Model model;
    private final MouseDrag drag;
    private Timer timer;
    private int fps = 100;
    private int windowSize  = getWidth();
    private double radians_per_second = 0.8;
    private double radius = 0.5*windowSize;
    private int x_center = windowSize/2;
    private int y_center = windowSize/2;
    private double cur_angle = 0.0;

    //need to quit somehow
    private Boolean quit = false;

    // Constructor
    View (Model m) {
        model = m;
        model.addObserver(this);

        setBackground(Color.WHITE);

        // add a couple of fruit instances for test purposes
        // in a real game, you want to spawn fruit in random locations from the bottom of the screen
        // we use ellipse2D for simple shapes, you might consider something more complex
        Fruit f = new Fruit(new Area(new Ellipse2D.Double(0, 50, 50, 50)));
        f.translate(100, 100);
        model.add(f);

        Fruit f2 = new Fruit(new Area(new Rectangle2D.Double(0, 50, 50, 50)));
        f2.translate(200, 200);
        model.add(f2);

        //update timer for animations
        animateFruits();

        // drag represents the last drag performed, which we will need to calculate the angle of the slice
        drag = new MouseDrag();
        // add mouse listener
        addMouseListener(mouseListener);
    }
    //animate fruits using timer
    public void animateFruits() {
        ActionListener repainter = new ActionListener(){
            public void actionPerformed(ActionEvent e){
                cur_angle += (radians_per_second/((double)fps));
            }
        };
        timer = new Timer(1000/fps, repainter);
        if(model.isObservable()){
            timer.start();
        }
    }

    // Update fired from model
    @Override
    public void update() {
        this.repaint();
    }

    // Panel size
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(500,400);
    }

    // Paint this panel
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // draw all pieces of fruit
        // note that fruit is responsible for figuring out where and how to draw itself
        for (Fruit s : model.getShapes()) {
            g2.translate(x_center, y_center);
            g2.rotate(cur_angle);
            g2.translate(0,radius);
            g2.rotate(-cur_angle);

            g2.setColor(Color.RED);
            g2.fill(s.getTransformedShape());

            g2.setColor(Color.BLACK);
            g2.draw(s.getTransformedShape());
            s.draw(g2);
        }
    }

//    public double getTheta(Point2D p1, Point2D p2) {
//      double theta = (p2.getY() - p1.getY()) / (p2.getX() - p1.getX());
//      if ((p2.getX() - p1.getX()) >= 0) return -theta;
//      else return (Math.PI - theta);
//    }
        public double getTheta(Point2D p1, Point2D p2) {
            Line2D line1 = new Line2D.Double(p1, p2);
            Line2D line2 = new Line2D.Double(p1, new Point2D.Double(p1.getX()+1, p1.getY()));
            double angle1 = Math.atan2(line1.getY1() - line1.getY2(),
                                       line1.getX1() - line1.getX2());
            double angle2 = Math.atan2(line2.getY1() - line2.getY2(),
                                       line2.getX1() - line2.getX2());
            return angle1-angle2;
        }
    
    // Mouse handler
    // This does most of the work: capturing mouse movement, and determining if we intersect a shape
    // Fruit is responsible for determining if it's been sliced and drawing itself, but we still
    // need to figure out what fruit we've intersected.
    private MouseAdapter mouseListener = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            super.mousePressed(e);
            drag.start(e.getPoint());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            super.mouseReleased(e);
            
            //if not playing then no need to update view
            if(!model.isObservable()) return;
            
            drag.stop(e.getPoint());
            // you could do something like this to draw a line for testing
            // not a perfect implementation, but works for 99% of the angles drawn
            
            int[] x = { (int) drag.getStart().getX(), (int) drag.getEnd().getX(), (int) drag.getEnd().getX(), (int) drag.getStart().getX()};
            int[] y = { (int) drag.getStart().getY()-1, (int) drag.getEnd().getY()-1, (int) drag.getEnd().getY()+1, (int) drag.getStart().getY()+1};
          
                Polygon pLine = new Polygon(x, y, x.length);
                model.add(new Fruit(new Area(pLine)));
             

            // find intersected shapes
            int offset = 0; // Used to offset new fruits
            for (Fruit s : model.getShapes()) {
                if (s.intersects(drag.getStart(), drag.getEnd())){
                    s.setFillColor(Color.RED);
                    try {
                        Fruit[] newFruits = s.split(drag.getStart(), drag.getEnd());
                        
                        // add offset so we can see them split - this is used for demo purposes only!
                        // you should change so that new pieces appear close to the same position as the original piece
                        for (Fruit f : newFruits) {
                            // f.setFillColor(Color.BLACK);
                            f.translate(offset, offset);
                            model.add(f);
                            offset += 20;
                        }
                    } catch (Exception ex) {
                        System.err.println("Caught error: " + ex.getMessage());
                    }
                } else {
                    s.setFillColor(Color.BLUE);
                }
            }
        }
    };

    /*
     * Track starting and ending positions for the drag operation
     * Needed to calculate angle of the slice
     */
    private class MouseDrag {
        private Point2D start;
        private Point2D end;

        MouseDrag() { }

        protected void start(Point2D start) { this.start = start; }
        protected void stop(Point2D end) { this.end = end; }

        protected Point2D getStart() { return start; }
        protected Point2D getEnd() { return end; }

    }
}
