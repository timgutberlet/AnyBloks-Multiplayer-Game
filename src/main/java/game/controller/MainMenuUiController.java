package game.controller;

import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import game.config.Config;
import game.model.Debug;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;

/**
 * Main Menu Ui Controller that is used first when the game is started.
 *
 * @author lbaudenb
 * @author tgutberl
 */

public class MainMenuUiController extends AbstractUiController {

  /**
   * Anbstract Game controller used in Application.
   */
  private final AbstractGameController gameController;
  /**
   * Main Anchorpane used for resizing.
   */
  @FXML
  AnchorPane mainPane;

  /**
   * Construcotr used for setting gamecontroller.
   *
   * @param gameController gameController
   * @author tgutberl
   */
  public MainMenuUiController(AbstractGameController gameController) {
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
      loader.setLocation(getClass().getResource("/MainMenuView.fxml"));
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
   * Method to Start the PlayView.
   *
   * @author tgutberl
   */
  @FXML
  public void play() {
    gameController.setActiveUiController(new PlayUiController(gameController));
  }

  /**
   * Method to Start Tutuorial.
   *
   * @author tgutberl
   */
  @FXML
  public void tutorial() {
    if (Desktop.isDesktopSupported()) {
      try {
        File myFile = new File(getClass().getResource("/documents/UserManual.pdf").getFile());
        //File myFile = new File("/src/main/resources/documents/UserManual.pdf");
        Desktop.getDesktop().open(myFile);
      } catch (IOException ex) {
        // no application registered for PDFs
        Debug.printMessage("");
      }
    }
  }

  /**
   * Method to get into SettingController - to get into SettingUI.
   *
   * @author tgutberl
   */
  @FXML
  public void setting() {
    gameController.setActiveUiController(new SettingUiController(gameController));
  }

  /**
   * Method to get Quit Menu - to End the Program.
   *
   * @author tgutberl
   */
  @FXML
  public void credits() {
    gameController.setActiveUiController(new CreditsUiController(gameController));
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

