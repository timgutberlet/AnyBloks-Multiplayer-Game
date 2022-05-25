package game.controller;

import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import engine.handler.ErrorMessageHandler;
import game.config.Config;
import game.model.Debug;
import game.model.GameSession;
import game.model.gamemodes.GameMode;
import game.model.gamemodes.GameModeClassic;
import game.model.gamemodes.GameModeDuo;
import game.model.gamemodes.GameModeJunior;
import game.model.gamemodes.GameModeTrigon;
import game.model.player.Player;
import game.model.player.PlayerType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.packet.game.HostQuitPacket;
import net.server.ClientHandler;
import net.server.HostServer;
import net.transmission.EndpointClient;

/**
 * Controller used to let the user start a local Game and set all AIs.
 *
 * @author lbaudenb
 * @author tgutberl
 */
public class LocalLobbyUiController extends AbstractUiController {

  /**
   * Gamecontroller used in Application.
   */
  private final AbstractGameController gameController;
  /**
   * Cool AI name.
   */
  private final String nameAiPlayer1 = Config.getStringValue("AIPLAYER1");
  /**
   * Cool AI name.
   */
  private final String nameAiPlayer2 = Config.getStringValue("AIPLAYER2");
  /**
   * Cool AI name.
   */
  private final String nameAiPlayer3 = Config.getStringValue("AIPLAYER3");
  /**
   * Gamesession for setting players.
   */
  private final GameSession gameSession;
  /**
   * Gamemode list.
   */
  private final LinkedList<GameMode> gameModes = new LinkedList<>();
  /**
   * Set Ai players List.
   */
  private final LinkedList<PlayerType> aiPlayers = new LinkedList<>();
  /**
   * Combobox where user can choose gamemode.
   */
  private final List<ComboBox<String>> rounds = new ArrayList<>();
  /**
   * Endpoint for server-client communication.
   */
  private final EndpointClient client;
  /**
   * Clienthandler for input to Server.
   */
  private final ClientHandler clientHandler;
  /**
   * Button to start Game.
   */
  @FXML
  Button playButton;
  /**
   * Main Anchorpane used for resizing.
   */
  @FXML
  AnchorPane mainPane;
  /**
   * Count of rounds.
   */
  @FXML
  Label roundCount;
  /**
   * Name of the user that is controlling game.
   */
  @FXML
  Label youPlayer;
  /**
   * Text to inform player of errors.
   */
  @FXML
  Text gamemodeError;
  /**
   * Rectangle used to disable Ui.
   */
  private Rectangle disableUi;
  /**
   * List where the gamemodes are set in.
   */
  private ObservableList<String> list;
  /**
   * Round count.
   */
  private int round = 1;
  /**
   * Player one name.
   */
  @FXML
  private Label player1;
  /**
   * Player two name.
   */
  @FXML
  private Label player2;
  /**
   * Player three name.
   */
  @FXML
  private Label player3;
  /**
   * Vbox containing Gamemodes.
   */
  @FXML
  private VBox box;
  /**
   * List of Gamemodes.
   */
  @FXML
  private ComboBox<String> gameMode;
  /**
   * Name of AI1.
   */
  @FXML
  private Label difficultyPlayer1;
  /**
   * Difficulty of AI2.
   */
  @FXML
  private Label difficultyPlayer2;
  /**
   * Difficulty of AI3.
   */
  @FXML
  private Label difficultyPlayer3;

