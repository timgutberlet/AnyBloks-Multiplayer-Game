package engine.handler;

import java.util.HashSet;
import java.util.Set;
import javafx.scene.shape.Rectangle;

/**
 * @author lbaudenb
 */
public class InputHandler {

  private final Set<Rectangle> rectanglesClicked;

  public InputHandler() {
    rectanglesClicked = new HashSet<>();
  }

  public static InputHandler getInstance() {
    return new InputHandler();
  }


  private void clearKeys() {
    rectanglesClicked.clear();
  }


  public void registerRectangle(Rectangle rectangle) {

    rectangle.setOnMouseClicked((e -> {
      rectanglesClicked.add(rectangle);
    }));

  }

  public boolean isRectangleClicked(Rectangle rectangle) {
    return rectanglesClicked.contains(rectangle);
  }

}
