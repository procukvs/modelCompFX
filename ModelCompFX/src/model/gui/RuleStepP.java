package model.gui;

import java.util.*;

import javafx.beans.property.*;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;

public class RuleStepP {
	private SimpleIntegerProperty step=
			 new SimpleIntegerProperty(this, "step", 0);
	private SimpleIntegerProperty num=
			 new SimpleIntegerProperty(this, "num", 0);
	private SimpleStringProperty before =
			 new SimpleStringProperty(this, "befor", null);
	private SimpleStringProperty after =
			 new SimpleStringProperty(this, "after", null);
	public RuleStepP() {
		this(0, 0, null, null);
	}
	public RuleStepP(int step, int num, String before, String after) {
		this.step.set(step); this.num.set(num);
		this.before.set(before); this.after.set(after);
	}
	
	/* step Property */
	public int getStep() { return step.get();}
	public IntegerProperty stepProperty() {	return step; }
	public void setStep(int step) {stepProperty().set(step);}	
	
	/* num Property */
	public int getNum() { return num.get();}
	public IntegerProperty numProperty() {	return num; }
	public void setNum(int num) {numProperty().set(num);}	
	
	/* before Property */
	public String getBefore() { return before.get();}
	public StringProperty beforeProperty() {	return before; }
	public void setBefore(String before) {beforeProperty().set(before);}
	
	/* after Property */
	public String getAfter() { return after.get();}
	public StringProperty afterProperty() {	return after; }
	public void setAfter(String after) {afterProperty().set(after);}
	
	// build TableColumns
	public static TableColumn<RuleStepP,Integer> getStepColumn(){
		TableColumn<RuleStepP,Integer> ruleStepCol = new TableColumn<>("Крок");
		ruleStepCol.setCellValueFactory(new PropertyValueFactory<>("step"));
		return ruleStepCol;
	}

	public static TableColumn<RuleStepP,Integer> getNumColumn(){
		TableColumn<RuleStepP,Integer> ruleNumCol = new TableColumn<>("Крок");
		ruleNumCol.setCellValueFactory(new PropertyValueFactory<>("num"));
		return ruleNumCol;
	}
	
	public static TableColumn<RuleStepP,String> getBeforeColumn(){
		TableColumn<RuleStepP,String> ruleBeforeCol = new TableColumn<>("Конфігурація");
		ruleBeforeCol.setCellValueFactory(new PropertyValueFactory<>("before"));
		return ruleBeforeCol;
	}
	
	public static TableColumn<RuleStepP,String> getAfterColumn(){
		TableColumn<RuleStepP,String> ruleAfterCol = new TableColumn<>("Скорочене представлення конфігурації");
		ruleAfterCol.setCellValueFactory(new PropertyValueFactory<>("after"));
		return ruleAfterCol;
	}	
	
	public static TableView<RuleStepP> initTableView(){
		TableView<RuleStepP> table = new TableView<>();
		table.getColumns().addAll(getStepColumn(), getNumColumn(), getBeforeColumn(), getAfterColumn());
		return table;
	}	
	
	public static void setTableView(TableView<RuleStepP> table, ArrayList sl){ 
		table.getItems().clear();
		ObservableList<RuleStepP> newL = FXCollections.<RuleStepP>observableArrayList();
		for(int i=0; i<sl.size(); i++){
			ArrayList row = (ArrayList)sl.get(i);
			RuleStepP st = new RuleStepP((int)row.get(0),(int)row.get(1),(String)row.get(2), (String)row.get(3));
			newL.add(st);
		}
		
		final double p = 1.0/875; // 100, 75,350,350
		table.getColumns().get(0).prefWidthProperty().bind(table.widthProperty().multiply(p*100));
		table.getColumns().get(0).setResizable(false);
		table.getColumns().get(0).setStyle("-fx-alignment: center");
		table.getColumns().get(1).prefWidthProperty().bind(table.widthProperty().multiply(p*75));
		table.getColumns().get(1).setResizable(false);
		table.getColumns().get(1).setStyle("-fx-alignment: center");
		table.getColumns().get(2).prefWidthProperty().bind(table.widthProperty().multiply(p*350));
		table.getColumns().get(2).setResizable(false);
		table.getColumns().get(3).prefWidthProperty().bind(table.widthProperty().multiply(p*350));
		table.getColumns().get(3).setResizable(false);
		
		table.getItems().addAll(newL);
	}
}
