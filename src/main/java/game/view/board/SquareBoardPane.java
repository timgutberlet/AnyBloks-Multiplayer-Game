package game.view.board;

import engine.component.CheckField;
import engine.component.ClassicField;
import engine.handler.ColorHandler;
import engine.handler.InputHandler;
import game.model.board.Board;
import javafx.scene.paint.Color;

/**
 * @author lbaudenb
 */
public class SquareBoardPane extends BoardPane {

  public SquareBoardPane(Board board, InputHandler inputHandler, double width) {
    super(board, inputHandler, width);
    super.size = (0.4 * width) / board.getSize();
    setBoard();
  }

  /**
   * Method that draws a square at the coordinates {row,j}
   *
   * @param color
   * @param i
   * @param j
   */
  public void setSquare(int i, int j, Color color) {

    CheckField checkField = new CheckField(i, j);
    double sizeHelp = size * 0.4;
    double move = size/2 - sizeHelp/2;
    checkField.getPoints().addAll(0 + j * size + move, 0 + i * size + move,
        sizeHelp + j * size + move, 0 + i * size + move,
        sizeHelp + j * size + move, sizeHelp + i * size + move,
        0 + j * size + move, sizeHelp + i * size + move);
    if(checkFieldColor.containsKey(""+i+j)){
      checkField.setFill(checkFieldColor.get(""+i+j));
    }else{
      checkField.setFill(color);
    }
    checkFields.add(checkField);
    this.getChildren().add(checkField);


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

  public void resize(double width) {
    this.size = (0.4 * width) / board.getSize();
    this.fields.clear();
    this.getChildren().clear();
    setBoard();
  }
}

