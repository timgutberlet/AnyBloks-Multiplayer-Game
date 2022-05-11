package game.view;

import game.model.polygon.PolyTrigon;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

/**
 * @author lbaudenb
 */

public class TrigonPolyPane extends Pane {

  private PolyTrigon poly;

  private final double size = 10;
  private final double xOfSet = Math.sin(Math.toRadians(30)) * size;
  private final double yOfSet = Math.sin(Math.toRadians(60)) * size;

  public TrigonPolyPane(PolyTrigon poly) {
    this.poly = poly;
    setPoly();
  }

  private void setTriangleRight(int i, int j, Color color) {
    Polygon triangleRight = new Polygon();
    triangleRight.getPoints().addAll(
        xOfSet + size + j * size + i * xOfSet, yOfSet + i * yOfSet, //right vertex
        size + j * size + i * xOfSet, 0.0 + i * yOfSet, // top vertex
        xOfSet + j * size + i * xOfSet, yOfSet + i * yOfSet); // left vertex
    triangleRight.setFill(color);
    triangleRight.setStroke(Color.TRANSPARENT);
    if (!color.equals(Color.TRANSPARENT)) {
      triangleRight.setStroke(Color.BLACK);
    }
    this.getChildren().add(triangleRight);
  }

  private void setTriangleLeft(int i, int j, Color color) {
    Polygon triangleLeft = new Polygon();
    triangleLeft.getPoints().addAll(
        xOfSet + j * size + i * xOfSet, yOfSet + i * yOfSet, // top vertex
        size + j * size + i * xOfSet, 0.0 + i * yOfSet, // right vertex
        0.0 + j * size + i * xOfSet, 0.0 + i * yOfSet);  // left vertex
    triangleLeft.setFill(color);
    triangleLeft.setStroke(Color.TRANSPARENT);
    if (!color.equals(Color.TRANSPARENT)) {
      triangleLeft.setStroke(Color.BLACK);
    }
    this.getChildren().add(triangleLeft);
  }

  private void setTriangle(int i, int j, int isRight, Color color) {
    switch (isRight) {
      case 1:
        setTriangleRight(i, j, color);
        break;
      case 0:
        setTriangleLeft(i, j, color);
        break;
    }
  }

  private void setPoly() {
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        if (poly.containsField(i, j, 0)) {
          setTriangle(i, j, 0, poly.getJavaColor());
        } else {
          setTriangle(i, j, 0, Color.TRANSPARENT);
        }

        if (poly.containsField(i, j, 1)) {
          setTriangle(i, j, 1, poly.getJavaColor());
        } else {
          setTriangle(i, j, 1, Color.TRANSPARENT);
        }
      }
    }
  }
}

