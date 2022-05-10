package game.model.board;

import game.model.Color;
import game.model.field.Field;
import game.model.polygon.Poly;
import game.model.Turn;
import game.view.InGameView;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


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

  public int getSize() {
    return SIZE;
  }

  //public abstract ArrayList<Field> getBoard();

  public abstract Field getField(int[] pos);

  public abstract Color getColor(int[] pos);

  public abstract boolean isOnTheBoard(int[] pos);

  public abstract javafx.scene.paint.Color getJavaColor(int[] pos);

  public abstract boolean isColorDirectNeighbor(int[] pos, Color c);

  public abstract boolean isColorIndirectNeighbor(int[] pos, Color c);

  public abstract boolean isPolyPossible(int[] pos, Poly poly, boolean isFirstRound);


  public abstract ArrayList<int[]> getPossibleFields(Color color, boolean isFirstRound);

  public abstract ArrayList<Turn> getPossibleMoves(ArrayList<Poly> remainingPolys,
      boolean isFirstRound);

  public abstract ArrayList<Turn> getPolyShadesPossible(int[] pos, Poly poly, boolean isFirstRound);

  public abstract ArrayList<Turn> getPossibleFieldsAndShadesForPoly(Poly poly,
      boolean isFirstRound);

  public abstract boolean playTurn(Turn turn, boolean isFirstRound);

  public abstract Board clone();

  public abstract void updateBoard(InGameView view);

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

  //AI Stuff
  public abstract void assignNumberBlockedFields(Turn turn);

  public abstract int getScoreOfColor(Color c);

  public abstract int getScoreOfColorMiniMax(Color c);
}



