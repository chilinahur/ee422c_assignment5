/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2016
 */
package assignment5; // cannot be in default package

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import assignment5.Critter;

import java.io.*;
import java.lang.reflect.Method;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/*
 * Usage: java <pkgname>.Main <input file> test
 * input file is optional.  If input file is specified, the word 'test' is optional.
 * May not use 'test' argument without specifying input file.
 */
public class Main extends Application {

    static Scanner kb;	// scanner connected to keyboard input, or input file
    private static String inputFile;	// input file, used instead of keyboard input if specified
    static ByteArrayOutputStream testOutputString;	// if test specified, holds all console output
    private static String myPackage;	// package of Critter file.  Critter cannot be in default pkg.
    private static boolean DEBUG = false; // Use it or not, as you wish!
    static PrintStream old = System.out;	// if you want to restore output to console


    // Gets the package name.  The usage assumes that Critter and its subclasses are all in the same package.
    static {
        myPackage = Critter.class.getPackage().toString().split(" ")[1];
    }

    /**
     * Main method.
     * @param args args can be empty.  If not empty, provide two parameters -- the first is a file name, 
     * and the second is test (for test output, where all output to be directed to a String), or nothing.
     */
    public static void main(String[] args) {
    
    	launch(args);
    	
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception{
    
    	Stage secStage = new Stage();
    	GridPane layout2 = new GridPane();
    	Scene scene2 = new Scene(layout2, Params.world_width * 10, Params.world_height * 10);
    	
    	secStage.setScene(scene2);
    	secStage.show();
    	
    	/*
    	try {			

			grid.setGridLinesVisible(true);

			Scene scene2 = new Scene(grid, 500, 500);
			secStage.setScene(scen,e2);
			
			secStage.show();
			
			// Paints the icons.
			Painter.paint();
			
		} 
    	
    	catch(Exception e) {
			e.printStackTrace();		
		}
    	*/
    	
    	primaryStage.setTitle("Critters GUI");
    	
    	Button show = new Button();
    	show.setText("show");
    	Button step = new Button();
    	step.setText("step");
    	Button seed = new Button();
    	seed.setText("seed");
    	Button make = new Button();
    	make.setText("make");
    	Button stats = new Button();
    	stats.setText("stats");
    	
    	TextField stepNum = new TextField();
    	
    	TextField makeType = new TextField();
    	makeType.setPrefWidth(75);
    	TextField makeNum = new TextField();
    	makeNum.setPrefWidth(75);

    	TextField seedNum = new TextField();
    	
    	TextField statsType = new TextField();
    	
    	
    	show.setOnAction(new EventHandler<ActionEvent>(){
    			
    		@Override
    		public void handle(ActionEvent event){
    			//System.out.println("HORRAY!!");
    			
    			Critter.displayWorld();
    			
    		}
    		
    	});
    	
    	step.setOnAction(new EventHandler<ActionEvent>(){
			
    		@Override
    		public void handle(ActionEvent event){
    			
    			int num = 0;
        		String input = stepNum.getText();
        		
        		try{
        			num = Integer.parseInt(input);
        		}
        		
        		catch(Exception e){
        			Critter.worldTimeStep();
        		}
        		
        		for(int i = 0; i < num; i++){
        			Critter.worldTimeStep();
        		}
    			
    		}
    		
    	});
    	
    	seed.setOnAction(new EventHandler<ActionEvent>(){
			
    		
    		@Override
    		public void handle(ActionEvent event){
    			
        		int num = 0;
        		String input = seedNum.getText();
        		
        		try{
        			num = Integer.parseInt(input);
        			
        			if(num < 0) throw new Exception();
        			
        			Critter.setSeed(num);
        			
        		}
        		
        		catch(Exception e){
        			
        		}
        		
    		}
    		
    	});
    	
    	make.setOnAction(new EventHandler<ActionEvent>(){
			
    		@Override
    		public void handle(ActionEvent event){
    			
    		
	    		int num = 0;
	    		String className = makeType.getText();
	    		String number = makeNum.getText();
	    		
	    		try{
	    			num = Integer.parseInt(number);
	    			
	    			if(num < 0) throw new Exception();
	    			
	        		if(num == 0)
	        			Critter.makeCritter(className.toString());
	        		else{
	        			for(int i = 0; i < num; i++)
	            			Critter.makeCritter(className.toString());
	        		}
	        		
	    		}
	    		
	    		catch(Exception e){
	    			
	    		}
    		}
    		
    	});
    	
    	stats.setOnAction(new EventHandler<ActionEvent>(){
			
    		@Override
    		public void handle(ActionEvent event){
    			
    			try{
    				String className = statsType.getText();
    				
    				List<Critter> result = new ArrayList<Critter>();
            		result = Critter.getInstances(className);
            		
            		Class<?> claz = Class.forName(myPackage + "." + className);
            		Method method = claz.getMethod("runStats", List.class);
            		method.invoke(claz, result);
    				
    			}
    			
    			catch(Exception e){
    				
    			}
    			
    		}
    		
    	});
    	
    	Pane layout = new Pane();
    	//layout.getChildren().addAll(show, step, seed, make, stats);
    	
    	show.setLayoutX(50);
    	show.setLayoutY(50);
    	
    	stepNum.setLayoutX(150);
    	stepNum.setLayoutY(100);
    	
    	step.setLayoutX(50);
    	step.setLayoutY(100);
    	
    	seed.setLayoutX(50);
    	seed.setLayoutY(150);
    	
    	seedNum.setLayoutX(150);
    	seedNum.setLayoutY(150);
    	
    	make.setLayoutX(50);
    	make.setLayoutY(200);
    	
    	makeType.setLayoutX(150);
    	makeType.setLayoutY(200);
    	
    	makeNum.setLayoutX(250);
    	makeNum.setLayoutY(200);
    	
    	stats.setLayoutX(50);
    	stats.setLayoutY(250);
    	
    	statsType.setLayoutX(150);
    	statsType.setLayoutY(250);
    	
    	
    	layout.getChildren().addAll(show, step, seed, make, stats, stepNum, makeType,
    	makeNum, seedNum, statsType);
    	
    	Scene scene = new Scene(layout, 500, 500);
    	primaryStage.setScene(scene);
    	primaryStage.show();
    	
    }
    
       
}
