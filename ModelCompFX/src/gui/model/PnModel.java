package gui.model;

import gui.*;
import model.*;

import javafx.scene.layout.*;
import javafx.stage.*;

public class PnModel extends BorderPane {
	private FrMain fMain;
	private AllModels env=null;
	
	private PnDescription pDescription;
	private PnComTable pComTable;
	private PnComButtons pComButtons;
	
	public PnModel(Stage stMain){	// !!!!!!!!!!!!!ref!!!!!!!!!!!!!!!
		//сформувати необхідні gui-елементи 
		pDescription = new PnDescription(true);
		pComTable = new PnComTable();
		pComTable.setStyle("-fx-padding: 3;" +
				"-fx-border-style: solid inside;" +
				"-fx-border-width: 1;" +
				"-fx-border-insets: 1;" +
				//"-fx-border-radius: 5;" +
				"-fx-border-color: blue;");
		pComButtons = new PnComButtons(stMain); // 
		
		setTop(pDescription);
		setCenter(pComTable);
		setBottom(pComButtons);
	}
	
	public void setEnv(FrMain owner){     // !!!!!!!!!!!!!ref!!!!!!!!!!!!!!!
		fMain = owner;                        // !!!!!!!!!!!!!ref!!!!!!!!!!!!!!!
		pDescription.setEnv(fMain);       // !!!!!!!!!!!!!ref!!!!!!!!!!!!!!!
		pComTable.setEnv(pComButtons);             // !!!!!!!!!!!!!ref!!!!!!!!!!!!!!!
		pComButtons.setEnv(fMain,pDescription,  pComTable); 
	}  
	public PnDescription getDescription(){return pDescription;}
	
	public void show(AllModels env){
		this.env = env;
		pDescription.show(env);
		pComTable.show(env);
		pComButtons.show(env);
	}	
}
