package game.controller;

import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import game.config.Config;
import game.core.App;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * @author lbaudenb
 * @author tgutberl
 */

public class MainMenuUiController extends AbstractUiController {
  @FXML
  AnchorPane menuPane;

  private final AbstractGameController gameController;

  public MainMenuUiController(AbstractGameController gameController) {
    super(gameController);
    this.gameController = gameController;
    init(super.root);
  }

  public void init(Group root) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/MainMenuView.fxml"));
      loader.setControllerFactory(e -> this);
      root.getChildren().add(loader.load());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  /**
   * Method to Start LocalControler - to get into LobbyUi
   * @author tgutberl
   */
  @FXML
  public void local() {
    gameController.setActiveUiController(new LobbyController(gameController));
  }
  /**
   * Method to Start LobbyController - to get into LobbyUi
   * @author lbaudenb
   */
  @FXML
  public void lobby() {
    gameController.setActiveUiController(new LobbyController(gameController));
  }
  /**
   * Method to Start Tutuorial
   * @author tgutberl
   */
  @FXML
  public void tutorial() {

  }
  /**
   * Method to get into SettingController - to get into SettingUI
   * @author tgutberl
   */
  @FXML
  public void setting() {
    gameController.setActiveUiController(new SettingUiController(gameController));
  }
  /**
   * Method to get Quit Menu - to End the Program
   * @author tgutberl
   */
  @FXML
  public void quit() {
    gameController.stop();
  }
  /**
   * Method to get get into the credits view
   * @author tgutberl
   */
  @FXML
  public void credits() {

  }
  /**
   * Method to get get into the help view
   * @author tgutberl
   */
  @FXML
  public void help() {

  }


  @Override
  public void onExit() {

  }

  @Override
  public void update(AbstractGameController gameController) {

  }
  @FXML
  public void initialize(){
    updateSize(menuPane, gameController.getStage());
  }
}

