package net.transmission;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import net.packet.Packet;

/**
 * Class to regenerate packets out of strings of encoded packets.
 *
 * @author tbuscher
 */
public class PacketDecoder implements Decoder.Text<Packet> {

  private final ObjectMapper objMapper = new ObjectMapper();

  /**
   * Empty. Needs to be implemented to inherit from Decoder.
   *
   * @param config for init
   */
  public void init(final EndpointConfig config) {
  }

  /**
   * Most used method of this class: recreate a packet out of String.
   *
   * @param toDecode String that has previously been encoded
   * @return Packet that was regenerated
   * @throws DecodeException is thrown
   */
  public Packet decode(final String toDecode) throws DecodeException {
    final Packet decoded;
    try {
      decoded = objMapper.readValue(toDecode, Packet.class);
    } catch (IOException e) {
      throw new DecodeException(toDecode, "The string could not be decoded into a packet!", e);
    }

    return decoded;
  }

  /**
   * Empty. Needs to be implemented to inherit from Decoder.
   *
   * @param str to be checked
   */
  public boolean willDecode(final String str) {
    return true;
  }

  /**
   * Empty. Needs to be implemented to inherit from Decoder.
   */
  public void destroy() {
  }

}
