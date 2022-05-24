package game.core;

import engine.controller.AbstractGameController;
import engine.handler.ThreadHandlerRestful;
import game.controller.MainMenuUiController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Gamecontroller Class, extending the Abstractgamecontroller, that sets the Scene and Stage of the
 * Ui and controls, what controller is shown in the UI. It also controls relevant Server threads.
 *
 * @author tgutberl
 */
public class GameController extends AbstractGameController {

  /**
   * Gamecontroller Constructor, initializing the stage and application and starting relevant Server
   * threads.
   *
   * @param stage       Stage object from the main Frame
   * @param application Application object controlling the Application
   */
  public GameController(Stage stage, Application application) {
    super(stage, application);
    super.setActiveUiController(new MainMenuUiController(this));
    ThreadHandlerRestful threadHandlerRestful = new ThreadHandlerRestful();
    threadHandlerRestful.start();
  }
}
