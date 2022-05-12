package game.view.stack;

import engine.handler.InputHandler;
import game.model.player.Player;
import game.model.polygon.Poly;
import game.view.poly.PolyPane;
import game.view.poly.SquarePolyPane;
import java.util.List;

/**
 * @author lbaudenb
 */
public class StackSquarePane extends StackPane {

  public StackSquarePane(Player player, InputHandler inputHandler, List<Poly> polys) {
    super(player, inputHandler, polys);
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
      PolyPane polyPane = new SquarePolyPane(p, inputHandler);
      polyPanes.add(polyPane);
    }
  }
}

