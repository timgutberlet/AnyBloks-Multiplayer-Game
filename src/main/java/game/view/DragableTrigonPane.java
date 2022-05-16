package game.view;

import engine.handler.InputHandler;
import game.model.field.FieldTrigon;
import game.model.polygon.PolyTrigon;
import game.view.poly.PolyPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

/**
 * @author lbaudenb
 */
public class DragableTrigonPane extends DragablePolyPane {

  public DragableTrigonPane(PolyPane polyPane, double size, InputHandler inputHandler) {
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
    innerCircle.setFill(this.innerCircleColor);
    innerCircle.setOpacity(0.5);

    Shape donut = Shape.subtract(outsideCircle, innerCircle);
    donut.setFill(Color.GRAY);

    polyPane.setSize(size);

    double polyX;
    double polyY;
    double yOfSet = Math.sin(Math.toRadians(60)) * size;
    double x = 0;
    double y = 0;
    double right = 0;
    for (FieldTrigon ft : ((PolyTrigon) polyPane.getPoly()).getShape()) {
      x += ft.getPos()[0];
      y += ft.getPos()[1];
      right += ft.getPos()[2];
    }
    x /= polyPane.getPoly().getSize();
    y /= polyPane.getPoly().getSize();
    right /= polyPane.getPoly().getSize();

    polyY = (x + 0.5) * yOfSet;
    polyX = (y + 0.5 + 0.5 * x + 0.5 * right) * size;

    double shiftX = circleX - polyX;
    double shiftY = circleY - polyY;

    polyPane.relocate(shiftX, shiftY);

    this.getChildren().add(innerCircle);
    this.getChildren().add(donut);
    this.getChildren().add(polyPane);
  }
}
