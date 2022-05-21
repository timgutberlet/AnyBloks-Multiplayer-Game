package game.controller;

import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import engine.handler.ThreadHandler;
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
 * @author lbaudenb
 * @author tgutberl
 */

public class JoinLobbyUiController extends AbstractUiController {

  private final AbstractGameController gameController;

  private final GameSession gameSession;

  private int chatMessageLength = 0;

  ArrayList<String> alreadyInChat;

  @FXML
  private TextArea chat;

  @FXML
  private TextField chatInput;

  @FXML
  private Label player1;

  @FXML
  private AnchorPane mainPane;


  /**
   * Constructor of Lobycontroller Class. Used set Gamesession, Controller and to initialize
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

  @FXML
  public void back() {
    gameController.setActiveUiController(new MainMenuUiController(gameController));
  }


  public void registerChatMessage() {
    if (chatInput.getText().length() > 0) {
      gameSession.addChatMessage(chatInput.getText());
      chatInput.setText("");
    } else {
    }
  }

  @FXML
  public void initialize() {
  }


  @Override
  public void onExit() {

  }

  @Override
  public void update(AbstractGameController gameController, double deltaTime) {
    System.out.println("Gamesession: " + this.gameSession);
    if (this.gameSession.isGameStarted()) {
      ThreadHandler threadHelp = new ThreadHandler(this.gameSession);
      gameController.setActiveUiController(
          new LocalGameUiController(gameController, this.gameSession.getGame(), gameSession,
              threadHelp));
    } else {
      Debug.printMessage(this, "GameSession Controller " + this.gameSession);
    }
    String help = "";
    for (ChatMessage chatMessage : gameSession.getChat().getChatMessages()) {
      if(!alreadyInChat.contains(chatMessage.getTime() + " "
          + chatMessage.getUsername() + " : " + chatMessage.getMessage() + "\n")){
        alreadyInChat.add(chatMessage.getTime() + " "
            + chatMessage.getUsername() + " : " + chatMessage.getMessage() + "\n");
        help += chatMessage.getTime().getHours() + ":" + chatMessage.getTime().getMinutes() + " "
            + chatMessage.getUsername() + " : " + chatMessage.getMessage() + "\n";
      }
    }
    this.chat.appendText(help);
  }

  @Override
  public void update(AbstractGameController gameController) {

  }
}
