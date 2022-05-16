package game.view;

import engine.handler.InputHandler;
import game.model.polygon.Poly;
import game.view.poly.PolyPane;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

/**
 * @author lbaudenb
 */
public class DragablePolyPane extends Pane {

  protected PolyPane polyPane;
  protected final double size;
  protected final InputHandler inputHandler;

  private boolean mirrored;
  private int rotation;

  protected Button rotateRight;
  protected Button rotateLeft;
  protected Button mirror;

  protected Circle innerCircle;
  protected Circle outsideCircle;

  protected double circleX;
  protected double circleY;


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
  }

  public void buttons() {
    mirror = new Button("M");
    mirror.setPrefWidth(size);
    mirror.setPrefHeight(size);
    rotateRight = new Button("R");
    rotateRight.setPrefWidth(size);
    rotateRight.setPrefHeight(size);
    rotateLeft = new Button("L");
    rotateLeft.setPrefWidth(size);
    rotateLeft.setPrefHeight(size);

    mirror.setOnMouseClicked(e -> {
      polyPane.getPoly().mirror();
      rerender();
    });
    rotateRight.setOnMouseClicked(e -> {
      polyPane.getPoly().rotateRight();
      rerender();
    });
    rotateLeft.setOnMouseClicked(e -> {
      polyPane.getPoly().rotateLeft();
      rerender();
    });

    mirror.relocate(circleX - size * 0.5, 0);
    rotateLeft.relocate(0, circleX - size * 0.5);
    rotateRight.relocate(2 * circleX - size, circleY - size * 0.5);
    this.getChildren().add(mirror);
    this.getChildren().add(rotateLeft);
    this.getChildren().add(rotateRight);
  }

  public void setPoly(PolyPane polyPane) {
    this.polyPane = polyPane;
    this.getChildren().clear();
    rerender();
  }

  public void rerender() {
    this.getChildren().clear();
    build();
    buttons();
  }

}

