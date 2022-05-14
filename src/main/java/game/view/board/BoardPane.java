package game.view.board;

import engine.component.Field;
import engine.handler.InputHandler;
import game.model.board.Board;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.Pane;

/**
 * @author lbaudenb
 */

public class BoardPane extends Pane {

  protected final List<Field> fields;

  protected Board board;

  protected final double size = 40;

  protected InputHandler inputHandler;

  public BoardPane(Board board, InputHandler inputHandler) {
    this.board = board;
    this.inputHandler = inputHandler;
    fields = new ArrayList<>();
    setBoard();
  }

  /**
   * Method that returns all field Elements of the Board
   */
  public List<Field> getFields() {
    return this.fields;
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
    this.getChildren().clear();
    setBoard();
  }

  public double getSize() {
    return this.size;
  }

}

