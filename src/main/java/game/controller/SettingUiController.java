package game.controller;

import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import game.config.Config;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * Ui Controller Class, used for Controlling the Settings View
 *
 * @author tgutberl
 */
public class SettingUiController extends AbstractUiController {

  private final AbstractGameController gameController;

  /**
   * Themes that are available
   */
  ObservableList<String> themes;

  private String saveMessage = "";

  @FXML
  TextField hostPlayerField;

  @FXML
  AnchorPane mainPane;

  @FXML
  Label saveConfirm;

  @FXML
  AnchorPane settingsPane;

  @FXML
  ChoiceBox themeBox;

  @FXML
  public void back() {
    gameController.setActiveUiController(new MainMenuUiController(gameController));
  }

  @FXML
  private Label windowWidth, windowHeight;

  @FXML
  private void increaseWidth() {
    windowWidth.setText(String.valueOf(Integer.parseInt(windowWidth.getText()) + 10));
    Config.set("SCREEN_WIDTH", windowWidth.getText());
    System.out.println("Screen width increast" + windowWidth.getText());
    saveConfirm.setText("");
  }

  @FXML
  private void decreaseWidth() {
    if (!(Integer.parseInt(windowWidth.getText()) - 10 < 1280)) {
      windowWidth.setText(String.valueOf(Integer.parseInt(windowWidth.getText()) - 10));
      Config.set("SCREEN_WIDTH", windowWidth.getText());
      saveConfirm.setText("");
    }
    saveConfirm.setText("");
  }

  @FXML
  private void increaseHeight() {
    windowHeight.setText(String.valueOf(Integer.parseInt(windowHeight.getText()) + 10));
    Config.set("SCREEN_HEIGHT", windowHeight.getText());
    saveConfirm.setText("");
  }

  @FXML
  private void decreaseHeight() {
    if (!(Integer.parseInt(windowHeight.getText()) - 10 < 720)) {
      windowHeight.setText(String.valueOf(Integer.parseInt(windowHeight.getText()) - 10));
      Config.set("SCREEN_HEIGHT", windowHeight.getText());
    }
    saveConfirm.setText("");
  }

  /**
   * Method to reset the the config to the standard Parameters
   */
  @FXML
  private void reset() {
    Config.loadStandardConfig();
    loadSettings();
    themeBox.setValue(Config.getStringValue("THEME"));
    save("Successfully Resettet");
  }


  /**
   * Method to save the changes into the config
   */
  @FXML
  public void save() {
    if(!Config.getStringValue("THEME").equals(themeBox.getValue())){
      switch (themeBox.getValue().toString()) {
        case "BRIGHT":
          Config.set("THEME", "BRIGHT");
          break;
        case "DARK":
          Config.set("THEME", "DARK");
          break;
        case "INTEGRA":
          Config.set("THEME", "INTEGRA");
          break;
        case "THINC!":
          Config.set("THEME", "THINC!");
          break;
      }
    }
    Config.saveProperty();
    updateSize(mainPane, gameController.getStage());
    // set alert type
    gameController.setActiveUiController(new MainMenuUiController(gameController));
    gameController.setActiveUiController(new SettingUiController(gameController, "Successfully saved!"));
    saveConfirm.setText("Successfully saved!");
  }
  @FXML
  public void save(String reset) {
    if(!Config.getStringValue("THEME").equals(themeBox.getValue())){
      switch (themeBox.getValue().toString()) {
        case "BRIGHT":
          Config.set("THEME", "BRIGHT");
          break;
        case "DARK":
          Config.set("THEME", "DARK");
          break;
        case "INTEGRA":
          Config.set("THEME", "INTEGRA");
          break;
        case "THINK":
          Config.set("THEME", "THINK");
          break;
      }
    }
    Config.set("HOSTPLAYER", hostPlayerField.getText());
    Config.saveProperty();
    updateSize(mainPane, gameController.getStage());
    // set alert type
    gameController.setActiveUiController(new MainMenuUiController(gameController));
    gameController.setActiveUiController(new SettingUiController(gameController, reset));
  }

  /**
   * Init Method declaring the FXML loader and connecting to the Settingsview FXML
   *
   * @param root Group Element from the AbstractUIController
   */
  public void init(Group root) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/SettingsView.fxml"));
      loader.setControllerFactory(e -> this);
      root.getChildren().add(loader.load());
      loadSettings();
      themes = FXCollections.observableArrayList("BRIGHT", "DARK", "INTEGRA", "THINC!");
      updateSize(mainPane, gameController.getStage());
      themeBox.setItems(themes);
      //Sets the Theme, according to the settings
      switch (Config.getStringValue("THEME")) {
        case "BRIGHT":
          mainPane.setStyle("-fx-background-color:#E7E7E0;");
          mainPane.getStylesheets()
              .add(getClass().getResource("/styles/styleBrightTheme.css").toExternalForm());
          themeBox.setValue("BRIGHT");
          break;
        case "DARK":
          mainPane.setStyle("-fx-background-color: #383837;");
          mainPane.getStylesheets()
              .add(getClass().getResource("/styles/styleDarkTheme.css").toExternalForm());
          themeBox.setValue("DARK");
          break;
        case "INTEGRA":
          mainPane.setStyle("-fx-background-color: #ffffff;");
          mainPane.getStylesheets()
              .add(getClass().getResource("/styles/styleIntegra.css").toExternalForm());
          themeBox.setValue("INTEGRA");
          break;
        case "THINC!":
          mainPane.setStyle("-fx-background-color: #ffffff;");
          mainPane.getStylesheets()
              .add(getClass().getResource("/styles/styleThinc.css").toExternalForm());
          themeBox.setValue("THINC!");
          break;
      }
      saveConfirm.setText(saveMessage);
      hostPlayerField.setText(Config.getStringValue("HOSTNAME"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Sets Gamecontroller and calls init Method
   *
   * @param gameController AbstractGameController Object
   */
  public SettingUiController(AbstractGameController gameController) {
    super(gameController);
    this.gameController = gameController;
    init(super.root);
  }

  /**
   * Sets Gamecontroller and calls init Method
   *
   * @param gameController AbstractGameController Object
   */
  public SettingUiController(AbstractGameController gameController, String saveMessage) {
    super(gameController);
    this.gameController = gameController;
    this.saveMessage = saveMessage;
    init(super.root);
  }

  /**
   * load Settings from Config File
   */
  private void loadSettings() {
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
  public void initialize() {
  }
}
