package game.controller;

import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import engine.handler.ThreadHandler;
import game.config.Config;
import game.model.Debug;
import game.model.GameSession;
import game.model.player.Player;
import game.model.player.PlayerType;
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
import net.packet.account.RestfulLoginPacket;
import net.server.ClientHandler;
import net.server.HashingHandler;
import net.transmission.EndpointClient;
import org.glassfish.jersey.jackson.JacksonFeature;

/**
 * Method used for controlling the joining of remote Games.
 *
 * @author tgutberl
 */
public class JoinAuthController extends AbstractUiController {

  /**
   * Gamecontroller used throughout the game.
   */
  private final AbstractGameController gameController;
  /**
   * Main anchorpane used for resizing.
   */
  @FXML
  AnchorPane mainPane;
  /**
   * Textfield for username Input.
   */
  @FXML
  TextField usernameField;
  /**
   * Textfield for ipField input.
   */
  @FXML
  TextField ipField;
  /**
   * Passwordfield with password protection used for password input.
   */
  @FXML
  PasswordField passwordField;
  /**
   * Textmessages to inform user of errors.
   */
  @FXML
  Text ipError, passwordError, usernameError;
  /**
   * Endpoint client used for server connection.
   */
  private EndpointClient client;
  /**
   * Clienthandler used for server handling.
   */
  private ClientHandler clientHandler;
  /**
   * Gessesion Method used for controllin playing.
   */
  private GameSession gameSession;
  /**
   * Used for checking if join was successfull.
   */
  private Boolean joinSuccesful;

  /**
   * Contstructor initiating class.
   *
   * @param gameController Gamecontroller Method
   */
  public JoinAuthController(AbstractGameController gameController) {
    super(gameController);
    this.gameController = gameController;
    init(super.root);
  }

  /**
   * Second constrctor with ip and username, when user comes from CreateAcccount Window.
   *
   * @param gameController Gamecontroller Method
   * @param ip             ip String
   * @param username       username String
   */
  public JoinAuthController(AbstractGameController gameController, String ip, String username) {
    super(gameController);
    this.gameController = gameController;
    init(super.root);
    this.ipField.setText(ip);
    this.usernameField.setText(username);
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
      loader.setLocation(getClass().getResource("/JoinAuthView.fxml"));
      loader.setControllerFactory(e -> this);
      root.getChildren().add(loader.load());
      updateSize(mainPane, gameController.getStage());
      this.ipError.setText("");
      this.passwordError.setText("");
      this.usernameError.setText("");
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
   * Method to Start the the createAccountController.
   *
   * @author tgutberl
   */
  @FXML
  public void createAccount() {
    gameController.setActiveUiController(new CreateAccountController(gameController));
  }

  /**
   * Method to Start the the EditAccountController.
   *
   * @author tgutberl
   */
  @FXML
  public void editAccount() {
    gameController.setActiveUiController(new EditAccountController(gameController));
  }

  /**
   * Method to Start the the DeleteAccountController.
   *
   * @author tgutberl
   */
  @FXML
  public void deleteAccount() {
    gameController.setActiveUiController(new DeleteAccountController(gameController));
  }

  /**
   * Method to Join a Lobby.
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
        System.out.println(token);
        this.gameSession.setAuthToken(token);
        this.clientHandler = client.getClientHandler();
        this.gameSession.setClientHandler(this.clientHandler);

        this.ipError.setText("");
        this.passwordError.setText("");
        this.usernameError.setText("");

        this.joinSuccesful = true;
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
   * Method to Host a Lobby.
   *
   * @author tgutberl
   */
  @FXML
  public void hostLobby() {
    gameController.setActiveUiController(new HostLobbyUiController(gameController));
  }

  /**
   * Method to get back to the MainMenu.
   *
   * @author tgutberl
   */
  @FXML
  public void back() {
    gameController.setActiveUiController(new PlayUiController(gameController));
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
   * Override on Exit.
   */
  @Override
  public void onExit() {

  }

  /**
   * Update Method used for starting Gamessesion, when palyer is connected
   *
   * @param gameController GameController of game
   */
  @Override
  public void update(AbstractGameController gameController) {

    if (this.gameSession.getLocalPlayer().isPlayerConnected()) {
      gameController.setActiveUiController(
          new JoinLobbyUiController(gameController, gameSession));
    } else {
      Debug.printMessage(this, "GameSession Controller " + this.gameSession);
    }
  }

  /**
   * Update Method used for error messages and joining
   *
   * @param gameController Gamecontroller class
   * @param deltaTime      used for Frames
   */
  @Override
  public void update(AbstractGameController gameController, double deltaTime) {

    if (this.gameSession != null) {

      if (!this.gameSession.getLoginStatus().equals("")) {

        if (this.gameSession.getLoginStatus().equals("password")) {
          usernameError.setText(
              "THE USERNAME DOES NOT EXIST ON THE SERVER - PLEASE CREATE A NEW ACCOUNT ");
        }

        if (this.gameSession.getLoginStatus().equals("ipAddress")) {
          ipError.setText(
              "THE LOBBY YOU ARE TRYING TO JOIN IS ALREADY FULL");
        }

        if (this.gameSession.getLoginStatus().equals("ipAddress")) {
          ipError.setText("THE LOBBY YOU ARE TRYING TO JOIN IS CURRENTLY IN A GAME");
        }

      } else {

        if (this.joinSuccesful) {
          ThreadHandler threadHelp = new ThreadHandler(this.gameSession);
          gameController.setActiveUiController(
              new JoinLobbyUiController(gameController, gameSession));
          this.gameSession.setGameStarted(false);
          this.joinSuccesful = false;
        } else {
          Debug.printMessage(this, "GameSession Controller " + this.gameSession);
        }
      }
    }

  }

  /**
   * Override Initialize
   */
  @FXML
  public void initialize() {
    updateSize(mainPane, gameController.getStage());
  }


}
