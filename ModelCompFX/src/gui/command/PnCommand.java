package gui.command;



import java.util.*;


import gui.*;
import javafx.scene.layout.*;
import main.*;
import javafx.geometry.*;
import javafx.scene.control.*;

import model.*;
import model.calc.*;
import model.rec.*;

public class PnCommand extends VBox {
	private DgEdCommand showCommand;
	private String type = "Algorithm";
	private Model model;
	private Algorithm algo = null;
	private Machine mach = null;
	private Post post = null;
	private Recursive recur = null;
	private Computer comp = null;
	private Calculus calc = null;

	private int idCom = 0; 
	private String comAlfa;
	private String what = "";
		
	private Label lModel;
	private Label lId;
	private TextField state;     // стан або порядковий номер зовнішня ідентифікація == редагуємо при створенні !!!!!!!!
	private HBox mainBox;
	//-----------Algorithm + Post
	private CheckBox checkAxiom;
	private Label labelLeft;
	private TextField sLeft;
	private Label labelRigth; 
	private TextField sRigth;
	private CheckBox checkEnd;
	private VBox leftBox;
	private VBox rigthBox; 
	//----------------------Computer
	private Label lCod;
	private ComboBox<String> bCod;
	private Label lReg1;
	private TextField tReg1;
	private Label lReg2;
	private TextField tReg2;
	private Label lNext;
	private TextField tNext;
	private VBox codBox;
	private VBox reg1Box;
	private VBox reg2Box;
	private VBox nextBox;
	//-------------------for Machine--------------
	private VBox moveBox[];
	private Label lChar[];
	private TextField sMove[];
	//---------------
	private Label labelBody;
	private TextField sBody;
	private TextField sComm;
	private TextField lTesting;
	// ------ for Calculus ------
	// .. містить номер виразу в списку виразів
	// .. якщо новий то або останній в списку, або наступний за тим, на якому стояли !!
	// .. dont editible!!
	private int numLambda;
	private HBox bodyBox;
	
