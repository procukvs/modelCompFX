package gui.model;


import java.util.*;


import main.*;
import model.*;
import db.*;
import javafx.stage.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class DgInsert extends BorderPane {
	private String type = "Machine";
	private Model model = null;
	private AllModels env=null;
	
	private String nmModel = "";
	private String nmFunction = "";
	
	private Label title;
	private Label lModel;
	private ComboBox <String> cbModel;
	private Label lFunction;
	private ComboBox <String> cbFunction;
	private Button yes;
	private Button cancel;
	
	private ArrayList aSet; 
	private boolean workModel= false;
	
	public DgInsert(Stage owner) {
		//сформувати необхідні gui-елементи
		title = new Label("Вставити програму");
		title.setStyle("-fx-font: italic bold 16px 'Arial'"); 
			//title.setHorizontalAlignment(title.CENTER);
			//title.setFont(new Font("Courier",Font.BOLD|Font.ITALIC,14));
		
		lModel = new Label("Набір");
		cbModel = new ComboBox();
		lFunction = new Label("Функція");
		cbFunction= new ComboBox();
		
		yes = new Button("Так");
		cancel = new Button("Вийти");
		
		// формуємо розміщення
		HBox head = new HBox(); 
		head.getChildren().add(title);
		head.setAlignment(Pos.CENTER);
		//head.add(Box.createHorizontalStrut(25));
		//head.add(title);
		//head.add(Box.createHorizontalStrut(25));
		//-----------------------------
		HBox main = new HBox(); 
		main.getChildren().addAll(lModel,cbModel,lFunction,cbFunction);
		main.setAlignment(Pos.CENTER);
		//main.add(Box.createGlue());
		//main.add(lModel);
		//main.add(cbModel);
		//main.add(Box.createHorizontalStrut(15));
		//main.add(lFunction);
		//main.add(cbFunction);
		//main.add(Box.createGlue());
		//-----------------------------
		VBox	rMain = new VBox(); 
		rMain.getChildren().add(main);
		rMain.setAlignment(Pos.CENTER);
		//rMain.add(Box.createVerticalStrut(2));
		//rMain.add(main);
		//rMain.add(Box.createVerticalStrut(5));
									
		//---------------------
		HBox button = new HBox(5); 
		button.getChildren().addAll(yes,cancel);
		button.setAlignment(Pos.CENTER);
		//button.add(Box.createGlue());
		//button.add(yes);
		//button.add(Box.createHorizontalStrut(15));
		//button.add(cancel);
		//button.add(Box.createGlue());
		//---------------------
		setTop(head);
		setCenter(rMain);
		setBottom(button);
	
		// встановити слухачів !!!
		//cbModel.addActionListener(new LsModel());
		cbModel.setOnAction(e-> selectModel());
		//yes.addActionListener(new LsYes());
		yes.setOnAction(e->selectYes(owner));
		cancel.setOnAction(e-> {nmModel = ""; nmFunction = ""; owner.close();});
	}

	public void setInsert(String type, Model model, AllModels env, int num){
		String fstSet = "";
		nmModel = ""; nmFunction = "";
		this.type = type; this.model = model;
		this.env = env;
		lFunction.setVisible(type.equals("Recursive")||type.equals("Calculus"));
		lFunction.setManaged(type.equals("Recursive")||type.equals("Calculus"));
		cbFunction.setVisible(type.equals("Recursive")||type.equals("Calculus"));
		cbFunction.setManaged(type.equals("Recursive")||type.equals("Calculus"));
		switch(type){
		case "Computer" :  
			title.setText("Вставити після команди " + num + " програму");
			lModel.setText("Машина  ");
			break;
		case "Machine" :
			title.setText("Вставити програму");
			lModel.setText("Машина  ");
			break;
		case "Recursive" :  lFunction.setText("Функція  ");
			title.setText("Вставити функцію з іншого набору");
			lModel.setText("Набір  ");
			break;
		case "Calculus" :	lFunction.setText("Вираз  ");
			title.setText("Вставити після виразу " + num + " вираз з іншого набору");
			lModel.setText("Набір  ");
			break;
		default: break;
		}	
		
		workModel = false;
		aSet = env.getAllModel();
		cbModel.getItems().clear();
		for (int i = 0; i < aSet.size(); i++){
			String name1 = (String)aSet.get(i);
			if(!name1.equals(model.name)) {
				cbModel.getItems().add(name1);
				if(fstSet.isEmpty()) fstSet = name1;
			}
		}
		workModel = true;
		if (type.equals("Recursive")||type.equals("Calculus"))	setNames(fstSet);
		//pack();
	}
	
	
	public String getNmModel() { return nmModel;}
	public String getNmFunction() { return nmFunction;}
	
	private void selectModel(){
		if (workModel && (type.equals("Recursive")||type.equals("Calculus")))	
			setNames((String)cbModel.getValue()); //.valueProperty().getValue());
	}
	
	private void selectYes(Stage owner){
		nmModel = (String)cbModel.getValue();
		nmFunction = (String)cbFunction.getValue();
		owner.close();
	}
	/*
	class LsModel implements ActionListener  {
		public void actionPerformed(ActionEvent event){
			if (workModel && (type.equals("Recursive")||type.equals("Calculus")))	
				setNames((String)cbModel.getSelectedItem());
		}
	}
	
	class LsYes implements ActionListener  {
		public void actionPerformed(ActionEvent e) {
			nmModel = (String)cbModel.getSelectedItem();
			nmFunction = (String)cbFunction.getSelectedItem();
			hide();
		}	
	}
	class LsCancel implements ActionListener  {
		public void actionPerformed(ActionEvent e) {
			nmModel = ""; nmFunction = "";
			hide();
		}	
	}
*/
	private void setNames(String nmSet){
		 cbFunction.getItems().clear();
		 if ((nmSet!=null) && !nmSet.isEmpty()){
			 aSet = env.getAllNameFunction(nmSet);
			 for (int i = 0; i < aSet.size(); i++){
				 cbFunction.getItems().add((String)aSet.get(i));
			 }
		 }
	}
	
}
