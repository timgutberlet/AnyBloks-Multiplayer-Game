package game.view;

import game.model.polygon.PolySquare;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author lbaudenb
 */
public class SquarePolyPane extends GridPane {

  private PolySquare poly;

  public SquarePolyPane(PolySquare poly) {
    this.poly = poly;
    setPoly();
  }

  public void setSquare(int i, int j, game.model.Color color) {
    Rectangle r = new Rectangle(7, 7);
    r.setFill(toColor(color));
    this.add(r, i, j);
  }

  public void setPoly() {
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        if (poly.containsField(i, j)) {
          setSquare(i, j, poly.getColor());
        } else {
          setSquare(i, j, game.model.Color.WHITE);
        }

      }
    }
  }

  public Color toColor(game.model.Color color) {
    switch (color) {
      case RED:
        return Color.RED;
      case BLUE:
        return Color.BLUE;
      case GREEN:
        return Color.GREEN;
      case YELLOW:
        return Color.YELLOW;
      default:
        return Color.WHITE;
    }
  }
}
