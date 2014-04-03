/**
 * CS349 Winter 2014
 * Assignment 4 Demo Code
 * Jeff Avery & Michael Terry
 */
package com.example.a4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.widget.TextView;

import java.util.Observable;
import java.util.Observer;

/*
 * View to display the Title, and Score
 * Score currently just increments every time we get an update
 * from the model (i.e. a new fruit is added).
 */
public class TitleView extends TextView implements Observer {
    private int playerlives = 5, score = 0, highscore = 0;
    private String playerlivesString = "Lives:\t" + playerlives + "\t\t";
    private String scoreString = "Score:\t" + score;
    Model m;
    
    // Constructor requires model reference
    public TitleView(Context context, Model model) {
        super(context);

        // set width, height of this view
        this.setHeight(80);
        this.setWidth(MainActivity.displaySize.x);

        // register with model so that we get updates
        model.addObserver(this);
        m = model;
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO BEGIN CS349
        // add high score, anything else you want to display in the title
        if(!m.isObservable()){
        	playerlivesString = "";
        	scoreString = "\t\tHigh Score:\t" + highscore;
        }else{
            playerlivesString = "Lives:\t" + playerlives + "\t\t";
            scoreString = "Score:\t" + score;
        }
        
        setBackgroundColor(Color.BLUE);
        setTextSize(20);
        setText("\tFruit Ninja\t\t" + playerlivesString + scoreString);
        // TODO END CS349
    }

    // Update from model
    // ONLY useful for testing that the view notifications work
    @Override
    public void update(Observable observable, Object data) {
        // TODO BEGIN CS349
        // do something more meaningful here
    	
    	score = m.getSplitFruit();
        playerlives = m.getPlayerLives();
    	highscore = score > highscore ? score : highscore;
        // TODO END CS349
        invalidate();
    }
}
