package engine.controller;

import game.config.Config;
import javafx.scene.Group;
import javafx.scene.SubScene;

/**
 * @author lbaudenb
 */
public abstract class AbstractUiController {

  public SubScene subScene;
  protected Group root = new Group();

  public AbstractUiController() {
    subScene = new SubScene(root, Config.getIntValue("SCREEN_WIDTH"), Config.getIntValue("SCREEN_HEIGHT"));
  }

  public void attachToRoot(Group gameRoot) {
    gameRoot.getChildren().addAll(subScene);
  }

  public void update(AbstractGameController gameController, double deltaTime) {
  }
}

