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
 * Class that shows up, when a player is Kicked from a Hostlobby.
 *
 * @author tgutberl
 */

public class KickInfoUiController extends AbstractUiController {

  /**
   * Gamecontroller used in Application.
   */
  private final AbstractGameController gameController;
  /**
   * Main Anchorpane used for resizing.
   */
  @FXML
  AnchorPane mainPane;

  /**
   * Contstructor used for initiating.
   *
   * @param gameController Gamecontroller
   * @author tgutberl
   */
  public KickInfoUiController(AbstractGameController gameController) {
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
      loader.setLocation(getClass().getResource("/KickInfoView.fxml"));
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
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method to Start the PlayView.
   *
   * @author tgutberl
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
   * Override onExit Method
   *
   * @author tgutberl
   */
  @Override
  public void onExit() {
    System.exit(0);
  }

  /**
   * Override Update Method
   *
   * @param gameController GameController of game
   * @author tgutberl
   */
  @Override
  public void update(AbstractGameController gameController) {

  }

  /**
   * Override Initalize
   *
   * @author tgutberl
   */
  @FXML
  public void initialize() {
    updateSize(mainPane, gameController.getStage());
  }
}

