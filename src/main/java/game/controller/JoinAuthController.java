package game.controller;

import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * @author tgutberl
 */
public class JoinAuthController extends AbstractUiController {

  private final AbstractGameController gameController;
  @FXML
  AnchorPane mainPane;

  @FXML
  TextField usernameField;

  @FXML
  TextField ipField;

  @FXML
  PasswordField passwordField;

  public JoinAuthController(AbstractGameController gameController) {
    super(gameController);
    this.gameController = gameController;
    init(super.root);
  }

  /**
   * Method to initialize the FXML
   *
   * @param root Group Object
   * @author tgutberl
   */
  public void init(Group root) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/JoinAuthView.fxml"));
      loader.setControllerFactory(e -> this);
      root.getChildren().add(loader.load());
      updateSize(mainPane, gameController.getStage());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method to Start the the LocalLobbyView
   *
   * @author tgutberl
   */
  @FXML
  public void createAccount() {
    gameController.setActiveUiController(new CreateAccountController(gameController));
  }

  /**
   * Method to Join a Lobby
   *
   * @author tgutberl
   */
  @FXML
  public void joinLobby() {
    if(this.ipField.getText().length() >= 7 && this.usernameField.getText().length() >= 2){
      gameController.setActiveUiController(new JoinLobbyUiController(gameController, this.ipField.getText(), this.usernameField.getText()));
    }
    else{
      if(this.ipField.getText().length() < 7){
        this.ipField.setText("IP to short, please reEnter!");
      }
      if(this.usernameField.getText().length() < 2){
        this.usernameField.setText("Username must at least be 2 Characters");
      }
    }
  }

  /**
   * Method to Host a Lobby
   *
   * @author tgutberl
   */
  @FXML
  public void hostLobby() {
    gameController.setActiveUiController(new HostLobbyUiController(gameController));
  }

  /**
   * Method to get back to the MainMenu
   *
   * @author tgutberl
   */
  @FXML
  public void back() {
    gameController.setActiveUiController(new PlayUiController(gameController));
  }

  /**
   * Method to get Quit Menu - to End the Program
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
  }

  @Override
  public void onExit() {

  }

  @Override
  public void update(AbstractGameController gameController) {

  }

  @FXML
  public void initialize() {
    updateSize(mainPane, gameController.getStage());
  }
}
