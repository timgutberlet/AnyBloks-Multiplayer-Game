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
public class EditAccountController extends AbstractUiController {

  private final AbstractGameController gameController;
  @FXML
  AnchorPane mainPane;

  @FXML
  TextField ipField;

  @FXML
  TextField usernameField;

  @FXML
  PasswordField oldPasswordField, newPasswordField;

  @FXML
  Text usernameError, oldPasswordError, newPasswordError, ipError;

  public EditAccountController(AbstractGameController gameController) {
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
      loader.setLocation(getClass().getResource("/EditAccountView.fxml"));
      loader.setControllerFactory(e -> this);
      root.getChildren().add(loader.load());
      updateSize(mainPane, gameController.getStage());
      ipError.setText("");
      usernameError.setText("");
      oldPasswordError.setText("");
      newPasswordError.setText("");
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
      gameController.setActiveUiController(new JoinAuthController(gameController,ip, username));

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
    oldPasswordError.setText("");
    newPasswordError.setText("");
    if(oldPasswordField.getText().length() >= 6 && !usernameField.getText().equals("") && usernameField.getText().length() >= 2 ){
      //TODO
    }else{
      if(usernameField.getText().length() < 2) {
        usernameError.setText("Please enter a username with at least two Characters");
      }
      if(oldPasswordField.getText().length() < 6){
        oldPasswordError.setText("The Password has to be at least 6 Characters!");
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
    if(oldPasswordField.getText().length() < 6 && oldPasswordField.getText().length() > 0){
      oldPasswordError.setText("The Password has to be at least 6 Characters!");
    }
    if(usernameField.getText().length() < 2 && usernameField.getText().length() > 0){
      usernameError.setText("The username has to be at least 2 Characters!");
    }
  }

  @FXML
  public void initialize() {
    updateSize(mainPane, gameController.getStage());
  }
}
