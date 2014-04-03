/**
 * CS349 Winter 2014
 * Assignment 4 Demo Code
 * Jeff Avery & Michael Terry
 */
package com.example.a4;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.*;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Iterator;
import java.util.Observable;
import java.util.Observer;

/*
 * View of the main game area.
 * Displays pieces of fruit, and allows players to slice them.
 */
@SuppressLint("ViewConstructor")
public class MainView extends View implements Observer {
    private final Model model;
    private final MouseDrag drag = new MouseDrag();

    // Constructor
    MainView(Context context, Model m) {
        super(context);

        // register this view with the model
        model = m;
        
        model.addObserver(this);

        // add controller
        // capture touch movement, and determine if we intersect a shape
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        drag.start(event.getX(), event.getY());
                        break;

                    case MotionEvent.ACTION_UP:
                        drag.stop(event.getX(), event.getY());

                        // find intersected shapes
                        Iterator<Fruit> i = model.getShapes().iterator();
                        while(i.hasNext() && model.isObservable()) {
                            Fruit s = i.next();

                            
                            if (s.intersects(drag.getStart(), drag.getEnd())) {
                                try {
                                    Fruit[] newFruits = s.split(drag.getStart(), drag.getEnd());
                                    // TODO BEGIN CS349
                                    // you may want to place the fruit more carefully than this
                                    if(newFruits.length > 0){
                                        newFruits[0].translate(0, -10);
                                        newFruits[0].setIsPiece(true);
                                        
                                        newFruits[1].translate(0, +10);
                                        newFruits[1].setIsPiece(true);
                                        // TODO END CS349
                                        model.add(newFruits[0]);
                                        model.add(newFruits[1]);

                                        // TODO BEGIN CS349
                                        // delete original fruit from model			
                                        model.remove(s);
                                        model.addSplitFruit(s);
                                        // TODO END CS349
                                    }

                                } catch (Exception ex) {
                                    Log.e("fruit_ninja", "Error: " + ex.getMessage());
                                }
                            }
                            invalidate();
                        }
                        break;
                }
                return true;
            }
        });
    }

    // inner class to track mouse drag
    // a better solution *might* be to dynamically track touch movement
    // in the controller above
    class MouseDrag {
        private float startx, starty;
        private float endx, endy;

        protected PointF getStart() { return new PointF(startx, starty); }
        protected PointF getEnd() { return new PointF(endx, endy); }

        protected void start(float x, float y) {
            this.startx = x;
            this.starty = y;
        }

        protected void stop(float x, float y) {
            this.endx = x;
            this.endy = y;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw background
        setBackgroundColor(Color.WHITE);

        // draw all pieces of fruit
        for (Fruit s : model.getShapes()) {

            if(!s.isPiece() && s.hasCompletedAnimation()){
    			model.addMissedFruits();;
    			model.remove(s);
            }
            s.draw(canvas);
        }
    }

    @Override
    public void update(Observable observable, Object data) {
        invalidate();
    }
}
