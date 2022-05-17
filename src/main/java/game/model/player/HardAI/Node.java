package game.model.player.HardAI;

import game.model.Debug;
import game.model.GameState;
import game.model.Turn;
import java.util.ArrayList;

/**
 * @author tiotto
 * @date 16.05.2022
 */
public class Node {
  NodeState state;
  Node parent;
  ArrayList<Node> childArray;

  public Node(Node parent, GameState state, Turn playedTurn){
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

  public Node getRandomChildNode(){
    return childArray.get((int) (Math.random() * childArray.size()));
  }

  public Node getChildWithMaxScore(){
    double max = Integer.MIN_VALUE;
    Node winner = null;
    for (Node child: childArray){
      if (child.getState().getWinScore() > max){
        max = child.getState().getWinScore();
        winner = child;
      }
    }
    System.out.println("Max Score: "+ max);
    if (winner != null){
      System.out.println("Visits: " + winner.getState().getVisitCount());
    }
    return winner;
  }

  public Node clone(){
    if (getState().getPlayedTurn() == null){
      return new Node(parent, getState().getGameState().clone(), null);
    }
    return new Node(parent, getState().getGameState().clone(), getState().getPlayedTurn().clone());
  }
}
