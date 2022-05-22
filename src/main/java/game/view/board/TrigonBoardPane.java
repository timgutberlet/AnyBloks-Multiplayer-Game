package game.view.board;

import engine.component.CheckTrigonField;
import engine.component.Field;
import engine.component.TrigonField;
import engine.handler.ColorHandler;
import engine.handler.InputHandler;
import game.model.board.Board;
import javafx.scene.paint.Color;

/**
 * Trigon Board Class representing the Board for the trigon game
 *
 * @author lbaudenb
 * @author tgutberl
 */
public class TrigonBoardPane extends BoardPane {

  /**
   * Shift variable used for triangle painting
   */
  private final double shift;
  /**
   * Offset Variable used for triangle painting in the parallelogram
   */
  private double xOfSet;
  /**
   * Offset Variable used for triangle painting in the parallelogram
   */
  private double yOfSet;

  /**
   * Constrcutor used for intializing
   * @param board Board of the game logic
   * @param inputHandler Inputhandler used for collision detection
   * @param width width used for board
   */
  public TrigonBoardPane(Board board, InputHandler inputHandler, double width) {
    super(board, inputHandler, width);
    size = 0.4 * width / 18;
    xOfSet = Math.sin(Math.toRadians(30)) * size;
    yOfSet = Math.sin(Math.toRadians(60)) * size;
    shift = 9 * size * 0.5;
    setBoard();
  }

  /**
   * Method that draws a triangle (right in the parallelogram) at the coordinates {i,j}.
   *
   * @param i coord x
   * @param j coord y
   * @param color coolor of triangle
   */
  private void setTriangleRight(int i, int j, Color color) {
    CheckTrigonField checkTrigonField = new CheckTrigonField(i, j, 1);
    double sizeHelp = size * 0.4;
    double move = size / 2 - sizeHelp / 2;

    double yOfSetHelp = yOfSet * 0.4;
    double moveYOfSet = yOfSet / 2 - yOfSetHelp / 2;

    checkTrigonField.getPoints()
        .addAll(xOfSet + size + j * size + i * xOfSet - move - shift,
            yOfSet + i * yOfSet - moveYOfSet, //right vertex
            size + j * size + i * xOfSet - shift, 0.0 + i * yOfSet + moveYOfSet, // top vertex
            xOfSet + j * size + i * xOfSet + move - shift, yOfSet + i * yOfSet - moveYOfSet);
    if (checkFieldColor.containsKey("" + (i * 1000) + j + 1)) {
      checkTrigonField.setFill(checkFieldColor.get("" + (i * 1000) + j + 1));
    } else {
      checkTrigonField.setFill(color);
    }
    checkFields.add(checkTrigonField);
    this.getChildren().add(checkTrigonField);
    Field triangleRight = new TrigonField(i, j, 1);
    triangleRight.getPoints().addAll(
        xOfSet + size + j * size + i * xOfSet - shift, yOfSet + i * yOfSet, //right vertex
        size + j * size + i * xOfSet - shift, 0.0 + i * yOfSet, // top vertex
        xOfSet + j * size + i * xOfSet - shift, yOfSet + i * yOfSet); // left vertex
    triangleRight.setFill(color);
    triangleRight.setStroke(Color.BLACK);
    this.getChildren().add(triangleRight);
    inputHandler.registerField(triangleRight);
  }

  /**
   * Method that draws a triangle (left in the parallelogram) at the coordinates {i,j}.
   *
   * @param i coord x
   * @param j coord y
   * @param color Color of triangle
   */
  private void setTriangleLeft(int i, int j, Color color) {

    CheckTrigonField checkTrigonField = new CheckTrigonField(i, j, 0);
    double sizeHelp = size * 0.4;
    double move = size / 2 - sizeHelp / 2;

    double yOfSetHelp = yOfSet * 0.4;
    double moveYOfSet = yOfSet / 2 - yOfSetHelp / 2;

    checkTrigonField.getPoints()
        .addAll(xOfSet + j * size + i * xOfSet - shift, yOfSet + i * yOfSet - moveYOfSet,
            // top vertex
            size + j * size + i * xOfSet - move - shift, 0.0 + i * yOfSet + moveYOfSet,
            // right vertex
            0.0 + j * size + i * xOfSet + move - shift, 0.0 + i * yOfSet + moveYOfSet);
    if (checkFieldColor.containsKey("" + (i * 1000) + j + 0)) {
      checkTrigonField.setFill(checkFieldColor.get("" + (i * 1000) + j + 0));
    } else {
      checkTrigonField.setFill(color);
    }
    checkFields.add(checkTrigonField);
    this.getChildren().add(checkTrigonField);

    Field triangleLeft = new TrigonField(i, j, 0);
    triangleLeft.getPoints().addAll(
        xOfSet + j * size + i * xOfSet - shift, yOfSet + i * yOfSet, // top vertex
        size + j * size + i * xOfSet - shift, 0.0 + i * yOfSet, // right vertex
        0.0 + j * size + i * xOfSet - shift, 0.0 + i * yOfSet);  // left vertex
    triangleLeft.setFill(color);
    triangleLeft.setStroke(Color.BLACK);
    this.getChildren().add(triangleLeft);
    inputHandler.registerField(triangleLeft);
  }

  /**
   * Method that draws a triangle at the coordinates {i,j}.
   *
   * @param i coord x
   * @param j coord y
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

  /**
   * Sets board of game
   */
  @Override
  public void setBoard() {
    for (int i = 0; i < 18; i++) {
      for (int j = 0; j < 18; j++) {
        setTriangle(i, j);
      }
    }
  }

  /**
   * Resizes board
   * @param width width of board
   */
  public void resize(double width) {
    size = 0.4 * width / 18;
    xOfSet = Math.sin(Math.toRadians(30)) * size;
    yOfSet = Math.sin(Math.toRadians(60)) * size;
    this.fields.clear();
    this.getChildren().clear();
    setBoard();
  }
}
