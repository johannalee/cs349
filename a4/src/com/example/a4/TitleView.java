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
    private int playerlives = 5, highscore = 0;;
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
        // TODO END CS349
        setBackgroundColor(Color.BLUE);
        setTextSize(20);
        setText("\tFruit Ninja \t\tLives:\t" + playerlives + "\t\tScore:\t"+highscore);
    }

    // Update from model
    // ONLY useful for testing that the view notifications work
    @Override
    public void update(Observable observable, Object data) {
        // TODO BEGIN CS349
        // do something more meaningful here
    	highscore = m.getSplitFruit();
        playerlives = m.getPlayerLives();

        if(m.isOver()){
          highscore = 0;
          playerlives = 5;
          m.resetModel();
        }
        // TODO END CS349
        invalidate();
    }
}
