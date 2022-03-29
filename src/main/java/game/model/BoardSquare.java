package game.model;

import game.model.gamemodes.GameMode;
import game.view.InGameView;
import java.io.Serializable;
import java.util.ArrayList;

public class BoardSquare extends Board implements Serializable, Cloneable {

  private final ArrayList<FieldSquare> board = new ArrayList<>();

  private final ArrayList<FieldSquare> startFields = new ArrayList<>();

  public BoardSquare(GameMode mode) {
    switch (mode.getName()) {
      case "CLASSIC":
        SIZE = 20;
        startFields.add(new FieldSquare(SIZE - 1, SIZE - 1));
        startFields.add(new FieldSquare(0, SIZE - 1));
        startFields.add(new FieldSquare(SIZE - 1, 0));
        startFields.add(new FieldSquare(0, 0));
        break;
      case "DUO":
      case "JUNIOR":
        SIZE = 14;
        startFields.add(new FieldSquare(4, 4));
        startFields.add(new FieldSquare(9, 9));
        break;
      default:
        SIZE = 0;
    }

    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        board.add(new FieldSquare(i, j));
      }
    }
  }

  public BoardSquare(ArrayList<FieldSquare> board, int size) {
    SIZE = size;
    for (FieldSquare fs : board) {
      this.board.add(fs.clone());
    }
  }

  public ArrayList<FieldSquare> getBoard() {
    return board;
  }

  @Override
  public Color getColor(int[] pos) {
    return getColor(pos[0], pos[1]);
  }

  public Color getColor(int x, int y) {
    if (!isOnTheBoard(x, y)) {
      return null;
    }
    return getField(x, y).getColor();
  }

  @Override
  public boolean isOnTheBoard(int[] pos) {
    return isOnTheBoard(pos[0], pos[1]);
  }

  public boolean isOnTheBoard(int x, int y) {
    return x >= 0 && x < SIZE && y >= 0 && y < SIZE;
  }

  @Override
  public Field getField(int[] pos) {
    return getField(pos[0], pos[1]);
  }

  public FieldSquare getField(int x, int y) {
    for (FieldSquare fs : board) {
      if (fs.pos[0] == x && fs.pos[1] == y) {
        return fs;
      }
    }
    System.out.println("" + x + "," + y);
    return null;
  }

  @Override
  public javafx.scene.paint.Color getJavaColor(int[] pos) {
    return getJavaColor(pos[0], pos[1]);
  }

  public javafx.scene.paint.Color getJavaColor(int x, int y) {
    return getField(x, y).getJavaColor();
  }

  @Override
  public boolean isColorDirectNeighbor(int[] pos, Color c) {
    return isColorDirectNeighbor(pos[0], pos[1], c);
  }

  /**
   * Method that gives back, if a specific square on the board has a side by side neighbor square of
   * a specific color
   *
   * @param x     row of the square
   * @param y     column of the square
   * @param color searched color
   * @return Boolean, if a direct neighbor of this color exists
   */
  public boolean isColorDirectNeighbor(int x, int y, Color color) {
    //over the square
    if (y - 1 > 0 && getColor(x, y - 1).equals(color)) {
      return true;
    }
    //right to the square
    if (x + 1 < getSize() && getColor(x + 1, y).equals(color)) {
      return true;
    }
    //under the square
    if (y + 1 < getSize() && getColor(x, y + 1).equals(color)) {
      return true;
    }
    //left to the square
    return x - 1 > 0 && getColor(x - 1, y).equals(color);
  }


  @Override
  public boolean isColorIndirectNeighbor(int[] pos, Color c) {
    return isColorIndirectNeighbor(pos[0], pos[1], c);
  }

  /**
   * Method that gives back, if a specific square on the board has a neighbor over the edge of a
   * specific color
   *
   * @param x     row of the square
   * @param y     column of the square
   * @param color searched color
   * @return Boolean, if a indirect neighbor of this color exists
   */
  public boolean isColorIndirectNeighbor(int x, int y, Color color) {
    //left and over the square
    if (y - 1 > 0 && x - 1 > 0 && getColor(x - 1, y - 1).equals(color)) {
      return true;
    }
    //right and over to the square
    if (y - 1 > 0 && x + 1 < getSize() && getColor(x + 1, y - 1).equals(color)) {
      return true;
    }
    //left and under the square
    if (x - 1 > 0 && y + 1 < getSize() && getColor(x - 1, y + 1).equals(color)) {
      return true;
    }
    //right and under to the square
    return x + 1 < getSize() && y + 1 < getSize() && getColor(x + 1, y + 1).equals(color);
  }

  @Override
  public boolean isPolyPossible(int[] pos, Poly poly, boolean isFirstRound) {
    return isPolyPossible(pos[0], pos[1], (PolySquare) poly, isFirstRound);
  }

  /**
   * Method that finds out if a placement of a poly at a specific position is legitimate
   *
   * @param x    row of the position (top left corner)
   * @param y    column of the position (top left corner)
   * @param poly considered polygon
   * @return boolean, if a placement is legitimate or not at this position on the board
   */
  public boolean isPolyPossible(int x, int y, PolySquare poly, boolean isFirstRound) {
    boolean indirectNeighbor = isFirstRound;
    int xRef = poly.shape.get(0).getPos()[0];
    int yRef = poly.shape.get(0).getPos()[1];

    for (FieldSquare fsPoly : poly.shape) {
      if (!isOnTheBoard(fsPoly.getPos()[0] + x - xRef, fsPoly.getPos()[1] + y - yRef)) {
        return false;
      }

      if (getField(fsPoly.getPos()[0] + x - xRef, fsPoly.getPos()[1] + y - yRef).isOccupied()) {
        return false;
      }

      if (isColorDirectNeighbor(fsPoly.getPos()[0] + x - xRef, fsPoly.getPos()[1] + y - yRef,
          poly.getColor())) {
        return false;
      }
      if (isFirstRound) {
        for (FieldSquare fs : startFields) {
          indirectNeighbor = indirectNeighbor || fsPoly.equals(fs);
        }
      } else {
        indirectNeighbor =
            indirectNeighbor || isColorIndirectNeighbor(fsPoly.getPos()[0] + x - xRef,
                fsPoly.getPos()[1] + y - yRef, poly.getColor());
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
  public ArrayList<int[]> getPossibleFields(Color color,
      boolean isFirstRound) { //toDo FirstRound need to be added
    ArrayList<int[]> res = new ArrayList<>();
    for (FieldSquare fs : board) {
      if (!isColorDirectNeighbor(fs.getPos()[0], fs.getPos()[1], color)
          && isColorIndirectNeighbor(fs.getPos()[0], fs.getPos()[1], color)) {
        res.add(fs.getPos());
      }
    }
    if (isFirstRound) {
      for (FieldSquare fs : startFields) {
        if (!getField(fs.getPos()).isOccupied()) {
          res.add(fs.getPos());
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
      ArrayList<Turn> movesWithPoly = possibleFieldsAndShadesForPoly((PolySquare) poly,
          isFirstRound);
      if (movesWithPoly.size() > 0) {
        res.addAll(movesWithPoly);
      }
    }
    return res;
  }

  @Override
  public ArrayList<Turn> getPolyShadesPossible(int[] pos, Poly poly, boolean isFirstRound) {
    return getPolyShadesPossible(pos[0], pos[1], (PolySquare) poly, isFirstRound);
  }

  /**
   * this method gives back the list of the specific placements of a polygon for a given position
   *
   * @param x            x value of the position
   * @param y            y value of the position
   * @param isFirstRound boolean, if it is the first Round
   * @param poly         given polygon
   * @return list turns which contain the poly and a tuple out of integers: {row, column, rotation,
   * mirrored}
   */
  private ArrayList<Turn> getPolyShadesPossible(int x, int y, PolySquare poly,
      boolean isFirstRound) {
    ArrayList<Turn> res = new ArrayList<>();
    for (Boolean mirrored : new boolean[]{false, true}) {
      for (int i = 0; i < 4; i++) {
        if (i == 0 && mirrored) {
          poly.mirror();
        }
        if (isPolyPossible(x, y, poly, isFirstRound)) {
          res.add(new Turn(poly.clone(), new int[]{x, y, i, (mirrored ? 1 : 0)}));
        }
        poly.rotateRight();
      }
    }
    return res;
  }


  @Override
  public ArrayList<Turn> getPossibleFieldsAndShadesForPoly(Poly poly, boolean isFirstRound) {
    return possibleFieldsAndShadesForPoly((PolySquare) poly, isFirstRound);
  }

  /**
   * this method gives back a list of the possible positions and the specific placement of possible
   * placements of a given polygon represented by a list of turns
   *
   * @param poly         the given polygon
   * @param isFirstRound boolean, if it is the firstRound
   * @return list of turns with the specific poly
   */
  private ArrayList<Turn> possibleFieldsAndShadesForPoly(PolySquare poly, boolean isFirstRound) {
    ArrayList<Turn> res = new ArrayList<>();
    for (FieldSquare fs : board) {
      ArrayList<Turn> erg = getPolyShadesPossible(fs.getPos()[0], fs.getPos()[1], poly,
          isFirstRound);
      if (erg.size() > 0) {
        res.addAll(erg);
      }
    }
    return res;
  }

  @Override
  public boolean playTurn(Turn turn, boolean isFirstRound) {
    if (isPolyPossible(turn.getX(), turn.getY(), turn.getPolySquare(), isFirstRound)) {
      System.out.println("Poly possible");
      int xRef = turn.getPolySquare().shape.get(0).getPos()[0];
      int yRef = turn.getPolySquare().shape.get(0).getPos()[1];

      for (FieldSquare fs : turn.getPolySquare().getShape()) {
        getField(fs.getPos()[0] + turn.getX() - xRef, fs.getPos()[1] + turn.getY() - yRef).setColor(
            turn.getPolySquare().getColor());
      }
      return true;
    }
    return false;
  }

  @Override
  public BoardSquare clone() {
    return new game.model.BoardSquare(this.board, this.SIZE);
  }

 /* public String toString() {
    StringBuffer res = new StringBuffer();
    for (FieldSquare fs : board) {
      res.append(fs);
      if (fs.getPos()[1] == SIZE - 1) {
        res.append("\n");
      }
    }
    return res.toString();
  }*/

  /**
   * Method updates the IngameView with the current colored Squares
   *
   * @param view current InGameView that is shown to the user
   * @author tgutberl
   */
  public void updateBoard(InGameView view) {
    for (FieldSquare fs : board) {
      view.getBoardPane().setSquare(fs.getJavaColor(), fs.getPos()[0], fs.getPos()[1]);
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
    int xRef = turn.getPolySquare().shape.get(0).getPos()[0];
    int yRef = turn.getPolySquare().shape.get(0).getPos()[1];
    for (Color c : Color.values()) {
      if (!c.equals(turn.getPoly().getColor())) {
        for (FieldSquare fsPoly : turn.getPolySquare().getShape()) {
          if (isColorIndirectNeighbor(fsPoly.getPos()[0] + turn.getX() - xRef,
              fsPoly.getPos()[1] + turn.getY() - yRef, c)) {
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
    for (FieldSquare fs : board) {
      if (fs.getColor().equals(c)) {
        res++;
      }
    }
    return res;
  }

  @Override
  public int getScoreOfColorMiniMax(Color c) {
    int res = 0;
    for (FieldSquare fs : board) {
      if (fs.getColor().equals(c)) {
        res++;
      } else if (fs.isOccupied()) {
        res--;
      }
    }
    return res;
  }
  /**
   * this method returns the maximum width a color has occupied measured from the starting edge
   *
   * @param c      Color, which is looked for
   * @param b      viewed board
   * @param startX 0 if the starting point is on the right side and b.getSize() if on the left side
   * @return maximum width
   */
  /*
  public static int occupiedWidth(Color c, BoardSquare b, int startX) {
    int maxWidth = 0;
    for (int i = 0; i < b.getSize(); i++) {
      for (int j = 0; j < b.getSize(); j++) {
        if (b.getBoard()[i][j].getColor().equals(c)) {
          if (startX == 0) { //if starting point was on the right side
            maxWidth = (maxWidth > i ? maxWidth : i);
          } else { //if starting point was on the left side
            maxWidth = (maxWidth > b.getSize() - i ? maxWidth : b.getSize() - i);
          }
        }
      }
    }
    return maxWidth;
  }

   */

  /**
   * this method returns the maximum height a color has occupied measured from the starting edge
   *
   * @param c      Color, which is looked for
   * @param b      viewed board
   * @param startY 0 if the starting point is on the top and b.getSize() if on the bottom
   * @return maximum width
   */
  /*
  public static int occupiedHeight(Color c, BoardSquare b, int startY) {
    int maxHeight = 0;
    for (int i = 0; i < b.getSize(); i++) {
      for (int j = 0; j < b.getSize(); j++) {
        if (b.getBoard()[i][j].getColor().equals(c)) {
          if (startY == 0) { //if starting point was on the right side
            maxHeight = (maxHeight > j ? maxHeight : j);
          } else { //if starting point was on the left side
            maxHeight = (maxHeight > b.getSize() - j ? maxHeight : b.getSize() - j);
          }
        }
      }
    }
    return maxHeight;
  }

   */

  /*
  public void assignRoomDiscovery(Turn turn) {
    int occWidth = occupiedWidth(turn.getPoly().getColor(), gameState.getBoard(),
        getPlayerFromColor(turn.getPoly().getColor()).getStartX());
    int occHeight = occupiedHeight(turn.getPoly().getColor(), gameState.getBoard(),
        getPlayerFromColor(turn.getPoly().getColor()).getStartY());

    BoardSquare boardAfterTurn = gameState.getBoard().clone();
    playTurn(turn, boardAfterTurn, gameState.isFirstRound());

    int occWidthAfterTurn = occupiedWidth(turn.getPoly().getColor(), boardAfterTurn,
        getPlayerFromColor(turn.getPoly().getColor()).getStartX());
    int occHeightAfterTurn = occupiedHeight(turn.getPoly().getColor(), boardAfterTurn,
        getPlayerFromColor(turn.getPoly().getColor()).getStartY());

    turn.setRoomDiscovery(occWidthAfterTurn - occWidth + occHeightAfterTurn - occHeight);
  }

   */
}
