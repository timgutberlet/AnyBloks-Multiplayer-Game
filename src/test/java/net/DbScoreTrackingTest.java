package net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import game.scores.GameScoreBoard;
import java.util.ArrayList;
import net.server.DbServer;
import org.junit.jupiter.api.Test;

/**
 * Tests that gameScores & gameSessionScores are put in, and read out of the DB correctly.
 * That is done by inserting 2 gameScores in one GameSessionScore and verifying them.
 *
 * @author tbuscher
 */
public class DbScoreTrackingTest {

  @Test
  public void testDbScoreTracking() {
    try {
      DbServer dbServer = DbServer.getInstance();
      dbServer.resetDb();
      //Make sure the 2 dummy accounts in use are not already there
      dbServer.deleteAccount("testuser1");
      dbServer.deleteAccount("testuser2");

      //Insert the 2 dummy accounts
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

      GameScoreBoard fetchedBoard = dbServer.getGameScores(gameId1);

      //Check that the scoreboard contains the correct values
      assertEquals(fetchedBoard.getGamemode(), "CLASSIC");
      assertEquals(50, fetchedBoard.getPlayerScores().get("testuser1"));
      assertEquals(70, fetchedBoard.getPlayerScores().get("testuser2"));

      ArrayList<GameScoreBoard> gameSessionScores = dbServer.getGameSessionScores(
          gameSessionScoreId);

      //Verfiy that both gameScores that belong to the gameSessionScore are loaded correctly
      assertTrue(gameSessionScores.size() == 2);
      assertEquals(fetchedBoard.getPlayerScores(), gameSessionScores.get(0).getPlayerScores());
      assertEquals(gameSessionScores.get(1).getPlayerScores().get("testuser1"), 60);
      assertEquals(gameSessionScores.get(1).getPlayerScores().get("testuser2"), 80);
      assertEquals(gameSessionScores.get(1).getGamemode(), "CLASSIC");
    } catch (Exception e) {
      e.printStackTrace();
    }


  }

}
