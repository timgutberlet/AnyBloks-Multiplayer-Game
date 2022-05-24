package game.controller;

import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import game.config.Config;
import game.model.Debug;
import game.model.GameSession;
import game.model.gamemodes.GMDuo;
import game.model.gamemodes.GameMode;
import game.model.player.Player;
import game.model.player.PlayerType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;
import net.server.ClientHandler;
import net.server.HostServer;
import net.tests.NoLogging;
import net.transmission.EndpointClient;

/**
 * Controller used to let the user start a local Game and set all AIs.
 *
 * @author tgutberl
 */
public class TutorialUiController extends AbstractUiController {

  /**
   * Gamecontroller used in Application.
   */
  private final AbstractGameController gameController;
  /**
   * Gamesession for setting players.
   */
  private final GameSession gameSession;
  /**
   * Gamemode list.
   */
  private final LinkedList<GameMode> gameModes = new LinkedList<>();
  /**
   * Endpoint for server-client communication.
   */
  private final EndpointClient client;
  /**
   * Clienthandler for input to Server.
   */
  private final ClientHandler clientHandler;
  /**
   * Main Anchorpane used for resizing.
   */
  @FXML
  AnchorPane mainPane;

  /**
   * Constructor of Lobycontroller Class. Used set Gamesession, Controller and to initialize.
   *
   * @param gameController Gamecontroller Object currently used
   * @author tgutberl
   */
  public TutorialUiController(AbstractGameController gameController) {
    super(gameController);
    this.gameController = gameController;
    this.init(super.root);

    HostServer hostServer = new HostServer();
    try {
      org.eclipse.jetty.util.log.Log.setLog(new NoLogging());
      hostServer.startWebsocket(8081);
      Debug.printMessage("[testChatServer] Server is running");
      //TimeUnit.SECONDS.sleep(3);
    } catch (Exception e) {
      e.printStackTrace();
    }

    Player player = new Player(Config.getStringValue("HOSTPLAYER"), PlayerType.HOST_PLAYER);
    this.client = new EndpointClient(this, player);

    this.gameSession = client.getGameSession();
    this.gameSession.setLocalPlayer(player);

    this.clientHandler = client.getClientHandler();
    gameSession.setClientHandler(this.clientHandler);

    this.gameSession.setDefaultAI(PlayerType.AI_EASY);

    List<String> gameModes = new ArrayList<>();

    this.gameModes.add(new GMDuo());

    this.gameSession.setGameList(this.gameModes);

    this.clientHandler.startLocalGame(this.gameModes, new LinkedList<PlayerType>());

    try {
      TimeUnit.SECONDS.sleep(3);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * Initalizing UI.
   *
   * @param root Root parameter
   * @author tgutberl
   */
  public void init(Group root) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/TutorialView.fxml"));
      loader.setControllerFactory(e -> this);
      root.getChildren().add(loader.load());
      updateSize(mainPane, gameController.getStage());
      //Sets the Theme, according to the settings
      switch (Config.getStringValue("THEME")) {
        case "BRIGHT":
          mainPane.setStyle("-fx-background-color:#ffffff;");
          mainPane.getStylesheets()
              .add(getClass().getResource("/styles/styleBrightTheme.css").toExternalForm());
          break;
        case "DARK":
          mainPane.setStyle("-fx-background-color: #383837;");
          mainPane.getStylesheets()
              .add(getClass().getResource("/styles/styleDarkTheme.css").toExternalForm());
          break;
        case "INTEGRA":
          mainPane.setStyle("-fx-background-color: #ffffff;");
          mainPane.getStylesheets()
              .add(getClass().getResource("/styles/styleIntegra.css").toExternalForm());
          break;
        case "THINC!":
          mainPane.setStyle("-fx-background-color: #D8EFFF;");
          mainPane.getStylesheets()
              .add(getClass().getResource("/styles/styleThinc.css").toExternalForm());
          break;
        default:
          break;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * override Initialize Method.
   */
  @FXML
  public void initialize() {
  }

  /**
   * override Exit Method.
   */
  @Override
  public void onExit() {

  }

  /**
   * Update Method used for starting game when server messages.
   *
   * @param gameController gameController
   * @param deltaTime deltaTime
   * @author tgutberl
   */
  @Override
  public void update(AbstractGameController gameController, double deltaTime) {

    if (this.gameSession.isGameStarted() && this.gameSession.isLocalPlayerTurn()) {
      gameController.setActiveUiController(
          new TutorialGameUiController(gameController, this.gameSession.getGame(), gameSession));
    } else {
      Debug.printMessage(this, "GameSession Controller " + this.gameSession);
    }

  }

  /**
   * Override Update Method.
   *
   * @param gameController GameController of game
   */
  @Override
  public void update(AbstractGameController gameController) {

  }
}
