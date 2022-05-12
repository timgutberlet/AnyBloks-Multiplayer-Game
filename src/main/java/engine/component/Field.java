package engine.component;

import game.model.polygon.Poly;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;

/**
 * @author lbaudenb
 */
public abstract class Field extends Polygon {

  private int x;
  private int y;

  public Field(int x, int y) {
    this.x = x;
    this.y = y;

  }

}
