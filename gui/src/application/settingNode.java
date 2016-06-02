package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javax.xml.stream.EventFilter;

import Main.UserInterface;
import Member.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.FocusModel;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

public class settingNode implements Initializable {
	/*
	 * @param UI
	 * 		UserInterface type instance to calculate Gauss Dedlimination
	 * 
	 */
	UserInterface UI;
	int type;
	int memberNum;
	int nodeNum;
	//when finishing programming, and encapsulation of SM
	//then architect the exact format
	private	Stage nodeStage = new Stage();
	private TableView propertyTable = new TableView<>();
	private TableView externFactorTable = new TableView<>();
	private TableView nodeConnectTable = new TableView<>();
	private final ObservableList<propertyStore> propertyData 
		= FXCollections.observableArrayList();
	private final ObservableList<nodeStore> externFacData 
		= FXCollections.observableArrayList();
	private final ObservableList<nodeConnectionStore> nodeConData
		= FXCollections.observableArrayList();
	
	public settingNode(UserInterface UI) {
		this.UI = UI;
		this.memberNum = UI.getStructureType().get_how_many_member();
		this.nodeNum = UI.getStructureType().get_how_many_node();
	}
	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// initialize member number, node number
		for (int i = 0; i < this.memberNum; i++) {
			propertyStore tempStore = new propertyStore();
			tempStore.set_memberNum(i+1);
			propertyData.add(tempStore);			
			nodeConnectionStore tempConn = new nodeConnectionStore();
			tempConn.setConNum(i+1);
			nodeConData.add(tempConn);
		}
		for (int i = 0; i < this.nodeNum; i++) {
			nodeStore tempStore = new nodeStore();
			tempStore.set_nodeNum(""+(i+1));
			externFacData.add(tempStore);
		}
		try {
			Scene scene = new Scene(new Group());
			nodeStage.setTitle("Setting node");
			nodeStage.setWidth(500);
			nodeStage.setHeight(650);
			
			/*   Setting Member data Table   */
			final Label label = new Label("node setting window");
			label.setFont(new Font("Arial", 20));
			// Create property, external factor cell factory
			// so that cells can support editing.
			Callback<TableColumn, TableCell> cellFactory = new Callback<TableColumn, TableCell>() {
				@Override
				public TableCell call(TableColumn param) {
					return new EditingCell();
				}
			};
			// Set up the columns
			// make this column un-editable
			TableColumn memberNumCol = new TableColumn<>("Member number");
			memberNumCol.setMinWidth(100);
			memberNumCol.setCellValueFactory(new PropertyValueFactory<propertyStore, String>("member number"));
			// set default member number columns 
			memberNumCol.setCellValueFactory(new Callback<CellDataFeatures<propertyStore, String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<propertyStore, String> member) {
					return new SimpleStringProperty("<"+member.getValue().get_memberNum()+">");
				}
			});
			memberNumCol.setCellFactory(cellFactory);
			memberNumCol.setEditable(false);
			TableColumn setA = new TableColumn<>("A");
			setA.setMinWidth(100);
			setA.setCellValueFactory(new PropertyValueFactory<propertyStore, Double>("A"));
			setA.setCellFactory(cellFactory);
			setA.setEditable(true);
			TableColumn setE = new TableColumn<>("E");
			setE.setMinWidth(100);
			setE.setCellValueFactory(new PropertyValueFactory<propertyStore, Double>("E"));
			setE.setCellFactory(cellFactory);
			setE.setEditable(true);
			TableColumn setL = new TableColumn<>("L");
			setL.setMinWidth(100);
			setL.setCellValueFactory(new PropertyValueFactory<propertyStore, Double>("L"));
			setL.setCellFactory(cellFactory);
			setL.setEditable(true);
			TableColumn setAngle = new TableColumn<>("Angle");
			setL.setMinWidth(100);
			setL.setCellValueFactory(new PropertyValueFactory<propertyStore, Double>("angle"));
			setL.setCellFactory(cellFactory);
			setL.setEditable(true);
			propertyTable.setItems(propertyData);
			propertyTable.getColumns().addAll(memberNumCol, setA, setE, setL, setAngle);
			propertyTable.setEditable(true);
			// Modifying nodNumCol property
			memberNumCol.setOnEditCommit(new EventHandler<CellEditEvent<propertyStore, Double>>() {
				@Override
				public void handle(CellEditEvent<propertyStore, Double> t) {
					// TODO Auto-generated method stub
					((propertyStore) t.getTableView().getItems().get(t.getTablePosition().getRow())).set_A(t.getNewValue());
				}
			});
			setA.setOnEditCommit(new EventHandler<CellEditEvent<propertyStore, Double>>() {
				@Override
				public void handle(CellEditEvent<propertyStore, Double> t) {
					// TODO Auto-generated method stub
					((propertyStore) t.getTableView().getItems().get(t.getTablePosition().getRow())).set_A(t.getNewValue());
				}
			});
			setE.setOnEditCommit(new EventHandler<CellEditEvent<propertyStore, Double>>() {
				@Override
				public void handle(CellEditEvent<propertyStore, Double> t) {
					// TODO Auto-generated method stub
					((propertyStore) t.getTableView().getItems().get(t.getTablePosition().getRow())).set_E(t.getNewValue());
				}
			});
			setL.setOnEditCommit(new EventHandler<CellEditEvent<propertyStore, Double>>() {
				@Override
				public void handle(CellEditEvent<propertyStore, Double> t) {
					// TODO Auto-generated method stub
					((propertyStore) t.getTableView().getItems().get(t.getTablePosition().getRow())).set_L(t.getNewValue());
				}
			});
			
			/* Setting Node data Table */
			TableColumn nodeNumCol = new TableColumn<>("Node Number");
			nodeNumCol.setMinWidth(200);
			nodeNumCol.setCellValueFactory(new PropertyValueFactory<>("node number"));
			// set default member number columns
			nodeNumCol.setCellValueFactory(new Callback<CellDataFeatures<nodeStore, String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<nodeStore, String> nodestore) {
					// TODO Auto-generated method stub
					return new SimpleStringProperty(nodestore.getValue().get_nodeNum());
				}
			});
			nodeNumCol.setCellFactory(cellFactory);
			nodeNumCol.setEditable(false);
			TableColumn FxCol = new TableColumn<>("Fx");
			FxCol.setMinWidth(100);
			FxCol.setCellValueFactory(new PropertyValueFactory<>("fx"));
			FxCol.setCellFactory(cellFactory);
			FxCol.setEditable(true);
			FxCol.setMinWidth(100);
			TableColumn FyCol = new TableColumn<>("Fy");
			FyCol.setCellValueFactory(new PropertyValueFactory<>("fy"));
			FyCol.setCellFactory(cellFactory);
			FyCol.setEditable(true);
			FyCol.setMinWidth(100);
			TableColumn duCol = new TableColumn<>("u");
			duCol.setCellValueFactory(new PropertyValueFactory<>("du"));
			duCol.setCellFactory(cellFactory);
			duCol.setEditable(true);
			TableColumn dvCol = new TableColumn<>("v");
			dvCol.setCellValueFactory(new PropertyValueFactory<>("dv"));
			dvCol.setCellFactory(cellFactory);
			dvCol.setEditable(true);
			externFactorTable.setItems(externFacData);
			externFactorTable.getColumns().addAll(nodeNumCol, FxCol, FyCol, duCol, dvCol);
			externFactorTable.setEditable(true);
			FxCol.setOnEditCommit(new EventHandler<CellEditEvent<nodeStore, Double>>() {
				@Override
				public void handle(CellEditEvent<nodeStore, Double> t) {
					// TODO Auto-generated method stub
					((nodeStore) t.getTableView().getItems().get(t.getTablePosition().getRow())).set_Fx(t.getNewValue());
				}
			});
			FyCol.setOnEditCommit(new EventHandler<CellEditEvent<nodeStore, Double>>() {
				@Override
				public void handle(CellEditEvent<nodeStore, Double> t) {
					// TODO Auto-generated method stub
					((nodeStore) t.getTableView().getItems().get(t.getTablePosition().getRow())).set_Fy(t.getNewValue());
				}
			});
			duCol.setOnEditCommit(new EventHandler<CellEditEvent<nodeStore, Double>>() {
				@Override
				public void handle(CellEditEvent<nodeStore, Double> t) {
					// TODO Auto-generated method stub
					((nodeStore) t.getTableView().getItems().get(t.getTablePosition().getRow())).set_u(t.getNewValue());
				}
			});
			dvCol.setOnEditCommit(new EventHandler<CellEditEvent<nodeStore, Double>>() {
				@Override
				public void handle(CellEditEvent<nodeStore, Double> t) {
					// TODO Auto-generated method stub
					((nodeStore) t.getTableView().getItems().get(t.getTablePosition().getRow())).set_v(t.getNewValue());
				}
			});
			
			/* Setting Node data Table */
			TableColumn connNumCol = new TableColumn<>("Connection Number");
			connNumCol.setMinWidth(100);
			connNumCol.setCellValueFactory(new PropertyValueFactory<>("connection number"));
			// set default member number columns
			connNumCol.setCellValueFactory(new Callback<CellDataFeatures<nodeConnectionStore, String>, ObservableValue<String>>() {
				@Override
				public ObservableValue<String> call(CellDataFeatures<nodeConnectionStore, String> conn) {
					return new SimpleStringProperty("Connection"+conn.getValue().getConNum());
				}
			});
			connNumCol.setCellFactory(cellFactory);
			connNumCol.setEditable(false);
			TableColumn nodeSCol = new TableColumn<>("Start Node");
			nodeSCol.setMinWidth(100);
			nodeSCol.setCellValueFactory(new PropertyValueFactory<>("start node"));
			nodeSCol.setCellFactory(cellFactory);
			nodeSCol.setEditable(true);
			TableColumn nodeECol = new TableColumn<>("End Node");
			nodeECol.setMinWidth(100);
			nodeECol.setCellValueFactory(new PropertyValueFactory<>("end node"));
			nodeECol.setCellFactory(cellFactory);
			nodeECol.setEditable(true);
			nodeConnectTable.setItems(nodeConData);
			nodeConnectTable.getColumns().addAll(connNumCol, nodeSCol, nodeECol);
			nodeConnectTable.setEditable(true);
			nodeSCol.setOnEditCommit(new EventHandler<CellEditEvent<nodeConnectionStore, Integer>>() {
				@Override
				public void handle(CellEditEvent<nodeConnectionStore, Integer> t) {
					// TODO Auto-generated method stub
					((nodeConnectionStore) t.getTableView().getItems().get(t.getTablePosition().getRow())).setNodeStart(t.getNewValue());
				}
			});
			nodeECol.setOnEditCommit(new EventHandler<CellEditEvent<nodeConnectionStore, Integer>>() {
				@Override
				public void handle(CellEditEvent<nodeConnectionStore, Integer> t) {
					// TODO Auto-generated method stub
					((nodeConnectionStore) t.getTableView().getItems().get(t.getTablePosition().getRow())).setNodeEnd(t.getNewValue());
				}
			});
			
			/* Setting container, button */
			final ScrollPane sp1 = new ScrollPane();
			final ScrollPane sp2 = new ScrollPane();
			final ScrollPane sp3 = new ScrollPane();
			sp1.setVmax(100);
			sp1.setLayoutX(5);
			sp1.setPrefSize(400, 200);
			sp1.setContent(propertyTable);
			// initialize focusing
			sp1.requestFocus();
			sp2.setVmax(100);
			sp2.setLayoutX(5);
			sp2.setLayoutY(200);
			sp2.setPrefSize(400, 200);
			sp2.setContent(externFactorTable);
			sp3.setVmax(100);
			sp3.setLayoutX(5);
			sp3.setLayoutY(400);
			sp3.setPrefSize(400, 200);
			sp3.setContent(nodeConnectTable);
			// seperate scroll bar scrolling
			sp1.focusedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if (newValue == false) {
						sp1.addEventFilter(ScrollEvent.SCROLL_STARTED, new EventHandler<ScrollEvent>() {
							@Override
							public void handle(ScrollEvent event) {
								if (event.getDeltaY() != 0) {
									event.consume();
								}
							}
						});
					}
				}
			});
			sp2.focusedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if (newValue == false) {
						sp2.addEventFilter(ScrollEvent.SCROLL_STARTED, new EventHandler<ScrollEvent>() {
							@Override
							public void handle(ScrollEvent event) {
								if (event.getDeltaY() != 0) {
									event.consume();
								}
							}
						});
					}
				}
			});
			sp3.focusedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if (newValue == false) {
						sp3.addEventFilter(ScrollEvent.SCROLL_STARTED, new EventHandler<ScrollEvent>() {
							@Override
							public void handle(ScrollEvent event) {
								if (event.getDeltaY() != 0) {
									event.consume();
								}
							}
						});
					}
				}
			});
			// button
			final Button sendButton = new Button("Calculate");
			sendButton.setLayoutX(410);
			sendButton.setLayoutY(10);
			sendButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent e) {
					/*
					 *  we should send data to caculation part
					 */
					double A;
					double E;
					double L;
					double angle;
					for (int i = 0; i < propertyData.size(); i++) {
						A = propertyData.get(i).get_A();
						E = propertyData.get(i).get_E();
						L = propertyData.get(i).get_L();
						angle = propertyData.get(i).get_angle();
						UI.setProperty(A, E, L, angle);
						int start = nodeConData.get(i).getNodeStart();
						int end = nodeConData.get(i).getNodeEnd();
						UI.howConnected(start, end);
					}
					double Fx;
					double Fy;
					double u;
					double v;
					String nodeNum;
					for (int i = 0; i < externFacData.size(); i++) {
						Fx = externFacData.get(i).get_Fx();
						Fy = externFacData.get(i).get_Fy();
						u = externFacData.get(i).get_u();
						v = externFacData.get(i).get_v();
						nodeNum = externFacData.get(i).get_nodeNum();
						UI.setFactor(Fx, Fy, u, v, nodeNum);
					}
					//UI.solution();
					
					// Should check whether all cells are not null
					FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/output.fxml"));
					try {
						Output outputController = new Output();
						loader.setController(outputController);
						Parent root = loader.load();
						// get the primary stage
						Stage primaryStage = Main.getPrimaryStage();
						primaryStage.setScene(root.getScene());
						primaryStage.show();
						//primaryStage.close();
						
					} catch (IOException ioe) {
						ioe.printStackTrace();
					}
					
				}
			});
			((Group) scene.getRoot()).getChildren().addAll(sp1, sp2, sp3, sendButton);
			nodeStage.setScene(scene);
			nodeStage.show();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	public void setType(int type){this.type = type;}
	public void setNodeNum(int nodeNum){this.nodeNum = nodeNum;}
	public void setMemberNum(int memberNum){this.memberNum = memberNum;}
}

