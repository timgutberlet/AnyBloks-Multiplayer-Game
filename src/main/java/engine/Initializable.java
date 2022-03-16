package engine;

import engine.controller.AbstractGameController;
import javafx.scene.Group;

/**
 * @author lbaudenb
 */
public interface Initializable {

  void init(AbstractGameController gameController, Group root2D);
}
