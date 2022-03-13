package Game;

import java.util.ArrayList;

/**
 * @author tgeilen
 * @Date 13.03.22
 */
public class Game {
	private Board board;
	private String rulebook;
	private ArrayList<Player> players;

	public Game(ArrayList<Player> players){
		this.board = new Board(1,1);
		this.rulebook = "TODO";
		this.players = players;
	}


	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public String getRulebook() {
		return rulebook;
	}

	public void setRulebook(String rulebook) {
		this.rulebook = rulebook;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

}
