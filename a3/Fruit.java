/**
 * CS349 Winter 2014
 * Assignment 3 Demo Code
 * Jeff Avery & Michael Terry
 */
import java.awt.*;
import java.awt.geom.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * Class that represents a Fruit. Can be split into two separate fruits.
 */
public class Fruit implements FruitInterface {
    private Area            fruitShape   = null;
    private Color           fillColor    = Color.RED;
    private Color           outlineColor = Color.BLACK;
    private AffineTransform transform    = new AffineTransform();
    private double          outlineWidth = 5;
    //for drawing lines created by user drag
    private ArrayList<Point2D> points;
    private boolean hasChanged = false;
    private int[] x_pts, y_pts;

    /**
     * A fruit is represented using any arbitrary geometric shape.
     */
    Fruit (Area fruitShape) {
        this.fruitShape = (Area)fruitShape.clone();
    }
    /**
     * get all the x points
     */
    public int[] getXpoints() {
        return this.x_pts;
    } 
    /**
     * get all the y points
     */
    public int[] getYpoints() {
        return this.y_pts;
    } 
    /**
     * get all the points
     */
    public int getPointsSize() {
        return this.points.size();
    } 
    /**
     * add a point to end of shape
     */
    // public void addPoint(double x, double y) {
    //     if(points == null){
    //         points = new ArrayList<Point2D>();
    //     }
    //     points.add(new Point2D.Double(x,y));
    //     hasChanged = true;
    // }
    public void addPoint(int[] x, int[] y) {
        x_pts = x;
        y_pts = y;
        hasChanged = true;
    }
    /**
     * The color used to paint the interior of the Fruit.
     */
    public Color getFillColor() {
        return fillColor;
    }
    /**
     * The color used to paint the interior of the Fruit.
     */
    public void setFillColor(Color color) {
        fillColor = color;
    }
    /**
     * The color used to paint the outline of the Fruit.
     */
    public Color getOutlineColor() {
        return outlineColor;
    }
    /**
     * The color used to paint the outline of the Fruit.
     */
    public void setOutlineColor(Color color) {
        outlineColor = color;
    }
    
    /**
     * Gets the width of the outline stroke used when painting.
     */
    public double getOutlineWidth() {
        return outlineWidth;
    }

    /**
     * Sets the width of the outline stroke used when painting.
     */
    public void setOutlineWidth(double newWidth) {
        outlineWidth = newWidth;
    }

    /**
     * Concatenates a rotation transform to the Fruit's affine transform
     */
    public void rotate(double theta) {
        transform.rotate(theta);
    }

    /**
     * Concatenates a scale transform to the Fruit's affine transform
     */
    public void scale(double x, double y) {
        transform.scale(x, y);
    }

    /**
     * Concatenates a translation transform to the Fruit's affine transform
     */
    public void translate(double tx, double ty) {
        transform.translate(tx, ty);
    }

    /**
     * Returns the Fruit's affine transform that is used when painting
     */
    public AffineTransform getTransform() {
        return (AffineTransform)transform.clone();
    }

    /**
     * Creates a transformed version of the fruit. Used for painting
     * and intersection testing.
     */
    public Area getTransformedShape() {
        return fruitShape.createTransformedArea(transform);
    }

    /**
     * Paints the Fruit to the screen using its current affine
     * transform and paint settings (fill, outline)
     */
    public void draw(Graphics2D g2) {
        // TODO BEGIN CS349
        if(hasChanged){
            g2.setColor(Color.DARK_GRAY);
            g2.setStroke(new BasicStroke(10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.drawPolyline(x_pts, y_pts, x_pts.length);
            hasChanged = false;
            g2.setColor(this.getFillColor());
            g2.fill(this.fruitShape);
        }
        // TODO END CS349
    }

    /**
     * Tests whether the line represented by the two points intersects
     * this Fruit.
     */
    public boolean intersects(Point2D p1, Point2D p2) {
        // TODO BEGIN CS349
        // TODO END CS349
        return false;
    }

    /**
     * Returns whether the given point is within the Fruit's shape.
     */
    public boolean contains(Point2D p1) {
        return this.getTransformedShape().contains(p1);
    }

    /**
     * This method assumes that the line represented by the two points
     * intersects the fruit. If not, unpredictable results will occur.
     * Returns two new Fruits, split by the line represented by the
     * two points given.
     */
    public Fruit[] split(Point2D p1, Point2D p2) throws NoninvertibleTransformException {
        Area topArea = null;
        Area bottomArea = null;

        // TODO BEGIN CS349
        // Rotate shape to align slice with x-axis
        // Bisect shape above/below x-axis (look at intersection methods!)
        // TODO END CS349
        if (topArea != null && bottomArea != null)
            return new Fruit[] { new Fruit(topArea), new Fruit(bottomArea) };
        return new Fruit[0];
     }
}
