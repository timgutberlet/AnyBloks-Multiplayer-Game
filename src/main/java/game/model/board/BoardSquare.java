package game.model.board;

import game.model.Color;
import game.model.Debug;
import game.model.Turn;
import game.model.field.Field;
import game.model.field.FieldSquare;
import game.model.gamemodes.GameMode;
import game.model.polygon.Poly;
import game.model.polygon.PolySquare;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * BoardSquare class, representing a Board with its squares on it.
 *
 * @author tiotto
 * @date 27.03.2022
 */
public class BoardSquare extends Board implements Serializable, Cloneable {

  /**
   * List with the fields of the board.
   */
  private final ArrayList<FieldSquare> board = new ArrayList<>();

  /**
   * list with the fields of the board, where player can start the game.
   */
  private final ArrayList<FieldSquare> startFields = new ArrayList<>();


  /**
   * empty constructor for jackson.
   */
  public BoardSquare() {

  }

  /**
   * creates a new and empty boardsquare depending on the game mode. Stores as well the right start
   * fields.
   *
   * @param mode given game mode
   */
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

  /**
   * creates a new boardsquare as a copy of the given parameters.
   *
   * @param board       given board as a list of fields
   * @param size        given size
   * @param startFields given start fields
   */
  public BoardSquare(ArrayList<FieldSquare> board, int size, ArrayList<FieldSquare> startFields) {
    SIZE = size;
    for (FieldSquare fs : board) {
      this.board.add(fs.clone());
    }
    for (FieldSquare fs : startFields) {
      this.startFields.add(fs.clone());
    }
  }

  // ======================================================================
  // ======================== Getter and Setter ===========================
  // ======================================================================

  /**
   * gives back the board.
   *
   * @return board
   */
  public ArrayList<FieldSquare> getBoard() {
    return board;
  }

  /**
   * returns the color of a field identified by the given coordinates.
   *
   * @param pos given coordinates
   * @return the specific field
   */
  @Override
  public Color getColor(int[] pos) {
    return getColor(pos[0], pos[1]);
  }

  /**
   * returns the color of a field identified by the given coordinates.
   *
   * @param x coordinate
   * @param y coordinate
   * @return the specific field
   */
  public Color getColor(int x, int y) {
    if (!isOnTheBoard(x, y)) {
      return null;
    }
    return getField(x, y).getColor();
  }

  /**
   * checks if a field identified by the given coordinates is part of the board.
   *
   * @param pos given coordinates
   * @return boolean, if the field is part of the board
   */
  @Override
  public boolean isOnTheBoard(int[] pos) {
    return isOnTheBoard(pos[0], pos[1]);
  }

  /**
   * checks if a field identified by the given coordinates is part of the board.
   *
   * @param x coordinate
   * @param y coordinate
   * @return boolean, if the field is part of the board
   */
  public boolean isOnTheBoard(int x, int y) {
    return x >= 0 && x < SIZE && y >= 0 && y < SIZE;
  }

  /**
   * returns the field identified by the given coordinates.
   *
   * @param pos given coordinates
   * @return the specific field
   */
  @Override
  public Field getField(int[] pos) {
    return getField(pos[0], pos[1]);
  }

  /**
   * returns the field identified by the given coordinates.
   *
   * @param x coordinate
   * @param y coordinate
   * @return the specific field
   */
  public FieldSquare getField(int x, int y) {
    for (FieldSquare fs : board) {
      if (fs.pos[0] == x && fs.pos[1] == y) {
        return fs;
      }
    }
    Debug.printMessage("" + x + "," + y);
    return null;
  }

  /**
   * returns the fields, where player can start their game.
   *
   * @return list of the fields, where can be started
   */
  public ArrayList<FieldSquare> getStartFields() {
    return startFields;
  }

  /**
   * returns the size of the board.
   *
   * @return size of the board
   */
  @Override
  public int getSize() {
    return super.getSize();
  }

  // ======================================================================
  // ============= Neighbor dependencies and isPolyPossible ===============
  // ======================================================================

