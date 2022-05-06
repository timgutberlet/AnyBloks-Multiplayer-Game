package game.model;

import game.model.gamemodes.GameMode;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.function.DoubleToIntFunction;

/**
 * this class represents the current state of one specific game
 */

public class GameState implements Serializable, Cloneable {

  /**
   * Required for serializable objects.
   */
  private static final long serialVersionUID = 1L;

  /**
   * The session of this GameState
   */
  private final Session session;

  /**
   * used game mode
   */
  private final GameMode gameMode;

  /**
   * game board
   */
  private final Board board;

  /**
   * list of arrays of the remaining polys of each player (every player has the same index in player
   * as their remaining polys)
   */
  private final ArrayList<ArrayList<Poly>> remainingPolys = new ArrayList<>();
  /**
   * list of every participating player
   */
  private final ArrayList<Player> player;
  /**
   * round number
   */
  private int round;
  /**
   * turn number
   */
  private int turn;
  /**
   *
   */
  private ArrayList<ArrayList<Turn>> history = new ArrayList<>();
  /**
   * states if the game is currently running
   */
  private boolean running;

  /**
   * states if the game has already started
   */
  private boolean started;

  /**
   * The reason why the game ended.
   */
  private String stateEnding;

  /**
   * initializing of all default parameters
   *
   * @param gameMode gives the game mode for the current game
   * @author tiotto
   */
  public GameState(Session session, GameMode gameMode) {
    this.gameMode = gameMode;
    if (gameMode.getName().equals("TRIGON")) {
      board = new BoardTrigon();
    } else {
      board = new BoardSquare(gameMode);
    }
    this.round = 1;
    this.turn = 0;
    this.session = session;
    this.player = this.session.getPlayerList();
    this.running = false;
    this.started = false;

    init();
  }

  /**
   * Constructor to copy a GameState
   *  @param gameMode       gameMode
   * @param board          board
   * @param remainingPolys remainingPolys
   * @param player         player
   * @param round          round
   * @param turn           turn
   * @param running        running
   * @param started        started
   * @param stateEnding    stateEnding
   */
  public GameState(Session session, GameMode gameMode, Board board,
      ArrayList<ArrayList<Poly>> remainingPolys,
      ArrayList<Player> player, int round, int turn, boolean running, boolean started,
      String stateEnding, ArrayList<ArrayList<Turn>> history) {
    this.gameMode = gameMode;
    this.board = board;
    this.session = session;
    for (ArrayList<Poly> polys : remainingPolys) {
      this.remainingPolys.add(polys);
    }
    this.player = player;
    this.round = round;
    this.turn = turn;
    this.running = running;
    this.started = started;
    this.stateEnding = stateEnding;
    for (ArrayList<Turn> turns : history) {
      this.history.add(turns);
    }
  }

  /**
   * initalises the polys for all players depending on the selected gamemode
   */
  private void init(){
    for (Player p: this.player){
      ArrayList<Poly> polyOfPlayer = new ArrayList<>();
      history.add(new ArrayList<>());

      switch (this.gameMode.getName()) { //remaining polys for every gamemode
        case "CLASSIC": {
          for (ArrayList<FieldSquare> shape : PolySquare.shapeListClassic) {
            polyOfPlayer.add(new PolySquare(shape, getColorFromPlayer(p)));
          }
          break;
        }
        case "DUO": {
          for (ArrayList<FieldSquare> shape : PolySquare.shapeListDuo) {
            polyOfPlayer.add(new PolySquare(shape, getColorFromPlayer(p)));
          }
          break;
        }
        case "JUNIOR": {
          for (ArrayList<FieldSquare> shape : PolySquare.shapeListJunior) {
            polyOfPlayer.add(new PolySquare(shape, getColorFromPlayer(p)));
          }
          break;
        }
        case "TRIGON": {
          for (ArrayList<FieldTrigon> shape : PolyTrigon.shapeListTrigon) {
            polyOfPlayer.add(new PolyTrigon(shape, getColorFromPlayer(p)));
          }
          break;
        }
      }
      remainingPolys.add(polyOfPlayer);

    }
  }


  public Board getBoard() {
    return board;
  }

  /**
   * returns the remaining polys for a specific player
   *
   * @param p player for whom the remaining parts are required
   * @return remaining polys for a specific player
   */
  public ArrayList<Poly> getRemainingPolys(Player p) {
    //Debug.printMessage("GET REMAINING POLYS: " + this.session.getPlayerList().size());
    return remainingPolys.get(this.session.getPlayerList().indexOf(p));
  }

  public Player getPlayerCurrent() {
    if (getPlayer().size() > 0) {
      return getPlayer().get(this.turn % this.player.size());
    } else {
      return null;
    }
  }

  public ArrayList<Player> getPlayer() {
    return player;
  }

  public Player getPlayerFromColor(Color c) {
    switch (c) {
      case RED:
        return getPlayer().get(0);
      case BLUE:
        return getPlayer().get(1);
      case GREEN:
        return getPlayer().get(2);
      case YELLOW:
        return getPlayer().get(3);
      default:
        try {
          throw new Exception("Wrong Player Color");
        } catch (Exception e) {
          e.printStackTrace();
        }
        return null;
    }
  }

  public Color getColorFromPlayer(Player p) {
    return Color.values()[getPlayer().indexOf(p) + 1];
  }


  public void incTurn() {
    this.turn = turn + 1;
    this.round = turn / player.size() + 1;
  }

  public int getRound() {
    return round;
  }

