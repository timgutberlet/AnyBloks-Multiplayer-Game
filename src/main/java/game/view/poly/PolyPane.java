package game.view.poly;

import engine.component.Field;
import engine.handler.InputHandler;
import game.model.polygon.Poly;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.Pane;

/**
 * @author lbaudenb
 */
public class PolyPane extends Pane {

  protected Poly poly;
  protected List<Field> fields;
  protected InputHandler inputHandler;
  protected final double size = 10;

  public PolyPane(Poly poly, InputHandler inputHandler) {
    this.poly = poly;
    fields = new ArrayList<>();
    this.inputHandler = inputHandler;
  }

  public void setPoly() {
  }
}
