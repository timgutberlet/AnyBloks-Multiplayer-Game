package net.tests.game;

import game.model.Debug;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author tgeilen
 * @Date 16.05.22
 */
public class testInetAdress {

	public static void main(String[] args) throws UnknownHostException {
		Debug.printMessage( Inet4Address.getLocalHost().getHostAddress());
	}

}
