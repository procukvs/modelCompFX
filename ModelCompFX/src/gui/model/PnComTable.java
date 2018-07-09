package gui.model;

import javafx.scene.layout.*;
import java.util.*;
import javafx.scene.control.*;

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
		tableFunction=FunctionP.initTableView();
		tableRule=RuleP.initTableView(); // new TableView<>();
		tableState=StateP.initTableView();
		HBox tables = new HBox();
		tables.getChildren().addAll(tableFunction, tableRule, tableState);
		setTop(label);
		//setCenter(tableRule);
		setCenter(tables);
	}
	public void setEnv(PnComButtons buttons){
	   	this.pComButtons = buttons;
	    //int all = dbm.getRowCount();
		int all = 0;
	   	buttons.setSelection("  0:" + all); //dbm.getRowCount());
	}
	public void show(AllModels env){
		this.env = env;
		type = env.getType(); model = env.getModel();
		label.setText(Model.title(type, 4));
		                                          //border.setTitle(Model.title(type, 4));
		showTable(true, 5); //table.getSelectedRow() + 1);
		//if (model==null) System.out.println(" model=null");
		//else System.out.println("PnComTable: show="+env.getType()+"..model = "+model.id); // env.getPos());
	}
	
	public void showTable(boolean update, int selected){
		tableFunction.setVisible(false); tableRule.setVisible(false);
		tableState.setVisible(false);
		tableFunction.setManaged(false); tableRule.setManaged(false);
		tableState.setManaged(false);	
		switch(type){
		case "Recursive": FunctionP.setTableView(tableFunction,(Recursive)model); 
			tableFunction.setManaged(true); tableFunction.setVisible(true); break; 
		case "Algorithm": RuleP.setTableView(tableRule,(Algorithm)model); 
			tableRule.setManaged(true);	tableRule.setVisible(true); break; 
		case "Machine":   StateP.setTableView(tableState,(Machine)model); 
			tableState.setManaged(true); tableState.setVisible(true);break; 
		}		
		
		//System.out.println("PnComTable.showTable: update="+update+"..selected="+selected);
		/*
		if (update) {
			//ArrayList ds = null;
			
			//table.setItems(ComTableUtil.getRuleList((Algorithm)model));
			if(model != null) table = new TableView(ComTableUtil.getRuleList((Algorithm)model));//    ds = model.getDataSource();
			ComTableUtil.setTableColumns( table);
			System.out.println("TableColums.size="+   table.getColumns().size());
			setCenter(table);
			//setTableStructure();
			//if ((ds != null) && (ds.size() > 0)) dbm.setDataSource(ds);
		}
		//int all = dbm.getRowCount();
		  int all = 10;
		if (selected > 0){ 
			if (selected > all) selected = all;
			//table.getSelectionModel().setSelectionInterval(selected - 1, selected - 1);
			pComButtons.setSelection(selected  + " : " + all);//selection.setText(selected  + " : " + all);
		}	else  pComButtons.setSelection("0  :  " + all); //selection.setText("0  :  " + all); 
		*/
	}
	
	// методи, що змінюють підстановки -- можливо переставляють і т. і.
	public int selectedRule() {
		// номер в БД виділеної підстановки/команди  або 0 !!
		int r = 0; //table.getSelectedRow();                    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		int rule = 0;
		if (r >= 0) {
			int col = 0;
			switch (type) {
			case "Computer": col = 3; break;	
			case "Algorithm": col = 5; break; 
			case "Machine": 
				col = model.getMain().length() + model.getAdd().length() + model.getNo().length() + 3;
				break;
			case "Post": col = 5; break;	
			case "Recursive": col = 6; break;	
			case "Calculus": col = 5; break;	
			}
			rule = 0; //(Integer)dbm.getValueAt(r,col);             !!!!!!!!!!!!!!!!!!!!!!!!!!
		}
		//return rule;
		return 1;  // for testing
	}	
	
	public void showRow(boolean update, int row){
		showTable(update, row);
	}	

}
