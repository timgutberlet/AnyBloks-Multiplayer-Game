package game.view.board;

import engine.component.CheckTrigonField;
import engine.component.Field;
import engine.component.TrigonField;
import engine.handler.ColorHandler;
import engine.handler.InputHandler;
import game.model.board.Board;
import javafx.scene.paint.Color;

/**
 * Trigon Board Class representing the Board for the trigon game.
 *
 * @author lbaudenb
 * @author tgutberl
 */
public class TrigonBoardPane extends BoardPane {

  /**
   * Shift variable used for triangle painting.
   */
  private final double shift;
  /**
   * Offset Variable used for triangle painting in the parallelogram.
   */
  private double ofSetX;
  /**
   * Offset Variable used for triangle painting in the parallelogram.
   */
  private double ofSetY;

  /**
   * Constructor for TrigonBoardPane.
   *
   * @param board        Board of the game logic
   * @param inputHandler Inputhandler used for collision detection
   * @param width        width used for board
   */

  public TrigonBoardPane(Board board, InputHandler inputHandler, double width) {
    super(board, inputHandler);
    size = 0.4 * width / 18;
    ofSetX = Math.sin(Math.toRadians(30)) * size;
    ofSetY = Math.sin(Math.toRadians(60)) * size;
    shift = 9 * size * 0.5;
    setBoard();
  }

  /**
   * Method that draws a triangle (right in the parallelogram) at the coordinates {i,j}.
   *
   * @param i     coord x
   * @param j     coord y
   * @param color coolor of triangle
   */
  private void setTriangleRight(int i, int j, Color color) {
    CheckTrigonField checkTrigonField = new CheckTrigonField(i, j, 1);
    double sizeHelp = size * 0.4;
    double move = size / 2 - sizeHelp / 2;

    double ofSetHelpY = ofSetY * 0.4;
    double moveOfSetY = ofSetY / 2 - ofSetHelpY / 2;

    checkTrigonField.getPoints()
        .addAll(ofSetX + size + j * size + i * ofSetX - move - shift,
            ofSetY + i * ofSetY - moveOfSetY, //right vertex
            size + j * size + i * ofSetX - shift, 0.0 + i * ofSetY + moveOfSetY, // top vertex
            ofSetX + j * size + i * ofSetX + move - shift, ofSetY + i * ofSetY - moveOfSetY);
    if (checkFieldColor.containsKey("" + (i * 1000) + j + 1)) {
      checkTrigonField.setFill(checkFieldColor.get("" + (i * 1000) + j + 1));
    } else {
      checkTrigonField.setFill(color);
    }
    checkFields.add(checkTrigonField);
    this.getChildren().add(checkTrigonField);
    Field triangleRight = new TrigonField(i, j, 1);
    triangleRight.getPoints().addAll(
        ofSetX + size + j * size + i * ofSetX - shift, ofSetY + i * ofSetY, //right vertex
        size + j * size + i * ofSetX - shift, 0.0 + i * ofSetY, // top vertex
        ofSetX + j * size + i * ofSetX - shift, ofSetY + i * ofSetY); // left vertex
    triangleRight.setFill(color);
    triangleRight.setStroke(ColorHandler.getBoardStrokeColor());
    this.getChildren().add(triangleRight);
    inputHandler.registerField(triangleRight);
  }

