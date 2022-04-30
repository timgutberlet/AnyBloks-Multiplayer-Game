package game.controller;

import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import game.model.AI;
import game.model.BoardSquare;
import game.model.Game;
import game.model.Player;
import game.model.PlayerType;
import game.view.BoardPane;
import game.view.BoardSquarePane;
import game.view.StackPane;
import game.view.StackSquarePane;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class InGameUiController extends AbstractUiController {

  private Game game;

  private AbstractGameController gameController;

  private BoardPane boardPane;

  private VBox playerStacks;

  private List<Label> playerPoints;

  private HBox Gui;

  private Button quitButton;


  public InGameUiController(AbstractGameController gameController, Game game) {
    super();
    this.game = game;
    this.gameController = gameController;
    this.Gui = new HBox();
    playerPoints = new ArrayList<>();

    createBoard();
    setUpUi();
  }

  public void createBoard() {
    this.boardPane = new BoardSquarePane((BoardSquare) game.getGameState().getBoard());
    Gui.getChildren().add(boardPane);
    super.root.getChildren().add(Gui);
  }

  private void setUpUi() {
    playerStacks = new VBox();
    for (Player p : game.getPlayers()) {
      StackPane squareStack = new StackSquarePane(p, game.getGameState().getRemainingPolys(p));
      playerStacks.getChildren().add(squareStack);
    }
    Gui.getChildren().add(playerStacks);

    for (Player p : game.getPlayers()) {
      Label label = new Label("0");
      playerPoints.add(label);
    }
    //Gui.getChildren().addAll(playerPoints);

    quitButton = new Button("Quit");
    quitButton.setOnMouseClicked(mouseEvent -> this.handleQuitButtonClicked());
    Gui.getChildren().add(quitButton);
  }

  private void handleQuitButtonClicked() {
    gameController.setActiveUiController(new MainMenuUiController(gameController));
  }

  private void refreshUi() {
    playerStacks.getChildren().clear();
    for (Player p : game.getPlayers()) {
      StackPane squareStack = new StackSquarePane(p, game.getGameState().getRemainingPolys(p));
      playerStacks.getChildren().add(squareStack);
    }

    /*Gui.getChildren().removeAll(playerPoints);
    for(Player p : game.getPlayers()){
      Label label = new Label(game.getGameState().getBoard().getScoreOfColor(game.getGameState().getColorFromPlayer(p))+"");
      playerPoints.add(label);
    }
    Gui.getChildren().addAll(playerPoints);*/
  }

  @Override
  public void update(AbstractGameController gameController, double deltaTime) {
    refreshUi();
    if (this.game.getGameState().isStateRunning()) {
      if (this.game.getGameState().getPlayerCurrent().getType().equals(PlayerType.AI_EASY)
          || this.game.getGameState().getPlayerCurrent().getType().equals(PlayerType.AI_MIDDLE)
          || this.game.getGameState().getPlayerCurrent().getType().equals(PlayerType.AI_HARD)) {
        this.game.getGameState().playTurn(AI.calculateNextMove(this.game.getGameState(),
            this.game.getGameState().getPlayerCurrent()));
      }
    }
    boardPane.repaint(game.getGameState().getBoard());

  }

}