  public boolean isFirstRound() {
    return (round == 1);
  }

  public boolean isStateRunning() {
    return running;
  }

  public void setStateRunning(boolean running) {
    this.running = running;
    if (running) {
      started = true;
    }
  }

  public String getStateEnding() {
    return stateEnding;
  }

  public void setStateEnding(String stateEnding) {
    this.setStateRunning(false);
    this.stateEnding = stateEnding;
  }

  public boolean isStarted() {
    return started;
  }

  public boolean checkEnd(Turn turn) {
    boolean end = true;
    for (Player p : player) {
      Debug.printMessage(
          board.getPossibleMoves(remainingPolys.get(player.indexOf(p)), isFirstRound()).size()
              + " möglichkeiten Steine zu legen");
      //Debug.printMessage(gameState.getRemainingPolys(p).toString() + "Size: " + gameState.getRemainingPolys(p).size());
      if (getRemainingPolys(p).size() > 0 && (
          board.getPossibleMoves(remainingPolys.get(player.indexOf(p)), isFirstRound()).size()
              > 0)) {
        end = false;
      }
    }
    if (end) {
      setStateEnding("Spiel Vorbei");
      setStateRunning(false);
      Debug.printMessage("Spiel Vorbei");

      int bestScore = 0;
      Debug.printMessage("Scores:");
      for (Player p : player) {
        Debug.printMessage(p.getName() + ": " + board.getScoreOfColor(getColorFromPlayer(p)) + " ("
            + getColorFromPlayer(p).toString() + " | " + p.getType() + ")");
        bestScore = Math.max(bestScore, board.getScoreOfColor(getColorFromPlayer(p)));
      }
      Debug.printMessage("Winner: ");
      for (Player p : player) {
        if (board.getScoreOfColor(getColorFromPlayer(p)) == bestScore) {
          Debug.printMessage(
              p.getName() + " (" + getColorFromPlayer(p).toString() + " | " + p.getType() + ")");
        }
      }

      return true;
    }
    return false;
  }

  public boolean playTurn(Turn turn) {
    if (checkEnd(turn)) {
      return false;
    }
    boolean res = board.playTurn(turn, isFirstRound()); //play turn
    if (res) { // remove played poly from remaining polys
      for (Poly p : getRemainingPolys(getPlayerFromColor(turn.getColor()))) {
        if (p.equals(turn.getPoly())) {
          getRemainingPolys(getPlayerFromColor(turn.getColor())).remove(p);
          break;
        }
      }
    }
    incTurn();

    history.get(this.turn % player.size()).add(turn);
    return res;
  }

  @Override
  public GameState clone() {
    Board boardCopy = this.board.clone();
    ArrayList<ArrayList<Poly>> remainingPolysCopy = new ArrayList<>();
    for (ArrayList<Poly> polys : this.remainingPolys) {
      ArrayList<Poly> polysCopy = new ArrayList<>();
      for (Poly p : polys) {
        polysCopy.add(p.clone());
      }
      remainingPolysCopy.add(polysCopy);
    }
    ArrayList<Player> playerCopy = new ArrayList<>();
    for (Player p : player) {
      playerCopy.add(p);
    }
    ArrayList<ArrayList<Turn>> historyCopy = new ArrayList<>();
    for (ArrayList<Turn> turns : this.history) {
      ArrayList<Turn> turnsCopy = new ArrayList<>();
      for (Turn t : turns) {
        if (t == null) {
          turnsCopy.add(null);
        } else {
          turnsCopy.add(t.clone());
        }
      }
      historyCopy.add(turnsCopy);
    }
    return new GameState(this.session, this.gameMode, boardCopy, remainingPolysCopy, playerCopy, round, turn,
        running, started, stateEnding, historyCopy);
  }

  public GameState tryTurn(Turn turn) {
    if (turn == null) {
      return this.clone();
    }
    GameState gameStateCopy = this.clone();
    gameStateCopy.getBoard().playTurn(turn, gameStateCopy.isFirstRound());
    return gameStateCopy;
  }

  public Color getNextColor(Color c) {
    switch (c) {
      case RED:
        return Color.BLUE;
      case BLUE:
        return Color.RED;
      default:
        return Color.BLUE;
    }
  }

  @Override
  public String toString() {
    return "GameState{" +
        "\n player=" + player +
        "\n round=" + round +
        "\n turn=" + turn +
        "\n running=" + running +
        "\n started=" + started +
        "\n stateEnding='" + stateEnding + '\'' +
        '}' + '\n';
  }


/*  private static String twoDigit(int i){
    if (i < 10){
      return "0" + i;
    }
    return Integer.toString(i);
  }

  public String getHistory(){ // table over all played poly sizes for every player
    StringBuffer res = new StringBuffer();
    for (int i = 1; i < getRound(); i++){
      res.append("" + twoDigit(i));
      for (int j = 0; j < player.size(); j++){
        Turn t = history.get(j).get(i-1);
        int size = 0;
        if (t != null){
          size = t.getPoly().getSize();
        }
        res.append(" | " + size);
      }
      res.append("\n");
    }
    res.append("--");
    for (Player p : player){
      res.append("----");
    }
    res.append("\n  ");
    for (int i = 0; i < player.size(); i++){
      int sum = 0;
      for (Turn t : history.get(i)){
        if (t == null){
          break;
        }
        sum += t.getPoly().getSize();
      }
      res.append(" | "+ sum);
    }
    return res.toString();
  }*/
}

