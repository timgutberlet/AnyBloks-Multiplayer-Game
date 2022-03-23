package game.model;

import game.model.gamemodes.GameMode;
import game.view.InGameView;
import java.util.ArrayList;
import javax.swing.plaf.basic.BasicBorders.FieldBorder;

/**
 * @author tiotto
 * @date 21.03.2022
 */
public class BoardTrigon {

  private final ArrayList<FieldTrigon> board = new ArrayList<>();

  private final int SIZE = 18;

  public BoardTrigon() {

    for (int i = 0; i < 18; i++) {
      for (int j = 0; j < 18; j++) {
        if (i + j == 8) {
          board.add(new FieldTrigon(i, j, 1));
        }
        if (i + j == 17) {
          board.add(new FieldTrigon(i, j, 0));
        }
        if (i + j > 8 && i + j < 17) {
          board.add(new FieldTrigon(i, j, 0));
          board.add(new FieldTrigon(i, j, 1));
        }
      }
    }
  }

  public BoardTrigon(ArrayList<FieldTrigon> board) {
    for (FieldTrigon ft : board) {
      this.board.add(ft.clone());
    }
  }


  public Color getColor(int x, int y, int isRight) {
    if (isOnTheBoard(x, y, isRight)) {
      return getField(x, y, isRight).getColor();
    }
    return null;
  }

  public boolean isOnTheBoard(int x, int y, int isRight) {
    return x + y > 8 && x + y < SIZE || x + y == 8 && isRight == 1 || x + y == SIZE && isRight == 0;
  }

  public FieldTrigon getField(int x, int y, int isRight) {
    for (FieldTrigon ft : board) {
      if (ft.pos.equals(new int[]{x, y, isRight})) {
        return ft;
      }
    }
    return null;
  }

  public javafx.scene.paint.Color getJavaColor(int x, int y, int isRight) {
    return getField(x, y, isRight).getJavaColor();
  }

  public int getSize() {
    return SIZE;
  }

  public ArrayList<FieldTrigon> getBoard() {
    return board;
  }

  /**
   * Method updates the IngameView with the current colored Squares
   *
   * @param view current InGameView that is shown to the user
   * @author tgutberl
   */
    /*public void updateBoard (InGameView view){
      for (int i = 0; i < getSize(); i++) {
        for (int j = 0; j < getSize(); j++) {
          view.getBoardPane().setSquare(this.getJavaColor(i, j), i, j);
        }
      }
    }*/

  /**
   * Method that gives back, if a specific square on the board has a side by side neighbor square of
   * a specific color
   *
   * @param x       x value of the field.
   * @param y       y value of the field
   * @param isRight isRight value of the field
   * @param color   Color of the field
   * @return Boolean, if a direct neighbor of this color exists
   */
  public boolean isColorDirectNeighbor(int x, int y, int isRight, Color color) {
    int add = (isRight == 1 ? 1 : -1);
    //one side
    if (isOnTheBoard(x + add, y, 1 - isRight) && getColor(x + add, y, 1 - isRight).equals(color)) {
      return true;
    }
    //second side
    if (isOnTheBoard(x, y + add, 1 - isRight) && getColor(x, y + add, 1 - isRight).equals(color)) {
      return true;
    }
    //third side
    if (isOnTheBoard(x, y, 1 - isRight) && getColor(x, y, 1 - isRight).equals(color)) {
      return true;
    }
    return false;
  }

