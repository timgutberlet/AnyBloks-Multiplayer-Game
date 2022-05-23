package engine.component;

/**
 * This is the Checkfield Class representing a field, that is getting checked for collision and
 * if a Poly can be placed. This is used for the Classic Gamemodes and not Trigon.
 *
 * @author tgutberl
 */
public class CheckField extends Field {

  /**
   * Checkfield constructor initializing the Checkfield
   *
   * @param x Coordinate x of the game
   * @param y Coordinate y of the game
   */
  public CheckField(int x, int y) {
    super(x, y);
  }
}
