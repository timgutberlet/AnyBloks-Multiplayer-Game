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
 * Packets that gets send to client to tell a game has started.
 *
 * @author tgeilen
 * @Date 10.05.22
 */
public class GameStartPacket extends Packet {

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


  public GameStartPacket() {
    this.gameMode = null;
  }

  /**
   * Initializes the GameStartPacket with the corresponding game state.
   *
   * @param gameState gameState
   */
  public GameStartPacket(GameState gameState) {
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

  public GameMode getGameMode() {
    return gameMode;
  }

  /**
   * gets the game state from the attributes.
   *
   * @return game state
   */
  public GameState getGameState() {

    ArrayList<Player> players;
    players   = this.playerList;

    ArrayList<ArrayList<Poly>> remPoly = this.remainingPolys;
    ArrayList<ArrayList<Turn>> hist = new ArrayList<ArrayList<Turn>>();
    for (ArrayList<Turn> turns : this.history) {
      hist.add(turns);
    }

    Debug.printMessage(this, this.gameMode.getName());
    //Debug.printMessage(this,this.board.toString());
    Debug.printMessage(this, "Rem poly size: " + remPoly.size());
    Debug.printMessage(this, "Playerlist: " + players.toString());
    Debug.printMessage(this, "Round: " + this.round);
    Debug.printMessage(this, "Turn: " + this.turn);
    Debug.printMessage(this, "Running: " + (this.running ? "true" : "false"));
    Debug.printMessage(this, "Started: " + this.started);
    Debug.printMessage(this, "StateEnding: " + this.stateEnding);
    Debug.printMessage(this, "Hist size" + this.history.size());

    return new GameState(
        this.gameMode,
        this.board,
        remPoly,
        players,
        this.round,
        this.turn,
        this.running,
        this.started,
        this.stateEnding,
        hist);
  }
}
