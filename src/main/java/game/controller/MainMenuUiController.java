package game.controller;

import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;

/**
 * @author lbaudenb
 * @author tgutberl
 */

public class MainMenuUiController extends AbstractUiController {

  private final AbstractGameController gameController;

  public MainMenuUiController(AbstractGameController gameController) {
    super();
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
   * Method to Start LobbyController - to get into LobbyUi
   * @lbaudenb
   */
  @FXML
  public void lobby() {
    gameController.setActiveUiController(new LobbyController(gameController));
  }
  /**
   * Method to Start Tutuorial
   * @tgutberl
   */
  @FXML
  public void tutorial() {

  }
  /**
   * Method to get into SettingController - to get into SettingUI
   * @tgutberl
   */
  @FXML
  public void setting() {
    gameController.setActiveUiController(new SettingUiController(gameController));
  }
  /**
   * Method to get Quit Menu - to End the Program
   * @tgutberl
   */
  @FXML
  public void quit() {

  }
  /**
   * Method to get get into the credits view
   * @tgutberl
   */
  @FXML
  public void credits() {

  }


}

