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
import javafx.scene.text.Text;

/**
 * @author tgutberl
 */
public class CreateAccountController extends AbstractUiController {

  private final AbstractGameController gameController;
  @FXML
  AnchorPane mainPane;

  @FXML
  TextField usernameField;

  @FXML
  PasswordField passwordField1, passwordField2;

  @FXML
  Text usernameError, passwordError1, passwordError2;

  public CreateAccountController(AbstractGameController gameController) {
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
      loader.setLocation(getClass().getResource("/CreateAccountView.fxml"));
      loader.setControllerFactory(e -> this);
      root.getChildren().add(loader.load());
      updateSize(mainPane, gameController.getStage());
      usernameError.setText("");
      passwordError1.setText("");
      passwordError2.setText("");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  /**
   * Method to give the Server the username and Password of user
   *
   * @author tgutberl
   */



  public void serverCreateAccount(String username, String password){

  }
  /**
   * Method to create an Account, go Back to JoinAuthController and be logged in
   *
   * @author tgutberl
   */
  @FXML
  public void createAccount() {
    usernameError.setText("");
    passwordError1.setText("");
    passwordError2.setText("");
    if(passwordField1.getText().length() >= 6 && !usernameField.getText().equals("") && usernameField.getText().length() > 3 && passwordField1.getText().equals(passwordField2.getText())){
      serverCreateAccount(usernameField.getText(), passwordField1.getText());
      gameController.setActiveUiController(new JoinAuthController(gameController));
    }else{
      if(usernameField.getText().length() < 3) {
        usernameError.setText("Please enter a username with at least three Characters");
      }
      if(!passwordField1.getText().equals(passwordField2.getText())){
        passwordError2.setText("The passwords do not match!");
      }
      if(passwordField1.getText().length() < 6){
        passwordError1.setText("The Password has to be at least 6 Characters!");
      }
    }
  }

  /**
   * Method to get back to the MainMenu
   *
   * @author tgutberl
   */
  @FXML
  public void back() {
    gameController.setActiveUiController(new JoinAuthController(gameController));
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
