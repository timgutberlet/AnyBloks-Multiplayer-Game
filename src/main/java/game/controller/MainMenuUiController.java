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

  /**
   * Method to initialize the FXML
   *
   * @param root Group Object
   * @author tgutberl
   */
  public void init(Group root) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/MainMenuView.fxml"));
      loader.setControllerFactory(e -> this);
      root.getChildren().add(loader.load());
      updateSize(menuPane, gameController.getStage());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method to Start LocalControler - to get into LobbyUi
   *
   * @author tgutberl
   */
  @FXML
  public void local() {
    gameController.setActiveUiController(new LobbyController(gameController));
  }

  /**
   * Method to Start LobbyController - to get into LobbyUi
   *
   * @author lbaudenb
   */
  @FXML
  public void multiplayer() {
    gameController.setActiveUiController(new LobbyController(gameController));
  }

  /**
   * Method to Start Tutuorial
   *
   * @author tgutberl
   */
  @FXML
  public void tutorial() {
    GameSession gameSession = new GameSession();
    GameMode gameMode = new GMTutorial();
    Player player = new Player("You", PlayerType.HOST_PLAYER);
    Player opponentAiPlayer = new Player("Opponent(Ai)", PlayerType.AI_EASY);
    gameSession.addPlayer(player);
    gameSession.addPlayer(opponentAiPlayer);
    gameSession.setGame(new Game(gameSession, gameMode));
    gameSession.startGame(gameMode);
    ThreadHandler threadHelp = new ThreadHandler(gameSession);
    gameController.setActiveUiController(new TutorialUiController(gameController, gameSession, threadHelp));
  }

  /**
   * Method to get into SettingController - to get into SettingUI
   *
   * @author tgutberl
   */
  @FXML
  public void setting() {
    gameController.setActiveUiController(new SettingUiController(gameController));
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

  /**
   * Method to get get into the credits view
   *
   * @author tgutberl
   */
  @FXML
  public void credits() {

  }

  /**
   * Method to get get into the help view
   *
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
  public void initialize() {
    updateSize(menuPane, gameController.getStage());
  }
}

