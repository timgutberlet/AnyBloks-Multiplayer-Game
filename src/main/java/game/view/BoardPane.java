package game.view;

import game.model.Board;
import game.model.BoardSquare;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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
