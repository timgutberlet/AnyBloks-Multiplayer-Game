package game.model;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * This class controls the AI player of the game.
 *
 * @author tiotto
 */

public class AI {

  public static Turn calculateNextMove(Player player, GameLogic gameLogic) {
    switch (player.getType()) {
      case AI_EASY:
        return calculateNextEasyMove(player, gameLogic);
      case AI_MIDDLE:
        return calculateNextMiddleMove(player, gameLogic);
      case AI_HARD:
        return calculateNextHardMove(player, gameLogic);
      default:
        System.out.println("AI move for human player");

        return null;
    }
  }

  public static Turn calculateNextEasyMove(Player player, GameLogic gameLogic) {
    ArrayList<Turn> possibleMoves = gameLogic.getPossibleMoves(player);
    System.out.println(gameLogic.getColorFromPlayer(player));
    System.out.println(possibleMoves.size());
    System.out.println();
    if (possibleMoves.size() == 0) {
      return null;
    } else {
      System.out.println(possibleMoves.get(0));
      System.out.println();
      return possibleMoves.get(0);
    }
  }

  public static Turn calculateNextMiddleMove(Player player, GameLogic gameLogic) {
    ArrayList<Turn> possibleMoves = gameLogic.getPossibleMoves(player);
    for (Turn turn : possibleMoves) {
      gameLogic.assignNumberBlockedSquares(turn);
    }
    System.out.println(gameLogic.getColorFromPlayer(player));
    System.out.println(possibleMoves.size());
    System.out.println();
    possibleMoves.sort(new Comparator<Turn>() {
      public int compare(Turn o1, Turn o2) {
        return o1.getNumberBlockedSquares() - o2.getNumberBlockedSquares();
      }
    });
    if (possibleMoves.size() == 0) { //"pass" to implement
      return null;
    }
    System.out.println(possibleMoves.get(0));
    System.out.println();
    return possibleMoves.get(0);
  }

  public static Turn calculateNextHardMove(Player player, GameLogic gameLogic) {
    ArrayList<Turn> possibleMoves = gameLogic.getPossibleMoves(player);
    for (Turn turn : possibleMoves) {
      gameLogic.assignNumberBlockedSquares(turn);
      gameLogic.assignRoomDiscovery(turn);
    }

    int currentRoomDiscovery = GameLogic.occupiedHeight(gameLogic.getColorFromPlayer(player),
        gameLogic.getGameState().getBoard(), player.getStartX())
        + GameLogic.occupiedHeight(gameLogic.getColorFromPlayer(player),
        gameLogic.getGameState().getBoard(), player.getStartY());

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


}
