package game.view;

import engine.handler.InputHandler;
import game.model.polygon.Poly;
import game.model.polygon.PolyTrigon;
import game.view.poly.PolyPane;
import game.view.poly.TrigonPolyPane;
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
    innerCircle.setFill(Color.GRAY);
    innerCircle.setOpacity(0.5);

    Shape donut = Shape.subtract(outsideCircle, innerCircle);
    donut.setFill(Color.GRAY);

    polyPane.setSize(size);

    /*PolyTrigon poly = (PolyTrigon) polyPane.getPoly();
    double polyX = 0;
    double polyY = 0;
    double xOfSet = Math.sin(Math.toRadians(30)) * size;
    double yOfSet = Math.sin(Math.toRadians(60)) * size;

    int[] res = poly.getWidthArray();

    switch (res[0]){
      case 1 : polyX = size*0.5;break;
      case 2 : polyX = (size + res[1] * xOfSet) * 0.5;break;
      case 3 : polyX = 2*size;break;
      case 4 : polyX = 2*size+ res[1] * xOfSet;break;
    }
    polyY = poly.getWidth() * yOfSet * 0.5;

    double shiftX = circleX-polyX;
    double shiftY = circleY-polyY;
    polyPane.relocate(shiftX,shiftY);*/

    this.getChildren().add(innerCircle);
    this.getChildren().add(donut);
    this.getChildren().add(polyPane);
  }
}
