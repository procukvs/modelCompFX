package gui.model;

import javafx.scene.layout.*;
import java.util.*;
import javafx.scene.control.*;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.*;
import javafx.collections.ObservableList;
import javafx.geometry.*;

import model.*;
import model.rec.*;
import model.gui.*;

public class PnComTable extends BorderPane {
	private AllModels env=null;
	private String type = "Algorithm";
	private Model model = null;
	private PnComButtons pComButtons;
	private Label label; 
		
	TableView<FunctionP> tableFunction;
	TableView<RuleP> tableRule;
	TableView<StateP> tableState;
	
	public PnComTable(){ 
		//t = new RuleP(new Rule());
		label = new Label("Підстановки алгоритму");
		// build all tables
		tableFunction=FunctionP.initTableView();
		tableRule=RuleP.initTableView(); 
		tableState=StateP.initTableView();
		HBox tables = new HBox();
		tables.getChildren().addAll(tableFunction, tableRule, tableState);
		tables.setStyle("-fx-alignment: center");
		setTop(label);
		setAlignment(label, Pos.CENTER);
		setCenter(tables);
		
		setWidthAllTables();
	}
	
	
	private void setWidthAllTables(){
		tableState.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableState.minWidthProperty().bind(this.widthProperty().subtract(10));
	}
	
	private void notShowAllTables(){
		tableFunction.setVisible(false); tableFunction.setManaged(false);
		tableState.setVisible(false);tableState.setManaged(false);
		tableState.getItems().clear();
		tableRule.setManaged(false);tableRule.setVisible(false);	
	}
	
	public void setEnv(PnComButtons buttons){
		//System.out.println(this.getWidth());
		//tableState.setMinWidth(500);
	   	this.pComButtons = buttons;
	    //int all = dbm.getRowCount();
		int all = 0;
	   	buttons.setSelection("  0:" + all); //dbm.getRowCount());
	}
	public void show(AllModels env){
		this.env = env;
		type = env.getType(); model = env.getModel();
		label.setText(Model.title(type, 4));
		onlyShowTable();
		//table.getSelectedRow() + 1);
		//if (model==null) System.out.println(" model=null");
		//else System.out.println("PnComTable: show="+env.getType()+"..model = "+model.id); // env.getPos());
	}
	
	private void onlyShowTable(){
		notShowAllTables();
		switch(type){
		case "Recursive": FunctionP.setTableView(tableFunction,(Recursive)model); 
			tableFunction.setManaged(true); tableFunction.setVisible(true);
			break; 
		case "Algorithm": RuleP.setTableView(tableRule,(Algorithm)model); 
			tableRule.setManaged(true);	tableRule.setVisible(true); break; 
		case "Machine": StateP.setTableView(tableState,(Machine)model);
			tableState.setManaged(true); tableState.setVisible(true); break;
		}
		pComButtons.setSelection( "0 : " + getCountRows());	
	}

	
	public void moveOnTable(String where){
		int beg=0,all=0;
		if (model!=null){
			switch(type){
			case "Recursive": break; 
			case "Algorithm": break; 
			case "Machine": 
				all=tableState.getItems().size();
				TableViewSelectionModel<StateP> tsm = tableState.getSelectionModel();
				switch (where){
				case "first": tsm.selectFirst(); break;
				case "prev": tsm.selectPrevious(); break;
				case "next": tsm.selectNext(); break;
				case "last": tsm.selectLast(); break;
				}
				beg=getSelectedRow();
				tableState.scrollTo(beg); break; 
			} 
		beg++; 	
		}		
		pComButtons.setSelection(beg + " : " + all);
	}
	
	public int getSelectedRow(){
		int row = -1;
		switch(type){
		case "Recursive": break;
		case "Algorithm": break;
		case "Machine":
			TableViewSelectionModel<StateP> tsm = tableState.getSelectionModel();
			if (!tsm.isEmpty()) row = tsm.getSelectedIndices().get(0);
			break;
		}
		return row;
	}
	
