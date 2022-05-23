package net.server;

import static game.scores.ScoreProvider.getGameSessionScoreBoard;

import game.model.Debug;
import game.model.GameSession;
import game.model.GameState;
import game.model.player.Player;
import game.model.player.PlayerType;
import game.scores.GameScoreBoard;
import game.scores.GameSessionScoreBoard;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import javax.websocket.EncodeException;
import javax.websocket.Session;
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.packet.account.CreateAccountResponsePacket;
import net.packet.game.GameStartPacket;
import net.packet.game.GameUpdatePacket;
import net.packet.game.GameWinPacket;
import net.packet.game.HostQuitPacket;
import net.packet.game.PlayerKickPacket;
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

    //CheckConnectionThread checkConnectionThread = new CheckConnectionThread(gameSession, username,
    //    this.server);
    //checkConnectionThread.start();
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
   * inform all players, but the host, that the host has left, players should return to the lobby.
   */
  public void broadcastHostQuit() {
    //TODOKICK: does this work for localGames?
    WrappedPacket wrappedPacket = new WrappedPacket(PacketType.HOST_QUIT_PACKET,
        new HostQuitPacket());
    for (Player p : this.gameSession.getPlayerList()) {
      //Only send the packet to AIs / remotePlayers
      if (!p.getType().equals(PlayerType.HOST_PLAYER)) {
        //Send the packet to the players via the existing connection
        this.server.sendMessage(wrappedPacket, p.getUsername());

      }
    }
  }

  /**
   * broadcast GAME_WIN_PACKET to all clients. save the gameScore, and if a gameSession ends,
   * connect all gameScores to it.
   *
   * @author tgeilen
   */
  public void broadcastGameWin() {
    //gameSession.updateGameSessionScoreboard();
    gameSession.setGameOver(true);
    Debug.printMessage("_____Hi from broadcast game Win");

    //Saving the result of the game to the DB
    String gameId = "";
    GameState gameState = gameSession.getGame().getGameState();
    HashMap<String, Integer> scoreboard = new HashMap<>();
    Debug.printMessage("Num Players:" + gameState.getPlayerList().size());
    Debug.printMessage("Num length score" + gameState.getScores().length);
    for (int i = 0; i < gameState.getPlayerList().size(); i++) {
      Debug.printMessage("Number: " + i);
      Debug.printMessage("username: " + gameState.getPlayerList().get(i).getUsername());
      Debug.printMessage("Score:" + gameState.getScores()[i]);
      scoreboard.put(gameState.getPlayerList().get(i).getUsername(), gameState.getScores()[i]);
    }
    String gameMode = gameState.getGameMode().getName();
    try {
      DbServer dbServer = DbServer.getInstance();
      //Insert the game and save its gameId
      gameId = dbServer.insertGame(gameMode);
      Debug.printMessage("Inserting game: " + gameId + gameMode);
      GameSession.currentGameIds.add(gameId);
      //Add the scores of the different players
      for (String username : scoreboard.keySet()) {

        Debug.printMessage("Inserting score: " + gameId + username + scoreboard.get(username));
        dbServer.insertScore(gameId, username, scoreboard.get(username));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    //Create gameSessionScoreBoard with current gameIds
    GameSessionScoreBoard gameSessionScoreBoard = getGameSessionScoreBoard(
        GameSession.currentGameIds.toArray(new String[GameSession.currentGameIds.size()]));

    GameWinPacket gameWinPacket = new GameWinPacket(new GameScoreBoard(gameMode, scoreboard),
        gameSessionScoreBoard, this.gameSession.getGameList());
    WrappedPacket wrappedPacket = new WrappedPacket(PacketType.GAME_WIN_PACKET, gameWinPacket);
    GameSession.currentGameIds.add(gameId);

    this.server.broadcastMessage(wrappedPacket);

    try {
      TimeUnit.MILLISECONDS.sleep(200);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    if (gameSession.getGameList().size() > 0) {
      //  Debug.printMessage(this, "STARTING A NEW GAME FROM THE GAMELIST");
      //  gameSession.startGameServer();
    } else {
      //No more games in gameSession, so we return to the lobby

      //Saving the scores of the gameSession
      try {
        DbServer dbServer = DbServer.getInstance();
        //Creating a new gameSessionScore
        String gameSessionScoreId = dbServer.insertGameSessionScore();

        //Save ids of GameScores from List in GameSession into GameScore to GameSessionScore
        for (int i = 0; i < GameSession.currentGameIds.size(); i++) {
          dbServer.insertGameSessionScore2Game(gameSessionScoreId,
              GameSession.currentGameIds.get(i));
        }
        //And remove the gameIds from it after they have been saved to GameSessionScore
        GameSession.currentGameIds = new ArrayList<>();
      } catch (Exception e) {
        e.printStackTrace();
      }


    }

  }

  /**
   * broadcasts a chat to all clients.
   *
   * @param wrappedPacket
   */
  public void broadcastChatMessage(WrappedPacket wrappedPacket) {
    this.server.broadcastMessage(wrappedPacket);
  }

  /**
   * kicks a player from the server.
   *
   * @param username
   */
  public void kickPlayer(String username) {
    PlayerKickPacket playerKickPacket = new PlayerKickPacket(username);
    WrappedPacket wrappedPacket = new WrappedPacket(PacketType.PLAYER_KICK_PACKET,
        playerKickPacket);

    this.server.sendMessage(wrappedPacket, username);

  }


}
