package net.tests.game;

import game.model.Debug;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author tgeilen
 * @Date 16.05.22
 */
public class testInetAdress {

	public static void main(String[] args){
		try {
			Debug.printMessage( InetAddress.getLocalHost().toString());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
