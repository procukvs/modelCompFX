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
	//private FrMain fMain;                         =========================================
	//private static AllModels env=null;            =========================================
	// C������ �� ��"����, ������� (��������) �������
	// ������� gui
	// ������ ������ (����� andWork)
	
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
			// C������ �� ��"����, ������� (��������) �������
			fMain = new FrMain(stage, db); 
			env = AllModels.getAllModels(db, fMain); 
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
	/*                =========================================
	Main(){
		db = DbAccess.getDbAccess();
		//====================
		
	    if (db.connectionDb("Model.db")) { 
	    	if (Parameters.getRegime().equals("teacher")) db.setParameters();
	    	System.out.println("Forming GUI-- " + "version " + Parameters.getVersion() + ": " + Parameters.getRegime() + "..");
	    	// C������ �� ��"����, ������� (��������) �������
			fMain = new FrMain(db); 
			env = AllModels.getAllModels(db, fMain); 
			fMain.setEnv(db,env);
			// ������� gui
			fMain.setVisible(true);
	    } 
	    else System.out.println("No connection to DB Model.db -- version Teacher..");
	}
	*/
	
	/*
	public static void endWork(){
	   // ������� �� ������� � ������ ������
	   	//db.disConnect();  
        //System.exit(0);
		System.out.println("stop application");
	}
	*/
	
	/*                         =========================================
	public static void main(String[] args) {
		if (args.length > 0) {
			 if(args[0].equals("Teacher")) Parameters.setRegime("teacher");
		}
		// ³��� ��������� � ������ ������� ����.
		SwingUtilities.invokeLater(new Runnable() {
			public void run(){
				// ���������� �� ������ ���������� ������� !!!!
				try {
					UIManager.setLookAndFeel(new com.incors.plaf.kunststoff.KunststoffLookAndFeel());
				}
				catch (Exception ex) { System.err.println(ex);}
				new Main();
			}
		});
	}
	*/

	

}
