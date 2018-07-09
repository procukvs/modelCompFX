package gui.system;


import javax.swing.JButton;

import db.*;
import gui.FrMain;
import javafx.scene.layout.*;
import javafx.scene.control.*;

public class PnParButtons extends HBox {
	private DbAccess db;
	private FrMain showModels;
	private PnParTable showFiles;
	
	private Button file ;
	private Button quit;
	
	public PnParButtons(DbAccess db){	   
		this.db = db;	
		
		file = new Button("Файл ...");
		quit = new Button("Вийти");
		getChildren().addAll(file,quit);
	}
	public void setEnv(FrMain showMain,PnParTable showFiles){   // !!!!!!!!!! ref !!!!!!!!!!!!!!!!!!!!!!
		this.showModels = showMain;  
		this.showFiles = showFiles;  // !!!!!!!!!! ref !!!!!!!!!!!!!!!!!!!!!!
	}
}
