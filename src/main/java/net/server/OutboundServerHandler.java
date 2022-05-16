package net.server;

import game.model.Color;
import game.model.Debug;
import game.model.GameSession;
import game.model.GameState;
import game.model.player.Player;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.websocket.EncodeException;
import javax.websocket.Session;
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.packet.account.CreateAccountResponsePacket;
import net.packet.game.GameStartPacket;
import net.packet.game.GameUpdatePacket;
import net.packet.game.GameWinPacket;
import net.packet.game.RequestTurnPacket;
import net.transmission.EndpointServer;

/**
 * Provides functions to ServerEndpoint.
 *
 * @author tgeilen
 */
public class OutboundServerHandler {

  private final EndpointServer server;
  private final GameSession gameSession;


  /**
   * Constructor
   */
  public OutboundServerHandler(EndpointServer server, GameSession gameSession) {

    this.server = server;
    this.gameSession = gameSession;
    this.gameSession.setOutboundServerHandler(this);

    Debug.printMessage(this, "OutboundServerHandler created");

  }

  /**
   * send a REQUEST_TURN_PACKET to a client
   *
   * @param username
   * @author tgeilen
   */
  public void requestTurn(String username) {
    Debug.printMessage(this, username + " will be requested to make a turn");
    GameState gameState = this.gameSession.getGame().getGameState();
    Debug.printMessage(this, gameState.toString());
    RequestTurnPacket requestTurnPacket = new RequestTurnPacket(username, gameState);
    WrappedPacket wrappedPacket = new WrappedPacket(PacketType.REQUEST_TURN_PACKET,
        requestTurnPacket);

    this.server.sendMessage(wrappedPacket, username);
    Debug.printMessage(this, "Requested turn from " + username);

    CheckConnectionThread checkConnectionThread = new CheckConnectionThread(gameSession, username,
        this.server);
    checkConnectionThread.start();
  }


  /**
   * send GAME_START_PACKET to all clients
   *
   * @param gameState
   * @tgeilen
   */
  public void sendGameStart(String username, GameState gameState) {
    GameStartPacket gameStartPacket = new GameStartPacket(gameState);
    WrappedPacket wrappedPacket = new WrappedPacket(PacketType.GAME_START_PACKET, gameStartPacket);

    this.server.sendMessage(wrappedPacket, username);
  }

  /**
   * broadcast GAME_START_PACKET to all clients
   *
   * @param gameState
   * @tgeilen
   */
  public void broadcastGameStart(GameState gameState) {
    GameStartPacket gameStartPacket = new GameStartPacket(gameState);
    WrappedPacket wrappedPacket = new WrappedPacket(PacketType.GAME_START_PACKET, gameStartPacket);

    this.server.broadcastMessage(wrappedPacket);
  }

  /**
   * broadcast GAME_UPDATE_PACKET to all clients
   *
   * @author tgeilen
   */
  public void broadcastGameUpdate() {
    GameState gameState = this.gameSession.getGame().getGameState();

    GameUpdatePacket gameUpdatePacket = new GameUpdatePacket(gameState);
    WrappedPacket wrappedPacket = new WrappedPacket(PacketType.GAME_UPDATE_PACKET,
        gameUpdatePacket);

    this.server.broadcastMessage(wrappedPacket);
  }

  /**
   * Sends an CreateAccountResponsePacket back to the client.
   *
   * @param client to send packet to
   * @param answer potential errorMessage, empty string if creation successful
   */
  public void accountRequestResponse(Session client, String answer) {
    //If the error message is set to something different than "" there was an error
    boolean success = answer.equals("");
    //Create the packet and send it
    CreateAccountResponsePacket carp = new CreateAccountResponsePacket(success, answer);
    WrappedPacket wrappedPacket = new WrappedPacket(PacketType.CREATE_ACCOUNT_RESPONSE_PACKET,
        carp);
    try {
      client.getBasicRemote().sendObject(wrappedPacket);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (EncodeException e) {
      e.printStackTrace();
    }
  }

  /**
   * broadcast GAME_WIN_PACKET to all clients
   *
   * @param usernameWinner
   * @author tgeilen
   */
  public void broadcastGameWin(String usernameWinner) {
    gameSession.updateGameSessionScoreboard();
    GameWinPacket gameWinPacket = new GameWinPacket(usernameWinner);
    WrappedPacket wrappedPacket = new WrappedPacket(PacketType.GAME_WIN_PACKET, gameWinPacket);

    //TODO: Save to DB
    //Remember inserted gameScoreID in GameSessionList
    GameState gameState = gameSession.getGame().getGameState();
    for (Player p : gameSession.getPlayerList()) {
      Color c = gameState.getColorFromPlayer(p);
      int score = gameState.getBoard().getScoreOfColor(c);
      String username = p.getUsername();
    }
    String gameMode = gameState.getGameMode().getName();
    this.server.broadcastMessage(wrappedPacket);

    try {
      TimeUnit.SECONDS.sleep(2);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    if (gameSession.getGameList().size() > 0) {
      Debug.printMessage(this, "STARTING A NEW GAME FROM THE GAMELIST");
      gameSession.startGameServer();
    } else {
      //No more games in gameSession, so we return to the lobby
      //TODO: SAVE TOURNAMENT SCORES TO DB
      //Save ids of GameScores from List in GameSession into GameScore to TournamentScore

    }

  }

  public void broadcastChatMessage(WrappedPacket wrappedPacket) {
    this.server.broadcastMessage(wrappedPacket);
  }


}
