package game.controller;

import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import game.config.Config;
import game.model.GameSession;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

/**
 * @author tgutberl
 */
public class LocalQuitUiController extends AbstractUiController {

  /**
   * Anbstract Game controller used in Application.
   */
  private final AbstractGameController gameController;
  /**
   * Main Anchorpane used for resizing.
   */
  @FXML
  AnchorPane mainPane;
  @FXML
  private Button backToLobbyButton = new Button();
  private final GameSession gameSession;
  private int waited;
  private boolean buttonActive;

  /**
   * Constructor used for setting gamecontroller.
   *
   * @param gameController AbstractGameController
   * @author tgutberl
   */
  public LocalQuitUiController(AbstractGameController gameController, GameSession gameSession,
      Boolean host) {
    super(gameController);
    this.gameController = gameController;
    this.gameSession = gameSession;
    this.waited = 0;
    this.buttonActive = false;
    backToLobbyButton.setDisable(true);
    try {
      gameSession.getClientHandler().getClient().getSession().close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    init(super.root);
    //Make sure that all clients have left.
    if (host) {
      try {
        TimeUnit.MILLISECONDS.sleep(5000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      //now that all clients have left, reset & stop the server
      gameSession.getHostServer().stopWebsocket();
    }
    try {
      TimeUnit.MILLISECONDS.sleep(3000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

  }

  /**
   * Method to initialize the FXML.
   *
   * @param root Group Object
   * @author tgutberl
   */
  public void init(Group root) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/LocalQuitView.fxml"));
      loader.setControllerFactory(e -> this);
      root.getChildren().add(loader.load());

      updateSize(mainPane, gameController.getStage());
      switch (Config.getStringValue("THEME")) {
        case "BRIGHT":
          mainPane.setStyle("-fx-background-color:#ffffff;");
          mainPane.getStylesheets()
              .add(getClass().getResource("/styles/styleBrightTheme.css").toExternalForm());
          break;
        case "DARK":
          mainPane.setStyle("-fx-background-color: #383837;");
          mainPane.getStylesheets()
              .add(getClass().getResource("/styles/styleDarkTheme.css").toExternalForm());
          break;
        case "INTEGRA":
          mainPane.setStyle("-fx-background-color: #ffffff;");
          mainPane.getStylesheets()
              .add(getClass().getResource("/styles/styleIntegra.css").toExternalForm());
          break;
        case "THINC!":
          mainPane.setStyle("-fx-background-color: #D8EFFF;");
          mainPane.getStylesheets()
              .add(getClass().getResource("/styles/styleThinc.css").toExternalForm());
          break;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Gets User Back to lobby
   */
  @FXML
  public void backToLobby() {
    gameController.setActiveUiController(new MainMenuUiController(gameController));
  }

  /**
   * Method to get Quit Menu - to End the Program.
   *
   * @author tgutberl
   */
  @FXML
  public void close() {
    try {
      gameController.getApplication().stop();
    } catch (Exception e) {
      e.printStackTrace();
    }
    Config.saveProperty();
    System.exit(0);
  }

  /**
   * Method for override on Exit
   *
   * @author tgutberl
   */
  @Override
  public void onExit() {
    System.exit(0);
  }

  /**
   * Method for override on update
   *
   * @author tbuscher
   */
  @Override
  public void update(AbstractGameController gameController, double deltaTime) {
    try {
      TimeUnit.MILLISECONDS.sleep(1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    if (waited == 10) {
      try {
        //Sleep in separate blocks in order to avoid "stalling" the application
        //Make sure that all clients have left.
        TimeUnit.MILLISECONDS.sleep(2000);
        waited++;
        TimeUnit.MILLISECONDS.sleep(2000);

        //now that all clients have left, reset & stop the server
        gameSession.getHostServer().stopWebsocket();

        TimeUnit.MILLISECONDS.sleep(2000);
        buttonActive = true;

        waited++;
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    if (waited < 10) {
      waited++;
    }

  }

  /**
   * Method for override on update
   *
   * @author tgutberl
   */
  @Override
  public void update(AbstractGameController gameController) {

  }

  /**
   * Method for override on initalize
   *
   * @author tgutberl
   */
  @FXML
  public void initialize() {
    updateSize(mainPane, gameController.getStage());
  }
}
