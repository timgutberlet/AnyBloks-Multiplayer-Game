package game.controller;

import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import engine.handler.ErrorMessageHandler;
import engine.handler.ThreadHandler;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.ComboBox;
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

  private final String nameAiPlayer1 = "Bob";
  private final String nameAiPlayer2 = "Anna";
  private final String nameAiPlayer3 = "Tom";

  private final GameSession gameSession;

  private GameMode gameMode;
  private ObservableList<String> list;


  private int chatMessageLength = 0;

  @FXML
  private TextArea chat;

  @FXML
  private TextField chatInput;

  @FXML
  private Label player1;

  @FXML
  private AnchorPane mainPane;

  @FXML
  private Label player2;

  @FXML
  private Label player3;

  @FXML
  private ComboBox<String> gameModes;

  @FXML
  private Label nameHostPlayer;

  @FXML
  private Label namePlayer1;

  @FXML
  private Label namePlayer2;

  @FXML
  private Label namePlayer3;

  @FXML
  private Label difficultyPlayer1;

  @FXML
  private Label difficultyPlayer2;

  @FXML
  private Label difficultyPlayer3;

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
  }

  public void init(Group root) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/JoinLobbyView.fxml"));
      loader.setControllerFactory(e -> this);
      root.getChildren().add(loader.load());
      updateSize(mainPane, gameController.getStage());
      //Sets the Theme, according to the settings
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
      chatInput.setOnKeyPressed(event -> {
        if(event.getCode().equals(KeyCode.ENTER)){
          registerChatMessage();
        }
      });
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void back() {
    gameController.setActiveUiController(new MainMenuUiController(gameController));
  }

  @FXML
  public void play() {
    ArrayList<Player> players = this.gameSession.getPlayerList();
    boolean error = false;

    if (!namePlayer1.equals("-")) {
      switch (difficultyPlayer1.getText()) {
        case "Easy":
          this.gameSession.setDefaultAI(PlayerType.AI_EASY);
          //this.gameSession.addPlayer(new Player(nameAiPlayer1, PlayerType.AI_EASY));
          //players.add(new Player(nameAiPlayer1, PlayerType.AI_EASY));
          break;
        case "Middle":
          this.gameSession.setDefaultAI(PlayerType.AI_MIDDLE);
          //this.gameSession.addPlayer(new Player(nameAiPlayer1, PlayerType.AI_MIDDLE));
          //players.add(new Player(nameAiPlayer1, PlayerType.AI_MIDDLE));
          break;
        case "Hard":
          this.gameSession.setDefaultAI(PlayerType.AI_HARD);
          //this.gameSession.addPlayer(new Player(nameAiPlayer1, PlayerType.AI_HARD));
          //players.add(new Player(nameAiPlayer1, PlayerType.AI_HARD));
      }
      Debug.printMessage("" + this.gameSession.getPlayerList().size());
    }

    //this.gameSession.addHost(new Player("You", PlayerType.AI_EASY));

    String gameMode = gameModes.getValue();

    switch (gameMode) {
      case "Classic":
        this.gameMode = new GMClassic();
        break;
      case "Duo":
        if (players.size() > 2) {
          ErrorMessageHandler.showErrorMessage("The GameMode Duo only allows for 2 players");
          error = true;
        }
        this.gameMode = new GMDuo();
        break;
      case "Junior":
        if (players.size() > 2) {
          ErrorMessageHandler.showErrorMessage("The GameMode Junior only allows for 2 players");
          error = true;
        }
        this.gameMode = new GMJunior();
        break;
      case "Trigon":
        this.gameMode = new GMTrigon();
        break;

      default:
        ErrorMessageHandler.showErrorMessage("No GameMode was selected");
        error = true;
    }
//    if (players.size() < 2) {
//      ErrorMessageHandler.showErrorMessage(
//          "The GameMode " + this.gameMode.getName() + " requires at least 2 players");
//      error = true;
//    }
    if (!error) {
      //for(Player p: players){
      //  p.setSession(this.session);
      //}
      //Game game = new Game(players, this.gameMode);
      Debug.printMessage("Hallo");
      //this.session.setGame(new Game(this.session, this.gameMode));
      Debug.printMessage("Hallo2");


      LinkedList<GameMode> gameList = new LinkedList<>();
      gameList.add(new GMTrigon());
      gameList.add(this.gameMode);
      //this.gameSession.setGameList(gameList);

      this.gameSession.clientHandler.startLocalGame(gameList);


      try {
        TimeUnit.SECONDS.sleep(3);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }





      Debug.printMessage(this,"Game has been set");

      this.gameSession.startGame(this.gameMode);
      Debug.printMessage("Hallo3");
      Debug.printMessage("Laenge der Liste: "+this.gameSession.getPlayerList().size());

      if(this.gameSession.getPlayerList().size()==4){
        player1.setText(this.gameSession.getPlayerList().get(1).getUsername());
        player2.setText(this.gameSession.getPlayerList().get(2).getUsername());
        player3.setText(this.gameSession.getPlayerList().get(3).getUsername());
      }


      //game.startGame();
      //ThreadHandler threadHelp = new ThreadHandler(this.gameSession);
      //gameController.setActiveUiController(
      //    new LocalGameUiController(gameController, this.gameSession.getGame(), gameSession, threadHelp));
    }
  }

  public void registerChatMessage() {
    if (chatInput.getText().length() > 0) {
      gameSession.addChatMessage(chatInput.getText());
    } else {
    }
  }

  @FXML
  public void initialize() {
    list = FXCollections.observableArrayList("Classic", "Duo", "Junior", "Trigon");
    gameModes.setItems(list);
  }


  @Override
  public void onExit() {

  }

  @Override
  public void update(AbstractGameController gameController, double deltaTime) {

    if(this.gameSession.isGameStarted()){
      ThreadHandler threadHelp = new ThreadHandler(this.gameSession);
      gameController.setActiveUiController(
          new LocalGameUiController(gameController, this.gameSession.getGame(), gameSession, threadHelp));
      //this.gameSession.setGameStarted();
    } else {
      Debug.printMessage(this, "GameSession Controller "+ this.gameSession);
    }

      if (gameSession.getChat().getChatMessages().size() > chatMessageLength) {
        chat.setText("");
        for (ChatMessage chatMessage : gameSession.getChat().getChatMessages()) {
          chat.appendText(
              chatMessage.getTime().getHours() + ":" + chatMessage.getTime().getHours() + " "
                  + chatMessage.getUsername() + " : " + chatMessage.getMessage() + "\n");
        }
        chatMessageLength = gameSession.getChat().getChatMessages().size();
      }

  }

  @Override
  public void update(AbstractGameController gameController) {

  }
}
