package gui.command;

import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.control.*;
import java.util.*;

import main.*;
import gui.*;


import model.*;


public class DgEdCommand extends BorderPane {
	private Label lWhat;
	private PnCommand pCommand;
	private Button test;
	private Button structure;
	private Button yes;
	private Button cancel;
	private Command command = null;
	private String type = "Algorithm";
	private Model model = null;
	private VBox mainBox ;
	
	public DgEdCommand(Stage owner){
		//сформувати необхідні gui-елементи
		lWhat = new Label("Edit");
		//lWhat.setHorizontalAlignment(lWhat.CENTER);
		//lWhat.setFont(new Font("Courier",Font.BOLD|Font.ITALIC,16));
		pCommand = new PnCommand();       
		pCommand.setEnv(this);         
		yes = new Button("Зберегти");
		test = new Button("Тестувати");
		structure = new Button("Структура");
		cancel = new Button("Не зберігати");
		
		// формуємо розміщення
		//-----------------------------
		mainBox = new VBox();
		mainBox.getChildren().add(pCommand);
		//---------------------------		
		HBox buttonBox = new HBox();
		buttonBox.getChildren().addAll(test, yes, cancel,structure);
		//---------------------
		setTop(lWhat);
		setCenter(mainBox);
		setBottom(buttonBox);
		
		// встановити слухачів !!!	
		//test.addActionListener(new LsTest());
		//yes.addActionListener(new LsYes());
		//cancel.addActionListener(new LsCancel());
		//structure.addActionListener(new LsStructure());
		yes.setOnAction(e->commandYes(owner));
		cancel.setOnAction(e-> {command=null; owner.close();});
	}
	
	public void setCommand(String what, String type, Model model,  int id){
		command = null;
		this.type = type; this.model = model;
		//System.out.println("ShowCommand:setCommand " + what + " " + type + " " + id);
		if (what == "Add") lWhat.setText(Model.title(type, 9)); else lWhat.setText("Редагувати");
	//	if (showTree != null) mainBox.remove(showTree);
		test.setVisible(type.equals("Recursive")||type.equals("Calculus"));
		structure.setVisible(type.equals("Recursive"));
	//	pCommand.setRule(type, model, id, what);
	//	pack();
	}
	public Command getCommand() { return command;}
	
	private void commandYes(Stage owner){
		ArrayList <String> mes = pCommand.testAllCommand();
		if (mes.size() == 0) {
			command = pCommand.getCommand();
			owner.close();
			//hide();
		} else DialogWork.showAlert("Command", "Errors in command !");
	
	}

}
