package gui.model;

import model.*;
import javafx.collections.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;

public class ComTableUtil {
	
	// Algorithm
	public static ObservableList<Command> getRuleList(Algorithm model) {
		ObservableList<Command> lRule = FXCollections.observableArrayList();
		for(int i=0; i<model.program.size();i++){
			Rule r = (Rule)model.program.get(i);
			lRule.add(r);
		}
		return lRule;
	}
	public static TableColumn<Command, Integer> getNum() {
		TableColumn<Command, Integer> number = new TableColumn<>("πœ");
		number.setCellValueFactory(new PropertyValueFactory<>("getNum"));
		return number;
	}
	public static TableColumn<Command, String> getLeft() {
		TableColumn<Command, String> left = new TableColumn<>("πLeftœ");
		left.setCellValueFactory(new PropertyValueFactory<>("getsLeft"));
		return left;
	}
	public static void setTableColumns(TableView<Command> table){
		table.getColumns().addAll( getNum(), getLeft());
	}
}
