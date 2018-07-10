package gui.model;

import main.*;
import model.*;
import gui.*;
import gui.eval.*;

import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.*;
import javafx.stage.*;

public class PnModButtons extends VBox {
	private AllModels env=null;	
	private FrMain fMain;
	private DgEval dEval; 
	//private Stage evWin;
	
	private PnDescription pDescription;
	private String type = "Algorithm";
	private Model model = null;	
	
	private Label selection;
	private TextField nmFile;
	
	private Button test;
	private Button add;
	private Button addBase;
	private Button delete;
	private Button work ;
	private Button quit;
	private Button output;
	private Button input ;
	private Label section;
	private Label version;

	private int selected = 0;
	private int r = 0;	
	
	public PnModButtons(Stage stMain){	         	
		//сформувати необхідні gui-елементи 
		selection = new Label("  0:0  ");  	
		Button first = new Button("|<");
		Button prev = new Button("<");
		Button next = new Button(">");
		Button last = new Button(">|");
		version = new Label(Parameter.getVersion()+ " ttt");  
		section = new Label("Test"); 
		test = new Button("Перевірка");
		add = new Button("Новий");
		addBase = new Button("Новий на основі");	
		delete = new Button("Вилучити");
		Button file = new Button("Файл ...");
		nmFile = new TextField();
		work = new Button("Рoбота з алгоритмом");
		output = new Button("Вивести алгоритм в файл");
		input = new Button("Ввести алгоритм з файлу");
		quit = new Button("Вийти");
		//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		Stage evWin = new Stage(); 
		evWin.initOwner(stMain);
		evWin.initModality(Modality.WINDOW_MODAL);
		evWin.initStyle(StageStyle.DECORATED);//(StageStyle.UTILITY);//(StageStyle.UNIFIED);//
		evWin.setTitle("Work");
		
		dEval = new DgEval(evWin);
		Scene scene = new Scene(dEval, 560,684);
		evWin.setScene(scene);
		
	
		HBox select = new HBox(5);
		TextField empty = new TextField();
		empty.setVisible(false);
		select.getChildren().addAll(first,prev,selection, next,last,empty);
		if(Parameter.getRegime().equals("teacher")){
			select.getChildren().add(section);
			section.setText(Parameter.getSection());
		} 
		select.getChildren().add(version);
		HBox.setHgrow(empty,Priority.ALWAYS);
		HBox fileBox = new HBox(5);
		fileBox.getChildren().addAll(file, nmFile,output,input);	
		HBox buttons = new HBox(5);
		
	
		buttons.getChildren().addAll(test,add,addBase,delete,work,quit);
		
		getChildren().addAll(select,fileBox,buttons);
		
		// встановити слухачів !!!	
		first.setOnAction(e->{env.getFirst(); selected=env.getPos();}
		                 );
		prev.setOnAction(e->{env.getPrev(); selected=env.getPos();}
		                );
		next.setOnAction(e->{env.getNext(); selected=env.getPos();});
		last.setOnAction(e->{env.getLast(); selected=env.getPos();});
		
		//delete.setOnAction(e->forTesting());
		work.setOnAction(e -> beginWork(evWin));
		/*
		{
			if (model != null){
				if (pDescription.testAndSave()){
					String[] text= model.iswfModel();
					if (text != null) {
						String ms = "";
						for(int i=0;i<text.length;i++) ms +=text[i]+"\n";
						Alert alert = new Alert(AlertType.ERROR, ms, ButtonType.CLOSE);
						alert.showAndWait();
					}
					else {
						dEval.show(env);
						evWin.showAndWait();
					}
				}	
			}			
		});
		
		*/
	}
	
	
	
	public void setEnv(FrMain fMain, PnDescription pDescription){   // !!!!!!!!!! ref !!!!!!!!!!!!!!!!!!!!!!
		this.fMain = fMain;
		this.pDescription = pDescription;
		//dEval.setEnv(pDescription);
	}
	public void show(AllModels env){
		this.env=env;
		this.type = env.getType();
		this.model = env.getModel();
		//if (model == null) selected = 0; else selected = db.getOrder(type, model.name);
		selected=env.getPos(); r=env.getCntModel();
		//r = db.getModelCount(type);
    	//System.out.println("setModel  " + selected + "  cnt = " + r);
    	//if (selected > r) selected = r;
    	selection.setText(selected + " : " + r);
    	if(Parameter.getRegime().equals("teacher")) section.setText(Parameter.getSection());
		// встановити надписи на кнопках
		add.setText(Model.title(type, 7));
		addBase.setText(Model.title(type, 7) + " на основі");
		work.setText("Рoбота з " + Model.title(type, 5));
		output.setText("Вивести " + Model.title(type, 6) + " в файл" );
		input.setText("Ввести " + Model.title(type, 6) + " з файлу");
		test.setVisible(!type.equals("Computer"));
		//System.out.println("PnModButtons: show="+env.getType()+".."+env.getPos());
	}
	
	private void beginWork(Stage evWin){
		if (model != null){
			if (pDescription.testAndSave()){
				String[] text= model.iswfModel();
				if (text != null) {
					String ms = "";
					for(int i=0;i<text.length;i++) ms +=text[i]+"\n";
					Alert alert = new Alert(AlertType.ERROR, ms, ButtonType.CLOSE);
					alert.showAndWait();
				}
				else {	
					dEval.show(env);
					evWin.showAndWait();
				}
			}	
		}					
	}
}
