package model.gui;

import javafx.beans.property.*;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;

import model.*;

public class RuleP {
	private SimpleIntegerProperty num=
			 new SimpleIntegerProperty(this, "num", 0);
	private SimpleStringProperty sLeft =
			 new SimpleStringProperty(this, "sLeft", null);
	private SimpleStringProperty sRigth =
			 new SimpleStringProperty(this, "sRigth", null);
	private SimpleBooleanProperty isEnd =
			 new SimpleBooleanProperty(this, "isEnd", false);
	private SimpleStringProperty txComm =
			 new SimpleStringProperty(this, "txComm", null);
	private SimpleIntegerProperty id=
			 new SimpleIntegerProperty(this, "id", 0);;

	public RuleP() {
		this(0, null, null, false, null, 0);
	}

	public RuleP(int num, String sLeft, String sRigth, boolean isEnd, String txComm, int id) {
		this.num.set(num); this.sLeft.set(sLeft); this.sRigth.set(sRigth);
		this.isEnd.set(isEnd); this.txComm.set(txComm); this.id.set(id);
	}
	
	public RuleP(Rule r){
		this(r.getNum(),r.getsLeft(), r.getsRigth(),
			 r.getisEnd(),r.gettxComm(),r.getId());
	}

	/* num Property */
	public int getNum() { return num.get();}
	public IntegerProperty numProperty() {	return num; }
	public void setNum(int num) {numProperty().set(num);}
	
	/* sLeft Property */
	public String getSLeft() { return sLeft.get();}
	public StringProperty sLeftProperty() {	return sLeft; }
	public void setSLeft(String sLeft) {sLeftProperty().set(sLeft);}
	
	/* sRigth Property */
	public String getSRigth() { return sRigth.get();}
	public StringProperty sRigthProperty() {return sRigth; }
	public void setSRigth(String sRigth) {sRigthProperty().set(sRigth);}
	
	/* isEnd Property */
	public boolean getIsEnd() { return isEnd.get();}
	public BooleanProperty isEndProperty() {return isEnd; }
	public void setIsEnd(boolean isEnd) {isEndProperty().set(isEnd);}
	
	/* txComm Property */
	public String getTxComm() { return txComm.get();}
	public StringProperty txCommProperty() { return txComm; }
	public void setTxComm(String txComm) {txCommProperty().set(txComm);}	
	
	/* id Property */
	public int getId() { return id.get();}
	public IntegerProperty idProperty() {	return id; }
	public void setId(int id) {idProperty().set(id);}	
	
	// build TableColumns
	public static TableColumn<RuleP,Integer> getNumColumn(){
		TableColumn<RuleP,Integer> ruleNumCol = new TableColumn<>("№П");
		ruleNumCol.setCellValueFactory(new PropertyValueFactory<>("num"));
		return ruleNumCol;
	}
	
	public static TableColumn<RuleP,String> getSLeftColumn(){
		TableColumn<RuleP,String> ruleSLeftCol = new TableColumn<>("Ліва частина підстановки");
		ruleSLeftCol.setCellValueFactory(new PropertyValueFactory<>("sLeft"));
		return ruleSLeftCol;
	}
	
	public static TableColumn<RuleP,String> getSRigthColumn(){
		TableColumn<RuleP,String> ruleSRigthCol = new TableColumn<>("Права частина підстановки");
		ruleSRigthCol.setCellValueFactory(new PropertyValueFactory<>("sRigth"));
		return ruleSRigthCol;
	}	
	
	public static TableColumn<RuleP,Boolean> getIsEndColumn(){
		TableColumn<RuleP,Boolean> ruleIsEndCol = new TableColumn<>("Заключна?");
		ruleIsEndCol.setCellValueFactory(new PropertyValueFactory<>("isEnd"));
		return ruleIsEndCol;
	}
	
	public static TableColumn<RuleP,String> getTxCommColumn(){
		TableColumn<RuleP,String> ruleTxCommCol = new TableColumn<>("Коментар");
		ruleTxCommCol.setCellValueFactory(new PropertyValueFactory<>("txComm"));
		return ruleTxCommCol;
	}
	
	public static TableColumn<RuleP,Integer> getIdColumn(){
		TableColumn<RuleP,Integer> ruleIdCol = new TableColumn<>("№");
		ruleIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		return ruleIdCol;
	}
	
	public static TableView<RuleP> initTableView(){
		TableView<RuleP> table = new TableView<>();
		table.getColumns().addAll(getNumColumn(), getSLeftColumn(),
				                  getSRigthColumn(), getIsEndColumn(),
				                  getTxCommColumn(), getIdColumn());
		//table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.setColumnResizePolicy(TableView.UNCONSTRAINED_RESIZE_POLICY);
		return table;
	}
	
	public static void setTableView(TableView<RuleP> table, Algorithm model){ //TableView<RuleP> setTableView(TableView<RuleP> table, Algorithm model){
		//TableView<RuleP> table = initTableView();
	
		if(model != null){
			TableColumn<RuleP,String> ruleIdModel = new TableColumn<>("№Ал");
			ruleIdModel.setCellValueFactory(celData-> {
				int idModel = model.id;
				return new ReadOnlyStringWrapper(""+idModel);});
			table.getItems().clear();
			if (table.getColumns().size()>6) table.getColumns().remove(6);
			table.getColumns().add(ruleIdModel);
			ObservableList<RuleP> newL = FXCollections.<RuleP>observableArrayList();
			for(int i=0; i<model.program.size(); i++){
				RuleP rp = new RuleP((Rule)model.program.get(i));
				newL.add(rp);
				
			}
			table.getItems().addAll(newL);
		}
		//return table;
	}
}
