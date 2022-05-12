package game.view.stack;

import engine.handler.InputHandler;
import game.model.player.Player;
import game.model.polygon.Poly;
import game.view.poly.PolyPane;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.GridPane;

/**
 * @author lbaudenb
 */
public abstract class StackPane extends GridPane {

  private final int MAX_NB_POLYS = 8;

  protected List<PolyPane> polyPanes;

  protected InputHandler inputHandler;

  public StackPane(Player player, InputHandler inputHandler, List<Poly> polys) {
    this.inputHandler = inputHandler;
    polyPanes = new ArrayList<>();
    setUpStack(polys);
    setContent();
  }

  public abstract void setUpStack(List<Poly> polys);

  /**
   * Method that fills the StackSquarePane with SquarePolyPane objects
   */
  public void setContent() {
    int row = 0;
    int col = 0;

    for (PolyPane polyPane : polyPanes) {
      this.add(polyPane, col, row);
      col++;
      if (col % MAX_NB_POLYS == 0) {
        row++;
        col = 0;
      }
    }
  }

}