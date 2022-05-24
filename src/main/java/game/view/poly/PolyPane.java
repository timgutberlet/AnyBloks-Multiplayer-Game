package game.view.poly;

import engine.component.Field;
import engine.handler.InputHandler;
import game.model.polygon.Poly;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * PolyPane to display a Poly in Game.
 *
 * @author lbaudenb
 */

public class PolyPane extends Pane {

  protected Poly poly;
  protected List<Field> fields;
  protected Field checkPolyField;
  protected InputHandler inputHandler;
  protected double size;

  /**
   * PolyPane to display Poly.
   *
   * @param poly         Poly from GameState.
   * @param inputHandler InputHandler from GameState.
   */
  public PolyPane(Poly poly, InputHandler inputHandler) {
    this.poly = poly;
    fields = new ArrayList<>();
    this.inputHandler = inputHandler;
  }

  public void setPoly() {
  }

  public Poly getPoly() {
    return this.poly;
  }

  public Field getCheckPolyField() {
    return this.checkPolyField;
  }

  /**
   * Method that returns the size of the PolyPane.
   *
   * @return returns size of the PolyPane.
   */
  public double getSize() {
    return this.size;
  }

  public void setSize(double size) {
  }

  /**
   * Returns the Fields of the PolyPane.
   *
   * @return List of fields contained in PolyPane.
   */
  public List<Field> getFields() {
    return fields;
  }
}
