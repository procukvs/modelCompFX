package gui.eval;


import java.util.*;


import javafx.scene.layout.*;
import javafx.stage.Stage;
import model.*;
import model.gui.*;
import model.rec.Recursive;
import javafx.scene.control.*;

public class PnStepsTable extends BorderPane {
	//ModelTable dbm;
	//===========================
	
	Label header;
	TableView table;
	CheckBox cInter;
	Label lInter;
	//ShowGroup showGroup;
	private int group=1;
	private boolean internal = true;
	ArrayList dataIn = new ArrayList();
	private String type = "Algorithm";
	private Algorithm algo = null;
	private Model model; 
	private ArrayList sl;
	
	TableView<StateStepP> tableStateStep;
	TableView<RuleStepP> tableRuleStep;
	
	public PnStepsTable(){
		
		//setLayout(new BorderLayout());
		String[][] inform = {
				{"Крок","I","N"},
				{"№ підст.","I","N"},
				{"До підстановки","S","N"},
				{"Пiсля підстановки","S","N"}
		};
	
		
		//dbm = new ModelTable(false);
		//table = new JTable(dbm);

		tableStateStep = StateStepP.initTableView();
		tableRuleStep = RuleStepP.initTableView();
		HBox tables = new HBox();
		tables.getChildren().addAll(tableStateStep, tableRuleStep);
		tables.setStyle("-fx-alignment: center");
	
		/*	
		dbm.setInitialModel(inform);
		JScrollPane forTable = new JScrollPane(table);
		
		TableColumn column = null;
		int[] widthCol = {100,75,300,300};
	    for (int i = 0; i < widthCol.length; i++) {
		        column.setPreferredWidth(widthCol[i]); 
	    }     
		 */
			
		//showGroup = new ShowGroup();
		//showGroup.setVisible(false);
		cInter = new CheckBox();
		lInter = new Label("Внутрішнє представлення");
			
		header = new Label("Послідовність підстановок алгоритму");
		header.setStyle("-fx-font: italic bold 12px 'Arial'"); 
		//header.setHorizontalAlignment(header.CENTER);
	
		HBox control = new HBox(); //Box.createHorizontalBox();
		control.getChildren().addAll(cInter,lInter);
		//control.add(showGroup);
		/*
		control.add(Box.createGlue());
		control.add(cInter);
		control.add(lInter);
		control.add(Box.createHorizontalStrut(10));
		control.add(Box.createHorizontalStrut(10));
		
		add(header,BorderLayout.NORTH);
		add(forTable,BorderLayout.CENTER);
		add(control,BorderLayout.SOUTH);
		*/
		HBox top = new HBox(header);
		top.setStyle("-fx-alignment: center");
		setTop(top);
		
		setCenter(tables);
		setBottom(control);
		//cInter.addActionListener(new TestInter());
		
		//showGroup.th.addActionListener(new TestTh());
		//showGroup.all.addActionListener(new TestAll());
		//showGroup.step.addActionListener(new TestStep());	
		
		setWidthAllTables();
	}
	
	private void setWidthAllTables(){
		tableStateStep.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableStateStep.minWidthProperty().bind(this.widthProperty().subtract(1));
		tableRuleStep.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableRuleStep.minWidthProperty().bind(this.widthProperty().subtract(10));
	}
	
	public void setShowSteps(String type,  Model model, ArrayList sl){
		ArrayList ds = null;
		int cnt = 0;
		boolean visible = false;
		this.model = model;
		this.type = type;
		this.sl = sl;
		group = 1;

		if(type.equals("Algorithm")  || type.equals("Post")) visible = model.getIsNumeric();
		cInter.setSelected(type.equals("Algorithm"));
		internal = type.equals("Algorithm");
		cInter.setVisible(visible); cInter.setManaged(visible);
		lInter.setVisible(visible); lInter.setManaged(visible);
		//showGroup.setVisible(type.equals("Post"));
		header.setText(Model.title(type, 13));
	
		ds = model.getStepSource(sl, type.equals("Algorithm"));
		//dbm.setDataSource(null);
		
		//dbm.setInitialModel(findInform(type));
		//dbm.fireTableStructureChanged();
		//setColumnWidth();
		if ((ds != null) && (ds.size() > 0)) onlyShowTable(ds); //dbm.setDataSource(ds);	
		if(type.equals("Post")) {
			if ((ds != null) && (ds.size() > 0)) cnt = ds.size();
			//showGroup.lCnt.setText(" " + cnt + " ");
		}
	}
	
	private void notShowAllTables(){
		//tableFunction.setVisible(false); tableFunction.setManaged(false);
		tableStateStep.setVisible(false);tableStateStep.setManaged(false);
		tableStateStep.getItems().clear();
		tableRuleStep.setVisible(false);tableRuleStep.setManaged(false);
		tableRuleStep.getItems().clear();
	}
	
	private void onlyShowTable(ArrayList sl){
		notShowAllTables();
		switch(type){
		case "Recursive": // FunctionP.setTableView(tableFunction,(Recursive)model); 
			//tableFunction.setManaged(true); tableFunction.setVisible(true);
			break; 
		case "Algorithm": RuleStepP.setTableView(tableRuleStep,sl); 
			tableRuleStep.setManaged(true);	tableRuleStep.setVisible(true); break; 
		case "Machine": StateStepP.setTableView(tableStateStep,sl);
			tableStateStep.setManaged(true); tableStateStep.setVisible(true); break;
		}
	}
}
