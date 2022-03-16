package game.view;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author lbaudenb
 */
public class BoardPane extends GridPane {

  private final Board board;
  private final List<Rectangle> squares;

  public BoardPane(Board board) {
    this.board = board;
    this.squares = new ArrayList<>();
    setBoard();
    setStyle();
  }

  void setSquare(Color color, int row, int column) {
    Rectangle r = new Rectangle(30, 30);

    if (color == null) {
      r.setFill(Color.GREY);
    } else {
      r.setFill(color);
    }

    squares.add(r);
    this.add(r, column, row);

  }

  final void setBoard() {
    for (int row = 0; row < Board.SIZE; row++) {
      for (int column = 0; column < Board.SIZE; column++) {
        setSquare(board.getColorAt(row, column), row, column);
      }
    }
  }

  public List<Rectangle> getSquares() {
    return this.squares;
  }

  final void setStyle() {
    this.setGridLinesVisible(true);
  }

}
