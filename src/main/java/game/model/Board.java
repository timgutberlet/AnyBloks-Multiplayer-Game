package game.model;

import game.view.InGameView;
import java.util.ArrayList;

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

  //AI Stuff
  public abstract void assignNumberBlockedFields(Turn turn);

  public abstract int getScoreOfColor(Color c);

  public abstract int getScoreOfColorMiniMax(Color c);
}



