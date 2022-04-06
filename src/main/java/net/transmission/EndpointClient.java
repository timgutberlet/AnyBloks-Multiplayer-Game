package net.transmission;

import java.io.IOException;
import javax.websocket.ClientEndpoint;
import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import net.packet.CreateAccountRequestPacket;
import net.packet.Packet;
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
   * @param ses      Session in use
   * @param username of user
   * @param hashedPw that user requests
   * @throws IOException     is thrown
   * @throws EncodeException is thrown
   */
  @OnOpen
  public void onOpen(final Session ses, String username, String hashedPw)
      throws IOException, EncodeException {
    ses.getBasicRemote().sendObject(new CreateAccountRequestPacket(username, hashedPw));
  }

  @OnMessage
  public void onMessage(final Packet packet) {
    LOG.info("A packet has been sent here by the server, it is of the type: {}",
        packet.getPacketType().toString());
  }

}


