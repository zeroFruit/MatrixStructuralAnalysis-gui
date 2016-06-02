package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Output implements Initializable{
	private Stage outputStage;
	private receiveResult receive;
	private TableView coeffiecientTbl = new TableView<>();
	private final ObservableList<double[]> coeffList 
		= FXCollections.observableArrayList();
	/* to dynamically populate columns make ArrayList saving tableColumns */
	private ArrayList<TableColumn<String, Double>> columnFactory
		= new ArrayList<TableColumn<String,Double>>();

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		Scene scene = new Scene(new Group());
		outputStage.setHeight(650);
		outputStage.setWidth(500);
		double[][] tempCoeff = receive.getrecCoeff();
		double[] sampleRow = tempCoeff[0];
		int columnSize = sampleRow.length;
		/* set up rows */
		for (int i = 0; i < tempCoeff.length; i++) {
			coeffList.add(tempCoeff[i]);
		}
		coeffiecientTbl.setItems(coeffList);
		/* set up columns */

		for (int i = 0; i < columnSize; i++) {
			String colName;
			if (i%2 == 0) {
				int colNum = i%2 + 1;
				colName = "u"+ colNum;
				TableColumn tempCol = new TableColumn(colName);
				tempCol.setMinWidth(50);
				coeffiecientTbl.getColumns().add(tempCol);
				//columnFactory.add(tempCol);
			}
			else if (i%2 == 1) {
				int colNum = i%2 + 1;
				colName = "v"+ colNum;
				TableColumn tempCol = new TableColumn(colName);
				tempCol.setMinWidth(50);
				coeffiecientTbl.getColumns().add(tempCol);
				//columnFactory.add(tempCol);
			}
		}
		/* add to tableView */
		
		VBox vbox = new VBox();
		vbox.getChildren().addAll(coeffiecientTbl);
		vbox.prefHeight(650);
		vbox.prefWidth(500);
		((Group) scene.getRoot()).getChildren().addAll(vbox);
		outputStage.setScene(scene);
		outputStage.show();
		
	}
	public void setResult(ArrayList<Double> receivedResult){
		receive.setrecResult(receivedResult);
	}
	public void setCoefficient(double[][] receivedCoeff){
		receive.setrecCoeff(receivedCoeff);
	}
}

class receiveResult{
	ArrayList<Double> receivedResult;
	double[][] receivedCoeff;
	
	void setrecResult(ArrayList<Double> receivedResult){
		this.receivedResult = receivedResult;
	}
	void setrecCoeff(double[][] receivedCoeff){
		this.receivedCoeff = receivedCoeff;
	}
	ArrayList<Double> getrecResult(){
		return this.receivedResult;
	}
	double[][] getrecCoeff(){
		return this.receivedCoeff;
	}
}
