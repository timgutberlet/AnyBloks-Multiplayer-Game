package net.transmission;


import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import net.packet.WrappedPacket;


/**
 * Endpoint for the servers websocket.
 *
 * @author tbuscher
 */

@ServerEndpoint(value = "/packet", encoders = {PacketEncoder.class}, decoders = {
    PacketDecoder.class})
public class EndpointServer {

  // Creating HashSet for all Sessions
  private static final Set<Session> allSessions = Collections.synchronizedSet(new HashSet<>());


  /**
   * Add a new session to the active ones.
   *
   * @param ses Session to be added
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
   * @param hashedPW message to be sent out
   * @throws IOException     is thrown
   * @throws EncodeException is thrown
   */
  @OnMessage
//  public void onMessage(final WrappedPacket packet) throws IOException, EncodeException {
    public void onMessage(final String hashedPW) throws IOException, EncodeException {
      System.out.println(hashedPW);
//      for (final Session ses : allSessions) {
////      ses.getBasicRemote().sendObject(packet);
//    }
  }


}