  /**
   * checks if a field identified by the given coordinates has the given color as a direct neighbor,
   * means with a shared edge.
   *
   * @param pos given coordinates
   * @param c   given color
   * @return boolean, if one of the direct neighbor fields has this color
   */
  @Override
  public boolean isColorDirectNeighbor(int[] pos, Color c) {
    return isColorDirectNeighbor(pos[0], pos[1], c);
  }

  /**
   * Method that gives back, if a specific square on the board has a side by side neighbor square of
   * a specific color.
   *
   * @param x     row of the square
   * @param y     column of the square
   * @param color searched color
   * @return Boolean, if a direct neighbor of this color exists
   */
  public boolean isColorDirectNeighbor(int x, int y, Color color) {
    //over the square
    if (y > 0 && getColor(x, y - 1).equals(color)) {
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
    return x > 0 && getColor(x - 1, y).equals(color);
  }

  /**
   * checks if a field identified by the given coordinates has the given color as an indirect
   * neighbor, means with a shared corner.
   *
   * @param pos given coordinates
   * @param c   given color
   * @return boolean, if one of the indirect neighbor fields has this color
   */
  @Override
  public boolean isColorIndirectNeighbor(int[] pos, Color c) {
    return isColorIndirectNeighbor(pos[0], pos[1], c);
  }

  /**
   * Method that gives back, if a specific square on the board has a neighbor over the edge of a
   * specific color.
   *
   * @param x     row of the square
   * @param y     column of the square
   * @param color searched color
   * @return Boolean, if a indirect neighbor of this color exists
   */
  public boolean isColorIndirectNeighbor(int x, int y, Color color) {
    //left and over the square
    if (isOnTheBoard(x - 1, y - 1) && getColor(x - 1, y - 1).equals(color)) {
      return true;
    }
    //right and over to the square
    if (isOnTheBoard(x + 1, y - 1) && getColor(x + 1, y - 1).equals(color)) {
      return true;
    }
    //left and under the square
    if (isOnTheBoard(x - 1, y + 1) && getColor(x - 1, y + 1).equals(color)) {
      return true;
    }
    //right and under to the square
    return isOnTheBoard(x + 1, y + 1) && getColor(x + 1, y + 1).equals(color);
  }

  /**
   * checks if the given poly is possible at the given position on the field.
   *
   * @param pos          given position (coordinates)
   * @param poly         given poly
   * @param isFirstRound if it is the first round
   * @return boolean, if the poly is possible at the position
   */
  @Override
  public boolean isPolyPossible(int[] pos, Poly poly, boolean isFirstRound) {
    return isPolyPossible(pos[0], pos[1], (PolySquare) poly, isFirstRound);
  }

  /**
   * Method that finds out if a placement of a poly at a specific position is legitimate.
   *
   * @param x    row of the position (top left corner)
   * @param y    column of the position (top left corner)
   * @param poly considered polygon
   * @return boolean, if a placement is legitimate or not at this position on the board
   */
  public boolean isPolyPossible(int x, int y, PolySquare poly, boolean isFirstRound) {
    boolean indirectNeighbor = false;
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
          indirectNeighbor = indirectNeighbor || (fs.pos[0] == fsPoly.getPos()[0] + x - xRef
              && fs.pos[1] == fsPoly.getPos()[1] + y - yRef);
        }
      } else {
        indirectNeighbor =
            indirectNeighbor || isColorIndirectNeighbor(fsPoly.getPos()[0] + x - xRef,
                fsPoly.getPos()[1] + y - yRef, poly.getColor());
      }
    }
    return indirectNeighbor;
  }

  // ======================================================================
  // ============= getPossibleFields general and for poly =================
  // ======================================================================

  /**
   * Method that gives back a list of all the possible positions, that are over the edge to already
   * placed polygons.
   *
   * @param color        searched color
   * @param isFirstRound if it is the first round
   * @return Arraylist with coordinates inside, which contain the position of the fields
   */
  @Override
  public ArrayList<int[]> getPossibleFields(Color color,
      boolean isFirstRound) {
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
   * gets the coordinates of all fields, where the given poly can be placed (the field with the
   * indirect color neighbor).
   *
   * @param poly         given poly
   * @param isFirstRound if it is the first round
   * @return list of coordinates, where the poly can be placed
   */
  @Override
  public ArrayList<int[]> getPossibleFieldsForPoly(Poly poly, boolean isFirstRound) {
    ArrayList<int[]> res = new ArrayList<>();
    A:
    for (int[] pos : getPossibleFields(poly.getColor(), isFirstRound)) {
      for (Turn t : getMovesForPoly((PolySquare) poly, isFirstRound)) {
        int xRef = ((PolySquare) poly).shape.get(0).getPos()[0];
        int yRef = ((PolySquare) poly).shape.get(0).getPos()[1];
        int x = t.getX();
        int y = t.getY();
        for (FieldSquare fs : ((PolySquare) t.getPoly()).getShape()) {
          if (fs.getPos()[0] + x - xRef == pos[0] && fs.getPos()[1] + y - yRef == pos[1]) {
            res.add(pos);
            continue A;
          }
        }
      }
    }
    return res;
  }

  // ======================================================================
  // ================ Get possible Moves and help methods =================
  // ======================================================================

  /**
   * Method that gives back a list of all possible moves of a list of remaining polygons.
   *
   * @param remainingPolys list of remaining polys
   * @param isFirstRound   boolean, if it is the first round
   * @return returns a List with all the possible moves
   */
  @Override
  public ArrayList<Turn> getPossibleMoves(ArrayList<Poly> remainingPolys, boolean isFirstRound) {
    ArrayList<Turn> res = new ArrayList<>();
    for (Poly p : remainingPolys) {
      Poly pClone = p.clone();
      for (boolean mirrored : new boolean[]{true, false}) {
        A:
        for (int i = 0; i < 4; i++) {
          res.addAll(getMovesForPoly((PolySquare) p, isFirstRound));
          p.rotateLeft();
          if (p.equalsReal(pClone)) {
            continue A;
          }
        }
        p.mirror();
      }
    }
    return res;
  }

  /**
   * Method gives back a list of all possible moves for one specific poly (without rotation or
   * mirror).
   *
   * @param p            considered poly
   * @param isFirstRound boolean, if it is the first round
   * @return returns a List with all the possible moves for p
   */
  public ArrayList<Turn> getMovesForPoly(PolySquare p, boolean isFirstRound) {
    ArrayList<Turn> res = new ArrayList<>();
    for (int[] pos : getPossibleFields(p.getColor(), isFirstRound)) {
      for (FieldSquare fs : p.getShape()) {
        if (isPolyPossible(pos[0] - fs.getPos()[0] + p.shape.get(0).getPos()[0],
            pos[1] - fs.getPos()[1] + p.shape.get(0).getPos()[1], p, isFirstRound)) {
          res.add(new Turn(p.clone(),
              new int[]{pos[0] - fs.getPos()[0] + p.shape.get(0).getPos()[0],
                  pos[1] - fs.getPos()[1] + p.shape.get(0).getPos()[1]}));
        }
      }
    }
    return res;
  }

  /**
   * gets a list of moves, that are possible with the given poly at one position (considering
   * rotation and mirroring as well).
   *
   * @param pos          given position
   * @param poly         given poly
   * @param isFirstRound if it is the first round
   * @return list of turns, that are possible with that poly at the given position
   */
  @Override
  public ArrayList<Turn> getPolyShadesPossible(int[] pos, Poly poly, boolean isFirstRound) {
    return getPolyShadesPossible(pos[0], pos[1], (PolySquare) poly, isFirstRound);
  }

  /**
   * this method gives back the list of the specific placements of a polygon for a given position.
   *
   * @param x            x value of the position
   * @param y            y value of the position
   * @param isFirstRound boolean, if it is the first Round
   * @param poly         given polygon
   * @return list turns which contain the poly and a tuple out of integers: {row, column,
   * rotation,mirrored}
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

  /**
   * this method gives back a list of the possible positions and the specific placement of possible
   * placements of a given polygon represented by a list of turns.
   *
   * @param poly         the given polygon
   * @param isFirstRound boolean, if it is the firstRound
   * @return list of turns with the specific poly
   */
  @Override
  public ArrayList<Turn> getPossibleFieldsAndShadesForPoly(Poly poly, boolean isFirstRound) {
    return possibleFieldsAndShadesForPoly((PolySquare) poly, isFirstRound);
  }

  /**
   * this method gives back a list of the possible positions and the specific placement of possible
   * placements of a given polygon represented by a list of turns.
   *
   * @param poly         the given polygon
   * @param isFirstRound boolean, if it is the firstRound
   * @return list of turns with the specific poly
   */
  private ArrayList<Turn> possibleFieldsAndShadesForPoly(PolySquare poly, boolean isFirstRound) {
    ArrayList<Turn> res = new ArrayList<>();
    for (FieldSquare fs : board) {
      if (fs.isOccupied()) {
        continue;
      }
      ArrayList<Turn> erg = getPolyShadesPossible(fs.getPos()[0], fs.getPos()[1], poly,
          isFirstRound);
      res.addAll(erg);
    }
    return res;
  }

  // ======================================================================
  // =================== Play turn and update board =======================
  // ======================================================================

  /**
   * method to play a given turn.
   *
   * @param turn         given turn
   * @param isFirstRound if it is the first round
   * @return boolean, if the turn was executed successful
   */
  @Override
  public boolean playTurn(Turn turn, boolean isFirstRound) {
    if (turn == null) {
      return false;
    }
    if (isPolyPossible(turn.getX(), turn.getY(), turn.getPolySquare(), isFirstRound)) {
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

  // ======================================================================
  // ========================= AI-methods =================================
  // ======================================================================

  /**
   * method that counts and safes the number of squares which could lead to a next turn for a
   * different color, which are covered by the given turn.
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
    turn.setNumberBlockedFields(num);
  }

  /**
   * evaluates the current score for a given color.
   *
   * @param c given color
   * @return score as int
   */
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

  /**
   * evaluates the current score for a given color reduced by the score of the other colors.
   *
   * @param c given color
   * @return score as int
   */
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
   * this method returns the maximum width a color has occupied measured from the starting edge.
   *
   * @param c Color, which is looked for
   * @return maximum width
   */
  @Override
  public int occupiedWidth(Color c) {
    int maxWidth = -1;
    int minWidth = getSize();
    for (FieldSquare fs : board) {
      if (fs.getColor().equals(c)) {
        maxWidth = Math.max(maxWidth, fs.getPos()[0]);
        minWidth = Math.min(minWidth, fs.getPos()[0]);
      }
    }
    return maxWidth - minWidth;
  }

  /**
   * this method returns the maximum height a color has occupied measured from the starting edge.
   *
   * @param c Color, which is looked for
   * @return maximum height
   */
  @Override
  public int occupiedHeight(Color c) {
    int maxHeight = -1;
    int minHeight = getSize();
    for (FieldSquare fs : board) {
      if (fs.getColor().equals(c)) {
        maxHeight = Math.max(maxHeight, fs.getPos()[1]);
        minHeight = Math.min(minHeight, fs.getPos()[1]);
      }
    }
    return maxHeight - minHeight;
  }

  // ======================================================================
  // ================ Clone, toString, toCode  ============================
  // ======================================================================

  /**
   * method to deep clone a board.
   *
   * @return returns the deep clone of the board
   */
  @Override
  public BoardSquare clone() {
    return new BoardSquare(this.board, this.SIZE, this.startFields);
  }

  /**
   * converts to board to a string.
   *
   * @return the string representation of the board
   */
  @Override
  public String toString() {
    StringBuffer res = new StringBuffer();
    res.append(super.toString() + "\n");
    int i = 0;
    for (FieldSquare fs : board) {
      res.append(fs.toString());
      if (++i % SIZE == 0) {
        res.append("\n");
      }
    }
    return res.toString();
  }

  /**
   * converts the board into code, which creates the board.
   *
   * @return string containing the creating code
   */
  @Override
  public String toCode() {
    StringBuffer res = new StringBuffer();
    res.append("BoardSquare bs = new BoardSquare();\n");
    for (FieldSquare fs : board) {
      res.append("bs.getBoard().add(" + fs.toCode() + "); ");
    }
    res.append("\n");
    for (FieldSquare fs : startFields) {
      res.append("bs.getStartFields().add(" + fs.toCode() + ");");
    }
    return res.toString();
  }
}
