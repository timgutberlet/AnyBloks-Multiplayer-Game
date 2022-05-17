package game.model.player.HardAI;

import java.util.Collections;
import java.util.Comparator;

/**
 * @author tiotto
 * @date 16.05.2022
 */
public class UpperConfidenceTree {

  public static double uctValue(int totalVisit, double nodeWinScore, int nodeVisit){
    if(nodeVisit == 0){
      return Integer.MAX_VALUE;
    }
    return (nodeWinScore / (double) nodeVisit) + 1.41 * Math.sqrt(Math.log(totalVisit) / (double) nodeVisit);
  }

  public static Node findBestNodeWithUCT(Node node){
    int parentVisit = node.getState().getVisitCount();
    return Collections.max(node.getChildArray(), Comparator.comparing(c -> uctValue(parentVisit, c.getState().getWinScore(), c.getState().getVisitCount())));
  }
}
