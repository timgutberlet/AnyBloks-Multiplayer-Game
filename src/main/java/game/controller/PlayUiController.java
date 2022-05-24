package game.controller;

import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import game.config.Config;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;

/**
 * Controller that lets the user choose, what Game he wants to play. He can choose between
 * "PlayVsAi", "Joinlobby"  and "Hostlobby".
 *
 * @author tgutberl
 */
public class PlayUiController extends AbstractUiController {

  /**
   * Gamecontroller used in Application.
   */
  private final AbstractGameController gameController;
  /**
   * Main Anchorpane for setting resize.
   */
  @FXML
  AnchorPane mainPane;

  /**
   * Constructor used for setting the gamecontroller.
   *
   * @param gameController gameController
   * @author tgutberl
   */
  public PlayUiController(AbstractGameController gameController) {
    super(gameController);
    this.gameController = gameController;
    init(super.root);
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
      loader.setLocation(getClass().getResource("/PlayView.fxml"));
      loader.setControllerFactory(e -> this);
      root.getChildren().add(loader.load());
      updateSize(mainPane, gameController.getStage());
      //Sets the Theme, according to the settings
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
   * Method to Start the the LocalLobbyView.
   *
   * @author tgutberl
   */
  @FXML
  public void playVsAi() {
    gameController.setActiveUiController(new LocalLobbyUiController(gameController));
  }

  /**
   * Method to Join a Lobby.
   *
   * @author tgutberl
   */
  @FXML
  public void joinLobby() {
    gameController.setActiveUiController(new JoinAuthController(gameController));
  }

  /**
   * Method to Host a Lobby.
   *
   * @author tgutberl
   */
  @FXML
  public void hostLobby() {
    gameController.setActiveUiController(new HostLobbyUiController(gameController));
  }

  /**
   * Method to get back to the MainMenu.
   *
   * @author tgutberl
   */
  @FXML
  public void back() {
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
   * Method for override onExit.
   *
   * @author tgutberl
   */
  @Override
  public void onExit() {
    System.exit(0);
  }

  /**
   * Method for override onUpdate.
   *
   * @param gameController GameController of game
   * @author tgutberl
   */
  @Override
  public void update(AbstractGameController gameController) {

  }

  /**
   * Method for override on initialize.
   *
   * @author tgutberl
   */
  @FXML
  public void initialize() {
    updateSize(mainPane, gameController.getStage());
  }
}
