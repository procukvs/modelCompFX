package gui.eval;

import javafx.scene.layout.*;
import model.AllModels;
import model.Model;

public class PnValFunction extends VBox {
	private AllModels env=null;
	private Model model;
	private String type;
	
	public PnValFunction(){
		
	}
	
	public void show(AllModels env){	
		//  only for Post 
		this.model = env.getModel();
		this.type = env.getType();
		this.env=env;
		//boolean isPost = type.equals("Post");
		/*
		if(!type.equals("Recursive")){
			rank = model.getRank();
			main = model.getMain();
			
			for(int i = 0; i < 10; i++)	tParam[i].setVisible(false);
			//lParam.setVisible(model.getIsNumeric());
			for(int i = 0; i < rank; i++){
				tParam[i].setVisible(true);
				tParam[i].setText("");
			}
			tParam[0].requestFocus();
			lParam.setVisible(true);	
		}
		tResult.setText("");
		*/
	}
}
