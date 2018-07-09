package gui.model;


import main.*;
import model.*;
import gui.*;

import java.util.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;

public class PnDescription extends VBox {
	private FrMain fMain;
	private AllModels env=null;	
	private String type = "Algorithm";
	private Model model;
	boolean isVisibleMany;
	
	private Label txtAlgo;
	private TextField sName;
	private Label txtNumb;
	
	private Label txtInit;
	private TextField sInit;
	private Label txtFin;
	private TextField sFin;
	
	private Label txtNumeric;
	private CheckBox isNumeric;
	private Label txtRank;
	private TextField iRank;
	
	private Label txtMain;
	private TextField sMain;
	private Label txtAdd;
	private TextField sAdd;
	private Label txtComm;
	private TextField sComm;
	
	public PnDescription(boolean isEdit){
		initialize();		
		
		
	}
	
	private void initialize() {
		//сформувати необхідні gui-елементи 
		txtAlgo = new Label("Алгоритм ");
		sName = new TextField();
		txtNumb = new Label("--------");
		
		txtInit = new Label("Початковий стан");
		sInit = new TextField();
		txtFin = new Label("Заключний стан");
		sFin = new TextField();
		
		txtNumeric = new Label("Функція ");
		isNumeric = new CheckBox();
		isNumeric.setSelected(true);
		txtRank = new Label("Арність");
		iRank = new TextField();
		
		txtMain = new Label("Алфавіт основний");
		sMain = new TextField(); 
		txtAdd = new Label("Алфавіт додатковий");
		sAdd = new TextField(); 
		
		txtComm = new Label("Опис алгоритму");
		sComm = new TextField(); 
		
		//=================================
		// формуємо розміщення
		//---------------------------
		HBox headBox = new HBox();
		headBox.getChildren().addAll(txtAlgo,sName,txtNumb,txtNumeric, isNumeric, txtRank, iRank);
		HBox alfaBox = new HBox();
		alfaBox.getChildren().addAll(txtMain,sMain,txtAdd,sAdd);
		HBox commBox = new HBox();	
		commBox.getChildren().addAll(txtComm,sComm);	
		getChildren().addAll(headBox,alfaBox, commBox);
		
		
	}
	public void setEnv(FrMain owner) {
		//this.db = db;
		fMain = owner;		
	}
	public void show(AllModels env){
		this.env = env;
		type = env.getType();
	    model = env.getModel();
		boolean isVisible = type.equals("Machine");
		boolean isComputer = type.equals("Computer");
		isVisibleMany = !type.equals("Recursive") && !type.equals("Calculus");
		
	  	txtAlgo.setText(Model.title(type, 2)); 
		txtComm.setText("Опис " + Model.title(type, 3));
	   
	    
	    txtMain.setVisible(isVisibleMany && !isComputer);
	    sMain.setVisible(isVisibleMany && !isComputer);
	    txtAdd.setVisible(isVisibleMany && !isComputer);
	    sAdd.setVisible(isVisibleMany && !isComputer);
	    txtNumeric.setVisible(isVisibleMany && !isComputer);
	    isNumeric.setVisible(isVisibleMany && !isComputer);
	    
		txtRank.setVisible(isVisibleMany);
		iRank.setVisible(isVisibleMany);

		txtInit.setVisible(isVisible);
		sInit.setVisible(isVisible);
		txtFin.setVisible(isVisible);
		sFin.setVisible(isVisible);
		
		if (model == null) showEmpty( );
		else showModel();
	}
	private void showModel() {
		sName.setText(model.name);
		txtNumb.setText("Номер " + model.id);
		sComm.setText(model.descr);
		sMain.setText(model.getMain());
		sAdd.setText(model.getAdd());
		isNumeric.setSelected(model.getIsNumeric());
		iRank.setText(((Integer)model.getRank()).toString());
		//System.out.println("PnDescription:showModel:model.getIsNumeric()" + model.getIsNumeric() +" isVisibleMany "+isVisibleMany);
		txtRank.setVisible(model.getIsNumeric() && isVisibleMany);
		iRank.setVisible(model.getIsNumeric() && isVisibleMany);
		sInit.setText(model.getInit());
		sFin.setText(model.getFin());
	}
	
	private void showEmpty() {
		sName.setText("");
		txtNumb.setText("-----");
		sInit.setText("@a0");
		sFin.setText("@zz");
		sMain.setText("");
		sAdd.setText("");
		isNumeric.setSelected(true);
		iRank.setText("2");
		txtRank.setVisible(isVisibleMany);
		iRank.setVisible(isVisibleMany);
		sComm.setText("");
	}
	
