package game.model;

import java.util.ArrayList;

/**
 * This class controls the AI player of the game.
 *
 * @author tiotto
 */

public class AI {

  /**
   * calculated the next move for an AI Player depending on the set difficulty
   *
   * @param gameState current gameState of the game
   * @param player    considered player
   * @return "best turn"
   */
  public static Turn calculateNextMove(GameState gameState, Player player) {
    switch (player.getType()) {
      case AI_HARD:
        // return calculateNextHardMove(gameState, player);
      case AI_MIDDLE:
        return calculateNextMiddleMove(gameState.getBoard(), gameState.getRemainingPolys(player),
            gameState.isFirstRound());
      case AI_EASY:
        return calculateNextEasyMove(gameState.getBoard(), gameState.getRemainingPolys(player),
            gameState.isFirstRound());
      default:
        System.out.println("AI move for human player");
        return null;
    }
  }

  /**
   * calculates the next easy move through sorting the possible moves after the size of the poly
   *
   * @param board          considered board
   * @param remainingPolys remaining polys of the player which the calculation is for
   * @param isFirstRound   boolean, if they are in the first round
   * @return "best" turn
   */
  public static Turn calculateNextEasyMove(Board board, ArrayList<Poly> remainingPolys,
      boolean isFirstRound) {
    ArrayList<Turn> possibleMoves = board.getPossibleMoves(remainingPolys, isFirstRound);
    possibleMoves.sort((o1, o2) -> o1.getPoly().getSize() - o2.getPoly().getSize());
    if (possibleMoves.size() == 0) {
      return null;
    } else {
      return possibleMoves.get(0);
    }
  }

  /**
   * calculates the next move through sorting the possible moves after the blocked fields of the
   * opponents as the first criteria and the size of the poly as the second criteria
   *
   * @param board          considered board
   * @param remainingPolys remaining polys of the player which the calculation is for
   * @param isFirstRound   boolean, if they are in the first round
   * @return "best" turn
   */

  public static Turn calculateNextMiddleMove(Board board, ArrayList<Poly> remainingPolys,
      boolean isFirstRound) {
    ArrayList<Turn> possibleMoves = board.getPossibleMoves(remainingPolys, isFirstRound);
    for (Turn turn : possibleMoves) {
      board.assignNumberBlockedFields(turn);
    }
    possibleMoves.sort((o1, o2) -> o1.getPoly().getSize() - o2.getPoly().getSize());
    possibleMoves.sort((o1, o2) -> o1.getNumberBlockedSquares() - o2.getNumberBlockedSquares());
    if (possibleMoves.size() == 0) {
      return null;
    }
    return possibleMoves.get(0);
  }

  /**
   * calculates the next move with the minimax algorithm or a variation of it
   *
   * @param gameState current gameState
   * @param player    player, for whom the next move is calculated
   * @return the next "best" move
   */
  public static Turn calculateNextHardMove(GameState gameState, Player player) {
    int bestVal = -1000;
    Turn bestTurn = null;
    for (Turn t : gameState.getBoard().getPossibleMoves(gameState.getRemainingPolys(player),
        gameState.isFirstRound())) { //for every possible turn are the future steps been calculated and then the best is chosen
      int turnVal;
      if (gameState.getPlayer().size()
          == 2) { //with two players the real minimax algorithm can be used for the evaluation of the turn
        turnVal = minimax2Player(gameState.tryTurn(t),
            gameState.getNextColor(gameState.getColorFromPlayer(player)), 0, false,
            3); // start with the next color
      } else { //with four players a variation of the minimax algorithm can be used for the evaluation of the turn
        turnVal = minimax4Player(gameState.tryTurn(t), gameState.getColorFromPlayer(player), 0,
            2); // always with the color of the current player
      }
      if (turnVal > bestVal) {
        bestTurn = t;
        bestVal = turnVal;
      }
    }
    return bestTurn;
  }

