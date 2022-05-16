package game.view;

import engine.handler.InputHandler;
import game.view.poly.PolyPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

/**
 * @author lbaudenb
 */
public class DragableSuarePane extends DragablePolyPane {

  public DragableSuarePane(PolyPane polyPane, double size, InputHandler inputHandler) {
    super(polyPane, size, inputHandler);
  }

  public void build() {

    circleX = 2.5 * size + size;
    circleY = 2.5 * size + size;

    outsideCircle = new Circle();
    outsideCircle.setCenterX(circleX);
    outsideCircle.setCenterY(circleY);
    outsideCircle.setRadius(2.5 * size + size);

    innerCircle = new Circle();
    innerCircle.setCenterX(circleX);
    innerCircle.setCenterY(circleY);
    innerCircle.setRadius(2.5 * size);
    innerCircle.setFill(Color.GRAY);
    innerCircle.setOpacity(0.5);

    Shape donut = Shape.subtract(outsideCircle, innerCircle);
    donut.setFill(Color.GRAY);

    polyPane.setSize(size);
    double polyX = polyPane.getSize() * (polyPane.getPoly().getHeight() / 2.0);
    double polyY = polyPane.getSize() * (polyPane.getPoly().getWidth() / 2.0);
    double xOfSet = circleX - polyX;
    double yOfSet = circleY - polyY;
    polyPane.relocate(xOfSet, yOfSet);

    this.getChildren().add(donut);
    this.getChildren().add(innerCircle);
    this.getChildren().add(polyPane);

  }
}
