package game.controller;

import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;

public class MainMenuUiController extends AbstractUiController {

  private final AbstractGameController gameController;

  public MainMenuUiController(AbstractGameController gameController) {
    super(gameController);
    this.gameController = gameController;
    System.out.println("gameController : " + gameController);
  }

  @FXML
  public void lobby() {
    gameController.setActiveUiController(new LobbyController(gameController));
  }

  @Override
  public void init(AbstractGameController gameController, Group root2D) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(MainMenuUiController.class.getResource("MainMenuView.fxml"));
      loader.setControllerFactory(e -> this);
      root2D.getChildren().add(loader.load());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
