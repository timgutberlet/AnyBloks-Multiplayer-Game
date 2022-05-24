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
import game.scores.LobbyScoreBoard;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.packet.game.HostQuitPacket;
import net.server.ClientHandler;
import net.server.HostServer;
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
  private final String nameAiPlayer1 = Config.getStringValue("AIPLAYER1");
  /**
   * cool Ai Player names.
   */
  private final String nameAiPlayer2 = Config.getStringValue("AIPLAYER2");
  /**
   * cool Ai Player names.
   */
  private final String nameAiPlayer3 = Config.getStringValue("AIPLAYER3");
  /**
   * Gamesession variable used for server communication.
   */
  private final GameSession gameSession;
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
   * used for comparing already in chat messages to chat Object.
   */
  private final ArrayList<String> alreadyInChat;
  /**
   * gamemodes that were chosen.
   */
  private final LinkedList<GameMode> gameModes = new LinkedList<>();
  /**
   * Button to play the game.
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
   * Rectangle used to disable Ui.
   */
  private Rectangle disableUi;
  /**
   * List for gamemodes to choose.
   */
  private ObservableList<String> list;
  /**
   * count of chosen rounds.
   */
  private int round = 1;
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
   * name of Hostplayer.
   */
  @FXML
  private Label hostPlayerName;
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
   * Label that shows IP.
   */
  @FXML
  private Label informationIp;
  /**
   * Trashbin Button that is used to kick a player
   */
  @FXML
  private Button trashBin1;
  /**
   * Trashbin Button that is used to kick a player (No. 2)
   */
  @FXML
  private Button trashBin2;
  /**
   * Trashbin Button that is used to kick a player (No. 3)
   */
  @FXML
  private Button trashBin3;
  /**
   * Game number Label, that shows the number of games, that have been Played on the server.
   */
  @FXML
  private Label gameNumber;
  /**
   * Game number Label, that shows the number of games, that have been Played on the server.
   */
  @FXML
  private Label bestPlayer;
  /**
   * Game number Label, that shows the number of games, that have been Played on the server.
   */
  @FXML
  private Label secondBestPlayer;
  /**
   * Game number Label, that shows the number of games, that have been Played on the server.
   */
  @FXML
  private Label thirdBestPlayer;
  /**
   * Lobbyscoreboard element, to get Data out of the Database.
   */
  private LobbyScoreBoard lobbyScoreBoard;

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
      // org.eclipse.jetty.util.log.Log.setLog(new NoLogging());
      hostServer.startWebsocket(8081);
      //Debug.printMessage("[testChatServer] Server is running");
      //TimeUnit.SECONDS.sleep(3);
    } catch (Exception e) {
      e.printStackTrace();
    }
    Player player = new Player(Config.getStringValue("HOSTPLAYER"), PlayerType.AI_HARD);
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
      setIp();
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
          trashBin1.setStyle(
              "-fx-alignment: BASELINE_CENTER; -fx-text-fill: #F9FAFE; -fx-background-color: #FFFFFF; -fx-background-radius: 10px; -fx-background-insets: 0; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 10, 0.5, 0.0, 0.0);");
          trashBin2.setStyle(
              "-fx-alignment: BASELINE_CENTER; -fx-text-fill: #F9FAFE; -fx-background-color: #FFFFFF; -fx-background-radius: 10px; -fx-background-insets: 0; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 10, 0.5, 0.0, 0.0);");
          trashBin3.setStyle(
              "-fx-alignment: BASELINE_CENTER; -fx-text-fill: #F9FAFE; -fx-background-color: #FFFFFF; -fx-background-radius: 10px; -fx-background-insets: 0; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 10, 0.5, 0.0, 0.0);");
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
   * sets the IP label, so that users can get Join Info.
   */
  public void setIp() {
    try {
      this.informationIp.setText(Inet4Address.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      this.informationIp.setText("No IP Found");
      e.printStackTrace();
    }
  }

  /**
   * Kicks first Player.
   */
  @FXML
  public void kickPlayer1() {
    gameSession.clientHandler.kickClient(gameSession.getPlayerList().get(1));
  }

  /**
   * kicks second player.
   */
  @FXML
  public void kickPlayer2() {
    gameSession.clientHandler.kickClient(gameSession.getPlayerList().get(2));
  }

  /**
   * Kicks third player.
   */
  @FXML
  public void kickPlayer3() {
    gameSession.clientHandler.kickClient(gameSession.getPlayerList().get(3));
  }

  /**
   * Copy to Clipboard Method, for the IP.
   *
   * @author tgutberl
   */
  @FXML
  public void copyToClipboard() {
    String clipBoardString = informationIp.getText();
    StringSelection stringSelection = new StringSelection(clipBoardString);
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    clipboard.setContents(stringSelection, null);
  }

  /**
   * method to get back PlayView.
   */
  @FXML
  public void back() {
    Debug.printMessage("THE HOST TRIED TO GO BACK!");
    Debug.printMessage("And kmows he is Host so he broadcasts!");

    this.gameSession.getClientHandler().getClient()
        .sendToServer(new WrappedPacket(PacketType.HOST_QUIT_PACKET, new HostQuitPacket()));

    Debug.printMessage("change UI controller!");

    gameController.setActiveUiController(
        new LocalQuitUiController(gameController, gameSession, true));
  }

  /**
   * Method to close the whole game.
   */
  @FXML
  public void close() {
    Config.saveProperty();
    System.exit(0);
  }


  /**
   * Method that is called when the playGame Button was pushed. Starts the game.
   */
  @FXML
  public void playGame() {

    disableUi = new Rectangle(gameController.getStage().getWidth(),
        gameController.getStage().getHeight());
    disableUi.setFill(Color.TRANSPARENT);
    root.getChildren().add(disableUi);

    playButton.setText("Waiting for game to start");
    playButton.setDisable(true);
    ArrayList<Player> players = this.gameSession.getPlayerList();
    boolean error = false;

    PlayerType defaultAi = null;

    switch (aiDefault.getText()) {
      case "Easy":
        defaultAi = PlayerType.AI_EASY;
        break;
      case "Middle":
        defaultAi = PlayerType.AI_MIDDLE;
        break;
      case "Hard":
        defaultAi = PlayerType.AI_HARD;
        break;
      case "Godlike":
        defaultAi = PlayerType.AI_GODLIKE;
        break;
      default:
        break;
    }

    this.gameSession.setDefaultAI(defaultAi);

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

      this.clientHandler.startLocalGame(gameList, defaultAi);

      try {
        TimeUnit.SECONDS.sleep(3);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      if (this.gameSession.getPlayerList().size() == 4) {
        playerName1.setText(this.gameSession.getPlayerList().get(1).getUsername());
        playerName2.setText(this.gameSession.getPlayerList().get(2).getUsername());
        playerName3.setText(this.gameSession.getPlayerList().get(3).getUsername());
      }
    } else {
      root.getChildren().remove(disableUi);
      this.gameModes.clear();
      playButton.setText("Play");
      playButton.setDisable(false);
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
      default:
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
      default:
        break;
    }
  }

  /**
   * Override to Exit.
   */
  @Override
  public void onExit() {
    System.exit(0);
  }

  /**
   * Method to update Playerlist and Chat.
   *
   * @param gameController gamecontroller class
   * @param deltaTime      for Frames
   */
  @Override
  public void update(AbstractGameController gameController, double deltaTime) {

    lobbyScoreBoard = this.gameSession.getLobbyScoreBoard();
    int gamesPlayed = 0;
    String bestPlayer1String = "";
    int bestPlayerScore = 0;
    String bestPlayer2String = "";
    int bestPlayer2Score = 0;
    String bestPlayer3String = "";
    int bestPlayer3Score = 0;
    HashMap<String, Integer> scoreMap = null;
    if (lobbyScoreBoard != null) {
      gamesPlayed = lobbyScoreBoard.gamesPlayedOnServer;
      scoreMap = lobbyScoreBoard.playerScores;
      if (scoreMap.size() > 0) {
        for (String playerKey : scoreMap.keySet()) {
          if (scoreMap.get(playerKey) > bestPlayerScore) {
            bestPlayer1String = playerKey;
            bestPlayerScore = scoreMap.get(playerKey);
          }
        }
        scoreMap.remove(bestPlayer1String);
      }
      if (scoreMap.size() > 0) {
        for (String playerKey : scoreMap.keySet()) {
          if (scoreMap.get(playerKey) > bestPlayerScore) {
            bestPlayer2String = playerKey;
            bestPlayer2Score = scoreMap.get(playerKey);
          }
        }
        scoreMap.remove(bestPlayer2String);
      }
      if (scoreMap.size() > 0) {
        for (String playerKey : scoreMap.keySet()) {
          if (scoreMap.get(playerKey) > bestPlayerScore) {
            bestPlayer3String = playerKey;
            bestPlayer3Score = scoreMap.get(playerKey);
          }
        }
        scoreMap.remove(bestPlayer3String);
      }
    }
    this.gameNumber.setText(gamesPlayed + "");
    bestPlayer.setText(bestPlayer1String);
    secondBestPlayer.setText(bestPlayer2String);
    thirdBestPlayer.setText(bestPlayer3String);

    if (this.gameSession.getPlayerList().size() == 1) {
      hostPlayerName.setText(this.gameSession.getPlayerList().get(0).getUsername() + " (HOST)");
    }

    if (this.gameSession.getPlayerList().size() == 2) {
      hostPlayerName.setText(this.gameSession.getPlayerList().get(0).getUsername() + " (HOST)");
      playerName1.setText(this.gameSession.getPlayerList().get(1).getUsername());
      playerName2.setText(" - ");
      playerName3.setText(" - ");
    }
    if (this.gameSession.getPlayerList().size() == 3) {
      hostPlayerName.setText(this.gameSession.getPlayerList().get(0).getUsername() + " (HOST)");
      playerName1.setText(this.gameSession.getPlayerList().get(1).getUsername());
      playerName2.setText(this.gameSession.getPlayerList().get(2).getUsername());
      playerName3.setText(" - ");
    }
    if (this.gameSession.getPlayerList().size() == 4) {
      hostPlayerName.setText(this.gameSession.getPlayerList().get(0).getUsername() + " (HOST)");
      playerName1.setText(this.gameSession.getPlayerList().get(1).getUsername());
      playerName2.setText(this.gameSession.getPlayerList().get(2).getUsername());
      playerName3.setText(this.gameSession.getPlayerList().get(3).getUsername());
    }

    if (this.gameSession.isGameStarted() && this.gameSession.isLocalPlayerTurn()) {
      gameSession.setGameOver(false);

      gameController.setActiveUiController(
          new LocalGameUiController(gameController, this.gameSession.getGame(), gameSession));
      this.gameSession.setGameStarted(false);
      //this.gameSession.setGameStarted();
    } else {
      //Debug.printMessage(this, "GameSession Controller " + this.gameSession);
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
