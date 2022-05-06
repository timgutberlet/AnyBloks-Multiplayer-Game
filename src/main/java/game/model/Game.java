package game.model;

import game.model.gamemodes.GameMode;
import java.util.ArrayList;

/**
 * a game is started in a Session by the host and dies when someone wins
 *
 * @author tgeilen
 */
public class Game {

  private Session session;
  private final GameState gameState;
  private Board board;
  private GameMode gamemode;
  private ArrayList<Player> players;


  public Game(Session session, GameMode gamemode) {
    this.session = session;
    this.board = new BoardSquare(gamemode);
    this.gamemode = gamemode;
    this.players = session.getPlayerList();
    this.gameState = new GameState(this.session, gamemode);
    //for (Player p : players) {
    //  this.gameState.addPlayer(p);
    //}

  }


  public Board getBoard() {
    return board;
  }

  public void setBoard(Board board) {
    this.board = board;
  }

  public GameMode getGamemode() {
    return gamemode;
  }

  public void setGamemode(GameMode gamemode) {
    this.gamemode = gamemode;
  }

  public ArrayList<Player> getPlayers() {
    return players;
  }

  public void setPlayers(ArrayList<Player> players) {
    this.players = players;
  }

  public GameState getGameState() {
    return gameState;
  }

  public void startGame() {
    gameState.setStateRunning(true);
  }
}
