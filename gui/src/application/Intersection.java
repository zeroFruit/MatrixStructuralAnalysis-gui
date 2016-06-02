package application;

import javafx.stage.Stage;

public class Intersection {
	//this class is for intersect of other class instance
	private Stage primaryStage;
	
	public Intersection() {
		// TODO Auto-generated constructor stub
	}

	public void setStage(Stage primaryStage){
		this.primaryStage = primaryStage;
	}
	
	public Stage getStage(){
		return primaryStage;
	}
	
}
