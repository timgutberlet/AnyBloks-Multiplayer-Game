package engine.controller;

import engine.handler.InputHandler;
import game.config.Config;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.ConditionalFeature;
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

  /**
   * Constructor of Abstract Game Controller, used to set stage, application, scene and inputhandler.
   * Also sets MinWidth and Min Height
   * @param stage
   * @param application
   *
   * @author tgutberl
   */
  public AbstractGameController(Stage stage, Application application) {
    gameRoot = new Group();
    Scene scene = new Scene(gameRoot, Config.getIntValue("SCREEN_WIDTH"), Config.getIntValue("SCREEN_HEIGHT"));
    stage.setScene(scene);
    this.stage = stage;
    this.application = application;
    lastNanoTime = System.nanoTime();
    inputHandler = new InputHandler();
    stage.setMinWidth(Config.getIntValue("SCREEN_MINIMUM_WIDTH"));
    stage.setMinHeight(Config.getIntValue("SCREEN_MINIMUM_HEIGHT"));
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


  /**
   * Method that closes the game
   * @author tgutberl
   */
  public void close() {
    super.stop();
    activeUiController.onExit();
  }
}
