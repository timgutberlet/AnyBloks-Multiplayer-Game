package net.transmission;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import net.packet.WrappedPacket;


/**
 * Used to encode a packet before it is sent through a websocket.
 *
 * @author tbuscher
 */
public class PacketEncoder implements Encoder.Text<WrappedPacket> {

  private final ObjectMapper objMapper = new ObjectMapper();

  /**
   * Empty. Needs to be implemented to inherit from Encoder.
   *
   * @param config for init
   */
  public void init(final EndpointConfig config) {
  }


  /**
   * Method to convert a packet into a string. Those strings can be sent via websockets.
   *
   * @param toEncode Packet that is to be encoded
   * @throws EncodeException is thrown
   */
  public String encode(final WrappedPacket toEncode) throws EncodeException {
    try {
      return objMapper.writeValueAsString(toEncode);
    } catch (JsonProcessingException e) {
      throw new EncodeException(toEncode, "The packet could not be encoded. See error message: \n",
          e);
    }
  }

  /**
   * Empty. Needs to be implemented to inherit from Encoder.
   */
  public void destroy() {
  }


}
