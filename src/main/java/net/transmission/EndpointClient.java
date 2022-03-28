package net.transmission;

import java.io.IOException;
import javax.websocket.ClientEndpoint;
import javax.websocket.EncodeException;
import net.packet.CreateAccountRequestPacket;
import net.packet.Packet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

/**
 * Endpoint for clients.
 *
 * @author tbuscher
 */

@ClientEndpoint(encoders = {PacketEncoder.class}, decoders = {PacketDecoder.class})

public class EndpointClient {

  private static final Logger LOG = LoggerFactory
      .getLogger(EndpointClient.class);

  /**
   * Method used to connect to server. TODO : evaluate createAccount / Login
   *
   * @param ses
   * @param username
   * @param hashedPW
   * @throws IOException
   * @throws EncodeException
   */
  @OnOpen
  public void onOpen(final Session ses, String username, String hashedPW)
      throws IOException, EncodeException {
    ses.getBasicRemote().sendObject(new CreateAccountRequestPacket(username, hashedPW));
  }

  @OnMessage
  public void onMessage(final Packet packet) {
    LOG.info("A packet has been sent here by the server, it is of the type: {}",
        packet.getPacketType().toString());
  }

}


