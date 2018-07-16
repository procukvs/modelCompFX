package gui.model;

import javafx.scene.layout.*;
import javafx.stage.*;
import main.StringWork;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.control.TableView.*;
import javafx.collections.*;

import model.*;

import gui.*;
import gui.command.*;
import gui.eval.*;
import model.gui.*;
import model.rec.Function;
import model.rec.Recursive;

public class PnComButtons extends VBox {
	private String type = "Algorithm";
	private AllModels env=null;
	private Model model = null;
	
	private PnComTable pComTable;
	private PnDescription pDescription;
	private FrMain fMain;
	private DgEdCommand dEdCommand;
	private DgInsert dInsert;
	private Command command;
	
	private Label selection;
	private Button add;
	private Button up ;
	private Button down ;
	private Button rename;
	private Button insert ;
	private Button see;
	
	private String tmpText;
	
	//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	public PnComButtons(Stage stMain){                             
		//сформувати необхідні gui-елементи 
		selection = new Label("  0:0 " );  ///+ dbm.getRowCount()); 
		//selection.setContentDisplay(ContentDisplay.BOTTOM);
		//selection.setPrefHeight(17);
		//selection.setAlignment(Pos.BOTTOM_CENTER);
		Button first = new Button("|<");
		Button prev = new Button("<");
		Button next = new Button(">");
		Button last = new Button(">|");
		add = new Button("Нова");
		//addAs = new JButton("Новий на основі");
		Button edit = new Button("Редагувати");
		Button delete = new Button("Вилучити");
		up = new Button("Перемістити вверх");
		down = new Button("Перемістити вниз");
		rename = new Button("Переіменувати");
		insert = new Button("Вставити програму");
		see = new Button("Переглянути");	
		//=================================
		// формуємо розміщення
		//---------------------------
		HBox select = new HBox();
		select.getChildren().addAll(first,prev,selection,next,last);
		select.setAlignment(Pos.BASELINE_LEFT);
		HBox buttons = new HBox(5);
		buttons.setAlignment(Pos.CENTER);
		buttons.getChildren().addAll(add,edit,delete,up,down,rename,insert); //see,
		
		getChildren().addAll(select,buttons);
		setSpacing(5);
		
		//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		Stage stCommand = new Stage(); 
		stCommand.initOwner(stMain);
		stCommand.initModality(Modality.WINDOW_MODAL);
		stCommand.initStyle(StageStyle.DECORATED);//(StageStyle.UTILITY);//(StageStyle.UNIFIED);//
		stCommand.setTitle("Command");
				
		dEdCommand = new DgEdCommand(stCommand);
		Scene scene = new Scene(dEdCommand); //, 560,684);
		stCommand.setScene(scene);
		
		//  dInsert = new DgInsert(fMain); !!!! as dEdCommand
		Stage stInsert = new Stage();
		stInsert.initOwner(stMain);
		stInsert.initModality(Modality.WINDOW_MODAL);
		stInsert.initStyle(StageStyle.DECORATED);
		stInsert.setTitle("Select");
		
		dInsert = new DgInsert(stInsert);
		Scene sceInsert = new Scene(dInsert,250,80); //, 560,684);
		stInsert.setScene(sceInsert);
		
		// встановити слухачів !!!	
		first.setOnAction(e->pComTable.moveOnTable("first")); 
		next.setOnAction(e->pComTable.moveOnTable("next")); //pComTable.showTable(false, -1));
		prev.setOnAction(e->pComTable.moveOnTable("prev")); //pComTable.showTable(false, -2));
		last.setOnAction(e->pComTable.moveOnTable("last")); 
	
