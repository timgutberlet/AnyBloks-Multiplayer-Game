package game.view;

import engine.component.Field;
import engine.handler.InputHandler;
import game.controller.InGameUiController;
import game.view.poly.PolyPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

/**
 * @author lbaudenb
 */
public class DragableSuarePane extends DragablePolyPane {

  public DragableSuarePane(PolyPane polyPane, double size, InputHandler inputHandler,
      InGameUiController inGameUiController) {
    super(polyPane, size, inputHandler, inGameUiController);
  }


  public void build() {

    circleX = 2.5 * size + size;
    circleY = 2.5 * size + size;

    Circle outsideCircle = new Circle();
    outsideCircle.setCenterX(circleX);
    outsideCircle.setCenterY(circleY);
    outsideCircle.setRadius(2.5 * size + size);

    innerCircle = new Circle();
    innerCircle.setCenterX(circleX);
    innerCircle.setCenterY(circleY);
    innerCircle.setRadius(2.5 * size);
    innerCircle.setFill(innerCircleColor);
    innerCircle.setOpacity(0.5);

    donut = Shape.subtract(outsideCircle, innerCircle);
    donut.setFill(Color.GRAY);

    polyPane.setSize(size);
    /*double polyX = 0;
    double polyY = 0;
    for(Field field : polyPane.getFields()){
      polyX += field.getX();
      polyY += field.getY();
    }
    polyX /= polyPane.getFields().size();
    polyY /= polyPane.getFields().size();

    polyX *= polyPane.getSize();
    polyY *= polyPane.getSize();*/
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
