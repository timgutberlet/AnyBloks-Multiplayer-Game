package game.view.stack;

import engine.handler.InputHandler;
import game.model.player.Player;
import game.model.polygon.Poly;
import game.view.poly.PolyPane;
import game.view.poly.TrigonPolyPane;
import java.util.List;

/**
 * @author lbaudenb
 */
public class StackTrigonPane extends StackPane {


  public StackTrigonPane(Player player, InputHandler inputHandler, List<Poly> polys, double width) {
    super(player, inputHandler, polys, width);
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
      PolyPane polyPane = new TrigonPolyPane(p, inputHandler, width);
      polyPanes.add(polyPane);
    }
  }
}