	public PnCommand(){
		
		this.setSpacing(5);
		//сформувати необхідні gui-елементи 
		//  common 
		//  nameBox 
		lModel = new Label("Algorithm   NNNNN    # xxx");
		lId = new Label("Rule   (yyy)");
		state = new TextField(); //(5)
		state.setPrefColumnCount(5);
		//state.setMaximumSize(new Dimension(50,20));
		state.setText("@a0");
		//  comBox
		Label labelComm = new Label("Коментар");
		sComm = new TextField(); //(50)
		sComm.setPrefColumnCount(40);
		//sComm.setMaximumSize(new Dimension(600,20));
		// mainBox 
		//-------- Algorithm + Post --------------
		checkAxiom = new CheckBox("Аксіома ?");
		labelLeft = new Label("Ліва частина підстановки");
		//labelLeft.setAlignmentX(labelLeft.CENTER_ALIGNMENT);                                 
		sLeft = new TextField(); //(30);
		sLeft.setPrefColumnCount(30);
						//state.setMaximumSize(new Dime
						//sLeft.setMaximumSize(new Dimension(400,20));
				                        //sLeft.setPreferredSize(new Dimension(100,20));
		labelRigth = new Label("Права частина підстановки");
		//labelRigth.setAlignmentX(labelRigth.CENTER_ALIGNMENT);  
		sRigth = new TextField(); //(30);
		sRigth.setPrefColumnCount(30);
						//sRigth.setMaximumSize(new Dimension(400,20));
				                         //sRigth.setPreferredSize(new Dimension(100,20));
		checkEnd = new CheckBox("Заключна підстановка ?");
		//------------ Machine--------------
		lChar = new Label[30];
		sMove = new TextField[30];
		String si;
		for (int i = 0; i < 30; i++){
			lChar[i] = new Label("'c'");
			//lChar[i].setAlignmentX(lChar[i].CENTER_ALIGNMENT);
			sMove[i] = new TextField(); //(5);
			sMove[i].setPrefColumnCount(5);
						//sMove[i].setMaximumSize(new Dimension(50,20));
			sMove[i].setText("@a0_>");
			si = ((Integer)i).toString();
			//sMove[i].setActionCommand(si);
		}		
		//------------ Computer ----------
		String[] codl = {"Z","S","T","J"};
		lCod = new Label("Код");
		//lCod.setAlignmentX(lCod.CENTER_ALIGNMENT);         
		bCod = new ComboBox();
		bCod.getItems().addAll("Z","S","T","J");
												//bCod.setPrototypeDisplayValue("11");
		//bCod.setBackground(Color.WHITE);
					//bCod.setMaximumSize(new Dimension(50,20));
		lReg1 = new Label("Регістр 1");
		//lReg1.setAlignmentX(lReg1.CENTER_ALIGNMENT);      
		tReg1 = new TextField();//(4);
		tReg1.setPrefColumnCount(4);
		tReg1.setText("1");
							//tReg1.setMaximumSize(new Dimension(30,20));
							//tReg1.setAlignmentX(lReg1.CENTER_ALIGNMENT);         
		lReg2 = new Label("Регістр 2");
		//lReg2.setAlignmentX(lReg2.CENTER_ALIGNMENT);    
		tReg2 = new TextField();//(4);
		tReg2.setPrefColumnCount(4);
							//tReg2.setMaximumSize(new Dimension(30,20));
		lNext = new Label("Наступна");
		//lNext.setAlignmentX(lNext.CENTER_ALIGNMENT);    
		tNext = new TextField();//(5);
		tNext.setPrefColumnCount(5);
							//tNext.setMaximumSize(new Dimension(40,20));		
		//------------- Recursive ---------
		labelBody = new Label("Тіло функції");
		sBody = new TextField();//(50);
		sBody.setPrefColumnCount(50);
					//sBody.setMaximumSize(new Dimension(600,20));
		lTesting = new TextField();//(50);
		lTesting.setPrefColumnCount(50);
					//lTesting.setMaximumSize(new Dimension(700,20));
		lTesting.setEditable(false);			
		// формуємо розміщення
		//------------------------------
		HBox nameBox = new HBox(5); //Box.createHorizontalBox();
		nameBox.getChildren().addAll(lModel,lId,state);
		nameBox.setAlignment(Pos.CENTER);
				//nameBox.add(Box.createHorizontalStrut(10));
				//nameBox.add(Box.createHorizontalStrut(3));
		//--------------------------------
		leftBox = new VBox(); //Box.createVerticalBox();
		leftBox.getChildren().addAll(labelLeft,sLeft);
				//leftBox.add(Box.createVerticalStrut(3));
		                                                //  leftBox.setBorder(new EtchedBorder());
		//leftBox.setPreferredSize(new Dimension(200,60));
		//-------------------------
		rigthBox = new VBox(); //Box.createVerticalBox();
		rigthBox.getChildren().addAll(labelRigth,sRigth);
		//rigthBox.add(Box.createVerticalStrut(3));
		                                                //  rigthBox.setBorder(new EtchedBorder());
	 	//rigthBox.setPreferredSize(new Dimension(200,60));
	 	//------------------------------
	 	mainBox = new HBox(5); //Box.createHorizontalBox();
	 	mainBox.setStyle("-fx-alignment: center");
	 	//--------------------------
		moveBox = new VBox[30];
		for (int i = 0; i < 30; i++){
			moveBox[i] = new VBox();// Box.createVerticalBox();
			moveBox[i].setStyle("-fx-alignment: center");
			moveBox[i].getChildren().addAll(lChar[i],sMove[i]);
			//moveBox[i].add(Box.createVerticalStrut(3));
													//moveBox[i].setPreferredSize(new Dimension(1000,60));
														//moveBox[i].setBorder(new EtchedBorder());
		}
	 	//-----------------------------
		codBox = new VBox();//Box.createVerticalBox();
		codBox.getChildren().addAll(lCod,bCod);
		//codBox.add(Box.createVerticalStrut(3));
		//codBox.setPreferredSize(new Dimension(100,60));
		reg1Box = new VBox(); //Box.createVerticalBox();
		reg1Box.getChildren().addAll(lReg1,tReg1);
		//reg1Box.add(Box.createVerticalStrut(3));
		//reg1Box.setPreferredSize(new Dimension(100,60));
		reg2Box = new VBox();  //Box.createVerticalBox();
		reg2Box.getChildren().addAll(lReg2,tReg2);
		//reg2Box.add(Box.createVerticalStrut(3));
		//reg2Box.setPreferredSize(new Dimension(100,60));
		nextBox = new VBox();   //Box.createVerticalBox();
		nextBox.getChildren().addAll(lNext,tNext);
		//nextBox.add(Box.createVerticalStrut(3));
		//nextBox.setPreferredSize(new Dimension(100,60));
		mainBox.getChildren().addAll(checkAxiom,leftBox,rigthBox,checkEnd);
		for (int i = 0; i < 30; i++) mainBox.getChildren().add(moveBox[i]);
		mainBox.getChildren().addAll(codBox,reg1Box,reg2Box,nextBox);
		//------------------------------
		bodyBox = new HBox();  //Box.createHorizontalBox();
		bodyBox.getChildren().addAll(labelBody, sBody);
		//------------------------------
		HBox comBox = new HBox(2);   //Box.createHorizontalBox();
		comBox.getChildren().addAll(labelComm,sComm);
		comBox.setAlignment(Pos.CENTER);
		//------------------------------------
		//setBorder(new EtchedBorder());
		getChildren().addAll(nameBox,mainBox,bodyBox,comBox);
		//add(Box.createGlue());
		//add(Box.createVerticalStrut(5));
		///add(Box.createVerticalStrut(3));
		//add(Box.createGlue());
		
		// встановити слухачів !!!
		state.setOnAction(e->testState());
		//checkAxiom.addActionListener(new LsCheckAxiom());
		//sLeft.addActionListener(new LssLeft());
		//sRigth.addActionListener(new LssRigth());
		for (int i = 0; i < 30; i++) {
			final int j = i;
			sMove[i].setOnAction(e-> testMove(j));  //testMove(myMove, nextMove, j)
		}
		//sBody.addActionListener(new LsBody());
		//bCod.addActionListener(new LsCod());
		//tReg1.addActionListener(new LsReg1());
		//tReg2.addActionListener(new LsReg2());
		//tNext.addActionListener(new LsNext());
		
	}
	
