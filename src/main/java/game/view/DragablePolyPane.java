package game.view;

import engine.component.Field;
import engine.handler.InputHandler;
import game.model.polygon.Poly;
import game.view.poly.PolyPane;
import java.util.List;
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


  protected Button rotateRight;
  protected Button rotateLeft;
  protected Button mirror;

  protected Circle innerCircle;
  protected Circle outsideCircle;

  protected Color innerCircleColor;

  protected double circleX;
  protected double circleY;


  public DragablePolyPane(PolyPane polyPane, double size, InputHandler inputHandler) {
    this.polyPane = polyPane;
    this.size = size;
    this.inputHandler = inputHandler;
    this.innerCircleColor = Color.GRAY;

    inputHandler.makeDraggable(this);

    build();
    buttons();
  }

  public void inncerCircleSetColor(){
    this.innerCircleColor = Color.YELLOW;
  }
  public void inncerCircleResetColor(){
    this.innerCircleColor = Color.GRAY;
  }

  public Field showLocation(){
    return this.polyPane.getFields().get(0);
  }

  public List<Field> getField(){
    return this.polyPane.getFields();
  }

  public Poly getPoly(){
    return polyPane.getPoly();
  }
  public Field getCheckPolyField(){
    return this.polyPane.getCheckPolyField();
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

