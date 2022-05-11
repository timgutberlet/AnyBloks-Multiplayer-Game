package net.transmission;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import net.packet.abstr.WrappedPacket;

/**
 * Class to regenerate packets out of strings of encoded packets.
 *
 * @author tbuscher
 */
public class PacketDecoder implements Decoder.Text<WrappedPacket> {

	private final ObjectMapper objMapper = new ObjectMapper();

	/**
	 * Empty. Needs to be implemented to inherit from Decoder.
	 *
	 * @param config for init
	 */
	public void init(final EndpointConfig config) {
	}

	/**
	 * Most used method of this class: recreate a WrappedPacket out of String.
	 *
	 * @param toDecode String that has previously been encoded
	 * @return Packet that was regenerated
	 * @throws DecodeException is thrown
	 */
	public WrappedPacket decode(final String toDecode) throws DecodeException {
		final WrappedPacket decoded;
		try {
			decoded = objMapper.readValue(toDecode, WrappedPacket.class);
		} catch (IOException e) {
			throw new DecodeException(toDecode, "The string could not be decoded into a WrappedPacket!",
					e);
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
