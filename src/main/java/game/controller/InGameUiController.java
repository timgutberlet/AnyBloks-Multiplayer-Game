package game.controller;

import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import engine.handler.InputHandler;
import game.model.Turn;
import game.model.board.BoardSquare;
import game.model.Game;
import game.model.board.BoardTrigon;
import game.model.player.Player;
import game.model.GameSession;
import game.model.chat.Chat;
import game.model.polygon.Poly;
import game.view.board.BoardPane;
import game.view.board.SquareBoardPane;
import game.view.board.TrigonBoardPane;
import game.view.stack.StackSquarePane;
import game.view.stack.StackTrigonPane;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class InGameUiController extends AbstractUiController {

  private GameSession gameSession;

  private Game game;

  private Chat chat;

  private AbstractGameController gameController;

  private BoardPane boardPane;

  private VBox playerStacks;

  private List<Label> playerPoints;

  private HBox Gui;

  private Button quitButton;

  private InputHandler inputHandler;

  private boolean aiCalcRunning;


  public InGameUiController(AbstractGameController gameController, Game game, GameSession gameSession) {
    super(gameController);
    this.inputHandler = gameController.getInputHandler();
    this.gameSession = gameSession;
    this.game = gameSession.getGame();
    this.chat = gameSession.getChat();
    this.gameController = gameController;
    this.Gui = new HBox();
    playerPoints = new ArrayList<>();

    super.root.getChildren().add(Gui);
    createBoard();
    setUpUi();


  }

  public void createBoard() {
    switch (game.getGamemode().getName()) {
      case "JUNIOR":
        ;
      case "DUO":
        ;
      case "CLASSIC":
        boardPane = new SquareBoardPane(game.getGameState().getBoard(), inputHandler);
        break;
      case "TRIGON":
        boardPane = new TrigonBoardPane(game.getGameState().getBoard(), inputHandler);
        break;
    }
    Gui.getChildren().add(boardPane);
  }


  private void setUpUi() {
    playerStacks = new VBox();

    switch (game.getGamemode().getName()) {
      case "JUNIOR":
        ;
      case "DUO":
        ;
      case "CLASSIC":
        for (Player p : this.gameSession.getPlayerList()) {
          StackSquarePane squareStack = new StackSquarePane(p, inputHandler , game.getGameState().getRemainingPolys(p));
          playerStacks.getChildren().add(squareStack);
        }
        ;
        break;
      case "TRIGON":
        for (Player p : this.gameSession.getPlayerList()) {
          StackTrigonPane trigonStack = new StackTrigonPane(p, inputHandler, game.getGameState().getRemainingPolys(p));
          playerStacks.getChildren().add(trigonStack);
        }
        break;
    }

    Gui.getChildren().add(playerStacks);

    for (Player p : this.gameSession.getPlayerList()) {
      Label label = new Label("0");
      playerPoints.add(label);
    }
    //Gui.getChildren().addAll(playerPoints);

    quitButton = new Button("Quit");
    quitButton.setOnMouseClicked(mouseEvent -> this.handleQuitButtonClicked());
    Gui.getChildren().add(quitButton);
  }

  private void handleQuitButtonClicked() {
    gameController.setActiveUiController(new MainMenuUiController(gameController));
  }

  private void refreshUi() {
    playerStacks.getChildren().clear();
    for (Player p : game.getPlayers()) {
      switch (game.getGamemode().getName()) {
        case "JUNIOR":
          ;
        case "DUO":
          ;
        case "CLASSIC":
          StackSquarePane squareStack = new StackSquarePane(p, inputHandler, game.getGameState().getRemainingPolys(p));
          playerStacks.getChildren().add(squareStack);
          ;
          break;
        case "TRIGON":
          StackTrigonPane trigonStack = new StackTrigonPane(p, inputHandler, game.getGameState().getRemainingPolys(p));
          playerStacks.getChildren().add(trigonStack);
          break;
      }

    }

    /*Gui.getChildren().removeAll(playerPoints);
    for(Player p : game.getPlayers()){
      Label label = new Label(game.getGameState().getBoard().getScoreOfColor(game.getGameState().getColorFromPlayer(p))+"");
      playerPoints.add(label);
    }
    Gui.getChildren().addAll(playerPoints);*/
  }

  /**
   * function that updates the screen and calls the next move to be made
   * @param gameController
   * @param deltaTime
   *
   * @author //TODO hier die klasse hat jemand anders geschrieben. ich habe nur paar changes gemacht. echter autor am besten noch dazu schreiben
   * @author tgeilen
   */
  @Override
  public void update(AbstractGameController gameController, double deltaTime) {
    this.game.makeMove();
    boardPane.repaint(game.getGameState().getBoard());
    refreshUi();
    Player localPlayer = gameSession.getLocalPlayer();
    aiCalcRunning = true;
    //Check if AI is calculating - only refresh Board then
    if(aiCalcRunning){
      updateBoard();
    }
    //Check if Player has Turn
    if(game.getGameState().getPlayerCurrent().equals(localPlayer)){
      boolean action = false;
      //If localPlayer has selected a Poly, check if he also already click on the Board
      if(localPlayer.getSelectedPoly() != null){

        //create helpArraylist containing the selectedPoly to check the possible Moves
        ArrayList<Poly> helpList = new ArrayList<>();
        helpList.add(localPlayer.getSelectedPoly());

        ArrayList<Turn> possibleTurns = new ArrayList<Turn>();
        possibleTurns = game.getGameState().getBoard().getPossibleMoves(helpList, false);
        paintPossibleTurns(possibleTurns);
        /* TODO implement check of any FieldTile if it is clicked
        if(gameController.getInputHandler().isBoardClicked()){

        }
        */
      }
    }
  }

  public void paintPossibleTurns(ArrayList<Turn> possibleTurns){
    for(Turn turn : possibleTurns){
      //TODO Implement paint all possible Turns in Board
    }
  }

  public void updateBoard(){
    //TODO
  }

  protected void gameEnd(){
    //TODO
  }

  /**
   * Exit Method given by Abstract Class
   *
   * @author tgutberl
   */
  @Override
  public void onExit() {

  }
  /**
   * Init Method given by Abstract Class
   *
   * @author tgutberl
   */
  @Override
  public void init(Group root) {

  }

  /**
   * Method used to update the current frame
   * @param gameController GameController of game
   *
   * @author tgutberl
   */
  @Override
  public void update(AbstractGameController gameController) {

  }
}

