package net.transmission;

import game.model.Debug;
import game.model.GameState;
import java.io.IOException;
import javax.websocket.ClientEndpoint;
import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import net.packet.chat.ChatMessagePacket;
import net.packet.account.CreateAccountRequestPacket;
import net.packet.game.GameUpdatePacket;
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Endpoint for clients.
 *
 * @author tbuscher
 */

@ClientEndpoint(encoders = {PacketEncoder.class}, decoders = {PacketDecoder.class})

public class EndpointClient {

  private static final Logger LOG = LoggerFactory.getLogger(EndpointClient.class);

  /**
   * Method used to connect to server. TODO : evaluate createAccount / Login
   *
   * @param ses Session in use
   * @throws IOException     is thrown
   * @throws EncodeException is thrown
   */
  @OnOpen
  public void onOpen(final Session ses)
      throws IOException, EncodeException {
//        ses.getBasicRemote().sendObject(new CreateAccountRequestPacket("testuser", "testPW"));
    ses.getBasicRemote().sendObject((new WrappedPacket((PacketType.CHAT_MESSAGE_PACKET),
        new ChatMessagePacket("Hello World", "username"))));
    Debug.printMessage(this,"CHAT_MESSAGE_PACKET sent");

   // ses.getBasicRemote().sendObject(new WrappedPacket(PacketType.CREATE_ACCOUNT_REQUEST_PACKET,
     //   new CreateAccountRequestPacket("testuser", "testPW")));
   // Debug.printMessage(this,"CREATE_ACCOUNT_REQUEST sent");

    ses.setMaxBinaryMessageBufferSize(1024*1024*20);
    ses.setMaxTextMessageBufferSize(1024*1024*20);
  }

  @OnMessage
  public void onMessage(final WrappedPacket wrappedPacket) {
    LOG.info("A packet has been sent here by the server, it is of the type: {}",
        wrappedPacket.getPacketType().toString());
    PacketType type = wrappedPacket.getPacketType();
    switch (type) {
      case PLAYER_ORDER_PACKET:
        break;
      case GAME_UPDATE_PACKET:
        GameUpdatePacket gameUpdatePacket = (GameUpdatePacket) wrappedPacket.getPacket();
        GameState gameState = gameUpdatePacket.getGameState();
        System.out.println(gameState);
        System.out.println("Enter this string: aaaJJJ to find this in console");
        break;

      case CHAT_MESSAGE_PACKET:
        ChatMessagePacket chatMessagePacket = (ChatMessagePacket) wrappedPacket.getPacket();

        System.out.println(chatMessagePacket.getSender());
        System.out.println(chatMessagePacket.getText());
        System.out.println("Enter this string: aaaJJJ to find this in console");

      case REQUEST_TURN_PACKET:

        //
    }

  }

}


