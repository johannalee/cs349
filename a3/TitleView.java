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
  private JLabel title, score, timer,  lives;
  private JButton button;
  private String start = "START";
  private String pause = "PAUSE";
  private String resume = "RESUME";
  private int playerlives = 5, highscore = 0;
  private long elasped = 0, remember=0;

  // Constructor requires model reference
  TitleView (Model model) {
    // register with model so that we get updates
    this.model = model;
    this.model.addObserver(this);

    // draw something
    setBorder(BorderFactory.createLineBorder(Color.black));
    setBackground(Color.PINK);
    // You may want a better name for this game!
    title = new JLabel("  Fruit Ninja");
    score = new JLabel();
    timer = new JLabel();
    lives = new JLabel();

    // create a button and add a listener for events
    button = new JButton(start);
    button.setSize(10, 20);
    button.addActionListener(this);

    // use border layout so that we can position labels on the left and right
    // this.setLayout(new BorderLayout());
    this.setLayout(new GridLayout(1, 4));
    this.add(title);//, FlowLayout.WEST);
    this.add(score);//, FlowLayout.EAST);
    this.add(lives);//, FlowLayout.WEST);
    this.add(timer);//, FlowLayout.WEST);
    this.add(button);//, FlowLayout.CENTER);

  }

  public void resetView(){
    title.setText("GAME OVER");
  }

  @Override
  public void actionPerformed(ActionEvent e){
    if(button.getText() == start){
      model.setIsOver(false);
      button.setText(pause);
      model.setObserve(true);
      model.setStartTime();

    }else if(button.getText() == pause){
      remember = elasped;
      button.setText(resume);
      model.setObserve(false);

    }else if(button.getText() == resume){
      model.setStartTime();
      button.setText(pause);
      model.setObserve(true);
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
    highscore = model.getSplitFruit();
    playerlives = model.getPlayerLives();
    elasped = model.calculateElasped();
    if(remember > 0){
      elasped += remember;
    }

    if(model.isOver()){
      highscore = 0;
      playerlives = 5;
      remember = 0;
      elasped = 0;
      button.setText(start);
      model.resetModel();
    }
    paint(getGraphics());
  }

  // Paint method
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    score.setText("High Score:\t" + highscore + "\t");
    lives.setText("Lives:\t" + playerlives + "\t");
    timer.setText("Time:\t" +  elasped + "\t");
    // }
  }
}
