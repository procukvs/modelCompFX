package gui.model;


import main.*;
import model.*;
import gui.*;

import java.util.*;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.beans.property.*;

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
	
	private boolean isNumericSet;
		
	public PnDescription(boolean isEdit){
		initialize();	
		isNumericSet = true;
		sName.setEditable(isEdit);//.setEnabled(isEdit);
		sMain.setEditable(isEdit);//setEnabled(isEdit);
		sAdd.setEditable(isEdit);//setEnabled(isEdit);
		//isNumeric.setEditable(isEdit);//setEnabled(isEdit);
		iRank.setEditable(isEdit);//setEnabled(isEdit);
		sComm.setEditable(isEdit);//setEnabled(isEdit);
		
		sInit.setEditable(isEdit);//setEnabled(isEdit);
		sFin.setEditable(isEdit);//setEnabled(isEdit);
		
		if (isEdit) {
			// 	���������� �������� !!!			
			//sName.addActionListener(new LssName());
			//sMain.addActionListener(new LssMain());
			//sAdd.addActionListener(new LssAdd());
			//isNumeric.addActionListener(new LsisNumeric());
			//iRank.addActionListener(new LsiRank());
			//sComm.addActionListener(new LsComm());
			//sInit.addActionListener(new LssInit());
			//sFin.addActionListener(new LssFin());
			sName.setOnAction(e->{if (model == null) showEmpty();
									else {	if (testAndSave()) {
													if (type.equals("Machine")) sInit.requestFocus();
													else sMain.requestFocus();
										}	
									}});
			sInit.setOnAction(e->{if (model == null) showEmpty();
									else {	if (testAndSave())sFin.requestFocus();
									}});
			sFin.setOnAction(e->{ if (model == null) showEmpty();
									else {	if (testAndSave())sMain.requestFocus();
									}});
			isNumeric.setOnAction(e-> testIsNumeric());
			iRank.setOnAction(e->{if (model == null) showEmpty();
									else {	if (testAndSave()){
												if (!type.equals("Computer") && !type.equals("Recursive")) sMain.requestFocus();
												else sComm.requestFocus();
												}
									}});
			sMain.setOnAction(e->{if (model == null) showEmpty();
									else { if (testAndSave())sAdd.requestFocus();
									}});
			sAdd.setOnAction(e->{if (testAndSave())sComm.requestFocus();});
			sComm.setOnAction(e->{if (model == null) showEmpty(); else {testAndSave();}});
		}
		else isNumeric.setOnAction(e-> isNumeric.setSelected(isNumericSet));//.selectedProperty().bind(isNumericSet);
		
	}
	
	private void initialize() {
		//���������� �������� gui-�������� 
		txtAlgo = new Label("�������� ");
		sName = new TextField();
		sName.setPrefColumnCount(9);
		txtNumb = new Label("--------");
		
		txtInit = new Label("���������� ����");
		sInit = new TextField();
		sInit.setPrefColumnCount(3);
		txtFin = new Label("��������� ����");
		sFin = new TextField();
		sFin.setPrefColumnCount(3);
		
		txtNumeric = new Label("������� ");
		isNumeric = new CheckBox();
		isNumeric.setSelected(true);
		txtRank = new Label("������");
		iRank = new TextField();
		iRank.setPrefColumnCount(1);
		
		txtMain = new Label("������ ��������");
		sMain = new TextField(); 
		sMain.setPrefColumnCount(6);
		txtAdd = new Label("������ ����������");
		sAdd = new TextField(); 
		sAdd.setPrefColumnCount(6);
		
		txtComm = new Label("���� ���������");
		sComm = new TextField(); 
		sComm.setPrefColumnCount(40);
		
		//=================================
		// ������� ���������
		//---------------------------
		HBox headBox = new HBox(5);
		headBox.getChildren().addAll(txtAlgo, sName, txtNumb, txtInit, sInit, txtFin, sFin,
				                     txtNumeric, isNumeric, txtRank, iRank);
		headBox.setAlignment(Pos.CENTER);
		HBox alfaBox = new HBox(5);
		alfaBox.getChildren().addAll(txtMain,sMain,txtAdd,sAdd);
		alfaBox.setAlignment(Pos.CENTER);
		HBox commBox = new HBox(5);	
		commBox.getChildren().addAll(txtComm,sComm);	
		commBox.setAlignment(Pos.CENTER);
		getChildren().addAll(headBox,alfaBox, commBox);
		this.setSpacing(3);
		txtInit.setVisible(false);
		sInit.setVisible(false);
		txtFin.setVisible(false);
		sFin.setVisible(false);
		

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
		txtComm.setText("���� " + Model.title(type, 3));
	   
	    
	    txtMain.setVisible(isVisibleMany && !isComputer); txtMain.setManaged(isVisibleMany && !isComputer);
	    sMain.setVisible(isVisibleMany && !isComputer); sMain.setManaged(isVisibleMany && !isComputer);
	    txtAdd.setVisible(isVisibleMany && !isComputer); txtAdd.setManaged(isVisibleMany && !isComputer);
	    sAdd.setVisible(isVisibleMany && !isComputer); sAdd.setManaged(isVisibleMany && !isComputer);
	    txtNumeric.setVisible(isVisibleMany && !isComputer); txtNumeric.setManaged(isVisibleMany && !isComputer);
	    isNumeric.setVisible(isVisibleMany && !isComputer);  isNumeric.setManaged(isVisibleMany && !isComputer);
	    
		txtRank.setVisible(isVisibleMany); txtRank.setManaged(isVisibleMany);
		iRank.setVisible(isVisibleMany); iRank.setManaged(isVisibleMany);

		txtInit.setVisible(isVisible); txtInit.setManaged(isVisible);
		sInit.setVisible(isVisible); sInit.setManaged(isVisible);
		txtFin.setVisible(isVisible); txtFin.setManaged(isVisible);
		sFin.setVisible(isVisible); sFin.setManaged(isVisible);
		
		if (model == null) showEmpty( );
		else showModel();
	}
	
	private void showModel() {
		sName.setText(model.name);
		txtNumb.setText("����� " + model.id);
		sComm.setText(model.descr);
		sMain.setText(model.getMain());
		sAdd.setText(model.getAdd());
		isNumeric.setSelected(model.getIsNumeric());
		isNumericSet =model.getIsNumeric();
		iRank.setText(((Integer)model.getRank()).toString());
		//System.out.println("PnDescription:showModel:model.getIsNumeric()" + model.getIsNumeric() +" isVisibleMany "+isVisibleMany);
		txtRank.setVisible(model.getIsNumeric() && isVisibleMany); txtRank.setManaged(model.getIsNumeric() && isVisibleMany);
		iRank.setVisible(model.getIsNumeric() && isVisibleMany); iRank.setManaged(model.getIsNumeric() && isVisibleMany);
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
		txtRank.setVisible(isVisibleMany); txtRank.setManaged(isVisibleMany);
		iRank.setVisible(isVisibleMany); iRank.setManaged(isVisibleMany);
		sComm.setText("");
	}
	
	private void testIsNumeric(){
		if (model != null){
			if(isNumeric.isSelected()){
				String tempAdd = sAdd.getText(); 
				tempAdd = StringWork.isAlfa("|#", tempAdd);
				tempAdd = tempAdd + StringWork.isAlfa(tempAdd +"|#", sMain.getText());
				sMain.setText("|#");
				sAdd.setText(tempAdd);	
			}
			if (testAndSave()){
				txtRank.setVisible(isNumeric.isSelected());
				iRank.setVisible(isNumeric.isSelected());
				if(isNumeric.isSelected()) iRank.requestFocus(); 
				else sComm.requestFocus();
			}
		} else showEmpty();
	}
	
	// �������� �������� ��� �� �� ����������  
	// .. ��� ����� ==> true 
	// .. � ��������� ���� ==> ����������� + false
	// .. ���� ������Ͳ==> ����������� � �� + ���������� ����� + true
	public boolean testAndSave(){
		boolean res = true;
		// 0->sName, 1->sInit, 2->sFin, 3->isNumeric, 4->iRank, 5->sMain, 6->sAdd, 7->sComm 
		boolean[] modif = new boolean[8];
		String strName, strInit="", strFin="", strMain="", strAdd="", strComm, srank="";
		boolean blnNumeric=true;
		int intRank=0;
		for (int i=0;i<8;i++) modif[i] = false;  // �� �������� ������� ��������
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
			// � ���� ������� �������� ����� ��������
			ArrayList <String> mess = new ArrayList();  // message about errors. 
			String text="";
			res = true;
		 	if (modif[0]){  //���������� ���������� ���� !!!!
		 		text = testName(strName);
		 		if(!text.isEmpty()) mess.add(text);
		 	}
		 	if (modif[1]){
		 		if (!StringWork.isState(strInit))mess.add("������ ����� - @STT. ���� \"" + strInit + "\" �� ��������� !");
		 	}
		 	if (modif[2]){
		 		if (!StringWork.isState(strFin))mess.add("������ ����� - @STT. ���� \"" + strFin + "\" �� ��������� !");
		 	}
		 	if (modif[3]){
		 		if(blnNumeric && !StringWork.isOnlyAlfa("|#", strMain))
		 			mess.add("�������� ������ ������� �� ���� �������� ������� ����� �� | ��� # ");  
		 	}
		 	if (modif[4]){
		 		if(blnNumeric && !StringWork.isPosNumber(srank))mess.add("������ - ������ ���� ����� ! ");
		 	}
		 	if (modif[5]){
		 		if(blnNumeric && !StringWork.isOnlyAlfa("|#", strMain))
		 			mess.add("�������� ������ ������� �� ���� �������� ������� ����� �� | ��� # ");  
		 	}
		 	if (modif[5] || modif[6]){
		 		text = model.iswfModelAlfa(strMain+strAdd);
		 		if(!text.isEmpty()) mess.add(text);
		 	}
		 	res = (mess.size()==0);
		 	if (res){
		 		// � ���� ������Ͳ ==> ����������� � �� + ���������� ����� + true
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
		 		DialogWork.showAlertError("Error", ms).showAndWait();
		 		/*
		 		Alert alert = new Alert(AlertType.ERROR);
		 		alert.setTitle("Error alert");
		 		alert.setHeaderText(null);
		 		alert.setContentText(ms);
		 		alert.showAndWait();
		 		*/
		 		
		 		//JOptionPane.showMessageDialog(PnDescription.this,aMess);
		 		showModel();
		 	}
		}
		return res;
	}
	
	private String testName(String name){
		String text="";
		if(!StringWork.isIdentifer(name))
			text = "��\"� " + Model.title(type, 3) + " " + name + " - �� �������������!";
		if((text.isEmpty()) && (env.isNameUse(name)))  ///(db.isModel(type,  name)))
			text = Model.title(type, 2) + " � ������ " + name + " ��� ���� !";
		return text;
	}
}
