package game.controller;

import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.UnknownHostException;
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
 * @author tgutberl
 */
public class CreateAccountController extends AbstractUiController {

  private final AbstractGameController gameController;
  @FXML
  AnchorPane mainPane;

  @FXML
  TextField ipField;

  @FXML
  TextField usernameField;

  @FXML
  PasswordField passwordField1, passwordField2;

  @FXML
  Text usernameError, passwordError1, passwordError2, ipError;

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



  public void serverCreateAccount(String username, String password, String ip){
    //TODO remove
    try {
      ip = Inet4Address.getLocalHost().getHostAddress();
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }


    Client testClient = ClientBuilder.newBuilder().register(JacksonFeature.class).build();

    password = HashingHandler.sha256encode("123456");
    CreateAccountRequestPacket carp = new CreateAccountRequestPacket("testuser", password);
    WrappedPacket wrappedPacket = new WrappedPacket(PacketType.CREATE_ACCOUNT_REQUEST_PACKET,
        carp);

    String targetAddress = "http://" + ip + ":8082/";

    WebTarget targetPath = testClient.target(targetAddress).path("/register/");
    Response receivedAnswer = targetPath.request(MediaType.APPLICATION_JSON)
        .put(Entity.entity(wrappedPacket, MediaType.APPLICATION_JSON));

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
      serverCreateAccount(usernameField.getText(), passwordField1.getText(),this.ipField.getText());
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
