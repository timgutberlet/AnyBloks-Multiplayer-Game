package game.model.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import game.model.Color;
import game.model.Turn;
import game.model.field.FieldSquare;
import game.model.gamemodes.GameModeClassic;
import game.model.gamemodes.GameModeDuo;
import game.model.gamemodes.GameModeJunior;
import game.model.polygon.Poly;
import game.model.polygon.PolySquare;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * @author tiotto
 * @date 15.05.2022
 */
class BoardSquareTest {

  @Nested
  @DisplayName("Test classic board")
  class TestClassicBoard {

    BoardSquare classic = new BoardSquare(new GameModeClassic());

    @Test
    void testSize() {
      assertEquals(classic.size, 20);
      assertEquals(classic.getBoard().size(), 400);
      for (FieldSquare fs : classic.getBoard()) {
        assertTrue(fs.getPos()[0] >= 0 && fs.getPos()[0] < 20 && fs.getPos()[1] >= 0
            && fs.getPos()[1] < 20);
      }
    }

    @Test
    void testColor() {
      for (FieldSquare fs : classic.getBoard()) {
        assertEquals(fs.getColor(), Color.WHITE);
      }
    }

    @Test
    void testStartFields() {
      assertTrue(classic.getStartFields().contains(new FieldSquare(0, 0)));
      assertTrue(classic.getStartFields().contains(new FieldSquare(19, 0)));
      assertTrue(classic.getStartFields().contains(new FieldSquare(0, 19)));
      assertTrue(classic.getStartFields().contains(new FieldSquare(19, 19)));
      assertEquals(classic.getStartFields().size(), 4);
    }
  }

  @Nested
  @DisplayName("Test duo board")
  class TestDuoBoard {

    BoardSquare duo = new BoardSquare(new GameModeDuo());

    @Test
    void testSize() {
      assertEquals(duo.size, 14);
      assertEquals(duo.getBoard().size(), 196);
      for (FieldSquare fs : duo.getBoard()) {
        assertTrue(fs.getPos()[0] >= 0 && fs.getPos()[0] < 14 && fs.getPos()[1] >= 0
            && fs.getPos()[1] < 14);
      }
    }

    @Test
    void testColor() {
      for (FieldSquare fs : duo.getBoard()) {
        assertEquals(fs.getColor(), Color.WHITE);
      }
    }

    @Test
    void testStartFields() {
      assertTrue(duo.getStartFields().contains(new FieldSquare(4, 4)));
      assertTrue(duo.getStartFields().contains(new FieldSquare(9, 9)));
      assertEquals(duo.getStartFields().size(), 2);
    }
  }

  @Nested
  @DisplayName("Test junior board")
  class TestJuniorBoard {

    BoardSquare junior = new BoardSquare(new GameModeJunior());

    @Test
    void testSize() {
      assertEquals(junior.size, 14);
      assertEquals(junior.getBoard().size(), 196);
      for (FieldSquare fs : junior.getBoard()) {
        assertTrue(fs.getPos()[0] >= 0 && fs.getPos()[0] < 14 && fs.getPos()[1] >= 0
            && fs.getPos()[1] < 14);
      }
    }

    @Test
    void testColor() {
      for (FieldSquare fs : junior.getBoard()) {
        assertEquals(fs.getColor(), Color.WHITE);
      }
    }

    @Test
    void testStartFields() {
      assertTrue(junior.getStartFields().contains(new FieldSquare(4, 4)));
      assertTrue(junior.getStartFields().contains(new FieldSquare(9, 9)));
      assertEquals(junior.getStartFields().size(), 2);
    }
  }

  @Nested
  @DisplayName("Test game mode independent methods")
  class TestOtherMethods {
    BoardSquare testBoard1;
    PolySquare testPoly1;
    Turn testTurn1;

