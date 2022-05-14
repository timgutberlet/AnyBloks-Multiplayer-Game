package game.view;

import engine.handler.InputHandler;
import game.view.poly.PolyPane;
import javafx.scene.layout.Pane;

/**
 * @author lbaudenb
 */
public class DragablePolyPane extends Pane {

  private PolyPane polyPane;
  private double size;
  private InputHandler inputHandler;

  public DragablePolyPane(PolyPane polyPane, double size, InputHandler inputHandler) {
    this.polyPane = polyPane;
    this.size = size;
    this.inputHandler = inputHandler;
    inputHandler.makeDraggable(this);
    build();
  }

  public void build() {
    polyPane.resize(size);
    this.getChildren().add(polyPane);
  }
}
