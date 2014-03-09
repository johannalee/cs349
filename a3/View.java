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
    private double radius = windowSize*0.2;
    private int x_center = windowSize/2;
    private int y_center = 0;
    private double cur_angle = 0.0;

    //need to quit somehow
    private Boolean quit = false;

    // Constructor
    View (Model m) {
        model = m;
        model.addObserver(this);

        setBackground(Color.WHITE);

        // animateFruits();

        // add a couple of fruit instances for test purposes
        // in a real game, you want to spawn fruit in random locations from the bottom of the screen
        // we use ellipse2D for simple shapes, you might consider something more complex
        

        // Fruit f2 = new Fruit(new Area(new Rectangle2D.Double(0, 0, 50, 50)));
        // f2.translate(200, 200);
        // model.add(f2);

        // drag represents the last drag performed, which we will need to calculate the angle of the slice
        drag = new MouseDrag();
        
        // add mouse listener
        addMouseListener(mouseListener);
    }
    //animate fruits using timer
    // public void animateFruits() {
    //     ActionListener repainter = new ActionListener(){
    //         public void actionPerformed(ActionEvent e){
    //             if(y_center >= 0 && y_center < 300){
    //                 y_center += 5;
    //             }else{
    //                 y_center = 0;
    //             }
    //             // cur_angle += (radians_per_second/((double)fps));
    //             // radius = 90;
    //             // x_center =  250;
    //             // y_center = 300;
    //             repaint();
    //         }
    //     };
    //     timer = new Timer(1000/fps, repainter);
    //     timer.setInitialDelay(1000);
    //     timer.start();
    // }

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
            //check if its original fruit that has been split
            if(s.isBlade()){//when s is a blade
                s = null;
                model.remove(s);
            }else{//when s is a fruit

                if(s.isCut()){
                    // already cut fruit shouldn't be existing here but just checking in case
                    model.remove(s);

                }else if(s.isPiece()){    //pieces so they need to fall off instead of moving along the arc
                 // g2.translate(0, y_center);
                 // if(y_center == 0){
                 //    model.remove(s);
                 //    // model.score();
                 // }
                // g2.setColor(Color.RED);
                // g2.fill(s.getTransformedShape());

                // g2.setColor(Color.BLACK);
                // g2.draw(s.getTransformedShape());

                }else{  //fruits that haven't been split so they need to move along the arc

                // timer.setDelay(500);
                // g2.translate(x_center, y_center);
                // g2.rotate(cur_angle);
                // g2.translate(0, radius);
                // g2.rotate(cur_angle);
                // if(cur_angle > 10 && cur_angle < 15){
                // timer.setDelay(1000);
                // if(!s.isPiece() && !s.isBlade()){
                //         System.out.println(y_center);
                // }
                // g2.translate(x, y);
                    
                }

                s.draw(g2);
            }
        }
    }

   public double getTheta(Point2D p1, Point2D p2) {
     double theta = (p2.getY() - p1.getY()) / (p2.getX() - p1.getX());
     if ((p2.getX() - p1.getX()) >= 0) return -theta;
     else return (Math.PI - theta);
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
            Fruit line = new Fruit(new Area(pLine));
            line.setBlade(true);

            model.add(new Fruit(new Area(pLine)));
             

            // find intersected shapes
            int offset = 0; // Used to offset new fruits
            for (Fruit s : model.getShapes()) {
                if(!s.isBlade()){
                    if (s.intersects(drag.getStart(), drag.getEnd())){

                        s.setFillColor(Color.RED);
                        Fruit copy = new Fruit(s.getTransformedShape());
                        try {
                            // timer.stop();
                            Fruit[] newFruits = copy.split(drag.getStart(), drag.getEnd());
                            
                            // if s has got spilt up then remove it from the arraylist
                            if(newFruits.length > 0){
                                model.remove(s);
                            }
                            // timer.restart();

                            // add offset so we can see them split - this is used for demo purposes only!
                            // you should change so that new pieces appear close to the same position as the original piece
                            for (Fruit f : newFruits) {
                                f.setIsPiece(true);
                                // f.translate(offset, offset);
                                model.add(f);
                                offset += 20;
                            }
                        } catch (Exception ex) {
                            System.err.println("Caught error: " + ex.getMessage());
                        }
                    } else {
                        s.setFillColor(Color.BLUE);

                        //how could you miss it T^T
                        model.missedIt();

                        System.out.println("MISSED: "+model.returnMissed());
                    }
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
