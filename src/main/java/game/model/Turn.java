package game.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import game.model.polygon.Poly;
import game.model.polygon.PolySquare;
import game.model.polygon.PolyTrigon;

/**
 * this class represents a move of a player in one turn.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Turn {

  /**
   * represents the poly.
   */
  public Poly poly;

  /**
   * represents the move in the form {column, row, rotation, mirrored}.
   */
  public int[] turn = new int[6];

  /**
   * represents the number of squares, which could be the start point for other players, which could
   * be blocked by this poly and turn.
   */
  int numberBlockedFields = 0;

  /**
   * represents the number of squares, which could be the start point for other players, which could
   * be blocked by this poly and turn multiplied with the size of the poly, which could be placed
   * there.
   */
  int numberBlockedFieldsWeighted = 0;

  /**
   * represents the number of new rows or columns, which can be discovered by this turn.
   */
  int roomDiscovery = 0;

  /**
   * initializing the values.
   *
   * @param poly represents the poly
   * @param turn represents the move in form an integer array {column, row, rotation, mirrored}
   * @author tiotto
   */
  public Turn(Poly poly, int[] turn) {

    //solving a major problem with jackson
    //DO NOT CHANGE!!! pls
    int[] turn6 = new int[6];
    for (int i = 0; i < turn.length; i++) {
      turn6[i] = turn[i];
    }
    for (int i = turn.length; i < turn6.length; i++) {
      turn6[i] = 0;
    }
    this.poly = poly;
    this.turn = turn6;
  }

  /**
   * empty constructor for jackson.
   */
  public Turn() {

  }

  public int getValue() {
    return this.poly.getSize();
  }

  public PolySquare getPolySquare() {
    if (poly.getPolyType().equals("Square")) {
      return (PolySquare) poly;
    } else {
      return null;
    }
  }

  public PolyTrigon getPolyTrigon() {
    if (poly.getPolyType().equals("Trigon")) {
      return (PolyTrigon) poly;
    } else {
      return null;
    }
  }

  public Poly getPoly() {
    return poly;
  }

  /**
   * setter.
   *
   * @param poly Polygon
   */
  public void setPoly(Poly poly) {
    this.poly = poly;
  }

  public Color getColor() {
    return poly.getColor();
  }

  public int[] getTurn() {
    return this.turn;
  }

  /**
   * setter.
   *
   * @param turn Turn
   */
  public void setTurn(int[] turn) {
    this.turn = turn;
  }

  public int getNumberBlockedFields() {
    return numberBlockedFields;
  }

  public void setNumberBlockedFields(int numberBlockedFields) {
    this.numberBlockedFields = numberBlockedFields;
  }

  public int getNumberBlockedFieldsWeighted() {
    return numberBlockedFieldsWeighted;
  }

  public void setNumberBlockedFieldsWeighted(int numberBlockedFieldsWeighted) {
    this.numberBlockedFieldsWeighted = numberBlockedFieldsWeighted;
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

  public void setIsRight(int i) {
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

  /**
   * converts to board to a string.
   *
   * @return the string representation of the board
   */
  @Override
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

  /**
   * method to deep clone a board.
   *
   * @return returns the deep clone of the board
   */
  @Override
  public Turn clone() {
    return new Turn(this.poly.clone(), turn);
  }

  /**
   * converts the board into code, which creates the board.
   *
   * @return string containing the creating code
   */
  public String toCode() {
    StringBuffer res = new StringBuffer(poly.toCode());
    res.append(
        "Turn t = new Turn(p, new int[] {" + getX() + "," + getY() + "," + getIsRight() + "});\n");
    return res.toString();
  }
}
