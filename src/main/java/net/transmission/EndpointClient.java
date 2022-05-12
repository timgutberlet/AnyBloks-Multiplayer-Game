package net.transmission;

import game.model.GameSession;
import game.model.player.Player;
import java.io.IOException;
import javax.websocket.ClientEndpoint;
import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.server.ClientHandler;
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
  private Session server;
  private GameSession gameSession;
  private ClientHandler clientHandler;
  private Player player;

  /**
   * Method used to connect to server. TODO : evaluate createAccount / Login
   *
   * @param ses Session in use
   * @throws EncodeException is thrown
   */
  @OnOpen
  public void onOpen(final Session ses) {
//        ses.getBasicRemote().sendObject(new CreateAccountRequestPacket("testuser", "testPW"));

/*
   ses.getBasicRemote().sendObject(new WrappedPacket(PacketType.CREATE_ACCOUNT_REQUEST_PACKET,
      new CreateAccountRequestPacket("testuser", "testPW")));
   Debug.printMessage(this,"CREATE_ACCOUNT_REQUEST sent to " + ses.getId());
*/
    this.server = ses;
    this.clientHandler = new ClientHandler(this);
    this.gameSession = new GameSession();
    this.player = new Player();
    this.player.setGameSession(this.gameSession);
    ses.setMaxBinaryMessageBufferSize(1024 * 1024 * 20);
    ses.setMaxTextMessageBufferSize(1024 * 1024 * 20);
  }

  /**
   * Called whenever a message is received.
   *
   * @param packet that has been received
   * @param ses from which the packet came
   */

  @OnMessage
  public void onMessage(final WrappedPacket packet, Session ses) {
    LOG.info("A packet has been sent here by the server, it is of the type: {} send by {}",
        packet.getPacketType().toString(), ses.getId());
    PacketType type = packet.getPacketType();
    switch (type) {
      case PLAYER_ORDER_PACKET:
        break;

      case CHAT_MESSAGE_PACKET:
        this.clientHandler.saveChatMessage(packet);
        break;

      case GAME_START_PACKET:
        this.clientHandler.startGame(packet);
        break;

      case REQUEST_TURN_PACKET:
        this.clientHandler.makeTurn(packet);
        break;

      case GAME_UPDATE_PACKET:
        this.clientHandler.updateGame(packet);
        break;

      case GAME_WIN_PACKET:
        this.clientHandler.endGame(packet);
        break;

      case ILLEGAL_TURN_PACKET:
        this.clientHandler.makeTurn(packet);
        break;

      //
    }

  }

  /**
   * function to easily send packets to server.
   *
   * @param wrappedPacket
   * @author tgeilen
   */
  public void sendToServer(WrappedPacket wrappedPacket) {
    try {
      this.server.getBasicRemote().sendObject(wrappedPacket);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (EncodeException e) {
      e.printStackTrace();
    }
  }

  public GameSession getGameSession() {
    return gameSession;
  }

  public Player getPlayer() {
    return player;
  }
}


