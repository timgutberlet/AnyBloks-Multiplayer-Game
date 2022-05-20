package net.server;

import game.model.Debug;
import game.model.GameSession;
import game.model.GameState;
import game.model.Turn;
import game.model.chat.ChatMessage;
import game.model.gamemodes.GameMode;
import game.model.player.Player;
import game.model.player.PlayerType;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.EncodeException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.packet.account.CreateAccountRequestPacket;
import net.packet.account.LoginRequestPacket;
import net.packet.account.LoginResponsePacket;
import net.packet.chat.ChatMessagePacket;
import net.packet.game.GameStartPacket;
import net.packet.game.GameUpdatePacket;
import net.packet.game.GameWinPacket;
import net.packet.game.InitGamePacket;
import net.packet.game.InitSessionPacket;
import net.packet.game.PlayerListPacket;
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

  private final EndpointClient client;
  private final Player player;
  private final GameSession gameSession;

  public ClientHandler(EndpointClient client) {
    this.client = client;
    this.player = this.client.getPlayer();
    this.gameSession = client.getGameSession();

  }


  public void initLocalGame(Player localPlayer){

    final WebSocketContainer container = ContainerProvider.getWebSocketContainer();

    try {
      TimeUnit.MILLISECONDS.sleep(200);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    Session ses;

    try {

      String IPAdress = Inet4Address.getLocalHost().getHostAddress();

      ses = container.connectToServer(this.client, URI.create("ws://" + IPAdress + ":8081/packet"));

      TimeUnit.MILLISECONDS.sleep(100);

      //Init session
//      InitSessionPacket initSessionPacket = new InitSessionPacket();
//      WrappedPacket wrappedPacket = new WrappedPacket(PacketType.INIT_SESSION_PACKET,
//          initSessionPacket);
//      ses.getBasicRemote().sendObject(wrappedPacket);

      //Create Account
      String passwordHash = HashingHandler.sha256encode("123456");
      CreateAccountRequestPacket createAccReq = new CreateAccountRequestPacket(
          localPlayer.getUsername(),
          passwordHash);
     WrappedPacket wrappedPacket = new WrappedPacket(PacketType.CREATE_ACCOUNT_REQUEST_PACKET,
          createAccReq);
      //... and send it
      client.sendToServer(wrappedPacket);

      //Sleep so updates can be made in DB
      try {
        TimeUnit.MILLISECONDS.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      //Login
      LoginRequestPacket loginRequestPacket = new LoginRequestPacket(localPlayer.getUsername(),
          passwordHash, localPlayer.getType());
      Debug.printMessage("LoginRequestPacket has been sent to the server");
      wrappedPacket = new WrappedPacket(PacketType.LOGIN_REQUEST_PACKET,
          loginRequestPacket);
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
  public void initLocalGame(Player localPlayer, String ip){

    final WebSocketContainer container = ContainerProvider.getWebSocketContainer();

    try {
      TimeUnit.MILLISECONDS.sleep(200);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    Session ses;

    try {

      String IPAdress = ip;

      ses = container.connectToServer(this.client, URI.create("ws://" + IPAdress + ":8081/packet"));

      TimeUnit.MILLISECONDS.sleep(100);

      //Init session
//      InitSessionPacket initSessionPacket = new InitSessionPacket();
//      WrappedPacket wrappedPacket = new WrappedPacket(PacketType.INIT_SESSION_PACKET,
//          initSessionPacket);
//      ses.getBasicRemote().sendObject(wrappedPacket);

      //Create Account
      String passwordHash = HashingHandler.sha256encode("123456");
      CreateAccountRequestPacket createAccReq = new CreateAccountRequestPacket(
          localPlayer.getUsername(),
          passwordHash);
      WrappedPacket wrappedPacket = new WrappedPacket(PacketType.CREATE_ACCOUNT_REQUEST_PACKET,
          createAccReq);
      //... and send it
      client.sendToServer(wrappedPacket);

      //Sleep so updates can be made in DB
      try {
        TimeUnit.MILLISECONDS.sleep(500);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      //Login
      LoginRequestPacket loginRequestPacket = new LoginRequestPacket(localPlayer.getUsername(),
          passwordHash, localPlayer.getType());
      Debug.printMessage("LoginRequestPacket has been sent to the server");
      wrappedPacket = new WrappedPacket(PacketType.LOGIN_REQUEST_PACKET,
          loginRequestPacket);
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
   * function called on client side to send INIT GAME PACKET TO SERVER
   *
   * @param gameModes
   */
  public void startLocalGame(LinkedList<GameMode> gameModes){
    InitGamePacket initGamePacket = new InitGamePacket(gameModes);
    WrappedPacket wrappedPacket = new WrappedPacket(PacketType.INIT_GAME_PACKET, initGamePacket);

    this.client.sendToServer(wrappedPacket);


  }


  /**
   * function that starts a game on the client side.
   *
   * @param wrappedPacket
   * @author tgeilen
   */
  public void startGame(WrappedPacket wrappedPacket) {
    Debug.printMessage(this, "StartGamePacket recieved by a client");
    GameStartPacket gameStartPacket = (GameStartPacket) wrappedPacket.getPacket();
    GameMode gamemode = gameStartPacket.getGameMode();
    GameState gameState = gameStartPacket.getGameState();
    this.client.getGameSession().startGame(gamemode);
    this.client.getGameSession().updateGame(gameState);
    this.client.getGameSession().setGameStarted(true);
  }

  /**
   * function that calls the client to make a turn and sends it to serve
   *
   * @param wrappedPacket
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
      Debug.printMessage(this,"Runde: " + gameState.getRound());
      Debug.printMessage(this,gameState.isFirstRound()?"FIRST ROUND!!!":"___NOT___ FIRST ROUND");

      // flag for UI to enable input
      this.gameSession.setLocalPlayerTurn(true);

      Turn turn = this.gameSession.getLocalPlayer().makeTurn(gameState);;
      if (turn == null) {
        Debug.printMessage(this, "I don't know what to do!!!");
      } else {
        Debug.printMessage(this, player.getUsername() + ": I know what to do...");
      }

      this.sendTurn(turn);

      this.gameSession.setLocalPlayerTurn(false);

    }
  }

  public void sendTurn(Turn turn){
    Debug.printMessage(this,turn.toString());
    TurnPacket turnPacket = new TurnPacket(player.getUsername(), turn);
    WrappedPacket wrPacket = new WrappedPacket(PacketType.TURN_PACKET, turnPacket);
    this.client.sendToServer(wrPacket);
  }


  /**
   * updates the gameState for the local client.
   *
   * @param wrappedPacket
   */
  public void updateGame(WrappedPacket wrappedPacket) {
    GameUpdatePacket gameUpdatePacket = (GameUpdatePacket) wrappedPacket.getPacket();
    GameState gameState = gameUpdatePacket.getGameState();

    this.client.getGameSession().updateGame(gameState);
  }

  /**
   * end a local game.
   *
   * @param wrappedPacket
   * @author tgeilen
   */
  public void endGame(WrappedPacket wrappedPacket) {
    GameWinPacket gameWinPacket = (GameWinPacket) wrappedPacket.getPacket();
    String winner = gameWinPacket.getUsername();
    this.client.getGameSession().endGame(winner);
  }

  /**
   * save a ChatMessage in the chat.
   */
  public void saveChatMessage(WrappedPacket wrappedPacket) {
    ChatMessagePacket chatMessagePacket = (ChatMessagePacket) wrappedPacket.getPacket();
    this.client.getGameSession().saveChatMessage(chatMessagePacket.getChatMessage());
    Debug.printMessage(this,chatMessagePacket.getChatMessage().getMessage());
  }

  public void updatePlayerList(WrappedPacket wrappedPacket){
    PlayerListPacket playerListPacket = (PlayerListPacket) wrappedPacket.getPacket();
    this.gameSession.setPlayerList(playerListPacket.getPlayerList());
  }

  public void broadcastChatMessage(ChatMessage chatMessage){

    ChatMessagePacket chatMessagePacket = new ChatMessagePacket(chatMessage);
    WrappedPacket wrappedPacket = new WrappedPacket(PacketType.CHAT_MESSAGE_PACKET, chatMessagePacket);

    this.client.sendToServer(wrappedPacket);
  }

}
