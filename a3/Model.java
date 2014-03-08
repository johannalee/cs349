/**
 * CS349 Winter 2014
 * Assignment 3 Demo Code
 * Jeff Avery & Michael Terry
 */
import java.util.ArrayList;
import java.util.Vector;
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
    }
  }

  // Model methods
  // You may need to add more methods here, depending on required functionality.
  // For instance, this sample makes to effort to discard fruit from the list.
  public void add(Fruit s) {
    shapes.add(s);
    notifyObservers();
  }

  public ArrayList<Fruit> getShapes() {
      return (ArrayList<Fruit>)shapes.clone();
  }
}
