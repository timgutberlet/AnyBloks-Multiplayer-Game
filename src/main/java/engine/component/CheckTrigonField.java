package engine.component;

/**
 * Checkfield used for the Trigon Collision Detection and Detection if Poly is possible.
 *
 * @author tgutberl
 */
public class CheckTrigonField extends TrigonField {

  /**
   * Constructor for the CheckTrigonField.
   *
   * @param x       Coordinate X of the Checktrigonfield.
   * @param y       Coordinate Y of the Checktrigonfield.
   * @param isRight Coordinate that shows if a Triangle ist right or left in the parrallelogram.
   */
  public CheckTrigonField(int x, int y, int isRight) {
    super(x, y, isRight);
  }
}