	public int getCountRows(){
		int all=0;
		if(model!=null){
			switch(type){
			case "Recursive": all=tableFunction.getItems().size(); break;
			case "Algorithm": all=tableRule.getItems().size(); break;
			case "Machine": all=tableState.getItems().size(); break;
			}
		}
		return all;
	}	
	
	
	
	
	public void showTable(boolean update, int selected){
		int all=0;
		//System.out.println("showTable update="+update + " selected=" + selected);
		if(update) {
			notShowAllTables();
			switch(type){
			case "Recursive": FunctionP.setTableView(tableFunction,(Recursive)model); 
				tableFunction.setManaged(true); tableFunction.setVisible(true);
				break; 
			case "Algorithm": RuleP.setTableView(tableRule,(Algorithm)model); 
				tableRule.setManaged(true);	tableRule.setVisible(true); break; 
			case "Machine": StateP.setTableView(tableState,(Machine)model);
				tableState.setManaged(true); tableState.setVisible(true); break;
			}
		}
		switch(type){
		case "Recursive": break; 
		case "Algorithm": break; 
		case "Machine": 
			if (model!=null){
				all=tableState.getItems().size();
				TableViewSelectionModel<StateP> tsm = tableState.getSelectionModel();
				if(selected > 0){
					
					//System.out.println("selected="+ selected + "tsm Empty?= " + tsm.isEmpty());
					//if (!tsm.isEmpty()) {
					//System.out.println("set ="+ (selected-1));
					tsm.select(selected-1);
					//if(selected>8)
					tableState.scrollTo(selected-1);
					//}
				} else if (selected == -1) // next
					    tsm.selectNext();
				else if (selected == -2) // prev
					{tsm.selectPrevious();
					tableState.scrollTo(getSelectedRow());}
			}
			break;  //visible(tableState,true); break; //
		}		
		pComButtons.setSelection(selected + " : " + all);//selection.setText(selected  + " : " + all);
	}
	
	public String selectedName() {
		// номер виділеної підстановки або 0 !!  State  !!!!
		int r = getSelectedRow();
		String name = "";
		if (r >= 0) {
					//name = (String)dbm.getValueAt(r,0);
			name = tableState.getItems().get(r).getState();
		}
		return name;
	}	
	
	
	/*
	public void showTable(boolean update, int selected){
		if (update) {
			ArrayList ds = null;
			if(model != null) ds = model.getDataSource();
			setTableStructure();
			if ((ds != null) && (ds.size() > 0)) dbm.setDataSource(ds);
		}
		int all = dbm.getRowCount();
		if (selected > 0){ 
			if (selected > all) selected = all;
			table.getSelectionModel().setSelectionInterval(selected - 1, selected - 1);
			pComButtons.setSelection(selected  + " : " + all);
		}	else  pComButtons.setSelection("0  :  " + all); 
	}
	*/
	private void visible(Node n, boolean bv){
		n.setVisible(false); n.setManaged(false);
	}
	
	
	public void showRow(boolean update, int row){
		showTable(update, row);
	}	
	
	// методи, що змінюють підстановки -- можливо переставляють і т. і.
	public int selectedRule() {
		// номер в БД виділеної підстановки/команди  або 0 !!
		int r = getSelectedRow();
		                  //int r = 0; //table.getSelectedRow();  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		int rule = 0;
		if (r >= 0) {
			int col = 0;
			switch (type) {
			case "Computer": col = 3; break;	
			case "Algorithm": col = 5; break; 
			case "Machine": 
				//col = model.getMain().length() + model.getAdd().length() + model.getNo().length() + 3;
				rule = tableState.getItems().get(r).getId();
				break;
			case "Post": col = 5; break;	
			case "Recursive": col = 6; break;	
			case "Calculus": col = 5; break;	
			}
			
			//rule = (Integer)dbm.getValueAt(r,col);
			//rule = 0; //(Integer)dbm.getValueAt(r,col);             !!!!!!!!!!!!!!!!!!!!!!!!!!
		}
		return rule;
		//return 1;  // for testing
	}	
	
	// selectedRow ==== numberSelectedRow  !!!!!!!!
	// номер виділеної команди для Algorithm / Post/ Computer
	/*
	public int numberSelectedRow(){
		int r = table.getSelectedRow();
		int rule = 0;
		if (r >= 0) {
			int col = 0;
			switch (type) {
			//case "Calculus": col = 4; break; 
			case "Machine": 
				col = model.getMain().length() + model.getAdd().length() + model.getNo().length() + 3;
				break;
			//case "Post": col = 1; break;
			//case "Computer": col = 1; break;
			case "Recursive": col = 6; break;	
			}
		rule = (Integer)dbm.getValueAt(r,col);		
		}
		return rule;
	}	
	*/
}
