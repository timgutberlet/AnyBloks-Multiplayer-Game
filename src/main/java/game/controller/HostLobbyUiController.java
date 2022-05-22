package game.controller;

import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import engine.handler.ErrorMessageHandler;
import game.config.Config;
import game.model.Debug;
import game.model.GameSession;
import game.model.chat.ChatMessage;
import game.model.gamemodes.GMClassic;
import game.model.gamemodes.GMDuo;
import game.model.gamemodes.GMJunior;
import game.model.gamemodes.GMTrigon;
import game.model.gamemodes.GameMode;
import game.model.player.Player;
import game.model.player.PlayerType;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import net.server.ClientHandler;
import net.server.HostServer;
import net.tests.NoLogging;
import net.transmission.EndpointClient;

/**
 * Class to Control Inputs for the HostLobby.
 *
 * @author tgutberl
 */
public class HostLobbyUiController extends AbstractUiController {

  /**
   * Italic Font build.
   */
  public static final Font ITALIC_FONT =
      Font.font(
          "Serif",
          FontPosture.ITALIC,
          Font.getDefault().getSize()
      );
  /**
   * Abstract gamecontroller class used for whole application.
   */
  private final AbstractGameController gameController;
  /**
   * cool Ai Player names.
   */
  private final String nameAiPlayer1 = "AlphaGo";
  /**
   * cool Ai Player names.
   */
  private final String nameAiPlayer2 = "DeepMind";
  /**
   * cool Ai Player names.
   */
  private final String nameAiPlayer3 = "Stockfish";
  /**
   * Gamesession variable used for server communication.
   */
  private final GameSession gameSession;
  /**
   * Button to play the game
   */
  @FXML
  Button playButton;
  /**
   * Error Textmessage.
   */
  @FXML
  Text gamemodeError;
  /**
   * roundcount text.
   */
  @FXML
  Label roundCount;
  /**
   * List for gamemodes to choose.
   */
  private ObservableList<String> list;
  /**
   * Endpoint client variable used for server establish.
   */
  private final EndpointClient client;
  /**
   * client handling used for communication handling.
   */
  private final ClientHandler clientHandler;
  /**
   * rounds gathered for multi round game.
   */
  private final List<ComboBox<String>> rounds = new ArrayList<>();
  /**
   * count of chosen rounds.
   */
  private int round = 1;
  /**
   * used for comparing already in chat messages to chat Object.
   */
  private final ArrayList<String> alreadyInChat;
  /**
   * Chat area.
   */
  @FXML
  private TextArea chat;
  /**
   * Text field for chat input.
   */
  @FXML
  private TextField chatInput;
  /**
   * Vbox for gamemodes.
   */
  @FXML
  private VBox box;
  /**
   * Anchorpane used for resizing.
   */
  @FXML
  private AnchorPane mainPane;
  /**
   * default ai that the player can set.
   */
  @FXML
  private Label aiDefault;
  /**
   * gamemodes to choose from.
   */
  @FXML
  private ComboBox<String> gameMode;
  /**
   * gamemodes that were chosen.
   */
  private final LinkedList<GameMode> gameModes = new LinkedList<>();
  /**
   * name of Hostplayer.
   */
  @FXML
  private Label nameHostPlayer;
  /**
   * Name of Player one.
   */
  @FXML
  private Label playerName1;
  /**
   * Name of Player one.
   */
  @FXML
  private Label playerName2;
  /**
   * Name of Player one.
   */
  @FXML
  private Label playerName3;
  /**
   * Label that shows IP
   */
  @FXML
  private Label informationIP;

  /**
   * Constructor of Lobycontroller Class. Used set Gamesession, Controller and to initialize.
   *
   * @param gameController Gamecontroller Object currently used
   */
  public HostLobbyUiController(AbstractGameController gameController) {
    super(gameController);
    //this.gameSession = new GameSession(new Player("You", PlayerType.HOST_PLAYER));
    this.gameController = gameController;
    this.init(super.root);
    alreadyInChat = new ArrayList<>();
    HostServer hostServer = new HostServer();
    try {
      //org.eclipse.jetty.util.log.Log.setLog(new NoLogging());
      hostServer.startWebsocket(8081);
      //Debug.printMessage("[testChatServer] Server is running");
      //TimeUnit.SECONDS.sleep(3);
    } catch (Exception e) {
      e.printStackTrace();
    }
    Player player = new Player(Config.getStringValue("HOSTPLAYER"), PlayerType.REMOTE_PLAYER);
    this.client = new EndpointClient(this, player);

    this.gameSession = client.getGameSession();
    this.gameSession.setLocalPlayer(player);
    this.clientHandler = client.getClientHandler();
    gameSession.setHostServer(hostServer);
    gameSession.setClientHandler(this.clientHandler);
  }

