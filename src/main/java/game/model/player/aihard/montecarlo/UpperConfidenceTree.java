package game.model.player.aihard.montecarlo;

import java.util.Collections;
import java.util.Comparator;

/**
 * collects all methods used for the upper confidence tree.
 *
 * @author tiotto
 * @date 16.05.2022
 */
public class UpperConfidenceTree {

  /**
   * evaluating a node after its uct value.
   *
   * @param totalVisit   number of the visits of the parent node
   * @param nodeWinScore number of the win score
   * @param nodeVisit    number of the visits of the node
   * @return uct value
   */
  public static double uctValue(int totalVisit, double nodeWinScore, int nodeVisit) {
    if (nodeVisit == 0) {
      return Integer.MAX_VALUE;
    }
    return (nodeWinScore / (double) nodeVisit) + 1.41 * Math.sqrt(
        Math.log(totalVisit) / (double) nodeVisit);
  }

  /**
   * finds the best child node using the uct value.
   *
   * @param node parent node
   * @return best child node
   */
  public static Node findBestNodeWithUct(Node node) {
    int parentVisit = node.getState().getVisitCount();
    return Collections.max(node.getChildArray(), Comparator.comparing(
        c -> uctValue(parentVisit, c.getState().getWinScore(), c.getState().getVisitCount())));
  }
}
