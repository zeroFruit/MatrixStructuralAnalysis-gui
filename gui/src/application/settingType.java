package application;

import java.awt.Dialog;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.swing.Box;

import Main.UserInterface;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class settingType implements Initializable {
	@FXML private ListView<String> structureType;
	@FXML private AnchorPane container;
	propertyInfo info = new propertyInfo();
	private Stage primaryStage;
	boolean illegalflag;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
	//	primaryStage = 
		dynamicNode creater = new dynamicNode();
		ComboBox<Integer> memberNum = creater.getMemNumCombo();
		ComboBox<Integer> nodeNum = creater.getNodeNumCombo();
		structureType.setItems(FXCollections.observableArrayList("truss", "frame"));
		structureType.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			ChangeListener<Number> memberListener = new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observable1, Number oldValue1, Number newValue1) {
					//set how many the node is?
					info.setMemberNum(newValue1.intValue()+1);
				}
			};		
			ChangeListener<Number> nodeListener = new ChangeListener<Number>(){
				@Override
				public void changed(ObservableValue<? extends Number> observable2, Number oldValue2, Number newValue2) {
					// TODO Auto-generated method stub
					info.setNodeNum(newValue2.intValue()+1);
				}
			};
			@Override
			// invoked when ListView changed
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				try {
					//set what type?
					info.setType(newValue.intValue());
					Label memlabel = new Label("member number");
					memlabel.setLayoutX(120);
					memlabel.setLayoutY(10);
					container.getChildren().add(memlabel);
					memberNum.setLayoutX(135);
					memberNum.setLayoutY(30);
					container.getChildren().add(memberNum);
					memberNum.getSelectionModel().selectedIndexProperty().addListener(memberListener);
					
					Label nodelabel = new Label("node number");
					nodelabel.setLayoutX(280);
					nodelabel.setLayoutY(10);
					container.getChildren().add(nodelabel);
					nodeNum.setLayoutX(295);
					nodeNum.setLayoutY(30);
					container.getChildren().add(nodeNum);
					nodeNum.getSelectionModel().selectedIndexProperty().addListener(nodeListener);
					
				} catch (IllegalArgumentException e) {
					info.setType(newValue.intValue());
					memberNum.getSelectionModel().selectedIndexProperty().removeListener(memberListener);
					container.getChildren().remove(memberNum);
					container.getChildren().add(memberNum);
					memberNum.getSelectionModel().selectedIndexProperty().addListener(memberListener);
					
					nodeNum.getSelectionModel().selectedIndexProperty().removeListener(nodeListener);
					container.getChildren().remove(nodeNum);
					container.getChildren().add(nodeNum);
					nodeNum.getSelectionModel().selectedIndexProperty().addListener(nodeListener);
					System.out.println("Err");
				}
			}
		});
	}
	public void handleBtnOkAction(ActionEvent e){
		// params
		int type = info.getType();
		int memberNum = info.getMemberNum();
		int nodeNum = info.getNodeNum();
		if (type != -1 && memberNum != 0 && nodeNum != 0) {
			/*
			 * @param type
			 * 		we should decide how discrete frame, truss
			 */
			UserInterface user = new UserInterface(nodeNum, memberNum, type);
			//move to setting page
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/settingNode.fxml"));
			try {
				settingNode setNodeController = new settingNode(user);
				//setNodeController.setType(type);
				//setNodeController.setMemberNum(memberNum);
				//setNodeController.setNodeNum(nodeNum);
				loader.setController(setNodeController);
				Parent root = loader.load();
				// get the primaryStage
				Stage primaryStage = Main.getPrimaryStage();
				primaryStage.setScene(root.getScene());
				primaryStage.show();
				primaryStage.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} 
		else {
			Stage popup = new Stage();
			popup.initModality(Modality.APPLICATION_MODAL);
			popup.initOwner(primaryStage);
			VBox popupVBox = new VBox(20);
			popupVBox.getChildren().add(new Text("Please choose the option"));
			Scene popupScene = new Scene(popupVBox, 200, 200);
			popup.setScene(popupScene);
			popup.show();
		}
	}
	
	void setPrimaryStage(Stage primaryStage){
		this.primaryStage = primaryStage;
	}
	
	public void handleBtnCancelAction(ActionEvent e){
		Platform.exit();
	}
}

class dynamicNode{
	private ObservableList<Integer> memNumBox;
	private ObservableList<Integer> nodeNumBox;
	private ComboBox<Integer> memberNum;
	private ComboBox<Integer> nodeNum;
	
	public dynamicNode() {
		// TODO Auto-generated constructor stub
		this.memNumBox = FXCollections.observableArrayList(1, 2, 3, 4);
		this.nodeNumBox = FXCollections.observableArrayList(1, 2, 3, 4);
		this.memberNum = new ComboBox<>(memNumBox);
		this.nodeNum = new ComboBox<>(nodeNumBox);
	}
	ComboBox<Integer> getMemNumCombo(){
		return this.memberNum;
	}
	ComboBox<Integer> getNodeNumCombo(){
		return this.nodeNum;
	}
}

class propertyInfo{
	private int nodeNum;
	private int memberNum;
	private int type;
	
	propertyInfo() {}
	void setMemberNum(int memberNum){this.memberNum = memberNum;}
	void setNodeNum(int nodeNum){this.nodeNum = nodeNum;}
	void setType(int type){this.type = type;}
	int getNodeNum(){return this.nodeNum;}
	int getMemberNum(){return this.memberNum;}
	int getType(){return this.type;}
}