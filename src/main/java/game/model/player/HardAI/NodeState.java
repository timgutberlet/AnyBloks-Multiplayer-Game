package game.model.player.HardAI;

import game.model.Game;
import game.model.GameState;
import game.model.Turn;
import game.model.player.AI;
import java.util.ArrayList;

/**
 * @author tiotto
 * @date 16.05.2022
 */
public class NodeState {
  GameState gameState;
  Turn playedTurn;
  int visitCount;
  double winScore;

  public NodeState(GameState state, Turn turn){
    this.gameState = state;
    this.playedTurn = turn;
    visitCount = 0;
    winScore = 0;
  }

  GameState getGameState(){
    return this.gameState;
  }

  Turn getPlayedTurn(){
    return playedTurn;
  }

  int getVisitCount(){
    return visitCount;
  }

  double getWinScore(){
    return winScore;
  }

  void incrementVisit(){
    visitCount++;
  }

  void addScore(int score){
    winScore += score;
  }

  public ArrayList<NodeState> getAllPossibleStates(){
    ArrayList<NodeState> res = new ArrayList<>();
    ArrayList<Turn> possibleMoves = gameState.getBoard().getPossibleMoves(gameState.getRemainingPolys(gameState.getPlayerCurrent()),gameState.isFirstRound());
    for (Turn t : possibleMoves){
      GameState newGameState = gameState.tryTurn(t);
      res.add(new NodeState(newGameState, t));
    }
    return res;
  }

  public void randomPlay(){
    gameState.playTurn(AI.calculateNextRandomMove(gameState.getBoard(), gameState.getRemainingPolys(gameState.getPlayerCurrent()),gameState.isFirstRound()));
  }


}