  /**
   * Method that gives back, if a specific square on the board has a neighbor over the edge of a
   * specific color
   *
   * @param x       x value of the field.
   * @param y       y value of the field
   * @param isRight isRight value of the field
   * @param color   Color of the field
   * @return Boolean, if a indirect neighbor of this color exists
   */
  public boolean isColorIndirectNeighbor(int x, int y, int isRight, Color color) {

    //neighbor of grad 2
    boolean hasNeighborOfGrad2Color = false;
    hasNeighborOfGrad2Color =
        hasNeighborOfGrad2Color || isOnTheBoard(x, y + 1, isRight) && getColor(x, y + 1,
            isRight).equals(color);
    hasNeighborOfGrad2Color =
        hasNeighborOfGrad2Color || isOnTheBoard(x + 1, y, isRight) && getColor(x + 1, y,
            isRight).equals(color);
    hasNeighborOfGrad2Color =
        hasNeighborOfGrad2Color || isOnTheBoard(x, y - 1, isRight) && getColor(x, y - 1,
            isRight).equals(color);
    hasNeighborOfGrad2Color =
        hasNeighborOfGrad2Color || isOnTheBoard(x - 1, y, isRight) && getColor(x - 1, y,
            isRight).equals(color);
    hasNeighborOfGrad2Color =
        hasNeighborOfGrad2Color || isOnTheBoard(x - 1, y + 1, isRight) && getColor(x - 1, y + 1,
            isRight).equals(color);
    hasNeighborOfGrad2Color =
        hasNeighborOfGrad2Color || isOnTheBoard(x + 1, y - 1, isRight) && getColor(x + 1, y - 1,
            isRight).equals(color);

    if (hasNeighborOfGrad2Color) {
      return true;
    }

    //neighbor of grad 3
    boolean hasNeighborOfGrad3Color = false;
    int add = (isRight == 1 ? 1 : -1);
    hasNeighborOfGrad3Color =
        hasNeighborOfGrad3Color || isOnTheBoard(x - 1, y + 1, 1 - isRight) && getColor(x - 1, y + 1,
            1 - isRight).equals(color);
    hasNeighborOfGrad3Color =
        hasNeighborOfGrad3Color || isOnTheBoard(x + 1, y - 1, 1 - isRight) && getColor(x + 1, y - 1,
            1 - isRight).equals(color);
    hasNeighborOfGrad3Color =
        hasNeighborOfGrad3Color || isOnTheBoard(x + add, y + add, 1 - isRight) && getColor(x + add,
            y + add, 1 - isRight).equals(color);

    return hasNeighborOfGrad3Color;
  }

  /**
   * Method that finds out if a placement of a poly at a specific position is legitimate
   *
   * @param x x value of the position
   * @param y y value of the position
   * @param isRight isRight value of the position
   * @param isFirstRound boolean, if it is the first Round
   * @param poly considered polygon
   * @return boolean, if a placement is legitimate or not at this position on the board
   */
  public boolean isPolyPossible(int x, int y, int isRight, PolyTrigon poly, boolean isFirstRound) {
    boolean indirectNeighbor = isFirstRound;
    int xRef = poly.shape.get(0).getPos()[0];
    int yRef = poly.shape.get(0).getPos()[1];
    if (poly.shape.get(0).getPos()[2] != isRight) {
      return false;
    }

    for (FieldTrigon ftPoly : poly.shape) {
      if (!isOnTheBoard(ftPoly.getPos()[0] + x - xRef, ftPoly.getPos()[1] + y - yRef,
          ftPoly.getPos()[2])) {
        return false;
      }

      if (getField(ftPoly.getPos()[0] + x - xRef, ftPoly.getPos()[1] + y - yRef,
          ftPoly.getPos()[2]).isOccupied()) {
        return false;
      }

      if (isColorDirectNeighbor(ftPoly.getPos()[0] + x - xRef, ftPoly.getPos()[1] + y - yRef,
          ftPoly.getPos()[2], poly.getColor())) {
        return false;
      }

      indirectNeighbor = indirectNeighbor || isColorIndirectNeighbor(ftPoly.getPos()[0] + x - xRef,
          ftPoly.getPos()[1] + y - yRef, ftPoly.getPos()[2], poly.getColor());

    }

    if (isFirstRound) { //toDo
    }
    return indirectNeighbor;
  }

  @Override
  public game.model.BoardTrigon clone() {
    return new game.model.BoardTrigon(this.board);
  }

