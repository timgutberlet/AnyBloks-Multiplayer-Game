package game.scores;

import java.util.HashMap;
import net.server.DbServer;

/**
 * @author tbuscher
 */
public class GameSessionScoreBoard {

  public int gamesPlayed;

  public HashMap<String, Integer[]> usernames2pointsAndWins;

  public GameSessionScoreBoard(int gamesPlayed, HashMap<String, Integer[]> usernames2pointsAndWins){
    this.gamesPlayed = gamesPlayed;
    this.usernames2pointsAndWins = usernames2pointsAndWins;

  }

}
