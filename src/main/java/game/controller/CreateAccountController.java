package game.controller;

import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import game.config.Config;
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
import net.packet.account.CreateAccountRequestPacket;
import net.server.HashingHandler;
import org.glassfish.jersey.jackson.JacksonFeature;

/**
 * Class that controls the CreateAccount View and lets the user create and set an account.
 *
 * @author tgutberl
 */
public class CreateAccountController extends AbstractUiController {

  /**
   * Gamecontroller that is used througout the Application.
   */
  private final AbstractGameController gameController;
  /**
   * Main Anchorpane that is used for resizing.
   */
  @FXML
  AnchorPane mainPane;
  /**
   * Field for input of IP.
   */
  @FXML
  TextField ipField;
  /**
   * Field for username input.
   */
  @FXML
  TextField usernameField;
  /**
   * 2 Password Fields for used to setting the password and also for comapring the two.
   */
  @FXML
  PasswordField passwordField1, passwordField2;
  /**
   * Error Variables used for informing the Player of wrong input.
   */
  @FXML
  Text usernameError, passwordError1, passwordError2, ipError;

  /**
   * Used for setting the Gamecontroller and initiating.
   *
   * @param gameController Gamecontrollers used throughout the Application
   */
  public CreateAccountController(AbstractGameController gameController) {
    super(gameController);
    this.gameController = gameController;
    init(super.root);
  }

  /**
   * Method to initialize the FXML.
   *
   * @param root Group Object
   */
  public void init(Group root) {
    try {
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(getClass().getResource("/CreateAccountView.fxml"));
      loader.setControllerFactory(e -> this);
      root.getChildren().add(loader.load());
      updateSize(mainPane, gameController.getStage());
      usernameField.setText(Config.getStringValue("HOSTPLAYER"));
      usernameError.setText("");
      passwordError1.setText("");
      passwordError2.setText("");
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
  public void serverCreateAccount(String username, String password, String ip) {

    Client testClient = ClientBuilder.newBuilder().register(JacksonFeature.class).build();

    password = HashingHandler.sha256encode(password);
    CreateAccountRequestPacket carp = new CreateAccountRequestPacket(usernameField.getText(),
        password);
    WrappedPacket wrappedPacket = new WrappedPacket(PacketType.CREATE_ACCOUNT_REQUEST_PACKET, carp);

    String targetAddress = "http://" + ip + ":8082/";
    System.out.println("Ich sende an den Server");

    WebTarget targetPath = testClient.target(targetAddress).path("/register/");
    Response receivedAnswer = targetPath.request(MediaType.APPLICATION_JSON)
        .put(Entity.entity(wrappedPacket, MediaType.APPLICATION_JSON));

    System.out.println("Ich empfange von dem Server");
    if (receivedAnswer.getStatus() != 200) {
      System.out.println("Something went wrong");
      usernameError.setText(String.valueOf(receivedAnswer.getStatusInfo()));
    } else {
      System.out.println(receivedAnswer.getStatus());
      System.out.println("Everything worked");
      gameController.setActiveUiController(new JoinAuthController(gameController, ip, username));

    }
  }

  /**
   * Method to create an Account, go Back to JoinAuthController and be logged in.
   *
   * @author tgutberl
   */
  @FXML
  public void createAccount() {
    usernameError.setText("");
    passwordError1.setText("");
    passwordError2.setText("");
    ipError.setText("");
    if (passwordField1.getText().length() >= 6 && !usernameField.getText().equals("")
        && usernameField.getText().length() >= 2 && passwordField1.getText()
        .equals(passwordField2.getText())) {
      serverCreateAccount(usernameField.getText(), passwordField1.getText(),
          this.ipField.getText());
      gameController.setActiveUiController(
          new JoinAuthController(gameController, ipField.getText(), usernameField.getText()));
    } else {
      if (usernameField.getText().length() < 2) {
        usernameError.setText("Please enter a username with at least two Characters");
      }
      if (!passwordField1.getText().equals(passwordField2.getText())) {
        passwordError2.setText("The passwords do not match!");
      }
      if (passwordField1.getText().length() < 6) {
        passwordError1.setText("The Password has to be at least 6 Characters!");
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
  }

  /**
   * Overried Exit Class.
   */
  @Override
  public void onExit() {

  }

  /**
   * Override Exit Class.
   *
   * @param gameController GameController of game
   */
  @Override
  public void update(AbstractGameController gameController) {

  }

  /**
   * Initalize Override Class.
   */
  @FXML
  public void initialize() {
    updateSize(mainPane, gameController.getStage());
  }
}