		add.setOnAction(e->addCommand(stCommand));
		edit.setOnAction(e->editCommand(stCommand));
		delete.setOnAction(e->deleteCommand());
		rename.setOnAction(e->renameState() );    //testingData());
		insert.setOnAction(e->insertMachine(stInsert));
	}

	private void testingData(){
		
		System.out.println("Testing data");
		/*
		System.out.println("Size pComTable =" + pComTable.widthProperty().get());
		System.out.println("Size tableState =" + pComTable.tableState.widthProperty().get());
		System.out.println("column 4 minW = " + pComTable.tableState.getColumns().get(4).getMinWidth() +
				" prefW = "+ pComTable.tableState.getColumns().get(4).getPrefWidth() +
				" maxW = "+ pComTable.tableState.getColumns().get(4).getMaxWidth());
		System.out.println("Width min=" + pComTable.tableState.getMinWidth() + " perf=" + 
				pComTable.tableState.getPrefWidth() + " max=" + pComTable.tableState.getMaxWidth());
		
		System.out.println("first prev =" + first.getPrefHeight() + " min = " + first.getMinHeight() +
				            " max = " + first.getMaxHeight() +  " height= " + first.getHeight());
		System.out.println("first font = " + first.getFont().toString());
		System.out.println("selection prev =" + selection.getPrefHeight() + " min = " + selection.getMinHeight() +
	            " max = " + selection.getMaxHeight() +  " height= " + selection.getHeight());
		System.out.println("selection font = " + selection.getFont().toString());
		System.out.println("add prev =" + add.getPrefHeight() + " min = " + add.getMinHeight() +
	            " max = " + add.getMaxHeight() +  " height= " + add.getHeight());
		System.out.println("add font = " + add.getFont().toString());
		*/
		System.out.println("Is selected row?");
		TableViewSelectionModel<StateP> tsm = pComTable.tableState.getSelectionModel();
		if (tsm.isEmpty()) {
			System.out.println("No selected row !");
		}
		else{
			ObservableList<Integer> list = tsm.getSelectedIndices();
			int row= list.get(0);
			System.out.println("Selected row = " + row);
			//int cntCol=pComTable.tableState.getColumns().size();
			int selR = pComTable.tableState.getItems().get(row).getId();
			System.out.println("In row = " + row + " Id =" + selR);
			
			/*
			Integer[] selectedIndices = new Integer[list.size()];
			selectedIndices = list.toArray(selectedIndices);
			
			if(selectedIndices!=null && selectedIndices.length>0){
				int row = selectedIndices[0];
				System.out.println("Selected row = " + row);
			}
			else System.out.println("No selected row (second round)! " + selectedIndices.toString());
			*/
			tsm.select(5);
				
				
			
		}
		
	}
	
	public void setEnv(FrMain owner, PnDescription pDescription, PnComTable table){   
		fMain = owner;  
		this.pComTable = table;
		this.pDescription = pDescription;
	} 
	public void show(AllModels env){
		String text = "програму";
		this.env = env; 
		type = env.getType();
		model = env.getModel();
		boolean isVisible = type.equals("Machine");
		boolean rec = type.equals("Recursive") ;
		boolean comp = type.equals("Computer");
		boolean calc = type.equals("Calculus");
		add.setText(Model.title(type, 9));
		if(rec) text = "функцію";
		if(calc) text = "вираз";
		insert.setText("Вставити "+ text);
		up.setVisible(!isVisible && !rec); up.setManaged(!isVisible && !rec);
		down.setVisible(!isVisible && !rec); down.setManaged(!isVisible && !rec);
		rename.setVisible(isVisible); rename.setManaged(isVisible);
		insert.setVisible(isVisible || comp || rec || type.equals("Calculus"));
		insert.setManaged(isVisible || comp || rec || type.equals("Calculus"));
		//see.setVisible(false);
		//System.out.println("PnCommButtons: show="+env.getType()+".."+env.getPos());
	}
	public void setSelection(String txt){
		selection.setText(txt);
	}
	
	
	private void addCommand(Stage edCommand){
		if (model != null) {
			if (pDescription.testAndSave()){
				Rule rule;
				int id = pComTable.selectedRule();//getSelectedRow()+1; //pComTable.selectedRule();
				int idNew;
			
				dEdCommand.setCommand("Add", type, model, id);
				edCommand.showAndWait();
				command= dEdCommand.getCommand();
				if (command != null) { 
					//db.newCommand(type, model, command);
					//fMain.setModel(type, model.id);
					env.newCommand(command);
					pComTable.showRow(false,model.findCommand(command.getId())+1);
				}
			}
		}	
	}
	
	private void editCommand(Stage edCommand){
		if (model != null) {
			if (pDescription.testAndSave()){
				int row = pComTable.selectedRule();//getSelectedRow()+1; //.selectedRule();
				if (row > 0){
					dEdCommand.setCommand("Edit", type, model, row);
					//dEdCommand.show();
					edCommand.showAndWait();
					command= dEdCommand.getCommand();
					if (command != null) { 
						//db.editCommand(type, model, row, command);
						//fMain.setModel(type, model.id);
						env.editCommand(row, command);
						pComTable.showRow(false,model.findCommand(command.getId())+1);
					}
				}
			}
		}	
	}

	private void deleteCommand(){
		if (model != null) {
			if (pDescription.testAndSave()){
				int row = pComTable.selectedRule();//getSelectedRow()+1;
				if (row > 0){
					int row1 = model.findCommand(row);
					Command c = (Command) model.program.get(row1);                 //get(row1-1);
					String [] text = new String[] {"Вилучити " + Model.title(type, 11) + " з номером " + row + ". ",  c.show(model.getAllChar())} ;
					if (type.equals("Recursive")){
						Function f = (Function)c;
						String using = ((Recursive)model).usingName(f.getName());
						if (!using.isEmpty()){
							text = new String[] {"Вилучити " + Model.title(type, 11) + " з номером " + row + ". ",  c.show(model.getAllChar()),
									"Функція використовується в означенні: ", "<" + using + ">."} ;
						}
					}
					/*
					UIManager.put("OptionPane.yesButtonText", "Так");
					UIManager.put("OptionPane.noButtonText", "Ні");	
					int response = 
							JOptionPane.showConfirmDialog(PnComButtons.this,text, "Вилучити ?",JOptionPane.YES_NO_OPTION);
					if (response == JOptionPane.YES_OPTION) { 
						//db.deleteCommand(type, model.id, row,c);
						//fMain.setModel(type, model.id);
						env.deleteCommand(row, c);
						pComTable.showRow(false,row1);
					} 
					*/	
					DialogWork.showAlertConfirm("Delete?", StringWork.transferToString(text))
							.showAndWait()
							.filter(res -> res == ButtonType.OK)
							.ifPresent(res -> {	env.deleteCommand(row, c);
												pComTable.showRow(false,row1);});
				}
			}
		}
	}

	private void insertMachine(Stage stInsert){
		String nmModel = "", nmFunction="";
		if (model != null) {
			if (pDescription.testAndSave()){
				int row = pComTable.selectedRule();
				dInsert.setInsert(type, model, env, row);
				stInsert.showAndWait();
				nmModel = dInsert.getNmModel();
				nmFunction = dInsert.getNmFunction();
				//System.out.println("PnCommandButton:inserMachine  Model= "  + nmModel + " Function=" + nmFunction);
				if ((nmModel !=null) &&!nmModel.isEmpty()){
					String text = env.insertModel(row, nmModel, nmFunction);
					if (!text.isEmpty()) DialogWork.showAlertError("State", text).showAndWait();
										//JOptionPane.showMessageDialog(PnComButtons.this,text);
				}
			}
		}
	}		

	private void testAndRename(String oldName, String newName){
		tmpText = ((Machine)model).testState(newName);
		if(tmpText.isEmpty()){
			if (!((Machine)model).isState(newName)){ 
				env.renameStateMachine(oldName, newName);
				//int r = model.findCommand(newName);
				//System.out.println("PnComButtom.testAndRename: row ="+ r + " for state = " + newName);
				pComTable.showRow(false,model.findCommand(newName));
				
			} else tmpText = "Програма машини " + model.name + " вже використовує стан " + newName + " !";
		}
	}
	
	
	private void renameState(){
		//String init;
 		tmpText = "";
		if (model != null){
			if (pDescription.testAndSave()){
				String name = pComTable.selectedName();
				//final String  text;
				String init = ((Machine)model).newState();
				if (!name.isEmpty()) {
					// формуємо Діалогове вікно "Переіменування стану"
					TextInputDialog renameSt = new TextInputDialog(init);
					renameSt.setTitle("Rename");  //("Переіменування стану");
					renameSt.setHeaderText(null);
					renameSt.setContentText("Змінити імя стану " + name + " на ");
					renameSt.showAndWait().ifPresent(name1 -> testAndRename(name,name1));
					if(!tmpText.isEmpty()) DialogWork.showAlertError(type, tmpText).showAndWait(); 
							// JOptionPane.showMessageDialog(PnComButtons.this, text);
				}
			}
		}
	}

}
