package game.model;

import game.model.chat.Chat;
import game.model.gamemodes.GameMode;
import game.model.player.Player;
import java.util.ArrayList;
import java.util.HashMap;
import net.server.HostServer;

/**
 * a session is the central place taking care of players joining, starting a game, selecting
 * gamemode, chat, etc...
 *
 * @author tgeilen
 */
public class GameSession {

  private static HostServer hostServer = new HostServer();

  private final Chat chat;
  private final ArrayList<Player> playerList;
  private  Player hostPlayer;
  private Game game;
  private GameServer gameServer;

  private HashMap<String,Integer> scoreboard = new HashMap<String,Integer>();

  /**
   * a Session is created by a Player in the MainMenu
   *
   * @param player
   * @author tgeilen
   */
  public GameSession(Player player) {
    //create chatThread and start it
    this.chat = new Chat();
    this.chat.run();

    this.playerList = new ArrayList<Player>();
    //this.host = player;
    //this.addPlayer(this.host);

    try {
      hostServer.startWebsocket(8080);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * a Session is created
   *
   * @param
   * @author tgeilen
   */
  public GameSession() {
    //create chatThread and start it
    this.chat = new Chat();
    this.chat.run();

    this.playerList = new ArrayList<Player>();

    try {
      //hostServer.startWebsocket(8080);

    } catch (Exception e) {
      e.printStackTrace();
    }

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
   * function to set the host of a session
   * @param host
   * @author tgeilen
   */
  public void addHost(Player host) {
    this.playerList.add(host);
    host.setSession(this);
  }


  /**
   * creates and starts a new Game
   *
   * @author tgeilen
   */
  public Game startGame(GameMode gameMode) {
    this.game = new Game(this, gameMode);
    this.game.startGame();
    return this.game;
  }


  /**
   * add the value of the placed poly to the scoreboard
   * @param player
   * @param value
   * @author tgeilen
   */
  public void increaseScore(Player player, int value){
    Integer currentScore = this.scoreboard.get(player.getName());

    if(currentScore != null){
      this.scoreboard.put(player.getName(),currentScore+value);
    } else {
      this.scoreboard.put(player.getName(), value);
    }

  }

  public void stopSession(){
    hostServer.stop();
  }

  public Chat getChat() {
    return this.chat;
  }

  public Game getGame() {
    return this.game;
  }

  public ArrayList<Player> getPlayerList(){
    return this.playerList;
  }

  public void setGame(Game game){
    this.game = game;
  }

  public GameServer getGameServer() {
    return gameServer;
  }

  /**
   * function that helps to output the most relevant information of a session
   * @return
   * @author tgeilen
   */
  @Override
  public String toString(){
    String str = "[SESSION INFO] \n";

    for(Player p: this.playerList){
      str += p.getName() + "  |  " + p.getType() + "  |  " + this.scoreboard.get(p.getName()) + "\n";
    }

    return str;
  }




}

