package game.model.board;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import game.model.Color;
import game.model.Turn;
import game.model.field.FieldTrigon;
import game.model.polygon.Poly;
import game.model.polygon.PolyTrigon;
import java.util.ArrayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author tiotto
 * @date 15.05.2022
 */
class BoardTrigonTest {

  BoardTrigon trigon = new BoardTrigon();
  BoardTrigon testBoard;
  PolyTrigon testPoly;
  Turn testTurn;

  BoardTrigonTest() { //Creation of help objects
    ArrayList<FieldTrigon> shape = new ArrayList<>();
    shape.add(new game.model.field.FieldTrigon(0, 3, 0, game.model.Color.RED));
    shape.add(new game.model.field.FieldTrigon(0, 2, 1, game.model.Color.RED));
    shape.add(new game.model.field.FieldTrigon(0, 2, 0, game.model.Color.RED));
    shape.add(new game.model.field.FieldTrigon(0, 1, 1, game.model.Color.RED));
    shape.add(new game.model.field.FieldTrigon(1, 2, 0, game.model.Color.RED));
    testPoly = new PolyTrigon(shape, game.model.Color.RED);
    testTurn = new Turn(testPoly, new int[]{10, 11, 0});

    BoardTrigon bt = new BoardTrigon();
    bt.getBoard().clear();
    bt.getBoard().add(new game.model.field.FieldTrigon(0, 8, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(0, 9, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(0, 9, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(0, 10, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(0, 10, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(0, 11, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(0, 11, 1, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(0, 12, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(0, 12, 1, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(0, 13, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(0, 13, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(0, 14, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(0, 14, 1, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(0, 15, 0, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(0, 15, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(0, 16, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(0, 16, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(0, 17, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(0, 17, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(1, 7, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(1, 8, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(1, 8, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(1, 9, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(1, 9, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(1, 10, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(1, 10, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(1, 11, 0, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(1, 11, 1, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(1, 12, 0, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(1, 12, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(1, 13, 0, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(1, 13, 1, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(1, 14, 0, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(1, 14, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(1, 15, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(1, 15, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(1, 16, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(1, 16, 1, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(1, 17, 0, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(1, 17, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(2, 6, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(2, 7, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(2, 7, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(2, 8, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(2, 8, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(2, 9, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(2, 9, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(2, 10, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(2, 10, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(2, 11, 0, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(2, 11, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(2, 12, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(2, 12, 1, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(2, 13, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(2, 13, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(2, 14, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(2, 14, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(2, 15, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(2, 15, 1, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(2, 16, 0, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(2, 16, 1, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(2, 17, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(2, 17, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(3, 5, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(3, 6, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(3, 6, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(3, 7, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(3, 7, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(3, 8, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(3, 8, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(3, 9, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(3, 9, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(3, 10, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(3, 10, 1, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(3, 11, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(3, 11, 1, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(3, 12, 0, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(3, 12, 1, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(3, 13, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(3, 13, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(3, 14, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(3, 14, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(3, 15, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(3, 15, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(3, 16, 0, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(3, 16, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(3, 17, 0, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(3, 17, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(4, 4, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(4, 5, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(4, 5, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(4, 6, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(4, 6, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(4, 7, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(4, 7, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(4, 8, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(4, 8, 1, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(4, 9, 0, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(4, 9, 1, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(4, 10, 0, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(4, 10, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(4, 11, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(4, 11, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(4, 12, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(4, 12, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(4, 13, 0, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(4, 13, 1, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(4, 14, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(4, 14, 1, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(4, 15, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(4, 15, 1, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(4, 16, 0, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(4, 16, 1, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(4, 17, 0, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(4, 17, 1, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(5, 3, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(5, 4, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(5, 4, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(5, 5, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(5, 5, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(5, 6, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(5, 6, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(5, 7, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(5, 7, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(5, 8, 0, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(5, 8, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(5, 9, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(5, 9, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(5, 10, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(5, 10, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(5, 11, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(5, 11, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(5, 12, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(5, 12, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(5, 13, 0, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(5, 13, 1, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(5, 14, 0, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(5, 14, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(5, 15, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(5, 15, 1, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(5, 16, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(5, 16, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(5, 17, 0, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(5, 17, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(6, 2, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(6, 3, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(6, 3, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(6, 4, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(6, 4, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(6, 5, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(6, 5, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(6, 6, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(6, 6, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(6, 7, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(6, 7, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(6, 8, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(6, 8, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(6, 9, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(6, 9, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(6, 10, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(6, 10, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(6, 11, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(6, 11, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(6, 12, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(6, 12, 1, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(6, 13, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(6, 13, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(6, 14, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(6, 14, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(6, 15, 0, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(6, 15, 1, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(6, 16, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(6, 16, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(6, 17, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(6, 17, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(7, 1, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(7, 2, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(7, 2, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(7, 3, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(7, 3, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(7, 4, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(7, 4, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(7, 5, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(7, 5, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(7, 6, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(7, 6, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(7, 7, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(7, 7, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(7, 8, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(7, 8, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(7, 9, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(7, 9, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(7, 10, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(7, 10, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(7, 11, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(7, 11, 1, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(7, 12, 0, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(7, 12, 1, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(7, 13, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(7, 13, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(7, 14, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(7, 14, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(7, 15, 0, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(7, 15, 1, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(7, 16, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(7, 16, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(7, 17, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(7, 17, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(8, 0, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(8, 1, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(8, 1, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(8, 2, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(8, 2, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(8, 3, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(8, 3, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(8, 4, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(8, 4, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(8, 5, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(8, 5, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(8, 6, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(8, 6, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(8, 7, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(8, 7, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(8, 8, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(8, 8, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(8, 9, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(8, 9, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(8, 10, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(8, 10, 1, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(8, 11, 0, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(8, 11, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(8, 12, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(8, 12, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(8, 13, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(8, 13, 1, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(8, 14, 0, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(8, 14, 1, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(8, 15, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(8, 15, 1, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(8, 16, 0, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(8, 16, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(8, 17, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(8, 17, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(9, 0, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(9, 0, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(9, 1, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(9, 1, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(9, 2, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(9, 2, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(9, 3, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(9, 3, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(9, 4, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(9, 4, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(9, 5, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(9, 5, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(9, 6, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(9, 6, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(9, 7, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(9, 7, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(9, 8, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(9, 8, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(9, 9, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(9, 9, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(9, 10, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(9, 10, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(9, 11, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(9, 11, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(9, 12, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(9, 12, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(9, 13, 0, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(9, 13, 1, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(9, 14, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(9, 14, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(9, 15, 0, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(9, 15, 1, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(9, 16, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(9, 16, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(9, 17, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(10, 0, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(10, 0, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(10, 1, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(10, 1, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(10, 2, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(10, 2, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(10, 3, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(10, 3, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(10, 4, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(10, 4, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(10, 5, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(10, 5, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(10, 6, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(10, 6, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(10, 7, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(10, 7, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(10, 8, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(10, 8, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(10, 9, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(10, 9, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(10, 10, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(10, 10, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(10, 11, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(10, 11, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(10, 12, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(10, 12, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(10, 13, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(10, 13, 1, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(10, 14, 0, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(10, 14, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(10, 15, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(10, 15, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(10, 16, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(11, 0, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(11, 0, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(11, 1, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(11, 1, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(11, 2, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(11, 2, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(11, 3, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(11, 3, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(11, 4, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(11, 4, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(11, 5, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(11, 5, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(11, 6, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(11, 6, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(11, 7, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(11, 7, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(11, 8, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(11, 8, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(11, 9, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(11, 9, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(11, 10, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(11, 10, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(11, 11, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(11, 11, 1, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(11, 12, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(11, 12, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(11, 13, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(11, 13, 1, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(11, 14, 0, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(11, 14, 1, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(11, 15, 0, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(12, 0, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(12, 0, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(12, 1, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(12, 1, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(12, 2, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(12, 2, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(12, 3, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(12, 3, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(12, 4, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(12, 4, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(12, 5, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(12, 5, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(12, 6, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(12, 6, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(12, 7, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(12, 7, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(12, 8, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(12, 8, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(12, 9, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(12, 9, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(12, 10, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(12, 10, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(12, 11, 0, game.model.Color.GREEN));
    bt.getBoard().add(new game.model.field.FieldTrigon(12, 11, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(12, 12, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(12, 12, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(12, 13, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(12, 13, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(12, 14, 0, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(13, 0, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(13, 0, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(13, 1, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(13, 1, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(13, 2, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(13, 2, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(13, 3, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(13, 3, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(13, 4, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(13, 4, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(13, 5, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(13, 5, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(13, 6, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(13, 6, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(13, 7, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(13, 7, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(13, 8, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(13, 8, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(13, 9, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(13, 9, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(13, 10, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(13, 10, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(13, 11, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(13, 11, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(13, 12, 0, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(13, 12, 1, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(13, 13, 0, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(14, 0, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(14, 0, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(14, 1, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(14, 1, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(14, 2, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(14, 2, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(14, 3, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(14, 3, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(14, 4, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(14, 4, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(14, 5, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(14, 5, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(14, 6, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(14, 6, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(14, 7, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(14, 7, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(14, 8, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(14, 8, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(14, 9, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(14, 9, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(14, 10, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(14, 10, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(14, 11, 0, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(14, 11, 1, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(14, 12, 0, game.model.Color.BLUE));
    bt.getBoard().add(new game.model.field.FieldTrigon(15, 0, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(15, 0, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(15, 1, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(15, 1, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(15, 2, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(15, 2, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(15, 3, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(15, 3, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(15, 4, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(15, 4, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(15, 5, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(15, 5, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(15, 6, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(15, 6, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(15, 7, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(15, 7, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(15, 8, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(15, 8, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(15, 9, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(15, 9, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(15, 10, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(15, 10, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(15, 11, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(16, 0, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(16, 0, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(16, 1, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(16, 1, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(16, 2, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(16, 2, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(16, 3, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(16, 3, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(16, 4, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(16, 4, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(16, 5, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(16, 5, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(16, 6, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(16, 6, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(16, 7, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(16, 7, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(16, 8, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(16, 8, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(16, 9, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(16, 9, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(16, 10, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(17, 0, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(17, 0, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(17, 1, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(17, 1, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(17, 2, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(17, 2, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(17, 3, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(17, 3, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(17, 4, 0, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(17, 4, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(17, 5, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(17, 5, 1, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(17, 6, 0, game.model.Color.RED));
    bt.getBoard().add(new game.model.field.FieldTrigon(17, 6, 1, game.model.Color.WHITE));
    bt.getBoard().add(new game.model.field.FieldTrigon(17, 7, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(17, 7, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(17, 8, 0, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(17, 8, 1, game.model.Color.YELLOW));
    bt.getBoard().add(new game.model.field.FieldTrigon(17, 9, 0, game.model.Color.YELLOW));
    bt.getStartFields().clear();
    bt.getStartFields().add(new game.model.field.FieldTrigon(6, 6, 0, game.model.Color.WHITE));
    bt.getStartFields().add(new game.model.field.FieldTrigon(11, 3, 1, game.model.Color.WHITE));
    bt.getStartFields().add(new game.model.field.FieldTrigon(14, 6, 0, game.model.Color.WHITE));
    bt.getStartFields().add(new game.model.field.FieldTrigon(11, 11, 1, game.model.Color.WHITE));
    bt.getStartFields().add(new game.model.field.FieldTrigon(6, 14, 0, game.model.Color.WHITE));
    bt.getStartFields().add(new game.model.field.FieldTrigon(3, 11, 1, game.model.Color.WHITE));
    testBoard = bt;
  }

  @Test
  void testSize() {
    assertEquals(trigon.size, 26);
    assertEquals(trigon.getBoard().size(), 486);
    for (FieldTrigon ft : trigon.getBoard()) {
      assertTrue(
          ft.getPos()[0] >= 0 && ft.getPos()[0] < 18 && ft.getPos()[1] >= 0 && ft.getPos()[1] < 18
              && (ft.getPos()[0] + ft.getPos()[1] > 8 && ft.getPos()[0] + ft.getPos()[1] < 26
              || ft.getPos()[0] + ft.getPos()[1] == 8 && ft.getPos()[2] == 1
              || ft.getPos()[0] + ft.getPos()[1] == 26 && ft.getPos()[2] == 0));
    }
  }

  @Test
  void testColor() {
    for (FieldTrigon ft : trigon.getBoard()) {
      assertEquals(ft.getColor(), Color.WHITE);
    }
  }

  @Test
  void testStartFields() {
    assertTrue(trigon.getStartFields().contains(new FieldTrigon(6, 6, 0)));
    assertTrue(trigon.getStartFields().contains(new FieldTrigon(11, 3, 1)));
    assertTrue(trigon.getStartFields().contains(new FieldTrigon(14, 6, 0)));
    assertTrue(trigon.getStartFields().contains(new FieldTrigon(11, 11, 1)));
    assertTrue(trigon.getStartFields().contains(new FieldTrigon(6, 14, 0)));
    assertTrue(trigon.getStartFields().contains(new FieldTrigon(3, 11, 1)));
    assertEquals(trigon.getStartFields().size(), 6);
  }

  @Test
  void testPlayTurn() {
    Assertions.assertAll(() -> assertFalse(testBoard.getField(10, 11, 0).isOccupied()),
        () -> assertFalse(testBoard.getField(10, 10, 1).isOccupied()),
        () -> assertFalse(testBoard.getField(10, 10, 0).isOccupied()),
        () -> assertFalse(testBoard.getField(10, 9, 1).isOccupied()),
        () -> assertFalse(testBoard.getField(11, 10, 0).isOccupied()));
    testBoard.playTurn(testTurn, false); //play Turn
    Assertions.assertAll(() -> assertTrue(testBoard.getField(10, 11, 0).isOccupied()),
        () -> assertTrue(testBoard.getField(10, 10, 1).isOccupied()),
        () -> assertTrue(testBoard.getField(10, 10, 0).isOccupied()),
        () -> assertTrue(testBoard.getField(10, 9, 1).isOccupied()),
        () -> assertTrue(testBoard.getField(11, 10, 0).isOccupied()));
  }

  @Test
  void testIsOnTheBoard() {
    assertTrue(testBoard.isOnTheBoard(8, 9, 1));
    assertFalse(testBoard.isOnTheBoard(27, 0, 1));
    assertFalse(testBoard.isOnTheBoard(-1, 7, 0));
    assertFalse(testBoard.isOnTheBoard(8, 7, 2));
  }

  @Test
  void testIsColorDirectNeighbor() {
    assertFalse(testBoard.isColorDirectNeighbor(10, 11, 1, Color.YELLOW));
    assertTrue(testBoard.isColorDirectNeighbor(10, 11, 1, Color.GREEN));
    assertFalse(testBoard.isColorDirectNeighbor(10, 11, 1, Color.BLUE));
    assertFalse(testBoard.isColorDirectNeighbor(10, 11, 1, Color.RED));
  }

  @Test
  void testIsColorIndirectNeighbor() {
    assertFalse(testBoard.isColorIndirectNeighbor(10, 10, 1, Color.BLUE));
    assertFalse(testBoard.isColorIndirectNeighbor(10, 10, 1, Color.YELLOW));
    assertTrue(testBoard.isColorIndirectNeighbor(10, 10, 1, Color.GREEN));
    assertFalse(testBoard.isColorIndirectNeighbor(10, 10, 1, Color.RED));
  }

  @Test
  void testIsPolyPossible() {
    assertTrue(testBoard.isPolyPossible(10, 11, 0, testPoly, false));
    assertFalse(testBoard.isPolyPossible(10, 12, 1, testPoly, false));
  }

  @Test
  void testGetScoreOfColor() {
    Assertions.assertAll(() -> assertEquals(testBoard.getScoreOfColor(Color.BLUE), 73),
        () -> assertEquals(testBoard.getScoreOfColor(Color.RED), 87),
        () -> assertEquals(testBoard.getScoreOfColor(Color.YELLOW), 87),
        () -> assertEquals(testBoard.getScoreOfColor(Color.GREEN), 87));
  }

  @Test
  void testGetPossible() {
    assertEquals(testBoard.getPossibleFieldsAndShadesForPoly(testPoly, false).size(), 2);
  }

  @Test
  void testGetPossibleMoves() {
    ArrayList<Poly> testRemainingPolys = new ArrayList<>();
    testRemainingPolys.add(testPoly);
    assertEquals(testBoard.getPossibleMoves(testRemainingPolys, false).size(), 2);
  }

  @Test
  void testGetPossibleFields() {
    assertEquals(testBoard.getPossibleFields(Color.RED, false).size(), 51);
  }
}