package game.model.player.HardAi.MonteCarloTreeSearch;

import game.model.GameState;

/**
 * represents the tree.
 *
 * @author tiotto
 * @date 16.05.2022
 */
public class Tree {

  /**
   * root node of the tree.
   */
  Node root;

  /**
   * generates a new tree out of the current game state.
   *
   * @param state given game state
   */
  public Tree(GameState state) {
    this.root = new Node(null, state, null);
  }

  /**
   * gives back the root.
   *
   * @return root node
   */
  public Node getRoot() {
    return root;
  }
}
