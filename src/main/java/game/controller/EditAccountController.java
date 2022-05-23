package game.controller;

import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import game.config.Config;
import game.model.Debug;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.packet.account.UpdateAccountRequestPacket;
import net.server.HashingHandler;
import org.glassfish.jersey.jackson.JacksonFeature;

/**
 * Class that controls the EditAccount View and lets the user Edut his account.
 *
 * @author tgutberl
 */
public class EditAccountController extends AbstractUiController {

  /**
   * Abstract Gamecontroller Variable used throughout the application.
   */
  private final AbstractGameController gameController;
  /**
   * Main Anchorpane used for resizing.
   */
  @FXML
  AnchorPane mainPane;
  /**
   * Textfield used for ipInput.
   */
  @FXML
  TextField ipField;
  /**
   * Textfield used for username Input.
   */
  @FXML
  TextField usernameField;
  /**
   * Textfield used for password input and comparing.
   */
  @FXML
  PasswordField oldPasswordField, newPasswordField;
  /**
   * Error Labels used for informing user of errors.
   */
  @FXML
  Text usernameError, oldPasswordError, newPasswordError, ipError;

  /**
   * Constructor used for initalizg the controller and UI.
   *
   * @param gameController used in whole application
   */
  public EditAccountController(AbstractGameController gameController) {
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
      loader.setLocation(getClass().getResource("/EditAccountView.fxml"));
      loader.setControllerFactory(e -> this);
      root.getChildren().add(loader.load());
      updateSize(mainPane, gameController.getStage());
      ipError.setText("");
      usernameError.setText("");
      oldPasswordError.setText("");
      newPasswordError.setText("");
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
   * Method to give the Server the username and Password of user.
   *
   * @author tgutberl
   */

  public void serverEditAccount(String username, String oldPassword, String updatedPassword,
      String ip) {

    Client testClient = ClientBuilder.newBuilder().register(JacksonFeature.class).build();

    String passwordHash = HashingHandler.sha256encode(oldPassword);
    String updatedPasswordHash = HashingHandler.sha256encode(updatedPassword);

    String targetAddress = "http://" + ip + ":8082/";

    UpdateAccountRequestPacket updateAccountRequestPacket = new UpdateAccountRequestPacket(
        passwordHash, username, updatedPasswordHash);
    WrappedPacket wrappedUpdateAccountRequestPacket = new WrappedPacket(
        PacketType.UPDATE_ACCOUNT_REQUEST_PACKET, updateAccountRequestPacket, username,
        passwordHash);

    WebTarget targetPathUpdate = testClient.target(targetAddress).path("/updateAccount/");
    Response receivedAnswer = targetPathUpdate.request(MediaType.APPLICATION_JSON)
        .put(Entity.entity(wrappedUpdateAccountRequestPacket, MediaType.APPLICATION_JSON));

    if (receivedAnswer.getStatus() != 200) {
      Debug.printMessage("Something went wrong");
      usernameError.setText(String.valueOf(receivedAnswer.getStatusInfo()));
    } else {
      Debug.printMessage(""+receivedAnswer.getStatus());
      Debug.printMessage("Everything worked");
      gameController.setActiveUiController(new JoinAuthController(gameController, ip, username));

    }
  }

  /**
   * Method to create an Account, go Back to JoinAuthController and be logged in.
   *
   * @author tgutberl
   */
  @FXML
  public void editAccount() {
    usernameError.setText("");
    oldPasswordError.setText("");
    newPasswordError.setText("");
    if (oldPasswordField.getText().length() >= 6 && !usernameField.getText().equals("")
        && usernameField.getText().length() >= 2) {
      serverEditAccount(usernameField.getText(), oldPasswordField.getText(),
          newPasswordField.getText(), ipField.getText());
    } else {
      if (usernameField.getText().length() < 2) {
        usernameError.setText("Please enter a username with at least two Characters");
      }
      if (oldPasswordField.getText().length() < 6) {
        oldPasswordError.setText("The Password has to be at least 6 Characters!");
      }
    }
  }

  /**
   * Method to get back to the MainMenu.
   *
   * @author tgutberl
   */
  @FXML
  public void back() {
    gameController.setActiveUiController(new JoinAuthController(gameController));
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
   * Overried on Exit Method.
   */
  @Override
  public void onExit() {
    System.exit(0);
  }

  /**
   * Update Method used for checking the password input.
   *
   * @param gameController GameController of game
   */
  @Override
  public void update(AbstractGameController gameController) {
    if (oldPasswordField.getText().length() < 6 && oldPasswordField.getText().length() > 0) {
      oldPasswordError.setText("The Password has to be at least 6 Characters!");
    }
    if (usernameField.getText().length() < 2 && usernameField.getText().length() > 0) {
      usernameError.setText("The username has to be at least 2 Characters!");
    }
  }

  /**
   * Initalizing override Method.
   */
  @FXML
  public void initialize() {
    updateSize(mainPane, gameController.getStage());
  }
}
