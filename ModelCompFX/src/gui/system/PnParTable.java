package gui.system;

import db.DbAccess;
import javafx.scene.layout.BorderPane;

public class PnParTable extends BorderPane {
	private DbAccess db;
	
	public PnParTable(DbAccess  db){
		this.db = db; 		
	}
}
