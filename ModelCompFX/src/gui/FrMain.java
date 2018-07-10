package gui;





import db.*;
import gui.model.*;
import gui.system.*;
import main.*;
import model.*;

import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.application.*;
import java.util.*;
import javafx.scene.control.*;

public class FrMain extends BorderPane {
	private DbAccess db;
	private String type = "Algorithm";
	private Model model = null;
	private Label label; 
	private AllModels env=null;
	
	private PnModel pModel;
	private PnParTable pParTable;
	private PnModButtons pModButtons;
	private PnParButtons pParButtons;
	
	public FrMain(Stage stMain, DbAccess db){
		this.db = db;
	    //  сформувати необхідні gui-елементи 
		MenuBar  menuBar = new MenuBar();
		//Menu quit = new Menu("Вийти з програми");
		
		//quit.getItems().add(exitItem);
		// створюємо випадаюче меню, що містить звичайні елементи меню
		Menu mModel= new Menu("Модель обчислень   ");
		// елемент меню - команда
		MenuItem computer = new MenuItem(Model.title("Computer", 1));
		MenuItem machine = new MenuItem(Model.title("Machine", 1));  		//("Машини Тьюрінга");
		MenuItem algorithm = new MenuItem(Model.title("Algorithm", 1));	//(new AlgorithmAction()); // 
		MenuItem post = new MenuItem(Model.title("Post", 1));
		MenuItem rec = new MenuItem(Model.title("Recursive", 1));
		MenuItem lambda = new MenuItem(Model.title("Calculus", 1));
		mModel.getItems().addAll(computer, machine,algorithm,post,rec,lambda);
		//mModel.add(machine);mModel.add(algorithm);
		//mModel.add(post);mModel.add(rec);mModel.add(lambda);
		
		Menu mFile= new Menu("Системні дії   ");
		//MenuItem exit = new MenuItem("Вийти з програми");
		MenuItem input = new MenuItem("Введення моделей з файлу");
		MenuItem output = new MenuItem("Виведення моделей в файл");
		mFile.getItems().addAll(input,output); //(exit,input,output);
		//mFile.add(input);mFile.add(output);
		
		Menu mParameter = new Menu("Параметри    ");
		MenuItem state = new MenuItem("Стан параметрів");
		MenuItem parameters = new MenuItem("Встановлення параметрів");
		if(Parameter.getRegime().equals("teacher")){
			mFile.getItems().addAll(state,parameters);
			//mFile.add(state);	mFile.add(parameters);
		}
		
		//JMenu plafmenu = createPlafMenu(); // Створюємо меню
		//menuBar.add(plafmenu);     // Додаємо меню в полосу меню
		
		menuBar.getMenus().addAll(mModel,mFile); //, quit);
		
		label = new Label("Нормальні алгоритми Маркова");
		label.setStyle("-fx-font: italic bold 20px 'Arial'"); //("-fx-font-size: 20px; -fx-font-weight: bold");
		//label.setTextAlignment(TextAlignment.CENTER);
		//label.setAlignment(Pos.CENTER);
		//label.setHorizontalAlignment(label.CENTER);
		//label.setFont(new Font("Courier",Font.BOLD|Font.ITALIC,20));
		HBox menu = new HBox();
		Button exit = new Button("Вийти з програми");
		menu.getChildren().addAll(menuBar,exit);
		
		computer.setOnAction(e-> DialogWork.showAlert("Model", "Computer"));
		machine.setOnAction(e->env.set("Machine"));
		algorithm.setOnAction(e->env.set("Algorithm")); //(e-> DialogWork.showAlert("Model", "Algorithm"));//(e-> System.out.println("Algoritm"));
		rec.setOnAction(e-> env.set("Recursive"));
		
		
		exit.setOnAction(e-> Platform.exit());//System.out.println("quit")); //{db.disConnect(); Platform.exit();});
		
		
		//-------------------------------
		pModel = new PnModel(stMain);    
		pModel.setStyle("-fx-padding: 3;" +
				"-fx-border-style: solid inside; " + 
				"-fx-border-width: 1;" +
				"-fx-border-insets:1;" +
				//"-fx-border-radius: 0;" +  //solid inside;" +
				"-fx-border-color: blue;");
		//-------------------------------
		pParTable = new PnParTable (db);
		pModButtons = new PnModButtons(stMain); 
		pParButtons = new PnParButtons(db); 
		//===============================
		// розташуємо їх
		//-----------------------------
		VBox bxNorth = new VBox();
		bxNorth.getChildren().addAll(menu,label);  //(menuBar,label);     
		bxNorth.setStyle("-fx-alignment: center");
		VBox bxCenter = new VBox();
		bxCenter.getChildren().addAll(pModel,pParTable); 
		VBox bxSouth = new VBox();
		bxSouth.getChildren().addAll(pModButtons,pParButtons); 
		
		setTop(bxNorth);
		setCenter(bxCenter);
		setBottom(bxSouth);
		setVisiblePane("NoModel");
		
  	   	initial();
	}
	
	public void setEnv(DbAccess db, AllModels env){
		this.db = db; this.env = env;
		pModel.setEnv(this);                 
		pModButtons.setEnv(this, pModel.getDescription()); 
		pParButtons.setEnv(this, pParTable);
	}
	public void initial(){ 
		// створює початковий стан gui - нічого не показує 
		label.setVisible(false);
		pModel.setVisible(false);
		pParTable.setVisible(false);
		pModButtons.setVisible(false); pModButtons.setManaged(false);
		pParButtons.setVisible(false); pParButtons.setManaged(false);
	}	
	
	private void setVisiblePane( String type) {
		boolean close = type.equals("NoModel");
		boolean files = type.equals("Input") || type.equals("Output") 
				         || type.equals("State") || type.equals("Parameters");
		boolean model = type.equals("Computer") || type.equals("Algorithm")  || type.equals("Machine") 
		         || type.equals("Post") || type.equals("Recursive")  || type.equals("Calculus") ;
		label.setVisible(!close);
		pModel.setVisible(model);
		pParTable.setVisible(files);
		pModButtons.setVisible(model); pModButtons.setManaged(model);
		pParButtons.setVisible(files);pParButtons.setManaged(files);
	}
	
	public void show(AllModels env){
		this.env = env;
		type=env.getType(); model= env.getModel();
		
		//System.out.println("FrMain " + type);
		setVisiblePane(type);
		label.setText( Model.title(type, 1));
		pModel.show(env);
		pModButtons.show(env);
	}
	
	public void setModel(String type, int id) {
		/*
		//boolean files = type.equals("Input") || type.equals("Output");
		String text = Model.title(type, 1);
		this.type = type;
		//System.out.println("ShowModel start ..." + type + "  " + id + " " + this.type);
		setVisiblePane(type);
		switch (type){
		case "Input": case "Output":
		case "State": case "Parameters":	
			ArrayList fs = null;
			switch (type){
			case "Output": fs = db.getAllModel(); break;
			case "State": fs = db.getStateParameter(); break;
			case "Parameters": fs = db.getAllParameter(); break;	
			}	
			pParTable.showTableFiles(type, fs);
			pParButtons.setModel(type);
			model = null;
			break;
		case "NoModel": break;
		default:
			if (!type.equals(this.type)) {
				// set label !!!!!!!!
				if (text.isEmpty()) text = "Not realise " + type + " !";
			}
			if (id == 0) model = null; else model = db.getModel(type, id);
			pModel.setModel(type,model);
			pModButtons.setModel(type,model);
		}
		label.setText(text);
		*/
	/*		
	*/		
	}
}
