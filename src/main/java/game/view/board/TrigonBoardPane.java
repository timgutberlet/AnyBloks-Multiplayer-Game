package game.view.board;

import engine.component.CheckTrigonField;
import engine.component.Field;
import engine.component.TrigonField;
import engine.handler.ColorHandler;
import engine.handler.InputHandler;
import game.model.board.Board;
import javafx.scene.paint.Color;

/**
 * @author lbaudenb
 */
public class TrigonBoardPane extends BoardPane {

  private final double xOfSet;
  private final double yOfSet;

  public TrigonBoardPane(Board board, InputHandler inputHandler, double width) {
    super(board, inputHandler, width);
    size = 0.4 * width / 18;
    xOfSet = Math.sin(Math.toRadians(30)) * size;
    yOfSet = Math.sin(Math.toRadians(60)) * size;
    setBoard();
  }

  /**
   * Method that draws a triangle (right in the parallelogram) at the coordinates {i,j}
   *
   * @param i
   * @param j
   * @param color
   */
  private void setTriangleRight(int i, int j, Color color) {

    CheckTrigonField checkTrigonField = new CheckTrigonField(i, j, 1);
    double sizeHelp = size * 0.4;
    double move = size / 2 - sizeHelp / 2;
    checkTrigonField.getPoints()
        .addAll(xOfSet + sizeHelp + j * size + i * xOfSet + move, yOfSet + i * yOfSet + move, //right vertex
            sizeHelp + j * size + i * xOfSet + move, 0.0 + i * yOfSet + move, // top vertex
            xOfSet + j * size + i * xOfSet + move, yOfSet + i * yOfSet + move);
    checkTrigonField.setFill(Color.RED);
    checkFields.add(checkTrigonField);
    this.getChildren().add(checkTrigonField);

    Field triangleRight = new TrigonField(i, j, 1);
    triangleRight.getPoints().addAll(
        xOfSet + size + j * size + i * xOfSet, yOfSet + i * yOfSet, //right vertex
        size + j * size + i * xOfSet, 0.0 + i * yOfSet, // top vertex
        xOfSet + j * size + i * xOfSet, yOfSet + i * yOfSet); // left vertex
    triangleRight.setFill(color);
    triangleRight.setStroke(Color.BLACK);
    this.getChildren().add(triangleRight);
    inputHandler.registerField(triangleRight);
  }

  /**
   * Method that draws a triangle (left in the parallelogram) at the coordinates {i,j}
   *
   * @param i
   * @param j
   * @param color
   */
  private void setTriangleLeft(int i, int j, Color color) {
    Field triangleLeft = new TrigonField(i, j, 0);
    triangleLeft.getPoints().addAll(
        xOfSet + j * size + i * xOfSet, yOfSet + i * yOfSet, // top vertex
        size + j * size + i * xOfSet, 0.0 + i * yOfSet, // right vertex
        0.0 + j * size + i * xOfSet, 0.0 + i * yOfSet);  // left vertex
    triangleLeft.setFill(color);
    triangleLeft.setStroke(Color.BLACK);
    this.getChildren().add(triangleLeft);
    inputHandler.registerField(triangleLeft);
  }

  /**
   * Method that draws a triangle at the coordinates {i,j}
   *
   * @param i
   * @param j
   */
  private void setTriangle(int i, int j) {
    Color color;
    int[] pos;
    if (i + j == 8) {
      pos = new int[]{i, j, 1};
      color = ColorHandler.getJavaColor(board.getColor(pos));
      setTriangleRight(i, j, color);
    }
    if (i + j == 26) {
      pos = new int[]{i, j, 0};
      color = ColorHandler.getJavaColor(board.getColor(pos));
      setTriangleLeft(i, j, color);
    }
    if (i + j > 8 && i + j < 26) {
      pos = new int[]{i, j, 1};
      color = ColorHandler.getJavaColor(board.getColor(pos));
      setTriangleRight(i, j, color);
      pos = new int[]{i, j, 0};
      color = ColorHandler.getJavaColor(board.getColor(pos));
      setTriangleLeft(i, j, color);
    }
  }

  @Override
  public void setBoard() {
    for (int i = 0; i < 18; i++) {
      for (int j = 0; j < 18; j++) {
        setTriangle(i, j);
      }
    }
  }
}