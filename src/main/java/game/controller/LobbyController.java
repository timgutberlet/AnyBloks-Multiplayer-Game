package game.controller;

import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;

public class LobbyController extends AbstractUiController {

  private final AbstractGameController gameController;

  public LobbyController(AbstractGameController gameController) {
    super(gameController);
    this.gameController = gameController;
    System.out.println("gameController : " + gameController);
  }

  public void init(AbstractGameController gameController, Group root2D) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(LobbyController.class.getResource("LobbyView.fxml"));
      loader.setControllerFactory(e -> this);
      root2D.getChildren().add(loader.load());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void backToMainMenu() {
    gameController.setActiveUiController(new MainMenuUiController(gameController));
  }

  @FXML
  public void playTest() {
    gameController.setActiveUiController(new InGameUiController(gameController));
  }
}

