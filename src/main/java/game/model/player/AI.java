package game.model.player;

import game.model.Debug;
import game.model.GameState;
import game.model.Turn;
import game.model.board.Board;
import game.model.player.HardAI.MiniMax.MiniMax;
import game.model.player.HardAI.MonteCarloTreeSearch.MonteCarloTreeSearch;
import game.model.polygon.Poly;
import java.util.ArrayList;

/**
 * This class controls the AI player of the game.
 *
 * @author tiotto
 */

public class AI {

  static int[][] roundSections = new int[][] {{5, 20}, //Classic Gamemode
      {2,16}, //Duo Gamemode
      {2,16}, //Junior Gamemode
      {6, 23}};   //Trigon Gamemode

  /**
   * calculated the next move for an AI Player depending on the set difficulty.
   *
   * @param gameState current gameState of the game
   * @param player    considered player
   * @return "best turn"
   */
  public static Turn calculateNextMove(GameState gameState, Player player) {
    switch (player.getType()) {
      case AI_HARD:
        return calculateNextHardMove(gameState, player);
      case AI_MIDDLE:
        return calculateNextMiddleMove(gameState.getBoard(), gameState.getRemainingPolys(player),
            gameState.isFirstRound());
      case AI_EASY:
        return calculateNextEasyMove(gameState.getBoard(), gameState.getRemainingPolys(player),
            gameState.isFirstRound());
      case AI_RANDOM:
        return calculateNextRandomMove(gameState.getBoard(), gameState.getRemainingPolys(player),
            gameState.isFirstRound());
      default:
        System.out.println("AI move for human player");
        return null;
    }
  }

  /**
   * chooses out of the possible next moves a random one
   *
   * @param board          considered board
   * @param remainingPolys remaining polys of the player which the calculation is for
   * @param isFirstRound   boolean, if they are in the first round
   * @return random turn
   */
  public static Turn calculateNextRandomMove(Board board, ArrayList<Poly> remainingPolys,
      boolean isFirstRound) {
    ArrayList<Turn> possibleMoves = board.getPossibleMoves(remainingPolys, isFirstRound);
    int rand = (int) (Math.random() * possibleMoves.size());
    if (possibleMoves.size() == 0) {
      return null;
    } else {
      return possibleMoves.get(rand);
    }
  }

  /**
   * calculates the next easy move through sorting the possible moves after the size of the poly.
   *
   * @param board          considered board
   * @param remainingPolys remaining polys of the player which the calculation is for
   * @param isFirstRound   boolean, if they are in the first round
   * @return "best" turn
   */
  public static Turn calculateNextEasyMove(Board board, ArrayList<Poly> remainingPolys,
      boolean isFirstRound) {
    Debug.printMessage(remainingPolys.size() + " remaining Polys when calculating the next move");
    Debug.printMessage(board.toString());
    ArrayList<Turn> possibleMoves = board.getPossibleMoves(remainingPolys, isFirstRound);
    Debug.printMessage(possibleMoves.size() + " possible moves when calculating the next move");
    possibleMoves.sort((o1, o2) -> o2.getPoly().getSize() - o1.getPoly().getSize());
    int rand = 0;
    for (int i = 0; i < possibleMoves.size(); i++) {
      if (possibleMoves.get(0).getPoly().getSize() > possibleMoves.get(i).getPoly().getSize()) {
        rand = (int) (Math.random() * i);
        break;
      }
    }
    if (possibleMoves.size() == 0) {
      Debug.printMessage("I think there are no possible moves...");
      return null;
    } else {
      return possibleMoves.get(rand);
    }
  }

  /**
   * calculates the next move through sorting the possible moves after the blocked fields of the
   * opponents as the first criteria and the size of the poly as the second criteria.
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
    possibleMoves.sort((o1, o2) -> o2.getPoly().getSize() - o1.getPoly().getSize());
    possibleMoves.sort((o1, o2) -> o2.getNumberBlockedFields() - o1.getNumberBlockedFields());
    int rand = 0;
    for (int i = 0; i < possibleMoves.size(); i++) {
      if (possibleMoves.get(0).getNumberBlockedFields() > possibleMoves.get(i)
          .getNumberBlockedFields()
          || possibleMoves.get(0).getPoly().getSize() > possibleMoves.get(i).getPoly().getSize()) {
        rand = (int) (Math.random() * i);
        break;
      }
    }
    if (possibleMoves.size() == 0) {
      return null;
    }
    return possibleMoves.get(rand);
  }

  /**
   * calculates the next move out of different tactics through the game.
   * First the algorithm tries to get as spread as possible with its polys.
   * Second the algorithm tries to block the other players moves.
   * Third it evaluates the best move till the end of the game.
   *
   * @param gameState current gameState
   * @param player    player, for whom the next move is calculated
   * @return the next "best" move
   */
  public static Turn calculateNextHardMove(GameState gameState, Player player) {
    int gameModeNumber = 0;
    switch (gameState.getGameMode().getName()) {
      case "CLASSIC":
        gameModeNumber = 0;
        break;
      case "DUO":
        gameModeNumber = 1;
        break;
      case "JUNIOR":
        gameModeNumber = 2;
        break;
      case "TRIGON":
        gameModeNumber = 3;
        break;
    }
    if (gameState.getRound() < roundSections[gameModeNumber][0] + 1) {
      return calculateNextHardMoveRoomDiscovery(gameState, player);
    } else if (gameState.getRound() < roundSections[gameModeNumber][1] + 1) {
      return calculateNextHardMoveAggressive(gameState, player);
    } else {
      return calculateNextHardMoveMCTS(gameState, player);
    }
  }

