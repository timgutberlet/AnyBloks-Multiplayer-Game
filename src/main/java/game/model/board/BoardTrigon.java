package game.model.board;

import game.model.Color;
import game.model.Debug;
import game.model.Turn;
import game.model.field.Field;
import game.model.field.FieldSquare;
import game.model.field.FieldTrigon;
import game.model.polygon.Poly;
import game.model.polygon.PolySquare;
import game.model.polygon.PolyTrigon;
import game.view.InGameView;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author tiotto
 */
public class BoardTrigon extends Board implements Serializable, Cloneable {

  private final ArrayList<FieldTrigon> board = new ArrayList<>();

  private final ArrayList<FieldTrigon> startFields = new ArrayList<>();

  public BoardTrigon() {
    for (int i = 0; i < 18; i++) {
      for (int j = 0; j < 18; j++) {
        if (i + j == 8) {
          board.add(new FieldTrigon(i, j, 1));
        }
        if (i + j == 26) {
          board.add(new FieldTrigon(i, j, 0));
        }
        if (i + j > 8 && i + j < 26) {
          board.add(new FieldTrigon(i, j, 0));
          board.add(new FieldTrigon(i, j, 1));
        }
      }
    }
    this.SIZE = 26;

    startFields.add(new FieldTrigon(6, 6, 0));
    startFields.add(new FieldTrigon(11, 3, 1));
    startFields.add(new FieldTrigon(14, 6, 0));
    startFields.add(new FieldTrigon(11, 11, 1));
    startFields.add(new FieldTrigon(6, 14, 0));
    startFields.add(new FieldTrigon(3, 11, 1));
  }

  public BoardTrigon(ArrayList<FieldTrigon> board, ArrayList<FieldTrigon> startFields) {
    for (FieldTrigon ft : board) {
      this.board.add(ft.clone());
    }
    for (FieldTrigon ft : startFields) {
      this.startFields.add(ft.clone());
    }
  }

  public ArrayList<FieldTrigon> getBoard() {
    return board;
  }

  @Override
  public Color getColor(int[] pos) {
    return getColor(pos[0], pos[1], pos[2]);
  }

  public Color getColor(int x, int y, int isRight) {
    if (isOnTheBoard(x, y, isRight)) {
      return getField(x, y, isRight).getColor();
    }
    return null;
  }

  @Override
  public boolean isOnTheBoard(int[] pos) {
    return isOnTheBoard(pos[0], pos[1], pos[2]);
  }

  public boolean isOnTheBoard(int x, int y, int isRight) {
    return x >= 0 && x < 18 && y >= 0 && y < 18 && (x + y > 8 && x + y < SIZE
        || x + y == 8 && isRight == 1 || x + y == SIZE && isRight == 0);
  }

  @Override
  public Field getField(int[] pos) {
    return getField(pos[0], pos[1], pos[2]);
  }

  public FieldTrigon getField(int x, int y, int isRight) {
    for (FieldTrigon ft : board) {
      if (ft.pos[0] == x && ft.pos[1] == y && ft.pos[2] == isRight) {
        return ft;
      }
    }
    Debug.printMessage("(" + x + ", " + y + ", " + isRight + ")");
    return null;
  }

