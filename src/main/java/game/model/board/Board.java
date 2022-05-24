package game.model.board;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import game.model.Color;
import game.model.Turn;
import game.model.field.Field;
import game.model.polygon.Poly;
import java.util.ArrayList;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
    @JsonSubTypes.Type(value = BoardSquare.class, name = "BoardSquare"),
    @JsonSubTypes.Type(value = BoardTrigon.class, name = "BoardTrigon"),}

)

/**
 * @author tiotto
 * @date 27.03.2022
 */
public abstract class Board {

  public int SIZE;

  /**
   * returns the size of the board.
   *
   * @return size of the board
   */
  public int getSize() {
    return SIZE;
  }

  /**
   * returns the field identified by the given coordinates.
   *
   * @param pos given coordinates
   * @return the specific field
   */
  public abstract Field getField(int[] pos);

  /**
   * returns the color of a field identified by the given coordinates.
   *
   * @param pos given coordinates
   * @return the specific field
   */
  public abstract Color getColor(int[] pos);

  /**
   * checks if a field identified by the given coordinates is part of the board.
   *
   * @param pos given coordinates
   * @return boolean, if the field is part of the board
   */
  public abstract boolean isOnTheBoard(int[] pos);

  /**
   * checks if a field identified by the given coordinates has the given color as a direct neighbor,
   * means with a shared edge.
   *
   * @param pos given coordinates
   * @param c   given color
   * @return boolean, if one of the direct neighbor fields has this color
   */
  public abstract boolean isColorDirectNeighbor(int[] pos, Color c);

  /**
   * checks if a field identified by the given coordinates has the given color as a indirect
   * neighbor, means with a shared corner.
   *
   * @param pos given coordinates
   * @param c   given color
   * @return boolean, if one of the indirect neighbor fields has this color
   */
  public abstract boolean isColorIndirectNeighbor(int[] pos, Color c);

  /**
   * checks if the given poly is possible at the given position on the field.
   *
   * @param pos          given position (coordinates)
   * @param poly         given poly
   * @param isFirstRound if it is the first round
   * @return boolean, if the poly is possible at the position
   */
  public abstract boolean isPolyPossible(int[] pos, Poly poly, boolean isFirstRound);

  /**
   * gets the coordinates of all fields, where the color has indirect neighbors, but no direct
   * neighbors, means where the color can place a new poly.
   *
   * @param color        given color
   * @param isFirstRound if it is the first round
   * @return List of coordinates, where the color can place a new poly
   */
  public abstract ArrayList<int[]> getPossibleFields(Color color, boolean isFirstRound);

  /**
   * gets the coordinates of all fields, where the given poly can be placed (the field with the
   * indirect color neighbor).
   *
   * @param poly         given poly
   * @param isFirstRound if it is the first round
   * @return list of coordinates, where the poly can be placed
   */
  public abstract ArrayList<int[]> getPossibleFieldsForPoly(Poly poly, boolean isFirstRound);

  /**
   * gets a list of moves, that are possible with the given remaining polys.
   *
   * @param remainingPolys given list of remaining polys
   * @param isFirstRound   if it is the first round
   * @return list of turns, that can be played
   */
  public abstract ArrayList<Turn> getPossibleMoves(ArrayList<Poly> remainingPolys,
      boolean isFirstRound);

  /**
   * gets a list of moves, that are possible with the given poly at one position (considering
   * rotation and mirroring as well).
   *
   * @param pos          given position
   * @param poly         given poly
   * @param isFirstRound if it is the first round
   * @return list of turns, that are possible with that poly at the given position
   */
  public abstract ArrayList<Turn> getPolyShadesPossible(int[] pos, Poly poly, boolean isFirstRound);

  /**
   * this method gives back a list of the possible positions and the specific placement of possible
   * placements of a given polygon represented by a list of turns.
   *
   * @param poly         the given polygon
   * @param isFirstRound boolean, if it is the firstRound
   * @return list of turns with the specific poly
   */
  public abstract ArrayList<Turn> getPossibleFieldsAndShadesForPoly(Poly poly,
      boolean isFirstRound);

  /**
   * method to play a given turn.
   *
   * @param turn         given turn
   * @param isFirstRound if it is the first round
   * @return boolean, if the turn was executed successful
   */
  public abstract boolean playTurn(Turn turn, boolean isFirstRound);

  /**
   * method to deep clone a board.
   *
   * @return returns the deep clone of the board
   */
  public abstract Board clone();


  /**
   * converts to board to a string.
   *
   * @return the string representation of the board
   */
  @Override
  public String toString() {
    StringBuffer res = new StringBuffer();
    res.append("----------------------------------\n");
    for (Color c : Color.values()) {
      res.append("Score " + c.name() + ": " + getScoreOfColor(c) + "\n");
    }
    res.append("----------------------------------\n\n");
    return res.toString();
  }

  /**
   * converts the board into code, which creates the board.
   *
   * @return string containing the creating code
   */
  public abstract String toCode();

  /**
   * measures the number fields, where opponents could place a poly in their next turns, which can
   * be occupied be the given turn. This number is stored in the turn object.
   *
   * @param turn given turn
   */
  public abstract void assignNumberBlockedFields(Turn turn);

  /**
   * evaluates the current score for a given color.
   *
   * @param c given color
   * @return score as int
   */
  public abstract int getScoreOfColor(Color c);

  /**
   * evaluates the current score for a given color reduced by the score of the other colors.
   *
   * @param c given color
   * @return score as int
   */
  public abstract int getScoreOfColorMiniMax(Color c);

  /**
   * evaluates the width, that is occupied by the color.
   *
   * @param c given color
   * @return width as int
   */
  public abstract int occupiedWidth(Color c);

  /**
   * evaluates the height, that is occupied by the color.
   *
   * @param c given color
   * @return height as int
   */
  public abstract int occupiedHeight(Color c);

}



