package engine.component;

import javafx.scene.shape.Polygon;

/**
 * Abstractfield class that represents a field Object that is used for Polys, Boards usw..
 *
 * @author lbaudenb
 */
public abstract class Field extends Polygon {

  /**
   * X Coordinate of the field object in a field.
   */
  private final int x;
  /**
   * Y Coordinate of the field object in a field.
   */
  private final int y;

  /**
   * Field Constructor.
   *
   * @param x Coordinate X of the field.
   * @param y Coordinate Y of the field.
   */
  public Field(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Getter of the X Coordinate.
   *
   * @return returns X Integer
   */
  public int getX() {
    return x;
  }

  /**
   * Getter of the Y Coordinate.
   *
   * @return returns Y Integer
   */
  public int getY() {
    return y;
  }
}