	public void setEnv(DgEdCommand owner){
		showCommand = owner;
	}
	public ArrayList <String> testAllCommand(){
		ArrayList <String> mes = new ArrayList <String> ();
		ArrayList <String> tmp;
		String text = "";
		switch(type){  
		case "Machine":
			int i = 0;
			if(what.equals("New")){
				text = mach.testState(state.getText());
				if(!text.isEmpty()) mes.add(text);
			}
			while ((i < comAlfa.length()-1) && (i < 29) ){
				text = mach.testMove(sMove[i].getText());
				if(!text.isEmpty()) mes.add(text);
				i++;
			};
			break;
		case "Computer":
			                                String cod = "S"; //(String)bCod.getSelectedItem();
			//String cod = (String)bCod.getSelectedItem();
			text = comp.testPart(cod, 1, tReg1.getText());
			if(!text.isEmpty()) mes.add(text);
			text = comp.testPart(cod, 2, tReg2.getText());
			if(!text.isEmpty()) mes.add(text);
			text = comp.testPart(cod, 3, tNext.getText());
			if(!text.isEmpty()) mes.add(text);
			break;
		case "Algorithm":
			if(what.equals("New")){
				text = algo.iswfNumString(state.getText());
				if(!text.isEmpty()) mes.add(text);
			}	
			text = StringWork.isAlfa(comAlfa, sLeft.getText());
			if (!text.isEmpty()){
				text = "Ліва частина підстановки містить символи " + 
							text + " що не входять до спільного алфавіту " + comAlfa + " !";
				mes.add(text);
			}
			text = StringWork.isAlfa(comAlfa, sRigth.getText());
			if (!text.isEmpty()){
				text = "Права частина підстановки містить символи " + 
						text + " що не входять до спільного алфавіту " + comAlfa + " !";
				mes.add(text);
			}
			break;
		case "Post":
			Derive com = new Derive(new Integer(state.getText()), 
									checkAxiom.isSelected(),sLeft.getText(),sRigth.getText(),sComm.getText(),idCom);
			mes = com.iswfCommand(post);
			break;	
		case "Calculus":
			String nameLam =state.getText();
			if (!StringWork.isIdentifer(nameLam))
				mes.add("Імя виразу " + nameLam + " не Ідентифікатор !");
			break;
		}
		return mes;
	}
	
