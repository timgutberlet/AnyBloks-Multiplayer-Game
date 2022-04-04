package game.controller;

import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;

/**
 * @author lbaudenb
 */

public class MainMenuUiController extends AbstractUiController {

  private final AbstractGameController gameController;

  public MainMenuUiController(AbstractGameController gameController) {
    super();
    this.gameController = gameController;
    init(super.root);
  }

  @FXML
  public void lobby() {
    gameController.setActiveUiController(new LobbyController(gameController));
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
}

