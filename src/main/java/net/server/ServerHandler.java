package net.server;

import game.model.GameSession;
import net.packet.ChatMessagePacket;
import net.packet.LoginRequestPacket;
import net.packet.WrappedPacket;

/**
 * Provides functions to ServerEndpoint
 *
 * @author tbuscher
 */
public class ServerHandler {
  private GameSession gameSession;
  /**
   * Constructor
   */
  public ServerHandler(GameSession gameSession){
    this.gameSession = gameSession;
  }

  /**
   * Verifies whether Login is correct according to Database
   *
   * @param wrappedPacket wrapped LoginRequestPacket
   * @return String[] [0] "true"/"false" [1] username
   */
  public String[] verifyLogin(WrappedPacket wrappedPacket){

    LoginRequestPacket loginPacket = (LoginRequestPacket) wrappedPacket.getPacket();
    String username = loginPacket.getUsername();
    String passwordHash = loginPacket.getPasswordHash();

    //TODO add logic with database here
    String[] toReturn = {"true", username};
    return toReturn;
  }

  public void saveChatMessage(WrappedPacket wrappedPacket){
    ChatMessagePacket chatMessagePacket = (ChatMessagePacket) wrappedPacket.getPacket();
    String senderUsername = chatMessagePacket.getText();
    String text = chatMessagePacket.getText();

    this.gameSession.getPlayerList();
    //this.gameSession.getChat().addMessage();

  }

}
