package gui.eval;

import javafx.scene.layout.*;
import model.AllModels;
import model.Model;

public class PnEvalFunctionString extends VBox {
	private DgEval owner;
	private AllModels env=null;
	private Model model;
	private String type;
	
	public PnEvalFunctionString(){
		
	}
	
	public void setParent(DgEval owner){       //     !!!!!!ref!!!!!!!!!!!!!!!!!!
		this.owner = owner;                        //     !!!!!!ref!!!!!!!!!!!!!!!!!!
	}  
	
	public void show(AllModels env){	
		//Algorithm algo = (Algorithm) model;
		this.model = env.getModel();
		this.type = env.getType();
		this.env =env;
		//System.out.println("PnEvalFunctionString:SetModel " + type + " " + model.id );
		boolean isPost = type.equals("Post");
		/*
			rank = model.getRank();
			main = model.getMain();
			
			for(int i = 0; i < 10; i++)	tParam[i].setVisible(false);
			lInit.setVisible(!model.getIsNumeric());
			tInit.setVisible(!model.getIsNumeric());
			lParam.setVisible(model.getIsNumeric());
			if(model.getIsNumeric()){
				for(int i = 0; i < rank; i++){
					tParam[i].setVisible(true);
					tParam[i].setText("");
					//	if (i < model.rank-1) tParam[i].setNextFocusableComponent(tParam[i+1]);
				}
				//tParam[0].setFocusable(true);
				tParam[0].requestFocus();
			} else {
				tInit.setText(""); 
				tInit.requestFocus();
			}
			
		tNodef.setText("1000");	
		tResult.setText("");
		tStep.setText("");
		lNodef.setVisible(!isPost);
		tNodef.setVisible(!isPost);
		//lFunction.setVisible(isPost);
		lStep.setVisible(!isPost);
		tStep.setVisible(!isPost);
		*/
		//see.setVisible(isPost); 
	}
}
