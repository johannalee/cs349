/**
 * CS349 Winter 2014
 * Assignment 4 Demo Code
 * Jeff Avery
 */
package com.example.a4;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.a4complete.R;

public class MainActivity extends Activity {
    private Model model;
    private MainView mainView;
    private TitleView titleView;
    public static Point displaySize;

    private Button button;
    private String start = "Start";
    private String pause = "Pause";
    private String resume = "Resume";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setTitle("CS349 A4 Demo");

        // save display size
        Display display = getWindowManager().getDefaultDisplay();
        displaySize = new Point();
        display.getSize(displaySize);

        // initialize model
        model = new Model();
        
        // set view
        setContentView(R.layout.main);
	   	button = (Button) findViewById(R.id.button1);
		 	
	 	button.setOnClickListener(new OnClickListener() {
	 		 @Override
	 		 public void onClick(View view) {
	 			 String btnStr = ((Button)button).getText().toString();

	 			 if(btnStr.equals(start)){
	 				 button.setText(pause);
	 				 model.setObserve(true);
	 			 }else if(btnStr.equals(pause)){
	 				 button.setText(resume);
	 				 model.setObserve(false);
	 			 }else if(btnStr.equals(resume)){
	 				 button.setText(pause);
	 				 model.setObserve(true);
	 			 }
	 		 }
	 	 });
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // create the views and add them to the main activity
        titleView = new TitleView(this.getApplicationContext(), model);
        ViewGroup v1 = (ViewGroup) findViewById(R.id.main_1);
        v1.addView(titleView);

        mainView = new MainView(this.getApplicationContext(), model);
        ViewGroup v2 = (ViewGroup) findViewById(R.id.main_2);
        v2.addView(mainView);

        // notify all views
        final Handler updateHandler = new Handler();
        Runnable runnable = new Runnable(){
        	public void run(){
        		if(Math.random() < 0.15 && model.isObservable()){
    	          model.renderFruits();
        	    }
        		
        		if(model.isOver()){
        			button.setText(start);
        			model.setGameOver(false);
        		}
        		model.initObservers();
        		updateHandler.postDelayed(this, 100);
        	}
        };
        updateHandler.postDelayed(runnable, 100);
    }
}
