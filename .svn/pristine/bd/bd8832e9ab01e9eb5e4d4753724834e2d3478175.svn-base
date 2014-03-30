/**
 * CS349 Winter 2014
 * Assignment 3 Demo Code
 * Jeff Avery & Michael Terry
 */
import java.awt.*;
import java.awt.geom.*;
import java.awt.geom.Line2D.Double;
import java.lang.Math;

/**
 * Class that represents a Fruit. Can be split into two separate fruits.
 */
public class Fruit implements FruitInterface {
    private Area            fruitShape   = null;
    private Color           fillColor    = Color.GREEN;
    private Color           outlineColor = Color.BLACK;
    private AffineTransform transform    = new AffineTransform();
    private double          outlineWidth = 5;   //default

    // private Boolean hasGotCut = false;
    private Boolean aPiece = false;
    private Boolean leftToRight = true;
    private double fvx = 0;
    private double fvy = 0;
    private double startY;

    /**
     * A fruit is represented using any arbitrary geometric shape.
     */
    Fruit (Area fruitShape) {
        this.fruitShape = (Area)fruitShape.clone();
    }
    /**
     * Set cloned bool if fruit is original
     */
    public void setFruitDirection(Boolean leftToRight) {
        this.leftToRight = leftToRight;
    }
    /**
     * Set cloned bool if fruit is original
     */
    public Boolean isLeftToRight() {
        return this.leftToRight;
    }
    /**
     * Set cloned bool if fruit is original
     */
    public void setIsPiece(Boolean aPiece) {
        this.aPiece = aPiece;
    }
    /**
     * Returns true if fruit is a piece cut from it original fruit
     */
    public Boolean isPiece() {
        return this.aPiece;
    }
    /**
     * Returns true if fruit is cut
     */
    public void setY(double y) {
        this.startY = y;
    }
    /**
     * set fruit cut state
     */
    public double getY() {
        return this.startY;
    }
    /**
     * Set the x movement
     */
    public void setFVX(double velocity){
        this.fvx = velocity;
    }
    /**
     * set the y movement
     */
    public void setFVY(double velocity){
        this.fvy = velocity;
    }
    /**
     * set velocity for fruit falling
     */
    public void deccelerate(){
        this.fvy += 5;
    }
    /**
     * get the x movement
     */
    public double getFVX(){
        return this.fvx;
    }
    /**
     * get the y movement
     */
    public double getFVY(){
        return this.fvy;
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
        // if(this.isBlade())  return;
        // TODO BEGIN CS349
        // TODO END CS349
        Graphics2D tg = g2;
        tg.setStroke(new BasicStroke((float)this.getOutlineWidth()));
        tg.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        tg.setPaint(outlineColor);
        tg.draw(this.getTransformedShape());
        tg.setPaint(fillColor);
        tg.fill(this.getTransformedShape());
    }

    /**
     * Tests whether the line represented by the two points intersects
     * this Fruit.
     */
    public boolean intersects(Point2D p1, Point2D p2) {
        // TODO BEGIN CS349
        // TODO END CS349
        int[] x = { (int) p1.getX(), (int) p2.getX(), (int) p2.getX(), (int) p1.getX()};
        int[] y = { (int) p1.getY()-1, (int) p2.getY()-1, (int) p2.getY()+1, (int) p1.getY()+1};
        Polygon pLine = new Polygon(x, y, x.length);
        
        AffineTransform at = new AffineTransform();
        at.rotate(-getThetaWrtXaxis(p1,p2), p1.getX(), p1.getY());
        
        boolean intersect = this.getTransformedShape().createTransformedArea(at).intersects(new Area(pLine).createTransformedArea(at).getBounds2D());
        if(intersect) {
            return true;
        } 
        
        return false;
    }

    public double getThetaWrtXaxis(Point2D p1, Point2D p2){
        double angle1 = Math.atan2((p1.getY() - p2.getY()), (p1.getX() - p2.getX()));
        double angle2 = Math.atan2(0, -1);

        return angle1 - angle2;
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

        if((this.contains(p1) && !this.contains(p2)) || (this.contains(p2) && !this.contains(p1))){
            return new Fruit[0];
        }

        // TODO BEGIN CS349
        // Rotate shape to align slice with x-axis
        int[] x = { (int) p1.getX(), (int) p2.getX(), (int) p2.getX(), (int) p1.getX()};
        int[] y = { (int) p1.getY()-1, (int) p2.getY()-1, (int) p2.getY()+1, (int) p1.getY()+1};
        Polygon pLine = new Polygon(x, y, x.length);
        
        AffineTransform at = new AffineTransform();
        at.rotate(-getThetaWrtXaxis(p1,p2), p1.getX(), p1.getY());

        Area rotatedPolyLine = new Area(new Polygon(x, y, x.length)).createTransformedArea(at);
        Rectangle rotatedPolyLineBounds = rotatedPolyLine.getBounds();

        Area rotatedFruit = this.getTransformedShape().createTransformedArea(at);

        Rectangle rotatedFruitBounds = rotatedFruit.getBounds();

        int bottomRecLeftBottomY = (int)(rotatedFruitBounds.getY() + rotatedFruitBounds.getHeight());
        int bottomRecHeight = bottomRecLeftBottomY - (int)(rotatedPolyLineBounds.getY());
        int topRecHeight = (int)(rotatedPolyLineBounds.getY() - rotatedFruitBounds.getY());

        Rectangle bottomRec = new Rectangle((int)rotatedFruitBounds.getX(), (int)rotatedPolyLineBounds.getY(), (int)rotatedFruitBounds.width, bottomRecHeight);
        Rectangle topRec = new Rectangle((int)rotatedFruitBounds.getX(), (int)rotatedFruitBounds.getY(), (int)rotatedFruitBounds.width, topRecHeight);

        //check if fruit pieces are not too small(less than or equal to 1/5 of original) or too big(less than or equal to 4/5 of original)
        if(bottomRec.getHeight() < rotatedFruitBounds.getHeight()*0.2 || bottomRec.getHeight() >= rotatedFruitBounds.getHeight()*0.8)
            return new Fruit[0];

        at.rotate(getThetaWrtXaxis(p1,p2), p1.getX(), p1.getY());
        double dx = this.getTransformedShape().getBounds().getX() - rotatedFruitBounds.getX();
        double dy = this.getTransformedShape().getBounds().getY() - rotatedFruitBounds.getY();
        bottomArea = rotatedFruit.createTransformedArea(at);
        bottomArea.intersect(new Area(bottomRec));
        topArea = rotatedFruit.createTransformedArea(at);
        topArea.intersect(new Area(topRec));
        at.translate(dx/2, dy/2);
        topArea = topArea.createTransformedArea(at);
        at.rotate(getThetaWrtXaxis(p1,p2)/1.667, p1.getX(), p1.getY());
        bottomArea = bottomArea.createTransformedArea(at);
        topArea.transform(this.transform.createInverse());
        bottomArea.transform(this.transform.createInverse());

        // Bisect shape above/below x-axis (look at intersection methods!)
        // TODO END CS349
        if (topArea != null && bottomArea != null)
            return new Fruit[] { new Fruit(topArea.createTransformedArea(at)), new Fruit(bottomArea.createTransformedArea(at)) };
        return new Fruit[0];
     }
}
