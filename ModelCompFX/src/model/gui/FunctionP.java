package model.gui;

import javafx.beans.property.*;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;

//import model.*;
import model.rec.*;


public class FunctionP {
	
	private SimpleStringProperty name =
			 new SimpleStringProperty(this, "name", null);
	private SimpleIntegerProperty rank =
			 new SimpleIntegerProperty(this, "rank", 0);
	private SimpleBooleanProperty isConst =
			 new SimpleBooleanProperty(this, "isConst", false);
	private SimpleBooleanProperty iswf =
			 new SimpleBooleanProperty(this, "iswf", false);
	private SimpleStringProperty txBody =
			 new SimpleStringProperty(this, "txBody", null);
	private SimpleStringProperty txComm =
			 new SimpleStringProperty(this, "txComm", null);
	private SimpleIntegerProperty id=
			 new SimpleIntegerProperty(this, "id", 0);;
	
	public FunctionP() {
		this(null, 0, false, false, null, null, 0);
	}		 
			 
	public FunctionP(String name,int rank, boolean isConst, boolean iswf, String txBody,String txComm, int id) {
		this.name.set(name); this.rank.set(rank); this.isConst.set(isConst); 
		this.iswf.set(iswf); this.txBody.set(txBody); this.txComm.set(txComm); this.id.set(id);
	}
	
	public FunctionP(Function f){
		this(f.getName(), f.getRank(), f.getisConst(),
			 f.getiswf(), f.gettxBody(), f.gettxComm(),f.getId());
	}
	
	/* name Property */
	public String getName() { return name.get();}
	public StringProperty nameProperty() {	return name; }
	public void setName(String name) {nameProperty().set(name);}

	/* rank Property */
	public int getRank() { return rank.get();}
	public IntegerProperty rankProperty() {	return rank; }
	public void setRank(int rank) {rankProperty().set(rank);}

	/* isConst Property */
	public boolean getIsConst() { return isConst.get();}
	public BooleanProperty isConstProperty() {return isConst; }
	public void setIsConst(boolean isConst) {isConstProperty().set(isConst);}
	
	/* iswf Property */
	public boolean getIswf() { return iswf.get();}
	public BooleanProperty iswfProperty() {return iswf; }
	public void setIswf(boolean iswf) {iswfProperty().set(iswf);}
	
	/* txBody Property */
	public String getTxBody() { return txBody.get();}
	public StringProperty txBodyProperty() {	return txBody; }
	public void setTxBody(String txBody) {txBodyProperty().set(txBody);}
	
	/* txComm Property */
	public String getTxComm() { return txComm.get();}
	public StringProperty txCommProperty() { return txComm; }
	public void setTxComm(String txComm) {txCommProperty().set(txComm);}	
	
	/* id Property */
	public int getId() { return id.get();}
	public IntegerProperty idProperty() {	return id; }
	public void setId(int id) {idProperty().set(id);}	

	// build TableColumns
	public static TableColumn<FunctionP,String> getNameColumn(){
		TableColumn<FunctionP,String> funcNameCol = new TableColumn<>("Назва");
		funcNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
		return funcNameCol;
	}
	
	public static TableColumn<FunctionP,Integer> getRankColumn(){
		TableColumn<FunctionP,Integer> funcRankCol = new TableColumn<>("Арність");
		funcRankCol.setCellValueFactory(new PropertyValueFactory<>("rank"));
		return funcRankCol;
	}
	
	public static TableColumn<FunctionP,Boolean> getIsConstColumn(){
		TableColumn<FunctionP,Boolean> funcIsConstCol = new TableColumn<>("Константа");
		funcIsConstCol.setCellValueFactory(new PropertyValueFactory<>("isConst"));
		return funcIsConstCol;
	}
	
	public static TableColumn<FunctionP,Boolean> getIswfColumn(){
		TableColumn<FunctionP,Boolean> funcIswfCol = new TableColumn<>("Вірна");
		funcIswfCol.setCellValueFactory(new PropertyValueFactory<>("iswf"));
		return funcIswfCol;
	}
	
	public static TableColumn<FunctionP,String> getTxBodyColumn(){
		TableColumn<FunctionP,String> funcTxBodyCol = new TableColumn<>("Тіло функції");
		funcTxBodyCol.setCellValueFactory(new PropertyValueFactory<>("txBody"));
		return funcTxBodyCol;
	}
	
	public static TableColumn<FunctionP,String> getTxCommColumn(){
		TableColumn<FunctionP,String> funcTxCommCol = new TableColumn<>("Коментар");
		funcTxCommCol.setCellValueFactory(new PropertyValueFactory<>("txComm"));
		return funcTxCommCol;
	}
	
	public static TableColumn<FunctionP,Integer> getIdColumn(){
		TableColumn<FunctionP,Integer> funcIdCol = new TableColumn<>("№");
		funcIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
		return funcIdCol;
	}
	
	public static TableView<FunctionP> initTableView(){
		TableView<FunctionP> table = new TableView<>();
		table.getColumns().addAll(getNameColumn(), getRankColumn(),
				                  getIsConstColumn(), getIswfColumn(), getTxBodyColumn(),
				                  getTxCommColumn(), getIdColumn());
		return table;
	}
	
	public static void setTableView(TableView<FunctionP> table, Recursive model){ 	
		if(model != null){
			TableColumn<FunctionP,String> funcIdModel = new TableColumn<>("№Н");
			funcIdModel.setCellValueFactory(celData-> {
				int idModel = model.id;
				return new ReadOnlyStringWrapper(""+idModel);});
			table.getItems().clear();
			if (table.getColumns().size()>7) table.getColumns().remove(7);
			table.getColumns().add(funcIdModel);
			ObservableList<FunctionP> newL = FXCollections.<FunctionP>observableArrayList();
			for(int i=0; i<model.program.size(); i++){
				FunctionP rp = new FunctionP((Function)model.program.get(i));
				newL.add(rp);
				
			}
			table.getItems().addAll(newL);
		}
	}
	
}
