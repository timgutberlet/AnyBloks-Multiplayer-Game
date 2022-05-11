package game.view;

import game.model.board.Board;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * @author lbaudenb
 */

public interface BoardPane {

  /**
   * Method that sets up the BoardPane for the first time Method that updates the BoardPane
   */
  void setBoard();

  /**
   * Method that updates the BoardPane
   *
   * @param board
   */
  void repaint(Board board);

}

