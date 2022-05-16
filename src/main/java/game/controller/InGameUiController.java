package game.controller;

import engine.component.Field;
import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import engine.handler.InputHandler;
import engine.handler.ThreadHandler;
import game.model.Game;
import game.model.GameSession;
import game.model.Turn;
import game.model.chat.Chat;
import game.model.player.Player;
import game.model.polygon.Poly;
import game.view.DragablePolyPane;
import game.view.DragableSuarePane;
import game.view.DragableTrigonPane;
import game.view.board.BoardPane;
import game.view.board.SquareBoardPane;
import game.view.board.TrigonBoardPane;
import game.view.poly.PolyPane;
import game.view.stack.StackPane;
import game.view.stack.StackSquarePane;
import game.view.stack.StackTrigonPane;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public abstract class InGameUiController extends AbstractUiController {

  private final GameSession gameSession;
  private final Game game;
  private final Chat chat;
  private final AbstractGameController gameController;
  private final InputHandler inputHandler;

  private final BorderPane pane;

  private final List<StackPane> stackPanes;
  private StackPane stackLocal;
  private VBox stacks;
  private VBox right;
  private DragablePolyPane dragablePolyPane;

  private BoardPane boardPane;

  private final List<Label> playerPoints;
  private final int count = 0;


  private Button quitButton;
  private HBox buttonBox;
  private boolean aiCalcRunning;
  private Player localPlayer;

  Stage stage;

  public InGameUiController(AbstractGameController gameController, Game game,
      GameSession gameSession, ThreadHandler threadHelp) {
    super(gameController);
    this.inputHandler = gameController.getInputHandler();
    this.gameSession = gameSession;
    this.game = gameSession.getGame();
    this.chat = gameSession.getChat();
    this.gameController = gameController;
    this.stage = gameController.getStage();
    this.pane = new BorderPane();
    playerPoints = new ArrayList<>();
    stackPanes = new ArrayList<>();
    threadHelp.start();
    super.root.getChildren().add(pane);
    createBoard();
    setUpUi();
  }

  public void createBoard() {
    switch (game.getGamemode().getName()) {
      case "JUNIOR":
      case "DUO":
      case "CLASSIC":
        boardPane = new SquareBoardPane(game.getGameState().getBoard(), inputHandler,
            stage.getWidth());
        break;
      case "TRIGON":
        boardPane = new TrigonBoardPane(game.getGameState().getBoard(), inputHandler,
            stage.getWidth());
        break;
    }
    pane.setCenter(boardPane);
  }

  private void setUpUi() {
    right = new VBox();
    stacks = new VBox();
    switch (game.getGamemode().getName()) {
      case "JUNIOR":
      case "DUO":
      case "CLASSIC":
        for (Player p : this.gameSession.getPlayerList()) {
          StackPane stackPane = new StackSquarePane(p, inputHandler,
              game.getGameState().getRemainingPolysClone(p), stage.getWidth());
          stackPanes.add(stackPane);
          stacks.getChildren().add(stackPane);
        }
        break;
      case "TRIGON":
        for (Player p : this.gameSession.getPlayerList()) {
          StackPane stackPane = new StackTrigonPane(p, inputHandler,
              game.getGameState().getRemainingPolysClone(p), stage.getWidth());
          stackPanes.add(stackPane);
          stacks.getChildren().add(stackPane);
        }
        break;
    }
    right.getChildren().add(stacks);
    pane.setRight(right);

   /* for (Player p : this.gameSession.getPlayerList()) {
      Label label = new Label("0");
      playerPoints.add(label);
    }
    Gui.getChildren().addAll(playerPoints);*/

    buttonBox = new HBox();
    quitButton = new Button("Quit");
    quitButton.setOnMouseClicked(mouseEvent -> this.handleQuitButtonClicked());
    buttonBox.getChildren().add(quitButton);
    pane.setBottom(buttonBox);
  }

  private void handleQuitButtonClicked() {

    gameController.setActiveUiController(new MainMenuUiController(gameController));
  }

  private void refreshUi() {
    stackPanes.clear();
    stacks.getChildren().clear();
    switch (game.getGamemode().getName()) {
      case "JUNIOR":
      case "DUO":
      case "CLASSIC":
        for (Player p : game.getPlayers()) {
          StackPane stackPane = new StackSquarePane(p, inputHandler,
              game.getGameState().getRemainingPolysClone(p), stage.getWidth());
          stackPanes.add(stackPane);
          stacks.getChildren().add(stackPane);
        }
        break;
      case "TRIGON":
        for (Player p : game.getPlayers()) {
          StackTrigonPane stackPane = new StackTrigonPane(p, inputHandler,
              game.getGameState().getRemainingPolysClone(p), stage.getWidth());
          stackPanes.add(stackPane);
          stacks.getChildren().add(stackPane);
        }
        break;
    }

  }

    /*Gui.getChildren().removeAll(playerPoints);
    for(Player p : game.getPlayers()){
      Label label = new Label(game.getGameState().getBoard().getScoreOfColor(game.getGameState().getColorFromPlayer(p))+"");
      playerPoints.add(label);
    }
    Gui.getChildren().addAll(playerPoints);*/

  /**
   * function that updates the screen and calls the next move to be made
   *
   * @param gameController
   * @param deltaTime
   * @author //TODO hier die klasse hat jemand anders geschrieben. ich habe nur paar changes
   * gemacht. echter autor am besten noch dazu schreiben
   * @author tgeilen
   */
  @Override
  public void update(AbstractGameController gameController, double deltaTime) {
    //Die Folgenden zwei Befehle sollten für einen Reibungslosen Spielablauf optimiert werden
    //this.game.makeMove();
    //boardPane.repaint(game.getGameState().getBoard());
    //noinspection LanguageDetectionInspection

    //makes board resizable
    stage.widthProperty().addListener((obs, oldVal, newVal) -> {
      boardPane.resize(stage.getWidth());
    });

    refreshUi();

    localPlayer = gameSession.getLocalPlayer();
    System.out.println("Localplayer : " + localPlayer.getType());
    aiCalcRunning = localPlayer.getAiCalcRunning();
    //Check if AI is calculating - only refresh Board then
    if (aiCalcRunning) {
      updateBoard();
    } else {
      //Check if Player has Turn
      for (Field field : boardPane.getFields()) {
        if (gameController.getInputHandler().isFieldPressed(field)) {
          System.out.println(
              "Field " + field.getX() + " " + field.getY() + " was Pressed in last Frame");
        }
      }

      if (game.getGameState().getPlayerCurrent().equals(localPlayer)) {
        boolean action = false;
        System.out.println("Current player is local player");

        for (PolyPane polyPane : stackPanes.get(0).getPolyPanes()) {
          if (inputHandler.isPolyClicked(polyPane)) {
            if (dragablePolyPane == null) {
              switch (game.getGamemode().getName()) {
                case "JUNIOR":
                case "DUO":
                case "CLASSIC":
                  dragablePolyPane = new DragableSuarePane(polyPane, boardPane.getSize(),
                      inputHandler);
                  break;
                case "TRIGON":
                  dragablePolyPane = new DragableTrigonPane(polyPane, boardPane.getSize(),
                      inputHandler);
                  break;
              }
              pane.setLeft(dragablePolyPane);
            } else {
              dragablePolyPane.setPoly(polyPane);
            }
            localPlayer.setSelectedPoly(polyPane.getPoly());
          }
        }

        if (this.dragablePolyPane != null) {
          for (Field field : boardPane.getFields()) {
            if (dragablePolyPane.intersects(field.getBoundsInParent())) {
              System.out.println(field.getX() + " " + field.getY() + " Intersection on this field");
            }
          }
        }

        //If localPlayer has selected a Poly, check if he also already click on the Board
        /*
        /*if (localPlayer.getSelectedPoly() != null) {
          localPlayer.setSelectedPoly(localPlayer.getSelectedPoly());
          System.out.println("Localplayer Selected Poly");
          //create helpArraylist containing the selectedPoly to check the possible Moves
          ArrayList<Poly> helpList = new ArrayList<>();
          helpList.add(localPlayer.getSelectedPoly());

          ArrayList<Turn> possibleTurns = new ArrayList<Turn>();
          possibleTurns = game.getGameState().getBoard().getPossibleMoves(helpList, false);
          localPlayer.setSelectedTurn(possibleTurns.get(0));
          paintPossibleTurns(possibleTurns);
          //TODO implement check of any FieldTile if it is clicked
        }*/
      }
    }
  }

  public void paintPossibleTurns(ArrayList<Turn> possibleTurns) {
    for (Turn turn : possibleTurns) {
      //TODO Implement paint all possible Turns in Board
    }
  }

  public void updateBoard() {
    //TODO
  }

  protected void gameEnd() {
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
   *
   * @param gameController GameController of game
   * @author tgutberl
   */
  @Override
  public void update(AbstractGameController gameController) {

  }
}

