package engine.component;

/**
 * TrigonField Object that is extending Field Class, representing a trigon triangle field.
 *
 * @author lbaudenb
 */
public class TrigonField extends Field {

  /**
   * isRight integer, that shows if a triangle of the Trigon is right or left in the parallelogram.
   */
  private final int isRight;

  /**
   * Constructor Method initializing the Trigonfield class.
   *
   * @param x Coordinate X of the field.
   * @param y Coordinate Y of the field.
   * @param isRight isRight Int showing if triangle is right or left in parallelogram.
   */
  public TrigonField(int x, int y, int isRight) {
    super(x, y);
    this.isRight = isRight;
  }

  /**
   * Returns the isRight Integer if triangle is right or left in parallelogram.
   *
   * @return isRight Int showing if triangle is right or left in parallelogram.
   */
  public int getIsRight() {
    return this.isRight;
  }
}
