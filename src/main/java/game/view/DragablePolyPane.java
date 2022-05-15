package game.view;

import engine.handler.InputHandler;
import game.model.polygon.Poly;
import game.view.poly.PolyPane;
import javafx.geometry.Bounds;
import javafx.scene.layout.Pane;
import javax.print.DocFlavor.INPUT_STREAM;

/**
 * @author lbaudenb
 */
public class DragablePolyPane extends Pane {

  private final PolyPane polyPane;
  private final double size;
  private final InputHandler inputHandler;
  private boolean mirrored;
  private int rotation;

  public DragablePolyPane(PolyPane polyPane, double size, InputHandler inputHandler) {
    this.polyPane = polyPane;
    this.size = size;
    this.inputHandler = inputHandler;
    this.mirrored = false;
    this.rotation = 0;

    inputHandler.makeDraggable(this);
    build();
  }

  //Check if a Polypane Object intersects with the given Bounds object
  public boolean intersects(Bounds bounds){
    return this.polyPane.getBoundsInParent().intersects(bounds);
  }

  public PolyPane getPolyPane(){
    return polyPane;
  }

  public Poly getPoly(){
    return polyPane.getPoly();
  }

  public boolean getMirrored(){
    return this.mirrored;
  }

  public int getRotation(){
    return this.rotation;
  }

  public void build() {
    polyPane.resize(size);
    this.getChildren().add(polyPane);
  }
}
