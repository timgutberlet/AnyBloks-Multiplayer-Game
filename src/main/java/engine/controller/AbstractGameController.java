package engine.controller;

import engine.handler.InputHandler;
import game.config.Config;
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
  private int frameCount;
  private long newTime;
  private long waitTime;
  private long fpsTimer;
  private final boolean printFps = Config.getBooleanValue("SHOW_FPS");

  private long lastNanoTime;

  /**
   * Constructor of Abstract Game Controller, used to set stage, application, scene and
   * inputhandler. Also sets MinWidth and Min Height
   *
   * @param stage       Current Stage opened
   * @param application Current Application of the App class used
   * @author tgutberl
   */
  public AbstractGameController(Stage stage, Application application) {
    gameRoot = new Group();
    Scene scene = new Scene(gameRoot, Config.getIntValue("SCREEN_WIDTH"),
        Config.getIntValue("SCREEN_HEIGHT"));
    stage.setScene(scene);
    fpsTimer = System.currentTimeMillis();
    this.stage = stage;
    this.application = application;
    lastNanoTime = System.nanoTime();
    inputHandler = new InputHandler(this);
    stage.setMinWidth(Config.getIntValue("SCREEN_MINIMUM_WIDTH"));
    stage.setMinHeight(Config.getIntValue("SCREEN_MINIMUM_HEIGHT"));
    this.start();
  }

  @Override
  public void handle(long currentNanoTime) {
    double deltaTime = (currentNanoTime - lastNanoTime) / 1e9f;
    lastNanoTime = currentNanoTime;
    frameCount++;
    inputHandler.start();
    if (activeUiController != null) {
      activeUiController.update(this, deltaTime);
    }
    inputHandler.end();

    newTime = System.nanoTime() - currentNanoTime;
    waitTime = ((1000000000 / Config.getIntValue("MAXIMUM_FPS")) - newTime) / 1000000;

    if (waitTime > 0) {
      try {
        Thread.sleep(waitTime);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }

    if (System.currentTimeMillis() - fpsTimer > 1000) {
      fpsTimer += 1000;
      if (printFps) {
        System.out.println("FPS: " + frameCount);
      }
      frameCount = 0;
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
   * @return returns current application
   * @author tgutberl
   */
  public Application getApplication() {
    return this.application;
  }


  /**
   * Method that closes the game
   *
   * @author tgutberl
   */
  public void close() {
    super.stop();
    activeUiController.onExit();
  }
}
