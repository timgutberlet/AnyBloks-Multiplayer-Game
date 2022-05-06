package game.view;

import game.model.board.Board;
import game.model.board.BoardSquare;
import game.model.field.FieldSquare;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author lbaudenb
 */
public class BoardSquarePane extends BoardPane {

  private final List<Rectangle> squares;

  public BoardSquarePane(BoardSquare boardSquare) {
    super(boardSquare);
    squares = new ArrayList<>();
    setBoard();
    setStyle();
  }

  public void setSquare(Color color, int row, int column) {
    Rectangle r = new Rectangle(30, 30);
    r.setFill(color);
    squares.add(r);
    this.add(r, column, row);
  }

  void setBoard() {
    for (int row = 0; row < board.SIZE; row++) {
      for (int column = 0; column < board.SIZE; column++) {
        int[] pos = {row, column};
        setSquare(board.getJavaColor(pos), row, column);
      }
    }
  }

  @Override
  public void repaint(Board board) {
    BoardSquare help = (BoardSquare) board;
    for (FieldSquare fs : help.getBoard()) {
      this.setSquare(fs.getJavaColor(), fs.getPos()[0], fs.getPos()[1]);
    }
  }

  final void setStyle() {
    this.setGridLinesVisible(true);
  }
}
