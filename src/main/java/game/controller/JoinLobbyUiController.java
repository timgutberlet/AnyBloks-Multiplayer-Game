package game.controller;

import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import game.config.Config;
import game.model.Debug;
import game.model.GameSession;
import game.model.chat.ChatMessage;
import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

/**
 * JoinLobby Controller used when player already joined a Hostlobby.
 *
 * @author lbaudenb
 * @author tgutberl
 */

public class JoinLobbyUiController extends AbstractUiController {

  /**
   * Gamecontroller method used in application.
   */
  private final AbstractGameController gameController;
  /**
   * Gamessession controlling the game.
   */
  private final GameSession gameSession;
  /**
   * Arraylist saving what is already in Chat.
   */
  ArrayList<String> alreadyInChat;
  /**
   * Name of Host Player.
   */
  @FXML
  private Label hostPlayerName;
  /**
   * Name of Remote Player one.
   */
  @FXML
  private Label remotePlayer1;
  /**
   * Name of Remote Player two.
   */
  @FXML
  private Label remotePlayer2;
  /**
   * Name of Remote Player three.
   */
  @FXML
  private Label remotePlayer3;
  /**
   * Textarea with Chat messages.
   */
  @FXML
  private TextArea chat;
  /**
   * Textfield for input of chat messages.
   */
  @FXML
  private TextField chatInput;
  /**
   * Main Anchorpane used for resizing.
   */
  @FXML
  private AnchorPane mainPane;


  /**
   * Constructor of Lobycontroller Class. Used set Gamesession, Controller and to initialize.
   *
   * @param gameController Gamecontroller Object currently used
   * @author tgutberl
   */
  public JoinLobbyUiController(AbstractGameController gameController, GameSession gameSession) {

    super(gameController);
    this.gameController = gameController;
    this.init(super.root);
    this.gameSession = gameSession;
    alreadyInChat = new ArrayList<>();
    this.chat.setText("");
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
      loader.setLocation(getClass().getResource("/JoinLobbyView.fxml"));
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
          mainPane.setStyle("-fx-background-color: #C;");
          mainPane.getStylesheets()
              .add(getClass().getResource("/styles/styleThinc.css").toExternalForm());
          break;
        default:
          break;
      }
      chatInput.setOnKeyPressed(event -> {
        if (event.getCode().equals(KeyCode.ENTER)) {
          registerChatMessage();
        }
      });
      chatInput.setOnMousePressed(event -> {
        chatInput.setText("");
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method to get Back the MainMenuView.
   *
   * @author tgutberl
   */
  @FXML
  public void back() {

    System.out.println("Disconnecting client");
    gameSession.clientHandler.disconnectClient();
    System.out.println("Stopping session");
    gameSession.stopSession();
    System.out.println("Changing UI");
    gameController.setActiveUiController(new MainMenuUiController(gameController));
  }

  /**
   * Method getting called when chat message is entered and sends it to server.
   *
   * @author tgutberl
   */
  public void registerChatMessage() {
    if (chatInput.getText().length() > 0) {
      gameSession.addChatMessage(chatInput.getText());
      chatInput.setText("");
    }
  }

  /**
   * Override initialize.
   *
   * @author tgutberl
   */
  @FXML
  public void initialize() {
  }

  /**
   * Method to get Quit Menu - to End the Program.
   *
   * @author tgutberl
   */
  @FXML
  public void close() {
    try {
      this.gameSession.stopSession();
      gameController.getApplication().stop();
    } catch (Exception e) {
      e.printStackTrace();
    }
    Config.saveProperty();
    System.exit(0);
  }

  /**
   * Override onExit .
   *
   * @author tgutberl
   */
  @Override
  public void onExit() {
    System.exit(0);
  }

  /**
   * Update Method used for Chat Messages and starting the game.
   *
   * @param gameController Gamecontroller method
   * @param deltaTime      used for frame
   * @author tgutberl
   */
  @Override
  public void update(AbstractGameController gameController, double deltaTime) {

    if (this.gameSession.getPlayerList().size() == 2) {
      hostPlayerName.setText(this.gameSession.getPlayerList().get(0).getUsername() + " (HOST)");
      remotePlayer1.setText(this.gameSession.getPlayerList().get(1).getUsername());
      remotePlayer2.setText(" - ");
      remotePlayer3.setText(" - ");
    }
    if (this.gameSession.getPlayerList().size() == 3) {
      hostPlayerName.setText(this.gameSession.getPlayerList().get(0).getUsername() + " (HOST)");
      remotePlayer1.setText(this.gameSession.getPlayerList().get(1).getUsername());
      remotePlayer2.setText(this.gameSession.getPlayerList().get(2).getUsername());
      remotePlayer3.setText(" - ");
    }
    if (this.gameSession.getPlayerList().size() == 4) {
      hostPlayerName.setText(this.gameSession.getPlayerList().get(0).getUsername() + " (HOST)");
      remotePlayer1.setText(this.gameSession.getPlayerList().get(1).getUsername());
      remotePlayer2.setText(this.gameSession.getPlayerList().get(2).getUsername());
      remotePlayer3.setText(this.gameSession.getPlayerList().get(3).getUsername());
    }

    if (gameSession.getGotKicked()) {
      gameController.setActiveUiController(new KickInfoUiController(gameController));
    }

    //Debug.printMessage("Gamesession: " + this.gameSession);
    if (this.gameSession.isGameStarted()) {

      if (this.gameSession.getGame() == null) {
        System.out.println("gs is null");
      }
      gameController.setActiveUiController(
          new LocalGameUiController(gameController, this.gameSession.getGame(), gameSession));
      this.gameSession.setGameStarted(false);
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

    //Check if the host left, is so, return to the lobby
    if (gameSession.getHostQuit()) {
      //The host has left, so the user is sent to proper screen
      Debug.printMessage("THE HOST QUIT HAS BEEN DETECTED IN THE JOIN LOBBY CONTROLLER");
      gameController.setActiveUiController(new HostQuitUiController(gameController, gameSession));
      gameSession.setHostQuit(false);

    }

    if (gameSession.isPlayerKicked()) {
      //The host has left, so the user is sent to proper screen
      Debug.printMessage("The player has been kicked");
      gameController.setActiveUiController(new MainMenuUiController(gameController));
    }
  }

  /**
   * Override update Method.
   *
   * @param gameController GameController of game
   */
  @Override
  public void update(AbstractGameController gameController) {

  }
}
