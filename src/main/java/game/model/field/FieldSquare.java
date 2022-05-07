package game.model.field;

import game.model.Color;

/**
 * This class represents one square of a square game board.
 *
 * @author tiotto
 * @date 27.03.2022
 */

public class FieldSquare extends Field implements Cloneable {

  /**
   * initializing the default values
   *
   * @param col column of the square on the board
   * @param row row of the square on the board
   * @author tiotto
   */
  public FieldSquare(int col, int row) {
    this.pos = new int[]{col, row};
    this.color = Color.WHITE;
  }

  /**
   * initializing the default values
   *
   * @param col column of the square on the board
   * @param row row of the square on the board
   * @param c   Color of the square, which is created
   * @author tiotto
   */
  public FieldSquare(int col, int row, Color c) {
    this.pos = new int[]{col, row};
    this.color = c;
  }

  /**
   * creates a real copy of the square
   *
   * @return real copy of the square
   */
  @Override
  public FieldSquare clone() {
    return new FieldSquare(this.pos[0], this.pos[1], this.color);
  }

}