  /**
   * Method that draws a triangle (left in the parallelogram) at the coordinates {i,j}.
   *
   * @param i     coord x
   * @param j     coord y
   * @param color Color of triangle
   */
  private void setTriangleLeft(int i, int j, Color color) {

    CheckTrigonField checkTrigonField = new CheckTrigonField(i, j, 0);
    double sizeHelp = size * 0.4;
    double move = size / 2 - sizeHelp / 2;

    double ofSetHelpY = ofSetY * 0.4;
    double moveOfSetY = ofSetY / 2 - ofSetHelpY / 2;

    checkTrigonField.getPoints()
        .addAll(ofSetX + j * size + i * ofSetX - shift, ofSetY + i * ofSetY - moveOfSetY,
            // top vertex
            size + j * size + i * ofSetX - move - shift, 0.0 + i * ofSetY + moveOfSetY,
            // right vertex
            0.0 + j * size + i * ofSetX + move - shift, 0.0 + i * ofSetY + moveOfSetY);
    if (checkFieldColor.containsKey("" + (i * 1000) + j + 0)) {
      checkTrigonField.setFill(Color.BLACK);
      //checkTrigonField.setFill(checkFieldColor.get("" + (i * 1000) + j + 0));
    } else {
      checkTrigonField.setFill(Color.BLACK);
    }
    checkFields.add(checkTrigonField);
    this.getChildren().add(checkTrigonField);

    Field triangleLeft = new TrigonField(i, j, 0);
    triangleLeft.getPoints().addAll(
        ofSetX + j * size + i * ofSetX - shift, ofSetY + i * ofSetY, // top vertex
        size + j * size + i * ofSetX - shift, 0.0 + i * ofSetY, // right vertex
        0.0 + j * size + i * ofSetX - shift, 0.0 + i * ofSetY);  // left vertex
    triangleLeft.setFill(color);
    triangleLeft.setStroke(ColorHandler.getBoardStrokeColor());
    this.getChildren().add(triangleLeft);
    inputHandler.registerField(triangleLeft);
  }

  /**
   * Method to draw the triangle out of the board bounds.
   *
   * @param i     coord x
   * @param j     coord y
   * @param color Color of triangle
   */
  private void setTriangleCheckfield(int i, int j, Color color) {

    CheckTrigonField checkTrigonField = new CheckTrigonField(i, j, 0);
    double sizeHelp = size * 0.4;
    double move = size / 2 - sizeHelp / 2;

    double ofSetHelpY = ofSetY * 0.4;
    double moveOfSetY = ofSetY / 2 - ofSetHelpY / 2;

    checkTrigonField.getPoints()
        .addAll(ofSetX + j * size + i * ofSetX - shift, ofSetY + i * ofSetY - moveOfSetY,
            // top vertex
            size + j * size + i * ofSetX - move - shift, 0.0 + i * ofSetY + moveOfSetY,
            // right vertex
            0.0 + j * size + i * ofSetX + move - shift, 0.0 + i * ofSetY + moveOfSetY);
    checkTrigonField.setFill(color);
    checkFields.add(checkTrigonField);
    this.getChildren().add(checkTrigonField);
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
    if (i == -1 || i == -2 || i == -3 || i == -4) {
      pos = new int[]{i, j, 0};
      setTriangleCheckfield(i, j, Color.BLACK);
    } else if (i + j == 7 || i + j == 6 || i + j == 5 || i + j == 4) {
      pos = new int[]{i, j, 0};
      setTriangleCheckfield(i, j, Color.BLACK);
    } else if (i + j == 8) {
      pos = new int[]{i, j, 1};
      color = ColorHandler.getJavaColor(board.getColor(pos));
      setTriangleCheckfield(i, j, Color.BLACK);
      setTriangleRight(i, j, color);
    } else if (i + j == 26) {
      pos = new int[]{i, j, 0};
      color = ColorHandler.getJavaColor(board.getColor(pos));
      setTriangleLeft(i, j, color);
    } else if (i + j > 8 && i + j < 26) {
      pos = new int[]{i, j, 1};
      color = ColorHandler.getJavaColor(board.getColor(pos));
      setTriangleRight(i, j, color);
      pos = new int[]{i, j, 0};
      color = ColorHandler.getJavaColor(board.getColor(pos));
      setTriangleLeft(i, j, color);
    }
  }

  /**
   * Sets Board.
   */
  @Override
  public void setBoard() {
    for (int i = -1; i < 18; i++) {
      for (int j = 0; j < 18; j++) {
        setTriangle(i, j);
      }
    }
  }

  /**
   * Resizes Board.
   *
   * @param width width of board
   */
  public void resize(double width) {
    size = 0.4 * width / 18;
    ofSetX = Math.sin(Math.toRadians(30)) * size;
    ofSetY = Math.sin(Math.toRadians(60)) * size;
    this.fields.clear();
    this.getChildren().clear();
    setBoard();
  }
}
