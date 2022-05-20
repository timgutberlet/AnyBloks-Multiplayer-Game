package game.controller;

import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import game.config.Config;
import game.model.GameSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

/**
 * @author lbaudenb
 */
public class ScoreBoardUiController extends AbstractUiController {

  private AbstractGameController gameController;
  private GameSession gameSession;

  private static List<String> players = new ArrayList<>();
  private static List<String> scores = new ArrayList<>();

  private static List<String> sessionScores = new ArrayList<>();

  @FXML
  private AnchorPane mainPane;

  @FXML
  private HBox board;

  @FXML
  private Line line;

  @FXML
  private Label userMessage;

  @FXML
  private Label nameWinner;
  @FXML
  private Label pointsWinner;

  @FXML
  private Label nameSecond;

  @FXML
  private Label pointsSecond;

  @FXML
  private Label nameThird;

  @FXML
  private Label pointsThird;

  @FXML
  private Label nameFourth;

  @FXML
  private Label pointsFourth;

  @FXML
  private Button backMainMenu;

  @FXML
  private Button nextRound;

  public ScoreBoardUiController(AbstractGameController gameController, GameSession gameSession) {
    super(gameController);
    this.gameController = gameController;
    this.gameSession = gameSession;
    this.init(super.root);
  }

  public void init(Group root) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/ScoreBoardView.fxml"));
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
          mainPane.getStylesheets().add(getClass().getResource("/styles/styleThink.css").toExternalForm());
          break;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void initialize() {
    setLabels();
    setMessage();
  }

  @FXML
  public void backMainMenu() {
    gameController.setActiveUiController(new MainMenuUiController(gameController));
  }

  public static void sortScoreBoard(GameSession gameSession) {
    HashMap<String, Integer> sortedScores = gameSession.getScoreboard().entrySet().stream()
        .sorted(Entry.comparingByValue())
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue,
            (e1, e2) -> e1, LinkedHashMap::new));
    for (Map.Entry<String, Integer> entry : sortedScores.entrySet()) {
      players.add(entry.getKey());
      scores.add(entry.getValue() + "");
    }

    /*HashMap<String, Integer> sortedSessionScores = gameSession.getGameSessionScoreboard().entrySet().stream()
        .sorted(Entry.comparingByValue())
        .collect(Collectors.toMap(Entry::getKey, Entry::getValue,
            (e1, e2) -> e1, LinkedHashMap::new));
    for (Map.Entry<String, Integer> entry : sortedSessionScores.entrySet()) {
      sessionScores.add(entry.getValue() + "");
    }*/

  }

  public void setLabels() {
    switch (gameSession.getPlayerList().size()) {
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
    }
    if (false) {
      VBox vBox = new VBox();
      vBox.setAlignment(Pos.CENTER);
      for (int i = 0; i < players.size(); i++) {
        HBox hBox = new HBox();
        Label name = new Label();
        name.setText(players.get(i));
        Label score = new Label();
        score.setText(sessionScores.get(i));
        hBox.getChildren().add(name);
        hBox.getChildren().add(score);
        vBox.getChildren().add(hBox);
      }
      board.getChildren().remove(line);
      board.getChildren().add(vBox);
    }
  }

  public void setMessage() {
    String username = gameSession.getLocalPlayer().getUsername();
    int index = players.indexOf(username);
    switch (index) {
      case 1:
        userMessage.setText("YOU WON!!!");
        break;
      case 2:
      case 3:
        userMessage.setText("More luck next time");
        break;
      case 4:
        userMessage.setText("Keep your head up");
        break;
    }
  }

  @Override
  public void update(AbstractGameController gameController) {

  }

  @Override
  public void onExit() {

  }
}



