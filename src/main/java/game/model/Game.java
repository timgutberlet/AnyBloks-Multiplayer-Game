package game.model;

import java.util.ArrayList;
import game.model.gamemodes.*;

/**
 * a game is started in a Session by the host and dies when someone wins
 * @author tgeilen
 * @Date 21.03.22
 */
public class Game {
	private Board board;
	private GameMode gamemode;
	private ArrayList<Player> players;
	private GameState gameState;

	public Game(ArrayList<Player> players, GameMode gamemode){
		this.board = new BoardSquare(gamemode);
		this.gamemode = gamemode;
		this.players = players;
		this.gameState = new GameState(gamemode);
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

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public GameState getGameState() {
		return gameState;
	}

	public void startGame(){
		gameState.setStateRunning(true);
	}
}