  /**
   * this method gives back the list of the specific placements of a polygon for a given position
   *
   * @param x x value of the position
   * @param y y value of the position
   * @param isRight isRight value of the position
   * @param isFirstRound boolean, if it is the first Round
   * @param poly given polygon
   * @return list turns which contain the poly and a tuple out of integers: {row, column, rotation,
   * mirrored}
   */
  private ArrayList<Turn> getPolyShadesPossible(int x, int y, int isRight, PolyTrigon poly, boolean isFirstRound) {
    ArrayList<Turn> res = new ArrayList<>();
    for (Boolean mirrored : new boolean[]{false, true}) {
      for (int i = 0; i < 6; i++) {
        if (i == 0 && mirrored) {
          poly.mirror();
        }
        if (isPolyPossible(x, y, isRight, poly, isFirstRound)) {
          res.add(new Turn(poly.clone(), new int[]{x, y,isRight, i, (mirrored ? 1 : 0)}));
        }
        poly.rotateRight();
      }
    }
    return res;
  }

  /**
   * this method gives back a list of the possible positions and the specific placement of possible
   * placements of a given polygon represented by a list of turns
   *
   * @param poly the given polygon
   * @param isFirstRound boolean, if it is the firstRound
   * @return list of turns with the specific poly
   */
  private ArrayList<Turn> possibleSquaresAndShadesForPoly(PolyTrigon poly, boolean isFirstRound) {
    ArrayList<Turn> res = new ArrayList<>();
    for (FieldTrigon ft : board){
        ArrayList<Turn> erg = getPolyShadesPossible(ft.getPos()[0], ft.getPos()[1], ft.getPos()[2], poly, isFirstRound);
        if (erg.size() > 0) {
          res.addAll(erg);
        }
      }

    return res;
  }

  /**
   * Method that gives back a list of all possible moves of all remaining polygons of a player
   *
   * @param player player who can play the moves, which are searched for
   * @return returns a List with all the possible moves. This class contains position, rotation and
   * if the polygon has to be mirrored.
   */
  public ArrayList<Turn> getPossibleMoves(Player player, ArrayList<PolyTrigon> remainingPolys, boolean isFirstRound) {
    ArrayList<Turn> res = new ArrayList<>();
    for (PolyTrigon poly : remainingPolys) {
      ArrayList<Turn> movesWithPoly = possibleSquaresAndShadesForPoly(poly,isFirstRound);
      if (movesWithPoly.size() > 0) {
        res.addAll(movesWithPoly);
      }
    }
    return res;
  }

  /**
   * Method that gives back a list of all the possible squares, that are over the edge to already
   * placed polygons
   *
   * @param color searched color
   * @return Arraylist with doubles inside, which contain the row and the column of the squares
   */
  private ArrayList<FieldTrigon> possibleSquares(Color color, boolean isFirstRound) { //toDo FirstRound need to be added
    ArrayList<FieldTrigon> res = new ArrayList<>();
    for (FieldTrigon ft : board) {
      if (!isColorDirectNeighbor(ft.getPos()[0], ft.getPos()[1], ft.getPos()[2], color)
          && isColorIndirectNeighbor(ft.getPos()[0], ft.getPos()[1], ft.getPos()[2], color)) {
        res.add(ft);
      }
    }
    return res;
  }

  public boolean playTurn(Turn turn, boolean isFirstRound) {
    if (isPolyPossible(turn.getX(), turn.getY(), turn.getIsRight(), turn.getPolyTrigon(), isFirstRound)) {
      System.out.println("Poly possible");
      int xRef = turn.getPolyTrigon().shape.get(0).getPos()[0];
      int yRef = turn.getPolyTrigon().shape.get(0).getPos()[1];

      for(FieldTrigon ft : turn.getPolyTrigon().getShape()){
        getField(ft.getPos()[0] + turn.getX() - xRef, ft.getPos()[1] + turn.getY() - yRef,
            ft.getPos()[2]).setColor(turn.getPolyTrigon().getColor());
      } //delete Poly in Remaining Poly in calling method
      return true;
    }
    return false;
  }
}



