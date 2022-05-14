package game.model;

import game.model.board.Board;
import game.model.board.BoardSquare;
import game.model.gamemodes.GameMode;
import game.model.player.Player;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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

	private Boolean isServer = false;


	public Game(GameSession gameSession, GameMode gamemode) {
		this.gameSession = gameSession;
		this.board = new BoardSquare(gamemode);
		this.gamemode = gamemode;
		this.players = gameSession.getPlayerList();
		this.gameState = new GameState(this.gameSession, gamemode);

		Debug.printMessage(this, "Game at client created");

	}

	public Game(GameSession gameSession, GameMode gamemode, Boolean isServer) {
		this.gameSession = gameSession;
		this.board = new BoardSquare(gamemode);
		this.gamemode = gamemode;
		this.players = gameSession.getPlayerList();
		this.gameState = new GameState(this.gameSession, gamemode);
		this.isServer = isServer;
		Debug.printMessage(this, "Game on server created");

	}

	public Game(GameState gameState) {

	}

	/**
	 * checks if a move is valid or not
	 *
	 * @author tgeilen
	 */

	public Boolean checkTurn(Turn turn) {
		//TODO this function needs to implemented @tilman
		return true;
	}


	/**
	 * OLD DO NOT USE ANYMORE!! //TODO delete when all usages are changed function that calls the next
	 * move to be made
	 *
	 * @author tgeilen
	 */

	public void makeMove() {
		if (this.gameState.isStateRunning()) {

			Player currentPlayer = this.gameState.getPlayerCurrent();
			Debug.printMessage("[GAMECONSOLE] " + currentPlayer.getUsername() + " is now the active player");

			Turn turn = currentPlayer.makeTurn(this.gameState);
			if (this.gameState.playTurn(turn)) {
				this.gameSession.increaseScore(currentPlayer, turn.getValue());
				currentPlayer.talk();
			}
		}
	}

	/**
	 * function used by the server to make a turn either call s the next player to make a move or
	 * broadcasts the winer to all clients
	 *
	 * @author tgeilen
	 */
	public void makeMove(Turn turn) {
		//Debug.printMessage(this,this.board.toString());
		Player currentPlayer = this.gameState.getPlayerCurrent();
		if (this.gameState.isStateRunning()) {

			this.gameState.playTurn(turn);
			Debug.printMessage(this, "The turn has been played");

			this.gameSession.getOutboundServerHandler().broadcastGameUpdate();
			Debug.printMessage(this, "All players have been informed about the made turn");
		}

		if (!this.gameState.checkEnd()) {
			Debug.printMessage(this, "The Game is still running and the next player will be"
					+ "asked to make a turn");

			Player nextPlayer = this.gameState.getPlayerCurrent();
			//Debug.printMessage(this, "Sending a GameUpdate to all players");
			//this.gameSession.getOutboundServerHandler().broadcastGameUpdate();
			Debug.printMessage(this, "Requesting " + nextPlayer.getUsername() + " to make a turn");

			this.gameSession.getOutboundServerHandler().requestTurn(nextPlayer.getUsername());
		} else {
			this.getGameState().setStateEnding("true");
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

	/**
	 * starts a new game and calls the first player to make a move
	 *
	 * @author tgeilen
	 */
	public void startGame() {
		Player firstPlayer = null;
		Debug.printMessage(this, "Game has been started");
		this.gameState.setStateRunning(true);



		if(this.isServer) {
			this.gameSession.outboundServerHandler.broadcastGameStart(this.gamemode);
			Debug.printMessage(this, "Game has been started on server");
			if (this.gameState == null) {
				Debug.printMessage(this, "EMPTY GAMESTATE AAAAAA");
			} else {
				Debug.printMessage(this, "AAAAA GAMESTATE NOT NULL");
			}
			firstPlayer = this.gameState.getPlayerCurrent();
			Debug.printMessage(this, "Name of first player :"+firstPlayer.getUsername());
			try {
				TimeUnit.SECONDS.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.gameSession.getOutboundServerHandler().requestTurn(firstPlayer.getUsername());
		}
		//this.makeMove();
		//this.run();
	}
}
