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
import javafx.geometry.*;
import java.io.*;

public class PnModButtons extends VBox {
	
	private FileChooser fc = new FileChooser();
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
		nmFile.setPrefColumnCount(35);
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
		Scene scene = new Scene(dEval); //, 560,684);
		evWin.setScene(scene);
		
	
		HBox select = new HBox(5);
		select.setAlignment(Pos.BASELINE_LEFT);
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
		first.setOnAction(e->{env.getFirst(); selected=env.getPos();} );
		prev.setOnAction(e->{env.getPrev(); selected=env.getPos();} );
		next.setOnAction(e->{env.getNext(); selected=env.getPos();});
		last.setOnAction(e->{env.getLast(); selected=env.getPos();});
		
		file.setOnAction(e->setNameFile(stMain));
		output.setOnAction(e-> modelOutput());
		input.setOnAction(e->modelInput());
		
		test.setOnAction(e->lsTesting());
		add.setOnAction(e->env.newModel());
		addBase.setOnAction(e->{if (model!=null) env.newModelAs();});
		delete.setOnAction(e->deleteModel());
		work.setOnAction(e -> beginWork(evWin));
		quit.setOnAction(e->fMain.initial());
		//delete.setOnAction(e->forTesting());
		
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
	
	private void deleteModel(){
		if (model != null){ 
			String text = "Вилучити " + Model.title(type, 6) + " "+ model.name + " з номером " + model.id + " ?";
			DialogWork.showAlertConfirm("Delete?", text)
							.showAndWait()
			                .filter(res -> res == ButtonType.OK)
			                .ifPresent(res -> env.deleteModel());
		}
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
	
	private void setNameFile(Stage stMain){
		fc.setTitle("Select file");
		if(nmFile.getText().isEmpty())	fc.setInitialDirectory(new File("E:\\kma\\spring_2018"));
		else fc.setInitialDirectory(new File(nmFile.getText()));
		//System.out.println("Select file");
		//fc.setApproveButtonText("Вибрати файл");
		//fc.setFileSelectionMode(FileChooser.FILES_ONLY);
		try{
			File file = fc.showOpenDialog(stMain);
			//int res = fc.showOpenDialog(PnModButtons.this);
			//if (res==FileChooser.APPROVE_OPTION ) 
			if (file!=null)	nmFile.setText(file.getAbsolutePath()); //fc.getSelectedFile().getAbsolutePath());
		}
		catch (Exception e){
			String text = "Початковий директорій "  + fc.getInitialDirectory() + "\n" + e.getMessage();
			DialogWork.showAlertError(type, text).showAndWait();
			//System.out.println("Початковий директорій "  + fc.getInitialDirectory() + "\n" + e.getMessage());
		}
	}
	
	private void modelOutput(){
		String text = "Файл для виведення не вказано !";
		String name = nmFile.getText();
		if (!name.isEmpty()){
			if (model != null) {
				text=env.outputModel(name);
			} 
		}
		if ((model != null) ) DialogWork.showAlertError(type, text).showAndWait();// JOptionPane.showMessageDialog(PnModButtons.this,text);
	}	
	
	
	private void modelInput(){
		String text = "Файл для введення не вказано !";
		String name = nmFile.getText();
		Model model = null;
		if (!name.isEmpty()) {
			text = env.inputModel(name);
		}
		DialogWork.showAlertError(type, text).showAndWait();//JOptionPane.showMessageDialog(PnModButtons.this,text); // text);	
	}

	private void lsTesting(){
		if (model != null){
			if (pDescription.testAndSave()){
				String[] text= model.iswfModel();
				if (text != null) DialogWork.showAlertError(type, StringWork.transferToString(text)).showAndWait();// JOptionPane.showMessageDialog(PnModButtons.this,text);
			}	
		}		
	}
	

}
