/**
 * CS349 Winter 2014
 * Assignment 4 Demo Code
 * Jeff Avery & Michael Terry
 */
package com.example.a4;

import android.graphics.Color;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/*
 * Class the contains a list of fruit to display.
 * Follows MVC pattern, with methods to add observers,
 * and notify them when the fruit list changes.
 */
public class Model extends Observable {
    // List of fruit that we want to display
    private ArrayList<Fruit> shapes = new ArrayList<Fruit>();
    private ArrayList<Fruit> splitFruits = new ArrayList<Fruit>();
    final int winW = MainActivity.displaySize.x;
    final int winH = MainActivity.displaySize.y;
    private boolean permit2observe = false;
    private boolean gameOver = false;
    private boolean paused = false;
    
    // Constructor
    Model() {
        shapes.clear();
    }

    // Model methods
    // You may need to add more methods here, depending on required functionality.
    // For instance, this sample makes to effort to discard fruit from the list.
    public void add(Fruit s) {
        shapes.add(s);
        setChanged();
        notifyObservers();
    }

    public void remove(Fruit s) {
        shapes.remove(s);
    }
    
    public void setPaused(boolean s) {
        paused = s;
    }
    
    public boolean getPaused() {
    	return paused;
    }

    public ArrayList<Fruit> getShapes() {
        return (ArrayList<Fruit>) shapes.clone();
    }

    // MVC methods
    // Basic MVC methods to bind view and model together.
    public void addObserver(Observer observer) {
        super.addObserver(observer);
    }

    // a helper to make it easier to initialize all observers
    public void initObservers() {

    	if(!this.isOver()){
        	if(this.isObservable()){
                fruitAnimation();
                setChanged();
                notifyObservers();
        	}
    	}else{
    		resetModel();
    	}
    }
    
    public void fruitAnimation() {
        for(Fruit s : this.shapes){
        	if(s != null){
        		s.deccelerate();
        		if(s.isLeftToRightit()){
        			s.translate(s.getFVX(), s.getFVY());
        		}else{
        			s.translate(-s.getFVX(), s.getFVY());
        		}
        	}
        }
    }

    public void resetModel(){
      shapes.clear();
      splitFruits.clear();
      permit2observe = false;
    }

    public Boolean isOver(){
      return this.gameOver;
    }
    
    public void setIsOver(Boolean gg){
      this.gameOver = gg;
    }

    public void setObserve(Boolean permit2observe){
      this.permit2observe = permit2observe;
    }

    public boolean isObservable(){
      return this.permit2observe;
    }
    
    public void addSplitFruit(Fruit s) {
        splitFruits.add(s);
        setChanged();
        notifyObservers();
    }
    
    public int getMissedFruits(){
      int score = 0;
      if(getShapes().size() != 0){
        for(Fruit s : getShapes()){
          if(!s.isPiece() && s.hasCompletedAnimation()){
            score++;
          }
        }
      }
      return score;
    }

    public int getSplitFruit(){
      return splitFruits.size();
    }

    public int getPlayerLives(){
      int lives = 5;
      lives -= (getMissedFruits());

      if(lives <= 0){
        setIsOver(true);
      }

      return lives;
    }
	public void renderFruits(){
		Fruit f = null;
		Path randomFruit = new Path();
		int c = 0;
	    //0 to 1
		double random = Math.random();
	
		if(random < 0.2){
		  //blueberry
			randomFruit.addCircle(75, this.winH-450, 20, Path.Direction.CW);
			c = Color.BLUE;
		}else if(random >= 0.2 && random < 0.4){
		  //orange
			randomFruit.addCircle(75, this.winH-450, 40, Path.Direction.CW);
			c = Color.rgb(255, 165, 0);
		}else if(random >= 0.4 && random < 0.6){
		  //red delicious apple
			randomFruit.addCircle(75, this.winH-450, 30, Path.Direction.CW);
			c = Color.RED;
		}else if(random >= 0.6 && random < 0.8){
		  //banana
			randomFruit.addOval(new RectF(75, this.winH-450, 225, this.winH-420), Path.Direction.CW);
			c = Color.YELLOW;
		}else if(random >= 0.8 && random < 1){
		  //granny smith apple
			randomFruit.addCircle(75, this.winH-450, 60, Path.Direction.CW);
			c = Color.rgb(0, 100, 0);
		}
		f = new Fruit(randomFruit);
		f.setFillColor(c);
		
		random = Math.random();
		if(random < 0.5){
		  f.translate(this.winW-235, 0);
		  f.setFruitDirection(false);
		}
		
		f.setFVX((float)(random*20));
		f.setFVY(-(float)(random*16+45));
		f.translate(f.getFVX(), f.getFVY());
		this.add(f);    
	}
    
    @Override
    public synchronized void deleteObserver(Observer observer) {
        super.deleteObserver(observer);
        setChanged();
        notifyObservers();
    }

    @Override
    public synchronized void deleteObservers() {
        super.deleteObservers();
        setChanged();
        notifyObservers();
    }
}
