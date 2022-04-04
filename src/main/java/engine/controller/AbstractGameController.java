package engine.controller;

import engine.handler.InputHandler;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * @author lbaudenb
 */
public abstract class AbstractGameController extends AnimationTimer {

  private final Stage stage;

  private final Group gameRoot;
  private final Application application;
  private final InputHandler inputHandler;
  private AbstractUiController activeUiController;

  private long lastNanoTime;

  public AbstractGameController(Stage stage, Application application) {
    gameRoot = new Group();
    Scene scene = new Scene(gameRoot, 1920, 1080);
    stage.setScene(scene);
    this.stage = stage;
    this.application = application;
    lastNanoTime = System.nanoTime();
    inputHandler = new InputHandler();
    this.start();
  }

  @Override
  public void handle(long currentNanoTime) {
    double deltaTime = (currentNanoTime - lastNanoTime) / 1e9f;
    lastNanoTime = currentNanoTime;
    if (activeUiController != null) {
      activeUiController.update(this, deltaTime);
    }
  }

  public void setActiveUiController(AbstractUiController activeUiController) {
    System.out.println("[GameController] Switched UI-Controller!");
    this.activeUiController = activeUiController;
    gameRoot.getChildren().clear();
    activeUiController.attachToRoot(gameRoot);
  }

  public Stage getStage() {
    return this.stage;
  }

  public Group getGroup() {
    return this.gameRoot;
  }

  public InputHandler getInputHandler() {
    return this.inputHandler;
  }
}
