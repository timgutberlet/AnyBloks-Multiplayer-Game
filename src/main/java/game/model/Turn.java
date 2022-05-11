package game.model;

import game.model.polygon.Poly;
import game.model.polygon.PolySquare;
import game.model.polygon.PolyTrigon;

/**
 * this class represents a move of a player in one turn
 */

public class Turn {

	/**
	 * represents the poly
	 */
	Poly poly;

	/**
	 * represents the move in the form {column, row, rotation, mirrored}
	 */
	int[] turn;

	/**
	 * represents the number of squares, which could be the start point for other players, which could
	 * be blocked by this poly and turn
	 */
	int numberBlockedSquares = 0;

	/**
	 * represents the number of new rows or columns, which can be discovered by this turn
	 */
	int roomDiscovery = 0;

	/**
	 * initializing the values
	 *
	 * @param poly represents the poly
	 * @param turn represents the move in form an integer array {column, row, rotation, mirrored}
	 * @author tiotto
	 */
	public Turn(Poly poly, int[] turn) {
		this.poly = poly;
		this.turn = turn;
	}

	public int getValue() {
		return this.poly.getSize();
	}

	public PolySquare getPolySquare() {
		return (PolySquare) poly;
	}

	public PolyTrigon getPolyTrigon() {
		return (PolyTrigon) poly;
	}

	public Poly getPoly() {
		return poly;
	}

	public Color getColor() {
		return poly.getColor();
	}

	public int[] getTurn() {
		return this.turn;
	}

	public int getNumberBlockedSquares() {
		return numberBlockedSquares;
	}

	public void setNumberBlockedSquares(int numberBlockedSquares) {
		this.numberBlockedSquares = numberBlockedSquares;
	}

	public int getColumn() {
		return turn[0];
	}

	public int getX() {
		return turn[0];
	}

	public int getRow() {
		return turn[1];
	}

	public int getY() {
		return turn[1];
	}

	public int getIsRight() {
		return turn[2];
	}

	public int getRoomDiscovery() {
		return roomDiscovery;
	}

	public void setRoomDiscovery(int roomDiscovery) {
		this.roomDiscovery = roomDiscovery;
	}

	public int getRotation() {
		return turn[2];
	}

	public boolean getMirrored() {
		return turn[3] == 1;
	}

	public String toString() {
		StringBuffer res = new StringBuffer(poly.toString());
		if (poly.getClass().getName().equals("game.model.polygon.PolyTrigon")) {
			res.append("\n" + "x : " + getX());
			res.append("\n" + "y : " + getY());
			res.append("\n" + "isRight : " + getIsRight());
		} else {
			res.append("\n" + "column : " + getColumn());
			res.append("\n" + "row : " + getRow());
		}
		return res.toString();

	}

	public Turn clone() {
		return new Turn(this.poly.clone(), turn);
	}

}
