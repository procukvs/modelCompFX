package gui.eval;

import javafx.scene.layout.*;
import model.AllModels;
import model.Model;
import model.rec.Function;
import model.rec.Recursive;

public class PnSelFunction extends VBox {
	private AllModels env = null;
	private DgEval dEval;	
	public PnSelFunction(){
		
	}
	
	public void setEnv(DgEval dEval){
		this.dEval = dEval;
	}

	public void show(AllModels env){	
		int cnt=0;
		Model model = env.getModel();
		this.env=env;
		/*
		isData = false;
		rec = (Recursive)model;
		cnt = rec.program.size();
		cbName.removeAllItems();
		for (int i = 0; i < cnt; i++){
			f = (Function)rec.program.get(i);
			cbName.addItem(f.getName());
		}
		cbName.setEnabled(cnt>0);
		isData = true;
		if (cnt==0){
			lName.setText("Функція ... ");
			rank = 0;
			tRank.setText("" + rank); 
			cConst.setSelected(false);
			tBody.setText(""); 
			tCom.setText("");
			for(int i = 0; i < 10; i++)tParam[i].setVisible(false);
		} else {
			f = (Function)rec.program.get(cbName.getSelectedIndex());
			setFunction(f); 
		}
	
		tNodef.setText("1000");	
		tResult.setText("");
		tStep.setText("");
		*/
	}
}
