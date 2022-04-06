package game.view;

import game.model.Player;
import game.model.Poly;
import game.model.PolySquare;
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

  public void setUpStack(List<Poly> polys) {
    for (Poly p : polys) {
      SquarePolyPane polyPane = new SquarePolyPane((PolySquare) p);
      polyPanes.add(polyPane);
    }
  }

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