package game.model;

import java.util.ArrayList;

/**
 * class that defines the logic of the game
 *
 * @author tiotto
 */

public class GameLogic {

  /**
   * states if an AI calculation is running
   */
  public boolean aiCalculationRunning = false;

  /**
   * states if an AI calculation is completed
   */
  public boolean aiCalculationCompleted = false;

  /**
   * the game state represents the current state of the game, what the logic refers to
   */
  private final GameState gameState;

  /**
   * initializes the default values
   *
   * @param gameState referred game state
   */
  public GameLogic(GameState gameState) {
    this.gameState = gameState;

  }

  /**
   * this method returns the maximum width a color has occupied measured from the starting edge
   *
   * @param c      Color, which is looked for
   * @param b      viewed board
   * @param startX 0 if the starting point is on the right side and b.getSize() if on the left side
   * @return maximum width
   */
  public static int occupiedWidth(Color c, Board b, int startX) {
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

  /**
   * this method returns the maximum height a color has occupied measured from the starting edge
   *
   * @param c      Color, which is looked for
   * @param b      viewed board
   * @param startY 0 if the starting point is on the top and b.getSize() if on the bottom
   * @return maximum width
   */
  public static int occupiedHeight(Color c, Board b, int startY) {
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

  public static boolean playTurn(Turn turn, Board b, boolean isFirstTurn) {
    if (turn == null) {
      return false;
    }
    if (b.isPolyPossible(turn.getColumn(), turn.getRow(), turn.getPoly(), isFirstTurn)) {
      for (int i = 0; i < turn.getPoly().getWidth(); i++) {
        for (int j = 0; j < turn.getPoly().getHeight(); j++) {
          if (turn.getPoly().getShape()[i][j]) {
            b.getBoard()[turn.getColumn() + i][turn.getRow() + j].setColor(
                turn.getPoly().getColor());
          }
        }
      }
      return true;
    }
    return false;
  }

  public void addPlayer(Player player) {
    gameState.addPlayer(player);
  }

  public void startGame() {
    gameState.setStateRunning(true);
  }

  public Color getColorFromPlayer(Player p) {
    return gameState.getColorFromPlayer(p);
  }

  public Player getPlayerFromColor(Color c) {
    return gameState.getPlayerFromColor(c);
  }

  /**
   * Method that gives back a list of all the possible squares, that are over the edge to already
   * placed polygons
   *
   * @param color searched color
   * @return Arraylist with doubles inside, which contain the row and the column of the squares
   */
  private ArrayList<int[]> possibleSquares(Color color) {
    ArrayList<int[]> res = new ArrayList<>();
    for (int i = 0; i < gameState.getBoard().getSize(); i++) {
      for (int j = 0; j < gameState.getBoard().getSize(); j++) {
        if (!gameState.getBoard().isColorDirectNeighbor(i, j, color) && gameState.getBoard()
            .isColorIndirectNeighbor(i, j, color)) {
          res.add(new int[]{i, j});
        }
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
  public ArrayList<Turn> getPossibleMoves(Player player) {
    ArrayList<Turn> res = new ArrayList<>();
    for (Poly poly : gameState.getRemainingPolys(player)) {
      ArrayList<Turn> movesWithPoly = possibleSquaresAndShadesForPoly(poly);
      if (movesWithPoly.size() > 0) {
        res.addAll(movesWithPoly);
      }
    }
    return res;
  }

  /**
   * this method gives back a list of the possible positions and the specific placement of possible
   * placements of a given polygon represented by a list of turns
   *
   * @param poly the given polygon
   * @return list of turns with the specific poly
   */
  private ArrayList<Turn> possibleSquaresAndShadesForPoly(Poly poly) {
    ArrayList<Turn> res = new ArrayList<>();
    for (int i = 0; i < gameState.getBoard().getSize() - poly.width - 1; i++) {
      for (int j = 0; j < gameState.getBoard().getSize() - poly.height - 1; j++) {
        ArrayList<Turn> erg = getPolyShadesPossible(i, j, poly);
        if (erg.size() > 0) {
          res.addAll(erg);
        }
      }
    }
    return res;
  }

  /**
   * this method gives back the list of the specific placements of a polygon for a given position
   *
   * @param n    row of the position (top left corner of the shape)
   * @param m    column of the position (top left corner of the shape)
   * @param poly given polygon
   * @return list turns which contain the poly and a tuple out of integers: {row, column, rotation,
   * mirrored}
   */
  private ArrayList<Turn> getPolyShadesPossible(int n, int m, Poly poly) {
    ArrayList<Turn> res = new ArrayList<>();
    for (Boolean mirrored : new boolean[]{false, true}) {
      for (int i = 0; i < 4; i++) {
        if (i == 0 && mirrored) {
          poly.mirror();
        }
        if (gameState.getBoard().isPolyPossible(n, m, poly, gameState.isFirstRound())) {
          res.add(new Turn(poly.clone(), new int[]{n, m, i, (mirrored ? 1 : 0)}));
        }
        poly.rotateRight();
      }
    }
    return res;
  }

  /**
   * method that counts and safes the number of squares which could lead to a next turn for a
   * different color, which are covered by the given turn
   *
   * @param turn the considered turn as a result the number is stored as an attribute of the turn
   *             itself
   */
  public void assignNumberBlockedSquares(Turn turn) {
    int num = 0;
    for (Color c : Color.values()) {
      if (!c.equals(turn.getPoly().getColor())) {
        for (int i = 0; i < turn.getPoly().getWidth(); i++) {
          for (int j = 0; j < turn.getPoly().getHeight(); j++) {
            if (turn.getPoly().getShape()[i][j] && gameState.getBoard()
                .isColorIndirectNeighbor(turn.getColumn() + i, turn.getRow() + j, c)) {
              num++;
            }
          }
        }
      }
    }
    turn.setNumberBlockedSquares(num);
  }

  public boolean playTurn(Turn turn) {
    if (turn == null) {
      gameState.incTurn();
      return false;
    }
    if (gameState.getBoard().isPolyPossible(turn.getColumn(), turn.getRow(), turn.getPoly(),
        gameState.isFirstRound())) {
      System.out.println("Poly possible");
      for (int i = 0; i < turn.getPoly().getWidth(); i++) {
        for (int j = 0; j < turn.getPoly().getHeight(); j++) {
          if (turn.getPoly().getShape()[i][j]) {
            gameState.getBoard().getBoard()[turn.getColumn() + i][turn.getRow() + j].setColor(
                turn.getPoly().getColor());
          }
        }
      }

      System.out.println(gameState.getPlayerFromColor(turn.getPoly().getColor()));
      System.out.println(turn.getPoly());
      for (Poly p : gameState.getRemainingPolys(
          gameState.getPlayerFromColor(turn.getPoly().getColor()))) {
        if (p.equals(turn.getPoly())) {
          gameState.getRemainingPolys(gameState.getPlayerFromColor(turn.getPoly().getColor()))
              .remove(p);
          System.out.println("Poly entfernt");
          break;
        }
      }
      gameState.incTurn();
      return true;
    }
    gameState.incTurn();
    return false;
  }

  public void assignRoomDiscovery(Turn turn) {
    int occWidth = occupiedWidth(turn.getPoly().getColor(), gameState.getBoard(),
        getPlayerFromColor(turn.getPoly().getColor()).getStartX());
    int occHeight = occupiedHeight(turn.getPoly().getColor(), gameState.getBoard(),
        getPlayerFromColor(turn.getPoly().getColor()).getStartY());

    Board boardAfterTurn = gameState.getBoard().clone();
    playTurn(turn, boardAfterTurn, gameState.isFirstRound());

    int occWidthAfterTurn = occupiedWidth(turn.getPoly().getColor(), boardAfterTurn,
        getPlayerFromColor(turn.getPoly().getColor()).getStartX());
    int occHeightAfterTurn = occupiedHeight(turn.getPoly().getColor(), boardAfterTurn,
        getPlayerFromColor(turn.getPoly().getColor()).getStartY());

    turn.setRoomDiscovery(occWidthAfterTurn - occWidth + occHeightAfterTurn - occHeight);
  }

  public GameState getGameState() {
    return gameState;
  }


}



