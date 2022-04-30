package engine.controller;

import javafx.scene.Group;
import javafx.scene.SubScene;

/**
 * @author lbaudenb
 */
public abstract class AbstractUiController {

  public SubScene subScene;
  protected Group root = new Group();

  public AbstractUiController() {
    subScene = new SubScene(root, 1280, 720);
  }

  public void attachToRoot(Group gameRoot) {
    gameRoot.getChildren().addAll(subScene);
  }

  public void update(AbstractGameController gameController, double deltaTime) {
  }
}