	public Command getCommand(){
		Command com = null;
		String st = state.getText();
		int num = model.program.size()+1;
		switch(type){
		case "Algorithm":
			if (StringWork.isPosNumber(st)) num = new Integer(st);  
			com = new Rule(num, sLeft.getText(),sRigth.getText(), checkEnd.isSelected(),sComm.getText(), idCom);
			break;
		case "Machine":	
			String  move = "";
			String ch = "";
			ArrayList <String> going = new ArrayList<String>();
			for(int i =0; i < comAlfa.length(); i++) {
				ch = comAlfa.substring(i,i+1);
				if (i < 30) {
					move = sMove[i].getText();
					if (!move.isEmpty()){
						if(st.equals(move.substring(0,3)) && ch.equals(move.substring(3,4)) && move.substring(4,5).equals(".")) move = ""; 
					}
				}
				else move = "";
				going.add(move);
			}
			com = new State(st,idCom,going,sComm.getText());
			break;
		case "Post":
			if (StringWork.isPosNumber(st)) num = new Integer(st);  
			com = new Derive(num,checkAxiom.isSelected(),sLeft.getText(),sRigth.getText(),sComm.getText(),idCom);
			//System.out.println(com.output());
			break;
		case "Recursive":
			com = new Function (idCom, st, sBody.getText(),sComm.getText());
			break;
		case "Calculus":
			com = new LambdaDecl (idCom, numLambda, st, sBody.getText(),sComm.getText());
			break;
		case "Computer":
			int reg2 = 0;
			int next = 0;
			        String cod = "S";
			//String cod = (String)bCod.getSelectedItem();
			if (!cod.equals("Z") && !cod.equals("S")) {
				reg2 = new Integer(tReg2.getText());
				if (cod.equals("J"))next = new Integer(tNext.getText());  
			}
			com = new Instruction (new Integer(st), cod, new Integer(tReg1.getText()), reg2, next, sComm.getText(), idCom); break;	
		}
		return com;
	}
	
