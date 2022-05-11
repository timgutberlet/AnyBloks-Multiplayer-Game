package game.model;

import game.model.board.Board;
import game.model.board.BoardSquare;
import game.model.gamemodes.GameMode;
import game.model.player.Player;
import java.util.ArrayList;

/**
 * a game is started in a Session by the host and dies when someone wins
 *
 * @author tgeilen
 */
public class Game {

  private GameSession gameSession;
  private GameState gameState;
  private Board board;
  private GameMode gamemode;
  private ArrayList<Player> players;


  public Game(GameSession gameSession, GameMode gamemode) {
    this.gameSession = gameSession;
    this.board = new BoardSquare(gamemode);
    this.gamemode = gamemode;
    this.players = gameSession.getPlayerList();
    this.gameState = new GameState(this.gameSession, gamemode);

  }

  public Game(GameState gameState){

  }

  /**
   * checks if a move is valid or not
   * @author tgeilen
   */

  public Boolean checkTurn(Turn turn){
    //TODO this function needs to implemented @tilman
    return true;
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
        this.gameSession.increaseScore(currentPlayer, turn.getValue());
        currentPlayer.talk();
      }
    }
  }

  /**
   * function used by the server to make a turn
   * @author tgeilen
   */
  public void makeMove(Turn turn){
    if (this.gameState.isStateRunning()){
      this.gameState.playTurn(turn);
    }
  }

  public Player getCurrentPlayer(){
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

  public void updateGameState(GameState gameState){
    this.gameState = gameState;
  }

  public void startGame() {
    System.out.println("[GAMECONSOLE] Game started");
    this.gameState.setStateRunning(true);
    //this.run();
  }
}
