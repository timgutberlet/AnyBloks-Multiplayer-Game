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
  private final ArrayList<ArrayList<Poly>> remainingPolys = new ArrayList<>();
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
    board = new BoardSquare(gameMode);
    round = 1;
    turn = 0;
    player = new ArrayList<>();
    running = false;
    started = false;
  }

  /**
   * Constructor to copy a GameState
   *
   * @param gameMode
   * @param board
   * @param remainingPolys
   * @param player
   * @param round
   * @param turn
   * @param running
   * @param started
   * @param stateEnding
   */
  public GameState(GameMode gameMode, Board board, ArrayList<ArrayList<Poly>> remainingPolys,
      ArrayList<Player> player, int round, int turn, boolean running, boolean started,
      String stateEnding) {
    this.gameMode = gameMode;
    this.board = board;
    for (ArrayList<Poly> polys : remainingPolys) {
      ArrayList<Poly> help = new ArrayList<>();
      for (Poly poly : polys) {
        help.add(poly.clone());
      }
      this.remainingPolys.add(help);
    }
    this.player = player;
    this.round = round;
    this.turn = turn;
    this.running = running;
    this.started = started;
    this.stateEnding = stateEnding;
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
  public ArrayList<Poly> getRemainingPolys(Player p) {

    return remainingPolys.get(player.indexOf(p));
  }

  public Player getPlayerCurrent() {
    if (getPlayer().size() > 0) {
      return getPlayer().get(this.turn);
    } else {
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
    ArrayList<Poly> polyOfPlayer = new ArrayList<>();
    for (ArrayList<FieldSquare> shape : PolySquare.shapeList) {
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

  public boolean checkEnd(Turn turn) {
    for (Player p : player) {
      Debug.printMessage(
          board.getPossibleMoves(remainingPolys.get(player.indexOf(p)), isFirstRound()).size()
              + " möglichkeiten Steine zu legen");
      //Debug.printMessage(gameState.getRemainingPolys(p).toString() + "Size: " + gameState.getRemainingPolys(p).size());
      if (getRemainingPolys(p).size() == 0 || (
          board.getPossibleMoves(remainingPolys.get(player.indexOf(p)), isFirstRound()).size() == 0
              && getRound() > 1)) {
        setStateEnding("Spiel Vorbei");
        setStateRunning(false);
        Debug.printMessage("Spiel Vorbei");
        Debug.printMessage(p.toString());
        return true;
      }
    }
    return false;
  }

  public boolean playTurn(Turn turn) {
    if (checkEnd(turn)) {
      return false;
    }
    boolean res = board.playTurn(turn, isFirstRound()); //play turn
    if (res) { // remove played poly from remaining polys
      for (Poly p : getRemainingPolys(getPlayerFromColor(turn.getColor()))) {
        if (p.equals(turn.getPoly())) {
          getRemainingPolys(getPlayerFromColor(turn.getColor())).remove(p);
          break;
        }
      }
    }
    incTurn();
    return res;
  }

  public GameState tryTurn(Turn turn) {
    Board help = this.board.clone();
    help.playTurn(turn, this.isFirstRound());
    return new GameState(this.gameMode, help, remainingPolys, player, round, this.turn, running,
        started, stateEnding);
  }

  public Color getNextColor(Color c) {
    switch (c) {
      case RED:
        return Color.BLUE;
      case BLUE:
        return Color.RED;
      default:
        return Color.BLUE;
    }
  }

}

