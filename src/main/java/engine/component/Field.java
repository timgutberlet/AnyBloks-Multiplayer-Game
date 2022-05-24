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
  private final int xcord;
  /**
   * Y Coordinate of the field object in a field.
   */
  private final int ycord;

  /**
   * Field Constructor.
   *
   * @param xcord Coordinate X of the field.
   * @param ycord Coordinate Y of the field.
   */
  public Field(int xcord, int ycord) {
    this.xcord = xcord;
    this.ycord = ycord;
  }

  /**
   * Getter of the X Coordinate.
   *
   * @return returns X Integer
   */
  public int getXcord() {
    return xcord;
  }

  /**
   * Getter of the Y Coordinate.
   *
   * @return returns Y Integer
   */
  public int getYcord() {
    return ycord;
  }
}
