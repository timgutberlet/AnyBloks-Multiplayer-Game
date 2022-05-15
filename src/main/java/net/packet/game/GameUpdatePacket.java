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
 * Packet to send GameState and allow Players to determine they have to make a move
 *
 * @author tbuscher
 */
public class GameUpdatePacket extends Packet {


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

  /**
   * empty constructor for jackson
   */

  public GameUpdatePacket() {
    this.gameMode = null;
    this.board = null;
    this.remainingPolys = null;
    this.playerList = null;

    this.history = null;

    this.stateEnding = null;

  }

  /**
   * Constructor
   *
   * @param gameState gs
   */
  public GameUpdatePacket(GameState gameState) {

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
   * Getter
   */


  /**
   * Getter
   */
  public GameState getGameState() {

    ArrayList<ArrayList<Poly>> remPoly = this.remainingPolys;
    ArrayList<ArrayList<Turn>> hist = new ArrayList<ArrayList<Turn>>();
    for(ArrayList<Turn> turns : this.history){
      hist.add(turns);
    }

    int rnd = this.round;

    Debug.printMessage(this,this.gameMode.toString());
    //Debug.printMessage(this,this.board.toString());
    Debug.printMessage(this, "Rem poly size: " +remPoly.size());
    Debug.printMessage(this,"Playerlist: " + this.playerList.toString());
    Debug.printMessage(this, "Round: "+ rnd);
    Debug.printMessage(this, "Turn: "+ this.turn);
    Debug.printMessage(this, "Running: " + (this.running?"true":"false"));
    Debug.printMessage(this, "Started: " + this.started);
    Debug.printMessage(this, "StateEnding: "+ this.stateEnding);
    Debug.printMessage(this, "Hist size" + this.history.size());

    return new GameState(
        this.gameMode,
        this.board,
        remPoly,
        this.playerList,
        rnd,
        this.turn,
        this.running,
        this.started,
        this.stateEnding,
        hist);
  }


}
