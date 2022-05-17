package net.server;

import game.model.Debug;
import game.model.GameSession;
import game.model.GameState;
import game.model.Turn;
import game.model.gamemodes.GameMode;
import game.model.player.Player;
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.packet.chat.ChatMessagePacket;
import net.packet.game.GameStartPacket;
import net.packet.game.GameUpdatePacket;
import net.packet.game.GameWinPacket;
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
    this.gameSession = new GameSession();
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
      Turn turn = this.player.makeTurn(gameState);
      if (turn == null) {
        Debug.printMessage(this, "I don't know what to do!!!");
      } else {
        Debug.printMessage(this, player.getUsername() + ": I know what to do...");
      }
      TurnPacket turnPacket = new TurnPacket(player.getUsername(), turn);
      WrappedPacket wrPacket = new WrappedPacket(PacketType.TURN_PACKET, turnPacket);
      this.client.sendToServer(wrPacket);
    }
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
    this.client.getGameSession().addChatMessage(chatMessagePacket.getChatMessage());
  }

  public void updatePlayerList(WrappedPacket wrappedPacket){
    PlayerListPacket playerListPacket = (PlayerListPacket) wrappedPacket.getPacket();
    this.gameSession.setPlayerList(playerListPacket.getPlayerList());
  }

}
