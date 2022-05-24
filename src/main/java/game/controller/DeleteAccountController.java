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
import net.packet.account.DeleteAccountRequestPacket;
import net.server.HashingHandler;
import org.glassfish.jersey.jackson.JacksonFeature;

/**
 * Class that controls the DeleteAccount View and lets the user delete his account.
 *
 * @author tgutberl
 */
public class DeleteAccountController extends AbstractUiController {

  /**
   * Gamecontroller class used throughout the application.
   */
  private final AbstractGameController gameController;
  /**
   * Main Anchorpane used for resizing.
   */
  @FXML
  AnchorPane mainPane;
  /**
   * Textfield used for inputting of IP.
   */
  @FXML
  TextField ipField;
  /**
   * Textfield used for inputting the username.
   */
  @FXML
  TextField usernameField;
  /**
   * Textfield used for inputting the password.
   */
  @FXML
  PasswordField passwordField;
  /**
   * Error Textfield used for informing the user of errors.
   */
  @FXML
  Text usernameError;
  @FXML
  Text passwordError;
  @FXML
  Text ipError;

  /**
   * Constructor used for setting the gamecontroller and initating.
   *
   * @param gameController Gamecontroller class
   */
  public DeleteAccountController(AbstractGameController gameController) {
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
      loader.setLocation(getClass().getResource("/DeleteAccountView.fxml"));
      loader.setControllerFactory(e -> this);
      root.getChildren().add(loader.load());
      updateSize(mainPane, gameController.getStage());
      usernameError.setText("");
      passwordError.setText("");
      ipError.setText("");
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
        default:
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
  public void serverDeleteAccount(String username, String password, String ip) {

    Client testClient = ClientBuilder.newBuilder().register(JacksonFeature.class).build();

    String passwordHash = HashingHandler.sha256encode(password);

    DeleteAccountRequestPacket deleteAccountRequestPacket = new DeleteAccountRequestPacket(username,
        passwordHash);
    WrappedPacket wrappedPacket = new WrappedPacket(PacketType.DELETE_ACCOUNT_REQUEST_PACKET,
        deleteAccountRequestPacket);

    String targetAddress = "http://" + ip + ":8082/";
    WebTarget targetPath = testClient.target(targetAddress).path("/deleteAccount/");
    Response receivedAnswer = targetPath.request(MediaType.APPLICATION_JSON)
        .put(Entity.entity(wrappedPacket, MediaType.APPLICATION_JSON));

    if (receivedAnswer.getStatus() != 200) {
      Debug.printMessage("Something went wrong");
      usernameError.setText(String.valueOf(receivedAnswer.getStatusInfo()));
    } else {
      Debug.printMessage("" + receivedAnswer.getStatus());
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
  public void deleteAccount() {
    usernameError.setText("");
    passwordError.setText("");
    if (passwordField.getText().length() >= 6 && !usernameField.getText().equals("")
        && usernameField.getText().length() >= 2) {
      serverDeleteAccount(usernameField.getText(), passwordField.getText(), ipField.getText());
    } else {
      if (usernameField.getText().length() < 2) {
        usernameError.setText("Please enter a username with at least two Characters");
      }
      if (passwordField.getText().length() < 6) {
        passwordError.setText("The Password has to be at least 6 Characters!");
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
   * Override onExit Method.
   */
  @Override
  public void onExit() {
    System.exit(0);
  }

  /**
   * Override Update Method.
   *
   * @param gameController GameController of game
   */
  @Override
  public void update(AbstractGameController gameController) {

  }

  /**
   * Override Intialize Class.
   */
  @FXML
  public void initialize() {
    updateSize(mainPane, gameController.getStage());
  }
}
