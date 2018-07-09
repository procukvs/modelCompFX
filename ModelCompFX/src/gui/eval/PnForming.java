package gui.eval;

import javafx.scene.layout.VBox;
import model.AllModels;
import model.Post;

public class PnForming extends VBox {
	private AllModels env = null;
	private DgEval showWork;
	
	public PnForming(){
		
	}
	
	public void setParent(DgEval owner){      
		showWork = owner;                      
	}  

	public void show(AllModels env){
		this.env = env;
		//if (env.getType().equals("Post")) post = (Post)env.getModel(); else post = null;
		//tMessage.setText("");
	}
}
