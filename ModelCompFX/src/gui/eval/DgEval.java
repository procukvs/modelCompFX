package gui.eval;

import gui.model.*;


import java.util.*;


import gui.*;
import model.*;
import model.calc.*;
import model.rec.*;

import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import main.*;

public class DgEval extends BorderPane {
	private Stage evWin;
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
	private ArrayList sl = null;

	public DgEval(Stage evWin){
		this.evWin = evWin;
		//сформувати необхідні gui-елементи
		lWhat = new Label("Робота з нормальним алгоритмом");
		lWhat.setStyle("-fx-font: italic bold 16px 'Arial'"); 
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
		headBox.setAlignment(Pos.CENTER);
		//-----------------------------
		evalBox = new VBox();
		evalBox.getChildren().addAll(pForming,pValFunction,
				pSelFunction,pEval,pEvExpr,pStepsTable,showTree);
		//-----------------------------
		HBox buttons = new HBox(5);
		buttons.getChildren().addAll(eval,show,quit);
		buttons.setAlignment(Pos.CENTER);
		//---------------------
		endBox = new VBox();
		endBox.getChildren().add(buttons);
		//--------------------
		setTop(headBox);
		setCenter(evalBox);
		setBottom(endBox);
		
		pForming.setVisible(false); pForming.setManaged(false);
		pSelFunction.setVisible(false); pSelFunction.setManaged(false);
		//setSize(560,684);		

		// встановити слухачів !!!	
		//eval.addActionListener(new LsEval());
		//show.addActionListener(new LsShow());
		//quit.addActionListener(new LsQuit());
		eval.setOnAction(e-> beginEval()); //DialogWork.showAlertInform("DgEval", "Eval").showAndWait());
		show.setOnAction(e-> beginShow()); //DialogWork.showAlertInform("DgEval", "Show").showAndWait());
		quit.setOnAction(e->evWin.close());
		
		//tableState.minWidthProperty().bind(this.widthProperty().subtract(10));
		//evWin.minHeightProperty().bind(pStepsTable.heightProperty().add(this.widthProperty()));
	}
	
	
	private void setHeight(){
		evWin.setMinHeight(this.heightProperty().get()+ pStepsTable.heightProperty().get());
	}
	public void show(AllModels env){
		this.env=env;
		this.type = env.getType();
		this.model = env.getModel();
		if (showTree != null){
			showTree.setVisible(false);	showTree.setManaged(false);
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
	    
	    pForming.setVisible(type.equals("Post")); pForming.setManaged(type.equals("Post"));
	    pValFunction.setVisible(false); pValFunction.setManaged(false);
	    pSelFunction.setVisible(type.equals("Recursive")); pSelFunction.setManaged(type.equals("Recursive"));
	    pEval.setVisible(!type.equals("Post") && !type.equals("Calculus") && !type.equals("Recursive"));
	    pEval.setManaged(!type.equals("Post") && !type.equals("Calculus") && !type.equals("Recursive"));
	    pEvExpr.setVisible(type.equals("Calculus")); pEvExpr.setManaged(type.equals("Calculus"));
	    pStepsTable.setVisible(false); pStepsTable.setManaged(false);
	    
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
			eval.setVisible(model.program.size()>0); eval.setManaged(model.program.size()>0);
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
	    show.setVisible(false); show.setManaged(false);
	   // show.setEnabled(false);
	   //pack();
	}
	
	private void beginEval(){
		String text = "";
		int nodef=0 ;
		       // after showEval change definition !!!!!!! 

		int[] arg ;
		//show.setEnabled(false);
		show.setVisible(false); show.setManaged(false);
		pStepsTable.setVisible(false); pStepsTable.setManaged(false);
		pEval.setVisible(!type.equals("Recursive"));
		switch(type){
		case "Post":
			/*
			pEval.setVisible(false); pEval.setManaged(false);
			//pack();
			sl = pForming.formingData();
			if (sl != null){
				pValFunction.setVisible(model.getIsNumeric());
				pack();
				show.setEnabled(true);	
			}; */ break;
		case "Recursive": /*
			if (showTree != null)evalBox.remove(showTree);
			pack();
			text = pSelFunction.testArguments();
			//System.out.println("LsEval:  "+ type + ".." );
			if (text.isEmpty()){
				nodef = pSelFunction.getNodef();
				arg = (int[])pSelFunction.getArguments();
				text = ((Recursive) model).evalFunction(pSelFunction.getFunction(), arg, nodef);
				pSelFunction.setResult(text, ((Recursive) model).getAllStep());
				show.setEnabled(true);
			} 
			else pSelFunction.setResult(text, -1);
			//System.out.println("DEval:LsEval1: " ); 
			*/break;
		case "Calculus":
			/*
			LamStep res;
			int step;
			pEval.setVisible(false);
			show.setEnabled(false);
			pack();
			text = pEvExpr.getLambda();
			nodef = pEvExpr .getNodef();
			if (!text.isEmpty()){
				sl = ((Calculus)model).eval(text, nodef, pEvExpr.getTest());
				step = sl.size()- 1;
				res = (LamStep)sl.get(step);
				if (res.getWhat().equals("Err")){
					pEvExpr.setResult(res.getName(), ""+(nodef+1));
				} else{
					Lambda rLd = ((Calculus)model).compressFull(res.getTerm()[0]);
					pEvExpr.setResult(rLd.toStringShort(new LamNames(), 0), res.getName());
					show.setEnabled(pEvExpr.getTest());
				}
			} else {
				pEvExpr.setResult("Введіть ламбда-вираз для обрахування !", "");
			}
			*/break;
		default:
			text = pEval.testArguments();
			if (text.isEmpty()){
				nodef = pEval.getNodef();
				if(type.equals("Computer")) 
					sl = model.eval((int[])pEval.getArguments(), nodef);
				else sl = model.eval((String)pEval.getArguments(), nodef);
				//System.out.println("ShowWork: Eval sl " + ((sl==null)?"Null": ""+sl.size() ));
				pEval.setResult(model.takeResult(sl, nodef), model.takeCountStep(sl,nodef));
				//show.setEnabled(true);
				show.setVisible(true); show.setManaged(true);
				//System.out.println("ShowWork: Eval sl2 " + ((sl==null)?"Null": ""+sl.size() ));
			}
			else pEval.setResult(text, -1);
			pStepsTable.setVisible(false); pStepsTable.setManaged(false);
			evWin.sizeToScene();
			//pack();
		}
		//System.out.println("DEval:LsEval2: " );
	}
	
	private void beginShow(){
		//show.setEnabled(false);
		show.setVisible(false); show.setManaged(false);
		if (type.equals("Recursive")){
			/*
			int[] arg = (int[])pSelFunction.getArguments();
			int nodef = pSelFunction.getNodef();
			f = pSelFunction.getFunction();
			String result = (((Recursive) model).getNoUndef()?""+((Recursive) model).getResult():"...");
			String sRoot = f.getName() + "<" + StringWork.argString(arg) + ">=" + result;
			DefaultMutableTreeNode root = new DefaultMutableTreeNode(sRoot,true);
			((Recursive) model).testFunction(f, arg, nodef, root);
			if (showTree != null)evalBox.remove(showTree);
			showTree = new PnTree(root);
			evalBox.add(showTree);
			pack();*/
		} else {
			//System.out.println("ShowWork: showsteps Show " + ((sl==null)?"Null": ""+sl.size() ) );
			pStepsTable.setShowSteps(type, model, sl);
			pStepsTable.setVisible(true); pStepsTable.setManaged(true);
			//pStepsTable.showGroup.th.setSelected(true);
			evWin.sizeToScene();
			//pack();
		}		
	}
	
	
}
