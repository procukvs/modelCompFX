package gui.eval;

import javafx.scene.layout.*;
import model.AllModels;
import model.Model;

public class PnEvExpr extends VBox {
	private AllModels env = null;
	private Model model =null;
	private String type = "Calculus";
	
	public PnEvExpr(){
		
	}
	public void show(AllModels env){		
		this.model = env.getModel();
		this.type = env.getType();
		this.env = env;
		/*
		tNodef.setText("1000");	
		tResult.setText("");
		tStep.setText("");
		*/
	}
}
