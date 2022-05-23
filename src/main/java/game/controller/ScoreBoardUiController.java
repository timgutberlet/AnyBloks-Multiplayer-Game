package game.controller;

import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import game.config.Config;
import game.model.Debug;
import game.model.GameSession;
import game.model.player.PlayerType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Class that controls the Scoreboard View
 *
 * @author lbaudenb
 */
public class ScoreBoardUiController extends AbstractUiController {

  private static final List<String> players = new ArrayList<>();
  private static final List<String> scores = new ArrayList<>();
  private static final List<String> sessionPlayers = new ArrayList<>();
  private static final List<String> sessionScores = new ArrayList<>();
  private final AbstractGameController gameController;
  private final GameSession gameSession;
  @FXML
  private AnchorPane mainPane;

  @FXML
  private HBox board;

  @FXML
  private Line line;

  @FXML
  private Label userMessage;

  @FXML
  private Label gameMode;

  @FXML
  private Text nameWinner;

  @FXML
  private Text pointsWinner;

  @FXML
  private Text nameSecond;

  @FXML
  private Text pointsSecond;

  @FXML
  private Text nameThird;

  @FXML
  private Text pointsThird;

  @FXML
  private Label nameFourth;

  @FXML
  private Label pointsFourth;

  @FXML
  private Label hostWaiting;

  @FXML
  private HBox buttonBox;

  public ScoreBoardUiController(AbstractGameController gameController, GameSession gameSession) {
    super(gameController);
    this.gameController = gameController;
    this.gameSession = gameSession;
    this.init(super.root);
  }

  public static void sortScoreBoard(GameSession gameSession) {
    List<Map.Entry<String, Integer>> list0
        = new ArrayList<Entry<String, Integer>>(
        gameSession.getGameScoreBoard().playerScores.entrySet());

    // Sort the list using lambda expression
    Collections.sort(
        list0,
        (i1, i2) -> i1.getValue().compareTo(i2.getValue()));

    for (int j = list0.size() - 1; j >= 0; j--) {
      players.add(list0.get(j).getKey());
      scores.add(list0.get(j).getValue() + "");
    }

    List<Map.Entry<String, Integer[]>> list1
        = new ArrayList<Entry<String, Integer[]>>(
        gameSession.getGameSessionScoreBoard().usernames2pointsAndWins.entrySet());

    // Sort the list using lambda expression
    Collections.sort(
        list1,
        (i1, i2) -> i1.getValue()[0].compareTo(i2.getValue()[0]));

    for (int i = list1.size() - 1; i >= 0; i--) {
      sessionPlayers.add(list1.get(i).getKey());
      sessionScores.add(list1.get(i).getValue()[0] + "");
    }


  }

  public void init(Group root) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/ScoreBoardView.fxml"));
      loader.setControllerFactory(e -> this);
      root.getChildren().add(loader.load());
      updateSize(mainPane, gameController.getStage());
      if (this.gameSession.getLocalPlayer().getType().equals(PlayerType.HOST_PLAYER)) {
        hostWaiting.setVisible(false);
      }
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

  @FXML
  public void initialize() {
    setLabels();
    setUserMessage();
  }

  @FXML
  public void backMainMenu() {
    gameController.setActiveUiController(new MainMenuUiController(gameController));
  }

  @FXML
  public void nextRound() {
    if (this.gameSession.getLocalPlayer().getType().equals(PlayerType.HOST_PLAYER) &&
        this.gameSession.getGameList().size() > 0) {
      this.gameSession.getClientHandler().startLocalGame(this.gameSession.getGameList());
    }
  }