    TestOtherMethods() { //creation of help objects
      ArrayList<FieldSquare> shape = new ArrayList<>();
      shape.add(new game.model.field.FieldSquare(2, 1, game.model.Color.RED));
      shape.add(new game.model.field.FieldSquare(1, 2, game.model.Color.RED));
      shape.add(new game.model.field.FieldSquare(1, 1, game.model.Color.RED));
      shape.add(new game.model.field.FieldSquare(0, 1, game.model.Color.RED));
      shape.add(new game.model.field.FieldSquare(1, 0, game.model.Color.RED));
      testPoly1 = new PolySquare(shape, Color.RED);
      testTurn1 = new Turn(testPoly1, new int[]{12, 2});

      BoardSquare bs = new BoardSquare(new GameModeClassic());
      bs.getBoard().clear();
      bs.getBoard().add(new FieldSquare(0, 0, Color.RED));
      bs.getBoard().add(new FieldSquare(0, 1, Color.WHITE));
      bs.getBoard().add(new FieldSquare(0, 2, Color.RED));
      bs.getBoard().add(new FieldSquare(0, 3, Color.WHITE));
      bs.getBoard().add(new FieldSquare(0, 4, Color.WHITE));
      bs.getBoard().add(new FieldSquare(0, 5, Color.WHITE));
      bs.getBoard().add(new FieldSquare(0, 6, Color.WHITE));
      bs.getBoard().add(new FieldSquare(0, 7, Color.WHITE));
      bs.getBoard().add(new FieldSquare(0, 8, Color.WHITE));
      bs.getBoard().add(new FieldSquare(0, 9, Color.WHITE));
      bs.getBoard().add(new FieldSquare(0, 10, Color.WHITE));
      bs.getBoard().add(new FieldSquare(0, 11, Color.WHITE));
      bs.getBoard().add(new FieldSquare(0, 12, Color.GREEN));
      bs.getBoard().add(new FieldSquare(0, 13, Color.WHITE));
      bs.getBoard().add(new FieldSquare(0, 14, Color.WHITE));
      bs.getBoard().add(new FieldSquare(0, 15, Color.WHITE));
      bs.getBoard().add(new FieldSquare(0, 16, Color.WHITE));
      bs.getBoard().add(new FieldSquare(0, 17, Color.WHITE));
      bs.getBoard().add(new FieldSquare(0, 18, Color.GREEN));
      bs.getBoard().add(new FieldSquare(0, 19, Color.GREEN));
      bs.getBoard().add(new FieldSquare(1, 0, Color.RED));
      bs.getBoard().add(new FieldSquare(1, 1, Color.RED));
      bs.getBoard().add(new FieldSquare(1, 2, Color.RED));
      bs.getBoard().add(new FieldSquare(1, 3, Color.WHITE));
      bs.getBoard().add(new FieldSquare(1, 4, Color.WHITE));
      bs.getBoard().add(new FieldSquare(1, 5, Color.WHITE));
      bs.getBoard().add(new FieldSquare(1, 6, Color.RED));
      bs.getBoard().add(new FieldSquare(1, 7, Color.RED));
      bs.getBoard().add(new FieldSquare(1, 8, Color.RED));
      bs.getBoard().add(new FieldSquare(1, 9, Color.WHITE));
      bs.getBoard().add(new FieldSquare(1, 10, Color.WHITE));
      bs.getBoard().add(new FieldSquare(1, 11, Color.WHITE));
      bs.getBoard().add(new FieldSquare(1, 12, Color.GREEN));
      bs.getBoard().add(new FieldSquare(1, 13, Color.GREEN));
      bs.getBoard().add(new FieldSquare(1, 14, Color.GREEN));
      bs.getBoard().add(new FieldSquare(1, 15, Color.WHITE));
      bs.getBoard().add(new FieldSquare(1, 16, Color.WHITE));
      bs.getBoard().add(new FieldSquare(1, 17, Color.WHITE));
      bs.getBoard().add(new FieldSquare(1, 18, Color.GREEN));
      bs.getBoard().add(new FieldSquare(1, 19, Color.GREEN));
      bs.getBoard().add(new FieldSquare(2, 0, Color.WHITE));
      bs.getBoard().add(new FieldSquare(2, 1, Color.WHITE));
      bs.getBoard().add(new FieldSquare(2, 2, Color.WHITE));
      bs.getBoard().add(new FieldSquare(2, 3, Color.RED));
      bs.getBoard().add(new FieldSquare(2, 4, Color.WHITE));
      bs.getBoard().add(new FieldSquare(2, 5, Color.WHITE));
      bs.getBoard().add(new FieldSquare(2, 6, Color.WHITE));
      bs.getBoard().add(new FieldSquare(2, 7, Color.RED));
      bs.getBoard().add(new FieldSquare(2, 8, Color.WHITE));
      bs.getBoard().add(new FieldSquare(2, 9, Color.RED));
      bs.getBoard().add(new FieldSquare(2, 10, Color.RED));
      bs.getBoard().add(new FieldSquare(2, 11, Color.WHITE));
      bs.getBoard().add(new FieldSquare(2, 12, Color.GREEN));
      bs.getBoard().add(new FieldSquare(2, 13, Color.WHITE));
      bs.getBoard().add(new FieldSquare(2, 14, Color.WHITE));
      bs.getBoard().add(new FieldSquare(2, 15, Color.GREEN));
      bs.getBoard().add(new FieldSquare(2, 16, Color.GREEN));
      bs.getBoard().add(new FieldSquare(2, 17, Color.GREEN));
      bs.getBoard().add(new FieldSquare(2, 18, Color.WHITE));
      bs.getBoard().add(new FieldSquare(2, 19, Color.GREEN));
      bs.getBoard().add(new FieldSquare(3, 0, Color.WHITE));
      bs.getBoard().add(new FieldSquare(3, 1, Color.RED));
      bs.getBoard().add(new FieldSquare(3, 2, Color.RED));
      bs.getBoard().add(new FieldSquare(3, 3, Color.RED));
      bs.getBoard().add(new FieldSquare(3, 4, Color.WHITE));
      bs.getBoard().add(new FieldSquare(3, 5, Color.RED));
      bs.getBoard().add(new FieldSquare(3, 6, Color.WHITE));
      bs.getBoard().add(new FieldSquare(3, 7, Color.RED));
      bs.getBoard().add(new FieldSquare(3, 8, Color.WHITE));
      bs.getBoard().add(new FieldSquare(3, 9, Color.WHITE));
      bs.getBoard().add(new FieldSquare(3, 10, Color.RED));
      bs.getBoard().add(new FieldSquare(3, 11, Color.RED));
      bs.getBoard().add(new FieldSquare(3, 12, Color.WHITE));
      bs.getBoard().add(new FieldSquare(3, 13, Color.WHITE));
      bs.getBoard().add(new FieldSquare(3, 14, Color.RED));
      bs.getBoard().add(new FieldSquare(3, 15, Color.GREEN));
      bs.getBoard().add(new FieldSquare(3, 16, Color.WHITE));
      bs.getBoard().add(new FieldSquare(3, 17, Color.GREEN));
      bs.getBoard().add(new FieldSquare(3, 18, Color.WHITE));
      bs.getBoard().add(new FieldSquare(3, 19, Color.WHITE));
      bs.getBoard().add(new FieldSquare(4, 0, Color.WHITE));
      bs.getBoard().add(new FieldSquare(4, 1, Color.RED));
      bs.getBoard().add(new FieldSquare(4, 2, Color.WHITE));
      bs.getBoard().add(new FieldSquare(4, 3, Color.WHITE));
      bs.getBoard().add(new FieldSquare(4, 4, Color.RED));
      bs.getBoard().add(new FieldSquare(4, 5, Color.RED));
      bs.getBoard().add(new FieldSquare(4, 6, Color.WHITE));
      bs.getBoard().add(new FieldSquare(4, 7, Color.WHITE));
      bs.getBoard().add(new FieldSquare(4, 8, Color.WHITE));
      bs.getBoard().add(new FieldSquare(4, 9, Color.WHITE));
      bs.getBoard().add(new FieldSquare(4, 10, Color.WHITE));
      bs.getBoard().add(new FieldSquare(4, 11, Color.RED));
      bs.getBoard().add(new FieldSquare(4, 12, Color.WHITE));
      bs.getBoard().add(new FieldSquare(4, 13, Color.RED));
      bs.getBoard().add(new FieldSquare(4, 14, Color.RED));
      bs.getBoard().add(new FieldSquare(4, 15, Color.WHITE));
      bs.getBoard().add(new FieldSquare(4, 16, Color.GREEN));
      bs.getBoard().add(new FieldSquare(4, 17, Color.WHITE));
      bs.getBoard().add(new FieldSquare(4, 18, Color.WHITE));
      bs.getBoard().add(new FieldSquare(4, 19, Color.WHITE));
      bs.getBoard().add(new FieldSquare(5, 0, Color.WHITE));
      bs.getBoard().add(new FieldSquare(5, 1, Color.WHITE));
      bs.getBoard().add(new FieldSquare(5, 2, Color.WHITE));
      bs.getBoard().add(new FieldSquare(5, 3, Color.WHITE));
      bs.getBoard().add(new FieldSquare(5, 4, Color.RED));
      bs.getBoard().add(new FieldSquare(5, 5, Color.RED));
      bs.getBoard().add(new FieldSquare(5, 6, Color.WHITE));
      bs.getBoard().add(new FieldSquare(5, 7, Color.WHITE));
      bs.getBoard().add(new FieldSquare(5, 8, Color.WHITE));
      bs.getBoard().add(new FieldSquare(5, 9, Color.WHITE));
      bs.getBoard().add(new FieldSquare(5, 10, Color.WHITE));
      bs.getBoard().add(new FieldSquare(5, 11, Color.WHITE));
      bs.getBoard().add(new FieldSquare(5, 12, Color.RED));
      bs.getBoard().add(new FieldSquare(5, 13, Color.GREEN));
      bs.getBoard().add(new FieldSquare(5, 14, Color.RED));
      bs.getBoard().add(new FieldSquare(5, 15, Color.RED));
      bs.getBoard().add(new FieldSquare(5, 16, Color.GREEN));
      bs.getBoard().add(new FieldSquare(5, 17, Color.WHITE));
      bs.getBoard().add(new FieldSquare(5, 18, Color.WHITE));
      bs.getBoard().add(new FieldSquare(5, 19, Color.WHITE));
      bs.getBoard().add(new FieldSquare(6, 0, Color.WHITE));
      bs.getBoard().add(new FieldSquare(6, 1, Color.RED));
      bs.getBoard().add(new FieldSquare(6, 2, Color.RED));
      bs.getBoard().add(new FieldSquare(6, 3, Color.RED));
      bs.getBoard().add(new FieldSquare(6, 4, Color.WHITE));
      bs.getBoard().add(new FieldSquare(6, 5, Color.WHITE));
      bs.getBoard().add(new FieldSquare(6, 6, Color.RED));
      bs.getBoard().add(new FieldSquare(6, 7, Color.RED));
      bs.getBoard().add(new FieldSquare(6, 8, Color.RED));
      bs.getBoard().add(new FieldSquare(6, 9, Color.RED));
      bs.getBoard().add(new FieldSquare(6, 10, Color.RED));
      bs.getBoard().add(new FieldSquare(6, 11, Color.WHITE));
      bs.getBoard().add(new FieldSquare(6, 12, Color.RED));
      bs.getBoard().add(new FieldSquare(6, 13, Color.GREEN));
      bs.getBoard().add(new FieldSquare(6, 14, Color.WHITE));
      bs.getBoard().add(new FieldSquare(6, 15, Color.WHITE));
      bs.getBoard().add(new FieldSquare(6, 16, Color.GREEN));
      bs.getBoard().add(new FieldSquare(6, 17, Color.WHITE));
      bs.getBoard().add(new FieldSquare(6, 18, Color.WHITE));
      bs.getBoard().add(new FieldSquare(6, 19, Color.WHITE));
      bs.getBoard().add(new FieldSquare(7, 0, Color.WHITE));
      bs.getBoard().add(new FieldSquare(7, 1, Color.WHITE));
      bs.getBoard().add(new FieldSquare(7, 2, Color.WHITE));
      bs.getBoard().add(new FieldSquare(7, 3, Color.RED));
      bs.getBoard().add(new FieldSquare(7, 4, Color.WHITE));
      bs.getBoard().add(new FieldSquare(7, 5, Color.WHITE));
      bs.getBoard().add(new FieldSquare(7, 6, Color.WHITE));
      bs.getBoard().add(new FieldSquare(7, 7, Color.WHITE));
      bs.getBoard().add(new FieldSquare(7, 8, Color.WHITE));
      bs.getBoard().add(new FieldSquare(7, 9, Color.WHITE));
      bs.getBoard().add(new FieldSquare(7, 10, Color.WHITE));
      bs.getBoard().add(new FieldSquare(7, 11, Color.RED));
      bs.getBoard().add(new FieldSquare(7, 12, Color.RED));
      bs.getBoard().add(new FieldSquare(7, 13, Color.GREEN));
      bs.getBoard().add(new FieldSquare(7, 14, Color.WHITE));
      bs.getBoard().add(new FieldSquare(7, 15, Color.WHITE));
      bs.getBoard().add(new FieldSquare(7, 16, Color.GREEN));
      bs.getBoard().add(new FieldSquare(7, 17, Color.GREEN));
      bs.getBoard().add(new FieldSquare(7, 18, Color.WHITE));
      bs.getBoard().add(new FieldSquare(7, 19, Color.WHITE));
      bs.getBoard().add(new FieldSquare(8, 0, Color.WHITE));
      bs.getBoard().add(new FieldSquare(8, 1, Color.BLUE));
      bs.getBoard().add(new FieldSquare(8, 2, Color.WHITE));
      bs.getBoard().add(new FieldSquare(8, 3, Color.RED));
      bs.getBoard().add(new FieldSquare(8, 4, Color.WHITE));
      bs.getBoard().add(new FieldSquare(8, 5, Color.WHITE));
      bs.getBoard().add(new FieldSquare(8, 6, Color.WHITE));
      bs.getBoard().add(new FieldSquare(8, 7, Color.WHITE));
      bs.getBoard().add(new FieldSquare(8, 8, Color.WHITE));
      bs.getBoard().add(new FieldSquare(8, 9, Color.WHITE));
      bs.getBoard().add(new FieldSquare(8, 10, Color.GREEN));
      bs.getBoard().add(new FieldSquare(8, 11, Color.GREEN));
      bs.getBoard().add(new FieldSquare(8, 12, Color.RED));
      bs.getBoard().add(new FieldSquare(8, 13, Color.GREEN));
      bs.getBoard().add(new FieldSquare(8, 14, Color.WHITE));
      bs.getBoard().add(new FieldSquare(8, 15, Color.GREEN));
      bs.getBoard().add(new FieldSquare(8, 16, Color.WHITE));
      bs.getBoard().add(new FieldSquare(8, 17, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(8, 18, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(8, 19, Color.WHITE));
      bs.getBoard().add(new FieldSquare(9, 0, Color.WHITE));
      bs.getBoard().add(new FieldSquare(9, 1, Color.BLUE));
      bs.getBoard().add(new FieldSquare(9, 2, Color.BLUE));
      bs.getBoard().add(new FieldSquare(9, 3, Color.BLUE));
      bs.getBoard().add(new FieldSquare(9, 4, Color.RED));
      bs.getBoard().add(new FieldSquare(9, 5, Color.RED));
      bs.getBoard().add(new FieldSquare(9, 6, Color.RED));
      bs.getBoard().add(new FieldSquare(9, 7, Color.RED));
      bs.getBoard().add(new FieldSquare(9, 8, Color.GREEN));
      bs.getBoard().add(new FieldSquare(9, 9, Color.GREEN));
      bs.getBoard().add(new FieldSquare(9, 10, Color.WHITE));
      bs.getBoard().add(new FieldSquare(9, 11, Color.GREEN));
      bs.getBoard().add(new FieldSquare(9, 12, Color.WHITE));
      bs.getBoard().add(new FieldSquare(9, 13, Color.GREEN));
      bs.getBoard().add(new FieldSquare(9, 14, Color.WHITE));
      bs.getBoard().add(new FieldSquare(9, 15, Color.GREEN));
      bs.getBoard().add(new FieldSquare(9, 16, Color.GREEN));
      bs.getBoard().add(new FieldSquare(9, 17, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(9, 18, Color.WHITE));
      bs.getBoard().add(new FieldSquare(9, 19, Color.WHITE));
      bs.getBoard().add(new FieldSquare(10, 0, Color.BLUE));
      bs.getBoard().add(new FieldSquare(10, 1, Color.WHITE));
      bs.getBoard().add(new FieldSquare(10, 2, Color.WHITE));
      bs.getBoard().add(new FieldSquare(10, 3, Color.BLUE));
      bs.getBoard().add(new FieldSquare(10, 4, Color.RED));
      bs.getBoard().add(new FieldSquare(10, 5, Color.WHITE));
      bs.getBoard().add(new FieldSquare(10, 6, Color.WHITE));
      bs.getBoard().add(new FieldSquare(10, 7, Color.GREEN));
      bs.getBoard().add(new FieldSquare(10, 8, Color.GREEN));
      bs.getBoard().add(new FieldSquare(10, 9, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(10, 10, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(10, 11, Color.GREEN));
      bs.getBoard().add(new FieldSquare(10, 12, Color.GREEN));
      bs.getBoard().add(new FieldSquare(10, 13, Color.WHITE));
      bs.getBoard().add(new FieldSquare(10, 14, Color.GREEN));
      bs.getBoard().add(new FieldSquare(10, 15, Color.WHITE));
      bs.getBoard().add(new FieldSquare(10, 16, Color.GREEN));
      bs.getBoard().add(new FieldSquare(10, 17, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(10, 18, Color.WHITE));
      bs.getBoard().add(new FieldSquare(10, 19, Color.WHITE));
      bs.getBoard().add(new FieldSquare(11, 0, Color.BLUE));
      bs.getBoard().add(new FieldSquare(11, 1, Color.WHITE));
      bs.getBoard().add(new FieldSquare(11, 2, Color.WHITE));
      bs.getBoard().add(new FieldSquare(11, 3, Color.WHITE));
      bs.getBoard().add(new FieldSquare(11, 4, Color.BLUE));
      bs.getBoard().add(new FieldSquare(11, 5, Color.BLUE));
      bs.getBoard().add(new FieldSquare(11, 6, Color.BLUE));
      bs.getBoard().add(new FieldSquare(11, 7, Color.BLUE));
      bs.getBoard().add(new FieldSquare(11, 8, Color.GREEN));
      bs.getBoard().add(new FieldSquare(11, 9, Color.WHITE));
      bs.getBoard().add(new FieldSquare(11, 10, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(11, 11, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(11, 12, Color.WHITE));
      bs.getBoard().add(new FieldSquare(11, 13, Color.GREEN));
      bs.getBoard().add(new FieldSquare(11, 14, Color.GREEN));
      bs.getBoard().add(new FieldSquare(11, 15, Color.WHITE));
      bs.getBoard().add(new FieldSquare(11, 16, Color.GREEN));
      bs.getBoard().add(new FieldSquare(11, 17, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(11, 18, Color.WHITE));
      bs.getBoard().add(new FieldSquare(11, 19, Color.WHITE));
      bs.getBoard().add(new FieldSquare(12, 0, Color.WHITE));
      bs.getBoard().add(new FieldSquare(12, 1, Color.WHITE));
      bs.getBoard().add(new FieldSquare(12, 2, Color.WHITE));
      bs.getBoard().add(new FieldSquare(12, 3, Color.WHITE));
      bs.getBoard().add(new FieldSquare(12, 4, Color.BLUE));
      bs.getBoard().add(new FieldSquare(12, 5, Color.GREEN));
      bs.getBoard().add(new FieldSquare(12, 6, Color.GREEN));
      bs.getBoard().add(new FieldSquare(12, 7, Color.GREEN));
      bs.getBoard().add(new FieldSquare(12, 8, Color.BLUE));
      bs.getBoard().add(new FieldSquare(12, 9, Color.BLUE));
      bs.getBoard().add(new FieldSquare(12, 10, Color.BLUE));
      bs.getBoard().add(new FieldSquare(12, 11, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(12, 12, Color.GREEN));
      bs.getBoard().add(new FieldSquare(12, 13, Color.GREEN));
      bs.getBoard().add(new FieldSquare(12, 14, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(12, 15, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(12, 16, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(12, 17, Color.WHITE));
      bs.getBoard().add(new FieldSquare(12, 18, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(12, 19, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(13, 0, Color.WHITE));
      bs.getBoard().add(new FieldSquare(13, 1, Color.BLUE));
      bs.getBoard().add(new FieldSquare(13, 2, Color.BLUE));
      bs.getBoard().add(new FieldSquare(13, 3, Color.BLUE));
      bs.getBoard().add(new FieldSquare(13, 4, Color.WHITE));
      bs.getBoard().add(new FieldSquare(13, 5, Color.BLUE));
      bs.getBoard().add(new FieldSquare(13, 6, Color.BLUE));
      bs.getBoard().add(new FieldSquare(13, 7, Color.GREEN));
      bs.getBoard().add(new FieldSquare(13, 8, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(13, 9, Color.BLUE));
      bs.getBoard().add(new FieldSquare(13, 10, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(13, 11, Color.WHITE));
      bs.getBoard().add(new FieldSquare(13, 12, Color.WHITE));
      bs.getBoard().add(new FieldSquare(13, 13, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(13, 14, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(13, 15, Color.WHITE));
      bs.getBoard().add(new FieldSquare(13, 16, Color.WHITE));
      bs.getBoard().add(new FieldSquare(13, 17, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(13, 18, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(13, 19, Color.WHITE));
      bs.getBoard().add(new FieldSquare(14, 0, Color.WHITE));
      bs.getBoard().add(new FieldSquare(14, 1, Color.BLUE));
      bs.getBoard().add(new FieldSquare(14, 2, Color.WHITE));
      bs.getBoard().add(new FieldSquare(14, 3, Color.WHITE));
      bs.getBoard().add(new FieldSquare(14, 4, Color.WHITE));
      bs.getBoard().add(new FieldSquare(14, 5, Color.BLUE));
      bs.getBoard().add(new FieldSquare(14, 6, Color.BLUE));
      bs.getBoard().add(new FieldSquare(14, 7, Color.GREEN));
      bs.getBoard().add(new FieldSquare(14, 8, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(14, 9, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(14, 10, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(14, 11, Color.WHITE));
      bs.getBoard().add(new FieldSquare(14, 12, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(14, 13, Color.WHITE));
      bs.getBoard().add(new FieldSquare(14, 14, Color.WHITE));
      bs.getBoard().add(new FieldSquare(14, 15, Color.WHITE));
      bs.getBoard().add(new FieldSquare(14, 16, Color.WHITE));
      bs.getBoard().add(new FieldSquare(14, 17, Color.WHITE));
      bs.getBoard().add(new FieldSquare(14, 18, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(14, 19, Color.WHITE));
      bs.getBoard().add(new FieldSquare(15, 0, Color.WHITE));
      bs.getBoard().add(new FieldSquare(15, 1, Color.BLUE));
      bs.getBoard().add(new FieldSquare(15, 2, Color.WHITE));
      bs.getBoard().add(new FieldSquare(15, 3, Color.WHITE));
      bs.getBoard().add(new FieldSquare(15, 4, Color.WHITE));
      bs.getBoard().add(new FieldSquare(15, 5, Color.WHITE));
      bs.getBoard().add(new FieldSquare(15, 6, Color.WHITE));
      bs.getBoard().add(new FieldSquare(15, 7, Color.WHITE));
      bs.getBoard().add(new FieldSquare(15, 8, Color.WHITE));
      bs.getBoard().add(new FieldSquare(15, 9, Color.WHITE));
      bs.getBoard().add(new FieldSquare(15, 10, Color.WHITE));
      bs.getBoard().add(new FieldSquare(15, 11, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(15, 12, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(15, 13, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(15, 14, Color.WHITE));
      bs.getBoard().add(new FieldSquare(15, 15, Color.WHITE));
      bs.getBoard().add(new FieldSquare(15, 16, Color.WHITE));
      bs.getBoard().add(new FieldSquare(15, 17, Color.WHITE));
      bs.getBoard().add(new FieldSquare(15, 18, Color.WHITE));
      bs.getBoard().add(new FieldSquare(15, 19, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(16, 0, Color.BLUE));
      bs.getBoard().add(new FieldSquare(16, 1, Color.WHITE));
      bs.getBoard().add(new FieldSquare(16, 2, Color.WHITE));
      bs.getBoard().add(new FieldSquare(16, 3, Color.WHITE));
      bs.getBoard().add(new FieldSquare(16, 4, Color.WHITE));
      bs.getBoard().add(new FieldSquare(16, 5, Color.BLUE));
      bs.getBoard().add(new FieldSquare(16, 6, Color.BLUE));
      bs.getBoard().add(new FieldSquare(16, 7, Color.BLUE));
      bs.getBoard().add(new FieldSquare(16, 8, Color.BLUE));
      bs.getBoard().add(new FieldSquare(16, 9, Color.WHITE));
      bs.getBoard().add(new FieldSquare(16, 10, Color.WHITE));
      bs.getBoard().add(new FieldSquare(16, 11, Color.WHITE));
      bs.getBoard().add(new FieldSquare(16, 12, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(16, 13, Color.WHITE));
      bs.getBoard().add(new FieldSquare(16, 14, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(16, 15, Color.WHITE));
      bs.getBoard().add(new FieldSquare(16, 16, Color.WHITE));
      bs.getBoard().add(new FieldSquare(16, 17, Color.WHITE));
      bs.getBoard().add(new FieldSquare(16, 18, Color.WHITE));
      bs.getBoard().add(new FieldSquare(16, 19, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(17, 0, Color.BLUE));
      bs.getBoard().add(new FieldSquare(17, 1, Color.BLUE));
      bs.getBoard().add(new FieldSquare(17, 2, Color.WHITE));
      bs.getBoard().add(new FieldSquare(17, 3, Color.WHITE));
      bs.getBoard().add(new FieldSquare(17, 4, Color.BLUE));
      bs.getBoard().add(new FieldSquare(17, 5, Color.WHITE));
      bs.getBoard().add(new FieldSquare(17, 6, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(17, 7, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(17, 8, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(17, 9, Color.WHITE));
      bs.getBoard().add(new FieldSquare(17, 10, Color.WHITE));
      bs.getBoard().add(new FieldSquare(17, 11, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(17, 12, Color.WHITE));
      bs.getBoard().add(new FieldSquare(17, 13, Color.WHITE));
      bs.getBoard().add(new FieldSquare(17, 14, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(17, 15, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(17, 16, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(17, 17, Color.WHITE));
      bs.getBoard().add(new FieldSquare(17, 18, Color.WHITE));
      bs.getBoard().add(new FieldSquare(17, 19, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(18, 0, Color.BLUE));
      bs.getBoard().add(new FieldSquare(18, 1, Color.WHITE));
      bs.getBoard().add(new FieldSquare(18, 2, Color.BLUE));
      bs.getBoard().add(new FieldSquare(18, 3, Color.BLUE));
      bs.getBoard().add(new FieldSquare(18, 4, Color.WHITE));
      bs.getBoard().add(new FieldSquare(18, 5, Color.WHITE));
      bs.getBoard().add(new FieldSquare(18, 6, Color.WHITE));
      bs.getBoard().add(new FieldSquare(18, 7, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(18, 8, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(18, 9, Color.WHITE));
      bs.getBoard().add(new FieldSquare(18, 10, Color.WHITE));
      bs.getBoard().add(new FieldSquare(18, 11, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(18, 12, Color.WHITE));
      bs.getBoard().add(new FieldSquare(18, 13, Color.WHITE));
      bs.getBoard().add(new FieldSquare(18, 14, Color.WHITE));
      bs.getBoard().add(new FieldSquare(18, 15, Color.WHITE));
      bs.getBoard().add(new FieldSquare(18, 16, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(18, 17, Color.WHITE));
      bs.getBoard().add(new FieldSquare(18, 18, Color.WHITE));
      bs.getBoard().add(new FieldSquare(18, 19, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(19, 0, Color.BLUE));
      bs.getBoard().add(new FieldSquare(19, 1, Color.WHITE));
      bs.getBoard().add(new FieldSquare(19, 2, Color.WHITE));
      bs.getBoard().add(new FieldSquare(19, 3, Color.BLUE));
      bs.getBoard().add(new FieldSquare(19, 4, Color.WHITE));
      bs.getBoard().add(new FieldSquare(19, 5, Color.WHITE));
      bs.getBoard().add(new FieldSquare(19, 6, Color.WHITE));
      bs.getBoard().add(new FieldSquare(19, 7, Color.WHITE));
      bs.getBoard().add(new FieldSquare(19, 8, Color.WHITE));
      bs.getBoard().add(new FieldSquare(19, 9, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(19, 10, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(19, 11, Color.YELLOW));
      bs.getBoard().add(new FieldSquare(19, 12, Color.WHITE));
      bs.getBoard().add(new FieldSquare(19, 13, Color.WHITE));
      bs.getBoard().add(new FieldSquare(19, 14, Color.WHITE));
      bs.getBoard().add(new FieldSquare(19, 15, Color.WHITE));
      bs.getBoard().add(new FieldSquare(19, 16, Color.WHITE));
      bs.getBoard().add(new FieldSquare(19, 17, Color.WHITE));
      bs.getBoard().add(new FieldSquare(19, 18, Color.WHITE));
      bs.getBoard().add(new FieldSquare(19, 19, Color.YELLOW));
      bs.getStartFields().clear();
      bs.getStartFields().add(new FieldSquare(19, 19, Color.WHITE));
      bs.getStartFields().add(new FieldSquare(0, 19, Color.WHITE));
      bs.getStartFields().add(new FieldSquare(19, 0, Color.WHITE));
      bs.getStartFields().add(new FieldSquare(0, 0, Color.WHITE));
      testBoard1 = bs;
    }

    @Test
    void testPlayTurn() {
      Assertions.assertAll(() -> assertFalse(testBoard1.getField(12, 2).isOccupied()),
          () -> assertFalse(testBoard1.getField(11, 3).isOccupied()),
          () -> assertFalse(testBoard1.getField(11, 2).isOccupied()),
          () -> assertFalse(testBoard1.getField(10, 2).isOccupied()),
          () -> assertFalse(testBoard1.getField(11, 1).isOccupied()));
      testBoard1.playTurn(testTurn1, false); //play the turn
      Assertions.assertAll(() -> assertTrue(testBoard1.getField(12, 2).isOccupied()),
          () -> assertTrue(testBoard1.getField(11, 3).isOccupied()),
          () -> assertTrue(testBoard1.getField(11, 2).isOccupied()),
          () -> assertTrue(testBoard1.getField(10, 2).isOccupied()),
          () -> assertTrue(testBoard1.getField(11, 1).isOccupied()));
    }

    @Test
    void testIsOnTheBoard() {
      assertTrue(testBoard1.isOnTheBoard(3, 5));
      assertFalse(testBoard1.isOnTheBoard(20, 0));
      assertFalse(testBoard1.isOnTheBoard(-1, 7));
    }

    @Test
    void testIsColorDirectNeighbor() {
      assertTrue(testBoard1.isColorDirectNeighbor(10, 10, Color.YELLOW));
      assertTrue(testBoard1.isColorDirectNeighbor(10, 10, Color.GREEN));
      assertFalse(testBoard1.isColorDirectNeighbor(10, 10, Color.BLUE));
      assertFalse(testBoard1.isColorDirectNeighbor(10, 10, Color.RED));
    }

    @Test
    void testIsColorIndirectNeighbor() {
      assertFalse(testBoard1.isColorIndirectNeighbor(10, 10, Color.BLUE));
      assertTrue(testBoard1.isColorIndirectNeighbor(10, 10, Color.YELLOW));
      assertTrue(testBoard1.isColorIndirectNeighbor(10, 10, Color.GREEN));
      assertFalse(testBoard1.isColorIndirectNeighbor(10, 10, Color.RED));
    }

    @Test
    void testIsPolyPossible() {
      assertTrue(testBoard1.isPolyPossible(12, 2, testPoly1, false));
      assertFalse(testBoard1.isPolyPossible(9, 11, testPoly1, false));
    }

    @Test
    void testGetScoreOfColor() {
      Assertions.assertAll(() -> assertEquals(testBoard1.getScoreOfColor(Color.BLUE), 38),
          () -> assertEquals(testBoard1.getScoreOfColor(Color.RED), 50),
          () -> assertEquals(testBoard1.getScoreOfColor(Color.YELLOW), 50),
          () -> assertEquals(testBoard1.getScoreOfColor(Color.GREEN), 50));
    }

    @Test
    void testGetPossible(){
      assertEquals(testBoard1.getPossibleFieldsAndShadesForPoly(testPoly1, false).size(),8);
    }

    @Test
    void testGetPossibleMoves(){
      ArrayList<Poly> testRemainingPolys = new ArrayList<>();
      testRemainingPolys.add(testPoly1);
      assertEquals(testBoard1.getPossibleMoves(testRemainingPolys, false).size(),8);
    }

    @Test
    void testGetPossibleFields(){
      assertEquals(testBoard1.getPossibleFields(Color.RED, false).size(), 22);
    }
  }
}