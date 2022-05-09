package game.controller;

import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import game.model.board.BoardSquare;
import game.model.Game;
import game.model.board.BoardTrigon;
import game.model.player.Player;
import game.model.GameSession;
import game.model.chat.Chat;
import game.view.BoardPane;
import game.view.BoardSquarePane;
import game.view.BoardTrigonPane;
import game.view.StackPane;
import game.view.StackSquarePane;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class InGameUiController extends AbstractUiController {

  private GameSession gameSession;

  private Game game;

  private Chat chat;

  private AbstractGameController gameController;

  private BoardPane boardPane;

  private VBox playerStacks;

  private List<Label> playerPoints;

  private HBox Gui;

  private Button quitButton;


  public InGameUiController(AbstractGameController gameController, Game game, GameSession gameSession) {
    super();
    this.gameSession = gameSession;
    this.game = gameSession.getGame();
    this.chat = gameSession.getChat();
    this.gameController = gameController;
    this.Gui = new HBox();
    playerPoints = new ArrayList<>();

    createBoard();
    setUpUi();


  }

  public void createBoard() {
    switch (game.getGamemode().getName()) {
      case "JUNIOR":
        ;
      case "DUO":
        ;
      case "CLASSIC":
        boardPane = new BoardSquarePane((BoardSquare) game.getGameState().getBoard());
        break;
      case "TRIGON":
        boardPane = new BoardTrigonPane((BoardTrigon) game.getGameState().getBoard());
        break;
    }
    Gui.getChildren().add((Node) boardPane);
    super.root.getChildren().add(Gui);
  }


  private void setUpUi() {
    playerStacks = new VBox();
    switch (game.getGamemode().getName()) {
      case "JUNIOR":
        ;
      case "DUO":
        ;
      case "CLASSIC":
        for (Player p : this.gameSession.getPlayerList()) {
          StackPane squareStack = new StackSquarePane(p, game.getGameState().getRemainingPolys(p));
          playerStacks.getChildren().add(squareStack);
        }
        ;
        break;
      case "TRIGON":
        break; // not implemented yet
    }

    Gui.getChildren().add(playerStacks);

    for (Player p : this.gameSession.getPlayerList()) {
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

  /**
   * function that updates the screen and calls the next move to be made
   * @param gameController
   * @param deltaTime
   *
   * @author //TODO hier die klasse hat jemand anders geschrieben. ich habe nur paar changes gemacht. echter autor am besten noch dazu schreiben
   * @author tgeilen
   */
  @Override
  public void update(AbstractGameController gameController, double deltaTime) {
    //refreshUi();
    this.game.makeMove();
    boardPane.repaint(game.getGameState().getBoard());

  }

}

