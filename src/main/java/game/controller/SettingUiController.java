package game.controller;

import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import game.config.Config;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * Ui Controller Class, used for Controlling the Settings View
 * @author tgutberl
 */
public class SettingUiController extends AbstractUiController {

  private final AbstractGameController gameController;

  @FXML
  AnchorPane settingsPane;

  @FXML
  public void back() {
    gameController.setActiveUiController(new MainMenuUiController(gameController));
  }

  @FXML
  private Label windowWidth, windowHeight;

  @FXML
  private void increaseWidth(){
    windowWidth.setText(String.valueOf(Integer.parseInt(windowWidth.getText()) + 10));
    Config.set("SCREEN_WIDTH", windowWidth.getText());
    System.out.println("Screen width increast" + windowWidth.getText());
  }

  @FXML
  private void decreaseWidth(){
    if(!(Integer.parseInt(windowWidth.getText())-10 < 1280)){
      windowWidth.setText(String.valueOf(Integer.parseInt(windowWidth.getText()) - 10));
      Config.set("SCREEN_WIDTH", windowWidth.getText());
    }
  }

  @FXML
  private void increaseHeight(){
    windowHeight.setText(String.valueOf(Integer.parseInt(windowHeight.getText()) + 10));
    Config.set("SCREEN_HEIGHT", windowHeight.getText());
  }

  @FXML
  private void decreaseHeight(){
    if(!(Integer.parseInt(windowHeight.getText())-10 < 720)){
      windowHeight.setText(String.valueOf(Integer.parseInt(windowHeight.getText()) - 10));
      Config.set("SCREEN_HEIGHT", windowHeight.getText());
    }
  }
  // create a alert
  Alert a = new Alert(AlertType.NONE);
  /**
   * Method to reset the the config to the standard Parameters
   */
  @FXML
  private void reset(){
    Config.loadStandardConfig();
    loadSettings();
    save();
  }
  /**
   * Method to save the changes into the config
   */
  @FXML
  public void save(){
    Config.saveProperty();
    updateSize(settingsPane, gameController.getStage());
    // set alert type
    a.setAlertType(AlertType.CONFIRMATION);

    // show the dialog
    a.show();
  }
  /**
   * Init Method declaring the FXML loader and connecting to the Settingsview FXML
   * @param root Group Element from the AbstractUIController
   */
  public void init(Group root) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/SettingsView.fxml"));
      loader.setControllerFactory(e -> this);
      root.getChildren().add(loader.load());
      loadSettings();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Sets Gamecontroller and calls init Method
   * @param gameController AbstractGameController Object
   */
  public SettingUiController(AbstractGameController gameController) {
    super(gameController);
    this.gameController = gameController;
    init(super.root);
  }

  /**
   * load Settings from Config File
   */
  private void loadSettings(){
    Config.loadProperty();
    windowWidth.setText(Config.getStringValue("SCREEN_WIDTH"));
    windowHeight.setText(Config.getStringValue("SCREEN_HEIGHT"));
  }

  @Override
  public void onExit() {

  }

  @Override
  public void update(AbstractGameController gameController) {

  }
  @FXML
  public void initialize(){
    updateSize(settingsPane, gameController.getStage());
  }
}
