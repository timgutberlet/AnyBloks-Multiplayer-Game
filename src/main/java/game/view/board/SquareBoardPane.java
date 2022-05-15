package game.view.board;

import engine.component.ClassicField;
import engine.handler.ColorHandler;
import engine.handler.InputHandler;
import game.model.board.Board;
import game.view.board.BoardPane;
import javafx.scene.paint.Color;

/**
 * @author lbaudenb
 */
public class SquareBoardPane extends BoardPane {

  public SquareBoardPane(Board board, InputHandler inputHandler, double width) {
    super(board, inputHandler, width);
  }

  /**
   * Method that draws a square at the coordinates {row,j}
   *
   * @param color
   * @param i
   * @param j
   */
  public void setSquare(int i, int j, Color color) {

    ClassicField field = new ClassicField(i, j);
    field.getPoints().addAll(
        0 + j * size, 0 + i * size,
        size + j * size, 0 + i * size,
        size + j * size, size + i * size,
        0 + j * size, size + i * size);
    field.setFill(color);
    field.setStroke(Color.BLACK);

    fields.add(field);
    this.getChildren().add(field);
    inputHandler.registerField(field);
  }

  @Override
  public void setBoard() {
    for (int i = 0; i < board.SIZE; i++) {
      for (int j = 0; j < board.SIZE; j++) {
        int[] pos = {i, j};
        setSquare(i, j, ColorHandler.getJavaColor(board.getColor(pos)));
      }
    }
  }
}

