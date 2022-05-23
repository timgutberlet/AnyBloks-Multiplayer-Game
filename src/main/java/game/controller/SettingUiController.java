package game.controller;

import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import game.config.Config;
import game.model.Debug;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * Ui Controller Class, used for Controlling the Settings View.
 *
 * @author tgutberl
 */
public class SettingUiController extends AbstractUiController {
  /**
   * Button to enable/Disable Tooltips in Game
   */
  @FXML
  Button tooltips;
  /**
   * Button to set Music on
   */
  @FXML
  Button music;
  /**
   * Gamecontroller used in Application.
   */
  private final AbstractGameController gameController;

  /**
   * Themes that are available.
   */
  ObservableList<String> themes;
  /**
   * Textfield for hostPlayer name setting.
   */
  @FXML
  TextField hostPlayerField;
  /**
   * Main Anchorpane used for resizing.
   */
  @FXML
  AnchorPane mainPane;
  /**
   * Label for informing user of saveconfirm.
   */
  @FXML
  Label saveConfirm;
  /**
   * Box that has themes in it.
   */
  @FXML
  ChoiceBox themeBox;
  /**
   * Savemasse used for messaging when save was successful.
   */
  private String saveMessage = "";
  /**
   * Label to set Window width and window Height.
   */
  @FXML
  private Label windowWidth, windowHeight;

  /**
   * Sets Gamecontroller and calls init Method.
   *
   * @param gameController AbstractGameController Object
   */
  public SettingUiController(AbstractGameController gameController) {
    super(gameController);
    this.gameController = gameController;
    init(super.root);
  }

  /**
   * Sets Gamecontroller and calls init Method.
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
   * Method to get back to MainMenu by Button.
   */
  @FXML
  public void back() {
    gameController.setActiveUiController(new MainMenuUiController(gameController));
  }

  /**
   * Method to increase the window width.
   */
  @FXML
  private void increaseWidth() {
    windowWidth.setText(String.valueOf(Integer.parseInt(windowWidth.getText()) + 10));
    Config.set("SCREEN_WIDTH", windowWidth.getText());
    Debug.printMessage("Screen width increast" + windowWidth.getText());
    saveConfirm.setText("");
  }

  /**
   * Method to decrease the Window width.
   */
  @FXML
  private void decreaseWidth() {
    if (!(Integer.parseInt(windowWidth.getText()) - 10 < 1280)) {
      windowWidth.setText(String.valueOf(Integer.parseInt(windowWidth.getText()) - 10));
      Config.set("SCREEN_WIDTH", windowWidth.getText());
      saveConfirm.setText("");
    }
    saveConfirm.setText("");
  }

  /**
   * Method to increase Height.
   */
  @FXML
  private void increaseHeight() {
    windowHeight.setText(String.valueOf(Integer.parseInt(windowHeight.getText()) + 10));
    Config.set("SCREEN_HEIGHT", windowHeight.getText());
    saveConfirm.setText("");
  }

  /**
   * Method to decrease Window Height.
   */
  @FXML
  private void decreaseHeight() {
    if (!(Integer.parseInt(windowHeight.getText()) - 10 < 720)) {
      windowHeight.setText(String.valueOf(Integer.parseInt(windowHeight.getText()) - 10));
      Config.set("SCREEN_HEIGHT", windowHeight.getText());
    }
    saveConfirm.setText("");
  }

  /**
   * Method to reset the the config to the standard Parameters.
   */
  @FXML
  private void reset() {
    Config.loadStandardConfig();
    loadSettings();
    themeBox.setValue(Config.getStringValue("THEME"));
    save("Successfully Resettet");
  }

  /**
   * Button to enable/disable the music
   */
  @FXML
  private void musicPush(){
    if(Config.getStringValue("MUSIC").equals("ON")){
      music.setText("START MUSIC");
      Config.set("MUSIC", "OFF");
    }else{
      music.setText("STOP MUSIC");
      Config.set("MUSIC", "ON");
    }
  }

  /**
   * Button to enable/disable tooltips
   */
  @FXML
  private void tooltipPush(){
    if(Config.getStringValue("TOOLTIPS").equals("ON")){
      tooltips.setText("ENABLE TOOLTIPS");
      Config.set("TOOLTIPS", "OFF");
    }else{
      tooltips.setText("DISABLE TOOLTIPS");
      Config.set("TOOLTIPS", "ON");
    }
  }

  /**
   * Method to save the changes into the config.
   */
  @FXML
  public void save() {
    if (!Config.getStringValue("THEME").equals(themeBox.getValue())) {
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
    Config.set("HOSTPLAYER", hostPlayerField.getText());
    Config.saveProperty();
    updateSize(mainPane, gameController.getStage());
    // set alert type
    gameController.setActiveUiController(new MainMenuUiController(gameController));
    gameController.setActiveUiController(
        new SettingUiController(gameController, "Successfully saved!"));
    saveConfirm.setText("Successfully saved!");
  }

  /**
   * Method to save the changes.
   *
   * @param reset check if reset was called before
   */
  @FXML
  public void save(String reset) {
    if (!Config.getStringValue("THEME").equals(themeBox.getValue())) {
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
   * Init Method declaring the FXML loader and connecting to the Settingsview FXML.
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
          mainPane.setStyle("-fx-background-color:#ffffff;");
          mainPane.getStylesheets()
              .add(getClass().getResource("/styles/styleBrightTheme.css").toExternalForm());
          themeBox.setValue("BRIGHT");
          break;
        case "DARK":
          mainPane.setStyle("-fx-background-color: #404040;");
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
          mainPane.setStyle("-fx-background-color: #D8EFFF;");
          mainPane.getStylesheets()
              .add(getClass().getResource("/styles/styleThinc.css").toExternalForm());
          themeBox.setValue("THINC!");
          break;
      }
      saveConfirm.setText(saveMessage);
      hostPlayerField.setText(Config.getStringValue("HOSTPLAYER"));
      hostPlayerField.setAlignment(Pos.CENTER);
      if(Config.getStringValue("MUSIC").equals("ON")){
        music.setText("STOP MUSIC");
      }else{
        music.setText("START MUSIC");
      }
      if(Config.getStringValue("TOOLTIPS").equals("ON")){
        tooltips.setText("DISABLE TOOLTIPS");
      }else{
        tooltips.setText("ENABLE TOOLTIPS");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * load Settings from Config File.
   */
  private void loadSettings() {
    Config.loadProperty();
    windowWidth.setText(Config.getStringValue("SCREEN_WIDTH"));
    windowHeight.setText(Config.getStringValue("SCREEN_HEIGHT"));
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
   * Override for update Method.
   *
   * @param gameController GameController of game
   */
  @Override
  public void update(AbstractGameController gameController) {

  }

  /**
   * Override for init Method.
   */
  @FXML
  public void initialize() {
  }
}
