package net;

import net.server.DbServer;
import org.junit.jupiter.api.Test;

/**
 * @author tbuscher
 */
public class DbScoreTrackingTest {

  @Test
  public void testDbScoreTracking(){
    try {
      DbServer dbServer = DbServer.getInstance();
      dbServer.resetDb();
      //Insert 2 dummy accounts
      dbServer.newAccount("testuser1", "unhashed");
      dbServer.newAccount("testuser2", "unhashed");

      //Insert 2 games
      String gameId1 = dbServer.insertGame("CLASSIC");
      String gameId2 = dbServer.insertGame("CLASSIC");

      //Add scores for both player for both games
      dbServer.insertScore(gameId1, "testuser1", 50);
      dbServer.insertScore(gameId1, "testuser2", 70);
      dbServer.insertScore(gameId2, "testuser1", 60);
      dbServer.insertScore(gameId2, "testuser2", 80);

      //Insert a GameSessionScore
      String gameSessionScoreId = dbServer.insertGameSessionScore();

      //Add the 2 gameScores to the GameSessionScore just inserted
      dbServer.insertGameSessionScore2Game(gameSessionScoreId, gameId1);
      dbServer.insertGameSessionScore2Game(gameSessionScoreId, gameId2);

      dbServer.getGameScores(gameId1);

    } catch (Exception e) {
      e.printStackTrace();
    }


  }

}
