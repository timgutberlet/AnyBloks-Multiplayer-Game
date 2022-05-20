package game.controller;

import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import engine.handler.ThreadHandler;
import game.model.Game;
import game.model.GameSession;
import game.model.gamemodes.GMTutorial;
import game.model.gamemodes.GameMode;
import game.model.player.Player;
import game.model.player.PlayerType;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;

/**
 * Class that shows up, when a player is Kicked from a Hostlobby
 *
 * @author tgutberl
 */

public class KickInfoUiController extends AbstractUiController {

  @FXML
  AnchorPane mainPane;

  private final AbstractGameController gameController;

  public KickInfoUiController(AbstractGameController gameController) {
    super(gameController);
    this.gameController = gameController;
    init(super.root);
  }

  /**
   * Method to initialize the FXML
   *
   * @param root Group Object
   * @author tgutberl
   */
  public void init(Group root) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/KickInfoView.fxml"));
      loader.setControllerFactory(e -> this);
      root.getChildren().add(loader.load());
      updateSize(mainPane, gameController.getStage());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method to Start the PlayView
   *
   * @author tgutberl
   */
  @FXML
  public void backToLobby() {
    gameController.setActiveUiController(new MainMenuUiController(gameController));
  }

  /**
   * Method to get Quit Menu - to End the Program
   *
   * @author tgutberl
   */
  @FXML
  public void close() {
    try {
      gameController.getApplication().stop();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  @Override
  public void onExit() {

  }

  @Override
  public void update(AbstractGameController gameController) {

  }

  @FXML
  public void initialize() {
    updateSize(mainPane, gameController.getStage());
  }
}

