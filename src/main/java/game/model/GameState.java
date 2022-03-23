package game.model;

import java.io.Serializable;
import java.util.ArrayList;
import game.model.gamemodes.*;

/**
 * this class represents the current state of one specific game
 */

public class GameState implements Serializable {

  /**
   * Required for serializable objects.
   */
  private static final long serialVersionUID = 1L;

  /**
   * used game mode
   */
  private final GameMode gameMode;

  /**
   * game board
   */
  private final Board board;

  /**
   * list of arrays of the remaining polys of each player (every player has the same index in player
   * as their remaining polys)
   */
  private final ArrayList<ArrayList<PolySquare>> remainingPolys = new ArrayList<>();
  /**
   * list of every participating player
   */
  private final ArrayList<Player> player;
  /**
   * round number
   */
  private int round;
  /**
   * turn number
   */
  private int turn;
  /**
   * states if the game is currently running
   */
  private boolean running;

  /**
   * states if the game has already started
   */
  private boolean started;

  /**
   * The reason why the game ended.
   */
  private String stateEnding;

  /**
   * initializing of all default parameters
   *
   * @param gameMode gives the game mode for the current game
   * @author tiotto
   */
  public GameState(GameMode gameMode) {
    this.gameMode = gameMode;
    board = new Board(gameMode);
    round = 1;
    turn = 0;
    player = new ArrayList<>();
    running = false;
    started = false;
  }

  public Board getBoard() {
    return board;
  }

  /**
   * returns the remaining polys for a specific player
   *
   * @param p player for whom the remaining parts are required
   * @return remaining polys for a specific player
   */
  public ArrayList<PolySquare> getRemainingPolys(Player p) {

    return remainingPolys.get(player.indexOf(p));
  }

  public Player getPlayerCurrent() {
    if ( getPlayer().size() > 0 ) {
      return getPlayer().get(this.turn);
    }
    else {
      return null;
    }
  }

  public ArrayList<Player> getPlayer() {
    return player;
  }

  public Player getPlayerFromColor(Color c) {
    switch (c) {
      case RED:
        return getPlayer().get(0);
      case BLUE:
        return getPlayer().get(1);
      case GREEN:
        return getPlayer().get(2);
      case YELLOW:
        return getPlayer().get(3);
      default:
        try {
          throw new Exception("Wrong Player Color");
        } catch (Exception e) {
          e.printStackTrace();
        }
        return null;
    }
  }

  public Color getColorFromPlayer(Player p) {
    return Color.values()[getPlayer().indexOf(p) + 1];
  }

  public void addPlayer(Player p) {
    this.player.add(p);
    ArrayList<PolySquare> polyOfPlayer = new ArrayList<>();
    for (boolean[][] shape : PolySquare.shapeList) {
      polyOfPlayer.add(new PolySquare(shape, getColorFromPlayer(p)));
    }
    remainingPolys.add(polyOfPlayer);
  }

  public void incTurn() {
    this.turn = turn + 1;
    this.round = turn / player.size() + 1;
  }

  public int getRound() {
    return round;
  }

  public boolean isFirstRound() {
    return (round == 1);
  }

  public boolean isStateRunning() {
    return running;
  }

  public void setStateRunning(boolean running) {
    this.running = running;
    if (running) {
      started = true;
    }
  }

  public String getStateEnding() {
    return stateEnding;
  }

  public void setStateEnding(String stateEnding) {
    this.setStateRunning(false);
    this.stateEnding = stateEnding;
  }

  public boolean isStarted() {
    return started;
  }

}

