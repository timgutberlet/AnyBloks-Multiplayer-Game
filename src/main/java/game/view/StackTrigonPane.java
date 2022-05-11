package game.view;

import game.model.player.Player;
import game.model.polygon.Poly;
import game.model.polygon.PolyTrigon;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lbaudenb
 */
public class StackTrigonPane extends StackPane {

  private List<TrigonPolyPane> polyPanes;

  public StackTrigonPane(Player player, List<Poly> polys) {
    super(player);
    polyPanes = new ArrayList<>();
    setUpStack(polys);
    setContent();
  }

  /**
   * Method that creates TrigonPolyPane objects, from a list of TrigonPoly objects, and stores them
   * in a list
   *
   * @param polys
   */
  @Override
  public void setUpStack(List<Poly> polys) {
    for (Poly p : polys) {
      TrigonPolyPane polyPane = new TrigonPolyPane((PolyTrigon) p);
      polyPanes.add(polyPane);
    }
  }

  /**
   * Method that fills the StackTrigonPane with TrigonPolyPane objects
   */
  public void setContent() {
    int row = 0;
    int col = 0;

    for (TrigonPolyPane polyPane : polyPanes) {
      this.add(polyPane, col, row);
      col++;
      if (col % 8 == 0) {
        row++;
        col = 0;
      }
    }
  }
}
