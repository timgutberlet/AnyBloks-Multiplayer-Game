package game.model.field;

import game.model.Color;

/**
 * This class represents one triangle of a trigon game board
 *
 * @author tiotto
 * @date 21.03.2022
 */
public class FieldTrigon extends Field implements Cloneable {

  /**
   * initializing the default values
   *
   * @param x       x position of the triangle on the board
   * @param y       y position of the triangle on the board
   * @param isRight 0 or 1, if the triangle is on the right or the left side of the parallelogram
   * @author tiotto
   */
  public FieldTrigon(int x, int y, int isRight) {
    super();
    this.pos = new int[]{x, y, isRight};
    this.color = Color.WHITE;
  }

  /**
   * initializing the default values
   *
   * @param x       x position of the triangle on the board
   * @param y       y position of the triangle on the board
   * @param isRight 1 or 0, if the triangle is on the right or the left side of the parallelogram
   * @param c       Color of the square, which is created
   * @author tiotto
   */
  public FieldTrigon(int x, int y, int isRight, Color c) {
    super();
    this.pos = new int[]{x, y, isRight};
    this.color = c;
  }

  /**
   * checks if the triangle has e specific position
   *
   * @param x       x position of the triangle on the board
   * @param y       y position of the triangle on the board
   * @param isRight 0 or 1, if the triangle is on the right or the left side of the parallelogram
   * @return boolean, if the given position matches with the actual position of the triangle
   */
  public boolean isPos(int x, int y, int isRight) {
    return x == pos[0] && y == pos[1] && isRight == pos[2];
  }

  /**
   * creates a real copy of the triangle
   *
   * @return real copy of the triangle
   */
  @Override
  public FieldTrigon clone() {
    return new FieldTrigon(this.pos[0], this.pos[1], this.pos[2], this.color);
  }

  public String toCode(){
    return "new game.model.field.FieldTrigon(" + pos[0] + "," + pos[1] +"," + pos[2] + "," + "game.model.Color." + getColor() + ")";
  }
}
