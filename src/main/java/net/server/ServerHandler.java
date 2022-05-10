package net.server;

import game.model.Debug;
import game.model.GameSession;
import game.model.gamemodes.GameMode;
import game.model.player.Player;
import java.util.HashMap;
import javax.websocket.Session;
import net.packet.abstr.PacketType;
import net.packet.chat.ChatMessagePacket;
import net.packet.account.LoginRequestPacket;
import net.packet.game.GameStartPacket;
import net.packet.game.InitGamePacket;
import net.packet.game.TurnPacket;
import net.packet.abstr.WrappedPacket;
import net.transmission.EndpointServer;

/**
 * Provides functions to ServerEndpoint
 *
 * @author tbuscher
 */
public class ServerHandler {

  private EndpointServer server;
  private GameSession gameSession;


  /**
   * Constructor
   */
  public ServerHandler(EndpointServer server,GameSession gameSession){

    this.server = server;
    this.gameSession = gameSession;


  }



  /**
   * Verifies whether Login is correct according to Database
   *
   * @param wrappedPacket wrapped LoginRequestPacket
   * @return String[] [0] "true"/"false" [1] username
   */
  public String[] verifyLogin(WrappedPacket wrappedPacket, Session session){

    Debug.printMessage(this,"LOGIN_REQUEST_PACKET recieved in Handler");
    LoginRequestPacket loginPacket = (LoginRequestPacket) wrappedPacket.getPacket();
    String username = loginPacket.getUsername();
    String passwordHash = loginPacket.getPasswordHash();

    Debug.printMessage(this, username + " " + passwordHash);

    //TODO add logic with database here
    String[] toReturn = {"true", username};

    this.server.addUsernameSession(username,session);
    Debug.printMessage(this,"New Length of KeySet: " + this.server.getUsername2Session().keySet().size());
    return toReturn;
  }

  public void startGame(WrappedPacket wrappedPacket){

    InitGamePacket initGamePacket = (InitGamePacket) wrappedPacket.getPacket();
    this.gameSession.startGame(initGamePacket.getGameMode());

    GameStartPacket gameStartPacket = new GameStartPacket(initGamePacket.getGameMode());
    WrappedPacket wrP = new WrappedPacket(PacketType.GAME_START_PACKET,gameStartPacket);

    for(String username: this.server.getUsername2Session().keySet()){
      this.server.sendMessage(wrP,username);
    }





  }

  public void broadcastChatMessage(WrappedPacket wrappedPacket){
    Debug.printMessage(this, "ChatMessage recieved in Handler");
    Debug.printMessage(this,"Number of Sessions: " + this.server.getUsername2Session().keySet().size());
    for(String username: this.server.getUsername2Session().keySet()){
      this.server.sendMessage(wrappedPacket,username);
      Debug.printMessage(this, "ChatMessage sent to " + username);
    }

  }


  /**
   * requests a player to make a turn
   * @author tgeilen
   */
  public void requestTurn(Player player){

    //Session client = this.username2Session.get(player.getName());


  //TODO send RequestTurnPacket to client

  }

  /**
   * recieve a turn by a player and forward it to game logic
   * @author tgeilen
   */

  public void recieveTurn(WrappedPacket wrappedPacket){

    TurnPacket turnPacket = (TurnPacket) wrappedPacket.getPacket();

    this.gameSession.getGameServer();
  }

}
