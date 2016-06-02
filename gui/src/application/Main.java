package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	private static Stage mainStage;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			setPrimaryStage(primaryStage);
			
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/settingType.fxml"));
			Parent root = loader.load();
			settingType controller = loader.getController();
			controller.setPrimaryStage(primaryStage);
			
			Scene scene = new Scene(root);
			
			mainStage.setTitle("SM");
			mainStage.setScene(scene);
			mainStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	//return primary stage
	static public Stage getPrimaryStage(){
		return mainStage;
	}
	
	private void setPrimaryStage(Stage primaryStage){
		mainStage = primaryStage;
	}
}