class propertyStore{
	double A;
	double E;
	double L;
	double angle;
	int memberNum;
	
	public propertyStore() {
		// TODO Auto-generated constructor stub
	}
	double get_A() {return this.A;}
	double get_E() {return E;}
	double get_L() {return L;}
	double get_angle() {return angle;}
	int get_memberNum(){return this.memberNum;}
	void set_A(double A) {this.A = A;}
	void set_E(double E) {this.E = E;}
	void set_L(double L) {this.L = L;}
	void set_angle(double angle) {this.angle = angle;}
	void set_memberNum(int memberNum){this.memberNum = memberNum;}
}
class nodeStore{
	double Fx;
	double Fy;
	double u;
	double v;
	String nodeNum;
	
	public nodeStore() {
		// TODO Auto-generated constructor stub
	}
	void set_Fx(double Fx) {this.Fx = Fx;}
	void set_Fy(double Fy) {this.Fy = Fy;}
	void set_u(double u) {this.u = u;}
	void set_v(double v) {this.v = v;}
	void set_nodeNum(String nodeNum){this.nodeNum = nodeNum;}
	double get_Fx() {return this.Fx;}
	double get_Fy() {return this.Fy;}
	double get_u() {return this.u;}
	double get_v() {return this.v;}
	String get_nodeNum(){return this.nodeNum;}
}
class nodeConnectionStore{
	int nodeStart;
	int nodeEnd;
	int conNum;
	
	public nodeConnectionStore() {
		// TODO Auto-generated constructor stub
	}
	void setNodeStart(int nodeStart){this.nodeStart = nodeStart;}
	void setNodeEnd(int nodeEnd){this.nodeEnd = nodeEnd;}
	void setConNum(int conNum){this.conNum = conNum;}
	int getNodeStart(){return this.nodeStart;}
	int getNodeEnd(){return this.nodeEnd;}
	int getConNum(){return this.conNum;}
}
