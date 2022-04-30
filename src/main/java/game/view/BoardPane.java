package game.view;

import game.model.Board;
import javafx.scene.layout.GridPane;

/**
 * @author lbaudenb
 */
public abstract class BoardPane extends GridPane {

  protected final Board board;

  public BoardPane(Board board) {
    this.board = board;
  }

  public abstract void repaint(Board board);

}
