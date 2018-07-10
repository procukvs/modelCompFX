package model.gui;

import javafx.beans.property.*;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;

import model.*;

public class StateP {
	private SimpleStringProperty state =
			 new SimpleStringProperty(this, "state", null);
	private SimpleStringProperty txComm =
			 new SimpleStringProperty(this, "txComm", null);
	private SimpleIntegerProperty id=
			 new SimpleIntegerProperty(this, "id", 0);
	public StateP() {
		this(null, null, 0);
	}
	public StateP(String state, String txComm, int id) {
		this.state.set(state); this.txComm.set(txComm); this.id.set(id);
	}
	
	public StateP(State s){
		this(s.getState(), s.gettxComm(),s.getId());
	}
	
	/* state Property */
	public String getState() { return state.get();}
	public StringProperty stateProperty() {	return state; }
	public void setState(String state) {stateProperty().set(state);}

	/* txComm Property */
	public String getTxComm() { return txComm.get();}
	public StringProperty txCommProperty() { return txComm; }
	public void setTxComm(String txComm) {txCommProperty().set(txComm);}	
	
	/* id Property */
	public int getId() { return id.get();}
	public IntegerProperty idProperty() {	return id; }
	public void setId(int id) {idProperty().set(id);}	
	
	// build TableColumns
	public static TableColumn<StateP,String> getStateColumn(){
		TableColumn<StateP,String> stateStateCol = new TableColumn<>("Стан");
		stateStateCol.setCellValueFactory(new PropertyValueFactory<>("state"));
		return stateStateCol;
	}
		
	public static TableColumn<StateP,String> getTxCommColumn(){
		TableColumn<StateP,String> stateTxCommCol = new TableColumn<>("Коментар");
		stateTxCommCol.setCellValueFactory(new PropertyValueFactory<>("txComm"));
		return stateTxCommCol;
	}
	
	public static TableColumn<StateP,Integer> getIdColumn(){
		TableColumn<StateP,Integer> stateIdCol = new TableColumn<>("№");
		stateIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		return stateIdCol;
	}
	
	public static TableView<StateP> initTableView(){
		TableView<StateP> table = new TableView<>();
		table.getColumns().addAll(getStateColumn(), getTxCommColumn(), getIdColumn());
		return table;
	}
	
	public static void setTableView(TableView<StateP> table, Machine model){ 	
		if(model != null){
			String inSym = "_" + model.main + model.add + model.no;
			TableColumn<StateP,String> stateIdModel = new TableColumn<>("№МТ");
			stateIdModel.setCellValueFactory(celData-> {
				int idModel = model.id;
				return new ReadOnlyStringWrapper(""+idModel);});
			table.getItems().clear();
			int sz =table.getColumns().size();
			//if (sz>3) table.getColumns().remove(3,sz);
			table.getColumns().remove(1,sz);
			for(int i=0; i<inSym.length();i++){
				TableColumn<StateP,String>stateWork = new TableColumn<>("" + inSym.charAt(i));
				final int j=i;            // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				stateWork.setCellValueFactory(celData->{
					StateP st = celData.getValue();
					int p = model.findCommand(st.getState());
					State si = (State)model.program.get(p);
					String go = si.getGoing().get(j);
					return new ReadOnlyStringWrapper(go ); //si.getGoing().get(i));
				});
				table.getColumns().add(stateWork);
			}
			table.getColumns().addAll(getTxCommColumn(), getIdColumn(),stateIdModel);
			ObservableList<StateP> newL = FXCollections.<StateP>observableArrayList();
			for(int j=0; j<model.program.size(); j++){
				StateP rp = new StateP((State)model.program.get(j));
				newL.add(rp);
				
			};
			int k = table.getColumns().size();
			final double p = 1.0/(k+3);//-0.001;
			for(int j=0; j<k;j++){
				if(j==k-3) table.getColumns().get(j).prefWidthProperty().bind(table.widthProperty().multiply(p*4));
				else {
					table.getColumns().get(j).prefWidthProperty().bind(table.widthProperty().multiply(p));
					table.getColumns().get(j).setStyle("-fx-alignment: center");
				}
				table.getColumns().get(j).setResizable(false);
			}
			
			//table.getColumns().get(1).setMaxWidth(50);
			//table.getColumns().get(1).setResizable(false);
			table.getItems().addAll(newL);
		}
	}
}
