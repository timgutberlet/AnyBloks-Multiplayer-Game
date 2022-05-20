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
 * Class to Control Inputs for the HostLobby
 *
 *
 * @author tgutberl
 */
public class HostLobbyUiController extends AbstractUiController {

  private final AbstractGameController gameController;

  private final String nameAiPlayer1 = "Bob";
  private final String nameAiPlayer2 = "Anna";
  private final String nameAiPlayer3 = "Tom";

  private final GameSession gameSession;


  private ObservableList<String> list;

  private int chatMessageLength = 0;

  private EndpointClient client;
  private ClientHandler clientHandler;

  private List<ComboBox<String>> rounds = new ArrayList<>();
  private int round = 1;

  @FXML
  private TextArea chat;

  @FXML
  private TextField chatInput;

  @FXML
  private VBox box;

  @FXML
  private AnchorPane mainPane;


  @FXML
  private Label player1;

  @FXML
  private Label aiDefault;

  @FXML
  private ComboBox<String> gameMode;

  private LinkedList<GameMode> gameModes = new LinkedList<>();

  @FXML
  private Label nameHostPlayer;

  @FXML
  private Label playerName1;

  @FXML
  private Label playerName2;

  @FXML
  private Label playerName3;

  @FXML
  private Label informationIP;

  @FXML
  Text gamemodeError;

  @FXML
  Label roundCount;

  /**
   * Constructor of Lobycontroller Class. Used set Gamesession, Controller and to initialize
   *
   * @param gameController Gamecontroller Object currently used
   * @author tgutberl
   */
  public HostLobbyUiController(AbstractGameController gameController) {
    super(gameController);
    //this.gameSession = new GameSession(new Player("You", PlayerType.HOST_PLAYER));
    this.gameController = gameController;
    this.init(super.root);

    HostServer hostServer = new HostServer();
    try {
      org.eclipse.jetty.util.log.Log.setLog(new NoLogging());
      hostServer.startWebsocket(8081);
      //Debug.printMessage("[testChatServer] Server is running");
      //TimeUnit.SECONDS.sleep(3);
    } catch (Exception e) {
      e.printStackTrace();
    }



    Player player = new Player(Config.getStringValue("HOST"),PlayerType.REMOTE_PLAYER);
    this.client = new EndpointClient(this,player);



    this.gameSession = client.getGameSession();
    this.gameSession.setLocalPlayer(player);


    this.clientHandler = client.getClientHandler();
  }

  public void registerChatMessage(){
    if(chatInput.getText().length() > 0 ){
      gameSession.addChatMessage(chatInput.getText());
      chatInput.setText("");
    }else{
    }
  }

  public void init(Group root) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/HostLobbyView.fxml"));
      loader.setControllerFactory(e -> this);
      root.getChildren().add(loader.load());
      updateSize(mainPane, gameController.getStage());
      gamemodeError.setText("");
      chatInput.setOnKeyPressed(event -> {
        if(event.getCode().equals(KeyCode.ENTER)){
          registerChatMessage();
        }
      });
      setIP();
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

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  //Italic Font
  public static final Font ITALIC_FONT =
      Font.font(
          "Serif",
          FontPosture.ITALIC,
          Font.getDefault().getSize()
      );

  public void setIP(){
    try {
      this.informationIP.setText(Inet4Address.getLocalHost().getHostAddress());
    } catch (UnknownHostException e) {
      this.informationIP.setText("No IP Found");
      e.printStackTrace();
    }
  }

  @FXML
  public void kickPlayer1(){

  }
  @FXML
  public void kickPlayer2(){

  }
  @FXML
  public void kickPlayer3(){

  }

  /**
   * Copy to Clipboard Method, for the IP
   *
   * @author tgutberl
   */
  @FXML
  public void copyToClipboard(){
    String clipBoardString = informationIP.getText();
    StringSelection stringSelection = new StringSelection(clipBoardString);
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    clipboard.setContents(stringSelection, null);
  }

  @FXML
  public void back() {
    gameController.setActiveUiController(new PlayUiController(gameController));
  }

  @FXML
  public void reset(){
    //TODO @tbuscher implement Reset Account Statistics
  }


  @FXML
  public void playGame() {
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

      if(this.gameSession.getPlayerList().size()==4){
        playerName1.setText(this.gameSession.getPlayerList().get(1).getUsername());
        playerName2.setText(this.gameSession.getPlayerList().get(2).getUsername());
        playerName3.setText(this.gameSession.getPlayerList().get(3).getUsername());
      }
    }


  }

  private void initializeComboBox(ComboBox<String> comboBox) {
    list = FXCollections.observableArrayList("Classic", "Duo", "Junior", "Trigon");
    comboBox.setItems(list);
    comboBox.setValue("Classic");
  }

  @FXML
  public void addRound() {
    if(gamemodeError.getText().length() > 0){
      gamemodeError.setText("");
    }
    round++;
    HBox hBox = new HBox();
    hBox.setAlignment(Pos.CENTER);
    ComboBox<String> comboBox = new ComboBox<>();
    comboBox.setPrefWidth(150);
    comboBox.setPrefHeight(25);
    initializeComboBox(comboBox);
    roundCount.setText(""+round);
    hBox.getChildren().add(comboBox);
    rounds.add(comboBox);
    box.getChildren().add(hBox);
  }

  @FXML
  public void deleteRound() {
    if(round > 1){
      round--;
      roundCount.setText(""+round);
      box.getChildren().remove(box.getChildren().get(round));
      rounds.remove(round);
    }else{
      gamemodeError.setText("You need to have at least one Round!");
    }
  }

  @FXML
  private void increaseDifficulty() {
    increaseAi(aiDefault);
  }


  @FXML
  private void decreaseDifficulty() {
    decreaseAi(aiDefault);
  }


  @FXML
  public void initialize() {
    list = FXCollections.observableArrayList("Classic", "Duo", "Junior", "Trigon");
    gameMode.setItems(list);
    gameMode.setValue("Classic");
    rounds.add(gameMode);
  }

  private void increaseAi(Label difficultyPlayer) {
      switch (difficultyPlayer.getText()) {
        case "Easy":
          difficultyPlayer.setText("Middle");
          break;
        case "Middle":
          difficultyPlayer.setText("Hard");
          break;
        case "Hard":
          difficultyPlayer.setText("Easy");
          break;
    }
  }

  private void decreaseAi(Label difficultyPlayer) {
    switch (difficultyPlayer.getText()) {
      case "Easy":
        difficultyPlayer.setText("Hard");
        break;
      case "Middle":
        difficultyPlayer.setText("Easy");
        break;
     case "Hard":
        difficultyPlayer.setText("Middle");
    }
  }

  @Override
  public void onExit() {

  }

  @Override
  public void update(AbstractGameController gameController, double deltaTime) {

    if(this.gameSession.getPlayerList().size()>1){
      playerName1.setText(this.gameSession.getPlayerList().get(1).getUsername());
    }

    if(this.gameSession.isGameStarted()){
      gameSession.setGameOver(false);
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