  /**
   * calculated the next move with two players with a view in the future of h steps under the
   * assumption that all other player play perfectly as well
   *
   * @param gameState used gameState (should be copied when given)
   * @param c         color of the current player (switches between the min and the max player)
   * @param depth     current depth of the minimax tree
   * @param isMax     boolean, if the current evaluation is for the max or the min player
   * @param h         maximum steps in the future / height of the minimax tree
   * @return best result that can be achieved with the current turn
   */
  public static int minimax2Player(GameState gameState, Color c, int depth, boolean isMax, int h) {
    if (depth == h) {
      return evaluate(gameState.getBoard(), c);
    }

    if (isMax) { //calculation for the max player with the goal to maximize the best integer
      int best = -1000; //lower than the worst result
      for (Turn t : gameState.getBoard()
          .getPossibleMoves(gameState.getRemainingPolys(gameState.getPlayerFromColor(c)),
              gameState.isFirstRound())) { //for every possible turn, the result of the next player will be calculated
        best = Math.max(best,
            minimax2Player(gameState.tryTurn(t), gameState.getNextColor(c), depth + 1, false,
                h)); //max of the already calculated evaluation and the current evaluation with the played turn
      }
      return best;
    } else { //calculation for the min player with the goal to minimize the best integer
      int best = 1000; //higher than the best result
      for (Turn t : gameState.getBoard()
          .getPossibleMoves(gameState.getRemainingPolys(gameState.getPlayerFromColor(c)),
              gameState.isFirstRound())) {//for every possible turn, the result of the next player will be calculated
        best = Math.min(best,
            minimax2Player(gameState.tryTurn(t), gameState.getNextColor(c), depth + 1, true,
                h)); //min of the already calculated evaluation and the current evaluation with the played turn
      }
      return best;
    }
  }

  /**
   * minimax evaluation of the board for a specific color
   *
   * @param board analyzed board
   * @param c     considered color
   * @return returns the evaluation, which subtracts the score of the opponents from the score of
   * the considered color
   */
  static int evaluate(Board board, Color c) {
    return board.getScoreOfColorMiniMax(c);
  }

  /**
   * calculates the next move with four players with a view in the future of h steps under the
   * assumption, that all other players play with the middle AI
   *
   * @param gameState used gameState (should be copied when given)
   * @param c         color of the player, which the calculation is for
   * @param depth     current steps in the future
   * @param h         maximum steps in the future
   * @return best result that can be achieved after the other players have played
   * <p>
   * actually it is not a minimax algorithm anymore, but only a max algorithm for the playing
   * player
   */
  public static int minimax4Player(GameState gameState, Color c, int depth, int h) {
    if (depth == h) {
      return evaluate(gameState.getBoard(),
          c); //after the defined steps into the future the evaluation of the board for the current color will be returned
    }
    Color current = c;
    for (int i = 0; i < 3; i++) { //calculate the other players turns
      current = gameState.getNextColor(current);
      gameState.playTurn(calculateNextMiddleMove(gameState.getBoard(),
          gameState.getRemainingPolys(gameState.getPlayerFromColor(current)),
          gameState.isFirstRound()));
    }
    int best = -1000; //lower that the worst possible result
    for (Turn t : gameState.getBoard()
        .getPossibleMoves(gameState.getRemainingPolys(gameState.getPlayerFromColor(c)),
            gameState.isFirstRound())) { //for every possible turn, another round will be calculated
      best = Math.max(best,
          minimax4Player(gameState.tryTurn(t), gameState.getNextColor(current), depth + 1, h));
    }
    return best; //return the evaluation of the best move
  }

  /*
  public static Turn calculateNextHardMove(Player player, GameLogic gameLogic) {
    ArrayList<Turn> possibleMoves = gameLogic.getPossibleMoves(player);
    for (Turn turn : possibleMoves) {
      gameLogic.assignNumberBlockedSquares(turn);
      gameLogic.assignRoomDiscovery(turn);
    }

    int currentRoomDiscovery = GameLogic.occupiedHeight(gameLogic.getColorFromPlayer(player),
        gameLogic.getGameState().getBoard(), player.getStartX()) + GameLogic.occupiedHeight(
        gameLogic.getColorFromPlayer(player), gameLogic.getGameState().getBoard(),
        player.getStartY());

    if (currentRoomDiscovery > gameLogic.getGameState().getBoard()
        .getSize()) { //If more than the half of the game board is discovered, it concentrates on playing against the enemy
      possibleMoves.sort(new Comparator<Turn>() {
        public int compare(Turn o1, Turn o2) {
          return o1.getNumberBlockedSquares() - o2.getNumberBlockedSquares();
        }
      });
    } else {
      possibleMoves.sort(new Comparator<Turn>() {
        public int compare(Turn o1, Turn o2) {
          return o1.getRoomDiscovery() - o2.getRoomDiscovery();
        }
      });
    }
    if (possibleMoves.size() == 0) { //"pass" to implement
      return null;
    }
    return possibleMoves.get(0);
  }
 */
}
