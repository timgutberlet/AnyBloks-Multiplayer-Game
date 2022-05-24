package game.view.poly;

import engine.component.CheckTrigonField;
import engine.component.TrigonField;
import engine.handler.ColorHandler;
import engine.handler.InputHandler;
import game.model.polygon.Poly;
import javafx.scene.paint.Color;

/**
 * TrigonPolyPane to display TrigonPoly in game.
 *
 * @author lbaudenb
 */

public class TrigonPolyPane extends PolyPane {


  private double ofSetX;

  private double ofSetY;

  /**
   * TrigonPolyPane to display TrigonPoly in Game.
   *
   * @param poly         Poly from Player.
   * @param inputHandler InputHandler from GameState.
   * @param width        Width from Stage.
   */
  public TrigonPolyPane(Poly poly, InputHandler inputHandler, double width) {
    super(poly, inputHandler);
    this.size = width / 132;
    ofSetX = Math.sin(Math.toRadians(30)) * size;
    ofSetY = Math.sin(Math.toRadians(60)) * size;
    setPoly();
  }

  /**
   * Method that draws a right triangle with a specific color at the coordinates i,j. This is done
   * by calculating each point of the triangle.
   *
   * @param i x coordinate of triangle
   * @param j y coordinate of triangle
   * @param color color of triangle
   */
  private void setTriangleRight(int i, int j, Color color) {
    //This sets the checkField used for checkng intersections with the Board

    TrigonField triangleRight = new TrigonField(i, j, 1);
    triangleRight.getPoints().addAll(
        size + j * size + i * ofSetX, 0.0 + i * ofSetY, // top vertex
        ofSetX + size + j * size + i * ofSetX, ofSetY + i * ofSetY, //right vertex
        ofSetX + j * size + i * ofSetX, ofSetY + i * ofSetY); // left vertex
    triangleRight.setFill(color);
    triangleRight.setStroke(Color.TRANSPARENT);
    if (!color.equals(Color.TRANSPARENT)) {
      fields.add(triangleRight);
      triangleRight.setStroke(Color.BLACK);
    }
    this.getChildren().add(triangleRight);
  }

  /**
   * Method that draws a left triangle with a specific color at the coordinates i,j. This is done by
   * calculating each point of the triangle.
   *
   * @param i x coordinate of triangle
   * @param j y coordinate of triangle
   * @param color color of triangle
   */
  private void setTriangleLeft(int i, int j, Color color) {
    if (i == 0 && j == 0) {
      CheckTrigonField checkTrigonField = new CheckTrigonField(i, j, 0);
      double sizeHelp = size * 0.4;
      double move = size / 2 - sizeHelp / 2;
      double ofSetHelpX = ofSetY * 0.4;
      double moveOfSetY = ofSetY / 2 - ofSetHelpX / 2;
      checkTrigonField.getPoints()
          .addAll(ofSetX + j * size + i * ofSetX, ofSetY + i * ofSetY - moveOfSetY, // top vertex
              size + j * size + i * ofSetX - move, 0.0 + i * ofSetY + moveOfSetY, // right vertex
              0.0 + j * size + i * ofSetX + move, 0.0 + i * ofSetY + moveOfSetY);
      checkTrigonField.setFill(color);
      checkPolyField = checkTrigonField;
      this.getChildren().add(checkTrigonField);
    }

    TrigonField triangleLeft = new TrigonField(i, j, 0);
    triangleLeft.getPoints().addAll(
        ofSetX + j * size + i * ofSetX, ofSetY + i * ofSetY, // top vertex
        size + j * size + i * ofSetX, 0.0 + i * ofSetY, // right vertex
        0.0 + j * size + i * ofSetX, 0.0 + i * ofSetY);  // left vertex
    triangleLeft.setFill(color);
    triangleLeft.setStroke(Color.TRANSPARENT);
    if (!color.equals(Color.TRANSPARENT)) {
      fields.add(triangleLeft);
      triangleLeft.setStroke(Color.BLACK);
    }
    this.getChildren().add(triangleLeft);
  }

  /**
   * Method that draws a triangle at the coordinates i,j.
   * Position in Parallelogram depends on isRight.
   * @param i x coordinate of triangle
   * @param j y coordinate of triangle
   * @param isRight position of triangle in parallelogram
   * @param color color of triangle
   */
  private void setTriangle(int i, int j, int isRight, Color color) {
    switch (isRight) {
      case 1:
        setTriangleRight(i, j, color);
        break;
      case 0:
        setTriangleLeft(i, j, color);
        break;
      default:
        break;
    }
  }

  /**
   * Method that draws a Poly.
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


  /**
   * Method to resize TrigonPolyPane.
   *
   * @param size Size from TrigonPolyPane.
   */
  public void setSize(double size) {
    this.size = size;
    ofSetX = Math.sin(Math.toRadians(30)) * size;
    ofSetY = Math.sin(Math.toRadians(60)) * size;
    this.fields.clear();
    this.getChildren().clear();
    setPoly();
  }
}


