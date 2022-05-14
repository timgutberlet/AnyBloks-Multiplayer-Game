package engine.component;

import javafx.scene.shape.Polygon;

/**
 * @author lbaudenb
 */
public abstract class Field extends Polygon {

  private final int x;
  private final int y;

  public Field(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }
}
