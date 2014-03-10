/**
 * CS349 Winter 2014
 * Assignment 3 Demo Code
 * Jeff Avery & Michael Terry
 */
import java.util.ArrayList;
import java.util.Vector;
import java.awt.geom.*;
import java.awt.*;
/*
 * Class the contains a list of fruit to display.
 * Follows MVC pattern, with methods to add observers,
 * and notify them when the fruit list changes.
 */
public class Model {
  // Observer list
  private Vector<ModelListener> views = new Vector();

  // Fruit that we want to display
  private ArrayList<Fruit> shapes = new ArrayList();
  private Boolean permit2observe = false;
  private int score = 0;
  private int missed = 0;
  private double windowWidth = 0, windowHeight = 0;

  // Constructor
  Model() {
    shapes.clear();
  }

  public void setObserve(Boolean permit2observe){
    this.permit2observe = permit2observe;
  }

  public Boolean isObservable(){
    return this.permit2observe;
  }

  public int returnScore(){
    return score/2;
  }
  public int returnMissed(){
    return missed;
  }
  public void yayScore(){
    score++;
  }
  public void missedIt(){
    missed++;
  }
  public void setWindowSize(double width, double height){
    this.windowWidth = width;
    this.windowHeight = height;
  }

  // MVC methods
  // These likely don't need to change, they're just an implementation of the
  // basic MVC methods to bind view and model together.
  public void addObserver(ModelListener view) {
    views.add(view);
  }

  public void notifyObservers() {
    if(this.isObservable()){
      for (ModelListener v : views) {
        v.update();
      }
      for(Fruit s : this.shapes){
        s.deccelerate();
        if(s.isLeftToRight()){
          s.translate(s.getFVX(), s.getFVY());
        }else{
          s.translate(-s.getFVX(), s.getFVY());
        }
      }
    }
  }

  public void renderFruits(){
    Fruit f = null;

    //0 to 1
    double random = Math.random();

    if(random < 0.2){
      //blueberry
      f = new Fruit(new Area(new Ellipse2D.Double(50, (this.windowHeight+50), 30, 30)));
      f.setFillColor(Color.BLUE);
    }else if(random >= 0.2 && random < 0.4){
      //orange
      f = new Fruit(new Area(new Ellipse2D.Double(50, (this.windowHeight+50), 70, 70)));
      f.setFillColor(Color.ORANGE);
    }else if(random >= 0.4 && random < 0.6){
      //red delicious apple
      f = new Fruit(new Area(new Ellipse2D.Double(50, (this.windowHeight+50), 45, 45)));
      f.setFillColor(Color.RED);
    }else if(random >= 0.6 && random < 0.8){
      //banana
      f = new Fruit(new Area(new Ellipse2D.Double(50, (this.windowHeight+50), 100, 20)));
      f.setFillColor(Color.YELLOW);
    }else if(random >= 0.8 && random < 1){
      //granny smith apple
      f = new Fruit(new Area(new Ellipse2D.Double(50, (this.windowHeight+50), 50, 50)));
      f.setFillColor(Color.GREEN);
    }
    if(Math.random() > 0.5){
      f.translate((this.windowWidth-150), 0);
      f.setFruitDirection(false);
    }
    f.setFVX(Math.random()*20);
    f.setFVY(-(Math.random()*20+45));
    f.translate(f.getFVX(), f.getFVY());
    this.add(f);    
  }
  // Model methods
  // You may need to add more methods here, depending on required functionality.
  // For instance, this sample makes to effort to discard fruit from the list.
  
  public void add(Fruit s) {
    shapes.add(s);
    notifyObservers();
  }

  public void remove(Fruit s) {
    shapes.remove(s);
    notifyObservers();
  }

  public ArrayList<Fruit> getShapes() {
      return (ArrayList<Fruit>)shapes.clone();
  }
}
