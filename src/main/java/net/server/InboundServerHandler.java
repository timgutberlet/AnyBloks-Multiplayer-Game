package net.server;

import game.model.Debug;
import game.model.GameSession;
import game.model.Turn;
import game.model.gamemodes.GameMode;
import game.model.player.Player;
import game.model.player.PlayerType;
import game.scores.ScoreProvider;
import java.util.LinkedList;
import javax.websocket.Session;
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.packet.account.CreateAccountRequestPacket;
import net.packet.account.LoginRequestPacket;
import net.packet.account.LoginResponsePacket;
import net.packet.game.IllegalTurnPacket;
import net.packet.game.InitGamePacket;
import net.packet.game.PlayerKickPacket;
import net.packet.game.LobbyScoreBoardPacket;
import net.packet.game.PlayerListPacket;
import net.packet.game.PlayerQuitPacket;
import net.packet.game.TurnPacket;
import net.transmission.EndpointClient;
import net.transmission.EndpointServer;

/**
 * Provides functions to ServerEndpoint.
 *
 * @author tbuscher
 */
public class InboundServerHandler {

  private GameSession gameSession;
  private EndpointServer server;

  /**
   * constructor for jackson
   */
  public InboundServerHandler() {
  }

  /**
   * Constructor
   */
  public InboundServerHandler(EndpointServer server, GameSession gameSession) {

    this.server = server;
    if (this.gameSession == null) {
      this.gameSession = gameSession;
      this.gameSession.setInboundServerHandler(this);
    }

    Debug.printMessage(this, "InboundServerHandler created");


  }


