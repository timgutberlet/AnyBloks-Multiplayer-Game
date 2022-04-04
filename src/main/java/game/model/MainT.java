package game.model;

import game.model.gamemodes.GMClassic;
import game.model.gamemodes.GMDuo;
import game.model.gamemodes.GameMode;
import game.model.gamemodes.GMJunior;
import game.model.gamemodes.GMTrigon;
import game.view.InGameView;
import java.util.ArrayList;
import java.util.Arrays;

public class MainT {

  public static void initGame(InGameView view) {
    Player p1 = new Player("AI1", PlayerType.AI_RANDOM);
    Player p2 = new Player("AI2", PlayerType.AI_RANDOM);
    Player p3 = new Player("AI3", PlayerType.AI_EASY);
    Player p4 = new Player("AI4", PlayerType.AI_MIDDLE);
    Game game = new Game(new ArrayList<>(Arrays.asList(p1, p2, p3, p4)), new GMClassic());
    for (Poly p : game.getGameState().getRemainingPolys(p1)) {
      System.out.println(p);
    }
    for (Poly p : game.getGameState().getRemainingPolys(p2)) {
      System.out.println(p);
    }
    System.out.println(game.getGameState().getBoard());

    game.startGame();
    while (game.getGameState().isStateRunning()) {
      game.getGameState().playTurn(AI.calculateNextMove(game.getGameState(), p1));
      game.getGameState().getBoard().updateBoard(view);
      game.getGameState().playTurn(AI.calculateNextMove(game.getGameState(), p2));
      game.getGameState().getBoard().updateBoard(view);
      game.getGameState().playTurn(AI.calculateNextMove(game.getGameState(), p3));
      game.getGameState().getBoard().updateBoard(view);
      game.getGameState().playTurn(AI.calculateNextMove(game.getGameState(), p4));
      game.getGameState().getBoard().updateBoard(view);
    }
  }

  public static void main(String[] args) {
    GameMode gameMode = new GMDuo(); //change here if you want to change the gamemode

    if (gameMode.getName().equals("CLASSIC") || gameMode.getName().equals(
        "TRIGON")) { //ToDo semantic mistake with the TRIGON-Mode, because some polys are too big and give a score over 6 so a over all score of 122 is possible
      Player p1 = new Player("AI1", PlayerType.AI_RANDOM);
      Player p2 = new Player("AI2", PlayerType.AI_RANDOM);
      Player p3 = new Player("AI3", PlayerType.AI_HARD);
      Player p4 = new Player("AI4", PlayerType.AI_MIDDLE);
      Game game = new Game(new ArrayList<>(Arrays.asList(p1, p2, p3, p4)), gameMode);
      int i = 0;
      for (Poly p : game.getGameState().getRemainingPolys(p1)) {
        System.out.println("Nr. " + ++i + ": " + p.polyTest());
      }
      for (Poly p : game.getGameState().getRemainingPolys(p2)) {
        System.out.println(p);
      }
      System.out.println(game.getGameState().getBoard());

      game.startGame();
      while (game.getGameState().isStateRunning()) {
        Turn t1 = AI.calculateNextMove(game.getGameState(), p1);
        System.out.println(t1);
        game.getGameState().playTurn(t1);
        System.out.println(game.getGameState().getBoard());

        Turn t2 = AI.calculateNextMove(game.getGameState(), p2);
        System.out.println(t2);
        game.getGameState().playTurn(t2);
        System.out.println(game.getGameState().getBoard());

        Turn t3 = AI.calculateNextMove(game.getGameState(), p3);
        System.out.println(t3);
        game.getGameState().playTurn(t3);
        System.out.println(game.getGameState().getBoard());

        Turn t4 = AI.calculateNextMove(game.getGameState(), p4);
        System.out.println(t4);
        game.getGameState().playTurn(t4);
        System.out.println(game.getGameState().getBoard());
      }
    } else {
      Player p1 = new Player("AI1", PlayerType.AI_RANDOM);
      Player p2 = new Player("AI2", PlayerType.AI_HARD);
      Game game = new Game(new ArrayList<>(Arrays.asList(p1, p2)), gameMode);
      for (Poly p : game.getGameState().getRemainingPolys(p1)) {
        System.out.println(p);
      }
      for (Poly p : game.getGameState().getRemainingPolys(p2)) {
        System.out.println(p);
      }
      System.out.println(game.getGameState().getBoard());

      game.startGame();
      while (game.getGameState().isStateRunning()) {
        Turn t1 = AI.calculateNextMove(game.getGameState(), p1);
        System.out.println(t1);
        game.getGameState().playTurn(t1);
        System.out.println(game.getGameState().getBoard());

        Turn t2 = AI.calculateNextMove(game.getGameState(), p2);
        System.out.println(t2);
        game.getGameState().playTurn(t2);
        System.out.println(game.getGameState().getBoard());
      }
    }
  }
}
