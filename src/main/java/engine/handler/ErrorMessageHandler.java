package engine.handler;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * @author lbaudenb
 */

public class ErrorMessageHandler {

  public static void showErrorMessage(String message) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Error");
    alert.setContentText(message);
    alert.setHeaderText(null);
    alert.show();
  }
}