  /**
   * calculates the next move which offers the most room discovery, means is the most spread one compared to the situation before.
   * @param gameState current game state
   * @param player ai player which needs to play
   * @return "best" turn
   */
  public static Turn calculateNextHardMoveRoomDiscovery(GameState gameState, Player player){
      ArrayList<Turn> possibleMoves = gameState.getBoard().getPossibleMoves(gameState.getRemainingPolys(player), gameState.isFirstRound());
      for (Turn turn : possibleMoves) {
        gameState.assignRoomDiscovery(turn);
        gameState.getBoard().assignNumberBlockedFields(turn);
      }
      possibleMoves.sort((o1, o2) -> o2.getPoly().getSize() - o1.getPoly().getSize());
      possibleMoves.sort((o1, o2) -> o2.getNumberBlockedFields() - o1.getNumberBlockedFields());
      possibleMoves.sort((o1, o2) -> o2.getRoomDiscovery() - o1.getRoomDiscovery());
      int rand = 0;
      for (int i = 0; i < possibleMoves.size(); i++) {
        if (possibleMoves.get(0).getRoomDiscovery() > possibleMoves.get(i)
            .getRoomDiscovery()
            || possibleMoves.get(0).getPoly().getSize() > possibleMoves.get(i).getPoly().getSize()) {
          rand = (int) (Math.random() * i);
          break;
        }
      }
      if (possibleMoves.size() == 0) {
        return null;
      }
      return possibleMoves.get(rand);
  }

  /**
   * calculates the next move, which can block as most fields, where opponents can place polys in the future, as possible.
   * @param gameState current state
   * @param player ai player which needs to play
   * @return "best" turn
   */
  public static Turn calculateNextHardMoveAggressive(GameState gameState, Player player){
    return calculateNextMiddleMove(gameState.getBoard(), gameState.getRemainingPolys(player),
        gameState.isFirstRound());
  }

  /**
   * calculates the next move through sorting the possible moves after the weighted blocked fields of the
   * opponents as the first criteria and the size of the poly as the second criteria.
   *
   * @param gameState current state
   * @param player ai player which needs to play
   * @return "best" turn
   */
  public static Turn calculateNextHardMoveReallyAggressive(GameState gameState, Player player){
    ArrayList<Turn> possibleMoves = gameState.getBoard().getPossibleMoves(gameState.getRemainingPolys(player), gameState.isFirstRound());
    for (Turn turn : possibleMoves) {
      gameState.assignNumberBlockedFieldsWeighted(turn);
    }
    possibleMoves.sort((o1, o2) -> o2.getPoly().getSize() - o1.getPoly().getSize());
    possibleMoves.sort((o1, o2) -> o2.getNumberBlockedFieldsWeighted() - o1.getNumberBlockedFieldsWeighted());
    int rand = 0;
    for (int i = 0; i < possibleMoves.size(); i++) {
      if (possibleMoves.get(0).getNumberBlockedFieldsWeighted() > possibleMoves.get(i)
          .getNumberBlockedFieldsWeighted()
          || possibleMoves.get(0).getPoly().getSize() > possibleMoves.get(i).getPoly().getSize()) {
        rand = (int) (Math.random() * i);
        break;
      }
    }
    if (possibleMoves.size() == 0) {
      return null;
    }
    return possibleMoves.get(rand);
  }

  /**
   * calculates the next move, which is calculated by the monte carlo tree search
   * @param gameState current state
   * @param player ai player which needs to play
   * @return "best" turn
   */
  public static Turn calculateNextHardMoveMCTS(GameState gameState, Player player){
    return MonteCarloTreeSearch.findNextMove(gameState);
  }

  /**
   * calculates the next move, which is calculated by the minimax algorithm
   * @param gameState current state
   * @param player ai player which needs to play
   * @return "best" turn
   */
  public static Turn calculateNextHardMoveMiniMax(GameState gameState, Player player){
    return MiniMax.calculateNextHardMoveMiniMax(gameState,player);
  }

  /**
   * sets the round sections for a specific game mode
   * @param a number of the game mode
   * @param value new round sections
   */
  public static void setRoundSections(int a, int[] value){
    roundSections[a] = value;
  }

}
