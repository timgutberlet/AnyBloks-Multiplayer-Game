package game.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import game.model.board.Board;
import game.model.board.BoardSquare;
import game.model.board.BoardTrigon;
import game.model.field.FieldSquare;
import game.model.field.FieldTrigon;
import game.model.gamemodes.GameMode;
import game.model.player.Player;
import game.model.polygon.Poly;
import game.model.polygon.PolySquare;
import game.model.polygon.PolyTrigon;
import java.io.Serializable;
import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)

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
  //private GameSession gameSession;

  /**
   * used game mode
   */
  private GameMode gameMode;

  /**
   * game board
   */
  private Board board;

  /**
   * list of arrays of the remaining polys of each player (every player has the same index in player
   * as their remaining polys)
   */
  private final ArrayList<ArrayList<Poly>> remainingPolys = new ArrayList<>();
  /**
   * list of every participating player
   */
  private ArrayList<Player> playerList;
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
  private final ArrayList<ArrayList<Turn>> history = new ArrayList<>();
  /**
   * states if the game is currently running
   */
  private boolean running;

  /**
   * states if the game has already started
   */
  private boolean started;

  /**
   *
   */
  private ArrayList<Color> winners = new ArrayList<>();

  private boolean playTurn;

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
  public GameState(GameSession gameSession, GameMode gameMode) {
    this.gameMode = gameMode;
    if (gameMode.getName().equals("TRIGON")) {
      board = new BoardTrigon();
    } else {
      board = new BoardSquare(gameMode);
    }
    this.round = 1;
    this.turn = 0;
    //this.gameSession = gameSession;
    this.playerList = gameSession.getPlayerList();
    this.running = false;
    this.started = false;


    init();

  }

  /**
   * Constructor to copy a GameState (only used in this.clone())
   *
   * @param gameMode       gameMode
   * @param board          board
   * @param remainingPolys remainingPolys
   * @param playerList     player
   * @param round          round
   * @param turn           turn
   * @param running        running
   * @param started        started
   * @param stateEnding    stateEnding
   */
  public GameState(GameMode gameMode, Board board,
      ArrayList<ArrayList<Poly>> remainingPolys,
      ArrayList<Player> playerList, int round, int turn, boolean running, boolean started,
      String stateEnding, ArrayList<ArrayList<Turn>> history) {
    this.gameMode = gameMode;
    this.board = board;
    for (ArrayList<Poly> polys : remainingPolys) {
      this.remainingPolys.add(polys);
    }
    this.playerList = (ArrayList<Player>) playerList;
    Debug.printMessage(this,"LIST SIZE PLAYERS: " +this.playerList.size());
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
   * empty constructor for jackson
   */
  public GameState() {

  }

  /**
   * initalises the polys for all players depending on the selected gamemode
   */
  private void init() {
    Debug.printMessage(this, "THE CURRENT PLAYER LIST SIZE IS " + this.playerList.size());
    for (Player p : this.playerList) {
      Debug.printMessage(this, "Playername: "+p.getUsername());
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
      //Debug.printMessage(this,"Remaing polys calculated and added to GameState");
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

    //Debug.printMessage(this, "Size fo remainingPolys arrayList:");
    //Debug.printMessage(this, "Entries: " + remainingPolys.size());
    for (ArrayList<Poly> arr : this.remainingPolys) {
      //Debug.printMessage(this, "Polys: " + arr.size());
    }

    //Debug.printMessage(this,"GET REMAINING POLYS: " + this.playerList.size());
    for (Player player : this.playerList) {
      //	Debug.printMessage(this,"Comparing " + p.getUsername() +" to " + player.getUsername());
      if (p.getUsername().equals(player.getUsername())) {
        //		Debug.printMessage(this,"Getting remaining polys for " + player.getUsername());
        return remainingPolys.get(this.playerList.indexOf(player));
      }
    }
    return null;
  }

  public ArrayList<Poly> getRemainingPolysClone(Player p) {

    // Debug.printMessage(this, "Size fo remainingPolys arrayList:");
    // Debug.printMessage(this, "Entries: " + remainingPolys.size());
    for (ArrayList<Poly> arr : this.remainingPolys) {
      // Debug.printMessage(this, "Polys: " + arr.size());
    }

    //Debug.printMessage(this,"GET REMAINING POLYS: " + this.playerList.size());
    for (Player player : this.playerList) {
      //	Debug.printMessage(this,"Comparing " + p.getUsername() +" to " + player.getUsername());
      if (p.getUsername().equals(player.getUsername())) {
        //		Debug.printMessage(this,"Getting remaining polys for " + player.getUsername());
        ArrayList<Poly> copy = new ArrayList<>();
        for (Poly poly : remainingPolys.get(this.playerList.indexOf(player))) {
          copy.add(poly.clone());
        }
        return copy;
      }
    }
    return null;
  }

  public Player getPlayerCurrent() {
    if (getPlayerList().size() > 0) {
      return getPlayerList().get(this.turn % this.playerList.size());
    } else {
      return null;
    }
  }

  public Color getColorCurrent(){
    return getColorFromPlayer(getPlayerCurrent());
  }

  public ArrayList<Player> getPlayerList() {
    return playerList;
  }

  public Player getPlayerFromColor(Color c) {
    switch (c) {
      case RED:
        return getPlayerList().get(0);
      case BLUE:
        return getPlayerList().get(1);
      case GREEN:
        return getPlayerList().get(2);
      case YELLOW:
        return getPlayerList().get(3);
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
    return Color.values()[getPlayerList().indexOf(p) + 1];
  }


  public void incTurn() {
    this.turn = turn + 1;
    this.round = turn / playerList.size() + 1;
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

  public boolean checkEnd() {
    boolean end = true;
    for (Player p : playerList) {
     /*Debug.printMessage(
          board.getPossibleMoves(remainingPolys.get(playerList.indexOf(p)), isFirstRound()).size()
              + " Möglichkeiten Steine zu legen");
      Debug.printMessage(this,
          "Remaining Polys for __" + p.getUsername() + "__ : " + this.getRemainingPolys(p).size());*/
      if (getRemainingPolys(p).size() > 0 && (
          board.getPossibleMoves(remainingPolys.get(playerList.indexOf(p)), isFirstRound()).size()
              > 0)) {
        end = false;
      }
    }
    if(end){
      int bestScore = 0;
      for (Player p : playerList) {
        bestScore = Math.max(bestScore, board.getScoreOfColor(getColorFromPlayer(p)));
      }
      for (Player p : playerList) {
        if (board.getScoreOfColor(getColorFromPlayer(p)) == bestScore) {
          winners.add(getColorFromPlayer(p));
        }
      }
    }
    return end;
  }

  public boolean checkEnd(Turn turn) {

    boolean end = true;
    for (Player p : playerList) {
      /*Debug.printMessage(
          board.getPossibleMoves(remainingPolys.get(playerList.indexOf(p)), isFirstRound()).size()
              + " möglichkeiten Steine zu legen");
      Debug.printMessage(this,
          "(TURN) Remaining Polys for : " + p.getUsername() + " : " + this.getRemainingPolys(p)
              .size());*/
      if (getRemainingPolys(p).size() > 0 && (
          board.getPossibleMoves(remainingPolys.get(playerList.indexOf(p)), isFirstRound()).size()
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
      for (Player p : playerList) {
        Debug.printMessage(
            p.getUsername() + ": " + board.getScoreOfColor(getColorFromPlayer(p)) + " ("
                + getColorFromPlayer(p).toString() + " | " + p.getType() + ")");
        bestScore = Math.max(bestScore, board.getScoreOfColor(getColorFromPlayer(p)));
      }
      Debug.printMessage("Winner: ");
      for (Player p : playerList) {
        if (board.getScoreOfColor(getColorFromPlayer(p)) == bestScore) {
          Debug.printMessage(
              p.getUsername() + " (" + getColorFromPlayer(p).toString() + " | " + p.getType()
                  + ")");
        }
      }

      return true;
    }
    return false;
  }

  /**
   * Checks if gamestate currently plays a turn
   *
   * @return
   */
  public boolean playsTurn() {
    return playTurn;
  }

  public boolean playTurn(Turn turn) {
    playTurn = true;
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

    history.get(this.turn % playerList.size()).add(turn);
    playTurn = false;
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
    for (Player p : playerList) {
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
    return new GameState(this.gameMode, boardCopy, remainingPolysCopy, playerCopy,
        round, turn,
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

    String result = "PlayerList: \n";

    for (Player p : this.playerList) {
      result += p.getUsername() + "\n";
    }

		/*
		result += "\nSize fo remainingPolys arrayList:\n";
		result += "Entries: "+remainingPolys.size() +"\n";
		for(ArrayList<Poly> arr : this.remainingPolys){
			result += "		Polys: "+arr.size() + "\n";
		}
		*
		 */
    return result;
  }
    /*
    return "GameState{" +
        "\n player=" + player +
        "\n round=" + round +
        "\n turn=" + turn +
        "\n running=" + running +
        "\n started=" + started +
        "\n stateEnding='" + stateEnding + '\'' +
        '}' + '\n';
  }
*/

  private static String twoDigit(int i) {
    if (i < 10) {
      return "0" + i;
    }
    return Integer.toString(i);
  }
/*
  public String getHistory(){ // table over all played poly sizes for every player
    StringBuffer res = new StringBuffer();
    for (int i = 1; i < getRound(); i++){
      res.append("" + twoDigit(i));
      for (int j = 0; j < playerList.size(); j++){
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
    for (Player p : playerList){
      res.append("----");
    }
    res.append("\n  ");
    for (int i = 0; i < playerList.size(); i++){
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

  public GameMode getGameMode() {
    return gameMode;
  }

  public ArrayList<ArrayList<Poly>> getRemainingPolys() {
    return remainingPolys;
  }

  public ArrayList<ArrayList<Turn>> getHistory() {
    return history;
  }

  public boolean isRunning() {
    return running;
  }

  public int getTurn() {
    return turn;
  }

  public ArrayList<Color> getWinners(){
    return winners;
  }

  public int[] getScores(){
    int[] res = new int[4];
    res[0] = board.getScoreOfColor(Color.RED);
    res[1] = board.getScoreOfColor(Color.BLUE);
    res[2] = board.getScoreOfColor(Color.GREEN);
    res[3] = board.getScoreOfColor(Color.YELLOW);
    return res;
  }

  public void assignRoomDiscovery(Turn t){
    int heightBefore = board.occupiedHeight(t.getColor());
    int widthBefore = board.occupiedWidth(t.getColor());
    GameState after = tryTurn(t);
    int heightAfter = after.getBoard().occupiedHeight(t.getColor());
    int widthAfter = after.getBoard().occupiedWidth(t.getColor());
    int roomDiscovery = (heightAfter-heightBefore) * (widthAfter-widthBefore);
    if (roomDiscovery == 0){
      roomDiscovery = Math.max((heightAfter-heightBefore),(widthAfter-widthBefore));
    }
    t.setRoomDiscovery(roomDiscovery);
  }
}

