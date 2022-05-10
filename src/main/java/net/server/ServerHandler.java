package net.server;

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

  private HashMap<String, Session> username2Session;
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

    LoginRequestPacket loginPacket = (LoginRequestPacket) wrappedPacket.getPacket();
    String username = loginPacket.getUsername();
    String passwordHash = loginPacket.getPasswordHash();

    //TODO add logic with database here
    String[] toReturn = {"true", username};
    this.username2Session.put(username,session);
    return toReturn;
  }

  public void startGame(WrappedPacket wrappedPacket){

    InitGamePacket initGamePacket = (InitGamePacket) wrappedPacket.getPacket();
    this.gameSession.startGame(initGamePacket.getGameMode());

    GameStartPacket gameStartPacket = new GameStartPacket(initGamePacket.getGameMode());
    WrappedPacket wrP = new WrappedPacket(PacketType.GAME_START_PACKET,gameStartPacket);

    for(String username: this.username2Session.keySet()){
      Session client = this.username2Session.get(username);
      this.server.sendMessage(wrP,client);
    }





  }

  public void broadcastChatMessage(WrappedPacket wrappedPacket){
    System.out.println("Message recieved");
    for(String username: this.username2Session.keySet()){
      Session client = this.username2Session.get(username);
      this.server.sendMessage(wrappedPacket,client);
    }

  }


  /**
   * requests a player to make a turn
   * @author tgeilen
   */
  public void requestTurn(Player player){

    Session client = this.username2Session.get(player.getName());


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
