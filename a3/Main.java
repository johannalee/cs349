/**
 * CS349 Winter 2014
 * Assignment 3 Demo Code
 * Jeff Avery & Michael Terry
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main{
  static private Model model;
  static private View view;
  static private TitleView title;

  /*
   * Main entry point for the application
   */
  public static void main(String[] args) {
    // instantiate your model and views
    // add any new views here
    model = new Model();
    title = new TitleView(model);
    view = new View(model);

    // customize the title and any other top-level settings
    JFrame frame = new JFrame("FRUIT NINJA");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.setLayout(new BorderLayout());
    frame.add(title, BorderLayout.NORTH);
    frame.add(view, BorderLayout.CENTER);
    frame.pack();
    frame.setVisible(true);

    ActionListener repainter = new ActionListener(){
      public void actionPerformed(ActionEvent e){
        if(Math.random() < 0.05 && model.isObservable()){
          model.renderFruits();
        }
        if(!model.isOver()){
         model.notifyObservers();
        }
      }
    };

    Timer timer = new Timer(100, repainter);
    timer.start();
  }
}
