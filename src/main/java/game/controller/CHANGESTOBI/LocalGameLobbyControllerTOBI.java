package game.controller.CHANGESTOBI;

import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import engine.handler.ErrorMessageHandler;
import engine.handler.ThreadHandler;
import game.controller.LocalGameUiController;
import game.controller.MainMenuUiController;
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
import java.util.concurrent.TimeUnit;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import net.server.ClientHandler;
import net.server.HostServer;
import net.tests.NoLogging;
import net.transmission.EndpointClient;

/**
 * @author lbaudenb
 * @author tgutberl
 */

public class LocalGameLobbyControllerTOBI extends AbstractUiController {

  private final AbstractGameController gameController;

  private final String nameAiPlayer1 = "Bob";
  private final String nameAiPlayer2 = "Anna";
  private final String nameAiPlayer3 = "Tom";

  private final GameSession gameSession;

  private GameMode gameMode;
  private ObservableList<String> list;

  private EndpointClient client;
  private ClientHandler clientHandler;

  @FXML
  private Label player1;

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
  public LocalGameLobbyControllerTOBI(AbstractGameController gameController) {
    super(gameController);
    //this.gameSession = new GameSession(new Player("You", PlayerType.HOST_PLAYER));
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



    Player player = new Player("tobi",PlayerType.REMOTE_PLAYER);
    this.client = new EndpointClient(this,player);



    this.gameSession = client.getGameSession();
    this.gameSession.setLocalPlayer(player);


    this.clientHandler = client.getClientHandler();

  }

  public void init(Group root) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/LobbyView.fxml"));
      loader.setControllerFactory(e -> this);
      root.getChildren().add(loader.load());
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
      //gameList.add(new GMClassic());
      gameList.add(this.gameMode);
      //this.gameSession.setGameList(gameList);

      this.clientHandler.startLocalGame(gameList);


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

  @FXML
  private void increaseDifficulty1() {
    increaseAi(namePlayer1, difficultyPlayer1, nameAiPlayer1, player1);
  }

  @FXML
  private void increaseDifficulty2() {
    increaseAi(namePlayer2, difficultyPlayer2, nameAiPlayer2, player2);
  }

  @FXML
  private void increaseDifficulty3() {
    increaseAi(namePlayer3, difficultyPlayer3, nameAiPlayer3, player3);
  }

  @FXML
  private void decreaseDifficulty1() {
    decreaseAi(namePlayer1, difficultyPlayer1, nameAiPlayer1, player1);
  }

  @FXML
  private void decreaseDifficulty2() {
    decreaseAi(namePlayer2, difficultyPlayer2, nameAiPlayer2, player2);
  }

  @FXML
  private void decreaseDifficulty3() {
    decreaseAi(namePlayer3, difficultyPlayer3, nameAiPlayer3, player3);
  }

  @FXML
  public void initialize() {
    list = FXCollections.observableArrayList("Classic", "Duo", "Junior", "Trigon");
    gameModes.setItems(list);
  }

  private void increaseAi(Label namePlayer, Label difficultyPlayer, String name, Label player) {
    if (namePlayer.getText().equals("-") || difficultyPlayer.getText().equals("-")) {
      namePlayer.setText(name);
      difficultyPlayer.setText("Easy");
      player.setText("Easy");
    } else {
      switch (difficultyPlayer.getText()) {
        case "Easy":
          difficultyPlayer.setText("Middle");
          player.setText("Middle");
          break;
        case "Middle":
          difficultyPlayer.setText("-");
          namePlayer.setText("-");
          player.setText("None");
          break;
        /*case "Hard":
          difficultyPlayer.setText("-");
          namePlayer.setText("-");*/
      }
    }
  }

  private void decreaseAi(Label namePlayer, Label difficultyPlayer, String name, Label player) {
    switch (difficultyPlayer.getText()) {
      case "Easy":
        difficultyPlayer.setText("-");
        namePlayer.setText("-");
        player.setText("None");
        break;
      case "Middle":
        difficultyPlayer.setText("Easy");
        player.setText("Easy");
        break;
     /* case "Hard":
        difficultyPlayer.setText("Middle");*/
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
    //this.gameSession.setGameStarted();
  } else {
      Debug.printMessage(this, "GameSession Controller "+ this.gameSession);
    }

  }

  @Override
  public void update(AbstractGameController gameController) {

  }
}