	// перевіряє наявність змін та їх коректність  
	// .. змін НЕМАЄ ==> true 
	// .. є некоректні зміни ==> повідомлення + false
	// .. зміни КОРЕКТНІ==> модифікація в БД + відновлення моделі + true
	public boolean testAndSave(){
		boolean res = true;
		// 0->sName, 1->sInit, 2->sFin, 3->isNumeric, 4->iRank, 5->sMain, 6->sAdd, 7->sComm 
		boolean[] modif = new boolean[8];
		String strName, strInit="", strFin="", strMain="", strAdd="", strComm, srank="";
		boolean blnNumeric=true;
		int intRank=0;
		for (int i=0;i<8;i++) modif[i] = false;  // не змінилося жодного елементу
		strName=sName.getText();
		modif[0] = (!strName.equals(model.name));
		if (type.equals("Machine")){
			strInit = sInit.getText(); modif[1] = (!strInit.equals(model.getInit()));
			strFin = sFin.getText(); modif[2] = (!strFin.equals(model.getFin()));
		}
		if(!type.equals("Recursive") && !type.equals("Calculus")&& !type.equals("Computer")){
			blnNumeric = isNumeric.isSelected();
			modif[3] = (blnNumeric!=model.getIsNumeric());
		}
		if(!type.equals("Recursive") && !type.equals("Calculus")){
			srank = iRank.getText();
			if (StringWork.isPosNumber(srank)){
				intRank = new Integer(srank); modif[4]=(intRank!=model.getRank());
			} else modif[4]=true;
		}
		if(!type.equals("Recursive") && !type.equals("Calculus")&& !type.equals("Computer")){
			String temp ="";
			strMain = sMain.getText(); temp = StringWork.isAlfa("", strMain);
			if (!strMain.equals(temp)){
				sMain.setText(temp); strMain = temp;
			}
			modif[5] = (!strMain.equals(model.getMain()));
			strAdd = sAdd.getText(); temp = StringWork.isAlfa(strMain, strAdd);
			if (!strAdd.equals(temp)){
				sAdd.setText(temp); strAdd = temp;
			}
			modif[6] = (!strAdd.equals(model.getAdd()));
		}
		strComm=sComm.getText();
		modif[7] = (!strComm.equals(model.descr));
		for(int i=0;i<8;i++) {if (modif[i]) res = false;}
		
		if (!res){
			// Є зміни потрібна перевірка нових значеннь
			ArrayList <String> mess = new ArrayList();  // message about errors. 
			String text="";
			res = true;
		 	if (modif[0]){  //перевіряємо коректність імені !!!!
		 		text = testName(strName);
		 		if(!text.isEmpty()) mess.add(text);
		 	}
		 	if (modif[1]){
		 		if (!StringWork.isState(strInit))mess.add("Формат стану - @STT. Стан \"" + strInit + "\" не коректний !");
		 	}
		 	if (modif[2]){
		 		if (!StringWork.isState(strFin))mess.add("Формат стану - @STT. Стан \"" + strFin + "\" не коректний !");
		 	}
		 	if (modif[3]){
		 		if(blnNumeric && !StringWork.isOnlyAlfa("|#", strMain))
		 			mess.add("Основний алфавіт функції не може включати символи відмінні від | або # ");  
		 	}
		 	if (modif[4]){
		 		if(blnNumeric && !StringWork.isPosNumber(srank))mess.add("Арність - додатнє ціле число ! ");
		 	}
		 	if (modif[5]){
		 		if(blnNumeric && !StringWork.isOnlyAlfa("|#", strMain))
		 			mess.add("Основний алфавіт функції не може включати символи відмінні від | або # ");  
		 	}
		 	if (modif[5] || modif[6]){
		 		text = model.iswfModelAlfa(strMain+strAdd);
		 		if(!text.isEmpty()) mess.add(text);
		 	}
		 	res = (mess.size()==0);
		 	if (res){
		 		// Є зміни КОРЕКТНІ ==> модифікація в БД + відновлення моделі + true
		 		if (modif[0]) model.name = strName;
		 		if (modif[1]) model.setInit(strInit);
		 		if (modif[2]) model.setFin(strFin);
		 		if (modif[3]) model.setIsNumeric(blnNumeric);
		 		if (modif[4]) model.setRank(intRank);
		 		if (modif[5]) model.setMain(strMain);
		 		if (modif[6]) model.setAdd(strAdd);
		 		//System.out.println("PnDescription:testAndSave: model.descr=" +  model.descr + " strComm = " + strComm); 
		 		if (modif[7]) model.descr = strComm;
		 		//db.editModel(type,model);
		 		env.editModel(model);
			} else {
		 		String[] aMess = new String[mess.size()];
		 		for(int i=0; i<mess.size();i++) aMess[i]=mess.get(i);
		 		String ms = "";
		 		for(int i=0; i<mess.size();i++) ms += mess.get(i) +"\n";
		 		
		 		Alert alert = new Alert(AlertType.ERROR);
		 		 
		 		alert.setTitle("Error alert");
		 		alert.setHeaderText("Can not add user");
		 		alert.setContentText(ms);
		 		 
		 		alert.showAndWait();
		 		
		 		//JOptionPane.showMessageDialog(PnDescription.this,aMess);
		 		showModel();
		 	}
		}
		return res;
	}
	
	private String testName(String name){
		String text="";
		if(!StringWork.isIdentifer(name))
			text = "Ім\"я " + Model.title(type, 3) + " " + name + " - не ідентифікатор!";
		if((text.isEmpty()) && (env.isNameUse(name)))  ///(db.isModel(type,  name)))
			text = Model.title(type, 2) + " з іменем " + name + " вже існує !";
		return text;
	}
}
