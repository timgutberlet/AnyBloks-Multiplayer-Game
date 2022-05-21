package net.packet.game;

import game.model.player.Player;
import java.util.ArrayList;
import net.packet.abstr.Packet;

/**
 * @author tgeilen
 * @Date 17.05.22
 */
public class PlayerListPacket extends Packet {

  ArrayList<Player> playerList;

  /**
   * empty constructor for jackson.
   */
  public PlayerListPacket() {
  }

  /**
   * constructor.
   *
   * @param playerList
   */
  public PlayerListPacket(ArrayList<Player> playerList) {
    this.playerList = playerList;
  }

  /**
   * getter.
   *
   * @return
   */
  public ArrayList<Player> getPlayerList() {
    ArrayList playerList = this.playerList;
    return playerList;
  }

}
