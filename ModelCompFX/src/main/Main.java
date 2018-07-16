package main;


import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import java.util.*;

import db.*;
import gui.*;
import model.*;

public class Main extends Application {
	private DbAccess db;
	// Cтворює всі об"єкти, відкриває (ініціалізує) ресурси
	// Запускає gui (метод start: викликається в Application.launch)
	// Закінчує роботи (метод stop)
	
	public static void main(String[] args) {
		Application.launch(args);
	}
	
	@Override
	public void start(Stage stage){
		db = DbAccess.getDbAccess();
		FrMain fMain;
		AllModels env=null; 
		Scene scene;
		if (db.connectionDb("Model.db")) { 
			setParameter();
			// Cтворює всі об"єкти, відкриває (ініціалізує) ресурси
			fMain = new FrMain(stage, db); 
			env = AllModels.getAllModels(db, fMain); 
			//System.out.println("---Begin " );
			fMain.setEnv(db,env);
			scene = new Scene(fMain,800,500);
			stage.setScene(scene);
			stage.setTitle("Models of computations");
			stage.show(); 
		}
		else System.out.println("No connection to DB Model.db -- version Teacher..");
	}
	
	@Override
	public void stop(){
		db.disConnect(); 
		System.out.println("stop application");
	}	
	
	void setParameter(){
		Parameters p = this.getParameters();
		List<String> rawParams = p.getRaw();
		if (rawParams.size() > 0) {
			 if(rawParams.get(0).equals("Teacher")) Parameter.setRegime("teacher");
		}
	}
}
