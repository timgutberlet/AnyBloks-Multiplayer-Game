package engine.handler;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Class to handle Error Messages.
 *
 * @author lbaudenb
 */

public class ErrorMessageHandler {

  /**
   * Constrctor of the showError class, determining the message to show.
   *
   * @param message message to be printed
   */
  public static void showErrorMessage(String message) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Error");
    alert.setContentText(message);
    alert.setHeaderText(null);
    alert.show();
  }
}


