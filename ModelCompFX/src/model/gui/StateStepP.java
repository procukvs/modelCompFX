package model.gui;

import javafx.beans.property.*;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import java.util.*;

import model.*;


public class StateStepP {
	private SimpleIntegerProperty step=
			 new SimpleIntegerProperty(this, "step", 0);
	private SimpleStringProperty conf =
			 new SimpleStringProperty(this, "conf", null);
	private SimpleStringProperty reduceConf =
			 new SimpleStringProperty(this, "reduceConf", null);
	public StateStepP() {
		this(0, null, null);
	}
	public StateStepP(int step, String conf, String reduceConf) {
		this.step.set(step); this.conf.set(conf); this.reduceConf.set(reduceConf);
	}
	/* step Property */
	public int getStep() { return step.get();}
	public IntegerProperty stepProperty() {	return step; }
	public void setStep(int step) {stepProperty().set(step);}	
	
	/* conf Property */
	public String getConf() { return conf.get();}
	public StringProperty confProperty() {	return conf; }
	public void setConf(String conf) {confProperty().set(conf);}
	
	/* reduceConf Property */
	public String getReduceConf() { return reduceConf.get();}
	public StringProperty reduceConfProperty() {	return reduceConf; }
	public void setReduceConf(String reduceConf) {reduceConfProperty().set(reduceConf);}
	
	// build TableColumns
	public static TableColumn<StateStepP,Integer> getStepColumn(){
		TableColumn<StateStepP,Integer> stateStepCol = new TableColumn<>("Крок");
		stateStepCol.setCellValueFactory(new PropertyValueFactory<>("step"));
		return stateStepCol;
	}
	
	public static TableColumn<StateStepP,String> getConfColumn(){
		TableColumn<StateStepP,String> stateConfCol = new TableColumn<>("Конфігурація");
		stateConfCol.setCellValueFactory(new PropertyValueFactory<>("conf"));
		return stateConfCol;
	}
	
	public static TableColumn<StateStepP,String> getReduceConfColumn(){
		TableColumn<StateStepP,String> stateReduceConfCol = new TableColumn<>("Скорочене представлення конфігурації");
		stateReduceConfCol.setCellValueFactory(new PropertyValueFactory<>("reduceConf"));
		return stateReduceConfCol;
	}	
	
	public static TableView<StateStepP> initTableView(){
		TableView<StateStepP> table = new TableView<>();
		table.getColumns().addAll(getStepColumn(), getConfColumn(), getReduceConfColumn());
		return table;
	}
	
	public static void setTableView(TableView<StateStepP> table, ArrayList sl){ 
		table.getItems().clear();
		ObservableList<StateStepP> newL = FXCollections.<StateStepP>observableArrayList();
		for(int i=0; i<sl.size(); i++){
			ArrayList row = (ArrayList)sl.get(i);
			StateStepP st = new StateStepP((int)row.get(0),(String)row.get(1), (String)row.get(2));
			newL.add(st);
		}
		
		final double p = 1.0/775; // 75,350,350
		table.getColumns().get(0).prefWidthProperty().bind(table.widthProperty().multiply(p*70));
		table.getColumns().get(0).setResizable(false);
		table.getColumns().get(0).setStyle("-fx-alignment: center");
		table.getColumns().get(1).prefWidthProperty().bind(table.widthProperty().multiply(p*350));
		table.getColumns().get(1).setResizable(false);
		table.getColumns().get(2).prefWidthProperty().bind(table.widthProperty().multiply(p*350));
		table.getColumns().get(2).setResizable(false);
		/*
		for(int j=0; j<k;j++){
			if(j==k-3) table.getColumns().get(j).prefWidthProperty().bind(table.widthProperty().multiply(p*4));
			else {
				table.getColumns().get(j).prefWidthProperty().bind(table.widthProperty().multiply(p));
				table.getColumns().get(j).setStyle("-fx-alignment: center");
			}
			table.getColumns().get(j).setResizable(false);
		}*/
		table.getItems().addAll(newL);
	}
}
