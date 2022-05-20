package game.controller;

import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import engine.handler.ThreadHandler;
import game.model.Debug;
import game.model.GameSession;
import game.model.gamemodes.GameMode;
import game.model.player.Player;
import game.model.player.PlayerType;
import java.io.IOException;
import javafx.collections.ObservableList;
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
import net.packet.account.RestfulLoginPacket;
import net.server.ClientHandler;
import net.server.HashingHandler;
import net.transmission.EndpointClient;
import org.glassfish.jersey.jackson.JacksonFeature;

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
  @FXML
  Text ipError, passwordError, usernameError;
  private GameMode gameMode;
  private ObservableList<String> list;
  private EndpointClient client;
  private ClientHandler clientHandler;
  private GameSession gameSession;

  public JoinAuthController(AbstractGameController gameController) {
    super(gameController);
    this.gameController = gameController;
    init(super.root);
  }

  public JoinAuthController(AbstractGameController gameController, String ip, String username) {
    super(gameController);
    this.gameController = gameController;
    init(super.root);
    this.ipField.setText(ip);
    this.usernameField.setText(username);
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
   * Method to Start the the createAccountController
   *
   * @author tgutberl
   */
  @FXML
  public void createAccount() {
    gameController.setActiveUiController(new CreateAccountController(gameController));
  }

  /**
   * Method to Start the the EditAccountController
   *
   * @author tgutberl
   */
  @FXML
  public void editAccount() {
    gameController.setActiveUiController(new EditAccountController(gameController));
  }

  /**
   * Method to Start the the DeleteAccountController
   *
   * @author tgutberl
   */
  @FXML
  public void deleteAccount() {
    gameController.setActiveUiController(new DeleteAccountController(gameController));
  }

  /**
   * Method to Join a Lobby
   *
   * @author tgutberl
   */
  @FXML
  public void joinLobby() {
    if (this.ipField.getText().length() >= 7 && this.usernameField.getText().length() >= 2) {


      String ip = this.ipField.getText();
      String username = this.usernameField.getText();
      String password = this.passwordField.getText();

      Client testClient = ClientBuilder.newBuilder().register(JacksonFeature.class).build();

      String targetAddress = "http://" + ip + ":8082/";

      String passwordHash = HashingHandler.sha256encode(password);

      RestfulLoginPacket restfulLoginPacket = new RestfulLoginPacket(username,
          passwordHash);
      WrappedPacket wrappedPacket = new WrappedPacket(PacketType.RESTFUL_LOGIN_PACKET,
          restfulLoginPacket);

      System.out.println("PasswortHash" + passwordHash);

      WebTarget targetPath = testClient.target(targetAddress).path("/authentication/");
      Response receivedToken = targetPath.request(MediaType.APPLICATION_JSON)
          .put(Entity.entity(wrappedPacket, MediaType.APPLICATION_JSON));

      if (receivedToken.getStatus() != 200) {
        System.out.println("Unexpected answer!");
        System.out.println(receivedToken.getStatus());
        System.out.println(receivedToken.getStatusInfo());


        passwordError.setText(
            "You seem to have entered an invalid username or password. "
                + "Please make sure you enter a valid username & password or create an account!");
      } else {
        String token = receivedToken.readEntity(String.class);

        Player player = new Player(this.usernameField.getText(), PlayerType.REMOTE_PLAYER);
        this.client = new EndpointClient(this, player, ipField.getText(), token);
        this.gameSession = client.getGameSession();
        this.gameSession.setLocalPlayer(player);
        this.clientHandler = client.getClientHandler();

        this.ipError.setText("");
        this.passwordError.setText("");
        this.usernameError.setText("");

      }


    } else {
      if (this.ipField.getText().length() < 7) {
        this.ipField.setText("IP to short, please reEnter!");
      }
      if (this.usernameField.getText().length() < 2) {
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

    if (this.gameSession.getLocalPlayer().isPlayerConnected()) {
      gameController.setActiveUiController(
          new JoinLobbyUiController(gameController, gameSession));
      //this.gameSession.setGameStarted();
    } else {
      Debug.printMessage(this, "GameSession Controller " + this.gameSession);
    }
  }

  @Override
  public void update(AbstractGameController gameController, double deltaTime) {

    if(this.gameSession != null){

    if (!this.gameSession.getLoginStatus().equals("")) {

      if(this.gameSession.getLoginStatus().equals("password")) {
        usernameError.setText(
            "THE USERNAME DOES NOT EXIST ON THE SERVER - PLEASE CREATE A NEW ACCOUNT ");
      }

      if(this.gameSession.getLoginStatus().equals("ipAddress")){
        ipError.setText(
            "THE LOBBY YOU ARE TRYING TO JOIN IS ALREADY FULL");
      }

      if(this.gameSession.getLoginStatus().equals("ipAddress")){
        ipError.setText("THE LOBBY YOU ARE TRYING TO JOIN IS CURRENTLY IN A GAME");
      }

    } else {

      if (this.gameSession.isGameStarted()) {
        ThreadHandler threadHelp = new ThreadHandler(this.gameSession);
        gameController.setActiveUiController(
            new LocalGameUiController(gameController, this.gameSession.getGame(), gameSession,
                threadHelp));
        this.gameSession.setGameStarted(false);
      } else {
        Debug.printMessage(this, "GameSession Controller " + this.gameSession);
      }
    }
    }

  }

  @FXML
  public void initialize() {
    updateSize(mainPane, gameController.getStage());
  }


}
