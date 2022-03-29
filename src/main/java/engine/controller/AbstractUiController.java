package engine.controller;

import engine.Initializable;
import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.stage.Stage;

/**
 * @author lbaudenb
 */
public abstract class AbstractUiController implements Initializable {

  public SubScene subScene;
  Group root = new Group();

  public AbstractUiController(AbstractGameController gameController) {
    Stage stage = gameController.getStage();
    subScene = new SubScene(root, 1920, 1080);
    this.init(gameController, root);
  }

  public void attachToRoot(Group gameRoot) {
    gameRoot.getChildren().addAll(subScene);
  }

  public void update(AbstractGameController gameController, double deltaTime) {
  }

  public Group getGroup() {
    return this.root;
  }
}

