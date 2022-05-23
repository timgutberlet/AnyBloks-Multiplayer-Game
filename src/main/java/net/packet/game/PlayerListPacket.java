package net.packet.game;

import game.model.player.Player;
import game.model.player.PlayerType;
import java.util.ArrayList;
import net.packet.abstr.Packet;

/**
 * @author tgeilen
 * @Date 17.05.22
 */
public class PlayerListPacket extends Packet {

  ArrayList<Player> playerList;
  Player host;

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
    for (Player p : playerList) {
      if (p.getType().equals(PlayerType.HOST_PLAYER)) {
        this.host = p;
      }
    }
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

  /**
   * getter.
   *
   * @return
   */
  public Player getHost() {
    return this.host;
  }

}
