package game.model;

import java.io.Serializable;
import javax.print.DocFlavor.STRING;

public class Board implements Serializable, Cloneable {

  public static int SIZE; //How do I make this final? There will be an error. - Tilman

  private final Square[][] board;

  public Board(GameMode mode) {
    switch (mode) {
      case CLASSIC:
        this.SIZE = 20;
        break;
      case DUO:
      case JUNIOR:
        this.SIZE = 14;
        break;
      case TRIGON:
        this.SIZE = 22;
        break;
      default:
        this.SIZE = 0;
    }

    this.board = new Square[SIZE][SIZE];

    for (int i = 0; i < SIZE; i++) {
      for (int j = 0; j < SIZE; j++) {
        board[i][j] = new Square(i, j);
      }
    }
  }

  public Board(int size) {
    this.SIZE = size;

    this.board = new Square[SIZE][SIZE];
  }

  public Color getColor(int n, int m) {
    return board[n][m].getColor();
  }

  public javafx.scene.paint.Color getJavaColor(int n, int m) {
    return board[n][m].getJavaColor();
  }

  public int getSize() {
    return SIZE;
  }

  public Square[][] getBoard() {
    return board;
  }

  /**
   * Method that gives back, if a specific square on the board has a side by side neighbor square of
   * a specific color
   *
   * @param n     row of the square
   * @param m     column of the square
   * @param color searched color
   * @return Boolean, if a direct neighbor of this color exists
   */
  public boolean isColorDirectNeighbor(int n, int m, Color color) {
    //over the square
    if (m > 0 && getColor(n, m - 1).equals(color)) {
      return true;
    }
    //right to the square
    if (n < getSize() && getColor(n + 1, m).equals(color)) {
      return true;
    }
    //under the square
    if (m < getSize() && getColor(n, m + 1).equals(color)) {
      return true;
    }
    //left to the square
    if (n > 0 && getColor(n - 1, m).equals(color)) {
      return true;
    }
    return false;
  }

  /**
   * Method that gives back, if a specific square on the board has a neighbor over the edge of a
   * specific color
   *
   * @param n     row of the square
   * @param m     column of the square
   * @param color searched color
   * @return Boolean, if a indirect neighbor of this color exists
   */
  public boolean isColorIndirectNeighbor(int n, int m, Color color) {
    //left and over the square
    if (m > 0 && n > 0 && getColor(n - 1, m - 1).equals(color)) {
      return true;
    }
    //right and over to the square
    if (m > 0 && n < getSize() && getColor(n + 1, m - 1).equals(color)) {
      return true;
    }
    //left and under the square
    if (n > 0 && m < getSize() && getColor(n - 1, m + 1).equals(color)) {
      return true;
    }
    //right and under to the square
    if (n < getSize() && m < getSize() && getColor(n + 1, m + 1).equals(color)) {
      return true;
    }
    return false;
  }

  /**
   * Method that finds out if a placement of a poly at a specific position is legitimate
   *
   * @param n    row of the position (top left corner)
   * @param m    column of the position (top left corner)
   * @param poly considered polygon
   * @return boolean, if a placement is legitimate or not at this position on the board
   */
  public boolean isPolyPossible(int n, int m, Poly poly, boolean isFirstRound) {
    boolean indirectNeighbor = isFirstRound;
    for (int i = 0; i < poly.width; i++) {
      for (int j = 0; j < poly.height; j++) {
        if (poly.getShape()[i][j]) {
          if ((n + i >= getSize()) || (m + j) >= getSize()) {
            return false;
          }

          if (getBoard()[n + i][m + j].isOccupied()) {
            return false;
          }

          if (isColorDirectNeighbor(n + i, m + j, poly.getColor())) {
            return false;
          }

          indirectNeighbor =
              indirectNeighbor || isColorIndirectNeighbor(n + i, m + j, poly.getColor());
        }
      }
    }
    if (isFirstRound) {
      if (poly.getColor().equals(Color.RED)) {
        return poly.getShape()[0][0] && n == 0 && m == 0;
      } else {
        return poly.getShape()[poly.getWidth() - 1][poly.getHeight() - 1]
            && n == getSize() - poly.getWidth() - 2 && m == getSize() - poly.getHeight() - 2;
      }

    }
    return indirectNeighbor;
  }

  @Override
  public Board clone() {
    Board clone = new Board(this.getSize());
    for (int i = 0; i < clone.getSize(); i++) {
      for (int j = 0; j < clone.getSize(); j++) {
        clone.getBoard()[i][j] = this.getBoard()[i][j].clone();
      }
    }
    return clone;
  }

  public String toString() {
    StringBuffer res = new StringBuffer();
    for (int i = 0; i < getSize(); i++) {
      for (int j = 0; j < getSize(); j++) {
        res.append(getBoard()[i][j]);
      }
      res.append("\n");
    }
    return res.toString();
  }
}
