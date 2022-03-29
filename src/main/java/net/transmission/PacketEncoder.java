package net.transmission;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import net.packet.Packet;


/**
 * Used to encode a packet before it is sent through a websocket.
 *
 * @author tbuscher
 */
public class PacketEncoder implements Encoder.Text<Packet> {

  private final ObjectMapper oMapper = new ObjectMapper();

  /**
   * Empty. Needs to be implemented to inherit from Encoder.
   *
   * @param config
   */
  public void init(final EndpointConfig config) {
  }


  /**
   * Method to convert a packet into a string. Those strings can be sent via websockets.
   *
   * @param toEncode
   * @throws EncodeException
   */
  public String encode(final Packet toEncode) throws EncodeException {
    try {
      return oMapper.writeValueAsString(toEncode);
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
