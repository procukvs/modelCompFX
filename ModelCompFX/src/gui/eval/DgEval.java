package gui.eval;

import gui.model.*;
import gui.*;
import model.*;
import model.rec.*;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class DgEval extends BorderPane {
	//private Stage evWin;
	private Label lWhat;
	private PnDescription pDescription;
	private PnForming pForming;
	private PnValFunction pValFunction;
	private PnSelFunction pSelFunction;
	private PnEvalFunctionString pEval;
	private PnEvExpr pEvExpr;  
	PnStepsTable pStepsTable; 
	PnTree showTree;
	
	private VBox evalBox ;
	private VBox headBox; 
	private VBox endBox;
	
	Button eval;
	Button show;
	Button quit;
	
	//==========================
	private AllModels env=null;
	
	private String type = "Algorithm";
	private Model model = null;
	private Function f = null;
	private boolean isNumeric = true;
	private int rank = 2;
	private String main = "";

	public DgEval(Stage evWin){
		//this.evWin = evWin;
		//сформувати необхідні gui-елементи
		lWhat = new Label("Робота з нормальним алгоритмом");
		//lWhat.setFont(new Font("Courier",Font.BOLD|Font.ITALIC,16));
		pDescription = new PnDescription(false);
		pForming = new PnForming();       
		pForming.setParent(this);         
		pValFunction = new PnValFunction();
		pSelFunction = new PnSelFunction();  
		pSelFunction.setEnv(this);
		
		pEval = new PnEvalFunctionString();        
		pEval.setParent(this);        
		pEvExpr = new PnEvExpr();           
		pStepsTable = new PnStepsTable();
		showTree = new PnTree();
		
		eval = new Button("Виконати");
		show = new Button("Переглянути");
		quit = new Button("Вийти");
		
		// формуємо розміщення
		headBox = new VBox();
		headBox.getChildren().addAll(lWhat,pDescription);
		//-----------------------------
		evalBox = new VBox();
		evalBox.getChildren().addAll(pForming,pValFunction,
				pSelFunction,pEval,pEvExpr,pStepsTable,showTree);
		//-----------------------------
		HBox buttons = new HBox();
		buttons.getChildren().addAll(eval,show,quit);
		//---------------------
		endBox = new VBox();
		endBox.getChildren().add(buttons);
		//--------------------
		setTop(headBox);
		setCenter(evalBox);
		setBottom(endBox);
		
		pForming.setVisible(false);
		pSelFunction.setVisible(false);
		//setSize(560,684);		

		// встановити слухачів !!!	
		//eval.addActionListener(new LsEval());
		//show.addActionListener(new LsShow());
		//quit.addActionListener(new LsQuit());
		quit.setOnAction(e->evWin.close());
	}
	
	public void show(AllModels env){
		this.env=env;
		this.type = env.getType();
		this.model = env.getModel();
		if (showTree != null){
			showTree.setVisible(false);
			showTree.setManaged(false);
			//evalBox.remove(showTree);
		}
	   	lWhat.setText("Робота з " + Model.title(type, 12));		
	    //lWhat.setAlignmentX(CENTER_ALIGNMENT);
	    if(!type.equals("Recursive") && !type.equals("Calculus")){
	    	isNumeric = model.getIsNumeric();
	    	rank = model.getRank();
	    	main = model.getMain();
	    } 
	    pDescription.show(env);
	    
	    pForming.setVisible(type.equals("Post"));
	    pValFunction.setVisible(false);
	    pSelFunction.setVisible(type.equals("Recursive"));
	    pEval.setVisible(!type.equals("Post") && !type.equals("Calculus") && !type.equals("Recursive"));
	    pEvExpr.setVisible(type.equals("Calculus"));        // !!!!!!!!!!!!!!!!!!!!!
	    pStepsTable.setVisible(false);
	    
		eval.setText("Виконати");
		show.setText("Переглянути");  
	    switch(type){
		case "Post":
			//pForming.setModel(type, model);
			pForming.show(env);
	    	//pValFunction.setModel(type, model);
			pValFunction.show(env);
	      	eval.setText("Формувати дані");
	    	show.setText("Переглянути дані");
			break;
		case "Recursive":
			//pSelFunction.setModel((Recursive)model);
			pSelFunction.show(env);
			eval.setVisible(model.program.size()>0);
			break;
		case "Calculus":
			//pEvExpr.setModel(type, model);
			pEvExpr.show(env);
			break;
		default:
			//System.out.println("DgEval:show2 " + type + " " + model.id );
			//pEval.setModel(type, model);
			pEval.show(env);
	    }	
	   // show.setEnabled(false);
	   //pack();
	}
	
}
