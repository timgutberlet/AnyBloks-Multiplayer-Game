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
import net.server.ClientHandler;
import net.transmission.EndpointClient;

/**
 * @author tgutberl
 */
public class JoinAuthController extends AbstractUiController {

  private final AbstractGameController gameController;
  @FXML
  AnchorPane mainPane;

  @FXML
  TextField usernameField;

  private GameMode gameMode;
  private ObservableList<String> list;

  private EndpointClient client;
  private ClientHandler clientHandler;

  private GameSession gameSession;

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
      Player player = new Player(this.usernameField.getText() ,PlayerType.REMOTE_PLAYER);
      this.client = new EndpointClient(this, player, ipField.getText());
      this.gameSession = client.getGameSession();
      this.gameSession.setLocalPlayer(player);
      this.clientHandler = client.getClientHandler();
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

    if(this.gameSession.getLocalPlayer().isPlayerConnected()){
      gameController.setActiveUiController(
          new JoinLobbyUiController(gameController, gameSession));
      //this.gameSession.setGameStarted();
    } else {
      Debug.printMessage(this, "GameSession Controller "+ this.gameSession);
    }
  }

  @FXML
  public void initialize() {
    updateSize(mainPane, gameController.getStage());
  }
}
