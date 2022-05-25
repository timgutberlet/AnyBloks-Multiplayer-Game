package game.view.poly;

import engine.component.CheckField;
import engine.component.ClassicField;
import engine.handler.ColorHandler;
import engine.handler.InputHandler;
import game.model.polygon.Poly;
import javafx.scene.paint.Color;

/**
 * SquarePolyPane to display SquarePoly in Game.
 *
 * @author lbaudenb
 */

public class SquarePolyPane extends PolyPane {

  /**
   * Object to display SquarePoly in Game.
   *
   * @param poly         Poly from GameState.
   * @param inputHandler InputHandler from GameSate.
   * @param width        Width from Stage.
   */
  public SquarePolyPane(Poly poly, InputHandler inputHandler, double width) {
    super(poly, inputHandler);
    this.size = width / 120;
    this.setPrefWidth(5 * size + 10);
    setPoly();
  }

  /**
   * Method that draws a square with a specific color at the coordinates i,j.
   *
   * @param i     x coordinate  of square
   * @param j     y coordinate of square
   * @param color color of square
   */
  public void setSquare(int i, int j, Color color) {
    //This sets the checkField used for checking intersections with the Board
    if (i == 0 && j == 0) {
      CheckField checkField = new CheckField(i, j);
      double sizeHelp = size * 0.25;
      double move = size / 2 - sizeHelp / 2;
      checkField.getPoints().addAll(0 + j * size + move, 0 + i * size + move,
          sizeHelp + j * size + move, 0 + i * size + move,
          sizeHelp + j * size + move, sizeHelp + i * size + move,
          0 + j * size + move, sizeHelp + i * size + move);
      checkField.setFill(color);
      checkPolyField = checkField;
      this.getChildren().add(checkField);
    }
    //This sets the real fields of the moved Poly
    ClassicField field = new ClassicField(i, j);
    field.getPoints().addAll(
        0 + j * size, 0 + i * size,
        size + j * size, 0 + i * size,
        size + j * size, size + i * size,
        0 + j * size, size + i * size);
    field.setFill(color);
    if (!color.equals(Color.TRANSPARENT)) {
      fields.add(field);
    }
    this.getChildren().add(field);
  }

  /**
   * Method that draws a Poly.
   */
  public void setPoly() {
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        if (poly.containsField(new int[]{i, j})) {
          if(polyGrey){
            setSquare(i, j, Color.GRAY);
          }else{
            setSquare(i, j, ColorHandler.getJavaColor(poly.getColor()));
          }
        } else {
          setSquare(i, j, Color.TRANSPARENT);
        }

      }
    }
  }

  /**
   * Method to resize SquarePolyPane.
   *
   * @param size Size from PolyPane.
   */
  public void setSize(double size) {
    this.size = size;
    this.fields.clear();
    this.getChildren().clear();
    setPoly();
  }
}

