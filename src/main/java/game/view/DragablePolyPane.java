package game.view;

import engine.handler.InputHandler;
import game.model.polygon.Poly;
import game.view.poly.PolyPane;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javax.print.DocFlavor.INPUT_STREAM;

/**
 * @author lbaudenb
 */
public class DragablePolyPane extends Pane {

  private PolyPane polyPane;
  private final double size;
  private final InputHandler inputHandler;

  private boolean mirrored;
  private int rotation;

  private Button rotateRight;
  private Button rotateLeft;
  private Button mirror;

  private Circle innerCircle;


  public DragablePolyPane(PolyPane polyPane, double size, InputHandler inputHandler) {
    this.polyPane = polyPane;
    this.size = size;
    this.inputHandler = inputHandler;

    this.mirrored = false;
    this.rotation = 0;

    inputHandler.makeDraggable(this);
    build();
    buttons();
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
    innerCircle = new Circle();
    double innerCircleX = 2.5 * size + size;
    double innerCircleY = 2.5 * size + size;
    innerCircle.setCenterX(innerCircleX);
    innerCircle.setCenterY(innerCircleY);
    innerCircle.setRadius(innerCircleX);
    innerCircle.setFill(Color.GRAY);

    double polyX = polyPane.getSize() * (polyPane.getPoly().getWidth() / 2.0);
    double polyY = polyPane.getSize() * (polyPane.getPoly().getHeight() / 2.0);
    double xOfSet = innerCircleX - polyX;
    double yOfSet = innerCircleY - polyY;
    polyPane.setSize(size);
    polyPane.relocate(xOfSet, yOfSet);

    this.getChildren().add(innerCircle);
    this.getChildren().add(polyPane);

  }

  public void buttons() {
    mirror = new Button("Mirror");
    rotateRight = new Button("Right");
    rotateLeft = new Button("Left");
    mirror.setOnMouseClicked(e -> {
      polyPane.getPoly().mirror();
      build();
    });
    rotateRight.setOnMouseClicked(e -> {
      polyPane.getPoly().rotateRight();
      build();
    });
    rotateLeft.setOnMouseClicked(e -> {
      polyPane.getPoly().rotateLeft();
      build();
    });
    /*this.setTop(mirror);
    this.setRight(rotateRight);
    this.setLeft(rotateLeft);*/
  }

  public void setPoly(PolyPane polyPane) {
    this.polyPane = polyPane;
    this.getChildren().clear();
    build();
  }

}

