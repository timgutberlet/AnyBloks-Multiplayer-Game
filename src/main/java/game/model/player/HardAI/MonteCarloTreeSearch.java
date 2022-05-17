package game.model.player.HardAI;

import game.model.Color;
import game.model.Debug;
import game.model.GameState;
import game.model.Turn;
import java.util.ArrayList;

/**
 * @author tiotto
 * @date 16.05.2022
 */
public class MonteCarloTreeSearch {
  static final int MAX_DEPTH = 5;
  static final int MAX_TIME_MILLI = 3000;

  public static Turn findNextMove(GameState state){
    long end = System.currentTimeMillis() + MAX_TIME_MILLI;
    Tree tree = new Tree(state);
    Node rootNode = tree.getRoot();

    while (System.currentTimeMillis() < end){
      Node promisingNode = selectPromisingNode(rootNode);
      if (!promisingNode.getState().getGameState().checkEnd()){
        expandNode(promisingNode);
      }
      Node nodeToExplore = promisingNode;
      if (promisingNode.getChildArray().size() > 0){
        nodeToExplore = promisingNode.getRandomChildNode();
      }
      int[] result = simulateRandomPlayout(nodeToExplore);
      backPropagation(nodeToExplore, result);
    }
    Node winnerNode = rootNode.getChildWithMaxScore();
    if(winnerNode != null){
      return winnerNode.getState().getPlayedTurn();
    }
    return null;
  }

  private static Node selectPromisingNode(Node rootNode){
    Node node = rootNode;
    while(node.getChildArray().size() != 0){
      node = UpperConfidenceTree.findBestNodeWithUCT(node);
    }
    return node;
  }

  private static void expandNode(Node node){
    ArrayList<NodeState> possibleStates = node.getState().getAllPossibleStates();
    possibleStates.forEach(state -> {Node newNode = new Node(node, state.getGameState(), state.playedTurn);
      newNode.setParent(node);
      node.getChildArray().add(newNode);
    });
  }

  private static void backPropagation(Node nodeToExplore, int[] result){
    Node tempNode = nodeToExplore;
    while (tempNode.getParent() != null){
      tempNode.getState().incrementVisit();
      tempNode.getState().addScore(result[colorToInt(tempNode.getState().getPlayedTurn().getColor())]);
      tempNode = tempNode.getParent();
    }
  }

  private static int[] simulateRandomPlayout(Node node){
    int depth = 1;
    Node tempNode = node.clone();
    NodeState tempState = tempNode.getState();
    boolean gameEnded = tempState.getGameState().checkEnd();
    if(gameEnded){
      int[] result = tempState.getGameState().getScores();
      for (Color c : tempState.getGameState().getWinners()){
        result[colorToInt(c)] = getMaxScore(tempState.gameState);
      }
    }
    while (!gameEnded && depth < MAX_DEPTH){
      depth++;
      tempState.randomPlay();
      gameEnded = tempState.getGameState().checkEnd();
    }
    return tempState.getGameState().getScores();
  }

  private static int getMaxScore(GameState gameState){
    switch(gameState.getGameMode().getName()){
      case "CLASSIC": return 89;
      case "DUO": return 89;
      case "JUNIOR": return 88;
      case "TRIGON": return 98;
      default:
        Debug.printMessage("falscher Gamemode bei AIHard");
        return 0;
    }
  }

  private static int colorToInt(Color c){
    switch (c){
      case RED -> {
        return 0;
      }
      case BLUE -> {
        return 1;
      }
      case GREEN -> {
        return 2;
      }
      case YELLOW -> {
        return 3;
      }
      default ->{ return 4;
      }
    }
  }
}


