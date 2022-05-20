package engine.controller;

import engine.Initializable;
import engine.Updating;
import game.config.Config;
import java.awt.Image;
import java.awt.Taskbar;
import java.awt.Toolkit;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.SubScene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * @author lbaudenb
 * @author tgutberl
 */
public abstract class AbstractUiController implements Initializable, Updating {

  public SubScene subScene;
  protected Group root = new Group();

  /**
   * @param gameController most recent Gamecontroller
   * @author tgutberl
   */
  public AbstractUiController(AbstractGameController gameController) {
    subScene = new SubScene(root, Config.getIntValue("SCREEN_WIDTH"),
        Config.getIntValue("SCREEN_HEIGHT"));
    Stage stage = gameController.getStage();
    subScene.heightProperty().bind(stage.heightProperty());
    subScene.widthProperty().bind(stage.widthProperty());
    stage.setMinWidth(Config.getIntValue("SCREEN_MINIMUM_WIDTH"));
    stage.setMinHeight(Config.getIntValue("SCREEN_MINIMUM_HEIGHT"));
  }

  public void attachToRoot(Group gameRoot) {
    gameRoot.getChildren().addAll(subScene);
  }

  public void update(AbstractGameController gameController, double deltaTime) {

  }

  /**
   * Method getting called, when closing the Game
   *
   * @author tgutberl
   */
  public abstract void onExit();

  /**
   * Method for Auto Updating Width and Heigth of fxml File
   *
   * @param anchorPane Anchorpane Object used as Wrapper in the fxml File for each Menu
   * @param stage      Stage currently used
   * @author tgutberl
   */
  @FXML
  public void updateSize(AnchorPane anchorPane, Stage stage) {
    anchorPane.setPrefHeight(stage.getHeight());
    anchorPane.setPrefWidth(stage.getWidth());
    stage.widthProperty().addListener((obs, oldVal, newVal) -> {
      anchorPane.setPrefWidth(stage.getWidth());
    });
    stage.heightProperty().addListener((obs, oldVal, newVal) -> {
      int heightCorrection = stage.isFullScreen() ? 0 : 28;
      anchorPane.setPrefHeight(stage.getHeight() - heightCorrection);
    });
  }
}

