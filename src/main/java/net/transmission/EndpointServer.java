package net.transmission;


import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import net.packet.Packet;


/**
 * Endpoint for the servers websocket.
 *
 * @author tbuscher
 */

@ServerEndpoint(
    value = "/packet",
    encoders = {PacketEncoder.class},
    decoders = {PacketDecoder.class}
)
public class EndpointServer {

  // Creating HashSet for all Sessions
  private static final Set<Session> allSessions = Collections.synchronizedSet(new HashSet<>());


  /**
   * Add a new session to the active ones.
   *
   * @param ses
   */
  @OnOpen
  public void onOpen(final Session ses) {
    allSessions.add(ses);
  }

  /**
   * Remove a session.
   *
   * @param ses from Set
   */
  @OnClose
  public void onClose(final Session ses) {
    allSessions.remove(ses);
  }

  /**
   * Broadcast a message to all clients that have a session.
   *
   * @param packet
   * @throws IOException
   * @throws EncodeException
   */
  @OnMessage
  public void onMessage(final Packet packet) throws IOException, EncodeException {
    for (final Session ses : allSessions) {
      ses.getBasicRemote().sendObject(packet);
    }
  }


}
