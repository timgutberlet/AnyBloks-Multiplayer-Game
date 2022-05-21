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
 * @author lbaudenb
 */

public class BoardPane extends Pane {

  protected final List<Field> fields;
  protected final List<Field> checkFields;
  protected Board board;
  protected HashMap<String, Paint> checkFieldColor;

  protected double size;

  protected InputHandler inputHandler;

  public BoardPane(Board board, InputHandler inputHandler, double width) {
    this.board = board;
    fields = new ArrayList<>();
    checkFields = new ArrayList<>();
    this.inputHandler = inputHandler;
    checkFieldColor = new HashMap<>();
  }

  public void setCheckFieldColor(Paint color, int x, int y) {
    this.checkFieldColor.put("" + (x * 1000) + y, color);
  }

  public void setCheckFieldColor(Paint color, int x, int y, int isRight) {
    this.checkFieldColor.put("" + (x * 1000) + y + isRight, color);
  }

  public void resetCheckFieldColor(int x, int y) {
    this.checkFieldColor.remove("" + (x * 1000) + y);
  }

  public void resetCheckFieldColor(int x, int y, int isRight) {
    this.checkFieldColor.remove("" + (x * 1000) + y + isRight);
  }

  public void resetAllCheckFields() {
    checkFieldColor = new HashMap<>();
  }

  /**
   * Method that returns all field Elements of the Board
   */
  public List<Field> getFields() {
    return this.fields;
  }

  public List<Field> getCheckFields() {
    return this.checkFields;
  }

  /**
   * Method that sets up the BoardPane for the first time Method that updates the BoardPane
   */
  public void setBoard() {
  }

  /**
   * Method that updates the BoardPane
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

  public double getSize() {
    return this.size;
  }

  public void resize(double width) {
  }

}

