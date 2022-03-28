package game.core;

import engine.controller.AbstractGameController;
import game.controller.MainMenuUiController;
import javafx.application.Application;
import javafx.stage.Stage;

public class GameController extends AbstractGameController {

  public GameController(Stage stage, Application application) {
    super(stage, application);
    super.setActiveUiController(new MainMenuUiController(this));
  }
}