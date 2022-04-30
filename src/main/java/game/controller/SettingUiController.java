package game.controller;

import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import game.config.Config;
import java.awt.Label;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;

/**
 * Ui Controller Class, used for Controlling the Settings View
 * @author timgutberlet
 */
public class SettingUiController extends AbstractUiController {

  private final AbstractGameController gameController;

  @FXML
  private Label themeLabel;

  /**
   * Init Method declaring the FXML loader and connecting to the Settingsview FXML
   * @param root Group Element from the AbstractUIController
   */
  public void init(Group root) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/Settingsview.fxml"));
      loader.setControllerFactory(e -> this);
      root.getChildren().add(loader.load());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Sets Gamecontroller and calls init Method
   * @param gameController AbstractGameController Object
   */
  public SettingUiController(AbstractGameController gameController) {
    super();
    this.gameController = gameController;
    init(super.root);
  }

  /**
   * load Settings from Config File
   */
  private void loadSettings(){
    themeLabel.setText("" + Config.getStringValue("THEME"));
  }
}
