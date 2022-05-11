package game.view;

import game.model.player.Player;
import game.model.polygon.Poly;
import game.model.polygon.PolySquare;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lbaudenb
 */
public class StackSquarePane extends StackPane {

  private List<SquarePolyPane> polyPanes;

  public StackSquarePane(Player player, List<Poly> polys) {
    super(player);
    polyPanes = new ArrayList<>();
    setUpStack(polys);
    setContent();
  }

  /**
   * Method that creates SquarePolyPane objects, from a list of SquarePoly objects, and stores them
   * in a list
   *
   * @param polys
   */
  @Override
  public void setUpStack(List<Poly> polys) {
    for (Poly p : polys) {
      SquarePolyPane polyPane = new SquarePolyPane((PolySquare) p);
      polyPanes.add(polyPane);
    }
  }

  /**
   * Method that fills the StackSquarePane with SquarePolyPane objects
   */
  public void setContent() {
    int row = 0;
    int col = 0;

    for (SquarePolyPane polyPane : polyPanes) {
      this.add(polyPane, col, row);
      col++;
      if (col % 8 == 0) {
        row++;
        col = 0;
      }
    }
  }
}