	public void setRule(String type, Model model, int id, String what) {
		//System.out.println("PnCommand: typ= "  + type + " id= " + id + " what= " + what);
		boolean isEdit = what.equals("Edit");
		boolean isFunctional = type.equals("Recursive") || type.equals("Calculus");
		this.model = model;
		this.type = type;
		this.what = what;
		
		Object obj = this.getParent();
																	//System.out.println("Parent = "+obj.getClass().getSimpleName());
		
		lModel.setText(Model.title(type, 2)+ " " + model.name + "     (" + model.id + ")");
		idCom = (isEdit ? id : model.findMaxNumber()+1);
		lId.setText(Model.title(type, 10) + "  (" + idCom + ")");	
		//state.setMaximumSize(new Dimension(50,20));
		//state.enable(!isEdit);
		
		//---------------------
		checkAxiom.setManaged(false); checkAxiom.setVisible(false);
		leftBox.setManaged(false); leftBox.setVisible(false);
		rigthBox.setManaged(false); rigthBox.setVisible(false);
		checkEnd.setManaged(false); checkEnd.setVisible(false);
		for (int i = 0; i < 30; i++){
			moveBox[i].setManaged(false); moveBox[i].setVisible(false);
		}
		codBox.setManaged(false); codBox.setVisible(false);
		reg1Box.setManaged(false); reg1Box.setVisible(false);
		reg2Box.setManaged(false); reg2Box.setVisible(false);
		nextBox.setManaged(false); nextBox.setVisible(false);
	
		mainBox.setManaged(!isFunctional); mainBox.setVisible(!isFunctional);    //(!type.equals("Recursive"));
		bodyBox.setManaged(isFunctional); bodyBox.setVisible(isFunctional);     //(type.equals("Recursive"));
		lTesting.setManaged(isFunctional); lTesting.setVisible(isFunctional);    //(type.equals("Recursive"));
		//-------------------
		
		switch(type){
		case "Algorithm":
			Rule ruleA;
			algo = (Algorithm)model;
			leftBox.setManaged(true); leftBox.setVisible(true);
			rigthBox.setManaged(true); rigthBox.setVisible(true);
			checkEnd.setManaged(true); checkEnd.setVisible(true);
			//mainBox.add(leftBox);
			//mainBox.add(rigthBox);
			//mainBox.add(checkEnd);
			
			
			if (id == 0) ruleA = new Rule(model.program.size(),"","",false,"====",idCom);
			else ruleA = (Rule)model.program.get( model.findCommand(id));
			if (isEdit) state.setText("" + ruleA.getNum());
			else state.setText("" + (ruleA.getNum()+1));
			
			labelLeft.setText("Ліва частина підстановки");
			sLeft.setText(ruleA.getsLeft());
			labelRigth.setText("Права частина підстановки");
			sRigth.setText(ruleA.getsRigth());
			checkEnd.setSelected(ruleA.getisEnd());
			sComm.setText(ruleA.gettxComm());
			comAlfa = StringWork.isAlfa("",algo.main);
			comAlfa = comAlfa + StringWork.isAlfa(comAlfa,algo.add);
		
			if (isEdit) sLeft.requestFocus(); else state.requestFocus();
			break;
		case "Post":
			Derive rule;
			post = (Post)model;
												//System.out.println("setRule: model.program.size() = " + model.program.size()); 	
			if (id == 0) rule = new Derive(model.program.size(),false,"","","====",idCom);
			else rule =  (Derive)model.program.get( model.findCommand(id)); 
			if (isEdit) state.setText("" + rule.getNum());
			else state.setText("" + (rule.getNum()+1));
						
			checkAxiom.setSelected(rule.getisAxiom());
			labelLeft.setText("Ліва частина правила");
			sLeft.setText(rule.getsLeft());
			if(rule.getisAxiom()) labelRigth.setText("Аксіома");
			else labelRigth.setText("Права частина правила");
			sRigth.setText(rule.getsRigth());
			
			checkAxiom.setManaged(true); checkAxiom.setVisible(true);
			//mainBox.add(checkAxiom);
			if (!rule.getisAxiom()) {
				//mainBox.add(leftBox);
				leftBox.setManaged(true); leftBox.setVisible(true);
			}
			rigthBox.setManaged(true); rigthBox.setVisible(true);
			//mainBox.add(rigthBox);
			
			sComm.setText(rule.gettxComm());
			comAlfa = StringWork.isAlfa("",post.main);
			comAlfa = comAlfa + StringWork.isAlfa(comAlfa,post.add);
	
			if (isEdit) {
				if (rule.getisAxiom()) sRigth.requestFocus(); else sLeft.requestFocus();
			}
			else state.requestFocus();
			break;		
		case "Machine":
			State st;
			mach = (Machine)model;
			if (id == 0) st = mach.emptyState("@a0");
			else st = (State)model.program.get(mach.findCommand(id));
			sComm.setText(st.gettxComm());
			if (isEdit) state.setText(st.getState()); else 	state.setText(mach.newState());
			comAlfa = "_" + model.getMain() + model.getAdd() + model.getNo();
			for (int i = 0; (i < comAlfa.length()) && (i < 30); i++){
				lChar[i].setText("'" + comAlfa.substring(i,i+1) + "'");
				sMove[i].setText(st.getGoing().get(i));
				moveBox[i].setManaged(true); moveBox[i].setVisible(true);
				//mainBox.add(moveBox[i]);
			}
	
			if (isEdit) sMove[0].requestFocus(); else state.requestFocus();
			break;
		case "Computer":
			Instruction inst;
			String cod;
			boolean bZS = true;
			boolean bJ = false;
			comp = (Computer)model;
			codBox.setManaged(true); codBox.setVisible(true);
			reg1Box.setManaged(true); reg1Box.setVisible(true);
			reg2Box.setManaged(true); reg2Box.setVisible(true);
			nextBox.setManaged(true); nextBox.setVisible(true);
			//mainBox.add(codBox);
			//mainBox.add(reg1Box);
			//mainBox.add(reg2Box);
			//mainBox.add(nextBox);
		
			if (id == 0) inst = new Instruction(model.program.size(),"Z",1,0,0,"====",idCom);
			else inst =  (Instruction)model.program.get( model.findCommand(id));
			if (isEdit) state.setText("" + inst.getNum());
			else state.setText("" + (inst.getNum()+1));
			
			cod = inst.getCod();
			//bCod.setSelectedItem(cod);
			bZS = cod.equals("Z") ||cod.equals("S");
			bJ = cod.equals("J"); 
			tReg1.setText("" + inst.getReg1());
			if (!bZS) tReg2.setText("" + inst.getReg2());
			if (bJ) tNext.setText("" + inst.getNext());
			sComm.setText(inst.gettxComm());
		
			if (isEdit) bCod.requestFocus(); else state.requestFocus();			
			break;
		case "Recursive":
			Function f;
			recur = (Recursive)model;
			//state.setMaximumSize(new Dimension(100,20));
			if (id==0) f = recur.newFunction(null);
			else f = (Function)model.program.get(recur.findCommand(id));
			if (isEdit) state.setText(f.getName());
			else state.setText(id==0?f.getName():recur.findNameCommand(f.getName()));
			
			sBody.setText(f.gettxBody());
			sComm.setText(f.gettxComm());
			lTesting.setText(f.geterrorText());
			if (isEdit)	sBody.requestFocus(); else state.requestFocus();
			break;
		case "Calculus":
			LambdaDecl ld;
												//System.out.println("ShowRule:setRule " + what + " " + type + " " + id);
			calc = (Calculus)model;
												//System.out.println("ShowRule:setRule1 " + what + " " + type + " " + id);
			//state.setMaximumSize(new Dimension(100,20));
			if (id==0) ld = calc.newLambdaDecl(null);
			else ld = (LambdaDecl)model.program.get(calc.findCommand(id));
												//System.out.println("ShowRule:setRule2 " + what + " " + type + " " + id);
			if (isEdit) state.setText(ld.getName());
			else state.setText(id==0?ld.getName():calc.findNameCommand(ld.getName()));
			numLambda = ld.getNum();
			if (!isEdit && (id != 0)) numLambda ++;
			
			
			sBody.setText(ld.gettxBody());
			sComm.setText(ld.gettxComm());
			lTesting.setText(ld.geterrorText());
			if (isEdit)	sBody.requestFocus(); else state.requestFocus();
			break;		
		}
	}

