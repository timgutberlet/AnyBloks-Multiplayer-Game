package game.view;

import game.model.Game;
import game.view.board.BoardPane;
import javafx.scene.Group;

/**
 * View in game.
 *
 * @author tgutberlet
 */
public class InGameView {

  private Group root;
  private Game game;
  private BoardPane boardPane;

  /**
   * initializes in game view.
   *
   * @param root Group
   * @param game Game
   */
  public void init(Group root, Game game) {
    this.root = root;
    this.game = game;
    //boardPane = new BoardSquarePane(new BoardSquare(new GMClassic()));
    root.getChildren().add(boardPane);
    //setBoardPane(game);
  }

  public BoardPane getBoardPane() {
    return this.boardPane;
  }
}
