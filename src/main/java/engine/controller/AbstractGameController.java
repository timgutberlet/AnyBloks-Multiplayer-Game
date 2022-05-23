package engine.controller;

import engine.handler.InputHandler;
import engine.handler.MusicThread;
import game.config.Config;
import game.model.Debug;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Abstractgamecontroller class, representing the gamecontroller that is used throughout the application.
 * It is extending the Animationtimer Class, controlling the frame logic.
 *
 * @author lbaudenb
 * @author tgutberl
 */
public abstract class AbstractGameController extends AnimationTimer {

  /**
   * Stage Object representing the current stage with window width and height.
   * The stage object also holds the scene.
   *
   */
  private final Stage stage;
  /**
   * Root Object representing the most deep Ui element, all other elements are added too.
   */
  private final Group gameRoot;
  /**
   * Application Object of the Program.
   */
  private final Application application;
  /**
   * Inputhandler Object handling inputs by mouse, key usw.
   */
  private final InputHandler inputHandler;
  /**
   * printFPS boolean object, selecting if the FPS should be printed or not.
   */
  private final boolean printFps = Config.getBooleanValue("SHOW_FPS");
  /**
   * AbstractUiController Element that is representing and controlling the current
   * active UiController and therefore the current scene we are in.
   */
  private AbstractUiController activeUiController;
  /**
   * framecount representing how many frames per second are being shown.
   */
  private int frameCount;
  /**
   * Time element used for checking framecount.
   */
  private long newTime;
  /**
   * Time element used for the little pause between frames.
   */
  private long waitTime;
  /**
   * Time object representing a timer used for framecounting.
   */
  private long fpsTimer;
  /**
   * long object represnting the lastNanosecond time that was selected.
   */
  private long lastNanoTime;

  /**
   * Constructor of Abstract Game Controller, used to set stage, application, scene and
   * inputhandler. Also sets MinWidth and Min Height.
   *
   * @param stage       Current Stage opened
   * @param application Current Application of the App class used
   * @author tgutberl
   */
  public AbstractGameController(Stage stage, Application application) {
    gameRoot = new Group();
    //Gets screen minimum width and height from the Config file
    Scene scene = new Scene(gameRoot, Config.getIntValue("SCREEN_WIDTH"),
        Config.getIntValue("SCREEN_HEIGHT"));
    stage.setScene(scene);
    stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));
    fpsTimer = System.currentTimeMillis();
    this.stage = stage;
    this.application = application;
    lastNanoTime = System.nanoTime();
    inputHandler = new InputHandler(this);
    //Set screen minimum widht and height
    stage.setMinWidth(Config.getIntValue("SCREEN_MINIMUM_WIDTH"));
    stage.setMinHeight(Config.getIntValue("SCREEN_MINIMUM_HEIGHT"));
    //Starts the music
    //MusicThread musicThread = new MusicThread();
    //musicThread.start();
    this.start();
  }

  /**
   * handle method handling the framecount.
   *
   * @author tgutberl
   * @param currentNanoTime
   */
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
        Debug.printMessage("FPS: " + frameCount);
      }
      frameCount = 0;
    }

  }

  /**
   * Method to set the activeUiController for the root and to therefore show the right window.
   *
   * @param activeUiController AbstractUiController element.
   * @author tgutberl
   */
  public void setActiveUiController(AbstractUiController activeUiController) {
    Debug.printMessage("[GameController] Switched UI-Controller!");
    this.activeUiController = activeUiController;
    gameRoot.getChildren().clear();
    activeUiController.attachToRoot(gameRoot);
  }

  /**
   *
   *
   * @return
   */
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
