package gui.eval;


import gui.*;
import model.*;
import main.*;

import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;




public class PnEvalFunctionString extends VBox {
	private DgEval owner;
	private Model model;
	private String type;
	private AllModels env=null;
	private Label lParam;
	private Label lInit;
	//private JLabel lFunction;
	TextField tParam[];
	TextField tInit;
	Label lNodef;
	TextField tNodef;
	TextField tResult;
	Label lStep;
	TextField tStep;
	
	private String main = "";
	private int rank = 0;
	
	public PnEvalFunctionString(){
		//сформувати необхідні gui-елементи 
		lParam = new Label("Аргументи");
		lInit = new Label("Початкове слово");
		tParam = new TextField[10];//JTextField(30);
		String si;
		for (int i = 0; i < 10; i++){
			tParam[i] = new TextField();
			tParam[i].setPrefColumnCount(2);
					//tParam[i].setMaximumSize(new Dimension(10,20));
			si = ((Integer)i).toString();
			//tParam[i].setActionCommand(si);
		}
		TextField dummy1 = new TextField();
		dummy1.setPrefColumnCount(5);
		dummy1.setVisible(false);
		tInit = new TextField();
		tInit.setPrefColumnCount(30);
			//tInit.setMaximumSize(new Dimension(100,20));	
		lNodef = new Label("Невизначено");
		
		tNodef = new TextField();
		tNodef.setPrefColumnCount(4);
			//tNodef.setMaximumSize(new Dimension(40,20));
		Label lResult = new Label("Результат");
		tResult = new TextField();
		tResult.setPrefColumnCount(30);
			//tResult.setMaximumSize(new Dimension(100,20));
		tResult.setEditable(false);
		lStep = new Label("Виконано кроків");
		tStep = new TextField();
		tStep.setPrefColumnCount(4);
			//tStep.setMaximumSize(new Dimension(40,20));
		tStep.setEditable(false);
	
		//=================================
		// формуємо розміщення
		//setBorder(new EtchedBorder());      !!!!!!!!!!!!!!!!!!!!!!!!!!!
		setStyle("-fx-padding: 3;" +
				"-fx-border-style: solid inside;" +
				"-fx-border-width: 1;" +
				"-fx-border-insets: 1;" +
				"-fx-border-color: blue;");
		setSpacing(5);
		//---------------------------
		HBox init = new HBox(3); //Box.createHorizontalBox();
		init.getChildren().add(lParam);
		for(int i = 0; i < 10; i++)	init.getChildren().add(tParam[i]);
		init.getChildren().addAll(lInit, tInit, dummy1, lNodef, tNodef);
		init.setAlignment(Pos.BASELINE_CENTER);
		/*
		init.add(Box.createGlue());
		init.add(lParam);
		for(int i = 0; i < 10; i++)	init.add(tParam[i]);
		init.add(lInit);
		init.add(tInit);
		init.add(Box.createGlue());
		init.add(lNodef);
		init.add(tNodef);
		init.add(Box.createGlue());
		*/
		HBox result = new HBox(3);  //Box.createHorizontalBox();
		result.getChildren().addAll(lResult,tResult,lStep,tStep);
		getChildren().addAll(init,result);
		result.setAlignment(Pos.BASELINE_CENTER);
		/*
		result.add(Box.createGlue());
		result.add(lResult);
		result.add(tResult);
		result.add(Box.createGlue());
		result.add(lStep);
		result.add(tStep);
		result.add(Box.createGlue());
		//------------------------------------
		add(Box.createGlue());
		add(Box.createVerticalStrut(5));
		add(init);	
		add(Box.createVerticalStrut(3));
		add(result);	
		add(Box.createVerticalStrut(5));
		add(Box.createGlue()); 
		*/
		// встановити слухачів !!!
		for (int i = 0; i < 10; i++) {
			final int j = i;
			tParam[i].setOnAction(e-> tParam(j)); //LtParam()
		}
		//for (int i = 0; i < 10; i++) tParam[i].addActionListener(new LtParam());
		tInit.setOnAction(e-> tInit());;  //.addActionListener(new LtInit());
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
			rank = model.getRank();
			main = model.getMain();
			
			for(int i = 0; i < 10; i++)	{
				tParam[i].setVisible(false); tParam[i].setManaged(false);
			}
			lInit.setVisible(!model.getIsNumeric()); lInit.setManaged(!model.getIsNumeric());
			tInit.setVisible(!model.getIsNumeric()); tInit.setManaged(!model.getIsNumeric());
			lParam.setVisible(model.getIsNumeric()); lParam.setManaged(model.getIsNumeric());
			if(model.getIsNumeric()){
				for(int i = 0; i < rank; i++){
					tParam[i].setVisible(true);	tParam[i].setManaged(true);
					tParam[i].setText("");
				}
				tParam[0].requestFocus();
			} else {
				tInit.setText(""); 
				tInit.requestFocus();
			}
			
		tNodef.setText("1000");	
		tResult.setText("");
		tStep.setText("");
		lNodef.setVisible(!isPost); lNodef.setManaged(!isPost);
		tNodef.setVisible(!isPost); tNodef.setManaged(!isPost);
		lStep.setVisible(!isPost); lStep.setManaged(!isPost);
		tStep.setVisible(!isPost); tStep.setManaged(!isPost);
	}
	
