package game.model.field;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import game.model.Color;

@JsonIgnoreProperties(ignoreUnknown = true)

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

  /**
   * empty constructor for jackson.
   */
  public FieldSquare() {

  }

  /**
   * creates a new empty field
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
   * initializing the default values.
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
   * creates a real copy of the square.
   *
   * @return real copy of the square
   */
  @Override
  public FieldSquare clone() {
    return new FieldSquare(this.pos[0], this.pos[1], this.color);
  }

  /**
   * converts the board into code, which creates the board.
   *
   * @return string containing the creating code
   */
  public String toCode() {
    return "new game.model.field.FieldSquare(" + pos[0] + "," + pos[1] + "," + "game.model.Color."
        + getColor() + ")";
  }

}
