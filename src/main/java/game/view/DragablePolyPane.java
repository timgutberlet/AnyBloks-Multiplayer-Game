package game.view;

import engine.component.Field;
import engine.handler.InputHandler;
import game.controller.InGameUiController;
import game.model.polygon.Poly;
import game.view.poly.PolyPane;
import java.util.List;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

/**
 * DragablePolyPane to make PolyPane dragable.
 *
 * @author lbaudenb
 */
public class DragablePolyPane extends Pane {

  protected final double size;
  protected final InputHandler inputHandler;
  protected final InGameUiController inGameUiController;
  protected PolyPane polyPane;
  protected Button rotateRight;
  protected Button rotateLeft;
  protected Button mirror;
  protected Button submit;

  protected Circle innerCircle;
  protected Shape donut;

  protected Color innerCircleColor;

  protected double circleX;
  protected double circleY;

  /**
   * DragablePolyPane to make PolyPane dragable.
   *
   * @param polyPane           PolyPane, clicked by Player.
   * @param size               Size of the PolyPane.
   * @param inputHandler       InputHandler from GameState.
   * @param inGameUiController Current InGameUiController.
   */
  public DragablePolyPane(PolyPane polyPane, double size, InputHandler inputHandler,
      InGameUiController inGameUiController) {
    this.polyPane = polyPane;
    this.size = size;
    this.inputHandler = inputHandler;
    this.innerCircleColor = Color.GRAY;
    this.inGameUiController = inGameUiController;

    inputHandler.makeDraggable(this);

    build();
    buttons();
  }

  public void inncerCircleSetColor() {
    this.innerCircleColor = Color.YELLOW;
  }

  public void inncerCircleResetColor() {
    this.innerCircleColor = Color.GRAY;
  }


  public List<Field> getField() {
    return this.polyPane.getFields();
  }

  public Poly getPoly() {
    return polyPane.getPoly();
  }

  public Field getCheckPolyField() {
    return this.polyPane.getCheckPolyField();
  }

  public void build() {
  }

  /**
   * Method to initialize all Buttons.
   */
  public void buttons() {
    mirror = new Button("M");
    mirror.setPrefWidth(size + 3);
    mirror.setPrefHeight(size);
    rotateRight = new Button("R");
    rotateRight.setPrefWidth(size);
    rotateRight.setPrefHeight(size);
    rotateLeft = new Button("L");
    rotateLeft.setPrefWidth(size);
    rotateLeft.setPrefHeight(size);
    submit = new Button("P");
    submit.setPrefWidth(size);
    submit.setPrefHeight(size);

    mirror.setOnMouseClicked(e -> {
      polyPane.getPoly().mirror();
      inGameUiController.paintPossibleFields(this);
      inGameUiController.repaintBoardPane();
      rerender();
    });
    rotateRight.setOnMouseClicked(e -> {
      polyPane.getPoly().rotateRight();
      inGameUiController.paintPossibleFields(this);
      inGameUiController.repaintBoardPane();
      rerender();
    });
    rotateLeft.setOnMouseClicked(e -> {
      polyPane.getPoly().rotateLeft();
      inGameUiController.paintPossibleFields(this);
      inGameUiController.repaintBoardPane();
      rerender();
    });
    submit.setOnMouseClicked(e -> {
      inGameUiController.setSubmitRequested();
    });

    mirror.relocate(circleX - size * 0.5, 0);
    rotateLeft.relocate(0, circleX - size * 0.5);
    rotateRight.relocate(2 * circleX - size, circleY - size * 0.5);
    submit.relocate(circleX - size * 0.5, 2 * (circleY - size * 0.5));

    this.getChildren().add(mirror);
    this.getChildren().add(rotateLeft);
    this.getChildren().add(rotateRight);
    this.getChildren().add(submit);
  }

  /**
   * Method to rerender DragablePolyPane. Buttons are not rerenderd.
   */
  public void rerender() {
    this.getChildren().remove(innerCircle);
    this.getChildren().remove(donut);
    this.getChildren().remove(polyPane);
    build();
    mirror.toFront();
    rotateLeft.toFront();
    rotateRight.toFront();
    submit.toFront();
  }

  public double getCircleX() {
    return circleX;
  }

  public double getCircleY() {
    return circleY;
  }
}

