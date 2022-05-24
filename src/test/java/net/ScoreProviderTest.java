package net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import game.scores.GameScoreBoard;
import game.scores.GameSessionScoreBoard;
import game.scores.ScoreProvider;
import net.server.DbServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * This test ensures that results delivered by the scoreProvider are correct.
 *
 * @author tbuscher
 */
public class ScoreProviderTest {

  @BeforeAll
  public static void beforeAll() {
    //Ensures that the database works as intended, and some scores are present.
    DbScoreTrackingTest dbScoreTrackingTest = new DbScoreTrackingTest();
    dbScoreTrackingTest.testDbScoreTracking();
  }

  @Test
  public void testScoreProvider() {
    DbServer dbServer = null;
    try {
      dbServer = DbServer.getInstance();
    } catch (Exception e) {
      e.printStackTrace();
    }
    assertNotNull(dbServer);

    String lastGameId = dbServer.getLastGameId();
    GameScoreBoard gameScoreBoard = dbServer.getGameScores(lastGameId);
    //The ScoreProvider has to fetch the same GameScoreBoard as the db directly
    assertEquals(gameScoreBoard.getGamemode(), ScoreProvider.getLastGameScoreBoard().getGamemode());
    assertEquals(gameScoreBoard.getPlayerScores(),
        ScoreProvider.getLastGameScoreBoard().getPlayerScores());

    String lastWinner = ScoreProvider.getWinner(gameScoreBoard);
    //as the dbScoreTrackingTest inserts a game, the values there have to show up here.
    assertEquals("testuser2", lastWinner);

    //Let the ScoreProvider create a GameSessionScoreBoard based off the last gameId
    GameSessionScoreBoard gameSessionScoreBoard = ScoreProvider.getGameSessionScoreBoard(
        new String[]{lastGameId});
    assertEquals(1, gameSessionScoreBoard.gamesPlayed);
    //testuser2 won the lastInserted game,
    //so his entry in the Hashmap tracking username, wins and scores should give 1 for wins
    assertEquals(1, gameSessionScoreBoard.usernames2pointsAndWins.get("testuser2")[1]);

  }


}
