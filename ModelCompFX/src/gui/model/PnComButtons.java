package gui.model;

import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;

import model.*;
import gui.*;
import gui.command.*;
import gui.eval.*;

public class PnComButtons extends VBox {
	private String type = "Algorithm";
	private AllModels env=null;
	private Model model = null;
	
	private PnComTable pComTable;
	private PnDescription pDescription;
	private FrMain fMain;
	private DgEdCommand dEdCommand;
	private Command command;
	
	private Label selection;
	private Button add;
	private Button up ;
	private Button down ;
	private Button rename;
	private Button insert ;
	private Button see;
	
	Button first;
	
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public PnComButtons(Stage stMain){                             
		//���������� �������� gui-�������� 
		selection = new Label("  0:0 " );  ///+ dbm.getRowCount());  
		first = new Button("|<");
		Button prev = new Button("<");
		Button next = new Button(">");
		Button last = new Button(">|");
		add = new Button("����");
		//addAs = new JButton("����� �� �����");
		Button edit = new Button("����������");
		Button delete = new Button("��������");
		up = new Button("���������� �����");
		down = new Button("���������� ����");
		rename = new Button("������������");
		insert = new Button("�������� ��������");
		see = new Button("�����������");	
		//=================================
		// ������� ���������
		//---------------------------
		HBox select = new HBox();
		select.getChildren().addAll(first,prev,selection,next,last);
		HBox buttons = new HBox();
		buttons.getChildren().addAll(add,edit,delete,see,up,down,rename,insert);
		getChildren().addAll(select,buttons);
		
		//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		Stage edCommand = new Stage(); 
		edCommand.initOwner(stMain);
		edCommand.initModality(Modality.WINDOW_MODAL);
		edCommand.initStyle(StageStyle.DECORATED);//(StageStyle.UTILITY);//(StageStyle.UNIFIED);//
		edCommand.setTitle("Command");
				
		dEdCommand = new DgEdCommand(edCommand);
		Scene scene = new Scene(dEdCommand, 560,684);
		edCommand.setScene(scene);
		
		
		
		// ���������� �������� !!!	
		first.setOnAction(e->pComTable.showTable(false, 1));
		add.setOnAction(e->addCommand(edCommand));
		edit.setOnAction(e->editCommand(edCommand));
		rename.setOnAction(e->testingData());
	}

	private void testingData(){
		
		System.out.println("Testing data");
		/*
		System.out.println("Size pComTable =" + pComTable.widthProperty().get());
		System.out.println("Size tableState =" + pComTable.tableState.widthProperty().get());
		System.out.println("column 4 minW = " + pComTable.tableState.getColumns().get(4).getMinWidth() +
				" prefW = "+ pComTable.tableState.getColumns().get(4).getPrefWidth() +
				" maxW = "+ pComTable.tableState.getColumns().get(4).getMaxWidth());
		System.out.println("Width min=" + pComTable.tableState.getMinWidth() + " perf=" + 
				pComTable.tableState.getPrefWidth() + " max=" + pComTable.tableState.getMaxWidth());
		*/
		System.out.println("first prev height=" + first.getPrefHeight());
	}
	public void setEnv(FrMain owner, PnDescription pDescription, PnComTable table){   
		fMain = owner;  
		this.pComTable = table;
		this.pDescription = pDescription;
	} 
	public void show(AllModels env){
		String text = "��������";
		this.env = env; 
		type = env.getType();
		model = env.getModel();
		boolean isVisible = type.equals("Machine");
		boolean rec = type.equals("Recursive") ;
		boolean comp = type.equals("Computer");
		boolean calc = type.equals("Calculus");
		add.setText(Model.title(type, 9));
		if(rec) text = "�������";
		if(calc) text = "�����";
		insert.setText("�������� "+ text);
		up.setVisible(!isVisible && !rec);
		down.setVisible(!isVisible && !rec);
		rename.setVisible(isVisible);
		insert.setVisible(isVisible || comp || rec || type.equals("Calculus"));
		see.setVisible(false);
		//System.out.println("PnCommButtons: show="+env.getType()+".."+env.getPos());
	}
	public void setSelection(String txt){
		selection.setText(txt);
	}
	
	
	private void addCommand(Stage edCommand){
		if (model != null) {
			if (pDescription.testAndSave()){
				Rule rule;
				int id = pComTable.selectedRule();
				int idNew;
				dEdCommand.setCommand("Add", type, model, id);
				//System.out.println("ShowCommandButton:LsAdd "  + type + " " + id);
				//dEdCommand.show();
				edCommand.showAndWait();
				command= dEdCommand.getCommand();
				if (command != null) { 
					//db.newCommand(type, model, command);
					//fMain.setModel(type, model.id);
					env.newCommand(command);
					pComTable.showRow(false,model.findCommand(command.getId())+1);
				}
			}
		}	
	}
	
	private void editCommand(Stage edCommand){
		if (model != null) {
			if (pDescription.testAndSave()){
				int row = pComTable.selectedRule();
				if (row > 0){
					dEdCommand.setCommand("Edit", type, model, row);
					//dEdCommand.show();
					edCommand.showAndWait();
					command= dEdCommand.getCommand();
					if (command != null) { 
						//db.editCommand(type, model, row, command);
						//fMain.setModel(type, model.id);
						env.editCommand(row, command);
					}
				}
			}
		}	
	}

}
