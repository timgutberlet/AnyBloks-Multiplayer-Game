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
  protected InputHandler inputHandler;
  protected double size = 10;

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

  public void setSize(double Size) {
    this.size = size;
  }

  public List<Field> getFields() {
    return fields;
  }

  public void resetPolyPane() {
    Rectangle r = new Rectangle(0, 0, this.getWidth(), this.getHeight());
    r.setFill(Color.WHITE);
    this.getChildren().add(r);
  }
}