	private void tParam(int sindex){
		//String sindex = e.getActionCommand();
		Integer index = new Integer(sindex);
		String sParam = tParam[index].getText();
		tResult.setText("");
		tStep.setText("");
		if (!type.equals("Post")) {
			//owner.show.setEnabled(false);
			owner.show.setVisible(false); owner.show.setManaged(false); 
			owner.pStepsTable.setVisible(false); owner.pStepsTable.setManaged(false);
			//owner.pack();
		}	
		if(StringWork.isNatur(sParam)) {
			if(index < rank-1) tParam[index+1].requestFocus();
			else tNodef.requestFocus();
		} else {
			String text = "Значення " + sindex + " аргументу " + sParam + " - не натуральне число !";
			DialogWork.showAlertError(type, text).showAndWait();
			//JOptionPane.showMessageDialog(PnEvalFunctionString.this, 
			//		"Значення " + sindex + " аргументу " + sParam + " - не натуральне число !");
		}		
	}
	
	private void tInit(){
		String sInit = tInit.getText();
		String noAlfa = StringWork.isAlfa(main, sInit);
		if (type.equals("Machine")) noAlfa = StringWork.isAlfa(main + "_", sInit);
		//System.out.println("init=" + sInit+ " main="+main + " noAlfa="+noAlfa);
		tResult.setText("");
		tStep.setText("");
		//owner.show.setEnabled(false);
		owner.show.setVisible(false); owner.show.setManaged(false); 
		owner.pStepsTable.setVisible(false); owner.pStepsTable.setManaged(false);
		//owner.pack();
		if(noAlfa.isEmpty()) tNodef.requestFocus();
		else {
			String text = "Вхідне слово " + sInit + " містить символи " + noAlfa + 
				          " що не належать основному алфавіту !";
			DialogWork.showAlertError(type, text).showAndWait();
			//JOptionPane.showMessageDialog(PnEvalFunctionString.this, 
			//		"Вхідне слово " + sInit + " містить символи " + noAlfa + 
			//			" що не належать основному алфавіту !");
		}
	}
	
	// перевіряє встановлені аргументи і кількiсть кроків на коректність !!! 
	public String testArguments(){
		String text = "";
		//if (!type.equals("Post")){
			if(type.equals("Recursive") || type.equals("Computer") || model.getIsNumeric()){
				for(int i=0; i < rank; i++) 
					if(!StringWork.isNatur(tParam[i].getText())) text = text + tParam[i].getText() + ":"; 
				if(!text.isEmpty()) text = "Аргументи:" + text + " - не натуральні числа";
			}	
			else text = testInitial();
			if (text.isEmpty()) 
				if(!StringWork.isNatur(tNodef.getText())) text = "Кількість кроків " + tNodef.getText() + " не натуральне число.";
		//}
		return text;
	}
	
	private String testInitial() {
		String sInit = tInit.getText();
		String noAlfa = StringWork.isAlfa(main, sInit);
		if (type.equals("Machine")) noAlfa = StringWork.isAlfa(main + "_", sInit);
		if(!noAlfa.isEmpty())
			noAlfa = "Вхідне слово " + sInit + " містить символи " + noAlfa + " що не належать основному алфавіту !"; 
		return noAlfa;
	}
	
	public Object getArguments() {
		if(type.equals("Recursive") || type.equals("Computer")){
			int[] arg = new int[rank];
			for(int i=0; i < rank; i++)  arg[i] = new Integer(tParam[i].getText());
			return arg;
		} 
		else if (model.getIsNumeric()) {
			String input = "";
			for (int i = 0; i < rank; i++) {
				if (i> 0) input = input + "#";
				input = input + StringWork.toInternal(new Integer(tParam[i].getText()));		
			}
			return input;
		} else return tInit.getText();
	}
	
	public int getNodef() { 
		return new Integer(tNodef.getText());
	}
	
	public void setResult(String text,int step)	{
		tResult.setText(text); 
		if (step == -1) tStep.setText(""); else tStep.setText("" + step);
	}
}
