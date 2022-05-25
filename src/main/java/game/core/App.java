package game.core;

import game.config.Config;
import game.model.Debug;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Application class, that starts the Frameable application, initiates a gamecontroller instances
 * and loads the Config.
 *
 * @author tgutberl
 */
public class App extends Application {

  private GameController controller;

  public static void main(String[] args) {
    Config.loadProperty();
    Config.saveProperty();
    launch();
  }

  @Override
  public void start(Stage stage) {
    controller = new GameController(stage, this);
    stage.setTitle("Bloks3");
    stage.show();
  }

  /**
   * Method used to stop the Application.
   *
   * @author tgutberl
   */
  @Override
  public void stop() {
    Config.saveProperty();
    controller.close();
    try {
      super.stop();
    } catch (Exception e) {
      //e.printStackTrace();
      Debug.printMessage("");
    }
    System.exit(0);
  }
}