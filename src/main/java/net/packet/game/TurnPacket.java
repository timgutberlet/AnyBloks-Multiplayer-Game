package net.packet.game;

import game.model.Turn;
import game.model.polygon.Poly;
import net.packet.abstr.Packet;

/**
 * packet that is sent from a player to a server containing the information of the made turn
 *
 * @author tgeilen
 * @Date 10.05.22
 */
public class TurnPacket extends Packet {

  public String username;
 public Turn turn;

  public TurnPacket() {
    this.username = null;
    this.turn = null;
  }

  public TurnPacket(String username, Turn turn) {
    this.username = username;
    this.turn = turn;
  }

  public Turn getTurn() {
    return this.turn;
  }

  public String getUsername() {
    return username;
  }
}
