package game.model.player.HardAi.MonteCarloTreeSearch;

import game.model.Debug;
import game.model.GameState;
import game.model.Turn;
import java.util.ArrayList;

/**
 * represents one node of a monte carlo tree search.
 *
 * @author tiotto
 * @date 16.05.2022
 */
public class Node {

  /**
   * state of the node.
   */
  NodeState state;

  /**
   * parent node.
   */
  Node parent;

  /**
   * list of child nodes.
   */
  ArrayList<Node> childArray;

  /**
   * creates a node out of the parent node and the initializing components of the node state.
   *
   * @param parent     parent
   * @param state      gameState
   * @param playedTurn last played turn to get to the gameState
   */
  public Node(Node parent, GameState state, Turn playedTurn) {
    this.parent = parent;
    this.state = new NodeState(state, playedTurn);
    this.childArray = new ArrayList<>();
  }

  public NodeState getState() {
    return state;
  }

  public void setState(NodeState state) {
    this.state = state;
  }

  public Node getParent() {
    return parent;
  }

  public void setParent(Node parent) {
    this.parent = parent;
  }

  public ArrayList<Node> getChildArray() {
    return childArray;
  }

  public void setChildArray(ArrayList<Node> childArray) {
    this.childArray = childArray;
  }

  /**
   * gives back a random picked child.
   *
   * @return random picked child.
   */
  public Node getRandomChildNode() {
    return childArray.get((int) (Math.random() * childArray.size()));
  }

  /**
   * gives back the child with the highest score.
   *
   * @return child with the highest score.
   */
  public Node getChildWithMaxScore() {
    double max = Integer.MIN_VALUE;
    Node winner = null;
    for (Node child : childArray) {
      if (child.getState().getWinScore() > max) {
        max = child.getState().getWinScore();
        winner = child;
      }
    }
    Debug.printMessage("Max Score: " + max);
    if (winner != null) {
      Debug.printMessage("Visits: " + winner.getState().getVisitCount());
    }
    return winner;
  }

  /**
   * deep clones a node.
   *
   * @return clones node
   */
  public Node clone() {
    if (getState().getPlayedTurn() == null) {
      return new Node(parent, getState().getGameState().clone(), null);
    }
    return new Node(parent, getState().getGameState().clone(), getState().getPlayedTurn().clone());
  }
}
