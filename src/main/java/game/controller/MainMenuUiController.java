package game.controller;

import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import engine.handler.ThreadHandler;
import engine.handler.ThreadHandlerRestful;
import game.config.Config;
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
  AnchorPane mainPane;

  private final AbstractGameController gameController;

  public MainMenuUiController(AbstractGameController gameController) {
    super(gameController);
    this.gameController = gameController;
    init(super.root);

    ThreadHandlerRestful threadHandlerRestful = new ThreadHandlerRestful();
    threadHandlerRestful.start();

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
      updateSize(mainPane, gameController.getStage());
      switch (Config.getStringValue("THEME")){
        case "BRIGHT":
          mainPane.setStyle("-fx-background-color:#E7E7E0;");
          mainPane.getStylesheets().add(getClass().getResource("/styles/styleBrightTheme.css").toExternalForm());
          break;
        case "DARK":
          mainPane.setStyle("-fx-background-color: #383837;");
          mainPane.getStylesheets().add(getClass().getResource("/styles/styleDarkTheme.css").toExternalForm());
          break;
        case "INTEGRA":
          mainPane.setStyle("-fx-background-color: #ffffff;");
          mainPane.getStylesheets().add(getClass().getResource("/styles/styleIntegra.css").toExternalForm());
          break;
        case "THINK":
          mainPane.setStyle("-fx-background-color: #ffffff;");
          mainPane.getStylesheets().add(getClass().getResource("/styles/styleThinc.css").toExternalForm());
          break;
      }
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
  public void play() {
    gameController.setActiveUiController(new PlayUiController(gameController));
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
    updateSize(mainPane, gameController.getStage());
  }
}

