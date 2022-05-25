package net.server;

import static game.model.player.AiMessages.getAfterMatchAiComment;
import static game.model.player.AiMessages.getAfterTurnAiComment;

import game.model.Debug;
import game.model.GameSession;
import game.model.GameState;
import game.model.Turn;
import game.model.chat.Chat;
import game.model.chat.ChatMessage;
import game.model.gamemodes.GameMode;
import game.model.player.Player;
import game.model.player.PlayerType;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.EncodeException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import net.auth.TokenGenerationRessource;
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.packet.account.LoginRequestPacket;
import net.packet.account.LoginResponsePacket;
import net.packet.chat.ChatMessagePacket;
import net.packet.game.GameStartPacket;
import net.packet.game.GameUpdatePacket;
import net.packet.game.GameWinPacket;
import net.packet.game.InitGamePacket;
import net.packet.game.LobbyScoreBoardPacket;
import net.packet.game.PlayerKickPacket;
import net.packet.game.PlayerListPacket;
import net.packet.game.PlayerQuitPacket;
import net.packet.game.RequestTurnPacket;
import net.packet.game.TurnPacket;
import net.transmission.EndpointClient;

/**
 * provides functions to EndpointClient.
 *
 * @author tgeilen
 * @Date 10.05.22
 */
public class ClientHandler {

  private EndpointClient client;
  private Player player;
  private GameSession gameSession;
  private Session session;

  /**
   * initializes a new client handler out of the endpoint client.
   *
   * @param client Client
   */
  public ClientHandler(EndpointClient client) {
    this.client = client;
    this.player = this.client.getPlayer();
    this.gameSession = client.getGameSession();

  }

