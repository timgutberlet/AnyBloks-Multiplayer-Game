package game.model.player.HardAi.MonteCarloTreeSearch;

import game.model.GameState;
import game.model.Turn;
import game.model.player.Ai;
import java.util.ArrayList;

/**
 * represents the node state.
 *
 * @author tiotto
 * @date 16.05.2022
 */
public class NodeState {

  /**
   * current game state (after the turn is played).
   */
  GameState gameState;

  /**
   * turn that is represented by the node.
   */
  Turn playedTurn;
  /**
   * number of visits by the algorithm.
   */
  int visitCount;
  /**
   * win score as an evaluation how good the turn represented by the node is.
   */
  double winScore;

  /**
   * creating a new node out of the turn and the resulting game stae.
   *
   * @param state game state
   * @param turn  turn
   */
  public NodeState(GameState state, Turn turn) {
    this.gameState = state;
    this.playedTurn = turn;
    visitCount = 0;
    winScore = 0;
  }

  GameState getGameState() {
    return this.gameState;
  }

  Turn getPlayedTurn() {
    return playedTurn;
  }

  int getVisitCount() {
    return visitCount;
  }

  double getWinScore() {
    return winScore;
  }

  void incrementVisit() {
    visitCount++;
  }

  void addScore(int score) {
    winScore += score;
  }

  /**
   * generates a list for possible child node states.
   *
   * @return list of possible child node states
   */
  public ArrayList<NodeState> getAllPossibleStates() {
    ArrayList<NodeState> res = new ArrayList<>();
    ArrayList<Turn> possibleMoves = gameState.getBoard()
        .getPossibleMoves(gameState.getRemainingPolys(gameState.getPlayerCurrent()),
            gameState.isFirstRound());
    for (Turn t : possibleMoves) {
      GameState newGameState = gameState.tryTurn(t);
      res.add(new NodeState(newGameState, t));
    }
    return res;
  }

  /**
   * a random play is played within the game state.
   */
  public void randomPlay() {
    gameState.playTurn(Ai.calculateNextRandomMove(gameState.getBoard(),
        gameState.getRemainingPolys(gameState.getPlayerCurrent()), gameState.isFirstRound()));
  }


}
