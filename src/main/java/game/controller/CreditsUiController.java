package game.controller;

import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import game.config.Config;
import game.model.Debug;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.layout.AnchorPane;

/**
 * Ui Controlling controlling the CreditsView.
 *
 * @author tgutberl
 */
public class CreditsUiController extends AbstractUiController {

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
   * @param gameController Gamecontroller Object used throughout the Application
   * @author tgutberl
   */
  public CreditsUiController(AbstractGameController gameController) {
    super(gameController);
    this.gameController = gameController;
    init(super.root);

  }

  @Override
  public void onExit() {
    System.exit(0);
    Config.saveProperty();
  }

  /**
   * Method to get back to Menu.
   *
   * @author tgutberl
   */
  @FXML
  public void back() {
    this.gameController.setActiveUiController(new MainMenuUiController(gameController));
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
      loader.setLocation(getClass().getResource("/CreditsView.fxml"));
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
      //e.printStackTrace();
      Debug.printMessage("");
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