  /**
   * Constructor of Lobycontroller Class. Used set Gamesession, Controller and to initialize.
   *
   * @param gameController Gamecontroller Object currently used
   * @author tgutberl
   * @author lbaudenb
   */
  public LocalLobbyUiController(AbstractGameController gameController) {
    super(gameController);
    this.gameController = gameController;
    this.init(super.root);

    HostServer hostServer = new HostServer();
    try {
      //org.eclipse.jetty.util.log.Log.setLog(new NoLogging());
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
    this.gameSession.setHostServer(hostServer);
    this.clientHandler = client.getClientHandler();
    gameSession.setClientHandler(this.clientHandler);

  }

  /**
   * Initalizing UI.
   *
   * @param root Root parameter
   * @author tgutberl
   * @author lbaudenb
   */
  public void init(Group root) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/LocalLobbyView.fxml"));
      loader.setControllerFactory(e -> this);
      root.getChildren().add(loader.load());
      gamemodeError.setText("");
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
      youPlayer.setText(Config.getStringValue("HOSTPLAYER"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method to get back to PlayView.
   *
   * @author tgutberl
   */
  @FXML
  public void back() {

    this.gameSession.getClientHandler().getClient()
        .sendToServer(new WrappedPacket(PacketType.HOST_QUIT_PACKET, new HostQuitPacket()));

    Debug.printMessage("change UI controller!");

    gameController.setActiveUiController(
        new LocalQuitUiController(gameController, gameSession, true));

  }

  /**
   * Method that is called when game is started.
   *
   * @author tgutberl
   * @author lbaudenb
   */
  @FXML
  public void playGame() {

    disableUi = new Rectangle(gameController.getStage().getWidth(),
        gameController.getStage().getHeight());
    disableUi.setFill(Color.TRANSPARENT);
    root.getChildren().add(disableUi);

    playButton.setText("Waiting for game to start!");
    playButton.setDisable(true);

    this.gameSession.setDefaultAi(PlayerType.AI_MIDDLE);
    boolean error = false;

    if (!player1.getText().equals("-")) {
      switch (difficultyPlayer1.getText()) {
        case "Easy":
          aiPlayers.add(PlayerType.AI_EASY);
          break;
        case "Middle":
          aiPlayers.add(PlayerType.AI_MIDDLE);
          break;
        case "Hard":
          aiPlayers.add(PlayerType.AI_HARD);
          break;
        case "Godlike":
          aiPlayers.add(PlayerType.AI_GODLIKE);
          break;
        default:
          break;
      }
    }
    if (!player2.getText().equals("-")) {
      switch (difficultyPlayer2.getText()) {
        case "Easy":
          aiPlayers.add(PlayerType.AI_EASY);
          break;
        case "Middle":
          aiPlayers.add(PlayerType.AI_MIDDLE);
          break;
        case "Hard":
          aiPlayers.add(PlayerType.AI_HARD);
          break;
        case "Godlike":
          aiPlayers.add(PlayerType.AI_GODLIKE);
          break;
        default:
          break;
      }
    }

    if (!player3.getText().equals("-")) {
      switch (difficultyPlayer3.getText()) {
        case "Easy":
          aiPlayers.add(PlayerType.AI_EASY);
          break;
        case "Middle":
          aiPlayers.add(PlayerType.AI_MIDDLE);
          break;
        case "Hard":
          aiPlayers.add(PlayerType.AI_HARD);
          break;
        case "Godlike":
          aiPlayers.add(PlayerType.AI_GODLIKE);
          break;
        default:
          break;
      }
    }
    this.gameSession.setAiPlayers(aiPlayers);

    List<String> gameModes = new ArrayList<>();

    for (ComboBox<String> gameRound : this.rounds) {
      gameModes.add(gameRound.getValue());
    }

    for (String gameMode : gameModes) {

      switch (gameMode) {
        case "Classic":
          this.gameModes.add(new GameModeClassic());
          break;
        case "Duo":
          if (aiPlayers.size() > 1) {
            ErrorMessageHandler.showErrorMessage("The GameMode Duo only allows for 2 players");
            error = true;
          }
          this.gameModes.add(new GameModeDuo());
          break;
        case "Junior":
          if (aiPlayers.size() > 2) {
            ErrorMessageHandler.showErrorMessage("The GameMode Junior only allows for 2 players");
            error = true;
          }
          this.gameModes.add(new GameModeJunior());
          break;
        case "Trigon":
          this.gameModes.add(new GameModeTrigon());
          break;

        default:
          ErrorMessageHandler.showErrorMessage("No GameMode was selected");
          error = true;
          break;
      }
    }

    if (!error) {
      LinkedList<GameMode> gameList = this.gameModes;
      this.gameSession.setGameList(gameList);

      this.clientHandler.startLocalGame(gameList, aiPlayers);

      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      Debug.printMessage(this, "Game has been set");
      Debug.printMessage("Hallo3");
      Debug.printMessage("Laenge der Liste: " + this.gameSession.getPlayerList().size());

      if (this.gameSession.getPlayerList().size() == 4) {
        if (player1.getText().equals("-")) {
          player1.setText(this.gameSession.getPlayerList().get(1).getUsername());
        }
        if (player2.getText().equals("-")) {
          player2.setText(this.gameSession.getPlayerList().get(2).getUsername());
        }
        if (player3.getText().equals("-")) {
          player3.setText(this.gameSession.getPlayerList().get(3).getUsername());
        }
      }
    } else {
      root.getChildren().remove(disableUi);
      aiPlayers.clear();
      this.gameModes.clear();
      gameSession.clearAiPlayers();
      playButton.setText("Play");
      playButton.setDisable(false);
    }
  }

  /**
   * Method to initializing Gamemode Combobox.
   *
   * @param comboBox box
   * @author tgutberl
   */
  private void initializeComboBox(ComboBox<String> comboBox) {
    list = FXCollections.observableArrayList("Classic", "Duo", "Junior", "Trigon");
    comboBox.setItems(list);
    comboBox.setValue("Classic");
  }

  /**
   * Method to add a round.
   *
   * @author tgutberl
   */
  @FXML
  public void addRound() {
    if (gamemodeError.getText().length() > 0) {
      gamemodeError.setText("");
    }
    round++;
    HBox hbox = new HBox();
    hbox.setAlignment(Pos.CENTER);
    ComboBox<String> comboBox = new ComboBox<>();
    comboBox.setPrefWidth(150);
    comboBox.setPrefHeight(25);
    initializeComboBox(comboBox);
    roundCount.setText("" + round);
    hbox.getChildren().add(comboBox);
    rounds.add(comboBox);
    box.getChildren().add(hbox);
  }

  /**
   * Method to delete a round.
   *
   * @author tgutberl
   */
  @FXML
  public void deleteRound() {
    if (round > 1) {
      round--;
      roundCount.setText("" + round);
      box.getChildren().remove(box.getChildren().get(round));
      rounds.remove(round);
    } else {
      gamemodeError.setText("You need to have at least one Round!");
    }
  }

  /**
   * Method to increase difficulty of first Ai.
   *
   * @author tgutberl
   */
  @FXML
  private void increaseDifficulty1() {
    Debug.printMessage("increase!!!!");
    increaseAi(difficultyPlayer1, nameAiPlayer1, player1);
  }

  /**
   * Method to increase difficulty of second Ai.
   *
   * @author tgutberl
   */
  @FXML
  private void increaseDifficulty2() {
    Debug.printMessage("increase!!!!");
    increaseAi(difficultyPlayer2, nameAiPlayer2, player2);
  }

  /**
   * Method to increase difficulty of third Ai.
   *
   * @author tgutberl
   */
  @FXML
  private void increaseDifficulty3() {
    Debug.printMessage("increase!!!!");
    increaseAi(difficultyPlayer3, nameAiPlayer3, player3);
  }

  /**
   * Method to decrease difficulty of first Ai.
   *
   * @author tgutberl
   */
  @FXML
  private void decreaseDifficulty1() {
    Debug.printMessage("decrease!!!!");
    decreaseAi(difficultyPlayer1, nameAiPlayer1, player1);
  }

  /**
   * Method to decrease difficulty of second Ai.
   *
   * @author tgutberl
   */
  @FXML
  private void decreaseDifficulty2() {
    Debug.printMessage("decrease!!!!");
    decreaseAi(difficultyPlayer2, nameAiPlayer2, player2);
  }

  /**
   * Method to decrease difficulty of third Ai.
   *
   * @author tgutberl
   */
  @FXML
  private void decreaseDifficulty3() {
    Debug.printMessage("decrease!!!!!");
    decreaseAi(difficultyPlayer3, nameAiPlayer3, player3);
  }

  /**
   * Method to initialize Gamemodes.
   *
   * @author tgutberl
   */
  @FXML
  public void initialize() {
    list = FXCollections.observableArrayList("Classic", "Duo", "Junior", "Trigon");
    gameMode.setItems(list);
    gameMode.setValue("Classic");
    rounds.add(gameMode);
  }

  /**
   * Method to increase Ai difficulty.
   *
   * @param difficultyPlayer difficulty
   * @param name             name of Ai
   * @param player           playerType
   * @author tgutberl
   */
  private void increaseAi(Label difficultyPlayer, String name, Label player) {
    if (difficultyPlayer.getText().equals("None")) {
      difficultyPlayer.setText("Easy");
      player.setText(name);
    } else {
      switch (difficultyPlayer.getText()) {
        case "Easy":
          difficultyPlayer.setText("Middle");
          player.setText(name);
          break;
        case "Middle":
          difficultyPlayer.setText("Hard");
          player.setText(name);
          break;
        case "Hard":
          difficultyPlayer.setText("Godlike");
          player.setText(name);
          break;
        case "Godlike":
          difficultyPlayer.setText("None");
          player.setText("-");
          break;
        default:
          break;
      }
    }
  }

  /**
   * Method to decrease Ai difficulty.
   *
   * @param difficultyPlayer difficulty
   * @param name             name of Ai
   * @param player           playerType
   * @author tgutberl
   */
  private void decreaseAi(Label difficultyPlayer, String name, Label player) {
    switch (difficultyPlayer.getText()) {
      case "Easy":
        difficultyPlayer.setText("None");
        player.setText("-");
        break;
      case "Middle":
        difficultyPlayer.setText("Easy");
        player.setText(name);
        break;
      case "Hard":
        difficultyPlayer.setText("Middle");
        player.setText(name);
        break;
      case "Godlike":
        difficultyPlayer.setText("Hard");
        player.setText(name);
        break;
      default:
        break;
    }
  }

  /**
   * Method to get Quit Menu - to End the Program.
   *
   * @author tgutberl
   */
  @FXML
  public void close() {
    Config.saveProperty();
    System.exit(0);
  }

  /**
   * Override onExit Method.
   *
   * @author tgutberl
   */
  @Override
  public void onExit() {
    Config.saveProperty();
    System.exit(0);
  }

  /**
   * Update Method used for starting game when server messages.
   *
   * @param gameController gameController
   * @param deltaTime      deltaTime
   * @author tgutberl
   */
  @Override
  public void update(AbstractGameController gameController, double deltaTime) {

    if (this.gameSession.isGameStarted() && this.gameSession.isLocalPlayerTurn()) {
      gameController.setActiveUiController(
          new LocalGameUiController(gameController, this.gameSession.getGame(), gameSession));
    } else {
      Debug.printMessage(this, "GameSession Controller " + this.gameSession);
    }

  }

  /**
   * Override Update Method.
   *
   * @param gameController GameController of game
   * @author lbaudenb
   */
  @Override
  public void update(AbstractGameController gameController) {

  }
}
