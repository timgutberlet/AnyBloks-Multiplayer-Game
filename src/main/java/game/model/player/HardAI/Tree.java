package game.model.player.HardAI;

import game.model.GameState;

/**
 * @author tiotto
 * @date 16.05.2022
 */
public class Tree {
  Node root;

  public Tree(GameState state){
    this.root = new Node(null, state, null);
  }

  public Node getRoot() {
    return root;
  }
}
