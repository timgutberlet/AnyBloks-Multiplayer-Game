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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class InGameUiController extends AbstractUiController {

  private Game game;

  private AbstractGameController gameController;

  private BoardPane boardPane;

  private VBox playerStacks;

  private HBox Gui;


  public InGameUiController(AbstractGameController gameController, Game game) {
    super();
    this.game = game;
    this.gameController = gameController;
    this.Gui = new HBox();

    createBoard();
    setUpUi();
  }

  public void createBoard() {
    this.boardPane = new BoardSquarePane((BoardSquare) game.getBoard());
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
  }

  private void refreshUi() {
    playerStacks.getChildren().clear();
    for (Player p : game.getPlayers()) {
      StackPane squareStack = new StackSquarePane(p, game.getGameState().getRemainingPolys(p));
      playerStacks.getChildren().add(squareStack);
    }
  }

  @Override
  public void update(AbstractGameController gameController, double deltaTime) {
    refreshUi();
    if (this.game.getGameState().isStateRunning()) {
      if (this.game.getGameState().getPlayerCurrent().getType().equals(PlayerType.AI_EASY)) {
        this.game.getGameState().playTurn(AI.calculateNextMove(this.game.getGameState(),
            this.game.getGameState().getPlayerCurrent()));
      }
    }
    boardPane.repaint(game.getGameState().getBoard());

  }

}

