package gui.command;

import java.util.*;
import javafx.scene.layout.*;

import model.*;

public class PnCommand extends VBox {
	private DgEdCommand showCommand;
	
	public PnCommand(){
		
	}
	
	public void setEnv(DgEdCommand owner){
		showCommand = owner;
	}
	public ArrayList <String> testAllCommand(){
		ArrayList <String> mes = new ArrayList <String> ();
		return mes;
	}
	
	public Command getCommand(){
		Command com = null;
		return com;
	}

}
