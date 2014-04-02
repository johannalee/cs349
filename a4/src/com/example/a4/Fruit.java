/**
 * CS349 Winter 2014
 * Assignment 4 Demo Code
 * Jeff Avery
 */
package com.example.a4;
import android.annotation.SuppressLint;
import android.graphics.*;
import android.util.Log;

import java.lang.Math;

/**
 * Class that represents a Fruit. Can be split into two separate fruits.
 */
public class Fruit {
    private Path path = new Path();
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    
    private Matrix transform = new Matrix();
    private Region clip = new Region(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
    
    private boolean leftToRight = true;
    private boolean aPiece = false;
    private float fvx = 0;
    private float fvy = 0;
    /**
     * A fruit is represented as Path, typically populated 
     * by a series of points 
     */
    Fruit(float[] points) {
        init();
        this.path.reset();
        this.path.moveTo(points[0], points[1]);
        for (int i = 2; i < points.length; i += 2) {
            this.path.lineTo(points[i], points[i + 1]);
        }
        this.path.moveTo(points[0], points[1]);
    }

    Fruit(Region region) {
        init();
        this.path = region.getBoundaryPath();
    }

    Fruit(Path path) {
        init();
        this.path = path;
    }

    public Boolean hasCompletedAnimation(){
    	Region r = new Region();
    	r.setPath(this.getTransformedPath(), clip);
        return r.getBounds().top >  MainActivity.displaySize.y;
    }

    public void setIsPiece(Boolean aPiece) {
        this.aPiece = aPiece;
    }
    /**
     * Returns true if fruit is a piece cut from it original fruit
     */
    public Boolean isPiece() {
        return this.aPiece;
    }
    private void init() {
        this.paint.setColor(Color.BLUE);
        this.paint.setStrokeWidth(5);
    }

    public void setFruitDirection(boolean leftToRight) {
        this.leftToRight = leftToRight;
    }

    public boolean isLeftToRightit() {
        return this.leftToRight;
    }
    
    public void setFVX(float velocity) {
        this.fvx = velocity;
    }

    public float getFVX() {
        return this.fvx;
    }

    public void setFVY(float velocity) {
        this.fvy = velocity;
    }

    public float getFVY() {
        return this.fvy;
    }

    public void deccelerate() {
        this.fvx += 5;
    }

    /**
     * The color used to paint the interior of the Fruit.
     */
    public int getFillColor() { return paint.getColor(); }
    public void setFillColor(int color) { paint.setColor(color); }

    /**
     * The width of the outline stroke used when painting.
     */
    public double getOutlineWidth() { return paint.getStrokeWidth(); }
    public void setOutlineWidth(float newWidth) { paint.setStrokeWidth(newWidth); }

    /**
     * Concatenates transforms to the Fruit's affine transform
     */
    public void rotate(float theta) { transform.postRotate(theta); }
    public void scale(float x, float y) { transform.postScale(x, y); }
    public void translate(float tx, float ty) { transform.postTranslate(tx, ty); }

    /**
     * Returns the Fruit's affine transform that is used when painting
     */
    public Matrix getTransform() { return transform; }

    /**
     * The path used to describe the fruit shape.
     */
    public Path getTransformedPath() {
        Path originalPath = new Path(path);
        Path transformedPath = new Path();
        originalPath.transform(transform, transformedPath);
        return transformedPath;
    }

    /**
     * Paints the Fruit to the screen using its current affine
     * transform and paint settings (fill, outline)
     */
    public void draw(Canvas canvas) {
        // TODO BEGIN CS349
        // tell the shape to draw itself using the matrix and paint parameters
    	canvas.drawPath(getTransformedPath(), this.paint);
        // TODO END CS349
    }

    /**
     * Tests whether the line represented by the two points intersects
     * this Fruit.
     */
    public boolean intersects(PointF p1, PointF p2) {
        // TODO BEGIN CS349
        // rotate and flatten points passed in 
    	Path cutline = new Path();
    	cutline.moveTo(p1.x, (p1.y));
    	cutline.lineTo(p2.x, (p2.y));
    	cutline.lineTo(p2.x+1, (p2.y+1));
    	cutline.lineTo(p1.x+1, (p1.y+1));
    	cutline.lineTo(p1.x, (p1.y));
    	cutline.close();

        Region cutlineRegion = new Region();
    	cutlineRegion.setPath(cutline, this.clip);
    	
    	Region newFruitRegion = new Region();
    	newFruitRegion.setPath(this.getTransformedPath(), this.clip);
    	
    	boolean valid = newFruitRegion.op(cutlineRegion, Region.Op.INTERSECT);
    	if(valid){
    		return true;
    	}
    	
        // TODO END CS349
        return false;
    }
    
    /**
     * Returns whether the given point is within the Fruit's shape.
     */
    public boolean contains(PointF p1) {
        Region region = new Region();
        boolean valid = region.setPath(getTransformedPath(), this.clip);
        return valid && region.contains((int) p1.x, (int) p1.y);
    }

    /**
     * This method assumes that the line represented by the two points
     * intersects the fruit. If not, unpredictable results will occur.
     * Returns two new Fruits, split by the line represented by the
     * two points given.
     */

	@SuppressLint("NewApi")
    public Fruit[] split(PointF p1, PointF p2) {
    	Path topPath = null;
    	Path bottomPath = null;
    	
    	//cutline should go through the fruit completely otherwise no split
    	if((this.contains(p1) && !this.contains(p2)) || (this.contains(p2) && !this.contains(p1))){
    		return new Fruit[0];
    	}
    	
    	// TODO BEGIN CS349
    	// calculate angle between points
    	float angle = Graphics2D.findAngle(p1, p2);
    	
        // rotate path and create region for comparison
    	
    	//create a path and initialize with the rotated original fruit 
//    	Path newFruitPath = new Path(this.getTransformedPath());
//    	newFruitPath.transform(newtransform);
    	this.rotate(-angle);
    	Log.d("ANGLE ", angle+"");
    	//make a region for the created path
    	Region newFruitRegion = new Region();
    	newFruitRegion.setPath(this.getTransformedPath(), clip);
    	
    	//crate a rectangle of the new path
    	Rect fruitRect = newFruitRegion.getBounds();
    	double fruitHeight = fruitRect.bottom - fruitRect.top;
    	double topPieceHeight = p1.y - fruitRect.top;
    	float y = p1.y;
    	if(topPieceHeight >= fruitHeight){
    		topPieceHeight = p2.y - fruitRect.top;
    		y = p2.y;
    	}
    	
    	Region topRegion = new Region();
    	topRegion.op(newFruitRegion, new Region(fruitRect.left, fruitRect.top, fruitRect.right, (int)(y)), Region.Op.INTERSECT);
    	
    	
    	Region bottomRegion = new Region();
    	bottomRegion.op(newFruitRegion, topRegion, Region.Op.DIFFERENCE);
    	
    	topPath = new Path(topRegion.getBoundaryPath());
    	bottomPath = new Path(bottomRegion.getBoundaryPath());
    	
    	this.rotate(angle);
    	Matrix newtransform = new Matrix();
    	newtransform.postRotate(angle);
    	topPath.transform(newtransform);
    	bottomPath.transform(newtransform);
        // TODO END CS349
    	boolean isCuttable = fruitHeight*0.2 < topPieceHeight & fruitHeight*0.8 > topPieceHeight;
        if (topPath != null && bottomPath != null && isCuttable) {
        	Log.i("Hi","HERE");
           return new Fruit[] { new Fruit(topPath), new Fruit(bottomPath) };
        }
    	Log.i("DIDnt","get there");
        return new Fruit[0];
    }
}
