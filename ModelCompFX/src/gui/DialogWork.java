package gui;

import javafx.scene.control.*;
import javafx.scene.control.Alert.*;

public class DialogWork {
	public static void showAlert(String sTitle, String sContent){
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(sTitle);
        alert.setHeaderText(null);
        alert.setContentText(sContent);
        alert.showAndWait();
	}

}
