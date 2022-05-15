package game.view.poly;

import engine.component.TrigonField;
import engine.handler.ColorHandler;
import engine.handler.InputHandler;
import game.model.polygon.Poly;
import javafx.scene.paint.Color;

/**
 * @author lbaudenb
 */

public class TrigonPolyPane extends PolyPane {

  /**
   * Each parallelogram consists of two equilateral triangles the attribute size determines the side
   * length of those triangles and hence the side length of the parallelogram
   */

  /**
   * The attribute xOfSet is calculated from the attribute size, so that each angle of the
   * equilateral triangles is exactly 60 degrees
   */
  private final double xOfSet;
  /**
   * the attrubite yOfSet is the shift in y direction
   */
  private final double yOfSet;

  public TrigonPolyPane(Poly poly, InputHandler inputHandler, double width) {
    super(poly, inputHandler, width);
    xOfSet = Math.sin(Math.toRadians(30)) * size;
    yOfSet = Math.sin(Math.toRadians(60)) * size;
    setPoly();
  }

  /**
   * Method that draws a right triangle with a specific color at the coordinates i,j. This is done
   * by calculating each point of the triangle.
   *
   * @param i
   * @param j
   * @param color
   */
  private void setTriangleRight(int i, int j, Color color) {
    TrigonField triangleRight = new TrigonField(i, j, 1);
    triangleRight.getPoints().addAll(
        size + j * size + i * xOfSet, 0.0 + i * yOfSet, // top vertex
        xOfSet + size + j * size + i * xOfSet, yOfSet + i * yOfSet, //right vertex
        xOfSet + j * size + i * xOfSet, yOfSet + i * yOfSet); // left vertex
    triangleRight.setFill(color);
    triangleRight.setStroke(Color.TRANSPARENT);
    if (!color.equals(Color.TRANSPARENT)) {
      triangleRight.setStroke(Color.BLACK);
    }
    fields.add(triangleRight);
    this.getChildren().add(triangleRight);
  }

  /**
   * Method that draws a left triangle with a specific color at the coordinates i,j. This is done by
   * calculating each point of the triangle.
   *
   * @param i
   * @param j
   * @param color
   */
  private void setTriangleLeft(int i, int j, Color color) {
    TrigonField triangleLeft = new TrigonField(i, j, 0);
    triangleLeft.getPoints().addAll(
        size + j * size + i * xOfSet, 0.0 + i * yOfSet, // right vertex
        0.0 + j * size + i * xOfSet, 0.0 + i * yOfSet,  // left vertex
        xOfSet + j * size + i * xOfSet, yOfSet + i * yOfSet); // top vertex
    triangleLeft.setFill(color);
    triangleLeft.setStroke(Color.TRANSPARENT);
    if (!color.equals(Color.TRANSPARENT)) {
      triangleLeft.setStroke(Color.BLACK);
    }
    fields.add(triangleLeft);
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
   * Method that draws a poly. This is done by a double for loop, which covers the maximum height as
   * well as the maximum width of a trigon poly
   */
  public void setPoly() {
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 4; j++) {
        if (poly.containsField(new int[]{i, j, 0})) {
          setTriangle(i, j, 0, ColorHandler.getJavaColor(poly.getColor()));
        } else {
          setTriangle(i, j, 0, Color.TRANSPARENT);
        }

        if (poly.containsField(new int[]{i, j, 1})) {
          setTriangle(i, j, 1, ColorHandler.getJavaColor(poly.getColor()));
        } else {
          setTriangle(i, j, 1, Color.TRANSPARENT);
        }
      }
    }
  }
}

