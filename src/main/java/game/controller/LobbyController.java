package game.controller;

import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import engine.handler.ErrorMessageHandler;
import game.model.Debug;
import game.model.player.Player;
import game.model.player.PlayerType;
import game.model.Session;
import game.model.gamemodes.GMClassic;
import game.model.gamemodes.GMDuo;
import game.model.gamemodes.GMJunior;
import game.model.gamemodes.GMTrigon;
import game.model.gamemodes.GameMode;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

/**
 * @author lbaudenb
 */

public class LobbyController extends AbstractUiController {

  private final AbstractGameController gameController;

  private final String nameAiPlayer1 = "Bob";
  private final String nameAiPlayer2 = "Anna";
  private final String nameAiPlayer3 = "Tom";

  private Session session;

  private GameMode gameMode;
  private ObservableList<String> list;

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

  public LobbyController(AbstractGameController gameController) {
    super();
    this.session = new Session(new Player("NAME_HOST_PLAYER",PlayerType.HOST_PLAYER));
    this.gameController = gameController;
    this.init(super.root);
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
    ArrayList<Player> players = this.session.getPlayerList();
    boolean error = false;

    if (!namePlayer1.equals("-")) {
      switch (difficultyPlayer1.getText()) {
        case "Easy":
          this.session.addPlayer(new Player(nameAiPlayer1, PlayerType.AI_EASY));
          //players.add(new Player(nameAiPlayer1, PlayerType.AI_EASY));
          break;
        case "Middle":
          this.session.addPlayer(new Player(nameAiPlayer1, PlayerType.AI_MIDDLE));
          //players.add(new Player(nameAiPlayer1, PlayerType.AI_MIDDLE));
          break;
        case "Hard":
          this.session.addPlayer(new Player(nameAiPlayer1, PlayerType.AI_HARD));
          //players.add(new Player(nameAiPlayer1, PlayerType.AI_HARD));
      }
      Debug.printMessage(""+this.session.getPlayerList().size());
    }
    if (!namePlayer2.equals("-")) {
      switch (difficultyPlayer2.getText()) {
        case "Easy":
          this.session.addPlayer(new Player(nameAiPlayer2, PlayerType.AI_EASY));
          //players.add(new Player(nameAiPlayer2, PlayerType.AI_EASY));
          break;
        case "Middle":
          this.session.addPlayer(new Player(nameAiPlayer2, PlayerType.AI_MIDDLE));
          //players.add(new Player(nameAiPlayer1, PlayerType.AI_MIDDLE));
          break;
        case "Hard":
          this.session.addPlayer(new Player(nameAiPlayer2, PlayerType.AI_HARD));
          //players.add(new Player(nameAiPlayer1, PlayerType.AI_HARD));
      }
      Debug.printMessage(""+this.session.getPlayerList().size());
    }
    if (!namePlayer3.equals("-")) {
      switch (difficultyPlayer3.getText()) {
        case "Easy":
          this.session.addPlayer(new Player(nameAiPlayer3, PlayerType.AI_EASY));
          //players.add(new Player(nameAiPlayer3, PlayerType.AI_EASY));
          break;
        case "Middle":
          this.session.addPlayer(new Player(nameAiPlayer3, PlayerType.AI_MIDDLE));
          //players.add(new Player(nameAiPlayer3, PlayerType.AI_MIDDLE));
          break;
        case "Hard":
          this.session.addPlayer(new Player(nameAiPlayer3, PlayerType.AI_HARD));
          //players.add(new Player(nameAiPlayer3, PlayerType.AI_HARD));
      }
      Debug.printMessage(""+this.session.getPlayerList().size());
    }
    this.session.addHost(new Player("You", PlayerType.AI_EASY));
    System.out.println(this.session.getPlayerList().size());
    //players.add(new Player("You", PlayerType.AI_EASY));

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
    if (players.size() < 2) {
      ErrorMessageHandler.showErrorMessage(
          "The GameMode " + this.gameMode.getName() + " requires at least 2 players");
      error = true;
    }
    if (!error) {
      //for(Player p: players){
      //  p.setSession(this.session);
      //}
      //Game game = new Game(players, this.gameMode);
      Debug.printMessage("Hallo");
      //this.session.setGame(new Game(this.session, this.gameMode));
      Debug.printMessage("Hallo2");
      this.session.startGame(this.gameMode);
      Debug.printMessage("Hallo3");
      //game.startGame();
      gameController.setActiveUiController(
          new InGameUiController(gameController, this.session.getGame(), session));
    }

  }

  @FXML
  private void increaseDifficulty1() {
    increaseAi(namePlayer1, difficultyPlayer1, nameAiPlayer1);
  }

  @FXML
  private void increaseDifficulty2() {
    increaseAi(namePlayer2, difficultyPlayer2, nameAiPlayer2);
  }

  @FXML
  private void increaseDifficulty3() {
    increaseAi(namePlayer3, difficultyPlayer3, nameAiPlayer3);
  }

  @FXML
  private void decreaseDifficulty1() {
    decreaseAi(namePlayer1, difficultyPlayer1, nameAiPlayer1);
  }

  @FXML
  private void decreaseDifficulty2() {
    decreaseAi(namePlayer2, difficultyPlayer2, nameAiPlayer2);
  }

  @FXML
  private void decreaseDifficulty3() {
    decreaseAi(namePlayer3, difficultyPlayer3, nameAiPlayer3);
  }

  @FXML
  public void initialize() {
    list = FXCollections.observableArrayList("Classic", "Duo", "Junior");
    gameModes.setItems(list);
  }

  private void increaseAi(Label namePlayer, Label difficultyPlayer, String name) {
    if (namePlayer.getText().equals("-") || difficultyPlayer.getText().equals("-")) {
      namePlayer.setText(name);
      difficultyPlayer.setText("Easy");
    } else {
      switch (difficultyPlayer.getText()) {
        case "Easy":
          difficultyPlayer.setText("Middle");
          break;
        case "Middle":
          difficultyPlayer.setText("-");
          namePlayer.setText("-");
          ;
          break;
        /*case "Hard":
          difficultyPlayer.setText("-");
          namePlayer.setText("-");*/
      }
    }
  }

  private void decreaseAi(Label namePlayer, Label difficultyPlayer, String name) {
    switch (difficultyPlayer.getText()) {
      case "Easy":
        difficultyPlayer.setText("-");
        namePlayer.setText("-");
        break;
      case "Middle":
        difficultyPlayer.setText("Easy");
        break;
     /* case "Hard":
        difficultyPlayer.setText("Middle");*/
    }
  }

}
