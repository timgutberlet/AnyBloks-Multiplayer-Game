package game.view.board;

import engine.component.Field;
import engine.handler.InputHandler;
import game.model.board.Board;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

/**
 * Abstract class representing the Board of the Bloks Game.
 *
 * @author lbaudenb
 * @author tgutberl
 */

public class BoardPane extends Pane {

  /**
   * List representing a specific field on the board.
   */
  protected final List<Field> fields;
  /**
   * List of smaller fields in a big field on the board that is being used for the collision
   * detection algorithm.
   */
  protected final List<Field> checkFields;
  /**
   * Board variable that we get from the game logic to paint the board.
   */
  protected Board board;
  /**
   * Color of the Fields used for collision detection.
   */
  protected HashMap<String, Paint> checkFieldColor;
  /**
   * size of the fields of the board controlling the overall boardsize also.
   */
  protected double size;
  /**
   * Inputhandler used for detection collision with the checkfields.
   */
  protected InputHandler inputHandler;

  /**
   * Constructor used to initalize all variables and to set board and inputhandler.
   *
   * @param board        Board of the game logic
   * @param inputHandler Handler used to control inputs for collision
   * @param width        width of frame
   * @author lbaudenb
   */
  public BoardPane(Board board, InputHandler inputHandler, double width) {
    this.board = board;
    fields = new ArrayList<>();
    checkFields = new ArrayList<>();
    this.inputHandler = inputHandler;
    checkFieldColor = new HashMap<>();
  }

  /**
   * Method to set the color of the checkfields.
   *
   * @param color Color of the collision field
   * @param x     coord x
   * @param y     coord y
   * @author tgutberl
   */
  public void setCheckFieldColor(Paint color, int x, int y) {
    this.checkFieldColor.put("" + (x * 1000) + y, color);
  }

  /**
   * Method to set the color of the ckeckfields in trigon.
   *
   * @param color   Color of the collision field
   * @param x       coord x
   * @param y       coord y
   * @param isRight marks if right or left triangle is to be painted
   * @author tgutberl
   */
  public void setCheckFieldColor(Paint color, int x, int y, int isRight) {
    this.checkFieldColor.put("" + (x * 1000) + y + isRight, color);
  }

  /**
   * Resets a checkfield to not being painted.
   *
   * @param x coord x
   * @param y coord y
   * @author tgutberl
   */
  public void resetCheckFieldColor(int x, int y) {
    this.checkFieldColor.remove("" + (x * 1000) + y);
  }

  /**
   * Resets a checkfield to not being painted.
   *
   * @param x       coord x
   * @param y       coord y
   * @param isRight marks if right or left triangle is to be painted.
   * @author tgutberl
   */
  public void resetCheckFieldColor(int x, int y, int isRight) {
    this.checkFieldColor.remove("" + (x * 1000) + y + isRight);
  }

  /**
   * Resets all checkfields.
   *
   * @author tgutberl
   */
  public void resetAllCheckFields() {
    checkFieldColor = new HashMap<>();
  }

  /**
   * Method that returns all field Elements of the Board.
   *
   * @return Returns the field elements
   * @author tgutberl
   */
  public List<Field> getFields() {
    return this.fields;
  }

  /**
   * Returns the Checkfield list.
   *
   * @return Returns all Chekcfields
   * @author tgutberl
   */
  public List<Field> getCheckFields() {
    return this.checkFields;
  }

  /**
   * Method that sets up the BoardPane for the first time Method that updates the BoardPane.
   */
  public void setBoard() {
  }

  /**
   * Method that updates the BoardPane.
   *
   * @param board
   */
  public void repaint(Board board) {
    this.board = board;
    this.fields.clear();
    this.checkFields.clear();
    this.getChildren().clear();
    setBoard();
  }

  /**
   * Returns Size.
   *
   * @return Size Element of the board.
   * @author lbaudenb
   */
  public double getSize() {
    return this.size;
  }

  /**
   * Resizes the Boardpane.
   *
   * @param width
   */
  public void resize(double width) {
  }

}