  public void setLabels() {

    gameMode.setText("Gamemode: " + gameSession.getGameScoreBoard().gamemode);

    switch (gameSession.getGameScoreBoard().playerScores.size()) {
      case 2:
        nameWinner.setText(players.get(0));
        nameSecond.setText(players.get(1));
        nameThird.setText("");
        nameFourth.setText("");
        pointsWinner.setText(scores.get(0));
        pointsSecond.setText(scores.get(1));
        pointsThird.setText("");
        pointsFourth.setText("");
        break;
      case 3:
        nameWinner.setText(players.get(0));
        nameSecond.setText(players.get(1));
        nameThird.setText(players.get(2));
        nameFourth.setText("");
        pointsWinner.setText(scores.get(0));
        pointsSecond.setText(scores.get(1));
        pointsThird.setText(scores.get(2));
        pointsFourth.setText("");
        break;
      case 4:
        nameWinner.setText(players.get(0));
        nameSecond.setText(players.get(1));
        nameThird.setText(players.get(2));
        nameFourth.setText(players.get(3));
        pointsWinner.setText(scores.get(0));
        pointsSecond.setText(scores.get(1));
        pointsThird.setText(scores.get(2));
        pointsFourth.setText(scores.get(3));
        break;
      default:
        Debug.printMessage("Scoreboard is empty");
    }

    if (gameSession.isMultiRound()) {

      VBox vBox = new VBox();
      vBox.setAlignment(Pos.CENTER);
      vBox.setFillWidth(true);
      Label label0 = new Label();
      label0.setText("Leaderboard");
      label0.setFont(Font.font("System", 40));
      Label label1 = new Label();
      label1.setText("Games played: " + gameSession.getGameSessionScoreBoard().gamesPlayed);
      label1.setFont(Font.font("System", 24));
      vBox.getChildren().add(label0);
      vBox.getChildren().add(label1);

      for (int i = 0; i < 4; i++) {

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20);
        hBox.setPrefHeight(80);
        Text name = new Text();
        Text score = new Text();

        if (i < gameSession.getPlayerList().size()) {
          name.setText((sessionPlayers.get(i)));
          score.setText(" " + sessionScores.get(i));
        } else {
          name.setText("");
          score.setText("");
        }

        switch (i) {
          case 0:
            name.setFill(Color.GOLD);
            name.setFont(Font.font("System", 40));
            score.setFill(Color.GOLD);
            score.setFont(Font.font("System", 40));
            break;
          case 1:
            name.setFill(Color.SILVER);
            name.setFont(Font.font("System", 30));
            score.setFill(Color.SILVER);
            score.setFont(Font.font("System", 30));
            break;
          case 2:
            name.setFill(Color.color(0.66, 0.4375, 0));
            name.setFont(Font.font("System", 30));
            score.setFill(Color.color(0.66, 0.4375, 0));
            score.setFont(Font.font("System", 30));
            break;
          case 3:
            name.setFill(Color.WHITE);
            name.setFont(Font.font("System", 30));
            score.setFill(Color.WHITE);
            score.setFont(Font.font("System", 30));
            break;
        }

        hBox.getChildren().add(name);
        hBox.getChildren().add(score);
        vBox.getChildren().add(hBox);
      }
      board.getChildren().remove(line);
      board.getChildren().add(vBox);

      if (gameSession.getLocalPlayer().equals(gameSession.getHostPlayer())) {
        Button button = new Button();
        button.setText("Next Round");
        button.setFont(Font.font("System", 20));
        button.setOnMouseClicked(mouseEvent -> {
          this.nextRound();
        });
        buttonBox.getChildren().add(button);
      }

    }

  }

  public void setUserMessage() {
    String username = gameSession.getLocalPlayer().getUsername();
    int place = players.indexOf(username);
    int overall = sessionPlayers.indexOf(username);

    if (gameSession.getGameList().size() > 0) {
      switch (place) {
        case 0:
          userMessage.setText("Strong performance!");
          break;
        case 1:
        case 2:
          userMessage.setText("You are getting closer!");
          break;
        case 3:
          userMessage.setText("Keep going!");
          break;
      }
    } else {
      switch (overall) {
        case 0:
          userMessage.setText("You Won!");
          break;
        case 1:
        case 2:
          userMessage.setText("Good job");
          break;
        case 3:
          userMessage.setText("Better luck next time!");
          break;
      }
    }
  }

  @Override
  public void update(AbstractGameController gameController) {

  }

  @Override
  public void update(AbstractGameController gameController, double deltaTime) {

    if (this.gameSession.isGameStarted()) {
      gameController.setActiveUiController(
          new LocalGameUiController(gameController, this.gameSession.getGame(), gameSession));
      this.gameSession.setGameStarted(false);
    }

  }

  @Override
  public void onExit() {
    System.exit(0);
  }
}