  @Override
  public boolean isColorDirectNeighbor(int[] pos, Color c) {
    return isColorDirectNeighbor(pos[0], pos[1], pos[2], c);
  }

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
    return isOnTheBoard(x, y, 1 - isRight) && getColor(x, y, 1 - isRight).equals(color);
  }

  @Override
  public boolean isColorIndirectNeighbor(int[] pos, Color c) {
    return isColorIndirectNeighbor(pos[0], pos[1], pos[2], c);
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
            isRight).equals(color); //TODO Wieso Warnung, dass der Bool immer false sein sollte?
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
            1 - isRight).equals(color); //TODO Warum Warnung, dass Bool immer false sein sollte?
    hasNeighborOfGrad3Color =
        hasNeighborOfGrad3Color || isOnTheBoard(x + 1, y - 1, 1 - isRight) && getColor(x + 1, y - 1,
            1 - isRight).equals(color);
    hasNeighborOfGrad3Color =
        hasNeighborOfGrad3Color || isOnTheBoard(x + add, y + add, 1 - isRight) && getColor(x + add,
            y + add, 1 - isRight).equals(color);

    return hasNeighborOfGrad3Color;
  }


  @Override
  public boolean isPolyPossible(int[] pos, Poly poly, boolean isFirstRound) {
    return isPolyPossible(pos[0], pos[1], pos[2], (PolyTrigon) poly, isFirstRound);
  }

  /**
   * Method that finds out if a placement of a poly at a specific position is legitimate
   *
   * @param x            x value of the position
   * @param y            y value of the position
   * @param isRight      isRight value of the position
   * @param isFirstRound boolean, if it is the first Round
   * @param poly         considered polygon
   * @return boolean, if a placement is legitimate or not at this position on the board
   */
  public boolean isPolyPossible(int x, int y, int isRight, PolyTrigon poly, boolean isFirstRound) {
    boolean indirectNeighbor = false;
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

      if (isFirstRound) {
        for (FieldTrigon ft : startFields) {
          indirectNeighbor = indirectNeighbor || (ft.pos[0] == ftPoly.getPos()[0] + x - xRef
              && ft.pos[1] == ftPoly.getPos()[1] + y - yRef && ft.pos[2] == ftPoly.getPos()[2]);
        }
      } else {
        indirectNeighbor =
            indirectNeighbor || isColorIndirectNeighbor(ftPoly.getPos()[0] + x - xRef,
                ftPoly.getPos()[1] + y - yRef, ftPoly.getPos()[2], poly.getColor());
      }
    }
    return indirectNeighbor;
  }


  /**
   * Method that gives back a list of all the possible positions, that are over the edge to already
   * placed polygons
   *
   * @param color searched color
   * @return Arraylist with coordinates inside, which contain the position of the fields
   */
  @Override
  public ArrayList<int[]> getPossibleFields(Color color, boolean isFirstRound) {
    ArrayList<int[]> res = new ArrayList<>();
    for (FieldTrigon ft : board) {
      if (!isColorDirectNeighbor(ft.getPos()[0], ft.getPos()[1], ft.getPos()[2], color)
          && isColorIndirectNeighbor(ft.getPos()[0], ft.getPos()[1], ft.getPos()[2], color)) {
        res.add(ft.getPos());
      }
    }
    if (isFirstRound) {
      for (FieldTrigon ft : startFields) {
        if (!getField(ft.getPos()).isOccupied()) {
          res.add(ft.getPos());
        }
      }
    }
    return res;
  }

  @Override
  public ArrayList<int[]> getPossibleFieldsForPoly(Poly poly, boolean isFirstRound){
    ArrayList<int[]> res = new ArrayList<>();
    ArrayList<Poly> help = new ArrayList<>();
    help.add(poly);
    for (Turn t : getPossibleMoves(help,isFirstRound)){
      int xRef = ((PolyTrigon)poly).shape.get(0).getPos()[0];
      int yRef = ((PolyTrigon)poly).shape.get(0).getPos()[1];
      int x = t.getX();
      int y = t.getY();
      for (int[] pos : getPossibleFields(poly.getColor(), isFirstRound)){
        for (FieldSquare fs: ((PolySquare) t.getPoly()).getShape()){
          if (fs.getPos()[0] + x - xRef == pos[0] && fs.getPos()[1] + y - yRef == pos[1] && fs.getPos()[2] == pos[2]){
            res.add(pos);
          }
        }
      }
    }
    return res;
  }

  /**
   * Method that gives back a list of all possible moves of a list of remaining polygons
   *
   * @param remainingPolys list of remaining polys
   * @param isFirstRound   boolean, if it is the first round
   * @return returns a List with all the possible moves. This class contains position, rotation and
   * if the polygon has to be mirrored.
   */
  @Override
  public ArrayList<Turn> getPossibleMoves(ArrayList<Poly> remainingPolys, boolean isFirstRound) {
    ArrayList<Turn> res = new ArrayList<>();
    for (Poly poly : remainingPolys) {
      ArrayList<Turn> movesWithPoly = possibleFieldsAndShadesForPoly((PolyTrigon) poly,
          isFirstRound);
      res.addAll(movesWithPoly);
    }
    return res;
  }


  @Override
  public ArrayList<Turn> getPolyShadesPossible(int[] pos, Poly poly, boolean isFirstRound) {
    return getPolyShadesPossible(pos[0], pos[1], pos[2], (PolyTrigon) poly, isFirstRound);
  }

  /**
   * this method gives back the list of the specific placements of a polygon for a given position
   *
   * @param x            x value of the position
   * @param y            y value of the position
   * @param isRight      isRight value of the position
   * @param isFirstRound boolean, if it is the first Round
   * @param poly         given polygon
   * @return list turns which contain the poly and a tuple out of integers: {row, column, rotation,
   * mirrored}
   */
  private ArrayList<Turn> getPolyShadesPossible(int x, int y, int isRight, PolyTrigon poly,
      boolean isFirstRound) {
    ArrayList<Turn> res = new ArrayList<>();
    for (Boolean mirrored : new boolean[]{false, true}) {
      for (int i = 0; i < 6; i++) {
        if (i == 0 && mirrored) {
          poly.mirror();
        }
        if (isPolyPossible(x, y, isRight, poly, isFirstRound)) {
          res.add(new Turn(poly.clone(), new int[]{x, y, isRight, i, (mirrored ? 1 : 0)}));
        }
        poly.rotateRight();
      }
    }
    return res;
  }


  @Override
  public ArrayList<Turn> getPossibleFieldsAndShadesForPoly(Poly poly, boolean isFirstRound) {
    return possibleFieldsAndShadesForPoly((PolyTrigon) poly, isFirstRound);
  }

  /**
   * this method gives back a list of the possible positions and the specific placement of possible
   * placements of a given polygon represented by a list of turns
   *
   * @param poly         the given polygon
   * @param isFirstRound boolean, if it is the firstRound
   * @return list of turns with the specific poly
   */
  private ArrayList<Turn> possibleFieldsAndShadesForPoly(PolyTrigon poly, boolean isFirstRound) {
    ArrayList<Turn> res = new ArrayList<>();
    for (FieldTrigon ft : board) {
      if (ft.isOccupied()) {
        continue;
      }
      ArrayList<Turn> erg = getPolyShadesPossible(ft.getPos()[0], ft.getPos()[1], ft.getPos()[2],
          poly, isFirstRound);
      res.addAll(erg);

    }

    return res;
  }

  @Override
  public boolean playTurn(Turn turn, boolean isFirstRound) {
    if (turn == null) {
      return false;
    }
    if (isPolyPossible(turn.getX(), turn.getY(), turn.getIsRight(), turn.getPolyTrigon(),
        isFirstRound)) {
      Debug.printMessage("Poly size: " + turn.getPoly().getSize());
      if (turn.getPoly().getSize() > 6) {
        Debug.printMessage(
            "!!!!!!!!!!!!!\n!!!!!!!!!!!!!!!!!!\n!!!!!!!!!!!!!!!!!!\n!!!!!!!!!!!!!!!!!!");
      }
      int xRef = turn.getPolyTrigon().shape.get(0).getPos()[0];
      int yRef = turn.getPolyTrigon().shape.get(0).getPos()[1];

      for (FieldTrigon ft : turn.getPolyTrigon().getShape()) {
        getField(ft.getPos()[0] + turn.getX() - xRef, ft.getPos()[1] + turn.getY() - yRef,
            ft.getPos()[2]).setColor(turn.getPolyTrigon().getColor());
      }
      return true;
    }
    return false;
  }

  @Override
  public BoardTrigon clone() {
    return new BoardTrigon(this.board, this.startFields);
  }

  /**
   * Method updates the IngameView with the current colored Squares
   *
   * @param view current InGameView that is shown to the user
   * @author tgutberl
   */
  public void updateBoard(InGameView view) {
    for (FieldTrigon ft : board) {
      //Funktioniert nicht weil noch kein TrigonBoardPane exestiert
      //view.getBoardPane().setSquare(ft.getJavaColor(), ft.getPos()[0], ft.getPos()[1]);
    }
  }

  // ======================================================================
  // ========================= AI-Stuff ===================================
  // ======================================================================

  /**
   * method that counts and safes the number of squares which could lead to a next turn for a
   * different color, which are covered by the given turn
   *
   * @param turn the considered turn as a result the number is stored as an attribute of the turn
   *             itself
   */
  @Override
  public void assignNumberBlockedFields(Turn turn) {
    int num = 0;
    int xRef = turn.getPolyTrigon().shape.get(0).getPos()[0];
    int yRef = turn.getPolyTrigon().shape.get(0).getPos()[1];
    for (Color c : Color.values()) {
      if (!c.equals(turn.getPoly().getColor())) {
        for (FieldTrigon ftPoly : turn.getPolyTrigon().getShape()) {
          if (isColorIndirectNeighbor(ftPoly.getPos()[0] + turn.getX() - xRef,
              ftPoly.getPos()[1] + turn.getY() - yRef, ftPoly.getPos()[2], c)) {
            num++;
          }
        }
      }
    }
    turn.setNumberBlockedSquares(num);
  }

  @Override
  public int getScoreOfColor(Color c) {
    int res = 0;
    for (FieldTrigon ft : board) {
      if (ft.getColor().equals(c)) {
        res++;
      }
    }
    return res;
  }

  @Override
  public int getScoreOfColorMiniMax(Color c) {
    int res = 0;
    for (FieldTrigon ft : board) {
      if (ft.getColor().equals(c)) {
        res++;
      } else if (ft.isOccupied()) {
        res--;
      }
    }
    return res;
  }


  public ArrayList<FieldTrigon> getStartFields() {
    return startFields;
  }
}



