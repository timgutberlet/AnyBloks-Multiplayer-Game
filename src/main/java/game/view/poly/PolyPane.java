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
 * @author lbaudenb
 */
public class PolyPane extends Pane {

  protected Poly poly;
  protected List<Field> fields;
  protected Field checkPolyField;
  protected InputHandler inputHandler;
  protected double size;

  public PolyPane(Poly poly, InputHandler inputHandler, double width) {
    this.poly = poly;
    fields = new ArrayList<>();
    this.inputHandler = inputHandler;
  }

  public void setPoly() {
  }

  public Poly getPoly() {
    return this.poly;
  }

  public Field getCheckPolyField(){
    return this.checkPolyField;
  }

  public double getSize() {
    return this.size;
  }

  public void setSize(double size) {
  }

  public void resize(double width) {
    this.size = width * 0.00128;
    this.fields.clear();
    this.getChildren().clear();
    setPoly();
  }

  public List<Field> getFields() {
    return fields;
  }

  public void reset() {
    Rectangle r = new Rectangle(0, 0, this.getWidth(), this.getHeight());
    r.setFill(Color.WHITE);
    this.getChildren().add(r);
  }
}
