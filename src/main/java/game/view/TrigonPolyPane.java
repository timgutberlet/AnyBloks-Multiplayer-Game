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

  /**
   * each parallelogram consists of two equilateral triangles the attribute size determines the side
   * length of those triangles and hence the side length of the parallelogram
   */
  private final double size = 10;
  /**
   * the attribute xOfSet is calculated from the attribute size, so that each angle of the
   * equilateral triangles is exactly 60 degrees
   */
  private final double xOfSet = Math.sin(Math.toRadians(30)) * size;
  /**
   * the attrubite yOfSet is the shift in y direction
   */
  private final double yOfSet = Math.sin(Math.toRadians(60)) * size;

  public TrigonPolyPane(PolyTrigon poly) {
    this.poly = poly;
    setPoly();
  }

  /**
   * Method that draws a right triangle with a specific color at the coordinates i,j This is done by
   * calculating each point of the triangle
   *
   * @param i
   * @param j
   * @param color
   */
  private void setTriangleRight(int i, int j, Color color) {
    Polygon triangleRight = new Polygon();
    triangleRight.getPoints().addAll(
        xOfSet + size + j * size + i * xOfSet, yOfSet + i * yOfSet, //right point
        size + j * size + i * xOfSet, 0.0 + i * yOfSet, // top point
        xOfSet + j * size + i * xOfSet, yOfSet + i * yOfSet); // left point
    triangleRight.setFill(color);
    triangleRight.setStroke(Color.TRANSPARENT);
    if (!color.equals(Color.TRANSPARENT)) {
      triangleRight.setStroke(Color.BLACK);
    }
    this.getChildren().add(triangleRight);
  }

  /**
   * Method that draws a left triangle with a specific color at the coordinates i,j This is done by
   * calculating each point of the triangle
   *
   * @param i
   * @param j
   * @param color
   */
  private void setTriangleLeft(int i, int j, Color color) {
    Polygon triangleLeft = new Polygon();
    triangleLeft.getPoints().addAll(
        xOfSet + j * size + i * xOfSet, yOfSet + i * yOfSet, // top point
        size + j * size + i * xOfSet, 0.0 + i * yOfSet, // right point
        0.0 + j * size + i * xOfSet, 0.0 + i * yOfSet);  // left point
    triangleLeft.setFill(color);
    triangleLeft.setStroke(Color.TRANSPARENT);
    if (!color.equals(Color.TRANSPARENT)) {
      triangleLeft.setStroke(Color.BLACK);
    }
    this.getChildren().add(triangleLeft);
  }

  /**
   * Method that draws a triangle at the coordinates i,j Depending on the parameter isRight, a right
   * or a left triangle is drawn
   *
   * @param i
   * @param j
   * @param isRight
   * @param color
   */
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

  /**
   * Method that draws a poly This is done by a double for loop, which covers the maximum height as
   * well as the maximum width of a trigon poly
   */
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

