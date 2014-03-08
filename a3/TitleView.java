/**
 * CS349 Winter 2014
 * Assignment 3 Demo Code
 * Jeff Avery & Michael Terry
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/*
 * View to display the Title, and Score
 * Score currently just increments every time we get an update
 * from the model (i.e. a new fruit is added).
 */
public class TitleView extends JPanel implements ModelListener, ActionListener {
  private Model model;
  private JLabel title, score;
  private JButton startButton;
  private int count = 0;
  private String start = "START";
  private String stop = "STOP";

  // Constructor requires model reference
  TitleView (Model model) {
    // register with model so that we get updates
    this.model = model;
    this.model.addObserver(this);

    // draw something
    setBorder(BorderFactory.createLineBorder(Color.black));
    setBackground(Color.YELLOW);
    // You may want a better name for this game!
    title = new JLabel("  Fruit Ninja");
    score = new JLabel();

    // create a button and add a listener for events
    startButton = new JButton(start);
    startButton.setSize(10, 20);
    startButton.addActionListener(this);

    // use border layout so that we can position labels on the left and right
    this.setLayout(new BorderLayout());
    this.add(title, BorderLayout.WEST);
    this.add(score, BorderLayout.EAST);

    this.setLayout(new FlowLayout());
    this.add(startButton, FlowLayout.CENTER);
  }

  @Override
  public void actionPerformed(ActionEvent e){
        // System.out.println("HERHERER UPDATE TITLE VIEW");

    if(startButton.getText() == start){
      startButton.setText(stop);
      model.setObserve(true);
    }else{
      startButton.setText(start);
      model.setObserve(false);

    }
  }

  // Panel size
  @Override
  public Dimension getPreferredSize() {
    return new Dimension(500,40);
  }

  // Update from model
  // This is ONLY really useful for testing that the view notifications work
  // You likely want something more meaningful here.
  @Override
  public void update() {
    System.out.println("HERHERER UPDATE TITLE VIEW");
    count++;
    paint(getGraphics());
  }

  // Paint method
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    score.setText("Count: " + count + "  ");
  }
}
