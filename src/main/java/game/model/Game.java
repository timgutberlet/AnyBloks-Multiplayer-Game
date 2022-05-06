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
  private GameState gameState;
  private Board board;
  private GameMode gamemode;
  private ArrayList<Player> players;


  public Game(Session session, GameMode gamemode) {
    this.session = session;
    this.board = new BoardSquare(gamemode);
    this.gamemode = gamemode;
    this.players = session.getPlayerList();
    this.gameState = new GameState(this.session, gamemode);

  }


  /**
   * function that calls the next move to be made
   * @author tgeilen
   */

  public void makeMove(){

    if (this.gameState.isStateRunning()){

        Player currentPlayer = this.gameState.getPlayerCurrent();
        Debug.printMessage("[GAMECONSOLE] " + currentPlayer.getName() + " is now the active player");

        Turn turn = currentPlayer.makeTurn(this.gameState);
        if(this.gameState.playTurn(turn)) {
        this.session.increaseScore(currentPlayer, turn.getValue());
        currentPlayer.talk();
      }
    }

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
    System.out.println("AAAAA Message Thread started");
    this.gameState.setStateRunning(true);
    //this.run();
  }
}