	// for listener
	public void testState(){
		ArrayList <String> mes;
		String text;
		//System.out.println("ShowRule:LsState " + type + " " + what + " " + state.getText());
		switch (type){
		case "Machine":
			text = mach.testState(state.getText());
			if (!text.isEmpty()) DialogWork.showAlertError("State", text).showAndWait();
					//JOptionPane.showMessageDialog(PnCommand.this, text);	
			else  sMove[0].requestFocus();
			break;
		case "Recursive":
			text = recur.testNameCommand(state.getText());                        //recur.testName(state.getText());
			if (!text.isEmpty())DialogWork.showAlertError(type, text).showAndWait();// JOptionPane.showMessageDialog(PnCommand.this, text);	
			else  sBody.requestFocus();
			break;	
		case "Calculus":
			text= "";
			if (what.equals("Add")){
				text = calc.testNameCommand(state.getText());
			//	System.out.println("ShowRule text " + text);
			}
			if (!text.isEmpty()) DialogWork.showAlertError(type, text).showAndWait();//JOptionPane.showMessageDialog(PnCommand.this, text);	
			else  sBody.requestFocus();
			break;	
		case "Post":
			mes = post.iswfNum(state.getText());
			if (mes.size() == 0) {
				if (checkAxiom.isSelected()) sRigth.requestFocus(); else sLeft.requestFocus();
			} else DialogWork.showAlertError(type, StringWork.transferToString(mes)).showAndWait();//JOptionPane.showMessageDialog(PnCommand.this,  StringWork.transferToArray(mes));
			break;
		case "Computer":
			mes = comp.iswfNum(state.getText());
			if (mes.size() == 0) bCod.requestFocus();
			else DialogWork.showAlertError(type, StringWork.transferToString(mes)).showAndWait();//JOptionPane.showMessageDialog(PnCommand.this,  StringWork.transferToArray(mes));
			break;
		case "Algorithm":
			mes = algo.iswfNum(state.getText());
			if (mes.size() == 0)  sLeft.requestFocus();
			else DialogWork.showAlertError(type, StringWork.transferToString(mes)).showAndWait();//JOptionPane.showMessageDialog(PnCommand.this,  StringWork.transferToArray(mes));
			break;		
		}
		
	}			
	
	public void testMove(int index){ 
		String text = mach.testMove(sMove[index].getText()); 
		//System.out.println("move="+sMove[index].getText()+ " text=" +text );
		if (text.isEmpty()) {
			if ((index < comAlfa.length()-1) && (index < 29)) sMove[index + 1].requestFocus();
			else sComm.requestFocus();
		}
		else DialogWork.showAlertError(type, text).showAndWait();
	}

}
