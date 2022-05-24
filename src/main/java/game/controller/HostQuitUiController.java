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
 * Class to Control Inputs for the HostQuit View.
 *
 * @author tgutberl
 */
public class HostQuitUiController extends AbstractUiController {

  /**
   * Anbstract Game controller used in Application.
   */
  private final AbstractGameController gameController;
  private final GameSession gameSession;
  /**
   * Main Anchorpane used for resizing.
   */
  @FXML
  AnchorPane mainPane;
  @FXML
  private Button backToLobbyButton;
  private boolean buttonActive;
  private int waited;

  /**
   * Constructor used for setting gamecontroller.
   *
   * @param gameController gameController
   * @author tgutberl
   */
  public HostQuitUiController(AbstractGameController gameController, GameSession gameSession) {
    super(gameController);
    this.gameController = gameController;
    this.gameSession = gameSession;
    init(super.root);
    this.waited = 0;
    this.buttonActive = false;
    backToLobbyButton.setDisable(true);
    gameSession.getClientHandler().stopClient();

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
      loader.setLocation(getClass().getResource("/HostQuitView.fxml"));
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
        default:
          break;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Gets User Back to lobby.
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
   * Method for override on Exit.
   *
   * @author tgutberl
   */
  @Override
  public void onExit() {
    System.exit(0);
  }

  /**
   * Method for override on update.
   *
   * @author tgutberl
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
        TimeUnit.MILLISECONDS.sleep(1000);
        waited++;
        TimeUnit.MILLISECONDS.sleep(1000);
        waited++;
        TimeUnit.MILLISECONDS.sleep(1000);
        backToLobbyButton.setDisable(buttonActive);
        buttonActive = true;

      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
    if (waited < 10) {
      waited++;
    }


  }


  /**
   * Method for override on update.
   *
   * @author tgutberl
   */
  @Override
  public void update(AbstractGameController gameController) {

  }

  /**
   * Method for override on initalize.
   *
   * @author tgutberl
   */
  @FXML
  public void initialize() {
    updateSize(mainPane, gameController.getStage());
  }
}
