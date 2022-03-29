package game.model;

import game.model.chat.Chat;
import game.model.gamemodes.GameMode;
import java.util.ArrayList;

/**
 * a session is the central place taking care of players joining, starting a game, selecting
 * gamemode, chat, etc...
 *
 * @author tgeilen
 * @Date 21.03.22
 */
public class Session {

  private final Chat chat;
  private final ArrayList<Player> playerList;
  private Game game;
  private final Player host;

  /**
   * a Session is created by a Player in the MainMenu
   *
   * @param player
   * @author tgeilen
   */
  public Session(Player player) {
    this.chat = new Chat();
    this.playerList = new ArrayList<Player>();
    this.host = player;
    this.addPlayer(this.host);
  }

  /**
   * a Player can join a Session from the MainMenu
   *
   * @param username
   * @author tgeilen
   */
  public void addPlayer(String username) {
    Player player = new Player(username, PlayerType.REMOTE_PLAYER);
    this.playerList.add(player);
    player.joinSession(this);
  }

  /**
   * overloading needed to add host to game
   *
   * @param host
   * @author tgeilen
   */
  public void addPlayer(Player host) {
    this.playerList.add(host);
    host.joinSession(this);
  }


  /**
   * starts a new Game of a given GameMode object and adds BOT players
   *
   * @param gameMode
   * @author tgeilen
   */
  public void startGame(GameMode gameMode, PlayerType difficulty) {
    while (this.playerList.size() != gameMode.getNeededPlayers()) {
      this.playerList.add(new Player("BOT", difficulty));
    }

    this.game = new Game(this.playerList, gameMode);
    //TODO change view to game

  }

  public Chat getChat() {
    return this.chat;
  }

  public Game getGame() {
    return this.game;
  }


}

