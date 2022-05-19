package game.controller;

import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import engine.handler.ErrorMessageHandler;
import engine.handler.ThreadHandler;
import game.model.Debug;
import game.model.GameSession;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import net.server.ClientHandler;
import net.server.HostServer;
import net.tests.NoLogging;
import net.transmission.EndpointClient;

/**
 * @author lbaudenb
 * @author tgutberl
 */

public class LocalLobbyUiController extends AbstractUiController {

  private final AbstractGameController gameController;

  private final String nameAiPlayer1 = "Tobi";
  private final String nameAiPlayer2 = "Janik";
  private final String nameAiPlayer3 = "Tore";

  private final GameSession gameSession;

  private LinkedList<GameMode> gameModes = new LinkedList<>();
  private LinkedList<PlayerType> aiPlayers = new LinkedList<>();
  private ObservableList<String> list;

  private List<ComboBox<String>> rounds = new ArrayList<>();
  private int round = 1;

  private EndpointClient client;
  private ClientHandler clientHandler;

  @FXML
  private Label player1;

  @FXML
  private Label player2;

  @FXML
  private Label player3;

  @FXML
  private VBox box;

  @FXML
  Label roundCount;

  @FXML
  private ComboBox<String> gameMode;

  @FXML
  Text gamemodeError;

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
  public LocalLobbyUiController(AbstractGameController gameController) {
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



    Player player = new Player("You", PlayerType.REMOTE_PLAYER);
    this.client = new EndpointClient(this,player);



    this.gameSession = client.getGameSession();
    this.gameSession.setLocalPlayer(player);


    this.clientHandler = client.getClientHandler();

  }

  public void init(Group root) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/LocalLobbyView.fxml"));
      loader.setControllerFactory(e -> this);
      root.getChildren().add(loader.load());
      gamemodeError.setText("");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void back() {
    gameController.setActiveUiController(new PlayUiController(gameController));
  }

  @FXML
  public void playGame() {
    ArrayList<Player> players = this.gameSession.getPlayerList();
    boolean error = false;

    if (!player1.getText().equals("-")) {
      switch (difficultyPlayer1.getText()) {
        case "Easy":
          aiPlayers.add(PlayerType.AI_EASY);
          this.gameSession.setDefaultAI(PlayerType.AI_EASY);
          break;
        case "Middle":
          this.gameSession.setDefaultAI(PlayerType.AI_MIDDLE);
          aiPlayers.add(PlayerType.AI_MIDDLE);
          break;
        case "Hard":
          this.gameSession.setDefaultAI(PlayerType.AI_HARD);
          aiPlayers.add(PlayerType.AI_HARD);
      }
    }
    if (!player2.getText().equals("-")) {
      switch (difficultyPlayer2.getText()) {
        case "Easy":
          aiPlayers.add(PlayerType.AI_EASY);
          break;
        case "Middle":
          this.gameSession.setDefaultAI(PlayerType.AI_MIDDLE);
          break;
        case "Hard":
          this.gameSession.setDefaultAI(PlayerType.AI_HARD);
      }
    }

    if (!player3.getText().equals("-")) {
      switch (difficultyPlayer3.getText()) {
        case "Easy":
          aiPlayers.add(PlayerType.AI_EASY);
          break;
        case "Middle":
          this.gameSession.setDefaultAI(PlayerType.AI_MIDDLE);
          break;
        case "Hard":
          this.gameSession.setDefaultAI(PlayerType.AI_HARD);
      }
    }

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

      Debug.printMessage(this,"Game has been set");

      this.gameSession.startGame(this.gameModes.get(0));
      Debug.printMessage("Hallo3");
      Debug.printMessage("Laenge der Liste: "+this.gameSession.getPlayerList().size());

      if(this.gameSession.getPlayerList().size()==4){
        player1.setText(this.gameSession.getPlayerList().get(1).getUsername());
        player2.setText(this.gameSession.getPlayerList().get(2).getUsername());
        player3.setText(this.gameSession.getPlayerList().get(3).getUsername());
      }
    }



  }

  @FXML
  private void increaseDifficulty1() {
    System.out.println("increase!!!!");
    increaseAi(difficultyPlayer1, nameAiPlayer1, player1);
  }

  @FXML
  private void increaseDifficulty2() {
    System.out.println("increase!!!!");
    increaseAi(difficultyPlayer2, nameAiPlayer2, player2);
  }

  @FXML
  private void increaseDifficulty3() {
    System.out.println("increase!!!!");
    increaseAi(difficultyPlayer3, nameAiPlayer3, player3);
  }

  @FXML
  private void decreaseDifficulty1() {
    System.out.println("decrease!!!!");
    decreaseAi(difficultyPlayer1, nameAiPlayer1, player1);
  }

  @FXML
  private void decreaseDifficulty2() {
    System.out.println("decrease!!!!");
    decreaseAi(difficultyPlayer2, nameAiPlayer2, player2);
  }

  @FXML
  private void decreaseDifficulty3() {
    System.out.println("decrease!!!!!");
    decreaseAi(difficultyPlayer3, nameAiPlayer3, player3);
  }

  @FXML
  public void initialize() {
    list = FXCollections.observableArrayList("Classic", "Duo", "Junior", "Trigon");
    gameMode.setItems(list);
    rounds.add(gameMode);
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

  private void initializeComboBox(ComboBox<String> comboBox) {
    list = FXCollections.observableArrayList("Classic", "Duo", "Junior", "Trigon");
    comboBox.setItems(list);
  }

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
          difficultyPlayer.setText("None");
          player.setText("-");
      }
    }
  }

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
    }
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
    } else {
      Debug.printMessage(this, "GameSession Controller "+ this.gameSession);
    }

  }

  @Override
  public void update(AbstractGameController gameController) {

  }
}