  /**
   * Method that is called when chatInput Enter was done. Sends Chatmessage to Server.
   */
  public void registerChatMessage() {
    if (chatInput.getText().length() > 0) {
      gameSession.addChatMessage(chatInput.getText());
      chatInput.setText("");
    } else {
    }
  }

  /**
   * Initalizing UI.
   */
  public void init(Group root) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/HostLobbyView.fxml"));
      loader.setControllerFactory(e -> this);
      root.getChildren().add(loader.load());
      updateSize(mainPane, gameController.getStage());
      gamemodeError.setText("");
      chatInput.setOnKeyPressed(event -> {
        if (event.getCode().equals(KeyCode.ENTER)) {
          registerChatMessage();
        }
      });
      chatInput.setOnMousePressed(event -> {
        chatInput.setText("");
      });
      setIP();
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
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * sets the IP label, so that users can get Join Info.
   */
  public void setIP() {
    try {
      this.informationIP.setText(Inet4Address.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      this.informationIP.setText("No IP Found");
      e.printStackTrace();
    }
  }

  /**
   * Kicks first Player
   */
  @FXML
  public void kickPlayer1() {

  }

  /**
   * kicks second player
   */
  @FXML
  public void kickPlayer2() {
    //Kicking a remote Player
    Player playerToKick = gameSession.getPlayerList().get(1);
    //Kick player out of username2Session
    gameSession.getInboundServerHandler().getServer().dropUser(playerToKick.getUsername());
  }

  /**
   * Kicks third player
   */
  @FXML
  public void kickPlayer3() {

  }

  /**
   * Copy to Clipboard Method, for the IP
   *
   * @author tgutberl
   */
  @FXML
  public void copyToClipboard() {
    String clipBoardString = informationIP.getText();
    StringSelection stringSelection = new StringSelection(clipBoardString);
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    clipboard.setContents(stringSelection, null);
  }

  /**
   * method to get back PlayView.
   */
  @FXML
  public void back() {
    gameController.setActiveUiController(new PlayUiController(gameController));
  }

  /**
   * Method to close the whole game
   */
  @FXML
  public void close() {
    gameSession.stopSession();
    gameController.setActiveUiController(new PlayUiController(gameController));
  }


  /**
   * Method that is called when the playGame Button was pushed. Starts the game.
   */
  @FXML
  public void playGame() {
    playButton.setText("Waiting for game to start");
    playButton.setDisable(true);
    ArrayList<Player> players = this.gameSession.getPlayerList();
    boolean error = false;

    switch (aiDefault.getText()) {
      case "Easy":
        this.gameSession.setDefaultAI(PlayerType.AI_EASY);
        break;
      case "Middle":
        this.gameSession.setDefaultAI(PlayerType.AI_MIDDLE);
        break;
      case "Hard":
        this.gameSession.setDefaultAI(PlayerType.AI_HARD);
        break;
      case "Godlike":
        this.gameSession.setDefaultAI(PlayerType.AI_GODLIKE);
        break;
    }
    Debug.printMessage("" + this.gameSession.getPlayerList().size());

    List<String> gameModes = new ArrayList<>();

    for (ComboBox<String> round : this.rounds) {
      gameModes.add(round.getValue());
    }

    for (String gameMode : gameModes) {

      switch (gameMode) {
        case "Classic":
          this.gameModes.add(new GMClassic());
          break;
        case "Duo":
          if (players.size() > 2) {
            ErrorMessageHandler.showErrorMessage("The GameMode Duo only allows for 2 players");
            error = true;
          }
          this.gameModes.add(new GMDuo());
          break;
        case "Junior":
          if (players.size() > 2) {
            ErrorMessageHandler.showErrorMessage("The GameMode Junior only allows for 2 players");
            error = true;
          }
          this.gameModes.add(new GMJunior());
          break;
        case "Trigon":
          this.gameModes.add(new GMTrigon());
          break;

        default:
          ErrorMessageHandler.showErrorMessage("No GameMode was selected");
          error = true;
      }
    }
    if (!error) {

      LinkedList<GameMode> gameList = this.gameModes;
      this.gameSession.setGameList(gameList);

      this.clientHandler.startLocalGame(gameList);

      try {
        TimeUnit.SECONDS.sleep(3);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      this.gameSession.startGame(this.gameModes.get(0));

      if (this.gameSession.getPlayerList().size() == 4) {
        playerName1.setText(this.gameSession.getPlayerList().get(1).getUsername());
        playerName2.setText(this.gameSession.getPlayerList().get(2).getUsername());
        playerName3.setText(this.gameSession.getPlayerList().get(3).getUsername());
      }
    }
  }

  /**
   * Initalizes the Gamemode Combobox.
   */
  private void initializeComboBox(ComboBox<String> comboBox) {
    list = FXCollections.observableArrayList("Classic", "Duo", "Junior", "Trigon");
    comboBox.setItems(list);
    comboBox.setValue("Classic");
  }

  /**
   * Method to add a round.
   */
  @FXML
  public void addRound() {
    if (gamemodeError.getText().length() > 0) {
      gamemodeError.setText("");
    }
    round++;
    HBox hBox = new HBox();
    hBox.setAlignment(Pos.CENTER);
    ComboBox<String> comboBox = new ComboBox<>();
    comboBox.setPrefWidth(150);
    comboBox.setPrefHeight(25);
    initializeComboBox(comboBox);
    roundCount.setText("" + round);
    hBox.getChildren().add(comboBox);
    rounds.add(comboBox);
    box.getChildren().add(hBox);
  }

  /**
   * Method to delete the round.
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
   * Method the increase the default AI difficulty.
   */
  @FXML
  private void increaseDifficulty() {
    increaseAi(aiDefault);
  }

  /**
   * Method the decrease the default AI difficulty.
   */
  @FXML
  private void decreaseDifficulty() {
    decreaseAi(aiDefault);
  }

  /**
   * Method to initalize the Gamemodes.
   */
  @FXML
  public void initialize() {
    list = FXCollections.observableArrayList("Classic", "Duo", "Junior", "Trigon");
    gameMode.setItems(list);
    gameMode.setValue("Classic");
    rounds.add(gameMode);
  }

  /**
   * Method to increase the Ai Difficulty.
   *
   * @param difficultyPlayer chosen Label of Player
   */
  private void increaseAi(Label difficultyPlayer) {
    switch (difficultyPlayer.getText()) {
      case "Easy":
        difficultyPlayer.setText("Middle");
        break;
      case "Middle":
        difficultyPlayer.setText("Hard");
        break;
      case "Hard":
        difficultyPlayer.setText("Godlike");
        break;
      case "Godlike":
        difficultyPlayer.setText("Easy");
        break;
    }
  }

  /**
   * Method to decrease the Ai Difficulty.
   *
   * @param difficultyPlayer chosen Label of Player
   */
  private void decreaseAi(Label difficultyPlayer) {
    switch (difficultyPlayer.getText()) {
      case "Easy":
        difficultyPlayer.setText("Godlike");
        break;
      case "Middle":
        difficultyPlayer.setText("Easy");
        break;
      case "Hard":
        difficultyPlayer.setText("Middle");
        break;
      case "Godlike":
        difficultyPlayer.setText("Hard");
        break;
    }
  }

  /**
   * Override to Exit.
   */
  @Override
  public void onExit() {

  }

  /**
   * Method to update Playerlist and Chat.
   *
   * @param gameController gamecontroller class
   * @param deltaTime      for Frames
   */
  @Override
  public void update(AbstractGameController gameController, double deltaTime) {

    if (this.gameSession.getPlayerList().size() > 1) {
      playerName1.setText(this.gameSession.getPlayerList().get(1).getUsername());
    }

    if (this.gameSession.isGameStarted() && this.gameSession.isLocalPlayerTurn()) {
      gameSession.setGameOver(false);

      gameController.setActiveUiController(
          new LocalGameUiController(gameController, this.gameSession.getGame(), gameSession));
      //this.gameSession.setGameStarted();
    } else {
      Debug.printMessage(this, "GameSession Controller " + this.gameSession);
    }
    String help = "";
    for (ChatMessage chatMessage : gameSession.getChat().getChatMessages()) {
      if (!alreadyInChat.contains(chatMessage.getTime() + " "
          + chatMessage.getUsername() + " : " + chatMessage.getMessage() + "\n")) {
        alreadyInChat.add(chatMessage.getTime() + " "
            + chatMessage.getUsername() + " : " + chatMessage.getMessage() + "\n");
        help += chatMessage.getTime().getHours() + ":" + chatMessage.getTime().getMinutes() + " "
            + chatMessage.getUsername() + " : " + chatMessage.getMessage() + "\n";
      }
    }
    this.chat.appendText(help);
  }

  /**
   * override update Method.
   *
   * @param gameController GameController of game
   */
  @Override
  public void update(AbstractGameController gameController) {

  }
}
