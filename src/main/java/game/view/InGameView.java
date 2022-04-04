package game.view;

import game.model.BoardSquare;
import game.model.Game;
import game.model.gamemodes.GMClassic;
import javafx.scene.Group;

public class InGameView {

  private Group root;
  private Game game;
  private BoardPane boardPane;

  public void init(Group root, Game game) {
    this.root = root;
    this.game = game;
    boardPane = new BoardSquarePane(new BoardSquare(new GMClassic()));
    root.getChildren().add(boardPane);
    //setBoardPane(game);
  }

  public BoardPane getBoardPane() {
    return this.boardPane;
  }
}
