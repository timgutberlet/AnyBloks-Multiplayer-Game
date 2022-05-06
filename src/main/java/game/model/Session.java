package game.model;

import game.model.chat.Chat;
import game.model.gamemodes.GameMode;
import java.util.ArrayList;

/**
 * a session is the central place taking care of players joining, starting a game, selecting
 * gamemode, chat, etc...
 *
 * @author tgeilen
 */
public class Session {

  private final Chat chat;
  private final ArrayList<Player> playerList;
  private  Player host;
  private Game game;

  /**
   * a Session is created by a Player in the MainMenu
   *
   * @param player
   * @author tgeilen
   */
  public Session(Player player) {
    //create chatThread and start it
    this.chat = new Chat();
    this.chat.run();

    this.playerList = new ArrayList<Player>();
    //this.host = player;
    //this.addPlayer(this.host);
  }

  /**
   * a Player can join a Session from the MainMenu
   *
   * @param player
   * @author tgeilen
   */
  public void addPlayer(Player player) {
    this.playerList.add(player);
    player.setSession(this);
  }

  /**
   *
   * @param host
   * @author tgeilen
   */
  public void addHost(Player host) {
    this.playerList.add(host);
    host.setSession(this);
  }


  /**
   * starts a new Game
   *
   * @author tgeilen
   */
  public void startGame() {
    this.game.startGame();
  }

  public Chat getChat() {
    return this.chat;
  }

  public Game getGame() {
    return this.game;
  }

  public ArrayList<Player> getPlayerList(){return this.playerList;}

  public void setGame(Game game){
    this.game = game;
  }


}

