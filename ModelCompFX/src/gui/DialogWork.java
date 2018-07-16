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
	
	public static Alert showAlertConfirm(String sTitle, String sContent){
		Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(sTitle);
        alert.setHeaderText(null);
        alert.setContentText(sContent);
        return alert;
	}
	public static Alert showAlertInform(String sTitle, String sContent){
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(sTitle);
        alert.setHeaderText(null);
        alert.setContentText(sContent);
        return alert;
	}
	
	public static Alert showAlertError(String sTitle, String sContent){
		Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(sTitle);
        alert.setHeaderText(null);
        alert.setContentText(sContent);
        return alert;
	}

}
