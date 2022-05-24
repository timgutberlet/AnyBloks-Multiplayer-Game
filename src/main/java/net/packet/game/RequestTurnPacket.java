package net.packet.game;

import game.model.Debug;
import game.model.GameState;
import game.model.Turn;
import game.model.board.Board;
import game.model.gamemodes.GameMode;
import game.model.player.Player;
import game.model.polygon.Poly;
import java.util.ArrayList;
import net.packet.abstr.Packet;

/**
 * packet to tell a player that a turn needs to be made.
 *
 * @author tgeilen
 * @Date 10.05.22
 */
public class RequestTurnPacket extends Packet {

  public GameMode gameMode;
  public Board board;
  public ArrayList<ArrayList<Poly>> remainingPolys;
  public ArrayList<Player> playerList;
  public int round;
  public int turn;
  public ArrayList<ArrayList<Turn>> history;
  public boolean running;
  public boolean started;
  public String stateEnding;
  String username;


  /**
   * empty constructor for jackson.
   *
   * @author tgeilen
   */
  public RequestTurnPacket() {
    this.username = null;

    this.gameMode = null;
    this.board = null;
    this.remainingPolys = null;
    this.playerList = null;

    this.history = null;

    this.stateEnding = null;


  }

  /**
   * contructor for packet
   *
   * @param username
   * @param gameState
   * @author tgeilen
   */
  public RequestTurnPacket(String username, GameState gameState) {
    this.username = username;

    this.gameMode = gameState.getGameMode();
    this.board = gameState.getBoard();
    this.remainingPolys = gameState.getRemainingPolys();
    this.playerList = gameState.getPlayerList();
    this.round = gameState.getRound();
    this.turn = gameState.getTurn();
    this.history = gameState.getHistory();
    this.running = gameState.isRunning();
    this.started = gameState.isStarted();
    this.stateEnding = gameState.getStateEnding();

  }

  /**
   * gets the game state from the attributes.
   *
   * @return game state
   */
  public GameState getGameState() {

    Debug.printMessage(this, "Length of received remaining polys " + this.remainingPolys.size());

    ArrayList<ArrayList<Poly>> remPoly = this.remainingPolys;

    return new GameState(
        this.gameMode,
        this.board,
        remPoly,
        this.playerList,
        this.round,
        this.turn,
        this.running,
        this.started,
        this.stateEnding,
        this.history);
  }

  public String getUsername() {
    return username;
  }
}
