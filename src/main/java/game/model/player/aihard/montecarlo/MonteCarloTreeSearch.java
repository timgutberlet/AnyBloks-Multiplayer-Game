package game.model.player.aihard.montecarlo;

import game.model.Color;
import game.model.Debug;
import game.model.GameState;
import game.model.Turn;
import java.util.ArrayList;

/**
 * central class of the representation of the monte carlo tree search. for the monte carlo tree
 * search https://www.baeldung.com/java-monte-carlo-tree-search was used as an orientation.
 *
 * @author tiotto
 * @date 16.05.2022
 */

public class MonteCarloTreeSearch {

  static final int MAX_DEPTH = 5;
  static final int MAX_TIME_MILLI = 3000;

  /**
   * finds based on the current game state the next potentially best move.
   *
   * @param state current game state
   * @return returns the potentially best move for the current player
   */
  public static Turn findNextMove(GameState state) {
    long end = System.currentTimeMillis() + MAX_TIME_MILLI;
    Tree tree = new Tree(state);
    Node rootNode = tree.getRoot();

    while (System.currentTimeMillis() < end) {
      Node promisingNode = selectPromisingNode(rootNode);
      if (!promisingNode.getState().getGameState().checkEnd()) {
        expandNode(promisingNode);
      }
      Node nodeToExplore = promisingNode;
      if (promisingNode.getChildArray().size() > 0) {
        nodeToExplore = promisingNode.getRandomChildNode();
      }
      int[] result = simulateRandomPlayout(nodeToExplore);
      backPropagation(nodeToExplore, result);
    }
    Node winnerNode = rootNode.getChildWithMaxScore();
    if (winnerNode != null) {
      return winnerNode.getState().getPlayedTurn();
    }
    return null;
  }

  /**
   * selects the next most promising node based on the upper confidence tree.
   *
   * @param rootNode root node
   * @return most promising next node
   */
  private static Node selectPromisingNode(Node rootNode) {
    Node node = rootNode;
    while (node.getChildArray().size() != 0) {
      node = UpperConfidenceTree.findBestNodeWithUCT(node);
    }
    return node;
  }

  /**
   * expands the new node with the generation of all its possible child nodes.
   *
   * @param node
   */
  private static void expandNode(Node node) {
    ArrayList<NodeState> possibleStates = node.getState().getAllPossibleStates();
    possibleStates.forEach(state -> {
      Node newNode = new Node(node, state.getGameState(), state.playedTurn);
      newNode.setParent(node);
      node.getChildArray().add(newNode);
    });
  }

  /**
   * through back propagation a given result will be transferred to all involved nodes.
   *
   * @param nodeToExplore leave node
   * @param result        given result
   */
  private static void backPropagation(Node nodeToExplore, int[] result) {
    Node tempNode = nodeToExplore;
    while (tempNode.getParent() != null) {
      tempNode.getState().incrementVisit();
      tempNode.getState()
          .addScore(result[colorToInt(tempNode.getState().getPlayedTurn().getColor())]);
      tempNode = tempNode.getParent();
    }
  }

  /**
   * simulates a random play till the end of the game or a given maximal depth.
   *
   * @param node given node from the state of which the game will be played randomly
   * @return results of the playout
   */
  private static int[] simulateRandomPlayout(Node node) {
    int depth = 1;
    Node tempNode = node.clone();
    NodeState tempState = tempNode.getState();
    boolean gameEnded = tempState.getGameState().checkEnd();
    if (gameEnded) {
      int[] result = tempState.getGameState().getScores();
      for (Color c : tempState.getGameState().getWinners()) {
        result[colorToInt(c)] = getMaxScore(tempState.gameState);
      }
    }
    while (!gameEnded && depth < MAX_DEPTH) {
      depth++;
      tempState.randomPlay();
      gameEnded = tempState.getGameState().checkEnd();
    }
    return tempState.getGameState().getScores();
  }

  /**
   * returns the maximum possible score for the game state based on the game mode.
   *
   * @param gameState given gameState
   * @return maximum possible score as the int
   */
  private static int getMaxScore(GameState gameState) {
    switch (gameState.getGameMode().getName()) {
      case "CLASSIC":
        return 89;
      case "DUO":
        return 89;
      case "JUNIOR":
        return 88;
      case "TRIGON":
        return 98;
      default:
        Debug.printMessage("falscher Gamemode bei AIHard");
        return 0;
    }
  }

  /**
   * converts a color into the corresponding integer.
   *
   * @param c color
   * @return integer
   */
  private static int colorToInt(Color c) {
    switch (c) {
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
      default -> {
        return 4;
      }
    }
  }
}