  /**
   * Verifies whether Login is correct according to Database
   *
   * @param wrappedPacket wrapped LoginRequestPacket
   * @return String[] [0] "true"/"false" [1] username
   */
  public void verifyLogin(WrappedPacket wrappedPacket, Session session) {
    Debug.printMessage(this, "LOGIN_REQUEST_PACKET received in Handler");
    Debug.printMessage("LOGIN_REQUEST_PACKET received in Handler");
    LoginRequestPacket loginPacket = (LoginRequestPacket) wrappedPacket.getPacket();
    String username = loginPacket.getUsername();
    String token = loginPacket.getToken();
    Debug.printMessage(username);
    Debug.printMessage(token);
    //Checking the credentials against the database
    boolean loginSuccess = false;
    //only check with db if not a bot
    if (loginPacket.getPlayerType().equals(PlayerType.REMOTE_PLAYER)) {
      Debug.printMessage(username + "Delete me in verify Login");
      try {
        DbServer dbServer = DbServer.getInstance();
        if (!dbServer.doesUserHaveAuthToken(username)) {
          loginSuccess = false;
        } else {
          //In this case there is a user that has a token stored in the database
          loginSuccess = dbServer.testAuthToken(username, token);
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      //AI players do not need auth
      loginSuccess = true;
    }

    if (loginSuccess) {
      //call when user is verified
      this.addVerifiedUser(wrappedPacket, session);

    } else {
      LoginResponsePacket loginResponsePacket = new LoginResponsePacket(
          "Credentials could not be verified", "password");
      wrappedPacket = new WrappedPacket(PacketType.LOGIN_RESPONSE_PACKET, loginResponsePacket);
      this.server.sendMessage(wrappedPacket, session);
    }


  }

  /**
   * converts a login request packet into the send attributes and adds the user to the session.
   *
   * @param wrappedPacket received packet
   * @param session       actual session
   * @return a string array out of the success boolean and the username
   */
  public String[] addVerifiedUser(WrappedPacket wrappedPacket, Session session) {
    LoginRequestPacket loginPacket = (LoginRequestPacket) wrappedPacket.getPacket();
    String username = loginPacket.getUsername();
    Debug.printMessage(username + " addVerfiedUser");
    //check if username has been connected before
    if (this.server.getUsername2Session().containsKey(username)) {
      Debug.printMessage(username + " in keyset");
      //find existing player with the username
      for (Player player : gameSession.getPlayerList()) {
        if (player.getUsername().equals(username)) {
          //only override if player is of type AI (former remote players have been made into an AI
          //by the changePlayer"AI from GameSession)
          if (player.isAI()) {
            Debug.printMessage(this, "THE REMOTE SESSION WILL REPLACE AN AI");
            this.server.getUsername2Session().put(username, session);
            Debug.printMessage(this, "" + loginPacket.getPlayerType());

            Debug.printMessage(this,
                "" + loginPacket.getPlayerType().equals(PlayerType.REMOTE_PLAYER));

            if (loginPacket.getPlayerType().equals(PlayerType.REMOTE_PLAYER)) {

              Debug.printMessage(this, "AN OLD PLAYER JOINED THE SESSION AGAIN!!!!");
              player.setAI(false);
              player.setType(loginPacket.getPlayerType());
              this.server.getOutboundServerHandler()
                  .sendGameStart(player.getUsername(), gameSession.getGame()
                      .getGameState());
            }

          }
        }
      }
    } else {
      // handle a new player by adding to gamesession and in the dictionary
//      if (gameSession.getGame().getGameState().isStateRunning()) {
//        LoginResponsePacket loginResponsePacket = new LoginResponsePacket(
//            "This Lobby is currently playing. Try again at another time.",
//            "ipAddress");
//        WrappedPacket wrappedPacketLoginResponse = new WrappedPacket(
//            PacketType.LOGIN_RESPONSE_PACKET,
//            loginResponsePacket);
//        this.server.sendMessage(wrappedPacketLoginResponse, session);
//      } else

      //Debug.printMessage(gameSession.getPlayerList().size());
      Debug.printMessage("" + gameSession.getPlayerList());
      //Debug.printMessage(gameSession.getPlayerList().get(0));
      if (gameSession.getPlayerList().size() < 4) {

        Debug.printMessage(this, "ADDING A NEW PLAYER TO THE GAMESESSION!");

        gameSession.addPlayer(new Player(username, loginPacket.getPlayerType()));

        this.server.getUsername2Session().put(username, session);
        Debug.printMessage(
            "Username2Session size: " + this.server.getUsername2Session().size());
        Debug.printMessage(
            "Gamesession size: " + EndpointServer.getGameSession().getPlayerList().size());
      } else {
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket(
            "This Lobby is full. Try again at another time.", "ipAddress");
        WrappedPacket wrappedPacketLoginResponse = new WrappedPacket(
            PacketType.LOGIN_RESPONSE_PACKET,
            loginResponsePacket);
        this.server.sendMessage(wrappedPacketLoginResponse, session);

      }
    }
    PlayerListPacket playerListPacket = new PlayerListPacket(gameSession.getPlayerList());
    wrappedPacket = new WrappedPacket(PacketType.PLAYER_LIST_PACKET, playerListPacket);
    this.server.broadcastMessage(wrappedPacket);
    //Fetch the LobbyScoreBoard and send it to all clients
    LobbyScoreBoardPacket lobbyScoreBoardPacket = new LobbyScoreBoardPacket(
        ScoreProvider.getLobbyScoreBoard());
    WrappedPacket wrappedLobbyScoreBoardPacket = new WrappedPacket(
        PacketType.LOBBY_SCORE_BOARD_PACKET, lobbyScoreBoardPacket);
    this.server.broadcastMessage(wrappedLobbyScoreBoardPacket);

    String[] toReturn = {"true", username};

    //this.server.addUsernameSession(username, session);
    Debug.printMessage(this,
        "New Length of KeySet: " + this.server.getUsername2Session().keySet().size());
    return toReturn;

  }

  /**
   * <p>
   * Method called after receiving a CreateAccountRequestPacket. This tries to save the account in
   * the Database, depending on the result of the attempted DB Insertion the response message for
   * the createAccountResponsePacket is set.
   *
   * @param packet that contains a createAccountRequestPacket
   */
  public String createAccount(WrappedPacket packet) {
    CreateAccountRequestPacket carp = (CreateAccountRequestPacket) packet.getPacket();
    String username = carp.getUsername();
    String passwordHash = carp.getPasswordHash();
    String errorMessage = "";
    DbServer dbServer = null;
    boolean success = false;
    try {
      dbServer = DbServer.getInstance();
      //Check if the username is already in DB, return with errorMessage
      if (dbServer.doesUsernameExist(username)) {
        return "The requested username already exists. Please choose another one!";
      }
      //Try to create account if the username is still available
      success = dbServer.newAccount(username, passwordHash);

    } catch (Exception e) {
      e.printStackTrace();
    }

    if (!success) {
      errorMessage = "There was an issue with the database!";
    }
    return errorMessage;
  }

  /**
   * starts the game out of the game init packet.
   *
   * @param wrappedPacket init game packet.
   */
  public void startGame(WrappedPacket wrappedPacket) {

    InitGamePacket initGamePacket = (InitGamePacket) wrappedPacket.getPacket();

    //this.server.getOutboundServerHandler().broadcastGameStart(initGamePacket.getGameMode());

    LinkedList<GameMode> gameModes = initGamePacket.getGameMode();

    if (initGamePacket.getDefaultAi() != null) {
      gameSession.setDefaultAI(initGamePacket.getDefaultAi());
    }
    if (initGamePacket.getPlayerTypes() != null) {
      gameSession.setAiPlayers(initGamePacket.getPlayerTypes());
    }

    gameSession.setGameList(gameModes);

    gameSession.startGameServer();


  }


  /**
   * receive a turn from a remote player and forward it to game logic
   *
   * @param packet
   * @author tgeilen
   */
  public void receiveTurn(Session client, WrappedPacket packet) {
    Debug.printMessage(this, "received turn");
    TurnPacket turnPacket = (TurnPacket) packet.getPacket();
    Turn turn = turnPacket.getTurn();

    CheckConnectionThread.turnReceived = true;

    Debug.printMessage(this, "The received turn is legal and will be played");
    gameSession.getGame().makeMoveServer(turn);
    //this.server.getOutboundServerHandler().broadcastGameUpdate();

  }

  /**
   * remove a player from the server
   * @param client
   * @param packet
   */
  public void disconnectClient(Session client, WrappedPacket packet){
    PlayerQuitPacket playerQuitPacket = (PlayerQuitPacket) packet.getPacket();
    String username = playerQuitPacket.getUsername();

    if (gameSession.getGame() == null) {
      //Game has not started and Lobby view is still active
      this.server.getUsername2Session().remove(username);
      EndpointServer.getSessions().remove(client);
      int indexPlayerToRemove = -1;
      for (int i = 0; i < gameSession.getPlayerList().size(); i++) {
        if (gameSession.getPlayerList().get(i).getUsername().equals(username)) {
          indexPlayerToRemove = i;
        }
      }
      gameSession.getPlayerList().remove(indexPlayerToRemove);

    } else {
      //Game has started and players are in-game
      gameSession.changePlayer2Ai(username);

    }


  }

  public void kickClient(Session client, WrappedPacket packet){
    PlayerKickPacket playerKickPacket = (PlayerKickPacket) packet.getPacket();
    String username = playerKickPacket.getUsername();
    this.server.getOutboundServerHandler().kickPlayer(username);

    this.server.getUsername2Session().remove(username);
    EndpointServer.getSessions().remove(client);
    int indexPlayerToRemove = -1;
    for(int i=0; i<gameSession.getPlayerList().size();i++){
      if (gameSession.getPlayerList().get(i).getUsername().equals(username)){
        indexPlayerToRemove = i;
      }
    }
    gameSession.getPlayerList().remove(indexPlayerToRemove);
  }

  public EndpointServer getServer() {
    return server;
  }
}
