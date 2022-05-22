package engine.handler;

import game.model.Color;

/**
 * @author lbaudenb
 */
public class ColorHandler {
  public static boolean darkMode = false;
  /**
   * Method that converts game.model.Color objects  to javafx.scene.paint.Color objects
   *
   * @param color
   * @return
   */
  public static javafx.scene.paint.Color getJavaColor(Color color) {
    if (darkMode) {
      switch (color) {
        case RED:
          return javafx.scene.paint.Color.DARKRED;
        case BLUE:
          return javafx.scene.paint.Color.DARKBLUE;
        case GREEN:
          return javafx.scene.paint.Color.DARKGREEN;
        case YELLOW:
          return javafx.scene.paint.Color.DARKORANGE;
        default:
          return javafx.scene.paint.Color.TRANSPARENT;
      }
    } else {
      switch (color) {
        case RED:
          return javafx.scene.paint.Color.RED;
        case BLUE:
          return javafx.scene.paint.Color.BLUE;
        case GREEN:
          return javafx.scene.paint.Color.GREEN;
        case YELLOW:
          return javafx.scene.paint.Color.YELLOW;
        default:
          return javafx.scene.paint.Color.TRANSPARENT;
      }
    }
  }
}
