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

  private final ObjectMapper oMapper = new ObjectMapper();

  /**
   * Empty. Needs to be implemented to inherit from Decoder.
   *
   * @param config
   */
  public void init(final EndpointConfig config) {
  }

  public Packet decode(final String toDecode) throws DecodeException {
    final Packet decoded;
    try {
      decoded = oMapper.readValue(toDecode, Packet.class);
    } catch (IOException e) {
      throw new DecodeException(toDecode, "The string could not be decoded into a packet!", e);
    }

    return decoded;
  }

  /**
   * Empty. Needs to be implemented to inherit from Decoder.
   *
   * @param str
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