  /**
   * this method initializes the local game out of the local player.
   *
   * @param localPlayer considered local player.
   */
  public void initLocalGame(Player localPlayer) {

    DbServer db = null;
    try {
      db = DbServer.getInstance();
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (!db.doesUsernameExist(localPlayer.getUsername())) {
      db.newAccount(localPlayer.getUsername(), "");
    }

    if (db.doesUserHaveAuthToken(localPlayer.getUsername())) {
      db.deleteAuthToken(localPlayer.getUsername());
    }

    TokenGenerationRessource tokenGenerationRessource = new TokenGenerationRessource();
    String token = tokenGenerationRessource.issueToken(localPlayer.getUsername());

    db.insertAuthToken(localPlayer.getUsername(), token);

    try {
      String ip = Inet4Address.getLocalHost().getHostAddress();
      this.initLocalGame(localPlayer, ip, token);
    } catch (UnknownHostException e) {
      e.printStackTrace();
    }

  }

  /**
   * initializes a local game out of the local player and its ip.
   *
   * @param localPlayer LocalPlayer
   * @param ip          Ip
   */
  public void initLocalGame(Player localPlayer, String ip) {
    String token = "";
    this.initLocalGame(player, ip, token);
  }

  /**
   * initializes a local game out of a local player and its ip as well as its token.
   *
   * @param localPlayer LocalPlayer
   * @param ip          Ip
   * @param token       token
   */
  public void initLocalGame(Player localPlayer, String ip, String token) {

    final WebSocketContainer container = ContainerProvider.getWebSocketContainer();

    try {
      TimeUnit.MILLISECONDS.sleep(200);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    Session ses;

    try {

      String ipAdress = ip;

      ses = container.connectToServer(this.client, URI.create("ws://" + ipAdress + ":8081/packet"));
      this.session = ses;
      TimeUnit.MILLISECONDS.sleep(150);

      //Sleep so updates can be made in DB
      try {
        TimeUnit.MILLISECONDS.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      //Login
      LoginRequestPacket loginRequestPacket = new LoginRequestPacket(localPlayer.getUsername(),
          token, localPlayer.getType());
      Debug.printMessage("LoginRequestPacket has been sent to the server");
      WrappedPacket wrappedPacket = new WrappedPacket(PacketType.LOGIN_REQUEST_PACKET,
          loginRequestPacket, localPlayer.getUsername(), token);
      wrappedPacket.setUsername(this.client.getGameSession().getLocalPlayer().getUsername());
      wrappedPacket.setToken(this.client.getGameSession().getAuthToken());
      ses.getBasicRemote().sendObject(wrappedPacket);

    } catch (UnknownHostException e) {
      e.printStackTrace();
    } catch (DeploymentException e) {
      e.printStackTrace();
    } catch (EncodeException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }


  }

  /**
   * function called on client side to send INIT GAME PACKET TO SERVER.
   *
   * @param gameModes gameModes
   */
  public void startLocalGame(LinkedList<GameMode> gameModes, LinkedList<PlayerType> playerTypes) {
    InitGamePacket initGamePacket = new InitGamePacket(gameModes, playerTypes);
    WrappedPacket wrappedPacket = new WrappedPacket(PacketType.INIT_GAME_PACKET, initGamePacket);

    this.client.sendToServer(wrappedPacket);


  }

  /**
   * function called on client side to send INIT GAME PACKET TO SERVER.
   *
   * @param gameModes gameMOdes
   */
  public void startLocalGame(LinkedList<GameMode> gameModes, PlayerType defaultAi) {
    InitGamePacket initGamePacket = new InitGamePacket(gameModes, defaultAi);
    WrappedPacket wrappedPacket = new WrappedPacket(PacketType.INIT_GAME_PACKET, initGamePacket);

    this.client.sendToServer(wrappedPacket);


  }

  /**
   * function called on client side to send INIT GAME PACKET TO SERVER.
   *
   * @param gameModes gameModes
   */
  public void startLocalGame(LinkedList<GameMode> gameModes) {
    InitGamePacket initGamePacket = new InitGamePacket(gameModes);
    WrappedPacket wrappedPacket = new WrappedPacket(PacketType.INIT_GAME_PACKET, initGamePacket);

    this.client.sendToServer(wrappedPacket);


  }


  /**
   * function that starts a game on the client side.
   *
   * @param wrappedPacket wrappedPacket
   * @author tgeilen
   */
  public void startGame(WrappedPacket wrappedPacket) {
    Debug.printMessage(this, "StartGamePacket received by a client");
    GameStartPacket gameStartPacket = (GameStartPacket) wrappedPacket.getPacket();
    GameMode gamemode = gameStartPacket.getGameMode();
    GameState gameState = gameStartPacket.getGameState();
    this.client.getGameSession().startGame(gamemode);
    this.client.getGameSession().updateGame(gameState);
    this.client.getGameSession().setGameOver(false);
    this.client.getGameSession().setGameStarted(true);

  }

  /**
   * function that calls the client to make a turn and sends it to serve.
   *
   * @param wrappedPacket wrappedPacket
   * @author tgeilen
   */
  public void makeTurn(WrappedPacket wrappedPacket) {
    RequestTurnPacket requestTurnPacket = (RequestTurnPacket) wrappedPacket.getPacket();
    GameState gameState = requestTurnPacket.getGameState();
    String username = requestTurnPacket.getUsername();

    if (gameState == null) {
      Debug.printMessage(this, "The received gamestate is NULL");
    }

    if (username == null) {
      Debug.printMessage(this, "The received username is NULL");
    }

    this.client.getGameSession().updateGame(gameState);
    Debug.printMessage(this,
        player.getUsername() + ": It is " + requestTurnPacket.getUsername() + " turn");

    if (this.player.getUsername().equals(requestTurnPacket.getUsername())) {
      Debug.printMessage(this, player.getUsername() + ": It is my turn...");
      Debug.printMessage(this, "Runde: " + gameState.getRound());
      Debug.printMessage(this,
          gameState.isFirstRound() ? "FIRST ROUND!!!" : "___NOT___ FIRST ROUND");

      // flag for UI to enable input
      this.gameSession.setLocalPlayerTurn(true);

      Turn turn = this.gameSession.getLocalPlayer().makeTurn(gameState);
      if (turn == null) {
        Debug.printMessage(this, "I don't know what to do!!!");
      } else {
        Debug.printMessage(this, player.getUsername() + ": I know what to do...");
      }

      //this.gameSession.getGame().getGameState().playTurn(turn);
      this.sendTurn(turn);

      this.gameSession.setLocalPlayerTurn(false);

    }
  }

  /**
   * sends the turn to the server.
   *
   * @param turn turn
   */
  public void sendTurn(Turn turn) {
    TurnPacket turnPacket = new TurnPacket(player.getUsername(), turn);
    WrappedPacket wrPacket = new WrappedPacket(PacketType.TURN_PACKET, turnPacket);
    this.client.sendToServer(wrPacket);
  }


  /**
   * updates the gameState for the local client.
   *
   * @param wrappedPacket wrappedPacket
   */
  public void updateGame(WrappedPacket wrappedPacket) {
    GameUpdatePacket gameUpdatePacket = (GameUpdatePacket) wrappedPacket.getPacket();
    GameState gameState = gameUpdatePacket.getGameState();

    this.client.getGameSession().updateGame(gameState);
    String aiMessage = getAfterTurnAiComment(this.gameSession);
    if (!aiMessage.equals("")) {
      Chat chat = this.gameSession.getChat();
      chat.addMessage(new ChatMessage(this.player.getUsername(), aiMessage));
      this.broadcastChatMessage(chat);
    }
  }

  /**
   * end a local game.
   *
   * @param wrappedPacket wrappedPacket
   * @author tgeilen
   */
  public void endGame(WrappedPacket wrappedPacket) {
    GameWinPacket gameWinPacket = (GameWinPacket) wrappedPacket.getPacket();

    GameSession gameSession = this.client.getGameSession();
    gameSession.setGameList(gameWinPacket.getGameList());
    gameSession.setGameScoreBoard(gameWinPacket.getGameScoreBoard());
    gameSession.setGameSessionScoreBoard(gameWinPacket.getGameSessionScoreBoard());
    gameSession.setGameOver(true);
    getAfterMatchAiComment(this.gameSession);
  }

  /**
   * Called if a HostQuitPacket is received. Sends the player back to the lobby.
   */
  public void handleHostQuit() {
    if (!player.getType().equals(PlayerType.HOST_PLAYER)) {
      Debug.printMessage("The client was informed about the host quitting");
      this.gameSession.setHostQuit(true);
    }
  }

  /**
   * Receives a wrapped LobbyScoreBoard and sets the received it in the gameSession.
   *
   * @param wrappedPacket wrappedPacket
   */
  public void handleLobbyScoreBoardPacket(WrappedPacket wrappedPacket) {
    LobbyScoreBoardPacket lobbyScoreBoardPacket = (LobbyScoreBoardPacket) wrappedPacket.getPacket();
    this.gameSession.setLobbyScoreBoard(lobbyScoreBoardPacket.getLobbyScoreBoard());
  }

  /**
   * save a ChatMessage in the chat.
   *
   * @param wrappedPacket wrappedPacket
   */
  public void saveChatMessage(WrappedPacket wrappedPacket) {
    ChatMessagePacket chatMessagePacket = (ChatMessagePacket) wrappedPacket.getPacket();
    this.client.getGameSession().setChat(chatMessagePacket.getChat());
    //Debug.printMessage(this,chatMessagePacket.getChatMessage().getMessage());
  }

  /**
   * updates the player list out of the player list packet.
   *
   * @param wrappedPacket player list packet
   */
  public void updatePlayerList(WrappedPacket wrappedPacket) {
    PlayerListPacket playerListPacket = (PlayerListPacket) wrappedPacket.getPacket();
    this.gameSession.setPlayerList(playerListPacket.getPlayerList());
  }

  /**
   * sends a chat message to the server.
   *
   * @param chat message
   */
  public void broadcastChatMessage(Chat chat) {

    ChatMessagePacket chatMessagePacket = new ChatMessagePacket(chat);
    WrappedPacket wrappedPacket = new WrappedPacket(PacketType.CHAT_MESSAGE_PACKET,
        chatMessagePacket);

    this.client.sendToServer(wrappedPacket);
  }

  /**
   * this method processes a login response packet and sets the login status as received.
   *
   * @param packet received packet
   */
  public void denyLogin(WrappedPacket packet) {

    LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet.getPacket();
    this.gameSession.setLoginStatus(loginResponsePacket.getLoginStatus());

  }


  /**
   * kicks a client from the server and removes all connections.
   *
   * @param player Player
   */
  public void kickClient(Player player) {
    PlayerKickPacket playerKickPacket = new PlayerKickPacket(player.getUsername());
    WrappedPacket wrappedPacket = new WrappedPacket(PacketType.PLAYER_KICK_PACKET,
        playerKickPacket);

    this.client.sendToServer(wrappedPacket);
  }

  /**
   * disconnects a dlient from the server and closes all connections.
   *
   * @param packet wrappedPacket
   */
  public void disconnectClient(WrappedPacket packet) {
    PlayerKickPacket playerKickPacket = (PlayerKickPacket) packet.getPacket();

    if (this.player.getUsername().equals(playerKickPacket.getUsername())) {
      this.gameSession.setPlayerKicked(true);
      disconnectClient();
      //this.gameSession.setPlayerKicked(false);
    }

  }

  /**
   * sends a disconnect message to the server.
   */
  public void disconnectClient() {

    PlayerQuitPacket playerQuitPacket = new PlayerQuitPacket(this.player.getUsername());
    WrappedPacket wrappedPacket = new WrappedPacket(PacketType.PLAYER_QUIT_PACKET,
        playerQuitPacket);

    this.client.sendToServer(wrappedPacket);

    this.client.setGameSession(null);
    this.client.setPlayer(null);
    this.gameSession = null;
    this.client = null;
    this.player = null;
  }


  /**
   * Stop a client by closing the session.
   */
  public void stopClient() {
    try {
      this.session.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public EndpointClient getClient() {
    return client;
  }

  public void setPlayer(Player player) {
    this.player = player;
  }
}
