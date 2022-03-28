package game.model;

import java.util.ArrayList;

/**
 * This class controls the AI player of the game.
 *
 * @author tiotto
 */

public class AI {

  public static Turn calculateNextMove(GameState gameState, Player player){
    switch (player.getType()) {
      case AI_HARD:
        return calculateNextHardMove(gameState, player);
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

  public static Turn calculateNextEasyMove(Board board, ArrayList<Poly> remainingPolys, boolean isFirstRound) {
    ArrayList<Turn> possibleMoves = board.getPossibleMoves(remainingPolys,isFirstRound);
    possibleMoves.sort((o1, o2) -> o1.getPoly().getSize() - o2.getPoly().getSize());
    if (possibleMoves.size() == 0) {
      return null;
    } else {
      return possibleMoves.get(0);
    }
  }


  public static Turn calculateNextMiddleMove(Board board, ArrayList<Poly> remainingPolys, boolean isFirstRound) {
    ArrayList<Turn> possibleMoves = board.getPossibleMoves(remainingPolys,isFirstRound);
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

  public static Turn calculateNextHardMove(GameState gameState, Player player){
    int bestVal = -1000;
    Turn bestTurn = null;
    for (Turn t : gameState.getBoard().getPossibleMoves(gameState.getRemainingPolys(player),
        gameState.isFirstRound())){
      int turnVal = minimax(gameState.tryTurn(t) , gameState.getNextColor(gameState.getColorFromPlayer(player)), 0,false, 3);
      if (turnVal > bestVal){
        bestTurn = t;
      }
    }
    return bestTurn;
  }

  public static int minimax(GameState gameState, Color c, int depth, boolean isMax, int h){
    if (depth == h){
      return evaluate(gameState.getBoard(), c);
    }

    if (isMax){
      int best = -1000;
      for (Turn t : gameState.getBoard().getPossibleMoves(gameState.getRemainingPolys(gameState.getPlayerFromColor(c)),gameState.isFirstRound())){
        best = Math.max(best, minimax(gameState.tryTurn(t), gameState.getNextColor(c), depth+1,  false,  h));
      }
      return best;
    } else {
      int best = 1000;
      for (Turn t : gameState.getBoard().getPossibleMoves(gameState.getRemainingPolys(gameState.getPlayerFromColor(c)),gameState.isFirstRound())){
        best = Math.min(best, minimax(gameState.tryTurn(t), gameState.getNextColor(c), depth+1, true, h));
      }
      return best;
    }
  }

  static int evaluate(Board board, Color c){
    return board.getScoreOfColorMiniMax(c);
  }
}
