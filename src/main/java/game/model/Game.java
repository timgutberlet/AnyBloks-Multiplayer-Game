package game.model;

import game.model.board.Board;
import game.model.board.BoardSquare;
import game.model.gamemodes.GameMode;
import game.model.player.Player;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * a game is started in a Session by the host and dies when someone wins
 *
 * @author tgeilen
 */
public class Game {

  private final HashMap<String, Integer> passedTurns = new HashMap<String, Integer>();
  private GameSession gameSession;
  private GameState gameState;
  private Board board;
  private GameMode gamemode;
  private ArrayList<Player> players;
  private Boolean isServer = false;


  public Game(GameSession gameSession, GameMode gamemode) {
    this.gameSession = gameSession;
    this.board = new BoardSquare(gamemode);
    this.gamemode = gamemode;
    this.players = gameSession.getPlayerList();
    this.gameState = new GameState(this.gameSession, gamemode);

    initPassedTurns();

    Debug.printMessage(this, "Game at client created");

  }

  public Game(GameSession gameSession, GameMode gamemode, Boolean isServer) {
    this.gameSession = gameSession;
    this.board = new BoardSquare(gamemode);
    this.gamemode = gamemode;
    this.players = gameSession.getPlayerList();
    this.gameState = new GameState(this.gameSession, gamemode);
    this.isServer = isServer;

    initPassedTurns();

    Debug.printMessage(this, "Game on server created");

  }

  public Game(GameState gameState) {

  }





  /**
   * function used by the server to make a turn either call s the next player to make a move or
   * broadcasts the winer to all clients
   *
   * @author tgeilen
   */
  public void makeMoveServer(Turn turn) {
    //Debug.printMessage(this,this.board.toString());
    Player currentPlayer = this.gameState.getPlayerCurrent();


					this.gameState.playTurn(turn);
					Debug.printMessage(this, "The turn has been played");

					if(!gameState.checkEnd()) {
						Debug.printMessage(this, "All players have been informed about the made turn");
						this.gameSession.getOutboundServerHandler().broadcastGameUpdate();
						Player nextPlayer = this.gameState.getPlayerCurrent();
						this.gameSession.getOutboundServerHandler().requestTurn(nextPlayer.getUsername());
						this.gameState.setStateEnding("true");

					} else {

					Debug.printMessage(this, "The game is over and all players will be informed");
					this.gameSession.getOutboundServerHandler().broadcastGameWin(currentPlayer.getUsername());
				}

	}

  public Player getCurrentPlayer() {
    return this.gameState.getPlayerCurrent();
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

  public void updateGameState(GameState gameState) {
    this.gameState = gameState;
  }

  public HashMap<String, Integer> getPassedTurns() {
    return passedTurns;
  }

  public void initPassedTurns() {
    for (Player p : this.gameState.getPlayerList()) {
      this.passedTurns.put(p.getUsername(), 0);
    }
  }

  public void increasePassedTurns(String username) {

    if (this.passedTurns.get(username) != null) {
      this.passedTurns.put(username, this.passedTurns.get(username) + 1);
    } else {
      this.passedTurns.put(username, 1);
    }
  }

  public void resetPassedTurns(String username) {
    this.passedTurns.put(username, 0);
  }

  public boolean checkPassedTurns() {
    boolean result = false;
    for (Player p : this.gameState.getPlayerList()) {
      if (this.passedTurns.get(p.getUsername()) >= 3) {
        return true;
      }
    }
    return false;

  }

  /**
   * starts a new game and calls the first player to make a move
   *
   * @author tgeilen
   */
  public void startGame() {
    Player firstPlayer = null;
    Debug.printMessage(this, "Game has been started");
    this.gameState.setStateRunning(true);

    if (this.isServer) {
      Debug.printMessage(this, "SERVER Round: " + this.gameState.getRound());
      this.gameSession.outboundServerHandler.broadcastGameStart(this.gameState);
      Debug.printMessage(this, "Game has been started on server");
      if (this.gameState == null) {
        Debug.printMessage(this, "EMPTY GAMESTATE AAAAAA");
      } else {
        Debug.printMessage(this, "AAAAA GAMESTATE NOT NULL");
      }
      firstPlayer = this.gameState.getPlayerCurrent();
      Debug.printMessage(this, "Name of first player :" + firstPlayer.getUsername());
      try {
        TimeUnit.SECONDS.sleep(5);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      this.gameSession.getOutboundServerHandler().requestTurn(firstPlayer.getUsername());
    }
    //this.makeMove();
    //this.run();
  }
}
