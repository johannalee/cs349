/**
 * CS349 Winter 2014
 * Assignment 3 Demo Code
 * Jeff Avery & Michael Terry
 */
import java.util.ArrayList;
import java.util.Vector;
import java.awt.geom.*;
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
        s.translate(s.getFVX(), s.getFVY());
      }
    }
  }

  public void renderFruits(){
    Fruit f = null;

    //0 to 1
    double random = Math.random() * 5;

    switch((int)random){
      case 1:
      //   f = new Fruit(new Area(new Ellipse2D.Double(0, 500, 100, 50)));
      //   f.setFVX(10);
      //   f.setFVY(-60);
      //   f.setFillColor(Color.YELLOW);
      //   f.translate(f.getFVX(), f.getFVY());
      // break;

      case 2:
      //   f = new Fruit(new Area(new Ellipse2D.Double(0, 500, 100, 50)));
      //   f.setFVX(10);
      //   f.setFVY(-60);
      //   f.setFillColor(Color.RED);
      //   f.translate(f.getFVX(), f.getFVY());
      // break;

      case 3:
      //   f = new Fruit(new Area(new Ellipse2D.Double(0, 500, 100, 50)));
      //   f.setFVX(10);
      //   f.setFVY(-60);
      //   f.setFillColor(Color.RED);
      //   f.translate(f.getFVX(), f.getFVY());
      // break;

      case 4:
      //   f = new Fruit(new Area(new Ellipse2D.Double(0, 500, 100, 50)));
      //   f.setFVX(10);
      //   f.setFVY(-60);
      //   f.translate(f.getFVX(), f.getFVY());
      // break;

      case 0:
        f = new Fruit(new Area(new Ellipse2D.Double(0, 500, 100, 50)));
        f.setFVX(10);
        f.setFVY(-60);
        f.translate(f.getFVX(), f.getFVY());
      break;
    }
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
